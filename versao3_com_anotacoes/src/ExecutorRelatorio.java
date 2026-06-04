import anotacoes.PassoRelatorio;
import anotacoes.RelatorioConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * VERSÃO 3 — COM REFLEXÃO E ANOTAÇÕES
 *
 * ExecutorRelatorio usa REFLEXÃO para:
 * 1. Ler @RelatorioConfig da classe e exibir metadados
 * 2. Descobrir todos os métodos anotados com @PassoRelatorio
 * 3. Ordená-los pelo campo 'ordem'
 * 4. Invocá-los dinamicamente
 *
 * REDUÇÃO DE ACOPLAMENTO:
 * - Este executor NÃO conhece RelatorioPDF, RelatorioHTML ou RelatorioCSV
 * - Ele trabalha apenas com a interface RelatorioGerador e as anotações
 * - Adicionar um novo formato (ex: RelatorioXML) não exige alterar este executor
 * - Basta criar a nova classe com @RelatorioConfig e @PassoRelatorio nos métodos
 */
public class ExecutorRelatorio {

    /**
     * Executa um gerador de relatório descobrindo os passos via reflexão.
     *
     * @param gerador qualquer instância de RelatorioGerador
     */
    public void executar(RelatorioGerador gerador) {
        Class<?> classe = gerador.getClass();

        // 1. Lê os metadados da classe via @RelatorioConfig
        exibirMetadados(classe);

        // 2. Coleta todos os métodos anotados com @PassoRelatorio
        //    percorrendo a hierarquia completa (classe + superclasses)
        List<Method> passos = descobrirPassos(classe);

        // 3. Ordena pelos campos 'ordem' da anotação
        passos.sort(Comparator.comparingInt(m -> m.getAnnotation(PassoRelatorio.class).ordem()));

        System.out.println("=== Executando " + passos.size() + " passos via reflexão ===");

        // 4. Invoca cada método dinamicamente
        for (Method metodo : passos) {
            PassoRelatorio passo = metodo.getAnnotation(PassoRelatorio.class);
            System.out.println(
                "\n[Passo " + passo.ordem() + "] " + passo.descricao()
                + (passo.obrigatorio() ? "" : " (opcional)")
            );
            try {
                metodo.setAccessible(true);
                metodo.invoke(gerador);
            } catch (Exception e) {
                if (passo.obrigatorio()) {
                    System.err.println("ERRO no passo obrigatório '" + passo.descricao()
                            + "': " + e.getMessage());
                    throw new RuntimeException("Falha em passo obrigatório", e);
                } else {
                    System.out.println("  [AVISO] Passo opcional ignorado: " + e.getMessage());
                }
            }
        }

        System.out.println("\n=== Relatório gerado com sucesso! ===\n");
    }

    /**
     * Percorre a hierarquia de classes do gerador e coleta os métodos
     * anotados com @PassoRelatorio, eliminando duplicatas (sobrescrita).
     */
    private List<Method> descobrirPassos(Class<?> classe) {
        List<Method> passos = new ArrayList<>();
        List<String> nomesCobertos = new ArrayList<>();

        Class<?> atual = classe;
        while (atual != null && atual != Object.class) {
            for (Method metodo : atual.getDeclaredMethods()) {
                // Só inclui se tem a anotação E ainda não foi coberto por subclasse
                if (metodo.isAnnotationPresent(PassoRelatorio.class)
                        && !nomesCobertos.contains(metodo.getName())) {
                    passos.add(metodo);
                    nomesCobertos.add(metodo.getName());
                }
            }
            atual = atual.getSuperclass();
        }

        return passos;
    }

    /**
     * Lê @RelatorioConfig da classe e exibe os metadados formatados.
     */
    private void exibirMetadados(Class<?> classe) {
        if (classe.isAnnotationPresent(RelatorioConfig.class)) {
            RelatorioConfig config = classe.getAnnotation(RelatorioConfig.class);
            System.out.println("┌─────────────────────────────────────────┐");
            System.out.printf("│ %-41s│%n", " Gerador: " + config.nome());
            System.out.printf("│ %-41s│%n", " Extensão: ." + config.extensao());
            System.out.printf("│ %-41s│%n", " Versão: " + config.versao());
            System.out.printf("│ %-41s│%n", " Descrição: " + config.descricao());
            System.out.println("└─────────────────────────────────────────┘");
        } else {
            System.out.println("[ExecutorRelatorio] Classe sem @RelatorioConfig: "
                    + classe.getSimpleName());
        }
    }
}

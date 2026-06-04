import anotacoes.PassoRelatorio;
import anotacoes.RelatorioConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExecutorRelatorio {

    public void executar(RelatorioGerador gerador) {
        Class<?> classe = gerador.getClass();

        exibirMetadados(classe);

        List<Method> passos = descobrirPassos(classe);

        passos.sort(Comparator.comparingInt(m -> m.getAnnotation(PassoRelatorio.class).ordem()));

        System.out.println("=== Executando " + passos.size() + " passos via reflexão ===");

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

    private List<Method> descobrirPassos(Class<?> classe) {
        List<Method> passos = new ArrayList<>();
        List<String> nomesCobertos = new ArrayList<>();

        Class<?> atual = classe;
        while (atual != null && atual != Object.class) {
            for (Method metodo : atual.getDeclaredMethods()) {
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

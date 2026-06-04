/**
 * VERSÃO 1 — SEM PADRÃO GoF
 *
 * Problema: terceira classe com o mesmo fluxo duplicado.
 * Se adicionarmos um passo de "log de auditoria", precisamos
 * editar TRÊS arquivos diferentes — altíssimo risco de inconsistência.
 */
public class RelatorioCSV {

    private String titulo;
    private String[] dados;

    public RelatorioCSV(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    // PROBLEMA: fluxo duplicado pela terceira vez
    public void gerar() {
        System.out.println("=== [CSV] Iniciando geração de relatório ===");

        // Passo 1: Coletar dados (DUPLICADO)
        System.out.println("[CSV] Coletando dados do banco de dados...");
        System.out.println("[CSV] " + dados.length + " registros carregados.");

        // Passo 2: Processar dados (DUPLICADO)
        System.out.println("[CSV] Processando e validando registros...");

        // Passo 3: Formatar cabeçalho — apenas isso muda!
        System.out.println("titulo,data_geracao,versao");
        System.out.println("\"" + titulo + "\",\"" + java.time.LocalDate.now() + "\",\"1.0\"");

        // Passo 4: Formatar corpo — apenas isso muda!
        System.out.println("[CSV] Serializando dados em formato delimitado...");
        System.out.println("indice,valor");
        for (int i = 0; i < dados.length; i++) {
            System.out.println((i + 1) + ",\"" + dados[i] + "\"");
        }

        // Passo 5: Formatar rodapé — apenas isso muda!
        System.out.println("# Total de registros: " + dados.length);
        System.out.println("# Fim do arquivo CSV");

        // Passo 6: Exportar (DUPLICADO)
        System.out.println("[CSV] Exportando arquivo: " + titulo + ".csv");
        System.out.println("=== [CSV] Relatório gerado com sucesso! ===\n");
    }
}

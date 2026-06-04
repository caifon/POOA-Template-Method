public class RelatorioCSV {

    private String titulo;
    private String[] dados;

    public RelatorioCSV(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    public void gerar() {
        System.out.println("=== [CSV] Iniciando geração de relatório ===");

        System.out.println("[CSV] Coletando dados do banco de dados...");
        System.out.println("[CSV] " + dados.length + " registros carregados.");

        System.out.println("[CSV] Processando e validando registros...");

        System.out.println("titulo,data_geracao,versao");
        System.out.println("\"" + titulo + "\",\"" + java.time.LocalDate.now() + "\",\"1.0\"");

        System.out.println("[CSV] Serializando dados em formato delimitado...");
        System.out.println("indice,valor");
        for (int i = 0; i < dados.length; i++) {
            System.out.println((i + 1) + ",\"" + dados[i] + "\"");
        }

        System.out.println("# Total de registros: " + dados.length);
        System.out.println("# Fim do arquivo CSV");

        System.out.println("[CSV] Exportando arquivo: " + titulo + ".csv");
        System.out.println("=== [CSV] Relatório gerado com sucesso! ===\n");
    }
}

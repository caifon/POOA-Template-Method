public class RelatorioPDF {

    private String titulo;
    private String[] dados;

    public RelatorioPDF(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    public void gerar() {
        System.out.println("=== [PDF] Iniciando geração de relatório ===");

        System.out.println("[PDF] Coletando dados do banco de dados...");
        System.out.println("[PDF] " + dados.length + " registros carregados.");

        System.out.println("[PDF] Processando e validando registros...");

        System.out.println("----------------------------------------");
        System.out.println("  RELATÓRIO PDF - FORMATO LANDSCAPE");
        System.out.println("  Título: " + titulo);
        System.out.println("  Gerado em: " + java.time.LocalDate.now());
        System.out.println("----------------------------------------");

        System.out.println("[PDF] Renderizando corpo com fontes embutidas...");
        for (String linha : dados) {
            System.out.println("  • " + linha);
        }

        System.out.println("----------------------------------------");
        System.out.println("  Página 1 de 1 | Confidencial | PDF/A");
        System.out.println("----------------------------------------");

        System.out.println("[PDF] Exportando arquivo: " + titulo + ".pdf");
        System.out.println("=== [PDF] Relatório gerado com sucesso! ===\n");
    }
}

public class RelatorioPDF extends RelatorioGerador {

    public RelatorioPDF(String titulo, String[] dados) {
        super(titulo, dados);
    }

    @Override
    protected void formatarCabecalho() {
        System.out.println("----------------------------------------");
        System.out.println("  RELATÓRIO PDF - FORMATO LANDSCAPE");
        System.out.println("  Título: " + titulo);
        System.out.println("  Gerado em: " + java.time.LocalDate.now());
        System.out.println("----------------------------------------");
    }

    @Override
    protected void formatarCorpo() {
        System.out.println("[PDF] Renderizando corpo com fontes embutidas...");
        for (String linha : dados) {
            System.out.println("  • " + linha);
        }
    }

    @Override
    protected void formatarRodape() {
        System.out.println("----------------------------------------");
        System.out.println("  Página 1 de 1 | Confidencial | PDF/A");
        System.out.println("----------------------------------------");
    }

    @Override
    protected void incluirMarcaDagua() {
        System.out.println("[PDF] Adicionando marca d'água: CONFIDENCIAL");
    }

    @Override
    protected String getFormato() {
        return "PDF";
    }
}

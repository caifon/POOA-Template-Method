public class RelatorioHTML extends RelatorioGerador {

    public RelatorioHTML(String titulo, String[] dados) {
        super(titulo, dados);
    }

    @Override
    protected void formatarCabecalho() {
        System.out.println("<html><head><title>" + titulo + "</title></head><body>");
        System.out.println("<h1>" + titulo + "</h1>");
        System.out.println("<p>Gerado em: " + java.time.LocalDate.now() + "</p>");
    }

    @Override
    protected void formatarCorpo() {
        System.out.println("[HTML] Renderizando tabela HTML...");
        System.out.println("<table border='1'><tr><th>Dados</th></tr>");
        for (String linha : dados) {
            System.out.println("  <tr><td>" + linha + "</td></tr>");
        }
        System.out.println("</table>");
    }

    @Override
    protected void formatarRodape() {
        System.out.println("<footer><p>Relatório gerado automaticamente.</p></footer>");
        System.out.println("</body></html>");
    }

    @Override
    protected String getFormato() {
        return "HTML";
    }
}

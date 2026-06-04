public class RelatorioHTML {

    private String titulo;
    private String[] dados;

    public RelatorioHTML(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    public void gerar() {
        System.out.println("=== [HTML] Iniciando geração de relatório ===");

        System.out.println("[HTML] Coletando dados do banco de dados...");
        System.out.println("[HTML] " + dados.length + " registros carregados.");

        System.out.println("[HTML] Processando e validando registros...");

        System.out.println("<html><head><title>" + titulo + "</title></head><body>");
        System.out.println("<h1>" + titulo + "</h1>");
        System.out.println("<p>Gerado em: " + java.time.LocalDate.now() + "</p>");

        System.out.println("[HTML] Renderizando tabela HTML...");
        System.out.println("<table border='1'><tr><th>Dados</th></tr>");
        for (String linha : dados) {
            System.out.println("  <tr><td>" + linha + "</td></tr>");
        }
        System.out.println("</table>");

        System.out.println("<footer><p>Relatório gerado automaticamente.</p></footer>");
        System.out.println("</body></html>");

        System.out.println("[HTML] Exportando arquivo: " + titulo + ".html");
        System.out.println("=== [HTML] Relatório gerado com sucesso! ===\n");
    }
}

/**
 * VERSÃO 1 — SEM PADRÃO GoF
 *
 * Problema: código duplicado. Os passos 1, 2 e 6 são idênticos ao RelatorioPDF
 * e ao RelatorioCSV. Qualquer mudança no fluxo exige alterar as 3 classes.
 */
public class RelatorioHTML {

    private String titulo;
    private String[] dados;

    public RelatorioHTML(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    // PROBLEMA: fluxo duplicado — idêntico à estrutura de RelatorioPDF e RelatorioCSV
    public void gerar() {
        System.out.println("=== [HTML] Iniciando geração de relatório ===");

        // Passo 1: Coletar dados (DUPLICADO)
        System.out.println("[HTML] Coletando dados do banco de dados...");
        System.out.println("[HTML] " + dados.length + " registros carregados.");

        // Passo 2: Processar dados (DUPLICADO)
        System.out.println("[HTML] Processando e validando registros...");

        // Passo 3: Formatar cabeçalho — apenas isso muda!
        System.out.println("<html><head><title>" + titulo + "</title></head><body>");
        System.out.println("<h1>" + titulo + "</h1>");
        System.out.println("<p>Gerado em: " + java.time.LocalDate.now() + "</p>");

        // Passo 4: Formatar corpo — apenas isso muda!
        System.out.println("[HTML] Renderizando tabela HTML...");
        System.out.println("<table border='1'><tr><th>Dados</th></tr>");
        for (String linha : dados) {
            System.out.println("  <tr><td>" + linha + "</td></tr>");
        }
        System.out.println("</table>");

        // Passo 5: Formatar rodapé — apenas isso muda!
        System.out.println("<footer><p>Relatório gerado automaticamente.</p></footer>");
        System.out.println("</body></html>");

        // Passo 6: Exportar (DUPLICADO)
        System.out.println("[HTML] Exportando arquivo: " + titulo + ".html");
        System.out.println("=== [HTML] Relatório gerado com sucesso! ===\n");
    }
}

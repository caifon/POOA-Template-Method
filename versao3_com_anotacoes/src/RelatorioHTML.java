import anotacoes.PassoRelatorio;
import anotacoes.RelatorioConfig;

/**
 * VERSÃO 3 — COM REFLEXÃO E ANOTAÇÕES
 *
 * Relatório HTML com anotações @PassoRelatorio nos métodos variáveis.
 */
@RelatorioConfig(
    nome = "Relatório HTML",
    extensao = "html",
    versao = "1.5",
    descricao = "Formato web interativo para visualização em navegador"
)
public class RelatorioHTML extends RelatorioGerador {

    public RelatorioHTML(String titulo, String[] dados) {
        super(titulo, dados);
    }

    @Override
    @PassoRelatorio(ordem = 3, descricao = "Geração do cabeçalho HTML")
    public void formatarCabecalho() {
        System.out.println("<html><head><title>" + titulo + "</title></head><body>");
        System.out.println("<h1>" + titulo + "</h1>");
        System.out.println("<p>Gerado em: " + java.time.LocalDate.now() + "</p>");
    }

    @Override
    @PassoRelatorio(ordem = 4, descricao = "Geração de tabela HTML com os dados")
    public void formatarCorpo() {
        System.out.println("[HTML] Renderizando tabela HTML...");
        System.out.println("<table border='1'><tr><th>Dados</th></tr>");
        for (String linha : dados) {
            System.out.println("  <tr><td>" + linha + "</td></tr>");
        }
        System.out.println("</table>");
    }

    @Override
    @PassoRelatorio(ordem = 6, descricao = "Geração do rodapé HTML")
    public void formatarRodape() {
        System.out.println("<footer><p>Relatório gerado automaticamente.</p></footer>");
        System.out.println("</body></html>");
    }

    // incluirMarcaDagua() não é sobrescrito → o ExecutorRelatorio
    // detecta que a versão herdada não tem @PassoRelatorio e a ignora

    @Override
    public String getFormato() {
        return "HTML";
    }
}

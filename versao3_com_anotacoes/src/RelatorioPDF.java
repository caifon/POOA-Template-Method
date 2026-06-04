import anotacoes.PassoRelatorio;
import anotacoes.RelatorioConfig;

@RelatorioConfig(
    nome = "Relatório PDF",
    extensao = "pdf",
    versao = "2.1",
    descricao = "Formato vetorial para impressão e arquivamento"
)
public class RelatorioPDF extends RelatorioGerador {

    public RelatorioPDF(String titulo, String[] dados) {
        super(titulo, dados);
    }

    @Override
    @PassoRelatorio(ordem = 3, descricao = "Formatação do cabeçalho PDF")
    public void formatarCabecalho() {
        System.out.println("----------------------------------------");
        System.out.println("  RELATÓRIO PDF - FORMATO LANDSCAPE");
        System.out.println("  Título: " + titulo);
        System.out.println("  Gerado em: " + java.time.LocalDate.now());
        System.out.println("----------------------------------------");
    }

    @Override
    @PassoRelatorio(ordem = 4, descricao = "Renderização do corpo com fontes embutidas")
    public void formatarCorpo() {
        System.out.println("[PDF] Renderizando corpo com fontes embutidas...");
        for (String linha : dados) {
            System.out.println("  • " + linha);
        }
    }

    @Override
    @PassoRelatorio(
        ordem = 5,
        descricao = "Inserção de marca d'água confidencial",
        obrigatorio = false
    )
    public void incluirMarcaDagua() {
        System.out.println("[PDF] Adicionando marca d'água: CONFIDENCIAL");
    }

    @Override
    @PassoRelatorio(ordem = 6, descricao = "Formatação do rodapé PDF")
    public void formatarRodape() {
        System.out.println("----------------------------------------");
        System.out.println("  Página 1 de 1 | Confidencial | PDF/A");
        System.out.println("----------------------------------------");
    }

    @Override
    public String getFormato() {
        return "PDF";
    }
}

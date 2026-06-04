import anotacoes.PassoRelatorio;
import anotacoes.RelatorioConfig;

/**
 * VERSÃO 3 — COM REFLEXÃO E ANOTAÇÕES
 *
 * Relatório CSV com anotações @PassoRelatorio nos métodos variáveis.
 */
@RelatorioConfig(
    nome = "Relatório CSV",
    extensao = "csv",
    versao = "3.0",
    descricao = "Formato tabular para importação em planilhas e sistemas"
)
public class RelatorioCSV extends RelatorioGerador {

    public RelatorioCSV(String titulo, String[] dados) {
        super(titulo, dados);
    }

    @Override
    @PassoRelatorio(ordem = 3, descricao = "Escrita do cabeçalho CSV com metadados")
    public void formatarCabecalho() {
        System.out.println("titulo,data_geracao,versao");
        System.out.println("\"" + titulo + "\",\"" + java.time.LocalDate.now() + "\",\"1.0\"");
    }

    @Override
    @PassoRelatorio(ordem = 4, descricao = "Serialização dos dados em linhas CSV")
    public void formatarCorpo() {
        System.out.println("[CSV] Serializando dados em formato delimitado...");
        System.out.println("indice,valor");
        for (int i = 0; i < dados.length; i++) {
            System.out.println((i + 1) + ",\"" + dados[i] + "\"");
        }
    }

    @Override
    @PassoRelatorio(ordem = 6, descricao = "Escrita do rodapé e sumário CSV")
    public void formatarRodape() {
        System.out.println("# Total de registros: " + dados.length);
        System.out.println("# Fim do arquivo CSV");
    }

    @Override
    public String getFormato() {
        return "CSV";
    }
}

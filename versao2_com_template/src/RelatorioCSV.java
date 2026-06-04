/**
 * VERSÃO 2 — COM PADRÃO TEMPLATE METHOD
 *
 * Relatório em formato CSV.
 * Apenas os métodos que realmente diferem são implementados aqui.
 */
public class RelatorioCSV extends RelatorioGerador {

    public RelatorioCSV(String titulo, String[] dados) {
        super(titulo, dados);
    }

    @Override
    protected void formatarCabecalho() {
        System.out.println("titulo,data_geracao,versao");
        System.out.println("\"" + titulo + "\",\"" + java.time.LocalDate.now() + "\",\"1.0\"");
    }

    @Override
    protected void formatarCorpo() {
        System.out.println("[CSV] Serializando dados em formato delimitado...");
        System.out.println("indice,valor");
        for (int i = 0; i < dados.length; i++) {
            System.out.println((i + 1) + ",\"" + dados[i] + "\"");
        }
    }

    @Override
    protected void formatarRodape() {
        System.out.println("# Total de registros: " + dados.length);
        System.out.println("# Fim do arquivo CSV");
    }

    @Override
    protected String getFormato() {
        return "CSV";
    }
}

import anotacoes.PassoRelatorio;

public abstract class RelatorioGerador {

    protected String titulo;
    protected String[] dados;

    public RelatorioGerador(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    @PassoRelatorio(ordem = 1, descricao = "Coleta de dados do repositório")
    public void coletarDados() {
        System.out.println("[" + getFormato() + "] Coletando dados do banco de dados...");
        System.out.println("[" + getFormato() + "] " + dados.length + " registros carregados.");
    }

    @PassoRelatorio(ordem = 2, descricao = "Processamento e validação dos registros")
    public void processarDados() {
        System.out.println("[" + getFormato() + "] Processando e validando registros...");
    }

    @PassoRelatorio(ordem = 7, descricao = "Exportação do arquivo final")
    public void exportar() {
        System.out.println("[" + getFormato() + "] Exportando arquivo: "
                + titulo + "." + getFormato().toLowerCase());
    }

    public abstract void formatarCabecalho();

    public abstract void formatarCorpo();

    public abstract void formatarRodape();

    public void incluirMarcaDagua() {
    }

    public abstract String getFormato();

    public String getTitulo() {
        return titulo;
    }
}

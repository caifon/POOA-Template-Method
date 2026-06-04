public abstract class RelatorioGerador {

    protected String titulo;
    protected String[] dados;

    public RelatorioGerador(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    public final void gerarRelatorio() {
        System.out.println("=== Iniciando geração de relatório ===");

        coletarDados();
        processarDados();
        formatarCabecalho();
        formatarCorpo();
        incluirMarcaDagua();
        formatarRodape();
        exportar();

        System.out.println("=== Relatório gerado com sucesso! ===\n");
    }

    private void coletarDados() {
        System.out.println("[" + getFormato() + "] Coletando dados do banco de dados...");
        System.out.println("[" + getFormato() + "] " + dados.length + " registros carregados.");
    }

    private void processarDados() {
        System.out.println("[" + getFormato() + "] Processando e validando registros...");
    }

    private void exportar() {
        System.out.println("[" + getFormato() + "] Exportando arquivo: "
                + titulo + "." + getFormato().toLowerCase());
    }

    protected abstract void formatarCabecalho();

    protected abstract void formatarCorpo();

    protected abstract void formatarRodape();

    protected void incluirMarcaDagua() {
    }

    protected abstract String getFormato();
}

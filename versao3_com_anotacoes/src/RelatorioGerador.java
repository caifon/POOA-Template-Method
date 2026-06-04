import anotacoes.PassoRelatorio;

/**
 * VERSÃO 3 — COM REFLEXÃO E ANOTAÇÕES
 *
 * Classe abstrata base para geradores de relatório.
 *
 * Diferença em relação à Versão 2:
 * - Os métodos de passo são anotados com @PassoRelatorio
 * - O ExecutorRelatorio usa REFLEXÃO para descobrir e invocar esses métodos
 * - Não há mais um método gerarRelatorio() chamando os passos explicitamente
 * - Isso reduz o acoplamento: o executor não conhece os nomes concretos dos métodos
 */
public abstract class RelatorioGerador {

    protected String titulo;
    protected String[] dados;

    public RelatorioGerador(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    // -------------------------------------------------------
    // Passos FIXOS — implementados aqui com @PassoRelatorio
    // -------------------------------------------------------

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

    // -------------------------------------------------------
    // Passos VARIÁVEIS — subclasses anotam com @PassoRelatorio
    // -------------------------------------------------------

    public abstract void formatarCabecalho();

    public abstract void formatarCorpo();

    public abstract void formatarRodape();

    /**
     * Hook method — não obrigatório.
     * Subclasses que quiserem adicionar marca d'água sobrescrevem e anotam.
     */
    public void incluirMarcaDagua() {
        // padrão: sem efeito
    }

    // -------------------------------------------------------
    // Auxiliar
    // -------------------------------------------------------

    public abstract String getFormato();

    public String getTitulo() {
        return titulo;
    }
}

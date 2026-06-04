/**
 * VERSÃO 2 — COM PADRÃO TEMPLATE METHOD
 *
 * Classe abstrata que define o ESQUELETO do algoritmo de geração de relatório.
 * O método gerarRelatorio() é o "template" — ele chama os passos na ordem certa.
 *
 * Passos fixos (implementados aqui):
 *   - coletarDados()
 *   - processarDados()
 *   - exportar()
 *
 * Passos variáveis (abstratos — cada subclasse implementa):
 *   - formatarCabecalho()
 *   - formatarCorpo()
 *   - formatarRodape()
 *
 * Hook method (opcional, subclasses podem sobrescrever):
 *   - incluirMarcaDagua()
 */
public abstract class RelatorioGerador {

    protected String titulo;
    protected String[] dados;

    public RelatorioGerador(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    /**
     * TEMPLATE METHOD — define o esqueleto do algoritmo.
     * Este método é final: nenhuma subclasse pode alterar a ordem dos passos.
     */
    public final void gerarRelatorio() {
        System.out.println("=== Iniciando geração de relatório ===");

        coletarDados();      // Passo fixo
        processarDados();    // Passo fixo
        formatarCabecalho(); // Variável — subclasse define
        formatarCorpo();     // Variável — subclasse define
        incluirMarcaDagua(); // Hook — opcional
        formatarRodape();    // Variável — subclasse define
        exportar();          // Passo fixo

        System.out.println("=== Relatório gerado com sucesso! ===\n");
    }

    // -------------------------------------------------------
    // Passos FIXOS — implementados na classe abstrata
    // -------------------------------------------------------

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

    // -------------------------------------------------------
    // Passos VARIÁVEIS — subclasses obrigatoriamente implementam
    // -------------------------------------------------------

    protected abstract void formatarCabecalho();

    protected abstract void formatarCorpo();

    protected abstract void formatarRodape();

    /**
     * Hook method — comportamento padrão: não adiciona marca d'água.
     * Subclasses podem sobrescrever se quiserem.
     */
    protected void incluirMarcaDagua() {
        // padrão: sem marca d'água
    }

    // -------------------------------------------------------
    // Método auxiliar — retorna o nome do formato para logs
    // -------------------------------------------------------

    protected abstract String getFormato();
}

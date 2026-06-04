/**
 * VERSÃO 1 — SEM PADRÃO GoF
 *
 * Problema: o fluxo de geração de relatório está completamente duplicado
 * em cada classe. Se precisarmos adicionar um novo passo (ex: auditoria),
 * teremos que alterar RelatorioPDF, RelatorioHTML e RelatorioCSV.
 * Isso viola o princípio DRY e o Open/Closed Principle.
 */
public class RelatorioPDF {

    private String titulo;
    private String[] dados;

    public RelatorioPDF(String titulo, String[] dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    // PROBLEMA: este método duplica o mesmo fluxo das outras classes
    public void gerar() {
        System.out.println("=== [PDF] Iniciando geração de relatório ===");

        // Passo 1: Coletar dados
        System.out.println("[PDF] Coletando dados do banco de dados...");
        System.out.println("[PDF] " + dados.length + " registros carregados.");

        // Passo 2: Processar dados
        System.out.println("[PDF] Processando e validando registros...");

        // Passo 3: Formatar cabeçalho
        System.out.println("----------------------------------------");
        System.out.println("  RELATÓRIO PDF - FORMATO LANDSCAPE");
        System.out.println("  Título: " + titulo);
        System.out.println("  Gerado em: " + java.time.LocalDate.now());
        System.out.println("----------------------------------------");

        // Passo 4: Formatar corpo
        System.out.println("[PDF] Renderizando corpo com fontes embutidas...");
        for (String linha : dados) {
            System.out.println("  • " + linha);
        }

        // Passo 5: Formatar rodapé
        System.out.println("----------------------------------------");
        System.out.println("  Página 1 de 1 | Confidencial | PDF/A");
        System.out.println("----------------------------------------");

        // Passo 6: Exportar
        System.out.println("[PDF] Exportando arquivo: " + titulo + ".pdf");
        System.out.println("=== [PDF] Relatório gerado com sucesso! ===\n");
    }
}

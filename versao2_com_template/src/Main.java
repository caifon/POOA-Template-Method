/**
 * VERSÃO 2 — COM PADRÃO TEMPLATE METHOD
 *
 * Para compilar e executar:
 *   cd versao2_com_template/src
 *   javac *.java
 *   java Main
 */
public class Main {

    public static void main(String[] args) {
        String[] dados = {
            "Vendas Janeiro: R$ 15.200,00",
            "Vendas Fevereiro: R$ 18.750,00",
            "Vendas Março: R$ 22.100,00"
        };

        System.out.println("############################################");
        System.out.println("#  VERSÃO 2 — COM TEMPLATE METHOD          #");
        System.out.println("#  Fluxo único na superclasse abstrata     #");
        System.out.println("############################################\n");

        // Polimorfismo: a referência é do tipo abstrato RelatorioGerador
        RelatorioGerador[] relatorios = {
            new RelatorioPDF("Relatório de Vendas Q1", dados),
            new RelatorioHTML("Relatório de Vendas Q1", dados),
            new RelatorioCSV("Relatório de Vendas Q1", dados)
        };

        for (RelatorioGerador relatorio : relatorios) {
            relatorio.gerarRelatorio(); // sempre chama o template
        }

        System.out.println("VANTAGEM DO TEMPLATE METHOD:");
        System.out.println("- O fluxo está definido UMA VEZ em RelatorioGerador.");
        System.out.println("- Para adicionar um novo passo (ex: auditoria),");
        System.out.println("  basta editar RelatorioGerador — apenas 1 lugar.");
        System.out.println("- Subclasses implementam APENAS o que é diferente.");
    }
}

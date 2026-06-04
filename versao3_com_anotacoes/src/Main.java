public class Main {

    public static void main(String[] args) {
        String[] dados = {
            "Vendas Janeiro: R$ 15.200,00",
            "Vendas Fevereiro: R$ 18.750,00",
            "Vendas Março: R$ 22.100,00"
        };

        System.out.println("##################################################");
        System.out.println("#  VERSÃO 3 — REFLEXÃO E ANOTAÇÕES               #");
        System.out.println("#  ExecutorRelatorio descobre passos via reflexão #");
        System.out.println("##################################################\n");

        ExecutorRelatorio executor = new ExecutorRelatorio();

        RelatorioGerador[] relatorios = {
            new RelatorioPDF("Relatório de Vendas Q1", dados),
            new RelatorioHTML("Relatório de Vendas Q1", dados),
            new RelatorioCSV("Relatório de Vendas Q1", dados)
        };

        for (RelatorioGerador relatorio : relatorios) {
            executor.executar(relatorio);
        }

        System.out.println("=======================================================");
        System.out.println("VANTAGEM DA REFLEXÃO + ANOTAÇÕES:");
        System.out.println("- ExecutorRelatorio NÃO conhece PDF/HTML/CSV.");
        System.out.println("- Para adicionar RelatorioXML: criar a classe com");
        System.out.println("  @RelatorioConfig e @PassoRelatorio — sem tocar no executor.");
        System.out.println("- Os metadados ficam declarados junto ao código que os usa.");
        System.out.println("- Acoplamento mínimo: apenas à anotação @PassoRelatorio.");
        System.out.println("=======================================================");
    }
}

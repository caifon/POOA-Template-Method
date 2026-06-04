public class Main {

    public static void main(String[] args) {
        String[] dados = {
            "Vendas Janeiro: R$ 15.200,00",
            "Vendas Fevereiro: R$ 18.750,00",
            "Vendas Março: R$ 22.100,00"
        };

        System.out.println("############################################");
        System.out.println("#  VERSÃO 1 — SEM PADRÃO GoF              #");
        System.out.println("#  Problema: código duplicado nas classes  #");
        System.out.println("############################################\n");

        RelatorioPDF pdf = new RelatorioPDF("Relatório de Vendas Q1", dados);
        pdf.gerar();

        RelatorioHTML html = new RelatorioHTML("Relatório de Vendas Q1", dados);
        html.gerar();

        RelatorioCSV csv = new RelatorioCSV("Relatório de Vendas Q1", dados);
        csv.gerar();

        System.out.println("PROBLEMA IDENTIFICADO:");
        System.out.println("- Os passos 1, 2 e 6 estão duplicados nas 3 classes.");
        System.out.println("- Adicionar um novo passo exige editar 3 arquivos.");
        System.out.println("- Alto risco de inconsistência e violação do DRY.");
    }
}

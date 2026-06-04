package anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VERSÃO 3 — ANOTAÇÃO PRÓPRIA
 *
 * @RelatorioConfig anota a classe com metadados sobre o tipo de relatório.
 * O ExecutorRelatorio usa essa anotação para exibir informações sobre
 * o relatório antes de executá-lo, sem precisar chamar métodos específicos
 * de cada classe concreta.
 *
 * Benefício: desacopla os metadados do código — as informações estão
 * declaradas na anotação, não espalhadas em métodos ou construtores.
 */
@Retention(RetentionPolicy.RUNTIME)  // disponível em tempo de execução
@Target(ElementType.TYPE)            // aplicável a classes
public @interface RelatorioConfig {

    /**
     * Nome amigável do formato de relatório.
     */
    String nome();

    /**
     * Extensão do arquivo gerado (ex: "pdf", "html", "csv").
     */
    String extensao();

    /**
     * Versão do gerador deste formato.
     */
    String versao() default "1.0";

    /**
     * Descrição do formato.
     */
    String descricao() default "";
}

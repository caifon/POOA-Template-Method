package anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VERSÃO 3 — ANOTAÇÃO PRÓPRIA
 *
 * @PassoRelatorio marca um método como sendo um passo do algoritmo
 * de geração de relatório. O ExecutorRelatorio usa reflexão para
 * descobrir e executar esses métodos na ordem correta.
 *
 * Isso REDUZ O ACOPLAMENTO porque o executor não precisa conhecer
 * os nomes concretos dos métodos de cada subclasse — ele os
 * descobre dinamicamente em tempo de execução via reflexão.
 */
@Retention(RetentionPolicy.RUNTIME)  // disponível em tempo de execução (reflexão)
@Target(ElementType.METHOD)          // aplicável apenas a métodos
public @interface PassoRelatorio {

    /**
     * Ordem de execução do passo (menor = primeiro).
     */
    int ordem();

    /**
     * Descrição legível do passo, usada nos logs.
     */
    String descricao() default "Passo do relatório";

    /**
     * Indica se este passo é obrigatório.
     * Passos não obrigatórios são hooks — podem ser ignorados se lançarem exceção.
     */
    boolean obrigatorio() default true;
}

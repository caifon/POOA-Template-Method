# Slides — Padrão Template Method

**Trabalho AVII — POOA | UCSal | 04/06/2026**  
**Equipe:** Caio Roldão · João Mário · Juvenal Junior

---

## SLIDE 1 — Capa

```
╔═══════════════════════════════════════════════════╗
║                                                   ║
║    Padrão de Projeto GoF                          ║
║    ▓▓▓ TEMPLATE METHOD ▓▓▓                        ║
║                                                   ║
║    Programação Orientada a Objetos Avançada       ║
║    UCSal — 2026.1                                 ║
║                                                   ║
║    Caio Roldão                                    ║
║    João Mário                                     ║
║    Juvenal Junior                                 ║
║                                                   ║
╚═══════════════════════════════════════════════════╝
```

---

## SLIDE 2 — Agenda

1. Definição do Problema
2. Por que a solução ingênua falha
3. O padrão Template Method
4. Aplicação prática — Sistema de Relatórios
5. Uso de Reflexão e Anotações
6. Como isso reduz o acoplamento
7. Demonstração ao vivo

---

## SLIDE 3 — Definição do Problema

### Sistema de Geração de Relatórios

> Uma empresa precisa gerar relatórios financeiros em 3 formatos:
> **PDF**, **HTML** e **CSV**

**O fluxo é sempre o mesmo:**

```
[1] Coletar dados  →  [2] Processar  →  [3] Formatar  →  [4] Exportar
```

**O que muda entre os formatos?**
- Estrutura do cabeçalho
- Layout do corpo
- Estrutura do rodapé

**O que NÃO muda?**
- Passos 1, 2 e 4 são idênticos em todos os formatos

---

## SLIDE 4 — Solução Ingênua (Versão 1 — Sem GoF)

### O que foi feito?

Cada classe tem seu próprio método `gerar()` com **tudo** duplicado:

```java
// RelatorioPDF.gerar()          // RelatorioHTML.gerar()
coletarDados(); ← DUPLICADO      coletarDados(); ← DUPLICADO
processarDados(); ← DUPLICADO    processarDados(); ← DUPLICADO
[formatar PDF]                   [formatar HTML]
exportar(); ← DUPLICADO          exportar(); ← DUPLICADO
```

### Problemas

| ❌ Problema | Impacto |
|------------|---------|
| Código duplicado (DRY) | 3 cópias dos mesmos passos |
| Violação do OCP | Novo passo = editar 3 arquivos |
| Sem garantia de ordem | Qualquer classe pode omitir um passo |
| Alta manutenção | Bug em 1 lugar → consertar em 3 |

---

## SLIDE 5 — O Padrão Template Method

### Definição (GoF)

> "Define o esqueleto de um algoritmo, delegando alguns passos às subclasses"

### Estrutura

```
ClasseAbstrata
├── templateMethod()  ← final! Ninguém muda o esqueleto
│   ├── passoFixo1()  ← implementado aqui
│   ├── passoAbstrato() ← subclasse obrigatoriamente implementa
│   ├── hookMethod()  ← opcional, padrão vazio
│   └── passoFixo2()  ← implementado aqui
│
├── SubclasseA → implementa passoAbstrato()
└── SubclasseB → implementa passoAbstrato() + sobrescreve hookMethod()
```

### Participantes (GoF)
- **AbstractClass:** define o template e os passos primitivos
- **ConcreteClass:** implementa os passos variáveis

---

## SLIDE 6 — Aplicação no Sistema de Relatórios (Versão 2)

### `RelatorioGerador` — Classe Abstrata

```java
public abstract class RelatorioGerador {

    public final void gerarRelatorio() {  // ← TEMPLATE
        coletarDados();        // fixo
        processarDados();      // fixo
        formatarCabecalho();   // abstract
        formatarCorpo();       // abstract
        incluirMarcaDagua();   // hook (padrão: vazio)
        formatarRodape();      // abstract
        exportar();            // fixo
    }

    protected abstract void formatarCabecalho();
    protected abstract void formatarCorpo();
    protected abstract void formatarRodape();

    protected void incluirMarcaDagua() { /* hook vazio */ }
}
```

### Subclasses implementam apenas o que muda

```java
public class RelatorioPDF extends RelatorioGerador {
    @Override
    protected void formatarCabecalho() { /* PDF específico */ }
    @Override
    protected void formatarCorpo()     { /* PDF específico */ }
    @Override
    protected void formatarRodape()    { /* PDF específico */ }
    @Override
    protected void incluirMarcaDagua() { /* CONFIDENCIAL */ }
}
```

---

## SLIDE 7 — Comparativo: Antes vs Depois

| | Versão 1 (Sem GoF) | Versão 2 (Template Method) |
|--|----|----|
| Fluxo definido em | 3 classes separadas | 1 classe abstrata |
| Código duplicado | Sim (passos 1, 2, 6) | Não |
| Garantia de ordem | Não | Sim (método `final`) |
| Adicionar passo | Editar 3 classes | Editar 1 classe |
| Adicionar formato | OK | Criar 1 subclasse |
| Princípio OCP | Violado | Respeitado |

**Resultado:** redução de ~40% de código, eliminação de duplicações, manutenção centralizada.

---

## SLIDE 8 — Hook Methods

### O que são?

Métodos na classe abstrata com **implementação padrão** (vazia ou comportamento básico).  
Subclasses podem sobrescrever **se quiserem** — é opcional.

### Exemplo no projeto

```java
// Na superclasse — hook vazio
protected void incluirMarcaDagua() { }

// RelatorioPDF — decide usar o hook
@Override
protected void incluirMarcaDagua() {
    System.out.println("[PDF] Marca d'água: CONFIDENCIAL");
}

// RelatorioHTML e RelatorioCSV — não sobrescrevem → sem marca d'água
```

### Por que hooks são poderosos?

Permitem **pontos de extensão** sem obrigar implementação — subclasses definem SE querem participar de um passo do algoritmo.

---

## SLIDE 9 — Reflexão e Anotações (Versão 3)

### Motivação

Na Versão 2, o template ainda conhece os nomes dos métodos:
```java
formatarCabecalho(); // acoplado ao nome do método
formatarCorpo();     // acoplado ao nome do método
```

### Solução com anotações

Criamos **2 anotações próprias**:

#### `@PassoRelatorio` — marca e ordena passos
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PassoRelatorio {
    int ordem();
    String descricao() default "Passo do relatório";
    boolean obrigatorio() default true;
}
```

#### `@RelatorioConfig` — metadados da classe
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RelatorioConfig {
    String nome();
    String extensao();
    String versao() default "1.0";
    String descricao() default "";
}
```

---

## SLIDE 10 — Como a Reflexão Funciona (Versão 3)

### `ExecutorRelatorio` — motor baseado em reflexão

```java
public void executar(RelatorioGerador gerador) {
    Class<?> classe = gerador.getClass();

    // 1. Lê metadados via @RelatorioConfig (reflexão)
    RelatorioConfig config = classe.getAnnotation(RelatorioConfig.class);

    // 2. Descobre métodos com @PassoRelatorio (reflexão)
    List<Method> passos = descobrirPassos(classe);

    // 3. Ordena por 'ordem' da anotação
    passos.sort(Comparator.comparingInt(
        m -> m.getAnnotation(PassoRelatorio.class).ordem()
    ));

    // 4. Invoca dinamicamente (reflexão)
    for (Method metodo : passos) {
        metodo.invoke(gerador); // ← sem conhecer o nome do método!
    }
}
```

### O executor NÃO conhece PDF, HTML ou CSV

---

## SLIDE 11 — Redução de Acoplamento

### Comparativo de acoplamento

```
Versão 2:                         Versão 3:
RelatorioGerador                  ExecutorRelatorio
  ↓ chama por nome                  ↓ descobre via reflexão
  formatarCabecalho()               @PassoRelatorio(ordem=3)
  formatarCorpo()                   qualquer método anotado
  formatarRodape()                  em qualquer classe
```

### Impacto prático

Para adicionar `RelatorioXML` na **Versão 2**:
- Criar subclasse → ✅ (simples)

Para adicionar `RelatorioXML` na **Versão 3**:
- Criar subclasse com `@RelatorioConfig` e `@PassoRelatorio` → ✅
- **Sem alterar `ExecutorRelatorio`** → acoplamento zero

### Vantagem adicional das anotações

Os metadados ficam **declarados junto ao código** que os descreve:
```java
@RelatorioConfig(nome="PDF", extensao="pdf", versao="2.1")
public class RelatorioPDF extends RelatorioGerador { ... }
```

---

## SLIDE 12 — Demonstração Ao Vivo

### Roteiro

1. Executar **Versão 1** → mostrar a duplicação nos 3 métodos `gerar()`
2. Executar **Versão 2** → mostrar o template único e as subclasses enxutas
3. Executar **Versão 3** → mostrar o executor descobrindo os passos via reflexão

### Comandos

```bash
# Versão 1
cd versao1_sem_gof/src && javac *.java && java Main

# Versão 2
cd versao2_com_template/src && javac *.java && java Main

# Versão 3
cd versao3_com_anotacoes/src
javac -d . anotacoes/*.java *.java
java Main
```

Ou simplesmente: **`executar_tudo.bat`** (Windows)

---

## SLIDE 13 — Conclusão

### Template Method Resolve

✅ **Duplicação** — fluxo definido uma única vez  
✅ **Inconsistência** — `final` garante a ordem  
✅ **Extensibilidade** — adicionar formato = criar subclasse  
✅ **OCP** — aberto para extensão, fechado para modificação  

### Reflexão e Anotações Agregam

✅ **Desacoplamento** — executor não conhece as classes concretas  
✅ **Declaratividade** — metadados junto ao código que os descreve  
✅ **Extensibilidade máxima** — novos formatos sem tocar no executor  

### Quando usar Template Method?

- Quando múltiplas classes implementam um **algoritmo com a mesma estrutura**
- Quando você quer garantir que **passos fixos não sejam alterados**
- Quando quer oferecer **pontos de extensão** (hooks) bem definidos

---

## SLIDE 14 — Referências

- GAMMA, E. et al. **Design Patterns: Elements of Reusable Object-Oriented Software**. Addison-Wesley, 1994.
- FREEMAN, E.; ROBSON, E. **Head First Design Patterns**. O'Reilly, 2021.
- BLOCH, J. **Effective Java**. 3ª ed. Addison-Wesley, 2018.
- Oracle. **The Reflection API** — docs.oracle.com/javase/tutorial/reflect

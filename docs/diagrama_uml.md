# Diagrama UML — Template Method

## Versão 2: Diagrama de Classes — Template Method Clássico

```mermaid
classDiagram
    class RelatorioGerador {
        <<abstract>>
        #String titulo
        #String[] dados
        +RelatorioGerador(titulo, dados)
        +gerarRelatorio() void <<final>>
        -coletarDados() void
        -processarDados() void
        -exportar() void
        #formatarCabecalho() void <<abstract>>
        #formatarCorpo() void <<abstract>>
        #formatarRodape() void <<abstract>>
        #incluirMarcaDagua() void
        #getFormato() String <<abstract>>
    }

    class RelatorioPDF {
        +RelatorioPDF(titulo, dados)
        #formatarCabecalho() void
        #formatarCorpo() void
        #formatarRodape() void
        #incluirMarcaDagua() void
        #getFormato() String
    }

    class RelatorioHTML {
        +RelatorioHTML(titulo, dados)
        #formatarCabecalho() void
        #formatarCorpo() void
        #formatarRodape() void
        #getFormato() String
    }

    class RelatorioCSV {
        +RelatorioCSV(titulo, dados)
        #formatarCabecalho() void
        #formatarCorpo() void
        #formatarRodape() void
        #getFormato() String
    }

    RelatorioGerador <|-- RelatorioPDF : extends
    RelatorioGerador <|-- RelatorioHTML : extends
    RelatorioGerador <|-- RelatorioCSV : extends
```

> **Nota:** `gerarRelatorio()` é marcado como `final` — nenhuma subclasse pode sobrescrever o esqueleto do algoritmo. Os métodos `formatarCabecalho()`, `formatarCorpo()` e `formatarRodape()` são `abstract` — obrigatórios nas subclasses. `incluirMarcaDagua()` é um **hook method** — tem implementação vazia padrão, mas pode ser sobrescrito (como faz RelatorioPDF).

---

## Versão 3: Diagrama de Classes — Com Reflexão e Anotações

```mermaid
classDiagram
    class PassoRelatorio {
        <<annotation>>
        +int ordem
        +String descricao
        +boolean obrigatorio
    }

    class RelatorioConfig {
        <<annotation>>
        +String nome
        +String extensao
        +String versao
        +String descricao
    }

    class RelatorioGerador {
        <<abstract>>
        #String titulo
        #String[] dados
        +coletarDados() void
        +processarDados() void
        +exportar() void
        +formatarCabecalho() void <<abstract>>
        +formatarCorpo() void <<abstract>>
        +formatarRodape() void <<abstract>>
        +incluirMarcaDagua() void
        +getFormato() String <<abstract>>
    }

    class RelatorioPDF {
        <<RelatorioConfig>>
        +formatarCabecalho() void
        +formatarCorpo() void
        +formatarRodape() void
        +incluirMarcaDagua() void
        +getFormato() String
    }

    class RelatorioHTML {
        <<RelatorioConfig>>
        +formatarCabecalho() void
        +formatarCorpo() void
        +formatarRodape() void
        +getFormato() String
    }

    class RelatorioCSV {
        <<RelatorioConfig>>
        +formatarCabecalho() void
        +formatarCorpo() void
        +formatarRodape() void
        +getFormato() String
    }

    class ExecutorRelatorio {
        +executar(gerador) void
        -descobrirPassos(classe) List~Method~
        -exibirMetadados(classe) void
    }

    PassoRelatorio ..> RelatorioGerador : anota métodos
    PassoRelatorio ..> RelatorioPDF : anota métodos
    PassoRelatorio ..> RelatorioHTML : anota métodos
    PassoRelatorio ..> RelatorioCSV : anota métodos
    RelatorioConfig ..> RelatorioPDF : anota classe
    RelatorioConfig ..> RelatorioHTML : anota classe
    RelatorioConfig ..> RelatorioCSV : anota classe
    RelatorioGerador <|-- RelatorioPDF : extends
    RelatorioGerador <|-- RelatorioHTML : extends
    RelatorioGerador <|-- RelatorioCSV : extends
    ExecutorRelatorio ..> RelatorioGerador : usa reflexão
    ExecutorRelatorio ..> PassoRelatorio : lê em runtime
    ExecutorRelatorio ..> RelatorioConfig : lê em runtime
```

---

## Sequência de Execução — Versão 3 (Reflexão)

```mermaid
sequenceDiagram
    participant Main
    participant Executor as ExecutorRelatorio
    participant Reflect as Java Reflection API
    participant PDF as RelatorioPDF

    Main->>Executor: executar(realtorioPDF)
    Executor->>Reflect: classe.getAnnotation(RelatorioConfig)
    Reflect-->>Executor: config {nome, extensao, versao}
    Executor->>Executor: exibirMetadados()

    Executor->>Reflect: descobrirPassos(classe)
    Reflect->>Reflect: percorre classe + superclasses
    Reflect->>Reflect: filtra métodos com @PassoRelatorio
    Reflect-->>Executor: [coletarDados, processarDados, formatarCabecalho, ...]

    Executor->>Executor: ordena por @PassoRelatorio.ordem

    loop Para cada método anotado
        Executor->>Reflect: metodo.invoke(realtorioPDF)
        Reflect->>PDF: executa método concreto
        PDF-->>Executor: saída impressa
    end
```

# Documento de Entrega — Trabalho AVII

**Disciplina:** Programação Orientada a Objetos Avançada  
**Instituição:** Universidade Católica do Salvador — UCSal  
**Data de Entrega:** 04/06/2026  
**Nota:** 4,0

---

## 1. Integrantes

| Nome | Matrícula |
|------|-----------|
| Caio Roldão | — |
| João Mário | — |
| Juvenal Junior | — |

---

## 2. Padrão GoF Escolhido

**Template Method** (Padrão Comportamental)

### Definição Formal (GoF)
> "Defina o esqueleto de um algoritmo em uma operação, postergando a definição de alguns passos para subclasses. O Template Method permite que subclasses redefinam certos passos de um algoritmo sem alterar a estrutura do algoritmo."

— *Design Patterns: Elements of Reusable Object-Oriented Software* (Gang of Four, 1994)

### Classificação
- **Família:** Comportamental
- **Escopo:** Classe (usa herança)
- **Intenção:** Invariância do algoritmo com variância dos passos

---

## 3. Problema Proposto

### Contexto
Uma empresa precisa de um sistema que gere **relatórios financeiros** em três formatos: **PDF**, **HTML** e **CSV**. Cada formato é usado em um contexto diferente:
- **PDF:** Impressão e arquivamento formal
- **HTML:** Visualização em portais web internos
- **CSV:** Importação em planilhas e ferramentas de BI

### Requisitos do Sistema
- Cada relatório deve seguir o mesmo fluxo: coleta de dados → processamento → formatação → exportação
- O formato de saída (cabeçalho, corpo, rodapé) varia por tipo
- Novos formatos poderão ser adicionados no futuro

---

## 4. Por Que a Solução Inicial é Inadequada

Na **Versão 1** (sem padrão), cada classe (`RelatorioPDF`, `RelatorioHTML`, `RelatorioCSV`) implementa seu próprio método `gerar()` do zero. O resultado é:

### Problemas Identificados

| Problema | Impacto |
|----------|---------|
| **Duplicação de código** (passos 1, 2 e 6 idênticos nas 3 classes) | Violação do princípio DRY |
| **Acoplamento alto** (fluxo espalhado em todas as classes) | Difícil de manter |
| **Violação do OCP** (adicionar passo de auditoria exige editar 3 arquivos) | Risco de inconsistência |
| **Sem garantia de ordem** (cada classe pode omitir um passo) | Comportamento imprevisível |

### Exemplo do Problema
```java
// RelatorioPDF.java — passo de coleta duplicado
System.out.println("[PDF] Coletando dados do banco de dados...");

// RelatorioHTML.java — mesma lógica duplicada
System.out.println("[HTML] Coletando dados do banco de dados...");

// RelatorioCSV.java — duplicado pela terceira vez!
System.out.println("[CSV] Coletando dados do banco de dados...");
```
Se o banco de dados mudar de tecnologia, precisamos alterar 3 arquivos. Se esquecer um, o sistema fica inconsistente.

---

## 5. Aplicação do Padrão Template Method

### 5.1 Estrutura do Padrão

O Template Method define:
1. **Classe abstrata** (`RelatorioGerador`): contém o método template `gerarRelatorio()` e os passos invariantes
2. **Método template** (`final`): define a sequência de passos — não pode ser sobrescrito
3. **Métodos abstratos**: passos que **devem** ser implementados pelas subclasses
4. **Hook methods**: passos **opcionais** com implementação padrão vazia
5. **Subclasses concretas**: implementam apenas os passos que variam

### 5.2 O Método Template

```java
// RelatorioGerador.java — Versão 2
public final void gerarRelatorio() {
    coletarDados();       // fixo — implementado aqui
    processarDados();     // fixo — implementado aqui
    formatarCabecalho();  // variável — subclasse implementa
    formatarCorpo();      // variável — subclasse implementa
    incluirMarcaDagua();  // hook — opcional (padrão: vazio)
    formatarRodape();     // variável — subclasse implementa
    exportar();           // fixo — implementado aqui
}
```

### 5.3 Como o Padrão Resolve o Problema

| Problema Anterior | Solução com Template Method |
|-------------------|-----------------------------|
| Código duplicado nos passos 1, 2 e 6 | Passos fixos implementados **uma única vez** na superclasse |
| Sem garantia de ordem | `gerarRelatorio()` é `final` — ordem é imutável |
| Adicionar passo exige editar 3 classes | Adiciona-se o passo **uma vez** em `RelatorioGerador` |
| Subclasse pode omitir passo | Métodos `abstract` forçam implementação |

### 5.4 Hook Method — `incluirMarcaDagua()`

O hook method demonstra a flexibilidade do padrão:
- `RelatorioGerador`: implementação vazia (não faz nada por padrão)
- `RelatorioPDF`: sobrescreve e adiciona a marca "CONFIDENCIAL"
- `RelatorioHTML`, `RelatorioCSV`: herdam o comportamento padrão (sem marca)

```java
// Hook na superclasse — comportamento padrão
protected void incluirMarcaDagua() { /* vazio */ }

// RelatorioPDF sobrescreve o hook
@Override
protected void incluirMarcaDagua() {
    System.out.println("[PDF] Adicionando marca d'água: CONFIDENCIAL");
}
```

---

## 6. Diagrama de Classes UML

Veja o arquivo: `docs/diagrama_uml.md`

### Resumo Visual

```
RelatorioGerador (abstract)
├── gerarRelatorio() [final — TEMPLATE]
├── coletarDados()   [implementado — FIXO]
├── processarDados() [implementado — FIXO]
├── exportar()       [implementado — FIXO]
├── formatarCabecalho() [abstract — VARIÁVEL]
├── formatarCorpo()     [abstract — VARIÁVEL]
├── formatarRodape()    [abstract — VARIÁVEL]
└── incluirMarcaDagua() [hook — OPCIONAL]
    │
    ├── RelatorioPDF   (implementa os 3 abstratos + sobrescreve hook)
    ├── RelatorioHTML  (implementa os 3 abstratos)
    └── RelatorioCSV   (implementa os 3 abstratos)
```

---

## 7. Uso de Reflexão e Anotações (Versão 3)

### 7.1 Anotações Criadas

#### `@PassoRelatorio`
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PassoRelatorio {
    int ordem();
    String descricao() default "Passo do relatório";
    boolean obrigatorio() default true;
}
```
**Propósito:** Marcar e ordenar métodos que são passos do algoritmo de geração.

#### `@RelatorioConfig`
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
**Propósito:** Declarar metadados do formato de relatório diretamente na classe.

### 7.2 Como a Reflexão é Usada

O `ExecutorRelatorio` realiza os seguintes passos em runtime:

```java
// 1. Lê metadados da classe via @RelatorioConfig
RelatorioConfig config = classe.getAnnotation(RelatorioConfig.class);

// 2. Descobre métodos anotados com @PassoRelatorio percorrendo a hierarquia
for (Method metodo : atual.getDeclaredMethods()) {
    if (metodo.isAnnotationPresent(PassoRelatorio.class)) {
        passos.add(metodo);
    }
}

// 3. Ordena pelos campos 'ordem'
passos.sort(Comparator.comparingInt(m -> m.getAnnotation(PassoRelatorio.class).ordem()));

// 4. Invoca dinamicamente
metodo.setAccessible(true);
metodo.invoke(gerador);
```

### 7.3 Como Isso Reduz o Acoplamento

Na **Versão 2**, o método template chama os passos diretamente por nome:
```java
// Acoplado aos nomes dos métodos
formatarCabecalho();
formatarCorpo();
formatarRodape();
```

Na **Versão 3**, o `ExecutorRelatorio` **desconhece** os nomes concretos:
```java
// Desacoplado — descobre métodos via anotação
metodo.invoke(gerador); // "metodo" descoberto via reflexão
```

| Cenário | Versão 2 | Versão 3 |
|---------|----------|----------|
| Adicionar `RelatorioXML` | Cria subclasse ✅ | Cria subclasse com `@RelatorioConfig` ✅ |
| Adicionar novo passo | Edita `RelatorioGerador` | Anota com `@PassoRelatorio(ordem=N)` |
| Executor conhece subclasses? | Não (polimorfismo) | **Não** (reflexão + anotações) |
| Metadados do formato | Espalhados em métodos | Declarados em `@RelatorioConfig` |

**Conclusão:** as anotações atuam como **contratos declarativos** — o executor descobre o que precisa fazer lendo os metadados em tempo de execução, sem acoplamento estático às implementações concretas.

---

## 8. Instruções para Executar o Projeto

### Pré-requisito
Ter o **JDK 11+** instalado. Verificar com: `java -version`

### Opção 1 — Script Automático (Windows)
```
Abrir o Explorador de Arquivos em:
  TRABALHO POOA\

Executar:
  executar_tudo.bat
```

### Opção 2 — Manual

#### Versão 1 (Sem GoF)
```bash
cd versao1_sem_gof/src
javac *.java
java Main
```

#### Versão 2 (Template Method)
```bash
cd versao2_com_template/src
javac *.java
java Main
```

#### Versão 3 (Reflexão e Anotações)
```bash
cd versao3_com_anotacoes/src
# Compilar as anotações primeiro
javac -d . anotacoes/PassoRelatorio.java anotacoes/RelatorioConfig.java
# Compilar o restante
javac -d . RelatorioGerador.java RelatorioPDF.java RelatorioHTML.java RelatorioCSV.java ExecutorRelatorio.java Main.java
java Main
```

### Estrutura de Pastas do Projeto
```
TRABALHO POOA/
├── executar_tudo.bat
├── versao1_sem_gof/
│   └── src/
│       ├── RelatorioPDF.java
│       ├── RelatorioHTML.java
│       ├── RelatorioCSV.java
│       └── Main.java
├── versao2_com_template/
│   └── src/
│       ├── RelatorioGerador.java   ← Classe abstrata com o Template Method
│       ├── RelatorioPDF.java
│       ├── RelatorioHTML.java
│       ├── RelatorioCSV.java
│       └── Main.java
├── versao3_com_anotacoes/
│   └── src/
│       ├── anotacoes/
│       │   ├── PassoRelatorio.java ← Anotação de passo
│       │   └── RelatorioConfig.java← Anotação de metadados
│       ├── RelatorioGerador.java
│       ├── RelatorioPDF.java
│       ├── RelatorioHTML.java
│       ├── RelatorioCSV.java
│       ├── ExecutorRelatorio.java  ← Motor que usa reflexão
│       └── Main.java
└── docs/
    ├── diagrama_uml.md
    └── documentacao.md
```

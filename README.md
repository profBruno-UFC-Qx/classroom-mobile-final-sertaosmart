[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=20977991)

# Sertão Smart

## Equipe
* **Nome do Aluno(a) 1:** Breno Gonzaga de Carvalho 542155
* **Nome do Aluno(a) 2:** [Seu nome e matrícula]
* **Nome do Aluno(a) 3:** [Seu nome e matrícula]
* ...

---

## Título do Projeto
Sertao Smart 

## Descrição do Projeto
O "Sertão Smart" é um aplicativo Android desenvolvido para auxiliar agricultores e entusiastas da jardinagem no semiárido a otimizar o uso da água na irrigação. O aplicativo resolve o problema do desperdício de água e da irrigação ineficiente, que podem prejudicar as plantações e esgotar recursos hídricos valiosos. O projeto está sendo desenvolvido em parceria com outra disciplina.

### Público-Alvo
O público-alvo são pequenos e médios produtores rurais, estudantes de agronomia e qualquer pessoa que pratique agricultura em regiões com escassez de água.

### Escopo da Solução
Utilizando dados agrometeorológicos de uma API externa (INMET), o app calcula o balanço hídrico diário (diferença entre evapotranspiração e precipitação) e fornece uma recomendação simples e direta sobre a necessidade de irrigação.

## Requisitos Funcionais

### Requisitos Prioritários (Foco Atual: CRUD Local)
- [ ] **RF001 - CRUD de Culturas:** Cadastro, visualização, edição e exclusão de culturas (ex: Milho, Feijão)
- [ ] **RF002 - Histórico de Irrigação:** Salvar histórico local das recomendações e irrigações realizadas
- [ ] **RF003 - Seleção de Localidade:** Escolha da cidade ou estação meteorológica mais próxima

### Requisitos Futuros (Backlog - API e Funcionalidades Avançadas)
- [ ] **RF004 - Busca de Dados Meteorológicos:** Integração com API do INMET
- [ ] **RF005 - Cálculo de Balanço Hídrico:** Cálculo baseado em precipitação e evapotranspiração
- [ ] **RF006 - Exibição de Recomendação:** Apresentação clara da necessidade de irrigação
- [ ] **RF007 - Interface Reativa:** Estados visuais de carregamento, sucesso e erro

## Requisitos Não-Funcionais

- **RNF001 - Plataforma:** Android nativo (mínimo API 26 - Oreo) com Kotlin
- **RNF002 - Arquitetura:** MVVM (Model-View-ViewModel)
- **RNF003 - Interface:** Jetpack Compose com Material Design 3
- **RNF004 - Persistência:** Banco de dados local com Room
- **RNF005 - Conectividade:** Tratamento de estados offline/online

## Tecnologias Utilizadas
- **Kotlin:** Linguagem de programação principal
- **Jetpack Compose:** UI toolkit moderno
- **Room:** Persistência de dados local
- **Retrofit:** Cliente HTTP para API do INMET
- **Jetpack Navigation:** Navegação entre telas
- **Material Design 3:** Design system
- **Coroutines & Flow:** Operações assíncronas
- **MVVM:** Padrão de arquitetura

## Instruções para Execução
Clone o repositório e abra o projeto no Android Studio. Certifique-se de ter o SDK Android 26 ou superior instalado.

```bash
git clone [URL_DO_REPOSITÓRIO]
cd SertoSmart
```

> [!NOTE]
> O projeto está em desenvolvimento ativo e algumas funcionalidades podem não estar disponíveis.
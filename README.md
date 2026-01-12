# Sertão Smart

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=20977991)

## 👥 Equipe

* **Nome do Aluno 1:** Breno Gonzaga de Carvalho - 542155
* **Nome do Aluno 2:** [Seu nome e matrícula]

---

## 📱 Sobre o Projeto

O **Sertão Smart** é um aplicativo Android desenvolvido para auxiliar agricultores e entusiastas da jardinagem no semiárido a otimizar o uso da água na irrigação. O aplicativo resolve o problema do desperdício de água e da irrigação ineficiente, que podem prejudicar as plantações e esgotar recursos hídricos valiosos.

### 🎯 Público-Alvo

Pequenos e médios produtores rurais, estudantes de agronomia e qualquer pessoa que pratique agricultura em regiões com escassez de água.

### 💡 Escopo da Solução

Utilizando dados agrometeorológicos da API do INMET (Instituto Nacional de Meteorologia), o app calcula o balanço hídrico diário (diferença entre evapotranspiração e precipitação) e fornece recomendações simples e diretas sobre a necessidade de irrigação.

---

## ✨ Funcionalidades Implementadas

### 🌾 Gestão de Culturas (CRUD Completo)
- ✅ Cadastro de culturas
- ✅ Listagem de culturas com interface moderna
- ✅ Edição de culturas existentes
- ✅ Exclusão de culturas
- ✅ Estado vazio com mensagem amigável

### 💧 Recomendações de Irrigação
- ✅ Cálculo automático baseado em dados meteorológicos
- ✅ Interface visual intuitiva com ícones
- ✅ Cards informativos com métricas
- ✅ Modo offline com dados mock para desenvolvimento
- ✅ Atualização manual de dados

### 📊 Histórico de Consultas
- ✅ Salvamento automático de recomendações
- ✅ Visualização detalhada do histórico
- ✅ Cards com informações de precipitação e evapotranspiração
- ✅ Badges de status (Irrigar/Não Irrigar)
- ✅ Chipsets com métricas visuais

### ⚙️ Configurações
- ✅ Seleção de estação meteorológica
- ✅ Lista de estações do Ceará
- ✅ Informações do aplicativo
- ✅ Modo offline com fallback

### 🎨 Interface Moderna
- ✅ Material Design 3
- ✅ Tema claro e escuro personalizados
- ✅ Bottom Navigation Bar
- ✅ Componentes reutilizáveis
- ✅ Animações e transições suaves
- ✅ Estados de loading e erro

---

## 🏗️ Arquitetura e Tecnologias

### Arquitetura
- **MVVM** (Model-View-ViewModel)
- Separação clara de responsabilidades
- Repository Pattern para acesso a dados

### Tecnologias Principais
- **Kotlin** - Linguagem de programação
- **Jetpack Compose** - UI toolkit moderno e declarativo
- **Material Design 3** - Sistema de design
- **Room** - Persistência de dados local
- **Retrofit** - Cliente HTTP para APIs
- **OkHttp** - Cliente HTTP com timeouts configurados
- **Jetpack Navigation** - Navegação entre telas
- **Kotlin Coroutines & Flow** - Programação assíncrona
- **DataStore** - Armazenamento de preferências

### Componentes Jetpack
- ViewModel
- LiveData/StateFlow
- Navigation Component
- DataStore Preferences

---

## 📋 Requisitos Atendidos

### Requisitos Funcionais
- [x] **RF001** - CRUD de Culturas completo
- [x] **RF002** - Histórico de Irrigação com persistência local
- [x] **RF003** - Seleção de Localidade/Estação Meteorológica
- [x] **RF004** - Integração com API do INMET
- [x] **RF005** - Cálculo de Balanço Hídrico
- [x] **RF006** - Exibição clara de Recomendações
- [x] **RF007** - Interface Reativa com estados visuais

### Requisitos Não-Funcionais
- [x] **RNF001** - Android nativo (API 26+) com Kotlin
- [x] **RNF002** - Arquitetura MVVM
- [x] **RNF003** - Jetpack Compose com Material Design 3
- [x] **RNF004** - Banco de dados Room
- [x] **RNF005** - Tratamento de estados offline/online

### Requisitos do Projeto Final
- [x] Múltiplas telas com navegação
- [x] CRUD completo
- [x] Persistência local (Room + DataStore)
- [x] MaterialTheme com modo claro/escuro
- [x] Integração com API externa (INMET)

---

## 🎨 Design System

### Paleta de Cores

#### Modo Claro
- **Background:** `#FFFFFF`
- **Primary:** `#030213`
- **Secondary:** `#F2F2F6`
- **Accent:** `#E9EBEF`
- **Destructive:** `#D4183D`

#### Modo Escuro
- **Background:** `#252525`
- **Primary:** `#FBFBFB`
- **Secondary:** `#444444`
- **Muted:** `#B5B5B5`
- **Destructive:** `#9F3A3A`

### Componentes Personalizados
- `SmartCard` - Cards com estilo consistente
- `SmartPrimaryButton` - Botões primários
- `SmartSecondaryButton` - Botões secundários
- `EmptyState` - Estado vazio de listas
- `SmartLoadingIndicator` - Indicador de carregamento
- `ErrorMessage` - Mensagens de erro
- `SectionHeader` - Cabeçalhos de seção
- `InfoCard` - Cards informativos com ícones

---

## 📂 Estrutura do Projeto

```
app/src/main/java/com/sertaosmart/
├── data/
│   ├── model/              # Modelos de dados
│   ├── DAO/                # Data Access Objects
│   ├── network/            # Configuração de rede
│   ├── remote/             # Serviços de API
│   └── repository/         # Repositórios
├── ui/
│   ├── components/         # Componentes reutilizáveis
│   ├── cultura/            # Telas de culturas
│   ├── history/            # Tela de histórico
│   ├── recommendation/     # Tela de recomendações
│   ├── settings/           # Tela de configurações
│   └── theme/              # Tema e cores
└── MainActivity.kt         # Activity principal
```

---

## 🚀 Instruções para Execução

### Pré-requisitos
- Android Studio (Arctic Fox ou superior)
- JDK 8 ou superior
- SDK Android 26 (Oreo) ou superior

### Passos

1. **Clone o repositório:**
```bash
git clone [URL_DO_REPOSITÓRIO]
cd SertoSmart
```

2. **Abra o projeto no Android Studio:**
   - File → Open → Selecione a pasta do projeto

3. **Aguarde a sincronização do Gradle:**
   - O Android Studio irá baixar todas as dependências automaticamente

4. **Execute o aplicativo:**
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em Run (▶️) ou pressione Shift+F10

---

## 🌐 API Externa

### INMET - Instituto Nacional de Meteorologia

**Base URL:** `https://apitempo.inmet.gov.br/`

**Endpoints utilizados:**
- `GET /estacoes/A` - Lista todas as estações automáticas
- `GET /estacao/diaria/{dataInicial}/{dataFinal}/{codigoEstacao}` - Dados diários de uma estação

**Dados retornados:**
- Precipitação (CHUVA) em mm
- Evapotranspiração (EVAPORACAO_PICH) em mm

### Modo Offline
O aplicativo possui dados mock (estações e dados meteorológicos) que são utilizados automaticamente quando a API não está disponível, permitindo desenvolvimento e testes sem conexão.

---

## 📱 Capturas de Tela

### Tela de Recomendações
Interface principal com recomendação de irrigação, botões de ação e cards informativos.

### Tela de Culturas
Lista de culturas cadastradas com opções de editar e excluir.

### Tela de Histórico
Histórico detalhado de todas as consultas realizadas com métricas.

### Tela de Configurações
Seleção de estação meteorológica e informações do app.

---

## 🔄 Fluxo de Dados

1. **Usuário abre o app**
2. **Sistema busca estação selecionada** (DataStore)
3. **API INMET é consultada** para dados meteorológicos
4. **Cálculo do balanço hídrico** (evapotranspiração - precipitação)
5. **Recomendação é gerada** e exibida
6. **Dados são salvos** no histórico (Room)

---

## 🛠️ Desenvolvimento

### Padrões de Código
- Código 100% em Kotlin
- Sem comentários (código autoexplicativo)
- Nomes descritivos para funções e variáveis
- Componentes Composable reutilizáveis
- Estados hoisted quando necessário

### Boas Práticas Implementadas
- Single Responsibility Principle
- Repository Pattern
- Injeção de dependências manual via Factory
- Separação de concerns (UI, Data, Domain)
- Tratamento de erros e estados
- UI states bem definidos

---

## 📝 Estado do Projeto

**Status:** ✅ **Completo e Funcional**

### Concluído
- ✅ CRUD de culturas
- ✅ Integração com API INMET
- ✅ Cálculo de balanço hídrico
- ✅ Histórico de consultas
- ✅ Configurações de estação
- ✅ Tema claro/escuro
- ✅ Navigation bar
- ✅ Estados de loading/erro
- ✅ Persistência local
- ✅ Modo offline

### Possíveis Melhorias Futuras
- 🔄 Notificações push para alertas de irrigação
- 🔄 Gráficos de histórico
- 🔄 Previsão do tempo
- 🔄 Sincronização em nuvem
- 🔄 Compartilhamento de recomendações
- 🔄 Suporte a múltiplos idiomas
- 🔄 Integração com sensores de solo

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos como parte do projeto final da disciplina.

---

## 📞 Contato

Para dúvidas ou sugestões sobre o projeto, entre em contato com a equipe através do GitHub.

---

**Desenvolvido com ❤️ usando Kotlin e Jetpack Compose**
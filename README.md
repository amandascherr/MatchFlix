# MatchFlix

Projeto final de **MC322 – Programação Orientada a Objetos** (Unicamp).

MatchFlix é um aplicativo desktop (Java + Swing) para descobrir filmes em grupo.
Cada usuário curte ou descarta filmes populares (obtidos da API do
[TMDB](https://www.themoviedb.org/)), e quando **todos os membros de um grupo
curtem o mesmo filme** acontece um *match*, notificado a todos — a ideia é
resolver o clássico "o que a gente vai assistir?".

---

## Funcionalidades

- **Cadastro e login** de usuários, com perfil e foto.
- **Avaliação de filmes** no estilo curtir/descartar, com filmes vindos do TMDB.
- **Grupos**: criar grupos e convidar outros usuários.
- **Match**: um filme vira match quando **todos** os membros do grupo o curtem
  (e o match acontece apenas uma vez por filme).
- **Notificações** de matches e de convites recebidos.
- **Persistência** local em arquivos JSON (nenhum banco de dados externo).

---

## Tecnologias

- **Java 17+** (usa *records*, *sealed interfaces* e *pattern matching*).
- **Swing** para a interface gráfica.
- **Gradle** (wrapper incluído) como ferramenta de build.
- **Jackson** para serialização JSON e **Spring Web (RestTemplate)** para as
  chamadas HTTP ao TMDB.
- **JUnit 5** + **JaCoCo** para testes e cobertura.

---

## Pré-requisitos

1. **JDK 17 ou superior** instalado.
2. Uma **chave de API do TMDB** (gratuita): crie uma conta em
   <https://www.themoviedb.org/settings/api> e copie sua *API Key*.

> Não é preciso instalar o Gradle: o projeto já inclui o *wrapper*
> (`./gradlew`), que baixa a versão correta automaticamente.

---

## Configuração

A chave da API é lida de um arquivo `.env` dentro do módulo `app`
(esse arquivo é ignorado pelo Git e não deve ser versionado).

Crie o arquivo `app/.env` com o seguinte conteúdo:

```env
TMDB_API_KEY=sua_chave_do_tmdb_aqui
```

---

## Como executar

A partir da raiz do projeto:

```bash
# Linux / macOS
./gradlew run

# Windows
gradlew.bat run
```

A janela de login será aberta. Na primeira vez, use **"Cadastrar"** para criar
um usuário.

---

## Como rodar os testes

```bash
./gradlew test
```

O relatório de cobertura (JaCoCo) é gerado em
`app/build/reports/jacoco/test/html/index.html` e o de resultados dos testes em
`app/build/reports/tests/test/index.html`.

---

## Fluxo básico de uso

1. **Cadastre-se** e faça **login**.
2. Na tela inicial, **curta (👍) ou descarte (👎)** os filmes apresentados.
3. **Crie um grupo** e **convide** outros usuários (pelo nome).
4. Quando todos os membros de um grupo curtirem o mesmo filme, um **match** é
   registrado e aparece nas notificações do grupo.

---

## Estrutura do projeto

O código segue o padrão **MVC**, com o padrão **Observer** ligando as curtidas
dos usuários aos grupos.

```
MatchFlix/
├── app/
│   ├── .env                         # chave da API TMDB (não versionado)
│   ├── build.gradle.kts             # dependências e tasks do módulo
│   └── src/
│       ├── main/java/
│       │   ├── Application.java      # ponto de entrada (carrega o .env e inicia a UI)
│       │   ├── controller/          # controladores (login, cadastro, grupos, navegação, sessão)
│       │   ├── model/               # entidades de domínio (User, Group, Movie, Match, Invite...)
│       │   │   └── observer/        # padrão Observer (Publisher / Subscriber)
│       │   ├── service/             # regras de aplicação e serviços
│       │   │   └── dataManager/     # persistência em JSON (DataManager / JsonDataManager)
│       │   ├── dto/                 # objetos de transferência (records) para serialização
│       │   ├── exception/           # exceções de domínio (validação, usuário não encontrado...)
│       │   ├── util/                # utilitários (diálogos, carregamento de imagens, tema)
│       │   └── view/                # telas e componentes Swing
│       │       ├── screens/
│       │       └── components/
│       └── test/java/               # testes (JUnit 5) das regras de negócio
├── settings.gradle.kts              # define o módulo "app"
├── gradle.properties
└── gradlew, gradlew.bat             # Gradle wrapper
```

### Como funciona o *match* (visão geral)

- Cada `User` possui um `Publisher` (do padrão Observer); ao curtir um filme,
  ele **notifica** os grupos dos quais participa.
- Cada `Group` é um `Subscriber`: recebe a curtida e contabiliza quantos membros
  curtiram cada filme.
- Quando o número de curtidas de um filme iguala o número de membros do grupo,
  o `Group` cria um `Match` e notifica de volta todos os seus usuários.

---

## Persistência

Os dados são gravados como arquivos JSON em `app/src/main/resources/data/`
(diretório ignorado pelo Git). Cada usuário e cada grupo vira um arquivo,
gerenciado pela implementação `JsonDataManager`.

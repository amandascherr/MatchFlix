# DataManager

Serviço de persistência simples que salva dados como arquivos JSON. Cada
registro é gravado em `src/main/resources/data/<id>.json`.

A implementação padrão é a `JsonDataManager`.

## Conceitos

- **`DataDTO<Body extends Record>`** — objeto de transferência com dois campos:
  - `id` → identificador do registro (vira o **nome do arquivo**).
  - `body` → o conteúdo em si (precisa ser um `Record` para serializar em JSON).
- O `body` é **sempre salvo como uma lista**, mesmo quando há um único item.
- O arquivo guarda também o `type` (nome da classe do `body`) para validar
  concatenações e permitir a leitura.

Exemplo de arquivo `src/main/resources/data/user1.json`:

```json
{
  "type": "model.User",
  "body": [ { "name": "Ana", "age": 30 } ]
}
```

Nos exemplos abaixo, considere o record:

```java
public record User(String name, int age) {}

DataManager db = new JsonDataManager();
```

## `createData(DataDTO<?> data)`

Cria (ou sobrescreve) o arquivo `<id>.json` guardando o `body` como uma lista
de um único item.

```java
db.createData(new DataDTO<>("user1", new User("Ana", 30)));
```

Resultado: cria `data/user1.json` com `"body": [ {"name":"Ana","age":30} ]`.

## `readData(String id, Class<T> type)`

Lê o arquivo e devolve o `body` como `List<T>`. O `type` informado é a classe
dos itens. Funciona tanto para um item só quanto para vários (após `appendData`).

```java
List<User> users = db.readData("user1", User.class);

if (users != null) {                 // pode ser null se o arquivo não existir
    for (User u : users) {
        System.out.println(u.name());
    }
}
```

> Retorna `null` em caso de falha (arquivo inexistente ou erro de leitura),
> então faça o `null`-check antes de iterar.

## `appendData(DataDTO<?> appendData)`

Adiciona um novo `body` à lista já gravada. O registro alvo é identificado pelo
`id` do próprio DTO. Se o arquivo ainda não existir, cria com um único item.

```java
db.appendData(new DataDTO<>("user1", new User("Bia", 25)));
```

Resultado: `"body": [ {"name":"Ana",...}, {"name":"Bia",...} ]`.

> A concatenação só ocorre se o `type` do novo `body` for **igual** ao já
> gravado no arquivo. Se for diferente, a operação é ignorada e um erro é
> registrado no console (`[ERROR] Tipo incompativel...`).

## `deleteData(DataDTO<?> data)`

Remove o arquivo `<id>.json` correspondente. Apenas o `id` do DTO é usado.

```java
db.deleteData(new DataDTO<>("user1", new User("Ana", 30)));
```

## Tratamento de erros

Nenhuma das funções lança exceção: falhas são apenas registradas no console no
formato `[ERROR] <descrição>`, para que a aplicação não pare. Por isso, na
leitura, sempre verifique se o retorno não é `null`.

# Blog-Spring-Api-Kotlin
## _Serviço de API's para Post's de um blog_
Kotlin - Spring - H2Database
## Como consumir as API's
- Criando um Post

Para a criação do post vamos utilizar o método **POST** e o Path `/posts` para enviarmos um arquivo JSON com o seguinte formato:
```sh
{
    "title": "*Campo-Obrigatório*",
    "description": "*Campo-Obrigatório*",
    "body": "*Campo-Obrigatório*",
    "createdAt": "*Campo-Obrigatório*",
    "updateAt": "*Campo-Obrigatório*"
}
```
> Nota: Os campos de Data precisam ser informados no seguinte padrão `yyyy-MM-dd`.


- Listando todos os Posts

Para listar todos os posts cadastrados basta acessar o Path `/posts` com o método **GET**.

> Nota: Todos os métodos de listagem retornaram um JSON semelhante ao que está abaixo;

```sh
{
        "id": 1,
        "title": "Test",
        "description": "Test description",
        "body": "Body test",
        "createdAt": "2021-08-10",
        "updateAt": "2021-08-11"
    }
```

- Listando Posts de acordo com a data de criação

Utilize o método **GET** com o Path `/posts/{date}`: Susbstitua `{date}` por uma data no padrão `yyyy-MM-dd`.

- Listando por intervalo de data de criação

Também é possivel filtrar os posts por um intervalo de datas de criação usando o mesmo método **GET** porém no Path será informada duas datas `/posts/{date}/{date}`

> Nota: As datas precisam estar em uma ordem onde a data mais antiga preenche a primeira lacuna de datas.
Ex. `/posts/2021-08-01/2021-08-10`.



> Nota: As datas precisam estar em uma ordem onde a data mais antiga preenche a primeira lacuna de datas.
Ex. `/posts/2021-08-01/2021-08-10`;

- Atualizando um Post

Envie uma requisição com o método **PUT** para o Path `/posts/{id}` passando um JSON no seguinte formato:
```sh
{
        "title": "Teste",
        "description": "Teste description",
        "body": "Body test",
        "createdAt": "2021-08-10",
        "updateAt": "2021-08-11"
    }
```
> Nota: Não mude as datas de criação e atualização, não afetará o resultado.

- Deletando um post

Envie uma requisição de **DELETE** para o Path `/posts/{id}` com o Id do post que deseja deletar 

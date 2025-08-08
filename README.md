# Literalura

Literalura é uma aplicação Java com Spring Boot que consome dados da API do Projeto Gutenberg e os armazena em um banco de dados PostgreSQL. O sistema permite buscar livros por título, salvar autores e livros no banco, e listar dados por diferentes critérios.

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- API REST (Gutenberg)

## 📦 Funcionalidades

- Buscar livros por título usando a API do Projeto Gutenberg
- Salvar livros e autores no banco de dados
- Evitar duplicação de autores com verificação de existência por nome
- Listar livros cadastrados
- Listar autores cadastrados
- Listar livros por idioma
- Listar autores de um determinado ano
- Listar os 10 livros mais baixados

## ⚙️ Configuração

### application.properties

```properties
spring.application.name=literalura
spring.datasource.url=jdbc:postgresql:${DB_HOST}/literalura_db
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.data.jpa.repositories.enabled=true
```

## 💡 Observações

- Ao buscar um livro, o sistema verifica se o autor já existe no banco antes de salvar um novo.
- Livros também são verificados para evitar duplicatas com base no título.
- O relacionamento entre livros e autores é `@ManyToOne` (um autor pode ter vários livros).

## 🖥️ Como Rodar o Projeto

1. Clone o repositório:
```bash
git clone https://github.com/jjsetech/literalura.git
```

2. Acesse o diretório do projeto:
```bash
cd literalura
```

3. Configure o banco PostgreSQL e crie o banco `literalura_db`.

4. Execute o projeto com Maven ou via IDE (IntelliJ, Eclipse, etc).

5. No terminal da aplicação, você verá o menu para buscar e listar livros.

## ✅ Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
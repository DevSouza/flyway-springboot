# flyway-springboot

Projeto desenvolvido à fim de teste.

### Objetivos
- [x] Implementar test de migração.
- [x] Implementar migração (dry-run) no endpoint GET `v1/migrations`.
- [x] Implementar migração (live-run) no endpoint POST `v1/migrations`.

### Requisitos.
- Java 17
- Docker
- Maven

### Como rodar.
1. Rode o comando abaixo para iniciar o banco de dados.
    ```shell
    $ docker compose up
    ```
2. Rode o comando abaixo para rodar os testes.
   ```shell
   $ mvn test
   ```

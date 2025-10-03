# CRUD de Professores (Spring Boot + JDBC Oracle)

CRUD completo conectado a **Oracle** via JDBC puro.

## Como rodar
1) Ajuste `src/main/resources/application.properties` com sua URL/usuario/senha Oracle.
2) Rode o script SQL: `script/createTable_Professor.sql` no seu Oracle.
3) Compile/rode:
```bash
mvn -q -U clean package
mvn spring-boot:run
```
API em: `http://localhost:8080`

## Endpoints
- `GET /api/professores`
- `GET /api/professores/{id}`
- `POST /api/professores`
- `PUT /api/professores/{id}`
- `DELETE /api/professores/{id}`

### cURL
```bash
# Criar
curl -X POST http://localhost:8080/api/professores  -H "Content-Type: application/json"  -d '{ "nome":"Ana Silva", "departamento":"Computação", "email":"ana@fiap.com", "titulacao":"Doutora" }'

# Listar
curl http://localhost:8080/api/professores

# Buscar
curl http://localhost:8080/api/professores/1

# Atualizar
curl -X PUT http://localhost:8080/api/professores/1  -H "Content-Type: application/json"  -d '{ "nome":"Ana C. Silva", "departamento":"Computação", "email":"ana@fiap.com", "titulacao":"Doutora" }'

# Deletar
curl -X DELETE http://localhost:8080/api/professores/1
```

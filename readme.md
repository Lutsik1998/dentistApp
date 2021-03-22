# Backend
 
#### HOMEPAGE http://localhost:8080
 
## Dokumentation
 
- open-api + swagger: `[default_url]/swagger-ui/index.html`
 
example: `http://localhost:8080/swagger-ui/index.html`
 
- openapi: `[default_url]/api-docs`
 
example: `http://localhost:8080/api-docs`
 
## Runing and building
 
### 2 containers will be built and run: dentist-spring-WebAPI + mongodb-dentist (backend + MongoDB)
 
- docker-compose (windows):
```
docker-compose build
docker-compose up
```
 
- docker-compose (*nix):
```
docker-compose -f docker-compose-nix.yml build
docker-compose -f docker-compose-nix.yml up
```
 
### You can use these steps for running/building one of pieces of application:
 
Running Mongodb in container:
```
docker-compose up db
```
build and run backend-api:
```
docker-compose build dentist-spring-WebAPI
docker-compose up dentist-spring-WebAPI
```
 
## Download existing image from dockerhub
 //TODO
 

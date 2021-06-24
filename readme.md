# Prodental

For english scroll down.

## Instalacja i uruchomienie (Dockerizing)

Dla uruchomiana aplikacji musi być zainstalowany `docker` oraz `docker-compose`

Wewnątrz korzeniowego folderu projektu wykonać komendy z konsoli:

- windows:

```
docker-compose build
docker-compose up
```

- linux:

```
docker-compose -f docker-compose-nix.yml build
docker-compose -f docker-compose-nix.yml up
```

Po pierwszym uruchomianiu należy stworzyć konto administratora. Dla tego wystarczy wysłać GET zapytanie, na przykład za pomocą przeglądarki

```
http://localhost:8080/api/admin/initialize?admin=ryPr8dg5LvTjicETulyzoC60QBUKCovLjZMKxMXFb7dHc1xXINKlr29Fsd99XsFmdNKD4hB4Mu1iAVsXSdoLQMhPqCW3uMMhYHKxX5fv3NfYfD4huU5GVyDmKJuVeGLMRGFCEdKZPpJ9LcPPh15vzfIh8llQ8LiGaP8EXRMRgo6RqHeL1j7kWx5reu09cnLEOmTUS7508J3L1RYDibxJHpJUNIv4yqTJwFJJBapOYBTCn0SoHGpg1g7JTWwvfUwl
```

### Uwaga! Jeśli jeden z portów jest już zajęty, nie wszystkie elementy będą uruchomione.

## Wykorzystanie

Części aplikacji uruchomione na następujących portach:

- frontend - 80 (http://127.0.0.1:80)
- backend - 8080 (http://127.0.0.1:8080)
- mongodb - 27017 (http://127.0.0.1:27017)
- redis - 6379 (http://127.0.0.1:6379)

## Dokumentacja

- documentacja dla Backend'u (swagger): http://127.0.0.1:8080/swagger-ui/index.html
- testy Postman(TODO: tooth_tests): plik ./Spring_API/DentistApp.postman_collection.json

## Dodatkowa informacja

Miejsce przechowywania bazy danych Mongodb:

- Linux: `~/example/mongodb`
- Windows: `c:/data/db`

Miejsce przechowywania bazy danych Redis:

- Linux: `~/example/redis/data`
- Windows: `c:/data/redis/data`

# English

### Documentation:

## Build and Run (Dockerizing)

To run the application must be installed `docker` and `docker-compose`

Inside the root folder of the project, execute commands from the console:

- windows:

```
docker-compose build
docker-compose up
```

- linux:

```
docker-compose -f docker-compose-nix.yml build
docker-compose -f docker-compose-nix.yml up
```

To run some of services add the name of servise (main-db, cache-db, spring-WebAPI)

```
docker-compose up main-db cache-db
```

### Warning! If one of the ports is already busy, not all items will be running.

## Usage

Application running on the following ports:

- frontend - 80 (http://127.0.0.1:80)
- backend - 8080 (http://127.0.0.1:8080)
- mongodb - 27017 (http://127.0.0.1:27017)
- redis - 6379 (http://127.0.0.1:6379)
- Backend - swagger_v2: http://127.0.0.1:8080/swagger-ui/index.html

## Documentation

- Backend documentation (swagger): http://127.0.0.1:8080/swagger-ui/index.html
- Postman tests(TODO: tooth_tests): plik ./Spring_API/DentistApp.postman_collection.json

## Additiona Information

Mongodb storage place:

- Linux: `~/example/mongodb`
- Windows: `c:/data/db`

Redis storage place:

- Linux: `~/example/redis/data`
- Windows: `c:/data/redis/data`

<!-- ### Remove previous builds (Linux)

- list images

```
docker images
```

You have to copy `IMAGE ID` and paste it:

```
docker rmi images_names
```

Remove all <none> images (linux)

```
docker rmi $(docker images | grep '<none>' | awk '{print $3}')
``` -->


## Authors 
Created by students of the Wroclaw University of Technology:
1. Vladyslav Lutsenko - lutsenkovlad98@gmail.com  
2. Nikita Stepanenko - nikita.stepanenko.dev@gmail.com
3. Vladyslav Bobrov - vbobrov1234@gmail.com
4. Paweł Kuriata - kuriatapaw@gmail.com
5. Wojciech Czarnecki - 

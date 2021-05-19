# This branch is a merge of the last stable backend and frontend branches

### Documentation:
- Backend - swagger_v2: http://127.0.0.1:8080/swagger-ui/index.html

## Dockerizing:

#### Ports:
- frontend - 80
- backend - 8080
- mongodb -27017
- redis - 6379

### Build and Run

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

### Remove previous builds (Linux)
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
```

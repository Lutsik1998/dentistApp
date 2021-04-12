## Git

You have to clone reposiroty or pull `demo` branch (at your own risk).

```
git pull origin demo
git submodule update
```

## Build and Run

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

### Fronend wordk on '80' port, backend works on '8080' port, mongodb - '27017', redis - '6379'

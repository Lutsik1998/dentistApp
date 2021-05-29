full: copy build up

copy:
	./create_prod.sh

build:
	docker-compose -f docker-compose-nix.yml build

up:
	docker-compose -f docker-compose-nix.yml up

up-dev:
	docker-compose -f docker-compose-nix.yml up main-db cache-db angular-WebUI

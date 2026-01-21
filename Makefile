# Spring build
.PHONY: help
.DEFAULT_GOAL := hello

hello:
	echo "Hello there!"

format:
	./mvnw spotless:apply -Dspotless.skip=false

check-format:
	./mvnw spotless:check -Dspotless.skip=false

check-style:
	./mvnw checkstyle:check -Dcheckstyle.skip=false

compile:
	./mvnw clean compile

test:
	./mvnw clean test

test-architecture:
	./mvnw test -Parchitecture-tests

test-component:
	./mvnw verify -Pcomponent-tests

test-integration:
	./mvnw verify -Pintegration-tests

test-all:
	./mvnw verify -Pall-tests

build:
	./mvnw clean install

run:
	./mvnw spring-boot:run

docker-build:
	docker build -t tmudri/myapp:latest .

docker-run:
	docker run --rm -p 8080:8080 --name myapp tmudri/myapp:latest
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

test-component:
	./mvnw verify -Pcomponent-tests

test-integration:
	./mvnw verify -Pintegration-tests

test-all:
	./mvnw verify -Pall-tests

build:
	./mvnw clean install
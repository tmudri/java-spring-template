# Spring build
.PHONY: help
.DEFAULT_GOAL := hello

hello:
	echo "Hello there!"

format:
	./mvnw spotless:apply

check-format:
	./mvnw spotless:check

check-style:
	./mvnw checkstyle:check

compile:
	./mvnw clean compile

test:
	./mvnw clean test
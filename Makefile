# Spring build
.PHONY: help
.DEFAULT_GOAL := hello

hello:
	echo "Hello there!"

format:
	./mvnw spotless:apply

check-style:
	./mvnw checkstyle:checkstyle

compile:
	./mvnw clean compile

test:
	./mvnw clean test
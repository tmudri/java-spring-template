# Java Spring Template

This is a template project for microservices with Java and SpringBoot. In initial version it will focus on
creating an example of REST endpoint with data stored in in-memory DB.

Project contains following utilities:
- Makefile for easier running of maven commands by using `make`command
- Automatic formatting using Spotless (uses Google format)
- Code conventions check using Checkstyle (uses Google style)
- Bug detection and code correctness check with ErrorProne

## Code example
TODO:

## Utilities

Utilities help developers to work with the codebase in a convenient way. We can differentiate them depending on the
job with which they help:
- run
- code formatting and styling
- code conventions
- bug detection and code correctness

### Run

Utility used to simplify building and running the code is `make`. Make is build automation tool. It uses `Makefile` 
which holds the definition of various commands. It helps developers and architects run various commands in a simple way.

As an example, in our template project we can automatically format code by running:
```
make format
```
or we can run tests:
```
make test
```

For more commands check file `Makefile` in the root of the project.

### Code formatting and styling

Utilities which improve and standardize code formatting and styling are important because they improve readability and
maintainability of the code. When everyone working on a codebase uses same format and style (e.g. spacing,
order of imports...) it help with readability and easier understanding of the code. That leads to easier
maintainability (e.g. avoiding changes in commits caused by different code writing style keeps focus of commit
on business logic changes). 

Code formatting and styling focuses on:
- Whitespace, indentation
- Import order
- Line endings
- File endings
- ...

In this template we use **spotless** for formatting and styling. We can run it manually using `make`:
```
make format
```

### Code conventions

Code conventions another important tool which helps developers to standardize code and improve its readability and
maintainability. Usefulness is similar to standardizing formatting and style but the focus is a bit different.

Code conventions focus on:
- naming conventions,
- method/class length,
- javadoc presence,
- visibility modifiers,
- ...

In this template we use **checkstyle** for checking code conventions. We can run it manually using `make`:
```
make check-style
```

### Bug detection and code correctness

Early bug detection and code correctness is important for building high quality code fast. It is a great 
addition to writing of the tests. 

For bug detection and correctness checks in this template we use `Error Prone`. Bug patterns checked by Error Prone 
can be found on its web page [Error Prone: Bug Patterns](https://errorprone.info/bugpatterns).

# Future goals
- Split code into domain and adapters
- Add usage of PostgreSQL for running code, keep  in-memory DB for component tests
- Add DB module with flyway migrations for PostgreSQL DB
- Add docker build
- Add build definition with GitHub Actions
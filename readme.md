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

## Code testing

Core activity for building and maintaining good quality software is testing. Usually in software development when
developers focus on quality they try to follow teh approach of testing pyramid and write:
- unit tests 
  - fast test focusing on smallest units, 
  - largest volume of tests between all test types
  - bottom of the pyramid
- component tests 
  - a bit slower than unit tests, test components without their integrations (mocked) while treating components 
    like black boxed
  - typically we write less component than unit tests
  - layer above unit tests in a test pyramid
- integration tests 
  - generally slow tests which test integration between our code and other systems (e.g. connection to DB, connection
    to messaging system,...)
  - typically we write relatively small amount of integration tests
  - layer above component tests and below end-to-emd-tests
- end-to-end tests (*not covered in the code, added for completeness of test pyramid definition*)
  - long-running tests which cover testing of functionality in our service or the whole system from one end to the other
  - typically the category with the least amount of tests
  - layer on the top od the test pyramid

### Test plugins

In this template I use `surefire` to run unit tests. Unit tests run by default when we run maven test phase or
any other phase which includes the test phase.
In order to run component and integration tests `failsafe` is used with two different executions. Maven profiles are
used to differentiate between running different types of test:
 - component-tests (runs only component tests)
 - integration-tests (runs only integration tests)
 - all-tests (runs all tests, including unit, component and integration tests)

### Naming convention

It is worth to keep in mind that tests in this template use naming convention based on class name suffix:
 - Test - for unit tests
 - CT - for component tests
 - IT - for integration tests

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

**RECOMMENDED:** In case you use IntelliJ IDEA use google-java-format plugin for code formatting an
addition to the maven setup. That way you can reduce the need of using `make format` command.

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

**IMPORTANT:** `spotless` and `checkstyle` can be set up to run automatically during maven build. I have
intentionally avoided this option to reduce build time, especially for situations when developers run a 
lot of local builds. Teams using the template to build their own services can make a different choice and
run these tools on every build by uncommenting defined maven phase in which plugins should run inside of
`pom.xml` file.

**RECOMMENDED**: Use git `pre-commit` hook to run checkstyle and spotless checks and avoid commits with broken 
style and formatting. You can set it up by creating `pre-commit` file in location `${PROJECT_HOME}/.git/hooks/` with
following content:
```
#!/bin/sh
set -e

make check-format check-style
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
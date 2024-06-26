![example workflow](https://github.com/GMahlerTheTragic/SPL-Interpreter/actions/workflows/maven.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GMahlerTheTragic_SPL-Interpreter&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=GMahlerTheTragic_SPL-Interpreter)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GMahlerTheTragic_SPL-Interpreter&metric=coverage)](https://sonarcloud.io/summary/new_code?id=GMahlerTheTragic_SPL-Interpreter)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=GMahlerTheTragic_SPL-Interpreter&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=GMahlerTheTragic_SPL-Interpreter)

# A simple interpreter for SPL (Structured Programming Language)

This project is a simple interpreter for the SPL' programming language. It consists of a parser created using ANTLR and
an interpreter written by hand in Java.

## Project Structure

The project is structured as a Maven project, with the following main packages:

- `src/main/java/frontend`: This package contains two classes `SPLLexer` and `SPLParser` which where created from the
  grammar file `src/main/java/resources/spl-grammar/SPL.g4` for SPL using ANTL 4.12.0.

- `src/main/java/interpreter`: This directory contains the Java source code for the interpreter.
    * The directory `src/main/java/interpreter/visitor` contains interface and implementation of a Parse Tree
      Visitor (`SPLVisitor`) which maps SPL statements and expressions to their Java counterparts according to the
      semantic decisions documented in `SEMANTICS.md`. The visitor also manages the variable environment.
    * The class `Environment` keeps track of defined variables and their values over time. It also manages variable
      scopes through a Linked List of HashMaps, where each node in the Linked List refers to a seperat scope. Variable
      look-up is performed starting from the innermost scope by traversing the List. This leads to variables of inner
      scopes shadowing their counterparts in outer scopes.
    * The class `Value` is an abstract datatype that encapsulates numeric, boolean and string values and their
      corresponding operations as defined in `SEMANTICS.md`. It serves as a generic return type for expression
      evaluation in the interpreter.
    * The class `SPLInterpreter` serves as an entry point for the interpreter. It takes a path to a `.spl` file as a
      command line argument, parses it using `SPLLexer` and `SPLParser` and interprets the code using `SPLVisitor`.

- `src/test/resources/spl-sample-programms`: This directory contains four simple sample programms for SPL. They serve as
  E2E tests for the interpreter.

## Prerequisites

Before running the code, ensure that you have the following prerequisites installed:

- Java Development Kit (JDK) (version 20)
- Apache Maven
- ANTLR 4.13.0
- JUnit 5.5.2
- Mockito 5.4.0

## Running the Code

To build and run the interpreter, follow these steps:

1. Clone or download the project repository.

2. Open a terminal or command prompt and navigate to the project's root directory.

3. Build the project using Maven by running the following command:

```bash
mvn clean
mvn package
```

4. Interpret an SPL programm by calling the interpreter in the following way:

```bash
java -jar target/spl-interpreter-1.0-SNAPSHOT.jar `path/to/your/.spl/file`
```

## Running the test suite

To build and run the test suite, follow these steps:

1. Open a terminal or command prompt and navigate to the project's root directory.

2. Run the tests using Maven by running the following command:

```bash
mvn clean
mvn test
```

3. Note the E2E test can be found in the class `src/test/interpreter/SPLInterpreterTest`.


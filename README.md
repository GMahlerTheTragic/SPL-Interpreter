# A simple interpreter for SPL'

This project is a simple interpreter for the SPL' programming language. It consists of a parser written in ANTLR and an interpreter written by hand in Java.

## Project Structure

The project is structured as a Maven project, with the following main components:

- `src/main/java/parser`: This directory contains the ANTLR grammar file (`YourGrammar.g4`) that defines the syntax and grammar rules for SPL'.

- `src/main/java/interpreter`: This directory contains the Java source code for the interpreter.

## Prerequisites

Before running the code, ensure that you have the following prerequisites installed:

- Java Development Kit (JDK)
- Apache Maven
- ANTLR 4.13.0

## Running the Code

To run the finished code, follow these steps:

1. Clone or download the project repository.

2. Open a terminal or command prompt and navigate to the project's root directory.

3. Build the project using Maven by running the following command:

## Additional Notes

- If you need to modify the SPL' grammar, update the `SPL.g4` file in the `src/main/java/parser/grammar` directory. After making changes, rebuild the project using Maven as described above.

# FileSorting
# File Concatenation with Dependency Resolution

This Java program concatenates text files from a directory and its subdirectories, considering dependencies between the files specified using the `*require` directive. It sorts the files to ensure that dependencies are resolved correctly and concatenates their contents into a single output file. If a cyclic dependency is detected, the program reports the error and exits.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Classes and Methods](#classes-and-methods)
    - [FileConcatenation](#fileconcatenation)
    - [DependencyResolver](#dependencyresolver)
    - [FileConcatenator](#fileconcatenator)
    - [CyclicDependencyException](#cyclicdependencyexception)
- [Example](#example)

## Installation

1. Clone the repository or download the source code.
2. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
3. Ensure that JDK 22 or higher is installed on your system.
4. Compile and run the project.

## Usage

1. Set the path to your root directory containing the text files in the `rootDirectory` variable in the `FileConcatenation` class.
2. Run the `FileConcatenation` class.
3. The concatenated output will be written to `output.txt` in the specified root directory.

## Classes and Methods

### FileConcatenation

The main class to handle file concatenation with dependency resolution.

#### Methods
- **main(String[] args)**
    - Main method to run the file concatenation process.

### DependencyResolver

Class to handle the resolution of file dependencies.

#### Methods
- **DependencyResolver(File rootDirectory)**
    - Constructs a `DependencyResolver`.
    - Parameters:
        - `rootDirectory`: The root directory to start searching for files.

- **List<String> resolveDependencies()**
    - Resolves the dependencies between files and returns a sorted list of file paths.
    - Returns: A list of sorted file paths with resolved dependencies.
    - Throws: `IOException`, `CyclicDependencyException`.

- **List<File> collectTextFiles(File directory)**
    - Recursively collects all text files in the specified directory.
    - Parameters:
        - `directory`: The directory to search for text files.
    - Returns: A list of text files.

- **void parseFileForDependencies(File file)**
    - Parses a file to identify its dependencies.
    - Parameters:
        - `file`: The file to parse.
    - Throws: `IOException`.

- **String getRelativePath(File file)**
    - Gets the relative path of a file from the root directory.
    - Parameters:
        - `file`: The file to get the relative path for.
    - Returns: The relative path of the file.

- **List<String> topologicalSort()**
    - Performs a topological sort on the files based on their dependencies.
    - Returns: A list of file paths sorted by their dependencies.
    - Throws: `CyclicDependencyException`.

- **void topologicalSortUtil(String file, Set<String> visited, Set<String> stack, List<String> sortedFiles)**
    - Utility method to assist with the topological sort.
    - Parameters:
        - `file`: The current file being processed.
        - `visited`: The set of visited files.
        - `stack`: The stack of files being processed.
        - `sortedFiles`: The list of sorted files.
    - Throws: `CyclicDependencyException`.

### FileConcatenator

Class to handle the concatenation of files.

#### Methods
- **FileConcatenator(File rootDirectory, String outputFilePath)**
    - Constructs a `FileConcatenator`.
    - Parameters:
        - `rootDirectory`: The root directory containing the files.
        - `outputFilePath`: The path to the output file.

- **void concatenateFiles(List<String> sortedFiles)**
    - Concatenates the contents of the specified files into the output file.
    - Parameters:
        - `sortedFiles`: The list of file paths to concatenate.

### CyclicDependencyException

Exception thrown when a cyclic dependency is detected.

#### Methods
- **CyclicDependencyException(String message)**
    - Constructs a `CyclicDependencyException` with the specified message.
    - Parameters:
        - `message`: The detail message.

## Example

Given the following directory structure and file contents:
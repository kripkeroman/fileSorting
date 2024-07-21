package org.fileSorting.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DependencyResolver
{

    private static final String REQUIRE_DIRECTIVE = "*require";
    private final File rootDirectory;
    private final Map<String, List<String>> dependencies = new HashMap<>();


    /**
     * Constructs a DependencyResolver.
     *
     * @param rootDirectory
     */
    public DependencyResolver(File rootDirectory)
    {
        this.rootDirectory = rootDirectory;
    }

    /**
     * Resolves the dependencies between files and returns a sorted list of file paths.
     *
     * @return
     * @throws IOException
     * @throws CyclicDependencyException
     */
    public List<String> resolveDependencies() throws IOException, CyclicDependencyException
    {
        List<File> textFiles = collectTextFiles(rootDirectory);

        for (File file : textFiles)
        {
            parseFileForDependencies(file);
        }

        return topologicalSort();
    }

    /**
     * Recursively collects all text files in the specified directory.
     *
     * @param directory
     * @return
     */
    private List<File> collectTextFiles(File directory)
    {
        try (Stream<Path> walk = Files.walk(directory.toPath()))
        {
            return walk.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Parses a file to identify its dependencies.
     *
     * @param file
     * @throws IOException
     */
    private void parseFileForDependencies(File file) throws IOException
    {
        String relativePath = getRelativePath(file);
        List<String> dependentFiles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (line.startsWith(REQUIRE_DIRECTIVE))
                {
                    String dependency = line.substring(REQUIRE_DIRECTIVE.length()).trim().replaceAll("[‘’']", "");
                    dependentFiles.add(dependency);
                }
            }
        }

        dependencies.put(relativePath, dependentFiles);
    }

    /**
     * Gets the relative path of a file from the root directory.
     *
     * @param file
     * @return
     */
    private String getRelativePath(File file)
    {
        return rootDirectory.toPath().relativize(file.toPath()).toString().replace("\\", "/");
    }

    /**
     * Performs a topological sort on the files based on their dependencies.
     *
     * @return
     * @throws CyclicDependencyException
     */
    private List<String> topologicalSort() throws CyclicDependencyException
    {
        List<String> sortedFiles = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> stack = new HashSet<>();

        for (String file : dependencies.keySet())
        {
            if (!visited.contains(file))
            {
                topologicalSortUtil(file, visited, stack, sortedFiles);
            }
        }

        return sortedFiles;
    }

    /**
     * Utility method to assist with the topological sort.
     *
     * @param file
     * @param visited
     * @param stack
     * @param sortedFiles
     * @throws CyclicDependencyException
     */
    private void topologicalSortUtil(String file, Set<String> visited, Set<String> stack, List<String> sortedFiles) throws CyclicDependencyException
    {
        if (stack.contains(file))
        {
            throw new CyclicDependencyException("Cyclic dependency involving: " + file);
        }

        if (!visited.contains(file))
        {
            visited.add(file);
            stack.add(file);

            for (String dependency : dependencies.getOrDefault(file, Collections.emptyList()))
            {
                topologicalSortUtil(dependency, visited, stack, sortedFiles);
            }

            stack.remove(file);
            sortedFiles.add(file);
        }
    }
}

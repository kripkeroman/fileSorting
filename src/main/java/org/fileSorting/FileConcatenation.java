package org.fileSorting;

import org.fileSorting.utils.CyclicDependencyException;
import org.fileSorting.utils.DependencyResolver;
import org.fileSorting.utils.FileConcatenator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileConcatenation
{

    public static void main(String[] args)
    {
        File rootDirectory = new File("root_directory_path"); // Замените на путь к корневому каталогу

        try
        {
            DependencyResolver resolver = new DependencyResolver(rootDirectory);
            List<String> sortedFiles = resolver.resolveDependencies();

            FileConcatenator concatenator = new FileConcatenator(rootDirectory, "output.txt");
            concatenator.concatenateFiles(sortedFiles);
        }
        catch (IOException e)
        {
            System.err.println("Error reading files: " + e.getMessage());
        }
        catch (CyclicDependencyException e)
        {
            System.err.println("Cyclic dependency detected: " + e.getMessage());
        }
    }
}

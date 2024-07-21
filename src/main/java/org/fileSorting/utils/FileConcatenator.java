package org.fileSorting.utils;

import java.io.*;
import java.util.List;

public class FileConcatenator
{

    private final File rootDirectory;
    private final String outputFilePath;

    /**
     * Constructs a FileConcatenator.
     *
     * @param rootDirectory
     * @param outputFilePath
     */
    public FileConcatenator(File rootDirectory, String outputFilePath)
    {
        this.rootDirectory = rootDirectory;
        this.outputFilePath = outputFilePath;
    }

    /**
     * Concatenates the contents of the specified files into the output file.
     *
     * @param sortedFiles
     */
    public void concatenateFiles(List<String> sortedFiles)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath)))
        {
            for (String filePath : sortedFiles)
            {
                File file = new File(rootDirectory, filePath);
                try (BufferedReader reader = new BufferedReader(new FileReader(file)))
                {
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }
}

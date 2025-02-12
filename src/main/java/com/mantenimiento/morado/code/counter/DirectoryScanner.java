package com.mantenimiento.morado.code.counter;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryScanner {
    private final String directoryPath;

    /**
     *
     * @param directoryPath
     */
    public DirectoryScanner(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     * Retrieves a list of Java source files (*.java) from the specified directory and its subdirectories.
     * This method walks through the given directory, filters out non-file entries, and returns only the
     * files with a ".java" extension as a list of strings representing their absolute paths.
     *
     * @return A list of strings containing the absolute paths of all Java files found in the directory.
     *         If an error occurs while accessing the directory, an empty list is returned.
     *
     * @author Rubén Alvarado
     */
    public List<String> getJavaFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(string -> string.endsWith(".java"))
                    .collect(Collectors.toList());
        } catch (IOException ioException) {
            System.err.println("Error while trying to read directory path: " + ioException.getMessage());
        }

        return List.of();
    }
}

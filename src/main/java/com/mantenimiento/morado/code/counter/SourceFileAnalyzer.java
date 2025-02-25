package com.mantenimiento.morado.code.counter;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.code.syntax.SyntaxAnalyzer;
import com.mantenimiento.morado.util.Constants;

import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SourceFileAnalyzer {
    private final String directoryPath;

    /**
     * Constructs a new SourceFileAnalyzer with the specified directory path
     *
     * @param directoryPath The path to the directory containing Java source files.
     */
    public SourceFileAnalyzer(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     * Scans the directory for Java source files and analyzes their LOC.
     * It prints the results to the console in a tabular format.
     */
    public void analyzePath() {
        DirectoryScanner scanner = new DirectoryScanner(directoryPath);
        List<String> javaFilesPaths = scanner.getJavaFiles();

        printHeader();

        for (String filePath : javaFilesPaths) {
            SourceFile file;
            if (SyntaxAnalyzer.isJavaFileWellWritten(filePath)) {
                file = LOCCounter.countLOC(filePath);
            } else {
                file = getBadSourceFile(filePath);
            }

            printDetails(file);
        }
    }

    /**
     * Prints the table header for displaying the LOC analysis results.
     */
    private void printHeader() {
        System.out.printf("%-20s %-15s %-15s %-10s%n", "Program", "Logical LOC", "Physical LOC", "Status");
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * Prints the details of a java file.
     * It prints the filename, the logical LOC and the physical LOC in that order.
     *
     * @param file The {@link SourceFile} object containing the filename,
     *             logical LOC, and physical LOC to be printed
     * @see SourceFile
     */
    private void printDetails(SourceFile file) {
        System.out.printf(
                "%-20s %-15d %-15d %-10s%n",
                file.filename(),
                file.logicalLOC(),
                file.physicalLOC(),
                file.status()
        );
    }

    private SourceFile getBadSourceFile(String filepath) {
        Path file = Paths.get(filepath);

        return new SourceFile(
            file.getFileName().toString(),
            0,
            0,
                Constants.JAVA_FILE_STATUS_ERROR
        );
    }

}

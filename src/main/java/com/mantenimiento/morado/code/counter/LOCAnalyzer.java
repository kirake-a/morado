package com.mantenimiento.morado.code.counter;

import com.mantenimiento.morado.code.model.SourceFile;

import java.util.List;

public class LOCAnalyzer {
    private final String directoryPath;

    /**
     * Constructs a new LOCAnalyzer with the specified directory path
     *
     * @param directoryPath The path to the directory containing Java source files.
     */
    public LOCAnalyzer(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     * Scans the directory for Java source files and analyzes their LOC.
     * It prints the results to the console in a tabular format.
     */
    public void analyzePath() {
        DirectoryScanner scanner = new DirectoryScanner(directoryPath);
        List<String> files = scanner.getJavaFiles();

        printHeader();

        for (String filePath : files) {
            SourceFile file = SourceFileAnalyzer.analyze(filePath);
            printDetails(file);
        }
    }

    /**
     * Prints the table header for displaying the LOC analysis results.
     */
    private void printHeader() {
        System.out.printf("%-20s %-15s %-15s%n", "Program", "Logical LOC", "Physical LOC");
        System.out.println("----------------------------------------------------");
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
                "%-20s %-15d %-15d%n",
                file.filename(),
                file.logicalLOC(),
                file.physicalLOC()
        );
    }

}

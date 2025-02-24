package com.mantenimiento.morado.code.counter;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.code.model.LOCCountResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class LOCAnalyzer {

    public static SourceFile createSourceFile(String filePath){
        LOCCountResult countResult = countLOC(filePath);
        Path path = Paths.get(filePath);

        return new SourceFile(
                path.getFileName().toString(),
                countResult.logicalLOC(),
                countResult.physicalLOC()
        );
    }

    private static LOCCountResult countLOC(String filePath) {
        int physicalLOC = 0;
        int logicalLOC = 0;
        boolean inBLockComment = false;

        Path path = Paths.get(filePath);

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String trimmed = line.trim();

                if (inBLockComment) {
                    if (endsBlockComment(trimmed)) {
                        inBLockComment = false;
                    }
                    continue;
                }

                if (startsBlockComment(trimmed)) {
                    inBLockComment = true;
                    continue;
                }

                if (isIgnorableLine(trimmed)) {
                    continue;
                }

                physicalLOC++;

                if (isLogicalLine(trimmed)) {
                    logicalLOC++;
                }
            }

        } catch (IOException ioException) {
            System.err.println("Error while processing: " + ioException.getMessage());
        }

        return new LOCCountResult(logicalLOC, physicalLOC);
    }

    /**
     * Checks if the line determines the end of a block comment
     * @param line The line which is going to be checked
     * @return True whether is the end of a block comment
     */
    private static boolean endsBlockComment(String line) {
        return line.endsWith("*/");
    }

    /**
     *
     * @param line The line which is going to be checked
     * @return True whether is the start of block comment
     */
    private static boolean startsBlockComment(String line) {
        return line.startsWith("/*");
    }

    /**
     *
     * @param line The line which is going to be checked
     * @return True if the given line is ignorable
     */
    private static boolean isIgnorableLine(String line) {
        return line.isEmpty() || line.startsWith("//") || line.startsWith("*");
    }

    /**
     *
     * @param line The line which is going to be checked
     * @return Determines if the line is a logical line according to the standard
     */
    private static boolean isLogicalLine(String line) {
        return line.endsWith(";") || line.endsWith("{");
    }
}


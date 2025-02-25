package com.mantenimiento.morado.code.counter;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.Constants;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class LOCCounter {
    private static int logicalLOC = 0;
    private static int physicalLOC = 0;

    public static SourceFile countLOC(String filePath) {
        Path path = Paths.get(filePath);

        try {
            List<String> codeLines = SourceFile.getAllLinesFromFile(filePath);

            setLogicalLOC(countLogicalLOC(codeLines));
            setPhysicalLOC(countPhysicalLOC(codeLines));
        } catch (IOException ioException) {
            System.err.println("Error while processing file: " + ioException.getMessage());
        }

        return new SourceFile(
            path.getFileName().toString(),
            logicalLOC,
            physicalLOC,
            Constants.JAVA_FILE_STATUS_OK
        );
    }

    private static int countPhysicalLOC(List<String> codeLines) {
        int physicalLOC = 0;
        boolean inBlockComment = false;

        for (String line : codeLines) {
            String trimmed = line.trim();

            if (inBlockComment) {
                if (endsBlockComment(trimmed)) {
                    inBlockComment = false;
                }
                continue;
            }

            if (startsBlockComment(trimmed)) {
                inBlockComment = true;
                continue;
            }

            if (!isIgnorableLine(trimmed)) {
                physicalLOC++;
            }
        }

        return physicalLOC;
    }

    private static int countLogicalLOC(List<String> codeLines) {
        int logicalLOC = 0;
        boolean inBlockComment = false;

        for (String line : codeLines) {
            String trimmed = line.trim();

            if (inBlockComment) {
                if (endsBlockComment(trimmed)) {
                    inBlockComment = false;
                }
                continue;
            }

            if (startsBlockComment(trimmed)) {
                inBlockComment = true;
                continue;
            }

            if (!isIgnorableLine(trimmed) && isLogicalLine(trimmed)) {
                logicalLOC++;
            }
        }

        return logicalLOC;
    }

    private static boolean endsBlockComment(String line) {
        return line.endsWith("*/");
    }

    private static boolean startsBlockComment(String line) {
        return line.startsWith("/*");
    }

    private static boolean isIgnorableLine(String line) {
        return line.isEmpty() || line.startsWith("//") || line.startsWith("*");
    }

    private static boolean isLogicalLine(String line) {
        return line.endsWith(";") || line.endsWith("{");
    }

    private static void setLogicalLOC(int _logicalLOC) {
        logicalLOC = _logicalLOC;
    }

    private static void setPhysicalLOC(int _physicalLOC) {
        physicalLOC = _physicalLOC;
    }
}

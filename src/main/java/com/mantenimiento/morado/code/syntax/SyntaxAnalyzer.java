package com.mantenimiento.morado.code.syntax;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.Regex;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class SyntaxAnalyzer {

    /**
     *
     * @param filepath The path of the file
     * @return If the java file is well written
     */
    public static boolean isJavaFileWellWritten(String filepath) {

        try {
            List<String> lines = SourceFile.getAllLinesFromFile(filepath);

            if (hasMultiInstance(lines)) {
                return false;
            }

            for (String line : lines) {
                String trimmedLine = line.trim();

                if (trimmedLine.startsWith("{")) {
                    return false;
                }

                if (trimmedLine.endsWith("}") && !trimmedLine.equals("}")) {
                    return false;
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error while reading file: " + ioException.getMessage());
        }

        return true;
    }

    private static boolean hasMultiInstance(List<String> fileLines) {
        Pattern pattern = Pattern.compile(Regex.MULTI_INSTANCE_REGEX);
        boolean hasMultiInstance = fileLines.stream().anyMatch(line -> pattern.matcher(line).matches());
        return hasMultiInstance;  
    }
}

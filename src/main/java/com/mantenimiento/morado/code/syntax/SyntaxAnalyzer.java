package com.mantenimiento.morado.code.syntax;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.Regex;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


/**
 * The {@code SyntaxAnalyzer} class provides methods to analyze the syntactical correctness of a Java source file.
 * <p>
 * It verifies specific coding conventions such as:
 * <ul>
 *     <li>Multi-instance variable declarations on a single line.</li>
 *     <li>Lines that improperly start with an opening curly brace '{'.</li>
 *     <li>Lines that end with a closing curly brace '}' when they contain other characters.</li>
 * </ul>
 * If any of these conditions are detected, the file is considered to be not well written.
 * </p>
 *
 * @author Ruben Alvarado
 * @version 1.0.0
 */
public class SyntaxAnalyzer {

    /**
     * Checks if a Java file is well-written based on specific syntactical criteria.
     * <p>
     * The method reads the file and applies several validations:
     * <ul>
     *   <li>If any line matches the pattern for multi-instance variable declarations, it is considered invalid.</li>
     *   <li>If any line starts with an opening curly brace '{', it is considered invalid.</li>
     *   <li>If any line ends with a closing curly brace '}' but is not exactly "}", it is considered invalid.</li>
     * </ul>
     * If any of these conditions are met, the file is deemed not well-written.
     * </p>
     *
     * @param filepath the path of the Java file to analyze
     * @return {@code true} if the file is considered well-written; {@code false} otherwise
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

    /**
     * Checks whether any line in the provided list matches the multi-instance variable declaration pattern.
     * <p>
     * A multi-instance declaration typically involves declaring multiple variables on a single line,
     * e.g., "int a, b, c;". This method uses a regular expression (defined in {@link Regex#MULTI_INSTANCE_REGEX})
     * to detect such patterns.
     * </p>
     *
     * @param fileLines the list of lines read from the source file
     * @return {@code true} if at least one line matches the multi-instance pattern; {@code false} otherwise
     */
    private static boolean hasMultiInstance(List<String> fileLines) {
        Pattern pattern = Pattern.compile(Regex.MULTI_INSTANCE_REGEX);
        return fileLines.stream().anyMatch(line -> pattern.matcher(line).matches());
    }
}

package com.mantenimiento.morado.code.counter;

import com.mantenimiento.morado.code.model.SourceFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class SourceFileAnalyzer {
    public static SourceFile analyze(String filePath) {
        int physicalLOC = 0;
        int logicalLOC = 0;
        boolean inBLockComment = false;

        Path path = Paths.get(filePath);

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                physicalLOC++;
                String trimmed = line.trim();

                if (inBLockComment) {
                    if (trimmed.endsWith("*/")) {
                        inBLockComment = false;
                    }
                }

                if (trimmed.startsWith("/*")) {
                    inBLockComment = true;
                    continue;
                }

                if (trimmed.isEmpty() || trimmed.startsWith("//")) {
                    continue;
                }

                logicalLOC++;
            }

        } catch (IOException ioException) {
            System.err.println("Error while processing: " + ioException.getMessage());
        }

        return new SourceFile(
                path.getFileName().toString(),
                logicalLOC,
                physicalLOC
        );
    }
}

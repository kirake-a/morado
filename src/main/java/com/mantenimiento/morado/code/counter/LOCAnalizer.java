package com.mantenimiento.morado.code.counter;

import com.mantenimiento.morado.code.model.SourceFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

public class LOCAnalizer {
    public static LOCCountResult countLOC(String filePath) {
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

        return new LOCCountResult(logicalLOC, physicalLOC);
    }

    public static SourceFile createSourceFile(String filePath){
        LOCCountResult countResult = countLOC(filePath);
        Path path = Paths.get(filePath);
        return new SourceFile(
                path.getFileName().toString(),
                countResult.getLogicalLOC(),
                countResult.getPhysicalLOC()
        );
    }

    public static class LOCCountResult {
        private final int logicalLOC;
        private final int physicalLOC;

        public LOCCountResult(int logicalLOC, int physicalLOC) {
            this.logicalLOC = logicalLOC;
            this.physicalLOC = physicalLOC;
        }

        public int getLogicalLOC() {
            return logicalLOC;
        }

        public int getPhysicalLOC() {
            return physicalLOC;
        }
    }
}


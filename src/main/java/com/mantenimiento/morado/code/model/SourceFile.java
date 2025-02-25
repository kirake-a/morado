package com.mantenimiento.morado.code.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public record SourceFile(String filename, int logicalLOC, int physicalLOC, String status) {
    public static List<String> getAllLinesFromFile(String filepath) throws IOException {
        return Files.readAllLines(Paths.get(filepath));
    }
}

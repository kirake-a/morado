package com.mantenimiento.morado.code.counter;

import com.mantenimiento.morado.code.model.SourceFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LOCAnalyzerTest {

    @Mock
    private SourceFile sourceFile;

    @InjectMocks
    private LOCAnalyzer locAnalyzer = new LOCAnalyzer("dummy/path");

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));

        when(sourceFile.filename()).thenReturn("TestFile.java");
        when(sourceFile.logicalLOC()).thenReturn(50);
        when(sourceFile.physicalLOC()).thenReturn(100);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testPrintDetails() {

    }
}

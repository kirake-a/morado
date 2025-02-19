package com.mantenimiento.morado;

import com.mantenimiento.morado.code.counter.SourceFileAnalizer;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Try with: java Main <directory>");
            return;
        }

        SourceFileAnalizer analyzer = new SourceFileAnalizer(args[0]);
        analyzer.analyzePath();
    }
}

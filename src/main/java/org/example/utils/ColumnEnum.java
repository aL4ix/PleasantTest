package org.example.utils;

import java.util.stream.Stream;

public interface ColumnEnum {
    String getColumnName();

    static String nameToTitleCase(String name) {
        return Stream.of(name.split("_"))
                .map(w -> w.toUpperCase().charAt(0) + w.toLowerCase().substring(1))
                .reduce((s, s2) -> s + " " + s2).orElse("");
    }

    static String nameToCamelCase(String name) {
        return Stream.of(name.split("_"))
                .map(w -> w.toUpperCase().charAt(0) + w.toLowerCase().substring(1))
                .reduce((s, s2) -> s + s2).orElse("");
    }
}
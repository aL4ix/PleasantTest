package org.example.tables;

public interface TableRow<T> {
    String getCell(T column);
    String removeCell(T column);
    void setCell(T column, String value);
}

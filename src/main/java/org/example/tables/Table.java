package org.example.tables;

import java.util.List;

public class Table<T> {
    private final List<TableRow<T>> rows;

    public Table(List<TableRow<T>> rows) {
        this.rows = rows;
    }

    public String getCell(T column, int row) {
        return rows.get(row).getCell(column);
    }

    @Override
    public String toString() {
        return "Table" +
                rows;
    }
}

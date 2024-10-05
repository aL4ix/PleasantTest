package org.example.tables;

import java.util.LinkedHashMap;
import java.util.Map;

public class GeneralTableRow<T> implements TableRow<T> {
    Map<T, String> tableRow;

    public GeneralTableRow() {
        tableRow = new LinkedHashMap<>();
    }

    @Override
    public String getCell(T column) {
        return tableRow.get(column);
    }

    @Override
    public String removeCell(T column) {
        return tableRow.remove(column);
    }

    @Override
    public void setCell(T column, String value) {
        tableRow.put(column, value);
    }

    @Override
    public String toString() {
        return "GeneralTableRow" +
                tableRow;
    }
}

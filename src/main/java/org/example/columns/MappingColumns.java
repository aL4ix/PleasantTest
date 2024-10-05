package org.example.columns;

import org.example.utils.ColumnEnum;

public enum MappingColumns implements ColumnEnum {
    ID("Id"), XPATH("XPath"), IMPLEMENTS("Implements");

    private final String columnName;

    MappingColumns(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}

package org.example.columns;

import org.example.utils.ColumnEnum;

public enum MappingColumns implements ColumnEnum {
    ID, XPATH("XPath"), IMPLEMENTS;

    private final String columnName;

    MappingColumns() {
        columnName = ColumnEnum.nameToTitleCase(name());
    }

    MappingColumns(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}

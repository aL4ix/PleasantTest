package org.example.columns;

import org.example.utils.ColumnEnum;

public enum EnvironmentColumns implements ColumnEnum {
    VARIABLES("Variable"),QA("QA"),STAGING("Staging"),PROD("Prod");

    private final String columnName;

    EnvironmentColumns(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}

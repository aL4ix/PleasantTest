package org.example.columns;

import org.example.utils.ColumnEnum;

public enum EnvironmentColumns implements ColumnEnum {
    VARIABLES,QA("QA"),STAGING,PROD;

    private final String columnName;

    EnvironmentColumns() {
        columnName = ColumnEnum.nameToTitleCase(name());
    }

    EnvironmentColumns(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}

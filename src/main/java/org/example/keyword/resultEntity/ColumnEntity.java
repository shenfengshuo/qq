package org.example.keyword.resultEntity;

public class ColumnEntity {

    private String title;
    private String sourceField;
    private ColumnTypeEnumEntity columnType;

    public ColumnEntity() { this.columnType = ColumnTypeEnumEntity.STRING; }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceField() {
        return this.sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public ColumnTypeEnumEntity getColumnType() {
        return this.columnType;
    }

    public void setColumnType(ColumnTypeEnumEntity columnType) {
        this.columnType = columnType;
    }
}

package org.example.keyword.resultEntity;

import java.util.ArrayList;
import java.util.List;

public class TableObjectEntity {
    private List<ColumnEntity> columns = new ArrayList<>();
    private List<Object> data = new ArrayList<>();
    private String tableName;


    public void addColumn(String title,String fieldName){
        ColumnEntity newColumn = new ColumnEntity();
        newColumn.setTitle(title);
        newColumn.setSourceField(fieldName);
        this.columns.add(newColumn);
    }

    public void addColumn(String title, String fieldName, ColumnTypeEnumEntity columnType){
        ColumnEntity newColumn = new ColumnEntity();
        newColumn.setTitle(title);
        newColumn.setSourceField(fieldName);
        newColumn.setColumnType(columnType);
        this.columns.add(newColumn);
    }

    public List<ColumnEntity> getColumns() {
        return this.columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public List<Object> getData() {
        return this.data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

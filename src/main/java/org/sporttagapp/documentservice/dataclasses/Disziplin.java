package org.sporttagapp.documentservice.dataclasses;
public class Disziplin {
    private String name;
    private String einheit;
    private int columns;
    private int rows;

    public Disziplin(String name, String einheit, int columns, int rows) {
        this.name = name;
        this.einheit = einheit;
        this.columns = columns;
        this.rows = rows;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEinheit() {
        return this.einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}

package newbank.server.database;

public class Column {
    private String name;
    private ColumnType type;
    private boolean isNullable;
    private boolean isPrimaryKey;
    private boolean isUnique;
    //    HOW do we implement a default value that can be a union of String or Number?
    //    Object defaultValue;

    public Column(String name, ColumnType type, boolean isNullable, boolean isPrimaryKey, boolean isUnique) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
        this.isPrimaryKey = isPrimaryKey;
        this.isUnique = isUnique;
    }

    public String toSql() {
        return String.format("%s   %s %s %s", this.name, String.valueOf(this.type), this.isNullable ? "" : "NOT NULL", isPrimaryKey ? "PRIMARY KEY" : (isUnique ? "UNIQUE" : ""));
    }
}

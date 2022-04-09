package newbank.server.database;

import newbank.server.accounts.Account;
import newbank.server.customers.Customer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class Model<T> {
    Database db = Database.getInstance();

    public static ArrayList<Column> columns() {
        return null;
    };

    public abstract HashMap<String, Object> toJson();

    public abstract T fromJson(HashMap<String, Object>  json);

    public ArrayList<T> fromJsonCollection(ArrayList<HashMap<String, Object>> jsonCollection) {
        return new ArrayList<T>(jsonCollection.stream().map(c -> fromJson(c)).toList());
    }

    // Create a new entry in the database
    public void insertToDb(String tableName, HashMap<String, Object> objectToInsert) throws SQLException {
        String keys = String.join( ",", objectToInsert.keySet());
        ArrayList<String> stringValueList = new ArrayList<String>();

        for (Object o : objectToInsert.values()) {
            stringValueList.add(o.toString());
        }

        String values = String.join( ",", stringValueList);

        //  use the actual statement here, it is subject to sql injection but I don't know another way around making it dynamic yet
        String sql = String.format("INSERT INTO %s(%s) VALUES(%s);", tableName, keys, values);

        db.executeUpdate(sql);
    }

    public void updateDb(String tableName, HashMap<String, Object> objectToInsert, String primaryKey) throws SQLException {
        String keysWithoutKeyset = objectToInsert.keySet().stream().filter(s -> s != primaryKey).map(s -> String.format("%s=%s", s, objectToInsert.get(s).toString())).collect(Collectors.joining(","));
        String updateSql = String.format("UPDATE %s SET %s WHERE %s=%s", tableName, keysWithoutKeyset, primaryKey, objectToInsert.get(primaryKey));
        db.executeUpdate(updateSql);
    }

    public ArrayList<HashMap<String, Object>> getQueryResults(String sql) throws SQLException {
        ResultSet resultSet = db.query(sql);

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        ArrayList<HashMap<String, Object>> items = new ArrayList<>();
        while (resultSet.next()) {
            HashMap<String, Object> item = new HashMap<>();
            for (int i = 1; i <= columnsNumber; i++) {
                String columnName = rsmd.getColumnName(i);
                item.put(columnName, resultSet.getObject(columnName));
            }
            items.add(item);
        }
        return items;
    }

    public ArrayList<HashMap<String, Object>> fetchAllFromDb(String tableName) throws SQLException {
        String sql = String.format("SELECT * FROM %s", tableName);
        return getQueryResults(sql);
    }

    public ArrayList<HashMap<String, Object>> fetchAllFromDb(String tableName, HashMap<String, String> where) throws SQLException {
        if(where.isEmpty()) return fetchAllFromDb(tableName);

        String whereQuery = String.format("WHERE %s", where.entrySet().stream().map(entry -> String.format("%s='%s'", entry.getKey(), entry.getValue())).collect(Collectors.joining(" AND ")));

        String sql = String.format("select * from %s %s;", tableName, whereQuery);

        return getQueryResults(sql);
    }
}

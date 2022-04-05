package newbank.server.database;

import newbank.server.customers.Customer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Model<T> {
    Database db = Database.getInstance();

    public static ArrayList<Column> columns() {
        return null;
    };

    public abstract HashMap<String, Object> toJson();

    public abstract T fromJson(HashMap<String, Object>  json);

    // Create a new entry in the database
    public void insertToDb(String tableName, HashMap<String, Object> objectToInsert) throws SQLException {
        String keys = String.join( ",", objectToInsert.keySet());
        ArrayList<String> stringValueList = new ArrayList<String>();

        for (Object o : objectToInsert.values()) {
            stringValueList.add(String.format("'%s'", o.toString()));
        }

        String values = String.join( ",", stringValueList);

        //  use the actual statement here, it is subject to sql injection but I don't know another way around making it dynamic yet
        String sql = String.format("INSERT INTO %s(%s) VALUES(%s);", tableName, keys, values);

        db.executeUpdate(sql);
    }

    public ArrayList<HashMap<String, Object>> fetchAllFromDb(String tableName) throws SQLException {
        String sql = String.format("select * from %s", tableName);
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
}

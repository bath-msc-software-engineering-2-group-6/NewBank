package newbank.server.database;

import newbank.server.accounts.AccountModel;
import newbank.server.customers.CustomerModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Database {
    public static Database theInstance;
    private Connection connection;

    public static Database getInstance() {
        if (theInstance == null) {
            theInstance = new Database();
        }
        theInstance.getActiveConnection();

        return theInstance;
    }

    public boolean hasTable(String tableName) throws SQLException {
        String sql = String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s';", tableName);

        ResultSet resultSet = query(sql);
        return resultSet.next();
    }

    public void createTable(String tableName, ArrayList<Column> columns) throws SQLException {
        // Convert the columns to sql and join with a comma
        String columnsSql = columns.stream().map(c -> c.toSql()).collect(Collectors.joining(","));
        // check if the table exists
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s);", tableName, columnsSql);

        try {
            executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        }
    }

    public void runMigrations() throws SQLException {
        // Customer table
        CustomerModel.setupTable();
        // Accounts
        AccountModel.setupTable();
    }

    public Connection getActiveConnection() {
        String url = "jdbc:sqlite:db.sqlite";
        try {
            if (theInstance.connection == null || theInstance.connection.isClosed()) {
                theInstance.connection = DriverManager.getConnection(url);
            }
            return theInstance.connection;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    public void closeConnection() {
        try {
            if (theInstance.connection != null) {
                theInstance.connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet query (String sql) throws SQLException {
        try {
            Statement st = theInstance.connection.createStatement();
            return st.executeQuery(sql);
        } catch(SQLException e) {
            throw e;
        }
    }

    public int executeUpdate(String sql) throws SQLException {
        System.out.println(sql);

        Statement st = theInstance.connection.createStatement();
        return st.executeUpdate(sql);
    }
}

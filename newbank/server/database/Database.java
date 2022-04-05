package newbank.server.database;

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

    public void destroyAllTables() {
        String sql1 = "select 'drop table ' || name || ';' from sqlite_master where type = 'table';";
        // SELECT name FROM sqlite_master WHERE type='table' AND name='test_table1';
        // CREATE TABLE IF NOT EXISTS projects (
        //    project_id   INTEGER PRIMARY KEY,
        //    project_name TEXT    NOT NULL
        //);
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
        // ! ORDER IS IMPORTANT because of db relationships
        // Customer table
        CustomerModel.setupTable();
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

//    public void connect() {
//        Connection conn = null;
//        try {
//            // db parameters
//
//            // create a connection to the database
//            conn = DriverManager.getConnection(url);
//
//            System.out.println("Connection to SQLite has been established.");
//
//            String sql = "SELECT \n" +
//                    "    name\n" +
//                    "FROM \n" +
//                    "    sqlite_schema\n" +
//                    "WHERE \n" +
//                    "    type ='table'";
//
//            Statement st2 = conn.createStatement();
//
//            // Executing query
//            ResultSet rs = st2.executeQuery(sql);
//            System.out.println(rs.getFetchSize());
//            while (rs.next()) {
//                System.out.println(rs.getString("name"));
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//
//        }
//
//    }
}

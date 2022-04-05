package newbank.server.database;

import newbank.server.NewBankServer;
import newbank.server.accounts.Account;
import newbank.server.accounts.AccountModel;
import newbank.server.customers.CustomerModel;

import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

// Thanos is a supervillain that drops all your database tables
public class Thanos {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in); // Create Reader
        System.out.println("Thanks is a supervillain that drops all your database tables! Enter 6 to continue: that's the number of infinity stones");
        int input = scan.nextInt();
        if(input == 6) {
            dropTables();
            return;
        }

        System.out.println("Exiting Thanos...");
    }

    public static void dropTables() throws SQLException {
        Database dbInstance = Database.getInstance();
        // TODO: Add any new tables created here
        String[] tables = {AccountModel.tableName, CustomerModel.tableName, SeederModel.tableName};

        for(String table: tables) {
            String sql = String.format("drop table %s", table);
            System.out.println("Running: " + sql);
            dbInstance.executeUpdate(sql);
        }

        System.out.println("Done");
    }
}

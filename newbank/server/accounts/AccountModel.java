package newbank.server.accounts;

import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.database.Column;
import newbank.server.database.ColumnType;
import newbank.server.database.Database;
import newbank.server.database.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class AccountModel extends Model<Account> {
    public static String tableName = "accounts";
    private Account account;

    public static ArrayList<Column> columns() {
        ArrayList<Column> theColumns = new ArrayList<>();
        theColumns.add(new Column("id", ColumnType.TEXT, false, true, true));
        theColumns.add(new Column("name", ColumnType.TEXT, false, false, false));
        // We store balance in cents and convert it to pounds on retrieval
        theColumns.add(new Column("balance", ColumnType.INTEGER, false, false, false));
        // Ideally we should be using foreign keys here, but for now, we'll use WHERE queries
        theColumns.add(new Column("customer_id", ColumnType.TEXT, false, false, false));

        return theColumns;
    }

    public AccountModel() {}

    public AccountModel(Account a) {
        this.account = a;
    }

    public static void setupTable() throws SQLException {
        Database.getInstance().createTable(tableName, columns());
    }


    @Override
    public HashMap<String, Object> toJson() {
        HashMap<String, Object> json = new HashMap();
        json.put("id", String.format("'%s'", account.getAccountId().getAccountID()));
        json.put("name", String.format("'%s'", account.getAccountName()));
        json.put("customer_id", String.format("'%s'", account.getCustomerId().getKey()));
        json.put("balance", convertBalanceToCents(account.getBalance()));

        return json;
    }

    public static double convertBalanceToPounds (int balance) {
        return balance / 100;
    }

    public static int convertBalanceToCents (double balance) {
        return (int) balance * 100;
    }

    @Override
    public Account fromJson(HashMap<String, Object> json) {

        double balance = convertBalanceToPounds((int) (json.get("balance")));
        Account a = new Account(new CustomerID(json.get("customer_id").toString()), new AccountID(json.get("id").toString()), json.get("name").toString(), balance);
        return a;
    }

//    public ArrayList<Account> fromJsonCollection(ArrayList<HashMap<String, Object>> jsonCollection) {
//        return new ArrayList<Account>(jsonCollection.stream().map(account -> fromJson(account)).toList());
//    }

    public void insertToDb() throws SQLException {
        super.insertToDb(tableName, this.toJson());
    }

    public void updateDb() throws SQLException {
        super.updateDb(tableName, this.toJson(), "id");
    }

    public ArrayList<Account> fetchAllAccountsFromDb() throws SQLException {
        ArrayList<HashMap<String, Object>> accounts = super.fetchAllFromDb(tableName);

        return fromJsonCollection(accounts);
    }

    public ArrayList<Account> fetchCustomerAccountsFromDb(Customer customer) throws SQLException {
        HashMap<String, String> whereQuery = new HashMap<>();
        whereQuery.put("customer_id", customer.getCustomerId().getKey());
        ArrayList<HashMap<String, Object>> accounts = super.fetchAllFromDb(tableName, whereQuery);

        return fromJsonCollection(accounts);
    }
}

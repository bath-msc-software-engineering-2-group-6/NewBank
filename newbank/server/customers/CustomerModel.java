package newbank.server.customers;

import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import newbank.server.accounts.AccountManager;
import newbank.server.accounts.AccountModel;
import newbank.server.database.Column;
import newbank.server.database.ColumnType;
import newbank.server.database.Database;
import newbank.server.database.Model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerModel extends Model<Customer> {
    public static String tableName = "customers";
    private Customer customer;

    public static ArrayList<Column> columns() {
        ArrayList<Column> theColumns = new ArrayList<>();
        theColumns.add(new Column("id", ColumnType.TEXT, false, true, true));
        theColumns.add(new Column("password", ColumnType.TEXT, false, false, false));

        return theColumns;
    }

    public CustomerModel() {}

    CustomerModel(Customer c) {
        this.customer = c;
    }

    public static void setupTable() throws SQLException {
        Database.getInstance().createTable(tableName, columns());
    }


    @Override
    public HashMap<String, Object> toJson() {
        HashMap<String, Object> customerJson = new HashMap();
        customerJson.put("id", "'" + customer.getCustomerId().getKey() + "'");
        customerJson.put("password", "'" + customer.getPassword() + "'");

        return customerJson;
    }

    @Override
    public Customer fromJson(HashMap<String, Object> json) {
        Customer c = new Customer(new CustomerID(json.get("id").toString()));
        c.setPassword(json.get("password").toString());
        return c;
    }

//    public ArrayList<Customer> fromJsonCollection(ArrayList<HashMap<String, Object>> jsonCollection) {
//        return new ArrayList<Customer>(jsonCollection.stream().map(customer -> fromJson(customer)).toList());
//    }

    public void insertToDb() throws SQLException {
        super.insertToDb(tableName, this.toJson());
    }

    public ArrayList<Customer> fetchAllCustomersFromDb() throws SQLException {
        ArrayList<HashMap<String, Object>> customersJson = super.fetchAllFromDb(tableName);
        ArrayList<Customer> customers = fromJsonCollection(customersJson);
        for(Customer customer : customers) {
            ArrayList<Account> accounts = (new AccountModel()).fetchCustomerAccountsFromDb(customer);
            for(Account account: accounts) {
                customer.addAccount(account);
            }
        }

        return customers;
    }
}

package newbank.server.database;

import newbank.server.accounts.AccountManager;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerManager;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DatabaseSeeder {

    public DatabaseSeeder() throws SQLException {
        SeederModel.setupTable();
    }

    public static boolean checkDbSeeded() throws SQLException {
        if(!Database.getInstance().hasTable(SeederModel.tableName)) return false;
        System.out.println("Database tables are set up");
        ArrayList<HashMap<String, Object>> seedDates = (new SeederModel()).fetchAllFromDb(SeederModel.tableName);
        System.out.println(seedDates.size() > 0 ? "Database is already seeded" : "DB is not Seeded");
        return seedDates.size() > 0;
    }

    public void run() throws SQLException {
        if(checkDbSeeded()) return;

        CustomerManager theCustomerManager = CustomerManager.getInstance();
        AccountManager theAccountManager = AccountManager.getInstance();
        // We assume db tables are migrated already
        // seed customers
        Customer bhagy = theCustomerManager.createCustomer("Bhagy", "password");
        bhagy.addAccount(theAccountManager.createAccount(bhagy.getCustomerId(),"Main", 10000.0));

        Customer christina = theCustomerManager.createCustomer("Christina", "password");
        christina.addAccount(theAccountManager.createAccount(christina.getCustomerId(), "Savings", 1500.0));

        Customer john = theCustomerManager.createCustomer("John", "password");
        john.addAccount(theAccountManager.createAccount(john.getCustomerId(),"Checking", 250.0));

        // create a new db table
        String tableName = SeederModel.tableName;

        // Insert seed time into table
        SeederModel seederModel = new SeederModel(LocalTime.now().toString());
        seederModel.insertToDb(tableName, seederModel.toJson());
    }
}

class SeederModel extends Model<SeederModel> {
    String seededOn;

    public static String tableName = "seeds";

    SeederModel() {}

    SeederModel(String seededOn) {
        this.seededOn = seededOn;
    }

    public static void setupTable() throws SQLException {
        ArrayList<Column> columns = new ArrayList<>();
        columns.add(new Column("seeded_on", ColumnType.TEXT, false, false, false));
        Database.getInstance().createTable(tableName, columns);
    }

    @Override
    public HashMap<String, Object> toJson() {
        HashMap<String, Object> seedJson = new HashMap();
        seedJson.put("seeded_on", seededOn);

        return seedJson;
    }

    @Override
    public SeederModel fromJson(HashMap<String, Object>  json) {
        return null;
    }
}
package newbank.server.customers;

import newbank.server.database.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public final class CustomerManager {
    private static CustomerManager theInstance;
    public HashMap<String, Customer> theCustomers;

    private CustomerManager() {
        theCustomers = new HashMap<>();
    }

    public static CustomerManager getInstance() {
        if (theInstance == null) {
            theInstance = new CustomerManager();
        }

        return theInstance;
    }

    public boolean validateLogin(String name, String password) {
        // Retrieve the customer.
        Customer myCustomer = theCustomers.get(name);
        if(myCustomer.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    // For when we fetch customers from DB
    public HashMap<String, Customer> updateCustomersList(ArrayList<Customer> customers) {
        for(Customer customer : customers) {
            theCustomers.put(customer.getCustomerId().getKey(), customer);
        }
        return theCustomers;
    }

    public Customer createCustomer(String name, String password) throws SQLException{
        Customer myCustomer = new Customer(new CustomerID(name));
        theCustomers.put(name, myCustomer);
        theCustomers.get(name).setPassword(password);

        // TODO: refactor this and get rid of this constructor
        (new CustomerModel(myCustomer)).insertToDb();

        return myCustomer;
    }

    public void addCustomer(String name, Customer customer) {
        theCustomers.put(name, customer);
    }

    public Customer getCustomer(CustomerID customerID) {
        return theCustomers.getOrDefault(customerID.getKey(), null);
    }
}

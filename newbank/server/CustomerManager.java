package newbank.server;

import java.util.HashMap;

public final class CustomerManager {
    private static CustomerManager theInstance;
    private HashMap<String, Customer> theCustomers;

    private CustomerManager() {
        theCustomers = new HashMap<>();

        Customer bhagy = new Customer();
        bhagy.addAccount(new Account("Main", 1000.0));
        theCustomers.put("Bhagy", bhagy);

        Customer christina = new Customer();
        christina.addAccount(new Account("Savings", 1500.0));
        theCustomers.put("Christina", christina);

        Customer john = new Customer();
        john.addAccount(new Account("Checking", 250.0));
        theCustomers.put("John", john);
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

        if (myCustomer != null) {
            return true;
        }

        return false;
    }

    public void addCustomer(String name, Customer customer) {
        theCustomers.put(name, customer);
    }

    public Customer getCustomer(CustomerID customerID) {
        if (theCustomers.containsKey(customerID.getKey())) {
            return theCustomers.get(customerID.getKey());
        } else {
            return null;
        }
    }
}

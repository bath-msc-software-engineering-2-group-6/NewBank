package newbank.server.customers;

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

        return myCustomer != null;
    }

    public Customer createCustomer(String name) {
        Customer myCustomer = new Customer();
        theCustomers.put(name, myCustomer);

        return myCustomer;
    }

    public void addCustomer(String name, Customer customer) {
        theCustomers.put(name, customer);
    }

    public Customer getCustomer(CustomerID customerID) {
        return theCustomers.getOrDefault(customerID.getKey(), null);
    }
}

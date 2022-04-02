package newbank.server.customers;

import java.util.HashMap;

public final class CustomerManager {
    private static CustomerManager theInstance;
    public HashMap<String, Customer> theCustomers;
    private final int masterKey = 240322;

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

    public Customer createCustomer(String name, String password) {
        Customer myCustomer = new Customer(new CustomerID(name));
        theCustomers.put(name, myCustomer);
        theCustomers.get(name).setPassword(password);


        return myCustomer;
    }

    public void lockCustomer(String name){
        //lock customer
        Customer myCustomer = theCustomers.get(name);
        myCustomer.lockCustomer();
    }

    public boolean unlockCustomer(String name, int key){
        //unlock customer
        Customer myCustomer = theCustomers.get(name);
        if (key == masterKey){
            myCustomer.unlockCustomer();
            return true;
        }
        return false;
    }

    public boolean checkCustomerLock(String name){
        //check if customer is locked
        Customer myCustomer = theCustomers.get(name);
        boolean check = myCustomer.checkIfLocked();
        return check;
    }


    public void addCustomer(String name, Customer customer) {
        theCustomers.put(name, customer);
    }

    public Customer getCustomer(CustomerID customerID) {
        return theCustomers.getOrDefault(customerID.getKey(), null);
    }
}

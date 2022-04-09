package newbank.server.commands;

import newbank.server.accounts.AccountManager;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.sql.SQLException;
import java.util.ArrayList;

public class NewAccount implements Command {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    private final AccountManager theAccountManager = AccountManager.getInstance();

    public CommandResponse process(ArrayList<String> argsList) throws CommandException, SQLException {

        if (argsList.size() != 2) {
            String myException = "Invalid arguments, argsList contains: ";
            myException = myException.concat(argsList.toString());
            throw new CommandException(myException);
        } else {
            // Only consists of 2 arguments, customer ID, account name.
            Customer customer = theCustomerManager.getCustomer(new CustomerID(argsList.get(0)));
            String accountName = argsList.get(1);

            String myResponse = "";

            if (accountName.length()>12){
                myResponse = "FAIL - Name too long";
            } else if(customer.hasAccount(accountName)) {
                myResponse = "FAIL";
            } else {
                customer.addAccount(theAccountManager.createAccount(customer.getCustomerId(), accountName));
                myResponse = "SUCCESS";
            }

            return new CommandResponse(myResponse);
        }
    }

    public CommandResponse usage() {
        String myResponse = "NEWACCOUNT Example usage:\n";
        myResponse = myResponse.concat("NEWACCOUNT Checking\n");
        myResponse = myResponse.concat("Response: SUCCESS\n");
        myResponse = myResponse.concat("FAIL (If account name already exists)\n");

        return new CommandResponse(myResponse);
    }
}

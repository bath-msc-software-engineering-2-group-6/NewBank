package newbank.server.commands;

import newbank.server.Account;
import newbank.server.Customer;
import newbank.server.CustomerID;
import newbank.server.CustomerManager;

import java.util.ArrayList;

public class NewAccount implements Command {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();

    public CommandResponse process(ArrayList<String> argsList) throws CommandException {

        if (argsList.size() != 2) {
            String myException = "Invalid arguments, argsList contains: ";
            myException = myException.concat(argsList.toString());
            throw new CommandException(myException);
        } else {
            // Only consists of 2 arguments, customer ID, account name.
            Customer customer = theCustomerManager.getCustomer(new CustomerID(argsList.get(0)));
            String accountName = argsList.get(1);

            String myResponse = "";

            if(customer.hasAccount(accountName)) {
                myResponse = "FAIL";
            } else {
                myResponse = "SUCCESS";
                customer.addAccount(new Account(accountName, Account.DEFAULT_OPENING_BALANCE));
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

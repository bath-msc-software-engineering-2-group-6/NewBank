package newbank.server.commands;

import newbank.server.CustomerID;
import newbank.server.CustomerManager;

import java.util.ArrayList;

public class ShowMyAccounts implements Command {

    private CustomerManager theCustomerManager = CustomerManager.getInstance();

    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {

        if (anArgsList.size() != 1) {

            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {
            // Only consists of one argument, the customer ID.
            String myResponse = theCustomerManager.getCustomer(new CustomerID(anArgsList.get(0))).accountsToString();

            return new CommandResponse(myResponse);
        }
    }

    public CommandResponse usage() {
        String myResponse = "SHOWMYACCOUNTS\n";
        myResponse = myResponse.concat("Example:\n");
        myResponse = myResponse.concat("Account Name | Amount\n");
        myResponse = myResponse.concat("-------------|--------\n");
        myResponse = myResponse.concat(" Main        | 1000.0\n");

        return new CommandResponse(myResponse);
    }
}

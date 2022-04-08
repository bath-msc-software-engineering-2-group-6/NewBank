package newbank.server.commands;

import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;
import newbank.server.loans.LoanVault;

import java.util.ArrayList;

public class ShowMyLoans implements Command {
    private final CustomerManager theCustomerManager = CustomerManager.getInstance();

    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {

        if (anArgsList.size() != 1) {

            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {
            Customer customer = theCustomerManager.getCustomer(new CustomerID(anArgsList.get(0)));
            return new CommandResponse(LoanVault.getInstance().showMyLoans(customer.getCustomerId()));
        }
    }

    public CommandResponse usage() {
        String myResponse = "SHOWMYLOANS Example usage:\n";
        myResponse = myResponse.concat("SHOWMYLOANS\n");

        return new CommandResponse(myResponse);
    }
}

package newbank.server.commands;

import newbank.server.accounts.Account;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;
import newbank.server.loans.LoanMarket;

import java.util.ArrayList;

public class AcceptLoan implements Command {
    private final CustomerManager theCustomerManager = CustomerManager.getInstance();

    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {

        if (anArgsList.size() != 3) {

            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {
            Customer customer = theCustomerManager.getCustomer(new CustomerID(anArgsList.get(0)));
            String accName = anArgsList.get(1);
            Account receivingAcc = customer.findAccount(accName);
            Integer loanNumber = Integer.parseInt(anArgsList.get(2));

            LoanMarket.getInstance().acceptLoan(receivingAcc, loanNumber);
            LoanMarket.getInstance().removeLoan(loanNumber);
        return new CommandResponse("Success");
        }
    }

    public CommandResponse usage() {
        String myResponse = "ACCEPTLOAN Example usage:\n";
        myResponse = myResponse.concat("ACCEPTLOAN <Receiving Account> <Loan Number>\n");

        return new CommandResponse(myResponse);
    }
}

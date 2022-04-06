package newbank.server.commands;

import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;
import newbank.server.loans.LoanMarket;

import java.util.ArrayList;

public class CreateLoan implements Command {
    private final CustomerManager theCustomerManager = CustomerManager.getInstance();

    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {

        if (anArgsList.size() != 4) {

            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {
            Customer customer = theCustomerManager.getCustomer(new CustomerID(anArgsList.get(0)));
            Account account = customer.findAccount(anArgsList.get(1));
            String interestRateString = anArgsList.get(2);
            double interestRate = Double.parseDouble(interestRateString);
            String balanceString = anArgsList.get(3);
            double balance = Double.parseDouble(balanceString);

            LoanMarket.getInstance().addToMarket(account.getAccountId(), interestRate, balance);

            return new CommandResponse("Success");
        }
    }

    public CommandResponse usage() {
        String myResponse = "CREATELOAN Example usage:\n";
        myResponse = myResponse.concat("CREATELOAN <Your Acc> <Interest Rate> <Balance>\n");

        return new CommandResponse(myResponse);
    }
}

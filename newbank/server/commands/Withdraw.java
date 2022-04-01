package newbank.server.commands;

import newbank.server.accounts.Account;
import newbank.server.authentication.Authenticator;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.util.ArrayList;

public class Withdraw implements Command {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    private final Authenticator theAuthenticator = Authenticator.getInstance();

    public CommandResponse process(ArrayList<String> argsList) throws CommandException {

        if (argsList.size() != 3) {
            String myException = "Invalid arguments, argsList contains: ";
            myException = myException.concat(argsList.toString());
            throw new CommandException(myException);
        } else {
            // Only consists of 3 arguments - customer name, account name and amount to withdraw

            Customer customer = theCustomerManager.getCustomer(new CustomerID(argsList.get(0)));
            Customer bank = theCustomerManager.getCustomer(new CustomerID("NewBankUKPLC"));
            Account atm = bank.findAccount("ATM");

            String myResponse = "";

            try {
                String AccName = argsList.get(1);
                Account acc = customer.findAccount(AccName);

                String amountString = argsList.get(2);
                double amount = Double.parseDouble(amountString);

                if (acc == null){
                    myResponse = "FAIL";
                } else {
                    if(acc.getBalance() < amount){
                        myResponse = "INSUFFICIENT FUNDS.";
                    } else {
                        acc.debit(amount);
                        atm.credit(amount);
                        myResponse = "SUCCESS";
                    }
                }

            } catch (Exception e) {
                myResponse = "FAIL";
            }

            return new CommandResponse(myResponse);
        }
    }

    public CommandResponse usage() {
        String myResponse = "WITHDRAW Example usage:\n";
        myResponse = myResponse.concat("WITHDRAW Current 200\n");
        myResponse = myResponse.concat("Response: SUCCESS\n");
        myResponse = myResponse.concat("FAIL (if accounts cannot be found or insufficient funds.)\n");

        return new CommandResponse(myResponse);
    }

}

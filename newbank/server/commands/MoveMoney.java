package newbank.server.commands;

import newbank.server.Account;
import newbank.server.Customer;
import newbank.server.CustomerID;
import newbank.server.CustomerManager;

import java.util.ArrayList;

public class MoveMoney implements Command {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();

    public CommandResponse process(ArrayList<String> argsList) throws CommandException {

        if (argsList.size() != 4) {
            String myException = "Invalid arguments, argsList contains: ";
            myException = myException.concat(argsList.toString());
            throw new CommandException(myException);
        } else {
            // Only consists of 4 arguments, customer name, amount, account name for the account we are
            // moving money from and the account name we are moving money to.
            Customer customer = theCustomerManager.getCustomer(new CustomerID(argsList.get(0)));
            String fromAccName = argsList.get(2);
            String toAccName = argsList.get(3);


            String myResponse = "";

            Account from = customer.findAccount(fromAccName);
            Account to = customer.findAccount(toAccName);

            if(from == null || to == null ) {
                myResponse = "FAIL";
            } else {


                String amountString = argsList.get(1);
                double amount = Double.parseDouble(amountString);

                if(from.getBalance() < amount){
                    myResponse = "INSUFFICIENT FUNDS.";
                } else {
                    from.debit(amount);
                    to.credit(amount);
                    myResponse = "SUCCESS";
                }
            }
            return new CommandResponse(myResponse);
        }
    }

    public CommandResponse usage() {
        String myResponse = "MOVEMONEY Example usage:\n";
        myResponse = myResponse.concat("MOVEMONEY 300 CURRENT SAVINGS (from - to)\n");
        myResponse = myResponse.concat("Response: SUCCESS\n");
        myResponse = myResponse.concat("FAIL (if accounts cannot be found or insufficient funds.)\n");

        return new CommandResponse(myResponse);
    }
}

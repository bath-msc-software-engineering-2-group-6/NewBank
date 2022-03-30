package newbank.server.commands;

import newbank.server.accounts.Account;
import newbank.server.authentication.Authenticator;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.util.ArrayList;

public class MoveMoney implements Command {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    private final Authenticator theAuthenticator = Authenticator.getInstance();

    public CommandResponse process(ArrayList<String> argsList) throws CommandException {

        if (argsList.size() != 4 && argsList.size() != 5) {
            String myException = "Invalid arguments, argsList contains: ";
            myException = myException.concat(argsList.toString());
            throw new CommandException(myException);
        } else {
            // Only consists of 4 arguments, customer name, amount, account name for the account we are
            // moving money from and the account name we are moving money to.
            Customer customer = theCustomerManager.getCustomer(new CustomerID(argsList.get(0)));
            String fromAccName = argsList.get(2);
            String toAccName = argsList.get(3);
            String string2FA = "";
            if (argsList.size() == 5) {
                string2FA = argsList.get(4);
            }

            String myResponse = "";

            Account from = customer.findAccount(fromAccName);
            Account to = customer.findAccount(toAccName);

            if(from == null || to == null ) {
                myResponse = "FAIL";
            } else {

                try {
                    String amountString = argsList.get(1);
                    double amount = Double.parseDouble(amountString);

                    if (amount > 1000){
                        if (theAuthenticator.containsKey(customer.getCustomerId())){
                            String secretKey = theAuthenticator.getSecretKey(customer.getCustomerId());
                            theAuthenticator.runAuthentication(secretKey);
                            if (!string2FA.equals(theAuthenticator.code)){
                                myResponse = "Incorrect 2FA Code";
                                return new CommandResponse(myResponse);
                            }
                        }
                    }

                    if(from.getBalance() < amount){
                        myResponse = "INSUFFICIENT FUNDS.";
                    } else {
                        from.debit(amount);
                        to.credit(amount);
                        myResponse = "SUCCESS";
                    }
                } catch (Exception e) {
                    myResponse = "FAIL";
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

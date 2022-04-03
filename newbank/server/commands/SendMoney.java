package newbank.server.commands;

import newbank.server.accounts.Account;
import newbank.server.authentication.Authenticator;
import newbank.server.authentication.QRGen;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.util.ArrayList;

public class SendMoney implements Command{

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    private final Authenticator theAuthenticator = Authenticator.getInstance();

    public CommandResponse process(ArrayList<String> argsList) throws CommandException {

        if (argsList.size() != 5 && argsList.size() != 6) {
            String myException = "Invalid arguments, argsList contains: ";
            myException = myException.concat(argsList.toString());
            throw new CommandException(myException);
        } else {

            Customer customer = theCustomerManager.getCustomer(new CustomerID(argsList.get(0)));
            Customer receivingCustomer = theCustomerManager.theCustomers.get(argsList.get(3));
            String fromAccName = argsList.get(1);
            String toAccName = argsList.get(4);
            String string2FA = "";
            if (argsList.size() == 6) {
                string2FA = argsList.get(5);
            }

            String myResponse = "";

            Account from = customer.findAccount(fromAccName);
            Account to = receivingCustomer.findAccount(toAccName);

            if(from == null || to == null ) {
                myResponse = "FAIL";
            } else {

                try {
                    String amountString = argsList.get(2);
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
        String myResponse = "SENDMONEY Example usage:\n";
        myResponse = myResponse.concat("SENDMONEY <Your Acc> <Amount> <Receiver Name> <Receiver Acc> (<2FA>) (from - to)\n");
        myResponse = myResponse.concat("Response: SUCCESS\n");
        myResponse = myResponse.concat("FAIL (if accounts cannot be found or insufficient funds.)\n");

        return new CommandResponse(myResponse);
    }
}
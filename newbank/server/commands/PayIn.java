package newbank.server.commands;

import newbank.server.accounts.Account;
import newbank.server.authentication.Authenticator;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.util.ArrayList;

public class PayIn implements Command {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    private final Authenticator theAuthenticator = Authenticator.getInstance();

    public CommandResponse process(ArrayList<String> argsList) throws CommandException {

        if (argsList.size() != 3) {
            String myException = "Invalid arguments, argsList contains: ";
            myException = myException.concat(argsList.toString());
            throw new CommandException(myException);
        } else {
            // Only consists of 3 arguments - customer name, account name and amount to pay in

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
                    acc.credit(amount);
                    atm.debit(amount);
                    myResponse = "SUCCESS";
                 
                }

            } catch (Exception e) {
                myResponse = "FAIL";
            }

            return new CommandResponse(myResponse);
        }
    }

    public CommandResponse usage() {
        String myResponse = "PAYIN Example usage:\n";
        myResponse = myResponse.concat("PAYIN Current 200\n");
        myResponse = myResponse.concat("Response: SUCCESS\n");
        myResponse = myResponse.concat("FAIL\n");

        return new CommandResponse(myResponse);
    }

}

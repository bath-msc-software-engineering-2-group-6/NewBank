package newbank.server.commands;

import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;


import java.util.ArrayList;

public class Unlock implements Command {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    private final int masterKey = 240322;

    public CommandResponse process(ArrayList<String> argsList) throws CommandException {

        if (argsList.size() != 2) {
            String myException = "Invalid arguments, argsList contains: ";
            myException = myException.concat(argsList.toString());
            throw new CommandException(myException);
        } else {
            // Only consists of 2 arguments - command and master key

            String myResponse = "";
            Customer customer = theCustomerManager.getCustomer(new CustomerID(argsList.get(0)));

            String enteredKey = argsList.get(1);

            try {
                int key = Integer.parseInt(enteredKey);

                if (key == masterKey){
                    customer.unlockCustomer();
                    myResponse = "SUCCESS";
                } else {
                    myResponse = "FAIL";
                }
            } catch (Exception e){
                myResponse = "FAIL";
            }
            return new CommandResponse(myResponse);
        }
    }

    public CommandResponse usage() {
        String myResponse = "UNLOCK Example usage:\n";
        myResponse = myResponse.concat("UNLOCK XXXXXX (Master key)\n");
        myResponse = myResponse.concat("Response: SUCCESS - Customer unlocked\n");

        return new CommandResponse(myResponse);
    }

}

package newbank.server.commands;

import newbank.server.accounts.Account;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.util.ArrayList;

public class Withdraw implements Command {

    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {
        // for some reason, it cant resolve 'aBalance' even though I have imported the Account idea.
        int placeholderBalance = 1000;
        if(placeholderBalance >= withdramAmount){
            System.out.println(name + " withdraw " + withdramAmount);
            placeholderBalance = placeholderBalance - withdramAmount;
            System.out.println("Balance after transaction:" + withdramAmount);
        }
        else{
            System.out.println("You do not have enough finds to make this transaction, your balance is:" + withdramAmount);
        }
    }
    public CommandResponse usage() {
        return new CommandResponse("Type \"WITHDRAW\"");
    }
}

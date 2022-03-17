package newbank.server.commands;

import newbank.server.NewBankClientHandler;
import newbank.server.NewBankServer;
import newbank.server.customers.CustomerID;

import java.util.ArrayList;

public class Logout implements Command {
    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {
        if (anArgsList.size() != 1) {

            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {


            String myResponse = "Logout Successful";

            return new CommandResponse(myResponse);
        }

    };

    public CommandResponse usage() {
        return new CommandResponse("");
    }
}
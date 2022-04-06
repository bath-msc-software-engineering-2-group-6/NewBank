package newbank.server.commands;

import newbank.server.loans.LoanMarket;

import java.util.ArrayList;

public class ShowLoans implements Command {

    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {

        if (anArgsList.size() != 1) {

            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {

            return new CommandResponse(LoanMarket.getInstance().showLoans());
        }
    }

    public CommandResponse usage() {
        String myResponse = "SHOWLOANS Example usage:\n";
        myResponse = myResponse.concat("SHOWLOANS\n");

        return new CommandResponse(myResponse);
    }
}

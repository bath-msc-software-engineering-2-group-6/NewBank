package newbank.server.commands;

import java.util.ArrayList;

public class Logout implements Command {
    /**
     * Processes command for logging out
     * @param anArgsList - List of arguments from user input
     * @return CommandResponse, response from the logout process
     */
    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {
        if (anArgsList.size() != 1) {

            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {

            String myResponse = Constants.logoutResponse;
            return new CommandResponse(myResponse);
        }

    };
    /**
     * Displays to the user how to use the command
     * @return CommandResponse, instructions on how to use command as a String
     */
    public CommandResponse usage() {
        return new CommandResponse("Type \"LOGOUT\"");
    }
}
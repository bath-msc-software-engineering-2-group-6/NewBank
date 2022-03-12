package newbank.server.commands;

import java.util.ArrayList;
import java.util.HashMap;

public final class CommandManager {
    private static CommandManager theInstance;
    private HashMap<String, Command> theCommandMap;

    private CommandManager() {
        theCommandMap = new HashMap<>();

        // Insert all our commands here.
        theCommandMap.put("EXAMPLE",        new CommandExample());
        theCommandMap.put("SHOWMYACCOUNTS", new ShowMyAccounts());
        theCommandMap.put("NEWACCOUNT", new NewAccount());
    }

    public static CommandManager getInstance() {
        if (theInstance == null) {
            theInstance = new CommandManager();
        }

        return theInstance;
    }

    public Command getCommand(String aCommandKey) throws InvalidCommandException {
        Command myCommand = theCommandMap.get(aCommandKey);

        if (myCommand != null) {
            return myCommand;
        } else {
            throw new InvalidCommandException();
        }
    }

    public ArrayList<String> listAllCommands() {
        ArrayList<String> myCommands = new ArrayList<>();

        for (String key : theCommandMap.keySet()) {
            myCommands.add(key);
        }

        return myCommands;
    }

    public ArrayList<String> listUsageAllCommands() {
        ArrayList<String> myUsageCommands = new ArrayList<>();

        for (Command command : theCommandMap.values()) {
            myUsageCommands.add(command.usage().getResponse());
        }

        return myUsageCommands;
    }
}
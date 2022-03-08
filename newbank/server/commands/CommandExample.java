package newbank.server.commands;

import java.util.ArrayList;

public class CommandExample implements Command {
    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {
        if (anArgsList.size() < 0) {
            throw new CommandException("Invalid arguments!");
        }

        return new CommandResponse("");
    };

    public CommandResponse usage() {
        return new CommandResponse("");
    }
}

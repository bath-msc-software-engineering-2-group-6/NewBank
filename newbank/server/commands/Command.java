package newbank.server.commands;

import java.util.ArrayList;

public interface Command {
    CommandResponse process(ArrayList<String> anArgsList) throws CommandException;
    CommandResponse usage();
}

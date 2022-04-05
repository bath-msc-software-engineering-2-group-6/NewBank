package newbank.server.commands;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Command {
    CommandResponse process(ArrayList<String> anArgsList) throws CommandException, SQLException;
    CommandResponse usage();
}

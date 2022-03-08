package newbank.server.commands;

public class CommandException extends Exception {
    public CommandException(String errorMessage) {
        super(errorMessage);
    }
}

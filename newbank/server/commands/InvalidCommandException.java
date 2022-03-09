package newbank.server.commands;

public class InvalidCommandException extends CommandException {
    public InvalidCommandException() {
        super("Invalid command!");
    }
}

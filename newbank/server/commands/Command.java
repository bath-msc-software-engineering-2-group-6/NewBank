package newbank.server.commands;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.ArrayList;

public interface Command {
    CommandResponse process(ArrayList<String> anArgsList) throws CommandException;
    CommandResponse usage();
}

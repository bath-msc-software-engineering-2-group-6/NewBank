package newbank.server;

import newbank.server.commands.Command;
import newbank.server.commands.CommandException;
import newbank.server.commands.CommandManager;
import newbank.server.commands.CommandResponse;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.util.ArrayList;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private CustomerManager theCustomerManager = CustomerManager.getInstance();
	private CommandManager theCommandManager = CommandManager.getInstance();
	
	private NewBank() { }

	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(theCustomerManager.validateLogin(userName, password)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customerId, ArrayList<String> anArgsList) {
		String myResponse = "Unknown customer";
		CommandResponse myCommandResponse = null;
		Command myCommand = null;

		Customer myCustomer = theCustomerManager.getCustomer(customerId);

		if(myCustomer != null) {
			try {
				// First item in list is command key.
				myCommand = theCommandManager.getCommand(anArgsList.get(0));

				if (myCommand != null) {
					System.out.println("Processing command.");

					// Remove our processed command, remaining items are the arguments.
					anArgsList.remove(0);
					myCommandResponse = myCommand.process(anArgsList);

				} else {
					myResponse = "Unknown command!";
				}

			} catch (CommandException e) {
				myCommandResponse = myCommand.usage();
				System.err.println(e.getMessage());
			}
		}
		myResponse = myCommandResponse.getResponse();

		return myResponse;
	}
}

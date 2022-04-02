package newbank.server;

import newbank.server.accounts.AccountManager;
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
	public CustomerManager theCustomerManager = CustomerManager.getInstance();
	private CommandManager theCommandManager = CommandManager.getInstance();
	private AccountManager theAccountManager = AccountManager.getInstance();
	
	private NewBank() { }

	public static NewBank getBank() {
		return bank;
	}

	public CustomerManager theCustomerManager() {return theCustomerManager;}
	
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

	public void setUpTestEnvironment() {
		Customer bhagy = theCustomerManager.createCustomer("Bhagy", "password");
		bhagy.addAccount(theAccountManager.createAccount(bhagy.getCustomerId(),"Main", 10000.0));
		bhagy.setPassword("password");

		Customer christina = theCustomerManager.createCustomer("Christina", "password");
		christina.addAccount(theAccountManager.createAccount(christina.getCustomerId(), "Savings", 1500.0));
		christina.setPassword(("password"));

		Customer john = theCustomerManager.createCustomer("John", "password");
		john.addAccount(theAccountManager.createAccount(john.getCustomerId(),"Checking", 250.0));
		john.setPassword(("password"));
	}
}

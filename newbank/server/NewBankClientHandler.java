package newbank.server;

import newbank.server.commands.Constants;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class NewBankClientHandler extends Thread{
	
	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	private CustomerID customer;
	private final CustomerManager theCustomerManager = CustomerManager.getInstance();

	/**
	 * Instantiates I/O for client handler and gets NewBank object
	 * @param s - NewBankServer server socket
	 */
	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		bank.setUpTestEnvironment();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}
	/**
	 * Handles requests from user from startup to commands when logged in
	 */
	public void run() {
		boolean loggedIn = false;
		// keep getting requests from the client and processing them
		try {
			// Run login method to get CustomerID
			while(true) {
				startUp();

				if (customer != null) {
					out.println("Log In Successful. What do you want to do?");
					loggedIn = true;
					while (loggedIn) {
						String myRequest = in.readLine();
						// Split string up by spaces. First word is request keyword, the rest are request arguments.
						ArrayList<String> mySplitRequest = new ArrayList<>(Arrays.asList(myRequest.split(" ")));

						// Insert customer after the request keyword.
						mySplitRequest.add(1, customer.getKey());

						System.out.println("Request from " + customer.getKey());

						// Process request.
						String response = bank.processRequest(customer, mySplitRequest);
						out.println(response);
						if (response.equals(Constants.logoutResponse)) {
							loggedIn = false;
							this.customer = null;
						}
					}
				} else {
					out.println("Log In Failed");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
	/**
	 * Allows the user the choice of logging in or creating a new customer object
	 */
	public void startUp() throws IOException {
		try {
			out.println("Login or Setup New Customer?");
			String startUpString = in.readLine();
			if (startUpString.equals(Constants.startLogin)) {
				this.customer = login();
			} else if (startUpString.equals(Constants.startSetupNewCustomer)){
				createCustomerOnStartup();
			} else {
				out.println("Type \"LOGIN\" or \"SETUPCUSOMTER\"");
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Creates a customer object
	 */
	private void createCustomerOnStartup() throws IOException {
		boolean creationSuccess = false;
		try {
			while (!creationSuccess) {
				out.println("Enter Customer Name");
				String customerName = in.readLine();
				out.println("Enter New Password");
				String customerPassword = in.readLine();
				if (checkCustomerExists(customerName)) {
					out.println("Customer Name Taken, Please Try Another");
				} else {
					Customer newCustomer = theCustomerManager.createCustomer(customerName, customerPassword);
					this.customer = bank.checkLogInDetails(customerName, customerPassword);
					creationSuccess = true;
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Checks if chosen customer name is already taken
	 * @param name - the given name
	 * @return boolean
	 */
	private boolean checkCustomerExists(String name) {
		for (String customer : theCustomerManager.theCustomers.keySet()){
			if (name.equals(customer)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Sets customerID to chosen customer from login details
	 * @return customer object
	 */
	public CustomerID login() throws IOException {
		// ask for user name
		out.println("Enter Username");
		String userName = in.readLine();
		//checking if the customer's accounts are locked
		if (!bank.theCustomerManager.checkCustomerLock(userName)){
		// we are going to let users try 5 times
			for (int i = 5; i > 0; i--){
				// ask for password
				out.println("Enter Password");
				String password = in.readLine();
				out.println("Checking Details...");
				// authenticate user and get customer ID token from bank for use in subsequent requests
				this.customer = bank.checkLogInDetails(userName, password);
				// if the user is authenticated then get requests from the user and process them
				if (this.customer != null){
					return this.customer;
				} else if (i>1){
					out.println("Password incorrect!");
					out.println("You can try again " + (i-1) + " times.");
				} else {
					out.println("Password incorrect! Customer locked.");
					bank.theCustomerManager.lockCustomer(userName);
					return null;
				}
			}
		} else {
			out.println("Customer locked. Unable to log in.");
			return null;
		}
		return null;
	}

}

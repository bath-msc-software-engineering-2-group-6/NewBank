package newbank.server;

import newbank.server.commands.Constants;
import newbank.server.customers.CustomerID;

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
	
	
	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		bank.setUpTestEnvironment();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}
	
	public void run() {
		boolean loggedIn = false;
		// keep getting requests from the client and processing them
		try {
			// Run login method to get CustomerID
			while(true) {
				this.customer = login();

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

	public CustomerID login() throws IOException {
		// ask for user name
		out.println("Enter Username");
		String userName = in.readLine();
		// we are going to let users try 3 times
		int remaining = 5;
		for (int i = 1; i < 6; i++){
			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");
			// authenticate user and get customer ID token from bank for use in subsequent requests
			this.customer = bank.checkLogInDetails(userName, password);
			// if the user is authenticated then get requests from the user and process them
			if (this.customer != null){
				return this.customer;
			} else if (remaining != 0){
				out.println("Password incorrect!");
				remaining--;
				out.println("You can try again " + remaining + " times.");
			} else {
				out.println("Account locked.");
				return null;
			}
		}
	}

}

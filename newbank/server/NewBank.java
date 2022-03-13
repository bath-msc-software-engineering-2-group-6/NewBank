package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		bhagy.setPassword("password"); //placeholder password
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		christina.setPassword("password");
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
		john.setPassword("password");
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			Customer customer = customers.get(userName);
			if(customer.getPassword().equals(password))

			return new CustomerID(userName);
		}
		return null;
	}

	//check newly created acccount passwords are valid
/*
	public boolean PasswordValidility(String password){
		int minCharacters = 10;
		int maxCharacters= 20;
		int passwordLength = password.length();

		if(passwordLength ≥ minCharacters && passwordLength ≤ maxCharacters){
			return true;
		}else{
			return false;

		}
	}*/

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}

	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

}

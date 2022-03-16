package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String CustomerID;
	private String password = "password"; //default test


	//add account here too
	public Customer(){
		accounts = new ArrayList<>();

	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

//setter
	public void setPassword(String Password){
		this.password =  Password;
	}
	//getter
	public String getPassword(){
		return this.password;
	}


	public void addAccount(Account account) {
		accounts.add(account);		
	}
}

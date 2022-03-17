package newbank.server.customers;

import newbank.server.accounts.Account;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public boolean hasAccount(String accountName) {
		System.out.println(accountName);
		System.out.println(accountsToString());
		// find accountName in list of user accounts
		Account existingAccount = accounts.stream().filter(account -> account.getAccountName().equals(accountName)).findFirst().orElse(null);
		return existingAccount != null;
	}

	public Account findAccount (String name){
		for (Account account : accounts){
			if (account.getAccountName().equals(name)){
				return account;
			}
		}
		return null;
	}
}

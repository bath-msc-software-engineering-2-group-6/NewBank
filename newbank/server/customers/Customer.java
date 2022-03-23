package newbank.server.customers;

import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import newbank.server.accounts.AccountManager;

import java.util.ArrayList;


public class Customer {
	private final AccountManager theAccountManager = AccountManager.getInstance();

	private ArrayList<AccountID> theAccountIds;
	private String CustomerID;
	private String password = "password";

	public Customer() {
		theAccountIds = new ArrayList<>();
	}

	public String accountsToString() {
		ArrayList<Account> myAccounts = theAccountManager.getAccounts(theAccountIds);
		String myString = "Account Name | Amount\n-------------|--------\n";
		for (Account account : myAccounts) {
			myString += account.toString();
		}
		return myString;
	}

	public void setPassword(String Password){
		this.password =  Password;
	}

	public String getPassword(){
		return this.password;
	}

	public void addAccount(AccountID anAccountId) {
		theAccountIds.add(anAccountId);
	}

	public boolean hasAccount(String accountName) {
		System.out.println(accountName);
		System.out.println(accountsToString());

		// Find accountName in list of user accounts
		Account existingAccount = theAccountManager.findAccount(theAccountIds, accountName);

		return existingAccount != null;
	}

	public Account findAccount (String accountName) {
		return theAccountManager.findAccount(theAccountIds, accountName);
	}
}

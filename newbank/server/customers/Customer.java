package newbank.server.customers;

import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import newbank.server.accounts.AccountManager;
import newbank.server.accounts.AccountModel;

import java.sql.SQLException;
import java.util.ArrayList;


public class Customer {
	private final AccountManager theAccountManager = AccountManager.getInstance();

	private ArrayList<AccountID> theAccountIds;
	private final CustomerID theCustomerId;
	private String password = "password";

	public Customer(CustomerID aCustomerId) {
		theAccountIds = new ArrayList<>();
		theCustomerId = aCustomerId;
	}

	public String accountsToString() {
		ArrayList<Account> myAccounts = theAccountManager.getAccounts(theAccountIds);
		String myString = "Account Name | Amount\n-------------|--------\n";
		for (Account account : myAccounts) {
			myString += account.toString();
		}
		return myString;
	}

	public void setPassword(String password){
		this.password =  password;
	}

	public String getPassword(){
		return this.password;
	}

	public CustomerID getCustomerId() {
		return theCustomerId;
	}

	public void addAccount(AccountID anAccountId) throws SQLException {
		addAccount(anAccountId, true);
	}

	public void addAccount(AccountID anAccountId, boolean saveToDb) throws SQLException {
		if(saveToDb) {
			// save to db
			Account theAccount = theAccountManager.getAccount(anAccountId);
			AccountModel accountModel = new AccountModel(theAccount);
			accountModel.insertToDb();
		}

		// add to the list
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

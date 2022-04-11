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
	public boolean locked;


	public Customer(CustomerID aCustomerId) {
		locked = false;
		theAccountIds = new ArrayList<>();
		theCustomerId = aCustomerId;
	}

	public void lockCustomer (){
		locked = true;
	}

	public void unlockCustomer (){
		locked = false;
	}

	public boolean checkIfLocked(){
		return locked;
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

	public void addAccount(Account anAccount) throws SQLException {
		Account theSavedAccount = theAccountManager.getAccount(anAccount.getAccountId());

		// This account is not yet in the instance.
		if(theSavedAccount == null) {
			theSavedAccount = theAccountManager.putAccount(anAccount);
		}
		// add to the list
		theAccountIds.add(theSavedAccount.getAccountId());
	}

	public boolean hasAccount(String accountName) {
		// Find accountName in list of user accounts
		Account existingAccount = theAccountManager.findAccount(theAccountIds, accountName);

		return existingAccount != null;
	}

	public Account findAccount (String accountName) {
		return theAccountManager.findAccount(theAccountIds, accountName);
	}
}

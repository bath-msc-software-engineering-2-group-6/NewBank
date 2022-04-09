package newbank.server.accounts;

import newbank.server.customers.CustomerID;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class AccountManager {
    private static AccountManager theInstance;
    private final HashMap<AccountID, Account> theAccounts;

    /**
     * AccountManager is a Singleton object which manages the accounts.
     */
    private AccountManager() {
        theAccounts = new HashMap<>();
    }

    /**
     * Retrieves the single instance of AccountManager.
     * @return an instance of AccountManager
     */
    public static AccountManager getInstance() {
        if (theInstance == null) {
            theInstance = new AccountManager();
        }

        return theInstance;
    }

    public static HashMap<AccountID, Account> allAccounts() {
        return getInstance().theAccounts;
    }

    /**
     * Creates an account with a given name and balance and stores it.
     * @param anAccountName - the given name
     * @param aBalance - the given balance
     * @return the account id associated with the account
     */
    public AccountID createAccount(CustomerID aCustomerId, String anAccountName, double aBalance) {
        // Generate an account id.
        AccountID myAccountId = new AccountID();
        Account myAccount = new Account(aCustomerId, myAccountId, anAccountName, aBalance);

        theAccounts.put(myAccountId, myAccount);

        return myAccountId;
    }

    /**
     * Creates an account with a given name and stores it.
     * @param anAccountName - the given name
     * @return the account id associated with the account
     */
    public AccountID createAccount(CustomerID aCustomerId, String anAccountName) {
        // Generate an account id.
        AccountID myAccountId = new AccountID();
        Account myAccount = new Account(aCustomerId, myAccountId, anAccountName);

        theAccounts.put(myAccountId, myAccount);

        return myAccountId;
    }

    /**
     * Adds a new account to the local collection of accounts. Used for when we fetch accounts from the db.
     * @param anAccount - the account
     * @return the account itself
     */
    public Account putAccount(Account anAccount) {
        if(theAccounts.get(anAccount.getAccountId()) == null) theAccounts.put(anAccount.getAccountId(), anAccount);
        return anAccount;
    }

    /**
     * Searches for an account by name within a given list of account id's.
     * @param anAccountIds - the list of account id's
     * @param anAccountName - the account name to search for
     * @return if found returns the account, otherwise null
     */
    public Account findAccount(ArrayList<AccountID> anAccountIds, String anAccountName) {
        for (AccountID accountId : anAccountIds) {
            Account myAccount = getAccount(accountId);
            if (myAccount != null && myAccount.getAccountName().equals(anAccountName)) {
                return myAccount;
            }
        }

        return null;
    }

    /**
     * Retrieves an account from the accounts list by a given account id.
     * @param anAccountId - the given account id
     * @return if exists returns the account, otherwise null
     */
    public Account getAccount(AccountID anAccountId) {
        return theAccounts.getOrDefault(anAccountId, null);
    }

    public ArrayList<Account> getAccounts(ArrayList<AccountID> anAccountIds) {
        ArrayList<Account> myAccountList = new ArrayList<>();

        for (AccountID accountId : anAccountIds) {
            myAccountList.add(getAccount(accountId));
        }

        return myAccountList;
    }

    /**
     * Transfers money between two accounts.
     * @param aFromAccountId - the account id to transfer money from
     * @param aToAccountId - the account id to transfer money to
     * @param anAmount - the amount of money to transfer
     * @return true if the transfer is successful, otherwise false
     */
    public boolean transferMoney(AccountID aFromAccountId, AccountID aToAccountId, double anAmount) throws SQLException {
        // Retrieve accounts by AccountID.
        Account myFromAccount = getAccount(aFromAccountId);
        Account myToAccount = getAccount(aToAccountId);

        if (myFromAccount != null && myToAccount != null) {
            // Verify the from account has a large enough balance.
            if (myFromAccount.getBalance() >= anAmount) {
                myFromAccount.debit(anAmount);
                myToAccount.credit(anAmount);

                return true;
            }
        }

        return false;
    }
}

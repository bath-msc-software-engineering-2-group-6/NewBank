package newbank.server.accounts;

import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     * Creates an account with a given name and balance and stores it.
     * @param anAccountName - the given name
     * @param aBalance - the given balance
     * @return the account id associated with the account
     */
    public AccountID createAccount(String anAccountName, double aBalance) {
        Account myAccount = new Account(anAccountName, aBalance);
        AccountID myAccountId = new AccountID(myAccount);

        theAccounts.put(myAccountId, myAccount);

        return myAccountId;
    }

    /**
     * Creates an account with a given name and stores it.
     * @param anAccountName - the given name
     * @return the account id associated with the account
     */
    public AccountID createAccount(String anAccountName) {
        Account myAccount = new Account(anAccountName);
        AccountID myAccountId = new AccountID(myAccount);

        theAccounts.put(myAccountId, myAccount);

        return myAccountId;
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

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public boolean transferMoney(AccountID from, AccountID to) {
        return true;
    }
}

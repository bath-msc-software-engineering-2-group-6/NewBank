package newbank.server.loans;

import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import newbank.server.accounts.AccountManager;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.sql.SQLException;

public class Loan {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    private final AccountManager theAccountManager = AccountManager.getInstance();

    private final AccountID theHolder;
    private final AccountID theRecipient;
    private final double theInterestRate;
    private double theBalance;

    /**
     * Class which represents a loan between a holder and a recipient.
     * @param aHolder - the holder of the loan
     * @param aRecipient - the recipient of the loan
     * @param anInterestRate - the interest rate of the loan
     * @param aBalance - the balance of the loan
     */
    public Loan(AccountID aHolder, AccountID aRecipient, double anInterestRate, double aBalance) throws SQLException {

        this.theHolder = aHolder;
        this.theRecipient = aRecipient;
        this.theInterestRate = anInterestRate;
        this.theBalance = aBalance;

        credit(aBalance);
    }

    /**
     * Retrieves the holder as a Customer.
     * @return the holder as a Customer
     */
    public Customer getHolder() {
        // Retrieve account via account id.
        Account myHolderAccount = theAccountManager.getAccount(theHolder);

        // Retrieve customer id via account.
        CustomerID myCustomerId = myHolderAccount.getCustomerId();

        return theCustomerManager.getCustomer(myCustomerId);
    }

    /**
     * Retrieves the recipient as a Customer.
     * @return the recipient as a Customer.
     */
    public Customer getRecipient() {
        // Retrieve account via account id.
        Account myHolderAccount = theAccountManager.getAccount(theRecipient);

        // Retrieve customer id via account.
        CustomerID myCustomerId = myHolderAccount.getCustomerId();

        return theCustomerManager.getCustomer(myCustomerId);
    }

    /**
     * Getter for balance.
     * @return the balance
     */
    public double getBalance() {
        return theBalance;
    }

    /**
     * Getter for interest rate.
     * @return the interest rate.
     */
    public double getInterestRate() {
        return theInterestRate;
    }

    /**
     * Credits the account with a given amount.
     * @param anAmount - the given amount
     * @return true if successful, otherwise false
     */
    public boolean credit(double anAmount) throws SQLException {
        return theAccountManager.transferMoney(theHolder, theRecipient, anAmount);
    }

    /**
     * Repays the loan with a given amount.
     * @param anAmount - the given amount
     * @return true if successful, otherwise false
     */
    public boolean repay(double anAmount) throws SQLException {
        return theAccountManager.transferMoney(theRecipient, theHolder, anAmount);
    }

}

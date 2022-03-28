package newbank.server.loans;

import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import newbank.server.accounts.AccountManager;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

public class Loan {

    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    private final AccountManager theAccountManager = AccountManager.getInstance();

    private final AccountID theHolder;
    private final AccountID theRecipient;
    private final double theInterestRate;
    private double theBalance;

    /**
     *
     * @param aHolder
     * @param aRecipient
     * @param anInterestRate
     * @param aBalance
     */
    public Loan(AccountID aHolder, AccountID aRecipient, double anInterestRate, double aBalance) {

        this.theHolder = aHolder;
        this.theRecipient = aRecipient;
        this.theInterestRate = anInterestRate;
        this.theBalance = aBalance;

        credit(aBalance);
    }

    /**
     *
     * @return
     */
    public Customer getHolder() {
        // Retrieve account via account id.
        Account myHolderAccount = theAccountManager.getAccount(theHolder);

        // Retrieve customer id via account.
        CustomerID myCustomerId = myHolderAccount.getCustomerId();

        return theCustomerManager.getCustomer(myCustomerId);
    }

    /**
     *
     * @return
     */
    public Customer getRecipient() {
        // Retrieve account via account id.
        Account myHolderAccount = theAccountManager.getAccount(theRecipient);

        // Retrieve customer id via account.
        CustomerID myCustomerId = myHolderAccount.getCustomerId();

        return theCustomerManager.getCustomer(myCustomerId);
    }

    /**
     *
     * @return
     */
    public double getBalance() {
        return theBalance;
    }

    /**
     *
     * @return
     */
    public double getInterestRate() {
        return theInterestRate;
    }

    /**
     *
     * @param anAmount
     * @return
     */
    public boolean credit(double anAmount) {
        return theAccountManager.transferMoney(theHolder, theRecipient, anAmount);
    }

    /**
     *
     * @param anAmount
     * @return
     */
    public boolean repay(double anAmount) {
        return theAccountManager.transferMoney(theRecipient, theHolder, anAmount);
    }

}

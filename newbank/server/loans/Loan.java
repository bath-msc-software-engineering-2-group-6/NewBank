package newbank.server.loans;

import newbank.server.accounts.AccountID;
import newbank.server.accounts.AccountManager;
import newbank.server.customers.Customer;

public class Loan {

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
        return null;
    }

    /**
     *
     * @return
     */
    public Customer getRecipient() {
        return null;
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
        return false;
    }

}

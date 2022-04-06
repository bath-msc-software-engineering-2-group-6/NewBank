package newbank.server.loans;

import newbank.server.accounts.AccountID;
import newbank.server.accounts.AccountManager;

public class LoanOffer {
    private final AccountManager theAccountManager = AccountManager.getInstance();
    private AccountID account;
    private double interestRate;
    private double balance;

    public LoanOffer(AccountID account, double interestRate, double balance){
        this.account = account;
        this.interestRate = interestRate;
        this.balance = balance;
    }

    public AccountID getAccount(){
        return this.account;
    }

    public double getInterestRate(){
        return this.interestRate;
    }

    public double getBalance(){
        return this.balance;
    }

    public String getSummary(){
        String summary = theAccountManager.getAccount(this.account).getAccountName() + "\t\t|";
        String interestR = Double.toString(this.interestRate);
        String stringBalance =Double.toString(this.balance);
        summary = summary.concat(interestR + "\t\t|");
        summary = summary.concat(stringBalance);

        return summary;
    }
}

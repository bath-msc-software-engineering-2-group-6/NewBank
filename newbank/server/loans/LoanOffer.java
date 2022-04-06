package newbank.server.loans;

import newbank.server.accounts.AccountID;

public class LoanOffer {
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
}

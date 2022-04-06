package newbank.server.loans;


import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;

import java.util.ArrayList;
import java.util.HashMap;

public class LoanMarket {

    public static LoanMarket theInstance;
    ArrayList<String> loans = new ArrayList<>();
    public HashMap<Integer, LoanOffer> loanMarket;
    private int loanNumber = 0;

    public static LoanMarket getInstance() {
        if (theInstance == null) {
            theInstance = new LoanMarket();
        }
        return theInstance;
    }

    public void addToMarket(AccountID aHolder, double anInterestRate, double aBalance){
        String interestRate = Double.toString(anInterestRate);
        String balance = Double.toString(aBalance);
        String loanDescription = aHolder.getAccountID() + " ";
        loanDescription = loanDescription.concat(interestRate) + " ";
        loanDescription = loanDescription.concat(balance);

        LoanOffer loanOffer = new LoanOffer(aHolder, anInterestRate, aBalance);
        loanMarket.put(loanNumber, loanOffer);
        loanNumber++;

        loans.add(loanDescription);
    }

    public String showLoans(){
        String loanTable = "Loan Marketplace \n";
        for (Integer loan: loanMarket.keySet()) {
            String key = loan.toString();
            String value = loanMarket.get(loan).toString();
            loanTable = loanTable.concat(key + " : " + value + "\n");
        }
        return loanTable;
    }

    public void acceptLoan(Account acc, int loanNum){
        Loan loan = new Loan(acc.getAccountId(), loanMarket.get(loanNum).getAccount(), loanMarket.get(loanNum).getInterestRate(), loanMarket.get(loanNum).getBalance());

        LoanVault.getInstance().activeLoans.put(loanNum, loan);
    }

// Not implemented yet
    public void removeLoan(int loanNumber){
        loanMarket.remove(loanNumber);
    }
}

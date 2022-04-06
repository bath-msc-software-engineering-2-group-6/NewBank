package newbank.server.loans;


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
    /*
    public void acceptLoan(int loanNumber){
        Loan loan = new Loan(loanMarket.get(loanNumber).getAccount(), loanMarket.get(loanNumber).getInterestRate(), loanMarket.get(loanNumber).getBalance());
    }
     */

    public String showLoans(){
        String loanTable = "Loan Marketplace \n";
        for (Integer loan: loanMarket.keySet()) {
            String key = loan.toString();
            String value = loanMarket.get(loan).toString();
            loanTable = loanTable.concat(key + " : " + value + "\n");
        }
        return loanTable;
    }

    public void removeLoan(){

    }
}

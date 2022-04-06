package newbank.server.loans;


import newbank.server.accounts.AccountID;

import java.util.ArrayList;

public class LoanMarket {
    public static LoanMarket theInstance;
    ArrayList<String> loans = new ArrayList<>();
    //private HashMap<Integer, loans> loanMarket;

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

        loans.add(loanDescription);
    }

    public void acceptLoan(){

    }

    public String showLoans(){
        String loanTable = "Loan Marketplace \n";
        int loanNumber = 0;
        for (String loan : loans){
            String loanNumberString = Integer.toString(loanNumber);
            loanTable = loanTable.concat(loanNumberString + loan + "\n");
            loanNumber++;
        }
        return loanTable;
    }

    public void removeLoan(){

    }
}

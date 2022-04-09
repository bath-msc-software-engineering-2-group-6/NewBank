package newbank.server.loans;


import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import newbank.server.customers.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoanMarket {

    public static LoanMarket theInstance;
    ArrayList<String> loans = new ArrayList<>();
    public HashMap<Integer, LoanOffer> loanMarket;
    private int loanNumber = 0;

    private LoanMarket(){
        loanMarket = new HashMap<>();
    }

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
        Integer loanInteger = loanNumber;
        this.loanMarket.put(loanInteger, loanOffer);
        loanNumber++;

        loans.add(loanDescription);
    }

    public String showLoans(){
        String loanTable = "Loan Marketplace \n";
        loanTable = loanTable.concat("Loan Number\t|AccountID\t|Interest\t|Balance\n");
        for (Integer loan: loanMarket.keySet()) {
            String key = loan.toString();
            LoanOffer value = loanMarket.get(loan);
            String valueString = value.getSummary();
            loanTable = loanTable.concat(key + "\t\t\t|" + valueString + "\n");
        }
        return loanTable;
    }

    public void acceptLoan(Customer customer, Account acc, Integer loanNum) throws SQLException {
        Loan loan = new Loan(customer.getCustomerId(), acc.getAccountId(), loanMarket.get(loanNum).getAccount(), loanMarket.get(loanNum).getInterestRate(), loanMarket.get(loanNum).getBalance());

        LoanVault.getInstance().activeLoans.put(loanNum, loan);
    }

// Not implemented yet
    public void removeLoan(Integer loanNumber){
        loanMarket.remove(loanNumber);
    }
}

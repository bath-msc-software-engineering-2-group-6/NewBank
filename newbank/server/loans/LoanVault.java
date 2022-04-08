package newbank.server.loans;

import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.util.HashMap;

public class LoanVault {
    private final CustomerManager theCustomerManager = CustomerManager.getInstance();
    public static LoanVault theInstance;
    public HashMap<Integer, Loan> activeLoans;

    private LoanVault(){
        activeLoans = new HashMap<>();
    }

    public static LoanVault getInstance() {
        if (theInstance == null) {
            theInstance = new LoanVault();
        }
        return theInstance;
    }

    public String showMyLoans(CustomerID customer){
        String loanTable = "My Loan \n";
        loanTable = loanTable.concat("Loan Number\t|AccountID\t|Interest\t|Balance\n");
        for (Integer loan: activeLoans.keySet()) {
            String key = loan.toString();
            String valueString = getSummary(customer);
            loanTable = loanTable.concat(key + "\t\t\t|" + valueString + "\n");
        }
        return loanTable;
    }

    public String getSummary(CustomerID customer){
        String summary = null;
        for (Integer loan : activeLoans.keySet()) {
            if (activeLoans.get(loan).getHolder().getCustomerId().equals(customer)) {
                summary = theCustomerManager.getCustomer(customer) + "\t\t|";
                String interestR = Double.toString(activeLoans.get(loan).getInterestRate());
                String stringBalance = Double.toString(activeLoans.get(loan).getBalance());
                summary = summary.concat(interestR + "\t\t|");
                summary = summary.concat(stringBalance);
            }
        }

        return summary;
    }
}

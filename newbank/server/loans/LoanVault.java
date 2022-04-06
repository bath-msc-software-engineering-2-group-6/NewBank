package newbank.server.loans;

import java.util.HashMap;

public class LoanVault {
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
}

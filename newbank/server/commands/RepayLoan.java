package newbank.server.commands;

import newbank.server.loans.LoanMarket;
import newbank.server.loans.LoanVault;

import java.util.ArrayList;

public class RepayLoan implements Command {
    private final LoanVault theLoanVault = LoanVault.getInstance();

    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {

        if (anArgsList.size() != 3) {

            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {
            String loanNumber = anArgsList.get(1);
            int repayAmount = Integer.parseInt(anArgsList.get(2));
            theLoanVault.getLoan(loanNumber).repay(repayAmount);

            if(theLoanVault.getLoan(loanNumber).getBalance() == 0){
                theLoanVault.activeLoans.remove(loanNumber);
            }

            return new CommandResponse("Success");
        }
    }

    public CommandResponse usage() {
        String myResponse = "REPAYLOAN Example usage:\n";
        myResponse = myResponse.concat("REPAYLOAN <Loan Number> <Repayment Amount>\n");

        return new CommandResponse(myResponse);
    }
}
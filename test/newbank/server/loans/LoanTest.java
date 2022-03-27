package newbank.server.loans;

import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import newbank.server.accounts.AccountManager;
import newbank.server.customers.Customer;
import newbank.server.customers.CustomerManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanTest {

    public CustomerManager theCustomerManager;
    public AccountManager theAccountManager;

    public Customer theHolder;
    public Customer theRecipient;

    public Loan theLoan;

    @BeforeEach
    void setUp() {
        theCustomerManager = CustomerManager.getInstance();
        theAccountManager = AccountManager.getInstance();

        theHolder = theCustomerManager.createCustomer("Holder");
        theRecipient = theCustomerManager.createCustomer("Recipient");

        theHolder.addAccount(theAccountManager.createAccount("Main", 100000));
        theRecipient.addAccount(theAccountManager.createAccount("Main", 0));

    }

    @Test
    void testRetrieveHolder() {
        AccountID myHolderAccountId = theHolder.findAccount("Main").getAccountId();
        AccountID myRecipientAccountId = theRecipient.findAccount("Main").getAccountId();

        theLoan = new Loan(myHolderAccountId, myRecipientAccountId, 5.0, 10000);
        Assertions.assertEquals(theHolder, theLoan.getHolder());
    }

    @Test
    void testRetrieveRecipient() {
        AccountID myHolderAccountId = theHolder.findAccount("Main").getAccountId();
        AccountID myRecipientAccountId = theRecipient.findAccount("Main").getAccountId();

        theLoan = new Loan(myHolderAccountId, myRecipientAccountId, 5.0, 10000);
        Assertions.assertEquals(theRecipient, theLoan.getRecipient());
    }

    @Test
    void testLoanCredit() {
        Account myHolderAccount = theHolder.findAccount("Main");
        Account myRecipientAccount = theRecipient.findAccount("Main");

        Assertions.assertEquals(100000, myHolderAccount.getBalance());
        Assertions.assertEquals(0, myRecipientAccount.getBalance());

        theLoan = new Loan(myHolderAccount.getAccountId(), myRecipientAccount.getAccountId(), 5.0, 10000);

        Assertions.assertEquals(90000, myHolderAccount.getBalance());
        Assertions.assertEquals(10000, myRecipientAccount.getBalance());

    }

    @Test
    void testLoanRepayment() {
        Account myHolderAccount = theHolder.findAccount("Main");
        Account myRecipientAccount = theRecipient.findAccount("Main");

        theLoan = new Loan(myHolderAccount.getAccountId(), myRecipientAccount.getAccountId(), 5.0, 10000);

        theLoan.repay(500);

        Assertions.assertEquals(90500, myHolderAccount.getBalance());
        Assertions.assertEquals(9500, myRecipientAccount.getBalance());
    }
}
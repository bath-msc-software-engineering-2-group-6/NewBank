package test.newbank.server.accounts;

import newbank.server.accounts.Account;
import newbank.server.accounts.AccountID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class AccountIDTest {
    private ArrayList<AccountID> theAccountIds;

    @BeforeEach
    void setUp() {
        theAccountIds = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Account myAccount = new Account(String.format("Test%d", i));
            theAccountIds.add(new AccountID(myAccount));
        }
    }

    @Test
    void fixedAccountIdLength() {
        int accountIdLength = 2 + 12;   // 2 for the NR characters, 12 for the number, including padding.

        for (int i = 0; i < theAccountIds.size(); i++) {
            Assertions.assertEquals(accountIdLength, theAccountIds.get(i).getAccountID().length());
        }
    }

    @Test
    void noIdenticalAccountIds() {
        // Test that no account IDs are the same.
        for (int i = 0; i < theAccountIds.size(); i++) {
            for (int j = i + 1; j < theAccountIds.size(); j++) {
                String firstAccount = theAccountIds.get(i).getAccountID();
                String secondAccount = theAccountIds.get(j).getAccountID();
                Assertions.assertFalse(firstAccount.equals(secondAccount));
            }
        }
    }
}
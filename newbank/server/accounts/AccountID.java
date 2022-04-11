package newbank.server.accounts;


import java.util.Random;

public class AccountID {
    private final String theAccountID;

    public AccountID(String id) {
        theAccountID = id;
    }

    public AccountID() {
        Random myRandom = new Random();
        int myAccountId = myRandom.nextInt(10000) + 1;
        theAccountID = String.format("NR%012d", myAccountId);
    }

    public String getAccountID() {
        return theAccountID;
    }
}

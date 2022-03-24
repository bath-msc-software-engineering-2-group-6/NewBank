package newbank.server.accounts;

public class AccountID {
    private final String theAccountID;

    public AccountID(Account anAccount) {
        // Use hash code of account to generate bank acount number.
        int myHashCode = anAccount.hashCode();
        theAccountID = String.format("NR%012d", myHashCode);
    }

    public String getAccountID() {
        return theAccountID;
    }
}

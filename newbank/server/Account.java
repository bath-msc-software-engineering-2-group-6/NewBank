package newbank.server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	public static int DEFAULT_OPENING_BALANCE = 0;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}
	
	public String toString() {
		return (accountName + ": " + openingBalance);
	}

	public String getAccountName() {
		return  accountName;
	}
}

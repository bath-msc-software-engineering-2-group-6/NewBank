package newbank.server.accounts;

public class Account {
	public static double DEFAULT_OPENING_BALANCE = 0.0;

	private final String accountName;
	private double balance;

	public Account(String anAccountName, double aBalance) {
		this.accountName = anAccountName;
		this.balance = aBalance;
	}

	public Account(String anAccountName) {
		this.accountName = anAccountName;
		this.balance = DEFAULT_OPENING_BALANCE;
	}

	public String toString() {
		return (accountName + ": " + balance);
	}

	public String getAccountName() {
		return  accountName;
	}

	public double getBalance() {
		return balance;
	}

	public void credit (double credit) {
		balance += credit;
	}

	public void debit (double debit) {
		if (balance > debit){
			balance -= debit;
		} else {
			System.out.println("Insufficient funds.\n");
		}
	}
}

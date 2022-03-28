package newbank.server.accounts;

import newbank.server.customers.CustomerID;

public class Account {
	public static double DEFAULT_OPENING_BALANCE = 0.0;

	private final CustomerID theCustomerId;
	private final AccountID theAccountId;
	private final String accountName;
	private double balance;

	public Account(CustomerID aCustomerId, AccountID anAccountId, String anAccountName, double aBalance) {
		this.theCustomerId = aCustomerId;
		this.theAccountId = anAccountId;
		this.accountName = anAccountName;
		this.balance = aBalance;
	}

	public Account(CustomerID aCustomerId, AccountID anAccountId, String anAccountName) {
		this.theCustomerId = aCustomerId;
		this.theAccountId = anAccountId;
		this.accountName = anAccountName;
		this.balance = DEFAULT_OPENING_BALANCE;
	}

	@Override
	public String toString() {
		String string = " " + accountName;
		int len = accountName.length();
		int space = 12 - len;
		for (int i=0; i<space; i++){
			string+= " ";
		}
		String formattedBalance = String.format("%.1f", balance);
		string = string + "| " + formattedBalance + "\n";
		return string;
	}

	public CustomerID getCustomerId() {
		return theCustomerId;
	}

	public AccountID getAccountId() {
		return theAccountId;
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

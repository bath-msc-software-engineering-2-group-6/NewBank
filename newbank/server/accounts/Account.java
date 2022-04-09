package newbank.server.accounts;

import newbank.server.customers.CustomerID;

import java.sql.SQLException;

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

	public void saveBalance(double newBalance) throws SQLException {
		balance = newBalance;
		(new AccountModel(this)).updateDb();
	}

	public void credit (double credit) throws SQLException {
		double newBalance = balance + credit;
		saveBalance(newBalance);
	}

	public void debit (double debit) throws SQLException {
		double newBalance;
		if (balance > debit){
			newBalance = balance - debit;
			saveBalance(newBalance);
		} else {
			System.out.println("Insufficient funds.\n");
		}
	}
}

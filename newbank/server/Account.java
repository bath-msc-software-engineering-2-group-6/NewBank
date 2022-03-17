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

	public double getBalance() {
		return openingBalance;
	}

	public void credit (double credit){
		openingBalance=openingBalance+credit;
	}

	public void debit (double debit){
		if(openingBalance>debit){
			openingBalance=openingBalance-debit;
		} else {
			System.out.println("Insufficient funds.\n");
		}
	}

	}

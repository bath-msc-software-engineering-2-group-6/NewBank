package newbank.server.commands;

import com.google.zxing.WriterException;
import newbank.server.authentication.Authenticator;
import newbank.server.authentication.QRGen;
import newbank.server.customers.CustomerID;
import newbank.server.customers.CustomerManager;

import java.io.IOException;
import java.util.ArrayList;

public class SetupAuthenticator implements Command {
    private final Authenticator theAuthenticator = Authenticator.getInstance();
    private final CustomerManager theCustomerManager = CustomerManager.getInstance();

    public CommandResponse process(ArrayList<String> anArgsList) throws CommandException {
        if (anArgsList.size() != 1) {
            String myException = "Invalid arguments, anArgsList contains: ";
            myException = myException.concat(anArgsList.toString());
            throw new CommandException(myException);
        } else {
            String myResponse = "";
            CustomerID customerID = new CustomerID(anArgsList.get(0));
            String secretKey = theAuthenticator.generateSecretKey();
            theAuthenticator.addAuthentication(customerID, secretKey);
            String barCode = QRGen.getGoogleAuthenticatorBarCode(secretKey, customerID.getKey(), "NewBankSys");
            try {
                QRGen.createQRCode(barCode, "D:\\CompSc\\SE2\\qr.png", 200,200);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myResponse = "Your key is " + secretKey;
            myResponse = myResponse.concat(". Please enter this into Google Authenticator to complete setup");

            return new CommandResponse(myResponse);
        }
    };

    public CommandResponse usage() {
        return new CommandResponse("Type \"2FA\" to setup 2-Factor-Authentication");
    }
}

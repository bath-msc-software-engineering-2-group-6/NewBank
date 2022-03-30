package newbank.server.authentication;

import de.taimos.totp.TOTP;
import newbank.server.customers.CustomerID;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import java.security.SecureRandom;
import java.util.HashMap;

public class Authenticator {
    public static Authenticator theInstance;
    private HashMap<CustomerID, String> authenticatorKeys;

    private Authenticator() {
        authenticatorKeys = new HashMap<>();
    }

    public static Authenticator getInstance() {
        if (theInstance == null) {
            theInstance = new Authenticator();
        }

        return theInstance;
    }

    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public static void main(String args[]){
        String secretKey = "KAPDQD3TPYRYNJYEGQA3RPZQ6RXM6JMN";
        String lastCode = null;
        while (true) {
            String code = getTOTPCode(secretKey);
            if (!code.equals(lastCode)) {
                System.out.println(code);
            }
            lastCode = code;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {};
        }
    }

    public void addAuthentication(CustomerID customerID, String secretKey){
        authenticatorKeys.put(customerID, secretKey);
    }
    public String getSecretKey(CustomerID customerID){
        return authenticatorKeys.get(customerID);
    }
}

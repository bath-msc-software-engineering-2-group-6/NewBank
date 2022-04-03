package newbank.server.authentication;

import de.taimos.totp.TOTP;
import newbank.server.customers.CustomerID;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import java.security.SecureRandom;
import java.util.HashMap;

public class Authenticator {
    public static Authenticator theInstance;
    public HashMap<String, String> authenticatorKeys;
    public String code;

    private Authenticator() {
        authenticatorKeys = new HashMap<>();
    }

    public static Authenticator getInstance() {
        if (theInstance == null) {
            theInstance = new Authenticator();
        }

        return theInstance;
    }

    public String generateSecretKey() {
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

    public void getCode(String key){
        code = getTOTPCode(key);

    }

    public void addAuthentication(String name, String secretKey){
        authenticatorKeys.put(name, secretKey);
    }

    public String getSecretKey(CustomerID customerID){
        return authenticatorKeys.get(customerID);
    }

    public boolean containsKey(CustomerID customerID){
        return authenticatorKeys.containsKey(customerID);
    }
}

package newbank.server.customers;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordEncryption {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static PasswordEncryption theInstance;
    private final String theSalt;

    /**
     * PasswordEncryption class manages the encryption of passwords.
     * Stores the salt locally so all passwords are created using the same salt.
     */
    private PasswordEncryption() {
        // Instantiate the objects.
        SecureRandom myRandom = new SecureRandom();
        StringBuilder mySalt = new StringBuilder(30);

        // Create a salt by randomly retrieving characters from the alphabet.
        for (int i = 0; i < mySalt.capacity(); i++) {
            mySalt.append(ALPHABET.charAt(myRandom.nextInt(ALPHABET.length())));
        }

        // Store generated salt.
        this.theSalt = mySalt.toString();
    }

    /**
     * Retrive an instance of PasswordEncryption.
     * @return an instance of PasswordEncryption
     */
    public static PasswordEncryption getInstance() {
        if (theInstance == null) {
            theInstance = new PasswordEncryption();
        }

        return theInstance;
    }

    /**
     * Hashes a given password.
     * @param aPassword - the given password
     * @return the hashed password
     */
    public String hashPassword(String aPassword) {
        // Create our key spec.
        PBEKeySpec mySpec = new PBEKeySpec(aPassword.toCharArray(), theSalt.getBytes(), 10000, 256);

        try {
            // Create our key factory.
            SecretKeyFactory mySkf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            // Encode the password.
            byte[] myEncodedPassword = mySkf.generateSecret(mySpec).getEncoded();
            // Convert it to a string.
            String myHashedPassword = Base64.getEncoder().encodeToString(myEncodedPassword);

            return myHashedPassword;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Unable to hash password.");
        } finally {
            mySpec.clearPassword();
        }

        // If we made it here, there's an error.
        return "";
    }

    /**
     * Verifies whether the user entered plain-text password matches the hashed password.
     * @param aPassword - the user entered plain-text password
     * @param aHashedPassword - the hashed password
     * @return true if they match, otherwise false
     */
    public boolean verifyPassword(String aPassword, String aHashedPassword) {
        // Generate another password locally.
        String myLocalHashedPassword = hashPassword(aPassword);

        // Verify whether they match.
        return myLocalHashedPassword.equals(aHashedPassword);
    }
}

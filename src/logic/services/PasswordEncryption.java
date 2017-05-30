package logic.services;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//
public class PasswordEncryption {
    private String salt;

    //Creates a Salt
    public PasswordEncryption() {
        this.salt = "NaCl";
    }
    //Hashes password with a 512 bit Secure Hashing Algorithm
    //kode lånt herfra https://stackoverflow.com/questions/33085493/hash-a-password-with-sha-512-in-java
    public String getHashedPassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    //lånt kode stop
}

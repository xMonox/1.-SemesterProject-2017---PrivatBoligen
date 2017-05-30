package logic.services;


public class PasswordService {
    //Bertram
    private PasswordEncryption pwe;

    //Constructor with PasswordEncryption dependency
    public PasswordService(PasswordEncryption pwe) {
        this.pwe = pwe;
    }

    //Takes a password as a String, sends it to PasswordEncryption and returns it.
    public String getHashedPassword(String password) {
        return pwe.getHashedPassword(password);
    }
}

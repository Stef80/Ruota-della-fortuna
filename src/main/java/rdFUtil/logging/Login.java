package rdFUtil.logging;

import java.io.Serializable;

public class Login implements Serializable {
    public static final long serialVersionUID = 1L;

    protected CryptedPassword passwordC;
    protected String email;

    public Login(String password, String mail){
        passwordC = CryptedPassword.encrypt(password);
        email = mail;
    }
}

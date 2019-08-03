package rdFUtil.logging;

import java.io.Serializable;

public class CryptedPassword implements Serializable {

    public static final long serialVersionUID = 1L;
    private byte[] cPassword;

    public static CryptedPassword encrypt(String pwd){
        return null;
    }

    public static String decrypt(CryptedPassword pwd){
        return null;
    }

    private CryptedPassword(){

    }

}

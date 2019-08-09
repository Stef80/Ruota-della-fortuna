import rdFUtil.logging.CryptPassword;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestPassword {
    private static MessageDigest md;

    public static String cryptWithMD5(String pass) {
        try {
            md = MessageDigest.getInstance("SHA-512");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TestPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        String s = CryptPassword.encrypt("ciao");
        if (s.equals("a0c299b71a9e59d5ebb07917e761a357aa13e99a7bb65a58e780ec9077b1902d1dedb31b1457beda595fe4d71d779b6ca9cad476266cc07590e31d84b26"))
            System.out.println("ok");
        else
            System.out.println("no");
        System.out.println(s);
    }
}

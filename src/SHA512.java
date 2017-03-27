import java.io.*;
import java.security.MessageDigest;
import java.security.*;

/**
 * Created by Clément on 27/03/2017.
 */
public class SHA512
{
    private String pass;
    public SHA512(String password)
    {
        pass = generateHash(password);
    }
    public String getPass()
    {
        return pass;
    }
    public String generateHash(String password)
    { // génére mdp hashé en SHA-512
        MessageDigest md = null;
        byte[] hash = null;
        try
        {
            md = MessageDigest.getInstance("SHA-512"); // remplacer SHA-512 par le cryptage quon veut(SHA-256, SHA1 ect)
            hash = md.digest(password.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return convertToHex(hash);
    }

    private String convertToHex(byte[] raw)
    {//convertis le mdp en hexa à la fin du cryptage en SHA-521
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < raw.length; i++)
            sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1)); // convertion en hexa pour chaque byte du mdp
        String res=sb.toString(); // cast le byte[] en string
        res=res+"c9l6g9b5";
        return res;
    }
}
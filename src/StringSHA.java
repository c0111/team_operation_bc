

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class StringSHA {

    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //Implement sha256
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            //Hex Hash
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
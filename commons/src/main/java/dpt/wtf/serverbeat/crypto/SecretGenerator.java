package dpt.wtf.serverbeat.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecretGenerator {

    public static String generate(String serverName, String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            String input = serverName + salt;
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

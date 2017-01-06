package util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHashing {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    public static String getSaltedHash(String data) {
        byte[] salt = new byte[0];
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return ENCODER.encodeToString(salt) + "$" + hash(data, salt);
    }

    public static boolean check(String s1, String s2) {
        String[] saltAndPass = s2.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored data has the form 'salt$hash'");
        }
        String hashOfInput = hash(s1, DECODER.decode(saltAndPass[0]));

        return hashOfInput.equals(saltAndPass[1]);
    }

    private static String hash(String data, byte[] salt)  {
        if (data == null || data.isEmpty())
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f;
        SecretKey key = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = f.generateSecret(new PBEKeySpec(
                    data.toCharArray(), salt, 2000, 128)
            );
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return ENCODER.encodeToString(key.getEncoded());
    }
}

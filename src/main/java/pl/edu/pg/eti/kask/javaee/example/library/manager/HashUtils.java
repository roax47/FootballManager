package pl.edu.pg.eti.kask.javaee.example.library.manager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utils for hashing.
 *
 * @author psysiu
 */
public class HashUtils {

    /**
     * Returns SHA-256 hash of the provided text value.
     *
     * @param original text value to be hashed
     * @return SHA-256 hash value
     */
    public static String sha256(String original) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(original.getBytes("UTF-8"));
            return String.format("%064x", new java.math.BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

}

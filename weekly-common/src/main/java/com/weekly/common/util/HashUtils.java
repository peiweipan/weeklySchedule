package com.weekly.common.util;

import com.firefly.errcode.exception.SystemException;
import org.apache.commons.codec.binary.Hex;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashUtils {

    private static final String HEX_STRING = "0123456789abcdef";

    private static final String WORD_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-";

    private HashUtils() { }

    public static String bcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean bcryptCheck(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }

    public static String sha256(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] cipherBytes = messageDigest.digest(password.getBytes());

            return Hex.encodeHexString(cipherBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException("Algorithm SHA-256 does not exists");
        }
    }

    public static String randomString() {
        return randomString(10);
    }

    public static String randomString(int length) {
        char[] chars = new char[length];
        int bound = WORD_STRING.length();

        Random random = new Random();

        for (int i = 0;i < length; ++i) {
            chars[i] = WORD_STRING.charAt(random.nextInt(bound));
        }

        return new String(chars);
    }
}

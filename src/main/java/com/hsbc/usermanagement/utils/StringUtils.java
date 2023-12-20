package com.hsbc.usermanagement.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.UUID;

public class StringUtils {

    public static String generateRandomString() {

        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static String getHash(String password) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] hash = md5.digest(password.getBytes());

        String hashedPassword = new String(hash);
        return hashedPassword;

    }
}

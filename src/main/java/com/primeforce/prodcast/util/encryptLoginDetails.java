package com.primeforce.prodcast.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

public class encryptLoginDetails {

    public String encrypt(String message) {
        try {
            final MessageDigest md = MessageDigest.getInstance("md5");
            final byte[] digestOfPassword = md.digest("HG58YZ3CR9"
                    .getBytes("utf-8"));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }

            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            final byte[] plainTextBytes = message.getBytes("utf-8");
            final byte[] cipherText = cipher.doFinal(plainTextBytes);
           final String encodedCipherText = new sun.misc.BASE64Encoder().encode(cipherText);

            return encodedCipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

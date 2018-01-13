package com.primeforce.prodcast.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

public class decryptCookie {
    public String decrypt(String message) {
        try
        {
            final MessageDigest md = MessageDigest.getInstance("md5");
            final byte[] digestOfPassword = md.digest("HG58YZ3CR9"
                    .getBytes("utf-8"));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }

            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, key, iv);

             final byte[] decodedText = new sun.misc.BASE64Decoder().decodeBuffer(message);
            final byte[] plainText = decipher.doFinal(decodedText);

            return new String(plainText, "UTF-8");



        }
         catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }


    }
}

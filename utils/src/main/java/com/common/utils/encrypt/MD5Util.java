package com.common.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wuyq on 14-2-17.
 */
public class MD5Util {
    public static String encrypt(String plainText) {
        return encrypt(plainText, null);
    }

    public static String encrypt(String plainText, String salt) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = plainText.getBytes();
            md.update(bytes);
            if (salt != null) {
                md.update(salt.getBytes());
            }
            byte b[] = md.digest();

            int i;

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}

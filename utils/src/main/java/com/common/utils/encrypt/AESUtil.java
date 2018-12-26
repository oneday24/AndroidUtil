package com.common.utils.encrypt;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wuyq on 14-2-15.
 */
public class AESUtil {
    private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    /**
     * 加密(AES+BASE64)
     *
     * @param content  需要加密的内容
     * @param password 秘钥
     * @return 加密后String字符串
     */
    public static String encrypt(String content, String password) {
        try {
            byte[] enCodeFormat = getKeyByteFromPassword(password);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] byteContent = content.getBytes("utf-8");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encodeToString(result, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密(AES+BASE64)
     *
     * @param content  需要解密的内容
     * @param password 秘钥
     * @return 解密后String字符串
     */
    public static String decrypt(String content, String password) {
        byte[] b = Base64.decode(content, Base64.NO_WRAP);
        byte[] result = decrypt(b, password);
        return result != null ? new String(result) : null;
    }

    private static byte[] decrypt(byte[] content, String password) {
        try {
            byte[] enCodeFormat = getKeyByteFromPassword(password);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getKeyByteFromPassword(String password) {
        String hexString = MD5Util.encrypt(password);
        return hexStringToByteArray(hexString);
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}

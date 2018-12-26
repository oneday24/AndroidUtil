package com.common.utils.encrypt;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA
 * User: Alan
 * Date: 2017/10/25
 */
public class DesUtils {

    public static final String ALGORITHM = "DES/ECB/PKCS5Padding";

    private static final String KEY_ALGORITHM = "DES";

    private final static String CHARSET = "utf-8";

    public static String encrypt(String key, String plainText) {
        byte[] keyBytes = Base64.decode(key, Base64.DEFAULT);
        byte[] textBytes = plainText.getBytes(Charset.forName("UTF-8"));
        byte[] encryptedBytes = encrypt(keyBytes, textBytes);
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }


    public static String decrypt(String key, String encryptedText) {
        byte[] keyBytes = Base64.decode(key, Base64.DEFAULT);
        byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] textBytes = decrypt(keyBytes, encryptedBytes);

        return new String(textBytes, Charset.forName("UTF-8"));
    }


    public static byte[] encrypt(byte[] key, byte[] src) {
        return security(key, src, Cipher.ENCRYPT_MODE);
    }

    public static byte[] decrypt(byte[] key, byte[] src) {
        return security(key, src, Cipher.DECRYPT_MODE);
    }

    private static byte[] security(byte[] key, byte[] src, int cipherType) {
        try {
            // 生成密钥
//            SecretKey securityKey = new SecretKeySpec(key, KEY_ALGORITHM);
            SecretKey securityKey = SecretKeyFactory.getInstance(KEY_ALGORITHM).generateSecret(new DESKeySpec(key));

            // 算法
            Cipher c1 = Cipher.getInstance(ALGORITHM);

            // 加密或解密
            c1.init(cipherType, securityKey);
            return c1.doFinal(src);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getKey() {
        return Base64.encodeToString(getKeyBytes(), Base64.DEFAULT);
    }

    public static byte[] getKeyBytes() {
        //实例化密钥生成器
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        //初始化密钥生成器
        kg.init(56);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获取二进制密钥编码形式
        byte[] keyBytes = secretKey.getEncoded();
        if (keyBytes.length != 8) {
            throw new IllegalArgumentException("key length is invaid!");
        }
        return keyBytes;
    }

    public static void main(String[] args) throws Exception {
        String key = getKey();
        System.out.println(key);

        String plainText = "Hello World!";

        String encryptedText = encrypt(key, plainText);
        String text = decrypt(key, encryptedText);

        System.out.println(text);
    }

}

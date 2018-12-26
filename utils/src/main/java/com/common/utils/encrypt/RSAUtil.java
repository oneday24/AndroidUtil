package com.common.utils.encrypt;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by zengjing on 16/3/28.
 */
public class RSAUtil {


    public static String encrypt(String content, String password) {
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//这里只能是RSA/ECB/PKCS1Padding

            PublicKey publicKey = stringToPublicKey(password);

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(content.getBytes());
            return Base64.encodeToString(result, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String sign(String privateKey, String content) {
        return sign(privateKey, content, "UTF-8");
    }

    public static String sign(String privateKey, String content, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey, Base64.DEFAULT));
            KeyFactory key = KeyFactory.getInstance("RSA");
            PrivateKey priKey = key.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return Base64.encodeToString(signed, Base64.DEFAULT);
        } catch (Exception var8) {
            throw new IllegalArgumentException(var8);
        }
    }

    private static PublicKey stringToPublicKey(String keyStr){

        byte[] publicBytes = Base64.decode(keyStr, Base64.NO_WRAP);
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");//这里只能是RSA
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;

    }

}

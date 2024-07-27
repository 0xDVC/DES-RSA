package com.dvc.des_gui.des.core;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class DES {

    private SecretKey myDesKey;

    public DES() throws NoSuchAlgorithmException {
        generateKey();
    }

    private void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        myDesKey = keyGenerator.generateKey();
    }

    public byte[] encrypt(String plainText) throws Exception {
        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
        return desCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public byte[] decrypt(byte[] encryptedData) throws Exception {
        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
        return desCipher.doFinal(encryptedData);
    }

    public String getKey() {
        return HexFormat.of().formatHex(myDesKey.getEncoded());
    }

    public void setKey(String key) {
        byte[] keyBytes = HexFormat.of().parseHex(key);
        myDesKey = new SecretKeySpec(keyBytes, "DES");
    }
}

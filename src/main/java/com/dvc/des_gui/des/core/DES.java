package com.dvc.des_gui.des.core;

import com.dvc.des_gui.des.core.exception.DecryptionException;
import com.dvc.des_gui.des.core.exception.EncryptionException;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;

public class DES {
    private static final String ALGORITHM = "DES";

    public static String encrypt(String plaintext, String key) throws EncryptionException {
        try {
            byte[] plaintextBytes = plaintext.getBytes();
            byte[] ciphertextBytes = encrypt(plaintextBytes, key);
            return Base64.getEncoder().encodeToString(ciphertextBytes);
        } catch (Exception e) {
            throw new EncryptionException("Error encrypting data", e);
        }
    }

    public static String decrypt(String ciphertext, String key) throws DecryptionException {
        try {
            byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
            byte[] plaintextBytes = decrypt(ciphertextBytes, key);
            return new String(plaintextBytes);
        } catch (Exception e) {
            throw new DecryptionException("Error decrypting data", e);
        }
    }

    private static byte[] encrypt(byte[] plaintextBytes, String key) throws Exception {
        SecretKey desKey = getSymmetricKey(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        return cipher.doFinal(plaintextBytes);
    }

    private static byte[] decrypt(byte[] ciphertextBytes, String key) throws Exception {
        SecretKey desKey = getSymmetricKey(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        return cipher.doFinal(ciphertextBytes);
    }

    private static SecretKey getSymmetricKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        return SecretKeyFactory.getInstance(ALGORITHM)
                .generateSecret(new DESKeySpec(key.getBytes()));
    }
}

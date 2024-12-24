package org.example.api;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesCipher {
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密方法
     *
     * @param plaintext 明文
     * @param key       密钥
     * @return          密文
     * @throws Exception 加密过程中的异常
     */
    public static String encrypt(String plaintext, String key) throws Exception {
        System.out.println(key);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("Lzz@2023.","ravenraveenraven"));
    }
    /**
     * 解密方法
     *
     * @param ciphertext 密文
     * @param key        密钥
     * @return           明文
     * @throws Exception 解密过程中的异常
     */
    public static String decrypt(String ciphertext, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decodedCiphertext = Base64.getDecoder().decode(ciphertext);
        byte[] decryptedBytes = cipher.doFinal(decodedCiphertext);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}

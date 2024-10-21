package com.example.demo.Utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class UserUtills {
	PublicKey publicKey;
    PrivateKey privateKey;

	public UserUtills() {
        KeyPair keyPair = null;
		try {
			keyPair = generateKeyPair();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
	}
	
	public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Расшифровка
    public  String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

	
    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);  // Размер ключа: 2048 бит
        return keyPairGenerator.generateKeyPair();
    }
}

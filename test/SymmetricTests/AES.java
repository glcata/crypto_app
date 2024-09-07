package SymmetricTests;

import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.junit.Assert.*;


public class AES {

    @Test
    public void generateARandomAesKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        assertEquals("AES", key.getAlgorithm());
        assertEquals(32, key.getEncoded().length);
        System.out.println("AES KEY : "+key.getEncoded().length + key.toString());
    }

    @Test
    public void encryptAMessageWithAes() throws Exception {
        String message = "Alice knows Bob's secret.";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        SecureRandom random = new SecureRandom();
        byte[] buffer = new byte[16];
        random.nextBytes(buffer);
        IvParameterSpec iv = new IvParameterSpec(buffer);

        byte[] cipertext = encryptWithAes(message, key, iv);
        System.out.println(Arrays.toString(cipertext));
        String actualMessage = decryptWithAes(cipertext, key, iv);
        System.out.println(actualMessage);

        assertEquals(message, actualMessage);
    }

    private byte[] encryptWithAes(String message, SecretKey key, IvParameterSpec iv)
            throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, key, iv);
        CipherOutputStream cipherOut = new CipherOutputStream(out, aes);
        OutputStreamWriter writer = new OutputStreamWriter(cipherOut);

        try {
            writer.write(message);
        } finally {
            writer.close();
        }

        return out.toByteArray();
    }

    private String decryptWithAes(byte[] cipertext, SecretKey key, IvParameterSpec iv)
            throws Exception {

        ByteArrayInputStream in = new ByteArrayInputStream(cipertext);
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, key, iv);
        CipherInputStream cipherIn = new CipherInputStream(in, aes);
        InputStreamReader reader = new InputStreamReader(cipherIn);
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            return bufferedReader.readLine();
        } finally {
            bufferedReader.close();
        }
    }

    /*
	Every implementation of the Java platform is required to support the following standard Cipher transformations with the keysizes in parentheses:
	AES/CBC/NoPadding (128)
	AES/CBC/PKCS5Padding (128)
	AES/ECB/NoPadding (128)
	AES/ECB/PKCS5Padding (128)
	DES/CBC/NoPadding (56)
	DES/CBC/PKCS5Padding (56)
	DES/ECB/NoPadding (56)
	DES/ECB/PKCS5Padding (56)
	DESede/CBC/NoPadding (168)
	DESede/CBC/PKCS5Padding (168)
	DESede/ECB/NoPadding (168)
	DESede/ECB/PKCS5Padding (168)
	RSA/ECB/PKCS1Padding (1024, 2048)
	RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
	RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
     */
}

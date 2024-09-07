package AsymmetricTests;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;

import static org.junit.Assert.*;

public class RSA {

    @Test
    public void generateAnRsaKeyPair() throws Exception {
        KeyPair keyPair = generateRsaKey();

        assertEquals("RSA", keyPair.getPublic().getAlgorithm());
        assertTrue(keyPair.getPublic().getEncoded().length > 2048 / 8);
        assertTrue(keyPair.getPrivate().getEncoded().length > 2048 / 8);
    }

    @Test
    public void encryptASymmetricKey() throws Exception {
        KeyPair keyPair = generateRsaKey();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();

        byte[] encryptedKey = encryptWithRsa(publicKey, key);
        byte[] decryptedKey = decryptWithRsa(privateKey, encryptedKey);

        assertArrayEquals(key.getEncoded(), decryptedKey);
    }

    private byte[] encryptWithRsa(PublicKey publicKey, SecretKey key) throws Exception {

        Cipher rsa = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        rsa.init(Cipher.ENCRYPT_MODE, publicKey);
        return rsa.doFinal(key.getEncoded());
    }

    private byte[] decryptWithRsa(PrivateKey privateKey, byte[] encryptedKey) throws Exception {

        Cipher rsa = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        rsa.init(Cipher.DECRYPT_MODE, privateKey);
        return rsa.doFinal(encryptedKey);
    }
    @Test
    public void signAMessage() throws Exception {
        KeyPair keyPair = generateRsaKey();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String message = "Alice knows Bob's secret.";
        byte[] messageBytes = message.getBytes();

        byte[] signatureBytes = signMessage(privateKey, messageBytes);
        boolean verified = verifySignature(publicKey, messageBytes, signatureBytes);

        assertTrue(verified);
    }

    private byte[] signMessage(PrivateKey privateKey, byte[] messageBytes) throws Exception {

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(messageBytes);
        return signature.sign();
    }

    private boolean verifySignature(PublicKey publicKey, byte[] messageBytes, byte[] signatureBytes) throws Exception {

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(messageBytes);
        return signature.verify(signatureBytes);
    }

    private KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
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

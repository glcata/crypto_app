// * @author Catalin Glavan
package crypto.encrypt;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.cert.X509Certificate;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class test_AES_OLD {

    public static void main(String[] args) throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Catalin Glavan\\Desktop\\server_certificate.crt"));) {
            String sCurrentLine = null;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inStream = new FileInputStream("C:\\Users\\Catalin Glavan\\Desktop\\server_certificate.crt");
        X509Certificate cert = X509Certificate.getInstance(inStream);
        System.out.println(DatatypeConverter.printBase64Binary(cert.getPublicKey().getEncoded()));
        inStream.close();
        byte[] data = cert.getPublicKey().getEncoded();
        byte[] keys = new byte[32];

        for (int x = 113; x <= 144; x++) {
            keys[x - 113] = data[x];
        }
        SecretKeySpec key = new SecretKeySpec(keys, "AES");

        System.out.println(DatatypeConverter.printBase64Binary(key.getEncoded()));

        SecureRandom random = new SecureRandom();
        byte[] buffer = new byte[16];
        random.nextBytes(buffer);
        IvParameterSpec iv = new IvParameterSpec(buffer);

//        byte[] ciphertext = encryptWithAes("ALABALA PORTOCALA", key, iv);
//        FileOutputStream fos = new FileOutputStream("C:\\Users\\Catalin Glavan\\Desktop\\encrypted.enc");
//        fos.write(ciphertext);
//        fos.close();

        Path path = Paths.get("C:\\Users\\Catalin Glavan\\Desktop\\encrypted.enc");
        byte[] in = Files.readAllBytes(path);
        System.out.println(decryptWithAes(in, key, iv));

    }

    private static byte[] encryptWithAes(String message, SecretKey key, IvParameterSpec iv) throws Exception {

        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, key, iv);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CipherOutputStream cipherOut = new CipherOutputStream(out, aes);

        try (OutputStreamWriter writer = new OutputStreamWriter(cipherOut)) {
            writer.write(message);
        }

        return out.toByteArray();
    }

    private static String decryptWithAes(byte[] cipertext, SecretKey key, IvParameterSpec iv) throws Exception {

        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, key, iv);

        ByteArrayInputStream in = new ByteArrayInputStream(cipertext);
        CipherInputStream cipherIn = new CipherInputStream(in, aes);
        InputStreamReader reader = new InputStreamReader(cipherIn);

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            return bufferedReader.readLine();
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

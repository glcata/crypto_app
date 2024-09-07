// * @author Catalin Glavan
package crypto.encrypt;

import org.bouncycastle.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.cert.CertificateException;
import javax.security.cert.CertificateExpiredException;
import javax.security.cert.CertificateNotYetValidException;
import javax.security.cert.X509Certificate;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class encrypt_old {

    private String encryption;
    private String CERT;
    private String default_store;

    public encrypt_old(String cert_PATH) {
        try {
            this.CERT = cert_PATH;
            default_store = System.getProperty("user.dir") + "\\src\\crypto\\sql\\store";

            encryption = DatatypeConverter.printBase64Binary(algorithm_crypto(CERT));
        } catch (CertificateException ex) {
            Logger.getLogger(encrypt_old.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public final byte [] algorithm_crypto(String cert_path) throws CertificateException {

        this.CERT = cert_path;
        try {
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(default_store + ".enc"));
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(default_store + ".txt"));

            X509Certificate cert;
            try (InputStream inStream = new FileInputStream(CERT)) {
                cert = X509Certificate.getInstance(inStream);
                cert.checkValidity();
            }
            encryptDecrypt(false, is, os, "*****", cert.getPublicKey());
            return cert.getPublicKey().getEncoded();
        } catch (IOException | CertificateExpiredException | CertificateNotYetValidException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void encryptDecrypt(Boolean encrypt_decrypt, InputStream in, OutputStream out, String password, PublicKey key) {
        try {
            byte[] key256 = getSHA256(key.getEncoded());
            byte[] iv = getSHA1_16(password);

            SecretKeySpec keySpec = new SecretKeySpec(key256, "AES");
            IvParameterSpec IvSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if (encrypt_decrypt) {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, IvSpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, keySpec, IvSpec);
            }
            out = new CipherOutputStream(out, cipher);
            int numRead = 0;
            byte[] buf = new byte[1024];
            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);
            }
            out.close();

        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getSHA256(byte[] input) {
        if (input != null) {
            try {
                MessageDigest hasher = MessageDigest.getInstance("SHA-256");
                return hasher.digest(input);
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private static byte[] getSHA1_16(String input) {
        byte[] sha1 = null;
        if (input != null) {
            try {
                MessageDigest hasher = MessageDigest.getInstance("SHA-1");
                sha1 = hasher.digest(input.getBytes());
                sha1 = Arrays.copyOfRange(sha1, 0, 16);
                return sha1;

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        System.out.println(DatatypeConverter.printBase64Binary(sha1));
        return null;
    }

    private static byte[] getSHA1(String input) {
        byte[] sha1 = null;
        if (input != null) {
            try {
                MessageDigest hasher = MessageDigest.getInstance("SHA-1");
                sha1 = hasher.digest(input.getBytes());
                return sha1;

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        System.out.println(DatatypeConverter.printBase64Binary(sha1));
        return null;
    }

    public String getEncription() {
        return encryption;
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

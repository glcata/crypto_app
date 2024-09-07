// * @author Catalin Glavan
package crypto.encrypt;

import crypto.sql.DBData;
import crypto.sql.DBSelect;
import org.bouncycastle.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

public class encrypt {

    private String default_fileOut = System.getProperty("user.home") + "\\Desktop\\file.enc";
    private String fileIn;
    private String fileOut;
    private String fileName;
    private String decrypt;
    private String captcha;
    private BufferedInputStream is = null;
    private BufferedOutputStream os = null;

    public encrypt() {
    }

    public encrypt(String name, String email, String certificatePath) {

        try {
            InputStream dataCertificate = new FileInputStream(certificatePath);
            X509Certificate certificate = X509Certificate.getInstance(dataCertificate);
            certificate.checkValidity();

            toFromPostgres("select", email, null, null);
            encryption("decrypt", null, certificate, captcha);
            //new File(default_fileOut).delete();

        } catch (FileNotFoundException | CertificateException e) {
            e.printStackTrace();
        }

    }

    public encrypt(String name, String email, String captcha_code, String certificatePath) {

        try {

            InputStream dataStream = new ByteArrayInputStream((name + ":" + email).getBytes(StandardCharsets.UTF_8));
            InputStream dataCertificate = new FileInputStream(certificatePath);
            X509Certificate certificate = X509Certificate.getInstance(dataCertificate);
            certificate.checkValidity();

            encryption("encrypt", dataStream, certificate, captcha_code);
            toFromPostgres("create", email, name, captcha_code);

        } catch (FileNotFoundException | CertificateException ex) {
            ex.printStackTrace();
        }
    }

    public final void toFromPostgres(String method, String email, String name, String captcha_code) {

        if (method.equals("create")) {
            DBData sqlData = new DBData();
            sqlData.createDATA(name, email, captcha_code, default_fileOut);
        }

        if (method.equals("select")) {
            DBSelect sqlData = new DBSelect();
            sqlData.selectDATA(email);
            captcha = sqlData.getCaptcha();
            writeFileBytes(sqlData.getEncryption());
        }

        if (method.equals("create_history")) {
            DBData sqlBData = new DBData();
            sqlBData.createDATAhistory(email, "encryption", fileName, fileOut);
            new File(fileOut).delete();
        }

        if (method.equals("create_history_lite")) {
            DBData sqlBData = new DBData();
            sqlBData.createDATAhistory(email, "encryption [without file]", fileName, null);
        }

        if (method.equals("create_history_decrypt")) {
            DBData sqlBData = new DBData();
            sqlBData.createDATAhistory(email, "decryption", fileName +".enc",null);
        }
    }

    public final void encryption(String method, InputStream dataStream, X509Certificate certificate, String captcha) {
        try {

            if (method.equals("encrypt")) {
                is = new BufferedInputStream(dataStream);
                os = new BufferedOutputStream(new FileOutputStream(default_fileOut));

                encryptDecrypt(true, is, os, captcha, certificate.getPublicKey());
            }
            if (method.equals("decrypt")) {
                is = new BufferedInputStream(new FileInputStream(new File(default_fileOut)));
                OutputStream dataOutStream = new ByteArrayOutputStream();
                os = new BufferedOutputStream(dataOutStream);

                encryptDecrypt(false, is, os, captcha, certificate.getPublicKey());
                decrypt = dataOutStream.toString();
            }
            if (method.equals("encryptFile")) {
                is = new BufferedInputStream(new FileInputStream(fileIn));
                os = new BufferedOutputStream(new FileOutputStream(fileOut));

                encryptDecrypt(true, is, os, captcha, certificate.getPublicKey());
            }

            if (method.equals("decryptFile")) {
                is = new BufferedInputStream(new FileInputStream(fileIn));
                os = new BufferedOutputStream(new FileOutputStream(fileOut));

                encryptDecrypt(false, is, os, captcha, certificate.getPublicKey());
            }

            if (method.equals("decryptFileHistory")) {
                is = new BufferedInputStream(dataStream);
                os = new BufferedOutputStream(new FileOutputStream(fileOut));

                encryptDecrypt(false, is, os, captcha, certificate.getPublicKey());
            }
            is.close();
            os.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(encrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static byte[] readFile(String filePath) {
        byte[] result = null;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath));) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                result = line.getBytes();
                System.out.println(java.util.Arrays.toString(result));
            }
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void writeFileBytes(byte[] b) {
        FileOutputStream out;
        try {
            out = new FileOutputStream(default_fileOut);
            out.write(b);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(encrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(encrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void encryptDecrypt(Boolean encrypt_decrypt, InputStream in, OutputStream out, String password, PublicKey key) {
        try {
            byte[] key256 = getSHA256(key.getEncoded());

            System.out.println(DatatypeConverter.printBase64Binary(key.getEncoded()));  // print key
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

        } catch (Exception e) {
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

    public String getDecrypt() {
        return decrypt;
    }

    public void setFileIn(String fileIn) {
        this.fileIn = fileIn;
    }

    public void setFileOut(String fileOut) {
        this.fileOut = fileOut;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

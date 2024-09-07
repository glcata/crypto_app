package crypto.encrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class test_x509Generator {

    public static void main(String[] args) {
        Thread setThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String Path = "cd C:\\openssl\\bin";
                    String CNF = "set OPENSSL_CONF=C:\\openssl\\bin\\openssl.cfg";
                   Thread.sleep(1000);
                    cmd(Path, CNF, "openssl genrsa -out demo\\ca.key 4096");
                   Thread.sleep(1000);
                    cmd(Path, CNF, "openssl req -new -x509 -days 365 -key demo\\ca.key -out demo\\ca.crt -subj \"/C=RO/ST=Romania/L=Bucharest/O=CRYPTO COMPANY/OU=IT Department/CN=Catalin Glavan CryptoSoft/emailAddress=glmihaicata@hotmail.com\"");
                   Thread.sleep(1000);
                    cmd(Path, CNF, "openssl genrsa -out demo\\ia.key 4096");
                   Thread.sleep(1000);
                    cmd(Path, CNF, "openssl req -new -key demo\\ia.key -out demo\\ia.csr -subj \"/C=RO/ST=Romania/L=Bucharest/O=Companie/OU=Departament/CN=Nume Client/emailAddress=adresa_client@contact.ro\"");
                   Thread.sleep(1000);
                    cmd(Path, CNF, "openssl x509 -req -days 365 -in demo\\ia.csr -CA demo\\ca.crt -CAkey demo\\ca.key -set_serial 01 -out demo\\ia.crt");
                   Thread.sleep(1000);
                    cmd(Path, CNF, "openssl pkcs12 -export -out demo\\ia.p12 -inkey demo\\ia.key -in demo\\ia.crt -chain -CAfile demo\\ca.crt -passout pass:1234");

                } catch (InterruptedException ex) {
                    Logger.getLogger(test_x509Generator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        setThread.start();
    }

    public static void cmd(String setPath, String setCNF, String command) {

        String st = setPath;
        String cd = command;
        String conf = setCNF;

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "C: && "+ st + " && " + conf + " && " + cd);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(test_x509Generator.class.getName()).log(Level.SEVERE, "WARNING", ex);
        }
    }
}

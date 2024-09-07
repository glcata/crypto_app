package crypto.encrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class x509Generator_sign {

    private String file, cn, c, st, l, org, org_unit, email;
    private boolean pks;
    private String pks_pass;

    public x509Generator_sign(String FILE_LOCATION, String COMMON_NAME, String COUNTRY, String STATE, String LOCATION, String ORG, String ORG_UNIT, String EMAIL, boolean PKS, String pks_password) {

        this.file = FILE_LOCATION;
        this.cn = COMMON_NAME;
        this.c = COUNTRY;
        this.st = STATE;
        this.l = LOCATION;
        this.org = ORG;
        this.org_unit = ORG_UNIT;
        this.email = EMAIL;
        this.pks = PKS;
        this.pks_pass = pks_password;

        Thread setThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String location = System.getProperty("user.dir");
                    String PART = Character.toString(location.charAt(0));
                    String PATH = "cd " + location + "\\openssl\\bin";
                    String CNF = "set OPENSSL_CONF=" + location + "\\openssl\\bin\\openssl.cfg";

                    cmd(PATH, CNF, PART, "openssl genrsa -out \"" + file + "\\" + cn + ".key\" 4096");
                    Thread.sleep(1000);
                    cmd(PATH, CNF, PART, "openssl req -new -key \"" + file + "\\" + cn + ".key\" -out \"" + file + "\\" + cn + ".csr\" -subj \"/C=" + c + "/ST=" + st + "/L=" + l + "/O=" + org + "/OU=" + org_unit + "/CN=" + cn + " Registered in CryptoSoft/emailAddress=" + email + "\"");
                    Thread.sleep(1000);
                    cmd(PATH, CNF, PART, "openssl x509 -req -days 365 -in \"" + file + "\\" + cn + ".csr\" -CA company\\ca.crt -CAkey company\\ca.key -set_serial 01 -out \"" + file + "\\" + cn + ".crt\"");
                    if (pks) {
                        Thread.sleep(1000);
                        cmd(PATH, CNF, PART, "openssl pkcs12 -export -out \"" + file + "\\" + cn + ".p12\" -inkey \"" + file + "\\" + cn + ".key\" -in \"" + file + "\\" + cn + ".crt\" -chain -CAfile company\\ca.crt -passout pass:" + pks_pass);
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(x509Generator_sign.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        setThread.start();
    }

    public static void cmd(String setPath, String setConfig, String setPartition, String command) {

        String st = setPath;
        String cd = command;
        String conf = setConfig;
        String partition = setPartition;

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", partition + ": && " + st + " && " + conf + " && " + cd);
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
            Logger.getLogger(x509Generator_sign.class.getName()).log(Level.SEVERE, "WARNING", ex);
        }
    }
}

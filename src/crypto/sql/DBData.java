// * @author Catalin Glavan
package crypto.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBData extends DBConnect {

    private String NAME;
    private String EMAIL;
    private String CAPTCHA_CODE;
    private String FILE;

    private String USER;
    private String MODE;
    private String fileName;
    private String fileEncrypted;

    public void createDATA(String name, String email, String captcha_code, String file_enc) {
        try {
            this.NAME = name;
            this.EMAIL = email;
            this.CAPTCHA_CODE = captcha_code;
            this.FILE = file_enc;

            String query = "INSERT INTO public.users (name, email, captcha_code, encryption) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, NAME);
            statement.setString(2, EMAIL);
            statement.setString(3, CAPTCHA_CODE);
            statement.setBinaryStream(4, new FileInputStream(new File(FILE)));
            statement.executeUpdate();

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(DBData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createDATAhistory(String user_email, String mode, String file_name, String file_encrypted) {
        try {
            this.USER = user_email;
            this.MODE = mode;
            this.fileName = file_name;
            this.fileEncrypted = file_encrypted;

            String query = "INSERT INTO public.history (user_email, mode, file_name, file_encrypted) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, USER);
            statement.setString(2, MODE);
            statement.setString(3, fileName);
            if (file_encrypted != null) {
                try (FileInputStream fileInputStream = new FileInputStream(new File(fileEncrypted))) {
                    statement.setBinaryStream(4, fileInputStream);
                }
            } else {
                statement.setBytes(4, null);
            }
            statement.executeUpdate();

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(DBData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

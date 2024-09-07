// * @author Catalin Glavan
package crypto.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBSelect extends DBConnect {

    private String EMAIL;
    private ResultSet rs;
    private byte[] encryption;
    private String captcha;
    private String FILE_NAME;
    private String DATE;

    public void selectDATA(String email) {
        try {
            this.EMAIL = email;
            String query = "SELECT encryption, captcha_code FROM users WHERE email='" + EMAIL + "'";
            PreparedStatement select = con.prepareStatement(query);
            rs = select.executeQuery();
            while (rs.next()) {
                encryption = rs.getBytes("encryption");
                captcha = rs.getString("captcha_code");
            }
            st.close();
          //  System.out.println(Arrays.toString(encryption));

        } catch (SQLException ex) {
            Logger.getLogger(DBData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet selectDATAhistory(String email) {
        try {
            this.EMAIL = email;
            String query = "SELECT file_name, mode, date, file_encrypted FROM history WHERE user_email='" + EMAIL + "'";
            PreparedStatement select = con.prepareStatement(query);
            rs = select.executeQuery();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public void selectDATAhistoryEncryption(String email, String file_name, String date) {
        try {
            this.EMAIL = email;
            this.FILE_NAME = file_name;
            this.DATE = date;
            String query = "SELECT file_encrypted FROM history WHERE user_email='" + EMAIL + "' and file_name='" + FILE_NAME + "' and date='" + DATE + "'";
            PreparedStatement select = con.prepareStatement(query);
            rs = select.executeQuery();
            while (rs.next()) {
                encryption = rs.getBytes("file_encrypted");
            }
            st.close();
          //  System.out.println(Arrays.toString(encryption));

        } catch (SQLException ex) {
            Logger.getLogger(DBData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] getEncryption() {
        return encryption;
    }

    public String getCaptcha() {
        return captcha;
    }
}

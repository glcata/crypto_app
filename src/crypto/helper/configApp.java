// * @author Catalin Glavan
package crypto.helper;

public class configApp {

    private final String iconPath;
    private final String username;
    private final String password;
    private final String database;
    private final String defaultLang;

    public configApp() {
        this.iconPath = "/crypto/images/simple_forms/aes_shield.png";
        this.username = "username";
        this.password = "password";
        this.database = "crypto";
        this.defaultLang = "en";
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getLang() {
        return defaultLang;
    }

}

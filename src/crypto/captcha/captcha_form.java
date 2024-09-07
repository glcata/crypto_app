package crypto.captcha;

import javax.swing.*;
import java.awt.*;

public class captcha_form extends JPanel {

    private String captcha;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setSize(115, 35);
        this.setBackground(Color.DARK_GRAY);

        g.setColor(Color.ORANGE);
        g.setFont(new Font("Tahoma", Font.PLAIN, 35));
        this.captcha = createCaptchaValue();
        for (int i = 0; i < captcha.length(); i++) {
            int x = (i == 0) ? 1 : 13 * i;
            g.drawString(Character.toString(captcha.charAt(i)), x, 30);

        }
    }

    private String createCaptchaValue() {

        java.util.Random random = new java.util.Random();
        int length = 7 + (Math.abs(random.nextInt()) % 3);
        StringBuilder captchaStrBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {

            int baseCharacterNumber = Math.abs(random.nextInt()) % 62;
            int characterNumber = 0;

            if (baseCharacterNumber < 26) {
                characterNumber = 65 + baseCharacterNumber;
            } else if (baseCharacterNumber < 52) {
                characterNumber = 97 + (baseCharacterNumber - 26);
            } else {
                characterNumber = 48 + (baseCharacterNumber - 52);
            }
            captchaStrBuffer.append((char) characterNumber);
        }
        return captchaStrBuffer.toString();
    }

    public String getCaptcha() {
        return captcha;
    }
}

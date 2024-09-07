package crypto.captcha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class generator extends JFrame {

    private JFrame frame;
    private final JPanel panel;
    private captcha cp;
    private final JButton check;
    private final JButton refresh;

    public generator() {

        frame = new JFrame();
        frame.setLayout(new GridLayout(0, 1));
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        cp = new captcha();
        check = new JButton("check");
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String check = JOptionPane.showInputDialog(null, "WHAT IS THE CAPTCHA CODE ?", "CAPTHA", JOptionPane.QUESTION_MESSAGE);
                if (check.equals(cp.getCaptcha())) {
                    JOptionPane.showMessageDialog(null, "TRUE \n the code is " + cp.getCaptcha(), "CAPTCHA", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "FALSE \n the code is " + cp.getCaptcha(), "CAPTCHA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        refresh = new JButton("refresh");
        refresh.setOpaque(true);
     //   refresh.setContentAreaFilled(false);
     //   refresh.setBorderPainted(false);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.invalidate();
                frame.validate();
                frame.repaint();
            }
        });
        panel.add(check);
        panel.add(refresh);
        panel.add(cp);

        frame.add(panel);
        frame.setTitle("TEST");
        frame.setLocationRelativeTo(null);
        frame.setSize(320, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new generator();
            }
        });
    }

    class captcha extends JPanel {

        private String captcha;

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            super.setSize(125, 35);
            this.setBackground(Color.DARK_GRAY);

            g.setColor(Color.ORANGE);
            g.setFont(new Font("Tahoma", Font.PLAIN, 35));
            this.captcha = createCaptchaValue();
            for (int i = 0; i < captcha.length(); i++) {
                int x = (i == 0) ? 1 : 14 * i;
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
}

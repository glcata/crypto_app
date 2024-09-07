// * @author Catalin Glavan
package crypto.chat;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chat extends JFrame implements WritableGUI {

    private final JFrame frame;
    private final JPanel panelW, panelW_mic, panelC;
    private final JPanel panelC_1, panelC_2;
    private final JPanel panelW_1, panelW_2, panelW_3, panelW_4;
    private final JLabel ip;
    private final JLabel port_local, port_server;
    private static JTextField ip_txt, send_txt;
    private static JTextField port_local_txt, port_server_txt;
    private final JButton listen, send;
    private static JTextArea chat;
    private final JScrollPane scroll;
    private static String client;

    public chat() {

        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        panelW = new JPanel();
        panelW.setLayout(new BorderLayout());
        panelW_mic = new JPanel();
        panelW_mic.setLayout(new BoxLayout(panelW_mic, BoxLayout.PAGE_AXIS));
        panelW_1 = new JPanel();
        ip = new JLabel("        IP : ");
        ip_txt = new JTextField("localhost", 10);
        panelW_1.add(ip);
        panelW_1.add(ip_txt);
        panelW_mic.add(panelW_1);

        panelW_2 = new JPanel();
        port_local = new JLabel(" PORT LOCAL : ");
        port_local_txt = new JTextField("666", 6);
        panelW_2.add(port_local);
        panelW_2.add(port_local_txt);
        panelW_mic.add(panelW_2);

        panelW_3 = new JPanel();
        port_server = new JLabel("PORT SERVER :");
        port_server_txt = new JTextField("667", 6);
        panelW_3.add(port_server);
        panelW_3.add(port_server_txt);
        panelW_mic.add(panelW_3);

        panelW_4 = new JPanel();
        listen = new JButton("LISTEN");
        panelW_4.add(listen);
        panelW.add(panelW_mic, BorderLayout.NORTH);
        panelW.add(panelW_4, BorderLayout.CENTER);
        frame.add(panelW, BorderLayout.WEST);

        panelC = new JPanel();
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.PAGE_AXIS));

        panelC_1 = new JPanel();
        chat = new JTextArea(20, 30);
        chat.setAutoscrolls(true);
        chat.setEditable(false);
        chat.setWrapStyleWord(true);
        scroll = new JScrollPane(chat);
        scroll.setAutoscrolls(true);
        scroll.setVisible(true);
        panelC_1.add(chat);

        panelC_2 = new JPanel();
        send_txt = new JTextField(21);
        send = new JButton("SEND");
        panelC_2.add(send_txt);
        panelC_2.add(send);

        panelC.add(panelC_1);
        panelC.add(panelC_2);
        panelC.add(new JButton("spatiuasd"));
        frame.add(panelC, BorderLayout.CENTER);

        //EVENTS
        EventHandler event = new EventHandler();
        listen.addActionListener(event);
        send.addActionListener(event);
        send_txt.addActionListener(event);

        frame.setTitle("CHAT");
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setSize(500, 450);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class EventHandler implements ActionListener {

        public EventHandler() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == listen) {
                MessageListener thListener = new MessageListener((WritableGUI) this, Integer.parseInt(port_local_txt.getText()));
                thListener.start();
            }
            if (e.getSource() == send || e.getSource() == send_txt) {
                if (send_txt.getText() != null && !send_txt.getText().isEmpty()) {
                    MessageTransmitter transmitter = new MessageTransmitter(client + send_txt.getText(), ip_txt.getText(), Integer.parseInt(port_server_txt.getText()));
                    transmitter.start();
                    write(client + send_txt.getText());
                    send_txt.setText(null);
                }
            }
        }
    }

    @Override
    public void write(String s) {
        chat.append(send_txt.getText() + System.lineSeparator());
    }

    public static void main(String[] args) {

        try {
            javax.swing.UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    client += JOptionPane.showInputDialog("Nume ?") + " : ";
                    new chat();
                }
            });
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(chat.class.getName()).log(java.util.logging.Level.WARNING, "ALERT", e);
        }
    }
}

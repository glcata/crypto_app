/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto.simple_forms;

import crypto.encrypt.encrypt;
import crypto.helper.configApp;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mihai-catalin.glavan
 */
public class codePanel extends javax.swing.JFrame {

    /**
     * Creates new form functionPanel
     *
     * @param userEmail
     * @param location_key
     * @param captcha
     * @param bundle
     * @param location
     */
    public codePanel(String userEmail, String location_key, String captcha, ResourceBundle bundle, Point location) {

        this.bundle = bundle;
        this.location_key = location_key;
        this.userEmail = userEmail;
        this.captcha = captcha;

        initComponents();
        super.setIconImage(new ImageIcon(getClass().getResource(config.getIconPath())).getImage());

        checkLocation(location);
        closeWindow();
        logout.requestFocus();
    }

    private void checkLocation(Point location) {
        setPoint = location;
        if (setPoint == null) {
            super.setLocationRelativeTo(null);
        } else {
            super.setLocation(setPoint);
        }
    }

    private void closeWindow() {

        super.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ImageIcon icon = new ImageIcon(getClass().getResource(config.getIconPath()));
                if (JOptionPane.showConfirmDialog(
                        windowEvent.getWindow(),
                        bundle.getString("key_msg_close"), bundle.getString("key_msg_close_title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        icon
                ) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        codePanel_bg1 = new crypto.helper.codePanel_bg();
        logout = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField_file = new javax.swing.JTextField();
        choose_file = new javax.swing.JButton();
        encrypt = new javax.swing.JButton();
        decrypt = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jCheckBox_option = new javax.swing.JCheckBox();
        jButton_history = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CODE PANEL");
        setResizable(false);

        logout.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        logout.setText("Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jTextField2.setText("USER");
        jTextField2.setEnabled(false);

        jTextField3.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jTextField3.setText(userEmail);
        jTextField3.setEnabled(false);

        jTextField_file.setText(bundle.getString("key_file"));
        jTextField_file.setColumns(12);
        jTextField_file.setEditable(false);
        jTextField_file.setToolTipText(jTextField_file.getText());

        choose_file.setText("Choose");
        choose_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choose_fileActionPerformed(evt);
            }
        });

        encrypt.setText("ENCRYPT");
        encrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptActionPerformed(evt);
            }
        });

        decrypt.setText("DECRYPT");
        decrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decryptActionPerformed(evt);
            }
        });

        jCheckBox_option.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jCheckBox_option.setForeground(new java.awt.Color(77, 182, 172));
        jCheckBox_option.setText("BELOW 1MB, YOU CAN ENCRYPT AND STORE IN APP");
        jCheckBox_option.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox_option)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox_option)
                .addContainerGap())
        );

        jButton_history.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jButton_history.setText("History");
        jButton_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_historyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout codePanel_bg1Layout = new javax.swing.GroupLayout(codePanel_bg1);
        codePanel_bg1.setLayout(codePanel_bg1Layout);
        codePanel_bg1Layout.setHorizontalGroup(
            codePanel_bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(codePanel_bg1Layout.createSequentialGroup()
                .addGroup(codePanel_bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(codePanel_bg1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_history)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logout))
                    .addGroup(codePanel_bg1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(codePanel_bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 62, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, codePanel_bg1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(codePanel_bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(codePanel_bg1Layout.createSequentialGroup()
                        .addComponent(encrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(decrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(codePanel_bg1Layout.createSequentialGroup()
                        .addComponent(jTextField_file, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choose_file, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(107, 107, 107))
        );
        codePanel_bg1Layout.setVerticalGroup(
            codePanel_bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(codePanel_bg1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(codePanel_bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logout)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_history))
                .addGap(36, 36, 36)
                .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(codePanel_bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(choose_file, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_file, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(codePanel_bg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(decrypt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(encrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(codePanel_bg1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(codePanel_bg1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        if (evt.getSource() == logout) {
            super.dispose();
            new login(setPoint, bundle).setVisible(true);
        }
    }//GEN-LAST:event_logoutActionPerformed

    private void choose_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choose_fileActionPerformed
        if (evt.getSource() == choose_file) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("user.dir"));
            chooser.setAcceptAllFileFilterUsed(true);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                location_file_in = chooser.getSelectedFile().getAbsolutePath();
                file_name = chooser.getSelectedFile().getName();
                jTextField_file.setText(location_file_in);
                jTextField_file.setToolTipText(location_file_in);

                File in = new File(location_file_in);
                if (in.length() < 1000000) {
                    jCheckBox_option.setEnabled(true);
                    System.out.println(in.length());
                } else {
                    jCheckBox_option.setEnabled(false);
                    jCheckBox_option.setSelected(false);
                }
            }
        }
    }//GEN-LAST:event_choose_fileActionPerformed

    private void encryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptActionPerformed
        if (evt.getSource() == encrypt) {
            try {
                InputStream dataCertificate = new FileInputStream(location_key);
                X509Certificate certificate = X509Certificate.getInstance(dataCertificate);
                certificate.checkValidity();
                encrypt objEncrypt = new encrypt();
                objEncrypt.setFileName(file_name);
                objEncrypt.setFileIn(location_file_in);

                if (!jCheckBox_option.isSelected()) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new java.io.File("user.dir"));
                    chooser.setDialogTitle("Save AS");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        location_file_out = chooser.getSelectedFile().toString();

                        objEncrypt.setFileOut(location_file_out + "\\" + file_name + ".enc");
                        objEncrypt.encryption("encryptFile", null, certificate, captcha);
                        objEncrypt.toFromPostgres("create_history_lite", userEmail, null, null);
                    }
                } else {
                    objEncrypt.setFileOut(location_file_in + ".enc");
                    objEncrypt.encryption("encryptFile", null, certificate, captcha);
                    objEncrypt.toFromPostgres("create_history", userEmail, null, null);
                }
                refreshVariables();
            } catch (HeadlessException | FileNotFoundException | CertificateException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_encryptActionPerformed

    private void decryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decryptActionPerformed
        if (evt.getSource() == decrypt) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("user.dir"));
            chooser.setDialogTitle("Save AS");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                location_file_out = chooser.getSelectedFile().toString();
                try {
                    InputStream dataCertificate = new FileInputStream(location_key);
                    X509Certificate certificate = X509Certificate.getInstance(dataCertificate);
                    certificate.checkValidity();

                    encrypt objEncrypt = new encrypt();
                    objEncrypt.setFileIn(location_file_in);
                    objEncrypt.setFileOut(location_file_in.replace(".enc", ""));
                    objEncrypt.setFileName(file_name);

                    objEncrypt.encryption("decryptFile", null, certificate, captcha);
                    objEncrypt.toFromPostgres("create_history_decrypt", userEmail, null, null);
                    refreshVariables();
                } catch (FileNotFoundException | CertificateException e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_decryptActionPerformed

    private void jButton_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_historyActionPerformed
        try {
            new historyPanel(userEmail, location_key, captcha, setPoint);
        } catch (SQLException ex) {
            Logger.getLogger(codePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_historyActionPerformed

    private void refreshVariables() {
        file_name = null;
        location_file_in = null;
        location_file_out = null;
        jTextField_file.setText(bundle.getString("key_file"));
        jTextField_file.setToolTipText(bundle.getString("key_file"));
        jCheckBox_option.setSelected(false);
        jCheckBox_option.setEnabled(false);
    }

    public static void main(String args[]) {

        try {
            javax.swing.UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final Locale RO = new Locale("ro", "Romania");
                    final Locale EN = new Locale("en", "US");
                    ResourceBundle bundle = ResourceBundle.getBundle("crypto/helper/lang_en", EN);
                    new codePanel(null, null, null, bundle, setPoint).setVisible(true);
                }
            });
        } catch (ParseException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private static Point setPoint = null;
    private static String location_key;
    private static String userEmail;
    private static String captcha;
    private static String location_file_in;
    private static String location_file_out;
    private static String file_name;
    private final configApp config = new configApp();
    private static ResourceBundle bundle;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton choose_file;
    private crypto.helper.codePanel_bg codePanel_bg1;
    private javax.swing.JButton decrypt;
    private javax.swing.JButton encrypt;
    private javax.swing.JButton jButton_history;
    private javax.swing.JCheckBox jCheckBox_option;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField_file;
    private javax.swing.JButton logout;
    // End of variables declaration//GEN-END:variables

}

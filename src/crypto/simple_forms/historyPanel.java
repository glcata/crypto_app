// * @author Catalin Glavan
package crypto.simple_forms;

import crypto.encrypt.encrypt;
import crypto.helper.configApp;
import crypto.sql.DBSelect;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class historyPanel extends JFrame {

    private ResultSet rs;
    private String location_file_out;
    private final configApp config = new configApp();
    private static String user_email;
    private static String key;
    private static String captcha_code;
    // private static int columnCount;

    public historyPanel(String userEmail, String location_key, String captcha, Point location) throws SQLException {

        this.user_email = userEmail;
        this.key = location_key;
        this.captcha_code = captcha;

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        DBSelect sqlData = new DBSelect();
        rs = sqlData.selectDATAhistory(user_email);

        JInternalFrame top = new JInternalFrame();
        top.setVisible(true);
        frame.add(top, BorderLayout.NORTH);

        JTable table = new JTable(buildTableModel(rs));
        JScrollPane scroll = new JScrollPane(table);
        table.setAutoCreateRowSorter(true);
        table.setAutoCreateColumnsFromModel(true);
        table.setRowHeight(20);
        table.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 15));
        table.setFont(new Font("Calibri", Font.PLAIN, 14));
        table.getColumnModel().getColumn(0).setPreferredWidth(4);
        table.getColumnModel().getColumn(1).setPreferredWidth(86);
        table.getColumnModel().getColumn(2).setPreferredWidth(36);
        table.getColumnModel().getColumn(3).setPreferredWidth(44);

        /* for (int i = 0; i < columnCount; i++) {
            table.setRowHeight(i, 40);
        }*/
        // table.getColumn("FILE_ENCRYPTED").setCellRenderer(new ButtonRenderer());
        // table.getColumn("FILE_ENCRYPTED").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getSelectionModel().addListSelectionListener(new ListSelection(table));
        frame.add(scroll, BorderLayout.CENTER);

        JInternalFrame bott = new JInternalFrame();
        bott.setVisible(true);
        frame.add(bott, BorderLayout.SOUTH);
        frame.setTitle("TEST");
        frame.setSize(820, 450);
        frame.setLocation(location);
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon("./favicon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private class ListSelection implements ListSelectionListener {

        private JTable table;

        public ListSelection(JTable table) {
            this.table = table;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                String fileEncrypted = table.getValueAt(table.getSelectedRow(), 3).toString();

                if (fileEncrypted != null) {
                    String fileName = table.getValueAt(table.getSelectedRow(), 0).toString();
                    String fileDate = table.getValueAt(table.getSelectedRow(), 2).toString();

                    ImageIcon icon = new ImageIcon(getClass().getResource(config.getIconPath()));
                    int dialogButton = JOptionPane.showConfirmDialog(null, "This file is stored in software db, do you want to decrypy this file?", "Warning", 0, JOptionPane.QUESTION_MESSAGE, icon);

                    System.out.println(dialogButton);
                    if (dialogButton == JOptionPane.YES_OPTION) {

                        JFileChooser chooser = new JFileChooser();
                        chooser.setCurrentDirectory(new java.io.File("user.dir"));
                        chooser.setDialogTitle("Save AS");
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                            location_file_out = chooser.getSelectedFile().toString();

                            InputStream dataCertificate = new FileInputStream(key);
                            X509Certificate certificate = X509Certificate.getInstance(dataCertificate);
                            certificate.checkValidity();

                            DBSelect sqlData = new DBSelect();
                            sqlData.selectDATAhistoryEncryption(user_email, fileName, fileDate);
                            InputStream inputStream = new ByteArrayInputStream(sqlData.getEncryption());

                            encrypt encryptObj = new encrypt();
                            encryptObj.setFileOut(location_file_out + "\\" + fileName);
                            encryptObj.encryption("decryptFileHistory", inputStream, certificate, captcha_code);
                        }
                    }
                }
            } catch (NullPointerException | ArrayIndexOutOfBoundsException | HeadlessException ex) {

            } catch (FileNotFoundException | CertificateException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData mD = rs.getMetaData();
        //columnCount = 0;
        Vector<String> numeCol = new Vector<>();
        for (int i = 1; i <= mD.getColumnCount(); i++) {
            numeCol.add(mD.getColumnName(i).toUpperCase());
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int i = 1; i <= mD.getColumnCount(); i++) {
                //     System.out.println(rs.getObject(i));
                vector.add(rs.getObject(i));
            }
            data.add(vector);
            //columnCount++;
        }
        return new DefaultTableModel(data, numeCol);
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        super.setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
        return new JLabel();
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;

    private String label;

    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value != null) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }
        return new JLabel();
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("user.dir"));
            chooser.setDialogTitle("Save AS");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                chooser.getSelectedFile().getAbsolutePath();
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

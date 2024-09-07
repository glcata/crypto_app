/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto.forms.old;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Catalin Glavan
 */
public class text_fields extends javax.swing.JPanel {

    private Image image_ON, image_OFF;
    private String text = "TXT";
    private int button;

    public text_fields() {
        initComponents();
        this.setDoubleBuffered(true);
        this.setOpaque(false);
        this.image_OFF = new ImageIcon(getClass().getResource("/crypto/images/field_off.png")).getImage();
        this.image_ON = new ImageIcon(getClass().getResource("/crypto/images/field_on.png")).getImage();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        switch (button) {
            case 0: {
                g2D.drawImage(image_OFF, 0, 0, null);
                OFF(g2D);
            }
            break;
            case 1: {
                g2D.drawImage(image_ON, 0, 0, null);
                ON(g2D);
            }
            break;
            case 2: {
                g2D.drawImage(image_OFF, 0, 0, null);
                OFF_intext(g2D);
            }
            break;
            case 3: {
                g2D.drawImage(image_ON, 0, 0, null);
                ON2(g2D);
            }
            break;
        }
    }

    private void ON(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString(text, 5, 27);
        g.setColor(new Color(62, 145, 238));
        g.setFont(new Font("Calibri", Font.BOLD, 14));
        g.drawString(text, 5, 25);
    }

    private void ON2(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString(text, 7, 46);
        g.setColor(new Color(62, 145, 238));
        g.setFont(new Font("Calibri", Font.BOLD, 16));
        g.drawString(text, 7, 46);
    }

    private void OFF(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString(text, 7, 46);
        g.setColor(new Color(77, 77, 79));
        g.setFont(new Font("Calibri", Font.BOLD, 16));
        g.drawString(text, 7, 45);
    }

    private void OFF_intext(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString(text, 5, 27);
        g.setColor(new Color(77, 77, 79));
        g.setFont(new Font("Calibri", Font.BOLD, 14));
        g.drawString(text, 5, 25);
    }

    public void refreshFrame() {
        super.invalidate();
        super.validate();
        super.repaint();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

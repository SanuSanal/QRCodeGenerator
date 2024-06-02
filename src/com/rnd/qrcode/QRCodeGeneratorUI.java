package com.rnd.qrcode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeGeneratorUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
    private JLabel qrLabel;
    private BufferedImage qrImage;

    public QRCodeGeneratorUI() {
        setTitle("QR Code Generator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel label = new JLabel("Enter Data:");
        label.setBounds(30, 20, 100, 30);
        add(label);

        textField = new JTextField();
        textField.setBounds(120, 20, 200, 30);
        add(textField);

        JButton generateButton = new JButton("Generate QR Code");
        generateButton.setBounds(120, 60, 200, 30);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateQRCode();
            }
        });
        add(generateButton);

        qrLabel = new JLabel();
        qrLabel.setBounds(30, 100, 320, 200);
        add(qrLabel);

        JButton saveButton = new JButton("Save QR Code");
        saveButton.setBounds(120, 310, 200, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveQRCode();
            }
        });
        add(saveButton);
    }

    private void generateQRCode() {
        String data = textField.getText();
        if (!data.isEmpty()) {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250);
                qrImage = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
                for (int x = 0; x < 250; x++) {
                    for (int y = 0; y < 250; y++) {
                        qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                    }
                }
                qrLabel.setIcon(new ImageIcon(qrImage));
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveQRCode() {
        if (qrImage != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    ImageIO.write(qrImage, "png", new File(fileToSave.getAbsolutePath() + ".png"));
                    JOptionPane.showMessageDialog(this, "QR Code saved successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QRCodeGeneratorUI().setVisible(true);
            }
        });
    }
}


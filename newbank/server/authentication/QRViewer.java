package newbank.server.authentication;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class QRViewer {
    public static void viewQR() {
        var frame = new JFrame();
        var icon = new ImageIcon("QR.png");
        var label = new JLabel(icon);
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
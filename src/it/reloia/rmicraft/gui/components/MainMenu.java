package it.reloia.rmicraft.gui.components;

import it.reloia.rmicraft.client.rmi.RMICraft;
import it.reloia.rmicraft.gui.RMICraftClientGUI;
import it.reloia.rmicraft.gui.components.swing.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainMenu extends JPanel {
    JTextPane errorBox;

    public MainMenu() {
        this.setBounds(0, 0, 800, 600);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);

        JLabel title = new JLabel("RMICraft Client");
        title.setForeground(new Color(255, 255, 255));
        title.setFont(new Font("Arial", Font.BOLD, 56));
        title.setBounds(0, 70, 800, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVisible(true);
        this.add(title);

        int buttonWidth = 300;
        int buttonHeight = 40;
        int buttonX = 800 / 2 - buttonWidth / 2;
        int buttonY = 420;

        JTextPane infoBox = getInfoBox();
        this.add(infoBox);

        errorBox = getErrorBox();
        this.add(errorBox);

        JButton startButton = startButton(buttonWidth, buttonHeight, buttonX, buttonY);
        this.add(startButton);

        JButton exitButton = exitButton(buttonWidth, buttonHeight, buttonX, buttonY + 60);
        this.add(exitButton);

        // https://stackoverflow.com/questions/32159065/how-to-grab-a-mouse-in-java-swing
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                RMICraftClientGUI.INSTANCE.x = evt.getX();
                RMICraftClientGUI.INSTANCE.y = evt.getY();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                RMICraftClientGUI.INSTANCE.setLocation(evt.getXOnScreen() - RMICraftClientGUI.INSTANCE.x, evt.getYOnScreen() - RMICraftClientGUI.INSTANCE.y);
                RMICraftClientGUI.INSTANCE.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseMoved(MouseEvent evt) {
                RMICraftClientGUI.INSTANCE.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private JTextPane getInfoBox() {
        JTextPane infoBox = new JTextPane();
        infoBox.setText("Connect to your local Minecraft instance and control it!");
        infoBox.setEditable(false);
        infoBox.setFont(new Font("Arial", Font.PLAIN, 22));

        int width = infoBox.getFontMetrics(infoBox.getFont()).stringWidth(infoBox.getText());

        infoBox.setBounds((800 / 2 ) - (width / 2), 140, 800, 40);
        infoBox.setOpaque(false);
        infoBox.setAlignmentX(Component.CENTER_ALIGNMENT);


        infoBox.setForeground(new Color(255, 255, 255));
        infoBox.setBorder(null);
        return infoBox;
    }

    private JTextPane getErrorBox() {
        JTextPane eBox = new JTextPane();
        eBox.setEditable(false);
        eBox.setFont(new Font("Arial", Font.PLAIN, 22));
        eBox.setBounds((800 / 2 ) - (eBox.getFontMetrics(eBox.getFont()).stringWidth(eBox.getText()) / 2), 200, 800, 40);
        eBox.setOpaque(false);
        eBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        eBox.setForeground(new Color(78, 0, 0));
        eBox.setBorder(null);
        eBox.setVisible(true);
        return eBox;
    }

    public void setError(String error) {
        errorBox.setText(error);
        errorBox.setBounds((800 / 2) - (errorBox.getFontMetrics(errorBox.getFont()).stringWidth(error.split("\n")[0]) / 2), 200, 800, 100);
    }

    private JButton startButton(int width, int height, int x, int y) {
        RoundedButton startButton = new RoundedButton("CONNECT", 12);
        startButton.setBounds(x, y, width, height);
        startButton.setBackground(new Color(55, 47, 47));
        startButton.setForeground(new Color(255, 255, 255));
        startButton.addActionListener(e -> {
            try {
                RMICraft.INSTANCE.connect();

                RMICraftClientGUI.INSTANCE.changePanel(new PlayingScreen());
            } catch (RemoteException | NotBoundException ex) {
                String msg = ex.getMessage();
                if (msg.contains("Connection refused")) {
                    setError("Connection refused\nIs the server running?");
                } else {
                    setError(ex.getMessage());
                }
                ex.printStackTrace();
            }
        });


        return startButton;
    }

    private static JButton exitButton(int width, int height, int x, int y) {
        RoundedButton startButton = new RoundedButton("EXIT", 12);
        startButton.setBounds(x, y, width, height);
        startButton.setBackground(new Color(106, 25, 25));
        startButton.setForeground(new Color(255, 255, 255));
        startButton.addActionListener(e -> System.exit(0));

        return startButton;
    }
}

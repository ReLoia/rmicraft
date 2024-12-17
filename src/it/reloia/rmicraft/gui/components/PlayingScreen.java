package it.reloia.rmicraft.gui.components;

import it.reloia.rmicraft.client.rmi.RMICraft;
import it.reloia.rmicraft.gui.RMICraftClientGUI;
import it.reloia.rmicraft.gui.components.swing.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;

public class PlayingScreen extends JPanel {
    JTextField chatField;
    
    public PlayingScreen() {
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

        JLabel chatLabel = new JLabel("Send a message to the server");
        chatLabel.setForeground(new Color(255, 255, 255));
        chatLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        chatLabel.setBounds(0, 180, 800, 40);
        chatLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chatLabel.setVisible(true);
        this.add(chatLabel);
        
        chatField = getChatField();
        this.add(chatField);
        
        JButton sendChatButton = getSendButton();
        this.add(sendChatButton);

        
        JButton exitButton = exitButton();
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
    
    private JButton getSendButton() {
        RoundedButton sendButton = new RoundedButton("SEND", 12);
        sendButton.setBounds(800 / 2 + 400 / 2 - 80 / 2, 220, 80, 40);
        sendButton.setBackground(new Color(106, 25, 25));
        sendButton.setForeground(new Color(255, 255, 255));
        sendButton.addActionListener(e -> {
            try {
                RMICraft.INSTANCE.sendChatMessage(chatField.getText());
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            chatField.setText("");
        });
        return sendButton;
    }

    private JTextField getChatField() {
        JTextField chatField = new JTextField();
        int width = 400;
        chatField.setBounds(800 / 2 - width / 2 - 80 / 2, 220, width, 40);
        chatField.setBackground(new Color(255, 255, 255));
        chatField.setForeground(new Color(0, 0, 0));
        chatField.setFont(new Font("Arial", Font.PLAIN, 22));
        chatField.addActionListener(e -> {
            try {
                RMICraft.INSTANCE.sendChatMessage(chatField.getText());
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            chatField.setText("");
        });
        return chatField;
    }

    private JButton exitButton() {
        RoundedButton startButton = new RoundedButton("DISCONNECT", 12);
        startButton.setBounds(800 / 2 - 300 / 2, 480, 300, 40);
        startButton.setBackground(new Color(106, 25, 25));
        startButton.setForeground(new Color(255, 255, 255));
        startButton.addActionListener(e -> {
            RMICraftClientGUI.INSTANCE.changePanel(new MainMenu());
            RMICraft.INSTANCE.disconnect();
        });

        return startButton;
    }
}

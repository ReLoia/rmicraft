package it.reloia.rmicraft.gui;

import it.reloia.rmicraft.gui.components.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RMICraftClientGUI extends JFrame {
    public static final RMICraftClientGUI INSTANCE = new RMICraftClientGUI();

    public int x = 0, y = 0;

    private RMICraftClientGUI() {
        super("RMICraft Client");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        // Con questo il mainFrame viene creato al centro dello schermo.
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(16, 16, 16));

        this.setUndecorated(true);
        this.setShape(new RoundRectangle2D.Double(0, 0, 800, 600, 24, 24));

        this.setLayout(null);

        this.add(new MainMenu());
    }

    public void changePanel(JPanel panel) {
        this.getContentPane().removeAll();
        this.add(panel);
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        INSTANCE.setVisible(true);
    }
}

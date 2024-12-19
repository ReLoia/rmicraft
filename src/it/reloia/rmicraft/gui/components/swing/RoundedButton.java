package it.reloia.rmicraft.gui.components.swing;

// gli import a stella sono usati solo per swing e awt perchè sono molto verbosi (avrei 300 import)
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Custom JButton with rounded corners - copied from the web
 */
public class RoundedButton extends JButton {
    public RoundedButton(String text, int radius) {
        super(text);

        setContentAreaFilled(false);
        setFont(new Font("Arial", Font.PLAIN, 22));

        setBorder(new RoundedBorder(radius));
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else if (e.getID() == MouseEvent.MOUSE_EXITED) {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        super.processMouseEvent(e);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        super.paintComponent(g2);

        g2.dispose();
    }

    // Custom Border class to create rounded border - from the web
    private record RoundedBorder(int radius) implements Border {
        @Override
            public void paintBorder(Component c, Graphics g, int x, int y,
                                    int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(c.getForeground());
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        }
}
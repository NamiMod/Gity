package com.company;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Gity !
 * in this class we can create round border for button
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 1.0
 *
 * Spring 2021
 */
public class RoundBorder implements Border {

    private int radius;

    /**
     * create new round border
     * @param radius radius
     */
    public RoundBorder(int radius) {
        this.radius = radius;
    }

    /**
     * get Border Insets
     * @param c component
     * @return Insets
     */
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    /**
     * is Border Opaque ?
     * @return true ot false
     */
    public boolean isBorderOpaque() {
        return true;
    }

    /**
     * paint border
     * @param c component
     * @param g Graphics
     * @param x x
     * @param y y
     * @param width width
     * @param height height
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}

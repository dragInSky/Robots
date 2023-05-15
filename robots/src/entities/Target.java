package entities;

import java.awt.*;

/**
 * @author draginsky
 * @since 15.05.2023
 */
public class Target {
    public final Point position = new Point(150, 100);

    public void setPosition(Point p) {
        position.x = p.x;
        position.y = p.y;
    }
}

package util;

import java.awt.*;

/**
 * @author draginsky
 * @since 15.05.2023
 */
public class Position {
    public volatile double x;
    public volatile double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
}

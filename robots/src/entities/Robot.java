package entities;

import util.Position;

/**
 * @author draginsky
 * @since 15.05.2023
 */
public class Robot {
    public final Position position = new Position(100, 100);
    public volatile double direction = 0;
    public static final double DURATION = 10;
    public static final double MAX_VELOCITY = 0.25;
    public static final double MAX_ANGULAR_VELOCITY = 0.0075;
}

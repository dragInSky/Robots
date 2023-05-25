package entities;

import util.Position;

/**
 * @author draginsky
 * @since 15.05.2023
 */
public class Robot {
    public final Position position = new Position(100, 100);
    public volatile double direction = 0;
    public final double duration = 10;
    public final double maxVelocity = 0.25;
    public final double maxAngularVelocity = 0.0075;
}

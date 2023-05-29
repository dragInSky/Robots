package util;

/**
 * @author draginsky
 * @since 15.05.2023
 */
public class Geometry {
    public static double distance(Position position1, Position position2) {
        return Math.sqrt((position1.x - position2.x) * (position1.x - position2.x) +
                (position1.y - position2.y) * (position1.y - position2.y));
    }

    public static double angleTo(Position from, Position to) {
        return asNormalizedRadians(Math.atan2((to.y - from.y), (to.x - from.x)));
    }

    public static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;

        return Math.min(value, max);
    }

    public static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }
}

package gui;

import java.awt.*;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

public class GameModelController extends JPanel {
    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private int count = 0;

    private static final double maxVelocity = 0.25;
    private static final double maxAngularVelocity = 0.0075;

    private final GameWindow gameWindow;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GameModelController(GameWindow gameWindow, CoordinatesWindow coordinatesWindow) {
        this.gameWindow = gameWindow;
        support.addPropertyChangeListener(coordinatesWindow);
    }

    public double getRobotPositionX() {
        return m_robotPositionX;
    }
    public double getRobotPositionY() {
        return m_robotPositionY;
    }
    public double getRobotDirection() {
        return m_robotDirection;
    }

    public int getTargetPositionX() {
        return m_targetPositionX;
    }
    public int getTargetPositionY() {
        return m_targetPositionY;
    }

    protected void setTargetPosition(Point p) {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        return asNormalizedRadians(Math.atan2((toY - fromY), (toX - fromX)));
    }

    protected void onModelUpdateEvent() {
        if (distance(m_targetPositionX, m_targetPositionY, m_robotPositionX, m_robotPositionY) < 2) {
            m_robotPositionX = m_targetPositionX;
            m_robotPositionY = m_targetPositionY;
            return;
        }

        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);

        double angularVelocity = 0;
        if (angleToTarget > m_robotDirection + 0.01) {
            angularVelocity = maxAngularVelocity;
        } else if (angleToTarget < m_robotDirection - 0.01) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(maxVelocity, angularVelocity, 10);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;

        return Math.min(value, max);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX, newY;
        if (angularVelocity != 0) {
            newX = m_robotPositionX + velocity / angularVelocity *
                    (Math.sin(m_robotDirection + angularVelocity * duration) -
                            Math.sin(m_robotDirection));
            newY = m_robotPositionY - velocity / angularVelocity *
                    (Math.cos(m_robotDirection + angularVelocity * duration) -
                            Math.cos(m_robotDirection));
        } else {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }

        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);

        int width = gameWindow.getWidth() * 2 - 20;
        if (newX < 0) {
            newX += width;
        } else if (newX > width) {
            newX -= width;
        }

        int height = gameWindow.getHeight() * 2 - 40;
        if (newY < 0) {
            newY += height;
        } else if (newY > height) {
            newY -= height;
        }

        if ((int) m_robotPositionX != (int) newX || (int) m_robotPositionY != (int) newY) {
            count++;
            if (count == 8) {
                support.firePropertyChange("coordinates",
                        new Point((int) m_robotPositionX, (int) m_robotPositionY), new Point((int) newX, (int) newY)
                );
                count = 0;
            }

        }

        m_robotPositionX = newX;
        m_robotPositionY = newY;
        m_robotDirection = newDirection;
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }
}
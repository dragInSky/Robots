package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class GameVisualizer extends JPanel {
    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    private int count = 0;

    private static final double maxVelocity = 0.15;
    private static final double maxAngularVelocity = 0.004;

    private final GameWindow gameWindow;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GameVisualizer(GameWindow gameWindow, CoordinatesWindow coordinatesWindow) {
        this.gameWindow = gameWindow;
        support.addPropertyChangeListener(coordinatesWindow);

        Timer timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 10);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                double scale = Toolkit.getDefaultToolkit().getScreenResolution() / 224.0;
                point.x = (int) (point.x / scale);
                point.y = (int) (point.y / scale);
                setTargetPosition(point);
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void setTargetPosition(Point p) {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        return asNormalizedRadians(Math.atan2((toY - fromY), (toX - fromX)) - Math.PI);
    }

    protected void onModelUpdateEvent() {
        if (distance(m_targetPositionX, m_targetPositionY, m_robotPositionX, m_robotPositionY) < 1) {
            m_robotPositionX = m_targetPositionX;
            m_robotPositionY = m_targetPositionY;
            return;
        }

        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity;
        if (Math.abs(angleToTarget - m_robotDirection) > maxAngularVelocity) {
            angularVelocity = Math.signum(angleToTarget - m_robotDirection) * maxAngularVelocity;
        } else {
            angularVelocity = angleToTarget - m_robotDirection;
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
        double newX, newY;

        if (angularVelocity == 0) {
            newX = m_robotPositionX + velocity * Math.cos(m_robotDirection) * duration;
            newY = m_robotPositionY + velocity * Math.sin(m_robotDirection) * duration;
        } else {
            angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
            newX = m_robotPositionX + velocity / angularVelocity *
                    (Math.sin(m_robotDirection + angularVelocity * duration) -
                            Math.sin(m_robotDirection));
            if (!Double.isFinite(newX)) {
                newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
            }
            newY = m_robotPositionY - velocity / angularVelocity *
                    (Math.cos(m_robotDirection + angularVelocity * duration) -
                            Math.cos(m_robotDirection));
            if (!Double.isFinite(newY)) {
                newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
            }
        }

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
            if (count == 5) {
                support.firePropertyChange("coordinates",
                        new Point((int) m_robotPositionX, (int) m_robotPositionY), new Point((int) newX, (int) newY)
                );
                count = 0;
            }
        }

        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        while (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(m_robotPositionX), round(m_robotPositionY), m_robotDirection);
        drawTarget(g2d, m_targetPositionX, m_targetPositionY);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        int robotCenterX = round(m_robotPositionX);
        int robotCenterY = round(m_robotPositionY);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
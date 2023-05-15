package game;

import entities.Robot;
import entities.Target;
import gui.CoordinatesWindow;
import gui.GameWindow;
import util.Geometry;
import util.Position;

import java.awt.*;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

public class Game extends JPanel {
    private int count = 0;
    private final GameWindow gameWindow;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public final Robot robot = new Robot();
    public final Target target = new Target();

    public Game(GameWindow gameWindow, CoordinatesWindow coordinatesWindow) {
        this.gameWindow = gameWindow;
        support.addPropertyChangeListener(coordinatesWindow);
    }

    protected void onModelUpdateEvent() {
        if (Geometry.distance(
                target.position.x, target.position.y, robot.position.x, robot.position.y
        ) < 2) {
            robot.position.setPosition(target.position);
            return;
        }

        double angleToTarget = Geometry.angleTo(
                robot.position.x, robot.position.y, target.position.x, target.position.y
        );

        double angularVelocity = 0;
        if (angleToTarget > robot.direction + 0.01) {
            angularVelocity = Robot.MAX_ANGULAR_VELOCITY;
        } else if (angleToTarget < robot.direction - 0.01) {
            angularVelocity = -Robot.MAX_ANGULAR_VELOCITY;
        }

        moveRobot(angularVelocity);
    }

    private void moveRobot(double angularVelocity) {
        angularVelocity = Geometry.applyLimits(
                angularVelocity, -Robot.MAX_ANGULAR_VELOCITY, Robot.MAX_ANGULAR_VELOCITY
        );

        double newX, newY;
        if (angularVelocity != 0) {
            newX = robot.position.x + Robot.MAX_VELOCITY / angularVelocity *
                    (Math.sin(robot.direction + angularVelocity * Robot.DURATION) -
                            Math.sin(robot.direction));
            newY = robot.position.y - Robot.MAX_VELOCITY / angularVelocity *
                    (Math.cos(robot.direction + angularVelocity * Robot.DURATION) -
                            Math.cos(robot.direction));
        } else {
            newX = robot.position.x + Robot.MAX_VELOCITY * Robot.DURATION * Math.cos(robot.direction);
            newY = robot.position.y + Robot.MAX_VELOCITY * Robot.DURATION * Math.sin(robot.direction);
        }

        double newDirection = Geometry.asNormalizedRadians(robot.direction + angularVelocity * Robot.DURATION);

        updateCoordinatesChange(newX, newY);

        Position topologyPosition = topology(newX, newY);
        newX = topologyPosition.x;
        newY = topologyPosition.y;

        robot.position.setPosition(newX, newY);
        robot.direction = newDirection;
    }

    private void updateCoordinatesChange(double newX, double newY) {
        if ((int) robot.position.x != (int) newX || (int) robot.position.y != (int) newY) {
            count++;
            if (count == 8) {
                support.firePropertyChange("coordinates",
                        new Point((int) robot.position.x, (int) robot.position.y), new Point((int) newX, (int) newY)
                );
                count = 0;
            }
        }
    }

    private Position topology(double newX, double newY) {
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

        return new Position(newX, newY);
    }
}
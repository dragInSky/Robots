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
        if (Geometry.distance(target.position, robot.position) < 2) {
            robot.position.setPosition(target.position);
            return;
        }

        double angleToTarget = Geometry.angleTo(robot.position, target.position);

        double angularVelocity = 0;
        if (angleToTarget > robot.direction + 0.01) {
            angularVelocity = robot.maxAngularVelocity;
        } else if (angleToTarget < robot.direction - 0.01) {
            angularVelocity = -robot.maxAngularVelocity;
        }

        moveRobot(angularVelocity);
    }

    private void moveRobot(double angularVelocity) {
        angularVelocity = Geometry.applyLimits(
                angularVelocity, -robot.maxAngularVelocity, robot.maxAngularVelocity
        );

        Position newCoordinate = new Position(0, 0);
        if (angularVelocity != 0) {
            newCoordinate.setPosition(
                    robot.position.x + robot.maxVelocity / angularVelocity *
                            (Math.sin(robot.direction + angularVelocity * robot.duration) -
                                    Math.sin(robot.direction)),
                    robot.position.y - robot.maxVelocity / angularVelocity *
                            (Math.cos(robot.direction + angularVelocity * robot.duration) -
                                    Math.cos(robot.direction))
            );
        } else {
            newCoordinate.setPosition(
                    robot.position.x + robot.maxVelocity * robot.duration * Math.cos(robot.direction),
                    robot.position.y + robot.maxVelocity * robot.duration * Math.sin(robot.direction)
            );
        }

        double newDirection = Geometry.asNormalizedRadians(robot.direction + angularVelocity * robot.duration);

        updateCoordinatesChange(newCoordinate);

        topology(newCoordinate);

        robot.position.setPosition(newCoordinate);
        robot.direction = newDirection;
    }

    private void updateCoordinatesChange(Position coordinate) {
        if ((int) robot.position.x != (int) coordinate.x || (int) robot.position.y != (int) coordinate.y) {
            count++;
            if (count == 8) {
                support.firePropertyChange("coordinates",
                        new Point((int) robot.position.x, (int) robot.position.y),
                        new Point((int) coordinate.x, (int) coordinate.y)
                );
                count = 0;
            }
        }
    }

    private void topology(Position coordinate) {
        int width = gameWindow.getWidth() * 2 - 20;
        if (coordinate.x < 0) {
            coordinate.x += width;
        } else if (coordinate.x > width) {
            coordinate.x -= width;
        }

        int height = gameWindow.getHeight() * 2 - 40;
        if (coordinate.y < 0) {
            coordinate.y += height;
        } else if (coordinate.y > height) {
            coordinate.y -= height;
        }
    }
}
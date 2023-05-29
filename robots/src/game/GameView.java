package game;

import gui.CoordinatesWindow;
import gui.GameWindow;
import util.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author draginsky
 * @since 07.05.2023
 */
public class GameView extends JPanel {
    private final Game game;

    public GameView(GameWindow gameWindow, CoordinatesWindow coordinatesWindow) {
        this.game = new Game(gameWindow, coordinatesWindow);

        java.util.Timer timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 10);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                game.onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                double scale = Toolkit.getDefaultToolkit().getScreenResolution() / 224.0;
                point.setLocation(
                        (int) (point.x / scale),
                        (int) (point.y / scale)
                );
                game.target.position.setPosition(point);
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, game.robot.direction);
        drawTarget(g2d, game.target.position);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, double direction) {
        Position position = new Position(
                round(game.robot.position.x),
                round(game.robot.position.y)
        );
        AffineTransform t = AffineTransform.getRotateInstance(direction, position.x, position.y);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, (int) position.x, (int) position.y, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, (int) position.x, (int) position.y, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, (int) position.x + 10, (int) position.y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, (int) position.x + 10, (int) position.y, 5, 5);
    }

    private void drawTarget(Graphics2D g, Position position) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, (int) position.x, (int) position.y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, (int) position.x, (int) position.y, 5, 5);
    }
}

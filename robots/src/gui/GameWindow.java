package gui;

import game.GameView;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame {
    public GameWindow(CoordinatesWindow coordinatesWindow) {
        super("Игровое поле", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GameView(this, coordinatesWindow), BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameWindow(CoordinatesWindow coordinatesWindow, int wight, int height) {
        this(coordinatesWindow);
        setSize(wight, height);
    }
}

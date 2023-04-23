package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame {
    private final GameVisualizer m_visualizer;

    public GameWindow(CoordinatesWindow coordinatesWindow) {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer(this, coordinatesWindow);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameWindow(CoordinatesWindow coordinatesWindow, int wight, int height) {
        this(coordinatesWindow);
        setSize(wight, height);
    }
}

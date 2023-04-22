package gui;

import serialization.SerializableInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GameWindow extends SerializableInternalFrame {
    private final GameVisualizer m_visualizer;

    public GameWindow() {
        super();
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameWindow(int wight, int height) {
        this();
        setSize(wight, height);
    }
}

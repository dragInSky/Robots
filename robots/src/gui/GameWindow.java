package gui;

import serialization.SerializableInternalFrame;

import java.awt.BorderLayout;
import java.util.Properties;
import javax.swing.JPanel;

public class GameWindow extends SerializableInternalFrame {
    public GameWindow(Properties cfg) {
        super("isGameWindowSerializable", "gameWindowOutPath", cfg);
        GameVisualizer m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameWindow(int wight, int height, Properties cfg) {
        this(cfg);
        setSize(wight, height);
    }
}

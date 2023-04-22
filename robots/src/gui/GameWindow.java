package gui;

import serialization.SerializableInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GameWindow extends SerializableInternalFrame {
    private final GameVisualizer m_visualizer;
    private final boolean isSerializable;

    public GameWindow(boolean isSerializable) {
        super();
        this.isSerializable = isSerializable;
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public boolean isSerializable() {
        return isSerializable;
    }
}

package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements SerializableFrame {
    private final GameVisualizer m_visualizer;

    private GameWindow(Object initializer) {
        m_visualizer = (GameVisualizer) initializer;
    }

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public static GameWindow getGameWindowInstance() {
        return new GameWindow(null);
    }

    @Override
    public boolean save(JInternalFrame frame, String outPath) {
        return false;
    }

    @Override
    public boolean load(JInternalFrame frame, String inPath) {
        return false;
    }
}

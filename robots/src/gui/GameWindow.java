package gui;

import locale.LanguageAdapter;

import serialization.SerializableInternalFrame;

import java.awt.BorderLayout;
import java.util.Properties;
import javax.swing.JPanel;

public class GameWindow extends SerializableInternalFrame {
    public GameWindow(LanguageAdapter adapter, Properties cfg) {
        super(adapter, "isGameWindowSerializable", "gameWindowOutPath", cfg);
        GameVisualizer m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameWindow(LanguageAdapter adapter, int wight, int height, Properties cfg) {
        this(adapter, cfg);
        setSize(wight, height);
    }
}

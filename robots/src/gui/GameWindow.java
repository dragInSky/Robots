package gui;

import serialization.FrameState;
import serialization.SerializableFrame;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements SerializableFrame {
    private final GameVisualizer m_visualizer;

    private GameWindow(GameVisualizer gameVisualizer) {
        m_visualizer = gameVisualizer;
    }

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public static GameWindow getInstance() {
        return new GameWindow(null);
    }

    @Override
    public FrameState getFrameState() {
        return new FrameState(this.getSize(), this.getLocation(), this.isIcon, this.isMaximum, this.isSelected);
    }

    @Override
    public void setState(FrameState frameState) {
        try {
            this.setSize(frameState.size);
            this.setLocation(frameState.location);
            this.setIcon(frameState.isIcon);
            this.setMaximum(frameState.isMaximum);
            this.setSelected(frameState.isSelected);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }
}

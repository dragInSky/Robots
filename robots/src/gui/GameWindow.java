package gui;

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
        System.out.println(this.isClosed);
        return new FrameState(this.getHeight(), this.getWidth(), this.getX(), this.getY(),
                this.isClosed, this.isIcon, this.isMaximum, this.isSelected);
    }

    @Override
    public void setState(FrameState frameState) {
        try {
            this.setSize(frameState.width, frameState.height);
            this.setLocation(frameState.xPos, frameState.yPos);
            this.setClosed(frameState.isClosed);
            this.setIcon(frameState.isIcon);
            this.setMaximum(frameState.isMaximum);
            this.setSelected(frameState.isSelected);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }
}

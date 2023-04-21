package gui;

import java.awt.BorderLayout;

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
        return new FrameState(this.getHeight(), this.getWidth(), this.getX(), this.getY(),
                this.isClosed, this.isIcon, this.isMaximum, this.isSelected);
    }

    @Override
    public void setState(FrameState frameState) {
        this.setSize(frameState.width, frameState.height);
        this.setLocation(frameState.xPos, frameState.yPos);
        this.isClosed = frameState.isClosed;
        this.isIcon = frameState.isIcon;
        this.isMaximum = frameState.isMaximum;
        this.isSelected = frameState.isSelected;
    }
}

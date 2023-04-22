package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import serialization.FrameState;
import serialization.SerializableFrame;

public class LogWindow extends JInternalFrame implements LogChangeListener, SerializableFrame {
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;

    private LogWindow(LogWindowSource logWindowSource, TextArea textArea) {
        m_logSource = logWindowSource;
        m_logContent = textArea;
    }

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    public static LogWindow getInstance() {
        return new LogWindow(null, null);
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
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

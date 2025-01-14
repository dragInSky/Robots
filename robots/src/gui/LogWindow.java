package gui;

import java.awt.*;
import java.util.Properties;
import javax.swing.JPanel;

import locale.LanguageAdapter;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import log.Logger;
import serialization.SerializableInternalFrame;

public class LogWindow extends SerializableInternalFrame implements LogChangeListener {
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;

    public LogWindow(LanguageAdapter adapter, LogWindowSource logSource, Properties cfg) {
        super(adapter.translate("Протокол работы"),
                "isLogWindowSerializable", "logWindowOutPath", cfg
        );
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

    public LogWindow(LanguageAdapter adapter, Dimension screenSize, Properties cfg) {
        this(adapter, Logger.getDefaultLogSource(), cfg);

        int width = 300;
        this.setLocation(screenSize.width - width, 0);

        this.setSize(width, screenSize.height);
        setMinimumSize(this.getSize());
        this.pack();
        Logger.debug(adapter.translate("Протокол работает"));
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
}

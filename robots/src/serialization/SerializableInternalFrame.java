package serialization;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Properties;

abstract public class SerializableInternalFrame extends JInternalFrame {
    private final String isSerializableKey;
    private final String outPathKey;
    private final Properties cfg;

    public SerializableInternalFrame(String title, String isSerializableKey, String outPathKey, Properties cfg) {
        super(title, true, true, true, true);
        this.isSerializableKey = isSerializableKey;
        this.outPathKey = outPathKey;
        this.cfg = cfg;
    }

    private FrameState getFrameState() {
        return new FrameState(this.getSize(), this.getLocation(), this.isIcon, this.isMaximum, this.isSelected);
    }

    private void setWindowState(FrameState frameState) {
        try {
            this.setSize(frameState.size);
            this.setLocation(frameState.location);
            this.setIcon(frameState.isIcon);
            this.setMaximum(frameState.isMaximum);
            this.setSelected(frameState.isSelected);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        if (isSerializable()) {
            getFrameState().save(getOutPathKey());
        }
    }

    public void load() {
        String outPath = getOutPathKey();
        if (isSerializable() && new File(outPath).isFile()) {
            FrameState frameState = new FrameState();
            frameState.load(outPath);
            setWindowState(frameState);
        }
    }

    private Boolean isSerializable() {
        return Boolean.parseBoolean(cfg.getProperty(isSerializableKey));
    }

    private String getOutPathKey() {
        return cfg.getProperty(outPathKey);
    }
}

package serialization;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.io.File;

abstract public class SerializableInternalFrame extends JInternalFrame {
    public SerializableInternalFrame() {
        super("Игровое поле", true, true, true, true);
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
            throw new RuntimeException(e);
        }
    }

    public void save(String outPath) {
        getFrameState().save(outPath);
    }

    public void load(String inPath) {
        if (new File(inPath).isFile()) {
            FrameState frameState = new FrameState();
            frameState.load(inPath);
            setWindowState(frameState);
        }
    }
}

package serialization;

import javax.swing.*;
import java.util.Properties;

public class ProgramState implements SerializableStruct {
    public String className;
    private final Properties cfg;

    public ProgramState(Properties cfg) {
        this.cfg = cfg;
        className = UIManager.getLookAndFeel().getName();
    }

    @Override
    public void copy(Object objProgramState) {
        ProgramState tmpProgramState = (ProgramState) objProgramState;
        className = tmpProgramState.className;
    }

    public void save() {
        if (isSerializable()) {
            className = UIManager.getLookAndFeel().getClass().getName();
            this.save(getOutPathKey());
        }
    }

    public void load() {
        if (isSerializable()) {
            this.load(getOutPathKey());
        }
    }

    private Boolean isSerializable() {
        return Boolean.parseBoolean(cfg.getProperty("isProgramStateSerializable"));
    }

    private String getOutPathKey() {
        return cfg.getProperty("programStateOutPath");
    }
}

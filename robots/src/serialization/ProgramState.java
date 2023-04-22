package serialization;

import javax.swing.*;

public class ProgramState implements SerializableStruct {
    public String className;

    public ProgramState() {
        className = UIManager.getLookAndFeel().getName();
    }

    @Override
    public void copy(Object objProgramState) {
        ProgramState tmpProgramState = (ProgramState) objProgramState;
        className = tmpProgramState.className;
    }
}

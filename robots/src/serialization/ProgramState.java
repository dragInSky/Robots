package serialization;

public class ProgramState implements SerializableStruct {
    public String className;
    private final boolean isSerializable;

    public ProgramState(boolean isSerializable, String className) {
        this.isSerializable = isSerializable;
        this.className = className;
    }

    @Override
    public boolean isSerializable() {
        return isSerializable;
    }

    @Override
    public void copy(Object objProgramState) {
        ProgramState tmpProgramState = (ProgramState) objProgramState;
        className = tmpProgramState.className;
    }
}

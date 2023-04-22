package serialization;

public class ProgramState implements SerializableStruct {
    public String className;

    public ProgramState(String className) {
        this.className = className;
    }

    @Override
    public void copy(Object objProgramState) {
        ProgramState tmpProgramState = (ProgramState) objProgramState;
        className = tmpProgramState.className;
    }
}

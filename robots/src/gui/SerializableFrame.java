package gui;

public interface SerializableFrame {
    FrameState getFrameState();

    void setState(FrameState frameState);

    default boolean save(String outPath) {
        return getFrameState().save(outPath);
    }

    default boolean load(String inPath) {
        FrameState frameState = new FrameState();
        boolean isSuccessLoad = frameState.load(inPath);
        if (!isSuccessLoad) {
            return false;
        }
        setState(frameState);
        return true;
    }
}

package serialization;

import java.io.File;

public interface SerializableFrame {
    FrameState getFrameState();

    void setState(FrameState frameState);

    default void save(String outPath) {
        getFrameState().save(outPath);
    }

    default void load(String inPath) {
        if (new File(inPath).isFile()) {
            FrameState frameState = new FrameState();
            frameState.load(inPath);
            setState(frameState);
        }
    }
}

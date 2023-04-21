package gui;

import javax.swing.*;

public interface SerializableFrame {
    default boolean save(JInternalFrame frame, String outPath) {
        return false;
    }

    default boolean load(JInternalFrame frame, String inPath) {
        return false;
    }
}

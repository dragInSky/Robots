package gui;

import serialization.ProgramState;

import java.awt.Frame;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram {
    public static void main(String[] args) {
        String cfgFilePath = "config.properties";
        try (FileInputStream cfgInput = new FileInputStream(cfgFilePath)) {
            Properties cfg = new Properties();
            cfg.load(cfgInput);

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

            SwingUtilities.invokeLater(() -> {
                MainApplicationFrame frame = new MainApplicationFrame(cfg,
                        Boolean.parseBoolean(cfg.getProperty("isGameWindowSerializable")),
                        Boolean.parseBoolean(cfg.getProperty("isLogWindowSerializable")),
                        new ProgramState(
                                Boolean.parseBoolean(cfg.getProperty("isProgramStateSerializable")),
                                UIManager.getLookAndFeel().getName()
                        )
                );
                frame.pack();
                frame.setVisible(true);
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

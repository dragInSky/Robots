package gui;

import java.awt.*;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Properties cfg = configSetUp();

        LogWindow logWindow = new LogWindow(Toolkit.getDefaultToolkit().getScreenSize(), cfg);
        GameWindow gameWindow = new GameWindow(400, 400, cfg);

        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame(cfg, logWindow, gameWindow);
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }

    private static Properties configSetUp() {
        Properties cfg = new Properties();
        String cfgFilePath = "config.properties";
        try (FileInputStream cfgInput = new FileInputStream(cfgFilePath)) {
            cfg.load(cfgInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cfg;
    }
}

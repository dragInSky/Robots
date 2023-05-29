package gui;

import locale.EnglishAdapter;
import locale.LanguageAdapter;

import java.awt.*;
import java.io.FileInputStream;
import java.util.Properties;

import java.awt.Frame;

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

        LanguageAdapter adapter = new EnglishAdapter();

        CoordinatesWindow coordinatesWindow = new CoordinatesWindow(adapter, cfg);
        LogWindow logWindow = new LogWindow(adapter, Toolkit.getDefaultToolkit().getScreenSize(), cfg);
        GameWindow gameWindow = new GameWindow(adapter, coordinatesWindow, 400, 400, cfg);

        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame(
                    adapter, cfg, logWindow, gameWindow, coordinatesWindow
            );
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

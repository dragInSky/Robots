package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.*;

import log.Logger;
import serialization.ProgramState;
import serialization.SerializableInternalFrame;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final SerializableInternalFrame[] windows;
    private final ProgramState programState = new ProgramState();
    private final Properties cfg = new Properties();

    public MainApplicationFrame(SerializableInternalFrame ... windows/*, Adapter adapter*/) {
        this.windows = windows;

        String cfgFilePath = "config.properties";
        try (FileInputStream cfgInput = new FileInputStream(cfgFilePath)) {
            cfg.load(cfgInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        for (SerializableInternalFrame window : windows) {
            addWindow(window);
        }

        loadWindows();

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void updateProgramState() {
        programState.className = UIManager.getLookAndFeel().getClass().getName();
    }

    private void saveWindows() {
        for (SerializableInternalFrame window : windows) {
            if (Boolean.parseBoolean(cfg.getProperty(window.isSerializable()))) {
                window.save(cfg.getProperty(window.getOutPath()));
            }
        }
        if (Boolean.parseBoolean(cfg.getProperty("isProgramStateSerializable"))) {
            updateProgramState();
            programState.save(cfg.getProperty("programStateOutPath"));
        }
    }

    private void loadWindows() {
        for (SerializableInternalFrame window : windows) {
            if (Boolean.parseBoolean(cfg.getProperty(window.isSerializable()))) {
                window.load(cfg.getProperty(window.getOutPath()));
            }
        }
        if (Boolean.parseBoolean(cfg.getProperty("isProgramStateSerializable"))) {
            programState.load(cfg.getProperty("programStateOutPath"));
            setLookAndFeel(programState.className);
        }
        this.invalidate();
    }

    private void programExit(ActionEvent e) {
        int confirmed = JOptionPane.showOptionDialog(null,
                "Вы точно хотите закрыть приложение?",
                "Подтверджение выхода",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"Да", "Нет"}, null
        );

        if (confirmed == JOptionPane.YES_OPTION) {
            saveWindows();
            dispose();
            System.exit(0);
        }
    }

    private JMenu generateMenu(int innerKeyEvent, String title, String description) {
        JMenu jMenu = new JMenu(title);
        jMenu.setMnemonic(innerKeyEvent);
        jMenu.getAccessibleContext().setAccessibleDescription(description);

        return jMenu;
    }

    private void generateMenuItems(JMenu jMenu, int externalKeyEvents, String texts, ActionListener actionListener) {
        JMenuItem jMenuItem = new JMenuItem(texts, externalKeyEvents);
        jMenuItem.addActionListener(actionListener);
        jMenu.add(jMenuItem);
    }

    private JMenu lookAndFeelMenu() {
        JMenu lookAndFeelMenu = generateMenu(KeyEvent.VK_U,
                "Режим отображения", "Управление режимом отображения приложения"
        );
        generateMenuItems(lookAndFeelMenu, KeyEvent.VK_U, "Облачная схема",
                event -> setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel")
        );
        generateMenuItems(lookAndFeelMenu, KeyEvent.VK_U, "Системная схема",
                event -> setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        );
        generateMenuItems(lookAndFeelMenu, KeyEvent.VK_U, "Универсальная схема",
                event -> setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())
        );
        return lookAndFeelMenu;
    }

    private JMenu testMenu() {
        JMenu testMenu = generateMenu(KeyEvent.VK_T, "Тесты", "Тестовые команды");
        generateMenuItems(testMenu, KeyEvent.VK_T, "Сообщение в лог",
                event -> Logger.debug("Новая строка")
        );
        return testMenu;
    }

    private JMenu saveLoadMenu() {
        JMenu saveLoadMenu = generateMenu(KeyEvent.VK_L,
                "Сохранение и загрузка", "Сохранение и загрузка состояния приложения"
        );
        generateMenuItems(saveLoadMenu, KeyEvent.VK_S, "Сохранение", event -> saveWindows());
        generateMenuItems(saveLoadMenu, KeyEvent.VK_L, "Загрузка", event -> loadWindows());
        return saveLoadMenu;
    }

    private JMenu exitMenu() {
        JMenu exitMenu = generateMenu(KeyEvent.VK_Z, "Выход", "Закрытие приложения");
        generateMenuItems(exitMenu, KeyEvent.VK_Z, "Закрытие приложения", this::programExit);
        return exitMenu;
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(lookAndFeelMenu());
        menuBar.add(testMenu());
        menuBar.add(saveLoadMenu());
        menuBar.add(exitMenu());

        return menuBar;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
        this.invalidate();
    }
}

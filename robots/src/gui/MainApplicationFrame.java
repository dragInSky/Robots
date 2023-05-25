package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

import javax.swing.*;

import locale.LanguageAdapter;
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
    private final ProgramState programState;
    private final LanguageAdapter adapter;

    public MainApplicationFrame(LanguageAdapter adapter, Properties cfg, SerializableInternalFrame ... windows) {
        this.adapter = adapter;
        this.windows = windows;
        programState = new ProgramState(cfg);

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

    private void saveWindows() {
        for (SerializableInternalFrame window : windows) {
            window.save();
        }
        programState.save();
    }

    private void loadWindows() {
        for (SerializableInternalFrame window : windows) {
            window.load();
        }
        programState.load();
        setLookAndFeel(programState.className);
        this.invalidate();
    }

    private void programExit(ActionEvent e) {
        int confirmed = JOptionPane.showOptionDialog(
                null,
                adapter.translate("Вы точно хотите закрыть приложение?"),
                adapter.translate("Подтверджение выхода"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{adapter.translate("Да"), adapter.translate("Нет")},
                null
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

    //Либо передавать структуру, либо создавать по одной менюшке
    private JMenuItem generateMenuItems(int externalKeyEvents, String texts, ActionListener actionListener) {
        JMenuItem jMenuItem = new JMenuItem(texts, externalKeyEvents);
        jMenuItem.addActionListener(actionListener);
        return jMenuItem;
    }

    private JMenu lookAndFeelMenu() {
        JMenu lookAndFeelMenu = generateMenu(KeyEvent.VK_V,
                adapter.translate("Режим отображения"),
                adapter.translate("Управление режимом отображения приложения")
        );
        lookAndFeelMenu.add(generateMenuItems(KeyEvent.VK_S, adapter.translate("Системная схема"),
                event -> {
                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    this.invalidate();
                }
        ));
        lookAndFeelMenu.add(generateMenuItems(KeyEvent.VK_U, adapter.translate("Универсальная схема"),
                event -> {
                    setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    this.invalidate();
                }
        ));
        return lookAndFeelMenu;
    }

    private JMenu testMenu() {
        JMenu testMenu = generateMenu(
                KeyEvent.VK_T, adapter.translate("Тесты"), adapter.translate("Тестовые команды")
        );
        testMenu.add(generateMenuItems(KeyEvent.VK_T, adapter.translate("Сообщение в лог"),
                event -> Logger.debug(adapter.translate("Новая строка"))
        ));
        return testMenu;
    }

    private JMenu saveLoadMenu() {
        JMenu saveLoadMenu = generateMenu(
                KeyEvent.VK_L,
                adapter.translate("Сохранение и загрузка"),
                adapter.translate("Сохранение и загрузка состояния приложения")
        );
        saveLoadMenu.add(generateMenuItems(KeyEvent.VK_S, adapter.translate("Сохранение"), event -> saveWindows()));
        saveLoadMenu.add(generateMenuItems(KeyEvent.VK_L, adapter.translate("Загрузка"), event -> loadWindows()));
        return saveLoadMenu;
    }

    private JMenu exitMenu() {
        JMenu exitMenu = generateMenu(
                KeyEvent.VK_Z, adapter.translate("Выход"), adapter.translate("Закрытие приложения")
        );
        exitMenu.add(generateMenuItems(KeyEvent.VK_Z, adapter.translate("Закрытие приложения"), this::programExit));
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

package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame(/*Adapter*/) {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        addWindow(createLogWindow(inset, screenSize));

        addWindow(new GameWindow() {
            {
                setSize(400, 400);
            }
        });

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected LogWindow createLogWindow(int inset, Dimension screenSize) {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

        int width = 300;
        logWindow.setLocation(screenSize.width - width - 10 + inset * 2, 0);

        logWindow.setSize(width, screenSize.height);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        //Logger.debug(adapter.Translate("Протокол работает");
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void programExit(ActionEvent e) {
        int confirmed = JOptionPane.showOptionDialog(
                null,
                "Вы точно хотите закрыть приложение?",
                "Подтверджение выхода",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"Да", "Нет"}, null
        );

        if (confirmed == JOptionPane.YES_OPTION) {
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
                "Режим отображения", "Управление режимом отображения приложения"
        );
        JMenuItem lookAndFeelMenuItem1 = generateMenuItems(KeyEvent.VK_S, "Системная схема",
                event -> {
                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    this.invalidate();
                }
        );
        JMenuItem lookAndFeelMenuItem2 = generateMenuItems(KeyEvent.VK_U, "Универсальная схема",
                event -> {
                    setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    this.invalidate();
                }
        );

        lookAndFeelMenu.add(lookAndFeelMenuItem1);
        lookAndFeelMenu.add(lookAndFeelMenuItem2);
        return lookAndFeelMenu;
    }

    private JMenu testMenu() {
        JMenu testMenu = generateMenu(KeyEvent.VK_T, "Тесты", "Тестовые команды");
        JMenuItem testMenuItem = generateMenuItems(KeyEvent.VK_T, "Сообщение в лог",
                event -> Logger.debug("Новая строка")
        );

        testMenu.add(testMenuItem);
        return testMenu;
    }

    private JMenu exitMenu() {
        JMenu exitMenu = generateMenu(KeyEvent.VK_Z, "Выход", "Закрытие приложения");
        JMenuItem exitMenuItem = generateMenuItems(KeyEvent.VK_Z, "Закрытие приложения", this::programExit);

        exitMenu.add(exitMenuItem);
        return exitMenu;
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(lookAndFeelMenu());
        menuBar.add(testMenu());
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
    }
}

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
    private final GameWindow gameWindow;
    private final LogWindow logWindow;

    public MainApplicationFrame(GameWindow gameWindow, LogWindow logWindow/*, Adapter adapter*/) {
        this.gameWindow = gameWindow;
        this.logWindow = logWindow;

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        boolean successLogWindowLoad = logWindow.load(null, "");
        if (!successLogWindowLoad) {
            logWindow = createLogWindow(inset, screenSize);
        }
        addWindow(logWindow);

        boolean successGameWindowLoad = gameWindow.load(null, "");
        if (!successGameWindowLoad) {
            gameWindow = createGameWindow(400, 400);
        }
        addWindow(gameWindow);

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

    protected GameWindow createGameWindow(int wight, int height) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(wight, height);
        return gameWindow;
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
            gameWindow.save(null, "");
            logWindow.save(null, "");
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
    private void generateMenuItems(
            JMenu jMenu, int externalKeyEvents, String texts, ActionListener actionListener) {
        JMenuItem jMenuItem = new JMenuItem(texts, externalKeyEvents);
        jMenuItem.addActionListener(actionListener);
        jMenu.add(jMenuItem);
    }

    private JMenu lookAndFeelMenu() {
        JMenu lookAndFeelMenu = generateMenu(KeyEvent.VK_V,
                "Режим отображения", "Управление режимом отображения приложения"
        );
        generateMenuItems(lookAndFeelMenu, KeyEvent.VK_S, "Системная схема",
                event -> {
                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    this.invalidate();
                }
        );
        generateMenuItems(lookAndFeelMenu, KeyEvent.VK_U, "Универсальная схема",
                event -> {
                    setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    this.invalidate();
                }
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

    private JMenu exitMenu() {
        JMenu exitMenu = generateMenu(KeyEvent.VK_Z, "Выход", "Закрытие приложения");
        generateMenuItems(exitMenu, KeyEvent.VK_Z, "Закрытие приложения", this::programExit);
        return exitMenu;
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(lookAndFeelMenu());
        menuBar.add(testMenu());
        menuBar.add(exitMenu());
        //menuBar.add(saveLoadMenu());

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

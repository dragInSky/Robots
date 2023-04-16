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

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);


        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }

    private void programExit(ActionEvent e)  {
        JFrame frame = new JFrame();
        String[] options = new String[2];
        options[0] = "Да";
        options[1] = "Нет";

        int confirmed = JOptionPane.showOptionDialog(
                frame.getContentPane(),
                "Вы точно хотите закрыть приложение?",
                "Подтверджение выхода",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, null
        );

        if (confirmed == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    private JMenu generateMenu(int innerKeyEvent, int[] externalKeyEvents,
                               String title, String description, String[] texts,
                               ActionListener[] actionListener) {
        JMenu jMenu = new JMenu(title);
        jMenu.setMnemonic(innerKeyEvent);
        jMenu.getAccessibleContext().setAccessibleDescription(description);

        for (int i = 0; i < externalKeyEvents.length; i++) {
            JMenuItem jMenuItem = new JMenuItem(texts[i], externalKeyEvents[i]);
            jMenuItem.addActionListener(actionListener[i]);
            jMenu.add(jMenuItem);
        }

        return jMenu;
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = generateMenu(KeyEvent.VK_V,
                new int[]{
                        KeyEvent.VK_S,
                        KeyEvent.VK_U},
                "Режим отображения", "Управление режимом отображения приложения",
                new String[]{
                        "Системная схема",
                        "Универсальная схема"},
                new ActionListener[]{
                        event -> {
                            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                            this.invalidate();
                        },
                        event -> {
                            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                            this.invalidate();
                        }}
        );

        JMenu testMenu = generateMenu(KeyEvent.VK_T,
                new int[]{KeyEvent.VK_T},
                "Тесты", "Тестовые команды",
                new String[]{"Сообщение в лог"},
                new ActionListener[]{event -> Logger.debug("Новая строка")}
        );

        JMenu exitMenu = generateMenu(KeyEvent.VK_Z,
                new int[]{KeyEvent.VK_Z},
                "Выход", "Закрытие приложения",
                new String[]{"Закрытие приложения"},
                new ActionListener[]{this::programExit}
        );

//        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
//        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
//        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription("Управление режимом отображения приложения");
//        {
//            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
//            systemLookAndFeel.addActionListener((event) -> {
//                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                this.invalidate();
//            });
//            lookAndFeelMenu.add(systemLookAndFeel);
//        }

//        {
//            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_U);
//            crossplatformLookAndFeel.addActionListener((event) -> {
//                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//                this.invalidate();
//            });
//            lookAndFeelMenu.add(crossplatformLookAndFeel);
//        }

//        JMenu testMenu = new JMenu("Тесты");
//        testMenu.setMnemonic(KeyEvent.VK_T);
//        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
//        {
//            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_T);
//            addLogMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));
//            testMenu.add(addLogMessageItem);
//        }

//        JMenu exitMenu = new JMenu("Выход");
//        exitMenu.setMnemonic(KeyEvent.VK_Z);
//        exitMenu.getAccessibleContext().setAccessibleDescription("Закрытие приложения");
//        {
//            JMenuItem exitItem = new JMenuItem("Закрытие приложения", KeyEvent.VK_Z);
//            exitItem.addActionListener(event -> {
//
//                JFrame frame = new JFrame();
//                String[] options = new String[2];
//                options[0] = "Да";
//                options[1] = "Нет";
//
//                int confirmed = JOptionPane.showOptionDialog(
//                        frame.getContentPane(),
//                        "Вы точно хотите закрыть приложение?",
//                        "Подтверджение выхода",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.INFORMATION_MESSAGE,
//                        null, options, null
//                );
//
//                if (confirmed == JOptionPane.YES_OPTION) {
//                    dispose();
//                    System.exit(0);
//                }
//            });
//            exitMenu.add(exitItem);
//        }

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(exitMenu);
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

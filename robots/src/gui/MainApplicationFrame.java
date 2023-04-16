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
    private final Dimension screenSize;
    private final int inset = 50;

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        addWindow(createLogWindow());

        addWindow(new GameWindow() { { setSize(400, 400); } } );

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

        int width = 300;
        logWindow.setLocation(screenSize.width - width - 10 + inset * 2, 0);

        logWindow.setSize(width, screenSize.height);
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

    private void generateMenuItems(JMenu jMenu,
                                   int[] externalKeyEvents,
                                   String[] texts,
                                   ActionListener[] actionListener) {
        for (int i = 0; i < externalKeyEvents.length; i++) {
            JMenuItem jMenuItem = new JMenuItem(texts[i], externalKeyEvents[i]);
            jMenuItem.addActionListener(actionListener[i]);
            jMenu.add(jMenuItem);
        }
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = generateMenu(
                KeyEvent.VK_V, "Режим отображения", "Управление режимом отображения приложения");
        generateMenuItems(lookAndFeelMenu,
                new int[]{KeyEvent.VK_S, KeyEvent.VK_U},
                new String[]{"Системная схема", "Универсальная схема"},
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

        JMenu testMenu = generateMenu(KeyEvent.VK_T, "Тесты", "Тестовые команды");
        generateMenuItems(testMenu,
                new int[]{KeyEvent.VK_T},
                new String[]{"Сообщение в лог"},
                new ActionListener[]{event -> Logger.debug("Новая строка")}
        );

        JMenu exitMenu = generateMenu(KeyEvent.VK_Z, "Выход", "Закрытие приложения");
        generateMenuItems(exitMenu,
                new int[]{KeyEvent.VK_Z},
                new String[]{"Закрытие приложения"},
                new ActionListener[]{this::programExit}
        );

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

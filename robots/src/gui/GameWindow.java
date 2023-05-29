package gui;

import game.GameView;

import locale.LanguageAdapter;

import serialization.SerializableInternalFrame;

import java.awt.BorderLayout;
import java.util.Properties;
import javax.swing.JPanel;

public class GameWindow extends SerializableInternalFrame {
    public GameWindow(LanguageAdapter adapter, CoordinatesWindow coordinatesWindow, Properties cfg) {
        super(adapter.translate("Игровое поле"),
                "isGameWindowSerializable", "gameWindowOutPath", cfg
        );
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GameView(this, coordinatesWindow), BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public GameWindow(
            LanguageAdapter adapter, CoordinatesWindow coordinatesWindow, int wight, int height, Properties cfg
    ) {
        this(adapter, coordinatesWindow, cfg);
        setSize(wight, height);
    }
}

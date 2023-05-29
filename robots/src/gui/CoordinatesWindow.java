package gui;

import locale.LanguageAdapter;
import serialization.SerializableInternalFrame;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Properties;

import javax.swing.JPanel;

public class CoordinatesWindow extends SerializableInternalFrame implements PropertyChangeListener {
    private final TextArea coordinatesContent;

    public CoordinatesWindow(LanguageAdapter adapter, Properties cfg) {
        super(adapter.translate("Координаты робота"),
                "isCoordinateWindowSerializable", "coordinateWindowOutPath", cfg
        );

        coordinatesContent = new TextArea("");
        coordinatesContent.setSize(300, 100);

        setLocation(400, 0);
        setMinimumSize(getSize());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coordinatesContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        Point newValue = (Point) e.getNewValue();
        String content = "x position is: " + newValue.x + "\ny position is: " + newValue.y;
        coordinatesContent.setText(content);
        coordinatesContent.invalidate();
    }
}

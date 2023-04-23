package gui;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class CoordinatesWindow extends JInternalFrame implements PropertyChangeListener {
    private TextArea coordinatesContent;

    public CoordinatesWindow() {
        super("Координаты робота", true, true, true, true);
        coordinatesContent = new TextArea("");
        coordinatesContent.setSize(300, 100);

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

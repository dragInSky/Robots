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
        coordinatesContent.setSize(300, 400);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coordinatesContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        DoublePoint newValue = (DoublePoint) e.getNewValue();
        String content = "x is: " + newValue.x() + "\ny is: " + newValue.y();
        coordinatesContent.setText(content);
        coordinatesContent.invalidate();
    }
}

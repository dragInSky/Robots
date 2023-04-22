package serialization;

import java.awt.*;

public class FrameState implements SerializableStruct {
    public Dimension size;
    public Point location;
    public boolean isIcon, isMaximum, isSelected;

    public FrameState() {
        fillFields(new Dimension(0, 0), new Point(0, 0),
                false, false, false);
    }

    public FrameState(Dimension size, Point location, boolean isIcon, boolean isMaximum, boolean isSelected) {
        fillFields(size, location, isIcon, isMaximum, isSelected);
    }

    public void fillFields(Dimension size, Point location, boolean isIcon, boolean isMaximum, boolean isSelected) {
        this.size = size;
        this.location = location;
        this.isIcon = isIcon;
        this.isMaximum = isMaximum;
        this.isSelected = isSelected;
    }

    @Override
    public void copy(Object objFrameState) {
        FrameState frameState = (FrameState) objFrameState;
        fillFields(frameState.size, frameState.location,
                frameState.isIcon, frameState.isMaximum, frameState.isSelected);
    }
}

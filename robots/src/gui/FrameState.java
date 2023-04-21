package gui;

import java.io.*;


public class FrameState implements Serializable {
    public int height, width, xPos, yPos;
    public boolean isClosed, isIcon, isMaximum, isSelected;

    public FrameState() {
        this.height = 0;
        this.width = 0;
        this.xPos = 0;
        this.yPos = 0;
        this.isClosed = false;
        this.isIcon = false;
        this.isMaximum = false;
        this.isSelected = false;
    }

    public FrameState(int height, int width, int xPos, int yPos,
                      boolean isClosed, boolean isIcon, boolean isMaximum, boolean isSelected) {
        this.height = height;
        this.width = width;
        this.xPos = xPos;
        this.yPos = yPos;
        this.isClosed = isClosed;
        this.isIcon = isIcon;
        this.isMaximum = isMaximum;
        this.isSelected = isSelected;
    }

    private void copy(FrameState frameState) {
        this.height = frameState.height;
        this.width = frameState.width;
        this.xPos = frameState.xPos;
        this.yPos = frameState.yPos;
        this.isClosed = frameState.isClosed;
        this.isIcon = frameState.isIcon;
        this.isMaximum = frameState.isMaximum;
        this.isSelected = frameState.isSelected;
    }

    public boolean save(String outPath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outPath))) {
            oos.writeObject(this);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean load(String inPath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inPath)))
        {
            FrameState frameState = (FrameState) ois.readObject();
            copy(frameState);
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

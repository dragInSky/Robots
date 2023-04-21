package gui;

import java.io.*;

public class ProgramState implements Serializable {
    public String className;

    ProgramState(String className) {
        this.className = className;
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
            ProgramState programState = (ProgramState) ois.readObject();
            className = programState.className;
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

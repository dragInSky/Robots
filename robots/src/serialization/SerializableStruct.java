package serialization;

import java.io.*;

public interface SerializableStruct extends Serializable {
    void copy(Object object);

    default void save(String outPath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outPath))) {
            oos.writeObject(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    default void load(String inPath) {
        if (new File(inPath).isFile()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inPath))) {
                Object object = ois.readObject();
                copy(object);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

package src.main.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public final class SerializationHelper {

    private SerializationHelper() {}

    public static IDataElement fromByteArray(byte[] serializedData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
        ObjectInput in = null;
        Object object = null;
        try {
            in = new ObjectInputStream(bais);
            object = in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            // ignore close exception
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return (IDataElement) object;
    }

    public static byte[] toByteArray(IDataElement dataElement) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] objBytes = null;

        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(dataElement);
            out.flush();
            objBytes = baos.toByteArray();

        } catch (IOException ex) {
            // ignore close exception
        } finally {
            try {
                baos.close();
            } catch (IOException ioEx) {
                // ignore close exception
            }
        }
        return objBytes;
    }
}

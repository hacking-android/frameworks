/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InvalidPropertiesFormatException
extends IOException {
    private static final long serialVersionUID = 7763056076009360219L;

    public InvalidPropertiesFormatException(String string) {
        super(string);
    }

    public InvalidPropertiesFormatException(Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        super(string);
        this.initCause(throwable);
    }

    private void readObject(ObjectInputStream objectInputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }
}


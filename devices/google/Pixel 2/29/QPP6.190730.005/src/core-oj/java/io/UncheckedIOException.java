/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Objects;

public class UncheckedIOException
extends RuntimeException {
    private static final long serialVersionUID = -8134305061645241065L;

    public UncheckedIOException(IOException iOException) {
        super(Objects.requireNonNull(iOException));
    }

    public UncheckedIOException(String string, IOException iOException) {
        super(string, Objects.requireNonNull(iOException));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (super.getCause() instanceof IOException) {
            return;
        }
        throw new InvalidObjectException("Cause must be an IOException");
    }

    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }
}


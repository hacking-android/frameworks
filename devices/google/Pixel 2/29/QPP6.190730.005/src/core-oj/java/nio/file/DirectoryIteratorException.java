/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public final class DirectoryIteratorException
extends ConcurrentModificationException {
    private static final long serialVersionUID = -6012699886086212874L;

    public DirectoryIteratorException(IOException iOException) {
        super(Objects.requireNonNull(iOException));
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


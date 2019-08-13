/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

final class extObjectInputStream
extends ObjectInputStream {
    private static ClassLoader systemClassLoader = null;

    extObjectInputStream(InputStream inputStream) throws IOException, StreamCorruptedException {
        super(inputStream);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        try {
            Class<?> class_ = super.resolveClass(objectStreamClass);
            return class_;
        }
        catch (ClassNotFoundException classNotFoundException) {
            ClassLoader classLoader;
            ClassLoader classLoader2 = classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                if (systemClassLoader == null) {
                    systemClassLoader = ClassLoader.getSystemClassLoader();
                }
                if ((classLoader2 = systemClassLoader) == null) {
                    throw new ClassNotFoundException(objectStreamClass.getName());
                }
            }
            return Class.forName(objectStreamClass.getName(), false, classLoader2);
        }
    }
}


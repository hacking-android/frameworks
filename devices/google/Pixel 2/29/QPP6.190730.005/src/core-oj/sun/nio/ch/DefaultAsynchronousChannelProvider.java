/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.nio.channels.spi.AsynchronousChannelProvider;

public class DefaultAsynchronousChannelProvider {
    private DefaultAsynchronousChannelProvider() {
    }

    public static AsynchronousChannelProvider create() {
        return DefaultAsynchronousChannelProvider.createProvider("sun.nio.ch.LinuxAsynchronousChannelProvider");
    }

    private static AsynchronousChannelProvider createProvider(String object) {
        try {
            object = Class.forName((String)object);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new AssertionError(classNotFoundException);
        }
        try {
            object = (AsynchronousChannelProvider)((Class)object).newInstance();
            return object;
        }
        catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
            throw new AssertionError(reflectiveOperationException);
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.system.BaseDexClassLoader;
import java.nio.ByteBuffer;

public final class InMemoryDexClassLoader
extends BaseDexClassLoader {
    public InMemoryDexClassLoader(ByteBuffer byteBuffer, ClassLoader classLoader) {
        this(new ByteBuffer[]{byteBuffer}, classLoader);
    }

    public InMemoryDexClassLoader(ByteBuffer[] arrbyteBuffer, ClassLoader classLoader) {
        this(arrbyteBuffer, null, classLoader);
    }

    public InMemoryDexClassLoader(ByteBuffer[] arrbyteBuffer, String string, ClassLoader classLoader) {
        super(arrbyteBuffer, string, classLoader);
    }
}


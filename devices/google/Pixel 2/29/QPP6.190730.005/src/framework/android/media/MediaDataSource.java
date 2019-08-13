/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import java.io.Closeable;
import java.io.IOException;

public abstract class MediaDataSource
implements Closeable {
    public abstract long getSize() throws IOException;

    public abstract int readAt(long var1, byte[] var3, int var4, int var5) throws IOException;
}


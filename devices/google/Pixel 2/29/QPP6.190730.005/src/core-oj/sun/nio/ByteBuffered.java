/*
 * Decompiled with CFR 0.145.
 */
package sun.nio;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ByteBuffered {
    public ByteBuffer getByteBuffer() throws IOException;
}


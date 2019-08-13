/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public interface GatheringByteChannel
extends WritableByteChannel {
    public long write(ByteBuffer[] var1) throws IOException;

    public long write(ByteBuffer[] var1, int var2, int var3) throws IOException;
}


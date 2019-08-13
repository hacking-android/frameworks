/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public interface SeekableByteChannel
extends ByteChannel {
    public long position() throws IOException;

    public SeekableByteChannel position(long var1) throws IOException;

    @Override
    public int read(ByteBuffer var1) throws IOException;

    public long size() throws IOException;

    public SeekableByteChannel truncate(long var1) throws IOException;

    @Override
    public int write(ByteBuffer var1) throws IOException;
}


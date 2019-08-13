/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;

public interface WritableByteChannel
extends Channel {
    public int write(ByteBuffer var1) throws IOException;
}


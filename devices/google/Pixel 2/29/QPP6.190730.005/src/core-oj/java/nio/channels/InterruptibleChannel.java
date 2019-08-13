/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.channels.Channel;

public interface InterruptibleChannel
extends Channel {
    @Override
    public void close() throws IOException;
}


/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.channels.spi.SelectorProvider;

public abstract class SelectableChannel
extends AbstractInterruptibleChannel
implements Channel {
    protected SelectableChannel() {
    }

    public abstract Object blockingLock();

    public abstract SelectableChannel configureBlocking(boolean var1) throws IOException;

    public abstract boolean isBlocking();

    public abstract boolean isRegistered();

    public abstract SelectionKey keyFor(Selector var1);

    public abstract SelectorProvider provider();

    public final SelectionKey register(Selector selector, int n) throws ClosedChannelException {
        return this.register(selector, n, null);
    }

    public abstract SelectionKey register(Selector var1, int var2, Object var3) throws ClosedChannelException;

    public abstract int validOps();
}


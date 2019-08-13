/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.nio.channels.SelectableChannel;
import java.nio.channels.Selector;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public abstract class SelectionKey {
    public static final int OP_ACCEPT = 16;
    public static final int OP_CONNECT = 8;
    public static final int OP_READ = 1;
    public static final int OP_WRITE = 4;
    private static final AtomicReferenceFieldUpdater<SelectionKey, Object> attachmentUpdater = AtomicReferenceFieldUpdater.newUpdater(SelectionKey.class, Object.class, "attachment");
    private volatile Object attachment = null;

    protected SelectionKey() {
    }

    public final Object attach(Object object) {
        return attachmentUpdater.getAndSet(this, object);
    }

    public final Object attachment() {
        return this.attachment;
    }

    public abstract void cancel();

    public abstract SelectableChannel channel();

    public abstract int interestOps();

    public abstract SelectionKey interestOps(int var1);

    public final boolean isAcceptable() {
        boolean bl = (this.readyOps() & 16) != 0;
        return bl;
    }

    public final boolean isConnectable() {
        boolean bl = (this.readyOps() & 8) != 0;
        return bl;
    }

    public final boolean isReadable() {
        int n = this.readyOps();
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public abstract boolean isValid();

    public final boolean isWritable() {
        boolean bl = (this.readyOps() & 4) != 0;
        return bl;
    }

    public abstract int readyOps();

    public abstract Selector selector();
}


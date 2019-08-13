/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import sun.nio.ch.AsynchronousFileChannelImpl;
import sun.nio.ch.FileChannelImpl;

public class FileLockImpl
extends FileLock {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile boolean valid = true;

    FileLockImpl(AsynchronousFileChannel asynchronousFileChannel, long l, long l2, boolean bl) {
        super(asynchronousFileChannel, l, l2, bl);
    }

    FileLockImpl(FileChannel fileChannel, long l, long l2, boolean bl) {
        super(fileChannel, l, l2, bl);
    }

    void invalidate() {
        this.valid = false;
    }

    @Override
    public boolean isValid() {
        return this.valid;
    }

    @Override
    public void release() throws IOException {
        synchronized (this) {
            Object object = this.acquiredBy();
            if (object.isOpen()) {
                if (this.valid) {
                    if (object instanceof FileChannelImpl) {
                        ((FileChannelImpl)object).release(this);
                    } else {
                        if (!(object instanceof AsynchronousFileChannelImpl)) {
                            object = new AssertionError();
                            throw object;
                        }
                        ((AsynchronousFileChannelImpl)object).release(this);
                    }
                    this.valid = false;
                }
                return;
            }
            object = new ClosedChannelException();
            throw object;
        }
    }
}


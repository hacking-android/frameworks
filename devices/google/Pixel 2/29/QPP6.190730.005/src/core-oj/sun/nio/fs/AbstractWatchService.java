/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import sun.nio.fs.AbstractWatchKey;

abstract class AbstractWatchService
implements WatchService {
    private final WatchKey CLOSE_KEY = new AbstractWatchKey(null, null){

        @Override
        public void cancel() {
        }

        @Override
        public boolean isValid() {
            return true;
        }
    };
    private final Object closeLock = new Object();
    private volatile boolean closed;
    private final LinkedBlockingDeque<WatchKey> pendingKeys = new LinkedBlockingDeque();

    protected AbstractWatchService() {
    }

    private void checkKey(WatchKey watchKey) {
        if (watchKey == this.CLOSE_KEY) {
            this.enqueueKey(watchKey);
        }
        this.checkOpen();
    }

    private void checkOpen() {
        if (!this.closed) {
            return;
        }
        throw new ClosedWatchServiceException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void close() throws IOException {
        Object object = this.closeLock;
        synchronized (object) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.implClose();
            this.pendingKeys.clear();
            this.pendingKeys.offer(this.CLOSE_KEY);
            return;
        }
    }

    final Object closeLock() {
        return this.closeLock;
    }

    final void enqueueKey(WatchKey watchKey) {
        this.pendingKeys.offer(watchKey);
    }

    abstract void implClose() throws IOException;

    final boolean isOpen() {
        return this.closed ^ true;
    }

    @Override
    public final WatchKey poll() {
        this.checkOpen();
        WatchKey watchKey = this.pendingKeys.poll();
        this.checkKey(watchKey);
        return watchKey;
    }

    @Override
    public final WatchKey poll(long l, TimeUnit object) throws InterruptedException {
        this.checkOpen();
        object = this.pendingKeys.poll(l, (TimeUnit)((Object)object));
        this.checkKey((WatchKey)object);
        return object;
    }

    abstract WatchKey register(Path var1, WatchEvent.Kind<?>[] var2, WatchEvent.Modifier ... var3) throws IOException;

    @Override
    public final WatchKey take() throws InterruptedException {
        this.checkOpen();
        WatchKey watchKey = this.pendingKeys.take();
        this.checkKey(watchKey);
        return watchKey;
    }

}


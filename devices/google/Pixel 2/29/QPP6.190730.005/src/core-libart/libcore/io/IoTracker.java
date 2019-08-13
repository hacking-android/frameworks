/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import dalvik.system.BlockGuard;

public final class IoTracker {
    private boolean isOpen = true;
    private Mode mode = Mode.READ;
    private int opCount;
    private int totalByteCount;

    public void reset() {
        this.opCount = 0;
        this.totalByteCount = 0;
    }

    public void trackIo(int n) {
        ++this.opCount;
        this.totalByteCount += n;
        if (this.isOpen && this.opCount > 10 && this.totalByteCount < 5120) {
            BlockGuard.getThreadPolicy().onUnbufferedIO();
            this.isOpen = false;
        }
    }

    public void trackIo(int n, Mode mode) {
        if (this.mode != mode) {
            this.reset();
            this.mode = mode;
        }
        this.trackIo(n);
    }

    public static enum Mode {
        READ,
        WRITE;
        
    }

}


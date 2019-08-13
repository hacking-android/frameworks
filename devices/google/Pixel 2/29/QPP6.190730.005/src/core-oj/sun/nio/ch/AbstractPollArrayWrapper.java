/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import sun.nio.ch.AllocatedNativeObject;

public abstract class AbstractPollArrayWrapper {
    static final short EVENT_OFFSET = 4;
    static final short FD_OFFSET = 0;
    static final short REVENT_OFFSET = 6;
    static final short SIZE_POLLFD = 8;
    protected AllocatedNativeObject pollArray;
    protected long pollArrayAddress;
    protected int totalChannels = 0;

    int getDescriptor(int n) {
        return this.pollArray.getInt(n * 8 + 0);
    }

    int getEventOps(int n) {
        return this.pollArray.getShort(n * 8 + 4);
    }

    int getReventOps(int n) {
        return this.pollArray.getShort(n * 8 + 6);
    }

    void putDescriptor(int n, int n2) {
        this.pollArray.putInt(n * 8 + 0, n2);
    }

    void putEventOps(int n, int n2) {
        this.pollArray.putShort(n * 8 + 4, (short)n2);
    }

    void putReventOps(int n, int n2) {
        this.pollArray.putShort(n * 8 + 6, (short)n2);
    }
}


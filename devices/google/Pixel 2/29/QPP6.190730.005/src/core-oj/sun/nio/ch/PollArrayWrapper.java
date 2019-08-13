/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import sun.nio.ch.AbstractPollArrayWrapper;
import sun.nio.ch.AllocatedNativeObject;
import sun.nio.ch.IOUtil;
import sun.nio.ch.Net;
import sun.nio.ch.SelChImpl;

public class PollArrayWrapper
extends AbstractPollArrayWrapper {
    int interruptFD;

    PollArrayWrapper(int n) {
        this.pollArray = new AllocatedNativeObject((n + 1) * 8, false);
        this.pollArrayAddress = this.pollArray.address();
        this.totalChannels = 1;
    }

    private static native void interrupt(int var0);

    private native int poll0(long var1, int var3, long var4);

    static void replaceEntry(PollArrayWrapper pollArrayWrapper, int n, PollArrayWrapper pollArrayWrapper2, int n2) {
        pollArrayWrapper2.putDescriptor(n2, pollArrayWrapper.getDescriptor(n));
        pollArrayWrapper2.putEventOps(n2, pollArrayWrapper.getEventOps(n));
        pollArrayWrapper2.putReventOps(n2, pollArrayWrapper.getReventOps(n));
    }

    void addEntry(SelChImpl selChImpl) {
        this.putDescriptor(this.totalChannels, IOUtil.fdVal(selChImpl.getFD()));
        this.putEventOps(this.totalChannels, 0);
        this.putReventOps(this.totalChannels, 0);
        ++this.totalChannels;
    }

    void free() {
        this.pollArray.free();
    }

    void grow(int n) {
        PollArrayWrapper pollArrayWrapper = new PollArrayWrapper(n);
        for (n = 0; n < this.totalChannels; ++n) {
            PollArrayWrapper.replaceEntry(this, n, pollArrayWrapper, n);
        }
        this.pollArray.free();
        this.pollArray = pollArrayWrapper.pollArray;
        this.pollArrayAddress = this.pollArray.address();
    }

    void initInterrupt(int n, int n2) {
        this.interruptFD = n2;
        this.putDescriptor(0, n);
        this.putEventOps(0, Net.POLLIN);
        this.putReventOps(0, 0);
    }

    public void interrupt() {
        PollArrayWrapper.interrupt(this.interruptFD);
    }

    int poll(int n, int n2, long l) {
        return this.poll0(this.pollArrayAddress + (long)(n2 * 8), n, l);
    }

    void release(int n) {
    }
}


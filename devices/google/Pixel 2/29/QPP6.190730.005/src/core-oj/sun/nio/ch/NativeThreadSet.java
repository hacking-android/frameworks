/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import sun.nio.ch.NativeThread;

class NativeThreadSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long[] elts;
    private int used = 0;
    private boolean waitingToEmpty;

    NativeThreadSet(int n) {
        this.elts = new long[n];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int add() {
        long l;
        long l2 = l = NativeThread.current();
        if (l == 0L) {
            l2 = -1L;
        }
        synchronized (this) {
            int n = 0;
            if (this.used >= this.elts.length) {
                n = this.elts.length;
                long[] arrl = new long[n * 2];
                System.arraycopy((Object)this.elts, 0, (Object)arrl, 0, n);
                this.elts = arrl;
            }
            while (n < this.elts.length) {
                if (this.elts[n] == 0L) {
                    this.elts[n] = l2;
                    ++this.used;
                    return n;
                }
                ++n;
            }
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void remove(int n) {
        synchronized (this) {
            this.elts[n] = 0L;
            --this.used;
            if (this.used == 0 && this.waitingToEmpty) {
                this.notifyAll();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void signalAndWait() {
        synchronized (this) {
            boolean bl = false;
            do {
                int n;
                int n2;
                if (this.used > 0) {
                    n2 = this.used;
                    n = this.elts.length;
                } else {
                    if (bl) {
                        Thread.currentThread().interrupt();
                    }
                    return;
                }
                for (int i = 0; i < n; ++i) {
                    int n3;
                    long l = this.elts[i];
                    if (l == 0L) continue;
                    if (l != -1L) {
                        NativeThread.signal(l);
                    }
                    n2 = n3 = n2 - 1;
                    if (n3 == 0) break;
                }
                this.waitingToEmpty = true;
                try {
                    this.wait(50L);
                    continue;
                }
                catch (InterruptedException interruptedException) {
                    bl = true;
                    continue;
                }
                finally {
                    this.waitingToEmpty = false;
                    continue;
                }
                break;
            } while (true);
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.SystemClock;

public class ConditionVariable {
    private volatile boolean mCondition;

    public ConditionVariable() {
        this.mCondition = false;
    }

    public ConditionVariable(boolean bl) {
        this.mCondition = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void block() {
        synchronized (this) {
            boolean bl;
            while (!(bl = this.mCondition)) {
                try {
                    this.wait();
                }
                catch (InterruptedException interruptedException) {
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean block(long l) {
        if (l == 0L) {
            this.block();
            return true;
        }
        synchronized (this) {
            boolean bl;
            long l2 = SystemClock.elapsedRealtime();
            long l3 = l2 + l;
            l = l2;
            while (!(bl = this.mCondition)) {
                if (l >= l3) return this.mCondition;
                try {
                    this.wait(l3 - l);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                l = SystemClock.elapsedRealtime();
            }
            return this.mCondition;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        synchronized (this) {
            this.mCondition = false;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void open() {
        synchronized (this) {
            boolean bl = this.mCondition;
            this.mCondition = true;
            if (!bl) {
                this.notifyAll();
            }
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package java.util;

public abstract class TimerTask
implements Runnable {
    static final int CANCELLED = 3;
    static final int EXECUTED = 2;
    static final int SCHEDULED = 1;
    static final int VIRGIN = 0;
    final Object lock = new Object();
    long nextExecutionTime;
    long period = 0L;
    int state = 0;

    protected TimerTask() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean cancel() {
        Object object = this.lock;
        synchronized (object) {
            int n = this.state;
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.state = 3;
            return bl;
        }
    }

    @Override
    public abstract void run();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long scheduledExecutionTime() {
        Object object = this.lock;
        synchronized (object) {
            if (this.period >= 0L) return this.nextExecutionTime - this.period;
            return this.nextExecutionTime + this.period;
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ICURWLock {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Stats stats = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void acquireRead() {
        if (this.stats != null) {
            synchronized (this) {
                Stats stats = this.stats;
                ++stats._rc;
                if (this.rwl.getReadLockCount() > 0) {
                    stats = this.stats;
                    ++stats._mrc;
                }
                if (this.rwl.isWriteLocked()) {
                    stats = this.stats;
                    ++stats._wrc;
                }
            }
        }
        this.rwl.readLock().lock();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void acquireWrite() {
        if (this.stats != null) {
            synchronized (this) {
                Stats stats = this.stats;
                ++stats._wc;
                if (this.rwl.getReadLockCount() > 0 || this.rwl.isWriteLocked()) {
                    stats = this.stats;
                    ++stats._wwc;
                }
            }
        }
        this.rwl.writeLock().lock();
    }

    public Stats clearStats() {
        synchronized (this) {
            Stats stats = this.stats;
            this.stats = null;
            return stats;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Stats getStats() {
        synchronized (this) {
            block4 : {
                if (this.stats != null) break block4;
                return null;
            }
            return new Stats(this.stats);
        }
    }

    public void releaseRead() {
        this.rwl.readLock().unlock();
    }

    public void releaseWrite() {
        this.rwl.writeLock().unlock();
    }

    public Stats resetStats() {
        synchronized (this) {
            Stats stats;
            Stats stats2 = this.stats;
            this.stats = stats = new Stats();
            return stats2;
        }
    }

    public static final class Stats {
        public int _mrc;
        public int _rc;
        public int _wc;
        public int _wrc;
        public int _wwc;

        private Stats() {
        }

        private Stats(int n, int n2, int n3, int n4, int n5) {
            this._rc = n;
            this._mrc = n2;
            this._wrc = n3;
            this._wc = n4;
            this._wwc = n5;
        }

        private Stats(Stats stats) {
            this(stats._rc, stats._mrc, stats._wrc, stats._wc, stats._wwc);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" rc: ");
            stringBuilder.append(this._rc);
            stringBuilder.append(" mrc: ");
            stringBuilder.append(this._mrc);
            stringBuilder.append(" wrc: ");
            stringBuilder.append(this._wrc);
            stringBuilder.append(" wc: ");
            stringBuilder.append(this._wc);
            stringBuilder.append(" wwc: ");
            stringBuilder.append(this._wwc);
            return stringBuilder.toString();
        }
    }

}


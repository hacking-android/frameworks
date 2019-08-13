/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.os.SystemClock;
import com.android.internal.util.Preconditions;

public class TokenBucket {
    private int mAvailable;
    private final int mCapacity;
    private final int mFillDelta;
    private long mLastFill;

    public TokenBucket(int n, int n2) {
        this(n, n2, n2);
    }

    public TokenBucket(int n, int n2, int n3) {
        this.mFillDelta = Preconditions.checkArgumentPositive(n, "deltaMs must be strictly positive");
        this.mCapacity = Preconditions.checkArgumentPositive(n2, "capacity must be strictly positive");
        this.mAvailable = Math.min(Preconditions.checkArgumentNonnegative(n3), this.mCapacity);
        this.mLastFill = this.scaledTime();
    }

    private void fill() {
        long l = this.scaledTime();
        int n = (int)(l - this.mLastFill);
        this.mAvailable = Math.min(this.mCapacity, this.mAvailable + n);
        this.mLastFill = l;
    }

    private long scaledTime() {
        return SystemClock.elapsedRealtime() / (long)this.mFillDelta;
    }

    public int available() {
        this.fill();
        return this.mAvailable;
    }

    public int capacity() {
        return this.mCapacity;
    }

    public int get(int n) {
        this.fill();
        if (n <= 0) {
            return 0;
        }
        int n2 = this.mAvailable;
        if (n > n2) {
            n = this.mAvailable;
            this.mAvailable = 0;
            return n;
        }
        this.mAvailable = n2 - n;
        return n;
    }

    public boolean get() {
        boolean bl = true;
        if (this.get(1) != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean has() {
        this.fill();
        boolean bl = this.mAvailable > 0;
        return bl;
    }

    public void reset(int n) {
        Preconditions.checkArgumentNonnegative(n);
        this.mAvailable = Math.min(n, this.mCapacity);
        this.mLastFill = this.scaledTime();
    }
}


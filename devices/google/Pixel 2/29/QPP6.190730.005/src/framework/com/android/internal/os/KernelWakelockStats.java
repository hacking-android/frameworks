/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import java.util.HashMap;

public class KernelWakelockStats
extends HashMap<String, Entry> {
    int kernelWakelockVersion;

    public static class Entry {
        public int mCount;
        public long mTotalTime;
        public int mVersion;

        Entry(int n, long l, int n2) {
            this.mCount = n;
            this.mTotalTime = l;
            this.mVersion = n2;
        }
    }

}


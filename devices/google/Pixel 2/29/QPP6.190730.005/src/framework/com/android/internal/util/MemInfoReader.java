/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.os.Debug;
import android.os.StrictMode;

public final class MemInfoReader {
    final long[] mInfos = new long[15];

    @UnsupportedAppUsage
    public long getCachedSize() {
        return this.getCachedSizeKb() * 1024L;
    }

    public long getCachedSizeKb() {
        long[] arrl = this.mInfos;
        return arrl[2] + arrl[6] + arrl[3] - arrl[11];
    }

    @UnsupportedAppUsage
    public long getFreeSize() {
        return this.mInfos[1] * 1024L;
    }

    public long getFreeSizeKb() {
        return this.mInfos[1];
    }

    public long getKernelUsedSize() {
        return this.getKernelUsedSizeKb() * 1024L;
    }

    public long getKernelUsedSizeKb() {
        long[] arrl = this.mInfos;
        return arrl[4] + arrl[7] + arrl[12] + arrl[13] + arrl[14];
    }

    @UnsupportedAppUsage
    public long[] getRawInfo() {
        return this.mInfos;
    }

    public long getSwapFreeSizeKb() {
        return this.mInfos[9];
    }

    public long getSwapTotalSizeKb() {
        return this.mInfos[8];
    }

    @UnsupportedAppUsage
    public long getTotalSize() {
        return this.mInfos[0] * 1024L;
    }

    public long getTotalSizeKb() {
        return this.mInfos[0];
    }

    public long getZramTotalSizeKb() {
        return this.mInfos[10];
    }

    @UnsupportedAppUsage
    public void readMemInfo() {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.allowThreadDiskReads();
        try {
            Debug.getMemInfo(this.mInfos);
            return;
        }
        finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }
}


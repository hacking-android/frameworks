/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public class KernelSingleUidTimeReader {
    private static final boolean DBG = false;
    private static final String PROC_FILE_DIR = "/proc/uid/";
    private static final String PROC_FILE_NAME = "/time_in_state";
    private static final String TAG = KernelSingleUidTimeReader.class.getName();
    @VisibleForTesting
    public static final int TOTAL_READ_ERROR_COUNT = 5;
    private static final String UID_TIMES_PROC_FILE = "/proc/uid_time_in_state";
    @GuardedBy(value={"this"})
    private final int mCpuFreqsCount;
    @GuardedBy(value={"this"})
    private boolean mCpuFreqsCountVerified;
    private final Injector mInjector;
    @GuardedBy(value={"this"})
    private SparseArray<long[]> mLastUidCpuTimeMs = new SparseArray();
    @GuardedBy(value={"this"})
    private int mReadErrorCounter;
    @GuardedBy(value={"this"})
    private boolean mSingleUidCpuTimesAvailable = true;

    KernelSingleUidTimeReader(int n) {
        this(n, new Injector());
    }

    public KernelSingleUidTimeReader(int n, Injector injector) {
        this.mInjector = injector;
        this.mCpuFreqsCount = n;
        if (this.mCpuFreqsCount == 0) {
            this.mSingleUidCpuTimesAvailable = false;
        }
    }

    private long[] readCpuTimesFromByteBuffer(ByteBuffer byteBuffer) {
        long[] arrl = new long[this.mCpuFreqsCount];
        for (int i = 0; i < this.mCpuFreqsCount; ++i) {
            arrl[i] = byteBuffer.getLong() * 10L;
        }
        return arrl;
    }

    private void verifyCpuFreqsCount(int n, String string2) {
        if (this.mCpuFreqsCount == (n /= 8)) {
            this.mCpuFreqsCountVerified = true;
            return;
        }
        this.mSingleUidCpuTimesAvailable = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Freq count didn't match,count from /proc/uid_time_in_state=");
        stringBuilder.append(this.mCpuFreqsCount);
        stringBuilder.append(", butcount from ");
        stringBuilder.append(string2);
        stringBuilder.append("=");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long[] computeDelta(int n, long[] arrl) {
        synchronized (this) {
            boolean bl;
            if (!this.mSingleUidCpuTimesAvailable) {
                return null;
            }
            long[] arrl2 = this.getDeltaLocked(this.mLastUidCpuTimeMs.get(n), arrl);
            if (arrl2 == null) {
                return null;
            }
            boolean bl2 = false;
            int n2 = arrl2.length - 1;
            do {
                bl = bl2;
                if (n2 < 0) break;
                if (arrl2[n2] > 0L) {
                    bl = true;
                    break;
                }
                --n2;
            } while (true);
            if (bl) {
                this.mLastUidCpuTimeMs.put(n, arrl);
                return arrl2;
            }
            return null;
        }
    }

    @GuardedBy(value={"this"})
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public long[] getDeltaLocked(long[] arrl, long[] arrl2) {
        int n;
        for (n = arrl2.length - 1; n >= 0; --n) {
            if (arrl2[n] >= 0L) continue;
            return null;
        }
        if (arrl == null) {
            return arrl2;
        }
        long[] arrl3 = new long[arrl2.length];
        for (n = arrl2.length - 1; n >= 0; --n) {
            arrl3[n] = arrl2[n] - arrl[n];
            if (arrl3[n] >= 0L) continue;
            return null;
        }
        return arrl3;
    }

    @VisibleForTesting
    public SparseArray<long[]> getLastUidCpuTimeMs() {
        return this.mLastUidCpuTimeMs;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long[] readDeltaMs(int n) {
        synchronized (this) {
            if (!this.mSingleUidCpuTimesAvailable) {
                return null;
            }
            long[] arrl = new StringBuilder(PROC_FILE_DIR);
            arrl.append(n);
            arrl.append(PROC_FILE_NAME);
            arrl = arrl.toString();
            try {
                byte[] arrby = this.mInjector.readData((String)arrl);
                if (!this.mCpuFreqsCountVerified) {
                    this.verifyCpuFreqsCount(arrby.length, (String)arrl);
                }
                arrl = ByteBuffer.wrap(arrby);
                arrl.order(ByteOrder.nativeOrder());
                arrl = this.readCpuTimesFromByteBuffer((ByteBuffer)arrl);
                return this.computeDelta(n, arrl);
            }
            catch (Exception exception) {
                this.mReadErrorCounter = n = this.mReadErrorCounter + 1;
                if (n < 5) return null;
                this.mSingleUidCpuTimesAvailable = false;
                return null;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeUid(int n) {
        synchronized (this) {
            this.mLastUidCpuTimeMs.delete(n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeUidsInRange(int n, int n2) {
        if (n2 < n) {
            return;
        }
        synchronized (this) {
            this.mLastUidCpuTimeMs.put(n, null);
            this.mLastUidCpuTimeMs.put(n2, null);
            n = this.mLastUidCpuTimeMs.indexOfKey(n);
            n2 = this.mLastUidCpuTimeMs.indexOfKey(n2);
            this.mLastUidCpuTimeMs.removeAtRange(n, n2 - n + 1);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAllUidsCpuTimesMs(SparseArray<long[]> sparseArray) {
        synchronized (this) {
            this.mLastUidCpuTimeMs.clear();
            int n = sparseArray.size() - 1;
            while (n >= 0) {
                long[] arrl = sparseArray.valueAt(n);
                if (arrl != null) {
                    this.mLastUidCpuTimeMs.put(sparseArray.keyAt(n), (long[])arrl.clone());
                }
                --n;
            }
            return;
        }
    }

    @VisibleForTesting
    public void setSingleUidCpuTimesAvailable(boolean bl) {
        this.mSingleUidCpuTimesAvailable = bl;
    }

    public boolean singleUidCpuTimesAvailable() {
        return this.mSingleUidCpuTimesAvailable;
    }

    @VisibleForTesting
    public static class Injector {
        public byte[] readData(String string2) throws IOException {
            return Files.readAllBytes(Paths.get(string2, new String[0]));
        }
    }

}


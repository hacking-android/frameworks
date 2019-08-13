/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.KernelCpuThreadReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KernelCpuThreadReaderDiff {
    private static final int OTHER_THREADS_ID = -1;
    private static final String OTHER_THREADS_NAME = "__OTHER_THREADS";
    private static final String TAG = "KernelCpuThreadReaderDiff";
    private int mMinimumTotalCpuUsageMillis;
    private Map<ThreadKey, int[]> mPreviousCpuUsage;
    private final KernelCpuThreadReader mReader;

    @VisibleForTesting
    public KernelCpuThreadReaderDiff(KernelCpuThreadReader kernelCpuThreadReader, int n) {
        this.mReader = kernelCpuThreadReader;
        this.mMinimumTotalCpuUsageMillis = n;
        this.mPreviousCpuUsage = null;
    }

    private static void addToCpuUsage(int[] arrn, int[] arrn2) {
        for (int i = 0; i < arrn.length; ++i) {
            arrn[i] = arrn[i] + arrn2[i];
        }
    }

    private void applyThresholding(KernelCpuThreadReader.ProcessCpuUsage processCpuUsage) {
        int[] arrn = null;
        ArrayList<KernelCpuThreadReader.ThreadCpuUsage> arrayList = new ArrayList<KernelCpuThreadReader.ThreadCpuUsage>();
        for (int i = 0; i < processCpuUsage.threadCpuUsages.size(); ++i) {
            int[] arrn2;
            KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(i);
            if (this.mMinimumTotalCpuUsageMillis > KernelCpuThreadReaderDiff.totalCpuUsage(threadCpuUsage.usageTimesMillis)) {
                arrn2 = arrn;
                if (arrn == null) {
                    arrn2 = new int[threadCpuUsage.usageTimesMillis.length];
                }
                KernelCpuThreadReaderDiff.addToCpuUsage(arrn2, threadCpuUsage.usageTimesMillis);
            } else {
                arrayList.add(threadCpuUsage);
                arrn2 = arrn;
            }
            arrn = arrn2;
        }
        if (arrn != null) {
            arrayList.add(new KernelCpuThreadReader.ThreadCpuUsage(-1, OTHER_THREADS_NAME, arrn));
        }
        processCpuUsage.threadCpuUsages = arrayList;
    }

    private static void changeToDiffs(Map<ThreadKey, int[]> map, KernelCpuThreadReader.ProcessCpuUsage processCpuUsage) {
        for (int i = 0; i < processCpuUsage.threadCpuUsages.size(); ++i) {
            int[] arrn;
            KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(i);
            int[] arrn2 = arrn = map.get(new ThreadKey(processCpuUsage.processId, threadCpuUsage.threadId, processCpuUsage.processName, threadCpuUsage.threadName));
            if (arrn == null) {
                arrn2 = new int[threadCpuUsage.usageTimesMillis.length];
            }
            threadCpuUsage.usageTimesMillis = KernelCpuThreadReaderDiff.cpuTimeDiff(threadCpuUsage.usageTimesMillis, arrn2);
        }
    }

    private static int[] cpuTimeDiff(int[] arrn, int[] arrn2) {
        int[] arrn3 = new int[arrn.length];
        for (int i = 0; i < arrn.length; ++i) {
            arrn3[i] = arrn[i] - arrn2[i];
        }
        return arrn3;
    }

    private static Map<ThreadKey, int[]> createCpuUsageMap(List<KernelCpuThreadReader.ProcessCpuUsage> list) {
        ArrayMap<ThreadKey, int[]> arrayMap = new ArrayMap<ThreadKey, int[]>();
        for (int i = 0; i < list.size(); ++i) {
            KernelCpuThreadReader.ProcessCpuUsage processCpuUsage = list.get(i);
            for (int j = 0; j < processCpuUsage.threadCpuUsages.size(); ++j) {
                KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(j);
                arrayMap.put(new ThreadKey(processCpuUsage.processId, threadCpuUsage.threadId, processCpuUsage.processName, threadCpuUsage.threadName), threadCpuUsage.usageTimesMillis);
            }
        }
        return arrayMap;
    }

    private static int totalCpuUsage(int[] arrn) {
        int n = 0;
        for (int i = 0; i < arrn.length; ++i) {
            n += arrn[i];
        }
        return n;
    }

    public int[] getCpuFrequenciesKhz() {
        return this.mReader.getCpuFrequenciesKhz();
    }

    public ArrayList<KernelCpuThreadReader.ProcessCpuUsage> getProcessCpuUsageDiffed() {
        int n;
        Object object;
        Map<ThreadKey, int[]> map;
        Map<ThreadKey, int[]> map2 = map = null;
        ArrayList<KernelCpuThreadReader.ProcessCpuUsage> arrayList = this.mReader.getProcessCpuUsage();
        map2 = map;
        map2 = map = KernelCpuThreadReaderDiff.createCpuUsageMap(arrayList);
        try {
            object = this.mPreviousCpuUsage;
            if (object == null) {
                this.mPreviousCpuUsage = map;
                return null;
            }
            n = 0;
        }
        catch (Throwable throwable) {
            this.mPreviousCpuUsage = map2;
            throw throwable;
        }
        do {
            map2 = map;
            if (n >= arrayList.size()) break;
            map2 = map;
            object = arrayList.get(n);
            map2 = map;
            KernelCpuThreadReaderDiff.changeToDiffs(this.mPreviousCpuUsage, (KernelCpuThreadReader.ProcessCpuUsage)object);
            map2 = map;
            this.applyThresholding((KernelCpuThreadReader.ProcessCpuUsage)object);
            ++n;
        } while (true);
        this.mPreviousCpuUsage = map;
        return arrayList;
    }

    void setMinimumTotalCpuUsageMillis(int n) {
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Negative minimumTotalCpuUsageMillis: ");
            stringBuilder.append(n);
            Slog.w(TAG, stringBuilder.toString());
            return;
        }
        this.mMinimumTotalCpuUsageMillis = n;
    }

    private static class ThreadKey {
        private final int mProcessId;
        private final int mProcessNameHash;
        private final int mThreadId;
        private final int mThreadNameHash;

        ThreadKey(int n, int n2, String string2, String string3) {
            this.mProcessId = n;
            this.mThreadId = n2;
            this.mProcessNameHash = Objects.hash(string2);
            this.mThreadNameHash = Objects.hash(string3);
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof ThreadKey;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (ThreadKey)object;
            bl = bl2;
            if (this.mProcessId == ((ThreadKey)object).mProcessId) {
                bl = bl2;
                if (this.mThreadId == ((ThreadKey)object).mThreadId) {
                    bl = bl2;
                    if (this.mProcessNameHash == ((ThreadKey)object).mProcessNameHash) {
                        bl = bl2;
                        if (this.mThreadNameHash == ((ThreadKey)object).mThreadNameHash) {
                            bl = true;
                        }
                    }
                }
            }
            return bl;
        }

        public int hashCode() {
            return Objects.hash(this.mProcessId, this.mThreadId, this.mProcessNameHash, this.mThreadNameHash);
        }
    }

}


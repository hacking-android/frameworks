/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.StrictMode;
import android.os.SystemClock;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.KernelCpuProcStringReader;
import com.android.internal.os.PowerProfile;
import com.android.internal.util.Preconditions;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class KernelCpuUidTimeReader<T> {
    protected static final boolean DEBUG = false;
    private static final long DEFAULT_MIN_TIME_BETWEEN_READ = 1000L;
    private long mLastReadTimeMs = 0L;
    final SparseArray<T> mLastTimes = new SparseArray();
    private long mMinTimeBetweenRead = 1000L;
    final KernelCpuProcStringReader mReader;
    final String mTag = this.getClass().getSimpleName();
    final boolean mThrottle;

    KernelCpuUidTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, boolean bl) {
        this.mReader = kernelCpuProcStringReader;
        this.mThrottle = bl;
    }

    public void readAbsolute(Callback<T> callback) {
        if (!this.mThrottle) {
            this.readAbsoluteImpl(callback);
            return;
        }
        long l = SystemClock.elapsedRealtime();
        if (l < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        this.readAbsoluteImpl(callback);
        this.mLastReadTimeMs = l;
    }

    abstract void readAbsoluteImpl(Callback<T> var1);

    public void readDelta(Callback<T> callback) {
        if (!this.mThrottle) {
            this.readDeltaImpl(callback);
            return;
        }
        long l = SystemClock.elapsedRealtime();
        if (l < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        this.readDeltaImpl(callback);
        this.mLastReadTimeMs = l;
    }

    abstract void readDeltaImpl(Callback<T> var1);

    public void removeUid(int n) {
        this.mLastTimes.delete(n);
    }

    public void removeUidsInRange(int n, int n2) {
        if (n2 < n) {
            String string2 = this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("start UID ");
            stringBuilder.append(n);
            stringBuilder.append(" > end UID ");
            stringBuilder.append(n2);
            Slog.e(string2, stringBuilder.toString());
            return;
        }
        this.mLastTimes.put(n, null);
        this.mLastTimes.put(n2, null);
        n = this.mLastTimes.indexOfKey(n);
        n2 = this.mLastTimes.indexOfKey(n2);
        this.mLastTimes.removeAtRange(n, n2 - n + 1);
    }

    public void setThrottle(long l) {
        if (this.mThrottle && l >= 0L) {
            this.mMinTimeBetweenRead = l;
        }
    }

    public static interface Callback<T> {
        public void onUidCpuTime(int var1, T var2);
    }

    public static class KernelCpuUidActiveTimeReader
    extends KernelCpuUidTimeReader<Long> {
        private long[] mBuffer;
        private int mCores = 0;

        private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
            if (throwable != null) {
                try {
                    autoCloseable.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                autoCloseable.close();
            }
        }

        @VisibleForTesting
        public KernelCpuUidActiveTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, boolean bl) {
            super(kernelCpuProcStringReader, bl);
        }

        public KernelCpuUidActiveTimeReader(boolean bl) {
            super(KernelCpuProcStringReader.getActiveTimeReaderInstance(), bl);
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator object) {
            if (object != null && ((KernelCpuProcStringReader.ProcFileIterator)object).hasNextLine()) {
                object = ((KernelCpuProcStringReader.ProcFileIterator)object).nextLine();
                if (this.mCores > 0) {
                    return true;
                }
                CharSequence charSequence = ((CharBuffer)object).toString();
                if (!((String)charSequence).startsWith("cpus:")) {
                    String string2 = this.mTag;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Malformed uid_concurrent_active_time line: ");
                    ((StringBuilder)charSequence).append(object);
                    Slog.wtf(string2, ((StringBuilder)charSequence).toString());
                    return false;
                }
                int n = Integer.parseInt(((String)charSequence).substring(5).trim(), 10);
                if (n <= 0) {
                    charSequence = this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Malformed uid_concurrent_active_time line: ");
                    stringBuilder.append(object);
                    Slog.wtf((String)charSequence, stringBuilder.toString());
                    return false;
                }
                this.mCores = n;
                this.mBuffer = new long[this.mCores + 1];
                return true;
            }
            return false;
        }

        private static long sumActiveTime(long[] arrl) {
            double d = 0.0;
            for (int i = 1; i < arrl.length; ++i) {
                d += (double)arrl[i] * 10.0 / (double)i;
            }
            return (long)d;
        }

        @Override
        void readAbsoluteImpl(Callback<Long> callback) {
            KernelCpuProcStringReader.ProcFileIterator procFileIterator;
            block10 : {
                block11 : {
                    procFileIterator = this.mReader.open(this.mThrottle ^ true);
                    try {
                        boolean bl = this.checkPrecondition(procFileIterator);
                        if (bl) break block10;
                        if (procFileIterator == null) break block11;
                    }
                    catch (Throwable throwable) {
                        try {
                            throw throwable;
                        }
                        catch (Throwable throwable2) {
                            if (procFileIterator != null) {
                                KernelCpuUidActiveTimeReader.$closeResource(throwable, procFileIterator);
                            }
                            throw throwable2;
                        }
                    }
                    KernelCpuUidActiveTimeReader.$closeResource(null, procFileIterator);
                }
                return;
            }
            do {
                CharBuffer charBuffer = procFileIterator.nextLine();
                if (charBuffer == null) break;
                if (KernelCpuProcStringReader.asLongs(charBuffer, this.mBuffer) != this.mBuffer.length) {
                    String string2 = this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid line: ");
                    stringBuilder.append(charBuffer.toString());
                    Slog.wtf(string2, stringBuilder.toString());
                    continue;
                }
                long l = KernelCpuUidActiveTimeReader.sumActiveTime(this.mBuffer);
                if (l <= 0L) continue;
                callback.onUidCpuTime((int)this.mBuffer[0], l);
                continue;
                break;
            } while (true);
            KernelCpuUidActiveTimeReader.$closeResource(null, procFileIterator);
            return;
        }

        @Override
        void readDeltaImpl(Callback<Long> callback) {
            KernelCpuProcStringReader.ProcFileIterator procFileIterator;
            block13 : {
                block14 : {
                    procFileIterator = this.mReader.open(this.mThrottle ^ true);
                    try {
                        boolean bl = this.checkPrecondition(procFileIterator);
                        if (bl) break block13;
                        if (procFileIterator == null) break block14;
                    }
                    catch (Throwable throwable) {
                        try {
                            throw throwable;
                        }
                        catch (Throwable throwable2) {
                            if (procFileIterator != null) {
                                KernelCpuUidActiveTimeReader.$closeResource(throwable, procFileIterator);
                            }
                            throw throwable2;
                        }
                    }
                    KernelCpuUidActiveTimeReader.$closeResource(null, procFileIterator);
                }
                return;
            }
            do {
                long l;
                CharSequence charSequence;
                String string2;
                block15 : {
                    charSequence = procFileIterator.nextLine();
                    if (charSequence == null) break;
                    if (KernelCpuProcStringReader.asLongs((CharBuffer)charSequence, this.mBuffer) != this.mBuffer.length) {
                        string2 = this.mTag;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid line: ");
                        stringBuilder.append(((CharBuffer)charSequence).toString());
                        Slog.wtf(string2, stringBuilder.toString());
                        continue;
                    }
                    int n = (int)this.mBuffer[0];
                    long l2 = KernelCpuUidActiveTimeReader.sumActiveTime(this.mBuffer);
                    if (l2 <= 0L) continue;
                    l = l2 - this.mLastTimes.get(n, 0L);
                    if (l <= 0L) break block15;
                    this.mLastTimes.put(n, l2);
                    if (callback == null) continue;
                    callback.onUidCpuTime(n, l);
                    continue;
                }
                if (l >= 0L) continue;
                string2 = this.mTag;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Negative delta from active time proc: ");
                ((StringBuilder)charSequence).append(l);
                Slog.e(string2, ((StringBuilder)charSequence).toString());
                continue;
                break;
            } while (true);
            KernelCpuUidActiveTimeReader.$closeResource(null, procFileIterator);
            return;
        }
    }

    public static class KernelCpuUidClusterTimeReader
    extends KernelCpuUidTimeReader<long[]> {
        private long[] mBuffer;
        private int[] mCoresOnClusters;
        private long[] mCurTime;
        private long[] mDeltaTime;
        private int mNumClusters;
        private int mNumCores;

        private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
            if (throwable != null) {
                try {
                    autoCloseable.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                autoCloseable.close();
            }
        }

        @VisibleForTesting
        public KernelCpuUidClusterTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, boolean bl) {
            super(kernelCpuProcStringReader, bl);
        }

        public KernelCpuUidClusterTimeReader(boolean bl) {
            super(KernelCpuProcStringReader.getClusterTimeReaderInstance(), bl);
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator object) {
            if (object != null && ((KernelCpuProcStringReader.ProcFileIterator)object).hasNextLine()) {
                int n;
                object = ((KernelCpuProcStringReader.ProcFileIterator)object).nextLine();
                if (this.mNumClusters > 0) {
                    return true;
                }
                Object object2 = ((CharBuffer)object).toString().split(" ");
                if (((String[])object2).length % 2 != 0) {
                    object2 = this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Malformed uid_concurrent_policy_time line: ");
                    stringBuilder.append(object);
                    Slog.wtf((String)object2, stringBuilder.toString());
                    return false;
                }
                Object object3 = new int[((String[])object2).length / 2];
                int n2 = 0;
                for (n = 0; n < ((int[])object3).length; ++n) {
                    if (!object2[n * 2].startsWith("policy")) {
                        object3 = this.mTag;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Malformed uid_concurrent_policy_time line: ");
                        ((StringBuilder)object2).append(object);
                        Slog.wtf((String)object3, ((StringBuilder)object2).toString());
                        return false;
                    }
                    object3[n] = Integer.parseInt(object2[n * 2 + 1], 10);
                    n2 += object3[n];
                }
                this.mNumClusters = ((int[])object3).length;
                this.mNumCores = n2;
                this.mCoresOnClusters = object3;
                this.mBuffer = new long[n2 + 1];
                n = this.mNumClusters;
                this.mCurTime = new long[n];
                this.mDeltaTime = new long[n];
                return true;
            }
            return false;
        }

        private void sumClusterTime() {
            int n = 1;
            for (int i = 0; i < this.mNumClusters; ++i) {
                double d = 0.0;
                int n2 = 1;
                while (n2 <= this.mCoresOnClusters[i]) {
                    d += (double)this.mBuffer[n] * 10.0 / (double)n2;
                    ++n2;
                    ++n;
                }
                this.mCurTime[i] = (long)d;
            }
        }

        @Override
        void readAbsoluteImpl(Callback<long[]> callback) {
            KernelCpuProcStringReader.ProcFileIterator procFileIterator;
            block9 : {
                block10 : {
                    procFileIterator = this.mReader.open(this.mThrottle ^ true);
                    try {
                        boolean bl = this.checkPrecondition(procFileIterator);
                        if (bl) break block9;
                        if (procFileIterator == null) break block10;
                    }
                    catch (Throwable throwable) {
                        try {
                            throw throwable;
                        }
                        catch (Throwable throwable2) {
                            if (procFileIterator != null) {
                                KernelCpuUidClusterTimeReader.$closeResource(throwable, procFileIterator);
                            }
                            throw throwable2;
                        }
                    }
                    KernelCpuUidClusterTimeReader.$closeResource(null, procFileIterator);
                }
                return;
            }
            do {
                CharBuffer charBuffer = procFileIterator.nextLine();
                if (charBuffer == null) break;
                if (KernelCpuProcStringReader.asLongs(charBuffer, this.mBuffer) != this.mBuffer.length) {
                    String string2 = this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid line: ");
                    stringBuilder.append(charBuffer.toString());
                    Slog.wtf(string2, stringBuilder.toString());
                    continue;
                }
                this.sumClusterTime();
                callback.onUidCpuTime((int)this.mBuffer[0], this.mCurTime);
                continue;
                break;
            } while (true);
            KernelCpuUidClusterTimeReader.$closeResource(null, procFileIterator);
            return;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        void readDeltaImpl(Callback<long[]> var1_1) {
            block14 : {
                var2_3 = this.mReader.open(this.mThrottle ^ true);
                try {
                    var3_4 = this.checkPrecondition(var2_3);
                    if (var3_4) break block14;
                    if (var2_3 == null) return;
                }
                catch (Throwable var4_6) {
                    try {
                        throw var4_6;
                    }
                    catch (Throwable var1_2) {
                        if (var2_3 == null) throw var1_2;
                        KernelCpuUidClusterTimeReader.$closeResource(var4_6, var2_3);
                        throw var1_2;
                    }
                }
                KernelCpuUidClusterTimeReader.$closeResource(null, var2_3);
                return;
            }
            do {
                do {
                    if ((var4_5 = var2_3.nextLine()) != null) {
                        if (KernelCpuProcStringReader.asLongs((CharBuffer)var4_5, this.mBuffer) != this.mBuffer.length) {
                            var5_8 = this.mTag;
                            var6_12 = new StringBuilder();
                            var6_12.append("Invalid line: ");
                            var6_12.append(var4_5.toString());
                            Slog.wtf(var5_8, var6_12.toString());
                            continue;
                        }
                        var7_13 = (int)this.mBuffer[0];
                        var4_5 = var5_9 = (long[])this.mLastTimes.get(var7_13);
                        if (var5_9 == null) {
                            var4_5 = new long[this.mNumClusters];
                            this.mLastTimes.put(var7_13, var4_5);
                        }
                        this.sumClusterTime();
                        var8_14 = true;
                        var9_15 = false;
                    } else {
                        KernelCpuUidClusterTimeReader.$closeResource(null, var2_3);
                        return;
                    }
                    for (var10_16 = 0; var10_16 < this.mNumClusters; var9_15 |= var11_17, ++var10_16) {
                        this.mDeltaTime[var10_16] = this.mCurTime[var10_16] - var4_5[var10_16];
                        if (this.mDeltaTime[var10_16] < 0L) {
                            var5_11 = this.mTag;
                            var6_12 = new StringBuilder();
                            var6_12.append("Negative delta from cluster time proc: ");
                            var6_12.append(this.mDeltaTime[var10_16]);
                            Slog.e(var5_11, var6_12.toString());
                            var8_14 = false;
                        }
                        var11_17 = this.mDeltaTime[var10_16] > 0L;
                    }
                    if (var9_15 && var8_14) ** break;
                } while (true);
                break;
            } while (true);
            {
                System.arraycopy(this.mCurTime, 0, var4_5, 0, this.mNumClusters);
                if (var1_1 == null) continue;
                var1_1.onUidCpuTime(var7_13, this.mDeltaTime);
                continue;
            }
        }
    }

    public static class KernelCpuUidFreqTimeReader
    extends KernelCpuUidTimeReader<long[]> {
        private static final int MAX_ERROR_COUNT = 5;
        private static final String UID_TIMES_PROC_FILE = "/proc/uid_time_in_state";
        private boolean mAllUidTimesAvailable = true;
        private long[] mBuffer;
        private long[] mCpuFreqs;
        private long[] mCurTimes;
        private long[] mDeltaTimes;
        private int mErrors = 0;
        private int mFreqCount = 0;
        private boolean mPerClusterTimesAvailable;
        private final Path mProcFilePath;

        private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
            if (throwable != null) {
                try {
                    autoCloseable.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                autoCloseable.close();
            }
        }

        @VisibleForTesting
        public KernelCpuUidFreqTimeReader(String string2, KernelCpuProcStringReader kernelCpuProcStringReader, boolean bl) {
            super(kernelCpuProcStringReader, bl);
            this.mProcFilePath = Paths.get(string2, new String[0]);
        }

        public KernelCpuUidFreqTimeReader(boolean bl) {
            this(UID_TIMES_PROC_FILE, KernelCpuProcStringReader.getFreqTimeReaderInstance(), bl);
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator object) {
            boolean bl = false;
            if (object != null && ((KernelCpuProcStringReader.ProcFileIterator)object).hasNextLine()) {
                object = ((KernelCpuProcStringReader.ProcFileIterator)object).nextLine();
                if (this.mCpuFreqs != null) {
                    return true;
                }
                if (this.readFreqs(((CharBuffer)object).toString()) != null) {
                    bl = true;
                }
                return bl;
            }
            return false;
        }

        private void copyToCurTimes() {
            for (int i = 0; i < this.mFreqCount; ++i) {
                this.mCurTimes[i] = this.mBuffer[i + 1] * 10L;
            }
        }

        private IntArray extractClusterInfoFromProcFileFreqs() {
            int n;
            IntArray intArray = new IntArray();
            int n2 = 0;
            for (int i = 0; i < (n = this.mFreqCount); ++i) {
                int n3 = n2 + 1;
                if (i + 1 != n) {
                    long[] arrl = this.mCpuFreqs;
                    n2 = n3;
                    if (arrl[i + 1] > arrl[i]) continue;
                }
                intArray.add(n3);
                n2 = 0;
            }
            return intArray;
        }

        private long[] readFreqs(String string2) {
            if (string2 == null) {
                return null;
            }
            Object object = string2.split(" ");
            if (((String[])object).length <= 1) {
                object = this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Malformed freq line: ");
                stringBuilder.append(string2);
                Slog.wtf((String)object, stringBuilder.toString());
                return null;
            }
            int n = this.mFreqCount = ((String[])object).length - 1;
            this.mCpuFreqs = new long[n];
            this.mCurTimes = new long[n];
            this.mDeltaTimes = new long[n];
            this.mBuffer = new long[n + 1];
            for (n = 0; n < this.mFreqCount; ++n) {
                this.mCpuFreqs[n] = Long.parseLong(object[n + 1], 10);
            }
            return this.mCpuFreqs;
        }

        public boolean allUidTimesAvailable() {
            return this.mAllUidTimesAvailable;
        }

        public SparseArray<long[]> getAllUidCpuFreqTimeMs() {
            return this.mLastTimes;
        }

        public boolean perClusterTimesAvailable() {
            return this.mPerClusterTimesAvailable;
        }

        @Override
        void readAbsoluteImpl(Callback<long[]> callback) {
            KernelCpuProcStringReader.ProcFileIterator procFileIterator;
            block9 : {
                block10 : {
                    procFileIterator = this.mReader.open(this.mThrottle ^ true);
                    try {
                        boolean bl = this.checkPrecondition(procFileIterator);
                        if (bl) break block9;
                        if (procFileIterator == null) break block10;
                    }
                    catch (Throwable throwable) {
                        try {
                            throw throwable;
                        }
                        catch (Throwable throwable2) {
                            if (procFileIterator != null) {
                                KernelCpuUidFreqTimeReader.$closeResource(throwable, procFileIterator);
                            }
                            throw throwable2;
                        }
                    }
                    KernelCpuUidFreqTimeReader.$closeResource(null, procFileIterator);
                }
                return;
            }
            do {
                CharBuffer charBuffer = procFileIterator.nextLine();
                if (charBuffer == null) break;
                if (KernelCpuProcStringReader.asLongs(charBuffer, this.mBuffer) != this.mBuffer.length) {
                    String string2 = this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid line: ");
                    stringBuilder.append(charBuffer.toString());
                    Slog.wtf(string2, stringBuilder.toString());
                    continue;
                }
                this.copyToCurTimes();
                callback.onUidCpuTime((int)this.mBuffer[0], this.mCurTimes);
                continue;
                break;
            } while (true);
            KernelCpuUidFreqTimeReader.$closeResource(null, procFileIterator);
            return;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        void readDeltaImpl(Callback<long[]> var1_1) {
            block14 : {
                var2_3 = this.mReader.open(this.mThrottle ^ true);
                try {
                    var3_4 = this.checkPrecondition(var2_3);
                    if (var3_4) break block14;
                    if (var2_3 == null) return;
                }
                catch (Throwable var5_7) {
                    try {
                        throw var5_7;
                    }
                    catch (Throwable var1_2) {
                        if (var2_3 == null) throw var1_2;
                        KernelCpuUidFreqTimeReader.$closeResource(var5_7, var2_3);
                        throw var1_2;
                    }
                }
                KernelCpuUidFreqTimeReader.$closeResource(null, var2_3);
                return;
            }
            do {
                do {
                    if ((var4_5 = var2_3.nextLine()) != null) {
                        if (KernelCpuProcStringReader.asLongs((CharBuffer)var4_5, this.mBuffer) != this.mBuffer.length) {
                            var5_6 = this.mTag;
                            var6_9 = new StringBuilder();
                            var6_9.append("Invalid line: ");
                            var6_9.append(var4_5.toString());
                            Slog.wtf((String)var5_6, var6_9.toString());
                            continue;
                        }
                        var7_13 = (int)this.mBuffer[0];
                        var5_6 = var6_10 = (long[])this.mLastTimes.get(var7_13);
                        if (var6_10 == null) {
                            var5_6 = new long[this.mFreqCount];
                            this.mLastTimes.put(var7_13, var5_6);
                        }
                        this.copyToCurTimes();
                        var8_14 = false;
                        var9_15 = true;
                    } else {
                        KernelCpuUidFreqTimeReader.$closeResource(null, var2_3);
                        return;
                    }
                    for (var10_16 = 0; var10_16 < this.mFreqCount; var8_14 |= var11_17, ++var10_16) {
                        this.mDeltaTimes[var10_16] = this.mCurTimes[var10_16] - var5_6[var10_16];
                        if (this.mDeltaTimes[var10_16] < 0L) {
                            var6_12 = this.mTag;
                            var4_5 = new StringBuilder();
                            var4_5.append("Negative delta from freq time proc: ");
                            var4_5.append(this.mDeltaTimes[var10_16]);
                            Slog.e(var6_12, var4_5.toString());
                            var9_15 = false;
                        }
                        var11_17 = this.mDeltaTimes[var10_16] > 0L;
                    }
                    if (var8_14 && var9_15) ** break;
                } while (true);
                break;
            } while (true);
            {
                System.arraycopy(this.mCurTimes, 0, var5_6, 0, this.mFreqCount);
                if (var1_1 == null) continue;
                var1_1.onUidCpuTime(var7_13, this.mDeltaTimes);
                continue;
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public long[] readFreqs(PowerProfile object) {
            Object object2;
            block19 : {
                int n2;
                int n;
                block18 : {
                    Throwable throwable42;
                    block16 : {
                        Object object3;
                        block17 : {
                            block15 : {
                                Preconditions.checkNotNull(object);
                                object2 = this.mCpuFreqs;
                                if (object2 != null) {
                                    return object2;
                                }
                                if (!this.mAllUidTimesAvailable) {
                                    return null;
                                }
                                n = StrictMode.allowThreadDiskReadsMask();
                                object2 = Files.newBufferedReader(this.mProcFilePath);
                                object3 = this.readFreqs(((BufferedReader)object2).readLine());
                                if (object3 != null) break block15;
                                KernelCpuUidFreqTimeReader.$closeResource(null, (AutoCloseable)object2);
                                StrictMode.setThreadPolicyMask(n);
                                return null;
                            }
                            KernelCpuUidFreqTimeReader.$closeResource(null, (AutoCloseable)object2);
                            StrictMode.setThreadPolicyMask(n);
                            object2 = this.extractClusterInfoFromProcFileFreqs();
                            n2 = ((PowerProfile)object).getNumCpuClusters();
                            if (((IntArray)object2).size() != n2) break block17;
                            this.mPerClusterTimesAvailable = true;
                            break block18;
                        }
                        this.mPerClusterTimesAvailable = false;
                        break block19;
                        catch (Throwable throwable2) {
                            try {
                                throw throwable2;
                            }
                            catch (Throwable throwable3) {
                                if (object2 == null) throw throwable3;
                                try {
                                    KernelCpuUidFreqTimeReader.$closeResource(throwable2, (AutoCloseable)object2);
                                    throw throwable3;
                                }
                                catch (Throwable throwable42) {
                                    break block16;
                                }
                                catch (IOException iOException) {
                                    int n3;
                                    this.mErrors = n3 = this.mErrors + 1;
                                    if (n3 >= 5) {
                                        this.mAllUidTimesAvailable = false;
                                    }
                                    object3 = this.mTag;
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Failed to read /proc/uid_time_in_state: ");
                                    ((StringBuilder)object).append(iOException);
                                    Slog.e((String)object3, ((StringBuilder)object).toString());
                                    StrictMode.setThreadPolicyMask(n);
                                    return null;
                                }
                            }
                        }
                    }
                    StrictMode.setThreadPolicyMask(n);
                    throw throwable42;
                }
                for (n = 0; n < n2; ++n) {
                    if (((IntArray)object2).get(n) == ((PowerProfile)object).getNumSpeedStepsInCpuCluster(n)) continue;
                    this.mPerClusterTimesAvailable = false;
                    break;
                }
            }
            object = this.mTag;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("mPerClusterTimesAvailable=");
            ((StringBuilder)object2).append(this.mPerClusterTimesAvailable);
            Slog.i((String)object, ((StringBuilder)object2).toString());
            return this.mCpuFreqs;
        }
    }

    public static class KernelCpuUidUserSysTimeReader
    extends KernelCpuUidTimeReader<long[]> {
        private static final String REMOVE_UID_PROC_FILE = "/proc/uid_cputime/remove_uid_range";
        private final long[] mBuffer = new long[4];
        private final long[] mUsrSysTime = new long[2];

        private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
            if (throwable != null) {
                try {
                    autoCloseable.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            } else {
                autoCloseable.close();
            }
        }

        @VisibleForTesting
        public KernelCpuUidUserSysTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, boolean bl) {
            super(kernelCpuProcStringReader, bl);
        }

        public KernelCpuUidUserSysTimeReader(boolean bl) {
            super(KernelCpuProcStringReader.getUserSysTimeReaderInstance(), bl);
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void removeUidsFromKernelModule(int n, int n2) {
            int n3;
            Throwable throwable42222;
            block10 : {
                CharSequence charSequence = this.mTag;
                Appendable appendable = new StringBuilder();
                ((StringBuilder)appendable).append("Removing uids ");
                ((StringBuilder)appendable).append(n);
                ((StringBuilder)appendable).append("-");
                ((StringBuilder)appendable).append(n2);
                Slog.d((String)charSequence, ((StringBuilder)appendable).toString());
                n3 = StrictMode.allowThreadDiskWritesMask();
                appendable = new FileWriter(REMOVE_UID_PROC_FILE);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append("-");
                ((StringBuilder)charSequence).append(n2);
                ((Writer)appendable).write(((StringBuilder)charSequence).toString());
                ((OutputStreamWriter)appendable).flush();
                KernelCpuUidUserSysTimeReader.$closeResource(null, (AutoCloseable)((Object)appendable));
                catch (Throwable throwable2) {
                    try {
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        try {
                            KernelCpuUidUserSysTimeReader.$closeResource(throwable2, (AutoCloseable)((Object)appendable));
                            throw throwable3;
                        }
                        catch (Throwable throwable42222) {
                            break block10;
                        }
                        catch (IOException iOException) {
                            charSequence = this.mTag;
                            appendable = new StringBuilder();
                            ((StringBuilder)appendable).append("failed to remove uids ");
                            ((StringBuilder)appendable).append(n);
                            ((StringBuilder)appendable).append(" - ");
                            ((StringBuilder)appendable).append(n2);
                            ((StringBuilder)appendable).append(" from uid_cputime module");
                            Slog.e((String)charSequence, ((StringBuilder)appendable).toString(), iOException);
                        }
                    }
                }
                StrictMode.setThreadPolicyMask(n3);
                return;
            }
            StrictMode.setThreadPolicyMask(n3);
            throw throwable42222;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void readAbsoluteImpl(Callback<long[]> callback) {
            KernelCpuProcStringReader.ProcFileIterator procFileIterator = this.mReader.open(this.mThrottle ^ true);
            if (procFileIterator == null) {
                if (procFileIterator != null) {
                    KernelCpuUidUserSysTimeReader.$closeResource(null, procFileIterator);
                }
                return;
            }
            try {
                CharBuffer charBuffer;
                while ((charBuffer = procFileIterator.nextLine()) != null) {
                    if (KernelCpuProcStringReader.asLongs(charBuffer, this.mBuffer) < 3) {
                        String string2 = this.mTag;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid line: ");
                        stringBuilder.append(charBuffer.toString());
                        Slog.wtf(string2, stringBuilder.toString());
                        continue;
                    }
                    this.mUsrSysTime[0] = this.mBuffer[1];
                    this.mUsrSysTime[1] = this.mBuffer[2];
                    callback.onUidCpuTime((int)this.mBuffer[0], this.mUsrSysTime);
                }
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    KernelCpuUidUserSysTimeReader.$closeResource(throwable, procFileIterator);
                    throw throwable2;
                }
            }
            KernelCpuUidUserSysTimeReader.$closeResource(null, procFileIterator);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void readDeltaImpl(Callback<long[]> callback) {
            KernelCpuProcStringReader.ProcFileIterator procFileIterator = this.mReader.open(this.mThrottle ^ true);
            if (procFileIterator == null) {
                if (procFileIterator != null) {
                    KernelCpuUidUserSysTimeReader.$closeResource(null, procFileIterator);
                }
                return;
            }
            try {
                long[] arrl;
                while ((arrl = procFileIterator.nextLine()) != null) {
                    StringBuilder stringBuilder;
                    long[] arrl2;
                    if (KernelCpuProcStringReader.asLongs((CharBuffer)arrl, this.mBuffer) < 3) {
                        String string2 = this.mTag;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid line: ");
                        stringBuilder.append(arrl.toString());
                        Slog.wtf(string2, stringBuilder.toString());
                        continue;
                    }
                    int n = (int)this.mBuffer[0];
                    arrl = arrl2 = (long[])this.mLastTimes.get(n);
                    if (arrl2 == null) {
                        arrl = new long[2];
                        this.mLastTimes.put(n, arrl);
                    }
                    long l = this.mBuffer[1];
                    long l2 = this.mBuffer[2];
                    this.mUsrSysTime[0] = l - arrl[0];
                    this.mUsrSysTime[1] = l2 - arrl[1];
                    if (this.mUsrSysTime[0] >= 0L && this.mUsrSysTime[1] >= 0L) {
                        if ((this.mUsrSysTime[0] > 0L || this.mUsrSysTime[1] > 0L) && callback != null) {
                            callback.onUidCpuTime(n, this.mUsrSysTime);
                        }
                    } else {
                        String string3 = this.mTag;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Negative user/sys time delta for UID=");
                        stringBuilder.append(n);
                        stringBuilder.append("\nPrev times: u=");
                        stringBuilder.append(arrl[0]);
                        stringBuilder.append(" s=");
                        stringBuilder.append(arrl[1]);
                        stringBuilder.append(" Curr times: u=");
                        stringBuilder.append(l);
                        stringBuilder.append(" s=");
                        stringBuilder.append(l2);
                        Slog.e(string3, stringBuilder.toString());
                    }
                    arrl[0] = l;
                    arrl[1] = l2;
                }
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    KernelCpuUidUserSysTimeReader.$closeResource(throwable, procFileIterator);
                    throw throwable2;
                }
            }
            KernelCpuUidUserSysTimeReader.$closeResource(null, procFileIterator);
        }

        @Override
        public void removeUid(int n) {
            super.removeUid(n);
            this.removeUidsFromKernelModule(n, n);
        }

        @Override
        public void removeUidsInRange(int n, int n2) {
            super.removeUidsInRange(n, n2);
            this.removeUidsFromKernelModule(n, n2);
        }
    }

}


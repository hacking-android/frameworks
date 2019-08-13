/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.Process;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.ProcStatsUtil;
import com.android.internal.os.ProcTimeInStateReader;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public class KernelCpuThreadReader {
    private static final String CPU_STATISTICS_FILENAME = "time_in_state";
    private static final boolean DEBUG = false;
    private static final Path DEFAULT_INITIAL_TIME_IN_STATE_PATH;
    private static final String DEFAULT_PROCESS_NAME = "unknown_process";
    private static final Path DEFAULT_PROC_PATH;
    private static final String DEFAULT_THREAD_NAME = "unknown_thread";
    private static final int ID_ERROR = -1;
    private static final String PROCESS_DIRECTORY_FILTER = "[0-9]*";
    private static final String PROCESS_NAME_FILENAME = "cmdline";
    private static final String TAG = "KernelCpuThreadReader";
    private static final String THREAD_NAME_FILENAME = "comm";
    private int[] mFrequenciesKhz;
    private FrequencyBucketCreator mFrequencyBucketCreator;
    private final Injector mInjector;
    private final Path mProcPath;
    private final ProcTimeInStateReader mProcTimeInStateReader;
    private Predicate<Integer> mUidPredicate;

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

    static {
        DEFAULT_PROC_PATH = Paths.get("/proc", new String[0]);
        DEFAULT_INITIAL_TIME_IN_STATE_PATH = DEFAULT_PROC_PATH.resolve("self/time_in_state");
    }

    @VisibleForTesting
    public KernelCpuThreadReader(int n, Predicate<Integer> predicate, Path path, Path path2, Injector injector) throws IOException {
        this.mUidPredicate = predicate;
        this.mProcPath = path;
        this.mProcTimeInStateReader = new ProcTimeInStateReader(path2);
        this.mInjector = injector;
        this.setNumBuckets(n);
    }

    public static KernelCpuThreadReader create(int n, Predicate<Integer> object) {
        try {
            Path path = DEFAULT_PROC_PATH;
            Path path2 = DEFAULT_INITIAL_TIME_IN_STATE_PATH;
            Injector injector = new Injector();
            object = new KernelCpuThreadReader(n, (Predicate<Integer>)object, path, path2, injector);
            return object;
        }
        catch (IOException iOException) {
            Slog.e(TAG, "Failed to initialize KernelCpuThreadReader", iOException);
            return null;
        }
    }

    /*
     * Exception decompiling
     */
    private ProcessCpuUsage getProcessCpuUsage(Path var1_1, int var2_4, int var3_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private int getProcessId(Path object) {
        String string2 = object.getFileName().toString();
        try {
            int n = Integer.parseInt(string2);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to parse ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" as process ID");
            Slog.w(TAG, ((StringBuilder)object).toString(), numberFormatException);
            return -1;
        }
    }

    private String getProcessName(Path object) {
        if ((object = ProcStatsUtil.readSingleLineProcFile(object.resolve(PROCESS_NAME_FILENAME).toString())) != null) {
            return object;
        }
        return DEFAULT_PROCESS_NAME;
    }

    private ThreadCpuUsage getThreadCpuUsage(Path arrl) {
        int n;
        try {
            n = Integer.parseInt(arrl.getFileName().toString());
        }
        catch (NumberFormatException numberFormatException) {
            Slog.w(TAG, "Failed to parse thread ID when iterating over /proc/*/task", numberFormatException);
            return null;
        }
        String string2 = this.getThreadName((Path)arrl);
        arrl = arrl.resolve(CPU_STATISTICS_FILENAME);
        arrl = this.mProcTimeInStateReader.getUsageTimesMillis((Path)arrl);
        if (arrl == null) {
            return null;
        }
        return new ThreadCpuUsage(n, string2, this.mFrequencyBucketCreator.bucketValues(arrl));
    }

    private String getThreadName(Path object) {
        if ((object = ProcStatsUtil.readNullSeparatedFile(object.resolve(THREAD_NAME_FILENAME).toString())) == null) {
            return DEFAULT_THREAD_NAME;
        }
        return object;
    }

    public int[] getCpuFrequenciesKhz() {
        return this.mFrequenciesKhz;
    }

    /*
     * Exception decompiling
     */
    public ArrayList<ProcessCpuUsage> getProcessCpuUsage() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    void setNumBuckets(int n) {
        if (n < 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Number of buckets must be at least 1, but was ");
            stringBuilder.append(n);
            Slog.w(TAG, stringBuilder.toString());
            return;
        }
        int[] arrn = this.mFrequenciesKhz;
        if (arrn != null && arrn.length == n) {
            return;
        }
        this.mFrequencyBucketCreator = new FrequencyBucketCreator(this.mProcTimeInStateReader.getFrequenciesKhz(), n);
        this.mFrequenciesKhz = this.mFrequencyBucketCreator.bucketFrequencies(this.mProcTimeInStateReader.getFrequenciesKhz());
    }

    void setUidPredicate(Predicate<Integer> predicate) {
        this.mUidPredicate = predicate;
    }

    @VisibleForTesting
    public static class FrequencyBucketCreator {
        private final int[] mBucketStartIndices;
        private final int mNumBuckets;
        private final int mNumFrequencies;

        @VisibleForTesting
        public FrequencyBucketCreator(long[] arrl, int n) {
            this.mNumFrequencies = arrl.length;
            this.mBucketStartIndices = FrequencyBucketCreator.getBucketStartIndices(FrequencyBucketCreator.getClusterStartIndices(arrl), n, this.mNumFrequencies);
            this.mNumBuckets = this.mBucketStartIndices.length;
        }

        private static int[] getBucketStartIndices(int[] arrn, int n, int n2) {
            int n3 = arrn.length;
            if (n3 > n) {
                return Arrays.copyOfRange(arrn, 0, n);
            }
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (int i = 0; i < n3; ++i) {
                int n4;
                int n5 = FrequencyBucketCreator.getLowerBound(i, arrn);
                int n6 = FrequencyBucketCreator.getUpperBound(i, arrn, n2);
                int n7 = i != n3 - 1 ? n / n3 : n - (n3 - 1) * (n / n3);
                int n8 = Math.max(1, (n6 - n5) / n7);
                for (int j = 0; j < n7 && (n4 = j * n8 + n5) < n6; ++j) {
                    arrayList.add(n4);
                }
            }
            return ArrayUtils.convertToIntArray(arrayList);
        }

        private static int[] getClusterStartIndices(long[] arrl) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(0);
            for (int i = 0; i < arrl.length - 1; ++i) {
                if (arrl[i] < arrl[i + 1]) continue;
                arrayList.add(i + 1);
            }
            return ArrayUtils.convertToIntArray(arrayList);
        }

        private static int getLowerBound(int n, int[] arrn) {
            return arrn[n];
        }

        private static int getUpperBound(int n, int[] arrn, int n2) {
            if (n != arrn.length - 1) {
                return arrn[n + 1];
            }
            return n2;
        }

        @VisibleForTesting
        public int[] bucketFrequencies(long[] arrl) {
            boolean bl = arrl.length == this.mNumFrequencies;
            Preconditions.checkArgument(bl);
            int[] arrn = new int[this.mNumBuckets];
            for (int i = 0; i < arrn.length; ++i) {
                arrn[i] = (int)arrl[this.mBucketStartIndices[i]];
            }
            return arrn;
        }

        @VisibleForTesting
        public int[] bucketValues(long[] arrl) {
            boolean bl = arrl.length == this.mNumFrequencies;
            Preconditions.checkArgument(bl);
            int[] arrn = new int[this.mNumBuckets];
            for (int i = 0; i < this.mNumBuckets; ++i) {
                int n = FrequencyBucketCreator.getUpperBound(i, this.mBucketStartIndices, arrl.length);
                for (int j = FrequencyBucketCreator.getLowerBound((int)i, (int[])this.mBucketStartIndices); j < n; ++j) {
                    arrn[i] = (int)((long)arrn[i] + arrl[j]);
                }
            }
            return arrn;
        }
    }

    @VisibleForTesting
    public static class Injector {
        public int getUidForPid(int n) {
            return Process.getUidForPid(n);
        }
    }

    public static class ProcessCpuUsage {
        public final int processId;
        public final String processName;
        public ArrayList<ThreadCpuUsage> threadCpuUsages;
        public final int uid;

        @VisibleForTesting
        public ProcessCpuUsage(int n, String string2, int n2, ArrayList<ThreadCpuUsage> arrayList) {
            this.processId = n;
            this.processName = string2;
            this.uid = n2;
            this.threadCpuUsages = arrayList;
        }
    }

    public static class ThreadCpuUsage {
        public final int threadId;
        public final String threadName;
        public int[] usageTimesMillis;

        @VisibleForTesting
        public ThreadCpuUsage(int n, String string2, int[] arrn) {
            this.threadId = n;
            this.threadName = string2;
            this.usageTimesMillis = arrn;
        }
    }

}


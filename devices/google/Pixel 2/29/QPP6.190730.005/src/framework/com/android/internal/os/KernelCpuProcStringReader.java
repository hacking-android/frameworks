/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.SystemClock;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class KernelCpuProcStringReader {
    private static final KernelCpuProcStringReader ACTIVE_TIME_READER;
    private static final KernelCpuProcStringReader CLUSTER_TIME_READER;
    private static final int ERROR_THRESHOLD = 5;
    private static final KernelCpuProcStringReader FREQ_TIME_READER;
    private static final long FRESHNESS = 500L;
    private static final int MAX_BUFFER_SIZE = 1048576;
    private static final String PROC_UID_ACTIVE_TIME = "/proc/uid_concurrent_active_time";
    private static final String PROC_UID_CLUSTER_TIME = "/proc/uid_concurrent_policy_time";
    private static final String PROC_UID_FREQ_TIME = "/proc/uid_time_in_state";
    private static final String PROC_UID_USER_SYS_TIME = "/proc/uid_cputime/show_uid_stat";
    private static final String TAG;
    private static final KernelCpuProcStringReader USER_SYS_TIME_READER;
    private char[] mBuf;
    private int mErrors = 0;
    private final Path mFile;
    private long mLastReadTime = 0L;
    private final ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock mReadLock = this.mLock.readLock();
    private int mSize;
    private final ReentrantReadWriteLock.WriteLock mWriteLock = this.mLock.writeLock();

    static {
        TAG = KernelCpuProcStringReader.class.getSimpleName();
        FREQ_TIME_READER = new KernelCpuProcStringReader(PROC_UID_FREQ_TIME);
        ACTIVE_TIME_READER = new KernelCpuProcStringReader(PROC_UID_ACTIVE_TIME);
        CLUSTER_TIME_READER = new KernelCpuProcStringReader(PROC_UID_CLUSTER_TIME);
        USER_SYS_TIME_READER = new KernelCpuProcStringReader(PROC_UID_USER_SYS_TIME);
    }

    public KernelCpuProcStringReader(String string2) {
        this.mFile = Paths.get(string2, new String[0]);
    }

    public static int asLongs(CharBuffer charBuffer, long[] arrl) {
        if (charBuffer == null) {
            return -1;
        }
        int n = charBuffer.position();
        int n2 = 0;
        long l = -1L;
        while (charBuffer.remaining() > 0 && n2 < arrl.length) {
            char c = charBuffer.get();
            if (!KernelCpuProcStringReader.isNumber(c) && c != ' ' && c != ':') {
                charBuffer.position(n);
                return -2;
            }
            if (l < 0L) {
                if (!KernelCpuProcStringReader.isNumber(c)) continue;
                l = c - 48;
                continue;
            }
            if (KernelCpuProcStringReader.isNumber(c)) {
                long l2;
                l = l2 = 10L * l + (long)c - 48L;
                if (l2 >= 0L) continue;
                charBuffer.position(n);
                return -3;
            }
            arrl[n2] = l;
            l = -1L;
            ++n2;
        }
        int n3 = n2;
        if (l >= 0L) {
            arrl[n2] = l;
            n3 = n2 + 1;
        }
        charBuffer.position(n);
        return n3;
    }

    private boolean dataValid() {
        boolean bl = this.mSize > 0 && SystemClock.elapsedRealtime() - this.mLastReadTime < 500L;
        return bl;
    }

    static KernelCpuProcStringReader getActiveTimeReaderInstance() {
        return ACTIVE_TIME_READER;
    }

    static KernelCpuProcStringReader getClusterTimeReaderInstance() {
        return CLUSTER_TIME_READER;
    }

    static KernelCpuProcStringReader getFreqTimeReaderInstance() {
        return FREQ_TIME_READER;
    }

    static KernelCpuProcStringReader getUserSysTimeReaderInstance() {
        return USER_SYS_TIME_READER;
    }

    private static boolean isNumber(char c) {
        boolean bl = c >= '0' && c <= '9';
        return bl;
    }

    public ProcFileIterator open() {
        return this.open(false);
    }

    /*
     * Exception decompiling
     */
    public ProcFileIterator open(boolean var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 15[WHILELOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    public class ProcFileIterator
    implements AutoCloseable {
        private int mPos;
        private final int mSize;

        public ProcFileIterator(int n) {
            this.mSize = n;
        }

        @Override
        public void close() {
            KernelCpuProcStringReader.this.mReadLock.unlock();
        }

        public boolean hasNextLine() {
            boolean bl = this.mPos < this.mSize;
            return bl;
        }

        public CharBuffer nextLine() {
            int n;
            if (this.mPos >= this.mSize) {
                return null;
            }
            for (n = this.mPos; n < this.mSize && KernelCpuProcStringReader.this.mBuf[n] != '\n'; ++n) {
            }
            int n2 = this.mPos;
            this.mPos = n + 1;
            return CharBuffer.wrap(KernelCpuProcStringReader.this.mBuf, n2, n - n2);
        }

        public int size() {
            return this.mSize;
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrier {
    private final Runnable barrierCommand;
    private int count;
    private Generation generation = new Generation();
    private final ReentrantLock lock = new ReentrantLock();
    private final int parties;
    private final Condition trip = this.lock.newCondition();

    public CyclicBarrier(int n) {
        this(n, null);
    }

    public CyclicBarrier(int n, Runnable runnable) {
        if (n > 0) {
            this.parties = n;
            this.count = n;
            this.barrierCommand = runnable;
            return;
        }
        throw new IllegalArgumentException();
    }

    private void breakBarrier() {
        this.generation.broken = true;
        this.count = this.parties;
        this.trip.signalAll();
    }

    /*
     * Exception decompiling
     */
    private int dowait(boolean var1_1, long var2_2) throws InterruptedException, BrokenBarrierException, TimeoutException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 10[UNCONDITIONALDOLOOP]
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

    private void nextGeneration() {
        this.trip.signalAll();
        this.count = this.parties;
        this.generation = new Generation();
    }

    public int await() throws InterruptedException, BrokenBarrierException {
        try {
            int n = this.dowait(false, 0L);
            return n;
        }
        catch (TimeoutException timeoutException) {
            throw new Error(timeoutException);
        }
    }

    public int await(long l, TimeUnit timeUnit) throws InterruptedException, BrokenBarrierException, TimeoutException {
        return this.dowait(true, timeUnit.toNanos(l));
    }

    public int getNumberWaiting() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int n = this.parties;
            int n2 = this.count;
            return n - n2;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public int getParties() {
        return this.parties;
    }

    public boolean isBroken() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            boolean bl = this.generation.broken;
            return bl;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public void reset() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            this.breakBarrier();
            this.nextGeneration();
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    private static class Generation {
        boolean broken;

        private Generation() {
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractExecutorService
implements ExecutorService {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static <T> void cancelAll(ArrayList<Future<T>> arrayList) {
        AbstractExecutorService.cancelAll(arrayList, 0);
    }

    private static <T> void cancelAll(ArrayList<Future<T>> arrayList, int n) {
        int n2 = arrayList.size();
        while (n < n2) {
            arrayList.get(n).cancel(true);
            ++n;
        }
    }

    /*
     * Exception decompiling
     */
    private <T> T doInvokeAny(Collection<? extends Callable<T>> var1_1, boolean var2_5, long var3_6) throws InterruptedException, ExecutionException, TimeoutException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 8[UNCONDITIONALDOLOOP]
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

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> future) throws InterruptedException {
        if (future != null) {
            int n;
            ArrayList<Future<T>> arrayList = new ArrayList<Future<T>>(future.size());
            future = future.iterator();
            while (future.hasNext()) {
                RunnableFuture<T> runnableFuture = this.newTaskFor((Callable)future.next());
                arrayList.add(runnableFuture);
                this.execute(runnableFuture);
            }
            try {
                n = arrayList.size();
            }
            catch (Throwable throwable) {
                AbstractExecutorService.cancelAll(arrayList);
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                future = arrayList.get(i);
                boolean bl = future.isDone();
                if (bl) continue;
                try {
                    future.get();
                    continue;
                }
                catch (ExecutionException executionException) {
                    continue;
                }
                catch (CancellationException cancellationException) {
                    continue;
                }
            }
            return arrayList;
        }
        throw new NullPointerException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> object, long l, TimeUnit object2) throws InterruptedException {
        int n;
        block14 : {
            int n2;
            if (object == null) {
                throw new NullPointerException();
            }
            long l2 = ((TimeUnit)((Object)object2)).toNanos(l);
            long l3 = System.nanoTime() + l2;
            object2 = new ArrayList(object.size());
            int n3 = 0;
            int n4 = 0;
            try {
                object = object.iterator();
                while (object.hasNext()) {
                    ((ArrayList)object2).add(this.newTaskFor((Callable)object.next()));
                }
                n2 = ((ArrayList)object2).size();
                int n5 = 0;
                do {
                    n = n4;
                    if (n5 >= n2) break;
                    l = n5 == 0 ? l2 : l3 - System.nanoTime();
                    if (l <= 0L) {
                        n = n3;
                        break block14;
                    }
                    this.execute((Runnable)((ArrayList)object2).get(n5));
                    ++n5;
                } while (true);
            }
            catch (Throwable throwable) {
                AbstractExecutorService.cancelAll(object2);
                throw throwable;
            }
            do {
                if (n >= n2) {
                    return object2;
                }
                object = (Future)((ArrayList)object2).get(n);
                boolean bl = object.isDone();
                if (!bl) {
                    try {
                        object.get(l3 - System.nanoTime(), TimeUnit.NANOSECONDS);
                    }
                    catch (TimeoutException timeoutException) {
                        // empty catch block
                        break;
                    }
                    catch (ExecutionException executionException) {
                    }
                    catch (CancellationException cancellationException) {
                        // empty catch block
                    }
                }
                ++n;
                continue;
                break;
            } while (true);
        }
        AbstractExecutorService.cancelAll(object2, n);
        return object2;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        try {
            collection = this.doInvokeAny(collection, false, 0L);
        }
        catch (TimeoutException timeoutException) {
            return null;
        }
        return (T)collection;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.doInvokeAny(collection, true, timeUnit.toNanos(l));
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new FutureTask<T>(runnable, t);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new FutureTask<T>(callable);
    }

    @Override
    public Future<?> submit(Runnable runnableFuture) {
        if (runnableFuture != null) {
            runnableFuture = this.newTaskFor(runnableFuture, null);
            this.execute(runnableFuture);
            return runnableFuture;
        }
        throw new NullPointerException();
    }

    @Override
    public <T> Future<T> submit(Runnable runnableFuture, T t) {
        if (runnableFuture != null) {
            runnableFuture = this.newTaskFor(runnableFuture, t);
            this.execute(runnableFuture);
            return runnableFuture;
        }
        throw new NullPointerException();
    }

    @Override
    public <T> Future<T> submit(Callable<T> object) {
        if (object != null) {
            object = this.newTaskFor((Callable<T>)object);
            this.execute((Runnable)object);
            return object;
        }
        throw new NullPointerException();
    }
}


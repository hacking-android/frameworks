/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import sun.nio.ch.FileLockImpl;
import sun.nio.ch.FileLockTable;

abstract class AsynchronousFileChannelImpl
extends AsynchronousFileChannel {
    protected final ReadWriteLock closeLock = new ReentrantReadWriteLock();
    protected volatile boolean closed;
    protected final ExecutorService executor;
    protected final FileDescriptor fdObj;
    private volatile FileLockTable fileLockTable;
    protected final boolean reading;
    protected final boolean writing;

    protected AsynchronousFileChannelImpl(FileDescriptor fileDescriptor, boolean bl, boolean bl2, ExecutorService executorService) {
        this.fdObj = fileDescriptor;
        this.reading = bl;
        this.writing = bl2;
        this.executor = executorService;
    }

    /*
     * Exception decompiling
     */
    protected final FileLockImpl addToFileLockTable(long var1_1, long var3_2, boolean var5_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[CATCHBLOCK]
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

    protected final void begin() throws IOException {
        this.closeLock.readLock().lock();
        if (!this.closed) {
            return;
        }
        throw new ClosedChannelException();
    }

    protected final void end() {
        this.closeLock.readLock().unlock();
    }

    protected final void end(boolean bl) throws IOException {
        this.end();
        if (!bl && !this.isOpen()) {
            throw new AsynchronousCloseException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void ensureFileLockTableInitialized() throws IOException {
        if (this.fileLockTable != null) return;
        synchronized (this) {
            if (this.fileLockTable != null) return;
            this.fileLockTable = FileLockTable.newSharedFileLockTable(this, this.fdObj);
            return;
        }
    }

    final ExecutorService executor() {
        return this.executor;
    }

    abstract <A> Future<FileLock> implLock(long var1, long var3, boolean var5, A var6, CompletionHandler<FileLock, ? super A> var7);

    abstract <A> Future<Integer> implRead(ByteBuffer var1, long var2, A var4, CompletionHandler<Integer, ? super A> var5);

    protected abstract void implRelease(FileLockImpl var1) throws IOException;

    abstract <A> Future<Integer> implWrite(ByteBuffer var1, long var2, A var4, CompletionHandler<Integer, ? super A> var5);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void invalidateAllLocks() throws IOException {
        if (this.fileLockTable != null) {
            for (FileLock fileLock : this.fileLockTable.removeAll()) {
                synchronized (fileLock) {
                    if (fileLock.isValid()) {
                        FileLockImpl fileLockImpl = (FileLockImpl)fileLock;
                        this.implRelease(fileLockImpl);
                        fileLockImpl.invalidate();
                    }
                }
            }
        }
    }

    @Override
    public final boolean isOpen() {
        return this.closed ^ true;
    }

    @Override
    public final Future<FileLock> lock(long l, long l2, boolean bl) {
        return this.implLock(l, l2, bl, null, null);
    }

    @Override
    public final <A> void lock(long l, long l2, boolean bl, A a, CompletionHandler<FileLock, ? super A> completionHandler) {
        if (completionHandler != null) {
            this.implLock(l, l2, bl, a, completionHandler);
            return;
        }
        throw new NullPointerException("'handler' is null");
    }

    @Override
    public final Future<Integer> read(ByteBuffer byteBuffer, long l) {
        return this.implRead(byteBuffer, l, null, null);
    }

    @Override
    public final <A> void read(ByteBuffer byteBuffer, long l, A a, CompletionHandler<Integer, ? super A> completionHandler) {
        if (completionHandler != null) {
            this.implRead(byteBuffer, l, a, completionHandler);
            return;
        }
        throw new NullPointerException("'handler' is null");
    }

    final void release(FileLockImpl fileLockImpl) throws IOException {
        try {
            this.begin();
            this.implRelease(fileLockImpl);
            this.removeFromFileLockTable(fileLockImpl);
            return;
        }
        finally {
            this.end();
        }
    }

    protected final void removeFromFileLockTable(FileLockImpl fileLockImpl) {
        this.fileLockTable.remove(fileLockImpl);
    }

    @Override
    public final Future<Integer> write(ByteBuffer byteBuffer, long l) {
        return this.implWrite(byteBuffer, l, null, null);
    }

    @Override
    public final <A> void write(ByteBuffer byteBuffer, long l, A a, CompletionHandler<Integer, ? super A> completionHandler) {
        if (completionHandler != null) {
            this.implWrite(byteBuffer, l, a, completionHandler);
            return;
        }
        throw new NullPointerException("'handler' is null");
    }
}


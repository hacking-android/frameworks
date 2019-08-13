/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package sun.nio.fs;

import dalvik.system.CloseGuard;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;

class UnixDirectoryStream
implements DirectoryStream<Path> {
    private final UnixPath dir;
    private final long dp;
    private final DirectoryStream.Filter<? super Path> filter;
    private final CloseGuard guard = CloseGuard.get();
    private volatile boolean isClosed;
    private Iterator<Path> iterator;
    private final ReentrantReadWriteLock streamLock = new ReentrantReadWriteLock(true);

    UnixDirectoryStream(UnixPath unixPath, long l, DirectoryStream.Filter<? super Path> filter) {
        this.dir = unixPath;
        this.dp = l;
        this.filter = filter;
        this.guard.open("close");
    }

    static /* synthetic */ long access$000(UnixDirectoryStream unixDirectoryStream) {
        return unixDirectoryStream.dp;
    }

    static /* synthetic */ UnixPath access$100(UnixDirectoryStream unixDirectoryStream) {
        return unixDirectoryStream.dir;
    }

    static /* synthetic */ DirectoryStream.Filter access$200(UnixDirectoryStream unixDirectoryStream) {
        return unixDirectoryStream.filter;
    }

    @Override
    public void close() throws IOException {
        this.writeLock().lock();
        try {
            this.closeImpl();
            return;
        }
        finally {
            this.writeLock().unlock();
        }
    }

    protected final boolean closeImpl() throws IOException {
        if (!this.isClosed) {
            this.isClosed = true;
            try {
                UnixNativeDispatcher.closedir(this.dp);
            }
            catch (UnixException unixException) {
                throw new IOException(unixException.errorString());
            }
            this.guard.close();
            return true;
        }
        return false;
    }

    protected final UnixPath directory() {
        return this.dir;
    }

    protected void finalize() throws IOException {
        CloseGuard closeGuard = this.guard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        this.close();
    }

    protected final boolean isOpen() {
        return this.isClosed ^ true;
    }

    @Override
    public Iterator<Path> iterator() {
        return this.iterator(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final Iterator<Path> iterator(DirectoryStream<Path> iterator) {
        if (this.isClosed) {
            throw new IllegalStateException("Directory stream is closed");
        }
        synchronized (this) {
            if (this.iterator == null) {
                UnixDirectoryIterator unixDirectoryIterator;
                this.iterator = unixDirectoryIterator = new UnixDirectoryIterator((DirectoryStream<Path>)((Object)iterator));
                return this.iterator;
            }
            iterator = new Iterator<Path>("Iterator already obtained");
            throw iterator;
        }
    }

    protected final Lock readLock() {
        return this.streamLock.readLock();
    }

    protected final Lock writeLock() {
        return this.streamLock.writeLock();
    }

    private class UnixDirectoryIterator
    implements Iterator<Path> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean atEof = false;
        private Path nextEntry;
        private final DirectoryStream<Path> stream;

        UnixDirectoryIterator(DirectoryStream<Path> directoryStream) {
            this.stream = directoryStream;
        }

        private boolean isSelfOrParent(byte[] arrby) {
            return arrby[0] == 46 && (arrby.length == 1 || arrby.length == 2 && arrby[1] == 46);
        }

        /*
         * Exception decompiling
         */
        private Path readNextEntry() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[DOLOOP]], but top level block is 1[TRYBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        @Override
        public boolean hasNext() {
            synchronized (this) {
                Path path;
                if (this.nextEntry == null && !this.atEof) {
                    this.nextEntry = this.readNextEntry();
                }
                boolean bl = (path = this.nextEntry) != null;
                return bl;
            }
        }

        @Override
        public Path next() {
            synchronized (this) {
                Object object;
                block6 : {
                    if (this.nextEntry == null && !this.atEof) {
                        object = this.readNextEntry();
                    } else {
                        object = this.nextEntry;
                        this.nextEntry = null;
                    }
                    if (object == null) break block6;
                    return object;
                }
                object = new NoSuchElementException();
                throw object;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}


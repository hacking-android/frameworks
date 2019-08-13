/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import sun.nio.ch.AsynchronousFileChannelImpl;
import sun.nio.ch.CompletedFuture;
import sun.nio.ch.FileDispatcher;
import sun.nio.ch.FileDispatcherImpl;
import sun.nio.ch.FileLockImpl;
import sun.nio.ch.IOUtil;
import sun.nio.ch.Invoker;
import sun.nio.ch.NativeThreadSet;
import sun.nio.ch.PendingFuture;
import sun.nio.ch.ThreadPool;

public class SimpleAsynchronousFileChannelImpl
extends AsynchronousFileChannelImpl {
    private static final FileDispatcher nd = new FileDispatcherImpl();
    private final NativeThreadSet threads = new NativeThreadSet(2);

    SimpleAsynchronousFileChannelImpl(FileDescriptor fileDescriptor, boolean bl, boolean bl2, ExecutorService executorService) {
        super(fileDescriptor, bl, bl2, executorService);
    }

    public static AsynchronousFileChannel open(FileDescriptor fileDescriptor, boolean bl, boolean bl2, ThreadPool object) {
        object = object == null ? DefaultExecutorHolder.defaultExecutor : ((ThreadPool)object).executor();
        return new SimpleAsynchronousFileChannelImpl(fileDescriptor, bl, bl2, (ExecutorService)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        FileDescriptor fileDescriptor = this.fdObj;
        synchronized (fileDescriptor) {
            if (this.closed) {
                return;
            }
            this.closed = true;
        }
        this.invalidateAllLocks();
        this.threads.signalAndWait();
        this.closeLock.writeLock().lock();
        this.closeLock.writeLock().unlock();
        nd.close(this.fdObj);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void force(boolean bl) throws IOException {
        int n = this.threads.add();
        int n2 = 0;
        int n3 = 0;
        boolean bl2 = true;
        boolean bl3 = true;
        try {
            this.begin();
            n2 = n3;
            while ((n3 = nd.force(this.fdObj, bl)) == -3) {
                n2 = n3;
                boolean bl4 = this.isOpen();
                n2 = n3;
                if (bl4) continue;
            }
            bl = n3 >= 0 ? bl3 : false;
        }
        catch (Throwable throwable) {
            bl = n2 >= 0 ? bl2 : false;
            this.end(bl);
            throw throwable;
        }
        try {
            this.end(bl);
            return;
        }
        finally {
            this.threads.remove(n);
        }
    }

    @Override
    <A> Future<FileLock> implLock(final long l, final long l2, final boolean bl, A object, final CompletionHandler<FileLock, ? super A> completionHandler) {
        if (bl && !this.reading) {
            throw new NonReadableChannelException();
        }
        if (!bl && !this.writing) {
            throw new NonWritableChannelException();
        }
        final FileLockImpl fileLockImpl = this.addToFileLockTable(l, l2, bl);
        Object object2 = null;
        if (fileLockImpl == null) {
            object2 = new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withFailure((Throwable)object2);
            }
            Invoker.invokeIndirectly(completionHandler, object, null, (Throwable)object2, this.executor);
            return null;
        }
        if (completionHandler == null) {
            object2 = new PendingFuture(this);
        }
        object = new Runnable((PendingFuture)object2, object){
            final /* synthetic */ Object val$attachment;
            final /* synthetic */ PendingFuture val$result;
            {
                this.val$result = pendingFuture;
                this.val$attachment = object;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                block13 : {
                    var1_1 = null;
                    var2_5 = SimpleAsynchronousFileChannelImpl.access$000(SimpleAsynchronousFileChannelImpl.this).add();
                    SimpleAsynchronousFileChannelImpl.this.begin();
                    while ((var3_6 = SimpleAsynchronousFileChannelImpl.access$100().lock(SimpleAsynchronousFileChannelImpl.this.fdObj, true, l, l2, bl)) == 2 && SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                    }
                    if (var3_6 != 0 || !(var4_7 = SimpleAsynchronousFileChannelImpl.this.isOpen())) break block13;
                    SimpleAsynchronousFileChannelImpl.this.end();
                    ** GOTO lbl26
                }
                try {
                    var1_1 = new AsynchronousCloseException();
                    throw var1_1;
                }
                catch (Throwable var1_2) {
                    ** GOTO lbl33
                }
                catch (IOException var1_3) {
                    SimpleAsynchronousFileChannelImpl.this.removeFromFileLockTable(fileLockImpl);
                    if (!SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                        var1_1 = new AsynchronousCloseException();
                    }
                    SimpleAsynchronousFileChannelImpl.this.end();
lbl26: // 2 sources:
                    var5_8 = completionHandler;
                    if (var5_8 == null) {
                        this.val$result.setResult(fileLockImpl, var1_1);
                        return;
                    }
                    Invoker.invokeUnchecked(var5_8, this.val$attachment, fileLockImpl, var1_1);
                    return;
lbl33: // 2 sources:
                    SimpleAsynchronousFileChannelImpl.this.end();
                    throw var1_2;
                }
                finally {
                    SimpleAsynchronousFileChannelImpl.access$000(SimpleAsynchronousFileChannelImpl.this).remove(var2_5);
                }
            }
        };
        try {
            this.executor.execute((Runnable)object);
            return object2;
        }
        finally {
            if (!true) {
                this.removeFromFileLockTable(fileLockImpl);
            }
        }
    }

    @Override
    <A> Future<Integer> implRead(ByteBuffer object, long l, A a, CompletionHandler<Integer, ? super A> completionHandler) {
        if (l >= 0L) {
            if (this.reading) {
                if (!((Buffer)object).isReadOnly()) {
                    boolean bl = this.isOpen();
                    PendingFuture pendingFuture = null;
                    if (bl && ((Buffer)object).remaining() != 0) {
                        if (completionHandler == null) {
                            pendingFuture = new PendingFuture(this);
                        }
                        object = new Runnable((ByteBuffer)object, l, completionHandler, pendingFuture, a){
                            final /* synthetic */ Object val$attachment;
                            final /* synthetic */ ByteBuffer val$dst;
                            final /* synthetic */ CompletionHandler val$handler;
                            final /* synthetic */ long val$position;
                            final /* synthetic */ PendingFuture val$result;
                            {
                                this.val$dst = byteBuffer;
                                this.val$position = l;
                                this.val$handler = completionHandler;
                                this.val$result = pendingFuture;
                                this.val$attachment = object;
                            }

                            /*
                             * Enabled aggressive block sorting
                             * Enabled unnecessary exception pruning
                             * Enabled aggressive exception aggregation
                             */
                            @Override
                            public void run() {
                                int n2;
                                int n;
                                Object object;
                                Throwable throwable22;
                                CompletionHandler completionHandler;
                                block8 : {
                                    n2 = 0;
                                    int n3 = 0;
                                    completionHandler = null;
                                    n = SimpleAsynchronousFileChannelImpl.this.threads.add();
                                    try {
                                        try {
                                            int n4;
                                            SimpleAsynchronousFileChannelImpl.this.begin();
                                            do {
                                                n2 = n3;
                                                n4 = IOUtil.read(SimpleAsynchronousFileChannelImpl.this.fdObj, this.val$dst, this.val$position, nd);
                                                if (n4 != -3) break;
                                                n3 = n4;
                                                n2 = n4;
                                            } while (SimpleAsynchronousFileChannelImpl.this.isOpen());
                                            n2 = n4;
                                            object = completionHandler;
                                            if (n4 < 0) {
                                                n2 = n4;
                                                if (!SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                                                    n2 = n4;
                                                    n2 = n4;
                                                    object = new AsynchronousCloseException();
                                                    n2 = n4;
                                                    throw object;
                                                }
                                                n2 = n4;
                                                object = completionHandler;
                                            }
                                        }
                                        catch (IOException iOException) {
                                            if (SimpleAsynchronousFileChannelImpl.this.isOpen()) break block8;
                                            object = new AsynchronousCloseException();
                                        }
                                    }
                                    catch (Throwable throwable22) {}
                                }
                                SimpleAsynchronousFileChannelImpl.this.end();
                                SimpleAsynchronousFileChannelImpl.this.threads.remove(n);
                                completionHandler = this.val$handler;
                                if (completionHandler == null) {
                                    this.val$result.setResult(n2, (Throwable)object);
                                    return;
                                }
                                Invoker.invokeUnchecked(completionHandler, this.val$attachment, n2, (Throwable)object);
                                return;
                                SimpleAsynchronousFileChannelImpl.this.end();
                                SimpleAsynchronousFileChannelImpl.this.threads.remove(n);
                                throw throwable22;
                            }
                        };
                        this.executor.execute((Runnable)object);
                        return pendingFuture;
                    }
                    object = this.isOpen() ? null : new ClosedChannelException();
                    if (completionHandler == null) {
                        return CompletedFuture.withResult(0, (Throwable)object);
                    }
                    Invoker.invokeIndirectly(completionHandler, a, 0, (Throwable)object, this.executor);
                    return null;
                }
                throw new IllegalArgumentException("Read-only buffer");
            }
            throw new NonReadableChannelException();
        }
        throw new IllegalArgumentException("Negative position");
    }

    @Override
    protected void implRelease(FileLockImpl fileLockImpl) throws IOException {
        nd.release(this.fdObj, fileLockImpl.position(), fileLockImpl.size());
    }

    @Override
    <A> Future<Integer> implWrite(ByteBuffer object, long l, A a, CompletionHandler<Integer, ? super A> completionHandler) {
        if (l >= 0L) {
            if (this.writing) {
                boolean bl = this.isOpen();
                PendingFuture pendingFuture = null;
                if (bl && ((Buffer)object).remaining() != 0) {
                    if (completionHandler == null) {
                        pendingFuture = new PendingFuture(this);
                    }
                    object = new Runnable((ByteBuffer)object, l, completionHandler, pendingFuture, a){
                        final /* synthetic */ Object val$attachment;
                        final /* synthetic */ CompletionHandler val$handler;
                        final /* synthetic */ long val$position;
                        final /* synthetic */ PendingFuture val$result;
                        final /* synthetic */ ByteBuffer val$src;
                        {
                            this.val$src = byteBuffer;
                            this.val$position = l;
                            this.val$handler = completionHandler;
                            this.val$result = pendingFuture;
                            this.val$attachment = object;
                        }

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        @Override
                        public void run() {
                            int n2;
                            int n;
                            Object object;
                            Throwable throwable22;
                            CompletionHandler completionHandler;
                            block8 : {
                                n2 = 0;
                                int n3 = 0;
                                completionHandler = null;
                                n = SimpleAsynchronousFileChannelImpl.this.threads.add();
                                try {
                                    try {
                                        int n4;
                                        SimpleAsynchronousFileChannelImpl.this.begin();
                                        do {
                                            n2 = n3;
                                            n4 = IOUtil.write(SimpleAsynchronousFileChannelImpl.this.fdObj, this.val$src, this.val$position, nd);
                                            if (n4 != -3) break;
                                            n3 = n4;
                                            n2 = n4;
                                        } while (SimpleAsynchronousFileChannelImpl.this.isOpen());
                                        n2 = n4;
                                        object = completionHandler;
                                        if (n4 < 0) {
                                            n2 = n4;
                                            if (!SimpleAsynchronousFileChannelImpl.this.isOpen()) {
                                                n2 = n4;
                                                n2 = n4;
                                                object = new AsynchronousCloseException();
                                                n2 = n4;
                                                throw object;
                                            }
                                            n2 = n4;
                                            object = completionHandler;
                                        }
                                    }
                                    catch (IOException iOException) {
                                        if (SimpleAsynchronousFileChannelImpl.this.isOpen()) break block8;
                                        object = new AsynchronousCloseException();
                                    }
                                }
                                catch (Throwable throwable22) {}
                            }
                            SimpleAsynchronousFileChannelImpl.this.end();
                            SimpleAsynchronousFileChannelImpl.this.threads.remove(n);
                            completionHandler = this.val$handler;
                            if (completionHandler == null) {
                                this.val$result.setResult(n2, (Throwable)object);
                                return;
                            }
                            Invoker.invokeUnchecked(completionHandler, this.val$attachment, n2, (Throwable)object);
                            return;
                            SimpleAsynchronousFileChannelImpl.this.end();
                            SimpleAsynchronousFileChannelImpl.this.threads.remove(n);
                            throw throwable22;
                        }
                    };
                    this.executor.execute((Runnable)object);
                    return pendingFuture;
                }
                object = this.isOpen() ? null : new ClosedChannelException();
                if (completionHandler == null) {
                    return CompletedFuture.withResult(0, (Throwable)object);
                }
                Invoker.invokeIndirectly(completionHandler, a, 0, (Throwable)object, this.executor);
                return null;
            }
            throw new NonWritableChannelException();
        }
        throw new IllegalArgumentException("Negative position");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long size() throws IOException {
        int n;
        boolean bl2;
        long l;
        block7 : {
            n = this.threads.add();
            l = 0L;
            boolean bl = true;
            bl2 = true;
            long l2 = l;
            try {
                this.begin();
                l2 = l;
                while ((l = nd.size(this.fdObj)) == -3L) {
                    l2 = l;
                    boolean bl3 = this.isOpen();
                    l2 = l;
                    if (bl3) continue;
                }
                if (l >= 0L) break block7;
                bl2 = false;
            }
            catch (Throwable throwable) {
                bl2 = l2 >= 0L ? bl : false;
                this.end(bl2);
                throw throwable;
            }
        }
        try {
            this.end(bl2);
            return l;
        }
        finally {
            this.threads.remove(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AsynchronousFileChannel truncate(long l) throws IOException {
        boolean bl;
        int n;
        block11 : {
            if (l < 0L) throw new IllegalArgumentException("Negative size");
            if (!this.writing) throw new NonWritableChannelException();
            n = this.threads.add();
            long l2 = 0L;
            boolean bl2 = true;
            bl = true;
            long l3 = l2;
            try {
                block10 : {
                    long l4;
                    this.begin();
                    do {
                        l3 = l2;
                        l4 = nd.size(this.fdObj);
                        if (l4 != -3L) break;
                        l2 = l4;
                        l3 = l4;
                    } while (this.isOpen());
                    l2 = l4;
                    if (l < l4) {
                        l2 = l4;
                        l3 = l4;
                        if (this.isOpen()) {
                            boolean bl3;
                            l3 = l4;
                            do {
                                l2 = l4 = (long)nd.truncate(this.fdObj, l);
                                if (l4 != -3L) break block10;
                                l3 = l4;
                                bl3 = this.isOpen();
                                l3 = l4;
                            } while (bl3);
                            l2 = l4;
                        }
                    }
                }
                if (l2 > 0L) break block11;
                bl = false;
            }
            catch (Throwable throwable) {
                bl = l3 > 0L ? bl2 : false;
                this.end(bl);
                throw throwable;
            }
        }
        try {
            this.end(bl);
            return this;
        }
        finally {
            this.threads.remove(n);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public FileLock tryLock(long var1_1, long var3_2, boolean var5_3) throws IOException {
        block9 : {
            block10 : {
                if (var5_3) {
                    if (this.reading == false) throw new NonReadableChannelException();
                }
                if (!var5_3) {
                    if (this.writing == false) throw new NonWritableChannelException();
                }
                if ((var6_4 = this.addToFileLockTable(var1_1, var3_2, var5_3)) == null) throw new ClosedChannelException();
                var7_5 = this.threads.add();
                try {
                    this.begin();
                    while ((var8_6 = SimpleAsynchronousFileChannelImpl.nd.lock(this.fdObj, false, var1_1, var3_2, var5_3)) == 2 && this.isOpen()) {
                    }
                    if (var8_6 != 0 || !(var5_3 = this.isOpen())) break block9;
                    if (true) break block10;
                }
                catch (Throwable var9_9) {
                    if (!false) {
                        this.removeFromFileLockTable(var6_4);
                    }
                    this.end();
                    this.threads.remove(var7_5);
                    throw var9_9;
                }
                this.removeFromFileLockTable(var6_4);
            }
            this.end();
            this.threads.remove(var7_5);
            return var6_4;
        }
        if (var8_6 == -1) {
            if (!false) {
                this.removeFromFileLockTable(var6_4);
            }
            this.end();
            this.threads.remove(var7_5);
            return null;
        }
        if (var8_6 != 2) ** GOTO lbl35
        var9_7 = new AsynchronousCloseException();
        throw var9_7;
lbl35: // 1 sources:
        var9_8 = new AssertionError();
        throw var9_8;
    }

    private static class DefaultExecutorHolder {
        static final ExecutorService defaultExecutor = ThreadPool.createDefault().executor();

        private DefaultExecutorHolder() {
        }
    }

}


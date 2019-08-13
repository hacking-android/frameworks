/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.OsConstants
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ProxyFileDescriptorCallback;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.FuseUnavailableMountException;
import com.android.internal.os._$$Lambda$FuseAppLoop$e9Yru2f_btesWlxIgerkPnHibpg;
import com.android.internal.util.Preconditions;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

public class FuseAppLoop
implements Handler.Callback {
    private static final int ARGS_POOL_SIZE = 50;
    private static final boolean DEBUG = Log.isLoggable("FuseAppLoop", 3);
    private static final int FUSE_FSYNC = 20;
    private static final int FUSE_GETATTR = 3;
    private static final int FUSE_LOOKUP = 1;
    private static final int FUSE_MAX_WRITE = 131072;
    private static final int FUSE_OK = 0;
    private static final int FUSE_OPEN = 14;
    private static final int FUSE_READ = 15;
    private static final int FUSE_RELEASE = 18;
    private static final int FUSE_WRITE = 16;
    private static final int MIN_INODE = 2;
    public static final int ROOT_INODE = 1;
    private static final String TAG = "FuseAppLoop";
    private static final ThreadFactory sDefaultThreadFactory = new ThreadFactory(){

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, FuseAppLoop.TAG);
        }
    };
    @GuardedBy(value={"mLock"})
    private final LinkedList<Args> mArgsPool = new LinkedList();
    @GuardedBy(value={"mLock"})
    private final BytesMap mBytesMap = new BytesMap();
    @GuardedBy(value={"mLock"})
    private final SparseArray<CallbackEntry> mCallbackMap = new SparseArray();
    @GuardedBy(value={"mLock"})
    private long mInstance;
    private final Object mLock = new Object();
    private final int mMountPointId;
    @GuardedBy(value={"mLock"})
    private int mNextInode = 2;
    private final Thread mThread;

    public FuseAppLoop(int n, ParcelFileDescriptor parcelFileDescriptor, ThreadFactory threadFactory) {
        this.mMountPointId = n;
        ThreadFactory threadFactory2 = threadFactory;
        if (threadFactory == null) {
            threadFactory2 = sDefaultThreadFactory;
        }
        this.mInstance = this.native_new(parcelFileDescriptor.detachFd());
        this.mThread = threadFactory2.newThread(new _$$Lambda$FuseAppLoop$e9Yru2f_btesWlxIgerkPnHibpg(this));
        this.mThread.start();
    }

    private static int checkInode(long l) {
        Preconditions.checkArgumentInRange(l, 2L, Integer.MAX_VALUE, "checkInode");
        return (int)l;
    }

    @GuardedBy(value={"mLock"})
    private CallbackEntry getCallbackEntryOrThrowLocked(long l) throws ErrnoException {
        CallbackEntry callbackEntry = this.mCallbackMap.get(FuseAppLoop.checkInode(l));
        if (callbackEntry != null) {
            return callbackEntry;
        }
        throw new ErrnoException("getCallbackEntryOrThrowLocked", OsConstants.ENOENT);
    }

    private static int getError(Exception exception) {
        int n;
        if (exception instanceof ErrnoException && (n = ((ErrnoException)exception).errno) != OsConstants.ENOSYS) {
            return -n;
        }
        return -OsConstants.EBADF;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void onCommand(int n, long l, long l2, long l3, int n2, byte[] object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            try {
                try {
                    Args args = this.mArgsPool.size() == 0 ? new Args() : this.mArgsPool.pop();
                    args.unique = l;
                    args.inode = l2;
                    args.offset = l3;
                    args.size = n2;
                    args.data = object;
                    args.entry = this.getCallbackEntryOrThrowLocked(l2);
                    if (!args.entry.handler.sendMessage(Message.obtain(args.entry.handler, n, 0, 0, args))) {
                        object = new ErrnoException("onCommand", OsConstants.EBADF);
                        throw object;
                    }
                }
                catch (Exception exception) {
                    this.replySimpleLocked(l, FuseAppLoop.getError(exception));
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private byte[] onOpen(long l, long l2) {
        Object object = this.mLock;
        synchronized (object) {
            try {
                try {
                    Object object2 = this.getCallbackEntryOrThrowLocked(l2);
                    if (((CallbackEntry)object2).opened) {
                        object2 = new ErrnoException("onOpen", OsConstants.EMFILE);
                        throw object2;
                    }
                    if (this.mInstance == 0L) return null;
                    this.native_replyOpen(this.mInstance, l, l2);
                    ((CallbackEntry)object2).opened = true;
                    return this.mBytesMap.startUsing(((CallbackEntry)object2).getThreadId());
                }
                catch (ErrnoException errnoException) {
                    this.replySimpleLocked(l, FuseAppLoop.getError((Exception)((Object)errnoException)));
                }
                return null;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @GuardedBy(value={"mLock"})
    private void recycleLocked(Args args) {
        if (this.mArgsPool.size() < 50) {
            this.mArgsPool.add(args);
        }
    }

    @GuardedBy(value={"mLock"})
    private void replySimpleLocked(long l, int n) {
        long l2 = this.mInstance;
        if (l2 != 0L) {
            this.native_replySimple(l2, l, n);
        }
    }

    public int getMountPointId() {
        return this.mMountPointId;
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean handleMessage(Message var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 31[CATCHBLOCK]
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$new$0$FuseAppLoop() {
        this.native_start(this.mInstance);
        Object object = this.mLock;
        synchronized (object) {
            this.native_delete(this.mInstance);
            this.mInstance = 0L;
            this.mBytesMap.clear();
            return;
        }
    }

    native void native_delete(long var1);

    native long native_new(int var1);

    native void native_replyGetAttr(long var1, long var3, long var5, long var7);

    native void native_replyLookup(long var1, long var3, long var5, long var7);

    native void native_replyOpen(long var1, long var3, long var5);

    native void native_replyRead(long var1, long var3, int var5, byte[] var6);

    native void native_replySimple(long var1, long var3, int var5);

    native void native_replyWrite(long var1, long var3, int var5);

    native void native_start(long var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int registerCallback(ProxyFileDescriptorCallback object, Handler handler) throws FuseUnavailableMountException {
        Object object2 = this.mLock;
        synchronized (object2) {
            Preconditions.checkNotNull(object);
            Preconditions.checkNotNull(handler);
            int n = this.mCallbackMap.size();
            boolean bl = false;
            boolean bl2 = n < 2147483645;
            Preconditions.checkState(bl2, "Too many opened files.");
            bl2 = bl;
            if (Thread.currentThread().getId() != handler.getLooper().getThread().getId()) {
                bl2 = true;
            }
            Preconditions.checkArgument(bl2, "Handler must be different from the current thread");
            if (this.mInstance == 0L) {
                object = new FuseUnavailableMountException(this.mMountPointId);
                throw object;
            }
            do {
                n = this.mNextInode++;
                if (this.mNextInode >= 0) continue;
                this.mNextInode = 2;
            } while (this.mCallbackMap.get(n) != null);
            SparseArray<CallbackEntry> sparseArray = this.mCallbackMap;
            Handler handler2 = new Handler(handler.getLooper(), this);
            CallbackEntry callbackEntry = new CallbackEntry((ProxyFileDescriptorCallback)object, handler2);
            sparseArray.put(n, callbackEntry);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterCallback(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.mCallbackMap.remove(n);
            return;
        }
    }

    private static class Args {
        byte[] data;
        CallbackEntry entry;
        long inode;
        long offset;
        int size;
        long unique;

        private Args() {
        }
    }

    private static class BytesMap {
        final Map<Long, BytesMapEntry> mEntries = new HashMap<Long, BytesMapEntry>();

        private BytesMap() {
        }

        void clear() {
            this.mEntries.clear();
        }

        byte[] startUsing(long l) {
            BytesMapEntry bytesMapEntry;
            BytesMapEntry bytesMapEntry2 = bytesMapEntry = this.mEntries.get(l);
            if (bytesMapEntry == null) {
                bytesMapEntry2 = new BytesMapEntry();
                this.mEntries.put(l, bytesMapEntry2);
            }
            ++bytesMapEntry2.counter;
            return bytesMapEntry2.bytes;
        }

        void stopUsing(long l) {
            BytesMapEntry bytesMapEntry = this.mEntries.get(l);
            Preconditions.checkNotNull(bytesMapEntry);
            --bytesMapEntry.counter;
            if (bytesMapEntry.counter <= 0) {
                this.mEntries.remove(l);
            }
        }
    }

    private static class BytesMapEntry {
        byte[] bytes = new byte[131072];
        int counter = 0;

        private BytesMapEntry() {
        }
    }

    private static class CallbackEntry {
        final ProxyFileDescriptorCallback callback;
        final Handler handler;
        boolean opened;

        CallbackEntry(ProxyFileDescriptorCallback proxyFileDescriptorCallback, Handler handler) {
            this.callback = Preconditions.checkNotNull(proxyFileDescriptorCallback);
            this.handler = Preconditions.checkNotNull(handler);
        }

        long getThreadId() {
            return this.handler.getLooper().getThread().getId();
        }
    }

    public static class UnmountedException
    extends Exception {
    }

}


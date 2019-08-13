/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$Binder
 *  android.os.-$$Lambda$Binder$IYUHVkWouPK_9CG2s8VwyWBt5_I
 *  dalvik.annotation.optimization.CriticalNative
 *  libcore.io.IoUtils
 *  libcore.util.NativeAllocationRegistry
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.-$;
import android.os.BinderProxy;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.StrictMode;
import android.os.ThreadLocalWorkSource;
import android.os.Trace;
import android.os.TransactionTracker;
import android.os.UserHandle;
import android.os._$$Lambda$Binder$IYUHVkWouPK_9CG2s8VwyWBt5_I;
import android.util.ExceptionUtils;
import android.util.Log;
import com.android.internal.os.BinderInternal;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FunctionalUtils;
import dalvik.annotation.optimization.CriticalNative;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import libcore.io.IoUtils;
import libcore.util.NativeAllocationRegistry;

public class Binder
implements IBinder {
    public static final boolean CHECK_PARCEL_SIZE = false;
    private static final boolean FIND_POTENTIAL_LEAKS = false;
    public static boolean LOG_RUNTIME_EXCEPTION = false;
    private static final int NATIVE_ALLOCATION_SIZE = 500;
    static final String TAG = "Binder";
    public static final int UNSET_WORKSOURCE = -1;
    private static volatile String sDumpDisabled;
    private static BinderInternal.Observer sObserver;
    private static volatile boolean sTracingEnabled;
    private static volatile TransactionTracker sTransactionTracker;
    static volatile boolean sWarnOnBlocking;
    private static volatile BinderInternal.WorkSourceProvider sWorkSourceProvider;
    private String mDescriptor;
    @UnsupportedAppUsage
    private final long mObject = Binder.getNativeBBinderHolder();
    private IInterface mOwner;

    static {
        LOG_RUNTIME_EXCEPTION = false;
        sDumpDisabled = null;
        sTransactionTracker = null;
        sObserver = null;
        sTracingEnabled = false;
        sWarnOnBlocking = false;
        sWorkSourceProvider = _$$Lambda$Binder$IYUHVkWouPK_9CG2s8VwyWBt5_I.INSTANCE;
    }

    public Binder() {
        this(null);
    }

    public Binder(String string2) {
        NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mObject);
        this.mDescriptor = string2;
    }

    static /* synthetic */ long access$000() {
        return Binder.getNativeFinalizer();
    }

    public static IBinder allowBlocking(IBinder iBinder) {
        block5 : {
            if (iBinder instanceof BinderProxy) {
                ((BinderProxy)iBinder).mWarnOnBlocking = false;
                break block5;
            }
            if (iBinder == null) break block5;
            try {
                if (iBinder.getInterfaceDescriptor() != null && iBinder.queryLocalInterface(iBinder.getInterfaceDescriptor()) == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to allow blocking on interface ");
                    stringBuilder.append(iBinder);
                    Log.w(TAG, stringBuilder.toString());
                }
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return iBinder;
    }

    public static final native void blockUntilThreadAvailable();

    static void checkParcel(IBinder iBinder, int n, Parcel parcel, String string2) {
    }

    @CriticalNative
    public static final native long clearCallingIdentity();

    @CriticalNative
    public static final native long clearCallingWorkSource();

    public static void copyAllowBlocking(IBinder iBinder, IBinder iBinder2) {
        if (iBinder instanceof BinderProxy && iBinder2 instanceof BinderProxy) {
            ((BinderProxy)iBinder2).mWarnOnBlocking = ((BinderProxy)iBinder).mWarnOnBlocking;
        }
    }

    public static IBinder defaultBlocking(IBinder iBinder) {
        if (iBinder instanceof BinderProxy) {
            ((BinderProxy)iBinder).mWarnOnBlocking = sWarnOnBlocking;
        }
        return iBinder;
    }

    public static void disableTracing() {
        sTracingEnabled = false;
    }

    public static void enableTracing() {
        sTracingEnabled = true;
    }

    @UnsupportedAppUsage
    private boolean execTransact(int n, long l, long l2, int n2) {
        int n3 = Binder.getCallingUid();
        long l3 = ThreadLocalWorkSource.setUid(n3);
        try {
            boolean bl = this.execTransactInternal(n, l, l2, n2, n3);
            return bl;
        }
        finally {
            ThreadLocalWorkSource.restore(l3);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean execTransactInternal(int var1_1, long var2_2, long var4_3, int var6_4, int var7_5) {
        block14 : {
            block16 : {
                block13 : {
                    var8_6 = Binder.sObserver;
                    var9_7 = var8_6 != null ? var8_6.callStarted(this, var1_1, -1) : null;
                    var10_8 = Parcel.obtain(var2_2);
                    var11_9 = Parcel.obtain(var4_3);
                    var12_10 = Binder.isTracingEnabled();
                    if (!var12_10) ** GOTO lbl19
                    var13_11 = this.getTransactionName(var1_1);
                    var14_14 = new StringBuilder();
                    var14_14.append(this.getClass().getName());
                    var14_14.append(":");
                    if (var13_11 == null) {
                        var13_11 = var1_1;
                    }
                    var14_14.append(var13_11);
                    Trace.traceBegin(1L, var14_14.toString());
lbl19: // 2 sources:
                    var15_15 = this.onTransact(var1_1, var10_8, var11_9, var6_4);
                    if (!var12_10) break block13;
                    Trace.traceEnd(1L);
                }
                var16_17 = var15_15;
                if (var8_6 != null) {
                    var16_17 = var15_15;
lbl26: // 2 sources:
                    do {
                        var6_4 = Binder.sWorkSourceProvider.resolveWorkSourceUid(var10_8.readCallingWorkSourceUid());
                        var8_6.callEnded(var9_7, var10_8.dataSize(), var11_9.dataSize(), var6_4);
                        ** GOTO lbl58
                        break;
                    } while (true);
                }
                break block16;
                {
                    block15 : {
                        catch (Throwable var13_12) {
                            break block14;
                        }
                        catch (RemoteException | RuntimeException var13_13) {}
                        if (var8_6 == null) ** GOTO lbl36
                        {
                            var8_6.callThrewException(var9_7, var13_13);
lbl36: // 2 sources:
                            if (var16_17 = Binder.LOG_RUNTIME_EXCEPTION) {
                                Log.w("Binder", "Caught a RuntimeException from the binder stub implementation.", var13_13);
                            }
                            if ((var6_4 & 1) != 0) {
                                if (var13_13 instanceof RemoteException) {
                                    Log.w("Binder", "Binder call failed.", var13_13);
                                } else {
                                    Log.w("Binder", "Caught a RuntimeException from the binder stub implementation.", var13_13);
                                }
                            } else {
                                var11_9.setDataSize(0);
                                var11_9.setDataPosition(0);
                                var11_9.writeException(var13_13);
                            }
                            var16_17 = true;
                            var15_16 = true;
                            if (!var12_10) break block15;
                        }
                        Trace.traceEnd(1L);
                    }
                    if (var8_6 == null) break block16;
                    var16_17 = var15_16;
                    ** continue;
                }
            }
            Binder.checkParcel(this, var1_1, var11_9, "Unreasonably large binder reply buffer");
            var11_9.recycle();
            var10_8.recycle();
            StrictMode.clearGatheredViolations();
            return var16_17;
        }
        if (var12_10) {
            Trace.traceEnd(1L);
        }
        if (var8_6 == null) throw var13_12;
        var1_1 = Binder.sWorkSourceProvider.resolveWorkSourceUid(var10_8.readCallingWorkSourceUid());
        var8_6.callEnded(var9_7, var10_8.dataSize(), var11_9.dataSize(), var1_1);
        throw var13_12;
    }

    public static final native void flushPendingCommands();

    @CriticalNative
    public static final native int getCallingPid();

    @CriticalNative
    public static final native int getCallingUid();

    public static final int getCallingUidOrThrow() {
        if (Binder.isHandlingTransaction()) {
            return Binder.getCallingUid();
        }
        throw new IllegalStateException("Thread is not in a binder transcation");
    }

    public static final UserHandle getCallingUserHandle() {
        return UserHandle.of(UserHandle.getUserId(Binder.getCallingUid()));
    }

    @CriticalNative
    public static final native int getCallingWorkSourceUid();

    private static native long getFinalizer();

    private static native long getNativeBBinderHolder();

    private static native long getNativeFinalizer();

    @CriticalNative
    public static final native int getThreadStrictModePolicy();

    public static TransactionTracker getTransactionTracker() {
        synchronized (Binder.class) {
            TransactionTracker transactionTracker;
            if (sTransactionTracker == null) {
                sTransactionTracker = transactionTracker = new TransactionTracker();
            }
            transactionTracker = sTransactionTracker;
            return transactionTracker;
        }
    }

    @CriticalNative
    public static final native boolean isHandlingTransaction();

    public static final boolean isProxy(IInterface iInterface) {
        boolean bl = iInterface.asBinder() != iInterface;
        return bl;
    }

    public static boolean isTracingEnabled() {
        return sTracingEnabled;
    }

    public static final void joinThreadPool() {
        BinderInternal.joinThreadPool();
    }

    static /* synthetic */ int lambda$static$0(int n) {
        return Binder.getCallingUid();
    }

    public static final native void restoreCallingIdentity(long var0);

    @CriticalNative
    public static final native void restoreCallingWorkSource(long var0);

    @CriticalNative
    public static final native long setCallingWorkSourceUid(int var0);

    public static void setDumpDisabled(String string2) {
        sDumpDisabled = string2;
    }

    public static void setObserver(BinderInternal.Observer observer) {
        sObserver = observer;
    }

    @SystemApi
    public static void setProxyTransactListener(ProxyTransactListener proxyTransactListener) {
        BinderProxy.setTransactListener(proxyTransactListener);
    }

    @CriticalNative
    public static final native void setThreadStrictModePolicy(int var0);

    public static void setWarnOnBlocking(boolean bl) {
        sWarnOnBlocking = bl;
    }

    public static void setWorkSourceProvider(BinderInternal.WorkSourceProvider workSourceProvider) {
        if (workSourceProvider != null) {
            sWorkSourceProvider = workSourceProvider;
            return;
        }
        throw new IllegalArgumentException("workSourceProvider cannot be null");
    }

    public static final <T> T withCleanCallingIdentity(FunctionalUtils.ThrowingSupplier<T> throwingSupplier) {
        long l = Binder.clearCallingIdentity();
        try {
            throwingSupplier = throwingSupplier.getOrThrow();
        }
        catch (Throwable throwable) {
            Binder.restoreCallingIdentity(l);
            throw ExceptionUtils.propagate(throwable);
        }
        Binder.restoreCallingIdentity(l);
        if (!false) {
            return (T)throwingSupplier;
        }
        throw ExceptionUtils.propagate(null);
    }

    public static final void withCleanCallingIdentity(FunctionalUtils.ThrowingRunnable throwingRunnable) {
        long l = Binder.clearCallingIdentity();
        try {
            throwingRunnable.runOrThrow();
        }
        catch (Throwable throwable) {
            Binder.restoreCallingIdentity(l);
            throw ExceptionUtils.propagate(throwable);
        }
        Binder.restoreCallingIdentity(l);
        if (!false) {
            return;
        }
        throw ExceptionUtils.propagate(null);
    }

    public void attachInterface(IInterface iInterface, String string2) {
        this.mOwner = iInterface;
        this.mDescriptor = string2;
    }

    void doDump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] object) {
        if (sDumpDisabled == null) {
            try {
                this.dump(fileDescriptor, printWriter, (String[])object);
            }
            catch (Throwable throwable) {
                printWriter.println();
                printWriter.println("Exception occurred while dumping:");
                throwable.printStackTrace(printWriter);
            }
            catch (SecurityException securityException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Security exception: ");
                ((StringBuilder)object).append(securityException.getMessage());
                printWriter.println(((StringBuilder)object).toString());
                throw securityException;
            }
        } else {
            printWriter.println(sDumpDisabled);
        }
    }

    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    @Override
    public void dump(FileDescriptor fileDescriptor, String[] arrstring) {
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(fileDescriptor));
        try {
            this.doDump(fileDescriptor, fastPrintWriter, arrstring);
            return;
        }
        finally {
            ((PrintWriter)fastPrintWriter).flush();
        }
    }

    @Override
    public void dumpAsync(final FileDescriptor fileDescriptor, String[] arrstring) {
        new Thread("Binder.dumpAsync", new FastPrintWriter(new FileOutputStream(fileDescriptor)), arrstring){
            final /* synthetic */ String[] val$args;
            final /* synthetic */ PrintWriter val$pw;
            {
                this.val$pw = printWriter;
                this.val$args = arrstring;
                super(string2);
            }

            @Override
            public void run() {
                try {
                    Binder.this.dump(fileDescriptor, this.val$pw, this.val$args);
                    return;
                }
                finally {
                    this.val$pw.flush();
                }
            }
        }.start();
    }

    @Override
    public String getInterfaceDescriptor() {
        return this.mDescriptor;
    }

    public String getTransactionName(int n) {
        return null;
    }

    @Override
    public boolean isBinderAlive() {
        return true;
    }

    @Override
    public void linkToDeath(IBinder.DeathRecipient deathRecipient, int n) {
    }

    public void onShellCommand(FileDescriptor object, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, String[] arrstring, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
        object = fileDescriptor2 != null ? fileDescriptor2 : fileDescriptor;
        object = new FastPrintWriter(new FileOutputStream((FileDescriptor)object));
        ((PrintWriter)object).println("No shell command implementation.");
        ((PrintWriter)object).flush();
        resultReceiver.send(0, null);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        ParcelFileDescriptor parcelFileDescriptor3;
        ParcelFileDescriptor parcelFileDescriptor2;
        ParcelFileDescriptor parcelFileDescriptor;
        block18 : {
            block16 : {
                block17 : {
                    if (n == 1598968902) {
                        parcel.writeString(this.getInterfaceDescriptor());
                        return true;
                    }
                    if (n != 1598311760) break block16;
                    ParcelFileDescriptor parcelFileDescriptor4 = ((Parcel)object).readFileDescriptor();
                    String[] arrstring = ((Parcel)object).readStringArray();
                    if (parcelFileDescriptor4 != null) {
                        void var2_5;
                        block13 : {
                            object = parcelFileDescriptor4.getFileDescriptor();
                            try {
                                this.dump((FileDescriptor)object, arrstring);
                            }
                            catch (Throwable throwable) {
                                break block13;
                            }
                            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor4);
                            break block17;
                            catch (Throwable throwable) {
                                // empty catch block
                            }
                        }
                        IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor4);
                        throw var2_5;
                    }
                }
                if (parcel != null) {
                    parcel.writeNoException();
                    return true;
                }
                StrictMode.clearGatheredViolations();
                return true;
            }
            if (n != 1598246212) return false;
            parcelFileDescriptor2 = ((Parcel)object).readFileDescriptor();
            parcelFileDescriptor3 = ((Parcel)object).readFileDescriptor();
            parcelFileDescriptor = ((Parcel)object).readFileDescriptor();
            String[] arrstring = ((Parcel)object).readStringArray();
            ShellCallback shellCallback = ShellCallback.CREATOR.createFromParcel((Parcel)object);
            ResultReceiver resultReceiver = ResultReceiver.CREATOR.createFromParcel((Parcel)object);
            if (parcelFileDescriptor3 != null) {
                Throwable throwable222;
                block15 : {
                    block14 : {
                        if (parcelFileDescriptor2 != null) {
                            try {
                                object = parcelFileDescriptor2.getFileDescriptor();
                                break block14;
                            }
                            catch (Throwable throwable222) {
                                break block15;
                            }
                        }
                        object = null;
                    }
                    FileDescriptor fileDescriptor = parcelFileDescriptor3.getFileDescriptor();
                    FileDescriptor fileDescriptor2 = parcelFileDescriptor != null ? parcelFileDescriptor.getFileDescriptor() : parcelFileDescriptor3.getFileDescriptor();
                    this.shellCommand((FileDescriptor)object, fileDescriptor, fileDescriptor2, arrstring, shellCallback, resultReceiver);
                    break block18;
                }
                IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor2);
                IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor3);
                IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
                if (parcel != null) {
                    parcel.writeNoException();
                    throw throwable222;
                }
                StrictMode.clearGatheredViolations();
                throw throwable222;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor2);
        IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor3);
        IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
        if (parcel != null) {
            parcel.writeNoException();
            return true;
        }
        StrictMode.clearGatheredViolations();
        return true;
    }

    @Override
    public boolean pingBinder() {
        return true;
    }

    @Override
    public IInterface queryLocalInterface(String string2) {
        String string3 = this.mDescriptor;
        if (string3 != null && string3.equals(string2)) {
            return this.mOwner;
        }
        return null;
    }

    @Override
    public void shellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] arrstring, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
        this.onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, arrstring, shellCallback, resultReceiver);
    }

    @Override
    public final boolean transact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (parcel != null) {
            parcel.setDataPosition(0);
        }
        boolean bl = this.onTransact(n, parcel, parcel2, n2);
        if (parcel2 != null) {
            parcel2.setDataPosition(0);
        }
        return bl;
    }

    @Override
    public boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int n) {
        return true;
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(Binder.class.getClassLoader(), Binder.access$000(), 500L);

        private NoImagePreloadHolder() {
        }
    }

    public static class PropagateWorkSourceTransactListener
    implements ProxyTransactListener {
        @Override
        public void onTransactEnded(Object object) {
            if (object != null) {
                Binder.restoreCallingWorkSource((Long)object);
            }
        }

        @Override
        public Object onTransactStarted(IBinder iBinder, int n) {
            n = ThreadLocalWorkSource.getUid();
            if (n != -1) {
                return Binder.setCallingWorkSourceUid(n);
            }
            return null;
        }
    }

    @SystemApi
    public static interface ProxyTransactListener {
        public void onTransactEnded(Object var1);

        public Object onTransactStarted(IBinder var1, int var2);
    }

}


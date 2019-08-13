/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.-$
 *  android.content.-$$Lambda
 *  android.content.-$$Lambda$AbstractThreadedSyncAdapter
 *  android.content.-$$Lambda$AbstractThreadedSyncAdapter$ISyncAdapterImpl
 *  android.content.-$$Lambda$AbstractThreadedSyncAdapter$ISyncAdapterImpl$L6ZtOCe8gjKwJj0908ytPlrD8Rc
 */
package android.content;

import android.accounts.Account;
import android.content.-$;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ISyncAdapter;
import android.content.ISyncAdapterUnsyncableAccountCallback;
import android.content.ISyncContext;
import android.content.SyncContext;
import android.content.SyncResult;
import android.content._$$Lambda$AbstractThreadedSyncAdapter$ISyncAdapterImpl$L6ZtOCe8gjKwJj0908ytPlrD8Rc;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractThreadedSyncAdapter {
    private static final boolean ENABLE_LOG;
    @Deprecated
    public static final int LOG_SYNC_DETAILS = 2743;
    private static final String TAG = "SyncAdapter";
    private boolean mAllowParallelSyncs;
    private final boolean mAutoInitialize;
    private final Context mContext;
    private final ISyncAdapterImpl mISyncAdapterImpl;
    private final AtomicInteger mNumSyncStarts;
    private final Object mSyncThreadLock = new Object();
    private final HashMap<Account, SyncThread> mSyncThreads = new HashMap();

    static {
        boolean bl = Build.IS_DEBUGGABLE && Log.isLoggable(TAG, 3);
        ENABLE_LOG = bl;
    }

    public AbstractThreadedSyncAdapter(Context context, boolean bl) {
        this(context, bl, false);
    }

    public AbstractThreadedSyncAdapter(Context context, boolean bl, boolean bl2) {
        this.mContext = context;
        this.mISyncAdapterImpl = new ISyncAdapterImpl();
        this.mNumSyncStarts = new AtomicInteger(0);
        this.mAutoInitialize = bl;
        this.mAllowParallelSyncs = bl2;
    }

    static /* synthetic */ boolean access$1100(AbstractThreadedSyncAdapter abstractThreadedSyncAdapter) {
        return abstractThreadedSyncAdapter.mAllowParallelSyncs;
    }

    static /* synthetic */ Context access$1300(AbstractThreadedSyncAdapter abstractThreadedSyncAdapter) {
        return abstractThreadedSyncAdapter.mContext;
    }

    private void handleOnUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) {
        boolean bl;
        try {
            bl = this.onUnsyncableAccount();
        }
        catch (RuntimeException runtimeException) {
            Log.e(TAG, "Exception while calling onUnsyncableAccount, assuming 'true'", runtimeException);
            bl = true;
        }
        try {
            iSyncAdapterUnsyncableAccountCallback.onUnsyncableAccountDone(bl);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Could not report result of onUnsyncableAccount", remoteException);
        }
    }

    private Account toSyncKey(Account account) {
        if (this.mAllowParallelSyncs) {
            return account;
        }
        return null;
    }

    public Context getContext() {
        return this.mContext;
    }

    public final IBinder getSyncAdapterBinder() {
        return this.mISyncAdapterImpl.asBinder();
    }

    public abstract void onPerformSync(Account var1, Bundle var2, String var3, ContentProviderClient var4, SyncResult var5);

    public void onSecurityException(Account account, Bundle bundle, String string2, SyncResult syncResult) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onSyncCanceled() {
        Object object = this.mSyncThreadLock;
        // MONITORENTER : object
        SyncThread syncThread = this.mSyncThreads.get(null);
        // MONITOREXIT : object
        if (syncThread == null) return;
        syncThread.interrupt();
    }

    public void onSyncCanceled(Thread thread) {
        thread.interrupt();
    }

    public boolean onUnsyncableAccount() {
        return true;
    }

    private class ISyncAdapterImpl
    extends ISyncAdapter.Stub {
        private ISyncAdapterImpl() {
        }

        static /* synthetic */ void lambda$onUnsyncableAccount$0(Object object, ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) {
            ((AbstractThreadedSyncAdapter)object).handleOnUnsyncableAccount(iSyncAdapterUnsyncableAccountCallback);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void cancelSync(ISyncContext var1_1) {
            block14 : {
                block13 : {
                    var2_4 = null;
                    var3_5 = AbstractThreadedSyncAdapter.access$300(AbstractThreadedSyncAdapter.this);
                    // MONITORENTER : var3_5
                    var4_6 = AbstractThreadedSyncAdapter.access$400(AbstractThreadedSyncAdapter.this).values().iterator();
                    do {
                        var5_7 = var2_4;
                    } while (var4_6.hasNext() && SyncThread.access$800(var5_7 = (SyncThread)var4_6.next()).getSyncContextBinder() != var1_1.asBinder());
                    // MONITOREXIT : var3_5
                    if (var5_7 == null) ** GOTO lbl29
                    try {
                        if (AbstractThreadedSyncAdapter.access$100()) {
                            var1_1 = new StringBuilder();
                            var1_1.append("cancelSync() ");
                            var1_1.append(SyncThread.access$900(var5_7));
                            var1_1.append(" ");
                            var1_1.append(SyncThread.access$1000(var5_7));
                            Log.d("SyncAdapter", var1_1.toString());
                        }
                        if (AbstractThreadedSyncAdapter.access$1100(AbstractThreadedSyncAdapter.this)) {
                            AbstractThreadedSyncAdapter.this.onSyncCanceled(var5_7);
                        } else {
                            AbstractThreadedSyncAdapter.this.onSyncCanceled();
                        }
                        break block13;
lbl29: // 1 sources:
                        if (!AbstractThreadedSyncAdapter.access$100()) break block13;
                        Log.w("SyncAdapter", "cancelSync() unknown context");
                    }
                    catch (Throwable var1_2) {
                        break block14;
                    }
                    catch (Error | RuntimeException var1_3) {
                        if (AbstractThreadedSyncAdapter.access$100() == false) throw var1_3;
                        Log.d("SyncAdapter", "cancelSync() caught exception", var1_3);
                        throw var1_3;
                    }
                }
                if (AbstractThreadedSyncAdapter.access$100() == false) return;
                Log.d("SyncAdapter", "cancelSync() finishing");
                return;
            }
            if (AbstractThreadedSyncAdapter.access$100() == false) throw var1_2;
            Log.d("SyncAdapter", "cancelSync() finishing");
            throw var1_2;
        }

        @Override
        public void onUnsyncableAccount(ISyncAdapterUnsyncableAccountCallback iSyncAdapterUnsyncableAccountCallback) {
            Handler.getMain().sendMessage(PooledLambda.obtainMessage(_$$Lambda$AbstractThreadedSyncAdapter$ISyncAdapterImpl$L6ZtOCe8gjKwJj0908ytPlrD8Rc.INSTANCE, AbstractThreadedSyncAdapter.this, iSyncAdapterUnsyncableAccountCallback));
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void startSync(ISyncContext object, String object2, Account account, Bundle bundle) {
            void var1_7;
            block25 : {
                boolean bl;
                Object object3;
                Account account2;
                void var1_6;
                if (ENABLE_LOG) {
                    if (bundle != null) {
                        bundle.size();
                    }
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("startSync() start ");
                    ((StringBuilder)object3).append((String)object2);
                    ((StringBuilder)object3).append(" ");
                    ((StringBuilder)object3).append(account);
                    ((StringBuilder)object3).append(" ");
                    ((StringBuilder)object3).append(bundle);
                    Log.d(AbstractThreadedSyncAdapter.TAG, ((StringBuilder)object3).toString());
                }
                try {
                    object3 = new SyncContext((ISyncContext)object);
                    account2 = AbstractThreadedSyncAdapter.this.toSyncKey(account);
                    object = AbstractThreadedSyncAdapter.this.mSyncThreadLock;
                    // MONITORENTER : object
                }
                catch (Throwable throwable) {
                    break block25;
                }
                catch (Error | RuntimeException throwable) {}
                if (!AbstractThreadedSyncAdapter.this.mSyncThreads.containsKey(account2)) {
                    boolean bl2;
                    if (AbstractThreadedSyncAdapter.this.mAutoInitialize && bundle != null && (bl2 = bundle.getBoolean("initialize", false))) {
                        if (ContentResolver.getIsSyncable(account, (String)object2) < 0) {
                            ContentResolver.setIsSyncable(account, (String)object2, 1);
                        }
                        // MONITOREXIT : object
                        if (!ENABLE_LOG) return;
                        Log.d(AbstractThreadedSyncAdapter.TAG, "startSync() finishing");
                        return;
                        finally {
                            object2 = new SyncResult();
                            ((SyncContext)object3).onFinished((SyncResult)object2);
                        }
                    }
                    AbstractThreadedSyncAdapter abstractThreadedSyncAdapter = AbstractThreadedSyncAdapter.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("SyncAdapterThread-");
                    stringBuilder.append(AbstractThreadedSyncAdapter.this.mNumSyncStarts.incrementAndGet());
                    SyncThread syncThread = abstractThreadedSyncAdapter.new SyncThread(stringBuilder.toString(), (SyncContext)object3, (String)object2, account, bundle);
                    AbstractThreadedSyncAdapter.this.mSyncThreads.put(account2, syncThread);
                    syncThread.start();
                    bl = false;
                } else {
                    if (ENABLE_LOG) {
                        Log.d(AbstractThreadedSyncAdapter.TAG, "  alreadyInProgress");
                    }
                    bl = true;
                }
                // MONITOREXIT : object
                if (bl) {
                    ((SyncContext)object3).onFinished(SyncResult.ALREADY_IN_PROGRESS);
                }
                if (!ENABLE_LOG) return;
                Log.d(AbstractThreadedSyncAdapter.TAG, "startSync() finishing");
                return;
                if (!ENABLE_LOG) throw var1_6;
                Log.d(AbstractThreadedSyncAdapter.TAG, "startSync() caught exception", (Throwable)var1_6);
                throw var1_6;
            }
            if (!ENABLE_LOG) throw var1_7;
            Log.d(AbstractThreadedSyncAdapter.TAG, "startSync() finishing");
            throw var1_7;
        }
    }

    private class SyncThread
    extends Thread {
        private final Account mAccount;
        private final String mAuthority;
        private final Bundle mExtras;
        private final SyncContext mSyncContext;
        private final Account mThreadsKey;

        private SyncThread(String string2, SyncContext syncContext, String string3, Account account, Bundle bundle) {
            super(string2);
            this.mSyncContext = syncContext;
            this.mAuthority = string3;
            this.mAccount = account;
            this.mExtras = bundle;
            this.mThreadsKey = AbstractThreadedSyncAdapter.this.toSyncKey(account);
        }

        static /* synthetic */ Account access$1000(SyncThread syncThread) {
            return syncThread.mAccount;
        }

        static /* synthetic */ SyncContext access$800(SyncThread syncThread) {
            return syncThread.mSyncContext;
        }

        static /* synthetic */ String access$900(SyncThread syncThread) {
            return syncThread.mAuthority;
        }

        private boolean isCanceled() {
            return Thread.currentThread().isInterrupted();
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            block37 : {
                block38 : {
                    block36 : {
                        block34 : {
                            block35 : {
                                Process.setThreadPriority(10);
                                if (AbstractThreadedSyncAdapter.access$100()) {
                                    Log.d("SyncAdapter", "Thread started");
                                }
                                Trace.traceBegin(128L, this.mAuthority);
                                var1_1 = new SyncResult();
                                var2_2 = null;
                                var3_3 = null;
                                var5_5 = var4_4 = null;
                                if (!this.isCanceled()) break block34;
                                var5_5 = var4_4;
                                if (!AbstractThreadedSyncAdapter.access$100()) break block35;
                                var5_5 = var4_4;
                                Log.d("SyncAdapter", "Already canceled");
                            }
                            Trace.traceEnd(128L);
                            if (false) {
                                throw new NullPointerException();
                            }
                            if (!this.isCanceled()) {
                                this.mSyncContext.onFinished(var1_1);
                            }
                            var4_4 = AbstractThreadedSyncAdapter.access$300(AbstractThreadedSyncAdapter.this);
                            // MONITORENTER : var4_4
                            AbstractThreadedSyncAdapter.access$400(AbstractThreadedSyncAdapter.this).remove(this.mThreadsKey);
                            // MONITOREXIT : var4_4
                            if (AbstractThreadedSyncAdapter.access$100() == false) return;
                            Log.d("SyncAdapter", "Thread finished");
                            return;
                        }
                        var5_5 = var4_4;
                        if (AbstractThreadedSyncAdapter.access$100()) {
                            var5_5 = var4_4;
                            Log.d("SyncAdapter", "Calling onPerformSync...");
                        }
                        var5_5 = var4_4;
                        var4_4 = AbstractThreadedSyncAdapter.access$1300(AbstractThreadedSyncAdapter.this).getContentResolver().acquireContentProviderClient(this.mAuthority);
                        if (var4_4 == null) ** GOTO lbl45
                        try {
                            block39 : {
                                AbstractThreadedSyncAdapter.this.onPerformSync(this.mAccount, this.mExtras, this.mAuthority, (ContentProviderClient)var4_4, var1_1);
                                break block39;
lbl45: // 1 sources:
                                var1_1.databaseError = true;
                            }
                            if (!AbstractThreadedSyncAdapter.access$100()) break block36;
                            Log.d("SyncAdapter", "onPerformSync done");
                        }
                        catch (Throwable var5_6) {
                            break block37;
                        }
                        catch (Error | RuntimeException var5_7) {
                            var6_9 = var5_7;
                            break block38;
                        }
                        catch (SecurityException var5_8) {
                            var6_10 = var5_8;
                            ** GOTO lbl90
                        }
                    }
                    Trace.traceEnd(128L);
                    if (var4_4 != null) {
                        var4_4.release();
                    }
                    if (!this.isCanceled()) {
                        this.mSyncContext.onFinished(var1_1);
                    }
                    var4_4 = AbstractThreadedSyncAdapter.access$300(AbstractThreadedSyncAdapter.this);
                    // MONITORENTER : var4_4
                    AbstractThreadedSyncAdapter.access$400(AbstractThreadedSyncAdapter.this).remove(this.mThreadsKey);
                    // MONITOREXIT : var4_4
                    if (AbstractThreadedSyncAdapter.access$100() == false) return;
                    Log.d("SyncAdapter", "Thread finished");
                    return;
                    catch (Throwable var6_11) {
                        var4_4 = var5_5;
                        var5_5 = var6_11;
                        break block37;
                    }
                    catch (Error | RuntimeException var6_12) {
                        var4_4 = var2_2;
                    }
                }
                var5_5 = var4_4;
                {
                    if (AbstractThreadedSyncAdapter.access$100()) {
                        var5_5 = var4_4;
                        Log.d("SyncAdapter", "caught exception", var6_9);
                    }
                    var5_5 = var4_4;
                    throw var6_9;
                    catch (SecurityException var6_13) {
                        var4_4 = var3_3;
                    }
lbl90: // 2 sources:
                    var5_5 = var4_4;
                    if (AbstractThreadedSyncAdapter.access$100()) {
                        var5_5 = var4_4;
                        Log.d("SyncAdapter", "SecurityException", var6_10);
                    }
                    var5_5 = var4_4;
                    AbstractThreadedSyncAdapter.this.onSecurityException(this.mAccount, this.mExtras, this.mAuthority, var1_1);
                    var5_5 = var4_4;
                    var1_1.databaseError = true;
                }
                Trace.traceEnd(128L);
                if (var4_4 != null) {
                    var4_4.release();
                }
                if (!this.isCanceled()) {
                    this.mSyncContext.onFinished(var1_1);
                }
                var4_4 = AbstractThreadedSyncAdapter.access$300(AbstractThreadedSyncAdapter.this);
                // MONITORENTER : var4_4
                AbstractThreadedSyncAdapter.access$400(AbstractThreadedSyncAdapter.this).remove(this.mThreadsKey);
                // MONITOREXIT : var4_4
                if (AbstractThreadedSyncAdapter.access$100() == false) return;
                Log.d("SyncAdapter", "Thread finished");
                return;
            }
            Trace.traceEnd(128L);
            if (var4_4 != null) {
                var4_4.release();
            }
            if (!this.isCanceled()) {
                this.mSyncContext.onFinished(var1_1);
            }
            var4_4 = AbstractThreadedSyncAdapter.access$300(AbstractThreadedSyncAdapter.this);
            // MONITORENTER : var4_4
            AbstractThreadedSyncAdapter.access$400(AbstractThreadedSyncAdapter.this).remove(this.mThreadsKey);
            // MONITOREXIT : var4_4
            if (AbstractThreadedSyncAdapter.access$100() == false) throw var5_5;
            Log.d("SyncAdapter", "Thread finished");
            throw var5_5;
        }
    }

}


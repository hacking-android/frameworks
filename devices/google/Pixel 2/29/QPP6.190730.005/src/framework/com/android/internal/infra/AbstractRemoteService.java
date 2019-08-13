/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.infra.-$
 *  com.android.internal.infra.-$$Lambda
 *  com.android.internal.infra.-$$Lambda$7-CJJfrUZBVuXZyYFEWBNh8Mky8
 *  com.android.internal.infra.-$$Lambda$AbstractRemoteService
 *  com.android.internal.infra.-$$Lambda$AbstractRemoteService$6FcEKfZ-7TXLg6dcCU8EMuMNAy4
 *  com.android.internal.infra.-$$Lambda$AbstractRemoteService$9IBVTCLLZgndvH7fu1P14PW1_1o
 *  com.android.internal.infra.-$$Lambda$AbstractRemoteService$MDW40b8CzodE5xRowI9wDEyXEnw
 *  com.android.internal.infra.-$$Lambda$AbstractRemoteService$YSUzqqi1Pbrg2dlwMGMtKWbGXck
 *  com.android.internal.infra.-$$Lambda$AbstractRemoteService$ocrHd68Md9x6FfAzVQ6w8MAjFqY
 *  com.android.internal.infra.-$$Lambda$EbzSql2RHkXox5Myj8A-7kLC4_A
 */
package com.android.internal.infra;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Slog;
import android.util.TimeUtils;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.infra.-$;
import com.android.internal.infra._$$Lambda$7_CJJfrUZBVuXZyYFEWBNh8Mky8;
import com.android.internal.infra._$$Lambda$AbstractRemoteService$6FcEKfZ_7TXLg6dcCU8EMuMNAy4;
import com.android.internal.infra._$$Lambda$AbstractRemoteService$9IBVTCLLZgndvH7fu1P14PW1_1o;
import com.android.internal.infra._$$Lambda$AbstractRemoteService$MDW40b8CzodE5xRowI9wDEyXEnw;
import com.android.internal.infra._$$Lambda$AbstractRemoteService$PendingRequest$IBoaBGXZQEXJr69u3aJF_LCJ42Y;
import com.android.internal.infra._$$Lambda$AbstractRemoteService$YSUzqqi1Pbrg2dlwMGMtKWbGXck;
import com.android.internal.infra._$$Lambda$AbstractRemoteService$ocrHd68Md9x6FfAzVQ6w8MAjFqY;
import com.android.internal.infra._$$Lambda$EbzSql2RHkXox5Myj8A_7kLC4_A;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public abstract class AbstractRemoteService<S extends AbstractRemoteService<S, I>, I extends IInterface>
implements IBinder.DeathRecipient {
    protected static final int LAST_PRIVATE_MSG = 2;
    private static final int MSG_BIND = 1;
    private static final int MSG_UNBIND = 2;
    public static final long PERMANENT_BOUND_TIMEOUT_MS = 0L;
    private boolean mBinding;
    private final int mBindingFlags;
    private boolean mCompleted;
    protected final ComponentName mComponentName;
    private final Context mContext;
    private boolean mDestroyed;
    protected final Handler mHandler;
    private final Intent mIntent;
    private long mNextUnbind;
    protected I mService;
    private final ServiceConnection mServiceConnection = new RemoteServiceConnection();
    private boolean mServiceDied;
    protected final String mTag = this.getClass().getSimpleName();
    private final ArrayList<BasePendingRequest<S, I>> mUnfinishedRequests = new ArrayList();
    private final int mUserId;
    public final boolean mVerbose;
    private final VultureCallback<S> mVultureCallback;

    AbstractRemoteService(Context context, String string2, ComponentName componentName, int n, VultureCallback<S> vultureCallback, Handler handler, int n2, boolean bl) {
        this.mContext = context;
        this.mVultureCallback = vultureCallback;
        this.mVerbose = bl;
        this.mComponentName = componentName;
        this.mIntent = new Intent(string2).setComponent(this.mComponentName);
        this.mUserId = n;
        this.mHandler = new Handler(handler.getLooper());
        this.mBindingFlags = n2;
    }

    private void cancelScheduledUnbind() {
        this.mHandler.removeMessages(2);
    }

    private boolean checkIfDestroyed() {
        if (this.mDestroyed && this.mVerbose) {
            String string2 = this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not handling operation as service for ");
            stringBuilder.append(this.mComponentName);
            stringBuilder.append(" is already destroyed");
            Slog.v(string2, stringBuilder.toString());
        }
        return this.mDestroyed;
    }

    private void handleBinderDied() {
        if (this.checkIfDestroyed()) {
            return;
        }
        I i = this.mService;
        if (i != null) {
            i.asBinder().unlinkToDeath(this, 0);
        }
        this.mService = null;
        this.mServiceDied = true;
        this.cancelScheduledUnbind();
        this.mVultureCallback.onServiceDied(this);
        this.handleBindFailure();
    }

    private void handleDestroy() {
        if (this.checkIfDestroyed()) {
            return;
        }
        this.handleOnDestroy();
        this.handleEnsureUnbound();
        this.mDestroyed = true;
    }

    private void handleEnsureBound() {
        if (!this.handleIsBound() && !this.mBinding) {
            if (this.mVerbose) {
                Slog.v(this.mTag, "ensureBound()");
            }
            this.mBinding = true;
            int n = 67108865 | this.mBindingFlags;
            if (!this.mContext.bindServiceAsUser(this.mIntent, this.mServiceConnection, n, this.mHandler, new UserHandle(this.mUserId))) {
                String string2 = this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("could not bind to ");
                stringBuilder.append(this.mIntent);
                stringBuilder.append(" using flags ");
                stringBuilder.append(n);
                Slog.w(string2, stringBuilder.toString());
                this.mBinding = false;
                if (!this.mServiceDied) {
                    this.handleBinderDied();
                }
            }
            return;
        }
    }

    private void handleEnsureUnbound() {
        if (!this.handleIsBound() && !this.mBinding) {
            return;
        }
        if (this.mVerbose) {
            Slog.v(this.mTag, "ensureUnbound()");
        }
        this.mBinding = false;
        if (this.handleIsBound()) {
            this.handleOnConnectedStateChangedInternal(false);
            I i = this.mService;
            if (i != null) {
                i.asBinder().unlinkToDeath(this, 0);
                this.mService = null;
            }
        }
        this.mNextUnbind = 0L;
        this.mContext.unbindService(this.mServiceConnection);
    }

    private void handleFinishRequest(BasePendingRequest<S, I> basePendingRequest) {
        this.mUnfinishedRequests.remove(basePendingRequest);
        if (this.mUnfinishedRequests.isEmpty()) {
            this.scheduleUnbind();
        }
    }

    private boolean handleIsBound() {
        boolean bl = this.mService != null;
        return bl;
    }

    private void handleOnConnectedStateChangedInternal(boolean bl) {
        this.handleOnConnectedStateChanged(bl);
        if (bl) {
            this.handlePendingRequests();
        }
    }

    private void handleUnbind() {
        if (this.checkIfDestroyed()) {
            return;
        }
        this.handleEnsureUnbound();
    }

    public static /* synthetic */ void lambda$6FcEKfZ-7TXLg6dcCU8EMuMNAy4(AbstractRemoteService abstractRemoteService, BasePendingRequest basePendingRequest) {
        abstractRemoteService.handleFinishRequest(basePendingRequest);
    }

    public static /* synthetic */ void lambda$9IBVTCLLZgndvH7fu1P14PW1_1o(AbstractRemoteService abstractRemoteService) {
        abstractRemoteService.handleDestroy();
    }

    public static /* synthetic */ void lambda$MDW40b8CzodE5xRowI9wDEyXEnw(AbstractRemoteService abstractRemoteService) {
        abstractRemoteService.handleUnbind();
    }

    public static /* synthetic */ void lambda$YSUzqqi1Pbrg2dlwMGMtKWbGXck(AbstractRemoteService abstractRemoteService) {
        abstractRemoteService.handleEnsureBound();
    }

    public static /* synthetic */ void lambda$ocrHd68Md9x6FfAzVQ6w8MAjFqY(AbstractRemoteService abstractRemoteService) {
        abstractRemoteService.handleBinderDied();
    }

    private void scheduleUnbind(boolean bl) {
        long l = this.getTimeoutIdleBindMillis();
        if (l <= 0L) {
            if (this.mVerbose) {
                String string2 = this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("not scheduling unbind when value is ");
                stringBuilder.append(l);
                Slog.v(string2, stringBuilder.toString());
            }
            return;
        }
        if (!bl) {
            l = 0L;
        }
        this.cancelScheduledUnbind();
        this.mNextUnbind = SystemClock.elapsedRealtime() + l;
        if (this.mVerbose) {
            String string3 = this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unbinding in ");
            stringBuilder.append(l);
            stringBuilder.append("ms: ");
            stringBuilder.append(this.mNextUnbind);
            Slog.v(string3, stringBuilder.toString());
        }
        this.mHandler.sendMessageDelayed(PooledLambda.obtainMessage(_$$Lambda$AbstractRemoteService$MDW40b8CzodE5xRowI9wDEyXEnw.INSTANCE, this).setWhat(2), l);
    }

    @Override
    public void binderDied() {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AbstractRemoteService$ocrHd68Md9x6FfAzVQ6w8MAjFqY.INSTANCE, this));
    }

    public final void destroy() {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AbstractRemoteService$9IBVTCLLZgndvH7fu1P14PW1_1o.INSTANCE, this));
    }

    public void dump(String string2, PrintWriter printWriter) {
        printWriter.append(string2).append("service:").println();
        printWriter.append(string2).append("  ").append("userId=").append(String.valueOf(this.mUserId)).println();
        printWriter.append(string2).append("  ").append("componentName=").append(this.mComponentName.flattenToString()).println();
        printWriter.append(string2).append("  ").append("destroyed=").append(String.valueOf(this.mDestroyed)).println();
        printWriter.append(string2).append("  ").append("numUnfinishedRequests=").append(String.valueOf(this.mUnfinishedRequests.size())).println();
        boolean bl = this.handleIsBound();
        printWriter.append(string2).append("  ").append("bound=").append(String.valueOf(bl));
        long l = this.getTimeoutIdleBindMillis();
        if (bl) {
            if (l > 0L) {
                printWriter.append(" (unbind in : ");
                TimeUtils.formatDuration(this.mNextUnbind - SystemClock.elapsedRealtime(), printWriter);
                printWriter.append(")");
            } else {
                printWriter.append(" (permanently bound)");
            }
        }
        printWriter.println();
        printWriter.append(string2).append("mBindingFlags=").println(this.mBindingFlags);
        printWriter.append(string2).append("idleTimeout=").append(Long.toString(l / 1000L)).append("s\n");
        printWriter.append(string2).append("requestTimeout=");
        try {
            printWriter.append(Long.toString(this.getRemoteRequestMillis() / 1000L)).append("s\n");
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            printWriter.append("not supported\n");
        }
        printWriter.println();
    }

    void finishRequest(BasePendingRequest<S, I> basePendingRequest) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AbstractRemoteService$6FcEKfZ_7TXLg6dcCU8EMuMNAy4.INSTANCE, this, basePendingRequest));
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    protected long getRemoteRequestMillis() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not implemented by ");
        stringBuilder.append(this.getClass());
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public final I getServiceInterface() {
        return this.mService;
    }

    protected abstract I getServiceInterface(IBinder var1);

    protected abstract long getTimeoutIdleBindMillis();

    abstract void handleBindFailure();

    protected void handleOnConnectedStateChanged(boolean bl) {
    }

    protected abstract void handleOnDestroy();

    protected final void handlePendingRequest(BasePendingRequest<S, I> basePendingRequest) {
        if (!this.checkIfDestroyed() && !this.mCompleted) {
            if (!this.handleIsBound()) {
                if (this.mVerbose) {
                    String string2 = this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("handlePendingRequest(): queuing ");
                    stringBuilder.append(basePendingRequest);
                    Slog.v(string2, stringBuilder.toString());
                }
                this.handlePendingRequestWhileUnBound(basePendingRequest);
                this.handleEnsureBound();
            } else {
                if (this.mVerbose) {
                    String string3 = this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("handlePendingRequest(): ");
                    stringBuilder.append(basePendingRequest);
                    Slog.v(string3, stringBuilder.toString());
                }
                this.mUnfinishedRequests.add(basePendingRequest);
                this.cancelScheduledUnbind();
                basePendingRequest.run();
                if (basePendingRequest.isFinal()) {
                    this.mCompleted = true;
                }
            }
            return;
        }
    }

    abstract void handlePendingRequestWhileUnBound(BasePendingRequest<S, I> var1);

    abstract void handlePendingRequests();

    public final boolean isDestroyed() {
        return this.mDestroyed;
    }

    protected void scheduleAsyncRequest(AsyncRequest<I> object) {
        object = new MyAsyncPendingRequest<AbstractRemoteService, I>(this, (AsyncRequest<I>)object);
        this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$EbzSql2RHkXox5Myj8A_7kLC4_A.INSTANCE, this, object));
    }

    protected void scheduleBind() {
        if (this.mHandler.hasMessages(1)) {
            if (this.mVerbose) {
                Slog.v(this.mTag, "scheduleBind(): already scheduled");
            }
            return;
        }
        this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AbstractRemoteService$YSUzqqi1Pbrg2dlwMGMtKWbGXck.INSTANCE, this).setWhat(1));
    }

    protected void scheduleRequest(BasePendingRequest<S, I> basePendingRequest) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$7_CJJfrUZBVuXZyYFEWBNh8Mky8.INSTANCE, this, basePendingRequest));
    }

    protected void scheduleUnbind() {
        this.scheduleUnbind(true);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("[");
        stringBuilder.append(this.mComponentName);
        stringBuilder.append(" ");
        stringBuilder.append(System.identityHashCode(this));
        String string2 = this.mService != null ? " (bound)" : " (unbound)";
        stringBuilder.append(string2);
        string2 = this.mDestroyed ? " (destroyed)" : "";
        stringBuilder.append(string2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static interface AsyncRequest<I extends IInterface> {
        public void run(I var1) throws RemoteException;
    }

    public static abstract class BasePendingRequest<S extends AbstractRemoteService<S, I>, I extends IInterface>
    implements Runnable {
        @GuardedBy(value={"mLock"})
        boolean mCancelled;
        @GuardedBy(value={"mLock"})
        boolean mCompleted;
        protected final Object mLock = new Object();
        protected final String mTag = this.getClass().getSimpleName();
        final WeakReference<S> mWeakService;

        BasePendingRequest(S s) {
            this.mWeakService = new WeakReference<S>(s);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean cancel() {
            Object object = this.mLock;
            synchronized (object) {
                if (!this.mCancelled && !this.mCompleted) {
                    this.mCancelled = true;
                    // MONITOREXIT [2, 3] lbl5 : MonitorExitStatement: MONITOREXIT : var1_1
                    this.onCancel();
                    return true;
                }
                return false;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected final boolean finish() {
            Object object = this.mLock;
            synchronized (object) {
                if (!this.mCompleted && !this.mCancelled) {
                    this.mCompleted = true;
                    // MONITOREXIT [2, 4] lbl5 : MonitorExitStatement: MONITOREXIT : var1_1
                    AbstractRemoteService abstractRemoteService = (AbstractRemoteService)this.mWeakService.get();
                    if (abstractRemoteService != null) {
                        abstractRemoteService.finishRequest(this);
                    }
                    this.onFinished();
                    return true;
                }
                return false;
            }
        }

        protected final S getService() {
            return (S)((AbstractRemoteService)this.mWeakService.get());
        }

        @GuardedBy(value={"mLock"})
        protected final boolean isCancelledLocked() {
            return this.mCancelled;
        }

        protected boolean isFinal() {
            return false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected boolean isRequestCompleted() {
            Object object = this.mLock;
            synchronized (object) {
                return this.mCompleted;
            }
        }

        void onCancel() {
        }

        protected void onFailed() {
        }

        void onFinished() {
        }
    }

    private static final class MyAsyncPendingRequest<S extends AbstractRemoteService<S, I>, I extends IInterface>
    extends BasePendingRequest<S, I> {
        private static final String TAG = MyAsyncPendingRequest.class.getSimpleName();
        private final AsyncRequest<I> mRequest;

        protected MyAsyncPendingRequest(S s, AsyncRequest<I> asyncRequest) {
            super(s);
            this.mRequest = asyncRequest;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            var1_1 = this.getService();
            if (var1_1 == null) {
                return;
            }
            this.mRequest.run(var1_1.mService);
lbl6: // 2 sources:
            do {
                this.finish();
                return;
                break;
            } while (true);
            {
                catch (Throwable var1_2) {
                }
                catch (RemoteException var1_3) {}
                {
                    var2_4 = MyAsyncPendingRequest.TAG;
                    var3_5 = new StringBuilder();
                    var3_5.append("exception handling async request (");
                    var3_5.append(this);
                    var3_5.append("): ");
                    var3_5.append(var1_3);
                    Slog.w(var2_4, var3_5.toString());
                    ** continue;
                }
            }
            this.finish();
            throw var1_2;
        }
    }

    public static abstract class PendingRequest<S extends AbstractRemoteService<S, I>, I extends IInterface>
    extends BasePendingRequest<S, I> {
        private final Handler mServiceHandler;
        private final Runnable mTimeoutTrigger;

        protected PendingRequest(S s) {
            super(s);
            this.mServiceHandler = ((AbstractRemoteService)s).mHandler;
            this.mTimeoutTrigger = new _$$Lambda$AbstractRemoteService$PendingRequest$IBoaBGXZQEXJr69u3aJF_LCJ42Y(this, (AbstractRemoteService)s);
            this.mServiceHandler.postAtTime(this.mTimeoutTrigger, SystemClock.uptimeMillis() + ((AbstractRemoteService)s).getRemoteRequestMillis());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ void lambda$new$0$AbstractRemoteService$PendingRequest(AbstractRemoteService abstractRemoteService) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mCancelled) {
                    return;
                }
                this.mCompleted = true;
            }
            AbstractRemoteService abstractRemoteService2 = (AbstractRemoteService)this.mWeakService.get();
            if (abstractRemoteService2 != null) {
                String string2 = this.mTag;
                object = new StringBuilder();
                ((StringBuilder)object).append("timed out after ");
                ((StringBuilder)object).append(abstractRemoteService.getRemoteRequestMillis());
                ((StringBuilder)object).append(" ms");
                Slog.w(string2, ((StringBuilder)object).toString());
                abstractRemoteService2.finishRequest(this);
                this.onTimeout(abstractRemoteService2);
                return;
            }
            Slog.w(this.mTag, "timed out (no service)");
        }

        @Override
        final void onCancel() {
            this.mServiceHandler.removeCallbacks(this.mTimeoutTrigger);
        }

        @Override
        final void onFinished() {
            this.mServiceHandler.removeCallbacks(this.mTimeoutTrigger);
        }

        protected abstract void onTimeout(S var1);
    }

    private class RemoteServiceConnection
    implements ServiceConnection {
        private RemoteServiceConnection() {
        }

        @Override
        public void onBindingDied(ComponentName componentName) {
            if (AbstractRemoteService.this.mVerbose) {
                Slog.v(AbstractRemoteService.this.mTag, "onBindingDied()");
            }
            AbstractRemoteService.this.scheduleUnbind(false);
        }

        @Override
        public void onServiceConnected(ComponentName object, IBinder iBinder) {
            if (AbstractRemoteService.this.mVerbose) {
                Slog.v(AbstractRemoteService.this.mTag, "onServiceConnected()");
            }
            if (!AbstractRemoteService.this.mDestroyed && AbstractRemoteService.this.mBinding) {
                AbstractRemoteService.this.mBinding = false;
                try {
                    iBinder.linkToDeath(AbstractRemoteService.this, 0);
                    object = AbstractRemoteService.this;
                    ((AbstractRemoteService)object).mService = ((AbstractRemoteService)object).getServiceInterface(iBinder);
                }
                catch (RemoteException remoteException) {
                    AbstractRemoteService.this.handleBinderDied();
                    return;
                }
                AbstractRemoteService.this.handleOnConnectedStateChangedInternal(true);
                AbstractRemoteService.this.mServiceDied = false;
                return;
            }
            Slog.wtf(AbstractRemoteService.this.mTag, "onServiceConnected() was dispatched after unbindService.");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            if (AbstractRemoteService.this.mVerbose) {
                Slog.v(AbstractRemoteService.this.mTag, "onServiceDisconnected()");
            }
            AbstractRemoteService.this.mBinding = true;
            AbstractRemoteService.this.mService = null;
        }
    }

    public static interface VultureCallback<T> {
        public void onServiceDied(T var1);
    }

}


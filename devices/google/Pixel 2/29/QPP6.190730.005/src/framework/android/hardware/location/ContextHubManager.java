/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.content.Context;
import android.hardware.location.ContextHubClient;
import android.hardware.location.ContextHubClientCallback;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.ContextHubMessage;
import android.hardware.location.ContextHubTransaction;
import android.hardware.location.IContextHubCallback;
import android.hardware.location.IContextHubClient;
import android.hardware.location.IContextHubClientCallback;
import android.hardware.location.IContextHubService;
import android.hardware.location.IContextHubTransactionCallback;
import android.hardware.location.NanoApp;
import android.hardware.location.NanoAppBinary;
import android.hardware.location.NanoAppFilter;
import android.hardware.location.NanoAppInstanceInfo;
import android.hardware.location.NanoAppMessage;
import android.hardware.location.NanoAppState;
import android.hardware.location._$$Lambda$ContextHubManager$3$5yx25kUuvL9qy3uBcIzI3sQQoL8;
import android.hardware.location._$$Lambda$ContextHubManager$3$8oeFzBAC_VuH1d32Kod8BVn0Os8;
import android.hardware.location._$$Lambda$ContextHubManager$3$KgVQePwT_QpjU9EQTp2L3LsHE5Y;
import android.hardware.location._$$Lambda$ContextHubManager$3$On2Q5Obzm4_zLY0UP3Xs4E3P_V0;
import android.hardware.location._$$Lambda$ContextHubManager$3$U9x_HK_GdADIEQ3mS5mDWMNWMu8;
import android.hardware.location._$$Lambda$ContextHubManager$3$hASoxw9hzmd9l2NpC91O5tXLzxU;
import android.hardware.location._$$Lambda$ContextHubManager$3$kLhhBRChCeue1LKohd5lK_lfKTU;
import android.hardware.location._$$Lambda$ContextHubManager$4$sylEfC1Rx_cxuQRnKuthZXmV8KI;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
public final class ContextHubManager {
    public static final int EVENT_HUB_RESET = 6;
    public static final int EVENT_NANOAPP_ABORTED = 4;
    public static final int EVENT_NANOAPP_DISABLED = 3;
    public static final int EVENT_NANOAPP_ENABLED = 2;
    public static final int EVENT_NANOAPP_LOADED = 0;
    public static final int EVENT_NANOAPP_MESSAGE = 5;
    public static final int EVENT_NANOAPP_UNLOADED = 1;
    public static final String EXTRA_CONTEXT_HUB_INFO = "android.hardware.location.extra.CONTEXT_HUB_INFO";
    public static final String EXTRA_EVENT_TYPE = "android.hardware.location.extra.EVENT_TYPE";
    public static final String EXTRA_MESSAGE = "android.hardware.location.extra.MESSAGE";
    public static final String EXTRA_NANOAPP_ABORT_CODE = "android.hardware.location.extra.NANOAPP_ABORT_CODE";
    public static final String EXTRA_NANOAPP_ID = "android.hardware.location.extra.NANOAPP_ID";
    private static final String TAG = "ContextHubManager";
    private Callback mCallback;
    private Handler mCallbackHandler;
    private final IContextHubCallback.Stub mClientCallback = new IContextHubCallback.Stub(){

        public /* synthetic */ void lambda$onMessageReceipt$0$ContextHubManager$4(int n, int n2, ContextHubMessage contextHubMessage) {
            ContextHubManager.this.invokeOnMessageReceiptCallback(n, n2, contextHubMessage);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onMessageReceipt(int n, int n2, ContextHubMessage contextHubMessage) {
            ContextHubManager contextHubManager = ContextHubManager.this;
            synchronized (contextHubManager) {
                if (ContextHubManager.this.mCallback != null) {
                    Handler handler = ContextHubManager.this.mCallbackHandler;
                    _$$Lambda$ContextHubManager$4$sylEfC1Rx_cxuQRnKuthZXmV8KI _$$Lambda$ContextHubManager$4$sylEfC1Rx_cxuQRnKuthZXmV8KI = new _$$Lambda$ContextHubManager$4$sylEfC1Rx_cxuQRnKuthZXmV8KI(this, n, n2, contextHubMessage);
                    handler.post(_$$Lambda$ContextHubManager$4$sylEfC1Rx_cxuQRnKuthZXmV8KI);
                } else if (ContextHubManager.this.mLocalCallback != null) {
                    ContextHubManager.this.mLocalCallback.onMessageReceipt(n, n2, contextHubMessage);
                }
                return;
            }
        }
    };
    @Deprecated
    private ICallback mLocalCallback;
    private final Looper mMainLooper;
    private final IContextHubService mService;

    public ContextHubManager(Context context, Looper looper) throws ServiceManager.ServiceNotFoundException {
        this.mMainLooper = looper;
        this.mService = IContextHubService.Stub.asInterface(ServiceManager.getServiceOrThrow("contexthub"));
        try {
            this.mService.registerCallback(this.mClientCallback);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private IContextHubClientCallback createClientCallback(final ContextHubClient contextHubClient, final ContextHubClientCallback contextHubClientCallback, final Executor executor) {
        return new IContextHubClientCallback.Stub(){

            static /* synthetic */ void lambda$onHubReset$1(ContextHubClientCallback contextHubClientCallback2, ContextHubClient contextHubClient2) {
                contextHubClientCallback2.onHubReset(contextHubClient2);
            }

            static /* synthetic */ void lambda$onMessageFromNanoApp$0(ContextHubClientCallback contextHubClientCallback2, ContextHubClient contextHubClient2, NanoAppMessage nanoAppMessage) {
                contextHubClientCallback2.onMessageFromNanoApp(contextHubClient2, nanoAppMessage);
            }

            static /* synthetic */ void lambda$onNanoAppAborted$2(ContextHubClientCallback contextHubClientCallback2, ContextHubClient contextHubClient2, long l, int n) {
                contextHubClientCallback2.onNanoAppAborted(contextHubClient2, l, n);
            }

            static /* synthetic */ void lambda$onNanoAppDisabled$6(ContextHubClientCallback contextHubClientCallback2, ContextHubClient contextHubClient2, long l) {
                contextHubClientCallback2.onNanoAppDisabled(contextHubClient2, l);
            }

            static /* synthetic */ void lambda$onNanoAppEnabled$5(ContextHubClientCallback contextHubClientCallback2, ContextHubClient contextHubClient2, long l) {
                contextHubClientCallback2.onNanoAppEnabled(contextHubClient2, l);
            }

            static /* synthetic */ void lambda$onNanoAppLoaded$3(ContextHubClientCallback contextHubClientCallback2, ContextHubClient contextHubClient2, long l) {
                contextHubClientCallback2.onNanoAppLoaded(contextHubClient2, l);
            }

            static /* synthetic */ void lambda$onNanoAppUnloaded$4(ContextHubClientCallback contextHubClientCallback2, ContextHubClient contextHubClient2, long l) {
                contextHubClientCallback2.onNanoAppUnloaded(contextHubClient2, l);
            }

            @Override
            public void onHubReset() {
                executor.execute(new _$$Lambda$ContextHubManager$3$kLhhBRChCeue1LKohd5lK_lfKTU(contextHubClientCallback, contextHubClient));
            }

            @Override
            public void onMessageFromNanoApp(NanoAppMessage nanoAppMessage) {
                executor.execute(new _$$Lambda$ContextHubManager$3$U9x_HK_GdADIEQ3mS5mDWMNWMu8(contextHubClientCallback, contextHubClient, nanoAppMessage));
            }

            @Override
            public void onNanoAppAborted(long l, int n) {
                executor.execute(new _$$Lambda$ContextHubManager$3$hASoxw9hzmd9l2NpC91O5tXLzxU(contextHubClientCallback, contextHubClient, l, n));
            }

            @Override
            public void onNanoAppDisabled(long l) {
                executor.execute(new _$$Lambda$ContextHubManager$3$On2Q5Obzm4_zLY0UP3Xs4E3P_V0(contextHubClientCallback, contextHubClient, l));
            }

            @Override
            public void onNanoAppEnabled(long l) {
                executor.execute(new _$$Lambda$ContextHubManager$3$8oeFzBAC_VuH1d32Kod8BVn0Os8(contextHubClientCallback, contextHubClient, l));
            }

            @Override
            public void onNanoAppLoaded(long l) {
                executor.execute(new _$$Lambda$ContextHubManager$3$5yx25kUuvL9qy3uBcIzI3sQQoL8(contextHubClientCallback, contextHubClient, l));
            }

            @Override
            public void onNanoAppUnloaded(long l) {
                executor.execute(new _$$Lambda$ContextHubManager$3$KgVQePwT_QpjU9EQTp2L3LsHE5Y(contextHubClientCallback, contextHubClient, l));
            }
        };
    }

    private IContextHubTransactionCallback createQueryCallback(final ContextHubTransaction<List<NanoAppState>> contextHubTransaction) {
        return new IContextHubTransactionCallback.Stub(){

            @Override
            public void onQueryResponse(int n, List<NanoAppState> list) {
                contextHubTransaction.setResponse(new ContextHubTransaction.Response<List<NanoAppState>>(n, list));
            }

            @Override
            public void onTransactionComplete(int n) {
                Log.e(ContextHubManager.TAG, "Received a non-query callback on a query request");
                contextHubTransaction.setResponse(new ContextHubTransaction.Response<Object>(7, null));
            }
        };
    }

    private IContextHubTransactionCallback createTransactionCallback(final ContextHubTransaction<Void> contextHubTransaction) {
        return new IContextHubTransactionCallback.Stub(){

            @Override
            public void onQueryResponse(int n, List<NanoAppState> list) {
                Log.e(ContextHubManager.TAG, "Received a query callback on a non-query request");
                contextHubTransaction.setResponse(new ContextHubTransaction.Response<Object>(7, null));
            }

            @Override
            public void onTransactionComplete(int n) {
                contextHubTransaction.setResponse(new ContextHubTransaction.Response<Object>(n, null));
            }
        };
    }

    private void invokeOnMessageReceiptCallback(int n, int n2, ContextHubMessage contextHubMessage) {
        synchronized (this) {
            if (this.mCallback != null) {
                this.mCallback.onMessageReceipt(n, n2, contextHubMessage);
            }
            return;
        }
    }

    public ContextHubClient createClient(ContextHubInfo object, PendingIntent pendingIntent, long l) {
        Preconditions.checkNotNull(pendingIntent);
        Preconditions.checkNotNull(object);
        ContextHubClient contextHubClient = new ContextHubClient((ContextHubInfo)object, true);
        try {
            object = this.mService.createPendingIntentClient(((ContextHubInfo)object).getId(), pendingIntent, l);
            contextHubClient.setClientProxy((IContextHubClient)object);
            return contextHubClient;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ContextHubClient createClient(ContextHubInfo contextHubInfo, ContextHubClientCallback contextHubClientCallback) {
        return this.createClient(contextHubInfo, contextHubClientCallback, new HandlerExecutor(Handler.getMain()));
    }

    public ContextHubClient createClient(ContextHubInfo object, ContextHubClientCallback object2, Executor executor) {
        Preconditions.checkNotNull(object2, "Callback cannot be null");
        Preconditions.checkNotNull(object, "ContextHubInfo cannot be null");
        Preconditions.checkNotNull(executor, "Executor cannot be null");
        ContextHubClient contextHubClient = new ContextHubClient((ContextHubInfo)object, false);
        object2 = this.createClientCallback(contextHubClient, (ContextHubClientCallback)object2, executor);
        try {
            object = this.mService.createClient(((ContextHubInfo)object).getId(), (IContextHubClientCallback)object2);
            contextHubClient.setClientProxy((IContextHubClient)object);
            return contextHubClient;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> disableNanoApp(ContextHubInfo contextHubInfo, long l) {
        Preconditions.checkNotNull(contextHubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<Void>(3);
        IContextHubTransactionCallback iContextHubTransactionCallback = this.createTransactionCallback(contextHubTransaction);
        try {
            this.mService.disableNanoApp(contextHubInfo.getId(), iContextHubTransactionCallback, l);
            return contextHubTransaction;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> enableNanoApp(ContextHubInfo contextHubInfo, long l) {
        Preconditions.checkNotNull(contextHubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<Void>(2);
        IContextHubTransactionCallback iContextHubTransactionCallback = this.createTransactionCallback(contextHubTransaction);
        try {
            this.mService.enableNanoApp(contextHubInfo.getId(), iContextHubTransactionCallback, l);
            return contextHubTransaction;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int[] findNanoAppOnHub(int n, NanoAppFilter arrn) {
        try {
            arrn = this.mService.findNanoAppOnHub(n, (NanoAppFilter)arrn);
            return arrn;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int[] getContextHubHandles() {
        try {
            int[] arrn = this.mService.getContextHubHandles();
            return arrn;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public ContextHubInfo getContextHubInfo(int n) {
        try {
            ContextHubInfo contextHubInfo = this.mService.getContextHubInfo(n);
            return contextHubInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<ContextHubInfo> getContextHubs() {
        try {
            List<ContextHubInfo> list = this.mService.getContextHubs();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NanoAppInstanceInfo getNanoAppInstanceInfo(int n) {
        try {
            NanoAppInstanceInfo nanoAppInstanceInfo = this.mService.getNanoAppInstanceInfo(n);
            return nanoAppInstanceInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int loadNanoApp(int n, NanoApp nanoApp) {
        try {
            n = this.mService.loadNanoApp(n, nanoApp);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> loadNanoApp(ContextHubInfo contextHubInfo, NanoAppBinary nanoAppBinary) {
        Preconditions.checkNotNull(contextHubInfo, "ContextHubInfo cannot be null");
        Preconditions.checkNotNull(nanoAppBinary, "NanoAppBinary cannot be null");
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<Void>(0);
        IContextHubTransactionCallback iContextHubTransactionCallback = this.createTransactionCallback(contextHubTransaction);
        try {
            this.mService.loadNanoAppOnHub(contextHubInfo.getId(), iContextHubTransactionCallback, nanoAppBinary);
            return contextHubTransaction;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<List<NanoAppState>> queryNanoApps(ContextHubInfo contextHubInfo) {
        Preconditions.checkNotNull(contextHubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<List<NanoAppState>> contextHubTransaction = new ContextHubTransaction<List<NanoAppState>>(4);
        IContextHubTransactionCallback iContextHubTransactionCallback = this.createQueryCallback(contextHubTransaction);
        try {
            this.mService.queryNanoApps(contextHubInfo.getId(), iContextHubTransactionCallback);
            return contextHubTransaction;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public int registerCallback(Callback callback) {
        return this.registerCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public int registerCallback(Callback callback, Handler handler) {
        synchronized (this) {
            if (this.mCallback != null) {
                Log.w(TAG, "Max number of callbacks reached!");
                return -1;
            }
            this.mCallback = callback;
            if (handler == null) {
                handler = new Handler(this.mMainLooper);
            }
            this.mCallbackHandler = handler;
            return 0;
        }
    }

    @Deprecated
    public int registerCallback(ICallback iCallback) {
        if (this.mLocalCallback != null) {
            Log.w(TAG, "Max number of local callbacks reached!");
            return -1;
        }
        this.mLocalCallback = iCallback;
        return 0;
    }

    @Deprecated
    public int sendMessage(int n, int n2, ContextHubMessage contextHubMessage) {
        try {
            n = this.mService.sendMessage(n, n2, contextHubMessage);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int unloadNanoApp(int n) {
        try {
            n = this.mService.unloadNanoApp(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ContextHubTransaction<Void> unloadNanoApp(ContextHubInfo contextHubInfo, long l) {
        Preconditions.checkNotNull(contextHubInfo, "ContextHubInfo cannot be null");
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<Void>(1);
        IContextHubTransactionCallback iContextHubTransactionCallback = this.createTransactionCallback(contextHubTransaction);
        try {
            this.mService.unloadNanoAppFromHub(contextHubInfo.getId(), iContextHubTransactionCallback, l);
            return contextHubTransaction;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public int unregisterCallback(Callback callback) {
        synchronized (this) {
            if (callback != this.mCallback) {
                Log.w(TAG, "Cannot recognize callback!");
                return -1;
            }
            this.mCallback = null;
            this.mCallbackHandler = null;
            return 0;
        }
    }

    @Deprecated
    public int unregisterCallback(ICallback iCallback) {
        synchronized (this) {
            block4 : {
                if (iCallback == this.mLocalCallback) break block4;
                Log.w(TAG, "Cannot recognize local callback!");
                return -1;
            }
            this.mLocalCallback = null;
            return 0;
        }
    }

    @Deprecated
    public static abstract class Callback {
        protected Callback() {
        }

        public abstract void onMessageReceipt(int var1, int var2, ContextHubMessage var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Event {
    }

    @Deprecated
    public static interface ICallback {
        public void onMessageReceipt(int var1, int var2, ContextHubMessage var3);
    }

}


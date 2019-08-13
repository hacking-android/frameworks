/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.appprediction.-$
 *  android.service.appprediction.-$$Lambda
 *  android.service.appprediction.-$$Lambda$AppPredictionService
 *  android.service.appprediction.-$$Lambda$AppPredictionService$1
 *  android.service.appprediction.-$$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik
 *  android.service.appprediction.-$$Lambda$AppPredictionService$1$CDfn7BNaxDP2sak-07muIxqD0XM
 *  android.service.appprediction.-$$Lambda$AppPredictionService$1$dlPwi16n_6u5po2eN8wlW4I1bRw
 *  android.service.appprediction.-$$Lambda$AppPredictionService$1$oZsrXgV2j_8Zo7GiDdpYvbTz4h8
 *  android.service.appprediction.-$$Lambda$AppPredictionService$1$oaGU8LD9Stlihi_KoW_pb0jZjQk
 *  android.service.appprediction.-$$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y
 *  android.service.appprediction.-$$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0
 *  android.service.appprediction.-$$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk
 */
package android.service.appprediction;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.app.prediction.IPredictionCallback;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.appprediction.-$;
import android.service.appprediction.IPredictionService;
import android.service.appprediction._$$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik;
import android.service.appprediction._$$Lambda$AppPredictionService$1$CDfn7BNaxDP2sak_07muIxqD0XM;
import android.service.appprediction._$$Lambda$AppPredictionService$1$dlPwi16n_6u5po2eN8wlW4I1bRw;
import android.service.appprediction._$$Lambda$AppPredictionService$1$oZsrXgV2j_8Zo7GiDdpYvbTz4h8;
import android.service.appprediction._$$Lambda$AppPredictionService$1$oaGU8LD9Stlihi_KoW_pb0jZjQk;
import android.service.appprediction._$$Lambda$AppPredictionService$BU3RVDaz_RDf_0tC58L6QbapMAs;
import android.service.appprediction._$$Lambda$AppPredictionService$QdiGSCeMaWGP0DGJNn4uhqgT9ZA;
import android.service.appprediction._$$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y;
import android.service.appprediction._$$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0;
import android.service.appprediction._$$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@SystemApi
public abstract class AppPredictionService
extends Service {
    public static final String SERVICE_INTERFACE = "android.service.appprediction.AppPredictionService";
    private static final String TAG = "AppPredictionService";
    private Handler mHandler;
    private final IPredictionService mInterface = new IPredictionService.Stub(){

        static /* synthetic */ void lambda$onCreatePredictionSession$0(Object object, AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) {
            ((AppPredictionService)object).doCreatePredictionSession(appPredictionContext, appPredictionSessionId);
        }

        static /* synthetic */ void lambda$onDestroyPredictionSession$4(Object object, AppPredictionSessionId appPredictionSessionId) {
            ((AppPredictionService)object).doDestroyPredictionSession(appPredictionSessionId);
        }

        static /* synthetic */ void lambda$registerPredictionUpdates$1(Object object, AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) {
            ((AppPredictionService)object).doRegisterPredictionUpdates(appPredictionSessionId, iPredictionCallback);
        }

        static /* synthetic */ void lambda$requestPredictionUpdate$3(Object object, AppPredictionSessionId appPredictionSessionId) {
            ((AppPredictionService)object).doRequestPredictionUpdate(appPredictionSessionId);
        }

        static /* synthetic */ void lambda$unregisterPredictionUpdates$2(Object object, AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) {
            ((AppPredictionService)object).doUnregisterPredictionUpdates(appPredictionSessionId, iPredictionCallback);
        }

        @Override
        public void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0.INSTANCE, AppPredictionService.this, appPredictionSessionId, appTargetEvent));
        }

        @Override
        public void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String string2, ParceledListSlice parceledListSlice) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y.INSTANCE, AppPredictionService.this, appPredictionSessionId, string2, parceledListSlice.getList()));
        }

        @Override
        public void onCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AppPredictionService$1$dlPwi16n_6u5po2eN8wlW4I1bRw.INSTANCE, AppPredictionService.this, appPredictionContext, appPredictionSessionId));
        }

        @Override
        public void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AppPredictionService$1$oZsrXgV2j_8Zo7GiDdpYvbTz4h8.INSTANCE, AppPredictionService.this, appPredictionSessionId));
        }

        @Override
        public void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AppPredictionService$1$CDfn7BNaxDP2sak_07muIxqD0XM.INSTANCE, AppPredictionService.this, appPredictionSessionId, iPredictionCallback));
        }

        @Override
        public void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AppPredictionService$1$oaGU8LD9Stlihi_KoW_pb0jZjQk.INSTANCE, AppPredictionService.this, appPredictionSessionId));
        }

        @Override
        public void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk.INSTANCE, AppPredictionService.this, appPredictionSessionId, parceledListSlice.getList(), null, new CallbackWrapper(iPredictionCallback, null)));
        }

        @Override
        public void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) {
            AppPredictionService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik.INSTANCE, AppPredictionService.this, appPredictionSessionId, iPredictionCallback));
        }
    };
    private final ArrayMap<AppPredictionSessionId, ArrayList<CallbackWrapper>> mSessionCallbacks = new ArrayMap();

    private void doCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) {
        this.mSessionCallbacks.put(appPredictionSessionId, new ArrayList());
        this.onCreatePredictionSession(appPredictionContext, appPredictionSessionId);
    }

    private void doDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) {
        this.mSessionCallbacks.remove(appPredictionSessionId);
        this.onDestroyPredictionSession(appPredictionSessionId);
    }

    private void doRegisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback object) {
        ArrayList<CallbackWrapper> arrayList = this.mSessionCallbacks.get(appPredictionSessionId);
        if (arrayList == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to register for updates for unknown session: ");
            ((StringBuilder)object).append(appPredictionSessionId);
            Slog.e(TAG, ((StringBuilder)object).toString());
            return;
        }
        if (this.findCallbackWrapper(arrayList, (IPredictionCallback)object) == null) {
            arrayList.add(new CallbackWrapper((IPredictionCallback)object, new _$$Lambda$AppPredictionService$BU3RVDaz_RDf_0tC58L6QbapMAs(this, arrayList)));
            if (arrayList.size() == 1) {
                this.onStartPredictionUpdates();
            }
        }
    }

    private void doRequestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) {
        ArrayList<CallbackWrapper> arrayList = this.mSessionCallbacks.get(appPredictionSessionId);
        if (arrayList != null && !arrayList.isEmpty()) {
            this.onRequestPredictionUpdate(appPredictionSessionId);
        }
    }

    private void doUnregisterPredictionUpdates(AppPredictionSessionId object, IPredictionCallback object2) {
        ArrayList<CallbackWrapper> arrayList = this.mSessionCallbacks.get(object);
        if (arrayList == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failed to unregister for updates for unknown session: ");
            ((StringBuilder)object2).append(object);
            Slog.e(TAG, ((StringBuilder)object2).toString());
            return;
        }
        object = this.findCallbackWrapper(arrayList, (IPredictionCallback)object2);
        if (object != null) {
            this.removeCallbackWrapper(arrayList, (CallbackWrapper)object);
        }
    }

    private CallbackWrapper findCallbackWrapper(ArrayList<CallbackWrapper> arrayList, IPredictionCallback iPredictionCallback) {
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            if (!arrayList.get(i).isCallback(iPredictionCallback)) continue;
            return arrayList.get(i);
        }
        return null;
    }

    private void removeCallbackWrapper(ArrayList<CallbackWrapper> arrayList, CallbackWrapper callbackWrapper) {
        if (arrayList == null) {
            return;
        }
        arrayList.remove(callbackWrapper);
        if (arrayList.isEmpty()) {
            this.onStopPredictionUpdates();
        }
    }

    public /* synthetic */ void lambda$doRegisterPredictionUpdates$0$AppPredictionService(ArrayList arrayList, CallbackWrapper callbackWrapper) {
        this.removeCallbackWrapper(arrayList, callbackWrapper);
    }

    public /* synthetic */ void lambda$doRegisterPredictionUpdates$1$AppPredictionService(ArrayList arrayList, CallbackWrapper callbackWrapper) {
        this.mHandler.post(new _$$Lambda$AppPredictionService$QdiGSCeMaWGP0DGJNn4uhqgT9ZA(this, arrayList, callbackWrapper));
    }

    public abstract void onAppTargetEvent(AppPredictionSessionId var1, AppTargetEvent var2);

    @Override
    public final IBinder onBind(Intent intent) {
        if ("android.service.appprediction.AppPredictionService".equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tried to bind to wrong intent (should be android.service.appprediction.AppPredictionService: ");
        stringBuilder.append(intent);
        Log.w("AppPredictionService", stringBuilder.toString());
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    public void onCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) {
    }

    public void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) {
    }

    public abstract void onLaunchLocationShown(AppPredictionSessionId var1, String var2, List<AppTargetId> var3);

    public abstract void onRequestPredictionUpdate(AppPredictionSessionId var1);

    public abstract void onSortAppTargets(AppPredictionSessionId var1, List<AppTarget> var2, CancellationSignal var3, Consumer<List<AppTarget>> var4);

    public void onStartPredictionUpdates() {
    }

    public void onStopPredictionUpdates() {
    }

    public final void updatePredictions(AppPredictionSessionId iterator, List<AppTarget> list) {
        if ((iterator = (List)this.mSessionCallbacks.get(iterator)) != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                ((CallbackWrapper)iterator.next()).accept(list);
            }
        }
    }

    private static final class CallbackWrapper
    implements Consumer<List<AppTarget>>,
    IBinder.DeathRecipient {
        private IPredictionCallback mCallback;
        private final Consumer<CallbackWrapper> mOnBinderDied;

        CallbackWrapper(IPredictionCallback iPredictionCallback, Consumer<CallbackWrapper> object) {
            this.mCallback = iPredictionCallback;
            this.mOnBinderDied = object;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to link to death: ");
                ((StringBuilder)object).append(remoteException);
                Slog.e(AppPredictionService.TAG, ((StringBuilder)object).toString());
            }
        }

        @Override
        public void accept(List<AppTarget> object) {
            try {
                if (this.mCallback != null) {
                    IPredictionCallback iPredictionCallback = this.mCallback;
                    ParceledListSlice parceledListSlice = new ParceledListSlice(object);
                    iPredictionCallback.onResult(parceledListSlice);
                }
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error sending result:");
                ((StringBuilder)object).append(remoteException);
                Slog.e(AppPredictionService.TAG, ((StringBuilder)object).toString());
            }
        }

        @Override
        public void binderDied() {
            this.mCallback = null;
            Consumer<CallbackWrapper> consumer = this.mOnBinderDied;
            if (consumer != null) {
                consumer.accept(this);
            }
        }

        public boolean isCallback(IPredictionCallback iPredictionCallback) {
            IPredictionCallback iPredictionCallback2 = this.mCallback;
            if (iPredictionCallback2 == null) {
                Slog.e(AppPredictionService.TAG, "Callback is null, likely the binder has died.");
                return false;
            }
            return iPredictionCallback2.equals(iPredictionCallback);
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.app.prediction;

import android.annotation.SystemApi;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.app.prediction.IPredictionCallback;
import android.app.prediction.IPredictionManager;
import android.app.prediction._$$Lambda$1lqxDplfWlUwgBrOynX9L0oK_uA;
import android.app.prediction._$$Lambda$AppPredictor$CallbackWrapper$gCs3O3sYRlsXAOdelds31867YXo;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@SystemApi
public final class AppPredictor {
    private static final String TAG = AppPredictor.class.getSimpleName();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mIsClosed = new AtomicBoolean(false);
    private final IPredictionManager mPredictionManager = IPredictionManager.Stub.asInterface(ServiceManager.getService("app_prediction"));
    private final ArrayMap<Callback, CallbackWrapper> mRegisteredCallbacks = new ArrayMap();
    private final AppPredictionSessionId mSessionId;

    AppPredictor(Context context, AppPredictionContext appPredictionContext) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getPackageName());
        stringBuilder.append(":");
        stringBuilder.append(UUID.randomUUID().toString());
        this.mSessionId = new AppPredictionSessionId(stringBuilder.toString());
        try {
            this.mPredictionManager.createPredictionSession(appPredictionContext, this.mSessionId);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to create predictor", remoteException);
            remoteException.rethrowAsRuntimeException();
        }
        this.mCloseGuard.open("close");
    }

    public void destroy() {
        if (!this.mIsClosed.getAndSet(true)) {
            this.mCloseGuard.close();
            try {
                this.mPredictionManager.onDestroyPredictionSession(this.mSessionId);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to notify app target event", remoteException);
                remoteException.rethrowAsRuntimeException();
            }
            return;
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mIsClosed.get()) {
                this.destroy();
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public AppPredictionSessionId getSessionId() {
        return this.mSessionId;
    }

    public void notifyAppTargetEvent(AppTargetEvent appTargetEvent) {
        if (!this.mIsClosed.get()) {
            try {
                this.mPredictionManager.notifyAppTargetEvent(this.mSessionId, appTargetEvent);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to notify app target event", remoteException);
                remoteException.rethrowAsRuntimeException();
            }
            return;
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    public void notifyLaunchLocationShown(String string2, List<AppTargetId> list) {
        if (!this.mIsClosed.get()) {
            try {
                IPredictionManager iPredictionManager = this.mPredictionManager;
                AppPredictionSessionId appPredictionSessionId = this.mSessionId;
                ParceledListSlice<AppTargetId> parceledListSlice = new ParceledListSlice<AppTargetId>(list);
                iPredictionManager.notifyLaunchLocationShown(appPredictionSessionId, string2, parceledListSlice);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to notify location shown event", remoteException);
                remoteException.rethrowAsRuntimeException();
            }
            return;
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    public void registerPredictionUpdates(Executor executor, Callback callback) {
        if (!this.mIsClosed.get()) {
            if (this.mRegisteredCallbacks.containsKey(callback)) {
                return;
            }
            try {
                Objects.requireNonNull(callback);
                _$$Lambda$1lqxDplfWlUwgBrOynX9L0oK_uA _$$Lambda$1lqxDplfWlUwgBrOynX9L0oK_uA = new _$$Lambda$1lqxDplfWlUwgBrOynX9L0oK_uA(callback);
                CallbackWrapper callbackWrapper = new CallbackWrapper(executor, _$$Lambda$1lqxDplfWlUwgBrOynX9L0oK_uA);
                this.mPredictionManager.registerPredictionUpdates(this.mSessionId, callbackWrapper);
                this.mRegisteredCallbacks.put(callback, callbackWrapper);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to register for prediction updates", remoteException);
                remoteException.rethrowAsRuntimeException();
            }
            return;
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    public void requestPredictionUpdate() {
        if (!this.mIsClosed.get()) {
            try {
                this.mPredictionManager.requestPredictionUpdate(this.mSessionId);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to request prediction update", remoteException);
                remoteException.rethrowAsRuntimeException();
            }
            return;
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    public void sortTargets(List<AppTarget> object, Executor executor, Consumer<List<AppTarget>> consumer) {
        if (!this.mIsClosed.get()) {
            try {
                IPredictionManager iPredictionManager = this.mPredictionManager;
                AppPredictionSessionId appPredictionSessionId = this.mSessionId;
                ParceledListSlice<AppTarget> parceledListSlice = new ParceledListSlice<AppTarget>((List<AppTarget>)object);
                object = new CallbackWrapper(executor, consumer);
                iPredictionManager.sortAppTargets(appPredictionSessionId, parceledListSlice, (IPredictionCallback)object);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to sort targets", remoteException);
                remoteException.rethrowAsRuntimeException();
            }
            return;
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    public void unregisterPredictionUpdates(Callback object) {
        if (!this.mIsClosed.get()) {
            if (!this.mRegisteredCallbacks.containsKey(object)) {
                return;
            }
            try {
                object = this.mRegisteredCallbacks.remove(object);
                this.mPredictionManager.unregisterPredictionUpdates(this.mSessionId, (IPredictionCallback)object);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to unregister for prediction updates", remoteException);
                remoteException.rethrowAsRuntimeException();
            }
            return;
        }
        throw new IllegalStateException("This client has already been destroyed.");
    }

    public static interface Callback {
        public void onTargetsAvailable(List<AppTarget> var1);
    }

    static class CallbackWrapper
    extends IPredictionCallback.Stub {
        private final Consumer<List<AppTarget>> mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor executor, Consumer<List<AppTarget>> consumer) {
            this.mCallback = consumer;
            this.mExecutor = executor;
        }

        public /* synthetic */ void lambda$onResult$0$AppPredictor$CallbackWrapper(ParceledListSlice parceledListSlice) {
            this.mCallback.accept(parceledListSlice.getList());
        }

        @Override
        public void onResult(ParceledListSlice parceledListSlice) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$AppPredictor$CallbackWrapper$gCs3O3sYRlsXAOdelds31867YXo _$$Lambda$AppPredictor$CallbackWrapper$gCs3O3sYRlsXAOdelds31867YXo = new _$$Lambda$AppPredictor$CallbackWrapper$gCs3O3sYRlsXAOdelds31867YXo(this, parceledListSlice);
                executor.execute(_$$Lambda$AppPredictor$CallbackWrapper$gCs3O3sYRlsXAOdelds31867YXo);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }
    }

}


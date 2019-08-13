/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Vr2dDisplayProperties;
import android.app.VrStateCallback;
import android.app._$$Lambda$VrManager$CallbackEntry$1$rgUBVVG1QhelpvAp8W3UQHDHJdU;
import android.app._$$Lambda$VrManager$CallbackEntry$2$KvHLIXm3_7igcOqTEl46YdjhHMk;
import android.content.ComponentName;
import android.os.RemoteException;
import android.service.vr.IPersistentVrStateCallbacks;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.ArrayMap;
import java.util.Map;
import java.util.concurrent.Executor;

@SystemApi
public class VrManager {
    private Map<VrStateCallback, CallbackEntry> mCallbackMap = new ArrayMap<VrStateCallback, CallbackEntry>();
    @UnsupportedAppUsage
    private final IVrManager mService;

    public VrManager(IVrManager iVrManager) {
        this.mService = iVrManager;
    }

    public int getVr2dDisplayId() {
        try {
            int n = this.mService.getVr2dDisplayId();
            return n;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return -1;
        }
    }

    public boolean isPersistentVrModeEnabled() {
        try {
            boolean bl = this.mService.getPersistentVrModeEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isVrModeEnabled() {
        try {
            boolean bl = this.mService.getVrModeState();
            return bl;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return false;
        }
    }

    public void registerVrStateCallback(Executor object, VrStateCallback vrStateCallback) {
        if (vrStateCallback != null && !this.mCallbackMap.containsKey(vrStateCallback)) {
            object = new CallbackEntry(vrStateCallback, (Executor)object);
            this.mCallbackMap.put(vrStateCallback, (CallbackEntry)object);
            try {
                this.mService.registerListener(((CallbackEntry)object).mStateCallback);
                this.mService.registerPersistentVrStateListener(((CallbackEntry)object).mPersistentStateCallback);
            }
            catch (RemoteException remoteException) {
                try {
                    this.unregisterVrStateCallback(vrStateCallback);
                }
                catch (Exception exception) {
                    remoteException.rethrowFromSystemServer();
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAndBindVrCompositor(ComponentName object) {
        try {
            IVrManager iVrManager = this.mService;
            object = object == null ? null : ((ComponentName)object).flattenToString();
            iVrManager.setAndBindCompositor((String)object);
            return;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public void setPersistentVrModeEnabled(boolean bl) {
        try {
            this.mService.setPersistentVrModeEnabled(bl);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public void setStandbyEnabled(boolean bl) {
        try {
            this.mService.setStandbyEnabled(bl);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties) {
        try {
            this.mService.setVr2dDisplayProperties(vr2dDisplayProperties);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public void setVrInputMethod(ComponentName componentName) {
    }

    public void unregisterVrStateCallback(VrStateCallback vrStateCallback) {
        CallbackEntry callbackEntry = this.mCallbackMap.remove(vrStateCallback);
        if (callbackEntry != null) {
            try {
                this.mService.unregisterListener(callbackEntry.mStateCallback);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            try {
                this.mService.unregisterPersistentVrStateListener(callbackEntry.mPersistentStateCallback);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private static class CallbackEntry {
        final VrStateCallback mCallback;
        final Executor mExecutor;
        final IPersistentVrStateCallbacks mPersistentStateCallback = new IPersistentVrStateCallbacks.Stub(){

            public /* synthetic */ void lambda$onPersistentVrStateChanged$0$VrManager$CallbackEntry$2(boolean bl) {
                CallbackEntry.this.mCallback.onPersistentVrStateChanged(bl);
            }

            @Override
            public void onPersistentVrStateChanged(boolean bl) {
                CallbackEntry.this.mExecutor.execute(new _$$Lambda$VrManager$CallbackEntry$2$KvHLIXm3_7igcOqTEl46YdjhHMk(this, bl));
            }
        };
        final IVrStateCallbacks mStateCallback = new IVrStateCallbacks.Stub(){

            public /* synthetic */ void lambda$onVrStateChanged$0$VrManager$CallbackEntry$1(boolean bl) {
                CallbackEntry.this.mCallback.onVrStateChanged(bl);
            }

            @Override
            public void onVrStateChanged(boolean bl) {
                CallbackEntry.this.mExecutor.execute(new _$$Lambda$VrManager$CallbackEntry$1$rgUBVVG1QhelpvAp8W3UQHDHJdU(this, bl));
            }
        };

        CallbackEntry(VrStateCallback vrStateCallback, Executor executor) {
            this.mCallback = vrStateCallback;
            this.mExecutor = executor;
        }

    }

}


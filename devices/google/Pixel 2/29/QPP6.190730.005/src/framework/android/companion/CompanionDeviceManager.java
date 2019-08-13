/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.companion.-$
 *  android.companion.-$$Lambda
 *  android.companion.-$$Lambda$OThxsns9MAD5QsKURFQAFbt-3qc
 *  android.companion.-$$Lambda$ZUPGnRMz08ZrG1ogNO-2O5Hso3I
 */
package android.companion;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.companion.-$;
import android.companion.AssociationRequest;
import android.companion.ICompanionDeviceManager;
import android.companion.IFindDeviceCallback;
import android.companion._$$Lambda$CompanionDeviceManager$CallbackProxy$gkUVA3m3QgEEk8G84_kcBFARHvo;
import android.companion._$$Lambda$OThxsns9MAD5QsKURFQAFbt_3qc;
import android.companion._$$Lambda$ZUPGnRMz08ZrG1ogNO_2O5Hso3I;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public final class CompanionDeviceManager {
    public static final String COMPANION_DEVICE_DISCOVERY_PACKAGE_NAME = "com.android.companiondevicemanager";
    private static final boolean DEBUG = false;
    public static final String EXTRA_DEVICE = "android.companion.extra.DEVICE";
    private static final String LOG_TAG = "CompanionDeviceManager";
    private final Context mContext;
    private final ICompanionDeviceManager mService;

    public CompanionDeviceManager(ICompanionDeviceManager iCompanionDeviceManager, Context context) {
        this.mService = iCompanionDeviceManager;
        this.mContext = context;
    }

    private boolean checkFeaturePresent() {
        boolean bl = this.mService != null;
        return bl;
    }

    private Activity getActivity() {
        return (Activity)this.mContext;
    }

    private String getCallingPackage() {
        return this.mContext.getPackageName();
    }

    public void associate(AssociationRequest associationRequest, Callback callback, Handler handler) {
        if (!this.checkFeaturePresent()) {
            return;
        }
        Preconditions.checkNotNull(associationRequest, "Request cannot be null");
        Preconditions.checkNotNull(callback, "Callback cannot be null");
        try {
            ICompanionDeviceManager iCompanionDeviceManager = this.mService;
            CallbackProxy callbackProxy = new CallbackProxy(associationRequest, callback, Handler.mainIfNull(handler));
            iCompanionDeviceManager.associate(associationRequest, callbackProxy, this.getCallingPackage());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disassociate(String string2) {
        if (!this.checkFeaturePresent()) {
            return;
        }
        try {
            this.mService.disassociate(string2, this.getCallingPackage());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<String> getAssociations() {
        if (!this.checkFeaturePresent()) {
            return Collections.emptyList();
        }
        try {
            List<String> list = this.mService.getAssociations(this.getCallingPackage(), this.mContext.getUserId());
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasNotificationAccess(ComponentName componentName) {
        if (!this.checkFeaturePresent()) {
            return false;
        }
        try {
            boolean bl = this.mService.hasNotificationAccess(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestNotificationAccess(ComponentName parcelable) {
        if (!this.checkFeaturePresent()) {
            return;
        }
        try {
            parcelable = this.mService.requestNotificationAccess((ComponentName)parcelable).getIntentSender();
            this.mContext.startIntentSender((IntentSender)parcelable, null, 0, 0, 0);
            return;
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            throw new RuntimeException(sendIntentException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static abstract class Callback {
        public abstract void onDeviceFound(IntentSender var1);

        public abstract void onFailure(CharSequence var1);
    }

    private class CallbackProxy
    extends IFindDeviceCallback.Stub
    implements Application.ActivityLifecycleCallbacks {
        private Callback mCallback;
        private Handler mHandler;
        final Object mLock = new Object();
        private AssociationRequest mRequest;

        private CallbackProxy(AssociationRequest associationRequest, Callback callback, Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler;
            this.mRequest = associationRequest;
            CompanionDeviceManager.this.getActivity().getApplication().registerActivityLifecycleCallbacks(this);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public /* synthetic */ void lambda$lockAndPost$0$CompanionDeviceManager$CallbackProxy(BiConsumer biConsumer, Object object) {
            Object object2 = this.mLock;
            // MONITORENTER : object2
            Callback callback = this.mCallback;
            // MONITOREXIT : object2
            if (callback == null) return;
            biConsumer.accept(callback, object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        <T> void lockAndPost(BiConsumer<Callback, T> biConsumer, T t) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mHandler != null) {
                    Handler handler = this.mHandler;
                    _$$Lambda$CompanionDeviceManager$CallbackProxy$gkUVA3m3QgEEk8G84_kcBFARHvo _$$Lambda$CompanionDeviceManager$CallbackProxy$gkUVA3m3QgEEk8G84_kcBFARHvo = new _$$Lambda$CompanionDeviceManager$CallbackProxy$gkUVA3m3QgEEk8G84_kcBFARHvo(this, biConsumer, t);
                    handler.post(_$$Lambda$CompanionDeviceManager$CallbackProxy$gkUVA3m3QgEEk8G84_kcBFARHvo);
                }
                return;
            }
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onActivityDestroyed(Activity activity) {
            Object object = this.mLock;
            synchronized (object) {
                if (activity != CompanionDeviceManager.this.getActivity()) {
                    return;
                }
                try {
                    CompanionDeviceManager.this.mService.stopScan(this.mRequest, this, CompanionDeviceManager.this.getCallingPackage());
                }
                catch (RemoteException remoteException) {
                    remoteException.rethrowFromSystemServer();
                }
                CompanionDeviceManager.this.getActivity().getApplication().unregisterActivityLifecycleCallbacks(this);
                this.mCallback = null;
                this.mHandler = null;
                this.mRequest = null;
                return;
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onFailure(CharSequence charSequence) {
            this.lockAndPost((BiConsumer<Callback, T>)_$$Lambda$ZUPGnRMz08ZrG1ogNO_2O5Hso3I.INSTANCE, (T)charSequence);
        }

        @Override
        public void onSuccess(PendingIntent pendingIntent) {
            this.lockAndPost((BiConsumer<Callback, T>)_$$Lambda$OThxsns9MAD5QsKURFQAFbt_3qc.INSTANCE, (T)pendingIntent.getIntentSender());
        }
    }

}


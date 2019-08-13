/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.content.pm.IPackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.ImsException;
import android.telephony.ims._$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT_fqeBCDl6FWK1nXcIt0;
import android.telephony.ims._$$Lambda$ProvisioningManager$Callback$CallbackBinder$R_8jXQuOM7aV7dIwYBzcWwV_YpM;
import android.telephony.ims._$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY;
import android.telephony.ims._$$Lambda$ProvisioningManager$Callback$CallbackBinder$rsWuitP9riQDO6nFxj5wJBdYX40;
import android.telephony.ims.aidl.IImsConfigCallback;
import com.android.internal.telephony.ITelephony;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
public class ProvisioningManager {
    public static final int KEY_VOICE_OVER_WIFI_MODE_OVERRIDE = 27;
    public static final int KEY_VOICE_OVER_WIFI_ROAMING_ENABLED_OVERRIDE = 26;
    public static final int PROVISIONING_VALUE_DISABLED = 0;
    public static final int PROVISIONING_VALUE_ENABLED = 1;
    public static final String STRING_QUERY_RESULT_ERROR_GENERIC = "STRING_QUERY_RESULT_ERROR_GENERIC";
    public static final String STRING_QUERY_RESULT_ERROR_NOT_READY = "STRING_QUERY_RESULT_ERROR_NOT_READY";
    private int mSubId;

    private ProvisioningManager(int n) {
        this.mSubId = n;
    }

    public static ProvisioningManager createForSubscriptionId(int n) {
        if (SubscriptionManager.isValidSubscriptionId(n)) {
            return new ProvisioningManager(n);
        }
        throw new IllegalArgumentException("Invalid subscription ID");
    }

    private static ITelephony getITelephony() {
        ITelephony iTelephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        if (iTelephony != null) {
            return iTelephony;
        }
        throw new RuntimeException("Could not find Telephony Service.");
    }

    private static boolean isImsAvailableOnDevice() {
        IPackageManager iPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        if (iPackageManager == null) {
            return true;
        }
        try {
            boolean bl = iPackageManager.hasSystemFeature("android.hardware.telephony.ims", 0);
            return bl;
        }
        catch (RemoteException remoteException) {
            return true;
        }
    }

    public int getProvisioningIntValue(int n) {
        try {
            n = ProvisioningManager.getITelephony().getImsProvisioningInt(this.mSubId, n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean getProvisioningStatusForCapability(int n, int n2) {
        try {
            boolean bl = ProvisioningManager.getITelephony().getImsProvisioningStatusForCapability(this.mSubId, n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public String getProvisioningStringValue(int n) {
        try {
            String string2 = ProvisioningManager.getITelephony().getImsProvisioningString(this.mSubId, n);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void registerProvisioningChangedCallback(Executor executor, Callback callback) throws ImsException {
        if (ProvisioningManager.isImsAvailableOnDevice()) {
            callback.setExecutor(executor);
            try {
                ProvisioningManager.getITelephony().registerImsProvisioningChangedCallback(this.mSubId, callback.getBinder());
                return;
            }
            catch (RemoteException | IllegalStateException exception) {
                throw new ImsException(exception.getMessage(), 1);
            }
        }
        throw new ImsException("IMS not available on device.", 2);
    }

    public int setProvisioningIntValue(int n, int n2) {
        try {
            n = ProvisioningManager.getITelephony().setImsProvisioningInt(this.mSubId, n, n2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setProvisioningStatusForCapability(int n, int n2, boolean bl) {
        try {
            ProvisioningManager.getITelephony().setImsProvisioningStatusForCapability(this.mSubId, n, n2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public int setProvisioningStringValue(int n, String string2) {
        try {
            n = ProvisioningManager.getITelephony().setImsProvisioningString(this.mSubId, n, string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void unregisterProvisioningChangedCallback(Callback callback) {
        try {
            ProvisioningManager.getITelephony().unregisterImsProvisioningChangedCallback(this.mSubId, callback.getBinder());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public static class Callback {
        private final CallbackBinder mBinder = new CallbackBinder(this);

        public final IImsConfigCallback getBinder() {
            return this.mBinder;
        }

        public void onProvisioningIntChanged(int n, int n2) {
        }

        public void onProvisioningStringChanged(int n, String string2) {
        }

        public void setExecutor(Executor executor) {
            this.mBinder.setExecutor(executor);
        }

        private static class CallbackBinder
        extends IImsConfigCallback.Stub {
            private Executor mExecutor;
            private final Callback mLocalConfigurationCallback;

            private CallbackBinder(Callback callback) {
                this.mLocalConfigurationCallback = callback;
            }

            private void setExecutor(Executor executor) {
                this.mExecutor = executor;
            }

            public /* synthetic */ void lambda$onIntConfigChanged$0$ProvisioningManager$Callback$CallbackBinder(int n, int n2) {
                this.mLocalConfigurationCallback.onProvisioningIntChanged(n, n2);
            }

            public /* synthetic */ void lambda$onIntConfigChanged$1$ProvisioningManager$Callback$CallbackBinder(int n, int n2) throws Exception {
                this.mExecutor.execute(new _$$Lambda$ProvisioningManager$Callback$CallbackBinder$R_8jXQuOM7aV7dIwYBzcWwV_YpM(this, n, n2));
            }

            public /* synthetic */ void lambda$onStringConfigChanged$2$ProvisioningManager$Callback$CallbackBinder(int n, String string2) {
                this.mLocalConfigurationCallback.onProvisioningStringChanged(n, string2);
            }

            public /* synthetic */ void lambda$onStringConfigChanged$3$ProvisioningManager$Callback$CallbackBinder(int n, String string2) throws Exception {
                this.mExecutor.execute(new _$$Lambda$ProvisioningManager$Callback$CallbackBinder$rsWuitP9riQDO6nFxj5wJBdYX40(this, n, string2));
            }

            @Override
            public final void onIntConfigChanged(int n, int n2) {
                Binder.withCleanCallingIdentity(new _$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY(this, n, n2));
            }

            @Override
            public final void onStringConfigChanged(int n, String string2) {
                Binder.withCleanCallingIdentity(new _$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT_fqeBCDl6FWK1nXcIt0(this, n, string2));
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StringResultError {
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.content.pm.IPackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.ImsException;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims._$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$4YNlUy9HsD02E7Sbv2VeVtbao08;
import android.telephony.ims._$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj_ZEUt9ISc;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$IeNlpXTAPM2z2VxFA81E0v9udZw;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$J4VhgcUtd6SivHcdkzpurbTuyLc;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2_8g;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$jAP4lCkBQEdyrlgt5jaNPTlFXlY;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$oDp7ilyKfflFThUCP4Du9EYoDoQ;
import android.telephony.ims._$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$q0Uz23ATIYan5EBJYUigIVvwE3g;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.feature.MmTelFeature;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.ITelephony;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

@SystemApi
public class ImsMmTelManager {
    private static final String TAG = "ImsMmTelManager";
    public static final int WIFI_MODE_CELLULAR_PREFERRED = 1;
    public static final int WIFI_MODE_WIFI_ONLY = 0;
    public static final int WIFI_MODE_WIFI_PREFERRED = 2;
    private int mSubId;

    @VisibleForTesting
    public ImsMmTelManager(int n) {
        this.mSubId = n;
    }

    public static ImsMmTelManager createForSubscriptionId(int n) {
        if (SubscriptionManager.isValidSubscriptionId(n)) {
            return new ImsMmTelManager(n);
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

    public int getVoWiFiModeSetting() {
        try {
            int n = ImsMmTelManager.getITelephony().getVoWiFiModeSetting(this.mSubId);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public int getVoWiFiRoamingModeSetting() {
        try {
            int n = ImsMmTelManager.getITelephony().getVoWiFiRoamingModeSetting(this.mSubId);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean isAdvancedCallingSettingEnabled() {
        try {
            boolean bl = ImsMmTelManager.getITelephony().isAdvancedCallingSettingEnabled(this.mSubId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean isAvailable(int n, int n2) {
        try {
            boolean bl = ImsMmTelManager.getITelephony().isAvailable(this.mSubId, n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean isCapable(int n, int n2) {
        try {
            boolean bl = ImsMmTelManager.getITelephony().isCapable(this.mSubId, n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    boolean isTtyOverVolteEnabled() {
        try {
            boolean bl = ImsMmTelManager.getITelephony().isTtyOverVolteEnabled(this.mSubId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean isVoWiFiRoamingSettingEnabled() {
        try {
            boolean bl = ImsMmTelManager.getITelephony().isVoWiFiRoamingSettingEnabled(this.mSubId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean isVoWiFiSettingEnabled() {
        try {
            boolean bl = ImsMmTelManager.getITelephony().isVoWiFiSettingEnabled(this.mSubId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public boolean isVtSettingEnabled() {
        try {
            boolean bl = ImsMmTelManager.getITelephony().isVtSettingEnabled(this.mSubId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void registerImsRegistrationCallback(Executor executor, RegistrationCallback registrationCallback) throws ImsException {
        if (registrationCallback != null) {
            if (executor != null) {
                if (ImsMmTelManager.isImsAvailableOnDevice()) {
                    registrationCallback.setExecutor(executor);
                    try {
                        ImsMmTelManager.getITelephony().registerImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
                        return;
                    }
                    catch (RemoteException | IllegalStateException exception) {
                        throw new ImsException(exception.getMessage(), 1);
                    }
                }
                throw new ImsException("IMS not available on device.", 2);
            }
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
    }

    public void registerMmTelCapabilityCallback(Executor executor, CapabilityCallback capabilityCallback) throws ImsException {
        if (capabilityCallback != null) {
            if (executor != null) {
                if (ImsMmTelManager.isImsAvailableOnDevice()) {
                    capabilityCallback.setExecutor(executor);
                    try {
                        ImsMmTelManager.getITelephony().registerMmTelCapabilityCallback(this.mSubId, capabilityCallback.getBinder());
                        return;
                    }
                    catch (IllegalStateException illegalStateException) {
                        throw new ImsException(illegalStateException.getMessage(), 1);
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowAsRuntimeException();
                    }
                }
                throw new ImsException("IMS not available on device.", 2);
            }
            throw new IllegalArgumentException("Must include a non-null Executor.");
        }
        throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
    }

    public void setAdvancedCallingSettingEnabled(boolean bl) {
        try {
            ImsMmTelManager.getITelephony().setAdvancedCallingSettingEnabled(this.mSubId, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setRttCapabilitySetting(boolean bl) {
        try {
            ImsMmTelManager.getITelephony().setRttCapabilitySetting(this.mSubId, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setVoWiFiModeSetting(int n) {
        try {
            ImsMmTelManager.getITelephony().setVoWiFiModeSetting(this.mSubId, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setVoWiFiNonPersistent(boolean bl, int n) {
        try {
            ImsMmTelManager.getITelephony().setVoWiFiNonPersistent(this.mSubId, bl, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setVoWiFiRoamingModeSetting(int n) {
        try {
            ImsMmTelManager.getITelephony().setVoWiFiRoamingModeSetting(this.mSubId, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setVoWiFiRoamingSettingEnabled(boolean bl) {
        try {
            ImsMmTelManager.getITelephony().setVoWiFiRoamingSettingEnabled(this.mSubId, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setVoWiFiSettingEnabled(boolean bl) {
        try {
            ImsMmTelManager.getITelephony().setVoWiFiSettingEnabled(this.mSubId, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void setVtSettingEnabled(boolean bl) {
        try {
            ImsMmTelManager.getITelephony().setVtSettingEnabled(this.mSubId, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void unregisterImsRegistrationCallback(RegistrationCallback registrationCallback) {
        if (registrationCallback != null) {
            try {
                ImsMmTelManager.getITelephony().unregisterImsRegistrationCallback(this.mSubId, registrationCallback.getBinder());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowAsRuntimeException();
            }
        }
        throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
    }

    public void unregisterMmTelCapabilityCallback(CapabilityCallback capabilityCallback) {
        if (capabilityCallback != null) {
            try {
                ImsMmTelManager.getITelephony().unregisterMmTelCapabilityCallback(this.mSubId, capabilityCallback.getBinder());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowAsRuntimeException();
            }
        }
        throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
    }

    public static class CapabilityCallback {
        private final CapabilityBinder mBinder = new CapabilityBinder(this);

        public final IImsCapabilityCallback getBinder() {
            return this.mBinder;
        }

        public void onCapabilitiesStatusChanged(MmTelFeature.MmTelCapabilities mmTelCapabilities) {
        }

        public final void setExecutor(Executor executor) {
            this.mBinder.setExecutor(executor);
        }

        private static class CapabilityBinder
        extends IImsCapabilityCallback.Stub {
            private Executor mExecutor;
            private final CapabilityCallback mLocalCallback;

            CapabilityBinder(CapabilityCallback capabilityCallback) {
                this.mLocalCallback = capabilityCallback;
            }

            private void setExecutor(Executor executor) {
                this.mExecutor = executor;
            }

            public /* synthetic */ void lambda$onCapabilitiesStatusChanged$0$ImsMmTelManager$CapabilityCallback$CapabilityBinder(int n) {
                this.mLocalCallback.onCapabilitiesStatusChanged(new MmTelFeature.MmTelCapabilities(n));
            }

            public /* synthetic */ void lambda$onCapabilitiesStatusChanged$1$ImsMmTelManager$CapabilityCallback$CapabilityBinder(int n) throws Exception {
                this.mExecutor.execute(new _$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$4YNlUy9HsD02E7Sbv2VeVtbao08(this, n));
            }

            @Override
            public void onCapabilitiesStatusChanged(int n) {
                if (this.mLocalCallback == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new _$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8(this, n));
            }

            @Override
            public void onChangeCapabilityConfigurationError(int n, int n2, int n3) {
            }

            @Override
            public void onQueryCapabilityConfiguration(int n, int n2, boolean bl) {
            }
        }

    }

    public static class RegistrationCallback {
        private final RegistrationBinder mBinder = new RegistrationBinder(this);

        public final IImsRegistrationCallback getBinder() {
            return this.mBinder;
        }

        public void onRegistered(int n) {
        }

        public void onRegistering(int n) {
        }

        public void onSubscriberAssociatedUriChanged(Uri[] arruri) {
        }

        public void onTechnologyChangeFailed(int n, ImsReasonInfo imsReasonInfo) {
        }

        public void onUnregistered(ImsReasonInfo imsReasonInfo) {
        }

        public void setExecutor(Executor executor) {
            this.mBinder.setExecutor(executor);
        }

        private static class RegistrationBinder
        extends IImsRegistrationCallback.Stub {
            private static final Map<Integer, Integer> IMS_REG_TO_ACCESS_TYPE_MAP = new HashMap<Integer, Integer>(){
                {
                    Integer n = -1;
                    this.put(n, n);
                    n = 1;
                    this.put(0, n);
                    this.put(n, 2);
                }
            };
            private Executor mExecutor;
            private final RegistrationCallback mLocalCallback;

            RegistrationBinder(RegistrationCallback registrationCallback) {
                this.mLocalCallback = registrationCallback;
            }

            private static int getAccessType(int n) {
                if (!IMS_REG_TO_ACCESS_TYPE_MAP.containsKey(n)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("RegistrationBinder - invalid regType returned: ");
                    stringBuilder.append(n);
                    Log.w(ImsMmTelManager.TAG, stringBuilder.toString());
                    return -1;
                }
                return IMS_REG_TO_ACCESS_TYPE_MAP.get(n);
            }

            private void setExecutor(Executor executor) {
                this.mExecutor = executor;
            }

            public /* synthetic */ void lambda$onDeregistered$4$ImsMmTelManager$RegistrationCallback$RegistrationBinder(ImsReasonInfo imsReasonInfo) {
                this.mLocalCallback.onUnregistered(imsReasonInfo);
            }

            public /* synthetic */ void lambda$onDeregistered$5$ImsMmTelManager$RegistrationCallback$RegistrationBinder(ImsReasonInfo imsReasonInfo) throws Exception {
                this.mExecutor.execute(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$q0Uz23ATIYan5EBJYUigIVvwE3g(this, imsReasonInfo));
            }

            public /* synthetic */ void lambda$onRegistered$0$ImsMmTelManager$RegistrationCallback$RegistrationBinder(int n) {
                this.mLocalCallback.onRegistered(RegistrationBinder.getAccessType(n));
            }

            public /* synthetic */ void lambda$onRegistered$1$ImsMmTelManager$RegistrationCallback$RegistrationBinder(int n) throws Exception {
                this.mExecutor.execute(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$oDp7ilyKfflFThUCP4Du9EYoDoQ(this, n));
            }

            public /* synthetic */ void lambda$onRegistering$2$ImsMmTelManager$RegistrationCallback$RegistrationBinder(int n) {
                this.mLocalCallback.onRegistering(RegistrationBinder.getAccessType(n));
            }

            public /* synthetic */ void lambda$onRegistering$3$ImsMmTelManager$RegistrationCallback$RegistrationBinder(int n) throws Exception {
                this.mExecutor.execute(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$J4VhgcUtd6SivHcdkzpurbTuyLc(this, n));
            }

            public /* synthetic */ void lambda$onSubscriberAssociatedUriChanged$8$ImsMmTelManager$RegistrationCallback$RegistrationBinder(Uri[] arruri) {
                this.mLocalCallback.onSubscriberAssociatedUriChanged(arruri);
            }

            public /* synthetic */ void lambda$onSubscriberAssociatedUriChanged$9$ImsMmTelManager$RegistrationCallback$RegistrationBinder(Uri[] arruri) throws Exception {
                this.mExecutor.execute(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$jAP4lCkBQEdyrlgt5jaNPTlFXlY(this, arruri));
            }

            public /* synthetic */ void lambda$onTechnologyChangeFailed$6$ImsMmTelManager$RegistrationCallback$RegistrationBinder(int n, ImsReasonInfo imsReasonInfo) {
                this.mLocalCallback.onTechnologyChangeFailed(RegistrationBinder.getAccessType(n), imsReasonInfo);
            }

            public /* synthetic */ void lambda$onTechnologyChangeFailed$7$ImsMmTelManager$RegistrationCallback$RegistrationBinder(int n, ImsReasonInfo imsReasonInfo) throws Exception {
                this.mExecutor.execute(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$IeNlpXTAPM2z2VxFA81E0v9udZw(this, n, imsReasonInfo));
            }

            @Override
            public void onDeregistered(ImsReasonInfo imsReasonInfo) {
                if (this.mLocalCallback == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU(this, imsReasonInfo));
            }

            @Override
            public void onRegistered(int n) {
                if (this.mLocalCallback == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj_ZEUt9ISc(this, n));
            }

            @Override
            public void onRegistering(int n) {
                if (this.mLocalCallback == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2_8g(this, n));
            }

            @Override
            public void onSubscriberAssociatedUriChanged(Uri[] arruri) {
                if (this.mLocalCallback == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM(this, arruri));
            }

            @Override
            public void onTechnologyChangeFailed(int n, ImsReasonInfo imsReasonInfo) {
                if (this.mLocalCallback == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new _$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY(this, n, imsReasonInfo));
            }

        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WiFiCallingMode {
    }

}


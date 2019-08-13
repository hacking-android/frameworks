/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsMmTelListener;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.feature.CapabilityChangeRequest;
import android.telephony.ims.feature.ImsFeature;
import android.telephony.ims.stub.ImsCallSessionImplBase;
import android.telephony.ims.stub.ImsEcbmImplBase;
import android.telephony.ims.stub.ImsMultiEndpointImplBase;
import android.telephony.ims.stub.ImsSmsImplBase;
import android.telephony.ims.stub.ImsUtImplBase;
import android.util.Log;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsUt;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public class MmTelFeature
extends ImsFeature {
    private static final String LOG_TAG = "MmTelFeature";
    public static final int PROCESS_CALL_CSFB = 1;
    public static final int PROCESS_CALL_IMS = 0;
    private final IImsMmTelFeature mImsMMTelBinder = new IImsMmTelFeature.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void acknowledgeSms(int n, int n2, int n3) {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                MmTelFeature.this.acknowledgeSms(n, n2, n3);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void acknowledgeSmsReport(int n, int n2, int n3) {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                MmTelFeature.this.acknowledgeSmsReport(n, n2, n3);
                return;
            }
        }

        @Override
        public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) {
            MmTelFeature.this.addCapabilityCallback(iImsCapabilityCallback);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                MmTelFeature.this.requestChangeEnabledCapabilities(capabilityChangeRequest, iImsCapabilityCallback);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ImsCallProfile createCallProfile(int n, int n2) throws RemoteException {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                try {
                    try {
                        return MmTelFeature.this.createCallProfile(n, n2);
                    }
                    catch (Exception exception) {
                        RemoteException remoteException = new RemoteException(exception.getMessage());
                        throw remoteException;
                    }
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsCallSession createCallSession(ImsCallProfile object) throws RemoteException {
            Object object2 = MmTelFeature.this.mLock;
            synchronized (object2) {
                return MmTelFeature.this.createCallSessionInterface((ImsCallProfile)object);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsEcbm getEcbmInterface() throws RemoteException {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                return MmTelFeature.this.getEcbmInterface();
            }
        }

        @Override
        public int getFeatureState() throws RemoteException {
            try {
                int n = MmTelFeature.this.getFeatureState();
                return n;
            }
            catch (Exception exception) {
                throw new RemoteException(exception.getMessage());
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                return MmTelFeature.this.getMultiEndpointInterface();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public String getSmsFormat() {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                return MmTelFeature.this.getSmsFormat();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public IImsUt getUtInterface() throws RemoteException {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                return MmTelFeature.this.getUtInterface();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onSmsReady() {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                MmTelFeature.this.onSmsReady();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void queryCapabilityConfiguration(int n, int n2, IImsCapabilityCallback iImsCapabilityCallback) {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                MmTelFeature.this.queryCapabilityConfigurationInternal(n, n2, iImsCapabilityCallback);
                return;
            }
        }

        @Override
        public int queryCapabilityStatus() {
            return ((MmTelCapabilities)MmTelFeature.this.queryCapabilityStatus()).mCapabilities;
        }

        @Override
        public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) {
            MmTelFeature.this.removeCapabilityCallback(iImsCapabilityCallback);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void sendSms(int n, int n2, String string2, String string3, boolean bl, byte[] arrby) {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                MmTelFeature.this.sendSms(n, n2, string2, string3, bl, arrby);
                return;
            }
        }

        @Override
        public void setListener(IImsMmTelListener iImsMmTelListener) {
            MmTelFeature.this.setListener(iImsMmTelListener);
        }

        @Override
        public void setSmsListener(IImsSmsListener iImsSmsListener) {
            MmTelFeature.this.setSmsListener(iImsSmsListener);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setUiTtyMode(int n, Message object) throws RemoteException {
            Object object2 = MmTelFeature.this.mLock;
            synchronized (object2) {
                try {
                    try {
                        MmTelFeature.this.setUiTtyMode(n, (Message)object);
                        return;
                    }
                    catch (Exception exception) {
                        object = new RemoteException(exception.getMessage());
                        throw object;
                    }
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int shouldProcessCall(String[] arrstring) {
            Object object = MmTelFeature.this.mLock;
            synchronized (object) {
                return MmTelFeature.this.shouldProcessCall(arrstring);
            }
        }
    };
    private IImsMmTelListener mListener;

    private void acknowledgeSms(int n, int n2, int n3) {
        this.getSmsImplementation().acknowledgeSms(n, n2, n3);
    }

    private void acknowledgeSmsReport(int n, int n2, int n3) {
        this.getSmsImplementation().acknowledgeSmsReport(n, n2, n3);
    }

    private String getSmsFormat() {
        return this.getSmsImplementation().getSmsFormat();
    }

    private void onSmsReady() {
        this.getSmsImplementation().onReady();
    }

    private void queryCapabilityConfigurationInternal(int n, int n2, IImsCapabilityCallback iImsCapabilityCallback) {
        block2 : {
            boolean bl = this.queryCapabilityConfiguration(n, n2);
            if (iImsCapabilityCallback == null) break block2;
            try {
                iImsCapabilityCallback.onQueryCapabilityConfiguration(n, n2, bl);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "queryCapabilityConfigurationInternal called on dead binder!");
            }
        }
    }

    private void sendSms(int n, int n2, String string2, String string3, boolean bl, byte[] arrby) {
        this.getSmsImplementation().sendSms(n, n2, string2, string3, bl, arrby);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setListener(IImsMmTelListener iImsMmTelListener) {
        Object object = this.mLock;
        synchronized (object) {
            this.mListener = iImsMmTelListener;
            if (this.mListener != null) {
                this.onFeatureReady();
            }
            return;
        }
    }

    private void setSmsListener(IImsSmsListener iImsSmsListener) {
        this.getSmsImplementation().registerSmsListener(iImsSmsListener);
    }

    @Override
    public void changeEnabledCapabilities(CapabilityChangeRequest capabilityChangeRequest, ImsFeature.CapabilityCallbackProxy capabilityCallbackProxy) {
    }

    public ImsCallProfile createCallProfile(int n, int n2) {
        return null;
    }

    public ImsCallSessionImplBase createCallSession(ImsCallProfile imsCallProfile) {
        return null;
    }

    public IImsCallSession createCallSessionInterface(ImsCallProfile object) throws RemoteException {
        object = (object = this.createCallSession((ImsCallProfile)object)) != null ? ((ImsCallSessionImplBase)object).getServiceImpl() : null;
        return object;
    }

    @Override
    public final IImsMmTelFeature getBinder() {
        return this.mImsMMTelBinder;
    }

    public ImsEcbmImplBase getEcbm() {
        return new ImsEcbmImplBase();
    }

    protected IImsEcbm getEcbmInterface() throws RemoteException {
        Object object = this.getEcbm();
        object = object != null ? ((ImsEcbmImplBase)object).getImsEcbm() : null;
        return object;
    }

    public ImsMultiEndpointImplBase getMultiEndpoint() {
        return new ImsMultiEndpointImplBase();
    }

    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
        Object object = this.getMultiEndpoint();
        object = object != null ? ((ImsMultiEndpointImplBase)object).getIImsMultiEndpoint() : null;
        return object;
    }

    public ImsSmsImplBase getSmsImplementation() {
        return new ImsSmsImplBase();
    }

    public ImsUtImplBase getUt() {
        return new ImsUtImplBase();
    }

    protected IImsUt getUtInterface() throws RemoteException {
        Object object = this.getUt();
        object = object != null ? ((ImsUtImplBase)object).getInterface() : null;
        return object;
    }

    public final void notifyCapabilitiesStatusChanged(MmTelCapabilities mmTelCapabilities) {
        if (mmTelCapabilities != null) {
            super.notifyCapabilitiesStatusChanged(mmTelCapabilities);
            return;
        }
        throw new IllegalArgumentException("MmTelCapabilities must be non-null!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void notifyIncomingCall(ImsCallSessionImplBase object, Bundle object2) {
        if (object != null && object2 != null) {
            Object object3 = this.mLock;
            synchronized (object3) {
                IImsMmTelListener iImsMmTelListener = this.mListener;
                if (iImsMmTelListener == null) {
                    object = new IllegalStateException("Session is not available.");
                    throw object;
                }
                try {
                    this.mListener.onIncomingCall(((ImsCallSessionImplBase)object).getServiceImpl(), (Bundle)object2);
                    return;
                }
                catch (RemoteException remoteException) {
                    object2 = new RuntimeException(remoteException);
                    throw object2;
                }
            }
        }
        throw new IllegalArgumentException("ImsCallSessionImplBase and Bundle can not be null.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void notifyIncomingCallSession(IImsCallSession object, Bundle bundle) {
        Object object2 = this.mLock;
        synchronized (object2) {
            IImsMmTelListener iImsMmTelListener = this.mListener;
            if (iImsMmTelListener == null) {
                object = new IllegalStateException("Session is not available.");
                throw object;
            }
            try {
                this.mListener.onIncomingCall((IImsCallSession)object, bundle);
                return;
            }
            catch (RemoteException remoteException) {
                object = new RuntimeException(remoteException);
                throw object;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void notifyRejectedCall(ImsCallProfile object, ImsReasonInfo imsReasonInfo) {
        if (object != null && imsReasonInfo != null) {
            Object object2 = this.mLock;
            synchronized (object2) {
                IImsMmTelListener iImsMmTelListener = this.mListener;
                if (iImsMmTelListener == null) {
                    object = new IllegalStateException("Session is not available.");
                    throw object;
                }
                try {
                    this.mListener.onRejectedCall((ImsCallProfile)object, imsReasonInfo);
                    return;
                }
                catch (RemoteException remoteException) {
                    object = new RuntimeException(remoteException);
                    throw object;
                }
            }
        }
        throw new IllegalArgumentException("ImsCallProfile and ImsReasonInfo must not be null.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void notifyVoiceMessageCountUpdate(int n) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2 = this.mListener;
            if (object2 == null) {
                object2 = new IllegalStateException("Session is not available.");
                throw object2;
            }
            try {
                this.mListener.onVoiceMessageCountUpdate(n);
                return;
            }
            catch (RemoteException remoteException) {
                object2 = new RuntimeException(remoteException);
                throw object2;
            }
        }
    }

    @Override
    public void onFeatureReady() {
    }

    @Override
    public void onFeatureRemoved() {
    }

    public boolean queryCapabilityConfiguration(int n, int n2) {
        return false;
    }

    @Override
    public final MmTelCapabilities queryCapabilityStatus() {
        return new MmTelCapabilities(super.queryCapabilityStatus());
    }

    public void setUiTtyMode(int n, Message message) {
    }

    public int shouldProcessCall(String[] arrstring) {
        return 0;
    }

    public static class Listener
    extends IImsMmTelListener.Stub {
        @Override
        public void onIncomingCall(IImsCallSession iImsCallSession, Bundle bundle) {
        }

        @Override
        public void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) {
        }

        @Override
        public void onVoiceMessageCountUpdate(int n) {
        }
    }

    public static class MmTelCapabilities
    extends ImsFeature.Capabilities {
        public static final int CAPABILITY_TYPE_SMS = 8;
        public static final int CAPABILITY_TYPE_UT = 4;
        public static final int CAPABILITY_TYPE_VIDEO = 2;
        public static final int CAPABILITY_TYPE_VOICE = 1;

        public MmTelCapabilities() {
        }

        public MmTelCapabilities(int n) {
            this.mCapabilities = n;
        }

        @Deprecated
        public MmTelCapabilities(ImsFeature.Capabilities capabilities) {
            this.mCapabilities = capabilities.mCapabilities;
        }

        @Override
        public final void addCapabilities(int n) {
            super.addCapabilities(n);
        }

        @Override
        public final boolean isCapable(int n) {
            return super.isCapable(n);
        }

        @Override
        public final void removeCapabilities(int n) {
            super.removeCapabilities(n);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("MmTel Capabilities - [");
            stringBuilder.append("Voice: ");
            stringBuilder.append(this.isCapable(1));
            stringBuilder.append(" Video: ");
            stringBuilder.append(this.isCapable(2));
            stringBuilder.append(" UT: ");
            stringBuilder.append(this.isCapable(4));
            stringBuilder.append(" SMS: ");
            stringBuilder.append(this.isCapable(8));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface MmTelCapability {
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProcessCallResult {
    }

}


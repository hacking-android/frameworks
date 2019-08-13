/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsServiceController;
import android.telephony.ims.aidl.IImsServiceControllerListener;
import android.telephony.ims.feature.ImsFeature;
import android.telephony.ims.feature.MmTelFeature;
import android.telephony.ims.feature.RcsFeature;
import android.telephony.ims.stub.ImsConfigImplBase;
import android.telephony.ims.stub.ImsFeatureConfiguration;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import android.util.Log;
import android.util.SparseArray;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.internal.annotations.VisibleForTesting;

@SystemApi
public class ImsService
extends Service {
    private static final String LOG_TAG = "ImsService";
    public static final String SERVICE_INTERFACE = "android.telephony.ims.ImsService";
    private final SparseArray<SparseArray<ImsFeature>> mFeaturesBySlot = new SparseArray();
    protected final IBinder mImsServiceController = new IImsServiceController.Stub(){

        @Override
        public IImsMmTelFeature createMmTelFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            return ImsService.this.createMmTelFeatureInternal(n, iImsFeatureStatusCallback);
        }

        @Override
        public IImsRcsFeature createRcsFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            return ImsService.this.createRcsFeatureInternal(n, iImsFeatureStatusCallback);
        }

        @Override
        public void disableIms(int n) {
            ImsService.this.disableIms(n);
        }

        @Override
        public void enableIms(int n) {
            ImsService.this.enableIms(n);
        }

        @Override
        public IImsConfig getConfig(int n) {
            Object object = ImsService.this.getConfig(n);
            object = object != null ? ((ImsConfigImplBase)object).getIImsConfig() : null;
            return object;
        }

        @Override
        public IImsRegistration getRegistration(int n) {
            Object object = ImsService.this.getRegistration(n);
            object = object != null ? ((ImsRegistrationImplBase)object).getBinder() : null;
            return object;
        }

        @Override
        public void notifyImsServiceReadyForFeatureCreation() {
            ImsService.this.readyForFeatureCreation();
        }

        @Override
        public ImsFeatureConfiguration querySupportedImsFeatures() {
            return ImsService.this.querySupportedImsFeatures();
        }

        @Override
        public void removeImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            ImsService.this.removeImsFeature(n, n2, iImsFeatureStatusCallback);
        }

        @Override
        public void setListener(IImsServiceControllerListener iImsServiceControllerListener) {
            ImsService.this.mListener = iImsServiceControllerListener;
        }
    };
    private IImsServiceControllerListener mListener;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addImsFeature(int n, int n2, ImsFeature imsFeature) {
        SparseArray<SparseArray<ImsFeature>> sparseArray = this.mFeaturesBySlot;
        synchronized (sparseArray) {
            SparseArray<ImsFeature> sparseArray2;
            SparseArray<ImsFeature> sparseArray3 = sparseArray2 = this.mFeaturesBySlot.get(n);
            if (sparseArray2 == null) {
                sparseArray3 = new SparseArray<ImsFeature>();
                this.mFeaturesBySlot.put(n, sparseArray3);
            }
            sparseArray3.put(n2, imsFeature);
            return;
        }
    }

    private IImsMmTelFeature createMmTelFeatureInternal(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        MmTelFeature mmTelFeature = this.createMmTelFeature(n);
        if (mmTelFeature != null) {
            this.setupFeature(mmTelFeature, n, 1, iImsFeatureStatusCallback);
            return mmTelFeature.getBinder();
        }
        Log.e(LOG_TAG, "createMmTelFeatureInternal: null feature returned.");
        return null;
    }

    private IImsRcsFeature createRcsFeatureInternal(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        RcsFeature rcsFeature = this.createRcsFeature(n);
        if (rcsFeature != null) {
            this.setupFeature(rcsFeature, n, 2, iImsFeatureStatusCallback);
            return rcsFeature.getBinder();
        }
        Log.e(LOG_TAG, "createRcsFeatureInternal: null feature returned.");
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeImsFeature(int n, int n2, IImsFeatureStatusCallback object) {
        SparseArray<SparseArray<ImsFeature>> sparseArray = this.mFeaturesBySlot;
        synchronized (sparseArray) {
            SparseArray<ImsFeature> sparseArray2 = this.mFeaturesBySlot.get(n);
            if (sparseArray2 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Can not remove ImsFeature. No ImsFeatures exist on slot ");
                ((StringBuilder)object).append(n);
                Log.w(LOG_TAG, ((StringBuilder)object).toString());
                return;
            }
            ImsFeature imsFeature = sparseArray2.get(n2);
            if (imsFeature == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Can not remove ImsFeature. No feature with type ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" exists on slot ");
                ((StringBuilder)object).append(n);
                Log.w(LOG_TAG, ((StringBuilder)object).toString());
                return;
            }
            imsFeature.removeImsFeatureStatusCallback((IImsFeatureStatusCallback)object);
            imsFeature.onFeatureRemoved();
            sparseArray2.remove(n2);
            return;
        }
    }

    private void setupFeature(ImsFeature imsFeature, int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        imsFeature.initialize(this, n);
        imsFeature.addImsFeatureStatusCallback(iImsFeatureStatusCallback);
        this.addImsFeature(n, n2, imsFeature);
    }

    public MmTelFeature createMmTelFeature(int n) {
        return null;
    }

    public RcsFeature createRcsFeature(int n) {
        return null;
    }

    public void disableIms(int n) {
    }

    public void enableIms(int n) {
    }

    public ImsConfigImplBase getConfig(int n) {
        return new ImsConfigImplBase();
    }

    @VisibleForTesting
    public SparseArray<ImsFeature> getFeatures(int n) {
        return this.mFeaturesBySlot.get(n);
    }

    public ImsRegistrationImplBase getRegistration(int n) {
        return new ImsRegistrationImplBase();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            Log.i(LOG_TAG, "ImsService Bound.");
            return this.mImsServiceController;
        }
        return null;
    }

    public final void onUpdateSupportedImsFeatures(ImsFeatureConfiguration imsFeatureConfiguration) throws RemoteException {
        IImsServiceControllerListener iImsServiceControllerListener = this.mListener;
        if (iImsServiceControllerListener != null) {
            iImsServiceControllerListener.onUpdateSupportedImsFeatures(imsFeatureConfiguration);
            return;
        }
        throw new IllegalStateException("Framework is not ready");
    }

    public ImsFeatureConfiguration querySupportedImsFeatures() {
        return new ImsFeatureConfiguration();
    }

    public void readyForFeatureCreation() {
    }

    public static class Listener
    extends IImsServiceControllerListener.Stub {
        @Override
        public void onUpdateSupportedImsFeatures(ImsFeatureConfiguration imsFeatureConfiguration) {
        }
    }

}


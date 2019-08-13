/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.compat;

import android.annotation.UnsupportedAppUsage;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.telephony.ims.compat.feature.ImsFeature;
import android.telephony.ims.compat.feature.MMTelFeature;
import android.telephony.ims.compat.feature.RcsFeature;
import android.util.Log;
import android.util.SparseArray;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.ims.internal.IImsMMTelFeature;
import com.android.ims.internal.IImsRcsFeature;
import com.android.ims.internal.IImsServiceController;
import com.android.internal.annotations.VisibleForTesting;

public class ImsService
extends Service {
    private static final String LOG_TAG = "ImsService(Compat)";
    public static final String SERVICE_INTERFACE = "android.telephony.ims.compat.ImsService";
    private final SparseArray<SparseArray<ImsFeature>> mFeaturesBySlot = new SparseArray();
    @UnsupportedAppUsage
    protected final IBinder mImsServiceController = new IImsServiceController.Stub(){

        @Override
        public IImsMMTelFeature createEmergencyMMTelFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            return ImsService.this.createEmergencyMMTelFeatureInternal(n, iImsFeatureStatusCallback);
        }

        @Override
        public IImsMMTelFeature createMMTelFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            return ImsService.this.createMMTelFeatureInternal(n, iImsFeatureStatusCallback);
        }

        @Override
        public IImsRcsFeature createRcsFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            return ImsService.this.createRcsFeatureInternal(n, iImsFeatureStatusCallback);
        }

        @Override
        public void removeImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
            ImsService.this.removeImsFeature(n, n2, iImsFeatureStatusCallback);
        }
    };

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

    private IImsMMTelFeature createEmergencyMMTelFeatureInternal(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        MMTelFeature mMTelFeature = this.onCreateEmergencyMMTelImsFeature(n);
        if (mMTelFeature != null) {
            this.setupFeature(mMTelFeature, n, 0, iImsFeatureStatusCallback);
            return mMTelFeature.getBinder();
        }
        return null;
    }

    private IImsMMTelFeature createMMTelFeatureInternal(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        MMTelFeature mMTelFeature = this.onCreateMMTelImsFeature(n);
        if (mMTelFeature != null) {
            this.setupFeature(mMTelFeature, n, 1, iImsFeatureStatusCallback);
            return mMTelFeature.getBinder();
        }
        return null;
    }

    private IImsRcsFeature createRcsFeatureInternal(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        RcsFeature rcsFeature = this.onCreateRcsFeature(n);
        if (rcsFeature != null) {
            this.setupFeature(rcsFeature, n, 2, iImsFeatureStatusCallback);
            return rcsFeature.getBinder();
        }
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
        imsFeature.setContext(this);
        imsFeature.setSlotId(n);
        imsFeature.addImsFeatureStatusCallback(iImsFeatureStatusCallback);
        this.addImsFeature(n, n2, imsFeature);
        imsFeature.onFeatureReady();
    }

    @VisibleForTesting
    public SparseArray<ImsFeature> getFeatures(int n) {
        return this.mFeaturesBySlot.get(n);
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            Log.i(LOG_TAG, "ImsService(Compat) Bound.");
            return this.mImsServiceController;
        }
        return null;
    }

    public MMTelFeature onCreateEmergencyMMTelImsFeature(int n) {
        return null;
    }

    public MMTelFeature onCreateMMTelImsFeature(int n) {
        return null;
    }

    public RcsFeature onCreateRcsFeature(int n) {
        return null;
    }

}


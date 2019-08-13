/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.content.Context;
import android.hardware.IConsumerIrService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

public final class ConsumerIrManager {
    private static final String TAG = "ConsumerIr";
    private final String mPackageName;
    private final IConsumerIrService mService;

    public ConsumerIrManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mPackageName = context.getPackageName();
        this.mService = IConsumerIrService.Stub.asInterface(ServiceManager.getServiceOrThrow("consumer_ir"));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public CarrierFrequencyRange[] getCarrierFrequencies() {
        CarrierFrequencyRange[] arrcarrierFrequencyRange;
        int n;
        int[] arrn;
        block6 : {
            arrn = this.mService;
            if (arrn == null) {
                Log.w(TAG, "no consumer ir service.");
                return null;
            }
            arrn = arrn.getCarrierFrequencies();
            if (arrn.length % 2 == 0) break block6;
            Log.w(TAG, "consumer ir service returned an uneven number of frequencies.");
            return null;
        }
        try {
            arrcarrierFrequencyRange = new CarrierFrequencyRange[arrn.length / 2];
            n = 0;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        do {
            if (n >= arrn.length) return arrcarrierFrequencyRange;
            arrcarrierFrequencyRange[n / 2] = new CarrierFrequencyRange(arrn[n], arrn[n + 1]);
            n += 2;
            continue;
            break;
        } while (true);
    }

    public boolean hasIrEmitter() {
        IConsumerIrService iConsumerIrService = this.mService;
        if (iConsumerIrService == null) {
            Log.w(TAG, "no consumer ir service.");
            return false;
        }
        try {
            boolean bl = iConsumerIrService.hasIrEmitter();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void transmit(int n, int[] arrn) {
        IConsumerIrService iConsumerIrService = this.mService;
        if (iConsumerIrService == null) {
            Log.w(TAG, "failed to transmit; no consumer ir service.");
            return;
        }
        try {
            iConsumerIrService.transmit(this.mPackageName, n, arrn);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public final class CarrierFrequencyRange {
        private final int mMaxFrequency;
        private final int mMinFrequency;

        public CarrierFrequencyRange(int n, int n2) {
            this.mMinFrequency = n;
            this.mMaxFrequency = n2;
        }

        public int getMaxFrequency() {
            return this.mMaxFrequency;
        }

        public int getMinFrequency() {
            return this.mMinFrequency;
        }
    }

}


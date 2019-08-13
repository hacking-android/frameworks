/*
 * Decompiled with CFR 0.145.
 */
package android.service.carrier;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.service.carrier.CarrierIdentifier;
import android.service.carrier.ICarrierService;
import android.util.Log;
import com.android.internal.telephony.ITelephonyRegistry;

public abstract class CarrierService
extends Service {
    public static final String CARRIER_SERVICE_INTERFACE = "android.service.carrier.CarrierService";
    private static final String LOG_TAG = "CarrierService";
    private static ITelephonyRegistry sRegistry;
    private final ICarrierService.Stub mStubWrapper = new ICarrierServiceWrapper();

    public CarrierService() {
        if (sRegistry == null) {
            sRegistry = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
        }
    }

    public final void notifyCarrierNetworkChange(boolean bl) {
        try {
            if (sRegistry != null) {
                sRegistry.notifyCarrierNetworkChange(bl);
            }
        }
        catch (RemoteException | NullPointerException exception) {
            // empty catch block
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mStubWrapper;
    }

    public abstract PersistableBundle onLoadConfig(CarrierIdentifier var1);

    public class ICarrierServiceWrapper
    extends ICarrierService.Stub {
        public static final String KEY_CONFIG_BUNDLE = "config_bundle";
        public static final int RESULT_ERROR = 1;
        public static final int RESULT_OK = 0;

        @Override
        public void getCarrierConfig(CarrierIdentifier object, ResultReceiver resultReceiver) {
            try {
                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_CONFIG_BUNDLE, CarrierService.this.onLoadConfig((CarrierIdentifier)object));
                resultReceiver.send(0, bundle);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error in onLoadConfig: ");
                ((StringBuilder)object).append(exception.getMessage());
                Log.e(CarrierService.LOG_TAG, ((StringBuilder)object).toString(), exception);
                resultReceiver.send(1, null);
            }
        }
    }

}


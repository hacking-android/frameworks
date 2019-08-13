/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.content.Context;
import android.location.BatchedLocationCallback;
import android.location.IBatchedLocationCallback;
import android.location.ILocationManager;
import android.location.LocalListenerHelper;
import android.location.Location;
import android.os.RemoteException;
import java.util.List;

class BatchedLocationCallbackTransport
extends LocalListenerHelper<BatchedLocationCallback> {
    private final IBatchedLocationCallback mCallbackTransport = new CallbackTransport();
    private final ILocationManager mLocationManager;

    public BatchedLocationCallbackTransport(Context context, ILocationManager iLocationManager) {
        super(context, "BatchedLocationCallbackTransport");
        this.mLocationManager = iLocationManager;
    }

    @Override
    protected boolean registerWithServer() throws RemoteException {
        return this.mLocationManager.addGnssBatchingCallback(this.mCallbackTransport, this.getContext().getPackageName());
    }

    @Override
    protected void unregisterFromServer() throws RemoteException {
        this.mLocationManager.removeGnssBatchingCallback();
    }

    private class CallbackTransport
    extends IBatchedLocationCallback.Stub {
        private CallbackTransport() {
        }

        @Override
        public void onLocationBatch(final List<Location> object) {
            object = new LocalListenerHelper.ListenerOperation<BatchedLocationCallback>(){

                @Override
                public void execute(BatchedLocationCallback batchedLocationCallback) throws RemoteException {
                    batchedLocationCallback.onLocationBatch(object);
                }
            };
            BatchedLocationCallbackTransport.this.foreach(object);
        }

    }

}


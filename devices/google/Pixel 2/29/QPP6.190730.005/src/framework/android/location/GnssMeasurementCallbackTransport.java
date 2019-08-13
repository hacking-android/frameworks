/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.content.Context;
import android.location.GnssMeasurementCorrections;
import android.location.GnssMeasurementsEvent;
import android.location.IGnssMeasurementsListener;
import android.location.ILocationManager;
import android.location.LocalListenerHelper;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;

class GnssMeasurementCallbackTransport
extends LocalListenerHelper<GnssMeasurementsEvent.Callback> {
    private static final String TAG = "GnssMeasCbTransport";
    private final IGnssMeasurementsListener mListenerTransport = new ListenerTransport();
    private final ILocationManager mLocationManager;

    public GnssMeasurementCallbackTransport(Context context, ILocationManager iLocationManager) {
        super(context, TAG);
        this.mLocationManager = iLocationManager;
    }

    protected long getGnssCapabilities() throws RemoteException {
        return this.mLocationManager.getGnssCapabilities(this.getContext().getPackageName());
    }

    protected void injectGnssMeasurementCorrections(GnssMeasurementCorrections gnssMeasurementCorrections) throws RemoteException {
        Preconditions.checkNotNull(gnssMeasurementCorrections);
        this.mLocationManager.injectGnssMeasurementCorrections(gnssMeasurementCorrections, this.getContext().getPackageName());
    }

    @Override
    protected boolean registerWithServer() throws RemoteException {
        return this.mLocationManager.addGnssMeasurementsListener(this.mListenerTransport, this.getContext().getPackageName());
    }

    @Override
    protected void unregisterFromServer() throws RemoteException {
        this.mLocationManager.removeGnssMeasurementsListener(this.mListenerTransport);
    }

    private class ListenerTransport
    extends IGnssMeasurementsListener.Stub {
        private ListenerTransport() {
        }

        @Override
        public void onGnssMeasurementsReceived(GnssMeasurementsEvent object) {
            object = new LocalListenerHelper.ListenerOperation<GnssMeasurementsEvent.Callback>((GnssMeasurementsEvent)object){
                final /* synthetic */ GnssMeasurementsEvent val$event;
                {
                    this.val$event = gnssMeasurementsEvent;
                }

                @Override
                public void execute(GnssMeasurementsEvent.Callback callback) throws RemoteException {
                    callback.onGnssMeasurementsReceived(this.val$event);
                }
            };
            GnssMeasurementCallbackTransport.this.foreach(object);
        }

        @Override
        public void onStatusChanged(final int n) {
            LocalListenerHelper.ListenerOperation<GnssMeasurementsEvent.Callback> listenerOperation = new LocalListenerHelper.ListenerOperation<GnssMeasurementsEvent.Callback>(){

                @Override
                public void execute(GnssMeasurementsEvent.Callback callback) throws RemoteException {
                    callback.onStatusChanged(n);
                }
            };
            GnssMeasurementCallbackTransport.this.foreach(listenerOperation);
        }

    }

}


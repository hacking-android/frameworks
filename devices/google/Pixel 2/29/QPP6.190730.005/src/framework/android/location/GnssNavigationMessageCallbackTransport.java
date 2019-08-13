/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.content.Context;
import android.location.GnssNavigationMessage;
import android.location.IGnssNavigationMessageListener;
import android.location.ILocationManager;
import android.location.LocalListenerHelper;
import android.os.RemoteException;

class GnssNavigationMessageCallbackTransport
extends LocalListenerHelper<GnssNavigationMessage.Callback> {
    private final IGnssNavigationMessageListener mListenerTransport = new ListenerTransport();
    private final ILocationManager mLocationManager;

    public GnssNavigationMessageCallbackTransport(Context context, ILocationManager iLocationManager) {
        super(context, "GnssNavigationMessageCallbackTransport");
        this.mLocationManager = iLocationManager;
    }

    @Override
    protected boolean registerWithServer() throws RemoteException {
        return this.mLocationManager.addGnssNavigationMessageListener(this.mListenerTransport, this.getContext().getPackageName());
    }

    @Override
    protected void unregisterFromServer() throws RemoteException {
        this.mLocationManager.removeGnssNavigationMessageListener(this.mListenerTransport);
    }

    private class ListenerTransport
    extends IGnssNavigationMessageListener.Stub {
        private ListenerTransport() {
        }

        @Override
        public void onGnssNavigationMessageReceived(GnssNavigationMessage object) {
            object = new LocalListenerHelper.ListenerOperation<GnssNavigationMessage.Callback>((GnssNavigationMessage)object){
                final /* synthetic */ GnssNavigationMessage val$event;
                {
                    this.val$event = gnssNavigationMessage;
                }

                @Override
                public void execute(GnssNavigationMessage.Callback callback) throws RemoteException {
                    callback.onGnssNavigationMessageReceived(this.val$event);
                }
            };
            GnssNavigationMessageCallbackTransport.this.foreach(object);
        }

        @Override
        public void onStatusChanged(final int n) {
            LocalListenerHelper.ListenerOperation<GnssNavigationMessage.Callback> listenerOperation = new LocalListenerHelper.ListenerOperation<GnssNavigationMessage.Callback>(){

                @Override
                public void execute(GnssNavigationMessage.Callback callback) throws RemoteException {
                    callback.onStatusChanged(n);
                }
            };
            GnssNavigationMessageCallbackTransport.this.foreach(listenerOperation);
        }

    }

}


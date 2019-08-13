/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.cardemulation;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public abstract class HostNfcFService
extends Service {
    public static final int DEACTIVATION_LINK_LOSS = 0;
    public static final String KEY_DATA = "data";
    public static final String KEY_MESSENGER = "messenger";
    public static final int MSG_COMMAND_PACKET = 0;
    public static final int MSG_DEACTIVATED = 2;
    public static final int MSG_RESPONSE_PACKET = 1;
    public static final String SERVICE_INTERFACE = "android.nfc.cardemulation.action.HOST_NFCF_SERVICE";
    public static final String SERVICE_META_DATA = "android.nfc.cardemulation.host_nfcf_service";
    static final String TAG = "NfcFService";
    final Messenger mMessenger = new Messenger(new MsgHandler());
    Messenger mNfcService = null;

    @Override
    public final IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }

    public abstract void onDeactivated(int var1);

    public abstract byte[] processNfcFPacket(byte[] var1, Bundle var2);

    public final void sendResponsePacket(byte[] arrby) {
        Message message = Message.obtain(null, 1);
        Bundle bundle = new Bundle();
        bundle.putByteArray("data", arrby);
        message.setData(bundle);
        try {
            this.mMessenger.send(message);
        }
        catch (RemoteException remoteException) {
            Log.e("TAG", "Local messenger has died.");
        }
    }

    final class MsgHandler
    extends Handler {
        MsgHandler() {
        }

        @Override
        public void handleMessage(Message arrby) {
            int n = arrby.what;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        super.handleMessage((Message)arrby);
                    } else {
                        HostNfcFService hostNfcFService = HostNfcFService.this;
                        hostNfcFService.mNfcService = null;
                        hostNfcFService.onDeactivated(arrby.arg1);
                    }
                } else {
                    if (HostNfcFService.this.mNfcService == null) {
                        Log.e(HostNfcFService.TAG, "Response not sent; service was deactivated.");
                        return;
                    }
                    try {
                        arrby.replyTo = HostNfcFService.this.mMessenger;
                        HostNfcFService.this.mNfcService.send((Message)arrby);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(HostNfcFService.TAG, "RemoteException calling into NfcService.");
                    }
                }
            } else {
                Bundle bundle = arrby.getData();
                if (bundle == null) {
                    return;
                }
                if (HostNfcFService.this.mNfcService == null) {
                    HostNfcFService.this.mNfcService = arrby.replyTo;
                }
                if ((arrby = bundle.getByteArray(HostNfcFService.KEY_DATA)) != null) {
                    if ((arrby = HostNfcFService.this.processNfcFPacket(arrby, null)) != null) {
                        if (HostNfcFService.this.mNfcService == null) {
                            Log.e(HostNfcFService.TAG, "Response not sent; service was deactivated.");
                            return;
                        }
                        Message message = Message.obtain(null, 1);
                        bundle = new Bundle();
                        bundle.putByteArray(HostNfcFService.KEY_DATA, arrby);
                        message.setData(bundle);
                        message.replyTo = HostNfcFService.this.mMessenger;
                        try {
                            HostNfcFService.this.mNfcService.send(message);
                        }
                        catch (RemoteException remoteException) {
                            Log.e("TAG", "Response not sent; RemoteException calling into NfcService.");
                        }
                    }
                } else {
                    Log.e(HostNfcFService.TAG, "Received MSG_COMMAND_PACKET without data.");
                }
            }
        }
    }

}


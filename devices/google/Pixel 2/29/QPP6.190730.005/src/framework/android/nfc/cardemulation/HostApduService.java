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
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

public abstract class HostApduService
extends Service {
    public static final int DEACTIVATION_DESELECTED = 1;
    public static final int DEACTIVATION_LINK_LOSS = 0;
    public static final String KEY_DATA = "data";
    public static final int MSG_COMMAND_APDU = 0;
    public static final int MSG_DEACTIVATED = 2;
    public static final int MSG_RESPONSE_APDU = 1;
    public static final int MSG_UNHANDLED = 3;
    public static final String SERVICE_INTERFACE = "android.nfc.cardemulation.action.HOST_APDU_SERVICE";
    public static final String SERVICE_META_DATA = "android.nfc.cardemulation.host_apdu_service";
    static final String TAG = "ApduService";
    final Messenger mMessenger = new Messenger(new MsgHandler());
    Messenger mNfcService = null;

    public final void notifyUnhandled() {
        Message message = Message.obtain(null, 3);
        try {
            this.mMessenger.send(message);
        }
        catch (RemoteException remoteException) {
            Log.e("TAG", "Local messenger has died.");
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }

    public abstract void onDeactivated(int var1);

    public abstract byte[] processCommandApdu(byte[] var1, Bundle var2);

    public final void sendResponseApdu(byte[] arrby) {
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
                        if (n != 3) {
                            super.handleMessage((Message)arrby);
                        } else {
                            if (HostApduService.this.mNfcService == null) {
                                Log.e(HostApduService.TAG, "notifyUnhandled not sent; service was deactivated.");
                                return;
                            }
                            try {
                                arrby.replyTo = HostApduService.this.mMessenger;
                                HostApduService.this.mNfcService.send((Message)arrby);
                            }
                            catch (RemoteException remoteException) {
                                Log.e(HostApduService.TAG, "RemoteException calling into NfcService.");
                            }
                        }
                    } else {
                        HostApduService hostApduService = HostApduService.this;
                        hostApduService.mNfcService = null;
                        hostApduService.onDeactivated(arrby.arg1);
                    }
                } else {
                    if (HostApduService.this.mNfcService == null) {
                        Log.e(HostApduService.TAG, "Response not sent; service was deactivated.");
                        return;
                    }
                    try {
                        arrby.replyTo = HostApduService.this.mMessenger;
                        HostApduService.this.mNfcService.send((Message)arrby);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(HostApduService.TAG, "RemoteException calling into NfcService.");
                    }
                }
            } else {
                Parcelable parcelable = arrby.getData();
                if (parcelable == null) {
                    return;
                }
                if (HostApduService.this.mNfcService == null) {
                    HostApduService.this.mNfcService = arrby.replyTo;
                }
                if ((arrby = ((Bundle)parcelable).getByteArray(HostApduService.KEY_DATA)) != null) {
                    if ((arrby = HostApduService.this.processCommandApdu(arrby, null)) != null) {
                        if (HostApduService.this.mNfcService == null) {
                            Log.e(HostApduService.TAG, "Response not sent; service was deactivated.");
                            return;
                        }
                        parcelable = Message.obtain(null, 1);
                        Bundle bundle = new Bundle();
                        bundle.putByteArray(HostApduService.KEY_DATA, arrby);
                        ((Message)parcelable).setData(bundle);
                        ((Message)parcelable).replyTo = HostApduService.this.mMessenger;
                        try {
                            HostApduService.this.mNfcService.send((Message)parcelable);
                        }
                        catch (RemoteException remoteException) {
                            Log.e("TAG", "Response not sent; RemoteException calling into NfcService.");
                        }
                    }
                } else {
                    Log.e(HostApduService.TAG, "Received MSG_COMMAND_APDU without data.");
                }
            }
        }
    }

}


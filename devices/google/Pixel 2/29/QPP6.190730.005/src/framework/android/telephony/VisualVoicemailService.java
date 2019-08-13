/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.telephony.VisualVoicemailSms;
import android.telephony.VisualVoicemailSmsFilterSettings;
import android.util.Log;

public abstract class VisualVoicemailService
extends Service {
    public static final String DATA_PHONE_ACCOUNT_HANDLE = "data_phone_account_handle";
    public static final String DATA_SMS = "data_sms";
    public static final int MSG_ON_CELL_SERVICE_CONNECTED = 1;
    public static final int MSG_ON_SIM_REMOVED = 3;
    public static final int MSG_ON_SMS_RECEIVED = 2;
    public static final int MSG_TASK_ENDED = 4;
    public static final int MSG_TASK_STOPPED = 5;
    public static final String SERVICE_INTERFACE = "android.telephony.VisualVoicemailService";
    private static final String TAG = "VvmService";
    private final Messenger mMessenger = new Messenger(new Handler(){

        @Override
        public void handleMessage(Message parcelable) {
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle)parcelable.getData().getParcelable(VisualVoicemailService.DATA_PHONE_ACCOUNT_HANDLE);
            VisualVoicemailTask visualVoicemailTask = new VisualVoicemailTask(parcelable.replyTo, parcelable.arg1);
            int n = parcelable.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 5) {
                            super.handleMessage((Message)parcelable);
                        } else {
                            VisualVoicemailService.this.onStopped(visualVoicemailTask);
                        }
                    } else {
                        VisualVoicemailService.this.onSimRemoved(visualVoicemailTask, phoneAccountHandle);
                    }
                } else {
                    parcelable = (VisualVoicemailSms)parcelable.getData().getParcelable(VisualVoicemailService.DATA_SMS);
                    VisualVoicemailService.this.onSmsReceived(visualVoicemailTask, (VisualVoicemailSms)parcelable);
                }
            } else {
                VisualVoicemailService.this.onCellServiceConnected(visualVoicemailTask, phoneAccountHandle);
            }
        }
    });

    private static int getSubId(Context object, PhoneAccountHandle phoneAccountHandle) {
        TelephonyManager telephonyManager = ((Context)object).getSystemService(TelephonyManager.class);
        object = ((Context)object).getSystemService(TelecomManager.class);
        return telephonyManager.getSubIdForPhoneAccount(((TelecomManager)object).getPhoneAccount(phoneAccountHandle));
    }

    @SystemApi
    public static final void sendVisualVoicemailSms(Context context, PhoneAccountHandle phoneAccountHandle, String string2, short s, String string3, PendingIntent pendingIntent) {
        context.getSystemService(TelephonyManager.class).sendVisualVoicemailSmsForSubscriber(VisualVoicemailService.getSubId(context, phoneAccountHandle), string2, s, string3, pendingIntent);
    }

    @SystemApi
    public static final void setSmsFilterSettings(Context context, PhoneAccountHandle phoneAccountHandle, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        TelephonyManager telephonyManager = context.getSystemService(TelephonyManager.class);
        int n = VisualVoicemailService.getSubId(context, phoneAccountHandle);
        if (visualVoicemailSmsFilterSettings == null) {
            telephonyManager.disableVisualVoicemailSmsFilter(n);
        } else {
            telephonyManager.enableVisualVoicemailSmsFilter(n, visualVoicemailSmsFilterSettings);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }

    public abstract void onCellServiceConnected(VisualVoicemailTask var1, PhoneAccountHandle var2);

    public abstract void onSimRemoved(VisualVoicemailTask var1, PhoneAccountHandle var2);

    public abstract void onSmsReceived(VisualVoicemailTask var1, VisualVoicemailSms var2);

    public abstract void onStopped(VisualVoicemailTask var1);

    public static class VisualVoicemailTask {
        private final Messenger mReplyTo;
        private final int mTaskId;

        private VisualVoicemailTask(Messenger messenger, int n) {
            this.mTaskId = n;
            this.mReplyTo = messenger;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof VisualVoicemailTask;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            if (this.mTaskId == ((VisualVoicemailTask)object).mTaskId) {
                bl2 = true;
            }
            return bl2;
        }

        public final void finish() {
            Message message = Message.obtain();
            try {
                message.what = 4;
                message.arg1 = this.mTaskId;
                this.mReplyTo.send(message);
            }
            catch (RemoteException remoteException) {
                Log.e(VisualVoicemailService.TAG, "Cannot send MSG_TASK_ENDED, remote handler no longer exist");
            }
        }

        public int hashCode() {
            return this.mTaskId;
        }
    }

}


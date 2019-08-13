/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.telephony.SmsManager
 *  android.telephony.SmsMessage
 */
package android.telephony.gsm;

import android.app.PendingIntent;
import android.telephony.SmsMessage;
import java.util.ArrayList;

@Deprecated
public final class SmsManager {
    @Deprecated
    public static final int RESULT_ERROR_GENERIC_FAILURE = 1;
    @Deprecated
    public static final int RESULT_ERROR_NO_SERVICE = 4;
    @Deprecated
    public static final int RESULT_ERROR_NULL_PDU = 3;
    @Deprecated
    public static final int RESULT_ERROR_RADIO_OFF = 2;
    @Deprecated
    public static final int STATUS_ON_SIM_FREE = 0;
    @Deprecated
    public static final int STATUS_ON_SIM_READ = 1;
    @Deprecated
    public static final int STATUS_ON_SIM_SENT = 5;
    @Deprecated
    public static final int STATUS_ON_SIM_UNREAD = 3;
    @Deprecated
    public static final int STATUS_ON_SIM_UNSENT = 7;
    private static SmsManager sInstance;
    private android.telephony.SmsManager mSmsMgrProxy = android.telephony.SmsManager.getDefault();

    @Deprecated
    private SmsManager() {
    }

    @Deprecated
    public static final SmsManager getDefault() {
        if (sInstance == null) {
            sInstance = new SmsManager();
        }
        return sInstance;
    }

    @Deprecated
    public final boolean copyMessageToSim(byte[] arrby, byte[] arrby2, int n) {
        return this.mSmsMgrProxy.copyMessageToIcc(arrby, arrby2, n);
    }

    @Deprecated
    public final boolean deleteMessageFromSim(int n) {
        return this.mSmsMgrProxy.deleteMessageFromIcc(n);
    }

    @Deprecated
    public final ArrayList<String> divideMessage(String string) {
        return this.mSmsMgrProxy.divideMessage(string);
    }

    @Deprecated
    public final ArrayList<SmsMessage> getAllMessagesFromSim() {
        return android.telephony.SmsManager.getDefault().getAllMessagesFromIcc();
    }

    @Deprecated
    public final void sendDataMessage(String string, String string2, short s, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        this.mSmsMgrProxy.sendDataMessage(string, string2, s, arrby, pendingIntent, pendingIntent2);
    }

    @Deprecated
    public final void sendMultipartTextMessage(String string, String string2, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3) {
        this.mSmsMgrProxy.sendMultipartTextMessage(string, string2, arrayList, arrayList2, arrayList3);
    }

    @Deprecated
    public final void sendTextMessage(String string, String string2, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        this.mSmsMgrProxy.sendTextMessage(string, string2, string3, pendingIntent, pendingIntent2);
    }

    @Deprecated
    public final boolean updateMessageOnSim(int n, int n2, byte[] arrby) {
        return this.mSmsMgrProxy.updateMessageOnIcc(n, n2, arrby);
    }
}


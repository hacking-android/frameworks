/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.INetInitiatedListener;
import android.location.LocationManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.app.NetInitiatedActivity;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.telephony.GsmAlphabet;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class GpsNetInitiatedHandler {
    public static final String ACTION_NI_VERIFY = "android.intent.action.NETWORK_INITIATED_VERIFY";
    private static final boolean DEBUG = Log.isLoggable("GpsNetInitiatedHandler", 3);
    public static final int GPS_ENC_NONE = 0;
    public static final int GPS_ENC_SUPL_GSM_DEFAULT = 1;
    public static final int GPS_ENC_SUPL_UCS2 = 3;
    public static final int GPS_ENC_SUPL_UTF8 = 2;
    public static final int GPS_ENC_UNKNOWN = -1;
    public static final int GPS_NI_NEED_NOTIFY = 1;
    public static final int GPS_NI_NEED_VERIFY = 2;
    public static final int GPS_NI_PRIVACY_OVERRIDE = 4;
    public static final int GPS_NI_RESPONSE_ACCEPT = 1;
    public static final int GPS_NI_RESPONSE_DENY = 2;
    public static final int GPS_NI_RESPONSE_IGNORE = 4;
    public static final int GPS_NI_RESPONSE_NORESP = 3;
    public static final int GPS_NI_TYPE_EMERGENCY_SUPL = 4;
    public static final int GPS_NI_TYPE_UMTS_CTRL_PLANE = 3;
    public static final int GPS_NI_TYPE_UMTS_SUPL = 2;
    public static final int GPS_NI_TYPE_VOICE = 1;
    public static final String NI_EXTRA_CMD_NOTIF_ID = "notif_id";
    public static final String NI_EXTRA_CMD_RESPONSE = "response";
    public static final String NI_INTENT_KEY_DEFAULT_RESPONSE = "default_resp";
    public static final String NI_INTENT_KEY_MESSAGE = "message";
    public static final String NI_INTENT_KEY_NOTIF_ID = "notif_id";
    public static final String NI_INTENT_KEY_TIMEOUT = "timeout";
    public static final String NI_INTENT_KEY_TITLE = "title";
    public static final String NI_RESPONSE_EXTRA_CMD = "send_ni_response";
    private static final String TAG = "GpsNetInitiatedHandler";
    private static boolean mIsHexInput = true;
    private final BroadcastReceiver mBroadcastReciever = new BroadcastReceiver(){

        @Override
        public void onReceive(Context object, Intent intent) {
            block2 : {
                block1 : {
                    object = intent.getAction();
                    if (!((String)object).equals("android.intent.action.NEW_OUTGOING_CALL")) break block1;
                    object = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
                    GpsNetInitiatedHandler.this.mIsInEmergencyCall = PhoneNumberUtils.isEmergencyNumber((String)object);
                    if (!DEBUG) break block2;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("ACTION_NEW_OUTGOING_CALL - ");
                    ((StringBuilder)object).append(GpsNetInitiatedHandler.this.getInEmergency());
                    Log.v(GpsNetInitiatedHandler.TAG, ((StringBuilder)object).toString());
                    break block2;
                }
                if (!((String)object).equals("android.location.MODE_CHANGED")) break block2;
                GpsNetInitiatedHandler.this.updateLocationMode();
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("location enabled :");
                    ((StringBuilder)object).append(GpsNetInitiatedHandler.this.getLocationEnabled());
                    Log.d(GpsNetInitiatedHandler.TAG, ((StringBuilder)object).toString());
                }
            }
        }
    };
    private volatile long mCallEndElapsedRealtimeMillis = 0L;
    private final Context mContext;
    private volatile long mEmergencyExtensionMillis = 0L;
    private volatile boolean mIsInEmergencyCall;
    private volatile boolean mIsLocationEnabled = false;
    private volatile boolean mIsSuplEsEnabled;
    private final LocationManager mLocationManager;
    private final INetInitiatedListener mNetInitiatedListener;
    private Notification.Builder mNiNotificationBuilder;
    private final PhoneStateListener mPhoneStateListener;
    private boolean mPlaySounds = false;
    private boolean mPopupImmediately = true;
    private final TelephonyManager mTelephonyManager;

    public GpsNetInitiatedHandler(Context object, INetInitiatedListener iNetInitiatedListener, boolean bl) {
        this.mContext = object;
        if (iNetInitiatedListener != null) {
            this.mNetInitiatedListener = iNetInitiatedListener;
            this.setSuplEsEnabled(bl);
            this.mLocationManager = (LocationManager)((Context)object).getSystemService("location");
            this.updateLocationMode();
            this.mTelephonyManager = (TelephonyManager)((Context)object).getSystemService("phone");
            this.mPhoneStateListener = new PhoneStateListener(){

                @Override
                public void onCallStateChanged(int n, String charSequence) {
                    if (DEBUG) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("onCallStateChanged(): state is ");
                        ((StringBuilder)charSequence).append(n);
                        Log.d(GpsNetInitiatedHandler.TAG, ((StringBuilder)charSequence).toString());
                    }
                    if (n == 0 && GpsNetInitiatedHandler.this.mIsInEmergencyCall) {
                        GpsNetInitiatedHandler.this.mCallEndElapsedRealtimeMillis = SystemClock.elapsedRealtime();
                        GpsNetInitiatedHandler.this.mIsInEmergencyCall = false;
                    }
                }
            };
            this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
            object = new IntentFilter();
            ((IntentFilter)object).addAction("android.intent.action.NEW_OUTGOING_CALL");
            ((IntentFilter)object).addAction("android.location.MODE_CHANGED");
            this.mContext.registerReceiver(this.mBroadcastReciever, (IntentFilter)object);
            return;
        }
        throw new IllegalArgumentException("netInitiatedListener is null");
    }

    static String decodeGSMPackedString(byte[] object) {
        int n;
        int n2 = ((byte[])object).length;
        int n3 = n = n2 * 8 / 7;
        if (n2 % 7 == 0) {
            n3 = n;
            if (n2 > 0) {
                n3 = n;
                if (object[n2 - 1] >> 1 == 0) {
                    n3 = n - 1;
                }
            }
        }
        String string2 = GsmAlphabet.gsm7BitPackedToString(object, 0, n3);
        object = string2;
        if (string2 == null) {
            Log.e(TAG, "Decoding of GSM packed string failed");
            object = "";
        }
        return object;
    }

    private static String decodeString(String string2, boolean bl, int n) {
        if (n != 0 && n != -1) {
            Object object = GpsNetInitiatedHandler.stringToByteArray(string2, bl);
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unknown encoding ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" for NI text ");
                        ((StringBuilder)object).append(string2);
                        Log.e(TAG, ((StringBuilder)object).toString());
                        return string2;
                    }
                    return GpsNetInitiatedHandler.decodeUCS2String((byte[])object);
                }
                return GpsNetInitiatedHandler.decodeUTF8String((byte[])object);
            }
            return GpsNetInitiatedHandler.decodeGSMPackedString((byte[])object);
        }
        return string2;
    }

    static String decodeUCS2String(byte[] object) {
        try {
            object = new String((byte[])object, "UTF-16");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError();
        }
    }

    static String decodeUTF8String(byte[] object) {
        try {
            object = new String((byte[])object, "UTF-8");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError();
        }
    }

    private static String getDialogMessage(GpsNiNotification gpsNiNotification, Context context) {
        return GpsNetInitiatedHandler.getNotifMessage(gpsNiNotification, context);
    }

    public static String getDialogTitle(GpsNiNotification gpsNiNotification, Context context) {
        return GpsNetInitiatedHandler.getNotifTitle(gpsNiNotification, context);
    }

    private Intent getDlgIntent(GpsNiNotification gpsNiNotification) {
        Intent intent = new Intent();
        String string2 = GpsNetInitiatedHandler.getDialogTitle(gpsNiNotification, this.mContext);
        String string3 = GpsNetInitiatedHandler.getDialogMessage(gpsNiNotification, this.mContext);
        intent.setFlags(268468224);
        intent.setClass(this.mContext, NetInitiatedActivity.class);
        intent.putExtra("notif_id", gpsNiNotification.notificationId);
        intent.putExtra(NI_INTENT_KEY_TITLE, string2);
        intent.putExtra(NI_INTENT_KEY_MESSAGE, string3);
        intent.putExtra(NI_INTENT_KEY_TIMEOUT, gpsNiNotification.timeout);
        intent.putExtra(NI_INTENT_KEY_DEFAULT_RESPONSE, gpsNiNotification.defaultResponse);
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("generateIntent, title: ");
            stringBuilder.append(string2);
            stringBuilder.append(", message: ");
            stringBuilder.append(string3);
            stringBuilder.append(", timeout: ");
            stringBuilder.append(gpsNiNotification.timeout);
            Log.d(TAG, stringBuilder.toString());
        }
        return intent;
    }

    private static String getNotifMessage(GpsNiNotification gpsNiNotification, Context context) {
        return String.format(context.getString(17040067), GpsNetInitiatedHandler.decodeString(gpsNiNotification.requestorId, mIsHexInput, gpsNiNotification.requestorIdEncoding), GpsNetInitiatedHandler.decodeString(gpsNiNotification.text, mIsHexInput, gpsNiNotification.textEncoding));
    }

    private static String getNotifTicker(GpsNiNotification gpsNiNotification, Context context) {
        return String.format(context.getString(17040068), GpsNetInitiatedHandler.decodeString(gpsNiNotification.requestorId, mIsHexInput, gpsNiNotification.requestorIdEncoding), GpsNetInitiatedHandler.decodeString(gpsNiNotification.text, mIsHexInput, gpsNiNotification.textEncoding));
    }

    private static String getNotifTitle(GpsNiNotification gpsNiNotification, Context context) {
        return String.format(context.getString(17040069), new Object[0]);
    }

    private void handleNi(GpsNiNotification gpsNiNotification) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("in handleNi () : needNotify: ");
            stringBuilder.append(gpsNiNotification.needNotify);
            stringBuilder.append(" needVerify: ");
            stringBuilder.append(gpsNiNotification.needVerify);
            stringBuilder.append(" privacyOverride: ");
            stringBuilder.append(gpsNiNotification.privacyOverride);
            stringBuilder.append(" mPopupImmediately: ");
            stringBuilder.append(this.mPopupImmediately);
            stringBuilder.append(" mInEmergency: ");
            stringBuilder.append(this.getInEmergency());
            Log.d(TAG, stringBuilder.toString());
        }
        if (!this.getLocationEnabled() && !this.getInEmergency()) {
            try {
                this.mNetInitiatedListener.sendNiResponse(gpsNiNotification.notificationId, 4);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "RemoteException in sendNiResponse");
            }
        }
        if (gpsNiNotification.needNotify) {
            if (gpsNiNotification.needVerify && this.mPopupImmediately) {
                this.openNiDialog(gpsNiNotification);
            } else {
                this.setNiNotification(gpsNiNotification);
            }
        }
        if (!gpsNiNotification.needVerify || gpsNiNotification.privacyOverride) {
            try {
                this.mNetInitiatedListener.sendNiResponse(gpsNiNotification.notificationId, 1);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "RemoteException in sendNiResponse");
            }
        }
    }

    private void handleNiInEs(GpsNiNotification gpsNiNotification) {
        boolean bl;
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("in handleNiInEs () : niType: ");
            stringBuilder.append(gpsNiNotification.niType);
            stringBuilder.append(" notificationId: ");
            stringBuilder.append(gpsNiNotification.notificationId);
            Log.d(TAG, stringBuilder.toString());
        }
        if ((bl = gpsNiNotification.niType == 4) != this.getInEmergency()) {
            try {
                this.mNetInitiatedListener.sendNiResponse(gpsNiNotification.notificationId, 4);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "RemoteException in sendNiResponse");
            }
        } else {
            this.handleNi(gpsNiNotification);
        }
    }

    private void openNiDialog(GpsNiNotification gpsNiNotification) {
        Intent intent = this.getDlgIntent(gpsNiNotification);
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("openNiDialog, notifyId: ");
            stringBuilder.append(gpsNiNotification.notificationId);
            stringBuilder.append(", requestorId: ");
            stringBuilder.append(gpsNiNotification.requestorId);
            stringBuilder.append(", text: ");
            stringBuilder.append(gpsNiNotification.text);
            Log.d(TAG, stringBuilder.toString());
        }
        this.mContext.startActivity(intent);
    }

    private void setNiNotification(GpsNiNotification gpsNiNotification) {
        synchronized (this) {
            NotificationManager notificationManager;
            Object object;
            block8 : {
                notificationManager = (NotificationManager)this.mContext.getSystemService("notification");
                if (notificationManager != null) break block8;
                return;
            }
            String string2 = GpsNetInitiatedHandler.getNotifTitle(gpsNiNotification, this.mContext);
            String string3 = GpsNetInitiatedHandler.getNotifMessage(gpsNiNotification, this.mContext);
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("setNiNotification, notifyId: ");
                ((StringBuilder)object).append(gpsNiNotification.notificationId);
                ((StringBuilder)object).append(", title: ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(", message: ");
                ((StringBuilder)object).append(string3);
                Log.d(TAG, ((StringBuilder)object).toString());
            }
            if (this.mNiNotificationBuilder == null) {
                object = new Notification.Builder(this.mContext, SystemNotificationChannels.NETWORK_ALERTS);
                this.mNiNotificationBuilder = ((Notification.Builder)object).setSmallIcon(17303560).setWhen(0L).setOngoing(true).setAutoCancel(true).setColor(this.mContext.getColor(17170460));
            }
            if (this.mPlaySounds) {
                this.mNiNotificationBuilder.setDefaults(1);
            } else {
                this.mNiNotificationBuilder.setDefaults(0);
            }
            object = !this.mPopupImmediately ? this.getDlgIntent(gpsNiNotification) : new Intent();
            object = PendingIntent.getBroadcast(this.mContext, 0, (Intent)object, 0);
            this.mNiNotificationBuilder.setTicker(GpsNetInitiatedHandler.getNotifTicker(gpsNiNotification, this.mContext)).setContentTitle(string2).setContentText(string3).setContentIntent((PendingIntent)object);
            notificationManager.notifyAsUser(null, gpsNiNotification.notificationId, this.mNiNotificationBuilder.build(), UserHandle.ALL);
            return;
        }
    }

    static byte[] stringToByteArray(String string2, boolean bl) {
        int n;
        int n2 = n = string2.length();
        if (bl) {
            n2 = n / 2;
        }
        byte[] arrby = new byte[n2];
        if (bl) {
            for (n = 0; n < n2; ++n) {
                arrby[n] = (byte)Integer.parseInt(string2.substring(n * 2, n * 2 + 2), 16);
            }
        } else {
            for (n = 0; n < n2; ++n) {
                arrby[n] = (byte)string2.charAt(n);
            }
        }
        return arrby;
    }

    public boolean getInEmergency() {
        long l = this.mCallEndElapsedRealtimeMillis;
        boolean bl = true;
        boolean bl2 = l > 0L && SystemClock.elapsedRealtime() - this.mCallEndElapsedRealtimeMillis < this.mEmergencyExtensionMillis;
        boolean bl3 = this.mTelephonyManager.getEmergencyCallbackMode();
        boolean bl4 = this.mTelephonyManager.isInEmergencySmsMode();
        boolean bl5 = bl;
        if (!this.mIsInEmergencyCall) {
            bl5 = bl;
            if (!bl3) {
                bl5 = bl;
                if (!bl2) {
                    bl5 = bl4 ? bl : false;
                }
            }
        }
        return bl5;
    }

    public boolean getLocationEnabled() {
        return this.mIsLocationEnabled;
    }

    public boolean getSuplEsEnabled() {
        return this.mIsSuplEsEnabled;
    }

    public void handleNiNotification(GpsNiNotification gpsNiNotification) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("in handleNiNotification () : notificationId: ");
            stringBuilder.append(gpsNiNotification.notificationId);
            stringBuilder.append(" requestorId: ");
            stringBuilder.append(gpsNiNotification.requestorId);
            stringBuilder.append(" text: ");
            stringBuilder.append(gpsNiNotification.text);
            stringBuilder.append(" mIsSuplEsEnabled");
            stringBuilder.append(this.getSuplEsEnabled());
            stringBuilder.append(" mIsLocationEnabled");
            stringBuilder.append(this.getLocationEnabled());
            Log.d(TAG, stringBuilder.toString());
        }
        if (this.getSuplEsEnabled()) {
            this.handleNiInEs(gpsNiNotification);
        } else {
            this.handleNi(gpsNiNotification);
        }
    }

    public void setEmergencyExtensionSeconds(int n) {
        this.mEmergencyExtensionMillis = TimeUnit.SECONDS.toMillis(n);
    }

    public void setSuplEsEnabled(boolean bl) {
        this.mIsSuplEsEnabled = bl;
    }

    public void updateLocationMode() {
        this.mIsLocationEnabled = this.mLocationManager.isProviderEnabled("gps");
    }

    public static class GpsNiNotification {
        public int defaultResponse;
        public boolean needNotify;
        public boolean needVerify;
        public int niType;
        public int notificationId;
        public boolean privacyOverride;
        public String requestorId;
        public int requestorIdEncoding;
        public String text;
        public int textEncoding;
        public int timeout;
    }

    public static class GpsNiResponse {
        int userResponse;
    }

}


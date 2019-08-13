/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.UserManager
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SmsMessage
 *  android.telephony.SmsMessage$MessageClass
 *  android.util.Pair
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.SmsMessageBase$SubmitPduBase
 *  com.android.internal.telephony.cdma.SmsMessage
 *  com.android.internal.telephony.gsm.SmsMessage
 *  com.android.internal.util.IState
 */
package com.android.internal.telephony;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.UserManager;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SmsMessage;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.ImsSmsDispatcher;
import com.android.internal.telephony.InboundSmsHandler;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SMSDispatcher;
import com.android.internal.telephony.SmsBroadcastUndelivered;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.SmsStorageMonitor;
import com.android.internal.telephony.SmsUsageMonitor;
import com.android.internal.telephony.cdma.CdmaInboundSmsHandler;
import com.android.internal.telephony.cdma.CdmaSMSDispatcher;
import com.android.internal.telephony.cdma.SmsMessage;
import com.android.internal.telephony.gsm.GsmInboundSmsHandler;
import com.android.internal.telephony.gsm.GsmSMSDispatcher;
import com.android.internal.util.IState;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class SmsDispatchersController
extends Handler {
    private static final int EVENT_IMS_STATE_CHANGED = 12;
    private static final int EVENT_IMS_STATE_DONE = 13;
    private static final int EVENT_PARTIAL_SEGMENT_TIMER_EXPIRY = 15;
    private static final int EVENT_RADIO_ON = 11;
    private static final int EVENT_SERVICE_STATE_CHANGED = 14;
    protected static final int EVENT_SMS_HANDLER_EXITING_WAITING_STATE = 17;
    private static final int EVENT_USER_UNLOCKED = 16;
    private static final long INVALID_TIME = -1L;
    private static final long PARTIAL_SEGMENT_WAIT_DURATION = 86400000L;
    private static final String TAG = "SmsDispatchersController";
    private static final boolean VDBG = false;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            object = new StringBuilder();
            object.append("Received broadcast ");
            object.append(intent.getAction());
            Rlog.d((String)SmsDispatchersController.TAG, (String)object.toString());
            if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                object = SmsDispatchersController.this;
                object.sendMessage(object.obtainMessage(16));
            }
        }
    };
    private SMSDispatcher mCdmaDispatcher;
    private CdmaInboundSmsHandler mCdmaInboundSmsHandler;
    private final CommandsInterface mCi;
    private final Context mContext;
    private long mCurrentWaitElapsedDuration = 0L;
    private long mCurrentWaitStartTime = -1L;
    private SMSDispatcher mGsmDispatcher;
    private GsmInboundSmsHandler mGsmInboundSmsHandler;
    private boolean mIms = false;
    private ImsSmsDispatcher mImsSmsDispatcher;
    private String mImsSmsFormat = "unknown";
    private long mLastInServiceTime = -1L;
    private Phone mPhone;
    private final SmsUsageMonitor mUsageMonitor;

    public SmsDispatchersController(Phone phone, SmsStorageMonitor smsStorageMonitor, SmsUsageMonitor smsUsageMonitor) {
        Rlog.d((String)TAG, (String)"SmsDispatchersController created");
        this.mContext = phone.getContext();
        this.mUsageMonitor = smsUsageMonitor;
        this.mCi = phone.mCi;
        this.mPhone = phone;
        this.mImsSmsDispatcher = new ImsSmsDispatcher(phone, this);
        this.mCdmaDispatcher = new CdmaSMSDispatcher(phone, this);
        this.mGsmInboundSmsHandler = GsmInboundSmsHandler.makeInboundSmsHandler(phone.getContext(), smsStorageMonitor, phone);
        this.mCdmaInboundSmsHandler = CdmaInboundSmsHandler.makeInboundSmsHandler(phone.getContext(), smsStorageMonitor, phone, (CdmaSMSDispatcher)this.mCdmaDispatcher);
        this.mGsmDispatcher = new GsmSMSDispatcher(phone, this, this.mGsmInboundSmsHandler);
        SmsBroadcastUndelivered.initialize(phone.getContext(), this.mGsmInboundSmsHandler, this.mCdmaInboundSmsHandler);
        InboundSmsHandler.registerNewMessageNotificationActionHandler(phone.getContext());
        this.mCi.registerForOn(this, 11, null);
        this.mCi.registerForImsNetworkStateChanged(this, 12, null);
        if (((UserManager)this.mContext.getSystemService("user")).isUserUnlocked()) {
            this.mPhone.registerForServiceStateChanged(this, 14, null);
            this.resetPartialSegmentWaitTimer();
        } else {
            phone = new IntentFilter();
            phone.addAction("android.intent.action.USER_UNLOCKED");
            this.mContext.registerReceiver(this.mBroadcastReceiver, (IntentFilter)phone);
        }
    }

    private Pair<Boolean, Boolean> handleCdmaStatusReport(SMSDispatcher.SmsTracker smsTracker, String string, byte[] arrby) {
        smsTracker.updateSentMessageStatus(this.mContext, 0);
        return new Pair((Object)this.triggerDeliveryIntent(smsTracker, string, arrby), (Object)true);
    }

    private Pair<Boolean, Boolean> handleGsmStatusReport(SMSDispatcher.SmsTracker smsTracker, String string, byte[] arrby) {
        boolean bl;
        boolean bl2;
        block2 : {
            block4 : {
                int n;
                block3 : {
                    com.android.internal.telephony.gsm.SmsMessage smsMessage = com.android.internal.telephony.gsm.SmsMessage.newFromCDS((byte[])arrby);
                    bl2 = false;
                    boolean bl3 = false;
                    bl = false;
                    if (smsMessage == null) break block2;
                    n = smsMessage.getStatus();
                    if (n >= 64) break block3;
                    bl2 = bl3;
                    if (n >= 32) break block4;
                }
                smsTracker.updateSentMessageStatus(this.mContext, n);
                bl2 = true;
            }
            bl = this.triggerDeliveryIntent(smsTracker, string, arrby);
        }
        return new Pair((Object)bl, (Object)bl2);
    }

    private void handleInService(long l) {
        if (this.mCurrentWaitStartTime == -1L) {
            this.mCurrentWaitStartTime = l;
        }
        this.sendMessageDelayed(this.obtainMessage(15, (Object)this.mCurrentWaitStartTime), 86400000L - this.mCurrentWaitElapsedDuration);
        this.mLastInServiceTime = l;
    }

    private void handleOutOfService(long l) {
        this.mLastInServiceTime = -1L;
    }

    private void handlePartialSegmentTimerExpiry(long l) {
        if (!this.mGsmInboundSmsHandler.getCurrentState().getName().equals("WaitingState") && !this.mCdmaInboundSmsHandler.getCurrentState().getName().equals("WaitingState")) {
            SmsBroadcastUndelivered.scanRawTable(this.mContext, this.mCdmaInboundSmsHandler, this.mGsmInboundSmsHandler, l);
            this.resetPartialSegmentWaitTimer();
            return;
        }
        this.logd("handlePartialSegmentTimerExpiry: ignoring timer expiry as InboundSmsHandler is in WaitingState");
    }

    private boolean isInService() {
        ServiceState serviceState = this.mPhone.getServiceState();
        boolean bl = serviceState != null && serviceState.getState() == 0;
        return bl;
    }

    private void logd(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void reevaluateTimerStatus() {
        long l = System.currentTimeMillis();
        this.removeMessages(15);
        long l2 = this.mLastInServiceTime;
        if (l2 != -1L) {
            this.mCurrentWaitElapsedDuration += l - l2;
        }
        if (this.mCurrentWaitElapsedDuration > 86400000L) {
            this.handlePartialSegmentTimerExpiry(this.mCurrentWaitStartTime);
        } else if (this.isInService()) {
            this.handleInService(l);
        } else {
            this.handleOutOfService(l);
        }
    }

    private void resetPartialSegmentWaitTimer() {
        long l = System.currentTimeMillis();
        this.removeMessages(15);
        if (this.isInService()) {
            this.mCurrentWaitStartTime = l;
            this.mLastInServiceTime = l;
            this.sendMessageDelayed(this.obtainMessage(15, (Object)this.mCurrentWaitStartTime), 86400000L);
        } else {
            this.mCurrentWaitStartTime = -1L;
            this.mLastInServiceTime = -1L;
        }
        this.mCurrentWaitElapsedDuration = 0L;
    }

    private void setImsSmsFormat(int n) {
        this.mImsSmsFormat = n != 1 ? (n != 2 ? "unknown" : "3gpp2") : "3gpp";
    }

    private boolean triggerDeliveryIntent(SMSDispatcher.SmsTracker smsTracker, String string, byte[] arrby) {
        smsTracker = smsTracker.mDeliveryIntent;
        Intent intent = new Intent();
        intent.putExtra("pdu", arrby);
        intent.putExtra("format", string);
        try {
            smsTracker.send(this.mContext, -1, intent);
            return true;
        }
        catch (PendingIntent.CanceledException canceledException) {
            return false;
        }
    }

    private void updateImsInfo(AsyncResult object) {
        object = (int[])((AsyncResult)object).result;
        boolean bl = true;
        this.setImsSmsFormat(object[1]);
        if (object[0] != 1 || "unknown".equals(this.mImsSmsFormat)) {
            bl = false;
        }
        this.mIms = bl;
        object = new StringBuilder();
        ((StringBuilder)object).append("IMS registration state: ");
        ((StringBuilder)object).append(this.mIms);
        ((StringBuilder)object).append(" format: ");
        ((StringBuilder)object).append(this.mImsSmsFormat);
        Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
    }

    public void dispose() {
        this.mCi.unregisterForOn(this);
        this.mCi.unregisterForImsNetworkStateChanged(this);
        this.mPhone.unregisterForServiceStateChanged(this);
        this.mGsmDispatcher.dispose();
        this.mCdmaDispatcher.dispose();
        this.mGsmInboundSmsHandler.dispose();
        this.mCdmaInboundSmsHandler.dispose();
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.mGsmInboundSmsHandler.dump(fileDescriptor, printWriter, arrstring);
        this.mCdmaInboundSmsHandler.dump(fileDescriptor, printWriter, arrstring);
    }

    public String getImsSmsFormat() {
        return this.mImsSmsFormat;
    }

    public int getPremiumSmsPermission(String string) {
        return this.mUsageMonitor.getPremiumSmsPermission(string);
    }

    public SmsUsageMonitor getUsageMonitor() {
        return this.mUsageMonitor;
    }

    public void handleMessage(Message object) {
        block9 : {
            switch (((Message)object).what) {
                default: {
                    if (!this.isCdmaMo()) break;
                    this.mCdmaDispatcher.handleMessage((Message)object);
                    break block9;
                }
                case 16: {
                    this.mPhone.registerForServiceStateChanged(this, 14, null);
                    this.resetPartialSegmentWaitTimer();
                    break block9;
                }
                case 15: {
                    this.handlePartialSegmentTimerExpiry((Long)((Message)object).obj);
                    break block9;
                }
                case 14: 
                case 17: {
                    this.reevaluateTimerStatus();
                    break block9;
                }
                case 13: {
                    AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                    if (asyncResult.exception == null) {
                        this.updateImsInfo(asyncResult);
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("IMS State query failed with exp ");
                        ((StringBuilder)object).append(asyncResult.exception);
                        Rlog.e((String)TAG, (String)((StringBuilder)object).toString());
                    }
                    break block9;
                }
                case 11: 
                case 12: {
                    this.mCi.getImsRegistrationState(this.obtainMessage(13));
                    break block9;
                }
            }
            this.mGsmDispatcher.handleMessage((Message)object);
        }
    }

    public Pair<Boolean, Boolean> handleSmsStatusReport(SMSDispatcher.SmsTracker smsTracker, String string, byte[] arrby) {
        if (this.isCdmaFormat(string)) {
            return this.handleCdmaStatusReport(smsTracker, string, arrby);
        }
        return this.handleGsmStatusReport(smsTracker, string, arrby);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public void injectSmsPdu(android.telephony.SmsMessage var1_1, String var2_3, SmsInjectionCallback var3_4, boolean var4_5) {
        Rlog.d((String)"SmsDispatchersController", (String)"SmsDispatchersController:injectSmsPdu");
        if (var1_1 != null) ** GOTO lbl9
        try {
            Rlog.e((String)"SmsDispatchersController", (String)"injectSmsPdu: createFromPdu returned null");
            var3_4.onSmsInjectedResult(2);
            return;
lbl9: // 1 sources:
            if (!var4_5 && var1_1.getMessageClass() != SmsMessage.MessageClass.CLASS_1) {
                Rlog.e((String)"SmsDispatchersController", (String)"injectSmsPdu: not class 1");
                var3_4.onSmsInjectedResult(2);
                return;
            }
            var5_6 = new AsyncResult((Object)var3_4, var1_1, null);
            var4_5 = var2_3.equals("3gpp");
            if (var4_5) {
                var6_7 = new StringBuilder();
                var6_7.append("SmsDispatchersController:injectSmsText Sending msg=");
                var6_7.append(var1_1);
                var6_7.append(", format=");
                var6_7.append(var2_3);
                var6_7.append("to mGsmInboundSmsHandler");
                Rlog.i((String)"SmsDispatchersController", (String)var6_7.toString());
                this.mGsmInboundSmsHandler.sendMessage(7, (Object)var5_6);
                return;
            }
            if (var2_3.equals("3gpp2")) {
                var6_8 = new StringBuilder();
                var6_8.append("SmsDispatchersController:injectSmsText Sending msg=");
                var6_8.append(var1_1);
                var6_8.append(", format=");
                var6_8.append(var2_3);
                var6_8.append("to mCdmaInboundSmsHandler");
                Rlog.i((String)"SmsDispatchersController", (String)var6_8.toString());
                this.mCdmaInboundSmsHandler.sendMessage(7, (Object)var5_6);
                return;
            }
            var1_1 = new StringBuilder();
            var1_1.append("Invalid pdu format: ");
            var1_1.append(var2_3);
            Rlog.e((String)"SmsDispatchersController", (String)var1_1.toString());
            var3_4.onSmsInjectedResult(2);
            return;
        }
        catch (Exception var1_2) {
            Rlog.e((String)"SmsDispatchersController", (String)"injectSmsPdu failed: ", (Throwable)var1_2);
            var3_4.onSmsInjectedResult(2);
        }
    }

    @VisibleForTesting
    public void injectSmsPdu(byte[] arrby, String string, SmsInjectionCallback smsInjectionCallback) {
        this.injectSmsPdu(android.telephony.SmsMessage.createFromPdu((byte[])arrby, (String)string), string, smsInjectionCallback, false);
    }

    public boolean isCdmaFormat(String string) {
        return this.mCdmaDispatcher.getFormat().equals(string);
    }

    protected boolean isCdmaMo() {
        if (!this.isIms()) {
            boolean bl = 2 == this.mPhone.getPhoneType();
            return bl;
        }
        return this.isCdmaFormat(this.mImsSmsFormat);
    }

    public boolean isIms() {
        return this.mIms;
    }

    protected void sendData(String string, String string2, String string3, int n, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        if (this.mImsSmsDispatcher.isAvailable()) {
            this.mImsSmsDispatcher.sendData(string, string2, string3, n, arrby, pendingIntent, pendingIntent2, bl);
        } else if (this.isCdmaMo()) {
            this.mCdmaDispatcher.sendData(string, string2, string3, n, arrby, pendingIntent, pendingIntent2, bl);
        } else {
            this.mGsmDispatcher.sendData(string, string2, string3, n, arrby, pendingIntent, pendingIntent2, bl);
        }
    }

    protected void sendMultipartText(String string, String string2, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3, Uri uri, String string3, boolean bl, int n, boolean bl2, int n2) {
        if (this.mImsSmsDispatcher.isAvailable()) {
            this.mImsSmsDispatcher.sendMultipartText(string, string2, arrayList, arrayList2, arrayList3, uri, string3, bl, -1, false, -1);
        } else if (this.isCdmaMo()) {
            this.mCdmaDispatcher.sendMultipartText(string, string2, arrayList, arrayList2, arrayList3, uri, string3, bl, n, bl2, n2);
        } else {
            this.mGsmDispatcher.sendMultipartText(string, string2, arrayList, arrayList2, arrayList3, uri, string3, bl, n, bl2, n2);
        }
    }

    public void sendRetrySms(SMSDispatcher.SmsTracker smsTracker) {
        Object object = smsTracker.mFormat;
        Object object2 = 2 == this.mPhone.getPhoneType() ? this.mCdmaDispatcher : this.mGsmDispatcher;
        String string = object2.getFormat();
        if (((String)object).equals(string)) {
            if (this.isCdmaFormat(string)) {
                Rlog.d((String)TAG, (String)"old format matched new format (cdma)");
                this.mCdmaDispatcher.sendSms(smsTracker);
                return;
            }
            Rlog.d((String)TAG, (String)"old format matched new format (gsm)");
            this.mGsmDispatcher.sendSms(smsTracker);
            return;
        }
        object = smsTracker.getData();
        boolean bl = ((HashMap)object).containsKey("scAddr");
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        boolean bl5 = true;
        if (bl && ((HashMap)object).containsKey("destAddr") && (((HashMap)object).containsKey("text") || ((HashMap)object).containsKey("data") && ((HashMap)object).containsKey("destPort"))) {
            String string2 = (String)((HashMap)object).get("scAddr");
            String string3 = (String)((HashMap)object).get("destAddr");
            object2 = null;
            if (((HashMap)object).containsKey("text")) {
                Rlog.d((String)TAG, (String)"sms failed was text");
                object2 = (String)((HashMap)object).get("text");
                if (this.isCdmaFormat(string)) {
                    Rlog.d((String)TAG, (String)"old format (gsm) ==> new format (cdma)");
                    if (smsTracker.mDeliveryIntent == null) {
                        bl5 = false;
                    }
                    object2 = SmsMessage.getSubmitPdu((String)string2, (String)string3, (String)object2, (boolean)bl5, null);
                } else {
                    Rlog.d((String)TAG, (String)"old format (cdma) ==> new format (gsm)");
                    bl5 = smsTracker.mDeliveryIntent != null ? bl2 : false;
                    object2 = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string2, (String)string3, (String)object2, (boolean)bl5, null);
                }
            } else if (((HashMap)object).containsKey("data")) {
                Rlog.d((String)TAG, (String)"sms failed was data");
                object2 = (byte[])((HashMap)object).get("data");
                Integer n = (Integer)((HashMap)object).get("destPort");
                if (this.isCdmaFormat(string)) {
                    Rlog.d((String)TAG, (String)"old format (gsm) ==> new format (cdma)");
                    int n2 = n;
                    bl5 = smsTracker.mDeliveryIntent != null ? bl3 : false;
                    object2 = SmsMessage.getSubmitPdu((String)string2, (String)string3, (int)n2, (byte[])object2, (boolean)bl5);
                } else {
                    Rlog.d((String)TAG, (String)"old format (cdma) ==> new format (gsm)");
                    int n3 = n;
                    bl5 = smsTracker.mDeliveryIntent != null ? bl4 : false;
                    object2 = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string2, (String)string3, (int)n3, (byte[])object2, (boolean)bl5);
                }
            }
            ((HashMap)object).put("smsc", object2.encodedScAddress);
            ((HashMap)object).put("pdu", object2.encodedMessage);
            object2 = this.isCdmaFormat(string) ? this.mCdmaDispatcher : this.mGsmDispatcher;
            smsTracker.mFormat = object2.getFormat();
            object2.sendSms(smsTracker);
            return;
        }
        Rlog.e((String)TAG, (String)"sendRetrySms failed to re-encode per missing fields!");
        smsTracker.onFailed(this.mContext, 1, 0);
    }

    public void sendText(String string, String string2, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2, Uri uri, String string4, boolean bl, int n, boolean bl2, int n2, boolean bl3) {
        if (!this.mImsSmsDispatcher.isAvailable() && !this.mImsSmsDispatcher.isEmergencySmsSupport(string)) {
            if (this.isCdmaMo()) {
                this.mCdmaDispatcher.sendText(string, string2, string3, pendingIntent, pendingIntent2, uri, string4, bl, n, bl2, n2, bl3);
            } else {
                this.mGsmDispatcher.sendText(string, string2, string3, pendingIntent, pendingIntent2, uri, string4, bl, n, bl2, n2, bl3);
            }
        } else {
            this.mImsSmsDispatcher.sendText(string, string2, string3, pendingIntent, pendingIntent2, uri, string4, bl, -1, false, -1, bl3);
        }
    }

    public void setPremiumSmsPermission(String string, int n) {
        this.mUsageMonitor.setPremiumSmsPermission(string, n);
    }

    public static interface SmsInjectionCallback {
        public void onSmsInjectedResult(int var1);
    }

}


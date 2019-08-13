/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.database.ContentObserver
 *  android.database.sqlite.SqliteWrapper
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.AsyncTask
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.RemoteException
 *  android.os.UserHandle
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Telephony
 *  android.provider.Telephony$Sms
 *  android.provider.Telephony$Sms$Sent
 *  android.service.carrier.ICarrierMessagingCallback
 *  android.service.carrier.ICarrierMessagingCallback$Stub
 *  android.service.carrier.ICarrierMessagingService
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.TelephonyManager
 *  android.text.Html
 *  android.text.TextUtils
 *  android.util.EventLog
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.TextView
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.GsmAlphabet$TextEncodingDetails
 *  com.android.internal.telephony.SmsApplication
 *  com.android.internal.telephony.SmsHeader
 *  com.android.internal.telephony.SmsHeader$ConcatRef
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.SmsMessageBase$SubmitPduBase
 *  com.android.internal.telephony.cdma.SmsMessage
 *  com.android.internal.telephony.cdma.sms.UserData
 *  com.android.internal.telephony.gsm.SmsMessage
 *  com.android.internal.telephony.gsm.SmsMessage$SubmitPdu
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.sqlite.SqliteWrapper;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.provider.Telephony;
import android.service.carrier.ICarrierMessagingCallback;
import android.service.carrier.ICarrierMessagingService;
import android.telephony.CarrierConfigManager;
import android.telephony.CarrierMessagingServiceManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.AsyncEmergencyContactNotifier;
import com.android.internal.telephony.CarrierSmsUtils;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SmsApplication;
import com.android.internal.telephony.SmsDispatchersController;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.SmsResponse;
import com.android.internal.telephony.SmsUsageMonitor;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.cdma.SmsMessage;
import com.android.internal.telephony.cdma.sms.UserData;
import com.android.internal.telephony.gsm.SmsMessage;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SMSDispatcher
extends Handler {
    static final boolean DBG = false;
    private static final int EVENT_CONFIRM_SEND_TO_POSSIBLE_PREMIUM_SHORT_CODE = 8;
    private static final int EVENT_CONFIRM_SEND_TO_PREMIUM_SHORT_CODE = 9;
    protected static final int EVENT_GET_IMS_SERVICE = 16;
    protected static final int EVENT_HANDLE_STATUS_REPORT = 10;
    protected static final int EVENT_ICC_CHANGED = 15;
    protected static final int EVENT_NEW_ICC_SMS = 14;
    static final int EVENT_SEND_CONFIRMED_SMS = 5;
    private static final int EVENT_SEND_LIMIT_REACHED_CONFIRMATION = 4;
    private static final int EVENT_SEND_RETRY = 3;
    protected static final int EVENT_SEND_SMS_COMPLETE = 2;
    static final int EVENT_STOP_SENDING = 7;
    protected static final String MAP_KEY_DATA = "data";
    protected static final String MAP_KEY_DEST_ADDR = "destAddr";
    protected static final String MAP_KEY_DEST_PORT = "destPort";
    protected static final String MAP_KEY_PDU = "pdu";
    protected static final String MAP_KEY_SC_ADDR = "scAddr";
    protected static final String MAP_KEY_SMSC = "smsc";
    protected static final String MAP_KEY_TEXT = "text";
    private static final int MAX_SEND_RETRIES = 3;
    private static final int MO_MSG_QUEUE_LIMIT = 5;
    private static final int PREMIUM_RULE_USE_BOTH = 3;
    private static final int PREMIUM_RULE_USE_NETWORK = 2;
    private static final int PREMIUM_RULE_USE_SIM = 1;
    private static final String SEND_NEXT_MSG_EXTRA = "SendNextMsg";
    private static final int SEND_RETRY_DELAY = 2000;
    private static final int SINGLE_PART_SMS = 1;
    static final String TAG = "SMSDispatcher";
    private static int sConcatenatedRef = new Random().nextInt(256);
    @UnsupportedAppUsage
    protected final ArrayList<SmsTracker> deliveryPendingList = new ArrayList();
    @UnsupportedAppUsage
    protected final CommandsInterface mCi;
    @UnsupportedAppUsage
    protected final Context mContext;
    private int mPendingTrackerCount;
    @UnsupportedAppUsage
    protected Phone mPhone;
    private final AtomicInteger mPremiumSmsRule = new AtomicInteger(1);
    @UnsupportedAppUsage
    protected final ContentResolver mResolver;
    private final SettingsObserver mSettingsObserver;
    protected boolean mSmsCapable = true;
    protected SmsDispatchersController mSmsDispatchersController;
    protected boolean mSmsSendDisabled;
    @UnsupportedAppUsage
    protected final TelephonyManager mTelephonyManager;

    protected SMSDispatcher(Phone object, SmsDispatchersController smsDispatchersController) {
        this.mPhone = object;
        this.mSmsDispatchersController = smsDispatchersController;
        this.mContext = ((Phone)object).getContext();
        this.mResolver = this.mContext.getContentResolver();
        this.mCi = ((Phone)object).mCi;
        this.mTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
        this.mSettingsObserver = new SettingsObserver(this, this.mPremiumSmsRule, this.mContext);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor((String)"sms_short_code_rule"), false, (ContentObserver)this.mSettingsObserver);
        this.mSmsCapable = this.mContext.getResources().getBoolean(17891521);
        this.mSmsSendDisabled = this.mTelephonyManager.getSmsSendCapableForPhone(this.mPhone.getPhoneId(), this.mSmsCapable) ^ true;
        object = new StringBuilder();
        ((StringBuilder)object).append("SMSDispatcher: ctor mSmsCapable=");
        ((StringBuilder)object).append(this.mSmsCapable);
        ((StringBuilder)object).append(" format=");
        ((StringBuilder)object).append(this.getFormat());
        ((StringBuilder)object).append(" mSmsSendDisabled=");
        ((StringBuilder)object).append(this.mSmsSendDisabled);
        Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    private void checkCallerIsPhoneOrCarrierApp() {
        int n = Binder.getCallingUid();
        if (UserHandle.getAppId((int)n) != 1001 && n != 0) {
            try {
                if (UserHandle.isSameApp((int)this.mContext.getPackageManager().getApplicationInfo((String)this.getCarrierAppPackageName(), (int)0).uid, (int)Binder.getCallingUid())) {
                    return;
                }
                SecurityException securityException = new SecurityException("Caller is not phone or carrier app!");
                throw securityException;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                throw new SecurityException("Caller is not phone or carrier app!");
            }
        }
    }

    private boolean denyIfQueueLimitReached(SmsTracker smsTracker) {
        int n = this.mPendingTrackerCount;
        if (n >= 5) {
            Rlog.e((String)TAG, (String)"Denied because queue limit reached");
            smsTracker.onFailed(this.mContext, 5, 0);
            return true;
        }
        this.mPendingTrackerCount = n + 1;
        return false;
    }

    private CharSequence getAppLabel(String string, int n) {
        Object object = this.mContext.getPackageManager();
        try {
            object = object.getApplicationInfoAsUser(string, 0, n).loadSafeLabel(object, 500.0f, 5);
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PackageManager Name Not Found for package ");
            stringBuilder.append(string);
            Rlog.e((String)TAG, (String)stringBuilder.toString());
            return string;
        }
    }

    @UnsupportedAppUsage
    private String getMultipartMessageText(ArrayList<String> object) {
        StringBuilder stringBuilder = new StringBuilder();
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            if (string == null) continue;
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private SmsTracker getNewSubmitPduTracker(String string, String hashMap, String string2, String string3, SmsHeader smsHeader, int n, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, AtomicInteger atomicInteger, AtomicBoolean atomicBoolean, Uri uri, String string4, int n2, boolean bl2, int n3) {
        if (this.isCdmaMo()) {
            StringBuilder stringBuilder;
            UserData userData = new UserData();
            userData.payloadStr = string3;
            userData.userDataHeader = smsHeader;
            if (n == 1) {
                n = this.isAscii7bitSupportedForLongMessage() ? 2 : 9;
                userData.msgEncoding = n;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Message encoding for proper 7 bit: ");
                stringBuilder.append(userData.msgEncoding);
                Rlog.d((String)TAG, (String)stringBuilder.toString());
            } else {
                userData.msgEncoding = 4;
            }
            userData.msgEncodingSet = true;
            boolean bl3 = pendingIntent2 != null && bl;
            stringBuilder = SmsMessage.getSubmitPdu((String)((Object)hashMap), (UserData)userData, (boolean)bl3, (int)n2);
            if (stringBuilder != null) {
                hashMap = this.getSmsTrackerMap((String)((Object)hashMap), string2, string3, (SmsMessageBase.SubmitPduBase)stringBuilder);
                string2 = this.getFormat();
                bl = !bl || bl2;
                return this.getSmsTracker(string, hashMap, pendingIntent, pendingIntent2, string2, atomicInteger, atomicBoolean, uri, smsHeader, bl, string4, true, true, n2, n3, false);
            }
            Rlog.e((String)TAG, (String)"CdmaSMSDispatcher.getNewSubmitPduTracker(): getSubmitPdu() returned null");
            return null;
        }
        boolean bl4 = pendingIntent2 != null;
        SmsMessage.SubmitPdu submitPdu = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu((String)string2, (String)((Object)hashMap), (String)string3, (boolean)bl4, (byte[])SmsHeader.toByteArray((SmsHeader)smsHeader), (int)n, (int)smsHeader.languageTable, (int)smsHeader.languageShiftTable, (int)n3);
        if (submitPdu != null) {
            hashMap = this.getSmsTrackerMap((String)((Object)hashMap), string2, string3, (SmsMessageBase.SubmitPduBase)submitPdu);
            string2 = this.getFormat();
            bl = !bl || bl2;
            return this.getSmsTracker(string, hashMap, pendingIntent, pendingIntent2, string2, atomicInteger, atomicBoolean, uri, smsHeader, bl, string4, true, false, n2, n3, false);
        }
        Rlog.e((String)TAG, (String)"GsmSMSDispatcher.getNewSubmitPduTracker(): getSubmitPdu() returned null");
        return null;
    }

    @UnsupportedAppUsage
    protected static int getNextConcatenatedRef() {
        return ++sConcatenatedRef;
    }

    protected static int getNotInServiceError(int n) {
        if (n == 3) {
            return 2;
        }
        return 4;
    }

    private static int getSendSmsFlag(PendingIntent pendingIntent) {
        return pendingIntent != null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static void handleNotInService(int var0, PendingIntent var1_1) {
        if (var1_1 == null) return;
        if (var0 != 3) ** GOTO lbl6
        try {
            var1_1.send(2);
            return;
lbl6: // 1 sources:
            var1_1.send(4);
            return;
        }
        catch (PendingIntent.CanceledException var1_2) {
            Rlog.e((String)"SMSDispatcher", (String)"Failed to send result");
        }
    }

    private boolean isAscii7bitSupportedForLongMessage() {
        long l;
        block4 : {
            l = Binder.clearCallingIdentity();
            PersistableBundle persistableBundle = ((CarrierConfigManager)this.mContext.getSystemService("carrier_config")).getConfigForSubId(this.mPhone.getSubId());
            if (persistableBundle == null) break block4;
            boolean bl = persistableBundle.getBoolean("ascii_7_bit_support_for_long_message_bool");
            return bl;
        }
        Binder.restoreCallingIdentity((long)l);
        return false;
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    @UnsupportedAppUsage
    private void processSendSmsResponse(SmsTracker smsTracker, int n, int n2) {
        if (smsTracker == null) {
            Rlog.e((String)TAG, (String)"processSendSmsResponse: null tracker");
            return;
        }
        Object object = new SmsResponse(n2, null, -1);
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown result ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" Retry on carrier network.");
                    Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
                    this.sendSubmitPdu(smsTracker);
                } else {
                    Rlog.d((String)TAG, (String)"Sending SMS by IP failed.");
                    this.sendMessage(this.obtainMessage(2, (Object)new AsyncResult((Object)smsTracker, object, (Throwable)new CommandException(CommandException.Error.GENERIC_FAILURE))));
                }
            } else {
                Rlog.d((String)TAG, (String)"Sending SMS by IP failed. Retry on carrier network.");
                this.sendSubmitPdu(smsTracker);
            }
        } else {
            Rlog.d((String)TAG, (String)"Sending SMS by IP succeeded.");
            this.sendMessage(this.obtainMessage(2, (Object)new AsyncResult((Object)smsTracker, object, null)));
        }
    }

    @UnsupportedAppUsage
    private void sendMultipartSms(SmsTracker object) {
        Cloneable cloneable = ((SmsTracker)object).getData();
        String string = (String)cloneable.get("destination");
        String string2 = (String)cloneable.get("scaddress");
        ArrayList arrayList = (ArrayList)cloneable.get("parts");
        ArrayList arrayList2 = (ArrayList)cloneable.get("sentIntents");
        cloneable = (ArrayList)cloneable.get("deliveryIntents");
        int n = this.mPhone.getServiceState().getState();
        if (!this.isIms() && n != 0) {
            int n2 = arrayList.size();
            for (int i = 0; i < n2; ++i) {
                arrayList = null;
                object = arrayList;
                if (arrayList2 != null) {
                    object = arrayList;
                    if (arrayList2.size() > i) {
                        object = (PendingIntent)arrayList2.get(i);
                    }
                }
                SMSDispatcher.handleNotInService(n, (PendingIntent)object);
            }
            return;
        }
        this.sendMultipartText(string, string2, arrayList, arrayList2, (ArrayList<PendingIntent>)cloneable, null, null, ((SmsTracker)object).mPersistMessage, ((SmsTracker)object).mPriority, ((SmsTracker)object).mExpectMore, ((SmsTracker)object).mValidityPeriod);
    }

    private boolean sendSmsByCarrierApp(boolean bl, SmsTracker object) {
        String string = this.getCarrierAppPackageName();
        if (string != null) {
            Rlog.d((String)TAG, (String)"Found carrier package.");
            object = bl ? new DataSmsSender((SmsTracker)object) : new TextSmsSender((SmsTracker)object);
            ((SmsSender)object).sendSmsByCarrierApp(string, new SmsSenderCallback((SmsSender)object));
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    private void sendSubmitPdu(SmsTracker smsTracker) {
        if (this.shouldBlockSmsForEcbm()) {
            Rlog.d((String)TAG, (String)"Block SMS in Emergency Callback mode");
            smsTracker.onFailed(this.mContext, 4, 0);
        } else {
            this.sendRawPdu(smsTracker);
        }
    }

    private void triggerSentIntentForFailure(PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(1);
            }
            catch (PendingIntent.CanceledException canceledException) {
                Rlog.e((String)TAG, (String)"Intent has been canceled!");
            }
        }
    }

    private void triggerSentIntentForFailure(List<PendingIntent> object) {
        if (object == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            this.triggerSentIntentForFailure((PendingIntent)object.next());
        }
    }

    @UnsupportedAppUsage
    protected abstract GsmAlphabet.TextEncodingDetails calculateLength(CharSequence var1, boolean var2);

    boolean checkDestination(SmsTracker smsTracker) {
        block11 : {
            int n;
            int n2;
            int n3;
            block16 : {
                String string;
                block18 : {
                    block17 : {
                        String string2;
                        block15 : {
                            block12 : {
                                block14 : {
                                    block13 : {
                                        if (this.mContext.checkCallingOrSelfPermission("android.permission.SEND_SMS_NO_CONFIRMATION") == 0 || smsTracker.mIsForVvm) break block11;
                                        n = this.mPremiumSmsRule.get();
                                        n2 = 0;
                                        if (n != 1 && n != 3) break block12;
                                        string2 = this.mTelephonyManager.getSimCountryIsoForPhone(this.mPhone.getPhoneId());
                                        if (string2 == null) break block13;
                                        string = string2;
                                        if (string2.length() == 2) break block14;
                                    }
                                    Rlog.e((String)"SMSDispatcher", (String)"Can't get SIM country Iso: trying network country Iso");
                                    string = this.mTelephonyManager.getNetworkCountryIsoForPhone(this.mPhone.getPhoneId());
                                }
                                n2 = this.mSmsDispatchersController.getUsageMonitor().checkDestination(smsTracker.mDestAddress, string);
                            }
                            if (n == 2) break block15;
                            n3 = n2;
                            if (n != 3) break block16;
                        }
                        if ((string2 = this.mTelephonyManager.getNetworkCountryIsoForPhone(this.mPhone.getPhoneId())) == null) break block17;
                        string = string2;
                        if (string2.length() == 2) break block18;
                    }
                    Rlog.e((String)"SMSDispatcher", (String)"Can't get Network country Iso: trying SIM country Iso");
                    string = this.mTelephonyManager.getSimCountryIsoForPhone(this.mPhone.getPhoneId());
                }
                n3 = SmsUsageMonitor.mergeShortCodeCategories(n2, this.mSmsDispatchersController.getUsageMonitor().checkDestination(smsTracker.mDestAddress, string));
            }
            if (n3 != 0 && n3 != 1 && n3 != 2) {
                if (Settings.Global.getInt((ContentResolver)this.mResolver, (String)"device_provisioned", (int)0) == 0) {
                    Rlog.e((String)"SMSDispatcher", (String)"Can't send premium sms during Setup Wizard");
                    return false;
                }
                n2 = n = this.mSmsDispatchersController.getUsageMonitor().getPremiumSmsPermission(smsTracker.getAppPackageName());
                if (n == 0) {
                    n2 = 1;
                }
                if (n2 != 2) {
                    if (n2 != 3) {
                        n2 = n3 == 3 ? 8 : 9;
                        this.sendMessage(this.obtainMessage(n2, (Object)smsTracker));
                        return false;
                    }
                    Rlog.d((String)"SMSDispatcher", (String)"User approved this app to send to premium SMS");
                    return true;
                }
                Rlog.w((String)"SMSDispatcher", (String)"User denied this app from sending to premium SMS");
                smsTracker = this.obtainMessage(7, (Object)smsTracker);
                ((Message)smsTracker).arg1 = 0;
                ((Message)smsTracker).arg2 = 1;
                this.sendMessage((Message)smsTracker);
                return false;
            }
            return true;
        }
        return true;
    }

    @UnsupportedAppUsage
    public void dispose() {
        this.mContext.getContentResolver().unregisterContentObserver((ContentObserver)this.mSettingsObserver);
    }

    @UnsupportedAppUsage
    protected String getCarrierAppPackageName() {
        Object object = UiccController.getInstance().getUiccCard(this.mPhone.getPhoneId());
        if (object == null) {
            return null;
        }
        if ((object = ((UiccCard)object).getCarrierPackageNamesForIntent(this.mContext.getPackageManager(), new Intent("android.service.carrier.CarrierMessagingService"))) != null && object.size() == 1) {
            return (String)object.get(0);
        }
        return CarrierSmsUtils.getCarrierImsPackageForIntent(this.mContext, this.mPhone, new Intent("android.service.carrier.CarrierMessagingService"));
    }

    protected abstract String getFormat();

    protected SmsTracker getSmsTracker(String string, HashMap<String, Object> hashMap, PendingIntent pendingIntent, PendingIntent pendingIntent2, String string2, Uri uri, boolean bl, String string3, boolean bl2, boolean bl3, int n, int n2, boolean bl4) {
        return this.getSmsTracker(string, hashMap, pendingIntent, pendingIntent2, string2, null, null, uri, null, bl, string3, bl2, bl3, n, n2, bl4);
    }

    protected SmsTracker getSmsTracker(String string, HashMap<String, Object> hashMap, PendingIntent pendingIntent, PendingIntent pendingIntent2, String string2, Uri uri, boolean bl, String string3, boolean bl2, boolean bl3, boolean bl4) {
        return this.getSmsTracker(string, hashMap, pendingIntent, pendingIntent2, string2, null, null, uri, null, bl, string3, bl2, bl3, -1, -1, bl4);
    }

    protected SmsTracker getSmsTracker(String string, HashMap<String, Object> hashMap, PendingIntent pendingIntent, PendingIntent pendingIntent2, String string2, AtomicInteger atomicInteger, AtomicBoolean atomicBoolean, Uri uri, SmsHeader smsHeader, boolean bl, String string3, boolean bl2, boolean bl3, int n, int n2, boolean bl4) {
        PackageManager packageManager = this.mContext.getPackageManager();
        int n3 = UserHandle.getCallingUserId();
        try {
            string = packageManager.getPackageInfoAsUser(string, 64, n3);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            string = null;
        }
        return new SmsTracker(hashMap, pendingIntent, pendingIntent2, (PackageInfo)string, PhoneNumberUtils.extractNetworkPortion((String)((String)hashMap.get("destAddr"))), string2, atomicInteger, atomicBoolean, uri, smsHeader, bl, string3, this.getSubId(), bl2, bl3, n3, n, n2, bl4);
    }

    protected HashMap<String, Object> getSmsTrackerMap(String string, String string2, int n, byte[] arrby, SmsMessageBase.SubmitPduBase submitPduBase) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("destAddr", string);
        hashMap.put("scAddr", string2);
        hashMap.put("destPort", n);
        hashMap.put("data", arrby);
        hashMap.put("smsc", submitPduBase.encodedScAddress);
        hashMap.put("pdu", submitPduBase.encodedMessage);
        return hashMap;
    }

    protected HashMap<String, Object> getSmsTrackerMap(String string, String string2, String string3, SmsMessageBase.SubmitPduBase submitPduBase) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("destAddr", string);
        hashMap.put("scAddr", string2);
        hashMap.put("text", string3);
        hashMap.put("smsc", submitPduBase.encodedScAddress);
        hashMap.put("pdu", submitPduBase.encodedMessage);
        return hashMap;
    }

    @UnsupportedAppUsage
    protected int getSubId() {
        return SubscriptionController.getInstance().getSubIdUsingPhoneId(this.mPhone.getPhoneId());
    }

    protected abstract SmsMessageBase.SubmitPduBase getSubmitPdu(String var1, String var2, int var3, byte[] var4, boolean var5);

    protected abstract SmsMessageBase.SubmitPduBase getSubmitPdu(String var1, String var2, String var3, boolean var4, SmsHeader var5, int var6, int var7);

    @UnsupportedAppUsage
    protected void handleConfirmShortCode(boolean bl, SmsTracker object) {
        if (this.denyIfQueueLimitReached((SmsTracker)object)) {
            return;
        }
        int n = bl ? 17041046 : 17041052;
        CharSequence charSequence = this.getAppLabel(((SmsTracker)object).getAppPackageName(), ((SmsTracker)object).mUserId);
        Resources resources = Resources.getSystem();
        charSequence = Html.fromHtml((String)resources.getString(17041050, new Object[]{charSequence, ((SmsTracker)object).mDestAddress}));
        View view = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(17367300, null);
        object = new ConfirmDialogListener((SmsTracker)object, (TextView)view.findViewById(16909380), 0);
        ((TextView)view.findViewById(16909375)).setText(charSequence);
        ((TextView)((ViewGroup)view.findViewById(16909376)).findViewById(16909377)).setText(n);
        ((CheckBox)view.findViewById(16909378)).setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)object);
        resources = new AlertDialog.Builder(this.mContext).setView(view).setPositiveButton((CharSequence)resources.getString(17041047), (DialogInterface.OnClickListener)object).setNegativeButton((CharSequence)resources.getString(17041049), (DialogInterface.OnClickListener)object).setOnCancelListener((DialogInterface.OnCancelListener)object).create();
        resources.getWindow().setType(2003);
        resources.show();
        ((ConfirmDialogListener)object).setPositiveButton(resources.getButton(-1));
        ((ConfirmDialogListener)object).setNegativeButton(resources.getButton(-2));
    }

    public void handleMessage(Message object) {
        switch (((Message)object).what) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleMessage() ignoring message of unexpected type ");
                stringBuilder.append(((Message)object).what);
                Rlog.e((String)"SMSDispatcher", (String)stringBuilder.toString());
                break;
            }
            case 10: {
                this.handleStatusReport(((Message)object).obj);
                break;
            }
            case 9: {
                this.handleConfirmShortCode(true, (SmsTracker)((Message)object).obj);
                break;
            }
            case 8: {
                this.handleConfirmShortCode(false, (SmsTracker)((Message)object).obj);
                break;
            }
            case 7: {
                SmsTracker smsTracker = (SmsTracker)((Message)object).obj;
                if (((Message)object).arg1 == 0) {
                    if (((Message)object).arg2 == 1) {
                        smsTracker.onFailed(this.mContext, 8, 0);
                        Rlog.d((String)"SMSDispatcher", (String)"SMSDispatcher: EVENT_STOP_SENDING - sending SHORT_CODE_NEVER_ALLOWED error code.");
                    } else {
                        smsTracker.onFailed(this.mContext, 7, 0);
                        Rlog.d((String)"SMSDispatcher", (String)"SMSDispatcher: EVENT_STOP_SENDING - sending SHORT_CODE_NOT_ALLOWED error code.");
                    }
                } else if (((Message)object).arg1 == 1) {
                    smsTracker.onFailed(this.mContext, 5, 0);
                    Rlog.d((String)"SMSDispatcher", (String)"SMSDispatcher: EVENT_STOP_SENDING - sending LIMIT_EXCEEDED error code.");
                } else {
                    Rlog.e((String)"SMSDispatcher", (String)"SMSDispatcher: EVENT_STOP_SENDING - unexpected cases.");
                }
                --this.mPendingTrackerCount;
                break;
            }
            case 5: {
                object = (SmsTracker)((Message)object).obj;
                if (((SmsTracker)object).isMultipart()) {
                    this.sendMultipartSms((SmsTracker)object);
                } else {
                    ((SmsTracker)object).mExpectMore = this.mPendingTrackerCount > 1;
                    this.sendSms((SmsTracker)object);
                }
                --this.mPendingTrackerCount;
                break;
            }
            case 4: {
                this.handleReachSentLimit((SmsTracker)((Message)object).obj);
                break;
            }
            case 3: {
                Rlog.d((String)"SMSDispatcher", (String)"SMS retry..");
                this.sendRetrySms((SmsTracker)((Message)object).obj);
                break;
            }
            case 2: {
                this.handleSendComplete((AsyncResult)((Message)object).obj);
            }
        }
    }

    protected void handleReachSentLimit(SmsTracker object) {
        if (this.denyIfQueueLimitReached((SmsTracker)object)) {
            return;
        }
        CharSequence charSequence = this.getAppLabel(((SmsTracker)object).getAppPackageName(), ((SmsTracker)object).mUserId);
        Resources resources = Resources.getSystem();
        charSequence = Html.fromHtml((String)resources.getString(17041041, new Object[]{charSequence}));
        object = new ConfirmDialogListener((SmsTracker)object, null, 1);
        object = new AlertDialog.Builder(this.mContext).setTitle(17041043).setIcon(17301642).setMessage(charSequence).setPositiveButton((CharSequence)resources.getString(17041044), (DialogInterface.OnClickListener)object).setNegativeButton((CharSequence)resources.getString(17041042), (DialogInterface.OnClickListener)object).setOnCancelListener((DialogInterface.OnCancelListener)object).create();
        object.getWindow().setType(2003);
        object.show();
    }

    protected void handleSendComplete(AsyncResult asyncResult) {
        SmsTracker smsTracker = (SmsTracker)asyncResult.userObj;
        Object object = smsTracker.mSentIntent;
        if (asyncResult.result != null) {
            smsTracker.mMessageRef = ((SmsResponse)asyncResult.result).mMessageRef;
        } else {
            Rlog.d((String)"SMSDispatcher", (String)"SmsResponse was null");
        }
        if (asyncResult.exception == null) {
            if (smsTracker.mDeliveryIntent != null) {
                this.deliveryPendingList.add(smsTracker);
            }
            smsTracker.onSent(this.mContext);
            this.mPhone.notifySmsSent(smsTracker.mDestAddress);
        } else {
            int n = this.mPhone.getServiceState().getState();
            if (smsTracker.mImsRetry > 0 && n != 0) {
                smsTracker.mRetryCount = 3;
                object = new StringBuilder();
                ((StringBuilder)object).append("handleSendComplete: Skipping retry:  isIms()=");
                ((StringBuilder)object).append(this.isIms());
                ((StringBuilder)object).append(" mRetryCount=");
                ((StringBuilder)object).append(smsTracker.mRetryCount);
                ((StringBuilder)object).append(" mImsRetry=");
                ((StringBuilder)object).append(smsTracker.mImsRetry);
                ((StringBuilder)object).append(" mMessageRef=");
                ((StringBuilder)object).append(smsTracker.mMessageRef);
                ((StringBuilder)object).append(" SS= ");
                ((StringBuilder)object).append(this.mPhone.getServiceState().getState());
                Rlog.d((String)"SMSDispatcher", (String)((StringBuilder)object).toString());
            }
            if (!this.isIms() && n != 0) {
                smsTracker.onFailed(this.mContext, SMSDispatcher.getNotInServiceError(n), 0);
            } else if (((CommandException)asyncResult.exception).getCommandError() == CommandException.Error.SMS_FAIL_RETRY && smsTracker.mRetryCount < 3) {
                ++smsTracker.mRetryCount;
                this.sendMessageDelayed(this.obtainMessage(3, (Object)smsTracker), 2000L);
            } else {
                n = 0;
                if (asyncResult.result != null) {
                    n = ((SmsResponse)asyncResult.result).mErrorCode;
                }
                int n2 = 1;
                if (((CommandException)asyncResult.exception).getCommandError() == CommandException.Error.FDN_CHECK_FAILURE) {
                    n2 = 6;
                }
                smsTracker.onFailed(this.mContext, n2, n);
            }
        }
    }

    protected void handleStatusReport(Object object) {
        Rlog.d((String)"SMSDispatcher", (String)"handleStatusReport() called with no subclass.");
    }

    protected boolean isCdmaMo() {
        return this.mSmsDispatchersController.isCdmaMo();
    }

    public boolean isIms() {
        SmsDispatchersController smsDispatchersController = this.mSmsDispatchersController;
        if (smsDispatchersController != null) {
            return smsDispatchersController.isIms();
        }
        Rlog.e((String)"SMSDispatcher", (String)"mSmsDispatchersController  is null");
        return false;
    }

    @UnsupportedAppUsage
    protected void sendData(String object, String object2, String string, int n, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        boolean bl2 = pendingIntent2 != null;
        SmsMessageBase.SubmitPduBase submitPduBase = this.getSubmitPdu(string, (String)object2, n, arrby, bl2);
        if (submitPduBase != null) {
            if (!this.sendSmsByCarrierApp(true, (SmsTracker)(object = this.getSmsTracker((String)object, (HashMap<String, Object>)(object2 = this.getSmsTrackerMap((String)object2, string, n, arrby, submitPduBase)), pendingIntent, pendingIntent2, this.getFormat(), null, false, null, false, true, bl)))) {
                this.sendSubmitPdu((SmsTracker)object);
            }
        } else {
            Rlog.e((String)"SMSDispatcher", (String)"SMSDispatcher.sendData(): getSubmitPdu() returned null");
            this.triggerSentIntentForFailure(pendingIntent);
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    public void sendMultipartText(String string, String object, ArrayList<String> pendingIntent, ArrayList<PendingIntent> arrayList, ArrayList<PendingIntent> arrayList2, Uri uri, String string2, boolean bl, int n, boolean bl2, int n2) {
        Object object2;
        SmsHeader smsHeader;
        int n3;
        int n4;
        SmsHeader smsHeader2 = this;
        Object object3 = pendingIntent;
        String string3 = smsHeader2.getMultipartMessageText((ArrayList<String>)object3);
        int n5 = SMSDispatcher.getNextConcatenatedRef() & 255;
        int n6 = pendingIntent.size();
        if (n6 < 1) {
            smsHeader2.triggerSentIntentForFailure(arrayList);
            return;
        }
        GsmAlphabet.TextEncodingDetails[] arrtextEncodingDetails = new GsmAlphabet.TextEncodingDetails[n6];
        int n7 = 0;
        for (n3 = 0; n3 < n6; ++n3) {
            block11 : {
                block12 : {
                    object2 = smsHeader2.calculateLength(object3.get(n3), false);
                    n4 = n7;
                    if (n7 == ((GsmAlphabet.TextEncodingDetails)object2).codeUnitSize) break block11;
                    if (n7 == 0) break block12;
                    n4 = n7;
                    if (n7 != 1) break block11;
                }
                n4 = ((GsmAlphabet.TextEncodingDetails)object2).codeUnitSize;
            }
            arrtextEncodingDetails[n3] = object2;
            n7 = n4;
        }
        smsHeader2 = new SmsTracker[n6];
        AtomicInteger atomicInteger = new AtomicInteger(n6);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        int n8 = 0;
        n4 = n5;
        n3 = n6;
        n6 = n8;
        do {
            smsHeader = this;
            object3 = arrayList;
            object2 = arrayList2;
            if (n6 >= n3) break;
            Object object4 = new SmsHeader.ConcatRef();
            object4.refNumber = n4;
            object4.seqNumber = n6 + 1;
            object4.msgCount = n3;
            object4.isEightBits = true;
            smsHeader = new SmsHeader();
            smsHeader.concatRef = object4;
            if (n7 == 1) {
                smsHeader.languageTable = arrtextEncodingDetails[n6].languageTable;
                smsHeader.languageShiftTable = arrtextEncodingDetails[n6].languageShiftTable;
            }
            object3 = object3 != null && arrayList.size() > n6 ? (PendingIntent)object3.get(n6) : null;
            object2 = object2 != null && arrayList2.size() > n6 ? (PendingIntent)((ArrayList)object2).get(n6) : null;
            object4 = (String)pendingIntent.get(n6);
            boolean bl3 = n6 == n3 - 1;
            smsHeader2[n6] = SMSDispatcher.super.getNewSubmitPduTracker(string2, string, (String)object, (String)object4, smsHeader, n7, (PendingIntent)object3, (PendingIntent)object2, bl3, atomicInteger, atomicBoolean, uri, string3, n, bl2, n2);
            if (smsHeader2[n6] == null) {
                SMSDispatcher.super.triggerSentIntentForFailure(arrayList);
                return;
            }
            ((SmsTracker)smsHeader2[n6]).mPersistMessage = bl;
            ++n6;
        } while (true);
        string = this.getCarrierAppPackageName();
        if (string != null) {
            Rlog.d((String)"SMSDispatcher", (String)"Found carrier package.");
            object = (SMSDispatcher)smsHeader.new MultipartSmsSender((ArrayList<String>)pendingIntent, (SmsTracker[])smsHeader2);
            ((MultipartSmsSender)object).sendSmsByCarrierApp(string, (SMSDispatcher)smsHeader.new MultipartSmsSenderCallback((MultipartSmsSender)object));
        } else {
            Rlog.v((String)"SMSDispatcher", (String)"No carrier package.");
            n2 = ((SmsTracker[])smsHeader2).length;
            for (n = 0; n < n2; ++n) {
                SMSDispatcher.super.sendSubmitPdu(smsHeader2[n]);
            }
        }
    }

    @VisibleForTesting
    public void sendRawPdu(SmsTracker smsTracker) {
        block5 : {
            block6 : {
                PackageManager packageManager = (PackageManager)smsTracker.getData().get("pdu");
                if (this.mSmsSendDisabled) {
                    Rlog.e((String)"SMSDispatcher", (String)"Device does not support sending sms.");
                    smsTracker.onFailed(this.mContext, 4, 0);
                    return;
                }
                if (packageManager == null) {
                    Rlog.e((String)"SMSDispatcher", (String)"Empty PDU");
                    smsTracker.onFailed(this.mContext, 3, 0);
                    return;
                }
                String string = smsTracker.getAppPackageName();
                packageManager = this.mContext.getPackageManager();
                try {
                    packageManager = packageManager.getPackageInfoAsUser(string, 64, smsTracker.mUserId);
                    if (!this.checkDestination(smsTracker)) break block5;
                    if (this.mSmsDispatchersController.getUsageMonitor().check(packageManager.packageName, 1)) break block6;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    Rlog.e((String)"SMSDispatcher", (String)"Can't get calling app package info: refusing to send SMS");
                    smsTracker.onFailed(this.mContext, 1, 0);
                    return;
                }
                this.sendMessage(this.obtainMessage(4, (Object)smsTracker));
                return;
            }
            this.sendSms(smsTracker);
        }
        if (PhoneNumberUtils.isLocalEmergencyNumber((Context)this.mContext, (String)smsTracker.mDestAddress)) {
            new AsyncEmergencyContactNotifier(this.mContext).execute((Object[])new Void[0]);
        }
    }

    public void sendRetrySms(SmsTracker object) {
        SmsDispatchersController smsDispatchersController = this.mSmsDispatchersController;
        if (smsDispatchersController != null) {
            smsDispatchersController.sendRetrySms((SmsTracker)object);
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append((Object)((Object)this.mSmsDispatchersController));
            ((StringBuilder)object).append(" is null. Retry failed");
            Rlog.e((String)"SMSDispatcher", (String)((StringBuilder)object).toString());
        }
    }

    @UnsupportedAppUsage
    protected abstract void sendSms(SmsTracker var1);

    public void sendText(String object, String string, String string2, PendingIntent pendingIntent, PendingIntent pendingIntent2, Uri uri, String string3, boolean bl, int n, boolean bl2, int n2, boolean bl3) {
        Rlog.d((String)"SMSDispatcher", (String)"sendText");
        boolean bl4 = pendingIntent2 != null;
        SmsMessageBase.SubmitPduBase submitPduBase = this.getSubmitPdu(string, (String)object, string2, bl4, null, n, n2);
        if (submitPduBase != null) {
            object = this.getSmsTrackerMap((String)object, string, string2, submitPduBase);
            if (!this.sendSmsByCarrierApp(false, (SmsTracker)(object = this.getSmsTracker(string3, (HashMap<String, Object>)object, pendingIntent, pendingIntent2, this.getFormat(), uri, bl2, string2, true, bl, n, n2, bl3)))) {
                this.sendSubmitPdu((SmsTracker)object);
            }
        } else {
            Rlog.e((String)"SMSDispatcher", (String)"SmsDispatcher.sendText(): getSubmitPdu() returned null");
            this.triggerSentIntentForFailure(pendingIntent);
        }
    }

    protected abstract boolean shouldBlockSmsForEcbm();

    private final class ConfirmDialogListener
    implements DialogInterface.OnClickListener,
    DialogInterface.OnCancelListener,
    CompoundButton.OnCheckedChangeListener {
        private static final int NEVER_ALLOW = 1;
        private static final int RATE_LIMIT = 1;
        private static final int SHORT_CODE_MSG = 0;
        private int mConfirmationType;
        @UnsupportedAppUsage
        private Button mNegativeButton;
        @UnsupportedAppUsage
        private Button mPositiveButton;
        private boolean mRememberChoice;
        @UnsupportedAppUsage
        private final TextView mRememberUndoInstruction;
        private final SmsTracker mTracker;

        ConfirmDialogListener(SmsTracker smsTracker, TextView textView, int n) {
            this.mTracker = smsTracker;
            this.mRememberUndoInstruction = textView;
            this.mConfirmationType = n;
        }

        public void onCancel(DialogInterface dialogInterface) {
            Rlog.d((String)SMSDispatcher.TAG, (String)"dialog dismissed: don't send SMS");
            dialogInterface = SMSDispatcher.this.obtainMessage(7, (Object)this.mTracker);
            dialogInterface.arg1 = this.mConfirmationType;
            SMSDispatcher.this.sendMessage((Message)dialogInterface);
        }

        public void onCheckedChanged(CompoundButton object, boolean bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("remember this choice: ");
            ((StringBuilder)object).append(bl);
            Rlog.d((String)SMSDispatcher.TAG, (String)((StringBuilder)object).toString());
            this.mRememberChoice = bl;
            if (bl) {
                this.mPositiveButton.setText(17041048);
                this.mNegativeButton.setText(17041051);
                object = this.mRememberUndoInstruction;
                if (object != null) {
                    object.setText(17041054);
                    this.mRememberUndoInstruction.setPadding(0, 0, 0, 32);
                }
            } else {
                this.mPositiveButton.setText(17041047);
                this.mNegativeButton.setText(17041049);
                object = this.mRememberUndoInstruction;
                if (object != null) {
                    object.setText((CharSequence)"");
                    this.mRememberUndoInstruction.setPadding(0, 0, 0, 0);
                }
            }
        }

        public void onClick(DialogInterface object, int n) {
            int n2 = 1;
            int n3 = 1;
            int n4 = -1;
            if (n == -1) {
                Rlog.d((String)SMSDispatcher.TAG, (String)"CONFIRM sending SMS");
                if (this.mTracker.mAppInfo.applicationInfo != null) {
                    n4 = this.mTracker.mAppInfo.applicationInfo.uid;
                }
                EventLog.writeEvent((int)50128, (int)n4);
                object = SMSDispatcher.this;
                object.sendMessage(object.obtainMessage(5, (Object)this.mTracker));
                if (this.mRememberChoice) {
                    n2 = 3;
                }
            } else if (n == -2) {
                Rlog.d((String)SMSDispatcher.TAG, (String)"DENY sending SMS");
                if (this.mTracker.mAppInfo.applicationInfo != null) {
                    n4 = this.mTracker.mAppInfo.applicationInfo.uid;
                }
                EventLog.writeEvent((int)50125, (int)n4);
                object = SMSDispatcher.this.obtainMessage(7, (Object)this.mTracker);
                object.arg1 = this.mConfirmationType;
                n2 = n3;
                if (this.mRememberChoice) {
                    n2 = 2;
                    object.arg2 = 1;
                }
                SMSDispatcher.this.sendMessage((Message)object);
            }
            SMSDispatcher.this.mSmsDispatchersController.setPremiumSmsPermission(this.mTracker.getAppPackageName(), n2);
        }

        void setNegativeButton(Button button) {
            this.mNegativeButton = button;
        }

        void setPositiveButton(Button button) {
            this.mPositiveButton = button;
        }
    }

    protected final class DataSmsSender
    extends SmsSender {
        public DataSmsSender(SmsTracker smsTracker) {
            super(smsTracker);
        }

        @Override
        protected void onServiceReady(ICarrierMessagingService object) {
            HashMap<String, Object> hashMap = this.mTracker.getData();
            byte[] arrby = (byte[])hashMap.get(SMSDispatcher.MAP_KEY_DATA);
            int n = (Integer)hashMap.get(SMSDispatcher.MAP_KEY_DEST_PORT);
            if (arrby != null) {
                try {
                    object.sendDataSms(arrby, SMSDispatcher.this.getSubId(), this.mTracker.mDestAddress, n, SMSDispatcher.getSendSmsFlag(this.mTracker.mDeliveryIntent), (ICarrierMessagingCallback)this.mSenderCallback);
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Exception sending the SMS: ");
                    ((StringBuilder)object).append((Object)remoteException);
                    Rlog.e((String)SMSDispatcher.TAG, (String)((StringBuilder)object).toString());
                    this.mSenderCallback.onSendSmsComplete(1, 0);
                }
            } else {
                this.mSenderCallback.onSendSmsComplete(1, 0);
            }
        }
    }

    private final class MultipartSmsSender
    extends CarrierMessagingServiceManager {
        private final List<String> mParts;
        private volatile MultipartSmsSenderCallback mSenderCallback;
        public final SmsTracker[] mTrackers;

        MultipartSmsSender(ArrayList<String> arrayList, SmsTracker[] arrsmsTracker) {
            this.mParts = arrayList;
            this.mTrackers = arrsmsTracker;
        }

        @Override
        protected void onServiceReady(ICarrierMessagingService object) {
            try {
                object.sendMultipartTextSms(this.mParts, SMSDispatcher.this.getSubId(), this.mTrackers[0].mDestAddress, SMSDispatcher.getSendSmsFlag(this.mTrackers[0].mDeliveryIntent), (ICarrierMessagingCallback)this.mSenderCallback);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Exception sending the SMS: ");
                ((StringBuilder)object).append((Object)remoteException);
                Rlog.e((String)SMSDispatcher.TAG, (String)((StringBuilder)object).toString());
                this.mSenderCallback.onSendMultipartSmsComplete(1, null);
            }
        }

        @UnsupportedAppUsage
        void sendSmsByCarrierApp(String string, MultipartSmsSenderCallback multipartSmsSenderCallback) {
            this.mSenderCallback = multipartSmsSenderCallback;
            if (!this.bindToCarrierMessagingService(SMSDispatcher.this.mContext, string)) {
                Rlog.e((String)SMSDispatcher.TAG, (String)"bindService() for carrier messaging service failed");
                this.mSenderCallback.onSendMultipartSmsComplete(1, null);
            } else {
                Rlog.d((String)SMSDispatcher.TAG, (String)"bindService() for carrier messaging service succeeded");
            }
        }
    }

    private final class MultipartSmsSenderCallback
    extends ICarrierMessagingCallback.Stub {
        private final MultipartSmsSender mSmsSender;

        MultipartSmsSenderCallback(MultipartSmsSender multipartSmsSender) {
            this.mSmsSender = multipartSmsSender;
        }

        public void onDownloadMmsComplete(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected onDownloadMmsComplete call with result: ");
            stringBuilder.append(n);
            Rlog.e((String)SMSDispatcher.TAG, (String)stringBuilder.toString());
        }

        public void onFilterComplete(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected onFilterComplete call with result: ");
            stringBuilder.append(n);
            Rlog.e((String)SMSDispatcher.TAG, (String)stringBuilder.toString());
        }

        public void onSendMmsComplete(int n, byte[] object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected onSendMmsComplete call with result: ");
            ((StringBuilder)object).append(n);
            Rlog.e((String)SMSDispatcher.TAG, (String)((StringBuilder)object).toString());
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onSendMultipartSmsComplete(int n, int[] arrn) {
            this.mSmsSender.disposeConnection(SMSDispatcher.this.mContext);
            if (this.mSmsSender.mTrackers == null) {
                Rlog.e((String)SMSDispatcher.TAG, (String)"Unexpected onSendMultipartSmsComplete call with null trackers.");
                return;
            }
            SMSDispatcher.this.checkCallerIsPhoneOrCarrierApp();
            long l = Binder.clearCallingIdentity();
            try {
                for (int n2 = 0; n2 < this.mSmsSender.mTrackers.length; ++n2) {
                    int n4;
                    int n3 = n4 = 0;
                    if (arrn != null) {
                        n3 = n4;
                        if (arrn.length > n2) {
                            n3 = arrn[n2];
                        }
                    }
                    SMSDispatcher.this.processSendSmsResponse(this.mSmsSender.mTrackers[n2], n, n3);
                }
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }

        public void onSendSmsComplete(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected onSendSmsComplete call with result: ");
            stringBuilder.append(n);
            Rlog.e((String)SMSDispatcher.TAG, (String)stringBuilder.toString());
        }
    }

    private static class SettingsObserver
    extends ContentObserver {
        private final Context mContext;
        private final AtomicInteger mPremiumSmsRule;

        SettingsObserver(Handler handler, AtomicInteger atomicInteger, Context context) {
            super(handler);
            this.mPremiumSmsRule = atomicInteger;
            this.mContext = context;
            this.onChange(false);
        }

        public void onChange(boolean bl) {
            this.mPremiumSmsRule.set(Settings.Global.getInt((ContentResolver)this.mContext.getContentResolver(), (String)"sms_short_code_rule", (int)1));
        }
    }

    protected abstract class SmsSender
    extends CarrierMessagingServiceManager {
        protected volatile SmsSenderCallback mSenderCallback;
        protected final SmsTracker mTracker;

        protected SmsSender(SmsTracker smsTracker) {
            this.mTracker = smsTracker;
        }

        public void sendSmsByCarrierApp(String string, SmsSenderCallback smsSenderCallback) {
            this.mSenderCallback = smsSenderCallback;
            if (!this.bindToCarrierMessagingService(SMSDispatcher.this.mContext, string)) {
                Rlog.e((String)SMSDispatcher.TAG, (String)"bindService() for carrier messaging service failed");
                this.mSenderCallback.onSendSmsComplete(1, 0);
            } else {
                Rlog.d((String)SMSDispatcher.TAG, (String)"bindService() for carrier messaging service succeeded");
            }
        }
    }

    protected final class SmsSenderCallback
    extends ICarrierMessagingCallback.Stub {
        private final SmsSender mSmsSender;

        public SmsSenderCallback(SmsSender smsSender) {
            this.mSmsSender = smsSender;
        }

        public void onDownloadMmsComplete(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected onDownloadMmsComplete call with result: ");
            stringBuilder.append(n);
            Rlog.e((String)SMSDispatcher.TAG, (String)stringBuilder.toString());
        }

        public void onFilterComplete(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected onFilterComplete call with result: ");
            stringBuilder.append(n);
            Rlog.e((String)SMSDispatcher.TAG, (String)stringBuilder.toString());
        }

        public void onSendMmsComplete(int n, byte[] object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected onSendMmsComplete call with result: ");
            ((StringBuilder)object).append(n);
            Rlog.e((String)SMSDispatcher.TAG, (String)((StringBuilder)object).toString());
        }

        public void onSendMultipartSmsComplete(int n, int[] object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected onSendMultipartSmsComplete call with result: ");
            ((StringBuilder)object).append(n);
            Rlog.e((String)SMSDispatcher.TAG, (String)((StringBuilder)object).toString());
        }

        public void onSendSmsComplete(int n, int n2) {
            SMSDispatcher.this.checkCallerIsPhoneOrCarrierApp();
            long l = Binder.clearCallingIdentity();
            try {
                this.mSmsSender.disposeConnection(SMSDispatcher.this.mContext);
                SMSDispatcher.this.processSendSmsResponse(this.mSmsSender.mTracker, n, n2);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
    }

    public static class SmsTracker {
        private AtomicBoolean mAnyPartFailed;
        @UnsupportedAppUsage
        public final PackageInfo mAppInfo;
        @UnsupportedAppUsage
        private final HashMap<String, Object> mData;
        @UnsupportedAppUsage
        public final PendingIntent mDeliveryIntent;
        @UnsupportedAppUsage
        public final String mDestAddress;
        public boolean mExpectMore;
        String mFormat;
        private String mFullMessageText;
        public int mImsRetry;
        private final boolean mIsForVvm;
        private boolean mIsText;
        @UnsupportedAppUsage
        public int mMessageRef;
        @UnsupportedAppUsage
        public Uri mMessageUri;
        @UnsupportedAppUsage
        private boolean mPersistMessage;
        public int mPriority;
        public int mRetryCount;
        @UnsupportedAppUsage
        public final PendingIntent mSentIntent;
        public final SmsHeader mSmsHeader;
        private int mSubId;
        @UnsupportedAppUsage
        private long mTimestamp = System.currentTimeMillis();
        private AtomicInteger mUnsentPartCount;
        private final int mUserId;
        public boolean mUsesImsServiceForIms;
        public int mValidityPeriod;

        private SmsTracker(HashMap<String, Object> hashMap, PendingIntent pendingIntent, PendingIntent pendingIntent2, PackageInfo packageInfo, String string, String string2, AtomicInteger atomicInteger, AtomicBoolean atomicBoolean, Uri uri, SmsHeader smsHeader, boolean bl, String string3, int n, boolean bl2, boolean bl3, int n2, int n3, int n4, boolean bl4) {
            this.mData = hashMap;
            this.mSentIntent = pendingIntent;
            this.mDeliveryIntent = pendingIntent2;
            this.mRetryCount = 0;
            this.mAppInfo = packageInfo;
            this.mDestAddress = string;
            this.mFormat = string2;
            this.mExpectMore = bl;
            this.mImsRetry = 0;
            this.mUsesImsServiceForIms = false;
            this.mMessageRef = 0;
            this.mUnsentPartCount = atomicInteger;
            this.mAnyPartFailed = atomicBoolean;
            this.mMessageUri = uri;
            this.mSmsHeader = smsHeader;
            this.mFullMessageText = string3;
            this.mSubId = n;
            this.mIsText = bl2;
            this.mPersistMessage = bl3;
            this.mUserId = n2;
            this.mPriority = n3;
            this.mValidityPeriod = n4;
            this.mIsForVvm = bl4;
        }

        private void persistOrUpdateMessage(Context context, int n, int n2) {
            if (this.mMessageUri != null) {
                this.updateMessageState(context, n, n2);
            } else {
                this.mMessageUri = this.persistSentMessageIfRequired(context, n, n2);
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private Uri persistSentMessageIfRequired(Context context, int n, int n2) {
            Throwable throwable2222;
            String string;
            long l;
            block8 : {
                if (!this.mIsText) return null;
                if (!this.mPersistMessage) return null;
                if (!SmsApplication.shouldWriteMessageForPackage((String)this.mAppInfo.packageName, (Context)context)) {
                    return null;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Persist SMS into ");
                string = n == 5 ? "FAILED" : "SENT";
                stringBuilder.append(string);
                Rlog.d((String)SMSDispatcher.TAG, (String)stringBuilder.toString());
                stringBuilder = new ContentValues();
                stringBuilder.put("sub_id", Integer.valueOf(this.mSubId));
                stringBuilder.put("address", this.mDestAddress);
                stringBuilder.put("body", this.mFullMessageText);
                stringBuilder.put("date", Long.valueOf(System.currentTimeMillis()));
                stringBuilder.put("seen", Integer.valueOf(1));
                stringBuilder.put("read", Integer.valueOf(1));
                string = this.mAppInfo;
                string = string != null ? ((PackageInfo)string).packageName : null;
                if (!TextUtils.isEmpty((CharSequence)string)) {
                    stringBuilder.put("creator", string);
                }
                if (this.mDeliveryIntent != null) {
                    stringBuilder.put("status", Integer.valueOf(32));
                }
                if (n2 != 0) {
                    stringBuilder.put("error_code", Integer.valueOf(n2));
                }
                l = Binder.clearCallingIdentity();
                context = context.getContentResolver();
                string = context.insert(Telephony.Sms.Sent.CONTENT_URI, (ContentValues)stringBuilder);
                if (string == null || n != 5) break block8;
                stringBuilder = new ContentValues(1);
                stringBuilder.put("type", Integer.valueOf(5));
                context.update((Uri)string, (ContentValues)stringBuilder, null, null);
            }
            Binder.restoreCallingIdentity((long)l);
            return string;
            {
                catch (Throwable throwable2222) {
                }
                catch (Exception exception) {}
                {
                    Rlog.e((String)SMSDispatcher.TAG, (String)"writeOutboxMessage: Failed to persist outbox message", (Throwable)exception);
                }
                Binder.restoreCallingIdentity((long)l);
                return null;
            }
            Binder.restoreCallingIdentity((long)l);
            throw throwable2222;
        }

        private void updateMessageState(Context object, int n, int n2) {
            if (this.mMessageUri == null) {
                return;
            }
            ContentValues contentValues = new ContentValues(2);
            contentValues.put("type", Integer.valueOf(n));
            contentValues.put("error_code", Integer.valueOf(n2));
            long l = Binder.clearCallingIdentity();
            try {
                if (SqliteWrapper.update((Context)object, (ContentResolver)object.getContentResolver(), (Uri)this.mMessageUri, (ContentValues)contentValues, null, null) != 1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failed to move message to ");
                    ((StringBuilder)object).append(n);
                    Rlog.e((String)SMSDispatcher.TAG, (String)((StringBuilder)object).toString());
                }
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }

        public String getAppPackageName() {
            Object object = this.mAppInfo;
            object = object != null ? object.packageName : null;
            return object;
        }

        public HashMap<String, Object> getData() {
            return this.mData;
        }

        @UnsupportedAppUsage
        boolean isMultipart() {
            return this.mData.containsKey("parts");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        public void onFailed(Context context, int n, int n2) {
            Serializable serializable = this.mAnyPartFailed;
            if (serializable != null) {
                ((AtomicBoolean)serializable).set(true);
            }
            boolean bl = true;
            serializable = this.mUnsentPartCount;
            if (serializable != null) {
                bl = ((AtomicInteger)serializable).decrementAndGet() == 0;
            }
            if (bl) {
                this.persistOrUpdateMessage(context, 5, n2);
            }
            if (this.mSentIntent == null) return;
            try {
                serializable = new Intent();
                if (this.mMessageUri != null) {
                    serializable.putExtra("uri", this.mMessageUri.toString());
                }
                if (n2 != 0) {
                    serializable.putExtra("errorCode", n2);
                }
                if (this.mUnsentPartCount != null && bl) {
                    serializable.putExtra(SMSDispatcher.SEND_NEXT_MSG_EXTRA, true);
                }
                this.mSentIntent.send(context, n, (Intent)serializable);
                return;
            }
            catch (PendingIntent.CanceledException canceledException) {
                Rlog.e((String)SMSDispatcher.TAG, (String)"Failed to send result");
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        public void onSent(Context context) {
            boolean bl = true;
            Serializable serializable = this.mUnsentPartCount;
            if (serializable != null) {
                bl = ((AtomicInteger)serializable).decrementAndGet() == 0;
            }
            if (bl) {
                int n = 2;
                serializable = this.mAnyPartFailed;
                int n2 = n;
                if (serializable != null) {
                    n2 = n;
                    if (((AtomicBoolean)serializable).get()) {
                        n2 = 5;
                    }
                }
                this.persistOrUpdateMessage(context, n2, 0);
            }
            if (this.mSentIntent == null) return;
            try {
                serializable = new Intent();
                if (this.mMessageUri != null) {
                    serializable.putExtra("uri", this.mMessageUri.toString());
                }
                if (this.mUnsentPartCount != null && bl) {
                    serializable.putExtra(SMSDispatcher.SEND_NEXT_MSG_EXTRA, true);
                }
                this.mSentIntent.send(context, -1, (Intent)serializable);
                return;
            }
            catch (PendingIntent.CanceledException canceledException) {
                Rlog.e((String)SMSDispatcher.TAG, (String)"Failed to send result");
            }
        }

        @UnsupportedAppUsage
        public void updateSentMessageStatus(Context context, int n) {
            if (this.mMessageUri != null) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put("status", Integer.valueOf(n));
                SqliteWrapper.update((Context)context, (ContentResolver)context.getContentResolver(), (Uri)this.mMessageUri, (ContentValues)contentValues, null, null);
            }
        }
    }

    protected final class TextSmsSender
    extends SmsSender {
        public TextSmsSender(SmsTracker smsTracker) {
            super(smsTracker);
        }

        @Override
        protected void onServiceReady(ICarrierMessagingService iCarrierMessagingService) {
            CharSequence charSequence = (String)this.mTracker.getData().get(SMSDispatcher.MAP_KEY_TEXT);
            if (charSequence != null) {
                try {
                    iCarrierMessagingService.sendTextSms((String)charSequence, SMSDispatcher.this.getSubId(), this.mTracker.mDestAddress, SMSDispatcher.getSendSmsFlag(this.mTracker.mDeliveryIntent), (ICarrierMessagingCallback)this.mSenderCallback);
                }
                catch (RemoteException remoteException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Exception sending the SMS: ");
                    ((StringBuilder)charSequence).append((Object)remoteException);
                    Rlog.e((String)SMSDispatcher.TAG, (String)((StringBuilder)charSequence).toString());
                    this.mSenderCallback.onSendSmsComplete(1, 0);
                }
            } else {
                this.mSenderCallback.onSendSmsComplete(1, 0);
            }
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.ActivityManager
 *  android.app.BroadcastOptions
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.IPackageManager
 *  android.content.pm.IPackageManager$Stub
 *  android.content.pm.PackageManager
 *  android.content.pm.UserInfo
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Binder
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IDeviceIdleController
 *  android.os.Message
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.os.UserHandle
 *  android.os.UserManager
 *  android.os.storage.StorageManager
 *  android.provider.Telephony
 *  android.provider.Telephony$Sms
 *  android.provider.Telephony$Sms$Inbox
 *  android.provider.Telephony$Sms$Intents
 *  android.telephony.Rlog
 *  android.telephony.SmsManager
 *  android.telephony.SmsMessage
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  android.util.Pair
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.SmsApplication
 *  com.android.internal.telephony.SmsConstants
 *  com.android.internal.telephony.SmsConstants$MessageClass
 *  com.android.internal.telephony.SmsHeader
 *  com.android.internal.telephony.SmsHeader$ConcatRef
 *  com.android.internal.telephony.SmsHeader$PortAddrs
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.util.HexDump
 *  com.android.internal.util.IState
 *  com.android.internal.util.State
 *  com.android.internal.util.StateMachine
 *  com.android.internal.util.StateMachine$LogRec
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Telephony;
import android.telephony.Rlog;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.LocalLog;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.AppSmsManager;
import com.android.internal.telephony.BlockChecker;
import com.android.internal.telephony.CarrierServicesSmsFilter;
import com.android.internal.telephony.CellBroadcastHandler;
import com.android.internal.telephony.IccSmsInterfaceManager;
import com.android.internal.telephony.InboundSmsTracker;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SmsApplication;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsDispatchersController;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.SmsStorageMonitor;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.VisualVoicemailSmsFilter;
import com.android.internal.telephony.WapPushOverSms;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.util.HexDump;
import com.android.internal.util.IState;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class InboundSmsHandler
extends StateMachine {
    private static String ACTION_OPEN_SMS_APP;
    public static final int ADDRESS_COLUMN = 6;
    public static final int COUNT_COLUMN = 5;
    public static final int DATE_COLUMN = 3;
    protected static final boolean DBG = true;
    public static final int DELETED_FLAG_COLUMN = 10;
    public static final int DESTINATION_PORT_COLUMN = 2;
    public static final int DISPLAY_ADDRESS_COLUMN = 9;
    private static final int EVENT_BROADCAST_COMPLETE = 3;
    public static final int EVENT_BROADCAST_SMS = 2;
    public static final int EVENT_INJECT_SMS = 7;
    public static final int EVENT_NEW_SMS = 1;
    private static final int EVENT_RELEASE_WAKELOCK = 5;
    private static final int EVENT_RETURN_TO_IDLE = 4;
    public static final int EVENT_START_ACCEPTING_SMS = 6;
    public static final int EVENT_UPDATE_TRACKER = 8;
    public static final int ID_COLUMN = 7;
    public static final int MESSAGE_BODY_COLUMN = 8;
    private static final int NOTIFICATION_ID_NEW_MESSAGE = 1;
    private static final String NOTIFICATION_TAG = "InboundSmsHandler";
    public static final int PDU_COLUMN = 0;
    private static final String[] PDU_DELETED_FLAG_PROJECTION;
    private static final Map<Integer, Integer> PDU_DELETED_FLAG_PROJECTION_INDEX_MAPPING;
    private static final String[] PDU_SEQUENCE_PORT_PROJECTION;
    private static final Map<Integer, Integer> PDU_SEQUENCE_PORT_PROJECTION_INDEX_MAPPING;
    public static final int REFERENCE_NUMBER_COLUMN = 4;
    public static final String SELECT_BY_ID = "_id=?";
    public static final int SEQUENCE_COLUMN = 1;
    protected static final boolean VDBG = false;
    private static final int WAKELOCK_TIMEOUT = 3000;
    protected static final Uri sRawUri;
    protected static final Uri sRawUriPermanentDelete;
    private final int DELETE_PERMANENTLY;
    private final int MARK_DELETED;
    @UnsupportedAppUsage
    protected CellBroadcastHandler mCellBroadcastHandler;
    @UnsupportedAppUsage
    protected final Context mContext;
    private final DefaultState mDefaultState = new DefaultState();
    @UnsupportedAppUsage
    private final DeliveringState mDeliveringState = new DeliveringState();
    @UnsupportedAppUsage
    IDeviceIdleController mDeviceIdleController;
    @UnsupportedAppUsage
    private final IdleState mIdleState = new IdleState();
    private boolean mLastSmsWasInjected = false;
    private LocalLog mLocalLog = new LocalLog(64);
    protected TelephonyMetrics mMetrics = TelephonyMetrics.getInstance();
    @UnsupportedAppUsage
    protected Phone mPhone;
    @UnsupportedAppUsage
    private final ContentResolver mResolver;
    private final boolean mSmsReceiveDisabled;
    private final StartupState mStartupState = new StartupState();
    protected SmsStorageMonitor mStorageMonitor;
    @UnsupportedAppUsage
    private UserManager mUserManager;
    @UnsupportedAppUsage
    private final WaitingState mWaitingState = new WaitingState();
    @UnsupportedAppUsage
    private final PowerManager.WakeLock mWakeLock;
    private int mWakeLockTimeout;
    @UnsupportedAppUsage
    private final WapPushOverSms mWapPush;

    static {
        PDU_DELETED_FLAG_PROJECTION = new String[]{"pdu", "deleted"};
        PDU_DELETED_FLAG_PROJECTION_INDEX_MAPPING = new HashMap<Integer, Integer>(){
            {
                Integer n = 0;
                this.put(n, n);
                this.put(10, 1);
            }
        };
        PDU_SEQUENCE_PORT_PROJECTION = new String[]{"pdu", "sequence", "destination_port", "display_originating_addr", "date"};
        PDU_SEQUENCE_PORT_PROJECTION_INDEX_MAPPING = new HashMap<Integer, Integer>(){
            {
                Integer n = 0;
                this.put(n, n);
                n = 1;
                this.put(n, n);
                n = 2;
                this.put(n, n);
                n = 3;
                this.put(9, n);
                this.put(n, 4);
            }
        };
        sRawUri = Uri.withAppendedPath((Uri)Telephony.Sms.CONTENT_URI, (String)"raw");
        sRawUriPermanentDelete = Uri.withAppendedPath((Uri)Telephony.Sms.CONTENT_URI, (String)"raw/permanentDelete");
        ACTION_OPEN_SMS_APP = "com.android.internal.telephony.OPEN_DEFAULT_SMS_APP";
    }

    protected InboundSmsHandler(String string, Context context, SmsStorageMonitor smsStorageMonitor, Phone phone, CellBroadcastHandler cellBroadcastHandler) {
        super(string);
        this.DELETE_PERMANENTLY = 1;
        this.MARK_DELETED = 2;
        this.mContext = context;
        this.mStorageMonitor = smsStorageMonitor;
        this.mPhone = phone;
        this.mCellBroadcastHandler = cellBroadcastHandler;
        this.mResolver = context.getContentResolver();
        this.mWapPush = new WapPushOverSms(context);
        boolean bl = this.mContext.getResources().getBoolean(17891521);
        this.mSmsReceiveDisabled = TelephonyManager.from((Context)this.mContext).getSmsReceiveCapableForPhone(this.mPhone.getPhoneId(), bl) ^ true;
        this.mWakeLock = ((PowerManager)this.mContext.getSystemService("power")).newWakeLock(1, string);
        this.mWakeLock.acquire();
        this.mUserManager = (UserManager)this.mContext.getSystemService("user");
        this.mDeviceIdleController = TelephonyComponentFactory.getInstance().inject(IDeviceIdleController.class.getName()).getIDeviceIdleController();
        this.addState((State)this.mDefaultState);
        this.addState((State)this.mStartupState, (State)this.mDefaultState);
        this.addState((State)this.mIdleState, (State)this.mDefaultState);
        this.addState((State)this.mDeliveringState, (State)this.mDefaultState);
        this.addState((State)this.mWaitingState, (State)this.mDeliveringState);
        this.setInitialState((State)this.mStartupState);
        this.log("created InboundSmsHandler");
    }

    private int addTrackerToRawTable(InboundSmsTracker inboundSmsTracker, boolean bl) {
        block8 : {
            if (bl) {
                try {
                    bl = this.checkAndHandleDuplicate(inboundSmsTracker);
                    if (bl) {
                        return 5;
                    }
                    break block8;
                }
                catch (SQLException sQLException) {
                    this.loge("Can't access SMS database", sQLException);
                    return 2;
                }
            }
            this.logd("Skipped message de-duping logic");
        }
        CharSequence charSequence = inboundSmsTracker.getAddress();
        String string = Integer.toString(inboundSmsTracker.getReferenceNumber());
        String string2 = Integer.toString(inboundSmsTracker.getMessageCount());
        ContentValues contentValues = inboundSmsTracker.getContentValues();
        contentValues = this.mResolver.insert(sRawUri, contentValues);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("URI of new row -> ");
        stringBuilder.append((Object)contentValues);
        this.log(stringBuilder.toString());
        try {
            long l = ContentUris.parseId((Uri)contentValues);
            if (inboundSmsTracker.getMessageCount() == 1) {
                inboundSmsTracker.setDeleteWhere(SELECT_BY_ID, new String[]{Long.toString(l)});
            } else {
                inboundSmsTracker.setDeleteWhere(inboundSmsTracker.getQueryForSegments(), new String[]{charSequence, string, string2});
            }
            return 1;
        }
        catch (Exception exception) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("error parsing URI for new row: ");
            ((StringBuilder)charSequence).append((Object)contentValues);
            this.loge(((StringBuilder)charSequence).toString(), exception);
            return 2;
        }
    }

    private static String buildMessageBodyFromPdus(SmsMessage[] arrsmsMessage) {
        int n = arrsmsMessage.length;
        if (n == 1) {
            return InboundSmsHandler.replaceFormFeeds(arrsmsMessage[0].getDisplayMessageBody());
        }
        StringBuilder stringBuilder = new StringBuilder();
        n = arrsmsMessage.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrsmsMessage[i].getDisplayMessageBody());
        }
        return InboundSmsHandler.replaceFormFeeds(stringBuilder.toString());
    }

    static void cancelNewMessageNotification(Context context) {
        ((NotificationManager)context.getSystemService("notification")).cancel(NOTIFICATION_TAG, 1);
    }

    private boolean checkAndHandleDuplicate(InboundSmsTracker inboundSmsTracker) throws SQLException {
        Cursor cursor;
        Cursor cursor2;
        StringBuilder stringBuilder;
        Object object;
        block47 : {
            block49 : {
                block48 : {
                    object = inboundSmsTracker.getExactMatchDupDetectQuery();
                    cursor2 = null;
                    cursor = this.mResolver.query(sRawUri, PDU_DELETED_FLAG_PROJECTION, (String)((Pair)object).first, (String[])((Pair)object).second, null);
                    if (cursor == null) break block47;
                    cursor2 = cursor;
                    if (!cursor.moveToNext()) break block47;
                    cursor2 = cursor;
                    if (cursor.getCount() == 1) break block48;
                    cursor2 = cursor;
                    cursor2 = cursor;
                    stringBuilder = new StringBuilder();
                    cursor2 = cursor;
                    stringBuilder.append("Exact match query returned ");
                    cursor2 = cursor;
                    stringBuilder.append(cursor.getCount());
                    cursor2 = cursor;
                    stringBuilder.append(" rows");
                    cursor2 = cursor;
                    this.loge(stringBuilder.toString());
                }
                cursor2 = cursor;
                if (cursor.getInt(PDU_DELETED_FLAG_PROJECTION_INDEX_MAPPING.get(10).intValue()) != 1) break block49;
                cursor2 = cursor;
                cursor2 = cursor;
                object = new StringBuilder();
                cursor2 = cursor;
                ((StringBuilder)object).append("Discarding duplicate message segment: ");
                cursor2 = cursor;
                ((StringBuilder)object).append(inboundSmsTracker);
                cursor2 = cursor;
                this.loge(((StringBuilder)object).toString());
                cursor2 = cursor;
                this.logDupPduMismatch(cursor, inboundSmsTracker);
                cursor.close();
                return true;
            }
            cursor2 = cursor;
            if (inboundSmsTracker.getMessageCount() != 1) break block47;
            cursor2 = cursor;
            this.deleteFromRawTable((String)((Pair)object).first, (String[])((Pair)object).second, 1);
            cursor2 = cursor;
            cursor2 = cursor;
            object = new StringBuilder();
            cursor2 = cursor;
            ((StringBuilder)object).append("Replacing duplicate message: ");
            cursor2 = cursor;
            ((StringBuilder)object).append(inboundSmsTracker);
            cursor2 = cursor;
            this.loge(((StringBuilder)object).toString());
            cursor2 = cursor;
            try {
                this.logDupPduMismatch(cursor, inboundSmsTracker);
            }
            catch (Throwable throwable) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw throwable;
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        if (inboundSmsTracker.getMessageCount() > 1) {
            block50 : {
                block51 : {
                    object = inboundSmsTracker.getInexactMatchDupDetectQuery();
                    cursor2 = null;
                    try {
                        cursor = this.mResolver.query(sRawUri, PDU_DELETED_FLAG_PROJECTION, (String)((Pair)object).first, (String[])((Pair)object).second, null);
                        if (cursor == null) break block50;
                        cursor2 = cursor;
                    }
                    catch (Throwable throwable) {
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw throwable;
                    }
                    if (!cursor.moveToNext()) break block50;
                    cursor2 = cursor;
                    if (cursor.getCount() == 1) break block51;
                    cursor2 = cursor;
                    cursor2 = cursor;
                    stringBuilder = new StringBuilder();
                    cursor2 = cursor;
                    stringBuilder.append("Inexact match query returned ");
                    cursor2 = cursor;
                    stringBuilder.append(cursor.getCount());
                    cursor2 = cursor;
                    stringBuilder.append(" rows");
                    cursor2 = cursor;
                    this.loge(stringBuilder.toString());
                }
                cursor2 = cursor;
                this.deleteFromRawTable((String)((Pair)object).first, (String[])((Pair)object).second, 1);
                cursor2 = cursor;
                cursor2 = cursor;
                object = new StringBuilder();
                cursor2 = cursor;
                ((StringBuilder)object).append("Replacing duplicate message segment: ");
                cursor2 = cursor;
                ((StringBuilder)object).append(inboundSmsTracker);
                cursor2 = cursor;
                this.loge(((StringBuilder)object).toString());
                cursor2 = cursor;
                this.logDupPduMismatch(cursor, inboundSmsTracker);
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    private void deleteFromRawTable(String charSequence, String[] arrstring, int n) {
        Uri uri = n == 1 ? sRawUriPermanentDelete : sRawUri;
        n = this.mResolver.delete(uri, (String)charSequence, arrstring);
        if (n == 0) {
            this.loge("No rows were deleted from raw table!");
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Deleted ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" rows from raw table.");
            this.log(((StringBuilder)charSequence).toString());
        }
    }

    private int dispatchMessage(SmsMessageBase smsMessageBase) {
        boolean bl;
        if (smsMessageBase == null) {
            this.loge("dispatchSmsMessage: message is null");
            return 2;
        }
        if (this.mSmsReceiveDisabled) {
            this.log("Received short message on device which doesn't support receiving SMS. Ignored.");
            return 1;
        }
        boolean bl2 = false;
        try {
            bl = IPackageManager.Stub.asInterface((IBinder)ServiceManager.getService((String)"package")).isOnlyCoreApps();
        }
        catch (RemoteException remoteException) {
            bl = bl2;
        }
        if (bl) {
            this.log("Received a short message in encrypted state. Rejecting.");
            return 2;
        }
        int n = this.dispatchMessageRadioSpecific(smsMessageBase);
        if (n != 1) {
            this.mMetrics.writeIncomingSmsError(this.mPhone.getPhoneId(), this.mLastSmsWasInjected, n);
        }
        return n;
    }

    private void dispatchSmsDeliveryIntent(byte[][] componentName, String charSequence, int n, SmsBroadcastReceiver smsBroadcastReceiver, boolean bl) {
        Intent intent = new Intent();
        intent.putExtra("pdus", (Serializable)componentName);
        intent.putExtra("format", (String)charSequence);
        if (n == -1) {
            intent.setAction("android.provider.Telephony.SMS_DELIVER");
            componentName = SmsApplication.getDefaultSmsApplication((Context)this.mContext, (boolean)true);
            if (componentName != null) {
                intent.setComponent(componentName);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Delivering SMS to: ");
                ((StringBuilder)charSequence).append(componentName.getPackageName());
                ((StringBuilder)charSequence).append(" ");
                ((StringBuilder)charSequence).append(componentName.getClassName());
                this.log(((StringBuilder)charSequence).toString());
            } else {
                intent.setComponent(null);
            }
            if (SmsManager.getDefault().getAutoPersisting() && (componentName = this.writeInboxMessage(intent)) != null) {
                intent.putExtra("uri", componentName.toString());
            }
            if (this.mPhone.getAppSmsManager().handleSmsReceivedIntent(intent)) {
                this.dropSms(smsBroadcastReceiver);
                return;
            }
        } else {
            intent.setAction("android.intent.action.DATA_SMS_RECEIVED");
            componentName = new StringBuilder();
            componentName.append("sms://localhost:");
            componentName.append(n);
            intent.setData(Uri.parse((String)componentName.toString()));
            intent.setComponent(null);
            intent.addFlags(16777216);
        }
        this.dispatchIntent(intent, "android.permission.RECEIVE_SMS", 16, this.handleSmsWhitelisting(intent.getComponent(), bl), smsBroadcastReceiver, UserHandle.SYSTEM);
    }

    private void dropSms(SmsBroadcastReceiver smsBroadcastReceiver) {
        this.deleteFromRawTable(smsBroadcastReceiver.mDeleteWhere, smsBroadcastReceiver.mDeleteWhereArgs, 2);
        this.sendMessage(3);
    }

    private boolean filterSms(byte[][] arrby, int n, InboundSmsTracker inboundSmsTracker, SmsBroadcastReceiver smsBroadcastReceiver, boolean bl) {
        CarrierServicesSmsFilterCallback carrierServicesSmsFilterCallback = new CarrierServicesSmsFilterCallback(arrby, n, inboundSmsTracker.getFormat(), smsBroadcastReceiver, bl, inboundSmsTracker.isClass0());
        if (new CarrierServicesSmsFilter(this.mContext, this.mPhone, arrby, n, inboundSmsTracker.getFormat(), carrierServicesSmsFilterCallback, this.getName(), this.mLocalLog).filter()) {
            return true;
        }
        if (VisualVoicemailSmsFilter.filter(this.mContext, arrby, inboundSmsTracker.getFormat(), n, this.mPhone.getSubId())) {
            this.log("Visual voicemail SMS dropped");
            this.dropSms(smsBroadcastReceiver);
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    private void handleInjectSms(AsyncResult asyncResult) {
        SmsDispatchersController.SmsInjectionCallback smsInjectionCallback;
        int n;
        SmsDispatchersController.SmsInjectionCallback smsInjectionCallback2;
        block7 : {
            smsInjectionCallback = null;
            smsInjectionCallback = smsInjectionCallback2 = (SmsDispatchersController.SmsInjectionCallback)asyncResult.userObj;
            asyncResult = (SmsMessage)asyncResult.result;
            if (asyncResult == null) {
                n = 2;
                break block7;
            }
            smsInjectionCallback = smsInjectionCallback2;
            this.mLastSmsWasInjected = true;
            smsInjectionCallback = smsInjectionCallback2;
            try {
                n = this.dispatchMessage(asyncResult.mWrappedSmsMessage);
            }
            catch (RuntimeException runtimeException) {
                this.loge("Exception dispatching message", runtimeException);
                n = 2;
            }
        }
        smsInjectionCallback = smsInjectionCallback2;
        if (smsInjectionCallback != null) {
            smsInjectionCallback.onSmsInjectedResult(n);
        }
    }

    @UnsupportedAppUsage
    private void handleNewSms(AsyncResult asyncResult) {
        int n;
        if (asyncResult.exception != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception processing incoming SMS: ");
            stringBuilder.append(asyncResult.exception);
            this.loge(stringBuilder.toString());
            return;
        }
        boolean bl = false;
        try {
            asyncResult = (SmsMessage)asyncResult.result;
            this.mLastSmsWasInjected = false;
            n = this.dispatchMessage(asyncResult.mWrappedSmsMessage);
        }
        catch (RuntimeException runtimeException) {
            this.loge("Exception dispatching message", runtimeException);
            n = 2;
        }
        if (n != -1) {
            if (n == 1) {
                bl = true;
            }
            this.notifyAndAcknowledgeLastIncomingSms(bl, n, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private Bundle handleSmsWhitelisting(ComponentName componentName, boolean bl) {
        String string;
        String string2;
        if (componentName != null) {
            string = componentName.getPackageName();
            string2 = "sms-app";
        } else {
            string = this.mContext.getPackageName();
            string2 = "sms-broadcast";
        }
        BroadcastOptions broadcastOptions = null;
        componentName = null;
        if (bl) {
            broadcastOptions = BroadcastOptions.makeBasic();
            broadcastOptions.setBackgroundActivityStartsAllowed(true);
            componentName = broadcastOptions.toBundle();
        }
        try {
            long l = this.mDeviceIdleController.addPowerSaveTempWhitelistAppForSms(string, 0, string2);
            string2 = broadcastOptions;
            if (broadcastOptions == null) {
                string2 = BroadcastOptions.makeBasic();
            }
            string2.setTemporaryAppWhitelistDuration(l);
            broadcastOptions = string2.toBundle();
            return broadcastOptions;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return componentName;
    }

    static boolean isCurrentFormat3gpp2() {
        boolean bl = 2 == TelephonyManager.getDefault().getCurrentPhoneType();
        return bl;
    }

    @UnsupportedAppUsage
    private boolean isSkipNotifyFlagSet(int n) {
        boolean bl = (n & 2) > 0;
        return bl;
    }

    private void logDupPduMismatch(Cursor arrby, InboundSmsTracker object) {
        byte[] arrby2 = arrby.getString(PDU_DELETED_FLAG_PROJECTION_INDEX_MAPPING.get(0).intValue());
        arrby = ((InboundSmsTracker)object).getPdu();
        if (!Arrays.equals(arrby2 = HexDump.hexStringToByteArray((String)arrby2), ((InboundSmsTracker)object).getPdu())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Warning: dup message PDU of length ");
            ((StringBuilder)object).append(arrby.length);
            ((StringBuilder)object).append(" is different from existing PDU of length ");
            ((StringBuilder)object).append(arrby2.length);
            this.loge(((StringBuilder)object).toString());
        }
    }

    private void notifyAndAcknowledgeLastIncomingSms(boolean bl, int n, Message message) {
        if (!bl) {
            Intent intent = new Intent("android.provider.Telephony.SMS_REJECTED");
            intent.putExtra("result", n);
            intent.addFlags(16777216);
            this.mContext.sendBroadcast(intent, "android.permission.RECEIVE_SMS");
        }
        this.acknowledgeLastIncomingSms(bl, n, message);
    }

    private static ContentValues parseSmsMessage(SmsMessage[] object) {
        Integer n = 0;
        SmsMessage smsMessage = object[0];
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", smsMessage.getDisplayOriginatingAddress());
        contentValues.put("body", InboundSmsHandler.buildMessageBodyFromPdus(object));
        contentValues.put("date_sent", Long.valueOf(smsMessage.getTimestampMillis()));
        contentValues.put("date", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("protocol", Integer.valueOf(smsMessage.getProtocolIdentifier()));
        contentValues.put("seen", n);
        contentValues.put("read", n);
        object = smsMessage.getPseudoSubject();
        if (!TextUtils.isEmpty((CharSequence)object)) {
            contentValues.put("subject", (String)object);
        }
        contentValues.put("reply_path_present", Integer.valueOf((int)smsMessage.isReplyPathPresent()));
        contentValues.put("service_center", smsMessage.getServiceCenterAddress());
        return contentValues;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private boolean processMessagePart(InboundSmsTracker object) {
        Throwable throwable2222;
        Object object2;
        block26 : {
            Object object4;
            Cursor cursor;
            Object object5;
            Object object3;
            int n = ((InboundSmsTracker)object).getMessageCount();
            int n2 = ((InboundSmsTracker)object).getDestPort();
            boolean bl = false;
            String string = ((InboundSmsTracker)object).getAddress();
            if (n <= 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("processMessagePart: returning false due to invalid message count ");
                ((StringBuilder)object).append(n);
                this.loge(((StringBuilder)object).toString());
                return false;
            }
            if (n == 1) {
                object2 = ((InboundSmsTracker)object).getPdu();
                long l = ((InboundSmsTracker)object).getTimestamp();
                bl = BlockChecker.isBlocked(this.mContext, ((InboundSmsTracker)object).getDisplayAddress(), null);
                object5 = new long[]{l};
                cursor = new Cursor[][]{object2};
            } else {
                byte[] arrby;
                int n3;
                block25 : {
                    object4 = null;
                    cursor = null;
                    object2 = cursor;
                    object5 = object4;
                    object3 = Integer.toString(((InboundSmsTracker)object).getReferenceNumber());
                    object2 = cursor;
                    object5 = object4;
                    arrby = Integer.toString(((InboundSmsTracker)object).getMessageCount());
                    object2 = cursor;
                    object5 = object4;
                    cursor = this.mResolver.query(sRawUri, PDU_SEQUENCE_PORT_PROJECTION, ((InboundSmsTracker)object).getQueryForSegments(), new String[]{string, object3, arrby}, null);
                    object2 = cursor;
                    object5 = cursor;
                    n3 = cursor.getCount();
                    if (n3 >= n) break block25;
                    cursor.close();
                    return false;
                }
                object2 = cursor;
                object5 = cursor;
                arrby = new byte[n][];
                object2 = cursor;
                object5 = cursor;
                object4 = new long[n];
                do {
                    object2 = cursor;
                    object5 = cursor;
                    if (!cursor.moveToNext()) break;
                    object2 = cursor;
                    object5 = cursor;
                    int n4 = cursor.getInt(PDU_SEQUENCE_PORT_PROJECTION_INDEX_MAPPING.get(1).intValue()) - ((InboundSmsTracker)object).getIndexOffset();
                    object2 = cursor;
                    object5 = cursor;
                    if (n4 < arrby.length && n4 >= 0) {
                        object2 = cursor;
                        object5 = cursor;
                        arrby[n4] = (byte)HexDump.hexStringToByteArray((String)cursor.getString(PDU_SEQUENCE_PORT_PROJECTION_INDEX_MAPPING.get(0).intValue()));
                        n3 = n2;
                        if (n4 == 0) {
                            n3 = n2;
                            object2 = cursor;
                            object5 = cursor;
                            if (!cursor.isNull(PDU_SEQUENCE_PORT_PROJECTION_INDEX_MAPPING.get(2).intValue())) {
                                object2 = cursor;
                                object5 = cursor;
                                int n5 = InboundSmsTracker.getRealDestPort(cursor.getInt(PDU_SEQUENCE_PORT_PROJECTION_INDEX_MAPPING.get(2).intValue()));
                                n3 = n2;
                                if (n5 != -1) {
                                    n3 = n5;
                                }
                            }
                        }
                        object2 = cursor;
                        object5 = cursor;
                        object4[n4] = cursor.getLong(PDU_SEQUENCE_PORT_PROJECTION_INDEX_MAPPING.get(3).intValue());
                        boolean bl2 = bl;
                        if (!bl) {
                            object2 = cursor;
                            object5 = cursor;
                            bl2 = BlockChecker.isBlocked(this.mContext, cursor.getString(PDU_SEQUENCE_PORT_PROJECTION_INDEX_MAPPING.get(9).intValue()), null);
                        }
                        n2 = n3;
                        bl = bl2;
                        continue;
                    }
                    object2 = cursor;
                    object5 = cursor;
                    this.loge(String.format("processMessagePart: invalid seqNumber = %d, messageCount = %d", ((InboundSmsTracker)object).getIndexOffset() + n4, n));
                } while (true);
                cursor.close();
                cursor = arrby;
                object5 = object4;
            }
            object4 = !((InboundSmsTracker)object).is3gpp2() ? "3gpp" : "3gpp2";
            if (n2 != 2948) {
                this.mMetrics.writeIncomingSmsSession(this.mPhone.getPhoneId(), this.mLastSmsWasInjected, (String)object4, (long[])object5, bl);
            }
            if ((object2 = Arrays.asList(cursor)).size() != 0 && !object2.contains(null)) {
                object3 = new SmsBroadcastReceiver((InboundSmsTracker)object);
                if (!this.mUserManager.isUserUnlocked()) {
                    return this.processMessagePartWithUserLocked((InboundSmsTracker)object, (byte[][])cursor, n2, (SmsBroadcastReceiver)((Object)object3));
                }
                if (n2 == 2948) {
                    object2 = new ByteArrayOutputStream();
                    for (byte[] arrby : cursor) {
                        if (!((InboundSmsTracker)object).is3gpp2()) {
                            if ((arrby = SmsMessage.createFromPdu((byte[])arrby, (String)"3gpp")) == null) {
                                this.loge("processMessagePart: SmsMessage.createFromPdu returned null");
                                this.mMetrics.writeIncomingWapPush(this.mPhone.getPhoneId(), this.mLastSmsWasInjected, (String)object4, (long[])object5, false);
                                return false;
                            }
                            arrby = arrby.getUserData();
                        }
                        ((ByteArrayOutputStream)object2).write(arrby, 0, arrby.length);
                    }
                    n2 = this.mWapPush.dispatchWapPdu(((ByteArrayOutputStream)object2).toByteArray(), (BroadcastReceiver)object3, this, string);
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("dispatchWapPdu() returned ");
                    ((StringBuilder)object2).append(n2);
                    this.log(((StringBuilder)object2).toString());
                    if (n2 != -1 && n2 != 1) {
                        this.mMetrics.writeIncomingWapPush(this.mPhone.getPhoneId(), this.mLastSmsWasInjected, (String)object4, (long[])object5, false);
                    } else {
                        this.mMetrics.writeIncomingWapPush(this.mPhone.getPhoneId(), this.mLastSmsWasInjected, (String)object4, (long[])object5, true);
                    }
                    if (n2 == -1) {
                        return true;
                    }
                    this.deleteFromRawTable(((InboundSmsTracker)object).getDeleteWhere(), ((InboundSmsTracker)object).getDeleteWhereArgs(), 2);
                    return false;
                }
                if (bl) {
                    this.deleteFromRawTable(((InboundSmsTracker)object).getDeleteWhere(), ((InboundSmsTracker)object).getDeleteWhereArgs(), 1);
                    return false;
                }
                if (this.filterSms((byte[][])cursor, n2, (InboundSmsTracker)object, (SmsBroadcastReceiver)((Object)object3), true)) return true;
                this.dispatchSmsDeliveryIntent((byte[][])cursor, ((InboundSmsTracker)object).getFormat(), n2, (SmsBroadcastReceiver)((Object)object3), ((InboundSmsTracker)object).isClass0());
                return true;
            }
            object5 = new StringBuilder();
            ((StringBuilder)object5).append("processMessagePart: returning false due to ");
            object = object2.size() == 0 ? "pduList.size() == 0" : "pduList.contains(null)";
            ((StringBuilder)object5).append((String)object);
            object = ((StringBuilder)object5).toString();
            this.loge((String)object);
            this.mLocalLog.log((String)object);
            return false;
            {
                catch (Throwable throwable2222) {
                    break block26;
                }
                catch (SQLException sQLException) {}
                object2 = object5;
                {
                    this.loge("Can't access multipart SMS database", sQLException);
                    if (object5 == null) return false;
                }
                object5.close();
                return false;
            }
        }
        if (object2 == null) throw throwable2222;
        object2.close();
        throw throwable2222;
    }

    private boolean processMessagePartWithUserLocked(InboundSmsTracker inboundSmsTracker, byte[][] arrby, int n, SmsBroadcastReceiver smsBroadcastReceiver) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Credential-encrypted storage not available. Port: ");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        if (n == 2948 && this.mWapPush.isWapPushForMms(arrby[0], this)) {
            this.showNewMessageNotification();
            return false;
        }
        if (n == -1) {
            if (this.filterSms(arrby, n, inboundSmsTracker, smsBroadcastReceiver, false)) {
                return true;
            }
            this.showNewMessageNotification();
            return false;
        }
        return false;
    }

    static void registerNewMessageNotificationActionHandler(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_OPEN_SMS_APP);
        context.registerReceiver((BroadcastReceiver)new NewMessageNotificationActionReceiver(), intentFilter);
    }

    private static String replaceFormFeeds(String string) {
        string = string == null ? "" : string.replace('\f', '\n');
        return string;
    }

    private void setWakeLockTimeout(int n) {
        this.mWakeLockTimeout = n;
    }

    @UnsupportedAppUsage
    private void showNewMessageNotification() {
        if (!StorageManager.isFileEncryptedNativeOrEmulated()) {
            return;
        }
        this.log("Show new message notification.");
        PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)this.mContext, (int)0, (Intent)new Intent(ACTION_OPEN_SMS_APP), (int)1073741824);
        pendingIntent = new Notification.Builder(this.mContext).setSmallIcon(17301646).setAutoCancel(true).setVisibility(1).setDefaults(-1).setContentTitle((CharSequence)this.mContext.getString(17040468)).setContentText((CharSequence)this.mContext.getString(17040467)).setContentIntent(pendingIntent).setChannelId("sms");
        NotificationManager notificationManager = (NotificationManager)this.mContext.getSystemService("notification");
        notificationManager.notify(NOTIFICATION_TAG, 1, pendingIntent.build());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private Uri writeInboxMessage(Intent contentValues) {
        Throwable throwable2222;
        int n;
        if ((contentValues = Telephony.Sms.Intents.getMessagesFromIntent((Intent)contentValues)) != null && ((SmsMessage[])contentValues).length >= 1) {
            n = ((SmsMessage[])contentValues).length;
        } else {
            this.loge("Failed to parse SMS pdu");
            return null;
        }
        for (int i = 0; i < n; ++i) {
            if (contentValues[i] != null) continue;
            this.loge("Can\u2019t write null SmsMessage");
            return null;
        }
        contentValues = InboundSmsHandler.parseSmsMessage((SmsMessage[])contentValues);
        long l = Binder.clearCallingIdentity();
        contentValues = this.mContext.getContentResolver().insert(Telephony.Sms.Inbox.CONTENT_URI, contentValues);
        Binder.restoreCallingIdentity((long)l);
        return contentValues;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                this.loge("Failed to persist inbox message", exception);
            }
            Binder.restoreCallingIdentity((long)l);
            return null;
        }
        Binder.restoreCallingIdentity((long)l);
        throw throwable2222;
    }

    @UnsupportedAppUsage
    protected abstract void acknowledgeLastIncomingSms(boolean var1, int var2, Message var3);

    protected int addTrackerToRawTableAndSendMessage(InboundSmsTracker inboundSmsTracker, boolean bl) {
        int n = this.addTrackerToRawTable(inboundSmsTracker, bl);
        if (n != 1) {
            if (n != 5) {
                return 2;
            }
            return 1;
        }
        this.sendMessage(2, (Object)inboundSmsTracker);
        return 1;
    }

    @UnsupportedAppUsage
    public void dispatchIntent(Intent intent, String string, int n, Bundle bundle, BroadcastReceiver broadcastReceiver, UserHandle object) {
        intent.addFlags(134217728);
        Object object2 = intent.getAction();
        if ("android.provider.Telephony.SMS_DELIVER".equals(object2) || "android.provider.Telephony.SMS_RECEIVED".equals(object2) || "android.provider.Telephony.WAP_PUSH_DELIVER".equals(object2) || "android.provider.Telephony.WAP_PUSH_RECEIVED".equals(object2)) {
            intent.addFlags(268435456);
        }
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)this.mPhone.getPhoneId());
        if (object.equals((Object)UserHandle.ALL)) {
            UserHandle userHandle;
            object2 = null;
            try {
                object2 = userHandle = ActivityManager.getService().getRunningUserIds();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            object = object2 == null ? new int[]{object.getIdentifier()} : object2;
            for (int i = ((int[])object).length - 1; i >= 0; --i) {
                userHandle = new UserHandle(object[i]);
                if (object[i] != false && (this.mUserManager.hasUserRestriction("no_sms", userHandle) || (object2 = this.mUserManager.getUserInfo((int)object[i])) == null || object2.isManagedProfile())) continue;
                Context context = this.mContext;
                object2 = object[i] == false ? broadcastReceiver : null;
                context.sendOrderedBroadcastAsUser(intent, userHandle, string, n, bundle, (BroadcastReceiver)object2, this.getHandler(), -1, null, null);
            }
        } else {
            this.mContext.sendOrderedBroadcastAsUser(intent, object, string, n, bundle, broadcastReceiver, this.getHandler(), -1, null, null);
        }
    }

    protected abstract int dispatchMessageRadioSpecific(SmsMessageBase var1);

    @UnsupportedAppUsage
    protected int dispatchNormalMessage(SmsMessageBase object) {
        boolean bl;
        Object object2 = object.getUserDataHeader();
        if (object2 != null && object2.concatRef != null) {
            Object object3 = object2.concatRef;
            object2 = object2.portAddrs;
            int n = object2 != null ? object2.destPort : -1;
            TelephonyComponentFactory telephonyComponentFactory = TelephonyComponentFactory.getInstance().inject(InboundSmsTracker.class.getName());
            byte[] arrby = object.getPdu();
            long l = object.getTimestampMillis();
            boolean bl2 = this.is3gpp2();
            String string = object.getOriginatingAddress();
            object2 = object.getDisplayOriginatingAddress();
            int n2 = object3.refNumber;
            int n3 = object3.seqNumber;
            int n4 = object3.msgCount;
            object3 = object.getMessageBody();
            bl = object.getMessageClass() == SmsConstants.MessageClass.CLASS_0;
            object = telephonyComponentFactory.makeInboundSmsTracker(arrby, l, n, bl2, string, (String)object2, n2, n3, n4, false, (String)object3, bl);
        } else {
            int n;
            CharSequence charSequence;
            int n5 = n = -1;
            if (object2 != null) {
                n5 = n;
                if (object2.portAddrs != null) {
                    n5 = object2.portAddrs.destPort;
                    charSequence = new StringBuilder();
                    charSequence.append("destination port: ");
                    charSequence.append(n5);
                    this.log(charSequence.toString());
                }
            }
            TelephonyComponentFactory telephonyComponentFactory = TelephonyComponentFactory.getInstance().inject(InboundSmsTracker.class.getName());
            byte[] arrby = object.getPdu();
            long l = object.getTimestampMillis();
            boolean bl3 = this.is3gpp2();
            object2 = object.getOriginatingAddress();
            String string = object.getDisplayOriginatingAddress();
            charSequence = object.getMessageBody();
            bl = object.getMessageClass() == SmsConstants.MessageClass.CLASS_0;
            object = telephonyComponentFactory.makeInboundSmsTracker(arrby, l, n5, bl3, false, (String)object2, string, (String)charSequence, bl);
        }
        bl = ((InboundSmsTracker)object).getDestPort() == -1;
        return this.addTrackerToRawTableAndSendMessage((InboundSmsTracker)object, bl);
    }

    public void dispose() {
        this.quit();
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(fileDescriptor, printWriter, arrstring);
        CellBroadcastHandler cellBroadcastHandler = this.mCellBroadcastHandler;
        if (cellBroadcastHandler != null) {
            cellBroadcastHandler.dump(fileDescriptor, printWriter, arrstring);
        }
        this.mLocalLog.dump(fileDescriptor, printWriter, arrstring);
    }

    @UnsupportedAppUsage
    public Phone getPhone() {
        return this.mPhone;
    }

    @VisibleForTesting
    public PowerManager.WakeLock getWakeLock() {
        return this.mWakeLock;
    }

    @VisibleForTesting
    public int getWakeLockTimeout() {
        return this.mWakeLockTimeout;
    }

    protected abstract boolean is3gpp2();

    @UnsupportedAppUsage
    protected void log(String string) {
        Rlog.d((String)this.getName(), (String)string);
    }

    @UnsupportedAppUsage
    protected void loge(String string) {
        Rlog.e((String)this.getName(), (String)string);
    }

    protected void loge(String string, Throwable throwable) {
        Rlog.e((String)this.getName(), (String)string, (Throwable)throwable);
    }

    protected void onQuitting() {
        this.mWapPush.dispose();
        while (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    private final class CarrierServicesSmsFilterCallback
    implements CarrierServicesSmsFilter.CarrierServicesSmsFilterCallbackInterface {
        private final int mDestPort;
        private final boolean mIsClass0;
        private final byte[][] mPdus;
        private final SmsBroadcastReceiver mSmsBroadcastReceiver;
        private final String mSmsFormat;
        private final boolean mUserUnlocked;

        CarrierServicesSmsFilterCallback(byte[][] arrby, int n, String string, SmsBroadcastReceiver smsBroadcastReceiver, boolean bl, boolean bl2) {
            this.mPdus = arrby;
            this.mDestPort = n;
            this.mSmsFormat = string;
            this.mSmsBroadcastReceiver = smsBroadcastReceiver;
            this.mUserUnlocked = bl;
            this.mIsClass0 = bl2;
        }

        @Override
        public void onFilterComplete(int n) {
            InboundSmsHandler inboundSmsHandler = InboundSmsHandler.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onFilterComplete: result is ");
            stringBuilder.append(n);
            inboundSmsHandler.logv(stringBuilder.toString());
            if ((n & 1) == 0) {
                if (VisualVoicemailSmsFilter.filter(InboundSmsHandler.this.mContext, this.mPdus, this.mSmsFormat, this.mDestPort, InboundSmsHandler.this.mPhone.getSubId())) {
                    InboundSmsHandler.this.log("Visual voicemail SMS dropped");
                    InboundSmsHandler.this.dropSms(this.mSmsBroadcastReceiver);
                    return;
                }
                if (this.mUserUnlocked) {
                    InboundSmsHandler.this.dispatchSmsDeliveryIntent(this.mPdus, this.mSmsFormat, this.mDestPort, this.mSmsBroadcastReceiver, this.mIsClass0);
                } else {
                    if (!InboundSmsHandler.this.isSkipNotifyFlagSet(n)) {
                        InboundSmsHandler.this.showNewMessageNotification();
                    }
                    InboundSmsHandler.this.sendMessage(3);
                }
            } else {
                InboundSmsHandler.this.dropSms(this.mSmsBroadcastReceiver);
            }
        }
    }

    private class DefaultState
    extends State {
        private DefaultState() {
        }

        public boolean processMessage(Message object) {
            int n = object.what;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("processMessage: unhandled message type ");
            ((StringBuilder)object2).append(object.what);
            ((StringBuilder)object2).append(" currState=");
            ((StringBuilder)object2).append(InboundSmsHandler.this.getCurrentState().getName());
            object = ((StringBuilder)object2).toString();
            if (Build.IS_DEBUGGABLE) {
                InboundSmsHandler.this.loge("---- Dumping InboundSmsHandler ----");
                object2 = InboundSmsHandler.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Total records=");
                stringBuilder.append(InboundSmsHandler.this.getLogRecCount());
                ((InboundSmsHandler)((Object)object2)).loge(stringBuilder.toString());
                for (n = java.lang.Math.max((int)(InboundSmsHandler.this.getLogRecSize() - 20), (int)0); n < InboundSmsHandler.this.getLogRecSize(); ++n) {
                    object2 = InboundSmsHandler.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Rec[%d]: %s\n");
                    stringBuilder.append(n);
                    stringBuilder.append(InboundSmsHandler.this.getLogRec(n).toString());
                    ((InboundSmsHandler)((Object)object2)).loge(stringBuilder.toString());
                }
                InboundSmsHandler.this.loge("---- Dumped InboundSmsHandler ----");
                throw new RuntimeException((String)object);
            }
            InboundSmsHandler.this.loge((String)object);
            return true;
        }
    }

    private class DeliveringState
    extends State {
        private DeliveringState() {
        }

        public void enter() {
            InboundSmsHandler.this.log("entering Delivering state");
        }

        public void exit() {
            InboundSmsHandler.this.log("leaving Delivering state");
        }

        public boolean processMessage(Message object) {
            Object object2 = InboundSmsHandler.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DeliveringState.processMessage:");
            stringBuilder.append(object.what);
            ((InboundSmsHandler)((Object)object2)).log(stringBuilder.toString());
            int n = object.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n != 5) {
                            if (n != 7) {
                                if (n != 8) {
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("Unhandled msg in delivering state, msg.what = ");
                                    ((StringBuilder)object2).append(object.what);
                                    object = ((StringBuilder)object2).toString();
                                    InboundSmsHandler.this.loge((String)object);
                                    InboundSmsHandler.this.mLocalLog.log((String)object);
                                    return false;
                                }
                                object2 = InboundSmsHandler.this;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("process tracker message in DeliveringState ");
                                stringBuilder.append(object.arg1);
                                ((InboundSmsHandler)((Object)object2)).logd(stringBuilder.toString());
                                return true;
                            }
                            InboundSmsHandler.this.handleInjectSms((AsyncResult)object.obj);
                            InboundSmsHandler.this.sendMessage(4);
                            return true;
                        }
                        InboundSmsHandler.this.mWakeLock.release();
                        if (!InboundSmsHandler.this.mWakeLock.isHeld()) {
                            InboundSmsHandler.this.loge("mWakeLock released while delivering/broadcasting!");
                        }
                        return true;
                    }
                    object = InboundSmsHandler.this;
                    object.transitionTo((IState)((InboundSmsHandler)((Object)object)).mIdleState);
                    return true;
                }
                object2 = (InboundSmsTracker)object.obj;
                if (InboundSmsHandler.this.processMessagePart((InboundSmsTracker)object2)) {
                    object2 = InboundSmsHandler.this;
                    object2.sendMessage(object2.obtainMessage(8, object.obj));
                    object = InboundSmsHandler.this;
                    object.transitionTo((IState)((InboundSmsHandler)((Object)object)).mWaitingState);
                } else {
                    InboundSmsHandler.this.log("No broadcast sent on processing EVENT_BROADCAST_SMS in Delivering state. Return to Idle state");
                    InboundSmsHandler.this.sendMessage(4);
                }
                return true;
            }
            InboundSmsHandler.this.handleNewSms((AsyncResult)object.obj);
            InboundSmsHandler.this.sendMessage(4);
            return true;
        }
    }

    private class IdleState
    extends State {
        private IdleState() {
        }

        public void enter() {
            InboundSmsHandler.this.log("entering Idle state");
            InboundSmsHandler inboundSmsHandler = InboundSmsHandler.this;
            inboundSmsHandler.sendMessageDelayed(5, (long)inboundSmsHandler.getWakeLockTimeout());
        }

        public void exit() {
            InboundSmsHandler.this.mWakeLock.acquire();
            InboundSmsHandler.this.log("acquired wakelock, leaving Idle state");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean processMessage(Message object) {
            InboundSmsHandler inboundSmsHandler = InboundSmsHandler.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IdleState.processMessage:");
            stringBuilder.append(object.what);
            inboundSmsHandler.log(stringBuilder.toString());
            inboundSmsHandler = InboundSmsHandler.this;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Idle state processing message type ");
            stringBuilder.append(object.what);
            inboundSmsHandler.log(stringBuilder.toString());
            int n = object.what;
            if (n != 1 && n != 2) {
                if (n == 4) return true;
                if (n != 5) {
                    if (n != 7) {
                        return false;
                    }
                } else {
                    InboundSmsHandler.this.mWakeLock.release();
                    if (InboundSmsHandler.this.mWakeLock.isHeld()) {
                        InboundSmsHandler.this.log("mWakeLock is still held after release");
                        return true;
                    } else {
                        InboundSmsHandler.this.log("mWakeLock released");
                    }
                    return true;
                }
            }
            InboundSmsHandler.this.deferMessage(object);
            InboundSmsHandler inboundSmsHandler2 = InboundSmsHandler.this;
            inboundSmsHandler2.transitionTo((IState)inboundSmsHandler2.mDeliveringState);
            return true;
        }
    }

    private static class NewMessageNotificationActionReceiver
    extends BroadcastReceiver {
        private NewMessageNotificationActionReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (ACTION_OPEN_SMS_APP.equals(intent.getAction()) && ((UserManager)context.getSystemService("user")).isUserUnlocked()) {
                context.startActivity(context.getPackageManager().getLaunchIntentForPackage(Telephony.Sms.getDefaultSmsPackage((Context)context)));
            }
        }
    }

    private final class SmsBroadcastReceiver
    extends BroadcastReceiver {
        private long mBroadcastTimeNano;
        @UnsupportedAppUsage
        private final String mDeleteWhere;
        @UnsupportedAppUsage
        private final String[] mDeleteWhereArgs;

        SmsBroadcastReceiver(InboundSmsTracker inboundSmsTracker) {
            this.mDeleteWhere = inboundSmsTracker.getDeleteWhere();
            this.mDeleteWhereArgs = inboundSmsTracker.getDeleteWhereArgs();
            this.mBroadcastTimeNano = System.nanoTime();
        }

        public void onReceive(Context object, Intent object2) {
            object = object2.getAction();
            if (((String)object).equals("android.provider.Telephony.SMS_DELIVER")) {
                object2.setAction("android.provider.Telephony.SMS_RECEIVED");
                object2.addFlags(16777216);
                object2.setComponent(null);
                object = InboundSmsHandler.this.handleSmsWhitelisting(null, false);
                InboundSmsHandler.this.dispatchIntent((Intent)object2, "android.permission.RECEIVE_SMS", 16, (Bundle)object, this, UserHandle.ALL);
            } else if (((String)object).equals("android.provider.Telephony.WAP_PUSH_DELIVER")) {
                String string;
                object2.setAction("android.provider.Telephony.WAP_PUSH_RECEIVED");
                object2.setComponent(null);
                object2.addFlags(16777216);
                object = null;
                try {
                    long l = InboundSmsHandler.this.mDeviceIdleController.addPowerSaveTempWhitelistAppForMms(InboundSmsHandler.this.mContext.getPackageName(), 0, "mms-broadcast");
                    string = BroadcastOptions.makeBasic();
                    string.setTemporaryAppWhitelistDuration(l);
                    string = string.toBundle();
                    object = string;
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                string = object2.getType();
                InboundSmsHandler.this.dispatchIntent((Intent)object2, WapPushOverSms.getPermissionForType(string), WapPushOverSms.getAppOpsPermissionForIntent(string), (Bundle)object, this, UserHandle.SYSTEM);
            } else {
                int n;
                if (!("android.intent.action.DATA_SMS_RECEIVED".equals(object) || "android.provider.Telephony.SMS_RECEIVED".equals(object) || "android.intent.action.DATA_SMS_RECEIVED".equals(object) || "android.provider.Telephony.WAP_PUSH_RECEIVED".equals(object))) {
                    InboundSmsHandler inboundSmsHandler = InboundSmsHandler.this;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("unexpected BroadcastReceiver action: ");
                    ((StringBuilder)object2).append((String)object);
                    inboundSmsHandler.loge(((StringBuilder)object2).toString());
                }
                if ((n = this.getResultCode()) != -1 && n != 1) {
                    object2 = InboundSmsHandler.this;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("a broadcast receiver set the result code to ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(", deleting from raw table anyway!");
                    ((InboundSmsHandler)((Object)object2)).loge(((StringBuilder)object).toString());
                } else {
                    InboundSmsHandler.this.log("successful broadcast, deleting from raw table.");
                }
                InboundSmsHandler.this.deleteFromRawTable(this.mDeleteWhere, this.mDeleteWhereArgs, 2);
                InboundSmsHandler.this.sendMessage(3);
                n = (int)((System.nanoTime() - this.mBroadcastTimeNano) / 1000000L);
                if (n >= 5000) {
                    object = InboundSmsHandler.this;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Slow ordered broadcast completion time: ");
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append(" ms");
                    ((InboundSmsHandler)((Object)object)).loge(((StringBuilder)object2).toString());
                } else {
                    object2 = InboundSmsHandler.this;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("ordered broadcast completed in: ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" ms");
                    ((InboundSmsHandler)((Object)object2)).log(((StringBuilder)object).toString());
                }
            }
        }
    }

    private class StartupState
    extends State {
        private StartupState() {
        }

        public void enter() {
            InboundSmsHandler.this.log("entering Startup state");
            InboundSmsHandler.this.setWakeLockTimeout(0);
        }

        public boolean processMessage(Message object) {
            InboundSmsHandler inboundSmsHandler = InboundSmsHandler.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("StartupState.processMessage:");
            stringBuilder.append(object.what);
            inboundSmsHandler.log(stringBuilder.toString());
            int n = object.what;
            if (n != 1 && n != 2) {
                if (n != 6) {
                    if (n != 7) {
                        return false;
                    }
                } else {
                    object = InboundSmsHandler.this;
                    object.transitionTo((IState)((InboundSmsHandler)((Object)object)).mIdleState);
                    return true;
                }
            }
            InboundSmsHandler.this.deferMessage(object);
            return true;
        }
    }

    private class WaitingState
    extends State {
        private InboundSmsTracker mLastDeliveredSmsTracker;

        private WaitingState() {
        }

        public void enter() {
            InboundSmsHandler.this.log("entering Waiting state");
        }

        public void exit() {
            InboundSmsHandler.this.log("exiting Waiting state");
            InboundSmsHandler.this.setWakeLockTimeout(3000);
            InboundSmsHandler.this.mPhone.getIccSmsInterfaceManager().mDispatchersController.sendEmptyMessage(17);
        }

        public boolean processMessage(Message object) {
            Object object2 = InboundSmsHandler.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("WaitingState.processMessage:");
            stringBuilder.append(object.what);
            ((InboundSmsHandler)((Object)object2)).log(stringBuilder.toString());
            int n = object.what;
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 8) {
                            return false;
                        }
                        this.mLastDeliveredSmsTracker = (InboundSmsTracker)object.obj;
                        return true;
                    }
                    return true;
                }
                this.mLastDeliveredSmsTracker = null;
                InboundSmsHandler.this.sendMessage(4);
                object = InboundSmsHandler.this;
                object.transitionTo((IState)((InboundSmsHandler)((Object)object)).mDeliveringState);
                return true;
            }
            if (this.mLastDeliveredSmsTracker != null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Defer sms broadcast due to undelivered sms,  messageCount = ");
                ((StringBuilder)object2).append(this.mLastDeliveredSmsTracker.getMessageCount());
                ((StringBuilder)object2).append(" destPort = ");
                ((StringBuilder)object2).append(this.mLastDeliveredSmsTracker.getDestPort());
                ((StringBuilder)object2).append(" timestamp = ");
                ((StringBuilder)object2).append(this.mLastDeliveredSmsTracker.getTimestamp());
                ((StringBuilder)object2).append(" currentTimestamp = ");
                ((StringBuilder)object2).append(System.currentTimeMillis());
                object2 = ((StringBuilder)object2).toString();
                InboundSmsHandler.this.logd((String)object2);
                InboundSmsHandler.this.mLocalLog.log((String)object2);
            }
            InboundSmsHandler.this.deferMessage(object);
            return true;
        }
    }

}


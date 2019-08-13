/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.BroadcastOptions
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.ServiceConnection
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.database.DatabaseUtils
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SqliteWrapper
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IDeviceIdleController
 *  android.os.RemoteException
 *  android.os.UserHandle
 *  android.os.UserManager
 *  android.provider.Telephony
 *  android.provider.Telephony$Mms
 *  android.provider.Telephony$Mms$Inbox
 *  android.telephony.Rlog
 *  android.telephony.SmsManager
 *  android.telephony.SubscriptionManager
 *  android.text.TextUtils
 *  android.util.Log
 *  com.android.internal.telephony.IWapPushManager
 *  com.android.internal.telephony.IWapPushManager$Stub
 *  com.android.internal.telephony.SmsApplication
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SqliteWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Telephony;
import android.telephony.Rlog;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.BlockChecker;
import com.android.internal.telephony.IWapPushManager;
import com.android.internal.telephony.InboundSmsHandler;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SmsApplication;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.WspTypeDecoder;
import com.google.android.mms.MmsException;
import com.google.android.mms.pdu.DeliveryInd;
import com.google.android.mms.pdu.EncodedStringValue;
import com.google.android.mms.pdu.GenericPdu;
import com.google.android.mms.pdu.NotificationInd;
import com.google.android.mms.pdu.PduParser;
import com.google.android.mms.pdu.PduPersister;
import com.google.android.mms.pdu.ReadOrigInd;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;

public class WapPushOverSms
implements ServiceConnection {
    private static final boolean DBG = false;
    private static final String LOCATION_SELECTION = "m_type=? AND ct_l =?";
    private static final String TAG = "WAP PUSH";
    private static final String THREAD_ID_SELECTION = "m_id=? AND m_type=?";
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Received broadcast ");
            ((StringBuilder)object).append(intent.getAction());
            Rlog.d((String)WapPushOverSms.TAG, (String)((StringBuilder)object).toString());
            if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                object = WapPushOverSms.this;
                (WapPushOverSms)object.new BindServiceThread(((WapPushOverSms)object).mContext).start();
            }
        }
    };
    @UnsupportedAppUsage
    private final Context mContext;
    @UnsupportedAppUsage
    private IDeviceIdleController mDeviceIdleController;
    @UnsupportedAppUsage
    private volatile IWapPushManager mWapPushManager;
    private String mWapPushManagerPackage;

    public WapPushOverSms(Context context) {
        this.mContext = context;
        this.mDeviceIdleController = TelephonyComponentFactory.getInstance().inject(IDeviceIdleController.class.getName()).getIDeviceIdleController();
        if (((UserManager)this.mContext.getSystemService("user")).isUserUnlocked()) {
            this.bindWapPushManagerService(this.mContext);
        } else {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_UNLOCKED");
            context.registerReceiver(this.mBroadcastReceiver, intentFilter);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void bindWapPushManagerService(Context context) {
        Intent intent = new Intent(IWapPushManager.class.getName());
        ComponentName componentName = intent.resolveSystemService(context.getPackageManager(), 0);
        intent.setComponent(componentName);
        if (componentName != null && context.bindService(intent, (ServiceConnection)this, 1)) {
            synchronized (this) {
                this.mWapPushManagerPackage = componentName.getPackageName();
                return;
            }
        }
        Rlog.e((String)TAG, (String)"bindService() for wappush manager failed");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private DecodedResult decodeWapPdu(byte[] var1_1, InboundSmsHandler var2_2) {
        var3_4 = new DecodedResult();
        var4_5 = 0 + 1;
        var5_6 = var1_1[0] & 255;
        var6_7 = var4_5 + 1;
        var7_8 = var1_1[var4_5] & 255;
        var8_9 = var2_2.getPhone().getPhoneId();
        var4_5 = var5_6;
        var9_10 = var7_8;
        var10_11 /* !! */  = var6_7;
        if (var7_8 == 6) ** GOTO lbl35
        var4_5 = var5_6;
        var9_10 = var7_8;
        var10_11 /* !! */  = var6_7;
        if (var7_8 == 7) ** GOTO lbl35
        var9_10 = this.mContext.getResources().getInteger(17694903);
        if (var9_10 == -1) ** GOTO lbl33
        var4_5 = var9_10 + 1;
        var5_6 = var1_1[var9_10] & 255;
        var6_7 = var4_5 + 1;
        var7_8 = var1_1[var4_5] & 255;
        var4_5 = var5_6;
        var9_10 = var7_8;
        var10_11 /* !! */  = var6_7;
        if (var7_8 == 6) ** GOTO lbl35
        var4_5 = var5_6;
        var9_10 = var7_8;
        var10_11 /* !! */  = var6_7;
        if (var7_8 == 7) ** GOTO lbl35
        try {
            var3_4.statusCode = 1;
            return var3_4;
lbl33: // 1 sources:
            var3_4.statusCode = 1;
            return var3_4;
lbl35: // 4 sources:
            if (!(var11_12 = TelephonyComponentFactory.getInstance().inject(WspTypeDecoder.class.getName()).makeWspTypeDecoder((byte[])var1_1)).decodeUintvarInteger(var10_11 /* !! */ )) {
                var3_4.statusCode = 2;
                return var3_4;
            }
            var6_7 = (int)var11_12.getValue32();
            if (!var11_12.decodeContentType(var10_11 /* !! */  += var11_12.getDecodedDataLength())) {
                var3_4.statusCode = 2;
                return var3_4;
            }
            var12_13 = var11_12.getValueString();
            var13_14 = var11_12.getValue32();
            var5_6 = var10_11 /* !! */  + var11_12.getDecodedDataLength();
            var15_15 = new byte[var6_7];
            System.arraycopy(var1_1, var10_11 /* !! */ , var15_15, 0, var15_15.length);
            if (var12_13 == null || !var12_13.equals("application/vnd.wap.coc")) {
                var2_2 = new byte[((Object)var1_1).length - (var10_11 /* !! */  += var6_7)];
                System.arraycopy(var1_1, var10_11 /* !! */ , var2_2, 0, ((Object)var2_2).length);
                var1_1 = var2_2;
            }
            var2_2 = SubscriptionManager.getSubId((int)var8_9);
            var10_11 /* !! */  = var2_2 != null && ((Object)var2_2).length > 0 ? (int)var2_2[0] : SmsManager.getDefaultSmsSubscriptionId();
            try {
                var2_2 = new PduParser((byte[])var1_1, WapPushOverSms.shouldParseContentDisposition(var10_11 /* !! */ ));
                var2_2 = var2_2.parse();
            }
            catch (Exception var16_16) {
                var2_2 = new StringBuilder();
                var2_2.append("Unable to parse PDU: ");
                var2_2.append(var16_16.toString());
                Rlog.e((String)"WAP PUSH", (String)var2_2.toString());
                var2_2 = null;
            }
            if (var2_2 != null && var2_2.getMessageType() == 130 && (var16_17 = (NotificationInd)var2_2).getFrom() != null && BlockChecker.isBlocked(this.mContext, var16_17.getFrom().getString(), null)) {
                var3_4.statusCode = 1;
                return var3_4;
            }
            if (var11_12.seekXWapApplicationId(var5_6, var5_6 + var6_7 - 1)) {
                var11_12.decodeXWapApplicationId((int)var11_12.getValue32());
                var16_17 = var11_12.getValueString();
                if (var16_17 == null) {
                    var16_17 = Integer.toString((int)var11_12.getValue32());
                }
                var3_4.wapAppId = var16_17;
                var16_17 = var12_13 == null ? Long.toString(var13_14) : var12_13;
                var3_4.contentType = var16_17;
            }
            var3_4.subId = var10_11 /* !! */ ;
            var3_4.phoneId = var8_9;
            var3_4.parsedPdu = var2_2;
            var3_4.mimeType = var12_13;
            var3_4.transactionId = var4_5;
            var3_4.pduType = var9_10;
            var3_4.header = var15_15;
            var3_4.intentData = var1_1;
            var3_4.contentTypeParameters = var11_12.getContentParameters();
            var3_4.statusCode = -1;
            return var3_4;
        }
        catch (ArrayIndexOutOfBoundsException var2_3) {
            var1_1 = new StringBuilder();
            var1_1.append("ignoring dispatchWapPdu() array index exception: ");
            var1_1.append(var2_3);
            Rlog.e((String)"WAP PUSH", (String)var1_1.toString());
            var3_4.statusCode = 2;
        }
        return var3_4;
    }

    public static int getAppOpsPermissionForIntent(String string) {
        int n = "application/vnd.wap.mms-message".equals(string) ? 18 : 19;
        return n;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private static long getDeliveryOrReadReportThreadId(Context var0, GenericPdu var1_3) {
        block8 : {
            if (var1_3 instanceof DeliveryInd) {
                var2_4 = new String(((DeliveryInd)var1_3).getMessageId());
            } else {
                if (!(var1_3 instanceof ReadOrigInd)) {
                    var0 = new StringBuilder();
                    var0.append("WAP Push data is neither delivery or read report type: ");
                    var0.append(var1_3.getClass().getCanonicalName());
                    Rlog.e((String)"WAP PUSH", (String)var0.toString());
                    return -1L;
                }
                var2_4 = new String(((ReadOrigInd)var1_3).getMessageId());
            }
            var3_5 = null;
            var5_7 = var4_6 = null;
            var1_3 = var3_5;
            var6_8 = var0.getContentResolver();
            var5_7 = var4_6;
            var1_3 = var3_5;
            var7_9 = Telephony.Mms.CONTENT_URI;
            var5_7 = var4_6;
            var1_3 = var3_5;
            var2_4 = DatabaseUtils.sqlEscapeString((String)var2_4);
            var5_7 = var4_6;
            var1_3 = var3_5;
            var8_10 = Integer.toString(128);
            var5_7 = var4_6;
            var1_3 = var3_5;
            var0 = SqliteWrapper.query((Context)var0, (ContentResolver)var6_8, (Uri)var7_9, (String[])new String[]{"thread_id"}, (String)"m_id=? AND m_type=?", (String[])new String[]{var2_4, var8_10}, null);
            if (var0 == null) break block8;
            var5_7 = var0;
            var1_3 = var0;
            if (!var0.moveToFirst()) break block8;
            var5_7 = var0;
            var1_3 = var0;
            var9_11 = var0.getLong(0);
            var0.close();
            return var9_11;
        }
        if (var0 == null) return -1L;
lbl42: // 2 sources:
        do {
            var0.close();
            return -1L;
            break;
        } while (true);
        {
            catch (Throwable var0_1) {
            }
            catch (SQLiteException var0_2) {}
            var5_7 = var1_3;
            {
                Rlog.e((String)"WAP PUSH", (String)"Failed to query delivery or read report thread id", (Throwable)var0_2);
                if (var1_3 == null) return -1L;
                var0 = var1_3;
                ** continue;
            }
        }
        if (var5_7 == null) throw var0_1;
        var5_7.close();
        throw var0_1;
    }

    public static String getPermissionForType(String string) {
        string = "application/vnd.wap.mms-message".equals(string) ? "android.permission.RECEIVE_MMS" : "android.permission.RECEIVE_WAP_PUSH";
        return string;
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
    private static boolean isDuplicateNotification(Context object, NotificationInd notificationInd) {
        Throwable throwable2222;
        Object notificationInd2;
        block5 : {
            block6 : {
                block4 : {
                    Object notificationInd3;
                    byte[] arrby = ((NotificationInd)notificationInd).getContentLocation();
                    if (arrby == null) return false;
                    new String(arrby);
                    Object var3_5 = null;
                    notificationInd2 = notificationInd3 = null;
                    notificationInd = var3_5;
                    ContentResolver contentResolver = object.getContentResolver();
                    notificationInd2 = notificationInd3;
                    notificationInd = var3_5;
                    Uri uri = Telephony.Mms.CONTENT_URI;
                    notificationInd2 = notificationInd3;
                    notificationInd = var3_5;
                    String string = Integer.toString(130);
                    notificationInd2 = notificationInd3;
                    notificationInd = var3_5;
                    notificationInd2 = notificationInd3;
                    notificationInd = var3_5;
                    String string2 = new String(arrby);
                    notificationInd2 = notificationInd3;
                    notificationInd = var3_5;
                    object = SqliteWrapper.query((Context)object, (ContentResolver)contentResolver, (Uri)uri, (String[])new String[]{"_id"}, (String)LOCATION_SELECTION, (String[])new String[]{string, string2}, null);
                    if (object == null) break block4;
                    notificationInd2 = object;
                    notificationInd = object;
                    int n = object.getCount();
                    if (n <= 0) break block4;
                    object.close();
                    return true;
                }
                if (object == null) return false;
                break block6;
                {
                    catch (Throwable throwable2222) {
                        break block5;
                    }
                    catch (SQLiteException sQLiteException) {}
                    notificationInd2 = notificationInd;
                    {
                        Rlog.e((String)TAG, (String)"failed to query existing notification ind", (Throwable)sQLiteException);
                        if (notificationInd == null) return false;
                        object = notificationInd;
                    }
                }
            }
            object.close();
            return false;
        }
        if (notificationInd2 == null) throw throwable2222;
        notificationInd2.close();
        throw throwable2222;
    }

    private static boolean shouldParseContentDisposition(int n) {
        return SmsManager.getSmsManagerForSubscriptionId((int)n).getCarrierConfigValues().getBoolean("supportMmsContentDisposition", true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeInboxMessage(int var1_1, GenericPdu var2_2) {
        if (var2_2 == null) {
            Rlog.e((String)"WAP PUSH", (String)"Invalid PUSH PDU");
        }
        var3_5 = PduPersister.getPduPersister(this.mContext);
        var4_6 = var2_2.getMessageType();
        if (var4_6 == 130) ** GOTO lbl27
        if (var4_6 == 134 || var4_6 == 136) ** GOTO lbl12
        try {
            Log.e((String)"WAP PUSH", (String)"Received unrecognized WAP Push PDU.");
            return;
lbl12: // 1 sources:
            var5_7 = WapPushOverSms.getDeliveryOrReadReportThreadId(this.mContext, (GenericPdu)var2_2);
            if (var5_7 == -1L) {
                Rlog.e((String)"WAP PUSH", (String)"Failed to find delivery or read report's thread id");
                return;
            }
            if ((var2_2 = var3_5.persist((GenericPdu)var2_2, Telephony.Mms.Inbox.CONTENT_URI, true, true, null)) == null) {
                Rlog.e((String)"WAP PUSH", (String)"Failed to persist delivery or read report");
                return;
            }
            var7_8 = new ContentValues(1);
            var7_8.put("thread_id", Long.valueOf(var5_7));
            if (SqliteWrapper.update((Context)this.mContext, (ContentResolver)this.mContext.getContentResolver(), (Uri)var2_2, (ContentValues)var7_8, null, null) == 1) return;
            Rlog.e((String)"WAP PUSH", (String)"Failed to update delivery or read report thread id");
            return;
lbl27: // 1 sources:
            var7_9 = (NotificationInd)var2_2;
            var8_11 = SmsManager.getSmsManagerForSubscriptionId((int)var1_1).getCarrierConfigValues();
            if (var8_11 != null && var8_11.getBoolean("enabledTransID", false) && 61 == (var9_12 = var7_9.getContentLocation())[var9_12.length - 1]) {
                var10_13 = var7_9.getTransactionId();
                var8_11 = new byte[var9_12.length + var10_13.length];
                System.arraycopy(var9_12, 0, var8_11, 0, var9_12.length);
                System.arraycopy(var10_13, 0, var8_11, var9_12.length, var10_13.length);
                var7_9.setContentLocation(var8_11);
            }
            if (!WapPushOverSms.isDuplicateNotification(this.mContext, var7_9)) {
                if (var3_5.persist((GenericPdu)var2_2, Telephony.Mms.Inbox.CONTENT_URI, true, true, null) != null) return;
                Rlog.e((String)"WAP PUSH", (String)"Failed to save MMS WAP push notification ind");
                return;
            }
            var3_5 = new StringBuilder();
            var3_5.append("Skip storing duplicate MMS WAP push notification ind: ");
            var2_2 = new String(var7_9.getContentLocation());
            var3_5.append((String)var2_2);
            Rlog.d((String)"WAP PUSH", (String)var3_5.toString());
            return;
        }
        catch (RuntimeException var2_3) {
            Log.e((String)"WAP PUSH", (String)"Unexpected RuntimeException in persisting MMS WAP push data", (Throwable)var2_3);
            return;
        }
        catch (MmsException var2_4) {
            var7_10 = new StringBuilder();
            var7_10.append("Failed to save MMS WAP push data: type=");
            var7_10.append(var4_6);
            Log.e((String)"WAP PUSH", (String)var7_10.toString(), (Throwable)var2_4);
        }
    }

    @UnsupportedAppUsage
    public int dispatchWapPdu(byte[] arrby, BroadcastReceiver broadcastReceiver, InboundSmsHandler inboundSmsHandler) {
        return this.dispatchWapPdu(arrby, broadcastReceiver, inboundSmsHandler, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int dispatchWapPdu(byte[] iWapPushManager, BroadcastReceiver broadcastReceiver, InboundSmsHandler inboundSmsHandler, String string) {
        DecodedResult decodedResult;
        Intent intent;
        block19 : {
            decodedResult = this.decodeWapPdu((byte[])iWapPushManager, inboundSmsHandler);
            if (decodedResult.statusCode != -1) {
                return decodedResult.statusCode;
            }
            if (SmsManager.getDefault().getAutoPersisting()) {
                this.writeInboxMessage(decodedResult.subId, decodedResult.parsedPdu);
            }
            if (decodedResult.wapAppId != null) {
                boolean bl;
                block18 : {
                    boolean bl2 = true;
                    iWapPushManager = this.mWapPushManager;
                    if (iWapPushManager == null) {
                        bl = bl2;
                        break block18;
                    }
                    // MONITORENTER : this
                    this.mDeviceIdleController.addPowerSaveTempWhitelistAppForMms(this.mWapPushManagerPackage, 0, "mms-mgr");
                    // MONITOREXIT : this
                    try {
                        intent = new Intent();
                        intent.putExtra("transactionId", decodedResult.transactionId);
                        intent.putExtra("pduType", decodedResult.pduType);
                        intent.putExtra("header", decodedResult.header);
                        intent.putExtra("data", decodedResult.intentData);
                        intent.putExtra("contentTypeParameters", decodedResult.contentTypeParameters);
                        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)decodedResult.phoneId);
                        if (!TextUtils.isEmpty((CharSequence)string)) {
                            intent.putExtra("address", string);
                        }
                        int n = iWapPushManager.processMessage(decodedResult.wapAppId, decodedResult.contentType, intent);
                        bl = bl2;
                        if ((n & 1) <= 0) break block18;
                        bl = bl2;
                        if ((32768 & n) != 0) break block18;
                        return 1;
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
                if (!bl) {
                    return 1;
                }
            }
            if (decodedResult.mimeType == null) {
                return 2;
            }
            intent = new Intent("android.provider.Telephony.WAP_PUSH_DELIVER");
            intent.setType(decodedResult.mimeType);
            intent.putExtra("transactionId", decodedResult.transactionId);
            intent.putExtra("pduType", decodedResult.pduType);
            intent.putExtra("header", decodedResult.header);
            intent.putExtra("data", decodedResult.intentData);
            intent.putExtra("contentTypeParameters", decodedResult.contentTypeParameters);
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)decodedResult.phoneId);
            if (!TextUtils.isEmpty((CharSequence)string)) {
                intent.putExtra("address", string);
            }
            if ((iWapPushManager = SmsApplication.getDefaultMmsApplication((Context)this.mContext, (boolean)true)) != null) {
                intent.setComponent((ComponentName)iWapPushManager);
                try {
                    long l = this.mDeviceIdleController.addPowerSaveTempWhitelistAppForMms(iWapPushManager.getPackageName(), 0, "mms-app");
                    iWapPushManager = BroadcastOptions.makeBasic();
                    iWapPushManager.setTemporaryAppWhitelistDuration(l);
                    iWapPushManager = iWapPushManager.toBundle();
                    break block19;
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            iWapPushManager = null;
        }
        inboundSmsHandler.dispatchIntent(intent, WapPushOverSms.getPermissionForType(decodedResult.mimeType), WapPushOverSms.getAppOpsPermissionForIntent(decodedResult.mimeType), (Bundle)iWapPushManager, broadcastReceiver, UserHandle.SYSTEM);
        return -1;
    }

    public void dispose() {
        if (this.mWapPushManager != null) {
            this.mContext.unbindService((ServiceConnection)this);
        } else {
            Rlog.e((String)TAG, (String)"dispose: not bound to a wappush manager");
        }
    }

    @UnsupportedAppUsage
    public boolean isWapPushForMms(byte[] object, InboundSmsHandler inboundSmsHandler) {
        object = this.decodeWapPdu((byte[])object, inboundSmsHandler);
        boolean bl = object.statusCode == -1 && "application/vnd.wap.mms-message".equals(object.mimeType);
        return bl;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.mWapPushManager = IWapPushManager.Stub.asInterface((IBinder)iBinder);
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.mWapPushManager = null;
    }

    private class BindServiceThread
    extends Thread {
        private final Context context;

        private BindServiceThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            WapPushOverSms.this.bindWapPushManagerService(this.context);
        }
    }

    private final class DecodedResult {
        String contentType;
        HashMap<String, String> contentTypeParameters;
        byte[] header;
        byte[] intentData;
        String mimeType;
        GenericPdu parsedPdu;
        int pduType;
        int phoneId;
        int statusCode;
        int subId;
        int transactionId;
        String wapAppId;

        private DecodedResult() {
        }
    }

}


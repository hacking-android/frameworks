/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.app.role.IRoleManager
 *  android.app.role.IRoleManager$Stub
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.os.ServiceManager$ServiceNotFoundException
 *  android.provider.Telephony
 *  android.provider.Telephony$Sms
 *  android.provider.Telephony$Sms$Intents
 *  android.telephony.IFinancialSmsCallback
 *  android.telephony.SmsMessage
 *  android.text.TextUtils
 *  android.util.ArrayMap
 *  android.util.Base64
 *  android.util.Log
 *  com.android.internal.annotations.GuardedBy
 *  com.android.internal.util.Preconditions
 */
package com.android.internal.telephony;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.app.role.IRoleManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Telephony;
import android.telephony.IFinancialSmsCallback;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.telephony.PackageBasedTokenUtil;
import com.android.internal.util.Preconditions;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AppSmsManager {
    private static final String LOG_TAG = "AppSmsManager";
    private static final long TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(5L);
    private final Context mContext;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private final Map<String, AppRequestInfo> mPackageMap = new ArrayMap();
    private final SecureRandom mRandom = new SecureRandom();
    @GuardedBy(value={"mLock"})
    private final Map<String, AppRequestInfo> mTokenMap = new ArrayMap();

    public AppSmsManager(Context context) {
        this.mContext = context;
    }

    private void addRequestLocked(AppRequestInfo appRequestInfo) {
        this.mTokenMap.put(appRequestInfo.token, appRequestInfo);
        this.mPackageMap.put(appRequestInfo.packageName, appRequestInfo);
    }

    private String extractMessage(Intent intent2) {
        SmsMessage[] arrsmsMessage = Telephony.Sms.Intents.getMessagesFromIntent((Intent)intent2);
        if (arrsmsMessage == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (SmsMessage smsMessage : arrsmsMessage) {
            if (smsMessage == null || smsMessage.getMessageBody() == null) continue;
            stringBuilder.append(smsMessage.getMessageBody());
        }
        return stringBuilder.toString();
    }

    private AppRequestInfo findAppRequestInfoSmsIntentLocked(String string) {
        for (String string2 : this.mTokenMap.keySet()) {
            if (!string.trim().contains(string2) || !this.hasPrefix(string2, string)) continue;
            return this.mTokenMap.get(string2);
        }
        return null;
    }

    private String generateNonce() {
        byte[] arrby = new byte[8];
        this.mRandom.nextBytes(arrby);
        return Base64.encodeToString((byte[])arrby, (int)11);
    }

    private boolean hasPrefix(String arrstring, String string) {
        arrstring = this.mTokenMap.get(arrstring);
        if (TextUtils.isEmpty((CharSequence)arrstring.prefixes)) {
            return true;
        }
        arrstring = arrstring.prefixes.split(",");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!string.startsWith(arrstring[i])) continue;
            return true;
        }
        return false;
    }

    private void removeExpiredTokenLocked() {
        long l = System.currentTimeMillis();
        for (String string : this.mTokenMap.keySet()) {
            AppRequestInfo object = this.mTokenMap.get(string);
            if (!object.packageBasedToken || l - TIMEOUT_MILLIS <= object.timestamp) continue;
            try {
                Intent canceledException = new Intent();
                canceledException = canceledException.putExtra("android.telephony.extra.STATUS", 1).addFlags(2097152);
                object.pendingIntent.send(this.mContext, 0, canceledException);
            }
            catch (PendingIntent.CanceledException canceledException) {
                // empty catch block
            }
            this.removeRequestLocked(object);
        }
    }

    private void removeRequestLocked(AppRequestInfo appRequestInfo) {
        this.mTokenMap.remove(appRequestInfo.token);
        this.mPackageMap.remove(appRequestInfo.packageName);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String createAppSpecificSmsToken(String string, PendingIntent pendingIntent) {
        ((AppOpsManager)this.mContext.getSystemService("appops")).checkPackage(Binder.getCallingUid(), string);
        String string2 = this.generateNonce();
        Object object = this.mLock;
        synchronized (object) {
            if (this.mPackageMap.containsKey(string)) {
                this.removeRequestLocked(this.mPackageMap.get(string));
            }
            AppRequestInfo appRequestInfo = new AppRequestInfo(string, pendingIntent, string2);
            this.addRequestLocked(appRequestInfo);
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String createAppSpecificSmsTokenWithPackageInfo(int n, String string, String string2, PendingIntent pendingIntent) {
        Preconditions.checkStringNotEmpty((CharSequence)string, (Object)"callingPackageName cannot be null or empty.");
        Preconditions.checkNotNull((Object)pendingIntent, (Object)"intent cannot be null");
        ((AppOpsManager)this.mContext.getSystemService("appops")).checkPackage(Binder.getCallingUid(), string);
        String string3 = PackageBasedTokenUtil.generateToken(this.mContext, string);
        if (string3 == null) return string3;
        Object object = this.mLock;
        synchronized (object) {
            if (this.mPackageMap.containsKey(string)) {
                this.removeRequestLocked(this.mPackageMap.get(string));
            }
            AppRequestInfo appRequestInfo = new AppRequestInfo(string, pendingIntent, string3, string2, n, true);
            this.addRequestLocked(appRequestInfo);
            return string3;
        }
    }

    public void getSmsMessagesForFinancialApp(String string, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) {
        try {
            IRoleManager.Stub.asInterface((IBinder)ServiceManager.getServiceOrThrow((String)"role")).getSmsMessagesForFinancialApp(string, bundle, iFinancialSmsCallback);
        }
        catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
            Log.e((String)LOG_TAG, (String)"Service not found.");
        }
        catch (RemoteException remoteException) {
            Log.e((String)LOG_TAG, (String)"Receive RemoteException.");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleSmsReceivedIntent(Intent intent) {
        if (intent.getAction() != "android.provider.Telephony.SMS_DELIVER") {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Got intent with incorrect action: ");
            stringBuilder.append(intent.getAction());
            Log.wtf((String)LOG_TAG, (String)stringBuilder.toString());
            return false;
        }
        Object object = this.mLock;
        synchronized (object) {
            this.removeExpiredTokenLocked();
            String string = this.extractMessage(intent);
            if (TextUtils.isEmpty((CharSequence)string)) {
                return false;
            }
            AppRequestInfo appRequestInfo = this.findAppRequestInfoSmsIntentLocked(string);
            if (appRequestInfo == null) {
                return false;
            }
            try {
                Intent intent2 = new Intent();
                intent = intent2.putExtras(intent.getExtras()).putExtra("android.telephony.extra.STATUS", 0).putExtra("android.telephony.extra.SMS_MESSAGE", string).putExtra("android.telephony.extra.SIM_SUBSCRIPTION_ID", appRequestInfo.subId).addFlags(2097152);
                appRequestInfo.pendingIntent.send(this.mContext, 0, intent);
                this.removeRequestLocked(appRequestInfo);
            }
            catch (PendingIntent.CanceledException canceledException) {
                this.removeRequestLocked(appRequestInfo);
                return false;
            }
            return true;
        }
    }

    private final class AppRequestInfo {
        public final boolean packageBasedToken;
        public final String packageName;
        public final PendingIntent pendingIntent;
        public final String prefixes;
        public final int subId;
        public final long timestamp;
        public final String token;

        AppRequestInfo(String string, PendingIntent pendingIntent, String string2) {
            this(string, pendingIntent, string2, null, -1, false);
        }

        AppRequestInfo(String string, PendingIntent pendingIntent, String string2, String string3, int n, boolean bl) {
            this.packageName = string;
            this.pendingIntent = pendingIntent;
            this.token = string2;
            this.timestamp = System.currentTimeMillis();
            this.prefixes = string3;
            this.subId = n;
            this.packageBasedToken = bl;
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.ActivityManager
 *  android.app.ActivityThread
 *  android.app.AppOpsManager
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.Context
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.ServiceManager
 *  android.telephony.IFinancialSmsCallback
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  com.android.internal.telephony.ISmsImplBase
 *  com.android.internal.telephony.SmsRawData
 *  com.android.internal.telephony.TelephonyPermissions
 *  com.android.internal.util.DumpUtils
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ServiceManager;
import android.telephony.IFinancialSmsCallback;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.AppSmsManager;
import com.android.internal.telephony.BtSmsInterfaceManager;
import com.android.internal.telephony.ISmsImplBase;
import com.android.internal.telephony.IccSmsInterfaceManager;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.SmsPermissions;
import com.android.internal.telephony.SmsRawData;
import com.android.internal.telephony.SmsUsageMonitor;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.TelephonyPermissions;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class SmsController
extends ISmsImplBase {
    static final String LOG_TAG = "SmsController";
    private final Context mContext;

    protected SmsController(Context context) {
        this.mContext = context;
        if (ServiceManager.getService((String)"isms") == null) {
            ServiceManager.addService((String)"isms", (IBinder)this);
        }
    }

    @UnsupportedAppUsage
    private IccSmsInterfaceManager getIccSmsInterfaceManager(int n) {
        return this.getPhone(n).getIccSmsInterfaceManager();
    }

    private Phone getPhone(int n) {
        Phone phone;
        Phone phone2 = phone = PhoneFactory.getPhone(SubscriptionManager.getPhoneId((int)n));
        if (phone == null) {
            phone2 = PhoneFactory.getDefaultPhone();
        }
        return phone2;
    }

    private SmsPermissions getSmsPermissions(int n) {
        Phone phone = this.getPhone(n);
        Context context = this.mContext;
        return new SmsPermissions(phone, context, (AppOpsManager)context.getSystemService("appops"));
    }

    private SubscriptionInfo getSubscriptionInfo(int n) {
        return ((SubscriptionManager)this.mContext.getSystemService("telephony_subscription_service")).getActiveSubscriptionInfo(n);
    }

    private boolean isBluetoothSubscription(SubscriptionInfo subscriptionInfo) {
        boolean bl = true;
        if (subscriptionInfo == null || subscriptionInfo.getSubscriptionType() != 1) {
            bl = false;
        }
        return bl;
    }

    private void sendBluetoothText(SubscriptionInfo subscriptionInfo, String string, String string2, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        new BtSmsInterfaceManager().sendText(string, string2, pendingIntent, pendingIntent2, subscriptionInfo);
    }

    private void sendDataForSubscriberWithSelfPermissionsInternal(int n, String charSequence, String string, String string2, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendDataWithSelfPermissions((String)charSequence, string, string2, n2, arrby, pendingIntent, pendingIntent2, bl);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendText iccSmsIntMgr is null for Subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntent(pendingIntent, 1);
        }
    }

    @UnsupportedAppUsage
    private void sendErrorInPendingIntent(PendingIntent pendingIntent, int n) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(n);
            }
            catch (PendingIntent.CanceledException canceledException) {
                // empty catch block
            }
        }
    }

    @UnsupportedAppUsage
    private void sendErrorInPendingIntents(List<PendingIntent> object, int n) {
        if (object == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            this.sendErrorInPendingIntent((PendingIntent)object.next(), n);
        }
    }

    private void sendIccText(int n, String charSequence, String string, String string2, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendText((String)charSequence, string, string2, string3, pendingIntent, pendingIntent2, bl);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendTextForSubscriber iccSmsIntMgr is null for Subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntent(pendingIntent, 1);
        }
    }

    private void sendTextForSubscriberWithSelfPermissionsInternal(int n, String charSequence, String string, String string2, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, boolean bl2) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendTextWithSelfPermissions((String)charSequence, string, string2, string3, pendingIntent, pendingIntent2, bl, bl2);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendText iccSmsIntMgr is null for Subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntent(pendingIntent, 1);
        }
    }

    public int checkSmsShortCodeDestination(int n, String string, String string2, String string3) {
        if (!TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)this.getPhone(n).getContext(), (int)n, (String)string, (String)"checkSmsShortCodeDestination")) {
            return 0;
        }
        long l = Binder.clearCallingIdentity();
        try {
            n = this.getPhone((int)n).mSmsUsageMonitor.checkDestination(string2, string3);
            return n;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    @UnsupportedAppUsage
    public boolean copyMessageToIccEfForSubscriber(int n, String charSequence, int n2, byte[] arrby, byte[] arrby2) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            return iccSmsInterfaceManager.copyMessageToIccEf((String)charSequence, n2, arrby, arrby2);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("copyMessageToIccEfForSubscriber iccSmsIntMgr is null for Subscription: ");
        ((StringBuilder)charSequence).append(n);
        Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        return false;
    }

    public String createAppSpecificSmsToken(int n, String string, PendingIntent pendingIntent) {
        return this.getPhone(n).getAppSmsManager().createAppSpecificSmsToken(string, pendingIntent);
    }

    public String createAppSpecificSmsTokenWithPackageInfo(int n, String string, String string2, PendingIntent pendingIntent) {
        return this.getPhone(n).getAppSmsManager().createAppSpecificSmsTokenWithPackageInfo(n, string, string2, pendingIntent);
    }

    @UnsupportedAppUsage
    public boolean disableCellBroadcastForSubscriber(int n, int n2, int n3) {
        return this.disableCellBroadcastRangeForSubscriber(n, n2, n2, n3);
    }

    @UnsupportedAppUsage
    public boolean disableCellBroadcastRangeForSubscriber(int n, int n2, int n3, int n4) {
        Object object = this.getIccSmsInterfaceManager(n);
        if (object != null) {
            return ((IccSmsInterfaceManager)object).disableCellBroadcastRange(n2, n3, n4);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("disableCellBroadcastRangeForSubscriber iccSmsIntMgr is null for Subscription:");
        ((StringBuilder)object).append(n);
        Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
        return false;
    }

    protected void dump(FileDescriptor fileDescriptor, PrintWriter arrphone, String[] arrstring) {
        if (!DumpUtils.checkDumpPermission((Context)this.mContext, (String)LOG_TAG, (PrintWriter)arrphone)) {
            return;
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)arrphone, "    ");
        arrphone = PhoneFactory.getPhones();
        int n = arrphone.length;
        for (int i = 0; i < n; ++i) {
            int n2 = arrphone[i].getSubId();
            indentingPrintWriter.println(String.format("SmsManager for subId = %d:", n2));
            indentingPrintWriter.increaseIndent();
            if (this.getIccSmsInterfaceManager(n2) != null) {
                this.getIccSmsInterfaceManager(n2).dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.flush();
    }

    @UnsupportedAppUsage
    public boolean enableCellBroadcastForSubscriber(int n, int n2, int n3) {
        return this.enableCellBroadcastRangeForSubscriber(n, n2, n2, n3);
    }

    @UnsupportedAppUsage
    public boolean enableCellBroadcastRangeForSubscriber(int n, int n2, int n3, int n4) {
        Object object = this.getIccSmsInterfaceManager(n);
        if (object != null) {
            return ((IccSmsInterfaceManager)object).enableCellBroadcastRange(n2, n3, n4);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("enableCellBroadcastRangeForSubscriber iccSmsIntMgr is null for Subscription: ");
        ((StringBuilder)object).append(n);
        Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
        return false;
    }

    @UnsupportedAppUsage
    public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int n, String charSequence) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            return iccSmsInterfaceManager.getAllMessagesFromIccEf((String)charSequence);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("getAllMessagesFromIccEfForSubscriber iccSmsIntMgr is null for Subscription: ");
        ((StringBuilder)charSequence).append(n);
        Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        return null;
    }

    @UnsupportedAppUsage
    public String getImsSmsFormatForSubscriber(int n) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            return iccSmsInterfaceManager.getImsSmsFormat();
        }
        Rlog.e((String)LOG_TAG, (String)"getImsSmsFormatForSubscriber iccSmsIntMgr is null");
        return null;
    }

    @UnsupportedAppUsage
    public int getPreferredSmsSubscription() {
        long l;
        block3 : {
            int[] arrn;
            int n = SubscriptionController.getInstance().getDefaultSmsSubId();
            if (SubscriptionManager.isValidSubscriptionId((int)n)) {
                return n;
            }
            l = Binder.clearCallingIdentity();
            try {
                arrn = SubscriptionController.getInstance().getActiveSubIdList(true);
                if (arrn.length != 1) break block3;
            }
            catch (Throwable throwable) {
                Binder.restoreCallingIdentity((long)l);
                throw throwable;
            }
            n = arrn[0];
            Binder.restoreCallingIdentity((long)l);
            return n;
        }
        Binder.restoreCallingIdentity((long)l);
        return -1;
    }

    public int getPremiumSmsPermission(String string) {
        return this.getPremiumSmsPermissionForSubscriber(this.getPreferredSmsSubscription(), string);
    }

    public int getPremiumSmsPermissionForSubscriber(int n, String string) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            return iccSmsInterfaceManager.getPremiumSmsPermission(string);
        }
        Rlog.e((String)LOG_TAG, (String)"getPremiumSmsPermissionForSubscriber iccSmsIntMgr is null");
        return 0;
    }

    public void getSmsMessagesForFinancialApp(int n, String string, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) {
        this.getPhone(n).getAppSmsManager().getSmsMessagesForFinancialApp(string, bundle, iFinancialSmsCallback);
    }

    public void injectSmsPduForSubscriber(int n, byte[] arrby, String string, PendingIntent pendingIntent) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.injectSmsPdu(arrby, string, pendingIntent);
        } else {
            Rlog.e((String)LOG_TAG, (String)"injectSmsPduForSubscriber iccSmsIntMgr is null");
            this.sendErrorInPendingIntent(pendingIntent, 2);
        }
    }

    @UnsupportedAppUsage
    public boolean isImsSmsSupportedForSubscriber(int n) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            return iccSmsInterfaceManager.isImsSmsSupported();
        }
        Rlog.e((String)LOG_TAG, (String)"isImsSmsSupportedForSubscriber iccSmsIntMgr is null");
        return false;
    }

    public boolean isSMSPromptEnabled() {
        return PhoneFactory.isSMSPromptEnabled();
    }

    public boolean isSmsSimPickActivityNeeded(int n) {
        long l;
        block6 : {
            Object object = ActivityThread.currentApplication().getApplicationContext();
            ActivityManager activityManager = (ActivityManager)object.getSystemService(ActivityManager.class);
            int n2 = activityManager != null && activityManager.getUidImportance(Binder.getCallingUid()) == 100 ? 1 : 0;
            if (n2 == 0) {
                Rlog.d((String)LOG_TAG, (String)"isSmsSimPickActivityNeeded: calling process not foreground. Suppressing activity.");
                return false;
            }
            activityManager = (TelephonyManager)object.getSystemService("phone");
            l = Binder.clearCallingIdentity();
            object = SubscriptionManager.from((Context)object).getActiveSubscriptionInfoList();
            if (object == null) break block6;
            int n3 = object.size();
            for (n2 = 0; n2 < n3; ++n2) {
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo)object.get(n2);
                if (subscriptionInfo == null || subscriptionInfo.getSubscriptionId() != n) continue;
                return false;
            }
            if (n3 > 0 && activityManager.getSimCount() > 1) {
                return true;
            }
        }
        return false;
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    @UnsupportedAppUsage
    public void sendDataForSubscriber(int n, String charSequence, String string, String string2, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendData((String)charSequence, string, string2, n2, arrby, pendingIntent, pendingIntent2);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendDataForSubscriber iccSmsIntMgr is null for Subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntent(pendingIntent, 1);
        }
    }

    public void sendDataForSubscriberWithSelfPermissions(int n, String string, String string2, String string3, int n2, byte[] arrby, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        this.sendDataForSubscriberWithSelfPermissionsInternal(n, string, string2, string3, n2, arrby, pendingIntent, pendingIntent2, false);
    }

    public void sendMultipartTextForSubscriber(int n, String charSequence, String string, String string2, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendMultipartText((String)charSequence, string, string2, list, list2, list3, bl);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendMultipartTextForSubscriber iccSmsIntMgr is null for Subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntents(list2, 1);
        }
    }

    public void sendMultipartTextForSubscriberWithOptions(int n, String charSequence, String string, String string2, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean bl, int n2, boolean bl2, int n3) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendMultipartTextWithOptions((String)charSequence, string, string2, list, list2, list3, bl, n2, bl2, n3);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendMultipartTextWithOptions iccSmsIntMgr is null for Subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntents(list2, 1);
        }
    }

    public void sendStoredMultipartText(int n, String charSequence, Uri uri, String string, List<PendingIntent> list, List<PendingIntent> list2) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendStoredMultipartText((String)charSequence, uri, string, list, list2);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendStoredMultipartText iccSmsIntMgr is null for subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntents(list, 1);
        }
    }

    public void sendStoredText(int n, String charSequence, Uri uri, String string, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendStoredText((String)charSequence, uri, string, pendingIntent, pendingIntent2);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendStoredText iccSmsIntMgr is null for subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntent(pendingIntent, 1);
        }
    }

    public void sendTextForSubscriber(int n, String string, String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        if (!this.getSmsPermissions(n).checkCallingCanSendText(bl, string, "Sending SMS message")) {
            this.sendErrorInPendingIntent(pendingIntent, 1);
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            SubscriptionInfo subscriptionInfo = this.getSubscriptionInfo(n);
            if (this.isBluetoothSubscription(subscriptionInfo)) {
                this.sendBluetoothText(subscriptionInfo, string2, string4, pendingIntent, pendingIntent2);
            } else {
                this.sendIccText(n, string, string2, string3, string4, pendingIntent, pendingIntent2, bl);
            }
            return;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public void sendTextForSubscriberWithOptions(int n, String charSequence, String string, String string2, String string3, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl, int n2, boolean bl2, int n3) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.sendTextWithOptions((String)charSequence, string, string2, string3, pendingIntent, pendingIntent2, bl, n2, bl2, n3);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("sendTextWithOptions iccSmsIntMgr is null for Subscription: ");
            ((StringBuilder)charSequence).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntent(pendingIntent, 1);
        }
    }

    public void sendTextForSubscriberWithSelfPermissions(int n, String string, String string2, String string3, String string4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean bl) {
        this.sendTextForSubscriberWithSelfPermissionsInternal(n, string, string2, string3, string4, pendingIntent, pendingIntent2, bl, false);
    }

    public void sendVisualVoicemailSmsForSubscriber(String string, int n, String string2, int n2, String arrby, PendingIntent pendingIntent) {
        if (n2 == 0) {
            this.sendTextForSubscriberWithSelfPermissionsInternal(n, string, string2, null, (String)arrby, pendingIntent, null, false, true);
        } else {
            arrby = arrby.getBytes(StandardCharsets.UTF_8);
            this.sendDataForSubscriberWithSelfPermissionsInternal(n, string, string2, null, (short)n2, arrby, pendingIntent, null, true);
        }
    }

    public void setPremiumSmsPermission(String string, int n) {
        this.setPremiumSmsPermissionForSubscriber(this.getPreferredSmsSubscription(), string, n);
    }

    public void setPremiumSmsPermissionForSubscriber(int n, String string, int n2) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            iccSmsInterfaceManager.setPremiumSmsPermission(string, n2);
        } else {
            Rlog.e((String)LOG_TAG, (String)"setPremiumSmsPermissionForSubscriber iccSmsIntMgr is null");
        }
    }

    @UnsupportedAppUsage
    public boolean updateMessageOnIccEfForSubscriber(int n, String charSequence, int n2, int n3, byte[] arrby) {
        IccSmsInterfaceManager iccSmsInterfaceManager = this.getIccSmsInterfaceManager(n);
        if (iccSmsInterfaceManager != null) {
            return iccSmsInterfaceManager.updateMessageOnIccEf((String)charSequence, n2, n3, arrby);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("updateMessageOnIccEfForSubscriber iccSmsIntMgr is null for Subscription: ");
        ((StringBuilder)charSequence).append(n);
        Rlog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        return false;
    }
}


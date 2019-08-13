/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ComponentInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.os.ServiceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.service.euicc.DownloadSubscriptionResult
 *  android.service.euicc.GetDefaultDownloadableSubscriptionListResult
 *  android.service.euicc.GetDownloadableSubscriptionMetadataResult
 *  android.service.euicc.GetEuiccProfileInfoListResult
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.UiccAccessRule
 *  android.telephony.UiccCardInfo
 *  android.telephony.euicc.DownloadableSubscription
 *  android.telephony.euicc.EuiccInfo
 *  android.text.TextUtils
 *  android.util.Log
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 *  com.android.internal.telephony.euicc.IEuiccController
 *  com.android.internal.telephony.euicc.IEuiccController$Stub
 */
package com.android.internal.telephony.euicc;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.ServiceManager;
import android.provider.Settings;
import android.service.euicc.DownloadSubscriptionResult;
import android.service.euicc.GetDefaultDownloadableSubscriptionListResult;
import android.service.euicc.GetDownloadableSubscriptionMetadataResult;
import android.service.euicc.GetEuiccProfileInfoListResult;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccAccessRule;
import android.telephony.UiccCardInfo;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.euicc.EuiccConnector;
import com.android.internal.telephony.euicc.EuiccOperation;
import com.android.internal.telephony.euicc.IEuiccController;
import com.android.internal.telephony.euicc._$$Lambda$EuiccController$aZ8yEHh32lS1TctCOFmVEa57ekc;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class EuiccController
extends IEuiccController.Stub {
    private static final int ERROR = 2;
    private static final String EXTRA_EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTION = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTION";
    @VisibleForTesting
    static final String EXTRA_OPERATION = "operation";
    private static final int OK = 0;
    private static final int RESOLVABLE_ERROR = 1;
    private static final String TAG = "EuiccController";
    private static EuiccController sInstance;
    private final AppOpsManager mAppOpsManager;
    private final EuiccConnector mConnector;
    private final Context mContext;
    private final PackageManager mPackageManager;
    private final SubscriptionManager mSubscriptionManager;
    private final TelephonyManager mTelephonyManager;

    private EuiccController(Context context) {
        this(context, new EuiccConnector(context));
        ServiceManager.addService((String)"econtroller", (IBinder)this);
    }

    @VisibleForTesting
    public EuiccController(Context context, EuiccConnector euiccConnector) {
        this.mContext = context;
        this.mConnector = euiccConnector;
        this.mSubscriptionManager = (SubscriptionManager)context.getSystemService("telephony_subscription_service");
        this.mTelephonyManager = (TelephonyManager)context.getSystemService("phone");
        this.mAppOpsManager = (AppOpsManager)context.getSystemService("appops");
        this.mPackageManager = context.getPackageManager();
    }

    private static <T> T awaitResult(CountDownLatch countDownLatch, AtomicReference<T> atomicReference) {
        try {
            countDownLatch.await();
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
        return atomicReference.get();
    }

    private String blockingGetEidFromEuiccService(int n) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference atomicReference = new AtomicReference();
        this.mConnector.getEid(n, new EuiccConnector.GetEidCommandCallback(){

            @Override
            public void onEuiccServiceUnavailable() {
                countDownLatch.countDown();
            }

            @Override
            public void onGetEidComplete(String string) {
                atomicReference.set(string);
                countDownLatch.countDown();
            }
        });
        return (String)EuiccController.awaitResult(countDownLatch, atomicReference);
    }

    private EuiccInfo blockingGetEuiccInfoFromEuiccService(int n) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference atomicReference = new AtomicReference();
        this.mConnector.getEuiccInfo(n, new EuiccConnector.GetEuiccInfoCommandCallback(){

            @Override
            public void onEuiccServiceUnavailable() {
                countDownLatch.countDown();
            }

            @Override
            public void onGetEuiccInfoComplete(EuiccInfo euiccInfo) {
                atomicReference.set(euiccInfo);
                countDownLatch.countDown();
            }
        });
        return (EuiccInfo)EuiccController.awaitResult(countDownLatch, atomicReference);
    }

    private int blockingGetOtaStatusFromEuiccService(int n) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(5);
        this.mConnector.getOtaStatus(n, new EuiccConnector.GetOtaStatusCommandCallback(){

            @Override
            public void onEuiccServiceUnavailable() {
                countDownLatch.countDown();
            }

            @Override
            public void onGetOtaStatusComplete(int n) {
                atomicReference.set(n);
                countDownLatch.countDown();
            }
        });
        return EuiccController.awaitResult(countDownLatch, atomicReference);
    }

    private boolean callerCanReadPhoneStatePrivileged() {
        boolean bl = this.mContext.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE") == 0;
        return bl;
    }

    private boolean callerCanWriteEmbeddedSubscriptions() {
        boolean bl = this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_EMBEDDED_SUBSCRIPTIONS") == 0;
        return bl;
    }

    private boolean canManageActiveSubscriptionOnTargetSim(int n, String string) {
        List list2 = this.mSubscriptionManager.getActiveSubscriptionInfoList(false);
        if (list2 != null && list2.size() != 0) {
            for (List list2 : list2) {
                if (n != -1 && list2.getCardId() != n || !list2.isEmbedded() || !this.mSubscriptionManager.canManageSubscription((SubscriptionInfo)list2, string)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean canManageSubscriptionOnTargetSim(int n, String string) {
        Object object = this.mSubscriptionManager;
        boolean bl = false;
        if ((object = object.getActiveSubscriptionInfoList(false)) != null && object.size() != 0) {
            if (this.supportMultiActiveSlots()) {
                List list = this.mTelephonyManager.getUiccCardsInfo();
                if (list != null && !list.isEmpty()) {
                    boolean bl2;
                    block8 : {
                        boolean bl3 = false;
                        Iterator iterator = list.iterator();
                        do {
                            bl2 = bl3;
                            if (!iterator.hasNext()) break block8;
                        } while ((list = (UiccCardInfo)iterator.next()) == null || list.getCardId() != n || !list.isEuicc());
                        bl2 = true;
                    }
                    if (!bl2) {
                        Log.i((String)TAG, (String)"The target SIM is not an eUICC.");
                        return false;
                    }
                    object = object.iterator();
                    while (object.hasNext()) {
                        list = (SubscriptionInfo)object.next();
                        if (!list.isEmbedded() || list.getCardId() != n) continue;
                        return this.mSubscriptionManager.canManageSubscription((SubscriptionInfo)list, string);
                    }
                    if (this.mTelephonyManager.checkCarrierPrivilegesForPackageAnyPhone(string) == 1) {
                        bl = true;
                    }
                    return bl;
                }
                return false;
            }
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                object = (SubscriptionInfo)iterator.next();
                if (!object.isEmbedded() || !this.mSubscriptionManager.canManageSubscription((SubscriptionInfo)object, string)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean checkCarrierPrivilegeInMetadata(DownloadableSubscription arruiccAccessRule, String string) {
        Object var3_4 = null;
        List list = arruiccAccessRule.getAccessRules();
        arruiccAccessRule = var3_4;
        if (list != null) {
            arruiccAccessRule = list.toArray((T[])new UiccAccessRule[list.size()]);
        }
        if (arruiccAccessRule == null) {
            Log.e((String)TAG, (String)"No access rules but caller is unprivileged");
            return false;
        }
        try {
            string = this.mPackageManager.getPackageInfo(string, 64);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)TAG, (String)"Calling package valid but gone");
            return false;
        }
        for (int i = 0; i < arruiccAccessRule.length; ++i) {
            if (arruiccAccessRule[i].getCarrierPrivilegeStatus((PackageInfo)string) != 1) continue;
            Log.i((String)TAG, (String)"Calling package has carrier privilege to this profile");
            return true;
        }
        Log.e((String)TAG, (String)"Calling package doesn't have carrier privilege to this profile");
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static EuiccController get() {
        if (sInstance != null) return sInstance;
        synchronized (EuiccController.class) {
            if (sInstance != null) {
                return sInstance;
            }
            IllegalStateException illegalStateException = new IllegalStateException("get() called before init()");
            throw illegalStateException;
        }
    }

    private SubscriptionInfo getSubscriptionForSubscriptionId(int n) {
        List list = this.mSubscriptionManager.getAvailableSubscriptionInfoList();
        int n2 = list != null ? list.size() : 0;
        for (int i = 0; i < n2; ++i) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo)list.get(i);
            if (n != subscriptionInfo.getSubscriptionId()) continue;
            return subscriptionInfo;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static EuiccController init(Context object) {
        synchronized (EuiccController.class) {
            if (sInstance == null) {
                EuiccController euiccController;
                sInstance = euiccController = new EuiccController((Context)object);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("init() called multiple times! sInstance = ");
                ((StringBuilder)object).append((Object)sInstance);
                Log.wtf((String)TAG, (String)((StringBuilder)object).toString());
            }
            return sInstance;
        }
    }

    private boolean supportMultiActiveSlots() {
        int n = this.mTelephonyManager.getPhoneCount();
        boolean bl = true;
        if (n <= 1) {
            bl = false;
        }
        return bl;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void addResolutionIntent(Intent intent, String string, String string2, int n, boolean bl, EuiccOperation euiccOperation, int n2) {
        Intent intent2 = new Intent("android.telephony.euicc.action.RESOLVE_ERROR");
        intent2.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_ACTION", string);
        intent2.putExtra("android.service.euicc.extra.RESOLUTION_CALLING_PACKAGE", string2);
        intent2.putExtra("android.service.euicc.extra.RESOLVABLE_ERRORS", n);
        intent2.putExtra("android.service.euicc.extra.RESOLUTION_CARD_ID", n2);
        intent2.putExtra("android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE_RETRIED", bl);
        intent2.putExtra(EXTRA_OPERATION, (Parcelable)euiccOperation);
        intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_INTENT", (Parcelable)PendingIntent.getActivity((Context)this.mContext, (int)0, (Intent)intent2, (int)1073741824));
    }

    public GetEuiccProfileInfoListResult blockingGetEuiccProfileInfoList(int n) {
        Object object = new CountDownLatch(1);
        final AtomicReference atomicReference = new AtomicReference();
        this.mConnector.getEuiccProfileInfoList(n, new EuiccConnector.GetEuiccProfileInfoListCommandCallback((CountDownLatch)object){
            final /* synthetic */ CountDownLatch val$latch;
            {
                this.val$latch = countDownLatch;
            }

            @Override
            public void onEuiccServiceUnavailable() {
                this.val$latch.countDown();
            }

            @Override
            public void onListComplete(GetEuiccProfileInfoListResult getEuiccProfileInfoListResult) {
                atomicReference.set(getEuiccProfileInfoListResult);
                this.val$latch.countDown();
            }
        });
        try {
            ((CountDownLatch)object).await();
        }
        catch (InterruptedException interruptedException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("blockingGetEuiccInfoFromEuiccService got InterruptedException e: ");
            ((StringBuilder)object).append(interruptedException);
            Log.e((String)TAG, (String)((StringBuilder)object).toString());
            Thread.currentThread().interrupt();
        }
        return (GetEuiccProfileInfoListResult)atomicReference.get();
    }

    public void continueOperation(int n, Intent object, Bundle bundle) {
        if (this.callerCanWriteEmbeddedSubscriptions()) {
            long l = Binder.clearCallingIdentity();
            try {
                EuiccOperation euiccOperation = (EuiccOperation)object.getParcelableExtra(EXTRA_OPERATION);
                if (euiccOperation != null) {
                    euiccOperation.continueOperation(n, bundle, (PendingIntent)object.getParcelableExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT"));
                    return;
                }
                object = new IllegalArgumentException("Invalid resolution intent");
                throw object;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
        throw new SecurityException("Must have WRITE_EMBEDDED_SUBSCRIPTIONS to continue operation");
    }

    public void deleteSubscription(int n, int n2, String charSequence, PendingIntent pendingIntent) {
        long l;
        SubscriptionInfo subscriptionInfo;
        block8 : {
            boolean bl;
            block7 : {
                bl = this.callerCanWriteEmbeddedSubscriptions();
                this.mAppOpsManager.checkPackage(Binder.getCallingUid(), (String)charSequence);
                l = Binder.clearCallingIdentity();
                subscriptionInfo = this.getSubscriptionForSubscriptionId(n2);
                if (subscriptionInfo != null) break block7;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Cannot delete nonexistent subscription: ");
                ((StringBuilder)charSequence).append(n2);
                Log.e((String)TAG, (String)((StringBuilder)charSequence).toString());
                this.sendResult(pendingIntent, 2, null);
                return;
            }
            if (!bl) {
                if (this.mSubscriptionManager.canManageSubscription(subscriptionInfo, (String)charSequence)) break block8;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("No permissions: ");
                ((StringBuilder)charSequence).append(n2);
                Log.e((String)TAG, (String)((StringBuilder)charSequence).toString());
                this.sendResult(pendingIntent, 2, null);
                Binder.restoreCallingIdentity((long)l);
                return;
            }
        }
        this.deleteSubscriptionPrivileged(n, subscriptionInfo.getIccId(), pendingIntent);
        Binder.restoreCallingIdentity((long)l);
        return;
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    void deleteSubscriptionPrivileged(int n, String string, final PendingIntent pendingIntent) {
        this.mConnector.deleteSubscription(n, string, new EuiccConnector.DeleteCommandCallback(){

            @Override
            public void onDeleteComplete(int n) {
                Intent intent = new Intent();
                if (n != 0) {
                    intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", n);
                    EuiccController.this.sendResult(pendingIntent, 2, intent);
                    return;
                }
                EuiccController.this.refreshSubscriptionsAndSendResult(pendingIntent, 0, intent);
            }

            @Override
            public void onEuiccServiceUnavailable() {
                EuiccController.this.sendResult(pendingIntent, 2, null);
            }
        });
    }

    public void downloadSubscription(int n, DownloadableSubscription downloadableSubscription, boolean bl, String string, Bundle bundle, PendingIntent pendingIntent) {
        this.downloadSubscription(n, downloadableSubscription, bl, string, false, bundle, pendingIntent);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void downloadSubscription(int n, DownloadableSubscription object, boolean bl, String string, boolean bl2, Bundle object2, PendingIntent pendingIntent) {
        block17 : {
            block18 : {
                bl3 = this.callerCanWriteEmbeddedSubscriptions();
                this.mAppOpsManager.checkPackage(Binder.getCallingUid(), string);
                l = Binder.clearCallingIdentity();
                if (bl3) {
                    try {
                        this.downloadSubscriptionPrivileged(n, l, (DownloadableSubscription)object, bl, bl2, string, (Bundle)object2, pendingIntent);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    Binder.restoreCallingIdentity((long)l);
                    return;
                }
                l2 = l;
                bl3 = this.canManageSubscriptionOnTargetSim(n, string);
                if (!bl3) ** GOTO lbl26
                object2 = this.mConnector;
                {
                    catch (Throwable throwable) {
                    }
                }
                downloadSubscriptionGetMetadataCommandCallback = new DownloadSubscriptionGetMetadataCommandCallback(l2, (DownloadableSubscription)object, bl, string, bl2, pendingIntent, false);
                {
                    catch (Throwable throwable) {
                    }
                }
                object2.getDownloadableSubscriptionMetadata(n, (DownloadableSubscription)object, bl2, downloadSubscriptionGetMetadataCommandCallback);
                break block18;
lbl26: // 1 sources:
                Log.i((String)"EuiccController", (String)"Caller can't manage subscription on target SIM. Ask user's consent first");
                object2 = new Intent();
                try {
                    object = EuiccOperation.forDownloadNoPrivilegesOrDeactivateSimCheckMetadata(l2, (DownloadableSubscription)object, bl, string);
                }
                catch (Throwable throwable) {
                    break block17;
                }
                try {
                    this.addResolutionIntent((Intent)object2, "android.service.euicc.action.RESOLVE_NO_PRIVILEGES", string, 0, false, (EuiccOperation)object, n);
                }
                catch (Throwable throwable) {
                    break block17;
                }
                try {
                    this.sendResult(pendingIntent, 1, (Intent)object2);
                }
                catch (Throwable throwable) {
                    break block17;
                }
            }
            Binder.restoreCallingIdentity((long)l);
            return;
            catch (Throwable throwable) {}
            break block17;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        Binder.restoreCallingIdentity((long)l);
        throw var2_10;
    }

    void downloadSubscriptionPrivileged(final int n, final long l, final DownloadableSubscription downloadableSubscription, final boolean bl, boolean bl2, final String string, Bundle bundle, final PendingIntent pendingIntent) {
        this.mConnector.downloadSubscription(n, downloadableSubscription, bl, bl2, bundle, new EuiccConnector.DownloadCommandCallback(){

            @Override
            public void onDownloadComplete(DownloadSubscriptionResult object) {
                Intent intent = new Intent();
                int n2 = object.getResult();
                if (n2 != -2) {
                    if (n2 != -1) {
                        if (n2 != 0) {
                            n2 = 2;
                            intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", object.getResult());
                        } else {
                            n2 = 0;
                            Settings.Global.putInt((ContentResolver)EuiccController.this.mContext.getContentResolver(), (String)"euicc_provisioned", (int)1);
                            intent.putExtra(EuiccController.EXTRA_EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTION, (Parcelable)downloadableSubscription);
                            if (!bl) {
                                EuiccController.this.refreshSubscriptionsAndSendResult(pendingIntent, 0, intent);
                                return;
                            }
                        }
                    } else {
                        object = EuiccController.this;
                        String string2 = string;
                        object.addResolutionIntent(intent, "android.service.euicc.action.RESOLVE_DEACTIVATE_SIM", string2, 0, false, EuiccOperation.forDownloadDeactivateSim(l, downloadableSubscription, bl, string2), n);
                        n2 = 1;
                    }
                } else {
                    boolean bl2 = !TextUtils.isEmpty((CharSequence)downloadableSubscription.getConfirmationCode());
                    if (object.getResolvableErrors() != 0) {
                        EuiccController.this.addResolutionIntent(intent, "android.service.euicc.action.RESOLVE_RESOLVABLE_ERRORS", string, object.getResolvableErrors(), bl2, EuiccOperation.forDownloadResolvableErrors(l, downloadableSubscription, bl, string, object.getResolvableErrors()), n);
                    } else {
                        EuiccController euiccController = EuiccController.this;
                        object = string;
                        euiccController.addResolutionIntent(intent, "android.service.euicc.action.RESOLVE_CONFIRMATION_CODE", (String)object, 0, bl2, EuiccOperation.forDownloadConfirmationCode(l, downloadableSubscription, bl, (String)object), n);
                    }
                    n2 = 1;
                }
                EuiccController.this.sendResult(pendingIntent, n2, intent);
            }

            @Override
            public void onEuiccServiceUnavailable() {
                EuiccController.this.sendResult(pendingIntent, 2, null);
            }
        });
    }

    void downloadSubscriptionPrivilegedCheckMetadata(int n, long l, DownloadableSubscription downloadableSubscription, boolean bl, boolean bl2, String string, Bundle bundle, PendingIntent pendingIntent) {
        this.mConnector.getDownloadableSubscriptionMetadata(n, downloadableSubscription, bl2, new DownloadSubscriptionGetMetadataCommandCallback(l, downloadableSubscription, bl, string, bl2, pendingIntent, true));
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Requires DUMP");
        long l = Binder.clearCallingIdentity();
        try {
            this.mConnector.dump(fileDescriptor, printWriter, arrstring);
            return;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public void eraseSubscriptions(int n, final PendingIntent pendingIntent) {
        if (this.callerCanWriteEmbeddedSubscriptions()) {
            long l = Binder.clearCallingIdentity();
            try {
                EuiccConnector euiccConnector = this.mConnector;
                EuiccConnector.EraseCommandCallback eraseCommandCallback = new EuiccConnector.EraseCommandCallback(){

                    @Override
                    public void onEraseComplete(int n) {
                        Intent intent = new Intent();
                        if (n != 0) {
                            intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", n);
                            EuiccController.this.sendResult(pendingIntent, 2, intent);
                            return;
                        }
                        EuiccController.this.refreshSubscriptionsAndSendResult(pendingIntent, 0, intent);
                    }

                    @Override
                    public void onEuiccServiceUnavailable() {
                        EuiccController.this.sendResult(pendingIntent, 2, null);
                    }
                };
                euiccConnector.eraseSubscriptions(n, eraseCommandCallback);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
        throw new SecurityException("Must have WRITE_EMBEDDED_SUBSCRIPTIONS to erase subscriptions");
    }

    public void getDefaultDownloadableSubscriptionList(int n, String string, PendingIntent pendingIntent) {
        this.getDefaultDownloadableSubscriptionList(n, false, string, pendingIntent);
    }

    void getDefaultDownloadableSubscriptionList(int n, boolean bl, String string, PendingIntent pendingIntent) {
        if (this.callerCanWriteEmbeddedSubscriptions()) {
            this.mAppOpsManager.checkPackage(Binder.getCallingUid(), string);
            long l = Binder.clearCallingIdentity();
            try {
                EuiccConnector euiccConnector = this.mConnector;
                GetDefaultListCommandCallback getDefaultListCommandCallback = new GetDefaultListCommandCallback(l, string, pendingIntent);
                euiccConnector.getDefaultDownloadableSubscriptionList(n, bl, getDefaultListCommandCallback);
                return;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
        throw new SecurityException("Must have WRITE_EMBEDDED_SUBSCRIPTIONS to get default list");
    }

    public void getDownloadableSubscriptionMetadata(int n, DownloadableSubscription downloadableSubscription, String string, PendingIntent pendingIntent) {
        this.getDownloadableSubscriptionMetadata(n, downloadableSubscription, false, string, pendingIntent);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void getDownloadableSubscriptionMetadata(int n, DownloadableSubscription downloadableSubscription, boolean bl, String string, PendingIntent pendingIntent) {
        void var2_5;
        long l;
        block4 : {
            if (!this.callerCanWriteEmbeddedSubscriptions()) throw new SecurityException("Must have WRITE_EMBEDDED_SUBSCRIPTIONS to get metadata");
            this.mAppOpsManager.checkPackage(Binder.getCallingUid(), string);
            l = Binder.clearCallingIdentity();
            EuiccConnector euiccConnector = this.mConnector;
            GetMetadataCommandCallback getMetadataCommandCallback = new GetMetadataCommandCallback(l, downloadableSubscription, string, pendingIntent);
            try {
                euiccConnector.getDownloadableSubscriptionMetadata(n, downloadableSubscription, bl, getMetadataCommandCallback);
            }
            catch (Throwable throwable) {
                break block4;
            }
            Binder.restoreCallingIdentity((long)l);
            return;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        Binder.restoreCallingIdentity((long)l);
        throw var2_5;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String getEid(int var1_1, String var2_2) {
        var3_4 = this.callerCanReadPhoneStatePrivileged();
        var4_5 = Binder.clearCallingIdentity();
        if (var3_4) ** GOTO lbl13
        try {
            if (!this.canManageSubscriptionOnTargetSim(var1_1, (String)var2_2)) {
                var2_2 = new StringBuilder();
                var2_2.append("Must have carrier privileges on subscription to read EID for cardId=");
                var2_2.append(var1_1);
                var6_6 = new SecurityException(var2_2.toString());
                throw var6_6;
            }
lbl13: // 3 sources:
            var2_2 = this.blockingGetEidFromEuiccService(var1_1);
            return var2_2;
        }
        finally {
            Binder.restoreCallingIdentity((long)var4_5);
        }
    }

    public EuiccInfo getEuiccInfo(int n) {
        long l = Binder.clearCallingIdentity();
        try {
            EuiccInfo euiccInfo = this.blockingGetEuiccInfoFromEuiccService(n);
            return euiccInfo;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public int getOtaStatus(int n) {
        if (this.callerCanWriteEmbeddedSubscriptions()) {
            long l = Binder.clearCallingIdentity();
            try {
                n = this.blockingGetOtaStatusFromEuiccService(n);
                return n;
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        }
        throw new SecurityException("Must have WRITE_EMBEDDED_SUBSCRIPTIONS to get OTA status");
    }

    public /* synthetic */ void lambda$refreshSubscriptionsAndSendResult$0$EuiccController(PendingIntent pendingIntent, int n, Intent intent) {
        this.sendResult(pendingIntent, n, intent);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void refreshSubscriptionsAndSendResult(PendingIntent pendingIntent, int n, Intent intent) {
        SubscriptionController.getInstance().requestEmbeddedSubscriptionInfoListRefresh(new _$$Lambda$EuiccController$aZ8yEHh32lS1TctCOFmVEa57ekc(this, pendingIntent, n, intent));
    }

    public void retainSubscriptionsForFactoryReset(int n, final PendingIntent pendingIntent) {
        this.mContext.enforceCallingPermission("android.permission.MASTER_CLEAR", "Must have MASTER_CLEAR to retain subscriptions for factory reset");
        long l = Binder.clearCallingIdentity();
        try {
            EuiccConnector euiccConnector = this.mConnector;
            EuiccConnector.RetainSubscriptionsCommandCallback retainSubscriptionsCommandCallback = new EuiccConnector.RetainSubscriptionsCommandCallback(){

                @Override
                public void onEuiccServiceUnavailable() {
                    EuiccController.this.sendResult(pendingIntent, 2, null);
                }

                @Override
                public void onRetainSubscriptionsComplete(int n) {
                    Intent intent = new Intent();
                    if (n != 0) {
                        int n2 = 2;
                        intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", n);
                        n = n2;
                    } else {
                        n = 0;
                    }
                    EuiccController.this.sendResult(pendingIntent, n, intent);
                }
            };
            euiccConnector.retainSubscriptions(n, retainSubscriptionsCommandCallback);
            return;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void sendOtaStatusChangedBroadcast() {
        Intent intent = new Intent("android.telephony.euicc.action.OTA_STATUS_CHANGED");
        EuiccConnector euiccConnector = this.mConnector;
        euiccConnector = EuiccConnector.findBestComponent(this.mContext.getPackageManager());
        if (euiccConnector != null) {
            intent.setPackage(((ComponentInfo)euiccConnector).packageName);
        }
        this.mContext.sendBroadcast(intent, "android.permission.WRITE_EMBEDDED_SUBSCRIPTIONS");
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void sendResult(PendingIntent pendingIntent, int n, Intent intent) {
        try {
            pendingIntent.send(this.mContext, n, intent);
        }
        catch (PendingIntent.CanceledException canceledException) {
            // empty catch block
        }
    }

    public void startOtaUpdatingIfNecessary() {
        this.startOtaUpdatingIfNecessary(this.mTelephonyManager.getCardIdForDefaultEuicc());
    }

    public void startOtaUpdatingIfNecessary(int n) {
        this.mConnector.startOtaIfNecessary(n, new EuiccConnector.OtaStatusChangedCallback(){

            @Override
            public void onEuiccServiceUnavailable() {
            }

            @Override
            public void onOtaStatusChanged(int n) {
                EuiccController.this.sendOtaStatusChangedBroadcast();
            }
        });
    }

    public void switchToSubscription(int n, int n2, String string, PendingIntent pendingIntent) {
        this.switchToSubscription(n, n2, false, string, pendingIntent);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void switchToSubscription(int var1_1, int var2_2, boolean var3_3, String var4_4, PendingIntent var5_9) {
        block21 : {
            block18 : {
                block16 : {
                    block17 : {
                        block20 : {
                            block15 : {
                                block19 : {
                                    block14 : {
                                        var6_10 = this.callerCanWriteEmbeddedSubscriptions();
                                        this.mAppOpsManager.checkPackage(Binder.getCallingUid(), (String)var4_4);
                                        var7_11 = Binder.clearCallingIdentity();
                                        if (var6_10) {
                                            var3_3 = true;
                                        }
                                        var9_12 = false;
                                        if (var2_2 != -1) break block19;
                                        if (!var6_10) {
                                            if (this.canManageActiveSubscriptionOnTargetSim(var1_1, (String)var4_4)) break block14;
                                            Log.e((String)"EuiccController", (String)"Not permitted to switch to empty subscription");
                                            this.sendResult(var5_9, 2, null);
                                            Binder.restoreCallingIdentity((long)var7_11);
                                            return;
                                        }
                                    }
                                    var9_12 = true;
                                    var10_13 /* !! */  = null;
                                    ** GOTO lbl63
                                }
                                var10_13 /* !! */  = this.getSubscriptionForSubscriptionId(var2_2);
                                if (var10_13 /* !! */  != null) break block15;
                                try {
                                    var4_4 = new StringBuilder();
                                    var4_4.append("Cannot switch to nonexistent sub: ");
                                    var4_4.append(var2_2);
                                    Log.e((String)"EuiccController", (String)var4_4.toString());
                                    this.sendResult(var5_9, 2, null);
                                }
                                catch (Throwable var4_5) {}
                                Binder.restoreCallingIdentity((long)var7_11);
                                return;
                            }
                            if (!var6_10) break block20;
                            var9_12 = true;
                            ** GOTO lbl62
                        }
                        var6_10 = this.mSubscriptionManager.canManageSubscription(var10_13 /* !! */ , (String)var4_4);
                        if (var6_10) break block16;
                        var4_4 = new StringBuilder();
                        var4_4.append("Not permitted to switch to sub: ");
                        var4_4.append(var2_2);
                        Log.e((String)"EuiccController", (String)var4_4.toString());
                        this.sendResult(var5_9, 2, null);
                        break block17;
                        break block21;
                    }
                    Binder.restoreCallingIdentity((long)var7_11);
                    return;
                }
                if (this.canManageSubscriptionOnTargetSim(var1_1, (String)var4_4)) {
                    var9_12 = true;
                }
lbl62: // 4 sources:
                var10_13 /* !! */  = var10_13 /* !! */ .getIccId();
lbl63: // 2 sources:
                if (var9_12) break block18;
                var11_14 = new Intent();
                var10_13 /* !! */  = EuiccOperation.forSwitchNoPrivileges(var7_11, var2_2, (String)var4_4);
                try {
                    this.addResolutionIntent(var11_14, "android.service.euicc.action.RESOLVE_NO_PRIVILEGES", (String)var4_4, 0, false, (EuiccOperation)var10_13 /* !! */ , var1_1);
                    this.sendResult(var5_9, 1, var11_14);
                }
                catch (Throwable var4_6) {}
                Binder.restoreCallingIdentity((long)var7_11);
                return;
            }
            var12_15 = var7_11;
            this.switchToSubscriptionPrivileged(var1_1, var12_15, var2_2, (String)var10_13 /* !! */ , var3_3, (String)var4_4, var5_9);
            Binder.restoreCallingIdentity((long)var12_15);
            return;
            break block21;
            catch (Throwable var4_7) {
                // empty catch block
            }
        }
        Binder.restoreCallingIdentity((long)var7_11);
        throw var4_8;
    }

    void switchToSubscriptionPrivileged(final int n, final long l, final int n2, String string, boolean bl, final String string2, final PendingIntent pendingIntent) {
        this.mConnector.switchToSubscription(n, string, bl, new EuiccConnector.SwitchCommandCallback(){

            @Override
            public void onEuiccServiceUnavailable() {
                EuiccController.this.sendResult(pendingIntent, 2, null);
            }

            @Override
            public void onSwitchComplete(int n3) {
                Intent intent = new Intent();
                if (n3 != -1) {
                    if (n3 != 0) {
                        int n22 = 2;
                        intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", n3);
                        n3 = n22;
                    } else {
                        n3 = 0;
                    }
                } else {
                    EuiccController euiccController = EuiccController.this;
                    String string = string2;
                    euiccController.addResolutionIntent(intent, "android.service.euicc.action.RESOLVE_DEACTIVATE_SIM", string, 0, false, EuiccOperation.forSwitchDeactivateSim(l, n2, string), n);
                    n3 = 1;
                }
                EuiccController.this.sendResult(pendingIntent, n3, intent);
            }
        });
    }

    void switchToSubscriptionPrivileged(int n, long l, int n2, boolean bl, String string, PendingIntent pendingIntent) {
        String string2 = null;
        SubscriptionInfo subscriptionInfo = this.getSubscriptionForSubscriptionId(n2);
        if (subscriptionInfo != null) {
            string2 = subscriptionInfo.getIccId();
        }
        this.switchToSubscriptionPrivileged(n, l, n2, string2, bl, string, pendingIntent);
    }

    public void updateSubscriptionNickname(int n, int n2, String charSequence, String object, final PendingIntent pendingIntent) {
        long l;
        Object object2;
        block8 : {
            boolean bl;
            block7 : {
                bl = this.callerCanWriteEmbeddedSubscriptions();
                this.mAppOpsManager.checkPackage(Binder.getCallingUid(), (String)object);
                l = Binder.clearCallingIdentity();
                object2 = this.getSubscriptionForSubscriptionId(n2);
                if (object2 != null) break block7;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Cannot update nickname to nonexistent sub: ");
                ((StringBuilder)charSequence).append(n2);
                Log.e((String)TAG, (String)((StringBuilder)charSequence).toString());
                this.sendResult(pendingIntent, 2, null);
                return;
            }
            if (!bl) {
                if (this.mSubscriptionManager.canManageSubscription(object2, (String)object)) break block8;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("No permissions: ");
                ((StringBuilder)charSequence).append(n2);
                Log.e((String)TAG, (String)((StringBuilder)charSequence).toString());
                this.sendResult(pendingIntent, 2, null);
                Binder.restoreCallingIdentity((long)l);
                return;
            }
        }
        object = this.mConnector;
        object2 = object2.getIccId();
        EuiccConnector.UpdateNicknameCommandCallback updateNicknameCommandCallback = new EuiccConnector.UpdateNicknameCommandCallback(){

            @Override
            public void onEuiccServiceUnavailable() {
                EuiccController.this.sendResult(pendingIntent, 2, null);
            }

            @Override
            public void onUpdateNicknameComplete(int n) {
                Intent intent = new Intent();
                if (n != 0) {
                    intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", n);
                    EuiccController.this.sendResult(pendingIntent, 2, intent);
                    return;
                }
                EuiccController.this.refreshSubscriptionsAndSendResult(pendingIntent, 0, intent);
            }
        };
        ((EuiccConnector)((Object)object)).updateSubscriptionNickname(n, (String)object2, (String)charSequence, updateNicknameCommandCallback);
        Binder.restoreCallingIdentity((long)l);
        return;
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    class DownloadSubscriptionGetMetadataCommandCallback
    extends GetMetadataCommandCallback {
        private final boolean mForceDeactivateSim;
        private final boolean mSwitchAfterDownload;
        private final boolean mWithUserConsent;

        DownloadSubscriptionGetMetadataCommandCallback(long l, DownloadableSubscription downloadableSubscription, boolean bl, String string, boolean bl2, PendingIntent pendingIntent, boolean bl3) {
            super(l, downloadableSubscription, string, pendingIntent);
            this.mSwitchAfterDownload = bl;
            this.mForceDeactivateSim = bl2;
            this.mWithUserConsent = bl3;
        }

        @Override
        public void onGetMetadataComplete(int n, GetDownloadableSubscriptionMetadataResult getDownloadableSubscriptionMetadataResult) {
            DownloadableSubscription downloadableSubscription = getDownloadableSubscriptionMetadataResult.getDownloadableSubscription();
            if (this.mWithUserConsent) {
                if (getDownloadableSubscriptionMetadataResult.getResult() != 0) {
                    super.onGetMetadataComplete(n, getDownloadableSubscriptionMetadataResult);
                    return;
                }
                if (EuiccController.this.checkCarrierPrivilegeInMetadata(downloadableSubscription, this.mCallingPackage)) {
                    EuiccController.this.downloadSubscriptionPrivileged(n, this.mCallingToken, downloadableSubscription, this.mSwitchAfterDownload, this.mForceDeactivateSim, this.mCallingPackage, null, this.mCallbackIntent);
                } else {
                    Log.e((String)EuiccController.TAG, (String)"Caller does not have carrier privilege in metadata.");
                    EuiccController.this.sendResult(this.mCallbackIntent, 2, null);
                }
            } else {
                if (getDownloadableSubscriptionMetadataResult.getResult() == -1) {
                    getDownloadableSubscriptionMetadataResult = new Intent();
                    EuiccController.this.addResolutionIntent((Intent)getDownloadableSubscriptionMetadataResult, "android.service.euicc.action.RESOLVE_DEACTIVATE_SIM", this.mCallingPackage, 0, false, EuiccOperation.forDownloadNoPrivilegesOrDeactivateSimCheckMetadata(this.mCallingToken, this.mSubscription, this.mSwitchAfterDownload, this.mCallingPackage), n);
                    EuiccController.this.sendResult(this.mCallbackIntent, 1, (Intent)getDownloadableSubscriptionMetadataResult);
                    return;
                }
                if (getDownloadableSubscriptionMetadataResult.getResult() != 0) {
                    super.onGetMetadataComplete(n, getDownloadableSubscriptionMetadataResult);
                    return;
                }
                if (EuiccController.this.checkCarrierPrivilegeInMetadata(downloadableSubscription, this.mCallingPackage)) {
                    EuiccController.this.downloadSubscriptionPrivileged(n, this.mCallingToken, downloadableSubscription, this.mSwitchAfterDownload, this.mForceDeactivateSim, this.mCallingPackage, null, this.mCallbackIntent);
                } else {
                    Log.e((String)EuiccController.TAG, (String)"Caller is not permitted to download this profile per metadata");
                    EuiccController.this.sendResult(this.mCallbackIntent, 2, null);
                }
            }
        }
    }

    class GetDefaultListCommandCallback
    implements EuiccConnector.GetDefaultListCommandCallback {
        final PendingIntent mCallbackIntent;
        final String mCallingPackage;
        final long mCallingToken;

        GetDefaultListCommandCallback(long l, String string, PendingIntent pendingIntent) {
            this.mCallingToken = l;
            this.mCallingPackage = string;
            this.mCallbackIntent = pendingIntent;
        }

        @Override
        public void onEuiccServiceUnavailable() {
            EuiccController.this.sendResult(this.mCallbackIntent, 2, null);
        }

        @Override
        public void onGetDefaultListComplete(int n, GetDefaultDownloadableSubscriptionListResult object) {
            Intent intent = new Intent();
            int n2 = object.getResult();
            if (n2 != -1) {
                if (n2 != 0) {
                    n = 2;
                    intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", object.getResult());
                } else {
                    n2 = 0;
                    object = object.getDownloadableSubscriptions();
                    n = n2;
                    if (object != null) {
                        n = n2;
                        if (object.size() > 0) {
                            intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTIONS", (Parcelable[])object.toArray((T[])new DownloadableSubscription[object.size()]));
                            n = n2;
                        }
                    }
                }
            } else {
                EuiccController euiccController = EuiccController.this;
                object = this.mCallingPackage;
                euiccController.addResolutionIntent(intent, "android.service.euicc.action.RESOLVE_DEACTIVATE_SIM", (String)object, 0, false, EuiccOperation.forGetDefaultListDeactivateSim(this.mCallingToken, (String)object), n);
                n = 1;
            }
            EuiccController.this.sendResult(this.mCallbackIntent, n, intent);
        }
    }

    class GetMetadataCommandCallback
    implements EuiccConnector.GetMetadataCommandCallback {
        protected final PendingIntent mCallbackIntent;
        protected final String mCallingPackage;
        protected final long mCallingToken;
        protected final DownloadableSubscription mSubscription;

        GetMetadataCommandCallback(long l, DownloadableSubscription downloadableSubscription, String string, PendingIntent pendingIntent) {
            this.mCallingToken = l;
            this.mSubscription = downloadableSubscription;
            this.mCallingPackage = string;
            this.mCallbackIntent = pendingIntent;
        }

        protected EuiccOperation getOperationForDeactivateSim() {
            return EuiccOperation.forGetMetadataDeactivateSim(this.mCallingToken, this.mSubscription, this.mCallingPackage);
        }

        @Override
        public void onEuiccServiceUnavailable() {
            EuiccController.this.sendResult(this.mCallbackIntent, 2, null);
        }

        @Override
        public void onGetMetadataComplete(int n, GetDownloadableSubscriptionMetadataResult getDownloadableSubscriptionMetadataResult) {
            Intent intent = new Intent();
            int n2 = getDownloadableSubscriptionMetadataResult.getResult();
            if (n2 != -1) {
                if (n2 != 0) {
                    n = 2;
                    intent.putExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", getDownloadableSubscriptionMetadataResult.getResult());
                } else {
                    n = 0;
                    intent.putExtra(EuiccController.EXTRA_EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTION, (Parcelable)getDownloadableSubscriptionMetadataResult.getDownloadableSubscription());
                }
            } else {
                EuiccController.this.addResolutionIntent(intent, "android.service.euicc.action.RESOLVE_DEACTIVATE_SIM", this.mCallingPackage, 0, false, this.getOperationForDeactivateSim(), n);
                n = 1;
            }
            EuiccController.this.sendResult(this.mCallbackIntent, n, intent);
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$1TnOMFYcM13ZTJNoLjxguPwVcxw
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$1zkPy06BwndFkKrGCUI1ORIPJcI
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$2WGP2Bp11k7_Xwi1N4YefElOUuM
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$2xgrYNleR8FFzFT8hEQx3mDtZ8g
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$9_e7IQZG40sfOlFgD3_7E7x3p4o
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$AAs5l6UPqOJI6iOy7O7wnhNgpN4
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$EYZUPU0CYhRoptGCGJ9y78u-jQM
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$LX6rN0XZFTVXkDiHGVCozgs8kHU
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$P0j9hvO3e-UE9_1i1QM_ujl8Bpo
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$Pb4HmeqsjasrNaXBByGh_-CFogk
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$UaKjkq7sTW3Fbf04O086aBFm63M
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$VgStcgP2F9IDb29Rx_E2o89A-7U
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$ZOtVAnuhxrXl2L906I6eTOentP0
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$_djiy1W26lRIJyfoQefqkIQNgSU
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$bWluhZvk2X-dQ0UidKfdpd0kwuw
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$dmWm-chcWksZlUJPg5OfrbagSrA
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$hh4N6_N4-PPm_vWjCdCRvS8--Cw
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$oLIrumQtrxqYONQeIeqNtbJdJMU
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$qSXnUMuIwAZ0TQjtyVEfznh1w8o
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$qVe7IcEgdBIfOarHqDJP3ePBBcI
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$rpyQeO7zACcc5v4krwU9_qRMHL8
 *  com.android.internal.telephony.IPhoneSubInfo
 *  com.android.internal.telephony.IPhoneSubInfo$Stub
 *  com.android.internal.telephony.TelephonyPermissions
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.IPhoneSubInfo;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.TelephonyPermissions;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$1TnOMFYcM13ZTJNoLjxguPwVcxw;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$1_6zFa_5X___HsO5oSaupKDtHL0;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$1zkPy06BwndFkKrGCUI1ORIPJcI;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$2WGP2Bp11k7_Xwi1N4YefElOUuM;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$2xgrYNleR8FFzFT8hEQx3mDtZ8g;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$9_e7IQZG40sfOlFgD3_7E7x3p4o;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$AAs5l6UPqOJI6iOy7O7wnhNgpN4;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$AjZFvwh3Ujx5W3fleFNksc6bLf0;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$ChCf_gnGN3K5prBkykg6tWs0aTk;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$EYZUPU0CYhRoptGCGJ9y78u_jQM;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$Ja9yTBcEYPqTRBIP_hL0otixVeE;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$LX6rN0XZFTVXkDiHGVCozgs8kHU;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$NO5_HxAafVP54fe9chLZKTACeyU;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$OJMEn1lB_IZwTxTEU9sWCr__XKs;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$P0j9hvO3e_UE9_1i1QM_ujl8Bpo;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$PONge0j2mBi_ILbtJD_7euF0uoM;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$Pb4HmeqsjasrNaXBByGh__CFogk;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$UaKjkq7sTW3Fbf04O086aBFm63M;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$VgStcgP2F9IDb29Rx_E2o89A_7U;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$ZOtVAnuhxrXl2L906I6eTOentP0;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$_djiy1W26lRIJyfoQefqkIQNgSU;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$bWluhZvk2X_dQ0UidKfdpd0kwuw;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$dmWm_chcWksZlUJPg5OfrbagSrA;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$hh4N6_N4_PPm_vWjCdCRvS8__Cw;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$oLIrumQtrxqYONQeIeqNtbJdJMU;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$qSXnUMuIwAZ0TQjtyVEfznh1w8o;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$qVe7IcEgdBIfOarHqDJP3ePBBcI;
import com.android.internal.telephony._$$Lambda$PhoneSubInfoController$rpyQeO7zACcc5v4krwU9_qRMHL8;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IsimRecords;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;

public class PhoneSubInfoController
extends IPhoneSubInfo.Stub {
    private static final boolean DBG = true;
    private static final String TAG = "PhoneSubInfoController";
    private static final boolean VDBG = false;
    private final AppOpsManager mAppOps;
    @UnsupportedAppUsage
    private final Context mContext;
    @UnsupportedAppUsage
    private final Phone[] mPhone;

    public PhoneSubInfoController(Context context, Phone[] arrphone) {
        this.mPhone = arrphone;
        if (ServiceManager.getService((String)"iphonesubinfo") == null) {
            ServiceManager.addService((String)"iphonesubinfo", (IBinder)this);
        }
        this.mContext = context;
        this.mAppOps = (AppOpsManager)this.mContext.getSystemService("appops");
    }

    private <T> T callPhoneMethodForPhoneIdWithReadDeviceIdentifiersCheck(int n, String string, String string2, CallPhoneMethodHelper<T> callPhoneMethodHelper) {
        Phone phone;
        int n2 = n;
        if (!SubscriptionManager.isValidPhoneId((int)n)) {
            n2 = 0;
        }
        if ((phone = this.mPhone[n2]) == null) {
            return null;
        }
        if (!TelephonyPermissions.checkCallingOrSelfReadDeviceIdentifiers((Context)this.mContext, (int)phone.getSubId(), (String)string, (String)string2)) {
            return null;
        }
        long l = Binder.clearCallingIdentity();
        try {
            string = callPhoneMethodHelper.callMethod(phone);
            return (T)string;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    private <T> T callPhoneMethodForSubIdWithModifyCheck(int n, String string, String string2, CallPhoneMethodHelper<T> callPhoneMethodHelper) {
        return this.callPhoneMethodWithPermissionCheck(n, null, string2, callPhoneMethodHelper, new _$$Lambda$PhoneSubInfoController$OJMEn1lB_IZwTxTEU9sWCr__XKs(this));
    }

    private <T> T callPhoneMethodForSubIdWithPrivilegedCheck(int n, String string, CallPhoneMethodHelper<T> callPhoneMethodHelper) {
        return this.callPhoneMethodWithPermissionCheck(n, null, string, callPhoneMethodHelper, new _$$Lambda$PhoneSubInfoController$PONge0j2mBi_ILbtJD_7euF0uoM(this, string));
    }

    private <T> T callPhoneMethodForSubIdWithReadCheck(int n, String string, String string2, CallPhoneMethodHelper<T> callPhoneMethodHelper) {
        return this.callPhoneMethodWithPermissionCheck(n, string, string2, callPhoneMethodHelper, (PermissionCheckHelper)_$$Lambda$PhoneSubInfoController$qSXnUMuIwAZ0TQjtyVEfznh1w8o.INSTANCE);
    }

    private <T> T callPhoneMethodForSubIdWithReadDeviceIdentifiersCheck(int n, String string, String string2, CallPhoneMethodHelper<T> callPhoneMethodHelper) {
        return this.callPhoneMethodWithPermissionCheck(n, string, string2, callPhoneMethodHelper, (PermissionCheckHelper)_$$Lambda$PhoneSubInfoController$qVe7IcEgdBIfOarHqDJP3ePBBcI.INSTANCE);
    }

    private <T> T callPhoneMethodForSubIdWithReadPhoneNumberCheck(int n, String string, String string2, CallPhoneMethodHelper<T> callPhoneMethodHelper) {
        return this.callPhoneMethodWithPermissionCheck(n, string, string2, callPhoneMethodHelper, (PermissionCheckHelper)_$$Lambda$PhoneSubInfoController$1TnOMFYcM13ZTJNoLjxguPwVcxw.INSTANCE);
    }

    private <T> T callPhoneMethodForSubIdWithReadSubscriberIdentifiersCheck(int n, String string, String string2, CallPhoneMethodHelper<T> callPhoneMethodHelper) {
        return this.callPhoneMethodWithPermissionCheck(n, string, string2, callPhoneMethodHelper, (PermissionCheckHelper)_$$Lambda$PhoneSubInfoController$EYZUPU0CYhRoptGCGJ9y78u_jQM.INSTANCE);
    }

    private <T> T callPhoneMethodWithPermissionCheck(int n, String object, String string, CallPhoneMethodHelper<T> callPhoneMethodHelper, PermissionCheckHelper permissionCheckHelper) {
        if (!permissionCheckHelper.checkPermission(this.mContext, n, (String)object, string)) {
            return null;
        }
        long l = Binder.clearCallingIdentity();
        try {
            object = this.getPhone(n);
            if (object != null) {
                object = callPhoneMethodHelper.callMethod((Phone)object);
                return (T)object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" phone is null for Subscription:");
            ((StringBuilder)object).append(n);
            this.loge(((StringBuilder)object).toString());
            return null;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    private void enforceModifyPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE", "Requires MODIFY_PHONE_STATE");
    }

    private void enforcePrivilegedPermissionOrCarrierPrivilege(int n, String string) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE") == 0) {
            return;
        }
        TelephonyPermissions.enforceCallingOrSelfCarrierPrivilege((int)n, (String)string);
    }

    @UnsupportedAppUsage
    private int getDefaultSubscription() {
        return PhoneFactory.getDefaultSubscription();
    }

    @UnsupportedAppUsage
    private Phone getPhone(int n) {
        int n2;
        n = n2 = SubscriptionManager.getPhoneId((int)n);
        if (!SubscriptionManager.isValidPhoneId((int)n2)) {
            n = 0;
        }
        return this.mPhone[n];
    }

    static /* synthetic */ boolean lambda$callPhoneMethodForSubIdWithReadCheck$22(Context context, int n, String string, String string2) {
        return TelephonyPermissions.checkCallingOrSelfReadPhoneState((Context)context, (int)n, (String)string, (String)string2);
    }

    static /* synthetic */ boolean lambda$callPhoneMethodForSubIdWithReadDeviceIdentifiersCheck$23(Context context, int n, String string, String string2) {
        return TelephonyPermissions.checkCallingOrSelfReadDeviceIdentifiers((Context)context, (int)n, (String)string, (String)string2);
    }

    static /* synthetic */ boolean lambda$callPhoneMethodForSubIdWithReadPhoneNumberCheck$27(Context context, int n, String string, String string2) {
        return TelephonyPermissions.checkCallingOrSelfReadPhoneNumber((Context)context, (int)n, (String)string, (String)string2);
    }

    static /* synthetic */ boolean lambda$callPhoneMethodForSubIdWithReadSubscriberIdentifiersCheck$24(Context context, int n, String string, String string2) {
        return TelephonyPermissions.checkCallingOrSelfReadSubscriberIdentifiers((Context)context, (int)n, (String)string, (String)string2);
    }

    static /* synthetic */ ImsiEncryptionInfo lambda$getCarrierInfoForImsiEncryption$3(int n, Phone phone) {
        return phone.getCarrierInfoForImsiEncryption(n);
    }

    static /* synthetic */ String lambda$getDeviceIdForPhone$0(Phone phone) {
        return phone.getDeviceId();
    }

    static /* synthetic */ String lambda$getDeviceSvnUsingSubId$6(Phone phone) {
        return phone.getDeviceSvn();
    }

    static /* synthetic */ String lambda$getGroupIdLevel1ForSubscriber$21(Phone phone) {
        return phone.getGroupIdLevel1();
    }

    static /* synthetic */ String lambda$getIccSerialNumberForSubscriber$8(Phone phone) {
        return phone.getIccSerialNumber();
    }

    static /* synthetic */ String lambda$getImeiForSubscriber$2(Phone phone) {
        return phone.getImei();
    }

    static /* synthetic */ String lambda$getIsimDomain$15(Phone object) {
        if ((object = ((Phone)object).getIsimRecords()) != null) {
            return object.getIsimDomain();
        }
        return null;
    }

    static /* synthetic */ String lambda$getIsimImpi$14(Phone object) {
        if ((object = ((Phone)object).getIsimRecords()) != null) {
            return object.getIsimImpi();
        }
        return null;
    }

    static /* synthetic */ String[] lambda$getIsimImpu$16(Phone object) {
        if ((object = ((Phone)object).getIsimRecords()) != null) {
            return object.getIsimImpu();
        }
        return null;
    }

    static /* synthetic */ String lambda$getIsimIst$17(Phone object) {
        if ((object = ((Phone)object).getIsimRecords()) != null) {
            return object.getIsimIst();
        }
        return null;
    }

    static /* synthetic */ String[] lambda$getIsimPcscf$18(Phone object) {
        if ((object = ((Phone)object).getIsimRecords()) != null) {
            return object.getIsimPcscf();
        }
        return null;
    }

    static /* synthetic */ String lambda$getLine1AlphaTagForSubscriber$10(Phone phone) {
        return phone.getLine1AlphaTag();
    }

    static /* synthetic */ String lambda$getLine1NumberForSubscriber$9(Phone phone) {
        return phone.getLine1Number();
    }

    static /* synthetic */ String lambda$getMsisdnForSubscriber$11(Phone phone) {
        return phone.getMsisdn();
    }

    static /* synthetic */ String lambda$getNaiForSubscriber$1(Phone phone) {
        return phone.getNai();
    }

    static /* synthetic */ String lambda$getSubscriberIdForSubscriber$7(Phone phone) {
        return phone.getSubscriberId();
    }

    static /* synthetic */ String lambda$getVoiceMailAlphaTagForSubscriber$13(Phone phone) {
        return phone.getVoiceMailAlphaTag();
    }

    static /* synthetic */ Object lambda$resetCarrierKeysForImsiEncryption$5(Phone phone) {
        phone.resetCarrierKeysForImsiEncryption();
        return null;
    }

    static /* synthetic */ Object lambda$setCarrierInfoForImsiEncryption$4(ImsiEncryptionInfo imsiEncryptionInfo, Phone phone) {
        phone.setCarrierInfoForImsiEncryption(imsiEncryptionInfo);
        return null;
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    @UnsupportedAppUsage
    private void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int n, int n2, String string) {
        return (ImsiEncryptionInfo)this.callPhoneMethodForSubIdWithReadCheck(n, string, "getCarrierInfoForImsiEncryption", new _$$Lambda$PhoneSubInfoController$AjZFvwh3Ujx5W3fleFNksc6bLf0(n2));
    }

    public String getDeviceId(String string) {
        return this.getDeviceIdForPhone(SubscriptionManager.getPhoneId((int)this.getDefaultSubscription()), string);
    }

    public String getDeviceIdForPhone(int n, String string) {
        return (String)this.callPhoneMethodForPhoneIdWithReadDeviceIdentifiersCheck(n, string, "getDeviceId", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$LX6rN0XZFTVXkDiHGVCozgs8kHU.INSTANCE);
    }

    public String getDeviceSvn(String string) {
        return this.getDeviceSvnUsingSubId(this.getDefaultSubscription(), string);
    }

    public String getDeviceSvnUsingSubId(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadCheck(n, string, "getDeviceSvn", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$VgStcgP2F9IDb29Rx_E2o89A_7U.INSTANCE);
    }

    public String getGroupIdLevel1ForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadCheck(n, string, "getGroupIdLevel1", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$bWluhZvk2X_dQ0UidKfdpd0kwuw.INSTANCE);
    }

    public String getIccSerialNumber(String string) {
        return this.getIccSerialNumberForSubscriber(this.getDefaultSubscription(), string);
    }

    public String getIccSerialNumberForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadSubscriberIdentifiersCheck(n, string, "getIccSerialNumber", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$1zkPy06BwndFkKrGCUI1ORIPJcI.INSTANCE);
    }

    public String getIccSimChallengeResponse(int n, int n2, int n3, String string) throws RemoteException {
        return (String)this.callPhoneMethodWithPermissionCheck(n, null, "getIccSimChallengeResponse", new _$$Lambda$PhoneSubInfoController$1_6zFa_5X___HsO5oSaupKDtHL0(this, n2, n3, string), new _$$Lambda$PhoneSubInfoController$NO5_HxAafVP54fe9chLZKTACeyU(this));
    }

    public String getImeiForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadDeviceIdentifiersCheck(n, string, "getImei", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$_djiy1W26lRIJyfoQefqkIQNgSU.INSTANCE);
    }

    public String getIsimDomain(int n) {
        return (String)this.callPhoneMethodForSubIdWithPrivilegedCheck(n, "getIsimDomain", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$ZOtVAnuhxrXl2L906I6eTOentP0.INSTANCE);
    }

    public String getIsimImpi(int n) {
        return (String)this.callPhoneMethodForSubIdWithPrivilegedCheck(n, "getIsimImpi", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$UaKjkq7sTW3Fbf04O086aBFm63M.INSTANCE);
    }

    public String[] getIsimImpu(int n) {
        return (String[])this.callPhoneMethodForSubIdWithPrivilegedCheck(n, "getIsimImpu", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$2xgrYNleR8FFzFT8hEQx3mDtZ8g.INSTANCE);
    }

    public String getIsimIst(int n) throws RemoteException {
        return (String)this.callPhoneMethodForSubIdWithPrivilegedCheck(n, "getIsimIst", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$rpyQeO7zACcc5v4krwU9_qRMHL8.INSTANCE);
    }

    public String[] getIsimPcscf(int n) throws RemoteException {
        return (String[])this.callPhoneMethodForSubIdWithPrivilegedCheck(n, "getIsimPcscf", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$9_e7IQZG40sfOlFgD3_7E7x3p4o.INSTANCE);
    }

    public String getLine1AlphaTag(String string) {
        return this.getLine1AlphaTagForSubscriber(this.getDefaultSubscription(), string);
    }

    public String getLine1AlphaTagForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadCheck(n, string, "getLine1AlphaTag", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$hh4N6_N4_PPm_vWjCdCRvS8__Cw.INSTANCE);
    }

    public String getLine1Number(String string) {
        return this.getLine1NumberForSubscriber(this.getDefaultSubscription(), string);
    }

    public String getLine1NumberForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadPhoneNumberCheck(n, string, "getLine1Number", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$P0j9hvO3e_UE9_1i1QM_ujl8Bpo.INSTANCE);
    }

    public String getMsisdn(String string) {
        return this.getMsisdnForSubscriber(this.getDefaultSubscription(), string);
    }

    public String getMsisdnForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadCheck(n, string, "getMsisdn", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$dmWm_chcWksZlUJPg5OfrbagSrA.INSTANCE);
    }

    public String getNaiForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadCheck(n, string, "getNai", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$AAs5l6UPqOJI6iOy7O7wnhNgpN4.INSTANCE);
    }

    public String getSubscriberId(String string) {
        return this.getSubscriberIdForSubscriber(this.getDefaultSubscription(), string);
    }

    public String getSubscriberIdForSubscriber(int n, String string) {
        long l;
        block7 : {
            l = Binder.clearCallingIdentity();
            boolean bl = SubscriptionController.getInstance().isActiveSubId(n, string);
            if (!bl) break block7;
            return (String)this.callPhoneMethodForSubIdWithReadSubscriberIdentifiersCheck(n, string, "getSubscriberId", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$2WGP2Bp11k7_Xwi1N4YefElOUuM.INSTANCE);
        }
        if (!TelephonyPermissions.checkCallingOrSelfReadSubscriberIdentifiers((Context)this.mContext, (int)n, (String)string, (String)"getSubscriberId")) {
            return null;
        }
        l = Binder.clearCallingIdentity();
        try {
            string = SubscriptionController.getInstance().getImsiPrivileged(n);
            return string;
        }
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    public String getVoiceMailAlphaTag(String string) {
        return this.getVoiceMailAlphaTagForSubscriber(this.getDefaultSubscription(), string);
    }

    public String getVoiceMailAlphaTagForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadCheck(n, string, "getVoiceMailAlphaTag", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$oLIrumQtrxqYONQeIeqNtbJdJMU.INSTANCE);
    }

    public String getVoiceMailNumber(String string) {
        return this.getVoiceMailNumberForSubscriber(this.getDefaultSubscription(), string);
    }

    public String getVoiceMailNumberForSubscriber(int n, String string) {
        return (String)this.callPhoneMethodForSubIdWithReadCheck(n, string, "getVoiceMailNumber", new _$$Lambda$PhoneSubInfoController$Ja9yTBcEYPqTRBIP_hL0otixVeE(this));
    }

    public /* synthetic */ boolean lambda$callPhoneMethodForSubIdWithModifyCheck$26$PhoneSubInfoController(Context context, int n, String string, String string2) {
        this.enforceModifyPermission();
        return true;
    }

    public /* synthetic */ boolean lambda$callPhoneMethodForSubIdWithPrivilegedCheck$25$PhoneSubInfoController(String string, Context context, int n, String string2, String string3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", string);
        return true;
    }

    public /* synthetic */ String lambda$getIccSimChallengeResponse$19$PhoneSubInfoController(int n, int n2, String charSequence, Phone object) {
        if ((object = ((Phone)object).getUiccCard()) == null) {
            this.loge("getIccSimChallengeResponse() UiccCard is null");
            return null;
        }
        if ((object = ((UiccCard)object).getApplicationByType(n)) == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("getIccSimChallengeResponse() no app with specified type -- ");
            ((StringBuilder)charSequence).append(n);
            this.loge(((StringBuilder)charSequence).toString());
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getIccSimChallengeResponse() found app ");
        stringBuilder.append(((UiccCardApplication)object).getAid());
        stringBuilder.append(" specified type -- ");
        stringBuilder.append(n);
        this.loge(stringBuilder.toString());
        if (n2 != 128 && n2 != 129) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("getIccSimChallengeResponse() unsupported authType: ");
            ((StringBuilder)charSequence).append(n2);
            this.loge(((StringBuilder)charSequence).toString());
            return null;
        }
        return ((UiccCardApplication)object).getIccRecords().getIccSimChallengeResponse(n2, (String)charSequence);
    }

    public /* synthetic */ boolean lambda$getIccSimChallengeResponse$20$PhoneSubInfoController(Context context, int n, String string, String string2) {
        this.enforcePrivilegedPermissionOrCarrierPrivilege(n, string2);
        return true;
    }

    public /* synthetic */ String lambda$getVoiceMailNumberForSubscriber$12$PhoneSubInfoController(Phone phone) {
        return PhoneNumberUtils.extractNetworkPortion((String)phone.getVoiceMailNumber());
    }

    public void resetCarrierKeysForImsiEncryption(int n, String string) {
        this.callPhoneMethodForSubIdWithModifyCheck(n, string, "setCarrierInfoForImsiEncryption", (CallPhoneMethodHelper<T>)_$$Lambda$PhoneSubInfoController$Pb4HmeqsjasrNaXBByGh__CFogk.INSTANCE);
    }

    public void setCarrierInfoForImsiEncryption(int n, String string, ImsiEncryptionInfo imsiEncryptionInfo) {
        this.callPhoneMethodForSubIdWithModifyCheck(n, string, "setCarrierInfoForImsiEncryption", new _$$Lambda$PhoneSubInfoController$ChCf_gnGN3K5prBkykg6tWs0aTk(imsiEncryptionInfo));
    }

    private static interface CallPhoneMethodHelper<T> {
        public T callMethod(Phone var1);
    }

    private static interface PermissionCheckHelper {
        public boolean checkPermission(Context var1, int var2, String var3, String var4);
    }

}


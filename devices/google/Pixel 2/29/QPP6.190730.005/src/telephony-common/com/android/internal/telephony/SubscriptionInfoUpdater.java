/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.ActivityManager
 *  android.app.IActivityManager
 *  android.app.IUserSwitchObserver
 *  android.app.UserSwitchObserver
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.IPackageManager
 *  android.content.pm.IPackageManager$Stub
 *  android.content.pm.UserInfo
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IRemoteCallback
 *  android.os.Looper
 *  android.os.Message
 *  android.os.ParcelUuid
 *  android.os.PersistableBundle
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Settings$SettingNotFoundException
 *  android.service.carrier.CarrierIdentifier
 *  android.service.euicc.EuiccProfileInfo
 *  android.service.euicc.GetEuiccProfileInfoListResult
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.UiccAccessRule
 *  android.telephony.euicc.EuiccManager
 *  android.text.TextUtils
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$SubscriptionInfoUpdater
 *  com.android.internal.telephony.-$$Lambda$SubscriptionInfoUpdater$DY4i_CG7hrAeejGLeh3hMUZySnw
 *  com.android.internal.telephony.-$$Lambda$SubscriptionInfoUpdater$ecTEeMEIjOEa2z5W3wjqiicibbY
 *  com.android.internal.telephony.-$$Lambda$SubscriptionInfoUpdater$tLUuQ7lYu8EjRd038qzQlDm-CtA
 *  com.android.internal.telephony.CarrierAppUtils
 *  com.android.internal.telephony.RILConstants
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.IUserSwitchObserver;
import android.app.UserSwitchObserver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.IPackageManager;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.service.carrier.CarrierIdentifier;
import android.service.euicc.EuiccProfileInfo;
import android.service.euicc.GetEuiccProfileInfoListResult;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccAccessRule;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.CarrierAppUtils;
import com.android.internal.telephony.CarrierResolver;
import com.android.internal.telephony.CarrierServiceBindHelper;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.IntentBroadcaster;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.MultiSimSettingController;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.RILConstants;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony._$$Lambda$SubscriptionInfoUpdater$DY4i_CG7hrAeejGLeh3hMUZySnw;
import com.android.internal.telephony._$$Lambda$SubscriptionInfoUpdater$UFyB0ValfLD0rdGDibCjTnGFkeo;
import com.android.internal.telephony._$$Lambda$SubscriptionInfoUpdater$Y5woGfEDKrozRViLH7WF93qPEno;
import com.android.internal.telephony._$$Lambda$SubscriptionInfoUpdater$ZTY4uxKw17CHcHQzbBUF7m_dN_E;
import com.android.internal.telephony._$$Lambda$SubscriptionInfoUpdater$ecTEeMEIjOEa2z5W3wjqiicibbY;
import com.android.internal.telephony._$$Lambda$SubscriptionInfoUpdater$qyDxq2AWyReUxdc6HttVGQeDD3Y;
import com.android.internal.telephony._$$Lambda$SubscriptionInfoUpdater$tLUuQ7lYu8EjRd038qzQlDm_CtA;
import com.android.internal.telephony.euicc.EuiccController;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.UiccSlot;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubscriptionInfoUpdater
extends Handler {
    public static final String CURR_SUBID = "curr_subid";
    private static final boolean DBG = true;
    private static final int EVENT_GET_NETWORK_SELECTION_MODE_DONE = 2;
    private static final int EVENT_INVALID = -1;
    private static final int EVENT_REFRESH_EMBEDDED_SUBSCRIPTIONS = 12;
    private static final int EVENT_SIM_ABSENT = 4;
    private static final int EVENT_SIM_IMSI = 11;
    private static final int EVENT_SIM_IO_ERROR = 6;
    private static final int EVENT_SIM_LOADED = 3;
    private static final int EVENT_SIM_LOCKED = 5;
    private static final int EVENT_SIM_NOT_READY = 9;
    private static final int EVENT_SIM_READY = 10;
    private static final int EVENT_SIM_RESTRICTED = 8;
    private static final int EVENT_SIM_UNKNOWN = 7;
    private static final String ICCID_STRING_FOR_NO_SIM = "";
    private static final String LOG_TAG = "SubscriptionInfoUpdater";
    @UnsupportedAppUsage
    private static final int PROJECT_SIM_NUM = TelephonyManager.getDefault().getPhoneCount();
    private static final ParcelUuid REMOVE_GROUP_UUID = ParcelUuid.fromString((String)"00000000-0000-0000-0000-000000000000");
    @UnsupportedAppUsage
    private static Context mContext = null;
    @UnsupportedAppUsage
    private static String[] mIccId;
    @UnsupportedAppUsage
    private static Phone[] mPhone;
    private static boolean sIsSubInfoInitialized;
    private static int[] sSimApplicationState;
    private static int[] sSimCardState;
    private Handler mBackgroundHandler;
    private CarrierServiceBindHelper mCarrierServiceBindHelper;
    @UnsupportedAppUsage
    private int mCurrentlyActiveUserId;
    private EuiccManager mEuiccManager;
    @UnsupportedAppUsage
    private IPackageManager mPackageManager;
    private SubscriptionManager mSubscriptionManager = null;

    static {
        int n = PROJECT_SIM_NUM;
        mIccId = new String[n];
        sSimCardState = new int[n];
        sSimApplicationState = new int[n];
        sIsSubInfoInitialized = false;
    }

    public SubscriptionInfoUpdater(Looper looper, Context context, Phone[] arrphone, CommandsInterface[] arrcommandsInterface) {
        this(looper, context, arrphone, arrcommandsInterface, IPackageManager.Stub.asInterface((IBinder)ServiceManager.getService((String)"package")));
    }

    @VisibleForTesting
    public SubscriptionInfoUpdater(Looper looper, Context context, Phone[] arrphone, CommandsInterface[] arrcommandsInterface, IPackageManager iPackageManager) {
        SubscriptionInfoUpdater.logd("Constructor invoked");
        this.mBackgroundHandler = new Handler(looper);
        mContext = context;
        mPhone = arrphone;
        this.mSubscriptionManager = SubscriptionManager.from((Context)mContext);
        this.mEuiccManager = (EuiccManager)mContext.getSystemService("euicc");
        this.mPackageManager = iPackageManager;
        this.mCarrierServiceBindHelper = new CarrierServiceBindHelper(mContext);
        this.initializeCarrierApps();
    }

    private void broadcastSimApplicationStateChanged(int n, int n2) {
        Intent intent = sSimApplicationState;
        if (n2 != intent[n] && (n2 != 6 || intent[n] != 0)) {
            SubscriptionInfoUpdater.sSimApplicationState[n] = n2;
            intent = new Intent("android.telephony.action.SIM_APPLICATION_STATE_CHANGED");
            intent.addFlags(16777216);
            intent.addFlags(67108864);
            intent.putExtra("android.telephony.extra.SIM_STATE", n2);
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)n);
            int n3 = UiccController.getInstance().getSlotIdFromPhoneId(n);
            intent.putExtra("slot", n3);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Broadcasting intent ACTION_SIM_APPLICATION_STATE_CHANGED ");
            stringBuilder.append(SubscriptionInfoUpdater.simStateString(n2));
            stringBuilder.append(" for phone: ");
            stringBuilder.append(n);
            stringBuilder.append(" slot: ");
            stringBuilder.append(n3);
            SubscriptionInfoUpdater.logd(stringBuilder.toString());
            mContext.sendBroadcast(intent, "android.permission.READ_PRIVILEGED_PHONE_STATE");
            TelephonyMetrics.getInstance().updateSimState(n, n2);
        }
    }

    private void broadcastSimCardStateChanged(int n, int n2) {
        Intent intent = sSimCardState;
        if (n2 != intent[n]) {
            intent[n] = n2;
            intent = new Intent("android.telephony.action.SIM_CARD_STATE_CHANGED");
            intent.addFlags(67108864);
            intent.addFlags(16777216);
            intent.putExtra("android.telephony.extra.SIM_STATE", n2);
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)n);
            int n3 = UiccController.getInstance().getSlotIdFromPhoneId(n);
            intent.putExtra("slot", n3);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Broadcasting intent ACTION_SIM_CARD_STATE_CHANGED ");
            stringBuilder.append(SubscriptionInfoUpdater.simStateString(n2));
            stringBuilder.append(" for phone: ");
            stringBuilder.append(n);
            stringBuilder.append(" slot: ");
            stringBuilder.append(n3);
            SubscriptionInfoUpdater.logd(stringBuilder.toString());
            mContext.sendBroadcast(intent, "android.permission.READ_PRIVILEGED_PHONE_STATE");
            TelephonyMetrics.getInstance().updateSimState(n, n2);
        }
    }

    @UnsupportedAppUsage
    private void broadcastSimStateChanged(int n, String string, String string2) {
        Intent intent = new Intent("android.intent.action.SIM_STATE_CHANGED");
        intent.addFlags(67108864);
        intent.putExtra("phoneName", "Phone");
        intent.putExtra("ss", string);
        intent.putExtra("reason", string2);
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Broadcasting intent ACTION_SIM_STATE_CHANGED ");
        stringBuilder.append(string);
        stringBuilder.append(" reason ");
        stringBuilder.append(string2);
        stringBuilder.append(" for phone: ");
        stringBuilder.append(n);
        SubscriptionInfoUpdater.logd(stringBuilder.toString());
        IntentBroadcaster.getInstance().broadcastStickyIntent(intent, n);
    }

    private static int findSubscriptionInfoForIccid(List<SubscriptionInfo> list, String string) {
        for (int i = 0; i < list.size(); ++i) {
            if (!TextUtils.equals((CharSequence)string, (CharSequence)list.get(i).getIccId())) continue;
            return i;
        }
        return -1;
    }

    private int getCardIdFromPhoneId(int n) {
        UiccController uiccController = UiccController.getInstance();
        UiccCard uiccCard = uiccController.getUiccCardForPhone(n);
        if (uiccCard != null) {
            return uiccController.convertToPublicCardId(uiccCard.getCardId());
        }
        return -2;
    }

    private String getDefaultCarrierServicePackageName() {
        return ((CarrierConfigManager)mContext.getSystemService("carrier_config")).getDefaultCarrierServicePackageName();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static int getSimStateFromLockedReason(String var0) {
        block6 : {
            switch (var0.hashCode()) {
                case 190660331: {
                    if (!var0.equals("PERM_DISABLED")) break;
                    var1_1 = 3;
                    break block6;
                }
                case 79590: {
                    if (!var0.equals("PUK")) break;
                    var1_1 = 1;
                    break block6;
                }
                case 79221: {
                    if (!var0.equals("PIN")) break;
                    var1_1 = 0;
                    break block6;
                }
                case -1733499378: {
                    if (!var0.equals("NETWORK")) break;
                    var1_1 = 2;
                    break block6;
                }
            }
            ** break;
lbl19: // 1 sources:
            var1_1 = -1;
        }
        if (var1_1 == 0) return 2;
        if (var1_1 == 1) return 3;
        if (var1_1 == 2) return 4;
        if (var1_1 == 3) return 7;
        var2_2 = new StringBuilder();
        var2_2.append("Unexpected SIM locked reason ");
        var2_2.append(var0);
        Rlog.e((String)"SubscriptionInfoUpdater", (String)var2_2.toString());
        return 0;
    }

    private void handleSimAbsent(int n, int n2) {
        Object object = mIccId;
        if (object[n] != null && !object[n].equals(ICCID_STRING_FOR_NO_SIM)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("SIM");
            ((StringBuilder)object).append(n + 1);
            ((StringBuilder)object).append(" hot plug out, absentAndInactive=");
            ((StringBuilder)object).append(n2);
            SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
        }
        SubscriptionInfoUpdater.mIccId[n] = ICCID_STRING_FOR_NO_SIM;
        this.updateSubscriptionInfoByIccId(n, true);
        if (n2 == 0) {
            this.broadcastSimStateChanged(n, "ABSENT", null);
            this.broadcastSimCardStateChanged(n, 1);
            this.broadcastSimApplicationStateChanged(n, 0);
            this.updateSubscriptionCarrierId(n, "ABSENT");
            this.updateCarrierServices(n, "ABSENT");
        }
    }

    private void handleSimError(int n) {
        Object object = mIccId;
        if (object[n] != null && !object[n].equals(ICCID_STRING_FOR_NO_SIM)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("SIM");
            ((StringBuilder)object).append(n + 1);
            ((StringBuilder)object).append(" Error ");
            SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
        }
        SubscriptionInfoUpdater.mIccId[n] = ICCID_STRING_FOR_NO_SIM;
        this.updateSubscriptionInfoByIccId(n, true);
        this.broadcastSimStateChanged(n, "CARD_IO_ERROR", "CARD_IO_ERROR");
        this.broadcastSimCardStateChanged(n, 8);
        this.broadcastSimApplicationStateChanged(n, 6);
        this.updateSubscriptionCarrierId(n, "CARD_IO_ERROR");
        this.updateCarrierServices(n, "CARD_IO_ERROR");
    }

    private void handleSimLoaded(int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("handleSimLoaded: slotId: ");
        ((StringBuilder)object).append(n);
        SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
        IccCard iccCard = mPhone[n].getIccCard();
        if (iccCard == null) {
            SubscriptionInfoUpdater.logd("handleSimLoaded: IccCard null");
            return;
        }
        IccRecords iccRecords = iccCard.getIccRecords();
        if (iccRecords == null) {
            SubscriptionInfoUpdater.logd("handleSimLoaded: IccRecords null");
            return;
        }
        if (IccUtils.stripTrailingFs((String)iccRecords.getFullIccId()) == null) {
            SubscriptionInfoUpdater.logd("handleSimLoaded: IccID null");
            return;
        }
        SubscriptionInfoUpdater.mIccId[n] = IccUtils.stripTrailingFs((String)iccRecords.getFullIccId());
        this.updateSubscriptionInfoByIccId(n, true);
        List<SubscriptionInfo> list = SubscriptionController.getInstance().getSubInfoUsingSlotIndexPrivileged(n);
        if (list != null && !list.isEmpty()) {
            object = list.iterator();
            while (object.hasNext()) {
                int n2;
                int n3 = ((SubscriptionInfo)object.next()).getSubscriptionId();
                Object object2 = (TelephonyManager)mContext.getSystemService("phone");
                Object object3 = object2.getSimOperatorNumeric(n3);
                if (!TextUtils.isEmpty((CharSequence)object3)) {
                    if (n3 == SubscriptionController.getInstance().getDefaultSubId()) {
                        MccTable.updateMccMncConfiguration(mContext, (String)object3);
                    }
                    SubscriptionController.getInstance().setMccMnc((String)object3, n3);
                } else {
                    SubscriptionInfoUpdater.logd("EVENT_RECORDS_LOADED Operator name is null");
                }
                object3 = object2.getSimCountryIsoForPhone(n);
                if (!TextUtils.isEmpty((CharSequence)object3)) {
                    SubscriptionController.getInstance().setCountryIso((String)object3, n3);
                } else {
                    SubscriptionInfoUpdater.logd("EVENT_RECORDS_LOADED sim country iso is null");
                }
                object3 = object2.getLine1Number(n3);
                if (object3 != null) {
                    SubscriptionController.getInstance().setDisplayNumber((String)object3, n3);
                }
                if ((object2 = object2.createForSubscriptionId(n3).getSubscriberId()) != null) {
                    SubscriptionController.getInstance().setImsi((String)object2, n3);
                }
                object3 = iccRecords.getEhplmns();
                object2 = iccRecords.getPlmnsFromHplmnActRecord();
                if (object3 != null || object2 != null) {
                    SubscriptionController.getInstance().setAssociatedPlmns((String[])object3, (String[])object2, n3);
                }
                object2 = PreferenceManager.getDefaultSharedPreferences((Context)mContext);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(CURR_SUBID);
                ((StringBuilder)object3).append(n);
                if (object2.getInt(((StringBuilder)object3).toString(), -1) == n3) continue;
                ContentResolver contentResolver = mPhone[n].getContext().getContentResolver();
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("preferred_network_mode");
                ((StringBuilder)object3).append(n3);
                int n4 = n2 = Settings.Global.getInt((ContentResolver)contentResolver, (String)((StringBuilder)object3).toString(), (int)-1);
                if (n2 == -1) {
                    n2 = RILConstants.PREFERRED_NETWORK_MODE;
                    try {
                        n4 = TelephonyManager.getIntAtIndex((ContentResolver)mContext.getContentResolver(), (String)"preferred_network_mode", (int)n);
                    }
                    catch (Settings.SettingNotFoundException settingNotFoundException) {
                        Rlog.e((String)LOG_TAG, (String)"Settings Exception Reading Value At Index for Settings.Global.PREFERRED_NETWORK_MODE");
                        n4 = n2;
                    }
                    contentResolver = mPhone[n].getContext().getContentResolver();
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("preferred_network_mode");
                    ((StringBuilder)object3).append(n3);
                    Settings.Global.putInt((ContentResolver)contentResolver, (String)((StringBuilder)object3).toString(), (int)n4);
                }
                mPhone[n].setPreferredNetworkType(n4, null);
                mPhone[n].getNetworkSelectionMode(this.obtainMessage(2, (Object)new Integer(n)));
                object3 = object2.edit();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(CURR_SUBID);
                ((StringBuilder)object2).append(n);
                object3.putInt(((StringBuilder)object2).toString(), n3);
                object3.apply();
            }
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("empty subinfo for slotId: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("could not update ContentResolver");
            SubscriptionInfoUpdater.loge(((StringBuilder)object).toString());
        }
        CarrierAppUtils.disableCarrierAppsUntilPrivileged((String)mContext.getOpPackageName(), (IPackageManager)this.mPackageManager, (TelephonyManager)TelephonyManager.getDefault(), (ContentResolver)mContext.getContentResolver(), (int)this.mCurrentlyActiveUserId);
        this.broadcastSimStateChanged(n, "LOADED", null);
        this.broadcastSimCardStateChanged(n, 11);
        this.broadcastSimApplicationStateChanged(n, 10);
        this.updateSubscriptionCarrierId(n, "LOADED");
        this.updateCarrierServices(n, "LOADED");
    }

    private void handleSimLocked(int n, String string) {
        Object object = mIccId;
        if (object[n] != null && object[n].equals(ICCID_STRING_FOR_NO_SIM)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("SIM");
            ((StringBuilder)object).append(n + 1);
            ((StringBuilder)object).append(" hot plug in");
            SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
            SubscriptionInfoUpdater.mIccId[n] = null;
        }
        if ((object = mIccId[n]) == null) {
            object = mPhone[n].getIccCard();
            if (object == null) {
                SubscriptionInfoUpdater.logd("handleSimLocked: IccCard null");
                return;
            }
            if ((object = ((IccCard)object).getIccRecords()) == null) {
                SubscriptionInfoUpdater.logd("handleSimLocked: IccRecords null");
                return;
            }
            if (IccUtils.stripTrailingFs((String)((IccRecords)object).getFullIccId()) == null) {
                SubscriptionInfoUpdater.logd("handleSimLocked: IccID null");
                return;
            }
            SubscriptionInfoUpdater.mIccId[n] = IccUtils.stripTrailingFs((String)((IccRecords)object).getFullIccId());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NOT Querying IccId its already set sIccid[");
            stringBuilder.append(n);
            stringBuilder.append("]=");
            stringBuilder.append((String)object);
            SubscriptionInfoUpdater.logd(stringBuilder.toString());
        }
        this.updateSubscriptionInfoByIccId(n, true);
        this.broadcastSimStateChanged(n, "LOCKED", string);
        this.broadcastSimCardStateChanged(n, 11);
        this.broadcastSimApplicationStateChanged(n, SubscriptionInfoUpdater.getSimStateFromLockedReason(string));
        this.updateSubscriptionCarrierId(n, "LOCKED");
        this.updateCarrierServices(n, "LOCKED");
    }

    private void handleSimNotReady(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleSimNotReady: slotId: ");
        stringBuilder.append(n);
        SubscriptionInfoUpdater.logd(stringBuilder.toString());
        if (mPhone[n].getIccCard().isEmptyProfile()) {
            SubscriptionInfoUpdater.mIccId[n] = ICCID_STRING_FOR_NO_SIM;
            this.updateSubscriptionInfoByIccId(n, false);
        }
        this.broadcastSimStateChanged(n, "NOT_READY", null);
        this.broadcastSimCardStateChanged(n, 11);
        this.broadcastSimApplicationStateChanged(n, 6);
    }

    private void initializeCarrierApps() {
        this.mCurrentlyActiveUserId = 0;
        try {
            IActivityManager iActivityManager = ActivityManager.getService();
            UserSwitchObserver userSwitchObserver = new UserSwitchObserver(){

                public void onUserSwitching(int n, IRemoteCallback iRemoteCallback) throws RemoteException {
                    SubscriptionInfoUpdater.this.mCurrentlyActiveUserId = n;
                    CarrierAppUtils.disableCarrierAppsUntilPrivileged((String)mContext.getOpPackageName(), (IPackageManager)SubscriptionInfoUpdater.this.mPackageManager, (TelephonyManager)TelephonyManager.getDefault(), (ContentResolver)mContext.getContentResolver(), (int)SubscriptionInfoUpdater.this.mCurrentlyActiveUserId);
                    if (iRemoteCallback != null) {
                        try {
                            iRemoteCallback.sendResult(null);
                        }
                        catch (RemoteException remoteException) {
                            // empty catch block
                        }
                    }
                }
            };
            iActivityManager.registerUserSwitchObserver((IUserSwitchObserver)userSwitchObserver, LOG_TAG);
            this.mCurrentlyActiveUserId = ActivityManager.getService().getCurrentUser().id;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't get current user ID; guessing it's 0: ");
            stringBuilder.append(remoteException.getMessage());
            SubscriptionInfoUpdater.logd(stringBuilder.toString());
        }
        CarrierAppUtils.disableCarrierAppsUntilPrivileged((String)mContext.getOpPackageName(), (IPackageManager)this.mPackageManager, (TelephonyManager)TelephonyManager.getDefault(), (ContentResolver)mContext.getContentResolver(), (int)this.mCurrentlyActiveUserId);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int internalIccStateToMessage(String var1_1) {
        block22 : {
            switch (var1_1.hashCode()) {
                case 1924388665: {
                    if (!var1_1.equals("ABSENT")) break;
                    var2_2 = 0;
                    break block22;
                }
                case 1599753450: {
                    if (!var1_1.equals("CARD_RESTRICTED")) break;
                    var2_2 = 3;
                    break block22;
                }
                case 1034051831: {
                    if (!var1_1.equals("NOT_READY")) break;
                    var2_2 = 4;
                    break block22;
                }
                case 433141802: {
                    if (!var1_1.equals("UNKNOWN")) break;
                    var2_2 = 1;
                    break block22;
                }
                case 77848963: {
                    if (!var1_1.equals("READY")) break;
                    var2_2 = 7;
                    break block22;
                }
                case 2251386: {
                    if (!var1_1.equals("IMSI")) break;
                    var2_2 = 8;
                    break block22;
                }
                case -1830845986: {
                    if (!var1_1.equals("CARD_IO_ERROR")) break;
                    var2_2 = 2;
                    break block22;
                }
                case -2044123382: {
                    if (!var1_1.equals("LOCKED")) break;
                    var2_2 = 5;
                    break block22;
                }
                case -2044189691: {
                    if (!var1_1.equals("LOADED")) break;
                    var2_2 = 6;
                    break block22;
                }
            }
            ** break;
lbl39: // 1 sources:
            var2_2 = -1;
        }
        switch (var2_2) {
            default: {
                var3_3 = new StringBuilder();
                var3_3.append("Ignoring simStatus: ");
                var3_3.append(var1_1);
                SubscriptionInfoUpdater.logd(var3_3.toString());
                return -1;
            }
            case 8: {
                return 11;
            }
            case 7: {
                return 10;
            }
            case 6: {
                return 3;
            }
            case 5: {
                return 5;
            }
            case 4: {
                return 9;
            }
            case 3: {
                return 8;
            }
            case 2: {
                return 6;
            }
            case 1: {
                return 7;
            }
            case 0: 
        }
        return 4;
    }

    @UnsupportedAppUsage
    private boolean isAllIccIdQueryDone() {
        for (int i = 0; i < PROJECT_SIM_NUM; ++i) {
            Object object = UiccController.getInstance().getUiccSlotForPhone(i);
            int n = UiccController.getInstance().getSlotIdFromPhoneId(i);
            if (mIccId[i] != null && object != null && ((UiccSlot)((Object)object)).isActive()) {
                continue;
            }
            if (mIccId[i] == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Wait for SIM ");
                ((StringBuilder)object).append(i);
                ((StringBuilder)object).append(" Iccid");
                SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
            } else {
                SubscriptionInfoUpdater.logd(String.format("Wait for slot corresponding to phone %d to be active, slotId is %d", i, n));
            }
            return false;
        }
        SubscriptionInfoUpdater.logd("All IccIds query complete");
        return true;
    }

    private boolean isCarrierServicePackage(int n, String string) {
        boolean bl = string.equals(this.getDefaultCarrierServicePackageName());
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        List list = TelephonyManager.from((Context)mContext).getCarrierPackageNamesForIntentAndPhone(new Intent("android.service.carrier.CarrierService"), n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Carrier Packages For Subscription = ");
        stringBuilder.append(list);
        SubscriptionInfoUpdater.logd(stringBuilder.toString());
        bl = bl2;
        if (list != null) {
            bl = bl2;
            if (list.contains(string)) {
                bl = true;
            }
        }
        return bl;
    }

    private boolean isNewSim(String charSequence, String string, String[] arrstring) {
        boolean bl;
        boolean bl2 = true;
        int n = 0;
        do {
            bl = bl2;
            if (n >= PROJECT_SIM_NUM) break;
            if (((String)charSequence).equals(arrstring[n])) {
                bl = false;
                break;
            }
            if (string != null && string.equals(arrstring[n])) {
                bl = false;
                break;
            }
            ++n;
        } while (true);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("newSim = ");
        ((StringBuilder)charSequence).append(bl);
        SubscriptionInfoUpdater.logd(((StringBuilder)charSequence).toString());
        return bl;
    }

    public static boolean isSubInfoInitialized() {
        return sIsSubInfoInitialized;
    }

    static /* synthetic */ void lambda$handleMessage$0(boolean bl) {
        if (bl) {
            SubscriptionController.getInstance().notifySubscriptionInfoChanged();
        }
    }

    static /* synthetic */ void lambda$handleMessage$1(boolean bl) {
        if (bl) {
            SubscriptionController.getInstance().notifySubscriptionInfoChanged();
        }
    }

    static /* synthetic */ void lambda$handleMessage$2(Runnable runnable, boolean bl) {
        if (bl) {
            SubscriptionController.getInstance().notifySubscriptionInfoChanged();
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    static /* synthetic */ void lambda$updateSubscriptionInfoByIccId$3(boolean bl) {
        if (bl) {
            SubscriptionController.getInstance().notifySubscriptionInfoChanged();
        }
        SubscriptionInfoUpdater.logd("updateSubscriptionInfoByIccId: SubscriptionInfo update complete");
    }

    @UnsupportedAppUsage
    private static void logd(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    private static void setSubInfoInitialized() {
        if (!sIsSubInfoInitialized) {
            SubscriptionInfoUpdater.logd("SubInfo Initialized");
            sIsSubInfoInitialized = true;
            SubscriptionController.getInstance().notifySubInfoReady();
            MultiSimSettingController.getInstance().notifyAllSubscriptionLoaded();
        }
    }

    private static String simStateString(int n) {
        switch (n) {
            default: {
                return "INVALID";
            }
            case 11: {
                return "PRESENT";
            }
            case 10: {
                return "LOADED";
            }
            case 9: {
                return "CARD_RESTRICTED";
            }
            case 8: {
                return "CARD_IO_ERROR";
            }
            case 7: {
                return "PERM_DISABLED";
            }
            case 6: {
                return "NOT_READY";
            }
            case 5: {
                return "READY";
            }
            case 4: {
                return "NETWORK_LOCKED";
            }
            case 3: {
                return "PUK_REQUIRED";
            }
            case 2: {
                return "PIN_REQUIRED";
            }
            case 1: {
                return "ABSENT";
            }
            case 0: 
        }
        return "UNKNOWN";
    }

    private void updateCarrierServices(int n, String string) {
        ((CarrierConfigManager)mContext.getSystemService("carrier_config")).updateConfigForPhoneId(n, string);
        this.mCarrierServiceBindHelper.updateForPhoneId(n, string);
    }

    private boolean updateEmbeddedSubscriptionsCache(GetEuiccProfileInfoListResult object) {
        SubscriptionInfoUpdater.logd("updateEmbeddedSubscriptionsCache");
        if (object == null) {
            return false;
        }
        Object object2 = object.getProfiles();
        if (object.getResult() == 0 && object2 != null) {
            int n;
            Object object3 = object2.toArray((T[])new EuiccProfileInfo[object2.size()]);
            ContentResolver contentResolver = new StringBuilder();
            contentResolver.append("blockingGetEuiccProfileInfoList: got ");
            contentResolver.append(object.getProfiles().size());
            contentResolver.append(" profiles");
            SubscriptionInfoUpdater.logd(contentResolver.toString());
            boolean bl = object.getIsRemovable();
            contentResolver = new String[((EuiccProfileInfo[])object3).length];
            for (n = 0; n < ((EuiccProfileInfo[])object3).length; ++n) {
                contentResolver[n] = object3[n].getIccid();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Get eUICC profile list of size ");
            ((StringBuilder)object).append(((EuiccProfileInfo[])object3).length);
            SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
            List<SubscriptionInfo> list = SubscriptionController.getInstance().getSubscriptionInfoListForEmbeddedSubscriptionUpdate((String[])contentResolver, bl);
            contentResolver = mContext.getContentResolver();
            int n2 = ((EuiccProfileInfo[])object3).length;
            boolean bl2 = false;
            object = object3;
            for (n = 0; n < n2; ++n) {
                int n3;
                Object object4 = object[n];
                int n4 = SubscriptionInfoUpdater.findSubscriptionInfoForIccid(list, object4.getIccid());
                int n5 = 0;
                if (n4 < 0) {
                    SubscriptionController.getInstance().insertEmptySubInfoRecord(object4.getIccid(), -1);
                    n3 = -1;
                } else {
                    n5 = list.get(n4).getNameSource();
                    n3 = list.get(n4).getCarrierId();
                    list.remove(n4);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("embeddedProfile ");
                stringBuilder.append(object4);
                stringBuilder.append(" existing record ");
                object3 = n4 < 0 ? "not found" : "found";
                stringBuilder.append((String)object3);
                SubscriptionInfoUpdater.logd(stringBuilder.toString());
                stringBuilder = new ContentValues();
                stringBuilder.put("is_embedded", Integer.valueOf(1));
                object3 = object4.getUiccAccessRules();
                n4 = 0;
                if (object3 == null || object3.size() == 0) {
                    n4 = 1;
                }
                object3 = n4 != 0 ? null : UiccAccessRule.encodeRules((UiccAccessRule[])object3.toArray((T[])new UiccAccessRule[object3.size()]));
                stringBuilder.put("access_rules", (byte[])object3);
                stringBuilder.put("is_removable", Boolean.valueOf(bl));
                if (SubscriptionController.getNameSourcePriority(n5) <= SubscriptionController.getNameSourcePriority(3)) {
                    stringBuilder.put("display_name", object4.getNickname());
                    stringBuilder.put("name_source", Integer.valueOf(3));
                }
                stringBuilder.put("profile_class", Integer.valueOf(object4.getProfileClass()));
                Object object5 = object4.getCarrierIdentifier();
                if (object5 != null) {
                    if (n3 == -1) {
                        stringBuilder.put("carrier_id", Integer.valueOf(CarrierResolver.getCarrierIdFromIdentifier(mContext, (CarrierIdentifier)object5)));
                    }
                    object3 = object5.getMcc();
                    object5 = object5.getMnc();
                    stringBuilder.put("mcc_string", (String)object3);
                    stringBuilder.put("mcc", (String)object3);
                    stringBuilder.put("mnc_string", (String)object5);
                    stringBuilder.put("mnc", (String)object5);
                }
                bl2 = true;
                object3 = SubscriptionManager.CONTENT_URI;
                object5 = new StringBuilder();
                ((StringBuilder)object5).append("icc_id=\"");
                ((StringBuilder)object5).append(object4.getIccid());
                ((StringBuilder)object5).append("\"");
                contentResolver.update((Uri)object3, (ContentValues)stringBuilder, ((StringBuilder)object5).toString(), null);
                SubscriptionController.getInstance().refreshCachedActiveSubscriptionInfoList();
            }
            if (!list.isEmpty()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Removing existing embedded subscriptions of size");
                ((StringBuilder)object).append(list.size());
                SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
                object = new ArrayList();
                for (n = 0; n < list.size(); ++n) {
                    object2 = list.get(n);
                    if (!object2.isEmbedded()) continue;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Removing embedded subscription of IccId ");
                    ((StringBuilder)object3).append(object2.getIccId());
                    SubscriptionInfoUpdater.logd(((StringBuilder)object3).toString());
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("\"");
                    ((StringBuilder)object3).append(object2.getIccId());
                    ((StringBuilder)object3).append("\"");
                    object.add(((StringBuilder)object3).toString());
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("icc_id IN (");
                ((StringBuilder)object2).append(TextUtils.join((CharSequence)",", (Iterable)object));
                ((StringBuilder)object2).append(")");
                object = ((StringBuilder)object2).toString();
                object2 = new ContentValues();
                object2.put("is_embedded", Integer.valueOf(0));
                bl2 = true;
                contentResolver.update(SubscriptionManager.CONTENT_URI, (ContentValues)object2, (String)object, null);
                SubscriptionController.getInstance().refreshCachedActiveSubscriptionInfoList();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("updateEmbeddedSubscriptions done hasChanges=");
            ((StringBuilder)object).append(bl2);
            SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
            return bl2;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("blockingGetEuiccProfileInfoList returns an error. Result code=");
        ((StringBuilder)object2).append(object.getResult());
        ((StringBuilder)object2).append(". Null profile list=");
        boolean bl = object.getProfiles() == null;
        ((StringBuilder)object2).append(bl);
        SubscriptionInfoUpdater.logd(((StringBuilder)object2).toString());
        return false;
    }

    private void updateSubscriptionCarrierId(int n, String string) {
        Phone[] arrphone = mPhone;
        if (arrphone != null && arrphone[n] != null) {
            arrphone[n].resolveSubscriptionCarrierId(string);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateSubscriptionInfoByIccId(int n, boolean bl) {
        synchronized (this) {
            UiccSlot[] arruiccSlot;
            Object object;
            int n2;
            SubscriptionInfoUpdater.logd("updateSubscriptionInfoByIccId:+ Start");
            if (!SubscriptionManager.isValidSlotIndex((int)n)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[updateSubscriptionInfoByIccId]- invalid slotIndex=");
                stringBuilder.append(n);
                SubscriptionInfoUpdater.loge(stringBuilder.toString());
                return;
            }
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("updateSubscriptionInfoByIccId: removing subscription info record: slotIndex ");
            ((StringBuilder)object2).append(n);
            SubscriptionInfoUpdater.logd(((StringBuilder)object2).toString());
            SubscriptionController.getInstance().clearSubInfoRecord(n);
            if (!ICCID_STRING_FOR_NO_SIM.equals(mIccId[n])) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("updateSubscriptionInfoByIccId: adding subscription info record: iccid: ");
                ((StringBuilder)object2).append(mIccId[n]);
                ((StringBuilder)object2).append("slot: ");
                ((StringBuilder)object2).append(n);
                SubscriptionInfoUpdater.logd(((StringBuilder)object2).toString());
                this.mSubscriptionManager.addSubscriptionInfoRecord(mIccId[n], n);
            }
            List<SubscriptionInfo> list = SubscriptionController.getInstance().getSubInfoUsingSlotIndexPrivileged(n);
            int n3 = 0;
            if (list != null) {
                n2 = 0;
                for (n = 0; n < list.size(); ++n) {
                    object2 = list.get(n);
                    object = new ContentValues(1);
                    String string = TelephonyManager.getDefault().getLine1Number(object2.getSubscriptionId());
                    if (TextUtils.equals((CharSequence)string, (CharSequence)object2.getNumber())) continue;
                    object.put("number", string);
                    mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)object2.getSubscriptionId()), (ContentValues)object, null, null);
                    n2 = 1;
                }
                if (n2 != 0) {
                    SubscriptionController.getInstance().refreshCachedActiveSubscriptionInfoList();
                }
            }
            if (this.isAllIccIdQueryDone()) {
                object2 = this.mSubscriptionManager;
                object = this.mSubscriptionManager;
                if (object2.isActiveSubId(SubscriptionManager.getDefaultDataSubscriptionId())) {
                    object2 = this.mSubscriptionManager;
                    object = this.mSubscriptionManager;
                    object2.setDefaultDataSubId(SubscriptionManager.getDefaultDataSubscriptionId());
                } else {
                    SubscriptionInfoUpdater.logd("bypass reset default data sub if inactive");
                }
                SubscriptionInfoUpdater.setSubInfoInitialized();
            }
            if ((arruiccSlot = ((UiccController)((Object)(object2 = UiccController.getInstance()))).getUiccSlots()) != null && bl) {
                object = new ArrayList();
                n2 = arruiccSlot.length;
                for (n = n3; n < n2; ++n) {
                    UiccSlot uiccSlot = arruiccSlot[n];
                    if (uiccSlot == null || uiccSlot.getUiccCard() == null) continue;
                    object.add(((UiccController)((Object)object2)).convertToPublicCardId(uiccSlot.getUiccCard().getCardId()));
                }
                this.updateEmbeddedSubscriptions((List<Integer>)object, (UpdateEmbeddedSubsCallback)_$$Lambda$SubscriptionInfoUpdater$ecTEeMEIjOEa2z5W3wjqiicibbY.INSTANCE);
            }
            SubscriptionController.getInstance().notifySubscriptionInfoChanged();
            SubscriptionInfoUpdater.logd("updateSubscriptionInfoByIccId: SubscriptionInfo update complete");
            return;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("SubscriptionInfoUpdater:");
        this.mCarrierServiceBindHelper.dump(fileDescriptor, printWriter, arrstring);
    }

    public void handleMessage(Message object) {
        AsyncResult asyncResult = new ArrayList<Integer>();
        switch (((Message)object).what) {
            default: {
                asyncResult = new StringBuilder();
                asyncResult.append("Unknown msg:");
                asyncResult.append(((Message)object).what);
                SubscriptionInfoUpdater.logd(asyncResult.toString());
                break;
            }
            case 12: {
                asyncResult.add(((Message)object).arg1);
                this.updateEmbeddedSubscriptions((List<Integer>)asyncResult, new _$$Lambda$SubscriptionInfoUpdater$UFyB0ValfLD0rdGDibCjTnGFkeo((Runnable)((Message)object).obj));
                break;
            }
            case 11: {
                this.broadcastSimStateChanged(((Message)object).arg1, "IMSI", null);
                break;
            }
            case 10: {
                asyncResult.add(this.getCardIdFromPhoneId(((Message)object).arg1));
                this.updateEmbeddedSubscriptions((List<Integer>)asyncResult, (UpdateEmbeddedSubsCallback)_$$Lambda$SubscriptionInfoUpdater$DY4i_CG7hrAeejGLeh3hMUZySnw.INSTANCE);
                this.broadcastSimStateChanged(((Message)object).arg1, "READY", null);
                this.broadcastSimCardStateChanged(((Message)object).arg1, 11);
                this.broadcastSimApplicationStateChanged(((Message)object).arg1, 6);
                break;
            }
            case 9: {
                asyncResult.add(this.getCardIdFromPhoneId(((Message)object).arg1));
                this.updateEmbeddedSubscriptions((List<Integer>)asyncResult, (UpdateEmbeddedSubsCallback)_$$Lambda$SubscriptionInfoUpdater$tLUuQ7lYu8EjRd038qzQlDm_CtA.INSTANCE);
                this.handleSimNotReady(((Message)object).arg1);
                break;
            }
            case 8: {
                this.broadcastSimStateChanged(((Message)object).arg1, "CARD_RESTRICTED", "CARD_RESTRICTED");
                this.broadcastSimCardStateChanged(((Message)object).arg1, 9);
                this.broadcastSimApplicationStateChanged(((Message)object).arg1, 6);
                this.updateSubscriptionCarrierId(((Message)object).arg1, "CARD_RESTRICTED");
                this.updateCarrierServices(((Message)object).arg1, "CARD_RESTRICTED");
                break;
            }
            case 7: {
                this.broadcastSimStateChanged(((Message)object).arg1, "UNKNOWN", null);
                this.broadcastSimCardStateChanged(((Message)object).arg1, 0);
                this.broadcastSimApplicationStateChanged(((Message)object).arg1, 0);
                this.updateSubscriptionCarrierId(((Message)object).arg1, "UNKNOWN");
                this.updateCarrierServices(((Message)object).arg1, "UNKNOWN");
                break;
            }
            case 6: {
                this.handleSimError(((Message)object).arg1);
                break;
            }
            case 5: {
                this.handleSimLocked(((Message)object).arg1, (String)((Message)object).obj);
                break;
            }
            case 4: {
                this.handleSimAbsent(((Message)object).arg1, ((Message)object).arg2);
                break;
            }
            case 3: {
                this.handleSimLoaded(((Message)object).arg1);
                break;
            }
            case 2: {
                asyncResult = (AsyncResult)((Message)object).obj;
                object = (Integer)asyncResult.userObj;
                if (asyncResult.exception == null && asyncResult.result != null) {
                    if (((int[])asyncResult.result)[0] != 1) break;
                    mPhone[(Integer)object].setNetworkSelectionModeAutomatic(null);
                    break;
                }
                SubscriptionInfoUpdater.logd("EVENT_GET_NETWORK_SELECTION_MODE_DONE: error getting network mode.");
            }
        }
    }

    public /* synthetic */ void lambda$updateEmbeddedSubscriptions$4$SubscriptionInfoUpdater(List object, UpdateEmbeddedSubsCallback updateEmbeddedSubsCallback) {
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.updateEmbeddedSubscriptionsCache((GetEuiccProfileInfoListResult)object.next())) continue;
            bl = true;
        }
        if (updateEmbeddedSubsCallback != null) {
            updateEmbeddedSubsCallback.run(bl);
        }
    }

    public /* synthetic */ void lambda$updateEmbeddedSubscriptions$5$SubscriptionInfoUpdater(List object, UpdateEmbeddedSubsCallback updateEmbeddedSubsCallback) {
        ArrayList<GetEuiccProfileInfoListResult> arrayList = new ArrayList<GetEuiccProfileInfoListResult>();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            int n = (Integer)iterator.next();
            GetEuiccProfileInfoListResult getEuiccProfileInfoListResult = EuiccController.get().blockingGetEuiccProfileInfoList(n);
            object = new StringBuilder();
            ((StringBuilder)object).append("blockingGetEuiccProfileInfoList cardId ");
            ((StringBuilder)object).append(n);
            SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
            arrayList.add(getEuiccProfileInfoListResult);
        }
        this.post((Runnable)new _$$Lambda$SubscriptionInfoUpdater$Y5woGfEDKrozRViLH7WF93qPEno(this, arrayList, updateEmbeddedSubsCallback));
    }

    public /* synthetic */ void lambda$updateSubscriptionByCarrierConfigAndNotifyComplete$6$SubscriptionInfoUpdater(int n, String string, PersistableBundle persistableBundle, Message message) {
        this.updateSubscriptionByCarrierConfig(n, string, persistableBundle);
        message.sendToTarget();
    }

    void requestEmbeddedSubscriptionInfoListRefresh(int n, Runnable runnable) {
        this.sendMessage(this.obtainMessage(12, n, 0, (Object)runnable));
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void updateEmbeddedSubscriptions(List<Integer> list, UpdateEmbeddedSubsCallback updateEmbeddedSubsCallback) {
        if (!this.mEuiccManager.isEnabled()) {
            updateEmbeddedSubsCallback.run(false);
            return;
        }
        this.mBackgroundHandler.post((Runnable)new _$$Lambda$SubscriptionInfoUpdater$qyDxq2AWyReUxdc6HttVGQeDD3Y(this, list, updateEmbeddedSubsCallback));
    }

    public void updateInternalIccState(String string, String string2, int n, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateInternalIccState to simStatus ");
        stringBuilder.append(string);
        stringBuilder.append(" reason ");
        stringBuilder.append(string2);
        stringBuilder.append(" slotId ");
        stringBuilder.append(n);
        SubscriptionInfoUpdater.logd(stringBuilder.toString());
        int n2 = this.internalIccStateToMessage(string);
        if (n2 != -1) {
            this.sendMessage(this.obtainMessage(n2, n, (int)bl, (Object)string2));
        }
    }

    @VisibleForTesting
    public void updateSubscriptionByCarrierConfig(int n, String object, PersistableBundle object2) {
        block34 : {
            block35 : {
                CharSequence charSequence;
                if (!SubscriptionManager.isValidPhoneId((int)n) || TextUtils.isEmpty((CharSequence)object) || object2 == null) break block34;
                SubscriptionController subscriptionController = SubscriptionController.getInstance();
                if (subscriptionController == null) {
                    SubscriptionInfoUpdater.loge("SubscriptionController was null");
                    return;
                }
                int n2 = subscriptionController.getSubIdUsingPhoneId(n);
                if (!SubscriptionManager.isValidSubscriptionId((int)n2) || n2 == Integer.MAX_VALUE) break block35;
                Object object3 = subscriptionController.getSubscriptionInfo(n2);
                if (object3 == null) {
                    SubscriptionInfoUpdater.loge("Couldn't retrieve subscription info for current subscription");
                    return;
                }
                if (!this.isCarrierServicePackage(n, (String)object)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Cannot manage subId=");
                    ((StringBuilder)object2).append(n2);
                    ((StringBuilder)object2).append(", carrierPackage=");
                    ((StringBuilder)object2).append((String)object);
                    SubscriptionInfoUpdater.loge(((StringBuilder)object2).toString());
                    return;
                }
                ContentValues contentValues = new ContentValues();
                boolean bl = object2.getBoolean("is_opportunistic_subscription_bool", false);
                if (object3.isOpportunistic() != bl) {
                    charSequence = new StringBuilder();
                    charSequence.append("Set SubId=");
                    charSequence.append(n2);
                    charSequence.append(" isOpportunistic=");
                    charSequence.append(bl);
                    SubscriptionInfoUpdater.logd(charSequence.toString());
                    charSequence = bl ? "1" : "0";
                    contentValues.put("is_opportunistic", (String)charSequence);
                }
                String string = object2.getString("subscription_group_uuid_string", ICCID_STRING_FOR_NO_SIM);
                object2 = null;
                charSequence = null;
                if (!TextUtils.isEmpty((CharSequence)string)) {
                    block32 : {
                        block33 : {
                            block31 : {
                                object2 = charSequence;
                                charSequence = ParcelUuid.fromString((String)string);
                                object2 = charSequence;
                                bl = charSequence.equals((Object)REMOVE_GROUP_UUID);
                                if (!bl) break block31;
                                object2 = charSequence;
                                if (object3.getGroupUuid() == null) break block31;
                                object2 = charSequence;
                                contentValues.put("group_uuid", (String)null);
                                object2 = charSequence;
                                object2 = charSequence;
                                object = new StringBuilder();
                                object2 = charSequence;
                                ((StringBuilder)object).append("Group Removed for");
                                object2 = charSequence;
                                ((StringBuilder)object).append(n2);
                                object2 = charSequence;
                                SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
                                break block32;
                            }
                            object2 = charSequence;
                            if (!SubscriptionController.getInstance().canPackageManageGroup((ParcelUuid)charSequence, (String)object)) break block33;
                            object2 = charSequence;
                            contentValues.put("group_uuid", charSequence.toString());
                            object2 = charSequence;
                            contentValues.put("group_owner", (String)object);
                            object2 = charSequence;
                            object2 = charSequence;
                            object = new StringBuilder();
                            object2 = charSequence;
                            ((StringBuilder)object).append("Group Added for");
                            object2 = charSequence;
                            ((StringBuilder)object).append(n2);
                            object2 = charSequence;
                            SubscriptionInfoUpdater.logd(((StringBuilder)object).toString());
                            break block32;
                        }
                        object2 = charSequence;
                        object2 = charSequence;
                        object3 = new StringBuilder();
                        object2 = charSequence;
                        ((StringBuilder)object3).append("configPackageName ");
                        object2 = charSequence;
                        ((StringBuilder)object3).append((String)object);
                        object2 = charSequence;
                        ((StringBuilder)object3).append(" doesn't own grouUuid ");
                        object2 = charSequence;
                        ((StringBuilder)object3).append((Object)charSequence);
                        object2 = charSequence;
                        try {
                            SubscriptionInfoUpdater.loge(((StringBuilder)object3).toString());
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid Group UUID=");
                            stringBuilder.append(string);
                            SubscriptionInfoUpdater.loge(stringBuilder.toString());
                        }
                    }
                    object2 = charSequence;
                }
                if (contentValues.size() > 0 && mContext.getContentResolver().update(SubscriptionManager.getUriForSubscriptionId((int)n2), contentValues, null, null) > 0) {
                    subscriptionController.refreshCachedActiveSubscriptionInfoList();
                    subscriptionController.notifySubscriptionInfoChanged();
                    MultiSimSettingController.getInstance().notifySubscriptionGroupChanged((ParcelUuid)object2);
                }
                return;
            }
            SubscriptionInfoUpdater.logd("No subscription is active for phone being updated");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("In updateSubscriptionByCarrierConfig(): phoneId=");
        stringBuilder.append(n);
        stringBuilder.append(" configPackageName=");
        stringBuilder.append((String)object);
        stringBuilder.append(" config=");
        object = object2 == null ? "null" : Integer.valueOf(object2.hashCode());
        stringBuilder.append(object);
        SubscriptionInfoUpdater.logd(stringBuilder.toString());
    }

    public void updateSubscriptionByCarrierConfigAndNotifyComplete(int n, String string, PersistableBundle persistableBundle, Message message) {
        this.post((Runnable)new _$$Lambda$SubscriptionInfoUpdater$ZTY4uxKw17CHcHQzbBUF7m_dN_E(this, n, string, persistableBundle, message));
    }

    private static interface UpdateEmbeddedSubsCallback {
        public void run(boolean var1);
    }

}


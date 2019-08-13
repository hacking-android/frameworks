/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 *  android.os.Message
 *  android.os.ParcelUuid
 *  android.provider.Settings
 *  android.provider.Settings$SettingNotFoundException
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.text.TextUtils
 *  android.util.Log
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$MultiSimSettingController
 *  com.android.internal.telephony.-$$Lambda$MultiSimSettingController$7eK1c9cJ2YdsAwoYGhX7w-7n-MM
 *  com.android.internal.telephony.-$$Lambda$MultiSimSettingController$OwaLr1D2oeslrR0hgRvph4WwUo8
 *  com.android.internal.util.ArrayUtils
 */
package com.android.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.GlobalSettingsHelper;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony._$$Lambda$MultiSimSettingController$55347QtGjuukX_px3jYZkJd_z3U;
import com.android.internal.telephony._$$Lambda$MultiSimSettingController$7eK1c9cJ2YdsAwoYGhX7w_7n_MM;
import com.android.internal.telephony._$$Lambda$MultiSimSettingController$DcLtrTEtdlCd4WOev4Zk79vrSko;
import com.android.internal.telephony._$$Lambda$MultiSimSettingController$OwaLr1D2oeslrR0hgRvph4WwUo8;
import com.android.internal.telephony._$$Lambda$MultiSimSettingController$WtGtOenjqxSBoW5BUjT_VlNoSTM;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.util.ArrayUtils;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiSimSettingController
extends Handler {
    private static final boolean DBG = true;
    private static final int EVENT_ALL_SUBSCRIPTIONS_LOADED = 3;
    private static final int EVENT_DEFAULT_DATA_SUBSCRIPTION_CHANGED = 6;
    private static final int EVENT_ROAMING_DATA_ENABLED = 2;
    private static final int EVENT_SUBSCRIPTION_GROUP_CHANGED = 5;
    private static final int EVENT_SUBSCRIPTION_INFO_CHANGED = 4;
    private static final int EVENT_USER_DATA_ENABLED = 1;
    private static final String LOG_TAG = "MultiSimSettingController";
    private static final int PRIMARY_SUB_ADDED = 1;
    private static final int PRIMARY_SUB_INITIALIZED = 6;
    private static final int PRIMARY_SUB_MARKED_OPPT = 5;
    private static final int PRIMARY_SUB_NO_CHANGE = 0;
    private static final int PRIMARY_SUB_REMOVED = 2;
    private static final int PRIMARY_SUB_SWAPPED = 3;
    private static final int PRIMARY_SUB_SWAPPED_IN_GROUP = 4;
    private static MultiSimSettingController sInstance = null;
    private final Context mContext;
    private List<Integer> mPrimarySubList = new ArrayList<Integer>();
    private final SubscriptionController mSubController;
    private boolean mSubInfoInitialized = false;

    @VisibleForTesting
    public MultiSimSettingController(Context context, SubscriptionController subscriptionController) {
        this.mContext = context;
        this.mSubController = subscriptionController;
    }

    private boolean areSubscriptionsInSameGroup(int n, int n2) {
        if (SubscriptionManager.isUsableSubscriptionId((int)n) && SubscriptionManager.isUsableSubscriptionId((int)n2)) {
            boolean bl = true;
            if (n == n2) {
                return true;
            }
            ParcelUuid parcelUuid = this.mSubController.getGroupUuid(n);
            ParcelUuid parcelUuid2 = this.mSubController.getGroupUuid(n2);
            if (parcelUuid == null || !parcelUuid.equals((Object)parcelUuid2)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    private void disableDataForNonDefaultNonOpportunisticSubscriptions() {
        if (!this.mSubInfoInitialized) {
            return;
        }
        int n = this.mSubController.getDefaultDataSubId();
        for (Phone phone : PhoneFactory.getPhones()) {
            if (phone.getSubId() == n || !SubscriptionManager.isValidSubscriptionId((int)phone.getSubId()) || this.mSubController.isOpportunistic(phone.getSubId()) || !phone.isUserDataEnabled() || this.areSubscriptionsInSameGroup(n, phone.getSubId())) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setting data to false on ");
            stringBuilder.append(phone.getSubId());
            this.log(stringBuilder.toString());
            phone.getDataEnabledSettings().setUserDataEnabled(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MultiSimSettingController getInstance() {
        synchronized (SubscriptionController.class) {
            if (sInstance != null) return sInstance;
            Log.wtf((String)LOG_TAG, (String)"getInstance null");
            return sInstance;
        }
    }

    private SimCombinationWarningParams getSimCombinationWarningParams(int n) {
        SimCombinationWarningParams simCombinationWarningParams = new SimCombinationWarningParams();
        if (this.mPrimarySubList.size() <= 1) {
            return simCombinationWarningParams;
        }
        if (!this.isUserVisibleChange(n)) {
            return simCombinationWarningParams;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        n = 0;
        for (int n2 : this.mPrimarySubList) {
            Phone phone = PhoneFactory.getPhone(SubscriptionManager.getPhoneId((int)n2));
            int n3 = n;
            if (phone != null) {
                n3 = n;
                if (phone.isCdmaSubscriptionAppPresent()) {
                    String string;
                    n3 = n + 1;
                    String string2 = string = this.mSubController.getActiveSubscriptionInfo(n2, this.mContext.getOpPackageName()).getDisplayName().toString();
                    if (TextUtils.isEmpty((CharSequence)string)) {
                        string2 = phone.getCarrierName();
                    }
                    arrayList.add(string2);
                }
            }
            n = n3;
        }
        if (n > 1) {
            simCombinationWarningParams.mWarningType = 1;
            simCombinationWarningParams.mSimNames = String.join((CharSequence)" & ", arrayList);
        }
        return simCombinationWarningParams;
    }

    private int getSimSelectDialogType(int n, boolean bl, boolean bl2, boolean bl3) {
        int n2;
        int n3 = 0;
        if (!(this.mPrimarySubList.size() != 1 || n != 2 || bl && bl3 && bl2)) {
            n2 = 4;
        } else {
            n2 = n3;
            if (this.mPrimarySubList.size() > 1) {
                n2 = n3;
                if (this.isUserVisibleChange(n)) {
                    n2 = 1;
                }
            }
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MultiSimSettingController init(Context object, SubscriptionController subscriptionController) {
        synchronized (SubscriptionController.class) {
            if (sInstance == null) {
                MultiSimSettingController multiSimSettingController;
                sInstance = multiSimSettingController = new MultiSimSettingController((Context)object, subscriptionController);
                return sInstance;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("init() called multiple times!  sInstance = ");
                ((StringBuilder)object).append((Object)sInstance);
                Log.wtf((String)LOG_TAG, (String)((StringBuilder)object).toString());
            }
            return sInstance;
        }
    }

    private boolean isUserVisibleChange(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = bl;
            if (n != 2) {
                bl2 = n == 3 ? bl : false;
            }
        }
        return bl2;
    }

    static /* synthetic */ boolean lambda$updatePrimarySubListAndGetChangeType$3(SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.isOpportunistic() ^ true;
    }

    static /* synthetic */ Integer lambda$updatePrimarySubListAndGetChangeType$4(SubscriptionInfo subscriptionInfo) {
        return subscriptionInfo.getSubscriptionId();
    }

    private void log(String string) {
        Log.d((String)LOG_TAG, (String)string);
    }

    private void loge(String string) {
        Log.e((String)LOG_TAG, (String)string);
    }

    private void onAllSubscriptionsLoaded() {
        this.log("onAllSubscriptionsLoaded");
        this.mSubInfoInitialized = true;
        this.updateDefaults(true);
        this.disableDataForNonDefaultNonOpportunisticSubscriptions();
    }

    private void onDefaultDataSettingChanged() {
        this.log("onDefaultDataSettingChanged");
        this.disableDataForNonDefaultNonOpportunisticSubscriptions();
    }

    private void onRoamingDataEnabled(int n, boolean bl) {
        this.log("onRoamingDataEnabled");
        this.setRoamingDataEnabledForGroup(n, bl);
        this.mSubController.setDataRoaming((int)bl, n);
    }

    private void onSubscriptionGroupChanged(ParcelUuid object) {
        this.log("onSubscriptionGroupChanged");
        object = this.mSubController.getSubscriptionsInGroup((ParcelUuid)object, this.mContext.getOpPackageName());
        if (object != null && !object.isEmpty()) {
            boolean bl;
            int n;
            int n2 = ((SubscriptionInfo)object.get(0)).getSubscriptionId();
            object = object.iterator();
            do {
                n = n2;
            } while (object.hasNext() && (!this.mSubController.isActiveSubId(n = ((SubscriptionInfo)object.next()).getSubscriptionId()) || this.mSubController.isOpportunistic(n)));
            object = new StringBuilder();
            ((StringBuilder)object).append("refSubId is ");
            ((StringBuilder)object).append(n);
            this.log(((StringBuilder)object).toString());
            boolean bl2 = false;
            bl2 = bl = GlobalSettingsHelper.getBoolean(this.mContext, "mobile_data", n);
            try {
                this.onUserDataEnabled(n, bl);
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                this.onUserDataEnabled(n, GlobalSettingsHelper.getBoolean(this.mContext, "mobile_data", -1, bl2));
            }
            bl2 = false;
            bl2 = bl = GlobalSettingsHelper.getBoolean(this.mContext, "data_roaming", n);
            try {
                this.onRoamingDataEnabled(n, bl);
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                this.onRoamingDataEnabled(n, GlobalSettingsHelper.getBoolean(this.mContext, "data_roaming", -1, bl2));
            }
            this.mSubController.syncGroupedSetting(n);
            return;
        }
    }

    private void onSubscriptionsChanged() {
        this.log("onSubscriptionsChanged");
        if (!this.mSubInfoInitialized) {
            return;
        }
        this.updateDefaults(false);
        this.disableDataForNonDefaultNonOpportunisticSubscriptions();
    }

    private void onUserDataEnabled(int n, boolean bl) {
        this.log("onUserDataEnabled");
        this.setUserDataEnabledForGroup(n, bl);
        if (this.mSubController.getDefaultDataSubId() != n && !this.mSubController.isOpportunistic(n) && bl) {
            this.mSubController.setDefaultDataSubId(n);
        }
    }

    private void sendSubChangeNotificationIfNeeded(int n, boolean bl, boolean bl2, boolean bl3) {
        int n2 = this.getSimSelectDialogType(n, bl, bl2, bl3);
        SimCombinationWarningParams simCombinationWarningParams = this.getSimCombinationWarningParams(n);
        if (n2 != 0 || simCombinationWarningParams.mWarningType != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[sendSubChangeNotificationIfNeeded] showing dialog type ");
            stringBuilder.append(n2);
            this.log(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("[sendSubChangeNotificationIfNeeded] showing sim warning ");
            stringBuilder.append(simCombinationWarningParams.mWarningType);
            this.log(stringBuilder.toString());
            stringBuilder = new Intent();
            stringBuilder.setAction("android.telephony.action.PRIMARY_SUBSCRIPTION_LIST_CHANGED");
            stringBuilder.setClassName("com.android.settings", "com.android.settings.sim.SimSelectNotification");
            stringBuilder.addFlags(268435456);
            stringBuilder.putExtra("android.telephony.extra.DEFAULT_SUBSCRIPTION_SELECT_TYPE", n2);
            if (n2 == 4) {
                stringBuilder.putExtra("android.telephony.extra.SUBSCRIPTION_ID", (Serializable)this.mPrimarySubList.get(0));
            }
            stringBuilder.putExtra("android.telephony.extra.SIM_COMBINATION_WARNING_TYPE", simCombinationWarningParams.mWarningType);
            if (simCombinationWarningParams.mWarningType == 1) {
                stringBuilder.putExtra("android.telephony.extra.SIM_COMBINATION_NAMES", simCombinationWarningParams.mSimNames);
            }
            this.mContext.sendBroadcast((Intent)stringBuilder);
        }
    }

    private void setRoamingDataEnabledForGroup(int n, boolean bl) {
        List<SubscriptionInfo> list = SubscriptionController.getInstance().getSubscriptionsInGroup(this.mSubController.getGroupUuid(n), this.mContext.getOpPackageName());
        if (list == null) {
            return;
        }
        for (SubscriptionInfo subscriptionInfo : list) {
            GlobalSettingsHelper.setBoolean(this.mContext, "data_roaming", subscriptionInfo.getSubscriptionId(), bl);
        }
    }

    private void setUserDataEnabledForGroup(int n, boolean bl) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setUserDataEnabledForGroup subId ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" enable ");
        ((StringBuilder)object).append(bl);
        this.log(((StringBuilder)object).toString());
        object = this.mSubController;
        object = ((SubscriptionController)((Object)object)).getSubscriptionsInGroup(((SubscriptionController)((Object)object)).getGroupUuid(n), this.mContext.getOpPackageName());
        if (object == null) {
            return;
        }
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            n = ((SubscriptionInfo)iterator.next()).getSubscriptionId();
            if (this.mSubController.isActiveSubId(n)) {
                object = PhoneFactory.getPhone(this.mSubController.getPhoneId(n));
                if (object == null) continue;
                ((Phone)object).getDataEnabledSettings().setUserDataEnabled(bl);
                continue;
            }
            GlobalSettingsHelper.setBoolean(this.mContext, "mobile_data", n, bl);
        }
    }

    private boolean updateDefaultValue(List<Integer> object, int n, UpdateDefaultAction updateDefaultAction) {
        int n2;
        block3 : {
            int n3;
            n2 = n3 = -1;
            if (object.size() > 0) {
                object = object.iterator();
                do {
                    n2 = n3;
                    if (!object.hasNext()) break block3;
                    n2 = (Integer)object.next();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("[updateDefaultValue] Record.id: ");
                    stringBuilder.append(n2);
                    this.log(stringBuilder.toString());
                } while (!this.areSubscriptionsInSameGroup(n2, n));
                object = new StringBuilder();
                ((StringBuilder)object).append("[updateDefaultValue] updates to subId=");
                ((StringBuilder)object).append(n2);
                this.log(((StringBuilder)object).toString());
            }
        }
        if (n != n2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[updateDefaultValue: subId] from ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" to ");
            ((StringBuilder)object).append(n2);
            this.log(((StringBuilder)object).toString());
            updateDefaultAction.update(n2);
        }
        return SubscriptionManager.isValidSubscriptionId((int)n2);
    }

    private void updateDefaults(boolean bl) {
        this.log("updateDefaults");
        if (!this.mSubInfoInitialized) {
            return;
        }
        List<SubscriptionInfo> list = this.mSubController.getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
        if (ArrayUtils.isEmpty(list)) {
            this.mPrimarySubList.clear();
            this.log("[updateDefaultValues] No active sub. Setting default to INVALID sub.");
            this.mSubController.setDefaultDataSubId(-1);
            this.mSubController.setDefaultVoiceSubId(-1);
            this.mSubController.setDefaultSmsSubId(-1);
            return;
        }
        int n = this.updatePrimarySubListAndGetChangeType(list, bl);
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("[updateDefaultValues] change: ");
        ((StringBuilder)((Object)list)).append(n);
        this.log(((StringBuilder)((Object)list)).toString());
        if (n == 0) {
            return;
        }
        if (this.mPrimarySubList.size() == 1 && n != 2) {
            n = this.mPrimarySubList.get(0);
            list = new StringBuilder();
            ((StringBuilder)((Object)list)).append("[updateDefaultValues] to only primary sub ");
            ((StringBuilder)((Object)list)).append(n);
            this.log(((StringBuilder)((Object)list)).toString());
            this.mSubController.setDefaultDataSubId(n);
            this.mSubController.setDefaultVoiceSubId(n);
            this.mSubController.setDefaultSmsSubId(n);
            return;
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("[updateDefaultValues] records: ");
        ((StringBuilder)((Object)list)).append(this.mPrimarySubList);
        this.log(((StringBuilder)((Object)list)).toString());
        this.log("[updateDefaultValues] Update default data subscription");
        bl = this.updateDefaultValue(this.mPrimarySubList, this.mSubController.getDefaultDataSubId(), new _$$Lambda$MultiSimSettingController$55347QtGjuukX_px3jYZkJd_z3U(this));
        this.log("[updateDefaultValues] Update default voice subscription");
        boolean bl2 = this.updateDefaultValue(this.mPrimarySubList, this.mSubController.getDefaultVoiceSubId(), new _$$Lambda$MultiSimSettingController$WtGtOenjqxSBoW5BUjT_VlNoSTM(this));
        this.log("[updateDefaultValues] Update default sms subscription");
        this.sendSubChangeNotificationIfNeeded(n, bl, bl2, this.updateDefaultValue(this.mPrimarySubList, this.mSubController.getDefaultSmsSubId(), new _$$Lambda$MultiSimSettingController$DcLtrTEtdlCd4WOev4Zk79vrSko(this)));
    }

    private int updatePrimarySubListAndGetChangeType(List<SubscriptionInfo> object, boolean bl) {
        Object object2 = this.mPrimarySubList;
        this.mPrimarySubList = object.stream().filter(_$$Lambda$MultiSimSettingController$7eK1c9cJ2YdsAwoYGhX7w_7n_MM.INSTANCE).map(_$$Lambda$MultiSimSettingController$OwaLr1D2oeslrR0hgRvph4WwUo8.INSTANCE).collect(Collectors.toList());
        if (bl) {
            return 6;
        }
        if (this.mPrimarySubList.equals(object2)) {
            return 0;
        }
        if (this.mPrimarySubList.size() > object2.size()) {
            return 1;
        }
        if (this.mPrimarySubList.size() == object2.size()) {
            for (int n : this.mPrimarySubList) {
                boolean bl2;
                block8 : {
                    boolean bl3 = false;
                    object = object2.iterator();
                    do {
                        bl2 = bl3;
                        if (!object.hasNext()) break block8;
                    } while (!this.areSubscriptionsInSameGroup(n, (Integer)object.next()));
                    bl2 = true;
                }
                if (bl2) continue;
                return 3;
            }
            return 4;
        }
        object2 = object2.iterator();
        while (object2.hasNext()) {
            int n = (Integer)object2.next();
            if (this.mPrimarySubList.contains(n)) continue;
            if (!this.mSubController.isActiveSubId(n)) {
                return 2;
            }
            if (this.mSubController.isOpportunistic(n)) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("[updatePrimarySubListAndGetChangeType]: missing active primary subId ");
            ((StringBuilder)object).append(n);
            this.loge(((StringBuilder)object).toString());
        }
        return 5;
    }

    public void handleMessage(Message message) {
        int n = message.what;
        boolean bl = true;
        boolean bl2 = true;
        switch (n) {
            default: {
                break;
            }
            case 6: {
                this.onDefaultDataSettingChanged();
                break;
            }
            case 5: {
                this.onSubscriptionGroupChanged((ParcelUuid)message.obj);
                break;
            }
            case 4: {
                this.onSubscriptionsChanged();
                break;
            }
            case 3: {
                this.onAllSubscriptionsLoaded();
                break;
            }
            case 2: {
                n = message.arg1;
                if (message.arg2 == 0) {
                    bl2 = false;
                }
                this.onRoamingDataEnabled(n, bl2);
                break;
            }
            case 1: {
                n = message.arg1;
                bl2 = message.arg2 != 0 ? bl : false;
                this.onUserDataEnabled(n, bl2);
            }
        }
    }

    public /* synthetic */ void lambda$updateDefaults$0$MultiSimSettingController(int n) {
        this.mSubController.setDefaultDataSubId(n);
    }

    public /* synthetic */ void lambda$updateDefaults$1$MultiSimSettingController(int n) {
        this.mSubController.setDefaultVoiceSubId(n);
    }

    public /* synthetic */ void lambda$updateDefaults$2$MultiSimSettingController(int n) {
        this.mSubController.setDefaultSmsSubId(n);
    }

    public void notifyAllSubscriptionLoaded() {
        this.obtainMessage(3).sendToTarget();
    }

    public void notifyDefaultDataSubChanged() {
        this.obtainMessage(6).sendToTarget();
    }

    public void notifyRoamingDataEnabled(int n, boolean bl) {
        this.obtainMessage(2, n, (int)bl).sendToTarget();
    }

    public void notifySubscriptionGroupChanged(ParcelUuid parcelUuid) {
        this.obtainMessage(5, (Object)parcelUuid).sendToTarget();
    }

    public void notifySubscriptionInfoChanged() {
        this.obtainMessage(4).sendToTarget();
    }

    public void notifyUserDataEnabled(int n, boolean bl) {
        this.obtainMessage(1, n, (int)bl).sendToTarget();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface PrimarySubChangeType {
    }

    private class SimCombinationWarningParams {
        String mSimNames;
        int mWarningType = 0;

        private SimCombinationWarningParams() {
        }
    }

    private static interface UpdateDefaultAction {
        public void update(int var1);
    }

}


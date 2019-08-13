/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.os.Handler
 *  android.os.ParcelUuid
 *  android.os.PersistableBundle
 *  android.os.RegistrantList
 *  android.os.SystemProperties
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneStateListener
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionInfo
 *  android.telephony.SubscriptionManager
 *  android.telephony.SubscriptionManager$OnSubscriptionsChangedListener
 *  android.telephony.TelephonyManager
 *  android.util.LocalLog
 *  android.util.Pair
 */
package com.android.internal.telephony.dataconnection;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.ParcelUuid;
import android.os.PersistableBundle;
import android.os.RegistrantList;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneStateListener;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.LocalLog;
import android.util.Pair;
import com.android.internal.telephony.GlobalSettingsHelper;
import com.android.internal.telephony.MultiSimSettingController;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.dataconnection.DataEnabledOverride;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DataEnabledSettings {
    private static final String LOG_TAG = "DataEnabledSettings";
    public static final int REASON_DATA_ENABLED_BY_CARRIER = 4;
    public static final int REASON_INTERNAL_DATA_ENABLED = 1;
    public static final int REASON_OVERRIDE_CONDITION_CHANGED = 8;
    public static final int REASON_OVERRIDE_RULE_CHANGED = 7;
    public static final int REASON_POLICY_DATA_ENABLED = 3;
    public static final int REASON_PROVISIONED_CHANGED = 5;
    public static final int REASON_PROVISIONING_DATA_ENABLED_CHANGED = 6;
    public static final int REASON_REGISTERED = 0;
    public static final int REASON_USER_DATA_ENABLED = 2;
    private boolean mCarrierDataEnabled = true;
    private DataEnabledOverride mDataEnabledOverride;
    private boolean mInternalDataEnabled = true;
    private boolean mIsDataEnabled = false;
    private final SubscriptionManager.OnSubscriptionsChangedListener mOnSubscriptionsChangeListener = new SubscriptionManager.OnSubscriptionsChangedListener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onSubscriptionsChanged() {
            synchronized (this) {
                if (DataEnabledSettings.this.mSubId != DataEnabledSettings.this.mPhone.getSubId()) {
                    DataEnabledSettings dataEnabledSettings = DataEnabledSettings.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onSubscriptionsChanged subId: ");
                    stringBuilder.append(DataEnabledSettings.this.mSubId);
                    stringBuilder.append(" to: ");
                    stringBuilder.append(DataEnabledSettings.this.mPhone.getSubId());
                    dataEnabledSettings.log(stringBuilder.toString());
                    DataEnabledSettings.this.mSubId = DataEnabledSettings.this.mPhone.getSubId();
                    DataEnabledSettings.this.mDataEnabledOverride = DataEnabledSettings.this.getDataEnabledOverride();
                    DataEnabledSettings.this.updatePhoneStateListener();
                    DataEnabledSettings.this.updateDataEnabledAndNotify(2);
                    DataEnabledSettings.this.mPhone.notifyUserMobileDataStateChanged(DataEnabledSettings.this.isUserDataEnabled());
                }
                return;
            }
        }
    };
    private final RegistrantList mOverallDataEnabledChangedRegistrants = new RegistrantList();
    private final RegistrantList mOverallDataEnabledOverrideChangedRegistrants = new RegistrantList();
    private final Phone mPhone;
    private final PhoneStateListener mPhoneStateListener = new PhoneStateListener(){

        public void onCallStateChanged(int n, String string) {
            DataEnabledSettings.this.updateDataEnabledAndNotify(8);
        }
    };
    private boolean mPolicyDataEnabled = true;
    private ContentResolver mResolver = null;
    private final LocalLog mSettingChangeLocalLog = new LocalLog(50);
    private int mSubId = -1;
    private TelephonyManager mTelephonyManager;

    public DataEnabledSettings(Phone phone) {
        this.mPhone = phone;
        this.mResolver = this.mPhone.getContext().getContentResolver();
        ((SubscriptionManager)this.mPhone.getContext().getSystemService("telephony_subscription_service")).addOnSubscriptionsChangedListener(this.mOnSubscriptionsChangeListener);
        this.mTelephonyManager = (TelephonyManager)this.mPhone.getContext().getSystemService("phone");
        this.mDataEnabledOverride = this.getDataEnabledOverride();
        this.updateDataEnabled();
    }

    private DataEnabledOverride getDataEnabledOverride() {
        return new DataEnabledOverride(SubscriptionController.getInstance().getDataEnabledOverrideRules(this.mPhone.getSubId()));
    }

    private static boolean isStandAloneOpportunistic(int n, Context context) {
        context = SubscriptionController.getInstance().getActiveSubscriptionInfo(n, context.getOpPackageName());
        boolean bl = context != null && context.isOpportunistic() && context.getGroupUuid() == null;
        return bl;
    }

    private void localLog(String string, boolean bl) {
        LocalLog localLog = this.mSettingChangeLocalLog;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" change to ");
        stringBuilder.append(bl);
        localLog.log(stringBuilder.toString());
    }

    private void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private void notifyDataEnabledChanged(boolean bl, int n) {
        this.mOverallDataEnabledChangedRegistrants.notifyResult((Object)new Pair((Object)bl, (Object)n));
    }

    private void notifyDataEnabledOverrideChanged() {
        this.mOverallDataEnabledOverrideChangedRegistrants.notifyRegistrants();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateDataEnabled() {
        synchronized (this) {
            if (this.isProvisioning()) {
                this.mIsDataEnabled = this.isProvisioningDataEnabled();
            } else {
                boolean bl = this.mInternalDataEnabled && (this.isUserDataEnabled() || this.mDataEnabledOverride.shouldOverrideDataEnabledSettings(this.mPhone, 255)) && this.mPolicyDataEnabled && this.mCarrierDataEnabled;
                this.mIsDataEnabled = bl;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateDataEnabledAndNotify(int n) {
        synchronized (this) {
            boolean bl = this.mIsDataEnabled;
            this.updateDataEnabled();
            if (bl != this.mIsDataEnabled) {
                bl = !bl;
                this.notifyDataEnabledChanged(bl, n);
            }
            return;
        }
    }

    private void updatePhoneStateListener() {
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        if (SubscriptionManager.isUsableSubscriptionId((int)this.mSubId)) {
            this.mTelephonyManager = this.mTelephonyManager.createForSubscriptionId(this.mSubId);
        }
        this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
    }

    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println(" DataEnabledSettings=");
        this.mSettingChangeLocalLog.dump(fileDescriptor, printWriter, arrstring);
    }

    public boolean getDataRoamingEnabled() {
        synchronized (this) {
            boolean bl = GlobalSettingsHelper.getBoolean(this.mPhone.getContext(), "data_roaming", this.mPhone.getSubId(), this.getDefaultDataRoamingEnabled());
            return bl;
        }
    }

    public boolean getDefaultDataRoamingEnabled() {
        synchronized (this) {
            CarrierConfigManager carrierConfigManager = (CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config");
            boolean bl = "true".equalsIgnoreCase(SystemProperties.get((String)"ro.com.android.dataroaming", (String)"false"));
            boolean bl2 = carrierConfigManager.getConfigForSubId(this.mPhone.getSubId()).getBoolean("carrier_default_data_roaming_enabled_bool");
            return bl | bl2;
        }
    }

    public boolean isCarrierDataEnabled() {
        synchronized (this) {
            boolean bl = this.mCarrierDataEnabled;
            return bl;
        }
    }

    public boolean isDataAllowedInVoiceCall() {
        synchronized (this) {
            boolean bl = this.mDataEnabledOverride.isDataAllowedInVoiceCall();
            return bl;
        }
    }

    public boolean isDataEnabled() {
        synchronized (this) {
            boolean bl = this.mIsDataEnabled;
            return bl;
        }
    }

    public boolean isDataEnabled(int n) {
        synchronized (this) {
            boolean bl;
            block4 : {
                if (!this.isProvisioning()) break block4;
                boolean bl2 = this.isProvisioningDataEnabled();
                return bl2;
            }
            boolean bl3 = this.isUserDataEnabled();
            boolean bl4 = this.mDataEnabledOverride.shouldOverrideDataEnabledSettings(this.mPhone, n);
            bl = this.mInternalDataEnabled && this.mPolicyDataEnabled && (bl = this.mCarrierDataEnabled) && (bl3 || bl4);
            return bl;
        }
    }

    public boolean isInternalDataEnabled() {
        synchronized (this) {
            boolean bl = this.mInternalDataEnabled;
            return bl;
        }
    }

    public boolean isPolicyDataEnabled() {
        synchronized (this) {
            boolean bl = this.mPolicyDataEnabled;
            return bl;
        }
    }

    public boolean isProvisioning() {
        ContentResolver contentResolver = this.mResolver;
        boolean bl = false;
        if (Settings.Global.getInt((ContentResolver)contentResolver, (String)"device_provisioned", (int)0) == 0) {
            bl = true;
        }
        return bl;
    }

    public boolean isProvisioningDataEnabled() {
        String string = SystemProperties.get((String)"ro.com.android.prov_mobiledata", (String)"false");
        int n = "true".equalsIgnoreCase(string);
        Object object = this.mResolver;
        boolean bl = (n = Settings.Global.getInt((ContentResolver)object, (String)"device_provisioning_mobile_data", (int)n)) != 0;
        object = new StringBuilder();
        ((StringBuilder)object).append("getDataEnabled during provisioning retVal=");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(" - (");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        this.log(((StringBuilder)object).toString());
        return bl;
    }

    public boolean isUserDataEnabled() {
        synchronized (this) {
            boolean bl;
            block4 : {
                bl = DataEnabledSettings.isStandAloneOpportunistic(this.mPhone.getSubId(), this.mPhone.getContext());
                if (!bl) break block4;
                return true;
            }
            bl = "true".equalsIgnoreCase(SystemProperties.get((String)"ro.com.android.mobiledata", (String)"true"));
            bl = GlobalSettingsHelper.getBoolean(this.mPhone.getContext(), "mobile_data", this.mPhone.getSubId(), bl);
            return bl;
        }
    }

    public void registerForDataEnabledChanged(Handler handler, int n, Object object) {
        this.mOverallDataEnabledChangedRegistrants.addUnique(handler, n, object);
        this.notifyDataEnabledChanged(this.isDataEnabled(), 0);
    }

    public void registerForDataEnabledOverrideChanged(Handler handler, int n) {
        this.mOverallDataEnabledOverrideChangedRegistrants.addUnique(handler, n, null);
        this.notifyDataEnabledOverrideChanged();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean setAllowDataDuringVoiceCall(boolean bl) {
        synchronized (this) {
            this.localLog("setAllowDataDuringVoiceCall", bl);
            this.mDataEnabledOverride.setDataAllowedInVoiceCall(bl);
            bl = SubscriptionController.getInstance().setDataEnabledOverrideRules(this.mPhone.getSubId(), this.mDataEnabledOverride.getRules());
            if (bl) {
                this.updateDataEnabledAndNotify(7);
                this.notifyDataEnabledOverrideChanged();
            }
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean setAlwaysAllowMmsData(boolean bl) {
        synchronized (this) {
            this.localLog("setAlwaysAllowMmsData", bl);
            this.mDataEnabledOverride.setAlwaysAllowMms(bl);
            bl = SubscriptionController.getInstance().setDataEnabledOverrideRules(this.mPhone.getSubId(), this.mDataEnabledOverride.getRules());
            if (bl) {
                this.updateDataEnabledAndNotify(7);
                this.notifyDataEnabledOverrideChanged();
            }
            return bl;
        }
    }

    public void setCarrierDataEnabled(boolean bl) {
        synchronized (this) {
            this.localLog("CarrierDataEnabled", bl);
            if (this.mCarrierDataEnabled != bl) {
                this.mCarrierDataEnabled = bl;
                this.updateDataEnabledAndNotify(4);
            }
            return;
        }
    }

    public void setDataRoamingEnabled(boolean bl) {
        synchronized (this) {
            this.localLog("setDataRoamingEnabled", bl);
            if (GlobalSettingsHelper.setBoolean(this.mPhone.getContext(), "data_roaming", this.mPhone.getSubId(), bl)) {
                MultiSimSettingController.getInstance().notifyRoamingDataEnabled(this.mPhone.getSubId(), bl);
            }
            return;
        }
    }

    public void setInternalDataEnabled(boolean bl) {
        synchronized (this) {
            this.localLog("InternalDataEnabled", bl);
            if (this.mInternalDataEnabled != bl) {
                this.mInternalDataEnabled = bl;
                this.updateDataEnabledAndNotify(1);
            }
            return;
        }
    }

    public void setPolicyDataEnabled(boolean bl) {
        synchronized (this) {
            this.localLog("PolicyDataEnabled", bl);
            if (this.mPolicyDataEnabled != bl) {
                this.mPolicyDataEnabled = bl;
                this.updateDataEnabledAndNotify(3);
            }
            return;
        }
    }

    public void setUserDataEnabled(boolean bl) {
        synchronized (this) {
            block6 : {
                boolean bl2 = DataEnabledSettings.isStandAloneOpportunistic(this.mPhone.getSubId(), this.mPhone.getContext());
                if (!bl2 || bl) break block6;
                return;
            }
            this.localLog("UserDataEnabled", bl);
            Context context = this.mPhone.getContext();
            int n = this.mPhone.getSubId();
            int n2 = bl ? 1 : 0;
            if (GlobalSettingsHelper.setInt(context, "mobile_data", n, n2)) {
                this.mPhone.notifyUserMobileDataStateChanged(bl);
                this.updateDataEnabledAndNotify(2);
                MultiSimSettingController.getInstance().notifyUserDataEnabled(this.mPhone.getSubId(), bl);
            }
            return;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[mInternalDataEnabled=");
        stringBuilder.append(this.mInternalDataEnabled);
        stringBuilder.append(", isUserDataEnabled=");
        stringBuilder.append(this.isUserDataEnabled());
        stringBuilder.append(", isProvisioningDataEnabled=");
        stringBuilder.append(this.isProvisioningDataEnabled());
        stringBuilder.append(", mPolicyDataEnabled=");
        stringBuilder.append(this.mPolicyDataEnabled);
        stringBuilder.append(", mCarrierDataEnabled=");
        stringBuilder.append(this.mCarrierDataEnabled);
        stringBuilder.append(", mIsDataEnabled=");
        stringBuilder.append(this.mIsDataEnabled);
        stringBuilder.append(", ");
        stringBuilder.append(this.mDataEnabledOverride);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void unregisterForDataEnabledChanged(Handler handler) {
        this.mOverallDataEnabledChangedRegistrants.remove(handler);
    }

    public void unregisterForDataEnabledOverrideChanged(Handler handler) {
        this.mOverallDataEnabledOverrideChangedRegistrants.remove(handler);
    }

    public void updateProvisionedChanged() {
        synchronized (this) {
            this.updateDataEnabledAndNotify(5);
            return;
        }
    }

    public void updateProvisioningDataEnabled() {
        synchronized (this) {
            this.updateDataEnabledAndNotify(6);
            return;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DataEnabledChangedReason {
    }

}


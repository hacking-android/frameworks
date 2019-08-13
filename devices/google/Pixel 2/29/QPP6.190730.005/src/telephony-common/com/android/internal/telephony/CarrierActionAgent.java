/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.database.ContentObserver
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.provider.Telephony
 *  android.provider.Telephony$Carriers
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.util.LocalLog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.util.LocalLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CarrierSignalAgent;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SettingsObserver;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;

public class CarrierActionAgent
extends Handler {
    public static final int CARRIER_ACTION_REPORT_DEFAULT_NETWORK_STATUS = 3;
    public static final int CARRIER_ACTION_RESET = 2;
    public static final int CARRIER_ACTION_SET_METERED_APNS_ENABLED = 0;
    public static final int CARRIER_ACTION_SET_RADIO_ENABLED = 1;
    private static final boolean DBG = true;
    public static final int EVENT_APM_SETTINGS_CHANGED = 4;
    public static final int EVENT_APN_SETTINGS_CHANGED = 8;
    public static final int EVENT_DATA_ROAMING_OFF = 6;
    public static final int EVENT_MOBILE_DATA_SETTINGS_CHANGED = 5;
    public static final int EVENT_SIM_STATE_CHANGED = 7;
    private static final String LOG_TAG = "CarrierActionAgent";
    private static final boolean VDBG = Rlog.isLoggable((String)"CarrierActionAgent", (int)2);
    private Boolean mCarrierActionOnMeteredApnEnabled;
    private Boolean mCarrierActionOnRadioEnabled;
    private Boolean mCarrierActionReportDefaultNetworkStatus;
    private RegistrantList mDefaultNetworkReportRegistrants = new RegistrantList();
    private RegistrantList mMeteredApnEnableRegistrants = new RegistrantList();
    private LocalLog mMeteredApnEnabledLog = new LocalLog(10);
    private final Phone mPhone;
    private RegistrantList mRadioEnableRegistrants = new RegistrantList();
    private LocalLog mRadioEnabledLog = new LocalLog(10);
    private final BroadcastReceiver mReceiver;
    private LocalLog mReportDefaultNetworkStatusLog = new LocalLog(10);
    private final SettingsObserver mSettingsObserver;

    public CarrierActionAgent(Phone phone) {
        Boolean bl;
        this.mCarrierActionOnMeteredApnEnabled = bl = Boolean.valueOf(true);
        this.mCarrierActionOnRadioEnabled = bl;
        this.mCarrierActionReportDefaultNetworkStatus = false;
        this.mReceiver = new BroadcastReceiver(){

            public void onReceive(Context object, Intent object2) {
                String string = object2.getAction();
                object = object2.getStringExtra("ss");
                if ("android.intent.action.SIM_STATE_CHANGED".equals(string)) {
                    if (object2.getBooleanExtra("rebroadcastOnUnlock", false)) {
                        return;
                    }
                    object2 = CarrierActionAgent.this;
                    object2.sendMessage(object2.obtainMessage(7, object));
                }
            }
        };
        this.mPhone = phone;
        this.mPhone.getContext().registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.SIM_STATE_CHANGED"));
        this.mSettingsObserver = new SettingsObserver(this.mPhone.getContext(), this);
        this.log("Creating CarrierActionAgent");
    }

    private Boolean getCarrierActionEnabled(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported action: ");
                    stringBuilder.append(n);
                    this.loge(stringBuilder.toString());
                    return null;
                }
                return this.mCarrierActionReportDefaultNetworkStatus;
            }
            return this.mCarrierActionOnRadioEnabled;
        }
        return this.mCarrierActionOnMeteredApnEnabled;
    }

    private RegistrantList getRegistrantsFromAction(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported action: ");
                    stringBuilder.append(n);
                    this.loge(stringBuilder.toString());
                    return null;
                }
                return this.mDefaultNetworkReportRegistrants;
            }
            return this.mRadioEnableRegistrants;
        }
        return this.mMeteredApnEnableRegistrants;
    }

    private void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private void logv(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
    }

    public void carrierActionReportDefaultNetworkStatus(boolean bl) {
        this.sendMessage(this.obtainMessage(3, (Object)bl));
    }

    public void carrierActionReset() {
        this.carrierActionReportDefaultNetworkStatus(false);
        this.carrierActionSetMeteredApnsEnabled(true);
        this.carrierActionSetRadioEnabled(true);
        this.mPhone.getCarrierSignalAgent().notifyCarrierSignalReceivers(new Intent("com.android.internal.telephony.CARRIER_SIGNAL_RESET"));
    }

    public void carrierActionSetMeteredApnsEnabled(boolean bl) {
        this.sendMessage(this.obtainMessage(0, (Object)bl));
    }

    public void carrierActionSetRadioEnabled(boolean bl) {
        this.sendMessage(this.obtainMessage(1, (Object)bl));
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)printWriter, "  ");
        printWriter.println(" mCarrierActionOnMeteredApnsEnabled Log:");
        indentingPrintWriter.increaseIndent();
        this.mMeteredApnEnabledLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
        printWriter.println(" mCarrierActionOnRadioEnabled Log:");
        indentingPrintWriter.increaseIndent();
        this.mRadioEnabledLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
        printWriter.println(" mCarrierActionReportDefaultNetworkStatus Log:");
        indentingPrintWriter.increaseIndent();
        this.mReportDefaultNetworkStatusLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
    }

    @VisibleForTesting
    public ContentObserver getContentObserver() {
        return this.mSettingsObserver;
    }

    public void handleMessage(Message object) {
        Serializable serializable = this.getCarrierActionEnabled(((Message)object).what);
        if (serializable != null && ((Boolean)serializable).booleanValue() == ((Boolean)((Message)object).obj).booleanValue()) {
            return;
        }
        int n = ((Message)object).what;
        int n2 = 5;
        switch (n) {
            default: {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Unknown carrier action: ");
                ((StringBuilder)serializable).append(((Message)object).what);
                this.loge(((StringBuilder)serializable).toString());
                break;
            }
            case 8: {
                this.log("EVENT_APN_SETTINGS_CHANGED");
                this.carrierActionReset();
                break;
            }
            case 7: {
                object = (String)((Message)object).obj;
                if ("LOADED".equals(object)) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("EVENT_SIM_STATE_CHANGED status: ");
                    ((StringBuilder)serializable).append((String)object);
                    this.log(((StringBuilder)serializable).toString());
                    this.carrierActionReset();
                    object = "mobile_data";
                    if (TelephonyManager.getDefault().getSimCount() != 1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("mobile_data");
                        ((StringBuilder)object).append(this.mPhone.getSubId());
                        object = ((StringBuilder)object).toString();
                    }
                    this.mSettingsObserver.observe(Settings.Global.getUriFor((String)object), 5);
                    this.mSettingsObserver.observe(Settings.Global.getUriFor((String)"airplane_mode_on"), 4);
                    this.mSettingsObserver.observe(Telephony.Carriers.CONTENT_URI, 8);
                    if (this.mPhone.getServiceStateTracker() == null) break;
                    this.mPhone.getServiceStateTracker().registerForDataRoamingOff(this, 6, null, false);
                    break;
                }
                if (!"ABSENT".equals(object)) break;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("EVENT_SIM_STATE_CHANGED status: ");
                ((StringBuilder)serializable).append((String)object);
                this.log(((StringBuilder)serializable).toString());
                this.carrierActionReset();
                this.mSettingsObserver.unobserve();
                if (this.mPhone.getServiceStateTracker() == null) break;
                this.mPhone.getServiceStateTracker().unregisterForDataRoamingOff(this);
                break;
            }
            case 6: {
                this.log("EVENT_DATA_ROAMING_OFF");
                this.carrierActionReset();
                break;
            }
            case 5: {
                this.log("EVENT_MOBILE_DATA_SETTINGS_CHANGED");
                if (this.mPhone.isUserDataEnabled()) break;
                this.carrierActionReset();
                break;
            }
            case 4: {
                this.log("EVENT_APM_SETTINGS_CHANGED");
                if (Settings.Global.getInt((ContentResolver)this.mPhone.getContext().getContentResolver(), (String)"airplane_mode_on", (int)0) == 0) break;
                this.carrierActionReset();
                break;
            }
            case 3: {
                this.mCarrierActionReportDefaultNetworkStatus = (boolean)((Boolean)((Message)object).obj);
                object = new StringBuilder();
                ((StringBuilder)object).append("CARRIER_ACTION_REPORT_AT_DEFAULT_NETWORK_STATUS: ");
                ((StringBuilder)object).append(this.mCarrierActionReportDefaultNetworkStatus);
                this.log(((StringBuilder)object).toString());
                serializable = this.mReportDefaultNetworkStatusLog;
                object = new StringBuilder();
                ((StringBuilder)object).append("REGISTER_DEFAULT_NETWORK_STATUS: ");
                ((StringBuilder)object).append(this.mCarrierActionReportDefaultNetworkStatus);
                serializable.log(((StringBuilder)object).toString());
                this.mDefaultNetworkReportRegistrants.notifyRegistrants(new AsyncResult(null, (Object)this.mCarrierActionReportDefaultNetworkStatus, null));
                break;
            }
            case 2: {
                this.log("CARRIER_ACTION_RESET");
                this.carrierActionReset();
                break;
            }
            case 1: {
                this.mCarrierActionOnRadioEnabled = (boolean)((Boolean)((Message)object).obj);
                object = new StringBuilder();
                ((StringBuilder)object).append("SET_RADIO_ENABLED: ");
                ((StringBuilder)object).append(this.mCarrierActionOnRadioEnabled);
                this.log(((StringBuilder)object).toString());
                object = this.mRadioEnabledLog;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("SET_RADIO_ENABLED: ");
                ((StringBuilder)serializable).append(this.mCarrierActionOnRadioEnabled);
                object.log(((StringBuilder)serializable).toString());
                this.mRadioEnableRegistrants.notifyRegistrants(new AsyncResult(null, (Object)this.mCarrierActionOnRadioEnabled, null));
                break;
            }
            case 0: {
                this.mCarrierActionOnMeteredApnEnabled = (boolean)((Boolean)((Message)object).obj);
                object = new StringBuilder();
                ((StringBuilder)object).append("SET_METERED_APNS_ENABLED: ");
                ((StringBuilder)object).append(this.mCarrierActionOnMeteredApnEnabled);
                this.log(((StringBuilder)object).toString());
                object = this.mMeteredApnEnabledLog;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("SET_METERED_APNS_ENABLED: ");
                ((StringBuilder)serializable).append(this.mCarrierActionOnMeteredApnEnabled);
                object.log(((StringBuilder)serializable).toString());
                if (this.mCarrierActionOnMeteredApnEnabled.booleanValue()) {
                    n2 = this.mPhone.getServiceStateTracker().getOtasp();
                }
                this.mPhone.notifyOtaspChanged(n2);
                this.mMeteredApnEnableRegistrants.notifyRegistrants(new AsyncResult(null, (Object)this.mCarrierActionOnMeteredApnEnabled, null));
            }
        }
    }

    public void registerForCarrierAction(int n, Handler object, int n2, Object object2, boolean bl) {
        Boolean bl2 = this.getCarrierActionEnabled(n);
        if (bl2 != null) {
            RegistrantList registrantList = this.getRegistrantsFromAction(n);
            object = new Registrant((Handler)object, n2, object2);
            registrantList.add((Registrant)object);
            if (bl) {
                object.notifyRegistrant(new AsyncResult(null, (Object)bl2, null));
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("invalid carrier action: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void unregisterForCarrierAction(Handler object, int n) {
        RegistrantList registrantList = this.getRegistrantsFromAction(n);
        if (registrantList != null) {
            registrantList.remove((Handler)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("invalid carrier action: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

}


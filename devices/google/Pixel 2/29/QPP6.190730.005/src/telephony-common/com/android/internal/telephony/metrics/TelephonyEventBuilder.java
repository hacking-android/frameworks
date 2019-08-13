/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.telephony.TelephonyManager
 *  android.util.SparseArray
 */
package com.android.internal.telephony.metrics;

import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.SparseArray;
import com.android.internal.telephony.nano.TelephonyProto;
import java.util.Arrays;

public class TelephonyEventBuilder {
    private final TelephonyProto.TelephonyEvent mEvent;

    public TelephonyEventBuilder() {
        this(-1);
    }

    public TelephonyEventBuilder(int n) {
        this(SystemClock.elapsedRealtime(), n);
    }

    public TelephonyEventBuilder(long l, int n) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent = new TelephonyProto.TelephonyEvent();
        telephonyEvent.timestampMillis = l;
        telephonyEvent.phoneId = n;
    }

    public TelephonyProto.TelephonyEvent build() {
        return this.mEvent;
    }

    public TelephonyEventBuilder setActiveSubscriptionInfoChange(TelephonyProto.ActiveSubscriptionInfo activeSubscriptionInfo) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 19;
        telephonyEvent.activeSubscriptionInfo = activeSubscriptionInfo;
        return this;
    }

    public TelephonyEventBuilder setCarrierIdMatching(TelephonyProto.TelephonyEvent.CarrierIdMatching carrierIdMatching) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 13;
        telephonyEvent.carrierIdMatching = carrierIdMatching;
        return this;
    }

    public TelephonyEventBuilder setCarrierKeyChange(TelephonyProto.TelephonyEvent.CarrierKeyChange carrierKeyChange) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 14;
        telephonyEvent.carrierKeyChange = carrierKeyChange;
        return this;
    }

    public TelephonyEventBuilder setDataCalls(TelephonyProto.RilDataCall[] arrrilDataCall) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 7;
        telephonyEvent.dataCalls = arrrilDataCall;
        return this;
    }

    public TelephonyEventBuilder setDataStallRecoveryAction(int n) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 10;
        telephonyEvent.dataStallAction = n;
        return this;
    }

    public TelephonyEventBuilder setDataSwitch(TelephonyProto.TelephonyEvent.DataSwitch dataSwitch) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 15;
        telephonyEvent.dataSwitch = dataSwitch;
        return this;
    }

    public TelephonyEventBuilder setDeactivateDataCall(TelephonyProto.TelephonyEvent.RilDeactivateDataCall rilDeactivateDataCall) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 8;
        telephonyEvent.deactivateDataCall = rilDeactivateDataCall;
        return this;
    }

    public TelephonyEventBuilder setDeactivateDataCallResponse(int n) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 9;
        telephonyEvent.error = n;
        return this;
    }

    public TelephonyEventBuilder setEnabledModemBitmap(int n) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 20;
        telephonyEvent.enabledModemBitmap = n;
        return this;
    }

    public TelephonyEventBuilder setImsCapabilities(TelephonyProto.ImsCapabilities imsCapabilities) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 4;
        telephonyEvent.imsCapabilities = imsCapabilities;
        return this;
    }

    public TelephonyEventBuilder setImsConnectionState(TelephonyProto.ImsConnectionState imsConnectionState) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 3;
        telephonyEvent.imsConnectionState = imsConnectionState;
        return this;
    }

    public TelephonyEventBuilder setModemRestart(TelephonyProto.TelephonyEvent.ModemRestart modemRestart) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 11;
        telephonyEvent.modemRestart = modemRestart;
        return this;
    }

    public TelephonyEventBuilder setNITZ(long l) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 12;
        telephonyEvent.nitzTimestampMillis = l;
        return this;
    }

    public TelephonyEventBuilder setNetworkValidate(int n) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 16;
        telephonyEvent.networkValidationState = n;
        return this;
    }

    public TelephonyEventBuilder setOnDemandDataSwitch(TelephonyProto.TelephonyEvent.OnDemandDataSwitch onDemandDataSwitch) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 17;
        telephonyEvent.onDemandDataSwitch = onDemandDataSwitch;
        return this;
    }

    public TelephonyEventBuilder setServiceState(TelephonyProto.TelephonyServiceState telephonyServiceState) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 2;
        telephonyEvent.serviceState = telephonyServiceState;
        return this;
    }

    public TelephonyEventBuilder setSettings(TelephonyProto.TelephonySettings telephonySettings) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 1;
        telephonyEvent.settings = telephonySettings;
        return this;
    }

    public TelephonyEventBuilder setSetupDataCall(TelephonyProto.TelephonyEvent.RilSetupDataCall rilSetupDataCall) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 5;
        telephonyEvent.setupDataCall = rilSetupDataCall;
        return this;
    }

    public TelephonyEventBuilder setSetupDataCallResponse(TelephonyProto.TelephonyEvent.RilSetupDataCallResponse rilSetupDataCallResponse) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 6;
        telephonyEvent.setupDataCallResponse = rilSetupDataCallResponse;
        return this;
    }

    public TelephonyEventBuilder setSimStateChange(SparseArray<Integer> sparseArray) {
        int n = TelephonyManager.getDefault().getPhoneCount();
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.simState = new int[n];
        Arrays.fill(telephonyEvent.simState, 0);
        this.mEvent.type = 18;
        for (int i = 0; i < sparseArray.size(); ++i) {
            int n2 = sparseArray.keyAt(i);
            if (n2 < 0 || n2 >= n) continue;
            this.mEvent.simState[n2] = (Integer)sparseArray.get(n2);
        }
        return this;
    }

    public TelephonyEventBuilder setUpdatedEmergencyNumber(TelephonyProto.EmergencyNumberInfo emergencyNumberInfo) {
        TelephonyProto.TelephonyEvent telephonyEvent = this.mEvent;
        telephonyEvent.type = 21;
        telephonyEvent.updatedEmergencyNumber = emergencyNumberInfo;
        return this;
    }
}


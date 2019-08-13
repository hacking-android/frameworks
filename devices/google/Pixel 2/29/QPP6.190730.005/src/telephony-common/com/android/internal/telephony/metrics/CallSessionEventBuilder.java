/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.metrics;

import com.android.internal.telephony.nano.TelephonyProto;

public class CallSessionEventBuilder {
    private final TelephonyProto.TelephonyCallSession.Event mEvent = new TelephonyProto.TelephonyCallSession.Event();

    public CallSessionEventBuilder(int n) {
        this.mEvent.type = n;
    }

    public TelephonyProto.TelephonyCallSession.Event build() {
        return this.mEvent;
    }

    public CallSessionEventBuilder setAudioCodec(int n) {
        this.mEvent.audioCodec = n;
        return this;
    }

    public CallSessionEventBuilder setCallIndex(int n) {
        this.mEvent.callIndex = n;
        return this;
    }

    public CallSessionEventBuilder setCallQuality(TelephonyProto.TelephonyCallSession.Event.CallQuality callQuality) {
        this.mEvent.callQuality = callQuality;
        return this;
    }

    public CallSessionEventBuilder setCallQualitySummaryDl(TelephonyProto.TelephonyCallSession.Event.CallQualitySummary callQualitySummary) {
        this.mEvent.callQualitySummaryDl = callQualitySummary;
        return this;
    }

    public CallSessionEventBuilder setCallQualitySummaryUl(TelephonyProto.TelephonyCallSession.Event.CallQualitySummary callQualitySummary) {
        this.mEvent.callQualitySummaryUl = callQualitySummary;
        return this;
    }

    public CallSessionEventBuilder setCallState(int n) {
        this.mEvent.callState = n;
        return this;
    }

    public CallSessionEventBuilder setDataCalls(TelephonyProto.RilDataCall[] arrrilDataCall) {
        this.mEvent.dataCalls = arrrilDataCall;
        return this;
    }

    public CallSessionEventBuilder setDelay(int n) {
        this.mEvent.delay = n;
        return this;
    }

    public CallSessionEventBuilder setImsCapabilities(TelephonyProto.ImsCapabilities imsCapabilities) {
        this.mEvent.imsCapabilities = imsCapabilities;
        return this;
    }

    public CallSessionEventBuilder setImsCommand(int n) {
        this.mEvent.imsCommand = n;
        return this;
    }

    public CallSessionEventBuilder setImsConnectionState(TelephonyProto.ImsConnectionState imsConnectionState) {
        this.mEvent.imsConnectionState = imsConnectionState;
        return this;
    }

    public CallSessionEventBuilder setImsEmergencyNumberInfo(TelephonyProto.EmergencyNumberInfo emergencyNumberInfo) {
        this.mEvent.imsEmergencyNumberInfo = emergencyNumberInfo;
        return this;
    }

    public CallSessionEventBuilder setImsReasonInfo(TelephonyProto.ImsReasonInfo imsReasonInfo) {
        this.mEvent.reasonInfo = imsReasonInfo;
        return this;
    }

    public CallSessionEventBuilder setIsImsEmergencyCall(boolean bl) {
        this.mEvent.isImsEmergencyCall = bl;
        return this;
    }

    public CallSessionEventBuilder setNITZ(long l) {
        this.mEvent.nitzTimestampMillis = l;
        return this;
    }

    public CallSessionEventBuilder setPhoneState(int n) {
        this.mEvent.phoneState = n;
        return this;
    }

    public CallSessionEventBuilder setRilCalls(TelephonyProto.TelephonyCallSession.Event.RilCall[] arrrilCall) {
        this.mEvent.calls = arrrilCall;
        return this;
    }

    public CallSessionEventBuilder setRilError(int n) {
        this.mEvent.error = n;
        return this;
    }

    public CallSessionEventBuilder setRilRequest(int n) {
        this.mEvent.rilRequest = n;
        return this;
    }

    public CallSessionEventBuilder setRilRequestId(int n) {
        this.mEvent.rilRequestId = n;
        return this;
    }

    public CallSessionEventBuilder setServiceState(TelephonyProto.TelephonyServiceState telephonyServiceState) {
        this.mEvent.serviceState = telephonyServiceState;
        return this;
    }

    public CallSessionEventBuilder setSettings(TelephonyProto.TelephonySettings telephonySettings) {
        this.mEvent.settings = telephonySettings;
        return this;
    }

    public CallSessionEventBuilder setSrcAccessTech(int n) {
        this.mEvent.srcAccessTech = n;
        return this;
    }

    public CallSessionEventBuilder setSrvccState(int n) {
        this.mEvent.srvccState = n;
        return this;
    }

    public CallSessionEventBuilder setTargetAccessTech(int n) {
        this.mEvent.targetAccessTech = n;
        return this;
    }
}


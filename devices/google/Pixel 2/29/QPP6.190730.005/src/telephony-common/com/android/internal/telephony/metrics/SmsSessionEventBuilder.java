/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.metrics;

import com.android.internal.telephony.nano.TelephonyProto;

public class SmsSessionEventBuilder {
    TelephonyProto.SmsSession.Event mEvent = new TelephonyProto.SmsSession.Event();

    public SmsSessionEventBuilder(int n) {
        this.mEvent.type = n;
    }

    public TelephonyProto.SmsSession.Event build() {
        return this.mEvent;
    }

    public SmsSessionEventBuilder setBlocked(boolean bl) {
        this.mEvent.blocked = bl;
        return this;
    }

    public SmsSessionEventBuilder setCellBroadcastMessage(TelephonyProto.SmsSession.Event.CBMessage cBMessage) {
        this.mEvent.cellBroadcastMessage = cBMessage;
        return this;
    }

    public SmsSessionEventBuilder setDataCalls(TelephonyProto.RilDataCall[] arrrilDataCall) {
        this.mEvent.dataCalls = arrrilDataCall;
        return this;
    }

    public SmsSessionEventBuilder setDelay(int n) {
        this.mEvent.delay = n;
        return this;
    }

    public SmsSessionEventBuilder setErrorCode(int n) {
        this.mEvent.errorCode = n;
        return this;
    }

    public SmsSessionEventBuilder setFormat(int n) {
        this.mEvent.format = n;
        return this;
    }

    public SmsSessionEventBuilder setImsCapabilities(TelephonyProto.ImsCapabilities imsCapabilities) {
        this.mEvent.imsCapabilities = imsCapabilities;
        return this;
    }

    public SmsSessionEventBuilder setImsConnectionState(TelephonyProto.ImsConnectionState imsConnectionState) {
        this.mEvent.imsConnectionState = imsConnectionState;
        return this;
    }

    public SmsSessionEventBuilder setImsServiceErrno(int n) {
        this.mEvent.imsError = n;
        return this;
    }

    public SmsSessionEventBuilder setIncompleteSms(TelephonyProto.SmsSession.Event.IncompleteSms incompleteSms) {
        this.mEvent.incompleteSms = incompleteSms;
        return this;
    }

    public SmsSessionEventBuilder setRilErrno(int n) {
        this.mEvent.error = n;
        return this;
    }

    public SmsSessionEventBuilder setRilRequestId(int n) {
        this.mEvent.rilRequestId = n;
        return this;
    }

    public SmsSessionEventBuilder setServiceState(TelephonyProto.TelephonyServiceState telephonyServiceState) {
        this.mEvent.serviceState = telephonyServiceState;
        return this;
    }

    public SmsSessionEventBuilder setSettings(TelephonyProto.TelephonySettings telephonySettings) {
        this.mEvent.settings = telephonySettings;
        return this;
    }

    public SmsSessionEventBuilder setSmsType(int n) {
        this.mEvent.smsType = n;
        return this;
    }

    public SmsSessionEventBuilder setTech(int n) {
        this.mEvent.tech = n;
        return this;
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.V1_0.RadioResponseInfo
 *  android.os.Message
 *  android.telephony.ModemInfo
 *  android.telephony.PhoneCapability
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.config.V1_1.ModemInfo;
import android.hardware.radio.config.V1_1.ModemsConfig;
import android.hardware.radio.config.V1_2.IRadioConfigResponse;
import android.hardware.radio.config.V1_2.SimSlotStatus;
import android.os.Message;
import android.telephony.PhoneCapability;
import android.telephony.Rlog;
import com.android.internal.telephony.RILRequest;
import com.android.internal.telephony.RadioConfig;
import com.android.internal.telephony.RadioResponse;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RadioConfigResponse
extends IRadioConfigResponse.Stub {
    private static final String TAG = "RadioConfigResponse";
    private final RadioConfig mRadioConfig;

    public RadioConfigResponse(RadioConfig radioConfig) {
        this.mRadioConfig = radioConfig;
    }

    private PhoneCapability convertHalPhoneCapability(android.hardware.radio.config.V1_1.PhoneCapability object) {
        byte by = ((android.hardware.radio.config.V1_1.PhoneCapability)object).maxActiveData;
        boolean bl = ((android.hardware.radio.config.V1_1.PhoneCapability)object).isInternetLingeringSupported;
        ArrayList<android.telephony.ModemInfo> arrayList = new ArrayList<android.telephony.ModemInfo>();
        object = ((android.hardware.radio.config.V1_1.PhoneCapability)object).logicalModemList.iterator();
        while (object.hasNext()) {
            arrayList.add(new android.telephony.ModemInfo((int)((ModemInfo)object.next()).modemId));
        }
        return new PhoneCapability(0, (int)by, 0, arrayList, bl);
    }

    @Override
    public void getModemsConfigResponse(RadioResponseInfo object, ModemsConfig object2) {
        RILRequest rILRequest = this.mRadioConfig.processResponse((RadioResponseInfo)object);
        if (rILRequest != null) {
            if (((RadioResponseInfo)object).error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object2);
                object = new StringBuilder();
                ((StringBuilder)object).append(rILRequest.serialString());
                ((StringBuilder)object).append("< ");
                object2 = this.mRadioConfig;
                ((StringBuilder)object).append(RadioConfig.requestToString(rILRequest.mRequest));
                Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
            } else {
                rILRequest.onError(((RadioResponseInfo)object).error, object2);
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(rILRequest.serialString());
                ((StringBuilder)object2).append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                ((StringBuilder)object2).append(RadioConfig.requestToString(rILRequest.mRequest));
                ((StringBuilder)object2).append(" error ");
                ((StringBuilder)object2).append(((RadioResponseInfo)object).error);
                Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
            }
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getModemsConfigResponse: Error ");
            ((StringBuilder)object2).append(object.toString());
            Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
        }
    }

    @Override
    public void getPhoneCapabilityResponse(RadioResponseInfo object, android.hardware.radio.config.V1_1.PhoneCapability object2) {
        RILRequest rILRequest = this.mRadioConfig.processResponse((RadioResponseInfo)object);
        if (rILRequest != null) {
            object2 = this.convertHalPhoneCapability((android.hardware.radio.config.V1_1.PhoneCapability)object2);
            if (object.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object2);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(rILRequest.serialString());
                stringBuilder.append("< ");
                object = this.mRadioConfig;
                stringBuilder.append(RadioConfig.requestToString(rILRequest.mRequest));
                stringBuilder.append(" ");
                stringBuilder.append(object2.toString());
                Rlog.d((String)TAG, (String)stringBuilder.toString());
            } else {
                rILRequest.onError(object.error, object2);
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(rILRequest.serialString());
                ((StringBuilder)object2).append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                ((StringBuilder)object2).append(RadioConfig.requestToString(rILRequest.mRequest));
                ((StringBuilder)object2).append(" error ");
                ((StringBuilder)object2).append(object.error);
                Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
            }
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getPhoneCapabilityResponse: Error ");
            ((StringBuilder)object2).append(object.toString());
            Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
        }
    }

    @Override
    public void getSimSlotsStatusResponse(RadioResponseInfo object, ArrayList<android.hardware.radio.config.V1_0.SimSlotStatus> serializable) {
        RILRequest rILRequest = this.mRadioConfig.processResponse((RadioResponseInfo)object);
        if (rILRequest != null) {
            serializable = RadioConfig.convertHalSlotStatus(serializable);
            if (((RadioResponseInfo)object).error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, serializable);
                object = new StringBuilder();
                ((StringBuilder)object).append(rILRequest.serialString());
                ((StringBuilder)object).append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                ((StringBuilder)object).append(RadioConfig.requestToString(rILRequest.mRequest));
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append(((AbstractCollection)((Object)serializable)).toString());
                Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
            } else {
                rILRequest.onError(((RadioResponseInfo)object).error, serializable);
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append(rILRequest.serialString());
                ((StringBuilder)serializable).append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                ((StringBuilder)serializable).append(RadioConfig.requestToString(rILRequest.mRequest));
                ((StringBuilder)serializable).append(" error ");
                ((StringBuilder)serializable).append(((RadioResponseInfo)object).error);
                Rlog.e((String)TAG, (String)((StringBuilder)serializable).toString());
            }
        } else {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("getSimSlotsStatusResponse: Error ");
            ((StringBuilder)serializable).append(object.toString());
            Rlog.e((String)TAG, (String)((StringBuilder)serializable).toString());
        }
    }

    @Override
    public void getSimSlotsStatusResponse_1_2(RadioResponseInfo object, ArrayList<SimSlotStatus> object2) {
        RILRequest rILRequest = this.mRadioConfig.processResponse((RadioResponseInfo)object);
        if (rILRequest != null) {
            object2 = RadioConfig.convertHalSlotStatus_1_2(object2);
            if (object.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object2);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(rILRequest.serialString());
                stringBuilder.append("< ");
                object = this.mRadioConfig;
                stringBuilder.append(RadioConfig.requestToString(rILRequest.mRequest));
                stringBuilder.append(" ");
                stringBuilder.append(((AbstractCollection)object2).toString());
                Rlog.d((String)TAG, (String)stringBuilder.toString());
            } else {
                rILRequest.onError(object.error, object2);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(rILRequest.serialString());
                stringBuilder.append("< ");
                object2 = this.mRadioConfig;
                stringBuilder.append(RadioConfig.requestToString(rILRequest.mRequest));
                stringBuilder.append(" error ");
                stringBuilder.append(object.error);
                Rlog.e((String)TAG, (String)stringBuilder.toString());
            }
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getSimSlotsStatusResponse_1_2: Error ");
            ((StringBuilder)object2).append(object.toString());
            Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
        }
    }

    @Override
    public void setModemsConfigResponse(RadioResponseInfo object) {
        Object object2 = this.mRadioConfig.processResponse((RadioResponseInfo)object);
        if (object2 != null) {
            if (((RadioResponseInfo)object).error == 0) {
                RadioResponse.sendMessageResponse(((RILRequest)object2).mResult, ((RILRequest)object2).mRequest);
                object = new StringBuilder();
                ((StringBuilder)object).append(((RILRequest)object2).serialString());
                ((StringBuilder)object).append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                ((StringBuilder)object).append(RadioConfig.requestToString(((RILRequest)object2).mRequest));
                Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
            } else {
                ((RILRequest)object2).onError(((RadioResponseInfo)object).error, null);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object2).serialString());
                stringBuilder.append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                stringBuilder.append(RadioConfig.requestToString(((RILRequest)object2).mRequest));
                stringBuilder.append(" error ");
                stringBuilder.append(((RadioResponseInfo)object).error);
                Rlog.e((String)TAG, (String)stringBuilder.toString());
            }
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("setModemsConfigResponse: Error ");
            ((StringBuilder)object2).append(object.toString());
            Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
        }
    }

    @Override
    public void setPreferredDataModemResponse(RadioResponseInfo object) {
        Object object2 = this.mRadioConfig.processResponse((RadioResponseInfo)object);
        if (object2 != null) {
            if (((RadioResponseInfo)object).error == 0) {
                RadioResponse.sendMessageResponse(((RILRequest)object2).mResult, null);
                object = new StringBuilder();
                ((StringBuilder)object).append(((RILRequest)object2).serialString());
                ((StringBuilder)object).append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                ((StringBuilder)object).append(RadioConfig.requestToString(((RILRequest)object2).mRequest));
                Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
            } else {
                ((RILRequest)object2).onError(((RadioResponseInfo)object).error, null);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object2).serialString());
                stringBuilder.append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                stringBuilder.append(RadioConfig.requestToString(((RILRequest)object2).mRequest));
                stringBuilder.append(" error ");
                stringBuilder.append(((RadioResponseInfo)object).error);
                Rlog.e((String)TAG, (String)stringBuilder.toString());
            }
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("setPreferredDataModemResponse: Error ");
            ((StringBuilder)object2).append(object.toString());
            Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
        }
    }

    @Override
    public void setSimSlotsMappingResponse(RadioResponseInfo object) {
        Object object2 = this.mRadioConfig.processResponse((RadioResponseInfo)object);
        if (object2 != null) {
            if (object.error == 0) {
                RadioResponse.sendMessageResponse(((RILRequest)object2).mResult, null);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object2).serialString());
                stringBuilder.append("< ");
                object = this.mRadioConfig;
                stringBuilder.append(RadioConfig.requestToString(((RILRequest)object2).mRequest));
                Rlog.d((String)TAG, (String)stringBuilder.toString());
            } else {
                ((RILRequest)object2).onError(object.error, null);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((RILRequest)object2).serialString());
                stringBuilder.append("< ");
                RadioConfig radioConfig = this.mRadioConfig;
                stringBuilder.append(RadioConfig.requestToString(((RILRequest)object2).mRequest));
                stringBuilder.append(" error ");
                stringBuilder.append(object.error);
                Rlog.e((String)TAG, (String)stringBuilder.toString());
            }
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("setSimSlotsMappingResponse: Error ");
            ((StringBuilder)object2).append(object.toString());
            Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
        }
    }
}


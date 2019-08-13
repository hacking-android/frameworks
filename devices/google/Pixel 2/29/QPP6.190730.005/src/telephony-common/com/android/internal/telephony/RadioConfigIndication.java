/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Registrant
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.hardware.radio.config.V1_2.IRadioConfigIndication;
import android.hardware.radio.config.V1_2.SimSlotStatus;
import android.os.AsyncResult;
import android.os.Registrant;
import android.telephony.Rlog;
import com.android.internal.telephony.RadioConfig;
import com.android.internal.telephony.uicc.IccSlotStatus;
import java.io.Serializable;
import java.util.ArrayList;

public class RadioConfigIndication
extends IRadioConfigIndication.Stub {
    private static final String TAG = "RadioConfigIndication";
    private final RadioConfig mRadioConfig;

    public RadioConfigIndication(RadioConfig radioConfig) {
        this.mRadioConfig = radioConfig;
    }

    @Override
    public void simSlotsStatusChanged(int n, ArrayList<android.hardware.radio.config.V1_0.SimSlotStatus> serializable) {
        ArrayList<IccSlotStatus> arrayList = RadioConfig.convertHalSlotStatus(serializable);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("[UNSL]<  UNSOL_SIM_SLOT_STATUS_CHANGED ");
        ((StringBuilder)serializable).append(arrayList.toString());
        Rlog.d((String)TAG, (String)((StringBuilder)serializable).toString());
        if (this.mRadioConfig.mSimSlotStatusRegistrant != null) {
            this.mRadioConfig.mSimSlotStatusRegistrant.notifyRegistrant(new AsyncResult(null, arrayList, null));
        }
    }

    @Override
    public void simSlotsStatusChanged_1_2(int n, ArrayList<SimSlotStatus> serializable) {
        ArrayList<IccSlotStatus> arrayList = RadioConfig.convertHalSlotStatus_1_2(serializable);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("[UNSL]<  UNSOL_SIM_SLOT_STATUS_CHANGED ");
        ((StringBuilder)serializable).append(arrayList.toString());
        Rlog.d((String)TAG, (String)((StringBuilder)serializable).toString());
        if (this.mRadioConfig.mSimSlotStatusRegistrant != null) {
            this.mRadioConfig.mSimSlotStatusRegistrant.notifyRegistrant(new AsyncResult(null, arrayList, null));
        }
    }
}


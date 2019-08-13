/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.V1_0.RadioResponseInfo
 *  android.os.Message
 */
package com.android.internal.telephony;

import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.deprecated.V1_0.IOemHookResponse;
import android.os.Message;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.RILRequest;
import com.android.internal.telephony.RadioResponse;
import java.util.ArrayList;

public class OemHookResponse
extends IOemHookResponse.Stub {
    RIL mRil;

    public OemHookResponse(RIL rIL) {
        this.mRil = rIL;
    }

    @Override
    public void sendRequestRawResponse(RadioResponseInfo radioResponseInfo, ArrayList<Byte> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            byte[] arrby = null;
            if (radioResponseInfo.error == 0) {
                arrby = RIL.arrayListToPrimitiveArray(arrayList);
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrby);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrby);
        }
    }

    @Override
    public void sendRequestStringsResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) {
        RadioResponse.responseStringArrayList(this.mRil, radioResponseInfo, arrayList);
    }
}


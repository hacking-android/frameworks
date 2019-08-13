/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Registrant
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony;

import android.hardware.radio.deprecated.V1_0.IOemHookIndication;
import android.os.AsyncResult;
import android.os.Registrant;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.ArrayList;

public class OemHookIndication
extends IOemHookIndication.Stub {
    RIL mRil;

    public OemHookIndication(RIL rIL) {
        this.mRil = rIL;
    }

    @Override
    public void oemHookRaw(int n, ArrayList<Byte> arrby) {
        this.mRil.processIndication(n);
        arrby = RIL.arrayListToPrimitiveArray(arrby);
        this.mRil.unsljLogvRet(1028, IccUtils.bytesToHexString((byte[])arrby));
        if (this.mRil.mUnsolOemHookRawRegistrant != null) {
            this.mRil.mUnsolOemHookRawRegistrant.notifyRegistrant(new AsyncResult(null, (Object)arrby, null));
        }
    }
}


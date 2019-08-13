/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ImsiEncryptionInfo
 */
package com.android.internal.telephony;

import android.telephony.ImsiEncryptionInfo;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneSubInfoController;

public final class _$$Lambda$PhoneSubInfoController$ChCf_gnGN3K5prBkykg6tWs0aTk
implements PhoneSubInfoController.CallPhoneMethodHelper {
    private final /* synthetic */ ImsiEncryptionInfo f$0;

    public /* synthetic */ _$$Lambda$PhoneSubInfoController$ChCf_gnGN3K5prBkykg6tWs0aTk(ImsiEncryptionInfo imsiEncryptionInfo) {
        this.f$0 = imsiEncryptionInfo;
    }

    public final Object callMethod(Phone phone) {
        return PhoneSubInfoController.lambda$setCarrierInfoForImsiEncryption$4(this.f$0, phone);
    }
}


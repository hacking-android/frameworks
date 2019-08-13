/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneSubInfoController;

public final class _$$Lambda$PhoneSubInfoController$1_6zFa_5X___HsO5oSaupKDtHL0
implements PhoneSubInfoController.CallPhoneMethodHelper {
    private final /* synthetic */ PhoneSubInfoController f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ String f$3;

    public /* synthetic */ _$$Lambda$PhoneSubInfoController$1_6zFa_5X___HsO5oSaupKDtHL0(PhoneSubInfoController phoneSubInfoController, int n, int n2, String string) {
        this.f$0 = phoneSubInfoController;
        this.f$1 = n;
        this.f$2 = n2;
        this.f$3 = string;
    }

    public final Object callMethod(Phone phone) {
        return this.f$0.lambda$getIccSimChallengeResponse$19$PhoneSubInfoController(this.f$1, this.f$2, this.f$3, phone);
    }
}


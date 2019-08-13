/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.android.internal.telephony;

import android.content.Context;
import com.android.internal.telephony.PhoneSubInfoController;

public final class _$$Lambda$PhoneSubInfoController$OJMEn1lB_IZwTxTEU9sWCr__XKs
implements PhoneSubInfoController.PermissionCheckHelper {
    private final /* synthetic */ PhoneSubInfoController f$0;

    public /* synthetic */ _$$Lambda$PhoneSubInfoController$OJMEn1lB_IZwTxTEU9sWCr__XKs(PhoneSubInfoController phoneSubInfoController) {
        this.f$0 = phoneSubInfoController;
    }

    @Override
    public final boolean checkPermission(Context context, int n, String string, String string2) {
        return this.f$0.lambda$callPhoneMethodForSubIdWithModifyCheck$26$PhoneSubInfoController(context, n, string, string2);
    }
}


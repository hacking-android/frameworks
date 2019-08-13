/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.android.internal.telephony;

import android.content.Context;
import com.android.internal.telephony.PhoneSubInfoController;

public final class _$$Lambda$PhoneSubInfoController$PONge0j2mBi_ILbtJD_7euF0uoM
implements PhoneSubInfoController.PermissionCheckHelper {
    private final /* synthetic */ PhoneSubInfoController f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$PhoneSubInfoController$PONge0j2mBi_ILbtJD_7euF0uoM(PhoneSubInfoController phoneSubInfoController, String string) {
        this.f$0 = phoneSubInfoController;
        this.f$1 = string;
    }

    @Override
    public final boolean checkPermission(Context context, int n, String string, String string2) {
        return this.f$0.lambda$callPhoneMethodForSubIdWithPrivilegedCheck$25$PhoneSubInfoController(this.f$1, context, n, string, string2);
    }
}


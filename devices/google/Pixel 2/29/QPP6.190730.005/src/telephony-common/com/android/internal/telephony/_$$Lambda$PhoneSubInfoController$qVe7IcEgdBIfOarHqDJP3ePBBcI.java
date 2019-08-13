/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$qVe7IcEgdBIfOarHqDJP3ePBBcI
 */
package com.android.internal.telephony;

import android.content.Context;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.PhoneSubInfoController;

public final class _$$Lambda$PhoneSubInfoController$qVe7IcEgdBIfOarHqDJP3ePBBcI
implements PhoneSubInfoController.PermissionCheckHelper {
    public static final /* synthetic */ -$.Lambda.PhoneSubInfoController.qVe7IcEgdBIfOarHqDJP3ePBBcI INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$PhoneSubInfoController$qVe7IcEgdBIfOarHqDJP3ePBBcI();
    }

    private /* synthetic */ _$$Lambda$PhoneSubInfoController$qVe7IcEgdBIfOarHqDJP3ePBBcI() {
    }

    @Override
    public final boolean checkPermission(Context context, int n, String string, String string2) {
        return PhoneSubInfoController.lambda$callPhoneMethodForSubIdWithReadDeviceIdentifiersCheck$23(context, n, string, string2);
    }
}


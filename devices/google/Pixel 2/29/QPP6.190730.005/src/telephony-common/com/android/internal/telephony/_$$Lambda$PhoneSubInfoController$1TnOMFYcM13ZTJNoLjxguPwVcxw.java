/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$1TnOMFYcM13ZTJNoLjxguPwVcxw
 */
package com.android.internal.telephony;

import android.content.Context;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.PhoneSubInfoController;

public final class _$$Lambda$PhoneSubInfoController$1TnOMFYcM13ZTJNoLjxguPwVcxw
implements PhoneSubInfoController.PermissionCheckHelper {
    public static final /* synthetic */ -$.Lambda.PhoneSubInfoController.1TnOMFYcM13ZTJNoLjxguPwVcxw INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$PhoneSubInfoController$1TnOMFYcM13ZTJNoLjxguPwVcxw();
    }

    private /* synthetic */ _$$Lambda$PhoneSubInfoController$1TnOMFYcM13ZTJNoLjxguPwVcxw() {
    }

    @Override
    public final boolean checkPermission(Context context, int n, String string, String string2) {
        return PhoneSubInfoController.lambda$callPhoneMethodForSubIdWithReadPhoneNumberCheck$27(context, n, string, string2);
    }
}


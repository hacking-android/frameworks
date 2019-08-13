/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$qSXnUMuIwAZ0TQjtyVEfznh1w8o
 */
package com.android.internal.telephony;

import android.content.Context;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.PhoneSubInfoController;

public final class _$$Lambda$PhoneSubInfoController$qSXnUMuIwAZ0TQjtyVEfznh1w8o
implements PhoneSubInfoController.PermissionCheckHelper {
    public static final /* synthetic */ -$.Lambda.PhoneSubInfoController.qSXnUMuIwAZ0TQjtyVEfznh1w8o INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$PhoneSubInfoController$qSXnUMuIwAZ0TQjtyVEfznh1w8o();
    }

    private /* synthetic */ _$$Lambda$PhoneSubInfoController$qSXnUMuIwAZ0TQjtyVEfznh1w8o() {
    }

    @Override
    public final boolean checkPermission(Context context, int n, String string, String string2) {
        return PhoneSubInfoController.lambda$callPhoneMethodForSubIdWithReadCheck$22(context, n, string, string2);
    }
}


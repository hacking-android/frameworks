/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController
 *  com.android.internal.telephony.-$$Lambda$PhoneSubInfoController$EYZUPU0CYhRoptGCGJ9y78u-jQM
 */
package com.android.internal.telephony;

import android.content.Context;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.PhoneSubInfoController;

public final class _$$Lambda$PhoneSubInfoController$EYZUPU0CYhRoptGCGJ9y78u_jQM
implements PhoneSubInfoController.PermissionCheckHelper {
    public static final /* synthetic */ -$.Lambda.PhoneSubInfoController.EYZUPU0CYhRoptGCGJ9y78u-jQM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$PhoneSubInfoController$EYZUPU0CYhRoptGCGJ9y78u_jQM();
    }

    private /* synthetic */ _$$Lambda$PhoneSubInfoController$EYZUPU0CYhRoptGCGJ9y78u_jQM() {
    }

    @Override
    public final boolean checkPermission(Context context, int n, String string, String string2) {
        return PhoneSubInfoController.lambda$callPhoneMethodForSubIdWithReadSubscriberIdentifiersCheck$24(context, n, string, string2);
    }
}


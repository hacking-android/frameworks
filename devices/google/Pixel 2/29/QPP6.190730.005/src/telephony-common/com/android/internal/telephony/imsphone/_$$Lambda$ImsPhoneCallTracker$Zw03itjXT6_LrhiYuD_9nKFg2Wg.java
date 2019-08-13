/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  com.android.internal.telephony.imsphone.-$
 *  com.android.internal.telephony.imsphone.-$$Lambda
 *  com.android.internal.telephony.imsphone.-$$Lambda$ImsPhoneCallTracker
 *  com.android.internal.telephony.imsphone.-$$Lambda$ImsPhoneCallTracker$Zw03itjXT6-LrhiYuD-9nKFg2Wg
 */
package com.android.internal.telephony.imsphone;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.internal.telephony.imsphone.-$;
import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;

public final class _$$Lambda$ImsPhoneCallTracker$Zw03itjXT6_LrhiYuD_9nKFg2Wg
implements ImsPhoneCallTracker.SharedPreferenceProxy {
    public static final /* synthetic */ -$.Lambda.ImsPhoneCallTracker.Zw03itjXT6-LrhiYuD-9nKFg2Wg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ImsPhoneCallTracker$Zw03itjXT6_LrhiYuD_9nKFg2Wg();
    }

    private /* synthetic */ _$$Lambda$ImsPhoneCallTracker$Zw03itjXT6_LrhiYuD_9nKFg2Wg() {
    }

    @Override
    public final SharedPreferences getDefaultSharedPreferences(Context context) {
        return ImsPhoneCallTracker.lambda$new$0(context);
    }
}


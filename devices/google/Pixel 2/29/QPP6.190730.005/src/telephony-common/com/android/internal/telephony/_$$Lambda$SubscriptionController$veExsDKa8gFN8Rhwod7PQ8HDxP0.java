/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.SubscriptionInfo
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$SubscriptionController
 *  com.android.internal.telephony.-$$Lambda$SubscriptionController$veExsDKa8gFN8Rhwod7PQ8HDxP0
 */
package com.android.internal.telephony;

import android.telephony.SubscriptionInfo;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.SubscriptionController;
import java.util.function.ToIntFunction;

public final class _$$Lambda$SubscriptionController$veExsDKa8gFN8Rhwod7PQ8HDxP0
implements ToIntFunction {
    public static final /* synthetic */ -$.Lambda.SubscriptionController.veExsDKa8gFN8Rhwod7PQ8HDxP0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$SubscriptionController$veExsDKa8gFN8Rhwod7PQ8HDxP0();
    }

    private /* synthetic */ _$$Lambda$SubscriptionController$veExsDKa8gFN8Rhwod7PQ8HDxP0() {
    }

    public final int applyAsInt(Object object) {
        return SubscriptionController.lambda$canPackageManageGroup$4((SubscriptionInfo)object);
    }
}


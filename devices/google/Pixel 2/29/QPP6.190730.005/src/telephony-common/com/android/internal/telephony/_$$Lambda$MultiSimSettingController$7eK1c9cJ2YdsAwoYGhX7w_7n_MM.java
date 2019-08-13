/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.SubscriptionInfo
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$MultiSimSettingController
 *  com.android.internal.telephony.-$$Lambda$MultiSimSettingController$7eK1c9cJ2YdsAwoYGhX7w-7n-MM
 */
package com.android.internal.telephony;

import android.telephony.SubscriptionInfo;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.MultiSimSettingController;
import java.util.function.Predicate;

public final class _$$Lambda$MultiSimSettingController$7eK1c9cJ2YdsAwoYGhX7w_7n_MM
implements Predicate {
    public static final /* synthetic */ -$.Lambda.MultiSimSettingController.7eK1c9cJ2YdsAwoYGhX7w-7n-MM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$MultiSimSettingController$7eK1c9cJ2YdsAwoYGhX7w_7n_MM();
    }

    private /* synthetic */ _$$Lambda$MultiSimSettingController$7eK1c9cJ2YdsAwoYGhX7w_7n_MM() {
    }

    public final boolean test(Object object) {
        return MultiSimSettingController.lambda$updatePrimarySubListAndGetChangeType$3((SubscriptionInfo)object);
    }
}


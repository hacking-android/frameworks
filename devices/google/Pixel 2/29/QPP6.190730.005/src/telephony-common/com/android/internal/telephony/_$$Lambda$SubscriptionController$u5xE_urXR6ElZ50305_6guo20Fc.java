/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$SubscriptionController
 *  com.android.internal.telephony.-$$Lambda$SubscriptionController$u5xE-urXR6ElZ50305_6guo20Fc
 */
package com.android.internal.telephony;

import com.android.internal.telephony.-$;
import com.android.internal.telephony.SubscriptionController;
import java.util.Comparator;
import java.util.Map;

public final class _$$Lambda$SubscriptionController$u5xE_urXR6ElZ50305_6guo20Fc
implements Comparator {
    public static final /* synthetic */ -$.Lambda.SubscriptionController.u5xE-urXR6ElZ50305_6guo20Fc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$SubscriptionController$u5xE_urXR6ElZ50305_6guo20Fc();
    }

    private /* synthetic */ _$$Lambda$SubscriptionController$u5xE_urXR6ElZ50305_6guo20Fc() {
    }

    public final int compare(Object object, Object object2) {
        return SubscriptionController.lambda$getActiveSubIdArrayList$2((Map.Entry)object, (Map.Entry)object2);
    }
}


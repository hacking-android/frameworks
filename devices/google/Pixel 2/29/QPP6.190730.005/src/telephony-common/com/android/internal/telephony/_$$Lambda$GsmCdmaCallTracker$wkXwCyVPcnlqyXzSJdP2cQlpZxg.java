/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$GsmCdmaCallTracker
 *  com.android.internal.telephony.-$$Lambda$GsmCdmaCallTracker$wkXwCyVPcnlqyXzSJdP2cQlpZxg
 */
package com.android.internal.telephony;

import com.android.internal.telephony.-$;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.GsmCdmaCallTracker;
import java.util.function.Predicate;

public final class _$$Lambda$GsmCdmaCallTracker$wkXwCyVPcnlqyXzSJdP2cQlpZxg
implements Predicate {
    public static final /* synthetic */ -$.Lambda.GsmCdmaCallTracker.wkXwCyVPcnlqyXzSJdP2cQlpZxg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$GsmCdmaCallTracker$wkXwCyVPcnlqyXzSJdP2cQlpZxg();
    }

    private /* synthetic */ _$$Lambda$GsmCdmaCallTracker$wkXwCyVPcnlqyXzSJdP2cQlpZxg() {
    }

    public final boolean test(Object object) {
        return GsmCdmaCallTracker.lambda$isInOtaspCall$0((Connection)object);
    }
}


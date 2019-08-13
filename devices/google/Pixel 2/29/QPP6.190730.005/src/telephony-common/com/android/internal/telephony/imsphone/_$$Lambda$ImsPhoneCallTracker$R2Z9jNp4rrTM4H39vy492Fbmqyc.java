/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.imsphone;

import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;
import java.util.Map;
import java.util.function.Predicate;

public final class _$$Lambda$ImsPhoneCallTracker$R2Z9jNp4rrTM4H39vy492Fbmqyc
implements Predicate {
    private final /* synthetic */ long f$0;

    public /* synthetic */ _$$Lambda$ImsPhoneCallTracker$R2Z9jNp4rrTM4H39vy492Fbmqyc(long l) {
        this.f$0 = l;
    }

    public final boolean test(Object object) {
        return ImsPhoneCallTracker.lambda$maintainConnectTimeCache$2(this.f$0, (Map.Entry)object);
    }
}


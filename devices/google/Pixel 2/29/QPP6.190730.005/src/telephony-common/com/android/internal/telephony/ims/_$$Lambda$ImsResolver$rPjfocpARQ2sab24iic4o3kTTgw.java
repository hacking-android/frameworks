/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.ImsResolver;
import java.util.function.Predicate;

public final class _$$Lambda$ImsResolver$rPjfocpARQ2sab24iic4o3kTTgw
implements Predicate {
    private final /* synthetic */ String f$0;

    public /* synthetic */ _$$Lambda$ImsResolver$rPjfocpARQ2sab24iic4o3kTTgw(String string) {
        this.f$0 = string;
    }

    public final boolean test(Object object) {
        return ImsResolver.lambda$getInfoByPackageName$2(this.f$0, (ImsResolver.ImsServiceInfo)object);
    }
}


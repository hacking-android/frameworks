/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import com.android.internal.os.BinderCallsStats;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class _$$Lambda$BinderCallsStats$Vota0PqfoPWckjXH35wE48myGdk
implements Consumer {
    private final /* synthetic */ List f$0;

    public /* synthetic */ _$$Lambda$BinderCallsStats$Vota0PqfoPWckjXH35wE48myGdk(List list) {
        this.f$0 = list;
    }

    public final void accept(Object object) {
        BinderCallsStats.lambda$dumpLocked$2(this.f$0, (Map.Entry)object);
    }
}


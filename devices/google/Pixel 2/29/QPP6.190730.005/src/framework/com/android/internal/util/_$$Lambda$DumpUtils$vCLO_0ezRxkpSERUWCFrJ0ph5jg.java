/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.ComponentName;
import com.android.internal.util.DumpUtils;
import java.util.function.Predicate;

public final class _$$Lambda$DumpUtils$vCLO_0ezRxkpSERUWCFrJ0ph5jg
implements Predicate {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$DumpUtils$vCLO_0ezRxkpSERUWCFrJ0ph5jg(int n, String string2) {
        this.f$0 = n;
        this.f$1 = string2;
    }

    public final boolean test(Object object) {
        return DumpUtils.lambda$filterRecord$2(this.f$0, this.f$1, (ComponentName.WithComponentName)object);
    }
}


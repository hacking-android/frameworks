/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.ComponentName;
import com.android.internal.util.DumpUtils;
import java.util.function.Predicate;

public final class _$$Lambda$DumpUtils$X8irOs5hfloCKy89_l1HRA1QeG0
implements Predicate {
    private final /* synthetic */ ComponentName f$0;

    public /* synthetic */ _$$Lambda$DumpUtils$X8irOs5hfloCKy89_l1HRA1QeG0(ComponentName componentName) {
        this.f$0 = componentName;
    }

    public final boolean test(Object object) {
        return DumpUtils.lambda$filterRecord$1(this.f$0, (ComponentName.WithComponentName)object);
    }
}


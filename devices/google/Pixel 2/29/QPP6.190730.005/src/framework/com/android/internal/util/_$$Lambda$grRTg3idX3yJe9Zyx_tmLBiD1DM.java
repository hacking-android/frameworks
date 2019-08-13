/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.util.-$
 *  com.android.internal.util.-$$Lambda
 *  com.android.internal.util.-$$Lambda$grRTg3idX3yJe9Zyx-tmLBiD1DM
 */
package com.android.internal.util;

import android.content.ComponentName;
import com.android.internal.util.-$;
import com.android.internal.util.DumpUtils;
import java.util.function.Predicate;

public final class _$$Lambda$grRTg3idX3yJe9Zyx_tmLBiD1DM
implements Predicate {
    public static final /* synthetic */ -$.Lambda.grRTg3idX3yJe9Zyx-tmLBiD1DM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$grRTg3idX3yJe9Zyx_tmLBiD1DM();
    }

    private /* synthetic */ _$$Lambda$grRTg3idX3yJe9Zyx_tmLBiD1DM() {
    }

    public final boolean test(Object object) {
        return DumpUtils.isPlatformCriticalPackage((ComponentName.WithComponentName)object);
    }
}


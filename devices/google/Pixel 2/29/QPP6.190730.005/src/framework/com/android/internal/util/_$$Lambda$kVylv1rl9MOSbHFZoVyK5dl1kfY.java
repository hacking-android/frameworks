/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.util.-$
 *  com.android.internal.util.-$$Lambda
 *  com.android.internal.util.-$$Lambda$kVylv1rl9MOSbHFZoVyK5dl1kfY
 */
package com.android.internal.util;

import android.content.ComponentName;
import com.android.internal.util.-$;
import com.android.internal.util.DumpUtils;
import java.util.function.Predicate;

public final class _$$Lambda$kVylv1rl9MOSbHFZoVyK5dl1kfY
implements Predicate {
    public static final /* synthetic */ -$.Lambda.kVylv1rl9MOSbHFZoVyK5dl1kfY INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$kVylv1rl9MOSbHFZoVyK5dl1kfY();
    }

    private /* synthetic */ _$$Lambda$kVylv1rl9MOSbHFZoVyK5dl1kfY() {
    }

    public final boolean test(Object object) {
        return DumpUtils.isPlatformPackage((ComponentName.WithComponentName)object);
    }
}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.util.-$
 *  com.android.internal.util.-$$Lambda
 *  com.android.internal.util.-$$Lambda$JwOUSWW2-Jzu15y4Kn4JuPh8tWM
 */
package com.android.internal.util;

import android.content.ComponentName;
import com.android.internal.util.-$;
import com.android.internal.util.DumpUtils;
import java.util.function.Predicate;

public final class _$$Lambda$JwOUSWW2_Jzu15y4Kn4JuPh8tWM
implements Predicate {
    public static final /* synthetic */ -$.Lambda.JwOUSWW2-Jzu15y4Kn4JuPh8tWM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$JwOUSWW2_Jzu15y4Kn4JuPh8tWM();
    }

    private /* synthetic */ _$$Lambda$JwOUSWW2_Jzu15y4Kn4JuPh8tWM() {
    }

    public final boolean test(Object object) {
        return DumpUtils.isNonPlatformPackage((ComponentName.WithComponentName)object);
    }
}


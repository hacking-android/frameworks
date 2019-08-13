/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.util.-$
 *  com.android.internal.util.-$$Lambda
 *  com.android.internal.util.-$$Lambda$TCbPpgWlKJUHZgFKCczglAvxLfw
 */
package com.android.internal.util;

import android.content.ComponentName;
import com.android.internal.util.-$;
import com.android.internal.util.DumpUtils;
import java.util.function.Predicate;

public final class _$$Lambda$TCbPpgWlKJUHZgFKCczglAvxLfw
implements Predicate {
    public static final /* synthetic */ -$.Lambda.TCbPpgWlKJUHZgFKCczglAvxLfw INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TCbPpgWlKJUHZgFKCczglAvxLfw();
    }

    private /* synthetic */ _$$Lambda$TCbPpgWlKJUHZgFKCczglAvxLfw() {
    }

    public final boolean test(Object object) {
        return DumpUtils.isPlatformNonCriticalPackage((ComponentName.WithComponentName)object);
    }
}


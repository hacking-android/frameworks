/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.util.-$
 *  com.android.internal.util.-$$Lambda
 *  com.android.internal.util.-$$Lambda$DumpUtils
 *  com.android.internal.util.-$$Lambda$DumpUtils$D1OlZP6xIpu72ypnJd0fzx0wd6I
 */
package com.android.internal.util;

import android.content.ComponentName;
import com.android.internal.util.-$;
import com.android.internal.util.DumpUtils;
import java.util.function.Predicate;

public final class _$$Lambda$DumpUtils$D1OlZP6xIpu72ypnJd0fzx0wd6I
implements Predicate {
    public static final /* synthetic */ -$.Lambda.DumpUtils.D1OlZP6xIpu72ypnJd0fzx0wd6I INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$DumpUtils$D1OlZP6xIpu72ypnJd0fzx0wd6I();
    }

    private /* synthetic */ _$$Lambda$DumpUtils$D1OlZP6xIpu72ypnJd0fzx0wd6I() {
    }

    public final boolean test(Object object) {
        return DumpUtils.lambda$filterRecord$0((ComponentName.WithComponentName)object);
    }
}


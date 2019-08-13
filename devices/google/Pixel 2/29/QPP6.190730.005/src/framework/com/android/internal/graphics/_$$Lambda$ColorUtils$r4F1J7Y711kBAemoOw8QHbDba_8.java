/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.graphics.-$
 *  com.android.internal.graphics.-$$Lambda
 *  com.android.internal.graphics.-$$Lambda$ColorUtils
 *  com.android.internal.graphics.-$$Lambda$ColorUtils$r4F1J7Y711kBAemoOw8QHbDba_8
 */
package com.android.internal.graphics;

import com.android.internal.graphics.-$;
import com.android.internal.graphics.ColorUtils;

public final class _$$Lambda$ColorUtils$r4F1J7Y711kBAemoOw8QHbDba_8
implements ColorUtils.ContrastCalculator {
    public static final /* synthetic */ -$.Lambda.ColorUtils.r4F1J7Y711kBAemoOw8QHbDba_8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ColorUtils$r4F1J7Y711kBAemoOw8QHbDba_8();
    }

    private /* synthetic */ _$$Lambda$ColorUtils$r4F1J7Y711kBAemoOw8QHbDba_8() {
    }

    @Override
    public final double calculateContrast(int n, int n2, int n3) {
        return ColorUtils.lambda$calculateMinimumAlpha$1(n, n2, n3);
    }
}


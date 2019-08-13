/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.graphics;

import com.android.internal.graphics.ColorUtils;

public final class _$$Lambda$ColorUtils$zbDH_52c8D9XBeqmvTHi3Boxl14
implements ColorUtils.ContrastCalculator {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$ColorUtils$zbDH_52c8D9XBeqmvTHi3Boxl14(int n) {
        this.f$0 = n;
    }

    @Override
    public final double calculateContrast(int n, int n2, int n3) {
        return ColorUtils.lambda$calculateMinimumBackgroundAlpha$0(this.f$0, n, n2, n3);
    }
}


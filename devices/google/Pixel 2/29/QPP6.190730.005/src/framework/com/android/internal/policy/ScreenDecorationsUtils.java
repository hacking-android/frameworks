/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.content.res.Resources;

public class ScreenDecorationsUtils {
    public static float getWindowCornerRadius(Resources resources) {
        float f;
        float f2;
        if (!ScreenDecorationsUtils.supportsRoundedCornersOnWindows(resources)) {
            return 0.0f;
        }
        float f3 = resources.getDimension(17105400);
        float f4 = f = resources.getDimension(17105402);
        if (f == 0.0f) {
            f4 = f3;
        }
        f = f2 = resources.getDimension(17105401);
        if (f2 == 0.0f) {
            f = f3;
        }
        return Math.min(f4, f);
    }

    public static boolean supportsRoundedCornersOnWindows(Resources resources) {
        return resources.getBoolean(17891540);
    }
}


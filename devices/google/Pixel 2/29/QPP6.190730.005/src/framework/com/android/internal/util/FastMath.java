/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;

public class FastMath {
    @UnsupportedAppUsage
    public static int round(float f) {
        return (int)(0x800000L + (long)(1.6777216E7f * f) >> 24);
    }
}


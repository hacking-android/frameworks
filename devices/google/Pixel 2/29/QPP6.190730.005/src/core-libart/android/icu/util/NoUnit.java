/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.MeasureUnit;

public class NoUnit
extends MeasureUnit {
    public static final NoUnit BASE = (NoUnit)MeasureUnit.internalGetInstance("none", "base");
    public static final NoUnit PERCENT = (NoUnit)MeasureUnit.internalGetInstance("none", "percent");
    public static final NoUnit PERMILLE = (NoUnit)MeasureUnit.internalGetInstance("none", "permille");
    private static final long serialVersionUID = 2467174286237024095L;

    NoUnit(String string) {
        super("none", string);
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import java.util.Date;

public interface DateRule {
    public Date firstAfter(Date var1);

    public Date firstBetween(Date var1, Date var2);

    public boolean isBetween(Date var1, Date var2);

    public boolean isOn(Date var1);
}


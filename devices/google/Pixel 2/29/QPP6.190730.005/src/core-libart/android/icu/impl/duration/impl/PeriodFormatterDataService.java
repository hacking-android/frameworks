/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration.impl;

import android.icu.impl.duration.impl.PeriodFormatterData;
import java.util.Collection;

public abstract class PeriodFormatterDataService {
    public abstract PeriodFormatterData get(String var1);

    public abstract Collection<String> getAvailableLocales();
}


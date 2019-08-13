/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import java.util.Date;
import java.util.TimeZone;

public interface DateFormatter {
    public String format(long var1);

    public String format(Date var1);

    public DateFormatter withLocale(String var1);

    public DateFormatter withTimeZone(TimeZone var1);
}


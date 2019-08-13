/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import java.io.Serializable;
import java.util.Date;

public abstract class TimeZoneRule
implements Serializable {
    private static final long serialVersionUID = 6374143828553768100L;
    private final int dstSavings;
    private final String name;
    private final int rawOffset;

    public TimeZoneRule(String string, int n, int n2) {
        this.name = string;
        this.rawOffset = n;
        this.dstSavings = n2;
    }

    public int getDSTSavings() {
        return this.dstSavings;
    }

    public abstract Date getFinalStart(int var1, int var2);

    public abstract Date getFirstStart(int var1, int var2);

    public String getName() {
        return this.name;
    }

    public abstract Date getNextStart(long var1, int var3, int var4, boolean var5);

    public abstract Date getPreviousStart(long var1, int var3, int var4, boolean var5);

    public int getRawOffset() {
        return this.rawOffset;
    }

    public boolean isEquivalentTo(TimeZoneRule timeZoneRule) {
        return this.rawOffset == timeZoneRule.rawOffset && this.dstSavings == timeZoneRule.dstSavings;
    }

    public abstract boolean isTransitionRule();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("name=");
        stringBuilder2.append(this.name);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", stdOffset=");
        stringBuilder2.append(this.rawOffset);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", dstSaving=");
        stringBuilder2.append(this.dstSavings);
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }
}


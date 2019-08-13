/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.TimeZoneRule;

public class TimeZoneTransition {
    private final TimeZoneRule from;
    private final long time;
    private final TimeZoneRule to;

    public TimeZoneTransition(long l, TimeZoneRule timeZoneRule, TimeZoneRule timeZoneRule2) {
        this.time = l;
        this.from = timeZoneRule;
        this.to = timeZoneRule2;
    }

    public TimeZoneRule getFrom() {
        return this.from;
    }

    public long getTime() {
        return this.time;
    }

    public TimeZoneRule getTo() {
        return this.to;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("time=");
        stringBuilder2.append(this.time);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", from={");
        stringBuilder2.append(this.from);
        stringBuilder2.append("}");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", to={");
        stringBuilder2.append(this.to);
        stringBuilder2.append("}");
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }
}


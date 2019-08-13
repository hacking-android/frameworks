/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.DateRule;
import java.util.Date;

class Range {
    public DateRule rule;
    public Date start;

    public Range(Date date, DateRule dateRule) {
        this.start = date;
        this.rule = dateRule;
    }
}


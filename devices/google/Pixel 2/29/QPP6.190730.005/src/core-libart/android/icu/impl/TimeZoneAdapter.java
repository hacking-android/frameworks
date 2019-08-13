/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.Date;
import java.util.TimeZone;

public class TimeZoneAdapter
extends TimeZone {
    static final long serialVersionUID = -2040072218820018557L;
    private android.icu.util.TimeZone zone;

    public TimeZoneAdapter(android.icu.util.TimeZone timeZone) {
        this.zone = timeZone;
        super.setID(timeZone.getID());
    }

    public static TimeZone wrap(android.icu.util.TimeZone timeZone) {
        return new TimeZoneAdapter(timeZone);
    }

    @Override
    public Object clone() {
        return new TimeZoneAdapter((android.icu.util.TimeZone)this.zone.clone());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof TimeZoneAdapter) {
            object = ((TimeZoneAdapter)object).zone;
            return this.zone.equals(object);
        }
        return false;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        return this.zone.getOffset(n, n2, n3, n4, n5, n6);
    }

    @Override
    public int getRawOffset() {
        return this.zone.getRawOffset();
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        boolean bl = timeZone instanceof TimeZoneAdapter && this.zone.hasSameRules(((TimeZoneAdapter)timeZone).zone);
        return bl;
    }

    public int hashCode() {
        synchronized (this) {
            int n = this.zone.hashCode();
            return n;
        }
    }

    @Override
    public boolean inDaylightTime(Date date) {
        return this.zone.inDaylightTime(date);
    }

    @Override
    public void setID(String string) {
        super.setID(string);
        this.zone.setID(string);
    }

    @Override
    public void setRawOffset(int n) {
        this.zone.setRawOffset(n);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TimeZoneAdapter: ");
        stringBuilder.append(this.zone.toString());
        return stringBuilder.toString();
    }

    public android.icu.util.TimeZone unwrap() {
        return this.zone;
    }

    @Override
    public boolean useDaylightTime() {
        return this.zone.useDaylightTime();
    }
}


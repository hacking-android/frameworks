/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

import java.util.Locale;
import java.util.TimeZone;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.Gregorian;
import sun.util.calendar.ImmutableGregorianDate;

public final class Era {
    private final String abbr;
    private int hash = 0;
    private final boolean localTime;
    private final String name;
    private final long since;
    private final CalendarDate sinceDate;

    public Era(String object, String object2, long l, boolean bl) {
        this.name = object;
        this.abbr = object2;
        this.since = l;
        this.localTime = bl;
        object2 = CalendarSystem.getGregorianCalendar();
        object = ((Gregorian)object2).newCalendarDate(null);
        ((Gregorian)object2).getCalendarDate(l, (CalendarDate)object);
        this.sinceDate = new ImmutableGregorianDate((BaseCalendar.Date)object);
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof Era;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (Era)object;
            if (!this.name.equals(((Era)object).name) || !this.abbr.equals(((Era)object).abbr) || this.since != ((Era)object).since || this.localTime != ((Era)object).localTime) break block1;
            bl = true;
        }
        return bl;
    }

    public String getAbbreviation() {
        return this.abbr;
    }

    public String getDiaplayAbbreviation(Locale locale) {
        return this.abbr;
    }

    public String getDisplayName(Locale locale) {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public long getSince(TimeZone timeZone) {
        if (timeZone != null && this.localTime) {
            int n = timeZone.getOffset(this.since);
            return this.since - (long)n;
        }
        return this.since;
    }

    public CalendarDate getSinceDate() {
        return this.sinceDate;
    }

    public int hashCode() {
        if (this.hash == 0) {
            int n = this.name.hashCode();
            int n2 = this.abbr.hashCode();
            long l = this.since;
            this.hash = n ^ n2 ^ (int)l ^ (int)(l >> 32) ^ this.localTime;
        }
        return this.hash;
    }

    public boolean isLocalTime() {
        return this.localTime;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        stringBuilder.append(this.getName());
        stringBuilder.append(" (");
        stringBuilder.append(this.getAbbreviation());
        stringBuilder.append(')');
        stringBuilder.append(" since ");
        stringBuilder.append(this.getSinceDate());
        if (this.localTime) {
            stringBuilder.setLength(stringBuilder.length() - 1);
            stringBuilder.append(" local time");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}


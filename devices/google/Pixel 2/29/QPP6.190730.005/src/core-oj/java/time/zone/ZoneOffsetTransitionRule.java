/*
 * Decompiled with CFR 0.145.
 */
package java.time.zone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.zone.Ser;
import java.time.zone.ZoneOffsetTransition;
import java.util.Objects;

public final class ZoneOffsetTransitionRule
implements Serializable {
    private static final long serialVersionUID = 6889046316657758795L;
    private final byte dom;
    private final DayOfWeek dow;
    private final Month month;
    private final ZoneOffset offsetAfter;
    private final ZoneOffset offsetBefore;
    private final ZoneOffset standardOffset;
    private final LocalTime time;
    private final TimeDefinition timeDefinition;
    private final boolean timeEndOfDay;

    ZoneOffsetTransitionRule(Month month, int n, DayOfWeek dayOfWeek, LocalTime localTime, boolean bl, TimeDefinition timeDefinition, ZoneOffset zoneOffset, ZoneOffset zoneOffset2, ZoneOffset zoneOffset3) {
        this.month = month;
        this.dom = (byte)n;
        this.dow = dayOfWeek;
        this.time = localTime;
        this.timeEndOfDay = bl;
        this.timeDefinition = timeDefinition;
        this.standardOffset = zoneOffset;
        this.offsetBefore = zoneOffset2;
        this.offsetAfter = zoneOffset3;
    }

    public static ZoneOffsetTransitionRule of(Month month, int n, DayOfWeek dayOfWeek, LocalTime localTime, boolean bl, TimeDefinition timeDefinition, ZoneOffset zoneOffset, ZoneOffset zoneOffset2, ZoneOffset zoneOffset3) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(localTime, "time");
        Objects.requireNonNull(timeDefinition, "timeDefnition");
        Objects.requireNonNull(zoneOffset, "standardOffset");
        Objects.requireNonNull(zoneOffset2, "offsetBefore");
        Objects.requireNonNull(zoneOffset3, "offsetAfter");
        if (n >= -28 && n <= 31 && n != 0) {
            if (bl && !localTime.equals(LocalTime.MIDNIGHT)) {
                throw new IllegalArgumentException("Time must be midnight when end of day flag is true");
            }
            return new ZoneOffsetTransitionRule(month, n, dayOfWeek, localTime, bl, timeDefinition, zoneOffset, zoneOffset2, zoneOffset3);
        }
        throw new IllegalArgumentException("Day of month indicator must be between -28 and 31 inclusive excluding zero");
    }

    static ZoneOffsetTransitionRule readExternal(DataInput object) throws IOException {
        int n = object.readInt();
        Month month = Month.of(n >>> 28);
        int n2 = (3670016 & n) >>> 19;
        DayOfWeek dayOfWeek = n2 == 0 ? null : DayOfWeek.of(n2);
        int n3 = (507904 & n) >>> 14;
        TimeDefinition timeDefinition = TimeDefinition.values()[(n & 12288) >>> 12];
        n2 = (n & 4080) >>> 4;
        int n4 = (n & 12) >>> 2;
        int n5 = n & 3;
        LocalTime localTime = n3 == 31 ? LocalTime.ofSecondOfDay(object.readInt()) : LocalTime.of(n3 % 24, 0);
        n2 = n2 == 255 ? object.readInt() : (n2 - 128) * 900;
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(n2);
        n2 = n4 == 3 ? object.readInt() : zoneOffset.getTotalSeconds() + n4 * 1800;
        ZoneOffset zoneOffset2 = ZoneOffset.ofTotalSeconds(n2);
        n2 = n5 == 3 ? object.readInt() : zoneOffset.getTotalSeconds() + n5 * 1800;
        object = ZoneOffset.ofTotalSeconds(n2);
        boolean bl = n3 == 24;
        return ZoneOffsetTransitionRule.of(month, ((264241152 & n) >>> 22) - 32, dayOfWeek, localTime, bl, timeDefinition, zoneOffset, zoneOffset2, (ZoneOffset)object);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(3, this);
    }

    public ZoneOffsetTransition createTransition(int n) {
        TemporalAccessor temporalAccessor;
        TemporalAccessor temporalAccessor2;
        byte by = this.dom;
        if (by < 0) {
            temporalAccessor = this.month;
            temporalAccessor2 = LocalDate.of(n, (Month)temporalAccessor, ((Month)temporalAccessor).length(IsoChronology.INSTANCE.isLeapYear(n)) + 1 + this.dom);
            DayOfWeek dayOfWeek = this.dow;
            temporalAccessor = temporalAccessor2;
            if (dayOfWeek != null) {
                temporalAccessor = ((LocalDate)temporalAccessor2).with(TemporalAdjusters.previousOrSame(dayOfWeek));
            }
        } else {
            temporalAccessor2 = LocalDate.of(n, this.month, (int)by);
            DayOfWeek dayOfWeek = this.dow;
            temporalAccessor = temporalAccessor2;
            if (dayOfWeek != null) {
                temporalAccessor = ((LocalDate)temporalAccessor2).with(TemporalAdjusters.nextOrSame(dayOfWeek));
            }
        }
        temporalAccessor2 = temporalAccessor;
        if (this.timeEndOfDay) {
            temporalAccessor2 = ((LocalDate)temporalAccessor).plusDays(1L);
        }
        temporalAccessor = LocalDateTime.of((LocalDate)temporalAccessor2, this.time);
        return new ZoneOffsetTransition(this.timeDefinition.createDateTime((LocalDateTime)temporalAccessor, this.standardOffset, this.offsetBefore), this.offsetBefore, this.offsetAfter);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof ZoneOffsetTransitionRule) {
            object = (ZoneOffsetTransitionRule)object;
            if (!(this.month == ((ZoneOffsetTransitionRule)object).month && this.dom == ((ZoneOffsetTransitionRule)object).dom && this.dow == ((ZoneOffsetTransitionRule)object).dow && this.timeDefinition == ((ZoneOffsetTransitionRule)object).timeDefinition && this.time.equals(((ZoneOffsetTransitionRule)object).time) && this.timeEndOfDay == ((ZoneOffsetTransitionRule)object).timeEndOfDay && this.standardOffset.equals(((ZoneOffsetTransitionRule)object).standardOffset) && this.offsetBefore.equals(((ZoneOffsetTransitionRule)object).offsetBefore) && this.offsetAfter.equals(((ZoneOffsetTransitionRule)object).offsetAfter))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getDayOfMonthIndicator() {
        return this.dom;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dow;
    }

    public LocalTime getLocalTime() {
        return this.time;
    }

    public Month getMonth() {
        return this.month;
    }

    public ZoneOffset getOffsetAfter() {
        return this.offsetAfter;
    }

    public ZoneOffset getOffsetBefore() {
        return this.offsetBefore;
    }

    public ZoneOffset getStandardOffset() {
        return this.standardOffset;
    }

    public TimeDefinition getTimeDefinition() {
        return this.timeDefinition;
    }

    public int hashCode() {
        int n = this.time.toSecondOfDay();
        int n2 = this.timeEndOfDay;
        int n3 = this.month.ordinal();
        byte by = this.dom;
        DayOfWeek dayOfWeek = this.dow;
        int n4 = dayOfWeek == null ? 7 : dayOfWeek.ordinal();
        int n5 = this.timeDefinition.ordinal();
        return this.standardOffset.hashCode() ^ (n + n2 << 15) + (n3 << 11) + (by + 32 << 5) + (n4 << 2) + n5 ^ this.offsetBefore.hashCode() ^ this.offsetAfter.hashCode();
    }

    public boolean isMidnightEndOfDay() {
        return this.timeEndOfDay;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TransitionRule[");
        Object object = this.offsetBefore.compareTo(this.offsetAfter) > 0 ? "Gap " : "Overlap ";
        stringBuilder.append((String)object);
        stringBuilder.append(this.offsetBefore);
        stringBuilder.append(" to ");
        stringBuilder.append(this.offsetAfter);
        stringBuilder.append(", ");
        object = this.dow;
        if (object != null) {
            byte by = this.dom;
            if (by == -1) {
                stringBuilder.append(((Enum)object).name());
                stringBuilder.append(" on or before last day of ");
                stringBuilder.append(this.month.name());
            } else if (by < 0) {
                stringBuilder.append(((Enum)object).name());
                stringBuilder.append(" on or before last day minus ");
                stringBuilder.append(-this.dom - 1);
                stringBuilder.append(" of ");
                stringBuilder.append(this.month.name());
            } else {
                stringBuilder.append(((Enum)object).name());
                stringBuilder.append(" on or after ");
                stringBuilder.append(this.month.name());
                stringBuilder.append(' ');
                stringBuilder.append(this.dom);
            }
        } else {
            stringBuilder.append(this.month.name());
            stringBuilder.append(' ');
            stringBuilder.append(this.dom);
        }
        stringBuilder.append(" at ");
        object = this.timeEndOfDay ? "24:00" : this.time.toString();
        stringBuilder.append((String)object);
        stringBuilder.append(" ");
        stringBuilder.append((Object)this.timeDefinition);
        stringBuilder.append(", standard offset ");
        stringBuilder.append(this.standardOffset);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        int n = this.timeEndOfDay ? 86400 : this.time.toSecondOfDay();
        int n2 = this.standardOffset.getTotalSeconds();
        int n3 = this.offsetBefore.getTotalSeconds() - n2;
        int n4 = this.offsetAfter.getTotalSeconds() - n2;
        int n5 = n % 3600 == 0 ? (this.timeEndOfDay ? 24 : this.time.getHour()) : 31;
        int n6 = n2 % 900 == 0 ? n2 / 900 + 128 : 255;
        n3 = n3 != 0 && n3 != 1800 && n3 != 3600 ? 3 : (n3 /= 1800);
        n4 = n4 != 0 && n4 != 1800 && n4 != 3600 ? 3 : (n4 /= 1800);
        DayOfWeek dayOfWeek = this.dow;
        int n7 = dayOfWeek == null ? 0 : dayOfWeek.getValue();
        dataOutput.writeInt((this.month.getValue() << 28) + (this.dom + 32 << 22) + (n7 << 19) + (n5 << 14) + (this.timeDefinition.ordinal() << 12) + (n6 << 4) + (n3 << 2) + n4);
        if (n5 == 31) {
            dataOutput.writeInt(n);
        }
        if (n6 == 255) {
            dataOutput.writeInt(n2);
        }
        if (n3 == 3) {
            dataOutput.writeInt(this.offsetBefore.getTotalSeconds());
        }
        if (n4 == 3) {
            dataOutput.writeInt(this.offsetAfter.getTotalSeconds());
        }
    }

    public static enum TimeDefinition {
        UTC,
        WALL,
        STANDARD;
        

        public LocalDateTime createDateTime(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
            int n = 1.$SwitchMap$java$time$zone$ZoneOffsetTransitionRule$TimeDefinition[this.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return localDateTime;
                }
                return localDateTime.plusSeconds(zoneOffset2.getTotalSeconds() - zoneOffset.getTotalSeconds());
            }
            return localDateTime.plusSeconds(zoneOffset2.getTotalSeconds() - ZoneOffset.UTC.getTotalSeconds());
        }
    }

}


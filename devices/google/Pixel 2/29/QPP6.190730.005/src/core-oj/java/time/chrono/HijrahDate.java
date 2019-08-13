/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateImpl;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahEra;
import java.time.chrono.Ser;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

public final class HijrahDate
extends ChronoLocalDateImpl<HijrahDate>
implements ChronoLocalDate,
Serializable {
    private static final long serialVersionUID = -5207853542612002020L;
    private final transient HijrahChronology chrono;
    private final transient int dayOfMonth;
    private final transient int monthOfYear;
    private final transient int prolepticYear;

    private HijrahDate(HijrahChronology hijrahChronology, int n, int n2, int n3) {
        hijrahChronology.getEpochDay(n, n2, n3);
        this.chrono = hijrahChronology;
        this.prolepticYear = n;
        this.monthOfYear = n2;
        this.dayOfMonth = n3;
    }

    private HijrahDate(HijrahChronology hijrahChronology, long l) {
        int[] arrn = hijrahChronology.getHijrahDateInfo((int)l);
        this.chrono = hijrahChronology;
        this.prolepticYear = arrn[0];
        this.monthOfYear = arrn[1];
        this.dayOfMonth = arrn[2];
    }

    public static HijrahDate from(TemporalAccessor temporalAccessor) {
        return HijrahChronology.INSTANCE.date(temporalAccessor);
    }

    private int getDayOfWeek() {
        return (int)Math.floorMod(this.toEpochDay() + 3L, 7L) + 1;
    }

    private int getDayOfYear() {
        return this.chrono.getDayOfYear(this.prolepticYear, this.monthOfYear) + this.dayOfMonth;
    }

    private int getEraValue() {
        int n = this.prolepticYear;
        int n2 = 1;
        if (n <= 1) {
            n2 = 0;
        }
        return n2;
    }

    private long getProlepticMonth() {
        return (long)this.prolepticYear * 12L + (long)this.monthOfYear - 1L;
    }

    public static HijrahDate now() {
        return HijrahDate.now(Clock.systemDefaultZone());
    }

    public static HijrahDate now(Clock clock) {
        return HijrahDate.ofEpochDay(HijrahChronology.INSTANCE, LocalDate.now(clock).toEpochDay());
    }

    public static HijrahDate now(ZoneId zoneId) {
        return HijrahDate.now(Clock.system(zoneId));
    }

    public static HijrahDate of(int n, int n2, int n3) {
        return HijrahChronology.INSTANCE.date(n, n2, n3);
    }

    static HijrahDate of(HijrahChronology hijrahChronology, int n, int n2, int n3) {
        return new HijrahDate(hijrahChronology, n, n2, n3);
    }

    static HijrahDate ofEpochDay(HijrahChronology hijrahChronology, long l) {
        return new HijrahDate(hijrahChronology, l);
    }

    static HijrahDate readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return ((HijrahChronology)objectInput.readObject()).date(objectInput.readInt(), objectInput.readByte(), objectInput.readByte());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private HijrahDate resolvePreviousValid(int n, int n2, int n3) {
        int n4 = this.chrono.getMonthLength(n, n2);
        int n5 = n3;
        if (n3 > n4) {
            n5 = n4;
        }
        return HijrahDate.of(this.chrono, n, n2, n5);
    }

    private Object writeReplace() {
        return new Ser(6, this);
    }

    public final ChronoLocalDateTime<HijrahDate> atTime(LocalTime localTime) {
        return super.atTime(localTime);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof HijrahDate) {
            object = (HijrahDate)object;
            if (this.prolepticYear != ((HijrahDate)object).prolepticYear || this.monthOfYear != ((HijrahDate)object).monthOfYear || this.dayOfMonth != ((HijrahDate)object).dayOfMonth || !((AbstractChronology)this.getChronology()).equals(((HijrahDate)object).getChronology())) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public HijrahChronology getChronology() {
        return this.chrono;
    }

    @Override
    public HijrahEra getEra() {
        return HijrahEra.AH;
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            switch ((ChronoField)temporalField) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported field: ");
                    stringBuilder.append(temporalField);
                    throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                }
                case ERA: {
                    return this.getEraValue();
                }
                case YEAR: {
                    return this.prolepticYear;
                }
                case YEAR_OF_ERA: {
                    return this.prolepticYear;
                }
                case PROLEPTIC_MONTH: {
                    return this.getProlepticMonth();
                }
                case MONTH_OF_YEAR: {
                    return this.monthOfYear;
                }
                case ALIGNED_WEEK_OF_YEAR: {
                    return (this.getDayOfYear() - 1) / 7 + 1;
                }
                case EPOCH_DAY: {
                    return this.toEpochDay();
                }
                case ALIGNED_DAY_OF_WEEK_IN_YEAR: {
                    return (this.getDayOfYear() - 1) % 7 + 1;
                }
                case ALIGNED_DAY_OF_WEEK_IN_MONTH: {
                    return (this.getDayOfWeek() - 1) % 7 + 1;
                }
                case DAY_OF_WEEK: {
                    return this.getDayOfWeek();
                }
                case ALIGNED_WEEK_OF_MONTH: {
                    return (this.dayOfMonth - 1) / 7 + 1;
                }
                case DAY_OF_YEAR: {
                    return this.getDayOfYear();
                }
                case DAY_OF_MONTH: 
            }
            return this.dayOfMonth;
        }
        return temporalField.getFrom(this);
    }

    @Override
    public int hashCode() {
        int n = this.prolepticYear;
        int n2 = this.monthOfYear;
        int n3 = this.dayOfMonth;
        return ((HijrahChronology)this.getChronology()).getId().hashCode() ^ n & -2048 ^ (n << 11) + (n2 << 6) + n3;
    }

    @Override
    public boolean isLeapYear() {
        return this.chrono.isLeapYear(this.prolepticYear);
    }

    @Override
    public int lengthOfMonth() {
        return this.chrono.getMonthLength(this.prolepticYear, this.monthOfYear);
    }

    @Override
    public int lengthOfYear() {
        return this.chrono.getYearLength(this.prolepticYear);
    }

    @Override
    public HijrahDate minus(long l, TemporalUnit temporalUnit) {
        return (HijrahDate)super.minus(l, temporalUnit);
    }

    @Override
    public HijrahDate minus(TemporalAmount temporalAmount) {
        return (HijrahDate)super.minus(temporalAmount);
    }

    @Override
    HijrahDate minusDays(long l) {
        return (HijrahDate)super.minusDays(l);
    }

    @Override
    HijrahDate minusMonths(long l) {
        return (HijrahDate)super.minusMonths(l);
    }

    @Override
    HijrahDate minusWeeks(long l) {
        return (HijrahDate)super.minusWeeks(l);
    }

    @Override
    HijrahDate minusYears(long l) {
        return (HijrahDate)super.minusYears(l);
    }

    @Override
    public HijrahDate plus(long l, TemporalUnit temporalUnit) {
        return (HijrahDate)super.plus(l, temporalUnit);
    }

    @Override
    public HijrahDate plus(TemporalAmount temporalAmount) {
        return (HijrahDate)super.plus(temporalAmount);
    }

    @Override
    HijrahDate plusDays(long l) {
        return new HijrahDate(this.chrono, this.toEpochDay() + l);
    }

    @Override
    HijrahDate plusMonths(long l) {
        if (l == 0L) {
            return this;
        }
        l = (long)this.prolepticYear * 12L + (long)(this.monthOfYear - 1) + l;
        return this.resolvePreviousValid(this.chrono.checkValidYear(Math.floorDiv(l, 12L)), (int)Math.floorMod(l, 12L) + 1, this.dayOfMonth);
    }

    @Override
    HijrahDate plusWeeks(long l) {
        return (HijrahDate)super.plusWeeks(l);
    }

    @Override
    HijrahDate plusYears(long l) {
        if (l == 0L) {
            return this;
        }
        return this.resolvePreviousValid(Math.addExact(this.prolepticYear, (int)l), this.monthOfYear, this.dayOfMonth);
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (this.isSupported(temporalField)) {
                int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)((Object)(temporalField = (ChronoField)temporalField))).ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return ((HijrahChronology)this.getChronology()).range((ChronoField)temporalField);
                        }
                        return ValueRange.of(1L, 5L);
                    }
                    return ValueRange.of(1L, this.lengthOfYear());
                }
                return ValueRange.of(1L, this.lengthOfMonth());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported field: ");
            stringBuilder.append(temporalField);
            throw new UnsupportedTemporalTypeException(stringBuilder.toString());
        }
        return temporalField.rangeRefinedBy(this);
    }

    @Override
    public long toEpochDay() {
        return this.chrono.getEpochDay(this.prolepticYear, this.monthOfYear, this.dayOfMonth);
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate chronoLocalDate) {
        long l;
        int n;
        chronoLocalDate = ((HijrahChronology)this.getChronology()).date(chronoLocalDate);
        long l2 = (((HijrahDate)chronoLocalDate).prolepticYear - this.prolepticYear) * 12 + (((HijrahDate)chronoLocalDate).monthOfYear - this.monthOfYear);
        int n2 = ((HijrahDate)chronoLocalDate).dayOfMonth - this.dayOfMonth;
        if (l2 > 0L && n2 < 0) {
            l = l2 - 1L;
            ChronoLocalDate chronoLocalDate2 = this.plusMonths(l);
            n = (int)(((HijrahDate)chronoLocalDate).toEpochDay() - ((HijrahDate)chronoLocalDate2).toEpochDay());
        } else {
            l = l2;
            n = n2;
            if (l2 < 0L) {
                l = l2;
                n = n2;
                if (n2 > 0) {
                    l = l2 + 1L;
                    n = n2 - ((HijrahDate)chronoLocalDate).lengthOfMonth();
                }
            }
        }
        l2 = l / 12L;
        n2 = (int)(l % 12L);
        return this.getChronology().period(Math.toIntExact(l2), n2, n);
    }

    @Override
    public HijrahDate with(TemporalAdjuster temporalAdjuster) {
        return (HijrahDate)super.with(temporalAdjuster);
    }

    @Override
    public HijrahDate with(TemporalField temporalField, long l) {
        if (temporalField instanceof ChronoField) {
            Object object = (ChronoField)temporalField;
            this.chrono.range((ChronoField)object).checkValidValue(l, (TemporalField)object);
            int n = (int)l;
            switch (1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object).ordinal()]) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported field: ");
                    ((StringBuilder)object).append(temporalField);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case 13: {
                    return this.resolvePreviousValid(1 - this.prolepticYear, this.monthOfYear, this.dayOfMonth);
                }
                case 12: {
                    return this.resolvePreviousValid(n, this.monthOfYear, this.dayOfMonth);
                }
                case 11: {
                    if (this.prolepticYear < 1) {
                        n = 1 - n;
                    }
                    return this.resolvePreviousValid(n, this.monthOfYear, this.dayOfMonth);
                }
                case 10: {
                    return this.plusMonths(l - this.getProlepticMonth());
                }
                case 9: {
                    return this.resolvePreviousValid(this.prolepticYear, n, this.dayOfMonth);
                }
                case 8: {
                    return this.plusDays((l - this.getLong(ChronoField.ALIGNED_WEEK_OF_YEAR)) * 7L);
                }
                case 7: {
                    return new HijrahDate(this.chrono, l);
                }
                case 6: {
                    return this.plusDays(l - this.getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
                }
                case 5: {
                    return this.plusDays(l - this.getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
                }
                case 4: {
                    return this.plusDays(l - (long)this.getDayOfWeek());
                }
                case 3: {
                    return this.plusDays((l - this.getLong(ChronoField.ALIGNED_WEEK_OF_MONTH)) * 7L);
                }
                case 2: {
                    return this.plusDays(Math.min(n, this.lengthOfYear()) - this.getDayOfYear());
                }
                case 1: 
            }
            return this.resolvePreviousValid(this.prolepticYear, this.monthOfYear, n);
        }
        return (HijrahDate)super.with(temporalField, l);
    }

    public HijrahDate withVariant(HijrahChronology hijrahChronology) {
        int n;
        if (this.chrono == hijrahChronology) {
            return this;
        }
        int n2 = hijrahChronology.getDayOfYear(this.prolepticYear, this.monthOfYear);
        int n3 = this.prolepticYear;
        int n4 = this.monthOfYear;
        int n5 = n = this.dayOfMonth;
        if (n > n2) {
            n5 = n2;
        }
        return HijrahDate.of(hijrahChronology, n3, n4, n5);
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(this.getChronology());
        objectOutput.writeInt(this.get(ChronoField.YEAR));
        objectOutput.writeByte(this.get(ChronoField.MONTH_OF_YEAR));
        objectOutput.writeByte(this.get(ChronoField.DAY_OF_MONTH));
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateImpl;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.Ser;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

public final class ThaiBuddhistDate
extends ChronoLocalDateImpl<ThaiBuddhistDate>
implements ChronoLocalDate,
Serializable {
    private static final long serialVersionUID = -8722293800195731463L;
    private final transient LocalDate isoDate;

    ThaiBuddhistDate(LocalDate localDate) {
        Objects.requireNonNull(localDate, "isoDate");
        this.isoDate = localDate;
    }

    public static ThaiBuddhistDate from(TemporalAccessor temporalAccessor) {
        return ThaiBuddhistChronology.INSTANCE.date(temporalAccessor);
    }

    private long getProlepticMonth() {
        return (long)this.getProlepticYear() * 12L + (long)this.isoDate.getMonthValue() - 1L;
    }

    private int getProlepticYear() {
        return this.isoDate.getYear() + 543;
    }

    public static ThaiBuddhistDate now() {
        return ThaiBuddhistDate.now(Clock.systemDefaultZone());
    }

    public static ThaiBuddhistDate now(Clock clock) {
        return new ThaiBuddhistDate(LocalDate.now(clock));
    }

    public static ThaiBuddhistDate now(ZoneId zoneId) {
        return ThaiBuddhistDate.now(Clock.system(zoneId));
    }

    public static ThaiBuddhistDate of(int n, int n2, int n3) {
        return new ThaiBuddhistDate(LocalDate.of(n - 543, n2, n3));
    }

    static ThaiBuddhistDate readExternal(DataInput dataInput) throws IOException {
        int n = dataInput.readInt();
        byte by = dataInput.readByte();
        byte by2 = dataInput.readByte();
        return ThaiBuddhistChronology.INSTANCE.date(n, by, by2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private ThaiBuddhistDate with(LocalDate chronoLocalDate) {
        chronoLocalDate = chronoLocalDate.equals(this.isoDate) ? this : new ThaiBuddhistDate((LocalDate)chronoLocalDate);
        return chronoLocalDate;
    }

    private Object writeReplace() {
        return new Ser(8, this);
    }

    public final ChronoLocalDateTime<ThaiBuddhistDate> atTime(LocalTime localTime) {
        return super.atTime(localTime);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof ThaiBuddhistDate) {
            object = (ThaiBuddhistDate)object;
            return this.isoDate.equals(((ThaiBuddhistDate)object).isoDate);
        }
        return false;
    }

    @Override
    public ThaiBuddhistChronology getChronology() {
        return ThaiBuddhistChronology.INSTANCE;
    }

    @Override
    public ThaiBuddhistEra getEra() {
        ThaiBuddhistEra thaiBuddhistEra = this.getProlepticYear() >= 1 ? ThaiBuddhistEra.BE : ThaiBuddhistEra.BEFORE_BE;
        return thaiBuddhistEra;
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            int n2 = 1;
            if (n != 4) {
                if (n != 5) {
                    if (n != 6) {
                        if (n != 7) {
                            return this.isoDate.getLong(temporalField);
                        }
                        if (this.getProlepticYear() < 1) {
                            n2 = 0;
                        }
                        return n2;
                    }
                    return this.getProlepticYear();
                }
                return this.getProlepticMonth();
            }
            n2 = this.getProlepticYear();
            if (n2 < 1) {
                n2 = 1 - n2;
            }
            return n2;
        }
        return temporalField.getFrom(this);
    }

    @Override
    public int hashCode() {
        return ((ThaiBuddhistChronology)this.getChronology()).getId().hashCode() ^ this.isoDate.hashCode();
    }

    @Override
    public int lengthOfMonth() {
        return this.isoDate.lengthOfMonth();
    }

    @Override
    public ThaiBuddhistDate minus(long l, TemporalUnit temporalUnit) {
        return (ThaiBuddhistDate)super.minus(l, temporalUnit);
    }

    @Override
    public ThaiBuddhistDate minus(TemporalAmount temporalAmount) {
        return (ThaiBuddhistDate)super.minus(temporalAmount);
    }

    @Override
    ThaiBuddhistDate minusDays(long l) {
        return (ThaiBuddhistDate)super.minusDays(l);
    }

    @Override
    ThaiBuddhistDate minusMonths(long l) {
        return (ThaiBuddhistDate)super.minusMonths(l);
    }

    @Override
    ThaiBuddhistDate minusWeeks(long l) {
        return (ThaiBuddhistDate)super.minusWeeks(l);
    }

    @Override
    ThaiBuddhistDate minusYears(long l) {
        return (ThaiBuddhistDate)super.minusYears(l);
    }

    @Override
    public ThaiBuddhistDate plus(long l, TemporalUnit temporalUnit) {
        return (ThaiBuddhistDate)super.plus(l, temporalUnit);
    }

    @Override
    public ThaiBuddhistDate plus(TemporalAmount temporalAmount) {
        return (ThaiBuddhistDate)super.plus(temporalAmount);
    }

    @Override
    ThaiBuddhistDate plusDays(long l) {
        return this.with(this.isoDate.plusDays(l));
    }

    @Override
    ThaiBuddhistDate plusMonths(long l) {
        return this.with(this.isoDate.plusMonths(l));
    }

    @Override
    ThaiBuddhistDate plusWeeks(long l) {
        return (ThaiBuddhistDate)super.plusWeeks(l);
    }

    @Override
    ThaiBuddhistDate plusYears(long l) {
        return this.with(this.isoDate.plusYears(l));
    }

    @Override
    public ValueRange range(TemporalField object) {
        if (object instanceof ChronoField) {
            if (this.isSupported((TemporalField)object)) {
                ChronoField chronoField = (ChronoField)object;
                int n = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                if (n != 1 && n != 2 && n != 3) {
                    if (n != 4) {
                        return ((ThaiBuddhistChronology)this.getChronology()).range(chronoField);
                    }
                    object = ChronoField.YEAR.range();
                    long l = this.getProlepticYear() <= 0 ? -(((ValueRange)object).getMinimum() + 543L) + 1L : 543L + ((ValueRange)object).getMaximum();
                    return ValueRange.of(1L, l);
                }
                return this.isoDate.range((TemporalField)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported field: ");
            stringBuilder.append(object);
            throw new UnsupportedTemporalTypeException(stringBuilder.toString());
        }
        return object.rangeRefinedBy(this);
    }

    @Override
    public long toEpochDay() {
        return this.isoDate.toEpochDay();
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate object) {
        object = this.isoDate.until((ChronoLocalDate)object);
        return this.getChronology().period(((Period)object).getYears(), ((Period)object).getMonths(), ((Period)object).getDays());
    }

    @Override
    public ThaiBuddhistDate with(TemporalAdjuster temporalAdjuster) {
        return (ThaiBuddhistDate)super.with(temporalAdjuster);
    }

    @Override
    public ThaiBuddhistDate with(TemporalField object, long l) {
        block5 : {
            int n;
            block9 : {
                block10 : {
                    block11 : {
                        block8 : {
                            ChronoField chronoField;
                            block6 : {
                                block7 : {
                                    if (!(object instanceof ChronoField)) break block5;
                                    chronoField = (ChronoField)object;
                                    if (this.getLong(chronoField) == l) {
                                        return this;
                                    }
                                    n = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                                    if (n == 4) break block6;
                                    if (n == 5) break block7;
                                    if (n == 6 || n == 7) break block6;
                                    break block8;
                                }
                                ((ThaiBuddhistChronology)this.getChronology()).range(chronoField).checkValidValue(l, chronoField);
                                return this.plusMonths(l - this.getProlepticMonth());
                            }
                            n = ((ThaiBuddhistChronology)this.getChronology()).range(chronoField).checkValidIntValue(l, chronoField);
                            int n2 = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                            if (n2 == 4) break block9;
                            if (n2 == 6) break block10;
                            if (n2 == 7) break block11;
                        }
                        return this.with(this.isoDate.with((TemporalField)object, l));
                    }
                    return this.with(this.isoDate.withYear(1 - this.getProlepticYear() - 543));
                }
                return this.with(this.isoDate.withYear(n - 543));
            }
            object = this.isoDate;
            if (this.getProlepticYear() < 1) {
                n = 1 - n;
            }
            return this.with(((LocalDate)object).withYear(n - 543));
        }
        return (ThaiBuddhistDate)super.with((TemporalField)object, l);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.get(ChronoField.YEAR));
        dataOutput.writeByte(this.get(ChronoField.MONTH_OF_YEAR));
        dataOutput.writeByte(this.get(ChronoField.DAY_OF_MONTH));
    }

}


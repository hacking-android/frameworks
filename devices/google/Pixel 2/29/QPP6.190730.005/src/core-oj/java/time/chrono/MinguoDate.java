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
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoEra;
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
import java.util.Objects;

public final class MinguoDate
extends ChronoLocalDateImpl<MinguoDate>
implements ChronoLocalDate,
Serializable {
    private static final long serialVersionUID = 1300372329181994526L;
    private final transient LocalDate isoDate;

    MinguoDate(LocalDate localDate) {
        Objects.requireNonNull(localDate, "isoDate");
        this.isoDate = localDate;
    }

    public static MinguoDate from(TemporalAccessor temporalAccessor) {
        return MinguoChronology.INSTANCE.date(temporalAccessor);
    }

    private long getProlepticMonth() {
        return (long)this.getProlepticYear() * 12L + (long)this.isoDate.getMonthValue() - 1L;
    }

    private int getProlepticYear() {
        return this.isoDate.getYear() - 1911;
    }

    public static MinguoDate now() {
        return MinguoDate.now(Clock.systemDefaultZone());
    }

    public static MinguoDate now(Clock clock) {
        return new MinguoDate(LocalDate.now(clock));
    }

    public static MinguoDate now(ZoneId zoneId) {
        return MinguoDate.now(Clock.system(zoneId));
    }

    public static MinguoDate of(int n, int n2, int n3) {
        return new MinguoDate(LocalDate.of(n + 1911, n2, n3));
    }

    static MinguoDate readExternal(DataInput dataInput) throws IOException {
        int n = dataInput.readInt();
        byte by = dataInput.readByte();
        byte by2 = dataInput.readByte();
        return MinguoChronology.INSTANCE.date(n, by, by2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private MinguoDate with(LocalDate chronoLocalDate) {
        chronoLocalDate = chronoLocalDate.equals(this.isoDate) ? this : new MinguoDate((LocalDate)chronoLocalDate);
        return chronoLocalDate;
    }

    private Object writeReplace() {
        return new Ser(7, this);
    }

    public final ChronoLocalDateTime<MinguoDate> atTime(LocalTime localTime) {
        return super.atTime(localTime);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof MinguoDate) {
            object = (MinguoDate)object;
            return this.isoDate.equals(((MinguoDate)object).isoDate);
        }
        return false;
    }

    @Override
    public MinguoChronology getChronology() {
        return MinguoChronology.INSTANCE;
    }

    @Override
    public MinguoEra getEra() {
        MinguoEra minguoEra = this.getProlepticYear() >= 1 ? MinguoEra.ROC : MinguoEra.BEFORE_ROC;
        return minguoEra;
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
        return ((MinguoChronology)this.getChronology()).getId().hashCode() ^ this.isoDate.hashCode();
    }

    @Override
    public int lengthOfMonth() {
        return this.isoDate.lengthOfMonth();
    }

    @Override
    public MinguoDate minus(long l, TemporalUnit temporalUnit) {
        return (MinguoDate)super.minus(l, temporalUnit);
    }

    @Override
    public MinguoDate minus(TemporalAmount temporalAmount) {
        return (MinguoDate)super.minus(temporalAmount);
    }

    @Override
    MinguoDate minusDays(long l) {
        return (MinguoDate)super.minusDays(l);
    }

    @Override
    MinguoDate minusMonths(long l) {
        return (MinguoDate)super.minusMonths(l);
    }

    @Override
    MinguoDate minusWeeks(long l) {
        return (MinguoDate)super.minusWeeks(l);
    }

    @Override
    MinguoDate minusYears(long l) {
        return (MinguoDate)super.minusYears(l);
    }

    @Override
    public MinguoDate plus(long l, TemporalUnit temporalUnit) {
        return (MinguoDate)super.plus(l, temporalUnit);
    }

    @Override
    public MinguoDate plus(TemporalAmount temporalAmount) {
        return (MinguoDate)super.plus(temporalAmount);
    }

    @Override
    MinguoDate plusDays(long l) {
        return this.with(this.isoDate.plusDays(l));
    }

    @Override
    MinguoDate plusMonths(long l) {
        return this.with(this.isoDate.plusMonths(l));
    }

    @Override
    MinguoDate plusWeeks(long l) {
        return (MinguoDate)super.plusWeeks(l);
    }

    @Override
    MinguoDate plusYears(long l) {
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
                        return ((MinguoChronology)this.getChronology()).range(chronoField);
                    }
                    object = ChronoField.YEAR.range();
                    long l = this.getProlepticYear() <= 0 ? -((ValueRange)object).getMinimum() + 1L + 1911L : ((ValueRange)object).getMaximum() - 1911L;
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
    public MinguoDate with(TemporalAdjuster temporalAdjuster) {
        return (MinguoDate)super.with(temporalAdjuster);
    }

    @Override
    public MinguoDate with(TemporalField object, long l) {
        block4 : {
            int n;
            int n2;
            block8 : {
                block9 : {
                    block10 : {
                        block7 : {
                            ChronoField chronoField;
                            block5 : {
                                block6 : {
                                    if (!(object instanceof ChronoField)) break block4;
                                    chronoField = (ChronoField)object;
                                    if (this.getLong(chronoField) == l) {
                                        return this;
                                    }
                                    n2 = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                                    if (n2 == 4) break block5;
                                    if (n2 == 5) break block6;
                                    if (n2 == 6 || n2 == 7) break block5;
                                    break block7;
                                }
                                ((MinguoChronology)this.getChronology()).range(chronoField).checkValidValue(l, chronoField);
                                return this.plusMonths(l - this.getProlepticMonth());
                            }
                            n = ((MinguoChronology)this.getChronology()).range(chronoField).checkValidIntValue(l, chronoField);
                            n2 = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                            if (n2 == 4) break block8;
                            if (n2 == 6) break block9;
                            if (n2 == 7) break block10;
                        }
                        return this.with(this.isoDate.with((TemporalField)object, l));
                    }
                    return this.with(this.isoDate.withYear(1 - this.getProlepticYear() + 1911));
                }
                return this.with(this.isoDate.withYear(n + 1911));
            }
            object = this.isoDate;
            n2 = this.getProlepticYear() >= 1 ? n + 1911 : 1 - n + 1911;
            return this.with(((LocalDate)object).withYear(n2));
        }
        return (MinguoDate)super.with((TemporalField)object, l);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.get(ChronoField.YEAR));
        dataOutput.writeByte(this.get(ChronoField.MONTH_OF_YEAR));
        dataOutput.writeByte(this.get(ChronoField.DAY_OF_MONTH));
    }

}


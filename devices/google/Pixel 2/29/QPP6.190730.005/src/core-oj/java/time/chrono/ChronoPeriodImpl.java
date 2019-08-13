/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Ser;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

final class ChronoPeriodImpl
implements ChronoPeriod,
Serializable {
    private static final List<TemporalUnit> SUPPORTED_UNITS = Collections.unmodifiableList(Arrays.asList(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS));
    private static final long serialVersionUID = 57387258289L;
    private final Chronology chrono;
    final int days;
    final int months;
    final int years;

    ChronoPeriodImpl(Chronology chronology, int n, int n2, int n3) {
        Objects.requireNonNull(chronology, "chrono");
        this.chrono = chronology;
        this.years = n;
        this.months = n2;
        this.days = n3;
    }

    private long monthRange() {
        ValueRange valueRange = this.chrono.range(ChronoField.MONTH_OF_YEAR);
        if (valueRange.isFixed() && valueRange.isIntValue()) {
            return valueRange.getMaximum() - valueRange.getMinimum() + 1L;
        }
        return -1L;
    }

    static ChronoPeriodImpl readExternal(DataInput dataInput) throws IOException {
        return new ChronoPeriodImpl(Chronology.of(dataInput.readUTF()), dataInput.readInt(), dataInput.readInt(), dataInput.readInt());
    }

    private void readObject(ObjectInputStream objectInputStream) throws ObjectStreamException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private ChronoPeriodImpl validateAmount(TemporalAmount temporalAmount) {
        Objects.requireNonNull(temporalAmount, "amount");
        if (temporalAmount instanceof ChronoPeriodImpl) {
            if (this.chrono.equals(((ChronoPeriodImpl)(temporalAmount = (ChronoPeriodImpl)temporalAmount)).getChronology())) {
                return temporalAmount;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Chronology mismatch, expected: ");
            stringBuilder.append(this.chrono.getId());
            stringBuilder.append(", actual: ");
            stringBuilder.append(((ChronoPeriodImpl)temporalAmount).getChronology().getId());
            throw new ClassCastException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to obtain ChronoPeriod from TemporalAmount: ");
        stringBuilder.append(temporalAmount.getClass());
        throw new DateTimeException(stringBuilder.toString());
    }

    private void validateChrono(TemporalAccessor object) {
        Objects.requireNonNull(object, "temporal");
        object = object.query(TemporalQueries.chronology());
        if (object != null && !this.chrono.equals(object)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Chronology mismatch, expected: ");
            stringBuilder.append(this.chrono.getId());
            stringBuilder.append(", actual: ");
            stringBuilder.append(object.getId());
            throw new DateTimeException(stringBuilder.toString());
        }
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        Temporal temporal2;
        int n;
        this.validateChrono(temporal);
        if (this.months == 0) {
            n = this.years;
            temporal2 = temporal;
            if (n != 0) {
                temporal2 = temporal.plus(n, ChronoUnit.YEARS);
            }
        } else {
            long l = this.monthRange();
            if (l > 0L) {
                temporal2 = temporal.plus((long)this.years * l + (long)this.months, ChronoUnit.MONTHS);
            } else {
                n = this.years;
                temporal2 = temporal;
                if (n != 0) {
                    temporal2 = temporal.plus(n, ChronoUnit.YEARS);
                }
                temporal2 = temporal2.plus(this.months, ChronoUnit.MONTHS);
            }
        }
        n = this.days;
        temporal = temporal2;
        if (n != 0) {
            temporal = temporal2.plus(n, ChronoUnit.DAYS);
        }
        return temporal;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ChronoPeriodImpl) {
            object = (ChronoPeriodImpl)object;
            if (this.years != ((ChronoPeriodImpl)object).years || this.months != ((ChronoPeriodImpl)object).months || this.days != ((ChronoPeriodImpl)object).days || !this.chrono.equals(((ChronoPeriodImpl)object).chrono)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public long get(TemporalUnit temporalUnit) {
        if (temporalUnit == ChronoUnit.YEARS) {
            return this.years;
        }
        if (temporalUnit == ChronoUnit.MONTHS) {
            return this.months;
        }
        if (temporalUnit == ChronoUnit.DAYS) {
            return this.days;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported unit: ");
        stringBuilder.append(temporalUnit);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    @Override
    public Chronology getChronology() {
        return this.chrono;
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return SUPPORTED_UNITS;
    }

    @Override
    public int hashCode() {
        return this.years + Integer.rotateLeft(this.months, 8) + Integer.rotateLeft(this.days, 16) ^ this.chrono.hashCode();
    }

    @Override
    public boolean isNegative() {
        boolean bl = this.years < 0 || this.months < 0 || this.days < 0;
        return bl;
    }

    @Override
    public boolean isZero() {
        boolean bl = this.years == 0 && this.months == 0 && this.days == 0;
        return bl;
    }

    @Override
    public ChronoPeriod minus(TemporalAmount temporalAmount) {
        temporalAmount = this.validateAmount(temporalAmount);
        return new ChronoPeriodImpl(this.chrono, Math.subtractExact(this.years, ((ChronoPeriodImpl)temporalAmount).years), Math.subtractExact(this.months, ((ChronoPeriodImpl)temporalAmount).months), Math.subtractExact(this.days, ((ChronoPeriodImpl)temporalAmount).days));
    }

    @Override
    public ChronoPeriod multipliedBy(int n) {
        if (!this.isZero() && n != 1) {
            return new ChronoPeriodImpl(this.chrono, Math.multiplyExact(this.years, n), Math.multiplyExact(this.months, n), Math.multiplyExact(this.days, n));
        }
        return this;
    }

    @Override
    public ChronoPeriod normalized() {
        long l = this.monthRange();
        if (l > 0L) {
            int n = this.years;
            long l2 = n;
            int n2 = this.months;
            l2 = l2 * l + (long)n2;
            long l3 = l2 / l;
            int n3 = (int)(l2 % l);
            if (l3 == (long)n && n3 == n2) {
                return this;
            }
            return new ChronoPeriodImpl(this.chrono, Math.toIntExact(l3), n3, this.days);
        }
        return this;
    }

    @Override
    public ChronoPeriod plus(TemporalAmount temporalAmount) {
        temporalAmount = this.validateAmount(temporalAmount);
        return new ChronoPeriodImpl(this.chrono, Math.addExact(this.years, ((ChronoPeriodImpl)temporalAmount).years), Math.addExact(this.months, ((ChronoPeriodImpl)temporalAmount).months), Math.addExact(this.days, ((ChronoPeriodImpl)temporalAmount).days));
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        Temporal temporal2;
        int n;
        this.validateChrono(temporal);
        if (this.months == 0) {
            n = this.years;
            temporal2 = temporal;
            if (n != 0) {
                temporal2 = temporal.minus(n, ChronoUnit.YEARS);
            }
        } else {
            long l = this.monthRange();
            if (l > 0L) {
                temporal2 = temporal.minus((long)this.years * l + (long)this.months, ChronoUnit.MONTHS);
            } else {
                n = this.years;
                temporal2 = temporal;
                if (n != 0) {
                    temporal2 = temporal.minus(n, ChronoUnit.YEARS);
                }
                temporal2 = temporal2.minus(this.months, ChronoUnit.MONTHS);
            }
        }
        n = this.days;
        temporal = temporal2;
        if (n != 0) {
            temporal = temporal2.minus(n, ChronoUnit.DAYS);
        }
        return temporal;
    }

    @Override
    public String toString() {
        if (this.isZero()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getChronology().toString());
            stringBuilder.append(" P0D");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getChronology().toString());
        stringBuilder.append(' ');
        stringBuilder.append('P');
        int n = this.years;
        if (n != 0) {
            stringBuilder.append(n);
            stringBuilder.append('Y');
        }
        if ((n = this.months) != 0) {
            stringBuilder.append(n);
            stringBuilder.append('M');
        }
        if ((n = this.days) != 0) {
            stringBuilder.append(n);
            stringBuilder.append('D');
        }
        return stringBuilder.toString();
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.chrono.getId());
        dataOutput.writeInt(this.years);
        dataOutput.writeInt(this.months);
        dataOutput.writeInt(this.days);
    }

    protected Object writeReplace() {
        return new Ser(9, this);
    }
}


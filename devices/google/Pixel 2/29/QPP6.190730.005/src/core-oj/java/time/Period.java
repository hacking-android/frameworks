/*
 * Decompiled with CFR 0.145.
 */
package java.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Ser;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Period
implements ChronoPeriod,
Serializable {
    private static final Pattern PATTERN;
    private static final List<TemporalUnit> SUPPORTED_UNITS;
    public static final Period ZERO;
    private static final long serialVersionUID = -3587258372562876L;
    private final int days;
    private final int months;
    private final int years;

    static {
        ZERO = new Period(0, 0, 0);
        PATTERN = Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?", 2);
        SUPPORTED_UNITS = Collections.unmodifiableList(Arrays.asList(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS));
    }

    private Period(int n, int n2, int n3) {
        this.years = n;
        this.months = n2;
        this.days = n3;
    }

    public static Period between(LocalDate localDate, LocalDate localDate2) {
        return localDate.until(localDate2);
    }

    private static Period create(int n, int n2, int n3) {
        if ((n | n2 | n3) == 0) {
            return ZERO;
        }
        return new Period(n, n2, n3);
    }

    public static Period from(TemporalAmount object) {
        if (object instanceof Period) {
            return (Period)object;
        }
        if (object instanceof ChronoPeriod && !IsoChronology.INSTANCE.equals(((ChronoPeriod)object).getChronology())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Period requires ISO chronology: ");
            stringBuilder.append(object);
            throw new DateTimeException(stringBuilder.toString());
        }
        Objects.requireNonNull(object, "amount");
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        for (TemporalUnit temporalUnit : object.getUnits()) {
            long l = object.get(temporalUnit);
            if (temporalUnit == ChronoUnit.YEARS) {
                n = Math.toIntExact(l);
                continue;
            }
            if (temporalUnit == ChronoUnit.MONTHS) {
                n2 = Math.toIntExact(l);
                continue;
            }
            if (temporalUnit == ChronoUnit.DAYS) {
                n3 = Math.toIntExact(l);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unit must be Years, Months or Days, but was ");
            ((StringBuilder)object).append(temporalUnit);
            throw new DateTimeException(((StringBuilder)object).toString());
        }
        return Period.create(n, n2, n3);
    }

    public static Period of(int n, int n2, int n3) {
        return Period.create(n, n2, n3);
    }

    public static Period ofDays(int n) {
        return Period.create(0, 0, n);
    }

    public static Period ofMonths(int n) {
        return Period.create(0, n, 0);
    }

    public static Period ofWeeks(int n) {
        return Period.create(0, 0, Math.multiplyExact(n, 7));
    }

    public static Period ofYears(int n) {
        return Period.create(n, 0, 0);
    }

    public static Period parse(CharSequence charSequence) {
        Objects.requireNonNull(charSequence, "text");
        Object object = PATTERN.matcher(charSequence);
        if (((Matcher)object).matches()) {
            int n = 1;
            if ("-".equals(((Matcher)object).group(1))) {
                n = -1;
            }
            String string = ((Matcher)object).group(2);
            String string2 = ((Matcher)object).group(3);
            Object object2 = ((Matcher)object).group(4);
            object = ((Matcher)object).group(5);
            if (string != null || string2 != null || object != null || object2 != null) {
                try {
                    int n2 = Period.parseNumber(charSequence, string, n);
                    int n3 = Period.parseNumber(charSequence, string2, n);
                    int n4 = Period.parseNumber(charSequence, (String)object2, n);
                    object2 = Period.create(n2, n3, Math.addExact(Period.parseNumber(charSequence, (String)object, n), Math.multiplyExact(n4, 7)));
                    return object2;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0, numberFormatException);
                }
            }
        }
        throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0);
    }

    private static int parseNumber(CharSequence charSequence, String string, int n) {
        if (string == null) {
            return 0;
        }
        int n2 = Integer.parseInt(string);
        try {
            n = Math.multiplyExact(n2, n);
            return n;
        }
        catch (ArithmeticException arithmeticException) {
            throw new DateTimeParseException("Text cannot be parsed to a Period", charSequence, 0, arithmeticException);
        }
    }

    static Period readExternal(DataInput dataInput) throws IOException {
        return Period.of(dataInput.readInt(), dataInput.readInt(), dataInput.readInt());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private void validateChrono(TemporalAccessor object) {
        Objects.requireNonNull(object, "temporal");
        Chronology chronology = object.query(TemporalQueries.chronology());
        if (chronology != null && !IsoChronology.INSTANCE.equals(chronology)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Chronology mismatch, expected: ISO, actual: ");
            ((StringBuilder)object).append(chronology.getId());
            throw new DateTimeException(((StringBuilder)object).toString());
        }
    }

    private Object writeReplace() {
        return new Ser(14, this);
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
            long l = this.toTotalMonths();
            temporal2 = temporal;
            if (l != 0L) {
                temporal2 = temporal.plus(l, ChronoUnit.MONTHS);
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
        if (object instanceof Period) {
            object = (Period)object;
            if (this.years != ((Period)object).years || this.months != ((Period)object).months || this.days != ((Period)object).days) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public long get(TemporalUnit temporalUnit) {
        if (temporalUnit == ChronoUnit.YEARS) {
            return this.getYears();
        }
        if (temporalUnit == ChronoUnit.MONTHS) {
            return this.getMonths();
        }
        if (temporalUnit == ChronoUnit.DAYS) {
            return this.getDays();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported unit: ");
        stringBuilder.append(temporalUnit);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    @Override
    public IsoChronology getChronology() {
        return IsoChronology.INSTANCE;
    }

    public int getDays() {
        return this.days;
    }

    public int getMonths() {
        return this.months;
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return SUPPORTED_UNITS;
    }

    public int getYears() {
        return this.years;
    }

    @Override
    public int hashCode() {
        return this.years + Integer.rotateLeft(this.months, 8) + Integer.rotateLeft(this.days, 16);
    }

    @Override
    public boolean isNegative() {
        boolean bl = this.years < 0 || this.months < 0 || this.days < 0;
        return bl;
    }

    @Override
    public boolean isZero() {
        boolean bl = this == ZERO;
        return bl;
    }

    @Override
    public Period minus(TemporalAmount temporalAmount) {
        temporalAmount = Period.from(temporalAmount);
        return Period.create(Math.subtractExact(this.years, ((Period)temporalAmount).years), Math.subtractExact(this.months, ((Period)temporalAmount).months), Math.subtractExact(this.days, ((Period)temporalAmount).days));
    }

    public Period minusDays(long l) {
        Period period = l == Long.MIN_VALUE ? this.plusDays(Long.MAX_VALUE).plusDays(1L) : this.plusDays(-l);
        return period;
    }

    public Period minusMonths(long l) {
        Period period = l == Long.MIN_VALUE ? this.plusMonths(Long.MAX_VALUE).plusMonths(1L) : this.plusMonths(-l);
        return period;
    }

    public Period minusYears(long l) {
        Period period = l == Long.MIN_VALUE ? this.plusYears(Long.MAX_VALUE).plusYears(1L) : this.plusYears(-l);
        return period;
    }

    @Override
    public Period multipliedBy(int n) {
        if (this != ZERO && n != 1) {
            return Period.create(Math.multiplyExact(this.years, n), Math.multiplyExact(this.months, n), Math.multiplyExact(this.days, n));
        }
        return this;
    }

    @Override
    public Period negated() {
        return this.multipliedBy(-1);
    }

    @Override
    public Period normalized() {
        long l = this.toTotalMonths();
        long l2 = l / 12L;
        int n = (int)(l % 12L);
        if (l2 == (long)this.years && n == this.months) {
            return this;
        }
        return Period.create(Math.toIntExact(l2), n, this.days);
    }

    @Override
    public Period plus(TemporalAmount temporalAmount) {
        temporalAmount = Period.from(temporalAmount);
        return Period.create(Math.addExact(this.years, ((Period)temporalAmount).years), Math.addExact(this.months, ((Period)temporalAmount).months), Math.addExact(this.days, ((Period)temporalAmount).days));
    }

    public Period plusDays(long l) {
        if (l == 0L) {
            return this;
        }
        return Period.create(this.years, this.months, Math.toIntExact(Math.addExact((long)this.days, l)));
    }

    public Period plusMonths(long l) {
        if (l == 0L) {
            return this;
        }
        return Period.create(this.years, Math.toIntExact(Math.addExact((long)this.months, l)), this.days);
    }

    public Period plusYears(long l) {
        if (l == 0L) {
            return this;
        }
        return Period.create(Math.toIntExact(Math.addExact((long)this.years, l)), this.months, this.days);
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
            long l = this.toTotalMonths();
            temporal2 = temporal;
            if (l != 0L) {
                temporal2 = temporal.minus(l, ChronoUnit.MONTHS);
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
        if (this == ZERO) {
            return "P0D";
        }
        StringBuilder stringBuilder = new StringBuilder();
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

    public long toTotalMonths() {
        return (long)this.years * 12L + (long)this.months;
    }

    public Period withDays(int n) {
        if (n == this.days) {
            return this;
        }
        return Period.create(this.years, this.months, n);
    }

    public Period withMonths(int n) {
        if (n == this.months) {
            return this;
        }
        return Period.create(this.years, n, this.days);
    }

    public Period withYears(int n) {
        if (n == this.years) {
            return this;
        }
        return Period.create(n, this.months, this.days);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.years);
        dataOutput.writeInt(this.months);
        dataOutput.writeInt(this.days);
    }
}


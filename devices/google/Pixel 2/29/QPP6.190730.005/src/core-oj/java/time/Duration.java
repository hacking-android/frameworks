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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.Ser;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Duration
implements TemporalAmount,
Comparable<Duration>,
Serializable {
    private static final BigInteger BI_NANOS_PER_SECOND;
    private static final Pattern PATTERN;
    public static final Duration ZERO;
    private static final long serialVersionUID = 3078945930695997490L;
    private final int nanos;
    private final long seconds;

    static {
        ZERO = new Duration(0L, 0);
        BI_NANOS_PER_SECOND = BigInteger.valueOf(1000000000L);
        PATTERN = Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?", 2);
    }

    private Duration(long l, int n) {
        this.seconds = l;
        this.nanos = n;
    }

    public static Duration between(Temporal temporal, Temporal temporal2) {
        try {
            Duration duration = Duration.ofNanos(temporal.until(temporal2, ChronoUnit.NANOS));
            return duration;
        }
        catch (ArithmeticException | DateTimeException runtimeException) {
            long l;
            long l2 = temporal.until(temporal2, ChronoUnit.SECONDS);
            try {
                long l3 = temporal2.getLong(ChronoField.NANO_OF_SECOND);
                l = temporal.getLong(ChronoField.NANO_OF_SECOND);
                if (l2 > 0L && (l3 -= l) < 0L) {
                    l = l2 + 1L;
                } else {
                    l = l2;
                    if (l2 < 0L) {
                        l = l2;
                        if (l3 > 0L) {
                            l = l2 - 1L;
                        }
                    }
                }
                l2 = l;
                l = l3;
            }
            catch (DateTimeException dateTimeException) {
                l = 0L;
            }
            return Duration.ofSeconds(l2, l);
        }
    }

    private static Duration create(long l, int n) {
        if (((long)n | l) == 0L) {
            return ZERO;
        }
        return new Duration(l, n);
    }

    private static Duration create(BigDecimal number) {
        Object object = ((BigInteger)(number = ((BigDecimal)number).movePointRight(9).toBigIntegerExact())).divideAndRemainder(BI_NANOS_PER_SECOND);
        if (object[0].bitLength() <= 63) {
            return Duration.ofSeconds(object[0].longValue(), object[1].intValue());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Exceeds capacity of Duration: ");
        ((StringBuilder)object).append(number);
        throw new ArithmeticException(((StringBuilder)object).toString());
    }

    private static Duration create(boolean bl, long l, long l2, long l3, long l4, int n) {
        l = Math.addExact(l, Math.addExact(l2, Math.addExact(l3, l4)));
        if (bl) {
            return Duration.ofSeconds(l, n).negated();
        }
        return Duration.ofSeconds(l, n);
    }

    public static Duration from(TemporalAmount temporalAmount) {
        Objects.requireNonNull(temporalAmount, "amount");
        Duration duration = ZERO;
        for (TemporalUnit temporalUnit : temporalAmount.getUnits()) {
            duration = duration.plus(temporalAmount.get(temporalUnit), temporalUnit);
        }
        return duration;
    }

    public static Duration of(long l, TemporalUnit temporalUnit) {
        return ZERO.plus(l, temporalUnit);
    }

    public static Duration ofDays(long l) {
        return Duration.create(Math.multiplyExact(l, 86400L), 0);
    }

    public static Duration ofHours(long l) {
        return Duration.create(Math.multiplyExact(l, 3600L), 0);
    }

    public static Duration ofMillis(long l) {
        int n;
        long l2 = l / 1000L;
        int n2 = n = (int)(l % 1000L);
        l = l2;
        if (n < 0) {
            n2 = n + 1000;
            l = l2 - 1L;
        }
        return Duration.create(l, 1000000 * n2);
    }

    public static Duration ofMinutes(long l) {
        return Duration.create(Math.multiplyExact(l, 60L), 0);
    }

    public static Duration ofNanos(long l) {
        long l2 = l / 1000000000L;
        int n = (int)(l % 1000000000L);
        l = l2;
        int n2 = n;
        if (n < 0) {
            n2 = (int)((long)n + 1000000000L);
            l = l2 - 1L;
        }
        return Duration.create(l, n2);
    }

    public static Duration ofSeconds(long l) {
        return Duration.create(l, 0);
    }

    public static Duration ofSeconds(long l, long l2) {
        return Duration.create(Math.addExact(l, Math.floorDiv(l2, 1000000000L)), (int)Math.floorMod(l2, 1000000000L));
    }

    public static Duration parse(CharSequence charSequence) {
        Objects.requireNonNull(charSequence, "text");
        Object object = PATTERN.matcher(charSequence);
        if (((Matcher)object).matches() && !"T".equals(((Matcher)object).group(3))) {
            int n = 1;
            boolean bl = "-".equals(((Matcher)object).group(1));
            String string = ((Matcher)object).group(2);
            String string2 = ((Matcher)object).group(4);
            Object object2 = ((Matcher)object).group(5);
            String string3 = ((Matcher)object).group(6);
            object = ((Matcher)object).group(7);
            if (string != null || string2 != null || object2 != null || string3 != null) {
                long l = Duration.parseNumber(charSequence, string, 86400, "days");
                long l2 = Duration.parseNumber(charSequence, string2, 3600, "hours");
                long l3 = Duration.parseNumber(charSequence, (String)object2, 60, "minutes");
                long l4 = Duration.parseNumber(charSequence, string3, 1, "seconds");
                if (l4 < 0L) {
                    n = -1;
                }
                n = Duration.parseFraction(charSequence, (String)object, n);
                try {
                    object2 = Duration.create(bl, l, l2, l3, l4, n);
                    return object2;
                }
                catch (ArithmeticException arithmeticException) {
                    throw (DateTimeParseException)new DateTimeParseException("Text cannot be parsed to a Duration: overflow", charSequence, 0).initCause(arithmeticException);
                }
            }
        }
        throw new DateTimeParseException("Text cannot be parsed to a Duration", charSequence, 0);
    }

    private static int parseFraction(CharSequence charSequence, String string, int n) {
        if (string != null && string.length() != 0) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append("000000000");
                int n2 = Integer.parseInt(stringBuilder.toString().substring(0, 9));
                return n2 * n;
            }
            catch (ArithmeticException | NumberFormatException runtimeException) {
                throw (DateTimeParseException)new DateTimeParseException("Text cannot be parsed to a Duration: fraction", charSequence, 0).initCause(runtimeException);
            }
        }
        return 0;
    }

    private static long parseNumber(CharSequence charSequence, String charSequence2, int n, String string) {
        if (charSequence2 == null) {
            return 0L;
        }
        try {
            long l = Math.multiplyExact(Long.parseLong((String)charSequence2), (long)n);
            return l;
        }
        catch (ArithmeticException | NumberFormatException runtimeException) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Text cannot be parsed to a Duration: ");
            ((StringBuilder)charSequence2).append(string);
            throw (DateTimeParseException)new DateTimeParseException(((StringBuilder)charSequence2).toString(), charSequence, 0).initCause(runtimeException);
        }
    }

    private Duration plus(long l, long l2) {
        if ((l | l2) == 0L) {
            return this;
        }
        return Duration.ofSeconds(Math.addExact(Math.addExact(this.seconds, l), l2 / 1000000000L), (long)this.nanos + l2 % 1000000000L);
    }

    static Duration readExternal(DataInput dataInput) throws IOException {
        return Duration.ofSeconds(dataInput.readLong(), dataInput.readInt());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private BigDecimal toSeconds() {
        return BigDecimal.valueOf(this.seconds).add(BigDecimal.valueOf(this.nanos, 9));
    }

    private Object writeReplace() {
        return new Ser(1, this);
    }

    public Duration abs() {
        Duration duration = this.isNegative() ? this.negated() : this;
        return duration;
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        long l = this.seconds;
        Temporal temporal2 = temporal;
        if (l != 0L) {
            temporal2 = temporal.plus(l, ChronoUnit.SECONDS);
        }
        int n = this.nanos;
        temporal = temporal2;
        if (n != 0) {
            temporal = temporal2.plus(n, ChronoUnit.NANOS);
        }
        return temporal;
    }

    @Override
    public int compareTo(Duration duration) {
        int n = Long.compare(this.seconds, duration.seconds);
        if (n != 0) {
            return n;
        }
        return this.nanos - duration.nanos;
    }

    public Duration dividedBy(long l) {
        if (l != 0L) {
            if (l == 1L) {
                return this;
            }
            return Duration.create(this.toSeconds().divide(BigDecimal.valueOf(l), RoundingMode.DOWN));
        }
        throw new ArithmeticException("Cannot divide by zero");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof Duration) {
            object = (Duration)object;
            if (this.seconds != ((Duration)object).seconds || this.nanos != ((Duration)object).nanos) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public long get(TemporalUnit temporalUnit) {
        if (temporalUnit == ChronoUnit.SECONDS) {
            return this.seconds;
        }
        if (temporalUnit == ChronoUnit.NANOS) {
            return this.nanos;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported unit: ");
        stringBuilder.append(temporalUnit);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    public int getNano() {
        return this.nanos;
    }

    public long getSeconds() {
        return this.seconds;
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return DurationUnits.UNITS;
    }

    public int hashCode() {
        long l = this.seconds;
        return (int)(l ^ l >>> 32) + this.nanos * 51;
    }

    public boolean isNegative() {
        boolean bl = this.seconds < 0L;
        return bl;
    }

    public boolean isZero() {
        boolean bl = (this.seconds | (long)this.nanos) == 0L;
        return bl;
    }

    public Duration minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    public Duration minus(Duration duration) {
        long l = duration.getSeconds();
        int n = duration.getNano();
        if (l == Long.MIN_VALUE) {
            return this.plus(Long.MAX_VALUE, -n).plus(1L, 0L);
        }
        return this.plus(-l, -n);
    }

    public Duration minusDays(long l) {
        Duration duration = l == Long.MIN_VALUE ? this.plusDays(Long.MAX_VALUE).plusDays(1L) : this.plusDays(-l);
        return duration;
    }

    public Duration minusHours(long l) {
        Duration duration = l == Long.MIN_VALUE ? this.plusHours(Long.MAX_VALUE).plusHours(1L) : this.plusHours(-l);
        return duration;
    }

    public Duration minusMillis(long l) {
        Duration duration = l == Long.MIN_VALUE ? this.plusMillis(Long.MAX_VALUE).plusMillis(1L) : this.plusMillis(-l);
        return duration;
    }

    public Duration minusMinutes(long l) {
        Duration duration = l == Long.MIN_VALUE ? this.plusMinutes(Long.MAX_VALUE).plusMinutes(1L) : this.plusMinutes(-l);
        return duration;
    }

    public Duration minusNanos(long l) {
        Duration duration = l == Long.MIN_VALUE ? this.plusNanos(Long.MAX_VALUE).plusNanos(1L) : this.plusNanos(-l);
        return duration;
    }

    public Duration minusSeconds(long l) {
        Duration duration = l == Long.MIN_VALUE ? this.plusSeconds(Long.MAX_VALUE).plusSeconds(1L) : this.plusSeconds(-l);
        return duration;
    }

    public Duration multipliedBy(long l) {
        if (l == 0L) {
            return ZERO;
        }
        if (l == 1L) {
            return this;
        }
        return Duration.create(this.toSeconds().multiply(BigDecimal.valueOf(l)));
    }

    public Duration negated() {
        return this.multipliedBy(-1L);
    }

    public Duration plus(long l, TemporalUnit object) {
        Objects.requireNonNull(object, "unit");
        if (object == ChronoUnit.DAYS) {
            return this.plus(Math.multiplyExact(l, 86400L), 0L);
        }
        if (!object.isDurationEstimated()) {
            if (l == 0L) {
                return this;
            }
            if (object instanceof ChronoUnit) {
                int n = 1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit)object).ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                return this.plusSeconds(Math.multiplyExact(object.getDuration().seconds, l));
                            }
                            return this.plusSeconds(l);
                        }
                        return this.plusMillis(l);
                    }
                    return this.plusSeconds(l / 1000000000L * 1000L).plusNanos(l % 1000000000L * 1000L);
                }
                return this.plusNanos(l);
            }
            object = object.getDuration().multipliedBy(l);
            return this.plusSeconds(((Duration)object).getSeconds()).plusNanos(((Duration)object).getNano());
        }
        throw new UnsupportedTemporalTypeException("Unit must not have an estimated duration");
    }

    public Duration plus(Duration duration) {
        return this.plus(duration.getSeconds(), duration.getNano());
    }

    public Duration plusDays(long l) {
        return this.plus(Math.multiplyExact(l, 86400L), 0L);
    }

    public Duration plusHours(long l) {
        return this.plus(Math.multiplyExact(l, 3600L), 0L);
    }

    public Duration plusMillis(long l) {
        return this.plus(l / 1000L, l % 1000L * 1000000L);
    }

    public Duration plusMinutes(long l) {
        return this.plus(Math.multiplyExact(l, 60L), 0L);
    }

    public Duration plusNanos(long l) {
        return this.plus(0L, l);
    }

    public Duration plusSeconds(long l) {
        return this.plus(l, 0L);
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        long l = this.seconds;
        Temporal temporal2 = temporal;
        if (l != 0L) {
            temporal2 = temporal.minus(l, ChronoUnit.SECONDS);
        }
        int n = this.nanos;
        temporal = temporal2;
        if (n != 0) {
            temporal = temporal2.minus(n, ChronoUnit.NANOS);
        }
        return temporal;
    }

    public long toDays() {
        return this.seconds / 86400L;
    }

    public long toHours() {
        return this.seconds / 3600L;
    }

    public long toMillis() {
        return Math.addExact(Math.multiplyExact(this.seconds, 1000L), (long)(this.nanos / 1000000));
    }

    public long toMinutes() {
        return this.seconds / 60L;
    }

    public long toNanos() {
        return Math.addExact(Math.multiplyExact(this.seconds, 1000000000L), (long)this.nanos);
    }

    public String toString() {
        if (this == ZERO) {
            return "PT0S";
        }
        long l = this.seconds;
        long l2 = l / 3600L;
        int n = (int)(l % 3600L / 60L);
        int n2 = (int)(l % 60L);
        StringBuilder stringBuilder = new StringBuilder(24);
        stringBuilder.append("PT");
        if (l2 != 0L) {
            stringBuilder.append(l2);
            stringBuilder.append('H');
        }
        if (n != 0) {
            stringBuilder.append(n);
            stringBuilder.append('M');
        }
        if (n2 == 0 && this.nanos == 0 && stringBuilder.length() > 2) {
            return stringBuilder.toString();
        }
        if (n2 < 0 && this.nanos > 0) {
            if (n2 == -1) {
                stringBuilder.append("-0");
            } else {
                stringBuilder.append(n2 + 1);
            }
        } else {
            stringBuilder.append(n2);
        }
        if (this.nanos > 0) {
            n = stringBuilder.length();
            if (n2 < 0) {
                stringBuilder.append(2000000000L - (long)this.nanos);
            } else {
                stringBuilder.append((long)this.nanos + 1000000000L);
            }
            while (stringBuilder.charAt(stringBuilder.length() - 1) == '0') {
                stringBuilder.setLength(stringBuilder.length() - 1);
            }
            stringBuilder.setCharAt(n, '.');
        }
        stringBuilder.append('S');
        return stringBuilder.toString();
    }

    public Duration withNanos(int n) {
        ChronoField.NANO_OF_SECOND.checkValidIntValue(n);
        return Duration.create(this.seconds, n);
    }

    public Duration withSeconds(long l) {
        return Duration.create(l, this.nanos);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.seconds);
        dataOutput.writeInt(this.nanos);
    }

    private static class DurationUnits {
        static final List<TemporalUnit> UNITS = Collections.unmodifiableList(Arrays.asList(ChronoUnit.SECONDS, ChronoUnit.NANOS));

        private DurationUnits() {
        }
    }

}


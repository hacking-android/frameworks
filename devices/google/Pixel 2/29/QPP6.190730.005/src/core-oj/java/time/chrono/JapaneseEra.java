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
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.chrono.Ser;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.LocalGregorianCalendar;

public final class JapaneseEra
implements Era,
Serializable {
    static final sun.util.calendar.Era[] ERA_CONFIG;
    static final int ERA_OFFSET = 2;
    public static final JapaneseEra HEISEI;
    private static final JapaneseEra[] KNOWN_ERAS;
    public static final JapaneseEra MEIJI;
    private static final int N_ERA_CONSTANTS;
    private static final JapaneseEra REIWA;
    public static final JapaneseEra SHOWA;
    public static final JapaneseEra TAISHO;
    private static final long serialVersionUID = 1466499369062886794L;
    private final transient int eraValue;
    private final transient LocalDate since;

    static {
        MEIJI = new JapaneseEra(-1, LocalDate.of(1868, 1, 1));
        TAISHO = new JapaneseEra(0, LocalDate.of(1912, 7, 30));
        SHOWA = new JapaneseEra(1, LocalDate.of(1926, 12, 25));
        HEISEI = new JapaneseEra(2, LocalDate.of(1989, 1, 8));
        REIWA = new JapaneseEra(3, LocalDate.of(2019, 5, 1));
        N_ERA_CONSTANTS = REIWA.getValue() + 2;
        ERA_CONFIG = JapaneseChronology.JCAL.getEras();
        KNOWN_ERAS = new JapaneseEra[ERA_CONFIG.length];
        Object object = KNOWN_ERAS;
        object[0] = MEIJI;
        object[1] = TAISHO;
        object[2] = SHOWA;
        object[3] = HEISEI;
        object[4] = REIWA;
        for (int i = JapaneseEra.N_ERA_CONSTANTS; i < ((Object[])(object = ERA_CONFIG)).length; ++i) {
            object = ((sun.util.calendar.Era)object[i]).getSinceDate();
            object = LocalDate.of(((CalendarDate)object).getYear(), ((CalendarDate)object).getMonth(), ((CalendarDate)object).getDayOfMonth());
            JapaneseEra.KNOWN_ERAS[i] = new JapaneseEra(i - 2 + 1, (LocalDate)object);
        }
    }

    private JapaneseEra(int n, LocalDate localDate) {
        this.eraValue = n;
        this.since = localDate;
    }

    static JapaneseEra from(LocalDate localDate) {
        if (!localDate.isBefore(JapaneseDate.MEIJI_6_ISODATE)) {
            for (int i = JapaneseEra.KNOWN_ERAS.length - 1; i > 0; --i) {
                JapaneseEra japaneseEra = KNOWN_ERAS[i];
                if (localDate.compareTo(japaneseEra.since) < 0) continue;
                return japaneseEra;
            }
            return null;
        }
        throw new DateTimeException("JapaneseDate before Meiji 6 are not supported");
    }

    public static JapaneseEra of(int n) {
        Object object;
        if (n >= JapaneseEra.MEIJI.eraValue && n + 2 <= ((JapaneseEra[])(object = KNOWN_ERAS)).length) {
            return object[JapaneseEra.ordinal(n)];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid era: ");
        ((StringBuilder)object).append(n);
        throw new DateTimeException(((StringBuilder)object).toString());
    }

    private static int ordinal(int n) {
        return n + 2 - 1;
    }

    static sun.util.calendar.Era privateEraFrom(LocalDate localDate) {
        for (int i = JapaneseEra.KNOWN_ERAS.length - 1; i > 0; --i) {
            if (localDate.compareTo(JapaneseEra.KNOWN_ERAS[i].since) < 0) continue;
            return ERA_CONFIG[i];
        }
        return null;
    }

    static JapaneseEra readExternal(DataInput dataInput) throws IOException {
        return JapaneseEra.of(dataInput.readByte());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    static JapaneseEra toJapaneseEra(sun.util.calendar.Era era) {
        for (int i = JapaneseEra.ERA_CONFIG.length - 1; i >= 0; --i) {
            if (!ERA_CONFIG[i].equals(era)) continue;
            return KNOWN_ERAS[i];
        }
        return null;
    }

    public static JapaneseEra valueOf(String string) {
        Objects.requireNonNull(string, "japaneseEra");
        for (JapaneseEra japaneseEra : KNOWN_ERAS) {
            if (!japaneseEra.getName().equals(string)) continue;
            return japaneseEra;
        }
        throw new IllegalArgumentException("japaneseEra is invalid");
    }

    public static JapaneseEra[] values() {
        JapaneseEra[] arrjapaneseEra = KNOWN_ERAS;
        return Arrays.copyOf(arrjapaneseEra, arrjapaneseEra.length);
    }

    private Object writeReplace() {
        return new Ser(5, this);
    }

    String getAbbreviation() {
        return ERA_CONFIG[JapaneseEra.ordinal(this.getValue())].getAbbreviation();
    }

    @Override
    public String getDisplayName(TextStyle object, Locale object2) {
        if (this.getValue() > N_ERA_CONSTANTS - 2) {
            Objects.requireNonNull(object2, "locale");
            object = object.asNormal() == TextStyle.NARROW ? this.getAbbreviation() : this.getName();
            return object;
        }
        object2 = new DateTimeFormatterBuilder().appendText((TemporalField)ChronoField.ERA, (TextStyle)((Object)object)).toFormatter((Locale)object2).withChronology(JapaneseChronology.INSTANCE);
        object = this == MEIJI ? JapaneseDate.MEIJI_6_ISODATE : this.since;
        return ((DateTimeFormatter)object2).format((TemporalAccessor)object);
    }

    String getName() {
        return ERA_CONFIG[JapaneseEra.ordinal(this.getValue())].getName();
    }

    sun.util.calendar.Era getPrivateEra() {
        return ERA_CONFIG[JapaneseEra.ordinal(this.eraValue)];
    }

    @Override
    public int getValue() {
        return this.eraValue;
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.ERA) {
            return JapaneseChronology.INSTANCE.range(ChronoField.ERA);
        }
        return Era.super.range(temporalField);
    }

    public String toString() {
        return this.getName();
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.getValue());
    }
}


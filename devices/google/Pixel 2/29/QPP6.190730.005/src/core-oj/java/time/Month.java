/*
 * Decompiled with CFR 0.145.
 */
package java.time;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;

public enum Month implements TemporalAccessor,
TemporalAdjuster
{
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;
    
    private static final Month[] ENUMS;

    static {
        ENUMS = Month.values();
    }

    public static Month from(TemporalAccessor object) {
        TemporalAccessor temporalAccessor;
        TemporalAccessor temporalAccessor2;
        block5 : {
            if (object instanceof Month) {
                return (Month)object;
            }
            temporalAccessor2 = object;
            temporalAccessor = object;
            try {
                if (IsoChronology.INSTANCE.equals(Chronology.from((TemporalAccessor)object))) break block5;
                temporalAccessor = object;
            }
            catch (DateTimeException dateTimeException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to obtain Month from TemporalAccessor: ");
                ((StringBuilder)object).append(temporalAccessor);
                ((StringBuilder)object).append(" of type ");
                ((StringBuilder)object).append(temporalAccessor.getClass().getName());
                throw new DateTimeException(((StringBuilder)object).toString(), dateTimeException);
            }
            temporalAccessor2 = LocalDate.from((TemporalAccessor)object);
        }
        temporalAccessor = temporalAccessor2;
        object = Month.of(temporalAccessor2.get(ChronoField.MONTH_OF_YEAR));
        return object;
    }

    public static Month of(int n) {
        if (n >= 1 && n <= 12) {
            return ENUMS[n - 1];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid value for MonthOfYear: ");
        stringBuilder.append(n);
        throw new DateTimeException(stringBuilder.toString());
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        if (Chronology.from(temporal).equals(IsoChronology.INSTANCE)) {
            return temporal.with(ChronoField.MONTH_OF_YEAR, this.getValue());
        }
        throw new DateTimeException("Adjustment only supported on ISO date-time");
    }

    public int firstDayOfYear(boolean bl) {
        switch (this) {
            default: {
                return bl + 335;
            }
            case OCTOBER: {
                return bl + 274;
            }
            case AUGUST: {
                return bl + 213;
            }
            case JULY: {
                return bl + 182;
            }
            case MAY: {
                return bl + 121;
            }
            case MARCH: {
                return bl + 60;
            }
            case JANUARY: {
                return 1;
            }
            case NOVEMBER: {
                return bl + 305;
            }
            case SEPTEMBER: {
                return bl + 244;
            }
            case JUNE: {
                return bl + 152;
            }
            case APRIL: {
                return bl + 91;
            }
            case FEBRUARY: 
        }
        return 32;
    }

    public Month firstMonthOfQuarter() {
        return ENUMS[this.ordinal() / 3 * 3];
    }

    @Override
    public int get(TemporalField temporalField) {
        if (temporalField == ChronoField.MONTH_OF_YEAR) {
            return this.getValue();
        }
        return TemporalAccessor.super.get(temporalField);
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        return new DateTimeFormatterBuilder().appendText((TemporalField)ChronoField.MONTH_OF_YEAR, textStyle).toFormatter(locale).format(this);
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField == ChronoField.MONTH_OF_YEAR) {
            return this.getValue();
        }
        if (!(temporalField instanceof ChronoField)) {
            return temporalField.getFrom(this);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported field: ");
        stringBuilder.append(temporalField);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    public int getValue() {
        return this.ordinal() + 1;
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if (temporalField != ChronoField.MONTH_OF_YEAR) {
                bl3 = false;
            }
            return bl3;
        }
        bl3 = temporalField != null && temporalField.isSupportedBy(this) ? bl2 : false;
        return bl3;
    }

    public int length(boolean bl) {
        int n = 1.$SwitchMap$java$time$Month[this.ordinal()];
        if (n != 1) {
            if (n != 2 && n != 3 && n != 4 && n != 5) {
                return 31;
            }
            return 30;
        }
        n = bl ? 29 : 28;
        return n;
    }

    public int maxLength() {
        int n = 1.$SwitchMap$java$time$Month[this.ordinal()];
        if (n != 1) {
            if (n != 2 && n != 3 && n != 4 && n != 5) {
                return 31;
            }
            return 30;
        }
        return 29;
    }

    public int minLength() {
        int n = 1.$SwitchMap$java$time$Month[this.ordinal()];
        if (n != 1) {
            if (n != 2 && n != 3 && n != 4 && n != 5) {
                return 31;
            }
            return 30;
        }
        return 28;
    }

    public Month minus(long l) {
        return this.plus(-(l % 12L));
    }

    public Month plus(long l) {
        int n = (int)(l % 12L);
        return ENUMS[(this.ordinal() + (n + 12)) % 12];
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.chronology()) {
            return (R)IsoChronology.INSTANCE;
        }
        if (temporalQuery == TemporalQueries.precision()) {
            return (R)ChronoUnit.MONTHS;
        }
        return TemporalAccessor.super.query(temporalQuery);
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.MONTH_OF_YEAR) {
            return temporalField.range();
        }
        return TemporalAccessor.super.range(temporalField);
    }

}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.format.-$
 *  java.time.format.-$$Lambda
 *  java.time.format.-$$Lambda$DateTimeFormatter
 *  java.time.format.-$$Lambda$DateTimeFormatter$GhpE1dbCMFpBqvhZZgrqVYpzk8E
 *  java.time.format.-$$Lambda$DateTimeFormatter$QqeEAMXK7Qf5gsmaSCLmrVwQ1Ns
 */
package java.time.format;

import java.io.IOException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.DateTimeException;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.-$;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseContext;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimePrintContext;
import java.time.format.DecimalStyle;
import java.time.format.FormatStyle;
import java.time.format.Parsed;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.format._$$Lambda$DateTimeFormatter$GhpE1dbCMFpBqvhZZgrqVYpzk8E;
import java.time.format._$$Lambda$DateTimeFormatter$QqeEAMXK7Qf5gsmaSCLmrVwQ1Ns;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class DateTimeFormatter {
    public static final DateTimeFormatter BASIC_ISO_DATE;
    public static final DateTimeFormatter ISO_DATE;
    public static final DateTimeFormatter ISO_DATE_TIME;
    public static final DateTimeFormatter ISO_INSTANT;
    public static final DateTimeFormatter ISO_LOCAL_DATE;
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter ISO_LOCAL_TIME;
    public static final DateTimeFormatter ISO_OFFSET_DATE;
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME;
    public static final DateTimeFormatter ISO_OFFSET_TIME;
    public static final DateTimeFormatter ISO_ORDINAL_DATE;
    public static final DateTimeFormatter ISO_TIME;
    public static final DateTimeFormatter ISO_WEEK_DATE;
    public static final DateTimeFormatter ISO_ZONED_DATE_TIME;
    private static final TemporalQuery<Period> PARSED_EXCESS_DAYS;
    private static final TemporalQuery<Boolean> PARSED_LEAP_SECOND;
    public static final DateTimeFormatter RFC_1123_DATE_TIME;
    private final Chronology chrono;
    private final DecimalStyle decimalStyle;
    private final Locale locale;
    private final DateTimeFormatterBuilder.CompositePrinterParser printerParser;
    private final Set<TemporalField> resolverFields;
    private final ResolverStyle resolverStyle;
    private final ZoneId zone;

    static {
        ISO_LOCAL_DATE = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_OFFSET_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_DATE).appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_DATE).optionalStart().appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_LOCAL_TIME = new DateTimeFormatterBuilder().appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).optionalStart().appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).toFormatter(ResolverStyle.STRICT, null);
        ISO_OFFSET_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_TIME).appendOffsetId().toFormatter(ResolverStyle.STRICT, null);
        ISO_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_TIME).optionalStart().appendOffsetId().toFormatter(ResolverStyle.STRICT, null);
        ISO_LOCAL_DATE_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_DATE).appendLiteral('T').append(ISO_LOCAL_TIME).toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_OFFSET_DATE_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_DATE_TIME).appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_ZONED_DATE_TIME = new DateTimeFormatterBuilder().append(ISO_OFFSET_DATE_TIME).optionalStart().appendLiteral('[').parseCaseSensitive().appendZoneRegionId().appendLiteral(']').toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_DATE_TIME = new DateTimeFormatterBuilder().append(ISO_LOCAL_DATE_TIME).optionalStart().appendOffsetId().optionalStart().appendLiteral('[').parseCaseSensitive().appendZoneRegionId().appendLiteral(']').toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_ORDINAL_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.DAY_OF_YEAR, 3).optionalStart().appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_WEEK_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().appendValue(IsoFields.WEEK_BASED_YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral("-W").appendValue(IsoFields.WEEK_OF_WEEK_BASED_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_WEEK, 1).optionalStart().appendOffsetId().toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        ISO_INSTANT = new DateTimeFormatterBuilder().parseCaseInsensitive().appendInstant().toFormatter(ResolverStyle.STRICT, null);
        BASIC_ISO_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive().appendValue(ChronoField.YEAR, 4).appendValue(ChronoField.MONTH_OF_YEAR, 2).appendValue(ChronoField.DAY_OF_MONTH, 2).optionalStart().appendOffset("+HHMMss", "Z").toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
        HashMap<Long, String> hashMap = new HashMap<Long, String>();
        Long l = 1L;
        hashMap.put(l, "Mon");
        Long l2 = 2L;
        hashMap.put(l2, "Tue");
        Long l3 = 3L;
        hashMap.put(l3, "Wed");
        Long l4 = 4L;
        hashMap.put(l4, "Thu");
        Long l5 = 5L;
        hashMap.put(l5, "Fri");
        hashMap.put(6L, "Sat");
        hashMap.put(7L, "Sun");
        HashMap<Long, String> hashMap2 = new HashMap<Long, String>();
        hashMap2.put(l, "Jan");
        hashMap2.put(l2, "Feb");
        hashMap2.put(l3, "Mar");
        hashMap2.put(l4, "Apr");
        hashMap2.put(l5, "May");
        hashMap2.put(6L, "Jun");
        hashMap2.put(7L, "Jul");
        hashMap2.put(8L, "Aug");
        hashMap2.put(9L, "Sep");
        hashMap2.put(10L, "Oct");
        hashMap2.put(11L, "Nov");
        hashMap2.put(12L, "Dec");
        RFC_1123_DATE_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive().parseLenient().optionalStart().appendText((TemporalField)ChronoField.DAY_OF_WEEK, hashMap).appendLiteral(", ").optionalEnd().appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE).appendLiteral(' ').appendText((TemporalField)ChronoField.MONTH_OF_YEAR, hashMap2).appendLiteral(' ').appendValue(ChronoField.YEAR, 4).appendLiteral(' ').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).optionalStart().appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).optionalEnd().appendLiteral(' ').appendOffset("+HHMM", "GMT").toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
        PARSED_EXCESS_DAYS = _$$Lambda$DateTimeFormatter$QqeEAMXK7Qf5gsmaSCLmrVwQ1Ns.INSTANCE;
        PARSED_LEAP_SECOND = _$$Lambda$DateTimeFormatter$GhpE1dbCMFpBqvhZZgrqVYpzk8E.INSTANCE;
    }

    DateTimeFormatter(DateTimeFormatterBuilder.CompositePrinterParser compositePrinterParser, Locale locale, DecimalStyle decimalStyle, ResolverStyle resolverStyle, Set<TemporalField> set, Chronology chronology, ZoneId zoneId) {
        this.printerParser = Objects.requireNonNull(compositePrinterParser, "printerParser");
        this.resolverFields = set;
        this.locale = Objects.requireNonNull(locale, "locale");
        this.decimalStyle = Objects.requireNonNull(decimalStyle, "decimalStyle");
        this.resolverStyle = Objects.requireNonNull(resolverStyle, "resolverStyle");
        this.chrono = chronology;
        this.zone = zoneId;
    }

    private DateTimeParseException createError(CharSequence charSequence, RuntimeException runtimeException) {
        CharSequence charSequence2;
        if (charSequence.length() > 64) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(charSequence.subSequence(0, 64).toString());
            ((StringBuilder)charSequence2).append("...");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        } else {
            charSequence2 = charSequence.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Text '");
        stringBuilder.append((String)charSequence2);
        stringBuilder.append("' could not be parsed: ");
        stringBuilder.append(runtimeException.getMessage());
        return new DateTimeParseException(stringBuilder.toString(), charSequence, 0, runtimeException);
    }

    static /* synthetic */ Period lambda$static$0(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Parsed) {
            return ((Parsed)temporalAccessor).excessDays;
        }
        return Period.ZERO;
    }

    static /* synthetic */ Boolean lambda$static$1(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Parsed) {
            return ((Parsed)temporalAccessor).leapSecond;
        }
        return Boolean.FALSE;
    }

    public static DateTimeFormatter ofLocalizedDate(FormatStyle formatStyle) {
        Objects.requireNonNull(formatStyle, "dateStyle");
        return new DateTimeFormatterBuilder().appendLocalized(formatStyle, null).toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    public static DateTimeFormatter ofLocalizedDateTime(FormatStyle formatStyle) {
        Objects.requireNonNull(formatStyle, "dateTimeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(formatStyle, formatStyle).toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    public static DateTimeFormatter ofLocalizedDateTime(FormatStyle formatStyle, FormatStyle formatStyle2) {
        Objects.requireNonNull(formatStyle, "dateStyle");
        Objects.requireNonNull(formatStyle2, "timeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(formatStyle, formatStyle2).toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    public static DateTimeFormatter ofLocalizedTime(FormatStyle formatStyle) {
        Objects.requireNonNull(formatStyle, "timeStyle");
        return new DateTimeFormatterBuilder().appendLocalized(null, formatStyle).toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
    }

    public static DateTimeFormatter ofPattern(String string) {
        return new DateTimeFormatterBuilder().appendPattern(string).toFormatter();
    }

    public static DateTimeFormatter ofPattern(String string, Locale locale) {
        return new DateTimeFormatterBuilder().appendPattern(string).toFormatter(locale);
    }

    private TemporalAccessor parseResolved0(CharSequence charSequence, ParsePosition object) {
        Object object2 = object != null ? object : new ParsePosition(0);
        Object object3 = this.parseUnresolved0(charSequence, (ParsePosition)object2);
        if (object3 != null && ((ParsePosition)object2).getErrorIndex() < 0 && (object != null || ((ParsePosition)object2).getIndex() >= charSequence.length())) {
            return ((DateTimeParseContext)object3).toResolved(this.resolverStyle, this.resolverFields);
        }
        if (charSequence.length() > 64) {
            object = new StringBuilder();
            ((StringBuilder)object).append(charSequence.subSequence(0, 64).toString());
            ((StringBuilder)object).append("...");
            object = ((StringBuilder)object).toString();
        } else {
            object = charSequence.toString();
        }
        if (((ParsePosition)object2).getErrorIndex() >= 0) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Text '");
            ((StringBuilder)object3).append((String)object);
            ((StringBuilder)object3).append("' could not be parsed at index ");
            ((StringBuilder)object3).append(((ParsePosition)object2).getErrorIndex());
            throw new DateTimeParseException(((StringBuilder)object3).toString(), charSequence, ((ParsePosition)object2).getErrorIndex());
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("Text '");
        ((StringBuilder)object3).append((String)object);
        ((StringBuilder)object3).append("' could not be parsed, unparsed text found at index ");
        ((StringBuilder)object3).append(((ParsePosition)object2).getIndex());
        throw new DateTimeParseException(((StringBuilder)object3).toString(), charSequence, ((ParsePosition)object2).getIndex());
    }

    private DateTimeParseContext parseUnresolved0(CharSequence charSequence, ParsePosition parsePosition) {
        Objects.requireNonNull(charSequence, "text");
        Objects.requireNonNull(parsePosition, "position");
        DateTimeParseContext dateTimeParseContext = new DateTimeParseContext(this);
        int n = parsePosition.getIndex();
        n = this.printerParser.parse(dateTimeParseContext, charSequence, n);
        if (n < 0) {
            parsePosition.setErrorIndex(n);
            return null;
        }
        parsePosition.setIndex(n);
        return dateTimeParseContext;
    }

    public static final TemporalQuery<Period> parsedExcessDays() {
        return PARSED_EXCESS_DAYS;
    }

    public static final TemporalQuery<Boolean> parsedLeapSecond() {
        return PARSED_LEAP_SECOND;
    }

    public String format(TemporalAccessor temporalAccessor) {
        StringBuilder stringBuilder = new StringBuilder(32);
        this.formatTo(temporalAccessor, stringBuilder);
        return stringBuilder.toString();
    }

    public void formatTo(TemporalAccessor object, Appendable appendable) {
        Objects.requireNonNull(object, "temporal");
        Objects.requireNonNull(appendable, "appendable");
        try {
            DateTimePrintContext dateTimePrintContext = new DateTimePrintContext((TemporalAccessor)object, this);
            if (appendable instanceof StringBuilder) {
                this.printerParser.format(dateTimePrintContext, (StringBuilder)appendable);
            } else {
                object = new StringBuilder(32);
                this.printerParser.format(dateTimePrintContext, (StringBuilder)object);
                appendable.append((CharSequence)object);
            }
            return;
        }
        catch (IOException iOException) {
            throw new DateTimeException(iOException.getMessage(), iOException);
        }
    }

    public Chronology getChronology() {
        return this.chrono;
    }

    public DecimalStyle getDecimalStyle() {
        return this.decimalStyle;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public Set<TemporalField> getResolverFields() {
        return this.resolverFields;
    }

    public ResolverStyle getResolverStyle() {
        return this.resolverStyle;
    }

    public ZoneId getZone() {
        return this.zone;
    }

    public <T> T parse(CharSequence charSequence, TemporalQuery<T> temporalQuery) {
        Objects.requireNonNull(charSequence, "text");
        Objects.requireNonNull(temporalQuery, "query");
        try {
            temporalQuery = this.parseResolved0(charSequence, null).query(temporalQuery);
        }
        catch (RuntimeException runtimeException) {
            throw this.createError(charSequence, runtimeException);
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw dateTimeParseException;
        }
        return (T)temporalQuery;
    }

    public TemporalAccessor parse(CharSequence charSequence) {
        Objects.requireNonNull(charSequence, "text");
        try {
            TemporalAccessor temporalAccessor = this.parseResolved0(charSequence, null);
            return temporalAccessor;
        }
        catch (RuntimeException runtimeException) {
            throw this.createError(charSequence, runtimeException);
        }
        catch (DateTimeParseException dateTimeParseException) {
            throw dateTimeParseException;
        }
    }

    public TemporalAccessor parse(CharSequence charSequence, ParsePosition object) {
        Objects.requireNonNull(charSequence, "text");
        Objects.requireNonNull(object, "position");
        try {
            object = this.parseResolved0(charSequence, (ParsePosition)object);
            return object;
        }
        catch (RuntimeException runtimeException) {
            throw this.createError(charSequence, runtimeException);
        }
        catch (IndexOutOfBoundsException | DateTimeParseException runtimeException) {
            throw runtimeException;
        }
    }

    public TemporalAccessor parseBest(CharSequence charSequence, TemporalQuery<?> ... object) {
        Objects.requireNonNull(charSequence, "text");
        Objects.requireNonNull(object, "queries");
        if (((Object)object).length >= 2) {
            TemporalAccessor temporalAccessor = this.parseResolved0(charSequence, null);
            for (Object object2 : object) {
                try {
                    TemporalAccessor temporalAccessor2 = (TemporalAccessor)temporalAccessor.query(object2);
                    return temporalAccessor2;
                }
                catch (RuntimeException object22) {
                }
            }
            try {
                DateTimeException runtimeException = new DateTimeException("Unable to convert parsed text using any of the specified queries");
                throw runtimeException;
            }
            catch (RuntimeException runtimeException) {
                throw this.createError(charSequence, runtimeException);
            }
            catch (DateTimeParseException dateTimeParseException) {
                throw dateTimeParseException;
            }
        }
        throw new IllegalArgumentException("At least two queries must be specified");
    }

    public TemporalAccessor parseUnresolved(CharSequence object, ParsePosition parsePosition) {
        if ((object = this.parseUnresolved0((CharSequence)object, parsePosition)) == null) {
            return null;
        }
        return ((DateTimeParseContext)object).toUnresolved();
    }

    public Format toFormat() {
        return new ClassicFormat(this, null);
    }

    public Format toFormat(TemporalQuery<?> temporalQuery) {
        Objects.requireNonNull(temporalQuery, "parseQuery");
        return new ClassicFormat(this, temporalQuery);
    }

    DateTimeFormatterBuilder.CompositePrinterParser toPrinterParser(boolean bl) {
        return this.printerParser.withOptional(bl);
    }

    public String toString() {
        String string = this.printerParser.toString();
        if (!string.startsWith("[")) {
            string = string.substring(1, string.length() - 1);
        }
        return string;
    }

    public DateTimeFormatter withChronology(Chronology chronology) {
        if (Objects.equals(this.chrono, chronology)) {
            return this;
        }
        return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, this.resolverFields, chronology, this.zone);
    }

    public DateTimeFormatter withDecimalStyle(DecimalStyle decimalStyle) {
        if (this.decimalStyle.equals(decimalStyle)) {
            return this;
        }
        return new DateTimeFormatter(this.printerParser, this.locale, decimalStyle, this.resolverStyle, this.resolverFields, this.chrono, this.zone);
    }

    public DateTimeFormatter withLocale(Locale locale) {
        if (this.locale.equals(locale)) {
            return this;
        }
        return new DateTimeFormatter(this.printerParser, locale, this.decimalStyle, this.resolverStyle, this.resolverFields, this.chrono, this.zone);
    }

    public DateTimeFormatter withResolverFields(Set<TemporalField> set) {
        if (Objects.equals(this.resolverFields, set)) {
            return this;
        }
        Set<TemporalField> set2 = set;
        if (set != null) {
            set2 = Collections.unmodifiableSet(new HashSet<TemporalField>(set));
        }
        return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, set2, this.chrono, this.zone);
    }

    public DateTimeFormatter withResolverFields(TemporalField ... arrtemporalField) {
        Set<TemporalField> set = null;
        if (arrtemporalField != null) {
            set = Collections.unmodifiableSet(new HashSet<TemporalField>(Arrays.asList(arrtemporalField)));
        }
        if (Objects.equals(this.resolverFields, set)) {
            return this;
        }
        return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, set, this.chrono, this.zone);
    }

    public DateTimeFormatter withResolverStyle(ResolverStyle resolverStyle) {
        Objects.requireNonNull(resolverStyle, "resolverStyle");
        if (Objects.equals((Object)this.resolverStyle, (Object)resolverStyle)) {
            return this;
        }
        return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, resolverStyle, this.resolverFields, this.chrono, this.zone);
    }

    public DateTimeFormatter withZone(ZoneId zoneId) {
        if (Objects.equals(this.zone, zoneId)) {
            return this;
        }
        return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, this.resolverFields, this.chrono, zoneId);
    }

    static class ClassicFormat
    extends Format {
        private final DateTimeFormatter formatter;
        private final TemporalQuery<?> parseType;

        public ClassicFormat(DateTimeFormatter dateTimeFormatter, TemporalQuery<?> temporalQuery) {
            this.formatter = dateTimeFormatter;
            this.parseType = temporalQuery;
        }

        @Override
        public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            Objects.requireNonNull(object, "obj");
            Objects.requireNonNull(stringBuffer, "toAppendTo");
            Objects.requireNonNull(fieldPosition, "pos");
            if (object instanceof TemporalAccessor) {
                fieldPosition.setBeginIndex(0);
                fieldPosition.setEndIndex(0);
                try {
                    this.formatter.formatTo((TemporalAccessor)object, stringBuffer);
                    return stringBuffer;
                }
                catch (RuntimeException runtimeException) {
                    throw new IllegalArgumentException(runtimeException.getMessage(), runtimeException);
                }
            }
            throw new IllegalArgumentException("Format target must implement TemporalAccessor");
        }

        @Override
        public Object parseObject(String string) throws ParseException {
            Objects.requireNonNull(string, "text");
            try {
                if (this.parseType == null) {
                    return this.formatter.parseResolved0(string, null);
                }
                string = this.formatter.parse((CharSequence)string, this.parseType);
                return string;
            }
            catch (RuntimeException runtimeException) {
                throw (ParseException)new ParseException(runtimeException.getMessage(), 0).initCause(runtimeException);
            }
            catch (DateTimeParseException dateTimeParseException) {
                throw new ParseException(dateTimeParseException.getMessage(), dateTimeParseException.getErrorIndex());
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Object parseObject(String object, ParsePosition parsePosition) {
            block4 : {
                Objects.requireNonNull(object, "text");
                try {
                    object = this.formatter.parseUnresolved0((CharSequence)object, parsePosition);
                    if (object != null) break block4;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    if (parsePosition.getErrorIndex() >= 0) return null;
                    parsePosition.setErrorIndex(0);
                    return null;
                }
                if (parsePosition.getErrorIndex() >= 0) return null;
                parsePosition.setErrorIndex(0);
                return null;
            }
            try {
                object = ((DateTimeParseContext)object).toResolved(this.formatter.resolverStyle, this.formatter.resolverFields);
                if (this.parseType != null) return object.query(this.parseType);
                return object;
            }
            catch (RuntimeException runtimeException) {
                parsePosition.setErrorIndex(0);
                return null;
            }
        }
    }

}


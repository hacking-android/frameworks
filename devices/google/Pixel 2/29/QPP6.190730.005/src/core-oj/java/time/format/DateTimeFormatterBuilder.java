/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.impl.ZoneMeta
 *  android.icu.text.LocaleDisplayNames
 *  android.icu.text.TimeZoneNames
 *  android.icu.text.TimeZoneNames$NameType
 *  android.icu.util.Calendar
 *  android.icu.util.ULocale
 *  java.time.format.-$
 *  java.time.format.-$$Lambda
 *  java.time.format.-$$Lambda$DateTimeFormatterBuilder
 *  java.time.format.-$$Lambda$DateTimeFormatterBuilder$M-GACNxm6552EiylPRPw4dyNXKo
 */
package java.time.format;

import android.icu.impl.ZoneMeta;
import android.icu.text.LocaleDisplayNames;
import android.icu.text.TimeZoneNames;
import android.icu.util.Calendar;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParsePosition;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.-$;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseContext;
import java.time.format.DateTimePrintContext;
import java.time.format.DateTimeTextProvider;
import java.time.format.DecimalStyle;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.time.format.TextStyle;
import java.time.format.ZoneName;
import java.time.format._$$Lambda$DateTimeFormatterBuilder$M_GACNxm6552EiylPRPw4dyNXKo;
import java.time.format._$$Lambda$DateTimeFormatterBuilder$ReducedPrinterParser$O7fxxUm4cHduGbldToNj0T92oIo;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.ValueRange;
import java.time.temporal.WeekFields;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public final class DateTimeFormatterBuilder {
    private static final Map<Character, TemporalField> FIELD_MAP;
    static final Comparator<String> LENGTH_SORT;
    private static final TemporalQuery<ZoneId> QUERY_REGION_ONLY;
    private DateTimeFormatterBuilder active = this;
    private final boolean optional;
    private char padNextChar;
    private int padNextWidth;
    private final DateTimeFormatterBuilder parent;
    private final List<DateTimePrinterParser> printerParsers = new ArrayList<DateTimePrinterParser>();
    private int valueParserIndex = -1;

    static {
        QUERY_REGION_ONLY = _$$Lambda$DateTimeFormatterBuilder$M_GACNxm6552EiylPRPw4dyNXKo.INSTANCE;
        FIELD_MAP = new HashMap<Character, TemporalField>();
        FIELD_MAP.put(Character.valueOf('G'), ChronoField.ERA);
        FIELD_MAP.put(Character.valueOf('y'), ChronoField.YEAR_OF_ERA);
        FIELD_MAP.put(Character.valueOf('u'), ChronoField.YEAR);
        FIELD_MAP.put(Character.valueOf('Q'), IsoFields.QUARTER_OF_YEAR);
        FIELD_MAP.put(Character.valueOf('q'), IsoFields.QUARTER_OF_YEAR);
        FIELD_MAP.put(Character.valueOf('M'), ChronoField.MONTH_OF_YEAR);
        FIELD_MAP.put(Character.valueOf('L'), ChronoField.MONTH_OF_YEAR);
        FIELD_MAP.put(Character.valueOf('D'), ChronoField.DAY_OF_YEAR);
        FIELD_MAP.put(Character.valueOf('d'), ChronoField.DAY_OF_MONTH);
        FIELD_MAP.put(Character.valueOf('F'), ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        FIELD_MAP.put(Character.valueOf('E'), ChronoField.DAY_OF_WEEK);
        FIELD_MAP.put(Character.valueOf('c'), ChronoField.DAY_OF_WEEK);
        FIELD_MAP.put(Character.valueOf('e'), ChronoField.DAY_OF_WEEK);
        FIELD_MAP.put(Character.valueOf('a'), ChronoField.AMPM_OF_DAY);
        FIELD_MAP.put(Character.valueOf('H'), ChronoField.HOUR_OF_DAY);
        FIELD_MAP.put(Character.valueOf('k'), ChronoField.CLOCK_HOUR_OF_DAY);
        FIELD_MAP.put(Character.valueOf('K'), ChronoField.HOUR_OF_AMPM);
        FIELD_MAP.put(Character.valueOf('h'), ChronoField.CLOCK_HOUR_OF_AMPM);
        FIELD_MAP.put(Character.valueOf('m'), ChronoField.MINUTE_OF_HOUR);
        FIELD_MAP.put(Character.valueOf('s'), ChronoField.SECOND_OF_MINUTE);
        FIELD_MAP.put(Character.valueOf('S'), ChronoField.NANO_OF_SECOND);
        FIELD_MAP.put(Character.valueOf('A'), ChronoField.MILLI_OF_DAY);
        FIELD_MAP.put(Character.valueOf('n'), ChronoField.NANO_OF_SECOND);
        FIELD_MAP.put(Character.valueOf('N'), ChronoField.NANO_OF_DAY);
        LENGTH_SORT = new Comparator<String>(){

            @Override
            public int compare(String string, String string2) {
                int n = string.length() == string2.length() ? string.compareTo(string2) : string.length() - string2.length();
                return n;
            }
        };
    }

    public DateTimeFormatterBuilder() {
        this.parent = null;
        this.optional = false;
    }

    private DateTimeFormatterBuilder(DateTimeFormatterBuilder dateTimeFormatterBuilder, boolean bl) {
        this.parent = dateTimeFormatterBuilder;
        this.optional = bl;
    }

    private int appendInternal(DateTimePrinterParser object) {
        Objects.requireNonNull(object, "pp");
        DateTimeFormatterBuilder dateTimeFormatterBuilder = this.active;
        int n = dateTimeFormatterBuilder.padNextWidth;
        DateTimePrinterParser dateTimePrinterParser = object;
        if (n > 0) {
            dateTimePrinterParser = object;
            if (object != null) {
                dateTimePrinterParser = new PadPrinterParserDecorator((DateTimePrinterParser)object, n, dateTimeFormatterBuilder.padNextChar);
            }
            object = this.active;
            ((DateTimeFormatterBuilder)object).padNextWidth = 0;
            ((DateTimeFormatterBuilder)object).padNextChar = (char)(false ? 1 : 0);
        }
        this.active.printerParsers.add(dateTimePrinterParser);
        object = this.active;
        ((DateTimeFormatterBuilder)object).valueParserIndex = -1;
        return ((DateTimeFormatterBuilder)object).printerParsers.size() - 1;
    }

    private DateTimeFormatterBuilder appendValue(NumberPrinterParser object) {
        Object object2 = this.active;
        if (((DateTimeFormatterBuilder)object2).valueParserIndex >= 0) {
            int n = ((DateTimeFormatterBuilder)object2).valueParserIndex;
            object2 = (NumberPrinterParser)((DateTimeFormatterBuilder)object2).printerParsers.get(n);
            if (((NumberPrinterParser)object).minWidth == ((NumberPrinterParser)object).maxWidth && ((NumberPrinterParser)object).signStyle == SignStyle.NOT_NEGATIVE) {
                object2 = ((NumberPrinterParser)object2).withSubsequentWidth(((NumberPrinterParser)object).maxWidth);
                this.appendInternal(((NumberPrinterParser)object).withFixedWidth());
                this.active.valueParserIndex = n;
                object = object2;
            } else {
                object2 = ((NumberPrinterParser)object2).withFixedWidth();
                this.active.valueParserIndex = this.appendInternal((DateTimePrinterParser)object);
                object = object2;
            }
            this.active.printerParsers.set(n, (DateTimePrinterParser)object);
        } else {
            ((DateTimeFormatterBuilder)object2).valueParserIndex = this.appendInternal((DateTimePrinterParser)object);
        }
        return this;
    }

    private static int convertStyle(FormatStyle formatStyle) {
        if (formatStyle == null) {
            return -1;
        }
        return formatStyle.ordinal();
    }

    public static String getLocalizedDateTimePattern(FormatStyle formatStyle, FormatStyle formatStyle2, Chronology chronology, Locale locale) {
        Objects.requireNonNull(locale, "locale");
        Objects.requireNonNull(chronology, "chrono");
        if (formatStyle == null && formatStyle2 == null) {
            throw new IllegalArgumentException("Either dateStyle or timeStyle must be non-null");
        }
        return Calendar.getDateTimeFormatString((ULocale)ULocale.forLocale((Locale)locale), (String)chronology.getCalendarType(), (int)DateTimeFormatterBuilder.convertStyle(formatStyle), (int)DateTimeFormatterBuilder.convertStyle(formatStyle2));
    }

    static /* synthetic */ ZoneId lambda$static$0(TemporalAccessor object) {
        if ((object = object.query(TemporalQueries.zoneId())) == null || object instanceof ZoneOffset) {
            object = null;
        }
        return object;
    }

    /*
     * Exception decompiling
     */
    private void parseField(char var1_1, int var2_2, TemporalField var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void parsePattern(String charSequence) {
        for (int i = 0; i < ((String)charSequence).length(); ++i) {
            Object object;
            int n;
            int n2;
            char c;
            block27 : {
                char c2;
                block52 : {
                    int n3;
                    block32 : {
                        block50 : {
                            block51 : {
                                block48 : {
                                    block49 : {
                                        block46 : {
                                            block47 : {
                                                block44 : {
                                                    block45 : {
                                                        block41 : {
                                                            block43 : {
                                                                block42 : {
                                                                    block37 : {
                                                                        block40 : {
                                                                            block39 : {
                                                                                block38 : {
                                                                                    block35 : {
                                                                                        block36 : {
                                                                                            block33 : {
                                                                                                block34 : {
                                                                                                    block31 : {
                                                                                                        block28 : {
                                                                                                            int n4;
                                                                                                            char c3;
                                                                                                            block29 : {
                                                                                                                char c4;
                                                                                                                block30 : {
                                                                                                                    c = ((String)charSequence).charAt(i);
                                                                                                                    if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) break block27;
                                                                                                                    for (n2 = i + 1; n2 < ((String)charSequence).length() && ((String)charSequence).charAt(n2) == c; ++n2) {
                                                                                                                    }
                                                                                                                    n = n2 - i;
                                                                                                                    c2 = c;
                                                                                                                    n3 = n2;
                                                                                                                    i = n;
                                                                                                                    if (c != 'p') break block28;
                                                                                                                    int n5 = 0;
                                                                                                                    c3 = c;
                                                                                                                    n3 = n2;
                                                                                                                    i = n;
                                                                                                                    n4 = n5;
                                                                                                                    if (n2 >= ((String)charSequence).length()) break block29;
                                                                                                                    c4 = ((String)charSequence).charAt(n2);
                                                                                                                    if (c4 >= 'A' && c4 <= 'Z') break block30;
                                                                                                                    c3 = c4;
                                                                                                                    n3 = n2;
                                                                                                                    i = n;
                                                                                                                    n4 = n5;
                                                                                                                    if (c4 < 'a') break block29;
                                                                                                                    c3 = c4;
                                                                                                                    n3 = n2;
                                                                                                                    i = n;
                                                                                                                    n4 = n5;
                                                                                                                    if (c4 > 'z') break block29;
                                                                                                                }
                                                                                                                for (i = n2 + 1; i < ((String)charSequence).length() && ((String)charSequence).charAt(i) == c4; ++i) {
                                                                                                                }
                                                                                                                n3 = i;
                                                                                                                i -= n2;
                                                                                                                n4 = n;
                                                                                                                c3 = c4;
                                                                                                            }
                                                                                                            if (n4 != 0) {
                                                                                                                this.padNext(n4);
                                                                                                                c2 = c3;
                                                                                                            } else {
                                                                                                                object = new StringBuilder();
                                                                                                                object.append("Pad letter 'p' must be followed by valid pad pattern: ");
                                                                                                                object.append((String)charSequence);
                                                                                                                throw new IllegalArgumentException(object.toString());
                                                                                                            }
                                                                                                        }
                                                                                                        if ((object = FIELD_MAP.get(Character.valueOf(c2))) == null) break block31;
                                                                                                        this.parseField(c2, i, (TemporalField)object);
                                                                                                        break block32;
                                                                                                    }
                                                                                                    if (c2 != 'z') break block33;
                                                                                                    if (i > 4) break block34;
                                                                                                    if (i == 4) {
                                                                                                        this.appendZoneText(TextStyle.FULL);
                                                                                                    } else {
                                                                                                        this.appendZoneText(TextStyle.SHORT);
                                                                                                    }
                                                                                                    break block32;
                                                                                                }
                                                                                                charSequence = new StringBuilder();
                                                                                                ((StringBuilder)charSequence).append("Too many pattern letters: ");
                                                                                                ((StringBuilder)charSequence).append(c2);
                                                                                                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                                                                            }
                                                                                            if (c2 != 'V') break block35;
                                                                                            if (i != 2) break block36;
                                                                                            this.appendZoneId();
                                                                                            break block32;
                                                                                        }
                                                                                        charSequence = new StringBuilder();
                                                                                        ((StringBuilder)charSequence).append("Pattern letter count must be 2: ");
                                                                                        ((StringBuilder)charSequence).append(c2);
                                                                                        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                                                                    }
                                                                                    object = "+0000";
                                                                                    if (c2 != 'Z') break block37;
                                                                                    if (i >= 4) break block38;
                                                                                    this.appendOffset("+HHMM", "+0000");
                                                                                    break block32;
                                                                                }
                                                                                if (i != 4) break block39;
                                                                                this.appendLocalizedOffset(TextStyle.FULL);
                                                                                break block32;
                                                                            }
                                                                            if (i != 5) break block40;
                                                                            this.appendOffset("+HH:MM:ss", "Z");
                                                                            break block32;
                                                                        }
                                                                        charSequence = new StringBuilder();
                                                                        ((StringBuilder)charSequence).append("Too many pattern letters: ");
                                                                        ((StringBuilder)charSequence).append(c2);
                                                                        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                                                    }
                                                                    if (c2 != 'O') break block41;
                                                                    if (i != 1) break block42;
                                                                    this.appendLocalizedOffset(TextStyle.SHORT);
                                                                    break block32;
                                                                }
                                                                if (i != 4) break block43;
                                                                this.appendLocalizedOffset(TextStyle.FULL);
                                                                break block32;
                                                            }
                                                            charSequence = new StringBuilder();
                                                            ((StringBuilder)charSequence).append("Pattern letter count must be 1 or 4: ");
                                                            ((StringBuilder)charSequence).append(c2);
                                                            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                                        }
                                                        n = 0;
                                                        n2 = 0;
                                                        if (c2 != 'X') break block44;
                                                        if (i > 5) break block45;
                                                        object = OffsetIdPrinterParser.PATTERNS;
                                                        if (i != 1) {
                                                            n2 = 1;
                                                        }
                                                        this.appendOffset(object[n2 + i], "Z");
                                                        break block32;
                                                    }
                                                    charSequence = new StringBuilder();
                                                    ((StringBuilder)charSequence).append("Too many pattern letters: ");
                                                    ((StringBuilder)charSequence).append(c2);
                                                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                                }
                                                if (c2 != 'x') break block46;
                                                if (i > 5) break block47;
                                                if (i == 1) {
                                                    object = "+00";
                                                } else if (i % 2 != 0) {
                                                    object = "+00:00";
                                                }
                                                String[] arrstring = OffsetIdPrinterParser.PATTERNS;
                                                n2 = i == 1 ? n : 1;
                                                this.appendOffset(arrstring[n2 + i], (String)object);
                                                break block32;
                                            }
                                            charSequence = new StringBuilder();
                                            ((StringBuilder)charSequence).append("Too many pattern letters: ");
                                            ((StringBuilder)charSequence).append(c2);
                                            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                        }
                                        if (c2 != 'W') break block48;
                                        if (i > 1) break block49;
                                        this.appendInternal(new WeekBasedFieldPrinterParser(c2, i));
                                        break block32;
                                    }
                                    charSequence = new StringBuilder();
                                    ((StringBuilder)charSequence).append("Too many pattern letters: ");
                                    ((StringBuilder)charSequence).append(c2);
                                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                }
                                if (c2 != 'w') break block50;
                                if (i > 2) break block51;
                                this.appendInternal(new WeekBasedFieldPrinterParser(c2, i));
                                break block32;
                            }
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Too many pattern letters: ");
                            ((StringBuilder)charSequence).append(c2);
                            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                        }
                        if (c2 != 'Y') break block52;
                        this.appendInternal(new WeekBasedFieldPrinterParser(c2, i));
                    }
                    i = n3 - 1;
                    continue;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown pattern letter: ");
                ((StringBuilder)charSequence).append(c2);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            if (c == '\'') {
                n2 = i + 1;
                while (n2 < ((String)charSequence).length()) {
                    n = n2;
                    if (((String)charSequence).charAt(n2) == '\'') {
                        if (n2 + 1 >= ((String)charSequence).length() || ((String)charSequence).charAt(n2 + 1) != '\'') break;
                        n = n2 + 1;
                    }
                    n2 = n + 1;
                }
                if (n2 < ((String)charSequence).length()) {
                    object = ((String)charSequence).substring(i + 1, n2);
                    if (object.length() == 0) {
                        this.appendLiteral('\'');
                    } else {
                        this.appendLiteral(object.replace("''", "'"));
                    }
                    i = n2;
                    continue;
                }
                object = new StringBuilder();
                object.append("Pattern ends with an incomplete string literal: ");
                object.append((String)charSequence);
                throw new IllegalArgumentException(object.toString());
            }
            if (c == '[') {
                this.optionalStart();
                continue;
            }
            if (c == ']') {
                if (this.active.parent != null) {
                    this.optionalEnd();
                    continue;
                }
                throw new IllegalArgumentException("Pattern invalid as it contains ] without previous [");
            }
            if (c != '{' && c != '}' && c != '#') {
                this.appendLiteral(c);
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Pattern includes reserved character: '");
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append("'");
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
    }

    private DateTimeFormatter toFormatter(Locale locale, ResolverStyle resolverStyle, Chronology chronology) {
        Objects.requireNonNull(locale, "locale");
        while (this.active.parent != null) {
            this.optionalEnd();
        }
        return new DateTimeFormatter(new CompositePrinterParser(this.printerParsers, false), locale, DecimalStyle.STANDARD, resolverStyle, null, chronology, null);
    }

    public DateTimeFormatterBuilder append(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        this.appendInternal(dateTimeFormatter.toPrinterParser(false));
        return this;
    }

    public DateTimeFormatterBuilder appendChronologyId() {
        this.appendInternal(new ChronoPrinterParser(null));
        return this;
    }

    public DateTimeFormatterBuilder appendChronologyText(TextStyle textStyle) {
        Objects.requireNonNull(textStyle, "textStyle");
        this.appendInternal(new ChronoPrinterParser(textStyle));
        return this;
    }

    public DateTimeFormatterBuilder appendFraction(TemporalField temporalField, int n, int n2, boolean bl) {
        this.appendInternal(new FractionPrinterParser(temporalField, n, n2, bl));
        return this;
    }

    public DateTimeFormatterBuilder appendInstant() {
        this.appendInternal(new InstantPrinterParser(-2));
        return this;
    }

    public DateTimeFormatterBuilder appendInstant(int n) {
        if (n >= -1 && n <= 9) {
            this.appendInternal(new InstantPrinterParser(n));
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The fractional digits must be from -1 to 9 inclusive but was ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public DateTimeFormatterBuilder appendLiteral(char c) {
        this.appendInternal(new CharLiteralPrinterParser(c));
        return this;
    }

    public DateTimeFormatterBuilder appendLiteral(String string) {
        Objects.requireNonNull(string, "literal");
        if (string.length() > 0) {
            if (string.length() == 1) {
                this.appendInternal(new CharLiteralPrinterParser(string.charAt(0)));
            } else {
                this.appendInternal(new StringLiteralPrinterParser(string));
            }
        }
        return this;
    }

    public DateTimeFormatterBuilder appendLocalized(FormatStyle formatStyle, FormatStyle formatStyle2) {
        if (formatStyle == null && formatStyle2 == null) {
            throw new IllegalArgumentException("Either the date or time style must be non-null");
        }
        this.appendInternal(new LocalizedPrinterParser(formatStyle, formatStyle2));
        return this;
    }

    public DateTimeFormatterBuilder appendLocalizedOffset(TextStyle textStyle) {
        Objects.requireNonNull(textStyle, "style");
        if (textStyle != TextStyle.FULL && textStyle != TextStyle.SHORT) {
            throw new IllegalArgumentException("Style must be either full or short");
        }
        this.appendInternal(new LocalizedOffsetIdPrinterParser(textStyle));
        return this;
    }

    public DateTimeFormatterBuilder appendOffset(String string, String string2) {
        this.appendInternal(new OffsetIdPrinterParser(string, string2));
        return this;
    }

    public DateTimeFormatterBuilder appendOffsetId() {
        this.appendInternal(OffsetIdPrinterParser.INSTANCE_ID_Z);
        return this;
    }

    public DateTimeFormatterBuilder appendOptional(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        this.appendInternal(dateTimeFormatter.toPrinterParser(true));
        return this;
    }

    public DateTimeFormatterBuilder appendPattern(String string) {
        Objects.requireNonNull(string, "pattern");
        this.parsePattern(string);
        return this;
    }

    public DateTimeFormatterBuilder appendText(TemporalField temporalField) {
        return this.appendText(temporalField, TextStyle.FULL);
    }

    public DateTimeFormatterBuilder appendText(TemporalField temporalField, TextStyle textStyle) {
        Objects.requireNonNull(temporalField, "field");
        Objects.requireNonNull(textStyle, "textStyle");
        this.appendInternal(new TextPrinterParser(temporalField, textStyle, DateTimeTextProvider.getInstance()));
        return this;
    }

    public DateTimeFormatterBuilder appendText(TemporalField temporalField, Map<Long, String> object) {
        Objects.requireNonNull(temporalField, "field");
        Objects.requireNonNull(object, "textLookup");
        object = new LinkedHashMap<Long, String>((Map<Long, String>)object);
        object = new DateTimeTextProvider(new DateTimeTextProvider.LocaleStore(Collections.singletonMap(TextStyle.FULL, object))){
            final /* synthetic */ DateTimeTextProvider.LocaleStore val$store;
            {
                this.val$store = localeStore;
            }

            @Override
            public String getText(TemporalField temporalField, long l, TextStyle textStyle, Locale locale) {
                return this.val$store.getText(l, textStyle);
            }

            @Override
            public Iterator<Map.Entry<String, Long>> getTextIterator(TemporalField temporalField, TextStyle textStyle, Locale locale) {
                return this.val$store.getTextIterator(textStyle);
            }
        };
        this.appendInternal(new TextPrinterParser(temporalField, TextStyle.FULL, (DateTimeTextProvider)object));
        return this;
    }

    public DateTimeFormatterBuilder appendValue(TemporalField temporalField) {
        Objects.requireNonNull(temporalField, "field");
        this.appendValue(new NumberPrinterParser(temporalField, 1, 19, SignStyle.NORMAL));
        return this;
    }

    public DateTimeFormatterBuilder appendValue(TemporalField object, int n) {
        Objects.requireNonNull(object, "field");
        if (n >= 1 && n <= 19) {
            this.appendValue(new NumberPrinterParser((TemporalField)object, n, n, SignStyle.NOT_NEGATIVE));
            return this;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("The width must be from 1 to 19 inclusive but was ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public DateTimeFormatterBuilder appendValue(TemporalField object, int n, int n2, SignStyle signStyle) {
        if (n == n2 && signStyle == SignStyle.NOT_NEGATIVE) {
            return this.appendValue((TemporalField)object, n2);
        }
        Objects.requireNonNull(object, "field");
        Objects.requireNonNull(signStyle, "signStyle");
        if (n >= 1 && n <= 19) {
            if (n2 >= 1 && n2 <= 19) {
                if (n2 >= n) {
                    this.appendValue(new NumberPrinterParser((TemporalField)object, n, n2, signStyle));
                    return this;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("The maximum width must exceed or equal the minimum width but ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" < ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("The maximum width must be from 1 to 19 inclusive but was ");
            ((StringBuilder)object).append(n2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("The minimum width must be from 1 to 19 inclusive but was ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public DateTimeFormatterBuilder appendValueReduced(TemporalField temporalField, int n, int n2, int n3) {
        Objects.requireNonNull(temporalField, "field");
        this.appendValue(new ReducedPrinterParser(temporalField, n, n2, n3, null));
        return this;
    }

    public DateTimeFormatterBuilder appendValueReduced(TemporalField temporalField, int n, int n2, ChronoLocalDate chronoLocalDate) {
        Objects.requireNonNull(temporalField, "field");
        Objects.requireNonNull(chronoLocalDate, "baseDate");
        this.appendValue(new ReducedPrinterParser(temporalField, n, n2, 0, chronoLocalDate));
        return this;
    }

    public DateTimeFormatterBuilder appendZoneId() {
        this.appendInternal(new ZoneIdPrinterParser(TemporalQueries.zoneId(), "ZoneId()"));
        return this;
    }

    public DateTimeFormatterBuilder appendZoneOrOffsetId() {
        this.appendInternal(new ZoneIdPrinterParser(TemporalQueries.zone(), "ZoneOrOffsetId()"));
        return this;
    }

    public DateTimeFormatterBuilder appendZoneRegionId() {
        this.appendInternal(new ZoneIdPrinterParser(QUERY_REGION_ONLY, "ZoneRegionId()"));
        return this;
    }

    public DateTimeFormatterBuilder appendZoneText(TextStyle textStyle) {
        this.appendInternal(new ZoneTextPrinterParser(textStyle, null));
        return this;
    }

    public DateTimeFormatterBuilder appendZoneText(TextStyle textStyle, Set<ZoneId> set) {
        Objects.requireNonNull(set, "preferredZones");
        this.appendInternal(new ZoneTextPrinterParser(textStyle, set));
        return this;
    }

    public DateTimeFormatterBuilder optionalEnd() {
        Object object = this.active;
        if (((DateTimeFormatterBuilder)object).parent != null) {
            if (((DateTimeFormatterBuilder)object).printerParsers.size() > 0) {
                object = this.active;
                object = new CompositePrinterParser(((DateTimeFormatterBuilder)object).printerParsers, ((DateTimeFormatterBuilder)object).optional);
                this.active = this.active.parent;
                this.appendInternal((DateTimePrinterParser)object);
            } else {
                this.active = this.active.parent;
            }
            return this;
        }
        throw new IllegalStateException("Cannot call optionalEnd() as there was no previous call to optionalStart()");
    }

    public DateTimeFormatterBuilder optionalStart() {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = this.active;
        dateTimeFormatterBuilder.valueParserIndex = -1;
        this.active = new DateTimeFormatterBuilder(dateTimeFormatterBuilder, true);
        return this;
    }

    public DateTimeFormatterBuilder padNext(int n) {
        return this.padNext(n, ' ');
    }

    public DateTimeFormatterBuilder padNext(int n, char c) {
        if (n >= 1) {
            DateTimeFormatterBuilder dateTimeFormatterBuilder = this.active;
            dateTimeFormatterBuilder.padNextWidth = n;
            dateTimeFormatterBuilder.padNextChar = c;
            dateTimeFormatterBuilder.valueParserIndex = -1;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The pad width must be at least one but was ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public DateTimeFormatterBuilder parseCaseInsensitive() {
        this.appendInternal(SettingsParser.INSENSITIVE);
        return this;
    }

    public DateTimeFormatterBuilder parseCaseSensitive() {
        this.appendInternal(SettingsParser.SENSITIVE);
        return this;
    }

    public DateTimeFormatterBuilder parseDefaulting(TemporalField temporalField, long l) {
        Objects.requireNonNull(temporalField, "field");
        this.appendInternal(new DefaultValueParser(temporalField, l));
        return this;
    }

    public DateTimeFormatterBuilder parseLenient() {
        this.appendInternal(SettingsParser.LENIENT);
        return this;
    }

    public DateTimeFormatterBuilder parseStrict() {
        this.appendInternal(SettingsParser.STRICT);
        return this;
    }

    public DateTimeFormatter toFormatter() {
        return this.toFormatter(Locale.getDefault(Locale.Category.FORMAT));
    }

    DateTimeFormatter toFormatter(ResolverStyle resolverStyle, Chronology chronology) {
        return this.toFormatter(Locale.getDefault(Locale.Category.FORMAT), resolverStyle, chronology);
    }

    public DateTimeFormatter toFormatter(Locale locale) {
        return this.toFormatter(locale, ResolverStyle.SMART, null);
    }

    static final class CharLiteralPrinterParser
    implements DateTimePrinterParser {
        private final char literal;

        CharLiteralPrinterParser(char c) {
            this.literal = c;
        }

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            stringBuilder.append(this.literal);
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            if (n == charSequence.length()) {
                return n;
            }
            char c = charSequence.charAt(n);
            if (c != this.literal && (dateTimeParseContext.isCaseSensitive() || Character.toUpperCase(c) != Character.toUpperCase(this.literal) && Character.toLowerCase(c) != Character.toLowerCase(this.literal))) {
                return n;
            }
            return n + 1;
        }

        public String toString() {
            if (this.literal == '\'') {
                return "''";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'");
            stringBuilder.append(this.literal);
            stringBuilder.append("'");
            return stringBuilder.toString();
        }
    }

    static final class ChronoPrinterParser
    implements DateTimePrinterParser {
        private final TextStyle textStyle;

        ChronoPrinterParser(TextStyle textStyle) {
            this.textStyle = textStyle;
        }

        private String getChronologyName(Chronology object, Locale object2) {
            object = (object2 = LocaleDisplayNames.getInstance((ULocale)ULocale.forLocale((Locale)object2)).keyValueDisplayName("calendar", object.getCalendarType())) != null ? object2 : object.getId();
            return object;
        }

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            Chronology chronology = dateTimePrintContext.getValue(TemporalQueries.chronology());
            if (chronology == null) {
                return false;
            }
            if (this.textStyle == null) {
                stringBuilder.append(chronology.getId());
            } else {
                stringBuilder.append(this.getChronologyName(chronology, dateTimePrintContext.getLocale()));
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            if (n >= 0 && n <= charSequence.length()) {
                Iterator<Chronology> iterator = Chronology.getAvailableChronologies().iterator();
                Chronology chronology = null;
                int n2 = -1;
                while (iterator.hasNext()) {
                    Chronology chronology2 = iterator.next();
                    String string = this.textStyle == null ? chronology2.getId() : this.getChronologyName(chronology2, dateTimeParseContext.getLocale());
                    int n3 = string.length();
                    Chronology chronology3 = chronology;
                    int n4 = n2;
                    if (n3 > n2) {
                        chronology3 = chronology;
                        n4 = n2;
                        if (dateTimeParseContext.subSequenceEquals(charSequence, n, string, 0, n3)) {
                            chronology3 = chronology2;
                            n4 = n3;
                        }
                    }
                    chronology = chronology3;
                    n2 = n4;
                }
                if (chronology == null) {
                    return n;
                }
                dateTimeParseContext.setParsed(chronology);
                return n + n2;
            }
            throw new IndexOutOfBoundsException();
        }
    }

    static final class CompositePrinterParser
    implements DateTimePrinterParser {
        private final boolean optional;
        private final DateTimePrinterParser[] printerParsers;

        CompositePrinterParser(List<DateTimePrinterParser> list, boolean bl) {
            this(list.toArray(new DateTimePrinterParser[list.size()]), bl);
        }

        CompositePrinterParser(DateTimePrinterParser[] arrdateTimePrinterParser, boolean bl) {
            this.printerParsers = arrdateTimePrinterParser;
            this.optional = bl;
        }

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            int n;
            DateTimePrinterParser[] arrdateTimePrinterParser;
            int n2 = stringBuilder.length();
            if (this.optional) {
                dateTimePrintContext.startOptional();
            }
            try {
                arrdateTimePrinterParser = this.printerParsers;
                n = arrdateTimePrinterParser.length;
            }
            catch (Throwable throwable) {
                if (this.optional) {
                    dateTimePrintContext.endOptional();
                }
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                if (arrdateTimePrinterParser[i].format(dateTimePrintContext, stringBuilder)) continue;
                stringBuilder.setLength(n2);
                if (this.optional) {
                    dateTimePrintContext.endOptional();
                }
                return true;
            }
            if (this.optional) {
                dateTimePrintContext.endOptional();
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            int n2;
            boolean bl = this.optional;
            int n3 = 0;
            if (bl) {
                dateTimeParseContext.startOptional();
                DateTimePrinterParser[] arrdateTimePrinterParser = this.printerParsers;
                int n4 = arrdateTimePrinterParser.length;
                n3 = n;
                for (int i = 0; i < n4; ++i) {
                    if ((n3 = arrdateTimePrinterParser[i].parse(dateTimeParseContext, charSequence, n3)) >= 0) continue;
                    dateTimeParseContext.endOptional(false);
                    return n;
                }
                dateTimeParseContext.endOptional(true);
                return n3;
            }
            DateTimePrinterParser[] arrdateTimePrinterParser = this.printerParsers;
            int n5 = arrdateTimePrinterParser.length;
            do {
                n2 = n;
                if (n3 >= n5) break;
                if ((n = arrdateTimePrinterParser[n3].parse(dateTimeParseContext, charSequence, n)) < 0) {
                    n2 = n;
                    break;
                }
                ++n3;
            } while (true);
            return n2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.printerParsers != null) {
                Object object = this.optional ? "[" : "(";
                stringBuilder.append((String)object);
                object = this.printerParsers;
                int n = ((DateTimePrinterParser[])object).length;
                for (int i = 0; i < n; ++i) {
                    stringBuilder.append(object[i]);
                }
                object = this.optional ? "]" : ")";
                stringBuilder.append((String)object);
            }
            return stringBuilder.toString();
        }

        public CompositePrinterParser withOptional(boolean bl) {
            if (bl == this.optional) {
                return this;
            }
            return new CompositePrinterParser(this.printerParsers, bl);
        }
    }

    static interface DateTimePrinterParser {
        public boolean format(DateTimePrintContext var1, StringBuilder var2);

        public int parse(DateTimeParseContext var1, CharSequence var2, int var3);
    }

    static class DefaultValueParser
    implements DateTimePrinterParser {
        private final TemporalField field;
        private final long value;

        DefaultValueParser(TemporalField temporalField, long l) {
            this.field = temporalField;
            this.value = l;
        }

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            if (dateTimeParseContext.getParsed(this.field) == null) {
                dateTimeParseContext.setParsedField(this.field, this.value, n, n);
            }
            return n;
        }
    }

    static final class FractionPrinterParser
    implements DateTimePrinterParser {
        private final boolean decimalPoint;
        private final TemporalField field;
        private final int maxWidth;
        private final int minWidth;

        FractionPrinterParser(TemporalField object, int n, int n2, boolean bl) {
            Objects.requireNonNull(object, "field");
            if (object.range().isFixed()) {
                if (n >= 0 && n <= 9) {
                    if (n2 >= 1 && n2 <= 9) {
                        if (n2 >= n) {
                            this.field = object;
                            this.minWidth = n;
                            this.maxWidth = n2;
                            this.decimalPoint = bl;
                            return;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Maximum width must exceed or equal the minimum width but ");
                        ((StringBuilder)object).append(n2);
                        ((StringBuilder)object).append(" < ");
                        ((StringBuilder)object).append(n);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Maximum width must be from 1 to 9 inclusive but was ");
                    ((StringBuilder)object).append(n2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Minimum width must be from 0 to 9 inclusive but was ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field must have a fixed set of values: ");
            stringBuilder.append(object);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        private long convertFromFraction(BigDecimal bigDecimal) {
            ValueRange valueRange = this.field.range();
            BigDecimal bigDecimal2 = BigDecimal.valueOf(valueRange.getMinimum());
            return bigDecimal.multiply(BigDecimal.valueOf(valueRange.getMaximum()).subtract(bigDecimal2).add(BigDecimal.ONE)).setScale(0, RoundingMode.FLOOR).add(bigDecimal2).longValueExact();
        }

        private BigDecimal convertToFraction(long l) {
            Serializable serializable = this.field.range();
            serializable.checkValidValue(l, this.field);
            BigDecimal bigDecimal = BigDecimal.valueOf(serializable.getMinimum());
            serializable = BigDecimal.valueOf(serializable.getMaximum()).subtract(bigDecimal).add(BigDecimal.ONE);
            bigDecimal = BigDecimal.valueOf(l).subtract(bigDecimal).divide((BigDecimal)serializable, 9, RoundingMode.FLOOR);
            bigDecimal = bigDecimal.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : bigDecimal.stripTrailingZeros();
            return bigDecimal;
        }

        @Override
        public boolean format(DateTimePrintContext object, StringBuilder stringBuilder) {
            Object object2 = ((DateTimePrintContext)object).getValue(this.field);
            if (object2 == null) {
                return false;
            }
            object = ((DateTimePrintContext)object).getDecimalStyle();
            if (((BigDecimal)(object2 = this.convertToFraction((Long)object2))).scale() == 0) {
                if (this.minWidth > 0) {
                    if (this.decimalPoint) {
                        stringBuilder.append(((DecimalStyle)object).getDecimalSeparator());
                    }
                    for (int i = 0; i < this.minWidth; ++i) {
                        stringBuilder.append(((DecimalStyle)object).getZeroDigit());
                    }
                }
            } else {
                object2 = ((DecimalStyle)object).convertNumberToI18N(((BigDecimal)object2).setScale(Math.min(Math.max(((BigDecimal)object2).scale(), this.minWidth), this.maxWidth), RoundingMode.FLOOR).toPlainString().substring(2));
                if (this.decimalPoint) {
                    stringBuilder.append(((DecimalStyle)object).getDecimalSeparator());
                }
                stringBuilder.append((String)object2);
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            int n2;
            int n3 = n;
            int n4 = dateTimeParseContext.isStrict() ? this.minWidth : 0;
            int n5 = dateTimeParseContext.isStrict() ? this.maxWidth : 9;
            int n6 = charSequence.length();
            if (n3 == n6) {
                if (n4 > 0) {
                    // empty if block
                }
                return n3;
            }
            int n7 = n3;
            if (this.decimalPoint) {
                if (charSequence.charAt(n) != dateTimeParseContext.getDecimalStyle().getDecimalSeparator()) {
                    n = n4 > 0 ? n3 : n3;
                    return n;
                }
                n7 = n3 + 1;
            }
            if ((n2 = n7 + n4) > n6) {
                return n7;
            }
            n5 = Math.min(n7 + n5, n6);
            n = n7;
            n3 = 0;
            while (n < n5) {
                n4 = n + 1;
                char c = charSequence.charAt(n);
                n = dateTimeParseContext.getDecimalStyle().convertToDigit(c);
                if (n < 0) {
                    if (n4 < n2) {
                        return n7;
                    }
                    n = n4 - 1;
                    break;
                }
                n3 = n3 * 10 + n;
                n = n4;
            }
            long l = this.convertFromFraction(new BigDecimal(n3).movePointLeft(n - n7));
            return dateTimeParseContext.setParsedField(this.field, l, n7, n);
        }

        public String toString() {
            String string = this.decimalPoint ? ",DecimalPoint" : "";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fraction(");
            stringBuilder.append(this.field);
            stringBuilder.append(",");
            stringBuilder.append(this.minWidth);
            stringBuilder.append(",");
            stringBuilder.append(this.maxWidth);
            stringBuilder.append(string);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static final class InstantPrinterParser
    implements DateTimePrinterParser {
        private static final long SECONDS_0000_TO_1970 = 62167219200L;
        private static final long SECONDS_PER_10000_YEARS = 315569520000L;
        private final int fractionalDigits;

        InstantPrinterParser(int n) {
            this.fractionalDigits = n;
        }

        @Override
        public boolean format(DateTimePrintContext object, StringBuilder stringBuilder) {
            int n;
            Long l = ((DateTimePrintContext)object).getValue(ChronoField.INSTANT_SECONDS);
            Long l2 = null;
            if (((DateTimePrintContext)object).getTemporal().isSupported(ChronoField.NANO_OF_SECOND)) {
                l2 = ((DateTimePrintContext)object).getTemporal().getLong(ChronoField.NANO_OF_SECOND);
            }
            if (l == null) {
                return false;
            }
            long l3 = l;
            object = ChronoField.NANO_OF_SECOND;
            long l4 = l2 != null ? l2 : 0L;
            int n2 = ((ChronoField)object).checkValidIntValue(l4);
            if (l3 >= -62167219200L) {
                l3 = l3 - 315569520000L + 62167219200L;
                l4 = Math.floorDiv(l3, 315569520000L) + 1L;
                object = LocalDateTime.ofEpochSecond(Math.floorMod(l3, 315569520000L) - 62167219200L, 0, ZoneOffset.UTC);
                if (l4 > 0L) {
                    stringBuilder.append('+');
                    stringBuilder.append(l4);
                }
                stringBuilder.append(object);
                if (((LocalDateTime)object).getSecond() == 0) {
                    stringBuilder.append(":00");
                }
            } else {
                l4 = (l3 += 62167219200L) / 315569520000L;
                object = LocalDateTime.ofEpochSecond((l3 %= 315569520000L) - 62167219200L, 0, ZoneOffset.UTC);
                n = stringBuilder.length();
                stringBuilder.append(object);
                if (((LocalDateTime)object).getSecond() == 0) {
                    stringBuilder.append(":00");
                }
                if (l4 < 0L) {
                    if (((LocalDateTime)object).getYear() == -10000) {
                        stringBuilder.replace(n, n + 2, Long.toString(l4 - 1L));
                    } else if (l3 == 0L) {
                        stringBuilder.insert(n, l4);
                    } else {
                        stringBuilder.insert(n + 1, Math.abs(l4));
                    }
                }
            }
            if (this.fractionalDigits < 0 && n2 > 0 || this.fractionalDigits > 0) {
                stringBuilder.append('.');
                int n3 = 100000000;
                for (n = 0; this.fractionalDigits == -1 && n2 > 0 || this.fractionalDigits == -2 && (n2 > 0 || n % 3 != 0) || n < this.fractionalDigits; ++n) {
                    int n4 = n2 / n3;
                    stringBuilder.append((char)(n4 + 48));
                    n2 -= n4 * n3;
                    n3 /= 10;
                }
            }
            stringBuilder.append('Z');
            return true;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence object, int n) {
            int n4;
            int n2;
            CompositePrinterParser compositePrinterParser;
            Object object2;
            int n3;
            int n5 = this.fractionalDigits;
            int n6 = 0;
            int n7 = n5;
            if (n5 < 0) {
                n7 = 0;
            }
            n5 = n2 = this.fractionalDigits;
            if (n2 < 0) {
                n5 = 9;
            }
            if ((n4 = (compositePrinterParser = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral('T').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).appendFraction(ChronoField.NANO_OF_SECOND, n7, n5, true).appendLiteral('Z').toFormatter().toPrinterParser(false)).parse((DateTimeParseContext)(object2 = dateTimeParseContext.copy()), (CharSequence)object, n)) < 0) {
                return n4;
            }
            long l = ((DateTimeParseContext)object2).getParsed(ChronoField.YEAR);
            int n8 = ((DateTimeParseContext)object2).getParsed(ChronoField.MONTH_OF_YEAR).intValue();
            int n9 = ((DateTimeParseContext)object2).getParsed(ChronoField.DAY_OF_MONTH).intValue();
            n5 = ((DateTimeParseContext)object2).getParsed(ChronoField.HOUR_OF_DAY).intValue();
            int n10 = ((DateTimeParseContext)object2).getParsed(ChronoField.MINUTE_OF_HOUR).intValue();
            object = ((DateTimeParseContext)object2).getParsed(ChronoField.SECOND_OF_MINUTE);
            object2 = ((DateTimeParseContext)object2).getParsed(ChronoField.NANO_OF_SECOND);
            n7 = object != null ? ((Long)object).intValue() : 0;
            if (object2 != null) {
                n6 = ((Long)object2).intValue();
            }
            if (n5 == 24 && n10 == 0 && n7 == 0 && n6 == 0) {
                n5 = 0;
                n2 = n7;
                n7 = 1;
            } else if (n5 == 23 && n10 == 59 && n7 == 60) {
                dateTimeParseContext.setParsedLeapSecond();
                n2 = 59;
                n7 = 0;
            } else {
                n3 = 0;
                n2 = n7;
                n7 = n3;
            }
            n3 = (int)l;
            object = LocalDateTime.of(n3 % 10000, n8, n9, n5, n10, n2, 0);
            long l2 = n7;
            try {
                l2 = ((LocalDateTime)object).plusDays(l2).toEpochSecond(ZoneOffset.UTC);
            }
            catch (RuntimeException runtimeException) {
                return n;
            }
            try {
                l /= 10000L;
            }
            catch (RuntimeException runtimeException) {
                return n;
            }
            try {
                l = Math.multiplyExact(l, 315569520000L);
            }
            catch (RuntimeException runtimeException) {
                return n;
            }
            n7 = dateTimeParseContext.setParsedField(ChronoField.INSTANT_SECONDS, l2 + l, n, n4);
            return dateTimeParseContext.setParsedField(ChronoField.NANO_OF_SECOND, n6, n, n7);
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
            return n;
        }

        public String toString() {
            return "Instant()";
        }
    }

    static final class LocalizedOffsetIdPrinterParser
    implements DateTimePrinterParser {
        private final TextStyle style;

        LocalizedOffsetIdPrinterParser(TextStyle textStyle) {
            this.style = textStyle;
        }

        private static StringBuilder appendHMS(StringBuilder stringBuilder, int n) {
            stringBuilder.append((char)(n / 10 + 48));
            stringBuilder.append((char)(n % 10 + 48));
            return stringBuilder;
        }

        @Override
        public boolean format(DateTimePrintContext object, StringBuilder stringBuilder) {
            if ((object = ((DateTimePrintContext)object).getValue(ChronoField.OFFSET_SECONDS)) == null) {
                return false;
            }
            stringBuilder.append("GMT");
            int n = Math.toIntExact((Long)object);
            if (n != 0) {
                int n2 = Math.abs(n / 3600 % 100);
                int n3 = Math.abs(n / 60 % 60);
                int n4 = Math.abs(n % 60);
                object = n < 0 ? "-" : "+";
                stringBuilder.append((String)object);
                if (this.style == TextStyle.FULL) {
                    LocalizedOffsetIdPrinterParser.appendHMS(stringBuilder, n2);
                    stringBuilder.append(':');
                    LocalizedOffsetIdPrinterParser.appendHMS(stringBuilder, n3);
                    if (n4 != 0) {
                        stringBuilder.append(':');
                        LocalizedOffsetIdPrinterParser.appendHMS(stringBuilder, n4);
                    }
                } else {
                    if (n2 >= 10) {
                        stringBuilder.append((char)(n2 / 10 + 48));
                    }
                    stringBuilder.append((char)(n2 % 10 + 48));
                    if (n3 != 0 || n4 != 0) {
                        stringBuilder.append(':');
                        LocalizedOffsetIdPrinterParser.appendHMS(stringBuilder, n3);
                        if (n4 != 0) {
                            stringBuilder.append(':');
                            LocalizedOffsetIdPrinterParser.appendHMS(stringBuilder, n4);
                        }
                    }
                }
            }
            return true;
        }

        int getDigit(CharSequence charSequence, int n) {
            if ((n = (int)charSequence.charAt(n)) >= 48 && n <= 57) {
                return n - 48;
            }
            return -1;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public int parse(DateTimeParseContext var1_1, CharSequence var2_2, int var3_3) {
            block16 : {
                block17 : {
                    block15 : {
                        var4_4 = var3_3 + var2_2.length();
                        if (!var1_1.subSequenceEquals(var2_2, var3_3, "GMT", 0, "GMT".length())) {
                            return var3_3;
                        }
                        var5_5 = var3_3 + "GMT".length();
                        if (var5_5 == var4_4) {
                            return var1_1.setParsedField(ChronoField.OFFSET_SECONDS, 0L, var3_3, var5_5);
                        }
                        var6_6 = var2_2.charAt(var5_5);
                        if (var6_6 == 43) {
                            var7_7 = 1;
                        } else {
                            if (var6_6 != 45) return var1_1.setParsedField(ChronoField.OFFSET_SECONDS, 0L, var3_3, var5_5);
                            var7_7 = -1;
                        }
                        var8_8 = var5_5 + 1;
                        var9_9 = 0;
                        var10_10 = 0;
                        if (this.style != TextStyle.FULL) break block15;
                        var5_5 = var8_8 + 1;
                        var8_8 = this.getDigit(var2_2, var8_8);
                        var6_6 = var5_5 + 1;
                        var11_11 = this.getDigit(var2_2, var5_5);
                        if (var8_8 < 0) return var3_3;
                        if (var11_11 < 0) return var3_3;
                        var5_5 = var6_6 + 1;
                        if (var2_2.charAt(var6_6) != ':') {
                            return var3_3;
                        }
                        var8_8 = var8_8 * 10 + var11_11;
                        var6_6 = var5_5 + 1;
                        var9_9 = this.getDigit(var2_2, var5_5);
                        var11_11 = var6_6 + 1;
                        var12_13 = this.getDigit(var2_2, var6_6);
                        if (var9_9 < 0) return var3_3;
                        if (var12_13 < 0) {
                            return var3_3;
                        }
                        if (var11_11 + 2 < var4_4) {
                            var5_5 = var10_10;
                            var6_6 = var11_11;
                            if (var2_2.charAt(var11_11) == ':') {
                                var4_4 = this.getDigit(var2_2, var11_11 + 1);
                                var13_15 = this.getDigit(var2_2, var11_11 + 2);
                                var5_5 = var10_10;
                                var6_6 = var11_11;
                                if (var4_4 >= 0) {
                                    var5_5 = var10_10;
                                    var6_6 = var11_11;
                                    if (var13_15 >= 0) {
                                        var5_5 = var4_4 * 10 + var13_15;
                                        var6_6 = var11_11 + 3;
                                    }
                                }
                            }
                        } else {
                            var6_6 = var11_11;
                            var5_5 = var10_10;
                        }
                        var11_11 = var9_9 * 10 + var12_13;
                        var10_10 = var6_6;
                        var6_6 = var5_5;
                        var5_5 = var11_11;
                        break block16;
                    }
                    var6_6 = var8_8 + 1;
                    var11_12 = this.getDigit(var2_2, var8_8);
                    if (var11_12 < 0) {
                        return var3_3;
                    }
                    if (var6_6 >= var4_4) break block17;
                    var10_10 = this.getDigit(var2_2, var6_6);
                    var8_8 = var6_6;
                    var5_5 = var11_12;
                    if (var10_10 >= 0) {
                        var8_8 = var6_6 + 1;
                        var5_5 = var11_12 * 10 + var10_10;
                    }
                    var10_10 = var9_9;
                    var6_6 = var8_8;
                    if (var8_8 + 2 >= var4_4) ** GOTO lbl-1000
                    var10_10 = var9_9;
                    var6_6 = var8_8;
                    if (var2_2.charAt(var8_8) != ':') ** GOTO lbl-1000
                    var10_10 = var9_9;
                    var6_6 = var8_8;
                    if (var8_8 + 2 >= var4_4) ** GOTO lbl-1000
                    var10_10 = var9_9;
                    var6_6 = var8_8;
                    if (var2_2.charAt(var8_8) != ':') ** GOTO lbl-1000
                    var12_14 = this.getDigit(var2_2, var8_8 + 1);
                    var11_12 = this.getDigit(var2_2, var8_8 + 2);
                    var10_10 = var9_9;
                    var6_6 = var8_8;
                    if (var12_14 < 0) ** GOTO lbl-1000
                    var10_10 = var9_9;
                    var6_6 = var8_8;
                    if (var11_12 < 0) ** GOTO lbl-1000
                    var11_12 = var12_14 * 10 + var11_12;
                    var10_10 = var11_12;
                    var6_6 = var8_8 += 3;
                    if (var8_8 + 2 >= var4_4) ** GOTO lbl-1000
                    var10_10 = var11_12;
                    var6_6 = var8_8;
                    if (var2_2.charAt(var8_8) != ':') ** GOTO lbl-1000
                    var9_9 = this.getDigit(var2_2, var8_8 + 1);
                    var4_4 = this.getDigit(var2_2, var8_8 + 2);
                    var10_10 = var11_12;
                    var6_6 = var8_8;
                    if (var9_9 < 0) ** GOTO lbl-1000
                    var10_10 = var11_12;
                    var6_6 = var8_8;
                    if (var4_4 >= 0) {
                        var6_6 = var9_9 * 10 + var4_4;
                        var10_10 = var8_8 + 3;
                        var8_8 = var5_5;
                        var5_5 = var11_12;
                    } else lbl-1000: // 10 sources:
                    {
                        var11_12 = var10_10;
                        var9_9 = 0;
                        var10_10 = var6_6;
                        var8_8 = var5_5;
                        var5_5 = var11_12;
                        var6_6 = var9_9;
                    }
                    break block16;
                }
                var5_5 = 0;
                var8_8 = 0;
                var10_10 = var6_6;
                var6_6 = var8_8;
                var8_8 = var11_12;
            }
            var14_16 = var7_7;
            var16_17 = var8_8;
            var18_18 = var5_5;
            var20_19 = var6_6;
            return var1_1.setParsedField(ChronoField.OFFSET_SECONDS, var14_16 * (var16_17 * 3600L + var18_18 * 60L + var20_19), var3_3, var10_10);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("LocalizedOffset(");
            stringBuilder.append((Object)this.style);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static final class LocalizedPrinterParser
    implements DateTimePrinterParser {
        private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<String, DateTimeFormatter>(16, 0.75f, 2);
        private final FormatStyle dateStyle;
        private final FormatStyle timeStyle;

        LocalizedPrinterParser(FormatStyle formatStyle, FormatStyle formatStyle2) {
            this.dateStyle = formatStyle;
            this.timeStyle = formatStyle2;
        }

        private DateTimeFormatter formatter(Locale object, Chronology object2) {
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append(object2.getId());
            ((StringBuilder)object3).append('|');
            ((StringBuilder)object3).append(((Locale)object).toString());
            ((StringBuilder)object3).append('|');
            ((StringBuilder)object3).append((Object)this.dateStyle);
            ((StringBuilder)object3).append((Object)this.timeStyle);
            String string = ((StringBuilder)object3).toString();
            DateTimeFormatter dateTimeFormatter = (DateTimeFormatter)FORMATTER_CACHE.get(string);
            object3 = dateTimeFormatter;
            if (dateTimeFormatter == null) {
                object2 = DateTimeFormatterBuilder.getLocalizedDateTimePattern(this.dateStyle, this.timeStyle, (Chronology)object2, (Locale)object);
                object3 = new DateTimeFormatterBuilder().appendPattern((String)object2).toFormatter((Locale)object);
                object = FORMATTER_CACHE.putIfAbsent(string, (DateTimeFormatter)object3);
                if (object != null) {
                    object3 = object;
                }
            }
            return object3;
        }

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            Chronology chronology = Chronology.from(dateTimePrintContext.getTemporal());
            return this.formatter(dateTimePrintContext.getLocale(), chronology).toPrinterParser(false).format(dateTimePrintContext, stringBuilder);
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            Chronology chronology = dateTimeParseContext.getEffectiveChronology();
            return this.formatter(dateTimeParseContext.getLocale(), chronology).toPrinterParser(false).parse(dateTimeParseContext, charSequence, n);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Localized(");
            Object object = this.dateStyle;
            if (object == null) {
                object = "";
            }
            stringBuilder.append(object);
            stringBuilder.append(",");
            object = this.timeStyle;
            if (object == null) {
                object = "";
            }
            stringBuilder.append(object);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static class NumberPrinterParser
    implements DateTimePrinterParser {
        static final long[] EXCEED_POINTS = new long[]{0L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L};
        final TemporalField field;
        final int maxWidth;
        final int minWidth;
        private final SignStyle signStyle;
        final int subsequentWidth;

        NumberPrinterParser(TemporalField temporalField, int n, int n2, SignStyle signStyle) {
            this.field = temporalField;
            this.minWidth = n;
            this.maxWidth = n2;
            this.signStyle = signStyle;
            this.subsequentWidth = 0;
        }

        protected NumberPrinterParser(TemporalField temporalField, int n, int n2, SignStyle signStyle, int n3) {
            this.field = temporalField;
            this.minWidth = n;
            this.maxWidth = n2;
            this.signStyle = signStyle;
            this.subsequentWidth = n3;
        }

        @Override
        public boolean format(DateTimePrintContext object, StringBuilder stringBuilder) {
            Object object2 = ((DateTimePrintContext)object).getValue(this.field);
            if (object2 == null) {
                return false;
            }
            long l = this.getValue((DateTimePrintContext)object, (Long)object2);
            object2 = ((DateTimePrintContext)object).getDecimalStyle();
            object = l == Long.MIN_VALUE ? "9223372036854775808" : Long.toString(Math.abs(l));
            if (((String)object).length() <= this.maxWidth) {
                int n;
                object = ((DecimalStyle)object2).convertNumberToI18N((String)object);
                if (l >= 0L) {
                    n = 3.$SwitchMap$java$time$format$SignStyle[this.signStyle.ordinal()];
                    if (n != 1) {
                        if (n == 2) {
                            stringBuilder.append(((DecimalStyle)object2).getPositiveSign());
                        }
                    } else {
                        n = this.minWidth;
                        if (n < 19 && l >= EXCEED_POINTS[n]) {
                            stringBuilder.append(((DecimalStyle)object2).getPositiveSign());
                        }
                    }
                } else {
                    n = 3.$SwitchMap$java$time$format$SignStyle[this.signStyle.ordinal()];
                    if (n != 1 && n != 2 && n != 3) {
                        if (n == 4) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Field ");
                            ((StringBuilder)object).append(this.field);
                            ((StringBuilder)object).append(" cannot be printed as the value ");
                            ((StringBuilder)object).append(l);
                            ((StringBuilder)object).append(" cannot be negative according to the SignStyle");
                            throw new DateTimeException(((StringBuilder)object).toString());
                        }
                    } else {
                        stringBuilder.append(((DecimalStyle)object2).getNegativeSign());
                    }
                }
                for (n = 0; n < this.minWidth - ((String)object).length(); ++n) {
                    stringBuilder.append(((DecimalStyle)object2).getZeroDigit());
                }
                stringBuilder.append((String)object);
                return true;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Field ");
            ((StringBuilder)object).append(this.field);
            ((StringBuilder)object).append(" cannot be printed as the value ");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(" exceeds the maximum print width of ");
            ((StringBuilder)object).append(this.maxWidth);
            throw new DateTimeException(((StringBuilder)object).toString());
        }

        long getValue(DateTimePrintContext dateTimePrintContext, long l) {
            return l;
        }

        boolean isFixedWidth(DateTimeParseContext dateTimeParseContext) {
            int n = this.subsequentWidth;
            boolean bl = n == -1 || n > 0 && this.minWidth == this.maxWidth && this.signStyle == SignStyle.NOT_NEGATIVE;
            return bl;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence object, int n) {
            int n2;
            int n3;
            long l;
            int n4;
            Object object2;
            block30 : {
                Object object3;
                int n5;
                boolean bl;
                boolean bl2;
                int n6 = object.length();
                if (n == n6) {
                    return n;
                }
                n2 = object.charAt(n);
                n4 = dateTimeParseContext.getDecimalStyle().getPositiveSign();
                int n7 = 1;
                if (n2 == n4) {
                    object2 = this.signStyle;
                    bl2 = dateTimeParseContext.isStrict();
                    if (!object2.parse(true, bl2, bl = this.minWidth == this.maxWidth)) {
                        return n;
                    }
                    ++n;
                    n3 = 0;
                    n4 = 1;
                } else if (n2 == dateTimeParseContext.getDecimalStyle().getNegativeSign()) {
                    object2 = this.signStyle;
                    bl2 = dateTimeParseContext.isStrict();
                    if (!object2.parse(false, bl2, bl = this.minWidth == this.maxWidth)) {
                        return n;
                    }
                    ++n;
                    n3 = 1;
                    n4 = 0;
                } else {
                    if (this.signStyle == SignStyle.ALWAYS && dateTimeParseContext.isStrict()) {
                        return n;
                    }
                    n3 = 0;
                    n4 = 0;
                }
                if (dateTimeParseContext.isStrict() || this.isFixedWidth(dateTimeParseContext)) {
                    n7 = this.minWidth;
                }
                if ((n5 = n + n7) > n6) {
                    return n;
                }
                int n8 = !dateTimeParseContext.isStrict() && !this.isFixedWidth(dateTimeParseContext) ? 9 : this.maxWidth;
                int n9 = Math.max(this.subsequentWidth, 0);
                int n10 = 0;
                n9 = n8 + n9;
                n8 = n2;
                do {
                    n2 = n;
                    object3 = null;
                    object2 = null;
                    l = 0L;
                    if (n10 >= 2) break;
                    n9 = Math.min(n2 + n9, n6);
                    while (n2 < n9) {
                        int n11 = n2 + 1;
                        char c = object.charAt(n2);
                        n2 = dateTimeParseContext.getDecimalStyle().convertToDigit(c);
                        if (n2 < 0) {
                            n2 = n11 - 1;
                            if (n2 >= n5) break;
                            return n;
                        }
                        if (n11 - n > 18) {
                            object3 = object2;
                            if (object2 == null) {
                                object3 = BigInteger.valueOf(l);
                            }
                            object2 = ((BigInteger)object3).multiply(BigInteger.TEN).add(BigInteger.valueOf(n2));
                        } else {
                            l = (long)n2 + 10L * l;
                        }
                        n2 = n11;
                    }
                    if ((n9 = this.subsequentWidth) > 0 && n10 == 0) {
                        n9 = Math.max(n7, n2 - n - n9);
                        ++n10;
                        continue;
                    }
                    break block30;
                    break;
                } while (true);
                object2 = object3;
            }
            if (n3 != 0) {
                if (object2 != null) {
                    if (((BigInteger)object2).equals(BigInteger.ZERO) && dateTimeParseContext.isStrict()) {
                        return n - 1;
                    }
                    object = ((BigInteger)object2).negate();
                } else {
                    if (l == 0L && dateTimeParseContext.isStrict()) {
                        return n - 1;
                    }
                    l = -l;
                    object = object2;
                }
            } else {
                if (this.signStyle == SignStyle.EXCEEDS_PAD && dateTimeParseContext.isStrict()) {
                    n3 = n2 - n;
                    if (n4 != 0) {
                        if (n3 <= this.minWidth) {
                            return n - 1;
                        }
                    } else if (n3 > this.minWidth) {
                        return n;
                    }
                }
                object = object2;
            }
            if (object != null) {
                object2 = object;
                n4 = n2;
                if (((BigInteger)object).bitLength() > 63) {
                    object2 = ((BigInteger)object).divide(BigInteger.TEN);
                    n4 = n2 - 1;
                }
                return this.setValue(dateTimeParseContext, ((BigInteger)object2).longValue(), n, n4);
            }
            return this.setValue(dateTimeParseContext, l, n, n2);
        }

        int setValue(DateTimeParseContext dateTimeParseContext, long l, int n, int n2) {
            return dateTimeParseContext.setParsedField(this.field, l, n, n2);
        }

        public String toString() {
            if (this.minWidth == 1 && this.maxWidth == 19 && this.signStyle == SignStyle.NORMAL) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value(");
                stringBuilder.append(this.field);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            if (this.minWidth == this.maxWidth && this.signStyle == SignStyle.NOT_NEGATIVE) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value(");
                stringBuilder.append(this.field);
                stringBuilder.append(",");
                stringBuilder.append(this.minWidth);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Value(");
            stringBuilder.append(this.field);
            stringBuilder.append(",");
            stringBuilder.append(this.minWidth);
            stringBuilder.append(",");
            stringBuilder.append(this.maxWidth);
            stringBuilder.append(",");
            stringBuilder.append((Object)this.signStyle);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        NumberPrinterParser withFixedWidth() {
            if (this.subsequentWidth == -1) {
                return this;
            }
            return new NumberPrinterParser(this.field, this.minWidth, this.maxWidth, this.signStyle, -1);
        }

        NumberPrinterParser withSubsequentWidth(int n) {
            return new NumberPrinterParser(this.field, this.minWidth, this.maxWidth, this.signStyle, this.subsequentWidth + n);
        }
    }

    static final class OffsetIdPrinterParser
    implements DateTimePrinterParser {
        static final OffsetIdPrinterParser INSTANCE_ID_Z;
        static final OffsetIdPrinterParser INSTANCE_ID_ZERO;
        static final String[] PATTERNS;
        private final String noOffsetText;
        private final int type;

        static {
            PATTERNS = new String[]{"+HH", "+HHmm", "+HH:mm", "+HHMM", "+HH:MM", "+HHMMss", "+HH:MM:ss", "+HHMMSS", "+HH:MM:SS"};
            INSTANCE_ID_Z = new OffsetIdPrinterParser("+HH:MM:ss", "Z");
            INSTANCE_ID_ZERO = new OffsetIdPrinterParser("+HH:MM:ss", "0");
        }

        OffsetIdPrinterParser(String string, String string2) {
            Objects.requireNonNull(string, "pattern");
            Objects.requireNonNull(string2, "noOffsetText");
            this.type = this.checkPattern(string);
            this.noOffsetText = string2;
        }

        private int checkPattern(String string) {
            Object object;
            for (int i = 0; i < ((String[])(object = PATTERNS)).length; ++i) {
                if (!object[i].equals(string)) continue;
                return i;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid zone offset pattern: ");
            ((StringBuilder)object).append(string);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        private boolean parseNumber(int[] arrn, int n, CharSequence charSequence, boolean bl) {
            int n2;
            int n3 = this.type;
            if ((n3 + 3) / 2 < n) {
                return false;
            }
            int n4 = n2 = arrn[0];
            if (n3 % 2 == 0) {
                n4 = n2;
                if (n > 1) {
                    if (n2 + 1 <= charSequence.length() && charSequence.charAt(n2) == ':') {
                        n4 = n2 + 1;
                    } else {
                        return bl;
                    }
                }
            }
            if (n4 + 2 > charSequence.length()) {
                return bl;
            }
            n2 = n4 + 1;
            n4 = charSequence.charAt(n4);
            n3 = charSequence.charAt(n2);
            if (n4 >= 48 && n4 <= 57 && n3 >= 48 && n3 <= 57) {
                if ((n4 = (n4 - 48) * 10 + (n3 - 48)) >= 0 && n4 <= 59) {
                    arrn[n] = n4;
                    arrn[0] = n2 + 1;
                    return false;
                }
                return bl;
            }
            return bl;
        }

        @Override
        public boolean format(DateTimePrintContext object, StringBuilder stringBuilder) {
            block6 : {
                int n;
                int n2;
                block8 : {
                    int n3;
                    String string;
                    int n4;
                    block9 : {
                        int n5;
                        block7 : {
                            int n6;
                            block5 : {
                                if ((object = ((DateTimePrintContext)object).getValue(ChronoField.OFFSET_SECONDS)) == null) {
                                    return false;
                                }
                                n6 = Math.toIntExact((Long)object);
                                if (n6 != 0) break block5;
                                stringBuilder.append(this.noOffsetText);
                                break block6;
                            }
                            n = Math.abs(n6 / 3600 % 100);
                            n5 = Math.abs(n6 / 60 % 60);
                            n4 = Math.abs(n6 % 60);
                            n2 = stringBuilder.length();
                            n3 = n;
                            object = n6 < 0 ? "-" : "+";
                            stringBuilder.append((String)object);
                            stringBuilder.append((char)(n / 10 + 48));
                            stringBuilder.append((char)(n % 10 + 48));
                            n6 = this.type;
                            if (n6 >= 3) break block7;
                            n = n3;
                            if (n6 < 1) break block8;
                            n = n3;
                            if (n5 <= 0) break block8;
                        }
                        n = this.type;
                        string = ":";
                        object = n % 2 == 0 ? ":" : "";
                        stringBuilder.append((String)object);
                        stringBuilder.append((char)(n5 / 10 + 48));
                        stringBuilder.append((char)(n5 % 10 + 48));
                        n3 += n5;
                        n5 = this.type;
                        if (n5 >= 7) break block9;
                        n = n3;
                        if (n5 < 5) break block8;
                        n = n3;
                        if (n4 <= 0) break block8;
                    }
                    object = this.type % 2 == 0 ? string : "";
                    stringBuilder.append((String)object);
                    stringBuilder.append((char)(n4 / 10 + 48));
                    stringBuilder.append((char)(n4 % 10 + 48));
                    n = n3 + n4;
                }
                if (n == 0) {
                    stringBuilder.setLength(n2);
                    stringBuilder.append(this.noOffsetText);
                }
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            int n2 = charSequence.length();
            int n3 = this.noOffsetText.length();
            if (n3 == 0) {
                if (n == n2) {
                    return dateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, 0L, n, n);
                }
            } else {
                if (n == n2) {
                    return n;
                }
                if (dateTimeParseContext.subSequenceEquals(charSequence, n, this.noOffsetText, 0, n3)) {
                    return dateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, 0L, n, n + n3);
                }
            }
            if ((n2 = (int)charSequence.charAt(n)) == 43 || n2 == 45) {
                boolean bl;
                n2 = n2 == 45 ? -1 : 1;
                int[] arrn = new int[4];
                arrn[0] = n + 1;
                boolean bl2 = this.parseNumber(arrn, 1, charSequence, true) || this.parseNumber(arrn, 2, charSequence, bl = this.type >= 3) || this.parseNumber(arrn, 3, charSequence, false);
                if (!bl2) {
                    long l = n2;
                    long l2 = arrn[1];
                    long l3 = arrn[2];
                    long l4 = arrn[3];
                    return dateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, l * (l2 * 3600L + l3 * 60L + l4), n, arrn[0]);
                }
            }
            if (n3 == 0) {
                return dateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, 0L, n, n + n3);
            }
            return n;
        }

        public String toString() {
            String string = this.noOffsetText.replace("'", "''");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Offset(");
            stringBuilder.append(PATTERNS[this.type]);
            stringBuilder.append(",'");
            stringBuilder.append(string);
            stringBuilder.append("')");
            return stringBuilder.toString();
        }
    }

    static final class PadPrinterParserDecorator
    implements DateTimePrinterParser {
        private final char padChar;
        private final int padWidth;
        private final DateTimePrinterParser printerParser;

        PadPrinterParserDecorator(DateTimePrinterParser dateTimePrinterParser, int n, char c) {
            this.printerParser = dateTimePrinterParser;
            this.padWidth = n;
            this.padChar = c;
        }

        @Override
        public boolean format(DateTimePrintContext object, StringBuilder stringBuilder) {
            int n = stringBuilder.length();
            if (!this.printerParser.format((DateTimePrintContext)object, stringBuilder)) {
                return false;
            }
            int n2 = stringBuilder.length() - n;
            if (n2 <= this.padWidth) {
                for (int i = 0; i < this.padWidth - n2; ++i) {
                    stringBuilder.insert(n, this.padChar);
                }
                return true;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot print as output of ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" characters exceeds pad width of ");
            ((StringBuilder)object).append(this.padWidth);
            throw new DateTimeException(((StringBuilder)object).toString());
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            boolean bl = dateTimeParseContext.isStrict();
            if (n <= charSequence.length()) {
                int n2;
                if (n == charSequence.length()) {
                    return n;
                }
                int n3 = n2 = this.padWidth + n;
                if (n2 > charSequence.length()) {
                    if (bl) {
                        return n;
                    }
                    n3 = charSequence.length();
                }
                for (n2 = n; n2 < n3 && dateTimeParseContext.charEquals(charSequence.charAt(n2), this.padChar); ++n2) {
                }
                int n4 = this.printerParser.parse(dateTimeParseContext, charSequence = charSequence.subSequence(0, n3), n2);
                if (n4 != n3 && bl) {
                    return n + n2;
                }
                return n4;
            }
            throw new IndexOutOfBoundsException();
        }

        public String toString() {
            CharSequence charSequence;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Pad(");
            stringBuilder.append(this.printerParser);
            stringBuilder.append(",");
            stringBuilder.append(this.padWidth);
            if (this.padChar == ' ') {
                charSequence = ")";
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(",'");
                ((StringBuilder)charSequence).append(this.padChar);
                ((StringBuilder)charSequence).append("')");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence);
            return stringBuilder.toString();
        }
    }

    static class PrefixTree {
        protected char c0;
        protected PrefixTree child;
        protected String key;
        protected PrefixTree sibling;
        protected String value;

        private PrefixTree(String string, String string2, PrefixTree prefixTree) {
            this.key = string;
            this.value = string2;
            this.child = prefixTree;
            this.c0 = string.length() == 0 ? (char)65535 : this.key.charAt(0);
        }

        private boolean add0(String object, String string) {
            String string2 = this.toKey((String)object);
            int n = this.prefixLength(string2);
            if (n == this.key.length()) {
                if (n < string2.length()) {
                    string2 = string2.substring(n);
                    object = this.child;
                    while (object != null) {
                        if (this.isEqual(((PrefixTree)object).c0, string2.charAt(0))) {
                            return PrefixTree.super.add0(string2, string);
                        }
                        object = ((PrefixTree)object).sibling;
                    }
                    object = this.newNode(string2, string, null);
                    ((PrefixTree)object).sibling = this.child;
                    this.child = object;
                    return true;
                }
                this.value = string;
                return true;
            }
            object = this.newNode(this.key.substring(n), this.value, this.child);
            this.key = string2.substring(0, n);
            this.child = object;
            if (n < string2.length()) {
                this.child.sibling = object = this.newNode(string2.substring(n), string, null);
                this.value = null;
            } else {
                this.value = string;
            }
            return true;
        }

        public static PrefixTree newTree(DateTimeParseContext dateTimeParseContext) {
            if (dateTimeParseContext.isCaseSensitive()) {
                return new PrefixTree("", null, null);
            }
            return new CI("", null, null);
        }

        public static PrefixTree newTree(Set<String> object, DateTimeParseContext object2) {
            object2 = PrefixTree.newTree((DateTimeParseContext)object2);
            object = object.iterator();
            while (object.hasNext()) {
                String string = (String)object.next();
                PrefixTree.super.add0(string, string);
            }
            return object2;
        }

        private int prefixLength(String string) {
            int n;
            for (n = 0; n < string.length() && n < this.key.length(); ++n) {
                if (this.isEqual(string.charAt(n), this.key.charAt(n))) continue;
                return n;
            }
            return n;
        }

        public boolean add(String string, String string2) {
            return this.add0(string, string2);
        }

        public PrefixTree copyTree() {
            PrefixTree prefixTree = new PrefixTree(this.key, this.value, null);
            PrefixTree prefixTree2 = this.child;
            if (prefixTree2 != null) {
                prefixTree.child = prefixTree2.copyTree();
            }
            if ((prefixTree2 = this.sibling) != null) {
                prefixTree.sibling = prefixTree2.copyTree();
            }
            return prefixTree;
        }

        protected boolean isEqual(char c, char c2) {
            boolean bl = c == c2;
            return bl;
        }

        public String match(CharSequence charSequence, int n, int n2) {
            if (!this.prefixOf(charSequence, n, n2)) {
                return null;
            }
            if (this.child != null && (n = this.key.length() + n) != n2) {
                PrefixTree prefixTree;
                PrefixTree prefixTree2 = this.child;
                do {
                    if (this.isEqual(prefixTree2.c0, charSequence.charAt(n))) {
                        if ((charSequence = prefixTree2.match(charSequence, n, n2)) != null) {
                            return charSequence;
                        }
                        return this.value;
                    }
                    prefixTree2 = prefixTree = prefixTree2.sibling;
                } while (prefixTree != null);
            }
            return this.value;
        }

        public String match(CharSequence charSequence, ParsePosition parsePosition) {
            int n;
            int n2 = parsePosition.getIndex();
            if (!this.prefixOf(charSequence, n2, n = charSequence.length())) {
                return null;
            }
            if (this.child != null && (n2 += this.key.length()) != n) {
                PrefixTree prefixTree;
                PrefixTree prefixTree2 = this.child;
                do {
                    if (this.isEqual(prefixTree2.c0, charSequence.charAt(n2))) {
                        parsePosition.setIndex(n2);
                        charSequence = prefixTree2.match(charSequence, parsePosition);
                        if (charSequence == null) break;
                        return charSequence;
                    }
                    prefixTree2 = prefixTree = prefixTree2.sibling;
                } while (prefixTree != null);
            }
            parsePosition.setIndex(n2);
            return this.value;
        }

        protected PrefixTree newNode(String string, String string2, PrefixTree prefixTree) {
            return new PrefixTree(string, string2, prefixTree);
        }

        protected boolean prefixOf(CharSequence charSequence, int n, int n2) {
            if (charSequence instanceof String) {
                return ((String)charSequence).startsWith(this.key, n);
            }
            int n3 = this.key.length();
            if (n3 > n2 - n) {
                return false;
            }
            int n4 = 0;
            n2 = n;
            n = n4;
            while (n3 > 0) {
                if (!this.isEqual(this.key.charAt(n), charSequence.charAt(n2))) {
                    return false;
                }
                ++n2;
                --n3;
                ++n;
            }
            return true;
        }

        protected String toKey(String string) {
            return string;
        }

        private static class CI
        extends PrefixTree {
            private CI(String string, String string2, PrefixTree prefixTree) {
                super(string, string2, prefixTree);
            }

            @Override
            protected boolean isEqual(char c, char c2) {
                return DateTimeParseContext.charEqualsIgnoreCase(c, c2);
            }

            @Override
            protected CI newNode(String string, String string2, PrefixTree prefixTree) {
                return new CI(string, string2, prefixTree);
            }

            @Override
            protected boolean prefixOf(CharSequence charSequence, int n, int n2) {
                int n3 = this.key.length();
                if (n3 > n2 - n) {
                    return false;
                }
                n2 = 0;
                int n4 = n;
                n = n2;
                n2 = n3;
                while (n2 > 0) {
                    if (!this.isEqual(this.key.charAt(n), charSequence.charAt(n4))) {
                        return false;
                    }
                    ++n4;
                    --n2;
                    ++n;
                }
                return true;
            }
        }

        private static class LENIENT
        extends CI {
            private LENIENT(String string, String string2, PrefixTree prefixTree) {
                super(string, string2, prefixTree);
            }

            private boolean isLenientChar(char c) {
                boolean bl = c == ' ' || c == '_' || c == '/';
                return bl;
            }

            @Override
            public String match(CharSequence charSequence, ParsePosition parsePosition) {
                int n = parsePosition.getIndex();
                int n2 = charSequence.length();
                int n3 = this.key.length();
                int n4 = 0;
                while (n4 < n3 && n < n2) {
                    if (this.isLenientChar(charSequence.charAt(n))) {
                        ++n;
                        continue;
                    }
                    if (!this.isEqual(this.key.charAt(n4), charSequence.charAt(n))) {
                        return null;
                    }
                    ++n;
                    ++n4;
                }
                if (n4 != n3) {
                    return null;
                }
                if (this.child != null && n != n2) {
                    for (n4 = n; n4 < n2 && this.isLenientChar(charSequence.charAt(n4)); ++n4) {
                    }
                    if (n4 < n2) {
                        PrefixTree prefixTree;
                        PrefixTree prefixTree2 = this.child;
                        do {
                            if (this.isEqual(prefixTree2.c0, charSequence.charAt(n4))) {
                                parsePosition.setIndex(n4);
                                charSequence = prefixTree2.match(charSequence, parsePosition);
                                if (charSequence == null) break;
                                return charSequence;
                            }
                            prefixTree2 = prefixTree = prefixTree2.sibling;
                        } while (prefixTree != null);
                    }
                }
                parsePosition.setIndex(n);
                return this.value;
            }

            @Override
            protected CI newNode(String string, String string2, PrefixTree prefixTree) {
                return new LENIENT(string, string2, prefixTree);
            }

            @Override
            protected String toKey(String string) {
                for (int i = 0; i < string.length(); ++i) {
                    if (!this.isLenientChar(string.charAt(i))) continue;
                    StringBuilder stringBuilder = new StringBuilder(string.length());
                    stringBuilder.append(string, 0, i);
                    ++i;
                    while (i < string.length()) {
                        if (!this.isLenientChar(string.charAt(i))) {
                            stringBuilder.append(string.charAt(i));
                        }
                        ++i;
                    }
                    return stringBuilder.toString();
                }
                return string;
            }
        }

    }

    static final class ReducedPrinterParser
    extends NumberPrinterParser {
        static final LocalDate BASE_DATE = LocalDate.of(2000, 1, 1);
        private final ChronoLocalDate baseDate;
        private final int baseValue;

        ReducedPrinterParser(TemporalField object, int n, int n2, int n3, ChronoLocalDate chronoLocalDate) {
            this((TemporalField)object, n, n2, n3, chronoLocalDate, 0);
            if (n >= 1 && n <= 10) {
                if (n2 >= 1 && n2 <= 10) {
                    if (n2 >= n) {
                        if (chronoLocalDate == null) {
                            if (object.range().isValidValue(n3)) {
                                if ((long)n3 + EXCEED_POINTS[n2] > Integer.MAX_VALUE) {
                                    throw new DateTimeException("Unable to add printer-parser as the range exceeds the capacity of an int");
                                }
                            } else {
                                throw new IllegalArgumentException("The base value must be within the range of the field");
                            }
                        }
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Maximum width must exceed or equal the minimum width but ");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append(" < ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("The maxWidth must be from 1 to 10 inclusive but was ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("The minWidth must be from 1 to 10 inclusive but was ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        private ReducedPrinterParser(TemporalField temporalField, int n, int n2, int n3, ChronoLocalDate chronoLocalDate, int n4) {
            super(temporalField, n, n2, SignStyle.NOT_NEGATIVE, n4);
            this.baseValue = n3;
            this.baseDate = chronoLocalDate;
        }

        @Override
        long getValue(DateTimePrintContext dateTimePrintContext, long l) {
            long l2 = Math.abs(l);
            int n = this.baseValue;
            if (this.baseDate != null) {
                n = Chronology.from(dateTimePrintContext.getTemporal()).date(this.baseDate).get(this.field);
            }
            if (l >= (long)n && l < (long)n + EXCEED_POINTS[this.minWidth]) {
                return l2 % EXCEED_POINTS[this.minWidth];
            }
            return l2 % EXCEED_POINTS[this.maxWidth];
        }

        @Override
        boolean isFixedWidth(DateTimeParseContext dateTimeParseContext) {
            if (!dateTimeParseContext.isStrict()) {
                return false;
            }
            return super.isFixedWidth(dateTimeParseContext);
        }

        public /* synthetic */ void lambda$setValue$0$DateTimeFormatterBuilder$ReducedPrinterParser(DateTimeParseContext dateTimeParseContext, long l, int n, int n2, Chronology chronology) {
            this.setValue(dateTimeParseContext, l, n, n2);
        }

        @Override
        int setValue(DateTimeParseContext dateTimeParseContext, long l, int n, int n2) {
            block2 : {
                int n3 = this.baseValue;
                if (this.baseDate != null) {
                    n3 = dateTimeParseContext.getEffectiveChronology().date(this.baseDate).get(this.field);
                    dateTimeParseContext.addChronoChangedListener(new _$$Lambda$DateTimeFormatterBuilder$ReducedPrinterParser$O7fxxUm4cHduGbldToNj0T92oIo(this, dateTimeParseContext, l, n, n2));
                }
                if (n2 - n != this.minWidth || l < 0L) break block2;
                long l2 = EXCEED_POINTS[this.minWidth];
                long l3 = n3;
                l3 = (long)n3 - l3 % l2;
                l = n3 > 0 ? l3 + l : l3 - l;
                if (l < (long)n3) {
                    l += l2;
                }
            }
            return dateTimeParseContext.setParsedField(this.field, l, n, n2);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ReducedValue(");
            stringBuilder.append(this.field);
            stringBuilder.append(",");
            stringBuilder.append(this.minWidth);
            stringBuilder.append(",");
            stringBuilder.append(this.maxWidth);
            stringBuilder.append(",");
            Comparable<ChronoLocalDate> comparable = this.baseDate;
            if (comparable == null) {
                comparable = this.baseValue;
            }
            stringBuilder.append(comparable);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        @Override
        ReducedPrinterParser withFixedWidth() {
            if (this.subsequentWidth == -1) {
                return this;
            }
            return new ReducedPrinterParser(this.field, this.minWidth, this.maxWidth, this.baseValue, this.baseDate, -1);
        }

        @Override
        ReducedPrinterParser withSubsequentWidth(int n) {
            return new ReducedPrinterParser(this.field, this.minWidth, this.maxWidth, this.baseValue, this.baseDate, this.subsequentWidth + n);
        }
    }

    static enum SettingsParser implements DateTimePrinterParser
    {
        SENSITIVE,
        INSENSITIVE,
        STRICT,
        LENIENT;
        

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            int n2 = this.ordinal();
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 == 3) {
                            dateTimeParseContext.setStrict(false);
                        }
                    } else {
                        dateTimeParseContext.setStrict(true);
                    }
                } else {
                    dateTimeParseContext.setCaseSensitive(false);
                }
            } else {
                dateTimeParseContext.setCaseSensitive(true);
            }
            return n;
        }

        public String toString() {
            int n = this.ordinal();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n == 3) {
                            return "ParseStrict(false)";
                        }
                        throw new IllegalStateException("Unreachable");
                    }
                    return "ParseStrict(true)";
                }
                return "ParseCaseSensitive(false)";
            }
            return "ParseCaseSensitive(true)";
        }
    }

    static final class StringLiteralPrinterParser
    implements DateTimePrinterParser {
        private final String literal;

        StringLiteralPrinterParser(String string) {
            this.literal = string;
        }

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            stringBuilder.append(this.literal);
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            if (n <= charSequence.length() && n >= 0) {
                String string = this.literal;
                if (!dateTimeParseContext.subSequenceEquals(charSequence, n, string, 0, string.length())) {
                    return n;
                }
                return this.literal.length() + n;
            }
            throw new IndexOutOfBoundsException();
        }

        public String toString() {
            String string = this.literal.replace("'", "''");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'");
            stringBuilder.append(string);
            stringBuilder.append("'");
            return stringBuilder.toString();
        }
    }

    static final class TextPrinterParser
    implements DateTimePrinterParser {
        private final TemporalField field;
        private volatile NumberPrinterParser numberPrinterParser;
        private final DateTimeTextProvider provider;
        private final TextStyle textStyle;

        TextPrinterParser(TemporalField temporalField, TextStyle textStyle, DateTimeTextProvider dateTimeTextProvider) {
            this.field = temporalField;
            this.textStyle = textStyle;
            this.provider = dateTimeTextProvider;
        }

        private NumberPrinterParser numberPrinterParser() {
            if (this.numberPrinterParser == null) {
                this.numberPrinterParser = new NumberPrinterParser(this.field, 1, 19, SignStyle.NORMAL);
            }
            return this.numberPrinterParser;
        }

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            Object object = dateTimePrintContext.getValue(this.field);
            if (object == null) {
                return false;
            }
            Chronology chronology = dateTimePrintContext.getTemporal().query(TemporalQueries.chronology());
            object = chronology != null && chronology != IsoChronology.INSTANCE ? this.provider.getText(chronology, this.field, (Long)object, this.textStyle, dateTimePrintContext.getLocale()) : this.provider.getText(this.field, (Long)object, this.textStyle, dateTimePrintContext.getLocale());
            if (object == null) {
                return this.numberPrinterParser().format(dateTimePrintContext, stringBuilder);
            }
            stringBuilder.append((String)object);
            return true;
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            int n2 = charSequence.length();
            if (n >= 0 && n <= n2) {
                Object object = dateTimeParseContext.isStrict() ? this.textStyle : null;
                Object object2 = dateTimeParseContext.getEffectiveChronology();
                object = object2 != null && object2 != IsoChronology.INSTANCE ? this.provider.getTextIterator((Chronology)object2, this.field, (TextStyle)((Object)object), dateTimeParseContext.getLocale()) : this.provider.getTextIterator(this.field, (TextStyle)((Object)object), dateTimeParseContext.getLocale());
                if (object != null) {
                    while (object.hasNext()) {
                        object2 = (Map.Entry)object.next();
                        String string = (String)object2.getKey();
                        if (!dateTimeParseContext.subSequenceEquals(string, 0, charSequence, n, string.length())) continue;
                        return dateTimeParseContext.setParsedField(this.field, (Long)object2.getValue(), n, n + string.length());
                    }
                    if (dateTimeParseContext.isStrict()) {
                        return n;
                    }
                }
                return this.numberPrinterParser().parse(dateTimeParseContext, charSequence, n);
            }
            throw new IndexOutOfBoundsException();
        }

        public String toString() {
            if (this.textStyle == TextStyle.FULL) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Text(");
                stringBuilder.append(this.field);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Text(");
            stringBuilder.append(this.field);
            stringBuilder.append(",");
            stringBuilder.append((Object)this.textStyle);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static final class WeekBasedFieldPrinterParser
    implements DateTimePrinterParser {
        private char chr;
        private int count;

        WeekBasedFieldPrinterParser(char c, int n) {
            this.chr = c;
            this.count = n;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private DateTimePrinterParser printerParser(Locale var1_1) {
            var1_2 = WeekFields.of((Locale)var1_1 /* !! */ );
            var2_10 = this.chr;
            if (var2_10 == 87) ** GOTO lbl20
            if (var2_10 != 89) {
                if (var2_10 != 99 && var2_10 != 101) {
                    if (var2_10 != 119) throw new IllegalStateException("unreachable");
                    var1_3 = var1_2.weekOfWeekBasedYear();
                } else {
                    var1_4 = var1_2.dayOfWeek();
                }
            } else {
                var3_11 = var1_2.weekBasedYear();
                var2_10 = this.count;
                if (var2_10 == 2) {
                    return new ReducedPrinterParser(var3_11, 2, 2, 0, ReducedPrinterParser.BASE_DATE, 0);
                }
                if (var2_10 < 4) {
                    var1_5 = SignStyle.NORMAL;
                    return new NumberPrinterParser(var3_11, var2_10, 19, (SignStyle)var1_7, -1);
                }
                var1_6 = SignStyle.EXCEEDS_PAD;
                return new NumberPrinterParser(var3_11, var2_10, 19, (SignStyle)var1_7, -1);
lbl20: // 1 sources:
                var1_8 = var1_2.weekOfMonth();
            }
            if (this.count == 2) {
                var2_10 = 2;
                return new NumberPrinterParser((TemporalField)var1_9, var2_10, 2, SignStyle.NOT_NEGATIVE);
            }
            var2_10 = 1;
            return new NumberPrinterParser((TemporalField)var1_9, var2_10, 2, SignStyle.NOT_NEGATIVE);
        }

        @Override
        public boolean format(DateTimePrintContext dateTimePrintContext, StringBuilder stringBuilder) {
            return this.printerParser(dateTimePrintContext.getLocale()).format(dateTimePrintContext, stringBuilder);
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            return this.printerParser(dateTimeParseContext.getLocale()).parse(dateTimeParseContext, charSequence, n);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(30);
            stringBuilder.append("Localized(");
            int n = this.chr;
            if (n == 89) {
                n = this.count;
                if (n == 1) {
                    stringBuilder.append("WeekBasedYear");
                } else if (n == 2) {
                    stringBuilder.append("ReducedValue(WeekBasedYear,2,2,2000-01-01)");
                } else {
                    stringBuilder.append("WeekBasedYear,");
                    stringBuilder.append(this.count);
                    stringBuilder.append(",");
                    stringBuilder.append(19);
                    stringBuilder.append(",");
                    SignStyle signStyle = this.count < 4 ? SignStyle.NORMAL : SignStyle.EXCEEDS_PAD;
                    stringBuilder.append((Object)signStyle);
                }
            } else {
                if (n != 87) {
                    if (n != 99 && n != 101) {
                        if (n == 119) {
                            stringBuilder.append("WeekOfWeekBasedYear");
                        }
                    } else {
                        stringBuilder.append("DayOfWeek");
                    }
                } else {
                    stringBuilder.append("WeekOfMonth");
                }
                stringBuilder.append(",");
                stringBuilder.append(this.count);
            }
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static class ZoneIdPrinterParser
    implements DateTimePrinterParser {
        private static volatile Map.Entry<Integer, PrefixTree> cachedPrefixTree;
        private static volatile Map.Entry<Integer, PrefixTree> cachedPrefixTreeCI;
        private final String description;
        private final TemporalQuery<ZoneId> query;

        ZoneIdPrinterParser(TemporalQuery<ZoneId> temporalQuery, String string) {
            this.query = temporalQuery;
            this.description = string;
        }

        private int parseOffsetBased(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n, int n2, OffsetIdPrinterParser offsetIdPrinterParser) {
            String string = charSequence.toString().substring(n, n2).toUpperCase();
            if (n2 >= charSequence.length()) {
                dateTimeParseContext.setParsed(ZoneId.of(string));
                return n2;
            }
            if (charSequence.charAt(n2) == '0' && string.equals("GMT")) {
                dateTimeParseContext.setParsed(ZoneId.of("GMT0"));
                return n2 + 1;
            }
            if (charSequence.charAt(n2) != '0' && !dateTimeParseContext.charEquals(charSequence.charAt(n2), 'Z')) {
                DateTimeParseContext dateTimeParseContext2 = dateTimeParseContext.copy();
                int n3 = offsetIdPrinterParser.parse(dateTimeParseContext2, charSequence, n2);
                if (n3 < 0) {
                    block8 : {
                        if (offsetIdPrinterParser != OffsetIdPrinterParser.INSTANCE_ID_Z) break block8;
                        return n;
                    }
                    dateTimeParseContext.setParsed(ZoneId.of(string));
                    return n2;
                }
                try {
                    dateTimeParseContext.setParsed(ZoneId.ofOffset(string, ZoneOffset.ofTotalSeconds((int)dateTimeParseContext2.getParsed(ChronoField.OFFSET_SECONDS).longValue())));
                    return n3;
                }
                catch (DateTimeException dateTimeException) {
                    return n;
                }
            }
            dateTimeParseContext.setParsed(ZoneId.of(string));
            return n2;
        }

        @Override
        public boolean format(DateTimePrintContext object, StringBuilder stringBuilder) {
            if ((object = ((DateTimePrintContext)object).getValue(this.query)) == null) {
                return false;
            }
            stringBuilder.append(((ZoneId)object).getId());
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected PrefixTree getTree(DateTimeParseContext dateTimeParseContext) {
            AbstractMap.SimpleImmutableEntry<Integer, PrefixTree> simpleImmutableEntry;
            block9 : {
                Map.Entry<Integer, PrefixTree> entry;
                block11 : {
                    int n;
                    Set<String> set;
                    block8 : {
                        set = ZoneRulesProvider.getAvailableZoneIds();
                        n = set.size();
                        entry = dateTimeParseContext.isCaseSensitive() ? cachedPrefixTree : cachedPrefixTreeCI;
                        if (entry == null) break block8;
                        simpleImmutableEntry = entry;
                        if (entry.getKey() == n) break block9;
                    }
                    synchronized (this) {
                        block10 : {
                            entry = dateTimeParseContext.isCaseSensitive() ? cachedPrefixTree : cachedPrefixTreeCI;
                            simpleImmutableEntry = entry;
                            if (simpleImmutableEntry == null) break block10;
                            entry = simpleImmutableEntry;
                            if ((Integer)simpleImmutableEntry.getKey() == n) break block11;
                        }
                        entry = new Map.Entry<Integer, PrefixTree>(n, PrefixTree.newTree(set, dateTimeParseContext));
                        if (dateTimeParseContext.isCaseSensitive()) {
                            cachedPrefixTree = entry;
                        } else {
                            cachedPrefixTreeCI = entry;
                        }
                    }
                }
                simpleImmutableEntry = entry;
            }
            return simpleImmutableEntry.getValue();
        }

        @Override
        public int parse(DateTimeParseContext dateTimeParseContext, CharSequence charSequence, int n) {
            int n2 = charSequence.length();
            if (n <= n2) {
                if (n == n2) {
                    return n;
                }
                char c = charSequence.charAt(n);
                if (c != '+' && c != '-') {
                    PrefixTree prefixTree;
                    ParsePosition parsePosition;
                    if (n2 >= n + 2) {
                        char c2 = charSequence.charAt(n + 1);
                        if (dateTimeParseContext.charEquals(c, 'U') && dateTimeParseContext.charEquals(c2, 'T')) {
                            if (n2 >= n + 3 && dateTimeParseContext.charEquals(charSequence.charAt(n + 2), 'C')) {
                                return this.parseOffsetBased(dateTimeParseContext, charSequence, n, n + 3, OffsetIdPrinterParser.INSTANCE_ID_ZERO);
                            }
                            return this.parseOffsetBased(dateTimeParseContext, charSequence, n, n + 2, OffsetIdPrinterParser.INSTANCE_ID_ZERO);
                        }
                        if (dateTimeParseContext.charEquals(c, 'G') && n2 >= n + 3 && dateTimeParseContext.charEquals(c2, 'M') && dateTimeParseContext.charEquals(charSequence.charAt(n + 2), 'T')) {
                            return this.parseOffsetBased(dateTimeParseContext, charSequence, n, n + 3, OffsetIdPrinterParser.INSTANCE_ID_ZERO);
                        }
                    }
                    if ((charSequence = (prefixTree = this.getTree(dateTimeParseContext)).match(charSequence, parsePosition = new ParsePosition(n))) == null) {
                        if (dateTimeParseContext.charEquals(c, 'Z')) {
                            dateTimeParseContext.setParsed(ZoneOffset.UTC);
                            return n + 1;
                        }
                        return n;
                    }
                    dateTimeParseContext.setParsed(ZoneId.of((String)charSequence));
                    return parsePosition.getIndex();
                }
                return this.parseOffsetBased(dateTimeParseContext, charSequence, n, n, OffsetIdPrinterParser.INSTANCE_ID_Z);
            }
            throw new IndexOutOfBoundsException();
        }

        public String toString() {
            return this.description;
        }
    }

    static final class ZoneTextPrinterParser
    extends ZoneIdPrinterParser {
        private static final int DST = 1;
        private static final TimeZoneNames.NameType[] FULL_TYPES;
        private static final int GENERIC = 2;
        private static final TimeZoneNames.NameType[] SHORT_TYPES;
        private static final int STD = 0;
        private static final TimeZoneNames.NameType[] TYPES;
        private static final Map<String, SoftReference<Map<Locale, String[]>>> cache;
        private final Map<Locale, Map.Entry<Integer, SoftReference<PrefixTree>>> cachedTree;
        private final Map<Locale, Map.Entry<Integer, SoftReference<PrefixTree>>> cachedTreeCI;
        private Set<String> preferredZones;
        private final TextStyle textStyle;

        static {
            TYPES = new TimeZoneNames.NameType[]{TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_DAYLIGHT, TimeZoneNames.NameType.LONG_GENERIC, TimeZoneNames.NameType.SHORT_GENERIC};
            FULL_TYPES = new TimeZoneNames.NameType[]{TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.LONG_GENERIC};
            SHORT_TYPES = new TimeZoneNames.NameType[]{TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT, TimeZoneNames.NameType.SHORT_GENERIC};
            cache = new ConcurrentHashMap<String, SoftReference<Map<Locale, String[]>>>();
        }

        ZoneTextPrinterParser(TextStyle object, Set<ZoneId> object2) {
            TemporalQuery<ZoneId> temporalQuery = TemporalQueries.zone();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ZoneText(");
            stringBuilder.append(object);
            stringBuilder.append(")");
            super(temporalQuery, stringBuilder.toString());
            this.cachedTree = new HashMap<Locale, Map.Entry<Integer, SoftReference<PrefixTree>>>();
            this.cachedTreeCI = new HashMap<Locale, Map.Entry<Integer, SoftReference<PrefixTree>>>();
            this.textStyle = Objects.requireNonNull(object, "textStyle");
            if (object2 != null && object2.size() != 0) {
                this.preferredZones = new HashSet<String>();
                object = object2.iterator();
                while (object.hasNext()) {
                    object2 = (ZoneId)object.next();
                    this.preferredZones.add(((ZoneId)object2).getId());
                }
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private String getDisplayName(String var1_1, int var2_2, Locale var3_3) {
            if (this.textStyle == TextStyle.NARROW) {
                return null;
            }
            var4_4 = ZoneTextPrinterParser.cache.get(var1_1);
            var5_5 = null;
            if (var4_4 == null) ** GOTO lbl14
            var6_6 = var4_4.get();
            var5_5 = var4_4 = var6_6;
            if (var6_6 == null) ** GOTO lbl14
            var5_5 = (String[])var4_4.get(var3_3);
            if (var5_5 != null) {
                var4_4 = var5_5;
            } else {
                var5_5 = var4_4;
lbl14: // 3 sources:
                var7_7 = TimeZoneNames.getInstance((Locale)var3_3);
                var6_6 = new String[ZoneTextPrinterParser.TYPES.length + 1];
                var6_6[0] = var1_1;
                var8_11 = ZoneMeta.getCanonicalCLDRID((String)var1_1);
                var9_12 = ZoneTextPrinterParser.TYPES;
                var10_13 = System.currentTimeMillis();
                var4_4 = var6_6;
                var7_7.getDisplayNames((String)var8_11, var9_12, var10_13, (String[])var6_6, 1);
                if (var4_4[1] == null || var4_4[2] == null || var4_4[3] == null || var4_4[4] == null) {
                    var6_6 = TimeZone.getTimeZone(var1_1);
                    var8_11 = TimeZone.createGmtOffsetString(true, true, var6_6.getRawOffset());
                    var6_6 = TimeZone.createGmtOffsetString(true, true, var6_6.getRawOffset() + var6_6.getDSTSavings());
                    if (var4_4[1] != null) {
                        var7_8 = var4_4[1];
                    } else {
                        var7_9 = var8_11;
                    }
                    var4_4[1] = var7_10;
                    if (var4_4[2] != null) {
                        var8_11 = var4_4[2];
                    }
                    var4_4[2] = var8_11;
                    var8_11 = var4_4[3] != null ? var4_4[3] : var6_6;
                    var4_4[3] = var8_11;
                    if (var4_4[4] != null) {
                        var6_6 = var4_4[4];
                    }
                    var4_4[4] = var6_6;
                }
                if (var4_4[5] == null) {
                    var4_4[5] = var4_4[0];
                }
                if (var4_4[6] == null) {
                    var4_4[6] = var4_4[0];
                }
                var6_6 = var5_5;
                if (var5_5 == null) {
                    var6_6 = new ConcurrentHashMap<K, V>();
                }
                var6_6.put(var3_3, var4_4);
                ZoneTextPrinterParser.cache.put(var1_1, new SoftReference<Object>(var6_6));
            }
            if (var2_2 == 0) return var4_4[this.textStyle.zoneNameStyleIndex() + 1];
            if (var2_2 == 1) return var4_4[this.textStyle.zoneNameStyleIndex() + 3];
            return var4_4[this.textStyle.zoneNameStyleIndex() + 5];
        }

        @Override
        public boolean format(DateTimePrintContext object, StringBuilder stringBuilder) {
            ZoneId zoneId = ((DateTimePrintContext)object).getValue(TemporalQueries.zoneId());
            int n = 0;
            if (zoneId == null) {
                return false;
            }
            String string = zoneId.getId();
            Object object2 = string;
            if (!(zoneId instanceof ZoneOffset)) {
                object2 = ((DateTimePrintContext)object).getTemporal();
                if (object2.isSupported(ChronoField.INSTANT_SECONDS)) {
                    if (zoneId.getRules().isDaylightSavings(Instant.from((TemporalAccessor)object2))) {
                        n = 1;
                    }
                } else {
                    n = 2;
                }
                object = this.getDisplayName(string, n, ((DateTimePrintContext)object).getLocale());
                object2 = string;
                if (object != null) {
                    object2 = object;
                }
            }
            stringBuilder.append((String)object2);
            return true;
        }

        @Override
        protected PrefixTree getTree(DateTimeParseContext arrobject) {
            Object object;
            block10 : {
                Locale locale;
                int n;
                ZoneTextPrinterParser zoneTextPrinterParser;
                Map<Locale, Map.Entry<Integer, SoftReference<PrefixTree>>> map;
                Set<String> set;
                Object object2;
                int n2;
                block9 : {
                    zoneTextPrinterParser = this;
                    if (zoneTextPrinterParser.textStyle == TextStyle.NARROW) {
                        return super.getTree((DateTimeParseContext)arrobject);
                    }
                    locale = arrobject.getLocale();
                    boolean bl = arrobject.isCaseSensitive();
                    set = ZoneRulesProvider.getAvailableZoneIds();
                    n2 = set.size();
                    map = bl ? zoneTextPrinterParser.cachedTree : zoneTextPrinterParser.cachedTreeCI;
                    object = map.get(locale);
                    if (object == null || object.getKey() != n2) break block9;
                    object2 = object.getValue().get();
                    object = object2;
                    if (object2 != null) break block10;
                }
                PrefixTree prefixTree = PrefixTree.newTree((DateTimeParseContext)arrobject);
                TimeZoneNames timeZoneNames = TimeZoneNames.getInstance((Locale)locale);
                long l = System.currentTimeMillis();
                arrobject = zoneTextPrinterParser.textStyle == TextStyle.FULL ? FULL_TYPES : SHORT_TYPES;
                object2 = new String[arrobject.length];
                Iterator<String> iterator = set.iterator();
                object = arrobject;
                arrobject = object2;
                while (iterator.hasNext()) {
                    object2 = iterator.next();
                    prefixTree.add((String)object2, (String)object2);
                    String string = ZoneName.toZid((String)object2, locale);
                    object2 = string;
                    Object[] arrobject2 = arrobject;
                    timeZoneNames.getDisplayNames(string, (TimeZoneNames.NameType[])object, l, (String[])arrobject, 0);
                    arrobject = arrobject2;
                    for (n = 0; n < arrobject.length; ++n) {
                        if (arrobject[n] == null) continue;
                        prefixTree.add((String)arrobject[n], (String)object2);
                    }
                }
                if (zoneTextPrinterParser.preferredZones != null) {
                    for (String string : set) {
                        if (!this.preferredZones.contains(string)) continue;
                        timeZoneNames.getDisplayNames(ZoneName.toZid(string, locale), (TimeZoneNames.NameType[])object, l, (String[])arrobject, 0);
                        for (n = 0; n < arrobject.length; ++n) {
                            if (arrobject[n] == null) continue;
                            prefixTree.add((String)arrobject[n], string);
                        }
                    }
                }
                map.put(locale, new AbstractMap.SimpleImmutableEntry<Integer, SoftReference<PrefixTree>>(n2, new SoftReference<PrefixTree>(prefixTree)));
                object = prefixTree;
            }
            return object;
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CacheBase;
import android.icu.impl.DontCareFieldPosition;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.SoftCache;
import android.icu.impl.StandardPlural;
import android.icu.impl.UResource;
import android.icu.lang.UCharacter;
import android.icu.text.BreakIterator;
import android.icu.text.DateFormatSymbols;
import android.icu.text.DisplayContext;
import android.icu.text.MessageFormat;
import android.icu.text.MessagePattern;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.text.QuantityFormatter;
import android.icu.util.ICUException;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.text.FieldPosition;
import java.util.EnumMap;
import java.util.Locale;

public final class RelativeDateTimeFormatter {
    private static final Cache cache;
    private static final Style[] fallbackCache;
    private final BreakIterator breakIterator;
    private final DisplayContext capitalizationContext;
    private final String combinedDateAndTime;
    private final DateFormatSymbols dateFormatSymbols;
    private final ULocale locale;
    private final NumberFormat numberFormat;
    private final EnumMap<Style, EnumMap<RelativeUnit, String[][]>> patternMap;
    private final PluralRules pluralRules;
    private final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
    private final Style style;
    private int[] styleToDateFormatSymbolsWidth = new int[]{1, 3, 2};

    static {
        fallbackCache = new Style[3];
        cache = new Cache();
    }

    private RelativeDateTimeFormatter(EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> enumMap, EnumMap<Style, EnumMap<RelativeUnit, String[][]>> enumMap2, String string, PluralRules pluralRules, NumberFormat numberFormat, Style style, DisplayContext displayContext, BreakIterator breakIterator, ULocale uLocale) {
        this.qualitativeUnitMap = enumMap;
        this.patternMap = enumMap2;
        this.combinedDateAndTime = string;
        this.pluralRules = pluralRules;
        this.numberFormat = numberFormat;
        this.style = style;
        if (displayContext.type() == DisplayContext.Type.CAPITALIZATION) {
            this.capitalizationContext = displayContext;
            this.breakIterator = breakIterator;
            this.locale = uLocale;
            this.dateFormatSymbols = new DateFormatSymbols(uLocale);
            return;
        }
        throw new IllegalArgumentException(displayContext.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String adjustForContext(String string) {
        if (this.breakIterator == null) return string;
        if (string.length() == 0) return string;
        if (!UCharacter.isLowerCase(UCharacter.codePointAt(string, 0))) {
            return string;
        }
        BreakIterator breakIterator = this.breakIterator;
        synchronized (breakIterator) {
            return UCharacter.toTitleCase(this.locale, string, this.breakIterator, 768);
        }
    }

    private String getAbsoluteUnitString(Style object, AbsoluteUnit absoluteUnit, Direction direction) {
        Object object2;
        do {
            if ((object2 = this.qualitativeUnitMap.get(object)) != null && (object2 = ((EnumMap)object2).get((Object)absoluteUnit)) != null && (object2 = (String)((EnumMap)object2).get((Object)direction)) != null) {
                return object2;
            }
            object2 = fallbackCache[object.ordinal()];
            object = object2;
        } while (object2 != null);
        return null;
    }

    public static RelativeDateTimeFormatter getInstance() {
        return RelativeDateTimeFormatter.getInstance(ULocale.getDefault(), null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale) {
        return RelativeDateTimeFormatter.getInstance(uLocale, null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale, NumberFormat numberFormat) {
        return RelativeDateTimeFormatter.getInstance(uLocale, numberFormat, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale uLocale, NumberFormat numberFormat, Style style, DisplayContext displayContext) {
        Object object = cache.get(uLocale);
        numberFormat = numberFormat == null ? NumberFormat.getInstance(uLocale) : (NumberFormat)numberFormat.clone();
        EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> enumMap = ((RelativeDateTimeFormatterData)object).qualitativeUnitMap;
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> enumMap2 = ((RelativeDateTimeFormatterData)object).relUnitPatternMap;
        String string = ((RelativeDateTimeFormatterData)object).dateTimePattern;
        PluralRules pluralRules = PluralRules.forLocale(uLocale);
        object = displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE ? BreakIterator.getSentenceInstance(uLocale) : null;
        return new RelativeDateTimeFormatter(enumMap, enumMap2, string, pluralRules, numberFormat, style, displayContext, (BreakIterator)object, uLocale);
    }

    public static RelativeDateTimeFormatter getInstance(Locale locale) {
        return RelativeDateTimeFormatter.getInstance(ULocale.forLocale(locale));
    }

    public static RelativeDateTimeFormatter getInstance(Locale locale, NumberFormat numberFormat) {
        return RelativeDateTimeFormatter.getInstance(ULocale.forLocale(locale), numberFormat);
    }

    private String getRelativeUnitPattern(Style object, RelativeUnit relativeUnit, int n, StandardPlural object2) {
        int n2 = object2.ordinal();
        do {
            if ((object2 = this.patternMap.get(object)) != null && (object2 = (String[][])((EnumMap)object2).get((Object)relativeUnit)) != null && object2[n][n2] != null) {
                return object2[n][n2];
            }
            object2 = fallbackCache[object.ordinal()];
            object = object2;
        } while (object2 != null);
        return null;
    }

    private String getRelativeUnitPluralPattern(Style style, RelativeUnit relativeUnit, int n, StandardPlural object) {
        if (object != StandardPlural.OTHER && (object = this.getRelativeUnitPattern(style, relativeUnit, n, (StandardPlural)((Object)object))) != null) {
            return object;
        }
        return this.getRelativeUnitPattern(style, relativeUnit, n, StandardPlural.OTHER);
    }

    private static Direction keyToDirection(UResource.Key key) {
        if (key.contentEquals("-2")) {
            return Direction.LAST_2;
        }
        if (key.contentEquals("-1")) {
            return Direction.LAST;
        }
        if (key.contentEquals("0")) {
            return Direction.THIS;
        }
        if (key.contentEquals("1")) {
            return Direction.NEXT;
        }
        if (key.contentEquals("2")) {
            return Direction.NEXT_2;
        }
        return null;
    }

    public String combineDateAndTime(String string, String string2) {
        MessageFormat messageFormat = new MessageFormat("");
        messageFormat.applyPattern(this.combinedDateAndTime, MessagePattern.ApostropheMode.DOUBLE_REQUIRED);
        StringBuffer stringBuffer = new StringBuffer(128);
        FieldPosition fieldPosition = new FieldPosition(0);
        return messageFormat.format(new Object[]{string2, string}, stringBuffer, fieldPosition).toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String format(double d, Direction object, RelativeUnit object2) {
        if (object != Direction.LAST && object != Direction.NEXT) {
            throw new IllegalArgumentException("direction must be NEXT or LAST");
        }
        int n = object == Direction.NEXT ? 1 : 0;
        object = this.numberFormat;
        synchronized (object) {
            StringBuffer stringBuffer = new StringBuffer();
            Object object3 = DontCareFieldPosition.INSTANCE;
            object3 = QuantityFormatter.selectPlural(d, this.numberFormat, this.pluralRules, stringBuffer, object3);
            object2 = SimpleFormatterImpl.formatCompiledPattern(this.getRelativeUnitPluralPattern(this.style, (RelativeUnit)((Object)object2), n, (StandardPlural)((Object)object3)), stringBuffer);
            return this.adjustForContext((String)object2);
        }
    }

    public String format(double d, RelativeDateTimeUnit relativeDateTimeUnit) {
        int n = 1;
        Object object = Direction.THIS;
        int n2 = n;
        Object object2 = object;
        if (d > -2.1) {
            n2 = n;
            object2 = object;
            if (d < 2.1) {
                double d2 = 100.0 * d;
                n2 = d2 < 0.0 ? (int)(d2 - 0.5) : (int)(0.5 + d2);
                if (n2 != -200) {
                    if (n2 != -100) {
                        if (n2 != 0) {
                            if (n2 != 100) {
                                if (n2 != 200) {
                                    n2 = n;
                                    object2 = object;
                                } else {
                                    object2 = Direction.NEXT_2;
                                    n2 = 0;
                                }
                            } else {
                                object2 = Direction.NEXT;
                                n2 = 0;
                            }
                        } else {
                            n2 = 0;
                            object2 = object;
                        }
                    } else {
                        object2 = Direction.LAST;
                        n2 = 0;
                    }
                } else {
                    object2 = Direction.LAST_2;
                    n2 = 0;
                }
            }
        }
        object = AbsoluteUnit.NOW;
        switch (relativeDateTimeUnit) {
            default: {
                n2 = 1;
                break;
            }
            case SATURDAY: {
                object = AbsoluteUnit.SATURDAY;
                break;
            }
            case FRIDAY: {
                object = AbsoluteUnit.FRIDAY;
                break;
            }
            case THURSDAY: {
                object = AbsoluteUnit.THURSDAY;
                break;
            }
            case WEDNESDAY: {
                object = AbsoluteUnit.WEDNESDAY;
                break;
            }
            case TUESDAY: {
                object = AbsoluteUnit.TUESDAY;
                break;
            }
            case MONDAY: {
                object = AbsoluteUnit.MONDAY;
                break;
            }
            case SUNDAY: {
                object = AbsoluteUnit.SUNDAY;
                break;
            }
            case SECOND: {
                if (object2 == Direction.THIS) {
                    object2 = Direction.PLAIN;
                    break;
                }
                n2 = 1;
                break;
            }
            case DAY: {
                object = AbsoluteUnit.DAY;
                break;
            }
            case WEEK: {
                object = AbsoluteUnit.WEEK;
                break;
            }
            case MONTH: {
                object = AbsoluteUnit.MONTH;
                break;
            }
            case QUARTER: {
                object = AbsoluteUnit.QUARTER;
                break;
            }
            case YEAR: {
                object = AbsoluteUnit.YEAR;
            }
        }
        if (n2 == 0 && (object = this.format((Direction)((Object)object2), (AbsoluteUnit)((Object)object))) != null && ((String)object).length() > 0) {
            return object;
        }
        return this.formatNumeric(d, relativeDateTimeUnit);
    }

    public String format(Direction object, AbsoluteUnit absoluteUnit) {
        if (absoluteUnit == AbsoluteUnit.NOW && object != Direction.PLAIN) {
            throw new IllegalArgumentException("NOW can only accept direction PLAIN.");
        }
        if (object == Direction.PLAIN && AbsoluteUnit.SUNDAY.ordinal() <= absoluteUnit.ordinal() && absoluteUnit.ordinal() <= AbsoluteUnit.SATURDAY.ordinal()) {
            int n = absoluteUnit.ordinal();
            int n2 = AbsoluteUnit.SUNDAY.ordinal();
            object = this.dateFormatSymbols.getWeekdays(1, this.styleToDateFormatSymbolsWidth[this.style.ordinal()])[n - n2 + 1];
        } else {
            object = this.getAbsoluteUnitString(this.style, absoluteUnit, (Direction)((Object)object));
        }
        object = object != null ? this.adjustForContext((String)object) : null;
        return object;
    }

    public String formatNumeric(double d, RelativeDateTimeUnit object) {
        Enum enum_ = RelativeUnit.SECONDS;
        switch (1.$SwitchMap$android$icu$text$RelativeDateTimeFormatter$RelativeDateTimeUnit[((Enum)object).ordinal()]) {
            default: {
                throw new UnsupportedOperationException("formatNumeric does not currently support RelativeUnit.SUNDAY..SATURDAY");
            }
            case 8: {
                object = enum_;
                break;
            }
            case 7: {
                object = RelativeUnit.MINUTES;
                break;
            }
            case 6: {
                object = RelativeUnit.HOURS;
                break;
            }
            case 5: {
                object = RelativeUnit.DAYS;
                break;
            }
            case 4: {
                object = RelativeUnit.WEEKS;
                break;
            }
            case 3: {
                object = RelativeUnit.MONTHS;
                break;
            }
            case 2: {
                object = RelativeUnit.QUARTERS;
                break;
            }
            case 1: {
                object = RelativeUnit.YEARS;
            }
        }
        enum_ = Direction.NEXT;
        double d2 = d;
        if (Double.compare(d, 0.0) < 0) {
            enum_ = Direction.LAST;
            d2 = -d;
        }
        if ((object = this.format(d2, (Direction)enum_, (RelativeUnit)((Object)object))) == null) {
            object = "";
        }
        return object;
    }

    public DisplayContext getCapitalizationContext() {
        return this.capitalizationContext;
    }

    public Style getFormatStyle() {
        return this.style;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public NumberFormat getNumberFormat() {
        NumberFormat numberFormat = this.numberFormat;
        synchronized (numberFormat) {
            return (NumberFormat)this.numberFormat.clone();
        }
    }

    public static enum AbsoluteUnit {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        DAY,
        WEEK,
        MONTH,
        YEAR,
        NOW,
        QUARTER;
        
    }

    private static class Cache {
        private final CacheBase<String, RelativeDateTimeFormatterData, ULocale> cache = new SoftCache<String, RelativeDateTimeFormatterData, ULocale>(){

            @Override
            protected RelativeDateTimeFormatterData createInstance(String string, ULocale uLocale) {
                return new Loader(uLocale).load();
            }
        };

        private Cache() {
        }

        public RelativeDateTimeFormatterData get(ULocale uLocale) {
            String string = uLocale.toString();
            return this.cache.getInstance(string, uLocale);
        }

    }

    public static enum Direction {
        LAST_2,
        LAST,
        THIS,
        NEXT,
        NEXT_2,
        PLAIN;
        
    }

    private static class Loader {
        private final ULocale ulocale;

        public Loader(ULocale uLocale) {
            this.ulocale = uLocale;
        }

        private String getDateTimePattern(ICUResourceBundle iCUResourceBundle) {
            String string;
            Object object;
            block8 : {
                block7 : {
                    object = iCUResourceBundle.getStringWithFallback("calendar/default");
                    if (object == null) break block7;
                    string = object;
                    if (!((String)object).equals("")) break block8;
                }
                string = "gregorian";
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("calendar/");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("/DateTimePatterns");
            ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.findWithFallback(((StringBuilder)object).toString());
            object = iCUResourceBundle2;
            if (iCUResourceBundle2 == null) {
                object = iCUResourceBundle2;
                if (string.equals("gregorian")) {
                    object = iCUResourceBundle.findWithFallback("calendar/gregorian/DateTimePatterns");
                }
            }
            if (object != null && ((UResourceBundle)object).getSize() >= 9) {
                if (((UResourceBundle)object).get(8).getType() == 8) {
                    return ((UResourceBundle)object).get(8).getString(0);
                }
                return ((UResourceBundle)object).getString(8);
            }
            return "{1} {0}";
        }

        public RelativeDateTimeFormatterData load() {
            RelDateTimeDataSink relDateTimeDataSink = new RelDateTimeDataSink();
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", this.ulocale);
            iCUResourceBundle.getAllItemsWithFallback("fields", relDateTimeDataSink);
            for (Style style : Style.values()) {
                style = fallbackCache[style.ordinal()];
                if (style == null || (style = fallbackCache[style.ordinal()]) == null || fallbackCache[style.ordinal()] == null) continue;
                throw new IllegalStateException("Style fallback too deep");
            }
            return new RelativeDateTimeFormatterData(relDateTimeDataSink.qualitativeUnitMap, relDateTimeDataSink.styleRelUnitPatterns, this.getDateTimePattern(iCUResourceBundle));
        }
    }

    private static final class RelDateTimeDataSink
    extends UResource.Sink {
        int pastFutureIndex;
        EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap = new EnumMap(Style.class);
        StringBuilder sb = new StringBuilder();
        Style style;
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> styleRelUnitPatterns = new EnumMap(Style.class);
        DateTimeUnit unit;

        RelDateTimeDataSink() {
        }

        private void handleAlias(UResource.Key charSequence, UResource.Value object, boolean bl) {
            block2 : {
                Style style;
                block3 : {
                    block6 : {
                        block5 : {
                            block4 : {
                                style = this.styleFromKey((UResource.Key)charSequence);
                                if (DateTimeUnit.orNullFromString(((UResource.Key)charSequence).substring(0, ((UResource.Key)charSequence).length() - RelDateTimeDataSink.styleSuffixLength(style))) == null) break block2;
                                if (style == (object = this.styleFromAlias((UResource.Value)object))) break block3;
                                if (fallbackCache[style.ordinal()] != null) break block4;
                                RelativeDateTimeFormatter.access$200()[style.ordinal()] = object;
                                break block5;
                            }
                            if (fallbackCache[style.ordinal()] != object) break block6;
                        }
                        return;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Inconsistent style fallback for style ");
                    ((StringBuilder)charSequence).append((Object)style);
                    ((StringBuilder)charSequence).append(" to ");
                    ((StringBuilder)charSequence).append(object);
                    throw new ICUException(((StringBuilder)charSequence).toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid style fallback from ");
                ((StringBuilder)charSequence).append((Object)style);
                ((StringBuilder)charSequence).append(" to itself");
                throw new ICUException(((StringBuilder)charSequence).toString());
            }
        }

        private void handlePlainDirection(UResource.Key cloneable, UResource.Value value) {
            EnumMap<Direction, String> enumMap;
            AbsoluteUnit absoluteUnit = this.unit.absUnit;
            if (absoluteUnit == null) {
                return;
            }
            EnumMap<Direction, String> enumMap2 = this.qualitativeUnitMap.get((Object)this.style);
            cloneable = enumMap2;
            if (enumMap2 == null) {
                cloneable = new EnumMap(AbsoluteUnit.class);
                this.qualitativeUnitMap.put(this.style, (EnumMap<AbsoluteUnit, EnumMap<Direction, String>>)cloneable);
            }
            enumMap2 = enumMap = (EnumMap<Direction, String>)((EnumMap)cloneable).get((Object)absoluteUnit);
            if (enumMap == null) {
                enumMap2 = new EnumMap<Direction, String>(Direction.class);
                ((EnumMap)cloneable).put(absoluteUnit, enumMap2);
            }
            if (enumMap2.get((Object)Direction.PLAIN) == null) {
                enumMap2.put(Direction.PLAIN, value.toString());
            }
        }

        private Style styleFromAlias(UResource.Value object) {
            if (((String)(object = ((UResource.Value)object).getAliasString())).endsWith("-short")) {
                return Style.SHORT;
            }
            if (((String)object).endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }

        private Style styleFromKey(UResource.Key key) {
            if (key.endsWith("-short")) {
                return Style.SHORT;
            }
            if (key.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }

        private static int styleSuffixLength(Style style) {
            int n = 1.$SwitchMap$android$icu$text$RelativeDateTimeFormatter$Style[style.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return 0;
                }
                return 7;
            }
            return 6;
        }

        public void consumeTableRelative(UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (value.getType() == 0) {
                    EnumMap<Direction, EnumMap<Direction, String>> enumMap;
                    EnumMap<Enum, EnumMap<Direction, EnumMap<Direction, String>>> enumMap2;
                    Object object = value.getString();
                    EnumMap<Direction, EnumMap<Direction, String>> enumMap3 = this.qualitativeUnitMap.get((Object)this.style);
                    if (this.unit.relUnit == RelativeUnit.SECONDS && key.contentEquals("0")) {
                        enumMap = enumMap3.get((Object)AbsoluteUnit.NOW);
                        enumMap2 = enumMap;
                        if (enumMap == null) {
                            enumMap2 = new EnumMap(Direction.class);
                            enumMap3.put((Direction)((Object)AbsoluteUnit.NOW), (EnumMap<Direction, String>)enumMap2);
                        }
                        if (enumMap2.get((Object)Direction.PLAIN) == null) {
                            enumMap2.put(Direction.PLAIN, (EnumMap<Direction, EnumMap<Direction, String>>)object);
                        }
                    } else {
                        Direction direction = RelativeDateTimeFormatter.keyToDirection(key);
                        if (direction != null && (object = this.unit.absUnit) != null) {
                            enumMap2 = enumMap3;
                            if (enumMap3 == null) {
                                enumMap2 = new EnumMap(AbsoluteUnit.class);
                                this.qualitativeUnitMap.put(this.style, (EnumMap<AbsoluteUnit, EnumMap<Direction, String>>)enumMap2);
                            }
                            enumMap3 = enumMap = (EnumMap<Direction, EnumMap<Direction, String>>)enumMap2.get(object);
                            if (enumMap == null) {
                                enumMap3 = new EnumMap<Direction, EnumMap<Direction, String>>(Direction.class);
                                enumMap2.put((Direction)((Object)object), enumMap3);
                            }
                            if (enumMap3.get((Object)direction) == null) {
                                enumMap3.put(direction, (EnumMap<Direction, String>)((Object)value.getString()));
                            }
                        }
                    }
                }
                ++n;
            }
        }

        public void consumeTableRelativeTime(UResource.Key key, UResource.Value value) {
            if (this.unit.relUnit == null) {
                return;
            }
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                block7 : {
                    block6 : {
                        block5 : {
                            if (!key.contentEquals("past")) break block5;
                            this.pastFutureIndex = 0;
                            break block6;
                        }
                        if (!key.contentEquals("future")) break block7;
                        this.pastFutureIndex = 1;
                    }
                    this.consumeTimeDetail(key, value);
                }
                ++n;
            }
        }

        public void consumeTimeDetail(UResource.Key key, UResource.Value value) {
            String[][] arrstring;
            UResource.Table table = value.getTable();
            String[][] arrstring2 = this.styleRelUnitPatterns.get((Object)this.style);
            Object object = arrstring2;
            if (arrstring2 == null) {
                object = new EnumMap(RelativeUnit.class);
                this.styleRelUnitPatterns.put(this.style, (EnumMap<RelativeUnit, String[][]>)object);
            }
            arrstring2 = arrstring = ((EnumMap)object).get((Object)this.unit.relUnit);
            if (arrstring == null) {
                arrstring2 = new String[2][StandardPlural.COUNT];
                ((EnumMap)object).put(this.unit.relUnit, arrstring2);
            }
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2;
                int n3;
                if (value.getType() == 0 && arrstring2[n3 = this.pastFutureIndex][n2 = StandardPlural.indexFromString(key.toString())] == null) {
                    arrstring2[n3][n2] = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 0, 1);
                }
                ++n;
            }
        }

        public void consumeTimeUnit(UResource.Key key, UResource.Value value) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (key.contentEquals("dn") && value.getType() == 0) {
                    this.handlePlainDirection(key, value);
                }
                if (value.getType() == 2) {
                    if (key.contentEquals("relative")) {
                        this.consumeTableRelative(key, value);
                    } else if (key.contentEquals("relativeTime")) {
                        this.consumeTableRelativeTime(key, value);
                    }
                }
                ++n;
            }
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            if (value.getType() == 3) {
                return;
            }
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (value.getType() == 3) {
                    this.handleAlias(key, value, bl);
                } else {
                    this.style = this.styleFromKey(key);
                    this.unit = DateTimeUnit.orNullFromString(key.substring(0, key.length() - RelDateTimeDataSink.styleSuffixLength(this.style)));
                    if (this.unit != null) {
                        this.consumeTimeUnit(key, value);
                    }
                }
                ++n;
            }
        }

        private static enum DateTimeUnit {
            SECOND(RelativeUnit.SECONDS, null),
            MINUTE(RelativeUnit.MINUTES, null),
            HOUR(RelativeUnit.HOURS, null),
            DAY(RelativeUnit.DAYS, AbsoluteUnit.DAY),
            WEEK(RelativeUnit.WEEKS, AbsoluteUnit.WEEK),
            MONTH(RelativeUnit.MONTHS, AbsoluteUnit.MONTH),
            QUARTER(RelativeUnit.QUARTERS, AbsoluteUnit.QUARTER),
            YEAR(RelativeUnit.YEARS, AbsoluteUnit.YEAR),
            SUNDAY(null, AbsoluteUnit.SUNDAY),
            MONDAY(null, AbsoluteUnit.MONDAY),
            TUESDAY(null, AbsoluteUnit.TUESDAY),
            WEDNESDAY(null, AbsoluteUnit.WEDNESDAY),
            THURSDAY(null, AbsoluteUnit.THURSDAY),
            FRIDAY(null, AbsoluteUnit.FRIDAY),
            SATURDAY(null, AbsoluteUnit.SATURDAY);
            
            AbsoluteUnit absUnit;
            RelativeUnit relUnit;

            private DateTimeUnit(RelativeUnit relativeUnit, AbsoluteUnit absoluteUnit) {
                this.relUnit = relativeUnit;
                this.absUnit = absoluteUnit;
            }

            private static final DateTimeUnit orNullFromString(CharSequence charSequence) {
                int n = charSequence.length();
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            if (n != 6) {
                                if (n == 7 && "quarter".contentEquals(charSequence)) {
                                    return QUARTER;
                                }
                            } else {
                                if ("minute".contentEquals(charSequence)) {
                                    return MINUTE;
                                }
                                if ("second".contentEquals(charSequence)) {
                                    return SECOND;
                                }
                            }
                        } else if ("month".contentEquals(charSequence)) {
                            return MONTH;
                        }
                    } else {
                        if ("hour".contentEquals(charSequence)) {
                            return HOUR;
                        }
                        if ("week".contentEquals(charSequence)) {
                            return WEEK;
                        }
                        if ("year".contentEquals(charSequence)) {
                            return YEAR;
                        }
                    }
                } else {
                    if ("day".contentEquals(charSequence)) {
                        return DAY;
                    }
                    if ("sun".contentEquals(charSequence)) {
                        return SUNDAY;
                    }
                    if ("mon".contentEquals(charSequence)) {
                        return MONDAY;
                    }
                    if ("tue".contentEquals(charSequence)) {
                        return TUESDAY;
                    }
                    if ("wed".contentEquals(charSequence)) {
                        return WEDNESDAY;
                    }
                    if ("thu".contentEquals(charSequence)) {
                        return THURSDAY;
                    }
                    if ("fri".contentEquals(charSequence)) {
                        return FRIDAY;
                    }
                    if ("sat".contentEquals(charSequence)) {
                        return SATURDAY;
                    }
                }
                return null;
            }
        }

    }

    private static class RelativeDateTimeFormatterData {
        public final String dateTimePattern;
        public final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> relUnitPatternMap;

        public RelativeDateTimeFormatterData(EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> enumMap, EnumMap<Style, EnumMap<RelativeUnit, String[][]>> enumMap2, String string) {
            this.qualitativeUnitMap = enumMap;
            this.relUnitPatternMap = enumMap2;
            this.dateTimePattern = string;
        }
    }

    public static enum RelativeDateTimeUnit {
        YEAR,
        QUARTER,
        MONTH,
        WEEK,
        DAY,
        HOUR,
        MINUTE,
        SECOND,
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY;
        
    }

    public static enum RelativeUnit {
        SECONDS,
        MINUTES,
        HOURS,
        DAYS,
        WEEKS,
        MONTHS,
        YEARS,
        QUARTERS;
        
    }

    public static enum Style {
        LONG,
        SHORT,
        NARROW;
        
        private static final int INDEX_COUNT = 3;
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.DontCareFieldPosition;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleCache;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.number.LongNameHandler;
import android.icu.number.FormattedNumber;
import android.icu.number.LocalizedNumberFormatter;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.text.CurrencyFormat;
import android.icu.text.DateFormat;
import android.icu.text.DecimalFormat;
import android.icu.text.ListFormatter;
import android.icu.text.NumberFormat;
import android.icu.text.NumberingSystem;
import android.icu.text.PluralRules;
import android.icu.text.SimpleDateFormat;
import android.icu.text.TimeUnitFormat;
import android.icu.text.UFormat;
import android.icu.util.Currency;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.icu.util.TimeUnit;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;

public class MeasureFormat
extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CURRENCY_FORMAT = 2;
    private static final int MEASURE_FORMAT = 0;
    static final int NUMBER_FORMATTER_CURRENCY = 2;
    static final int NUMBER_FORMATTER_INTEGER = 3;
    static final int NUMBER_FORMATTER_STANDARD = 1;
    private static final int TIME_UNIT_FORMAT = 1;
    private static final Map<MeasureUnit, Integer> hmsTo012;
    private static final Map<ULocale, String> localeIdToRangeFormat;
    private static final SimpleCache<ULocale, NumericFormatters> localeToNumericDurationFormatters;
    static final long serialVersionUID = -7182021401701778240L;
    private final transient FormatWidth formatWidth;
    private transient NumberFormatterCacheEntry formatter1 = null;
    private transient NumberFormatterCacheEntry formatter2 = null;
    private transient NumberFormatterCacheEntry formatter3 = null;
    private final transient NumberFormat numberFormat;
    private final transient LocalizedNumberFormatter numberFormatter;
    private final transient NumericFormatters numericFormatters;
    private final transient PluralRules rules;

    static {
        localeToNumericDurationFormatters = new SimpleCache();
        hmsTo012 = new HashMap<MeasureUnit, Integer>();
        hmsTo012.put(MeasureUnit.HOUR, 0);
        hmsTo012.put(MeasureUnit.MINUTE, 1);
        hmsTo012.put(MeasureUnit.SECOND, 2);
        localeIdToRangeFormat = new ConcurrentHashMap<ULocale, String>();
    }

    MeasureFormat(ULocale uLocale, FormatWidth formatWidth) {
        this(uLocale, formatWidth, null, null, null);
    }

    MeasureFormat(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat, PluralRules pluralRules) {
        this(uLocale, formatWidth, numberFormat, pluralRules, null);
        if (formatWidth != FormatWidth.NUMERIC) {
            return;
        }
        throw new IllegalArgumentException("The format width 'numeric' is not allowed by this constructor");
    }

    private MeasureFormat(ULocale uLocale, FormatWidth formatWidth, NumberFormat object, PluralRules serializable, NumericFormatters numericFormatters) {
        this.setLocale(uLocale, uLocale);
        this.formatWidth = formatWidth;
        PluralRules pluralRules = serializable;
        if (serializable == null) {
            pluralRules = PluralRules.forLocale(uLocale);
        }
        this.rules = pluralRules;
        serializable = object == null ? NumberFormat.getInstance(uLocale) : (NumberFormat)((NumberFormat)object).clone();
        this.numberFormat = serializable;
        object = numericFormatters;
        if (numericFormatters == null) {
            object = numericFormatters;
            if (formatWidth == FormatWidth.NUMERIC) {
                numericFormatters = localeToNumericDurationFormatters.get(uLocale);
                object = numericFormatters;
                if (numericFormatters == null) {
                    object = MeasureFormat.loadNumericFormatters(uLocale);
                    localeToNumericDurationFormatters.put(uLocale, (NumericFormatters)object);
                }
            }
        }
        this.numericFormatters = object;
        if (serializable instanceof DecimalFormat) {
            this.numberFormatter = (LocalizedNumberFormatter)((DecimalFormat)serializable).toNumberFormatter().unitWidth(formatWidth.unitWidth);
            return;
        }
        throw new IllegalArgumentException();
    }

    private FormattedNumber formatMeasure(Measure measure) {
        MeasureUnit measureUnit = measure.getUnit();
        if (measureUnit instanceof Currency) {
            return this.getUnitFormatterFromCache(2, measureUnit, null).format(measure.getNumber());
        }
        return this.getUnitFormatterFromCache(1, measureUnit, null).format(measure.getNumber());
    }

    private FormattedNumber formatMeasureInteger(Measure measure) {
        return this.getUnitFormatterFromCache(3, measure.getUnit(), null).format(measure.getNumber());
    }

    private void formatMeasuresInternal(Appendable appendable, FieldPosition arrstring, Measure ... object) {
        Object object2;
        if (((Measure[])object).length == 0) {
            return;
        }
        if (((Measure[])object).length == 1) {
            object = this.formatMeasure(object[0]);
            ((FormattedNumber)object).populateFieldPosition((FieldPosition)arrstring);
            ((FormattedNumber)object).appendTo(appendable);
            return;
        }
        if (this.formatWidth == FormatWidth.NUMERIC && (object2 = MeasureFormat.toHMS((Measure[])object)) != null) {
            this.formatNumeric((Number[])object2, appendable);
            return;
        }
        object2 = ListFormatter.getInstance(this.getLocale(), this.formatWidth.getListFormatterStyle());
        if (arrstring != DontCareFieldPosition.INSTANCE) {
            this.formatMeasuresSlowTrack((ListFormatter)object2, appendable, (FieldPosition)arrstring, (Measure[])object);
            return;
        }
        arrstring = new String[((Object)object).length];
        for (int i = 0; i < ((Object)object).length; ++i) {
            arrstring[i] = i == ((Object)object).length - 1 ? this.formatMeasure((Measure)object[i]).toString() : this.formatMeasureInteger((Measure)object[i]).toString();
        }
        ((ListFormatter)object2).format(Arrays.asList(arrstring), -1).appendTo(appendable);
    }

    private void formatMeasuresSlowTrack(ListFormatter object, Appendable appendable, FieldPosition fieldPosition, Measure ... arrmeasure) {
        String[] arrstring = new String[arrmeasure.length];
        FieldPosition fieldPosition2 = new FieldPosition(fieldPosition.getFieldAttribute(), fieldPosition.getField());
        int n = -1;
        for (int i = 0; i < arrmeasure.length; ++i) {
            FormattedNumber formattedNumber = i == arrmeasure.length - 1 ? this.formatMeasure(arrmeasure[i]) : this.formatMeasureInteger(arrmeasure[i]);
            int n2 = n;
            if (n == -1) {
                formattedNumber.populateFieldPosition(fieldPosition2);
                n2 = n;
                if (fieldPosition2.getEndIndex() != 0) {
                    n2 = i;
                }
            }
            arrstring[i] = formattedNumber.toString();
            n = n2;
        }
        if (((ListFormatter.FormattedListBuilder)(object = ((ListFormatter)object).format(Arrays.asList(arrstring), n))).getOffset() != -1) {
            fieldPosition.setBeginIndex(fieldPosition2.getBeginIndex() + ((ListFormatter.FormattedListBuilder)object).getOffset());
            fieldPosition.setEndIndex(fieldPosition2.getEndIndex() + ((ListFormatter.FormattedListBuilder)object).getOffset());
        }
        ((ListFormatter.FormattedListBuilder)object).appendTo(appendable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void formatNumeric(Date object, DateFormat dateFormat, DateFormat.Field object2, Number object3, Appendable appendable) {
        FieldPosition fieldPosition = new FieldPosition(0);
        object3 = this.getNumberFormatter().format((Number)object3);
        ((FormattedNumber)object3).populateFieldPosition(fieldPosition);
        object3 = ((FormattedNumber)object3).toString();
        if (fieldPosition.getBeginIndex() == 0) {
            if (fieldPosition.getEndIndex() == 0) throw new IllegalStateException();
        }
        object2 = new FieldPosition((Format.Field)object2);
        synchronized (dateFormat) {
            StringBuffer stringBuffer = new StringBuffer();
            object = dateFormat.format((Date)object, stringBuffer, (FieldPosition)object2).toString();
        }
        try {
            if (((FieldPosition)object2).getBeginIndex() == 0 && ((FieldPosition)object2).getEndIndex() == 0) {
                appendable.append((CharSequence)object);
                return;
            }
            appendable.append((CharSequence)object, 0, ((FieldPosition)object2).getBeginIndex());
            appendable.append((CharSequence)object3, 0, fieldPosition.getBeginIndex());
            appendable.append((CharSequence)object, ((FieldPosition)object2).getBeginIndex(), ((FieldPosition)object2).getEndIndex());
            appendable.append((CharSequence)object3, fieldPosition.getEndIndex(), ((String)object3).length());
            appendable.append((CharSequence)object, ((FieldPosition)object2).getEndIndex(), ((String)object).length());
            return;
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private void formatNumeric(Number[] arrnumber, Appendable appendable) {
        block9 : {
            block7 : {
                Date date;
                int n;
                int n2;
                block8 : {
                    block6 : {
                        n = -1;
                        n2 = -1;
                        for (int i = 0; i < arrnumber.length; ++i) {
                            if (arrnumber[i] != null) {
                                if (n == -1) {
                                    n2 = i;
                                    n = i;
                                    continue;
                                }
                                n2 = i;
                                continue;
                            }
                            arrnumber[i] = 0;
                        }
                        date = new Date((long)(((Math.floor(arrnumber[0].doubleValue()) * 60.0 + Math.floor(arrnumber[1].doubleValue())) * 60.0 + Math.floor(arrnumber[2].doubleValue())) * 1000.0));
                        if (n != 0 || n2 != 2) break block6;
                        this.formatNumeric(date, this.numericFormatters.getHourMinuteSecond(), DateFormat.Field.SECOND, arrnumber[n2], appendable);
                        break block7;
                    }
                    if (n != 1 || n2 != 2) break block8;
                    this.formatNumeric(date, this.numericFormatters.getMinuteSecond(), DateFormat.Field.SECOND, arrnumber[n2], appendable);
                    break block7;
                }
                if (n != 0 || n2 != 1) break block9;
                this.formatNumeric(date, this.numericFormatters.getHourMinute(), DateFormat.Field.MINUTE, arrnumber[n2], appendable);
            }
            return;
        }
        throw new IllegalStateException();
    }

    private static FormatWidth fromFormatWidthOrdinal(int n) {
        FormatWidth[] arrformatWidth = FormatWidth.values();
        if (n >= 0 && n < arrformatWidth.length) {
            return arrformatWidth[n];
        }
        return FormatWidth.SHORT;
    }

    public static MeasureFormat getCurrencyFormat() {
        return MeasureFormat.getCurrencyFormat(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static MeasureFormat getCurrencyFormat(ULocale uLocale) {
        return new CurrencyFormat(uLocale);
    }

    public static MeasureFormat getCurrencyFormat(Locale locale) {
        return MeasureFormat.getCurrencyFormat(ULocale.forLocale(locale));
    }

    public static MeasureFormat getInstance(ULocale uLocale, FormatWidth formatWidth) {
        return MeasureFormat.getInstance(uLocale, formatWidth, NumberFormat.getInstance(uLocale));
    }

    public static MeasureFormat getInstance(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat) {
        return new MeasureFormat(uLocale, formatWidth, numberFormat, null, null);
    }

    public static MeasureFormat getInstance(Locale locale, FormatWidth formatWidth) {
        return MeasureFormat.getInstance(ULocale.forLocale(locale), formatWidth);
    }

    public static MeasureFormat getInstance(Locale locale, FormatWidth formatWidth, NumberFormat numberFormat) {
        return MeasureFormat.getInstance(ULocale.forLocale(locale), formatWidth, numberFormat);
    }

    @Deprecated
    public static String getRangeFormat(ULocale uLocale, FormatWidth object) {
        Object object2;
        if (uLocale.getLanguage().equals("fr")) {
            return MeasureFormat.getRangeFormat(ULocale.ROOT, (FormatWidth)((Object)object));
        }
        object = object2 = localeIdToRangeFormat.get(uLocale);
        if (object2 == null) {
            object2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale);
            ULocale uLocale2 = ((ICUResourceBundle)object2).getULocale();
            if (!uLocale.equals(uLocale2) && (object = localeIdToRangeFormat.get(uLocale)) != null) {
                localeIdToRangeFormat.put(uLocale, (String)object);
                return object;
            }
            NumberingSystem numberingSystem = NumberingSystem.getInstance(uLocale);
            try {
                object = new StringBuilder();
                ((StringBuilder)object).append("NumberElements/");
                ((StringBuilder)object).append(numberingSystem.getName());
                ((StringBuilder)object).append("/miscPatterns/range");
                object = ((ICUResourceBundle)object2).getStringWithFallback(((StringBuilder)object).toString());
            }
            catch (MissingResourceException missingResourceException) {
                object = ((ICUResourceBundle)object2).getStringWithFallback("NumberElements/latn/patterns/range");
            }
            object2 = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object, new StringBuilder(), 2, 2);
            localeIdToRangeFormat.put(uLocale, (String)object2);
            object = object2;
            if (!uLocale.equals(uLocale2)) {
                localeIdToRangeFormat.put(uLocale2, (String)object2);
                object = object2;
            }
        }
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private LocalizedNumberFormatter getUnitFormatterFromCache(int n, MeasureUnit object, MeasureUnit measureUnit) {
        synchronized (this) {
            NumberFormatterCacheEntry numberFormatterCacheEntry;
            void var3_3;
            if (this.formatter1 != null) {
                if (this.formatter1.type == n && this.formatter1.unit == object && this.formatter1.perUnit == var3_3) {
                    return this.formatter1.formatter;
                }
                if (this.formatter2 != null) {
                    if (this.formatter2.type == n && this.formatter2.unit == object && this.formatter2.perUnit == var3_3) {
                        return this.formatter2.formatter;
                    }
                    if (this.formatter3 != null && this.formatter3.type == n && this.formatter3.unit == object && this.formatter3.perUnit == var3_3) {
                        return this.formatter3.formatter;
                    }
                }
            }
            LocalizedNumberFormatter localizedNumberFormatter = n == 1 ? (LocalizedNumberFormatter)((LocalizedNumberFormatter)((LocalizedNumberFormatter)this.getNumberFormatter().unit((MeasureUnit)object)).perUnit((MeasureUnit)var3_3)).unitWidth(this.formatWidth.unitWidth) : (n == 2 ? (LocalizedNumberFormatter)((LocalizedNumberFormatter)((LocalizedNumberFormatter)NumberFormatter.withLocale(this.getLocale()).unit((MeasureUnit)object)).perUnit((MeasureUnit)var3_3)).unitWidth(this.formatWidth.currencyWidth) : (LocalizedNumberFormatter)((LocalizedNumberFormatter)((LocalizedNumberFormatter)((LocalizedNumberFormatter)this.getNumberFormatter().unit((MeasureUnit)object)).perUnit((MeasureUnit)var3_3)).unitWidth(this.formatWidth.unitWidth)).rounding(Precision.integer().withMode(RoundingMode.DOWN)));
            this.formatter3 = this.formatter2;
            this.formatter2 = this.formatter1;
            this.formatter1 = numberFormatterCacheEntry = new NumberFormatterCacheEntry();
            this.formatter1.type = n;
            this.formatter1.unit = object;
            this.formatter1.perUnit = var3_3;
            this.formatter1.formatter = localizedNumberFormatter;
            return localizedNumberFormatter;
        }
    }

    private static DateFormat loadNumericDurationFormat(ICUResourceBundle object, String string) {
        object = new SimpleDateFormat(((ICUResourceBundle)object).getWithFallback(String.format("durationUnits/%s", string)).getString().replace("h", "H"));
        ((DateFormat)object).setTimeZone(TimeZone.GMT_ZONE);
        return object;
    }

    private static NumericFormatters loadNumericFormatters(ULocale object) {
        object = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/unit", (ULocale)object);
        return new NumericFormatters(MeasureFormat.loadNumericDurationFormat((ICUResourceBundle)object, "hm"), MeasureFormat.loadNumericDurationFormat((ICUResourceBundle)object, "ms"), MeasureFormat.loadNumericDurationFormat((ICUResourceBundle)object, "hms"));
    }

    private static Number[] toHMS(Measure[] arrmeasure) {
        Number[] arrnumber = new Number[3];
        int n = -1;
        for (Measure measure : arrmeasure) {
            if (measure.getNumber().doubleValue() < 0.0) {
                return null;
            }
            Integer n2 = hmsTo012.get(measure.getUnit());
            if (n2 == null) {
                return null;
            }
            int n3 = n2;
            if (n3 <= n) {
                return null;
            }
            n = n3;
            arrnumber[n3] = measure.getNumber();
        }
        return arrnumber;
    }

    private Object writeReplace() throws ObjectStreamException {
        return new MeasureProxy(this.getLocale(), this.formatWidth, this.getNumberFormatInternal(), 0);
    }

    void clearCache() {
        synchronized (this) {
            this.formatter1 = null;
            this.formatter2 = null;
            this.formatter3 = null;
            return;
        }
    }

    public final boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof MeasureFormat)) {
            return false;
        }
        object = (MeasureFormat)object;
        if (this.getWidth() != ((MeasureFormat)object).getWidth() || !this.getLocale().equals(((MeasureFormat)object).getLocale()) || !this.getNumberFormatInternal().equals(((MeasureFormat)object).getNumberFormatInternal())) {
            bl = false;
        }
        return bl;
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        block9 : {
            int n;
            block7 : {
                block8 : {
                    block6 : {
                        n = stringBuffer.length();
                        fieldPosition.setBeginIndex(0);
                        fieldPosition.setEndIndex(0);
                        if (!(object instanceof Collection)) break block6;
                        Object object2 = (Collection)object;
                        Measure[] arrmeasure = new Measure[object2.size()];
                        int n2 = 0;
                        object2 = object2.iterator();
                        while (object2.hasNext()) {
                            Object e = object2.next();
                            if (e instanceof Measure) {
                                arrmeasure[n2] = (Measure)e;
                                ++n2;
                                continue;
                            }
                            throw new IllegalArgumentException(object.toString());
                        }
                        this.formatMeasuresInternal(stringBuffer, fieldPosition, arrmeasure);
                        break block7;
                    }
                    if (!(object instanceof Measure[])) break block8;
                    this.formatMeasuresInternal(stringBuffer, fieldPosition, (Measure[])object);
                    break block7;
                }
                if (!(object instanceof Measure)) break block9;
                object = this.formatMeasure((Measure)object);
                ((FormattedNumber)object).populateFieldPosition(fieldPosition);
                ((FormattedNumber)object).appendTo(stringBuffer);
            }
            if (n > 0 && fieldPosition.getEndIndex() != 0) {
                fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + n);
                fieldPosition.setEndIndex(fieldPosition.getEndIndex() + n);
            }
            return stringBuffer;
        }
        throw new IllegalArgumentException(object.toString());
    }

    public StringBuilder formatMeasurePerUnit(Measure object, MeasureUnit measureUnit, StringBuilder stringBuilder, FieldPosition fieldPosition) {
        object = this.getUnitFormatterFromCache(1, ((Measure)object).getUnit(), measureUnit).format(((Measure)object).getNumber());
        DecimalFormat.fieldPositionHelper((FormattedNumber)object, fieldPosition, stringBuilder.length());
        ((FormattedNumber)object).appendTo(stringBuilder);
        return stringBuilder;
    }

    public final String formatMeasures(Measure ... arrmeasure) {
        return this.formatMeasures(new StringBuilder(), DontCareFieldPosition.INSTANCE, arrmeasure).toString();
    }

    public StringBuilder formatMeasures(StringBuilder stringBuilder, FieldPosition fieldPosition, Measure ... arrmeasure) {
        int n = stringBuilder.length();
        this.formatMeasuresInternal(stringBuilder, fieldPosition, arrmeasure);
        if (n > 0 && fieldPosition.getEndIndex() > 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + n);
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + n);
        }
        return stringBuilder;
    }

    public final ULocale getLocale() {
        return this.getLocale(ULocale.VALID_LOCALE);
    }

    public NumberFormat getNumberFormat() {
        return (NumberFormat)this.numberFormat.clone();
    }

    NumberFormat getNumberFormatInternal() {
        return this.numberFormat;
    }

    LocalizedNumberFormatter getNumberFormatter() {
        return this.numberFormatter;
    }

    public String getUnitDisplayName(MeasureUnit measureUnit) {
        return LongNameHandler.getUnitDisplayName(this.getLocale(), measureUnit, this.formatWidth.unitWidth);
    }

    public FormatWidth getWidth() {
        if (this.formatWidth == FormatWidth.DEFAULT_CURRENCY) {
            return FormatWidth.WIDE;
        }
        return this.formatWidth;
    }

    public final int hashCode() {
        return (this.getLocale().hashCode() * 31 + this.getNumberFormatInternal().hashCode()) * 31 + this.getWidth().hashCode();
    }

    @Override
    public Measure parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    Object toCurrencyProxy() {
        return new MeasureProxy(this.getLocale(), this.formatWidth, this.getNumberFormatInternal(), 2);
    }

    Object toTimeUnitProxy() {
        return new MeasureProxy(this.getLocale(), this.formatWidth, this.getNumberFormatInternal(), 1);
    }

    MeasureFormat withLocale(ULocale uLocale) {
        return MeasureFormat.getInstance(uLocale, this.getWidth());
    }

    MeasureFormat withNumberFormat(NumberFormat numberFormat) {
        return new MeasureFormat(this.getLocale(), this.formatWidth, numberFormat, this.rules, this.numericFormatters);
    }

    public static enum FormatWidth {
        WIDE(ListFormatter.Style.DURATION, NumberFormatter.UnitWidth.FULL_NAME, NumberFormatter.UnitWidth.FULL_NAME),
        SHORT(ListFormatter.Style.DURATION_SHORT, NumberFormatter.UnitWidth.SHORT, NumberFormatter.UnitWidth.ISO_CODE),
        NARROW(ListFormatter.Style.DURATION_NARROW, NumberFormatter.UnitWidth.NARROW, NumberFormatter.UnitWidth.SHORT),
        NUMERIC(ListFormatter.Style.DURATION_NARROW, NumberFormatter.UnitWidth.NARROW, NumberFormatter.UnitWidth.SHORT),
        DEFAULT_CURRENCY(ListFormatter.Style.DURATION, NumberFormatter.UnitWidth.FULL_NAME, NumberFormatter.UnitWidth.SHORT);
        
        final NumberFormatter.UnitWidth currencyWidth;
        private final ListFormatter.Style listFormatterStyle;
        final NumberFormatter.UnitWidth unitWidth;

        private FormatWidth(ListFormatter.Style style, NumberFormatter.UnitWidth unitWidth, NumberFormatter.UnitWidth unitWidth2) {
            this.listFormatterStyle = style;
            this.unitWidth = unitWidth;
            this.currencyWidth = unitWidth2;
        }

        ListFormatter.Style getListFormatterStyle() {
            return this.listFormatterStyle;
        }
    }

    static class MeasureProxy
    implements Externalizable {
        private static final long serialVersionUID = -6033308329886716770L;
        private FormatWidth formatWidth;
        private HashMap<Object, Object> keyValues;
        private ULocale locale;
        private NumberFormat numberFormat;
        private int subClass;

        public MeasureProxy() {
        }

        public MeasureProxy(ULocale uLocale, FormatWidth formatWidth, NumberFormat numberFormat, int n) {
            this.locale = uLocale;
            this.formatWidth = formatWidth;
            this.numberFormat = numberFormat;
            this.subClass = n;
            this.keyValues = new HashMap();
        }

        private TimeUnitFormat createTimeUnitFormat() throws InvalidObjectException {
            block4 : {
                int n;
                block3 : {
                    block2 : {
                        if (this.formatWidth != FormatWidth.WIDE) break block2;
                        n = 0;
                        break block3;
                    }
                    if (this.formatWidth != FormatWidth.SHORT) break block4;
                    n = 1;
                }
                TimeUnitFormat timeUnitFormat = new TimeUnitFormat(this.locale, n);
                timeUnitFormat.setNumberFormat(this.numberFormat);
                return timeUnitFormat;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad width: ");
            stringBuilder.append((Object)this.formatWidth);
            throw new InvalidObjectException(stringBuilder.toString());
        }

        private Object readResolve() throws ObjectStreamException {
            int n = this.subClass;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        return MeasureFormat.getCurrencyFormat(this.locale);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown subclass: ");
                    stringBuilder.append(this.subClass);
                    throw new InvalidObjectException(stringBuilder.toString());
                }
                return this.createTimeUnitFormat();
            }
            return MeasureFormat.getInstance(this.locale, this.formatWidth, this.numberFormat);
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            objectInput.readByte();
            this.locale = ULocale.forLanguageTag(objectInput.readUTF());
            this.formatWidth = MeasureFormat.fromFormatWidthOrdinal(objectInput.readByte() & 255);
            this.numberFormat = (NumberFormat)objectInput.readObject();
            if (this.numberFormat != null) {
                this.subClass = objectInput.readByte() & 255;
                this.keyValues = (HashMap)objectInput.readObject();
                if (this.keyValues != null) {
                    return;
                }
                throw new InvalidObjectException("Missing optional values map.");
            }
            throw new InvalidObjectException("Missing number format.");
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(0);
            objectOutput.writeUTF(this.locale.toLanguageTag());
            objectOutput.writeByte(this.formatWidth.ordinal());
            objectOutput.writeObject(this.numberFormat);
            objectOutput.writeByte(this.subClass);
            objectOutput.writeObject(this.keyValues);
        }
    }

    static class NumberFormatterCacheEntry {
        LocalizedNumberFormatter formatter;
        MeasureUnit perUnit;
        int type;
        MeasureUnit unit;

        NumberFormatterCacheEntry() {
        }
    }

    static class NumericFormatters {
        private DateFormat hourMinute;
        private DateFormat hourMinuteSecond;
        private DateFormat minuteSecond;

        public NumericFormatters(DateFormat dateFormat, DateFormat dateFormat2, DateFormat dateFormat3) {
            this.hourMinute = dateFormat;
            this.minuteSecond = dateFormat2;
            this.hourMinuteSecond = dateFormat3;
        }

        public DateFormat getHourMinute() {
            return this.hourMinute;
        }

        public DateFormat getHourMinuteSecond() {
            return this.hourMinuteSecond;
        }

        public DateFormat getMinuteSecond() {
            return this.minuteSecond;
        }
    }

}


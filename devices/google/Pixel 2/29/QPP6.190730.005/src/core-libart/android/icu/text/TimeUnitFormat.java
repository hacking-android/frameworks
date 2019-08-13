/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.UResource;
import android.icu.number.LocalizedNumberFormatter;
import android.icu.text.DecimalFormat;
import android.icu.text.MeasureFormat;
import android.icu.text.MessageFormat;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.text.UFormat;
import android.icu.util.Measure;
import android.icu.util.TimeUnit;
import android.icu.util.TimeUnitAmount;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;

@Deprecated
public class TimeUnitFormat
extends MeasureFormat {
    @Deprecated
    public static final int ABBREVIATED_NAME = 1;
    private static final String DEFAULT_PATTERN_FOR_DAY = "{0} d";
    private static final String DEFAULT_PATTERN_FOR_HOUR = "{0} h";
    private static final String DEFAULT_PATTERN_FOR_MINUTE = "{0} min";
    private static final String DEFAULT_PATTERN_FOR_MONTH = "{0} m";
    private static final String DEFAULT_PATTERN_FOR_SECOND = "{0} s";
    private static final String DEFAULT_PATTERN_FOR_WEEK = "{0} w";
    private static final String DEFAULT_PATTERN_FOR_YEAR = "{0} y";
    @Deprecated
    public static final int FULL_NAME = 0;
    private static final int TOTAL_STYLES = 2;
    private static final long serialVersionUID = -3707773153184971529L;
    private NumberFormat format;
    private transient boolean isReady;
    private ULocale locale;
    private transient PluralRules pluralRules;
    private int style;
    private transient Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns;

    @Deprecated
    public TimeUnitFormat() {
        this(ULocale.getDefault(), 0);
    }

    @Deprecated
    public TimeUnitFormat(ULocale uLocale) {
        this(uLocale, 0);
    }

    @Deprecated
    public TimeUnitFormat(ULocale uLocale, int n) {
        MeasureFormat.FormatWidth formatWidth = n == 0 ? MeasureFormat.FormatWidth.WIDE : MeasureFormat.FormatWidth.SHORT;
        super(uLocale, formatWidth);
        this.format = super.getNumberFormatInternal();
        if (n >= 0 && n < 2) {
            this.style = n;
            this.isReady = false;
            return;
        }
        throw new IllegalArgumentException("style should be either FULL_NAME or ABBREVIATED_NAME style");
    }

    private TimeUnitFormat(ULocale uLocale, int n, NumberFormat numberFormat) {
        this(uLocale, n);
        if (numberFormat != null) {
            this.setNumberFormat((NumberFormat)numberFormat.clone());
        }
    }

    @Deprecated
    public TimeUnitFormat(Locale locale) {
        this(locale, 0);
    }

    @Deprecated
    public TimeUnitFormat(Locale locale, int n) {
        this(ULocale.forLocale(locale), n);
    }

    private Object readResolve() throws ObjectStreamException {
        return new TimeUnitFormat(this.locale, this.style, this.format);
    }

    private void searchInTree(String object, int n, TimeUnit arrobject, String string, String arrobject2, Map<String, Object[]> map) {
        ULocale uLocale;
        String string2 = arrobject.toString();
        for (uLocale = this.locale; uLocale != null; uLocale = uLocale.getFallback()) {
            MessageFormat messageFormat;
            block22 : {
                Object[] arrobject3;
                try {
                    Object[] arrobject4;
                    arrobject3 = ((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/unit", uLocale)).getWithFallback((String)object).getWithFallback(string2).getStringWithFallback((String)arrobject2);
                    messageFormat = new MessageFormat((String)arrobject3, this.locale);
                    arrobject3 = arrobject4 = map.get(string);
                    if (arrobject4 != null) break block22;
                }
                catch (MissingResourceException missingResourceException) {
                    continue;
                }
                arrobject3 = new Object[2];
                map.put(string, arrobject3);
            }
            arrobject3[n] = messageFormat;
            return;
        }
        if (uLocale == null && ((String)object).equals("unitsShort")) {
            this.searchInTree("units", n, (TimeUnit)arrobject, string, (String)arrobject2, map);
            if (map.get(string) != null && map.get(string)[n] != null) {
                return;
            }
        }
        if (arrobject2.equals("other")) {
            object = null;
            if (arrobject == TimeUnit.SECOND) {
                object = new MessageFormat(DEFAULT_PATTERN_FOR_SECOND, this.locale);
            } else if (arrobject == TimeUnit.MINUTE) {
                object = new MessageFormat(DEFAULT_PATTERN_FOR_MINUTE, this.locale);
            } else if (arrobject == TimeUnit.HOUR) {
                object = new MessageFormat(DEFAULT_PATTERN_FOR_HOUR, this.locale);
            } else if (arrobject == TimeUnit.WEEK) {
                object = new MessageFormat(DEFAULT_PATTERN_FOR_WEEK, this.locale);
            } else if (arrobject == TimeUnit.DAY) {
                object = new MessageFormat(DEFAULT_PATTERN_FOR_DAY, this.locale);
            } else if (arrobject == TimeUnit.MONTH) {
                object = new MessageFormat(DEFAULT_PATTERN_FOR_MONTH, this.locale);
            } else if (arrobject == TimeUnit.YEAR) {
                object = new MessageFormat(DEFAULT_PATTERN_FOR_YEAR, this.locale);
            }
            arrobject = arrobject2 = map.get(string);
            if (arrobject2 == null) {
                arrobject = new Object[2];
                map.put(string, arrobject);
            }
            arrobject[n] = object;
        } else {
            this.searchInTree((String)object, n, (TimeUnit)arrobject, string, "other", map);
        }
    }

    private void setup() {
        Object object;
        if (this.locale == null) {
            object = this.format;
            this.locale = object != null ? ((UFormat)object).getLocale(null) : ULocale.getDefault(ULocale.Category.FORMAT);
            object = this.locale;
            this.setLocale((ULocale)object, (ULocale)object);
        }
        if (this.format == null) {
            this.format = NumberFormat.getNumberInstance(this.locale);
        }
        this.pluralRules = PluralRules.forLocale(this.locale);
        this.timeUnitToCountToPatterns = new HashMap<TimeUnit, Map<String, Object[]>>();
        object = this.pluralRules.getKeywords();
        this.setup("units/duration", this.timeUnitToCountToPatterns, 0, (Set<String>)object);
        this.setup("unitsShort/duration", this.timeUnitToCountToPatterns, 1, (Set<String>)object);
        this.isReady = true;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void setup(String string, Map<TimeUnit, Map<String, Object[]>> map, int n, Set<String> map2) {
        Object object2;
        Object object;
        Serializable serializable;
        block9 : {
            object = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/unit", this.locale);
            serializable = this.locale;
            try {
                object2 = new TimeUnitFormatSetupSink(map, n, (Set<String>)((Object)map2), (ULocale)serializable);
            }
            catch (MissingResourceException missingResourceException) {
                break block9;
            }
            try {
                ((ICUResourceBundle)object).getAllItemsWithFallback(string, (UResource.Sink)object2);
            }
            catch (MissingResourceException missingResourceException) {}
            break block9;
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        object = TimeUnit.values();
        object2 = this.pluralRules.getKeywords();
        int i = 0;
        while (i < ((TimeUnit[])object).length) {
            serializable = object[i];
            map2 = map.get(serializable);
            if (map2 == null) {
                map2 = new TreeMap();
                map.put((TimeUnit)serializable, map2);
            }
            Iterator iterator = object2.iterator();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                if (map2.get(string2) != null && map2.get(string2)[n] != null) continue;
                this.searchInTree(string, n, (TimeUnit)serializable, string2, string2, map2);
            }
            ++i;
        }
    }

    private Object writeReplace() throws ObjectStreamException {
        return super.toTimeUnitProxy();
    }

    @Deprecated
    @Override
    public Object clone() {
        TimeUnitFormat timeUnitFormat = (TimeUnitFormat)super.clone();
        timeUnitFormat.format = (NumberFormat)this.format.clone();
        return timeUnitFormat;
    }

    @Deprecated
    @Override
    public NumberFormat getNumberFormat() {
        return (NumberFormat)this.format.clone();
    }

    @Override
    NumberFormat getNumberFormatInternal() {
        return this.format;
    }

    @Override
    LocalizedNumberFormatter getNumberFormatter() {
        return ((DecimalFormat)this.format).toNumberFormatter();
    }

    @Deprecated
    @Override
    public TimeUnitAmount parseObject(String object, ParsePosition parsePosition) {
        TimeUnitFormat timeUnitFormat = this;
        if (!timeUnitFormat.isReady) {
            this.setup();
        }
        TimeUnitFormat timeUnitFormat2 = null;
        Object object2 = null;
        int n = parsePosition.getIndex();
        int n2 = -1;
        int n3 = 0;
        String string = null;
        for (TimeUnit timeUnit : timeUnitFormat.timeUnitToCountToPatterns.keySet()) {
            for (Map.Entry<String, Object[]> entry : this.timeUnitToCountToPatterns.get(timeUnit).entrySet()) {
                String string2 = entry.getKey();
                timeUnitFormat = timeUnitFormat2;
                timeUnitFormat2 = object2;
                for (int i = 0; i < 2; ++i) {
                    Object object3;
                    Serializable serializable;
                    String string3;
                    int n4;
                    int n5;
                    block16 : {
                        object2 = (MessageFormat)entry.getValue()[i];
                        parsePosition.setErrorIndex(-1);
                        parsePosition.setIndex(n);
                        Object object4 = ((MessageFormat)object2).parseObject((String)object, parsePosition);
                        object3 = timeUnitFormat;
                        n5 = n3;
                        string3 = string;
                        serializable = timeUnitFormat2;
                        n4 = n2;
                        if (parsePosition.getErrorIndex() == -1) {
                            if (parsePosition.getIndex() == n) {
                                object3 = timeUnitFormat;
                                n5 = n3;
                                string3 = string;
                                serializable = timeUnitFormat2;
                                n4 = n2;
                            } else {
                                object2 = null;
                                if (((Object[])object4).length != 0) {
                                    object2 = ((Object[])object4)[0];
                                    if (object2 instanceof Number) {
                                        object2 = (Number)object2;
                                    } else {
                                        try {
                                            object2 = this.format.parse(object2.toString());
                                        }
                                        catch (ParseException parseException) {
                                            object3 = timeUnitFormat;
                                            n5 = n3;
                                            string3 = string;
                                            serializable = timeUnitFormat2;
                                            n4 = n2;
                                            break block16;
                                        }
                                    }
                                }
                                int n6 = parsePosition.getIndex() - n;
                                object3 = timeUnitFormat;
                                n5 = n3;
                                string3 = string;
                                serializable = timeUnitFormat2;
                                n4 = n2;
                                if (n6 > n3) {
                                    n4 = parsePosition.getIndex();
                                    n5 = n6;
                                    string3 = string2;
                                    serializable = timeUnit;
                                    object3 = object2;
                                }
                            }
                        }
                    }
                    timeUnitFormat = object3;
                    n3 = n5;
                    string = string3;
                    timeUnitFormat2 = serializable;
                    n2 = n4;
                }
                object2 = timeUnitFormat2;
                timeUnitFormat2 = timeUnitFormat;
            }
        }
        object = timeUnitFormat2;
        if (timeUnitFormat2 == null) {
            object = timeUnitFormat2;
            if (n3 != 0) {
                object = string.equals("zero") ? Integer.valueOf(0) : (string.equals("one") ? Integer.valueOf(1) : (string.equals("two") ? Integer.valueOf(2) : Integer.valueOf(3)));
            }
        }
        if (n3 == 0) {
            parsePosition.setIndex(n);
            parsePosition.setErrorIndex(0);
            return null;
        }
        parsePosition.setIndex(n2);
        parsePosition.setErrorIndex(-1);
        return new TimeUnitAmount((Number)object, (TimeUnit)object2);
    }

    @Deprecated
    public TimeUnitFormat setLocale(ULocale uLocale) {
        this.setLocale(uLocale, uLocale);
        this.clearCache();
        return this;
    }

    @Deprecated
    public TimeUnitFormat setLocale(Locale locale) {
        return this.setLocale(ULocale.forLocale(locale));
    }

    @Deprecated
    public TimeUnitFormat setNumberFormat(NumberFormat serializable) {
        if (serializable == this.format) {
            return this;
        }
        if (serializable == null) {
            serializable = this.locale;
            if (serializable == null) {
                this.isReady = false;
            } else {
                this.format = NumberFormat.getNumberInstance((ULocale)serializable);
            }
        } else {
            this.format = serializable;
        }
        this.clearCache();
        return this;
    }

    private static final class TimeUnitFormatSetupSink
    extends UResource.Sink {
        boolean beenHere;
        ULocale locale;
        Set<String> pluralKeywords;
        int style;
        Map<TimeUnit, Map<String, Object[]>> timeUnitToCountToPatterns;

        TimeUnitFormatSetupSink(Map<TimeUnit, Map<String, Object[]>> map, int n, Set<String> set, ULocale uLocale) {
            this.timeUnitToCountToPatterns = map;
            this.style = n;
            this.pluralKeywords = set;
            this.locale = uLocale;
            this.beenHere = false;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            if (this.beenHere) {
                return;
            }
            this.beenHere = true;
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                block17 : {
                    Object object;
                    Object object2;
                    block11 : {
                        block16 : {
                            block15 : {
                                block14 : {
                                    block13 : {
                                        block12 : {
                                            block10 : {
                                                object2 = key.toString();
                                                if (!object2.equals("year")) break block10;
                                                object2 = TimeUnit.YEAR;
                                                break block11;
                                            }
                                            if (!object2.equals("month")) break block12;
                                            object2 = TimeUnit.MONTH;
                                            break block11;
                                        }
                                        if (!object2.equals("day")) break block13;
                                        object2 = TimeUnit.DAY;
                                        break block11;
                                    }
                                    if (!object2.equals("hour")) break block14;
                                    object2 = TimeUnit.HOUR;
                                    break block11;
                                }
                                if (!object2.equals("minute")) break block15;
                                object2 = TimeUnit.MINUTE;
                                break block11;
                            }
                            if (!object2.equals("second")) break block16;
                            object2 = TimeUnit.SECOND;
                            break block11;
                        }
                        if (!object2.equals("week")) break block17;
                        object2 = TimeUnit.WEEK;
                    }
                    Object[] arrobject = object = this.timeUnitToCountToPatterns.get(object2);
                    if (object == null) {
                        arrobject = new TreeMap<String, Object[]>();
                        this.timeUnitToCountToPatterns.put((TimeUnit)object2, (Map<String, Object[]>)arrobject);
                    }
                    UResource.Table table2 = value.getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        String string = key.toString();
                        if (this.pluralKeywords.contains(string)) {
                            object2 = object = arrobject.get(string);
                            if (object == null) {
                                object2 = new Object[2];
                                arrobject.put(string, object2);
                            }
                            if (object2[this.style] == null) {
                                object2[this.style] = object = new MessageFormat(value.getString(), this.locale);
                            }
                        }
                        ++n2;
                    }
                }
                ++n;
            }
        }
    }

}


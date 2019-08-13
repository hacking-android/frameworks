/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.impl.ICUResourceBundle
 *  android.icu.util.UResourceBundle
 */
package java.time.format;

import android.icu.impl.ICUResourceBundle;
import android.icu.util.UResourceBundle;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseChronology;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalField;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.util.locale.provider.CalendarDataUtility;

class DateTimeTextProvider {
    private static final ConcurrentMap<Map.Entry<TemporalField, Locale>, Object> CACHE = new ConcurrentHashMap<Map.Entry<TemporalField, Locale>, Object>(16, 0.75f, 2);
    private static final Comparator<Map.Entry<String, Long>> COMPARATOR = new Comparator<Map.Entry<String, Long>>(){

        @Override
        public int compare(Map.Entry<String, Long> entry, Map.Entry<String, Long> entry2) {
            return entry2.getKey().length() - entry.getKey().length();
        }
    };

    DateTimeTextProvider() {
    }

    private static <A, B> Map.Entry<A, B> createEntry(A a, B b) {
        return new AbstractMap.SimpleImmutableEntry<A, B>(a, b);
    }

    private Object createStore(TemporalField object, Locale locale) {
        int n;
        HashMap<TextStyle, Map<Long, String>> hashMap = new HashMap<TextStyle, Map<Long, String>>();
        ChronoField object22 = ChronoField.ERA;
        int n2 = 0;
        int n3 = 0;
        if (object == object22) {
            for (TextStyle textStyle : TextStyle.values()) {
                Map<String, Integer> map;
                if (textStyle.isStandalone() || (map = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 0, textStyle.toCalendarStyle(), locale)) == null) continue;
                HashMap<Long, String> hashMap2 = new HashMap<Long, String>();
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    hashMap2.put(Long.valueOf(entry.getValue().intValue()), entry.getKey());
                }
                if (hashMap2.isEmpty()) continue;
                hashMap.put(textStyle, hashMap2);
            }
            return new LocaleStore(hashMap);
        }
        if (object == ChronoField.MONTH_OF_YEAR) {
            for (TextStyle textStyle : TextStyle.values()) {
                Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 2, textStyle.toCalendarStyle(), locale);
                HashMap<Long, String> hashMap3 = new HashMap<Long, String>();
                if (map != null) {
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        hashMap3.put(Long.valueOf(entry.getValue() + 1), entry.getKey());
                    }
                } else {
                    String string;
                    for (n2 = 0; n2 <= 11 && (string = CalendarDataUtility.retrieveJavaTimeFieldValueName("gregory", 2, n2, textStyle.toCalendarStyle(), locale)) != null; ++n2) {
                        hashMap3.put(Long.valueOf(n2 + 1), string);
                    }
                }
                if (hashMap3.isEmpty()) continue;
                hashMap.put(textStyle, hashMap3);
            }
            return new LocaleStore(hashMap);
        }
        if (object == ChronoField.DAY_OF_WEEK) {
            TextStyle[] arrtextStyle = TextStyle.values();
            n3 = arrtextStyle.length;
            for (n = n2; n < n3; ++n) {
                object = arrtextStyle[n];
                Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 7, ((TextStyle)((Object)object)).toCalendarStyle(), locale);
                HashMap<Long, String> hashMap4 = new HashMap<Long, String>();
                if (map != null) {
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        hashMap4.put(Long.valueOf(DateTimeTextProvider.toWeekDay(entry.getValue())), entry.getKey());
                    }
                } else {
                    String string;
                    for (n2 = 1; n2 <= 7 && (string = CalendarDataUtility.retrieveJavaTimeFieldValueName("gregory", 7, n2, ((TextStyle)((Object)object)).toCalendarStyle(), locale)) != null; ++n2) {
                        hashMap4.put(Long.valueOf(DateTimeTextProvider.toWeekDay(n2)), string);
                    }
                }
                if (hashMap4.isEmpty()) continue;
                hashMap.put((TextStyle)((Object)object), hashMap4);
            }
            return new LocaleStore(hashMap);
        }
        if (object == ChronoField.AMPM_OF_DAY) {
            TextStyle[] arrtextStyle = TextStyle.values();
            n2 = arrtextStyle.length;
            for (n = n3; n < n2; ++n) {
                Map<String, Integer> map;
                TextStyle textStyle = arrtextStyle[n];
                if (textStyle.isStandalone() || (map = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 9, textStyle.toCalendarStyle(), locale)) == null) continue;
                object = new HashMap();
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    object.put(Long.valueOf(entry.getValue().intValue()), entry.getKey());
                }
                if (object.isEmpty()) continue;
                hashMap.put(textStyle, (Map<Long, String>)object);
            }
            return new LocaleStore(hashMap);
        }
        if (object == IsoFields.QUARTER_OF_YEAR) {
            locale = ((ICUResourceBundle)UResourceBundle.getBundleInstance((String)"android/icu/impl/data/icudt63b", (Locale)locale)).getWithFallback("calendar/gregorian/quarters");
            object = locale.getWithFallback("format");
            locale = locale.getWithFallback("stand-alone");
            hashMap.put(TextStyle.FULL, DateTimeTextProvider.extractQuarters((ICUResourceBundle)object, "wide"));
            hashMap.put(TextStyle.FULL_STANDALONE, DateTimeTextProvider.extractQuarters((ICUResourceBundle)locale, "wide"));
            hashMap.put(TextStyle.SHORT, DateTimeTextProvider.extractQuarters((ICUResourceBundle)object, "abbreviated"));
            hashMap.put(TextStyle.SHORT_STANDALONE, DateTimeTextProvider.extractQuarters((ICUResourceBundle)locale, "abbreviated"));
            hashMap.put(TextStyle.NARROW, DateTimeTextProvider.extractQuarters((ICUResourceBundle)object, "narrow"));
            hashMap.put(TextStyle.NARROW_STANDALONE, DateTimeTextProvider.extractQuarters((ICUResourceBundle)locale, "narrow"));
            return new LocaleStore(hashMap);
        }
        return "";
    }

    private static Map<Long, String> extractQuarters(ICUResourceBundle object, String arrstring) {
        arrstring = object.getWithFallback((String)arrstring).getStringArray();
        object = new HashMap();
        for (int i = 0; i < arrstring.length; ++i) {
            object.put(Long.valueOf(i + 1), arrstring[i]);
        }
        return object;
    }

    private Object findStore(TemporalField object, Locale locale) {
        Object v;
        Map.Entry<TemporalField, Locale> entry = DateTimeTextProvider.createEntry(object, locale);
        Object v2 = v = CACHE.get(entry);
        if (v == null) {
            object = this.createStore((TemporalField)object, locale);
            CACHE.putIfAbsent(entry, object);
            v2 = CACHE.get(entry);
        }
        return v2;
    }

    static DateTimeTextProvider getInstance() {
        return new DateTimeTextProvider();
    }

    private static int toWeekDay(int n) {
        if (n == 1) {
            return 7;
        }
        return n - 1;
    }

    public String getText(Chronology chronology, TemporalField temporalField, long l, TextStyle textStyle, Locale locale) {
        block5 : {
            block10 : {
                int n;
                int n2;
                block7 : {
                    block9 : {
                        block8 : {
                            block6 : {
                                if (chronology == IsoChronology.INSTANCE || !(temporalField instanceof ChronoField)) break block5;
                                if (temporalField != ChronoField.ERA) break block6;
                                n2 = 0;
                                n = chronology == JapaneseChronology.INSTANCE ? (l == -999L ? 0 : (int)l + 2) : (int)l;
                                break block7;
                            }
                            if (temporalField != ChronoField.MONTH_OF_YEAR) break block8;
                            n2 = 2;
                            n = (int)l - 1;
                            break block7;
                        }
                        if (temporalField != ChronoField.DAY_OF_WEEK) break block9;
                        int n3 = 7;
                        int n4 = (int)l + 1;
                        n2 = n3;
                        n = n4;
                        if (n4 > 7) {
                            n = 1;
                            n2 = n3;
                        }
                        break block7;
                    }
                    if (temporalField != ChronoField.AMPM_OF_DAY) break block10;
                    n2 = 9;
                    n = (int)l;
                }
                return CalendarDataUtility.retrieveJavaTimeFieldValueName(chronology.getCalendarType(), n2, n, textStyle.toCalendarStyle(), locale);
            }
            return null;
        }
        return this.getText(temporalField, l, textStyle, locale);
    }

    public String getText(TemporalField object, long l, TextStyle textStyle, Locale locale) {
        if ((object = this.findStore((TemporalField)object, locale)) instanceof LocaleStore) {
            return ((LocaleStore)object).getText(l, textStyle);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    public Iterator<Map.Entry<String, Long>> getTextIterator(Chronology object5, TemporalField object2, TextStyle object32, Locale object42) {
        void var3_7;
        void var4_15;
        ArrayList<Map.Entry<String, Long>> arrayList;
        if (object5 != IsoChronology.INSTANCE && arrayList instanceof ChronoField) {
            int n = 2.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)((Object)arrayList)).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        n = 9;
                    } else {
                        n = 7;
                    }
                } else {
                    n = 2;
                }
            } else {
                n = 0;
            }
            int n2 = var3_7 == null ? 0 : var3_7.toCalendarStyle();
            Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames(object5.getCalendarType(), n, n2, (Locale)var4_15);
            if (map == null) {
                return null;
            }
            arrayList = new ArrayList<Map.Entry<String, Long>>(map.size());
            if (n != 0) {
                if (n != 2) {
                    if (n != 7) {
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            arrayList.add(DateTimeTextProvider.createEntry(entry.getKey(), Long.valueOf(entry.getValue().intValue())));
                        }
                    } else {
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            arrayList.add(DateTimeTextProvider.createEntry(entry.getKey(), Long.valueOf(DateTimeTextProvider.toWeekDay(entry.getValue()))));
                        }
                    }
                } else {
                    for (Map.Entry<String, Integer> entry : map.entrySet()) {
                        arrayList.add(DateTimeTextProvider.createEntry(entry.getKey(), Long.valueOf(entry.getValue() + 1)));
                    }
                }
            } else {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    n = n2 = entry.getValue().intValue();
                    if (object5 == JapaneseChronology.INSTANCE) {
                        n = n2 == 0 ? -999 : n2 - 2;
                    }
                    arrayList.add(DateTimeTextProvider.createEntry(entry.getKey(), Long.valueOf(n)));
                }
            }
            return arrayList.iterator();
        }
        return this.getTextIterator((TemporalField)((Object)arrayList), (TextStyle)var3_7, (Locale)var4_15);
    }

    public Iterator<Map.Entry<String, Long>> getTextIterator(TemporalField object, TextStyle textStyle, Locale locale) {
        if ((object = this.findStore((TemporalField)object, locale)) instanceof LocaleStore) {
            return ((LocaleStore)object).getTextIterator(textStyle);
        }
        return null;
    }

    static final class LocaleStore {
        private final Map<TextStyle, List<Map.Entry<String, Long>>> parsable;
        private final Map<TextStyle, Map<Long, String>> valueTextMap;

        LocaleStore(Map<TextStyle, Map<Long, String>> object2) {
            this.valueTextMap = object2;
            HashMap<TextStyle, List<Map.Entry<String, Long>>> hashMap = new HashMap<TextStyle, List<Map.Entry<String, Long>>>();
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : object2.entrySet()) {
                Cloneable cloneable = new HashMap<String, Map.Entry>();
                for (Map.Entry entry2 : ((Map)entry.getValue()).entrySet()) {
                    if (cloneable.put((String)entry2.getValue(), DateTimeTextProvider.createEntry((String)entry2.getValue(), (Long)entry2.getKey())) == null) continue;
                }
                cloneable = new ArrayList(cloneable.values());
                Collections.sort(cloneable, COMPARATOR);
                hashMap.put((TextStyle)((Object)entry.getKey()), (List<Map.Entry<String, Long>>)((Object)cloneable));
                arrayList.addAll(cloneable);
                hashMap.put(null, arrayList);
            }
            Collections.sort(arrayList, COMPARATOR);
            this.parsable = hashMap;
        }

        String getText(long l, TextStyle object) {
            object = (object = this.valueTextMap.get(object)) != null ? (String)object.get(l) : null;
            return object;
        }

        Iterator<Map.Entry<String, Long>> getTextIterator(TextStyle object) {
            object = (object = this.parsable.get(object)) != null ? object.iterator() : null;
            return object;
        }
    }

}


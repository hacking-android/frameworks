/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.UResource;
import android.icu.impl.number.parse.ParsingUtils;
import android.icu.text.UnicodeSet;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.EnumMap;
import java.util.Map;

public class StaticUnicodeSets {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Map<Key, UnicodeSet> unicodeSets = new EnumMap<Key, UnicodeSet>(Key.class);

    static {
        unicodeSets.put(Key.DEFAULT_IGNORABLES, new UnicodeSet("[[:Zs:][\\u0009][:Bidi_Control:][:Variation_Selector:]]").freeze());
        unicodeSets.put(Key.STRICT_IGNORABLES, new UnicodeSet("[[:Bidi_Control:]]").freeze());
        ((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", ULocale.ROOT)).getAllItemsWithFallback("parse", new ParseDataSink());
        unicodeSets.put(Key.OTHER_GROUPING_SEPARATORS, new UnicodeSet("['\u066c\u2018\u2019\uff07\\u0020\\u00A0\\u2000-\\u200A\\u202F\\u205F\\u3000]").freeze());
        unicodeSets.put(Key.ALL_SEPARATORS, StaticUnicodeSets.computeUnion(Key.COMMA, Key.PERIOD, Key.OTHER_GROUPING_SEPARATORS));
        unicodeSets.put(Key.STRICT_ALL_SEPARATORS, StaticUnicodeSets.computeUnion(Key.STRICT_COMMA, Key.STRICT_PERIOD, Key.OTHER_GROUPING_SEPARATORS));
        unicodeSets.put(Key.PERCENT_SIGN, new UnicodeSet("[%\u066a]").freeze());
        unicodeSets.put(Key.PERMILLE_SIGN, new UnicodeSet("[\u2030\u0609]").freeze());
        unicodeSets.put(Key.INFINITY, new UnicodeSet("[\u221e]").freeze());
        unicodeSets.put(Key.YEN_SIGN, new UnicodeSet("[\u00a5\\uffe5]").freeze());
        unicodeSets.put(Key.DIGITS, new UnicodeSet("[:digit:]").freeze());
        unicodeSets.put(Key.DIGITS_OR_ALL_SEPARATORS, StaticUnicodeSets.computeUnion(Key.DIGITS, Key.ALL_SEPARATORS));
        unicodeSets.put(Key.DIGITS_OR_STRICT_ALL_SEPARATORS, StaticUnicodeSets.computeUnion(Key.DIGITS, Key.STRICT_ALL_SEPARATORS));
    }

    public static Key chooseCurrency(String string) {
        if (StaticUnicodeSets.get(Key.DOLLAR_SIGN).contains(string)) {
            return Key.DOLLAR_SIGN;
        }
        if (StaticUnicodeSets.get(Key.POUND_SIGN).contains(string)) {
            return Key.POUND_SIGN;
        }
        if (StaticUnicodeSets.get(Key.RUPEE_SIGN).contains(string)) {
            return Key.RUPEE_SIGN;
        }
        if (StaticUnicodeSets.get(Key.YEN_SIGN).contains(string)) {
            return Key.YEN_SIGN;
        }
        return null;
    }

    public static Key chooseFrom(String object, Key key) {
        object = ParsingUtils.safeContains(StaticUnicodeSets.get(key), object) ? key : null;
        return object;
    }

    public static Key chooseFrom(String string, Key key, Key key2) {
        if (!ParsingUtils.safeContains(StaticUnicodeSets.get(key), string)) {
            key = StaticUnicodeSets.chooseFrom(string, key2);
        }
        return key;
    }

    private static UnicodeSet computeUnion(Key key, Key key2) {
        return new UnicodeSet().addAll(StaticUnicodeSets.get(key)).addAll(StaticUnicodeSets.get(key2)).freeze();
    }

    private static UnicodeSet computeUnion(Key key, Key key2, Key key3) {
        return new UnicodeSet().addAll(StaticUnicodeSets.get(key)).addAll(StaticUnicodeSets.get(key2)).addAll(StaticUnicodeSets.get(key3)).freeze();
    }

    public static UnicodeSet get(Key object) {
        if ((object = unicodeSets.get(object)) == null) {
            return UnicodeSet.EMPTY;
        }
        return object;
    }

    private static void saveSet(Key key, String string) {
        unicodeSets.put(key, new UnicodeSet(string).freeze());
    }

    public static enum Key {
        DEFAULT_IGNORABLES,
        STRICT_IGNORABLES,
        COMMA,
        PERIOD,
        STRICT_COMMA,
        STRICT_PERIOD,
        OTHER_GROUPING_SEPARATORS,
        ALL_SEPARATORS,
        STRICT_ALL_SEPARATORS,
        MINUS_SIGN,
        PLUS_SIGN,
        PERCENT_SIGN,
        PERMILLE_SIGN,
        INFINITY,
        DOLLAR_SIGN,
        POUND_SIGN,
        RUPEE_SIGN,
        YEN_SIGN,
        DIGITS,
        DIGITS_OR_ALL_SEPARATORS,
        DIGITS_OR_STRICT_ALL_SEPARATORS;
        
    }

    static class ParseDataSink
    extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        ParseDataSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (!key.contentEquals("date")) {
                    UResource.Table table2 = value.getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        bl = key.contentEquals("lenient");
                        UResource.Array array = value.getArray();
                        for (int i = 0; i < array.getSize(); ++i) {
                            Key key2;
                            array.getValue(i, value);
                            String string = value.toString();
                            if (string.indexOf(46) != -1) {
                                key2 = bl ? Key.PERIOD : Key.STRICT_PERIOD;
                                StaticUnicodeSets.saveSet(key2, string);
                                continue;
                            }
                            if (string.indexOf(44) != -1) {
                                key2 = bl ? Key.COMMA : Key.STRICT_COMMA;
                                StaticUnicodeSets.saveSet(key2, string);
                                continue;
                            }
                            if (string.indexOf(43) != -1) {
                                StaticUnicodeSets.saveSet(Key.PLUS_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(8210) != -1) {
                                StaticUnicodeSets.saveSet(Key.MINUS_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(36) != -1) {
                                StaticUnicodeSets.saveSet(Key.DOLLAR_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(163) != -1) {
                                StaticUnicodeSets.saveSet(Key.POUND_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(8360) == -1) continue;
                            StaticUnicodeSets.saveSet(Key.RUPEE_SIGN, string);
                        }
                        ++n2;
                    }
                }
                ++n;
            }
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.StandardPlural;
import android.icu.impl.UResource;
import android.icu.impl.number.MultiplierProducer;
import android.icu.text.CompactDecimalFormat;
import android.icu.util.ICUException;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CompactData
implements MultiplierProducer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int COMPACT_MAX_DIGITS = 15;
    private static final String USE_FALLBACK = "<USE FALLBACK>";
    private boolean isEmpty = true;
    private byte largestMagnitude = (byte)(false ? 1 : 0);
    private final byte[] multipliers = new byte[16];
    private final String[] patterns = new String[StandardPlural.COUNT * 16];

    private static final int countZeros(String string) {
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            int n2;
            if (string.charAt(i) == '0') {
                n2 = n + 1;
            } else {
                n2 = n;
                if (n > 0) break;
            }
            n = n2;
        }
        return n;
    }

    private static final int getIndex(int n, StandardPlural standardPlural) {
        return StandardPlural.COUNT * n + standardPlural.ordinal();
    }

    private static void getResourceBundleKey(String string, CompactDecimalFormat.CompactStyle compactStyle, CompactType compactType, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        stringBuilder.append("NumberElements/");
        stringBuilder.append(string);
        string = compactStyle == CompactDecimalFormat.CompactStyle.SHORT ? "/patternsShort" : "/patternsLong";
        stringBuilder.append(string);
        string = compactType == CompactType.DECIMAL ? "/decimalFormat" : "/currencyFormat";
        stringBuilder.append(string);
    }

    @Override
    public int getMultiplier(int n) {
        if (n < 0) {
            return 0;
        }
        int n2 = n;
        if (n > this.largestMagnitude) {
            n2 = this.largestMagnitude;
        }
        return this.multipliers[n2];
    }

    public String getPattern(int n, StandardPlural object) {
        String string;
        if (n < 0) {
            return null;
        }
        int n2 = n;
        if (n > this.largestMagnitude) {
            n2 = this.largestMagnitude;
        }
        String string2 = string = this.patterns[CompactData.getIndex(n2, object)];
        if (string == null) {
            string2 = string;
            if (object != StandardPlural.OTHER) {
                string2 = this.patterns[CompactData.getIndex(n2, StandardPlural.OTHER)];
            }
        }
        object = string2;
        if (string2 == USE_FALLBACK) {
            object = null;
        }
        return object;
    }

    public void getUniquePatterns(Set<String> set) {
        set.addAll(Arrays.asList(this.patterns));
        set.remove(USE_FALLBACK);
        set.remove(null);
    }

    public void populate(ULocale uLocale, String charSequence, CompactDecimalFormat.CompactStyle compactStyle, CompactType compactType) {
        CompactDataSink compactDataSink = new CompactDataSink(this);
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale);
        boolean bl = ((String)charSequence).equals("latn");
        boolean bl2 = compactStyle == CompactDecimalFormat.CompactStyle.SHORT;
        StringBuilder stringBuilder = new StringBuilder();
        CompactData.getResourceBundleKey((String)charSequence, compactStyle, compactType, stringBuilder);
        iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), compactDataSink);
        if (this.isEmpty && !bl) {
            CompactData.getResourceBundleKey("latn", compactStyle, compactType, stringBuilder);
            iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), compactDataSink);
        }
        if (this.isEmpty && !bl2) {
            CompactData.getResourceBundleKey((String)charSequence, CompactDecimalFormat.CompactStyle.SHORT, compactType, stringBuilder);
            iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), compactDataSink);
        }
        if (this.isEmpty && !bl && !bl2) {
            CompactData.getResourceBundleKey("latn", CompactDecimalFormat.CompactStyle.SHORT, compactType, stringBuilder);
            iCUResourceBundle.getAllItemsWithFallbackNoFail(stringBuilder.toString(), compactDataSink);
        }
        if (!this.isEmpty) {
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Could not load compact decimal data for locale ");
        ((StringBuilder)charSequence).append(uLocale);
        throw new ICUException(((StringBuilder)charSequence).toString());
    }

    public void populate(Map<String, Map<String, String>> object) {
        for (Map.Entry entry : object.entrySet()) {
            byte by = (byte)(((String)entry.getKey()).length() - 1);
            for (Map.Entry entry2 : ((Map)entry.getValue()).entrySet()) {
                String string;
                StandardPlural standardPlural = StandardPlural.fromString(((String)entry2.getKey()).toString());
                this.patterns[CompactData.getIndex((int)by, (StandardPlural)standardPlural)] = string = ((String)entry2.getValue()).toString();
                int n = CompactData.countZeros(string);
                if (n <= 0) continue;
                this.multipliers[by] = (byte)(n - by - 1);
                if (by > this.largestMagnitude) {
                    this.largestMagnitude = by;
                }
                this.isEmpty = false;
            }
        }
    }

    private static final class CompactDataSink
    extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        CompactData data;

        public CompactDataSink(CompactData compactData) {
            this.data = compactData;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                byte by = (byte)(key.length() - 1);
                byte by2 = this.data.multipliers[by];
                UResource.Table table2 = value.getTable();
                int n2 = 0;
                while (table2.getKeyAndValue(n2, key, value)) {
                    byte by3;
                    StandardPlural standardPlural = StandardPlural.fromString(key.toString());
                    if (this.data.patterns[CompactData.getIndex(by, standardPlural)] != null) {
                        by3 = by2;
                    } else {
                        String string;
                        String string2 = string = value.toString();
                        if (string.equals("0")) {
                            string2 = CompactData.USE_FALLBACK;
                        }
                        CompactData.access$100((CompactData)this.data)[CompactData.access$200((int)by, (StandardPlural)standardPlural)] = string2;
                        by3 = by2;
                        if (by2 == 0) {
                            int n3 = CompactData.countZeros(string2);
                            by3 = by2;
                            if (n3 > 0) {
                                by3 = (byte)(n3 - by - 1);
                            }
                        }
                    }
                    ++n2;
                    by2 = by3;
                }
                if (this.data.multipliers[by] == 0) {
                    CompactData.access$000((CompactData)this.data)[by] = by2;
                    if (by > this.data.largestMagnitude) {
                        this.data.largestMagnitude = by;
                    }
                    this.data.isEmpty = false;
                }
                ++n;
            }
        }
    }

    public static enum CompactType {
        DECIMAL,
        CURRENCY;
        
    }

}


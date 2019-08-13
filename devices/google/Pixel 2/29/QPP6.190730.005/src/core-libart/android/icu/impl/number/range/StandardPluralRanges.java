/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.range;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.StandardPlural;
import android.icu.impl.UResource;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.MissingResourceException;

public class StandardPluralRanges {
    StandardPlural[] flatTriples;
    int numTriples = 0;

    public StandardPluralRanges(ULocale uLocale) {
        StandardPluralRanges.getPluralRangesData(uLocale, this);
    }

    private void addPluralRange(StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural standardPlural3) {
        StandardPlural[] arrstandardPlural = this.flatTriples;
        int n = this.numTriples;
        arrstandardPlural[n * 3] = standardPlural;
        arrstandardPlural[n * 3 + 1] = standardPlural2;
        arrstandardPlural[n * 3 + 2] = standardPlural3;
        this.numTriples = n + 1;
    }

    private static void getPluralRangesData(ULocale object, StandardPluralRanges standardPluralRanges) {
        StringBuilder stringBuilder = new StringBuilder();
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "pluralRanges");
        stringBuilder.append("locales/");
        stringBuilder.append(((ULocale)object).getLanguage());
        object = stringBuilder.toString();
        try {
            object = iCUResourceBundle.getStringWithFallback((String)object);
        }
        catch (MissingResourceException missingResourceException) {
            return;
        }
        stringBuilder.setLength(0);
        stringBuilder.append("rules/");
        stringBuilder.append((String)object);
        iCUResourceBundle.getAllItemsWithFallback(stringBuilder.toString(), new PluralRangesDataSink(standardPluralRanges));
    }

    private void setCapacity(int n) {
        this.flatTriples = new StandardPlural[n * 3];
    }

    public StandardPlural resolve(StandardPlural standardPlural, StandardPlural standardPlural2) {
        for (int i = 0; i < this.numTriples; ++i) {
            StandardPlural[] arrstandardPlural = this.flatTriples;
            if (standardPlural != arrstandardPlural[i * 3] || standardPlural2 != arrstandardPlural[i * 3 + 1]) continue;
            return arrstandardPlural[i * 3 + 2];
        }
        return StandardPlural.OTHER;
    }

    private static final class PluralRangesDataSink
    extends UResource.Sink {
        StandardPluralRanges output;

        PluralRangesDataSink(StandardPluralRanges standardPluralRanges) {
            this.output = standardPluralRanges;
        }

        @Override
        public void put(UResource.Key object, UResource.Value value, boolean bl) {
            UResource.Array array = value.getArray();
            this.output.setCapacity(array.getSize());
            int n = 0;
            while (array.getValue(n, value)) {
                Object object2 = value.getArray();
                object2.getValue(0, value);
                StandardPlural standardPlural = StandardPlural.fromString(value.getString());
                object2.getValue(1, value);
                object = StandardPlural.fromString(value.getString());
                object2.getValue(2, value);
                object2 = StandardPlural.fromString(value.getString());
                this.output.addPluralRange(standardPlural, (StandardPlural)((Object)object), (StandardPlural)((Object)object2));
                ++n;
            }
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.CurrencyData;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.StandardPlural;
import android.icu.impl.UResource;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.MicroProps;
import android.icu.impl.number.MicroPropsGenerator;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.ModifierStore;
import android.icu.impl.number.SimpleModifier;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.util.Currency;
import android.icu.util.ICUException;
import android.icu.util.MeasureUnit;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

public class LongNameHandler
implements MicroPropsGenerator,
ModifierStore {
    private static final int ARRAY_LENGTH;
    private static final int DNAM_INDEX;
    private static final int PER_INDEX;
    private final Map<StandardPlural, SimpleModifier> modifiers;
    private final MicroPropsGenerator parent;
    private final PluralRules rules;

    static {
        DNAM_INDEX = StandardPlural.COUNT;
        PER_INDEX = StandardPlural.COUNT + 1;
        ARRAY_LENGTH = StandardPlural.COUNT + 2;
    }

    private LongNameHandler(Map<StandardPlural, SimpleModifier> map, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        this.modifiers = map;
        this.rules = pluralRules;
        this.parent = microPropsGenerator;
    }

    private static LongNameHandler forCompoundUnit(ULocale object, MeasureUnit object2, MeasureUnit object3, NumberFormatter.UnitWidth unitWidth, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        String[] arrstring = new String[ARRAY_LENGTH];
        LongNameHandler.getMeasureData((ULocale)object, (MeasureUnit)object2, unitWidth, arrstring);
        object2 = new String[ARRAY_LENGTH];
        LongNameHandler.getMeasureData((ULocale)object, (MeasureUnit)object3, unitWidth, object2);
        int n = PER_INDEX;
        if (object2[n] != null) {
            object = object2[n];
        } else {
            object3 = LongNameHandler.getPerUnitFormat((ULocale)object, unitWidth);
            object = new StringBuilder();
            object3 = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object3, (StringBuilder)object, 2, 2);
            object2 = LongNameHandler.getWithPlural(object2, StandardPlural.ONE);
            object = SimpleFormatterImpl.formatCompiledPattern((String)object3, "{0}", SimpleFormatterImpl.getTextWithNoArguments(SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object2, (StringBuilder)object, 1, 1)).trim());
        }
        object2 = new LongNameHandler(new EnumMap<StandardPlural, SimpleModifier>(StandardPlural.class), pluralRules, microPropsGenerator);
        LongNameHandler.super.multiSimpleFormatsToModifiers(arrstring, (String)object, null);
        return object2;
    }

    public static LongNameHandler forCurrencyLongNames(ULocale object, Currency currency, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        String[] arrstring = new String[ARRAY_LENGTH];
        LongNameHandler.getCurrencyLongNameData((ULocale)object, currency, arrstring);
        object = new LongNameHandler(new EnumMap<StandardPlural, SimpleModifier>(StandardPlural.class), pluralRules, microPropsGenerator);
        LongNameHandler.super.simpleFormatsToModifiers(arrstring, null);
        return object;
    }

    public static LongNameHandler forMeasureUnit(ULocale object, MeasureUnit arrstring, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        Object object2 = arrstring;
        if (measureUnit != null && (object2 = MeasureUnit.resolveUnitPerUnit((MeasureUnit)arrstring, measureUnit)) == null) {
            return LongNameHandler.forCompoundUnit((ULocale)object, (MeasureUnit)arrstring, measureUnit, unitWidth, pluralRules, microPropsGenerator);
        }
        arrstring = new String[ARRAY_LENGTH];
        LongNameHandler.getMeasureData((ULocale)object, (MeasureUnit)object2, unitWidth, arrstring);
        object = new LongNameHandler(new EnumMap<StandardPlural, SimpleModifier>(StandardPlural.class), pluralRules, microPropsGenerator);
        LongNameHandler.super.simpleFormatsToModifiers(arrstring, null);
        return object;
    }

    private static void getCurrencyLongNameData(ULocale uLocale, Currency currency, String[] arrstring) {
        for (Map.Entry<String, String> entry : CurrencyData.provider.getInstance(uLocale, true).getUnitPatterns().entrySet()) {
            String string = entry.getKey();
            int n = LongNameHandler.getIndex(string);
            string = currency.getName(uLocale, 2, string, null);
            arrstring[n] = entry.getValue().replace("{1}", string);
        }
    }

    private static int getIndex(String string) {
        if (string.equals("dnam")) {
            return DNAM_INDEX;
        }
        if (string.equals("per")) {
            return PER_INDEX;
        }
        return StandardPlural.fromString(string).ordinal();
    }

    private static void getMeasureData(ULocale object, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth, String[] object2) {
        object2 = new PluralTableSink((String[])object2);
        object = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/unit", (ULocale)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("units");
        if (unitWidth == NumberFormatter.UnitWidth.NARROW) {
            stringBuilder.append("Narrow");
        } else if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
            stringBuilder.append("Short");
        }
        stringBuilder.append("/");
        stringBuilder.append(measureUnit.getType());
        stringBuilder.append("/");
        stringBuilder.append(measureUnit.getSubtype());
        try {
            ((ICUResourceBundle)object).getAllItemsWithFallback(stringBuilder.toString(), (UResource.Sink)object2);
            return;
        }
        catch (MissingResourceException missingResourceException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No data for unit ");
            ((StringBuilder)object).append(measureUnit);
            ((StringBuilder)object).append(", width ");
            ((StringBuilder)object).append((Object)unitWidth);
            throw new IllegalArgumentException(((StringBuilder)object).toString(), missingResourceException);
        }
    }

    private static String getPerUnitFormat(ULocale uLocale, NumberFormatter.UnitWidth unitWidth) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/unit", uLocale);
        CharSequence charSequence = new StringBuilder();
        charSequence.append("units");
        if (unitWidth == NumberFormatter.UnitWidth.NARROW) {
            charSequence.append("Narrow");
        } else if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
            charSequence.append("Short");
        }
        charSequence.append("/compound/per");
        try {
            charSequence = iCUResourceBundle.getStringWithFallback(charSequence.toString());
            return charSequence;
        }
        catch (MissingResourceException missingResourceException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find x-per-y format for ");
            stringBuilder.append(uLocale);
            stringBuilder.append(", width ");
            stringBuilder.append((Object)unitWidth);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static String getUnitDisplayName(ULocale uLocale, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth) {
        String[] arrstring = new String[ARRAY_LENGTH];
        LongNameHandler.getMeasureData(uLocale, measureUnit, unitWidth, arrstring);
        return arrstring[DNAM_INDEX];
    }

    private static String getWithPlural(String[] arrstring, StandardPlural object) {
        String string = arrstring[object.ordinal()];
        object = string;
        if (string == null) {
            object = arrstring[StandardPlural.OTHER.ordinal()];
        }
        if (object != null) {
            return object;
        }
        throw new ICUException("Could not find data in 'other' plural variant");
    }

    private void multiSimpleFormatsToModifiers(String[] arrstring, String object, NumberFormat.Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object, stringBuilder, 1, 1);
        for (StandardPlural standardPlural : StandardPlural.VALUES) {
            String string2 = SimpleFormatterImpl.formatCompiledPattern(string, LongNameHandler.getWithPlural(arrstring, standardPlural));
            string2 = SimpleFormatterImpl.compileToStringMinMaxArguments(string2, stringBuilder, 0, 1);
            Modifier.Parameters parameters = new Modifier.Parameters();
            parameters.obj = this;
            parameters.signum = 0;
            parameters.plural = standardPlural;
            this.modifiers.put(standardPlural, new SimpleModifier(string2, field, false, parameters));
        }
    }

    private void simpleFormatsToModifiers(String[] arrstring, NumberFormat.Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StandardPlural standardPlural : StandardPlural.VALUES) {
            String string = SimpleFormatterImpl.compileToStringMinMaxArguments(LongNameHandler.getWithPlural(arrstring, standardPlural), stringBuilder, 0, 1);
            Modifier.Parameters parameters = new Modifier.Parameters();
            parameters.obj = this;
            parameters.signum = 0;
            parameters.plural = standardPlural;
            this.modifiers.put(standardPlural, new SimpleModifier(string, field, false, parameters));
        }
    }

    @Override
    public Modifier getModifier(int n, StandardPlural standardPlural) {
        return this.modifiers.get((Object)standardPlural);
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microProps = this.parent.processQuantity(decimalQuantity);
        decimalQuantity = decimalQuantity.createCopy();
        microProps.rounder.apply(decimalQuantity);
        microProps.modOuter = this.modifiers.get((Object)decimalQuantity.getStandardPlural(this.rules));
        return microProps;
    }

    private static final class PluralTableSink
    extends UResource.Sink {
        String[] outArray;

        public PluralTableSink(String[] arrstring) {
            this.outArray = arrstring;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2 = LongNameHandler.getIndex(key.toString());
                if (this.outArray[n2] == null) {
                    String string;
                    this.outArray[n2] = string = value.getString();
                }
                ++n;
            }
        }
    }

}


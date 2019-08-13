/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.StandardPlural;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.text.SimpleFormatter;
import android.icu.text.UFieldPosition;
import java.text.FieldPosition;
import java.text.Format;

class QuantityFormatter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final SimpleFormatter[] templates = new SimpleFormatter[StandardPlural.COUNT];

    public static StringBuilder format(String string, CharSequence charSequence, StringBuilder stringBuilder, FieldPosition fieldPosition) {
        int[] arrn = new int[1];
        SimpleFormatterImpl.formatAndAppend(string, stringBuilder, arrn, charSequence);
        if (fieldPosition.getBeginIndex() != 0 || fieldPosition.getEndIndex() != 0) {
            if (arrn[0] >= 0) {
                fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + arrn[0]);
                fieldPosition.setEndIndex(fieldPosition.getEndIndex() + arrn[0]);
            } else {
                fieldPosition.setBeginIndex(0);
                fieldPosition.setEndIndex(0);
            }
        }
        return stringBuilder;
    }

    public static StandardPlural selectPlural(double d, NumberFormat object, PluralRules pluralRules) {
        object = object instanceof DecimalFormat ? pluralRules.select(((DecimalFormat)object).getFixedDecimal(d)) : pluralRules.select(d);
        return StandardPlural.orOtherFromString((CharSequence)object);
    }

    public static StandardPlural selectPlural(Number object, NumberFormat numberFormat, PluralRules pluralRules, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        UFieldPosition uFieldPosition = new UFieldPosition(fieldPosition.getFieldAttribute(), fieldPosition.getField());
        numberFormat.format(object, stringBuffer, (FieldPosition)uFieldPosition);
        object = pluralRules.select(new PluralRules.FixedDecimal(((Number)object).doubleValue(), uFieldPosition.getCountVisibleFractionDigits(), uFieldPosition.getFractionDigits()));
        fieldPosition.setBeginIndex(uFieldPosition.getBeginIndex());
        fieldPosition.setEndIndex(uFieldPosition.getEndIndex());
        return StandardPlural.orOtherFromString((CharSequence)object);
    }

    public void addIfAbsent(CharSequence arrsimpleFormatter, String string) {
        int n = StandardPlural.indexFromString((CharSequence)arrsimpleFormatter);
        arrsimpleFormatter = this.templates;
        if (arrsimpleFormatter[n] != null) {
            return;
        }
        arrsimpleFormatter[n] = SimpleFormatter.compileMinMaxArguments(string, 0, 1);
    }

    public String format(double d, NumberFormat object, PluralRules object2) {
        String string = ((NumberFormat)object).format(d);
        object = QuantityFormatter.selectPlural(d, (NumberFormat)object, (PluralRules)object2);
        object = object2 = this.templates[((Enum)object).ordinal()];
        if (object2 == null) {
            object = this.templates[StandardPlural.OTHER_INDEX];
        }
        return ((SimpleFormatter)object).format(string);
    }

    public SimpleFormatter getByVariant(CharSequence object) {
        block0 : {
            int n = StandardPlural.indexOrOtherIndexFromString((CharSequence)object);
            object = this.templates[n];
            if (object != null || n == StandardPlural.OTHER_INDEX) break block0;
            object = this.templates[StandardPlural.OTHER_INDEX];
        }
        return object;
    }

    public boolean isValid() {
        boolean bl = this.templates[StandardPlural.OTHER_INDEX] != null;
        return bl;
    }
}


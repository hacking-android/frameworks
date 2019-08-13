/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.number.FormattedNumber;
import android.icu.number.LocalizedNumberFormatter;
import android.icu.text.DecimalFormat;
import android.icu.text.MessagePattern;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.text.RbnfLenientScanner;
import android.icu.text.UFormat;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class PluralFormat
extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 1L;
    private transient MessagePattern msgPattern;
    private NumberFormat numberFormat = null;
    private transient double offset = 0.0;
    private Map<String, String> parsedValues = null;
    private String pattern = null;
    private PluralRules pluralRules = null;
    private transient PluralSelectorAdapter pluralRulesWrapper = new PluralSelectorAdapter();
    private ULocale ulocale = null;

    public PluralFormat() {
        this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public PluralFormat(PluralRules pluralRules) {
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
    }

    public PluralFormat(PluralRules pluralRules, String string) {
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        this.applyPattern(string);
    }

    public PluralFormat(ULocale uLocale) {
        this.init(null, PluralRules.PluralType.CARDINAL, uLocale, null);
    }

    public PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType) {
        this.init(null, pluralType, uLocale, null);
    }

    public PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType, String string) {
        this.init(null, pluralType, uLocale, null);
        this.applyPattern(string);
    }

    PluralFormat(ULocale uLocale, PluralRules.PluralType pluralType, String string, NumberFormat numberFormat) {
        this.init(null, pluralType, uLocale, numberFormat);
        this.applyPattern(string);
    }

    public PluralFormat(ULocale uLocale, PluralRules pluralRules) {
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, uLocale, null);
    }

    public PluralFormat(ULocale uLocale, PluralRules pluralRules, String string) {
        this.init(pluralRules, PluralRules.PluralType.CARDINAL, uLocale, null);
        this.applyPattern(string);
    }

    public PluralFormat(ULocale uLocale, String string) {
        this.init(null, PluralRules.PluralType.CARDINAL, uLocale, null);
        this.applyPattern(string);
    }

    public PluralFormat(String string) {
        this.init(null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), null);
        this.applyPattern(string);
    }

    public PluralFormat(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    public PluralFormat(Locale locale, PluralRules.PluralType pluralType) {
        this(ULocale.forLocale(locale), pluralType);
    }

    public PluralFormat(Locale locale, PluralRules pluralRules) {
        this(ULocale.forLocale(locale), pluralRules);
    }

    static int findSubMessage(MessagePattern messagePattern, int n, PluralSelector pluralSelector, Object object, double d) {
        double d2;
        int n2;
        int n3;
        int n4 = messagePattern.countParts();
        Object object2 = messagePattern.getPart(n);
        if (((MessagePattern.Part)object2).getType().hasNumericValue()) {
            d2 = messagePattern.getNumericValue((MessagePattern.Part)object2);
            n2 = n + 1;
        } else {
            d2 = 0.0;
            n2 = n;
        }
        object2 = null;
        n = 0;
        int n5 = 0;
        int n6 = n2;
        do {
            Object object3;
            int n7;
            n3 = n6 + 1;
            MessagePattern.Part part = messagePattern.getPart(n6);
            if (part.getType() == MessagePattern.Part.Type.ARG_LIMIT) {
                n2 = n5;
                break;
            }
            if (messagePattern.getPartType(n3).hasNumericValue()) {
                n7 = n3 + 1;
                if (d == messagePattern.getNumericValue(messagePattern.getPart(n3))) {
                    return n7;
                }
                object3 = object2;
                n2 = n5;
            } else if (n == 0) {
                if (messagePattern.partSubstringMatches(part, "other")) {
                    if (n5 == 0) {
                        n2 = n3;
                        if (object2 != null && ((String)object2).equals("other")) {
                            n = 1;
                            object3 = object2;
                            n7 = n3;
                        } else {
                            object3 = object2;
                            n7 = n3;
                        }
                    } else {
                        object3 = object2;
                        n2 = n5;
                        n7 = n3;
                    }
                } else {
                    if (object2 == null) {
                        object2 = object3 = pluralSelector.select(object, d - d2);
                        n6 = n;
                        if (n5 != 0) {
                            object2 = object3;
                            n6 = n;
                            if (((String)object3).equals("other")) {
                                n6 = 1;
                                object2 = object3;
                            }
                        }
                    } else {
                        n6 = n;
                    }
                    object3 = object2;
                    n = n6;
                    n2 = n5;
                    n7 = n3;
                    if (n6 == 0) {
                        object3 = object2;
                        n = n6;
                        n2 = n5;
                        n7 = n3;
                        if (messagePattern.partSubstringMatches(part, (String)object2)) {
                            n2 = n3;
                            n = 1;
                            object3 = object2;
                            n7 = n3;
                        }
                    }
                }
            } else {
                n7 = n3;
                n2 = n5;
                object3 = object2;
            }
            n6 = n3 = messagePattern.getLimitPartIndex(n7) + 1;
            object2 = object3;
            n5 = n2;
        } while (n3 < n4);
        return n2;
    }

    private String format(Number object, double d) {
        Object object2 = this.msgPattern;
        if (object2 != null && ((MessagePattern)object2).countParts() != 0) {
            Object object3;
            double d2 = this.offset;
            double d3 = d - d2;
            object2 = this.numberFormat;
            if (object2 instanceof DecimalFormat) {
                object2 = ((DecimalFormat)object2).toNumberFormatter();
                object = this.offset == 0.0 ? ((LocalizedNumberFormatter)object2).format((Number)object) : ((LocalizedNumberFormatter)object2).format(d3);
                object3 = ((FormattedNumber)object).toString();
                object = ((FormattedNumber)object).getFixedDecimal();
            } else {
                object = d2 == 0.0 ? ((Format)object2).format(object) : ((NumberFormat)object2).format(d3);
                object2 = new PluralRules.FixedDecimal(d3);
                object3 = object;
                object = object2;
            }
            int n = PluralFormat.findSubMessage(this.msgPattern, 0, this.pluralRulesWrapper, object, d);
            object = null;
            int n2 = this.msgPattern.getPart(n).getLimit();
            do {
                int n3;
                object2 = this.msgPattern;
                int n4 = n + 1;
                MessagePattern.Part part = ((MessagePattern)object2).getPart(n4);
                MessagePattern.Part.Type type = part.getType();
                int n5 = part.getIndex();
                if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                    if (object == null) {
                        return this.pattern.substring(n2, n5);
                    }
                    ((StringBuilder)object).append(this.pattern, n2, n5);
                    return ((StringBuilder)object).toString();
                }
                if (!(type == MessagePattern.Part.Type.REPLACE_NUMBER || type == MessagePattern.Part.Type.SKIP_SYNTAX && this.msgPattern.jdkAposMode())) {
                    n = n4;
                    object2 = object;
                    n3 = n2;
                    if (type == MessagePattern.Part.Type.ARG_START) {
                        object2 = object;
                        if (object == null) {
                            object2 = new StringBuilder();
                        }
                        ((StringBuilder)object2).append(this.pattern, n2, n5);
                        n = this.msgPattern.getLimitPartIndex(n4);
                        n3 = this.msgPattern.getPart(n).getLimit();
                        MessagePattern.appendReducedApostrophes(this.pattern, n5, n3, (StringBuilder)object2);
                    }
                } else {
                    object2 = object;
                    if (object == null) {
                        object2 = new StringBuilder();
                    }
                    ((StringBuilder)object2).append(this.pattern, n2, n5);
                    if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                        ((StringBuilder)object2).append((String)object3);
                    }
                    n3 = part.getLimit();
                    n = n4;
                }
                object = object2;
                n2 = n3;
            } while (true);
        }
        return this.numberFormat.format(object);
    }

    private void init(PluralRules serializable, PluralRules.PluralType pluralType, ULocale uLocale, NumberFormat numberFormat) {
        this.ulocale = uLocale;
        if (serializable == null) {
            serializable = PluralRules.forLocale(this.ulocale, pluralType);
        }
        this.pluralRules = serializable;
        this.resetPattern();
        serializable = numberFormat == null ? NumberFormat.getInstance(this.ulocale) : numberFormat;
        this.numberFormat = serializable;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        this.pluralRulesWrapper = new PluralSelectorAdapter();
        this.parsedValues = null;
        object = this.pattern;
        if (object != null) {
            this.applyPattern((String)object);
        }
    }

    private void resetPattern() {
        this.pattern = null;
        MessagePattern messagePattern = this.msgPattern;
        if (messagePattern != null) {
            messagePattern.clear();
        }
        this.offset = 0.0;
    }

    public void applyPattern(String string) {
        this.pattern = string;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        try {
            this.msgPattern.parsePluralStyle(string);
            this.offset = this.msgPattern.getPluralOffset(0);
            return;
        }
        catch (RuntimeException runtimeException) {
            this.resetPattern();
            throw runtimeException;
        }
    }

    public boolean equals(PluralFormat pluralFormat) {
        return this.equals((Object)pluralFormat);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (PluralFormat)object;
            if (!(Objects.equals(this.ulocale, ((PluralFormat)object).ulocale) && Objects.equals(this.pluralRules, ((PluralFormat)object).pluralRules) && Objects.equals(this.msgPattern, ((PluralFormat)object).msgPattern) && Objects.equals(this.numberFormat, ((PluralFormat)object).numberFormat))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public final String format(double d) {
        return this.format(d, d);
    }

    @Override
    public StringBuffer format(Object object, StringBuffer abstractStringBuilder, FieldPosition fieldPosition) {
        if (object instanceof Number) {
            object = (Number)object;
            ((StringBuffer)abstractStringBuilder).append(this.format((Number)object, ((Number)object).doubleValue()));
            return abstractStringBuilder;
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("'");
        ((StringBuilder)abstractStringBuilder).append(object);
        ((StringBuilder)abstractStringBuilder).append("' is not a Number");
        throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
    }

    public int hashCode() {
        return this.pluralRules.hashCode() ^ this.parsedValues.hashCode();
    }

    public Number parse(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    String parseType(String string, RbnfLenientScanner rbnfLenientScanner, FieldPosition fieldPosition) {
        block8 : {
            int n;
            Object object = this;
            MessagePattern messagePattern = ((PluralFormat)object).msgPattern;
            if (messagePattern == null || messagePattern.countParts() == 0) break block8;
            int n2 = 0;
            int n3 = ((PluralFormat)object).msgPattern.countParts();
            int n4 = n = fieldPosition.getBeginIndex();
            if (n < 0) {
                n4 = 0;
            }
            messagePattern = null;
            object = null;
            n = -1;
            do {
                Object object2;
                int n5;
                Object object3;
                int n6;
                block9 : {
                    String string2;
                    MessagePattern.Part part;
                    MessagePattern.Part part2;
                    PluralFormat pluralFormat;
                    block10 : {
                        object3 = string;
                        pluralFormat = this;
                        if (n2 >= n3) break;
                        object2 = pluralFormat.msgPattern;
                        n5 = n2 + 1;
                        if (((MessagePattern)object2).getPart(n2).getType() != MessagePattern.Part.Type.ARG_SELECTOR) {
                            n2 = n5;
                            continue;
                        }
                        object2 = pluralFormat.msgPattern;
                        n2 = n5 + 1;
                        part = ((MessagePattern)object2).getPart(n5);
                        if (part.getType() != MessagePattern.Part.Type.MSG_START) continue;
                        object2 = pluralFormat.msgPattern;
                        n5 = n2 + 1;
                        part2 = ((MessagePattern)object2).getPart(n2);
                        if (part2.getType() != MessagePattern.Part.Type.MSG_LIMIT) {
                            n2 = n5;
                            continue;
                        }
                        string2 = pluralFormat.pattern.substring(part.getLimit(), part2.getIndex());
                        n2 = rbnfLenientScanner != null ? rbnfLenientScanner.findText((String)object3, string2, n4)[0] : ((String)object3).indexOf(string2, n4);
                        object2 = messagePattern;
                        object3 = object;
                        n6 = n;
                        if (n2 < 0) break block9;
                        object2 = messagePattern;
                        object3 = object;
                        n6 = n;
                        if (n2 < n) break block9;
                        if (object == null) break block10;
                        object2 = messagePattern;
                        object3 = object;
                        n6 = n;
                        if (string2.length() <= ((String)object).length()) break block9;
                    }
                    object3 = string2;
                    object2 = pluralFormat.pattern.substring(part.getLimit(), part2.getIndex());
                    n6 = n2;
                }
                n2 = n5;
                messagePattern = object2;
                object = object3;
                n = n6;
            } while (true);
            if (messagePattern != null) {
                fieldPosition.setBeginIndex(n);
                fieldPosition.setEndIndex(((String)object).length() + n);
                return messagePattern;
            }
            fieldPosition.setBeginIndex(-1);
            fieldPosition.setEndIndex(-1);
            return null;
        }
        fieldPosition.setBeginIndex(-1);
        fieldPosition.setEndIndex(-1);
        return null;
    }

    @Deprecated
    public void setLocale(ULocale uLocale) {
        ULocale uLocale2 = uLocale;
        if (uLocale == null) {
            uLocale2 = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        this.init(null, PluralRules.PluralType.CARDINAL, uLocale2, null);
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public String toPattern() {
        return this.pattern;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("locale=");
        stringBuilder2.append(this.ulocale);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", rules='");
        stringBuilder2.append(this.pluralRules);
        stringBuilder2.append("'");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", pattern='");
        stringBuilder2.append(this.pattern);
        stringBuilder2.append("'");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", format='");
        stringBuilder2.append(this.numberFormat);
        stringBuilder2.append("'");
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }

    static interface PluralSelector {
        public String select(Object var1, double var2);
    }

    private final class PluralSelectorAdapter
    implements PluralSelector {
        private PluralSelectorAdapter() {
        }

        @Override
        public String select(Object object, double d) {
            object = (PluralRules.IFixedDecimal)object;
            return PluralFormat.this.pluralRules.select((PluralRules.IFixedDecimal)object);
        }
    }

}


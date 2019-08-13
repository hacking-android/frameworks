/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.math.BigDecimal
 *  android.icu.text.DecimalFormatSymbols
 *  android.icu.text.DecimalFormat_ICU58_Android
 *  android.icu.text.NumberFormat
 *  android.icu.text.NumberFormat$Field
 *  libcore.icu.LocaleData
 */
package java.text;

import android.icu.math.BigDecimal;
import android.icu.text.DecimalFormat_ICU58_Android;
import android.icu.text.NumberFormat;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import libcore.icu.LocaleData;

public class DecimalFormat
extends NumberFormat {
    static final int DOUBLE_FRACTION_DIGITS = 340;
    static final int DOUBLE_INTEGER_DIGITS = 309;
    static final int MAXIMUM_FRACTION_DIGITS = Integer.MAX_VALUE;
    static final int MAXIMUM_INTEGER_DIGITS = Integer.MAX_VALUE;
    static final int currentSerialVersion = 4;
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("positivePrefix", String.class), new ObjectStreamField("positiveSuffix", String.class), new ObjectStreamField("negativePrefix", String.class), new ObjectStreamField("negativeSuffix", String.class), new ObjectStreamField("posPrefixPattern", String.class), new ObjectStreamField("posSuffixPattern", String.class), new ObjectStreamField("negPrefixPattern", String.class), new ObjectStreamField("negSuffixPattern", String.class), new ObjectStreamField("multiplier", Integer.TYPE), new ObjectStreamField("groupingSize", Byte.TYPE), new ObjectStreamField("groupingUsed", Boolean.TYPE), new ObjectStreamField("decimalSeparatorAlwaysShown", Boolean.TYPE), new ObjectStreamField("parseBigDecimal", Boolean.TYPE), new ObjectStreamField("roundingMode", RoundingMode.class), new ObjectStreamField("symbols", DecimalFormatSymbols.class), new ObjectStreamField("useExponentialNotation", Boolean.TYPE), new ObjectStreamField("minExponentDigits", Byte.TYPE), new ObjectStreamField("maximumIntegerDigits", Integer.TYPE), new ObjectStreamField("minimumIntegerDigits", Integer.TYPE), new ObjectStreamField("maximumFractionDigits", Integer.TYPE), new ObjectStreamField("minimumFractionDigits", Integer.TYPE), new ObjectStreamField("serialVersionOnStream", Integer.TYPE)};
    static final long serialVersionUID = 864413376551465018L;
    private transient DecimalFormat_ICU58_Android icuDecimalFormat;
    private int maximumFractionDigits;
    private int maximumIntegerDigits;
    private int minimumFractionDigits;
    private int minimumIntegerDigits;
    private RoundingMode roundingMode = RoundingMode.HALF_EVEN;
    private DecimalFormatSymbols symbols = null;

    public DecimalFormat() {
        Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        String string = LocaleData.get((Locale)locale).numberPattern;
        this.symbols = DecimalFormatSymbols.getInstance(locale);
        this.initPattern(string);
    }

    public DecimalFormat(String string) {
        this.symbols = DecimalFormatSymbols.getInstance(Locale.getDefault(Locale.Category.FORMAT));
        this.initPattern(string);
    }

    public DecimalFormat(String string, DecimalFormatSymbols decimalFormatSymbols) {
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.initPattern(string);
    }

    private boolean compareIcuRoundingIncrement(DecimalFormat_ICU58_Android decimalFormat_ICU58_Android) {
        java.math.BigDecimal bigDecimal = this.icuDecimalFormat.getRoundingIncrement();
        boolean bl = true;
        boolean bl2 = true;
        if (bigDecimal != null) {
            if (decimalFormat_ICU58_Android.getRoundingIncrement() == null || !bigDecimal.equals(decimalFormat_ICU58_Android.getRoundingIncrement())) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = decimalFormat_ICU58_Android.getRoundingIncrement() == null ? bl : false;
        return bl2;
    }

    private static int convertRoundingMode(RoundingMode roundingMode) {
        switch (roundingMode) {
            default: {
                throw new IllegalArgumentException("Invalid rounding mode specified");
            }
            case UNNECESSARY: {
                return 7;
            }
            case HALF_EVEN: {
                return 6;
            }
            case HALF_DOWN: {
                return 5;
            }
            case HALF_UP: {
                return 4;
            }
            case FLOOR: {
                return 3;
            }
            case CEILING: {
                return 2;
            }
            case DOWN: {
                return 1;
            }
            case UP: 
        }
        return 0;
    }

    private StringBuffer format(java.math.BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FieldPosition fieldPosition2 = DecimalFormat.getIcuFieldPosition(fieldPosition);
        this.icuDecimalFormat.format(bigDecimal, stringBuffer, fieldPosition);
        fieldPosition.setBeginIndex(fieldPosition2.getBeginIndex());
        fieldPosition.setEndIndex(fieldPosition2.getEndIndex());
        return stringBuffer;
    }

    private StringBuffer format(BigInteger bigInteger, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FieldPosition fieldPosition2 = DecimalFormat.getIcuFieldPosition(fieldPosition);
        this.icuDecimalFormat.format(bigInteger, stringBuffer, fieldPosition);
        fieldPosition.setBeginIndex(fieldPosition2.getBeginIndex());
        fieldPosition.setEndIndex(fieldPosition2.getEndIndex());
        return stringBuffer;
    }

    private static FieldPosition getIcuFieldPosition(FieldPosition fieldPosition) {
        block15 : {
            Object object;
            block5 : {
                block14 : {
                    block13 : {
                        block12 : {
                            block11 : {
                                block10 : {
                                    block9 : {
                                        block8 : {
                                            block7 : {
                                                block6 : {
                                                    block4 : {
                                                        object = fieldPosition.getFieldAttribute();
                                                        if (object == null) {
                                                            return fieldPosition;
                                                        }
                                                        if (object != NumberFormat.Field.INTEGER) break block4;
                                                        object = NumberFormat.Field.INTEGER;
                                                        break block5;
                                                    }
                                                    if (object != NumberFormat.Field.FRACTION) break block6;
                                                    object = NumberFormat.Field.FRACTION;
                                                    break block5;
                                                }
                                                if (object != NumberFormat.Field.DECIMAL_SEPARATOR) break block7;
                                                object = NumberFormat.Field.DECIMAL_SEPARATOR;
                                                break block5;
                                            }
                                            if (object != NumberFormat.Field.EXPONENT_SYMBOL) break block8;
                                            object = NumberFormat.Field.EXPONENT_SYMBOL;
                                            break block5;
                                        }
                                        if (object != NumberFormat.Field.EXPONENT_SIGN) break block9;
                                        object = NumberFormat.Field.EXPONENT_SIGN;
                                        break block5;
                                    }
                                    if (object != NumberFormat.Field.EXPONENT) break block10;
                                    object = NumberFormat.Field.EXPONENT;
                                    break block5;
                                }
                                if (object != NumberFormat.Field.GROUPING_SEPARATOR) break block11;
                                object = NumberFormat.Field.GROUPING_SEPARATOR;
                                break block5;
                            }
                            if (object != NumberFormat.Field.CURRENCY) break block12;
                            object = NumberFormat.Field.CURRENCY;
                            break block5;
                        }
                        if (object != NumberFormat.Field.PERCENT) break block13;
                        object = NumberFormat.Field.PERCENT;
                        break block5;
                    }
                    if (object != NumberFormat.Field.PERMILLE) break block14;
                    object = NumberFormat.Field.PERMILLE;
                    break block5;
                }
                if (object != NumberFormat.Field.SIGN) break block15;
                object = NumberFormat.Field.SIGN;
            }
            object = new FieldPosition((Format.Field)object);
            ((FieldPosition)object).setBeginIndex(fieldPosition.getBeginIndex());
            ((FieldPosition)object).setEndIndex(fieldPosition.getEndIndex());
            return object;
        }
        throw new IllegalArgumentException("Unexpected field position attribute type.");
    }

    private void initPattern(String string) {
        this.icuDecimalFormat = new DecimalFormat_ICU58_Android(string, this.symbols.getIcuDecimalFormatSymbols());
        this.updateFieldsFromIcu();
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        int n;
        boolean bl;
        object = ((ObjectInputStream)object).readFields();
        this.symbols = (DecimalFormatSymbols)((ObjectInputStream.GetField)object).get("symbols", null);
        this.initPattern("#");
        Object object2 = (String)((ObjectInputStream.GetField)object).get("positivePrefix", "");
        if (!Objects.equals(object2, this.icuDecimalFormat.getPositivePrefix())) {
            this.icuDecimalFormat.setPositivePrefix(object2);
        }
        if (!Objects.equals(object2 = (String)((ObjectInputStream.GetField)object).get("positiveSuffix", ""), this.icuDecimalFormat.getPositiveSuffix())) {
            this.icuDecimalFormat.setPositiveSuffix(object2);
        }
        if (!Objects.equals(object2 = (String)((ObjectInputStream.GetField)object).get("negativePrefix", "-"), this.icuDecimalFormat.getNegativePrefix())) {
            this.icuDecimalFormat.setNegativePrefix(object2);
        }
        if (!Objects.equals(object2 = (String)((ObjectInputStream.GetField)object).get("negativeSuffix", ""), this.icuDecimalFormat.getNegativeSuffix())) {
            this.icuDecimalFormat.setNegativeSuffix(object2);
        }
        if ((n = ((ObjectInputStream.GetField)object).get("multiplier", 1)) != this.icuDecimalFormat.getMultiplier()) {
            this.icuDecimalFormat.setMultiplier(n);
        }
        if ((bl = ((ObjectInputStream.GetField)object).get("groupingUsed", true)) != this.icuDecimalFormat.isGroupingUsed()) {
            this.icuDecimalFormat.setGroupingUsed(bl);
        }
        if ((n = (int)((ObjectInputStream.GetField)object).get("groupingSize", (byte)3)) != this.icuDecimalFormat.getGroupingSize()) {
            this.icuDecimalFormat.setGroupingSize(n);
        }
        if ((bl = ((ObjectInputStream.GetField)object).get("decimalSeparatorAlwaysShown", false)) != this.icuDecimalFormat.isDecimalSeparatorAlwaysShown()) {
            this.icuDecimalFormat.setDecimalSeparatorAlwaysShown(bl);
        }
        if (DecimalFormat.convertRoundingMode((RoundingMode)((Object)(object2 = (RoundingMode)((Object)((ObjectInputStream.GetField)object).get("roundingMode", (Object)RoundingMode.HALF_EVEN))))) != this.icuDecimalFormat.getRoundingMode()) {
            this.setRoundingMode((RoundingMode)((Object)object2));
        }
        if ((n = ((ObjectInputStream.GetField)object).get("maximumIntegerDigits", 309)) != this.icuDecimalFormat.getMaximumIntegerDigits()) {
            this.icuDecimalFormat.setMaximumIntegerDigits(n);
        }
        if ((n = ((ObjectInputStream.GetField)object).get("minimumIntegerDigits", 309)) != this.icuDecimalFormat.getMinimumIntegerDigits()) {
            this.icuDecimalFormat.setMinimumIntegerDigits(n);
        }
        if ((n = ((ObjectInputStream.GetField)object).get("maximumFractionDigits", 340)) != this.icuDecimalFormat.getMaximumFractionDigits()) {
            this.icuDecimalFormat.setMaximumFractionDigits(n);
        }
        if ((n = ((ObjectInputStream.GetField)object).get("minimumFractionDigits", 340)) != this.icuDecimalFormat.getMinimumFractionDigits()) {
            this.icuDecimalFormat.setMinimumFractionDigits(n);
        }
        if ((bl = ((ObjectInputStream.GetField)object).get("parseBigDecimal", true)) != this.icuDecimalFormat.isParseBigDecimal()) {
            this.icuDecimalFormat.setParseBigDecimal(bl);
        }
        this.updateFieldsFromIcu();
        if (((ObjectInputStream.GetField)object).get("serialVersionOnStream", 0) < 3) {
            this.setMaximumIntegerDigits(super.getMaximumIntegerDigits());
            this.setMinimumIntegerDigits(super.getMinimumIntegerDigits());
            this.setMaximumFractionDigits(super.getMaximumFractionDigits());
            this.setMinimumFractionDigits(super.getMinimumFractionDigits());
        }
    }

    private static NumberFormat.Field toJavaFieldAttribute(AttributedCharacterIterator.Attribute serializable) {
        String string = ((AttributedCharacterIterator.Attribute)serializable).getName();
        if (string.equals(NumberFormat.Field.INTEGER.getName())) {
            return NumberFormat.Field.INTEGER;
        }
        if (string.equals(NumberFormat.Field.CURRENCY.getName())) {
            return NumberFormat.Field.CURRENCY;
        }
        if (string.equals(NumberFormat.Field.DECIMAL_SEPARATOR.getName())) {
            return NumberFormat.Field.DECIMAL_SEPARATOR;
        }
        if (string.equals(NumberFormat.Field.EXPONENT.getName())) {
            return NumberFormat.Field.EXPONENT;
        }
        if (string.equals(NumberFormat.Field.EXPONENT_SIGN.getName())) {
            return NumberFormat.Field.EXPONENT_SIGN;
        }
        if (string.equals(NumberFormat.Field.EXPONENT_SYMBOL.getName())) {
            return NumberFormat.Field.EXPONENT_SYMBOL;
        }
        if (string.equals(NumberFormat.Field.FRACTION.getName())) {
            return NumberFormat.Field.FRACTION;
        }
        if (string.equals(NumberFormat.Field.GROUPING_SEPARATOR.getName())) {
            return NumberFormat.Field.GROUPING_SEPARATOR;
        }
        if (string.equals(NumberFormat.Field.SIGN.getName())) {
            return NumberFormat.Field.SIGN;
        }
        if (string.equals(NumberFormat.Field.PERCENT.getName())) {
            return NumberFormat.Field.PERCENT;
        }
        if (string.equals(NumberFormat.Field.PERMILLE.getName())) {
            return NumberFormat.Field.PERMILLE;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unrecognized attribute: ");
        ((StringBuilder)serializable).append(string);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    private void updateFieldsFromIcu() {
        if (this.icuDecimalFormat.getMaximumIntegerDigits() == 309) {
            this.icuDecimalFormat.setMaximumIntegerDigits(2000000000);
        }
        this.maximumIntegerDigits = this.icuDecimalFormat.getMaximumIntegerDigits();
        this.minimumIntegerDigits = this.icuDecimalFormat.getMinimumIntegerDigits();
        this.maximumFractionDigits = this.icuDecimalFormat.getMaximumFractionDigits();
        this.minimumFractionDigits = this.icuDecimalFormat.getMinimumFractionDigits();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        ObjectOutputStream.PutField putField = objectOutputStream.putFields();
        putField.put("positivePrefix", this.icuDecimalFormat.getPositivePrefix());
        putField.put("positiveSuffix", this.icuDecimalFormat.getPositiveSuffix());
        putField.put("negativePrefix", this.icuDecimalFormat.getNegativePrefix());
        putField.put("negativeSuffix", this.icuDecimalFormat.getNegativeSuffix());
        String string = null;
        putField.put("posPrefixPattern", string);
        putField.put("posSuffixPattern", string);
        putField.put("negPrefixPattern", string);
        putField.put("negSuffixPattern", string);
        putField.put("multiplier", this.icuDecimalFormat.getMultiplier());
        putField.put("groupingSize", (byte)this.icuDecimalFormat.getGroupingSize());
        putField.put("groupingUsed", this.icuDecimalFormat.isGroupingUsed());
        putField.put("decimalSeparatorAlwaysShown", this.icuDecimalFormat.isDecimalSeparatorAlwaysShown());
        putField.put("parseBigDecimal", this.icuDecimalFormat.isParseBigDecimal());
        putField.put("roundingMode", (Object)this.roundingMode);
        putField.put("symbols", this.symbols);
        putField.put("useExponentialNotation", false);
        putField.put("minExponentDigits", (byte)0);
        putField.put("maximumIntegerDigits", this.icuDecimalFormat.getMaximumIntegerDigits());
        putField.put("minimumIntegerDigits", this.icuDecimalFormat.getMinimumIntegerDigits());
        putField.put("maximumFractionDigits", this.icuDecimalFormat.getMaximumFractionDigits());
        putField.put("minimumFractionDigits", this.icuDecimalFormat.getMinimumFractionDigits());
        putField.put("serialVersionOnStream", 4);
        objectOutputStream.writeFields();
    }

    void adjustForCurrencyDefaultFractionDigits() {
        Currency currency;
        int n;
        Currency currency2 = currency = this.symbols.getCurrency();
        if (currency == null) {
            try {
                currency2 = Currency.getInstance(this.symbols.getInternationalCurrencySymbol());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                currency2 = currency;
            }
        }
        if (currency2 != null && (n = currency2.getDefaultFractionDigits()) != -1) {
            int n2 = this.getMinimumFractionDigits();
            if (n2 == this.getMaximumFractionDigits()) {
                this.setMinimumFractionDigits(n);
                this.setMaximumFractionDigits(n);
            } else {
                this.setMinimumFractionDigits(Math.min(n, n2));
                this.setMaximumFractionDigits(n);
            }
        }
    }

    public void applyLocalizedPattern(String string) {
        this.icuDecimalFormat.applyLocalizedPattern(string);
        this.updateFieldsFromIcu();
    }

    public void applyPattern(String string) {
        this.icuDecimalFormat.applyPattern(string);
        this.updateFieldsFromIcu();
    }

    @Override
    public Object clone() {
        try {
            DecimalFormat decimalFormat = (DecimalFormat)super.clone();
            decimalFormat.icuDecimalFormat = (DecimalFormat_ICU58_Android)this.icuDecimalFormat.clone();
            decimalFormat.symbols = (DecimalFormatSymbols)this.symbols.clone();
            return decimalFormat;
        }
        catch (Exception exception) {
            throw new InternalError();
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        block3 : {
            bl = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (!(object instanceof DecimalFormat)) {
                return false;
            }
            object = (DecimalFormat)object;
            if (!this.icuDecimalFormat.equals((Object)((DecimalFormat)object).icuDecimalFormat) || !this.compareIcuRoundingIncrement(((DecimalFormat)object).icuDecimalFormat)) break block3;
            bl = true;
        }
        return bl;
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FieldPosition fieldPosition2 = DecimalFormat.getIcuFieldPosition(fieldPosition);
        this.icuDecimalFormat.format(d, stringBuffer, fieldPosition2);
        fieldPosition.setBeginIndex(fieldPosition2.getBeginIndex());
        fieldPosition.setEndIndex(fieldPosition2.getEndIndex());
        return stringBuffer;
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FieldPosition fieldPosition2 = DecimalFormat.getIcuFieldPosition(fieldPosition);
        this.icuDecimalFormat.format(l, stringBuffer, fieldPosition2);
        fieldPosition.setBeginIndex(fieldPosition2.getBeginIndex());
        fieldPosition.setEndIndex(fieldPosition2.getEndIndex());
        return stringBuffer;
    }

    @Override
    public final StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (!(object instanceof Long || object instanceof Integer || object instanceof Short || object instanceof Byte || object instanceof AtomicInteger || object instanceof AtomicLong || object instanceof BigInteger && ((BigInteger)object).bitLength() < 64)) {
            if (object instanceof java.math.BigDecimal) {
                return this.format((java.math.BigDecimal)object, stringBuffer, fieldPosition);
            }
            if (object instanceof BigInteger) {
                return this.format((BigInteger)object, stringBuffer, fieldPosition);
            }
            if (object instanceof Number) {
                return this.format(((Number)object).doubleValue(), stringBuffer, fieldPosition);
            }
            throw new IllegalArgumentException("Cannot format given Object as a Number");
        }
        return this.format(((Number)object).longValue(), stringBuffer, fieldPosition);
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        if (object != null) {
            int n;
            object = this.icuDecimalFormat.formatToCharacterIterator(object);
            StringBuilder stringBuilder = new StringBuilder(object.getEndIndex() - object.getBeginIndex());
            for (n = object.getBeginIndex(); n < object.getEndIndex(); ++n) {
                stringBuilder.append(object.current());
                object.next();
            }
            AttributedString attributedString = new AttributedString(stringBuilder.toString());
            for (n = object.getBeginIndex(); n < object.getEndIndex(); ++n) {
                object.setIndex(n);
                for (AttributedCharacterIterator.Attribute attribute : object.getAttributes().keySet()) {
                    int n2 = object.getRunStart();
                    int n3 = object.getRunLimit();
                    attribute = DecimalFormat.toJavaFieldAttribute(attribute);
                    attributedString.addAttribute(attribute, attribute, n2, n3);
                }
            }
            return attributedString.getIterator();
        }
        throw new NullPointerException("object == null");
    }

    @Override
    public Currency getCurrency() {
        return this.symbols.getCurrency();
    }

    public DecimalFormatSymbols getDecimalFormatSymbols() {
        return DecimalFormatSymbols.fromIcuInstance(this.icuDecimalFormat.getDecimalFormatSymbols());
    }

    public int getGroupingSize() {
        return this.icuDecimalFormat.getGroupingSize();
    }

    @Override
    public int getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }

    @Override
    public int getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }

    @Override
    public int getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }

    @Override
    public int getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }

    public int getMultiplier() {
        return this.icuDecimalFormat.getMultiplier();
    }

    public String getNegativePrefix() {
        return this.icuDecimalFormat.getNegativePrefix();
    }

    public String getNegativeSuffix() {
        return this.icuDecimalFormat.getNegativeSuffix();
    }

    public String getPositivePrefix() {
        return this.icuDecimalFormat.getPositivePrefix();
    }

    public String getPositiveSuffix() {
        return this.icuDecimalFormat.getPositiveSuffix();
    }

    @Override
    public RoundingMode getRoundingMode() {
        return this.roundingMode;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 37 + this.getPositivePrefix().hashCode();
    }

    public boolean isDecimalSeparatorAlwaysShown() {
        return this.icuDecimalFormat.isDecimalSeparatorAlwaysShown();
    }

    @Override
    public boolean isGroupingUsed() {
        return this.icuDecimalFormat.isGroupingUsed();
    }

    public boolean isParseBigDecimal() {
        return this.icuDecimalFormat.isParseBigDecimal();
    }

    @Override
    public boolean isParseIntegerOnly() {
        return this.icuDecimalFormat.isParseIntegerOnly();
    }

    @Override
    public Number parse(String object, ParsePosition parsePosition) {
        if (parsePosition.index >= 0 && parsePosition.index < ((String)object).length()) {
            if ((object = this.icuDecimalFormat.parse((String)object, parsePosition)) == null) {
                return null;
            }
            if (this.isParseBigDecimal()) {
                if (object instanceof Long) {
                    return new java.math.BigDecimal(((Number)object).longValue());
                }
                if (object instanceof Double && !((Double)object).isInfinite() && !((Double)object).isNaN()) {
                    return new java.math.BigDecimal(object.toString());
                }
                if (object instanceof Double && (((Double)object).isNaN() || ((Double)object).isInfinite())) {
                    return object;
                }
                if (object instanceof BigDecimal) {
                    return ((BigDecimal)object).toBigDecimal();
                }
            }
            if (!(object instanceof BigDecimal) && !(object instanceof BigInteger)) {
                if (this.isParseIntegerOnly() && object.equals(new Double(0.0))) {
                    return 0L;
                }
                return object;
            }
            return ((Number)object).doubleValue();
        }
        return null;
    }

    @Override
    public void setCurrency(Currency currency) {
        if (currency != this.symbols.getCurrency() || !currency.getSymbol().equals(this.symbols.getCurrencySymbol())) {
            this.symbols.setCurrency(currency);
            this.icuDecimalFormat.setDecimalFormatSymbols(this.symbols.getIcuDecimalFormatSymbols());
            this.icuDecimalFormat.setMinimumFractionDigits(this.minimumFractionDigits);
            this.icuDecimalFormat.setMaximumFractionDigits(this.maximumFractionDigits);
        }
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        try {
            this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
            this.icuDecimalFormat.setDecimalFormatSymbols(this.symbols.getIcuDecimalFormatSymbols());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setDecimalSeparatorAlwaysShown(boolean bl) {
        this.icuDecimalFormat.setDecimalSeparatorAlwaysShown(bl);
    }

    public void setGroupingSize(int n) {
        this.icuDecimalFormat.setGroupingSize(n);
    }

    @Override
    public void setGroupingUsed(boolean bl) {
        this.icuDecimalFormat.setGroupingUsed(bl);
    }

    @Override
    public void setMaximumFractionDigits(int n) {
        this.maximumFractionDigits = Math.min(Math.max(0, n), Integer.MAX_VALUE);
        if ((n = this.maximumFractionDigits) > 340) {
            n = 340;
        }
        super.setMaximumFractionDigits(n);
        int n2 = this.minimumFractionDigits;
        n = this.maximumFractionDigits;
        if (n2 > n) {
            this.minimumFractionDigits = n;
            if ((n = this.minimumFractionDigits) > 340) {
                n = 340;
            }
            super.setMinimumFractionDigits(n);
        }
        this.icuDecimalFormat.setMaximumFractionDigits(this.getMaximumFractionDigits());
    }

    @Override
    public void setMaximumIntegerDigits(int n) {
        this.maximumIntegerDigits = Math.min(Math.max(0, n), Integer.MAX_VALUE);
        if ((n = this.maximumIntegerDigits) > 309) {
            n = 309;
        }
        super.setMaximumIntegerDigits(n);
        int n2 = this.minimumIntegerDigits;
        n = this.maximumIntegerDigits;
        if (n2 > n) {
            this.minimumIntegerDigits = n;
            if ((n = this.minimumIntegerDigits) > 309) {
                n = 309;
            }
            super.setMinimumIntegerDigits(n);
        }
        this.icuDecimalFormat.setMaximumIntegerDigits(this.getMaximumIntegerDigits());
    }

    @Override
    public void setMinimumFractionDigits(int n) {
        this.minimumFractionDigits = Math.min(Math.max(0, n), Integer.MAX_VALUE);
        if ((n = this.minimumFractionDigits) > 340) {
            n = 340;
        }
        super.setMinimumFractionDigits(n);
        n = this.minimumFractionDigits;
        if (n > this.maximumFractionDigits) {
            this.maximumFractionDigits = n;
            if ((n = this.maximumFractionDigits) > 340) {
                n = 340;
            }
            super.setMaximumFractionDigits(n);
        }
        this.icuDecimalFormat.setMinimumFractionDigits(this.getMinimumFractionDigits());
    }

    @Override
    public void setMinimumIntegerDigits(int n) {
        this.minimumIntegerDigits = Math.min(Math.max(0, n), Integer.MAX_VALUE);
        if ((n = this.minimumIntegerDigits) > 309) {
            n = 309;
        }
        super.setMinimumIntegerDigits(n);
        n = this.minimumIntegerDigits;
        if (n > this.maximumIntegerDigits) {
            this.maximumIntegerDigits = n;
            if ((n = this.maximumIntegerDigits) > 309) {
                n = 309;
            }
            super.setMaximumIntegerDigits(n);
        }
        this.icuDecimalFormat.setMinimumIntegerDigits(this.getMinimumIntegerDigits());
    }

    public void setMultiplier(int n) {
        this.icuDecimalFormat.setMultiplier(n);
    }

    public void setNegativePrefix(String string) {
        this.icuDecimalFormat.setNegativePrefix(string);
    }

    public void setNegativeSuffix(String string) {
        this.icuDecimalFormat.setNegativeSuffix(string);
    }

    public void setParseBigDecimal(boolean bl) {
        this.icuDecimalFormat.setParseBigDecimal(bl);
    }

    @Override
    public void setParseIntegerOnly(boolean bl) {
        super.setParseIntegerOnly(bl);
        this.icuDecimalFormat.setParseIntegerOnly(bl);
    }

    public void setPositivePrefix(String string) {
        this.icuDecimalFormat.setPositivePrefix(string);
    }

    public void setPositiveSuffix(String string) {
        this.icuDecimalFormat.setPositiveSuffix(string);
    }

    @Override
    public void setRoundingMode(RoundingMode roundingMode) {
        if (roundingMode != null) {
            this.roundingMode = roundingMode;
            this.icuDecimalFormat.setRoundingMode(DecimalFormat.convertRoundingMode(roundingMode));
            return;
        }
        throw new NullPointerException();
    }

    public String toLocalizedPattern() {
        return this.icuDecimalFormat.toLocalizedPattern();
    }

    public String toPattern() {
        return this.icuDecimalFormat.toPattern();
    }

}


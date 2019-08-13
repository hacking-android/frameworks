/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.number.AffixUtils;
import android.icu.impl.number.DecimalFormatProperties;
import android.icu.impl.number.Padder;
import android.icu.impl.number.PatternStringParser;
import android.icu.impl.number.PatternStringUtils;
import android.icu.impl.number.Properties;
import android.icu.impl.number.parse.NumberParserImpl;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.math.BigDecimal;
import android.icu.number.FormattedNumber;
import android.icu.number.LocalizedNumberFormatter;
import android.icu.number.NumberFormatter;
import android.icu.text.CurrencyPluralInfo;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.util.Currency;
import android.icu.util.CurrencyAmount;
import android.icu.util.Measure;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.ParsePosition;

public class DecimalFormat
extends NumberFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int PAD_AFTER_PREFIX = 1;
    public static final int PAD_AFTER_SUFFIX = 3;
    public static final int PAD_BEFORE_PREFIX = 0;
    public static final int PAD_BEFORE_SUFFIX = 2;
    private static final long serialVersionUID = 864413376551465018L;
    volatile transient NumberParserImpl currencyParser;
    volatile transient DecimalFormatProperties exportedProperties;
    volatile transient LocalizedNumberFormatter formatter;
    private transient int icuMathContextForm = 0;
    volatile transient NumberParserImpl parser;
    transient DecimalFormatProperties properties;
    private final int serialVersionOnStream;
    volatile transient DecimalFormatSymbols symbols;

    public DecimalFormat() {
        this.serialVersionOnStream = 5;
        String string = DecimalFormat.getPattern(ULocale.getDefault(ULocale.Category.FORMAT), 0);
        this.symbols = DecimalFormat.getDefaultSymbols();
        this.properties = new DecimalFormatProperties();
        this.exportedProperties = new DecimalFormatProperties();
        this.setPropertiesFromPattern(string, 1);
        this.refreshFormatter();
    }

    public DecimalFormat(String string) {
        this.serialVersionOnStream = 5;
        this.symbols = DecimalFormat.getDefaultSymbols();
        this.properties = new DecimalFormatProperties();
        this.exportedProperties = new DecimalFormatProperties();
        this.setPropertiesFromPattern(string, 1);
        this.refreshFormatter();
    }

    public DecimalFormat(String string, DecimalFormatSymbols decimalFormatSymbols) {
        this.serialVersionOnStream = 5;
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.properties = new DecimalFormatProperties();
        this.exportedProperties = new DecimalFormatProperties();
        this.setPropertiesFromPattern(string, 1);
        this.refreshFormatter();
    }

    DecimalFormat(String string, DecimalFormatSymbols decimalFormatSymbols, int n) {
        this.serialVersionOnStream = 5;
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.properties = new DecimalFormatProperties();
        this.exportedProperties = new DecimalFormatProperties();
        if (n != 1 && n != 5 && n != 7 && n != 8 && n != 9 && n != 6) {
            this.setPropertiesFromPattern(string, 1);
        } else {
            this.setPropertiesFromPattern(string, 2);
        }
        this.refreshFormatter();
    }

    public DecimalFormat(String string, DecimalFormatSymbols decimalFormatSymbols, CurrencyPluralInfo currencyPluralInfo, int n) {
        this(string, decimalFormatSymbols, n);
        this.properties.setCurrencyPluralInfo(currencyPluralInfo);
        this.refreshFormatter();
    }

    static void fieldPositionHelper(FormattedNumber formattedNumber, FieldPosition fieldPosition, int n) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        if (formattedNumber.nextFieldPosition(fieldPosition) && n != 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + n);
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + n);
        }
    }

    private static DecimalFormatSymbols getDefaultSymbols() {
        return DecimalFormatSymbols.getInstance();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField getField = ((ObjectInputStream)object).readFields();
        ObjectStreamField[] arrobjectStreamField = getField.getObjectStreamClass().getFields();
        int n = getField.get("serialVersionOnStream", -1);
        if (n <= 5) {
            block71 : {
                if (n == 5) {
                    if (arrobjectStreamField.length > 1) throw new IOException("Too many fields when reading serial version 5");
                    ((ObjectInputStream)object).readInt();
                    Object object2 = ((ObjectInputStream)object).readObject();
                    this.properties = object2 instanceof DecimalFormatProperties ? (DecimalFormatProperties)object2 : ((Properties)object2).getInstance();
                    this.symbols = (DecimalFormatSymbols)((ObjectInputStream)object).readObject();
                    this.exportedProperties = new DecimalFormatProperties();
                    this.refreshFormatter();
                    return;
                }
                this.properties = new DecimalFormatProperties();
                String string = null;
                String string2 = null;
                String string3 = null;
                String string4 = null;
                String string5 = null;
                String string6 = null;
                int n2 = arrobjectStreamField.length;
                object = null;
                String string7 = null;
                for (n = 0; n < n2; ++n) {
                    String string8;
                    String string9;
                    String string10;
                    String string11;
                    String string12;
                    String string13;
                    String string14;
                    Object object3;
                    String string15 = arrobjectStreamField[n].getName();
                    if (string15.equals("decimalSeparatorAlwaysShown")) {
                        this.setDecimalSeparatorAlwaysShown(getField.get("decimalSeparatorAlwaysShown", false));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("exponentSignAlwaysShown")) {
                        this.setExponentSignAlwaysShown(getField.get("exponentSignAlwaysShown", false));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("formatWidth")) {
                        this.setFormatWidth(getField.get("formatWidth", 0));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("groupingSize")) {
                        this.setGroupingSize(getField.get("groupingSize", (byte)3));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("groupingSize2")) {
                        this.setSecondaryGroupingSize(getField.get("groupingSize2", (byte)0));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("maxSignificantDigits")) {
                        this.setMaximumSignificantDigits(getField.get("maxSignificantDigits", 6));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("minExponentDigits")) {
                        this.setMinimumExponentDigits(getField.get("minExponentDigits", (byte)0));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("minSignificantDigits")) {
                        this.setMinimumSignificantDigits(getField.get("minSignificantDigits", 1));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("multiplier")) {
                        this.setMultiplier(getField.get("multiplier", 1));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("pad")) {
                        this.setPadCharacter(getField.get("pad", ' '));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("padPosition")) {
                        this.setPadPosition(getField.get("padPosition", 0));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("parseBigDecimal")) {
                        this.setParseBigDecimal(getField.get("parseBigDecimal", false));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("parseRequireDecimalPoint")) {
                        this.setDecimalPatternMatchRequired(getField.get("parseRequireDecimalPoint", false));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("roundingMode")) {
                        this.setRoundingMode(getField.get("roundingMode", 0));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("useExponentialNotation")) {
                        this.setScientificNotation(getField.get("useExponentialNotation", false));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("useSignificantDigits")) {
                        this.setSignificantDigitsUsed(getField.get("useSignificantDigits", false));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("currencyPluralInfo")) {
                        this.setCurrencyPluralInfo((CurrencyPluralInfo)getField.get("currencyPluralInfo", null));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("mathContext")) {
                        this.setMathContextICU((android.icu.math.MathContext)getField.get("mathContext", null));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("negPrefixPattern")) {
                        string8 = (String)getField.get("negPrefixPattern", null);
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("negSuffixPattern")) {
                        string11 = (String)getField.get("negSuffixPattern", null);
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        object3 = object;
                    } else if (string15.equals("negativePrefix")) {
                        string13 = (String)getField.get("negativePrefix", null);
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("negativeSuffix")) {
                        string12 = (String)getField.get("negativeSuffix", null);
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("posPrefixPattern")) {
                        string10 = (String)getField.get("posPrefixPattern", null);
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("posSuffixPattern")) {
                        string14 = (String)getField.get("posSuffixPattern", null);
                        string10 = string;
                        string9 = string7;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("positivePrefix")) {
                        string9 = (String)getField.get("positivePrefix", null);
                        string10 = string;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else if (string15.equals("positiveSuffix")) {
                        object3 = (String)getField.get("positiveSuffix", null);
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                    } else if (string15.equals("roundingIncrement")) {
                        this.setRoundingIncrement((java.math.BigDecimal)getField.get("roundingIncrement", null));
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                    } else {
                        string10 = string;
                        string9 = string7;
                        string14 = string2;
                        string13 = string3;
                        string8 = string4;
                        string12 = string5;
                        string11 = string6;
                        object3 = object;
                        if (string15.equals("symbols")) {
                            this.setDecimalFormatSymbols((DecimalFormatSymbols)getField.get("symbols", null));
                            object3 = object;
                            string11 = string6;
                            string12 = string5;
                            string8 = string4;
                            string13 = string3;
                            string14 = string2;
                            string9 = string7;
                            string10 = string;
                        }
                    }
                    string = string10;
                    string7 = string9;
                    string2 = string14;
                    string3 = string13;
                    string4 = string8;
                    string5 = string12;
                    string6 = string11;
                    object = object3;
                }
                if (string4 == null) {
                    this.properties.setNegativePrefix(string3);
                } else {
                    this.properties.setNegativePrefixPattern(string4);
                }
                if (string6 == null) {
                    this.properties.setNegativeSuffix(string5);
                } else {
                    this.properties.setNegativeSuffixPattern(string6);
                }
                if (string == null) {
                    this.properties.setPositivePrefix(string7);
                } else {
                    this.properties.setPositivePrefixPattern(string);
                }
                if (string2 == null) {
                    this.properties.setPositiveSuffix((String)object);
                } else {
                    this.properties.setPositiveSuffixPattern(string2);
                }
                try {
                    object = NumberFormat.class.getDeclaredField("groupingUsed");
                    ((AccessibleObject)object).setAccessible(true);
                    this.setGroupingUsed((Boolean)((Field)object).get(this));
                    object = NumberFormat.class.getDeclaredField("parseIntegerOnly");
                    ((AccessibleObject)object).setAccessible(true);
                    this.setParseIntegerOnly((Boolean)((Field)object).get(this));
                    object = NumberFormat.class.getDeclaredField("maximumIntegerDigits");
                    ((AccessibleObject)object).setAccessible(true);
                    this.setMaximumIntegerDigits((Integer)((Field)object).get(this));
                    object = NumberFormat.class.getDeclaredField("minimumIntegerDigits");
                    ((AccessibleObject)object).setAccessible(true);
                    this.setMinimumIntegerDigits((Integer)((Field)object).get(this));
                    object = NumberFormat.class.getDeclaredField("maximumFractionDigits");
                    ((AccessibleObject)object).setAccessible(true);
                    this.setMaximumFractionDigits((Integer)((Field)object).get(this));
                    object = NumberFormat.class.getDeclaredField("minimumFractionDigits");
                    ((AccessibleObject)object).setAccessible(true);
                    this.setMinimumFractionDigits((Integer)((Field)object).get(this));
                    object = NumberFormat.class.getDeclaredField("currency");
                    ((AccessibleObject)object).setAccessible(true);
                    this.setCurrency((Currency)((Field)object).get(this));
                    object = NumberFormat.class.getDeclaredField("parseStrict");
                    ((AccessibleObject)object).setAccessible(true);
                    this.setParseStrict((Boolean)((Field)object).get(this));
                    if (this.symbols != null) break block71;
                }
                catch (SecurityException securityException) {
                    throw new IOException(securityException);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    throw new IOException(noSuchFieldException);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new IOException(illegalAccessException);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new IOException(illegalArgumentException);
                }
                this.symbols = DecimalFormat.getDefaultSymbols();
            }
            this.exportedProperties = new DecimalFormatProperties();
            this.refreshFormatter();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot deserialize newer android.icu.text.DecimalFormat (v");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        throw new IOException(((StringBuilder)object).toString());
    }

    private Number safeConvertBigDecimal(java.math.BigDecimal bigDecimal) {
        try {
            BigDecimal bigDecimal2 = new BigDecimal(bigDecimal);
            return bigDecimal2;
        }
        catch (NumberFormatException numberFormatException) {
            if (bigDecimal.signum() > 0 && bigDecimal.scale() < 0) {
                return Double.POSITIVE_INFINITY;
            }
            if (bigDecimal.scale() < 0) {
                return Double.NEGATIVE_INFINITY;
            }
            if (bigDecimal.signum() < 0) {
                return 0.0;
            }
            return 0.0;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeInt(0);
            objectOutputStream.writeObject(this.properties);
            objectOutputStream.writeObject(this.symbols);
            return;
        }
    }

    public void applyLocalizedPattern(String string) {
        synchronized (this) {
            this.applyPattern(PatternStringUtils.convertLocalized(string, this.symbols, false));
            return;
        }
    }

    public void applyPattern(String string) {
        synchronized (this) {
            this.setPropertiesFromPattern(string, 0);
            this.properties.setPositivePrefix(null);
            this.properties.setNegativePrefix(null);
            this.properties.setPositiveSuffix(null);
            this.properties.setNegativeSuffix(null);
            this.properties.setCurrencyPluralInfo(null);
            this.refreshFormatter();
            return;
        }
    }

    public boolean areSignificantDigitsUsed() {
        synchronized (this) {
            boolean bl;
            block4 : {
                int n;
                if (this.properties.getMinimumSignificantDigits() == -1 && (n = this.properties.getMaximumSignificantDigits()) == -1) {
                    bl = false;
                    break block4;
                }
                bl = true;
            }
            return bl;
        }
    }

    @Override
    public Object clone() {
        DecimalFormat decimalFormat = (DecimalFormat)super.clone();
        decimalFormat.symbols = (DecimalFormatSymbols)this.symbols.clone();
        decimalFormat.properties = this.properties.clone();
        decimalFormat.exportedProperties = new DecimalFormatProperties();
        decimalFormat.refreshFormatter();
        return decimalFormat;
    }

    @Override
    public boolean equals(Object object) {
        synchronized (this) {
            boolean bl;
            block8 : {
                boolean bl2;
                block7 : {
                    bl2 = false;
                    if (object == null) {
                        return false;
                    }
                    if (object == this) {
                        return true;
                    }
                    bl = object instanceof DecimalFormat;
                    if (bl) break block7;
                    return false;
                }
                object = (DecimalFormat)object;
                bl = bl2;
                if (!this.properties.equals(((DecimalFormat)object).properties)) break block8;
                boolean bl3 = this.symbols.equals(((DecimalFormat)object).symbols);
                bl = bl2;
                if (!bl3) break block8;
                bl = true;
            }
            return bl;
        }
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.formatter.format(d);
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuffer.length());
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        FormattedNumber formattedNumber = this.formatter.format(l);
        DecimalFormat.fieldPositionHelper(formattedNumber, fieldPosition, stringBuffer.length());
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(BigDecimal object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        object = this.formatter.format((Number)object);
        DecimalFormat.fieldPositionHelper((FormattedNumber)object, fieldPosition, stringBuffer.length());
        ((FormattedNumber)object).appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(CurrencyAmount object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        object = this.formatter.format((Measure)object);
        DecimalFormat.fieldPositionHelper((FormattedNumber)object, fieldPosition, stringBuffer.length());
        ((FormattedNumber)object).appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(java.math.BigDecimal object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        object = this.formatter.format((Number)object);
        DecimalFormat.fieldPositionHelper((FormattedNumber)object, fieldPosition, stringBuffer.length());
        ((FormattedNumber)object).appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public StringBuffer format(BigInteger object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        object = this.formatter.format((Number)object);
        DecimalFormat.fieldPositionHelper((FormattedNumber)object, fieldPosition, stringBuffer.length());
        ((FormattedNumber)object).appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        if (object instanceof Number) {
            object = (Number)object;
            return this.formatter.format((Number)object).getFieldIterator();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Currency getCurrency() {
        synchronized (this) {
            Currency currency = this.exportedProperties.getCurrency();
            return currency;
        }
    }

    NumberParserImpl getCurrencyParser() {
        if (this.currencyParser == null) {
            this.currencyParser = NumberParserImpl.createParserFromProperties(this.properties, this.symbols, true);
        }
        return this.currencyParser;
    }

    public CurrencyPluralInfo getCurrencyPluralInfo() {
        synchronized (this) {
            CurrencyPluralInfo currencyPluralInfo = this.properties.getCurrencyPluralInfo();
            return currencyPluralInfo;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Currency.CurrencyUsage getCurrencyUsage() {
        synchronized (this) {
            Currency.CurrencyUsage currencyUsage;
            Currency.CurrencyUsage currencyUsage2 = currencyUsage = this.properties.getCurrencyUsage();
            if (currencyUsage != null) return currencyUsage2;
            return Currency.CurrencyUsage.STANDARD;
        }
    }

    public DecimalFormatSymbols getDecimalFormatSymbols() {
        synchronized (this) {
            DecimalFormatSymbols decimalFormatSymbols = (DecimalFormatSymbols)this.symbols.clone();
            return decimalFormatSymbols;
        }
    }

    @Deprecated
    public PluralRules.IFixedDecimal getFixedDecimal(double d) {
        return this.formatter.format(d).getFixedDecimal();
    }

    public int getFormatWidth() {
        synchronized (this) {
            int n = this.properties.getFormatWidth();
            return n;
        }
    }

    public int getGroupingSize() {
        synchronized (this) {
            int n;
            block4 : {
                n = this.properties.getGroupingSize();
                if (n >= 0) break block4;
                return 0;
            }
            n = this.properties.getGroupingSize();
            return n;
        }
    }

    public MathContext getMathContext() {
        synchronized (this) {
            MathContext mathContext = this.exportedProperties.getMathContext();
            return mathContext;
        }
    }

    public android.icu.math.MathContext getMathContextICU() {
        synchronized (this) {
            Serializable serializable = this.getMathContext();
            serializable = new android.icu.math.MathContext(serializable.getPrecision(), this.icuMathContextForm, false, serializable.getRoundingMode().ordinal());
            return serializable;
        }
    }

    @Override
    public int getMaximumFractionDigits() {
        synchronized (this) {
            int n = this.exportedProperties.getMaximumFractionDigits();
            return n;
        }
    }

    @Override
    public int getMaximumIntegerDigits() {
        synchronized (this) {
            int n = this.exportedProperties.getMaximumIntegerDigits();
            return n;
        }
    }

    public int getMaximumSignificantDigits() {
        synchronized (this) {
            int n = this.exportedProperties.getMaximumSignificantDigits();
            return n;
        }
    }

    public byte getMinimumExponentDigits() {
        synchronized (this) {
            int n = this.properties.getMinimumExponentDigits();
            byte by = (byte)n;
            return by;
        }
    }

    @Override
    public int getMinimumFractionDigits() {
        synchronized (this) {
            int n = this.exportedProperties.getMinimumFractionDigits();
            return n;
        }
    }

    @Deprecated
    public int getMinimumGroupingDigits() {
        synchronized (this) {
            if (this.properties.getMinimumGroupingDigits() > 0) {
                int n = this.properties.getMinimumGroupingDigits();
                return n;
            }
            return 1;
        }
    }

    @Override
    public int getMinimumIntegerDigits() {
        synchronized (this) {
            int n = this.exportedProperties.getMinimumIntegerDigits();
            return n;
        }
    }

    public int getMinimumSignificantDigits() {
        synchronized (this) {
            int n = this.exportedProperties.getMinimumSignificantDigits();
            return n;
        }
    }

    public int getMultiplier() {
        synchronized (this) {
            block4 : {
                if (this.properties.getMultiplier() == null) break block4;
                int n = this.properties.getMultiplier().intValue();
                return n;
            }
            double d = Math.pow(10.0, this.properties.getMagnitudeMultiplier());
            int n = (int)d;
            return n;
        }
    }

    public String getNegativePrefix() {
        synchronized (this) {
            String string = this.formatter.getAffixImpl(true, true);
            return string;
        }
    }

    public String getNegativeSuffix() {
        synchronized (this) {
            String string = this.formatter.getAffixImpl(false, true);
            return string;
        }
    }

    public char getPadCharacter() {
        synchronized (this) {
            String string;
            block5 : {
                string = this.properties.getPadString();
                if (string != null) break block5;
                char c = " ".charAt(0);
                return c;
            }
            char c = string.charAt(0);
            return c;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getPadPosition() {
        synchronized (this) {
            Padder.PadPosition padPosition;
            block4 : {
                padPosition = this.properties.getPadPosition();
                if (padPosition != null) break block4;
                return 0;
            }
            return padPosition.toOld();
        }
    }

    @Deprecated
    public boolean getParseCaseSensitive() {
        synchronized (this) {
            boolean bl = this.properties.getParseCaseSensitive();
            return bl;
        }
    }

    @Deprecated
    public int getParseMaxDigits() {
        return 1000;
    }

    @Deprecated
    public boolean getParseNoExponent() {
        synchronized (this) {
            boolean bl = this.properties.getParseNoExponent();
            return bl;
        }
    }

    NumberParserImpl getParser() {
        if (this.parser == null) {
            this.parser = NumberParserImpl.createParserFromProperties(this.properties, this.symbols, false);
        }
        return this.parser;
    }

    public String getPositivePrefix() {
        synchronized (this) {
            String string = this.formatter.getAffixImpl(true, false);
            return string;
        }
    }

    public String getPositiveSuffix() {
        synchronized (this) {
            String string = this.formatter.getAffixImpl(false, false);
            return string;
        }
    }

    public java.math.BigDecimal getRoundingIncrement() {
        synchronized (this) {
            java.math.BigDecimal bigDecimal = this.exportedProperties.getRoundingIncrement();
            return bigDecimal;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int getRoundingMode() {
        synchronized (this) {
            RoundingMode roundingMode;
            block4 : {
                roundingMode = this.exportedProperties.getRoundingMode();
                if (roundingMode != null) break block4;
                return 0;
            }
            return roundingMode.ordinal();
        }
    }

    public int getSecondaryGroupingSize() {
        synchronized (this) {
            int n;
            block3 : {
                n = this.properties.getSecondaryGroupingSize();
                if (n >= 0) break block3;
                return 0;
            }
            return n;
        }
    }

    @Deprecated
    public boolean getSignAlwaysShown() {
        synchronized (this) {
            boolean bl = this.properties.getSignAlwaysShown();
            return bl;
        }
    }

    @Override
    public int hashCode() {
        synchronized (this) {
            int n = this.properties.hashCode();
            int n2 = this.symbols.hashCode();
            return n ^ n2;
        }
    }

    public boolean isDecimalPatternMatchRequired() {
        synchronized (this) {
            boolean bl = this.properties.getDecimalPatternMatchRequired();
            return bl;
        }
    }

    public boolean isDecimalSeparatorAlwaysShown() {
        synchronized (this) {
            boolean bl = this.properties.getDecimalSeparatorAlwaysShown();
            return bl;
        }
    }

    public boolean isExponentSignAlwaysShown() {
        synchronized (this) {
            boolean bl = this.properties.getExponentSignAlwaysShown();
            return bl;
        }
    }

    @Override
    public boolean isGroupingUsed() {
        synchronized (this) {
            boolean bl = this.properties.getGroupingUsed();
            return bl;
        }
    }

    public boolean isParseBigDecimal() {
        synchronized (this) {
            boolean bl = this.properties.getParseToBigDecimal();
            return bl;
        }
    }

    @Override
    public boolean isParseIntegerOnly() {
        synchronized (this) {
            boolean bl = this.properties.getParseIntegerOnly();
            return bl;
        }
    }

    @Override
    public boolean isParseStrict() {
        synchronized (this) {
            DecimalFormatProperties.ParseMode parseMode = this.properties.getParseMode();
            DecimalFormatProperties.ParseMode parseMode2 = DecimalFormatProperties.ParseMode.STRICT;
            boolean bl = parseMode == parseMode2;
            return bl;
        }
    }

    public boolean isScientificNotation() {
        synchronized (this) {
            int n = this.properties.getMinimumExponentDigits();
            boolean bl = n != -1;
            return bl;
        }
    }

    @Override
    public Number parse(String object, ParsePosition object2) {
        if (object != null) {
            ParsePosition parsePosition = object2;
            if (object2 == null) {
                parsePosition = new ParsePosition(0);
            }
            if (parsePosition.getIndex() >= 0) {
                if (parsePosition.getIndex() >= ((String)object).length()) {
                    return null;
                }
                object2 = new ParsedNumber();
                int n = parsePosition.getIndex();
                NumberParserImpl numberParserImpl = this.getParser();
                numberParserImpl.parse((String)object, n, true, (ParsedNumber)object2);
                if (((ParsedNumber)object2).success()) {
                    parsePosition.setIndex(((ParsedNumber)object2).charEnd);
                    object = object2 = ((ParsedNumber)object2).getNumber(numberParserImpl.getParseFlags());
                    if (object2 instanceof java.math.BigDecimal) {
                        object = this.safeConvertBigDecimal((java.math.BigDecimal)object2);
                    }
                    return object;
                }
                parsePosition.setErrorIndex(((ParsedNumber)object2).charEnd + n);
                return null;
            }
            throw new IllegalArgumentException("Cannot start parsing at a negative offset");
        }
        throw new IllegalArgumentException("Text cannot be null");
    }

    @Override
    public CurrencyAmount parseCurrency(CharSequence object, ParsePosition object2) {
        if (object != null) {
            ParsePosition parsePosition = object2;
            if (object2 == null) {
                parsePosition = new ParsePosition(0);
            }
            if (parsePosition.getIndex() >= 0) {
                if (parsePosition.getIndex() >= object.length()) {
                    return null;
                }
                ParsedNumber parsedNumber = new ParsedNumber();
                int n = parsePosition.getIndex();
                object2 = this.getCurrencyParser();
                ((NumberParserImpl)object2).parse(object.toString(), n, true, parsedNumber);
                if (parsedNumber.success()) {
                    parsePosition.setIndex(parsedNumber.charEnd);
                    object = object2 = parsedNumber.getNumber(((NumberParserImpl)object2).getParseFlags());
                    if (object2 instanceof java.math.BigDecimal) {
                        object = this.safeConvertBigDecimal((java.math.BigDecimal)object2);
                    }
                    return new CurrencyAmount((Number)object, Currency.getInstance(parsedNumber.currencyCode));
                }
                parsePosition.setErrorIndex(parsedNumber.charEnd + n);
                return null;
            }
            throw new IllegalArgumentException("Cannot start parsing at a negative offset");
        }
        throw new IllegalArgumentException("Text cannot be null");
    }

    void refreshFormatter() {
        ULocale uLocale;
        if (this.exportedProperties == null) {
            return;
        }
        ULocale uLocale2 = uLocale = this.getLocale(ULocale.ACTUAL_LOCALE);
        if (uLocale == null) {
            uLocale2 = this.symbols.getLocale(ULocale.ACTUAL_LOCALE);
        }
        uLocale = uLocale2;
        if (uLocale2 == null) {
            uLocale = this.symbols.getULocale();
        }
        this.formatter = NumberFormatter.fromDecimalFormat(this.properties, this.symbols, this.exportedProperties).locale(uLocale);
        this.parser = null;
        this.currencyParser = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setCurrency(Currency object) {
        synchronized (this) {
            this.properties.setCurrency((Currency)object);
            if (object != null) {
                this.symbols.setCurrency((Currency)object);
                object = ((Currency)object).getName(this.symbols.getULocale(), 0, null);
                this.symbols.setCurrencySymbol((String)object);
            }
            this.refreshFormatter();
            return;
        }
    }

    public void setCurrencyPluralInfo(CurrencyPluralInfo currencyPluralInfo) {
        synchronized (this) {
            this.properties.setCurrencyPluralInfo(currencyPluralInfo);
            this.refreshFormatter();
            return;
        }
    }

    public void setCurrencyUsage(Currency.CurrencyUsage currencyUsage) {
        synchronized (this) {
            this.properties.setCurrencyUsage(currencyUsage);
            this.refreshFormatter();
            return;
        }
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        synchronized (this) {
            this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
            this.refreshFormatter();
            return;
        }
    }

    public void setDecimalPatternMatchRequired(boolean bl) {
        synchronized (this) {
            this.properties.setDecimalPatternMatchRequired(bl);
            this.refreshFormatter();
            return;
        }
    }

    public void setDecimalSeparatorAlwaysShown(boolean bl) {
        synchronized (this) {
            this.properties.setDecimalSeparatorAlwaysShown(bl);
            this.refreshFormatter();
            return;
        }
    }

    public void setExponentSignAlwaysShown(boolean bl) {
        synchronized (this) {
            this.properties.setExponentSignAlwaysShown(bl);
            this.refreshFormatter();
            return;
        }
    }

    public void setFormatWidth(int n) {
        synchronized (this) {
            this.properties.setFormatWidth(n);
            this.refreshFormatter();
            return;
        }
    }

    public void setGroupingSize(int n) {
        synchronized (this) {
            this.properties.setGroupingSize(n);
            this.refreshFormatter();
            return;
        }
    }

    @Override
    public void setGroupingUsed(boolean bl) {
        synchronized (this) {
            this.properties.setGroupingUsed(bl);
            this.refreshFormatter();
            return;
        }
    }

    public void setMathContext(MathContext mathContext) {
        synchronized (this) {
            this.properties.setMathContext(mathContext);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMathContextICU(android.icu.math.MathContext serializable) {
        synchronized (this) {
            void var1_4;
            this.icuMathContextForm = ((android.icu.math.MathContext)serializable).getForm();
            if (((android.icu.math.MathContext)serializable).getLostDigits()) {
                MathContext mathContext;
                MathContext mathContext2 = mathContext = new MathContext(((android.icu.math.MathContext)serializable).getDigits(), RoundingMode.UNNECESSARY);
            } else {
                MathContext mathContext = new MathContext(((android.icu.math.MathContext)serializable).getDigits(), RoundingMode.valueOf(((android.icu.math.MathContext)serializable).getRoundingMode()));
            }
            this.setMathContext((MathContext)var1_4);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setMaximumFractionDigits(int n) {
        synchronized (this) {
            int n2 = this.properties.getMinimumFractionDigits();
            if (n2 >= 0 && n2 > n) {
                this.properties.setMinimumFractionDigits(n);
            }
            this.properties.setMaximumFractionDigits(n);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setMaximumIntegerDigits(int n) {
        synchronized (this) {
            int n2 = this.properties.getMinimumIntegerDigits();
            if (n2 >= 0 && n2 > n) {
                this.properties.setMinimumIntegerDigits(n);
            }
            this.properties.setMaximumIntegerDigits(n);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMaximumSignificantDigits(int n) {
        synchronized (this) {
            int n2 = this.properties.getMinimumSignificantDigits();
            if (n2 >= 0 && n2 > n) {
                this.properties.setMinimumSignificantDigits(n);
            }
            this.properties.setMaximumSignificantDigits(n);
            this.refreshFormatter();
            return;
        }
    }

    public void setMinimumExponentDigits(byte by) {
        synchronized (this) {
            this.properties.setMinimumExponentDigits(by);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setMinimumFractionDigits(int n) {
        synchronized (this) {
            int n2 = this.properties.getMaximumFractionDigits();
            if (n2 >= 0 && n2 < n) {
                this.properties.setMaximumFractionDigits(n);
            }
            this.properties.setMinimumFractionDigits(n);
            this.refreshFormatter();
            return;
        }
    }

    @Deprecated
    public void setMinimumGroupingDigits(int n) {
        synchronized (this) {
            this.properties.setMinimumGroupingDigits(n);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setMinimumIntegerDigits(int n) {
        synchronized (this) {
            int n2 = this.properties.getMaximumIntegerDigits();
            if (n2 >= 0 && n2 < n) {
                this.properties.setMaximumIntegerDigits(n);
            }
            this.properties.setMinimumIntegerDigits(n);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMinimumSignificantDigits(int n) {
        synchronized (this) {
            int n2 = this.properties.getMaximumSignificantDigits();
            if (n2 >= 0 && n2 < n) {
                this.properties.setMaximumSignificantDigits(n);
            }
            this.properties.setMinimumSignificantDigits(n);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMultiplier(int n) {
        synchronized (this) {
            int n2;
            if (n == 0) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Multiplier must be nonzero.");
                throw illegalArgumentException;
            }
            int n3 = 0;
            int n4 = n;
            do {
                n2 = n3++;
                if (n4 == 1) break;
                n2 = n4 / 10;
                if (n2 * 10 != n4) {
                    n2 = -1;
                    break;
                }
                n4 = n2;
            } while (true);
            if (n2 != -1) {
                this.properties.setMagnitudeMultiplier(n2);
                this.properties.setMultiplier(null);
            } else {
                this.properties.setMagnitudeMultiplier(0);
                this.properties.setMultiplier(java.math.BigDecimal.valueOf(n));
            }
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setNegativePrefix(String object) {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    this.properties.setNegativePrefix((String)object);
                    this.refreshFormatter();
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException();
                throw object;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setNegativeSuffix(String object) {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    this.properties.setNegativeSuffix((String)object);
                    this.refreshFormatter();
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException();
                throw object;
            }
            throw throwable2;
        }
    }

    public void setPadCharacter(char c) {
        synchronized (this) {
            this.properties.setPadString(Character.toString(c));
            this.refreshFormatter();
            return;
        }
    }

    public void setPadPosition(int n) {
        synchronized (this) {
            this.properties.setPadPosition(Padder.PadPosition.fromOld(n));
            this.refreshFormatter();
            return;
        }
    }

    public void setParseBigDecimal(boolean bl) {
        synchronized (this) {
            this.properties.setParseToBigDecimal(bl);
            this.refreshFormatter();
            return;
        }
    }

    @Deprecated
    public void setParseCaseSensitive(boolean bl) {
        synchronized (this) {
            this.properties.setParseCaseSensitive(bl);
            this.refreshFormatter();
            return;
        }
    }

    @Override
    public void setParseIntegerOnly(boolean bl) {
        synchronized (this) {
            this.properties.setParseIntegerOnly(bl);
            this.refreshFormatter();
            return;
        }
    }

    @Deprecated
    public void setParseMaxDigits(int n) {
    }

    @Deprecated
    public void setParseNoExponent(boolean bl) {
        synchronized (this) {
            this.properties.setParseNoExponent(bl);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setParseStrict(boolean bl) {
        synchronized (this) {
            DecimalFormatProperties.ParseMode parseMode = bl ? DecimalFormatProperties.ParseMode.STRICT : DecimalFormatProperties.ParseMode.LENIENT;
            this.properties.setParseMode(parseMode);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPositivePrefix(String object) {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    this.properties.setPositivePrefix((String)object);
                    this.refreshFormatter();
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException();
                throw object;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPositiveSuffix(String object) {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    this.properties.setPositiveSuffix((String)object);
                    this.refreshFormatter();
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException();
                throw object;
            }
            throw throwable2;
        }
    }

    @Deprecated
    public void setProperties(PropertySetter propertySetter) {
        synchronized (this) {
            propertySetter.set(this.properties);
            this.refreshFormatter();
            return;
        }
    }

    void setPropertiesFromPattern(String string, int n) {
        if (string != null) {
            PatternStringParser.parseToExistingProperties(string, this.properties, n);
            return;
        }
        throw new NullPointerException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRoundingIncrement(double d) {
        synchronized (this) {
            if (d == 0.0) {
                this.setRoundingIncrement((java.math.BigDecimal)null);
            } else {
                this.setRoundingIncrement(java.math.BigDecimal.valueOf(d));
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRoundingIncrement(BigDecimal number) {
        synchronized (this) {
            void var1_4;
            if (number == null) {
                Object var1_2 = null;
            } else {
                java.math.BigDecimal bigDecimal = ((BigDecimal)number).toBigDecimal();
            }
            this.setRoundingIncrement((java.math.BigDecimal)var1_4);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setRoundingIncrement(java.math.BigDecimal bigDecimal) {
        synchronized (this) {
            if (bigDecimal != null && bigDecimal.compareTo(java.math.BigDecimal.ZERO) == 0) {
                this.properties.setMaximumFractionDigits(Integer.MAX_VALUE);
                return;
            }
            this.properties.setRoundingIncrement(bigDecimal);
            this.refreshFormatter();
            return;
        }
    }

    @Override
    public void setRoundingMode(int n) {
        synchronized (this) {
            this.properties.setRoundingMode(RoundingMode.valueOf(n));
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setScientificNotation(boolean bl) {
        synchronized (this) {
            if (bl) {
                this.properties.setMinimumExponentDigits(1);
            } else {
                this.properties.setMinimumExponentDigits(-1);
            }
            this.refreshFormatter();
            return;
        }
    }

    public void setSecondaryGroupingSize(int n) {
        synchronized (this) {
            this.properties.setSecondaryGroupingSize(n);
            this.refreshFormatter();
            return;
        }
    }

    @Deprecated
    public void setSignAlwaysShown(boolean bl) {
        synchronized (this) {
            this.properties.setSignAlwaysShown(bl);
            this.refreshFormatter();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSignificantDigitsUsed(boolean bl) {
        synchronized (this) {
            int n = this.properties.getMinimumSignificantDigits();
            int n2 = this.properties.getMaximumSignificantDigits();
            int n3 = -1;
            if (bl) {
                if (n != -1 || n2 != -1) {
                    return;
                }
            } else if (n == -1 && n2 == -1) {
                return;
            }
            n2 = bl ? 1 : -1;
            if (bl) {
                n3 = 6;
            }
            this.properties.setMinimumSignificantDigits(n2);
            this.properties.setMaximumSignificantDigits(n3);
            this.refreshFormatter();
            return;
        }
    }

    public String toLocalizedPattern() {
        synchronized (this) {
            String string = PatternStringUtils.convertLocalized(this.toPattern(), this.symbols, true);
            return string;
        }
    }

    public LocalizedNumberFormatter toNumberFormatter() {
        return this.formatter;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toPattern() {
        synchronized (this) {
            Object object = new DecimalFormatProperties();
            object = ((DecimalFormatProperties)object).copyFrom(this.properties);
            boolean bl = ((DecimalFormatProperties)object).getCurrency() != null || ((DecimalFormatProperties)object).getCurrencyPluralInfo() != null || ((DecimalFormatProperties)object).getCurrencyUsage() != null || AffixUtils.hasCurrencySymbols(((DecimalFormatProperties)object).getPositivePrefixPattern()) || AffixUtils.hasCurrencySymbols(((DecimalFormatProperties)object).getPositiveSuffixPattern()) || AffixUtils.hasCurrencySymbols(((DecimalFormatProperties)object).getNegativePrefixPattern()) || AffixUtils.hasCurrencySymbols(((DecimalFormatProperties)object).getNegativeSuffixPattern());
            if (!bl) return PatternStringUtils.propertiesToPatternString((DecimalFormatProperties)object);
            ((DecimalFormatProperties)object).setMinimumFractionDigits(this.exportedProperties.getMinimumFractionDigits());
            ((DecimalFormatProperties)object).setMaximumFractionDigits(this.exportedProperties.getMaximumFractionDigits());
            ((DecimalFormatProperties)object).setRoundingIncrement(this.exportedProperties.getRoundingIncrement());
            return PatternStringUtils.propertiesToPatternString((DecimalFormatProperties)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        stringBuilder.append(" { symbols@");
        stringBuilder.append(Integer.toHexString(this.symbols.hashCode()));
        synchronized (this) {
            this.properties.toStringBare(stringBuilder);
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Deprecated
    public static interface PropertySetter {
        @Deprecated
        public void set(DecimalFormatProperties var1);
    }

}


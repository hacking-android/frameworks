/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CacheBase;
import android.icu.impl.CurrencyData;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SoftCache;
import android.icu.impl.UResource;
import android.icu.text.NumberingSystem;
import android.icu.util.Currency;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;

public class DecimalFormatSymbols
implements Cloneable,
Serializable {
    public static final int CURRENCY_SPC_CURRENCY_MATCH = 0;
    public static final int CURRENCY_SPC_INSERT = 2;
    public static final int CURRENCY_SPC_SURROUNDING_MATCH = 1;
    private static final char DEF_DECIMAL_SEPARATOR = '.';
    private static final char[] DEF_DIGIT_CHARS_ARRAY;
    private static final String[] DEF_DIGIT_STRINGS_ARRAY;
    private static final char DEF_GROUPING_SEPARATOR = ',';
    private static final char DEF_MINUS_SIGN = '-';
    private static final char DEF_PERCENT = '%';
    private static final char DEF_PERMILL = '\u2030';
    private static final char DEF_PLUS_SIGN = '+';
    private static final String LATIN_NUMBERING_SYSTEM = "latn";
    private static final String NUMBER_ELEMENTS = "NumberElements";
    private static final String SYMBOLS = "symbols";
    private static final String[] SYMBOL_DEFAULTS;
    private static final String[] SYMBOL_KEYS;
    private static final CacheBase<ULocale, CacheData, Void> cachedLocaleData;
    private static final int currentSerialVersion = 8;
    private static final long serialVersionUID = 5772796243397350300L;
    private String NaN;
    private ULocale actualLocale;
    private transient int codePointZero;
    private transient Currency currency;
    private String currencyPattern = null;
    private String[] currencySpcAfterSym;
    private String[] currencySpcBeforeSym;
    private String currencySymbol;
    private char decimalSeparator;
    private String decimalSeparatorString;
    private char digit;
    private String[] digitStrings;
    private char[] digits;
    private String exponentMultiplicationSign = null;
    private String exponentSeparator;
    private char exponential;
    private char groupingSeparator;
    private String groupingSeparatorString;
    private String infinity;
    private String intlCurrencySymbol;
    private char minusSign;
    private String minusString;
    private char monetaryGroupingSeparator;
    private String monetaryGroupingSeparatorString;
    private char monetarySeparator;
    private String monetarySeparatorString;
    private char padEscape;
    private char patternSeparator;
    private char perMill;
    private String perMillString;
    private char percent;
    private String percentString;
    private char plusSign;
    private String plusString;
    private Locale requestedLocale;
    private int serialVersionOnStream = 8;
    private char sigDigit;
    private ULocale ulocale;
    private ULocale validLocale;
    private char zeroDigit;

    static {
        SYMBOL_KEYS = new String[]{"decimal", "group", "percentSign", "minusSign", "plusSign", "exponential", "perMille", "infinity", "nan", "currencyDecimal", "currencyGroup", "superscriptingExponent"};
        DEF_DIGIT_STRINGS_ARRAY = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        DEF_DIGIT_CHARS_ARRAY = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        SYMBOL_DEFAULTS = new String[]{String.valueOf('.'), String.valueOf(','), String.valueOf('%'), String.valueOf('-'), String.valueOf('+'), "E", String.valueOf('\u2030'), "\u221e", "NaN", null, null, "\u00d7"};
        cachedLocaleData = new SoftCache<ULocale, CacheData, Void>(){

            @Override
            protected CacheData createInstance(ULocale uLocale, Void void_) {
                return DecimalFormatSymbols.loadData(uLocale);
            }
        };
    }

    public DecimalFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public DecimalFormatSymbols(ULocale uLocale) {
        this.initialize(uLocale, null);
    }

    private DecimalFormatSymbols(ULocale uLocale, NumberingSystem numberingSystem) {
        this.initialize(uLocale, numberingSystem);
    }

    public DecimalFormatSymbols(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    private DecimalFormatSymbols(Locale locale, NumberingSystem numberingSystem) {
        this(ULocale.forLocale(locale), numberingSystem);
    }

    public static DecimalFormatSymbols forNumberingSystem(ULocale uLocale, NumberingSystem numberingSystem) {
        return new DecimalFormatSymbols(uLocale, numberingSystem);
    }

    public static DecimalFormatSymbols forNumberingSystem(Locale locale, NumberingSystem numberingSystem) {
        return new DecimalFormatSymbols(locale, numberingSystem);
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    public static DecimalFormatSymbols getInstance() {
        return new DecimalFormatSymbols();
    }

    public static DecimalFormatSymbols getInstance(ULocale uLocale) {
        return new DecimalFormatSymbols(uLocale);
    }

    public static DecimalFormatSymbols getInstance(Locale locale) {
        return new DecimalFormatSymbols(locale);
    }

    private void initSpacingInfo(CurrencyData.CurrencySpacingInfo currencySpacingInfo) {
        this.currencySpcBeforeSym = currencySpacingInfo.getBeforeSymbols();
        this.currencySpcAfterSym = currencySpacingInfo.getAfterSymbols();
    }

    private void initialize(ULocale object, NumberingSystem object2) {
        this.requestedLocale = ((ULocale)object).toLocale();
        this.ulocale = object;
        object2 = object2 == null ? object : ((ULocale)object).setKeywordValue("numbers", ((NumberingSystem)object2).getName());
        object2 = cachedLocaleData.getInstance((ULocale)object2, null);
        this.setLocale(((CacheData)object2).validLocale, ((CacheData)object2).validLocale);
        this.setDigitStrings(((CacheData)object2).digits);
        object2 = ((CacheData)object2).numberElements;
        this.setDecimalSeparatorString(object2[0]);
        this.setGroupingSeparatorString(object2[1]);
        this.patternSeparator = (char)59;
        this.setPercentString(object2[2]);
        this.setMinusSignString(object2[3]);
        this.setPlusSignString(object2[4]);
        this.setExponentSeparator(object2[5]);
        this.setPerMillString(object2[6]);
        this.setInfinity(object2[7]);
        this.setNaN(object2[8]);
        this.setMonetaryDecimalSeparatorString(object2[9]);
        this.setMonetaryGroupingSeparatorString(object2[10]);
        this.setExponentMultiplicationSign(object2[11]);
        this.digit = (char)35;
        this.padEscape = (char)42;
        this.sigDigit = (char)64;
        object2 = CurrencyData.provider.getInstance((ULocale)object, true);
        Currency currency = this.currency = Currency.getInstance((ULocale)object);
        if (currency != null) {
            this.intlCurrencySymbol = currency.getCurrencyCode();
            this.currencySymbol = this.currency.getName((ULocale)object, 0, null);
            object = ((CurrencyData.CurrencyDisplayInfo)object2).getFormatInfo(this.intlCurrencySymbol);
            if (object != null) {
                this.currencyPattern = ((CurrencyData.CurrencyFormatInfo)object).currencyPattern;
                this.setMonetaryDecimalSeparatorString(((CurrencyData.CurrencyFormatInfo)object).monetaryDecimalSeparator);
                this.setMonetaryGroupingSeparatorString(((CurrencyData.CurrencyFormatInfo)object).monetaryGroupingSeparator);
            }
        } else {
            this.intlCurrencySymbol = "XXX";
            this.currencySymbol = "\u00a4";
        }
        this.initSpacingInfo(((CurrencyData.CurrencyDisplayInfo)object2).getSpacingInfo());
    }

    private static CacheData loadData(ULocale arrstring) {
        String string;
        int n;
        int n2;
        int n3;
        Object object = NumberingSystem.getInstance((ULocale)arrstring);
        String[] arrstring2 = new String[10];
        if (object != null && ((NumberingSystem)object).getRadix() == 10 && !((NumberingSystem)object).isAlgorithmic() && NumberingSystem.isValidDigitString(((NumberingSystem)object).getDescription())) {
            string = ((NumberingSystem)object).getDescription();
            n3 = 0;
            for (n = 0; n < 10; ++n) {
                n2 = Character.charCount(string.codePointAt(n3)) + n3;
                arrstring2[n] = string.substring(n3, n2);
                n3 = n2;
            }
            string = ((NumberingSystem)object).getName();
        } else {
            arrstring2 = DEF_DIGIT_STRINGS_ARRAY;
            string = LATIN_NUMBERING_SYSTEM;
        }
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)arrstring);
        object = iCUResourceBundle.getULocale();
        arrstring = new String[SYMBOL_KEYS.length];
        DecFmtDataSink decFmtDataSink = new DecFmtDataSink(arrstring);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NumberElements/");
            stringBuilder.append(string);
            stringBuilder.append("/");
            stringBuilder.append(SYMBOLS);
            iCUResourceBundle.getAllItemsWithFallback(stringBuilder.toString(), decFmtDataSink);
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        n2 = 0;
        int n4 = arrstring.length;
        n3 = 0;
        do {
            n = n2;
            if (n3 >= n4) break;
            if (arrstring[n3] == null) {
                n = 1;
                break;
            }
            ++n3;
        } while (true);
        if (n != 0 && !string.equals(LATIN_NUMBERING_SYSTEM)) {
            iCUResourceBundle.getAllItemsWithFallback("NumberElements/latn/symbols", decFmtDataSink);
        }
        for (n3 = 0; n3 < SYMBOL_KEYS.length; ++n3) {
            if (arrstring[n3] != null) continue;
            arrstring[n3] = SYMBOL_DEFAULTS[n3];
        }
        if (arrstring[9] == null) {
            arrstring[9] = arrstring[0];
        }
        if (arrstring[10] == null) {
            arrstring[10] = arrstring[1];
        }
        return new CacheData((ULocale)object, arrstring2, arrstring);
    }

    private void readObject(ObjectInputStream arrc) throws IOException, ClassNotFoundException {
        arrc.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.monetarySeparator = this.decimalSeparator;
            this.exponential = (char)69;
        }
        if (this.serialVersionOnStream < 2) {
            this.padEscape = (char)42;
            this.plusSign = (char)43;
            this.exponentSeparator = String.valueOf(this.exponential);
        }
        if (this.serialVersionOnStream < 3) {
            this.requestedLocale = Locale.getDefault();
        }
        if (this.serialVersionOnStream < 4) {
            this.ulocale = ULocale.forLocale(this.requestedLocale);
        }
        if (this.serialVersionOnStream < 5) {
            this.monetaryGroupingSeparator = this.groupingSeparator;
        }
        if (this.serialVersionOnStream < 6) {
            if (this.currencySpcBeforeSym == null) {
                this.currencySpcBeforeSym = new String[3];
            }
            if (this.currencySpcAfterSym == null) {
                this.currencySpcAfterSym = new String[3];
            }
            this.initSpacingInfo(CurrencyData.CurrencySpacingInfo.DEFAULT);
        }
        if (this.serialVersionOnStream < 7) {
            if (this.minusString == null) {
                this.minusString = String.valueOf(this.minusSign);
            }
            if (this.plusString == null) {
                this.plusString = String.valueOf(this.plusSign);
            }
        }
        if (this.serialVersionOnStream < 8 && this.exponentMultiplicationSign == null) {
            this.exponentMultiplicationSign = "\u00d7";
        }
        if (this.serialVersionOnStream < 9) {
            if (this.digitStrings == null) {
                this.digitStrings = new String[10];
                arrc = this.digits;
                if (arrc != null && arrc.length == 10) {
                    this.zeroDigit = arrc[0];
                    for (int i = 0; i < 10; ++i) {
                        this.digitStrings[i] = String.valueOf(this.digits[i]);
                    }
                } else {
                    char c = this.zeroDigit;
                    if (this.digits == null) {
                        this.digits = new char[10];
                    }
                    char c2 = c;
                    for (int i = 0; i < 10; ++i) {
                        this.digits[i] = c2;
                        this.digitStrings[i] = String.valueOf(c2);
                        c = (char)(c2 + '\u0001');
                        c2 = c;
                    }
                }
            }
            if (this.decimalSeparatorString == null) {
                this.decimalSeparatorString = String.valueOf(this.decimalSeparator);
            }
            if (this.groupingSeparatorString == null) {
                this.groupingSeparatorString = String.valueOf(this.groupingSeparator);
            }
            if (this.percentString == null) {
                this.percentString = String.valueOf(this.percent);
            }
            if (this.perMillString == null) {
                this.perMillString = String.valueOf(this.perMill);
            }
            if (this.monetarySeparatorString == null) {
                this.monetarySeparatorString = String.valueOf(this.monetarySeparator);
            }
            if (this.monetaryGroupingSeparatorString == null) {
                this.monetaryGroupingSeparatorString = String.valueOf(this.monetaryGroupingSeparator);
            }
        }
        this.serialVersionOnStream = 8;
        this.currency = Currency.getInstance(this.intlCurrencySymbol);
        this.setDigitStrings(this.digitStrings);
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        int n;
        if (!(object instanceof DecimalFormatSymbols)) {
            return false;
        }
        boolean bl = true;
        if (this == object) {
            return true;
        }
        object = (DecimalFormatSymbols)object;
        for (n = 0; n <= 2; ++n) {
            if (!this.currencySpcBeforeSym[n].equals(((DecimalFormatSymbols)object).currencySpcBeforeSym[n])) {
                return false;
            }
            if (this.currencySpcAfterSym[n].equals(((DecimalFormatSymbols)object).currencySpcAfterSym[n])) continue;
            return false;
        }
        char[] arrc = ((DecimalFormatSymbols)object).digits;
        if (arrc == null) {
            for (n = 0; n < 10; ++n) {
                if (this.digits[n] == ((DecimalFormatSymbols)object).zeroDigit + n) continue;
                return false;
            }
        } else if (!Arrays.equals(this.digits, arrc)) {
            return false;
        }
        if (!(this.groupingSeparator == ((DecimalFormatSymbols)object).groupingSeparator && this.decimalSeparator == ((DecimalFormatSymbols)object).decimalSeparator && this.percent == ((DecimalFormatSymbols)object).percent && this.perMill == ((DecimalFormatSymbols)object).perMill && this.digit == ((DecimalFormatSymbols)object).digit && this.minusSign == ((DecimalFormatSymbols)object).minusSign && this.minusString.equals(((DecimalFormatSymbols)object).minusString) && this.patternSeparator == ((DecimalFormatSymbols)object).patternSeparator && this.infinity.equals(((DecimalFormatSymbols)object).infinity) && this.NaN.equals(((DecimalFormatSymbols)object).NaN) && this.currencySymbol.equals(((DecimalFormatSymbols)object).currencySymbol) && this.intlCurrencySymbol.equals(((DecimalFormatSymbols)object).intlCurrencySymbol) && this.padEscape == ((DecimalFormatSymbols)object).padEscape && this.plusSign == ((DecimalFormatSymbols)object).plusSign && this.plusString.equals(((DecimalFormatSymbols)object).plusString) && this.exponentSeparator.equals(((DecimalFormatSymbols)object).exponentSeparator) && this.monetarySeparator == ((DecimalFormatSymbols)object).monetarySeparator && this.monetaryGroupingSeparator == ((DecimalFormatSymbols)object).monetaryGroupingSeparator && this.exponentMultiplicationSign.equals(((DecimalFormatSymbols)object).exponentMultiplicationSign))) {
            bl = false;
        }
        return bl;
    }

    @Deprecated
    public int getCodePointZero() {
        return this.codePointZero;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    String getCurrencyPattern() {
        return this.currencyPattern;
    }

    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    public char getDecimalSeparator() {
        return this.decimalSeparator;
    }

    public String getDecimalSeparatorString() {
        return this.decimalSeparatorString;
    }

    public char getDigit() {
        return this.digit;
    }

    public String[] getDigitStrings() {
        return (String[])this.digitStrings.clone();
    }

    @Deprecated
    public String[] getDigitStringsLocal() {
        return this.digitStrings;
    }

    public char[] getDigits() {
        return (char[])this.digits.clone();
    }

    public String getExponentMultiplicationSign() {
        return this.exponentMultiplicationSign;
    }

    public String getExponentSeparator() {
        return this.exponentSeparator;
    }

    public char getGroupingSeparator() {
        return this.groupingSeparator;
    }

    public String getGroupingSeparatorString() {
        return this.groupingSeparatorString;
    }

    public String getInfinity() {
        return this.infinity;
    }

    public String getInternationalCurrencySymbol() {
        return this.intlCurrencySymbol;
    }

    @UnsupportedAppUsage
    public final ULocale getLocale(ULocale.Type object) {
        object = object == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
        return object;
    }

    public Locale getLocale() {
        return this.requestedLocale;
    }

    public char getMinusSign() {
        return this.minusSign;
    }

    public String getMinusSignString() {
        return this.minusString;
    }

    public char getMonetaryDecimalSeparator() {
        return this.monetarySeparator;
    }

    public String getMonetaryDecimalSeparatorString() {
        return this.monetarySeparatorString;
    }

    public char getMonetaryGroupingSeparator() {
        return this.monetaryGroupingSeparator;
    }

    public String getMonetaryGroupingSeparatorString() {
        return this.monetaryGroupingSeparatorString;
    }

    public String getNaN() {
        return this.NaN;
    }

    public char getPadEscape() {
        return this.padEscape;
    }

    public String getPatternForCurrencySpacing(int n, boolean bl) {
        if (n >= 0 && n <= 2) {
            if (bl) {
                return this.currencySpcBeforeSym[n];
            }
            return this.currencySpcAfterSym[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unknown currency spacing: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public char getPatternSeparator() {
        return this.patternSeparator;
    }

    public char getPerMill() {
        return this.perMill;
    }

    public String getPerMillString() {
        return this.perMillString;
    }

    public char getPercent() {
        return this.percent;
    }

    public String getPercentString() {
        return this.percentString;
    }

    public char getPlusSign() {
        return this.plusSign;
    }

    public String getPlusSignString() {
        return this.plusString;
    }

    public char getSignificantDigit() {
        return this.sigDigit;
    }

    public ULocale getULocale() {
        return this.ulocale;
    }

    public char getZeroDigit() {
        return this.zeroDigit;
    }

    public int hashCode() {
        return (this.digits[0] * 37 + this.groupingSeparator) * 37 + this.decimalSeparator;
    }

    public void setCurrency(Currency currency) {
        if (currency != null) {
            this.currency = currency;
            this.intlCurrencySymbol = currency.getCurrencyCode();
            this.currencySymbol = currency.getSymbol(this.requestedLocale);
            return;
        }
        throw new NullPointerException();
    }

    public void setCurrencySymbol(String string) {
        this.currencySymbol = string;
    }

    public void setDecimalSeparator(char c) {
        this.decimalSeparator = c;
        this.decimalSeparatorString = String.valueOf(c);
    }

    public void setDecimalSeparatorString(String string) {
        if (string != null) {
            this.decimalSeparatorString = string;
            this.decimalSeparator = string.length() == 1 ? string.charAt(0) : (char)46;
            return;
        }
        throw new NullPointerException("The input decimal separator is null");
    }

    public void setDigit(char c) {
        this.digit = c;
    }

    public void setDigitStrings(String[] arrobject) {
        if (arrobject != null) {
            if (arrobject.length == 10) {
                String[] arrstring = new String[10];
                Object object = new char[10];
                int n = -1;
                for (int i = 0; i < 10; ++i) {
                    Object object2 = arrobject[i];
                    if (object2 != null) {
                        int n2;
                        int n3;
                        arrstring[i] = object2;
                        if (object2.length() == 0) {
                            n3 = -1;
                            n2 = 0;
                        } else {
                            n3 = Character.codePointAt(arrobject[i], 0);
                            n2 = Character.charCount(n3);
                        }
                        if (n2 == object2.length()) {
                            if (n2 == 1 && object != null) {
                                object[i] = (char)n3;
                                object2 = object;
                            } else {
                                object2 = null;
                            }
                            if (i == 0) {
                                n2 = n3;
                                object = object2;
                            } else {
                                object = object2;
                                n2 = n;
                                if (n3 != n + i) {
                                    n2 = -1;
                                    object = object2;
                                }
                            }
                        } else {
                            n2 = -1;
                            object = null;
                        }
                        n = n2;
                        continue;
                    }
                    throw new IllegalArgumentException("The input digit string array contains a null element");
                }
                this.digitStrings = arrstring;
                this.codePointZero = n;
                if (object == null) {
                    arrobject = DEF_DIGIT_CHARS_ARRAY;
                    this.zeroDigit = (char)arrobject[0];
                    this.digits = arrobject;
                } else {
                    this.zeroDigit = object[0];
                    this.digits = object;
                }
                return;
            }
            throw new IllegalArgumentException("Number of digit strings is not 10");
        }
        throw new NullPointerException("The input digit string array is null");
    }

    public void setExponentMultiplicationSign(String string) {
        this.exponentMultiplicationSign = string;
    }

    public void setExponentSeparator(String string) {
        this.exponentSeparator = string;
    }

    public void setGroupingSeparator(char c) {
        this.groupingSeparator = c;
        this.groupingSeparatorString = String.valueOf(c);
    }

    public void setGroupingSeparatorString(String string) {
        if (string != null) {
            this.groupingSeparatorString = string;
            this.groupingSeparator = string.length() == 1 ? string.charAt(0) : (char)44;
            return;
        }
        throw new NullPointerException("The input grouping separator is null");
    }

    public void setInfinity(String string) {
        this.infinity = string;
    }

    public void setInternationalCurrencySymbol(String string) {
        this.intlCurrencySymbol = string;
    }

    final void setLocale(ULocale uLocale, ULocale uLocale2) {
        boolean bl = true;
        boolean bl2 = uLocale == null;
        if (uLocale2 != null) {
            bl = false;
        }
        if (bl2 == bl) {
            this.validLocale = uLocale;
            this.actualLocale = uLocale2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setMinusSign(char c) {
        this.minusSign = c;
        this.minusString = String.valueOf(c);
    }

    public void setMinusSignString(String string) {
        if (string != null) {
            this.minusString = string;
            this.minusSign = string.length() == 1 ? string.charAt(0) : (char)45;
            return;
        }
        throw new NullPointerException("The input minus sign is null");
    }

    public void setMonetaryDecimalSeparator(char c) {
        this.monetarySeparator = c;
        this.monetarySeparatorString = String.valueOf(c);
    }

    public void setMonetaryDecimalSeparatorString(String string) {
        if (string != null) {
            this.monetarySeparatorString = string;
            this.monetarySeparator = string.length() == 1 ? string.charAt(0) : (char)46;
            return;
        }
        throw new NullPointerException("The input monetary decimal separator is null");
    }

    public void setMonetaryGroupingSeparator(char c) {
        this.monetaryGroupingSeparator = c;
        this.monetaryGroupingSeparatorString = String.valueOf(c);
    }

    public void setMonetaryGroupingSeparatorString(String string) {
        if (string != null) {
            this.monetaryGroupingSeparatorString = string;
            this.monetaryGroupingSeparator = string.length() == 1 ? string.charAt(0) : (char)44;
            return;
        }
        throw new NullPointerException("The input monetary grouping separator is null");
    }

    public void setNaN(String string) {
        this.NaN = string;
    }

    public void setPadEscape(char c) {
        this.padEscape = c;
    }

    public void setPatternForCurrencySpacing(int n, boolean bl, String charSequence) {
        if (n >= 0 && n <= 2) {
            if (bl) {
                this.currencySpcBeforeSym = (String[])this.currencySpcBeforeSym.clone();
                this.currencySpcBeforeSym[n] = charSequence;
            } else {
                this.currencySpcAfterSym = (String[])this.currencySpcAfterSym.clone();
                this.currencySpcAfterSym[n] = charSequence;
            }
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("unknown currency spacing: ");
        ((StringBuilder)charSequence).append(n);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public void setPatternSeparator(char c) {
        this.patternSeparator = c;
    }

    public void setPerMill(char c) {
        this.perMill = c;
        this.perMillString = String.valueOf(c);
    }

    public void setPerMillString(String string) {
        if (string != null) {
            this.perMillString = string;
            this.perMill = string.length() == 1 ? string.charAt(0) : (char)8240;
            return;
        }
        throw new NullPointerException("The input permille string is null");
    }

    public void setPercent(char c) {
        this.percent = c;
        this.percentString = String.valueOf(c);
    }

    public void setPercentString(String string) {
        if (string != null) {
            this.percentString = string;
            this.percent = string.length() == 1 ? string.charAt(0) : (char)37;
            return;
        }
        throw new NullPointerException("The input percent sign is null");
    }

    public void setPlusSign(char c) {
        this.plusSign = c;
        this.plusString = String.valueOf(c);
    }

    public void setPlusSignString(String string) {
        if (string != null) {
            this.plusString = string;
            this.plusSign = string.length() == 1 ? string.charAt(0) : (char)43;
            return;
        }
        throw new NullPointerException("The input plus sign is null");
    }

    public void setSignificantDigit(char c) {
        this.sigDigit = c;
    }

    public void setZeroDigit(char c) {
        this.zeroDigit = c;
        this.digitStrings = (String[])this.digitStrings.clone();
        this.digits = (char[])this.digits.clone();
        this.digitStrings[0] = String.valueOf(c);
        this.digits[0] = c;
        for (int i = 1; i < 10; ++i) {
            char c2 = (char)(c + i);
            this.digitStrings[i] = String.valueOf(c2);
            this.digits[i] = c2;
        }
        this.codePointZero = c;
    }

    private static class CacheData {
        final String[] digits;
        final String[] numberElements;
        final ULocale validLocale;

        public CacheData(ULocale uLocale, String[] arrstring, String[] arrstring2) {
            this.validLocale = uLocale;
            this.digits = arrstring;
            this.numberElements = arrstring2;
        }
    }

    private static final class DecFmtDataSink
    extends UResource.Sink {
        private String[] numberElements;

        public DecFmtDataSink(String[] arrstring) {
            this.numberElements = arrstring;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                for (int i = 0; i < SYMBOL_KEYS.length; ++i) {
                    if (!key.contentEquals(SYMBOL_KEYS[i])) continue;
                    String[] arrstring = this.numberElements;
                    if (arrstring[i] != null) break;
                    arrstring[i] = value.toString();
                    break;
                }
                ++n;
            }
        }
    }

}


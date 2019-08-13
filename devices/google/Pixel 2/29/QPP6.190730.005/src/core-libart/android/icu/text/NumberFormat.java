/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUResourceBundle;
import android.icu.math.BigDecimal;
import android.icu.text.CurrencyPluralInfo;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.DisplayContext;
import android.icu.text.NumberingSystem;
import android.icu.text.RuleBasedNumberFormat;
import android.icu.text.UFormat;
import android.icu.util.Currency;
import android.icu.util.CurrencyAmount;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

public abstract class NumberFormat
extends UFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ACCOUNTINGCURRENCYSTYLE = 7;
    public static final int CASHCURRENCYSTYLE = 8;
    public static final int CURRENCYSTYLE = 1;
    public static final int FRACTION_FIELD = 1;
    public static final int INTEGERSTYLE = 4;
    public static final int INTEGER_FIELD = 0;
    public static final int ISOCURRENCYSTYLE = 5;
    public static final int NUMBERSTYLE = 0;
    public static final int PERCENTSTYLE = 2;
    public static final int PLURALCURRENCYSTYLE = 6;
    public static final int SCIENTIFICSTYLE = 3;
    public static final int STANDARDCURRENCYSTYLE = 9;
    static final int currentSerialVersion = 2;
    private static final char[] doubleCurrencySign = new char[]{'\u00a4', '\u00a4'};
    private static final String doubleCurrencyStr = new String(doubleCurrencySign);
    private static final long serialVersionUID = -2308460125733713944L;
    private static NumberFormatShim shim;
    private DisplayContext capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
    private Currency currency;
    private boolean groupingUsed = true;
    private byte maxFractionDigits = (byte)3;
    private byte maxIntegerDigits = (byte)40;
    private int maximumFractionDigits = 3;
    private int maximumIntegerDigits = 40;
    private byte minFractionDigits = (byte)(false ? 1 : 0);
    private byte minIntegerDigits = (byte)(true ? 1 : 0);
    private int minimumFractionDigits = 0;
    private int minimumIntegerDigits = 1;
    private boolean parseIntegerOnly = false;
    private boolean parseStrict;
    private int serialVersionOnStream = 2;

    static NumberFormat createInstance(ULocale object, int n) {
        Object object2;
        String string;
        DecimalFormatSymbols decimalFormatSymbols;
        String string2;
        block16 : {
            block15 : {
                string = NumberFormat.getPattern((ULocale)object, n);
                decimalFormatSymbols = new DecimalFormatSymbols((ULocale)object);
                if (n == 1 || n == 5 || n == 7 || n == 8) break block15;
                object2 = string;
                if (n != 9) break block16;
            }
            string2 = decimalFormatSymbols.getCurrencyPattern();
            object2 = string;
            if (string2 != null) {
                object2 = string2;
            }
        }
        string2 = object2;
        if (n == 5) {
            string2 = ((String)object2).replace("\u00a4", doubleCurrencyStr);
        }
        if ((object2 = NumberingSystem.getInstance((ULocale)object)) == null) {
            return null;
        }
        if (((NumberingSystem)object2).isAlgorithmic()) {
            n = 4;
            string2 = ((NumberingSystem)object2).getDescription();
            int n2 = string2.indexOf("/");
            int n3 = string2.lastIndexOf("/");
            if (n3 > n2) {
                object2 = string2.substring(0, n2);
                string = string2.substring(n2 + 1, n3);
                object = string2.substring(n3 + 1);
                object2 = new ULocale((String)object2);
                if (string.equals("SpelloutRules")) {
                    n = 1;
                }
            } else {
                object2 = object;
                object = string2;
            }
            object2 = new RuleBasedNumberFormat((ULocale)object2, n);
            ((RuleBasedNumberFormat)object2).setDefaultRuleSet((String)object);
            object = object2;
        } else {
            object2 = new DecimalFormat(string2, decimalFormatSymbols, n);
            if (n == 4) {
                ((DecimalFormat)object2).setMaximumFractionDigits(0);
                ((DecimalFormat)object2).setDecimalSeparatorAlwaysShown(false);
                ((DecimalFormat)object2).setParseIntegerOnly(true);
            }
            if (n == 8) {
                ((DecimalFormat)object2).setCurrencyUsage(Currency.CurrencyUsage.CASH);
            }
            if (n == 6) {
                ((DecimalFormat)object2).setCurrencyPluralInfo(CurrencyPluralInfo.getInstance((ULocale)object));
            }
            object = object2;
        }
        ((UFormat)object).setLocale(decimalFormatSymbols.getLocale(ULocale.VALID_LOCALE), decimalFormatSymbols.getLocale(ULocale.ACTUAL_LOCALE));
        return object;
    }

    public static Locale[] getAvailableLocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return NumberFormat.getShim().getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return NumberFormat.getShim().getAvailableULocales();
    }

    public static final NumberFormat getCurrencyInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 1);
    }

    public static NumberFormat getCurrencyInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 1);
    }

    public static NumberFormat getCurrencyInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 1);
    }

    public static final NumberFormat getInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 0);
    }

    public static final NumberFormat getInstance(int n) {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), n);
    }

    public static NumberFormat getInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 0);
    }

    public static NumberFormat getInstance(ULocale uLocale, int n) {
        if (n >= 0 && n <= 9) {
            return NumberFormat.getShim().createInstance(uLocale, n);
        }
        throw new IllegalArgumentException("choice should be from NUMBERSTYLE to STANDARDCURRENCYSTYLE");
    }

    public static NumberFormat getInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 0);
    }

    public static NumberFormat getInstance(Locale locale, int n) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), n);
    }

    public static final NumberFormat getIntegerInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 4);
    }

    public static NumberFormat getIntegerInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 4);
    }

    public static NumberFormat getIntegerInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 4);
    }

    public static final NumberFormat getNumberInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 0);
    }

    public static NumberFormat getNumberInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 0);
    }

    public static NumberFormat getNumberInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 0);
    }

    protected static String getPattern(ULocale uLocale, int n) {
        return NumberFormat.getPatternForStyle(uLocale, n);
    }

    @Deprecated
    protected static String getPattern(Locale locale, int n) {
        return NumberFormat.getPattern(ULocale.forLocale(locale), n);
    }

    @Deprecated
    public static String getPatternForStyle(ULocale uLocale, int n) {
        return NumberFormat.getPatternForStyleAndNumberingSystem(uLocale, NumberingSystem.getInstance(uLocale).getName(), n);
    }

    @Deprecated
    public static String getPatternForStyleAndNumberingSystem(ULocale object, String string, int n) {
        String string2;
        block9 : {
            switch (n) {
                default: {
                    break;
                }
                case 7: {
                    string2 = "accountingFormat";
                    break block9;
                }
                case 5: 
                case 8: 
                case 9: {
                    string2 = "currencyFormat";
                    break block9;
                }
                case 3: {
                    string2 = "scientificFormat";
                    break block9;
                }
                case 2: {
                    string2 = "percentFormat";
                    break block9;
                }
                case 1: {
                    string2 = ((ULocale)object).getKeywordValue("cf");
                    string2 = string2 != null && string2.equals("account") ? "accountingFormat" : "currencyFormat";
                    break block9;
                }
                case 0: 
                case 4: 
                case 6: {
                    string2 = "decimalFormat";
                    break block9;
                }
            }
            string2 = "decimalFormat";
        }
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object);
        object = new StringBuilder();
        ((StringBuilder)object).append("NumberElements/");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("/patterns/");
        ((StringBuilder)object).append(string2);
        string = iCUResourceBundle.findStringWithFallback(((StringBuilder)object).toString());
        object = string;
        if (string == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("NumberElements/latn/patterns/");
            ((StringBuilder)object).append(string2);
            object = iCUResourceBundle.getStringWithFallback(((StringBuilder)object).toString());
        }
        return object;
    }

    public static final NumberFormat getPercentInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 2);
    }

    public static NumberFormat getPercentInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 2);
    }

    public static NumberFormat getPercentInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 2);
    }

    public static final NumberFormat getScientificInstance() {
        return NumberFormat.getInstance(ULocale.getDefault(ULocale.Category.FORMAT), 3);
    }

    public static NumberFormat getScientificInstance(ULocale uLocale) {
        return NumberFormat.getInstance(uLocale, 3);
    }

    public static NumberFormat getScientificInstance(Locale locale) {
        return NumberFormat.getInstance(ULocale.forLocale(locale), 3);
    }

    private static NumberFormatShim getShim() {
        if (shim == null) {
            try {
                shim = (NumberFormatShim)Class.forName("android.icu.text.NumberFormatServiceShim").newInstance();
            }
            catch (Exception exception) {
                throw new RuntimeException(exception.getMessage());
            }
            catch (MissingResourceException missingResourceException) {
                throw missingResourceException;
            }
        }
        return shim;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int n;
        int n2;
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.maximumIntegerDigits = this.maxIntegerDigits;
            this.minimumIntegerDigits = this.minIntegerDigits;
            this.maximumFractionDigits = this.maxFractionDigits;
            this.minimumFractionDigits = this.minFractionDigits;
        }
        if (this.serialVersionOnStream < 2) {
            this.capitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
        }
        if ((n = this.minimumIntegerDigits) <= this.maximumIntegerDigits && (n2 = this.minimumFractionDigits) <= this.maximumFractionDigits && n >= 0 && n2 >= 0) {
            this.serialVersionOnStream = 2;
            return;
        }
        throw new InvalidObjectException("Digit count range invalid");
    }

    public static Object registerFactory(NumberFormatFactory numberFormatFactory) {
        if (numberFormatFactory != null) {
            return NumberFormat.getShim().registerFactory(numberFormatFactory);
        }
        throw new IllegalArgumentException("factory must not be null");
    }

    public static boolean unregister(Object object) {
        if (object != null) {
            NumberFormatShim numberFormatShim = shim;
            if (numberFormatShim == null) {
                return false;
            }
            return numberFormatShim.unregister(object);
        }
        throw new IllegalArgumentException("registryKey must not be null");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.maximumIntegerDigits;
        int n2 = 127;
        n = n > 127 ? 127 : (int)((byte)n);
        this.maxIntegerDigits = (byte)n;
        n = this.minimumIntegerDigits;
        n = n > 127 ? 127 : (int)((byte)n);
        this.minIntegerDigits = (byte)n;
        n = this.maximumFractionDigits;
        n = n > 127 ? 127 : (int)((byte)n);
        this.maxFractionDigits = (byte)n;
        n = this.minimumFractionDigits;
        n = n > 127 ? n2 : (int)((byte)n);
        this.minFractionDigits = (byte)n;
        objectOutputStream.defaultWriteObject();
    }

    @Override
    public Object clone() {
        return (NumberFormat)super.clone();
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (NumberFormat)object;
        boolean bl2 = bl;
        if (this.maximumIntegerDigits == ((NumberFormat)object).maximumIntegerDigits) {
            bl2 = bl;
            if (this.minimumIntegerDigits == ((NumberFormat)object).minimumIntegerDigits) {
                bl2 = bl;
                if (this.maximumFractionDigits == ((NumberFormat)object).maximumFractionDigits) {
                    bl2 = bl;
                    if (this.minimumFractionDigits == ((NumberFormat)object).minimumFractionDigits) {
                        bl2 = bl;
                        if (this.groupingUsed == ((NumberFormat)object).groupingUsed) {
                            bl2 = bl;
                            if (this.parseIntegerOnly == ((NumberFormat)object).parseIntegerOnly) {
                                bl2 = bl;
                                if (this.parseStrict == ((NumberFormat)object).parseStrict) {
                                    bl2 = bl;
                                    if (this.capitalizationSetting == ((NumberFormat)object).capitalizationSetting) {
                                        bl2 = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public final String format(double d) {
        return this.format(d, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(long l) {
        StringBuffer stringBuffer = new StringBuffer(19);
        this.format(l, stringBuffer, new FieldPosition(0));
        return stringBuffer.toString();
    }

    public final String format(BigDecimal bigDecimal) {
        return this.format(bigDecimal, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(CurrencyAmount currencyAmount) {
        return this.format(currencyAmount, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(java.math.BigDecimal bigDecimal) {
        return this.format(bigDecimal, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public final String format(BigInteger bigInteger) {
        return this.format(bigInteger, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public abstract StringBuffer format(double var1, StringBuffer var3, FieldPosition var4);

    public abstract StringBuffer format(long var1, StringBuffer var3, FieldPosition var4);

    public abstract StringBuffer format(BigDecimal var1, StringBuffer var2, FieldPosition var3);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public StringBuffer format(CurrencyAmount currencyAmount, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        synchronized (this) {
            Currency currency = this.getCurrency();
            Currency currency2 = currencyAmount.getCurrency();
            boolean bl = currency2.equals(currency);
            if (!bl) {
                this.setCurrency(currency2);
            }
            this.format(currencyAmount.getNumber(), stringBuffer, fieldPosition);
            if (!bl) {
                this.setCurrency(currency);
            }
            return stringBuffer;
        }
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof Long) {
            return this.format((Long)object, stringBuffer, fieldPosition);
        }
        if (object instanceof BigInteger) {
            return this.format((BigInteger)object, stringBuffer, fieldPosition);
        }
        if (object instanceof java.math.BigDecimal) {
            return this.format((java.math.BigDecimal)object, stringBuffer, fieldPosition);
        }
        if (object instanceof BigDecimal) {
            return this.format((BigDecimal)object, stringBuffer, fieldPosition);
        }
        if (object instanceof CurrencyAmount) {
            return this.format((CurrencyAmount)object, stringBuffer, fieldPosition);
        }
        if (object instanceof Number) {
            return this.format(((Number)object).doubleValue(), stringBuffer, fieldPosition);
        }
        throw new IllegalArgumentException("Cannot format given Object as a Number");
    }

    public abstract StringBuffer format(java.math.BigDecimal var1, StringBuffer var2, FieldPosition var3);

    public abstract StringBuffer format(BigInteger var1, StringBuffer var2, FieldPosition var3);

    public DisplayContext getContext(DisplayContext.Type enum_) {
        if (enum_ != DisplayContext.Type.CAPITALIZATION || (enum_ = this.capitalizationSetting) == null) {
            enum_ = DisplayContext.CAPITALIZATION_NONE;
        }
        return enum_;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    @Deprecated
    protected Currency getEffectiveCurrency() {
        Serializable serializable;
        Serializable serializable2 = serializable = this.getCurrency();
        if (serializable == null) {
            serializable2 = serializable = this.getLocale(ULocale.VALID_LOCALE);
            if (serializable == null) {
                serializable2 = ULocale.getDefault(ULocale.Category.FORMAT);
            }
            serializable2 = Currency.getInstance((ULocale)serializable2);
        }
        return serializable2;
    }

    public int getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }

    public int getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }

    public int getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }

    public int getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }

    public int getRoundingMode() {
        throw new UnsupportedOperationException("getRoundingMode must be implemented by the subclass implementation.");
    }

    public int hashCode() {
        return this.maximumIntegerDigits * 37 + this.maxFractionDigits;
    }

    public boolean isGroupingUsed() {
        return this.groupingUsed;
    }

    public boolean isParseIntegerOnly() {
        return this.parseIntegerOnly;
    }

    public boolean isParseStrict() {
        return this.parseStrict;
    }

    public Number parse(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Serializable serializable = this.parse(string, parsePosition);
        if (parsePosition.getIndex() != 0) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unparseable number: \"");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append('\"');
        throw new ParseException(((StringBuilder)serializable).toString(), parsePosition.getErrorIndex());
    }

    public abstract Number parse(String var1, ParsePosition var2);

    public CurrencyAmount parseCurrency(CharSequence object, ParsePosition parsePosition) {
        object = (object = this.parse(object.toString(), parsePosition)) == null ? null : new CurrencyAmount((Number)object, this.getEffectiveCurrency());
        return object;
    }

    @Override
    public final Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    public void setContext(DisplayContext displayContext) {
        if (displayContext.type() == DisplayContext.Type.CAPITALIZATION) {
            this.capitalizationSetting = displayContext;
        }
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setGroupingUsed(boolean bl) {
        this.groupingUsed = bl;
    }

    public void setMaximumFractionDigits(int n) {
        this.maximumFractionDigits = Math.max(0, n);
        if ((n = this.maximumFractionDigits) < this.minimumFractionDigits) {
            this.minimumFractionDigits = n;
        }
    }

    public void setMaximumIntegerDigits(int n) {
        this.maximumIntegerDigits = Math.max(0, n);
        int n2 = this.minimumIntegerDigits;
        n = this.maximumIntegerDigits;
        if (n2 > n) {
            this.minimumIntegerDigits = n;
        }
    }

    public void setMinimumFractionDigits(int n) {
        this.minimumFractionDigits = Math.max(0, n);
        n = this.maximumFractionDigits;
        int n2 = this.minimumFractionDigits;
        if (n < n2) {
            this.maximumFractionDigits = n2;
        }
    }

    public void setMinimumIntegerDigits(int n) {
        this.minimumIntegerDigits = Math.max(0, n);
        if ((n = this.minimumIntegerDigits) > this.maximumIntegerDigits) {
            this.maximumIntegerDigits = n;
        }
    }

    public void setParseIntegerOnly(boolean bl) {
        this.parseIntegerOnly = bl;
    }

    public void setParseStrict(boolean bl) {
        this.parseStrict = bl;
    }

    public void setRoundingMode(int n) {
        throw new UnsupportedOperationException("setRoundingMode must be implemented by the subclass implementation.");
    }

    public static class Field
    extends Format.Field {
        public static final Field CURRENCY;
        public static final Field DECIMAL_SEPARATOR;
        public static final Field EXPONENT;
        public static final Field EXPONENT_SIGN;
        public static final Field EXPONENT_SYMBOL;
        public static final Field FRACTION;
        public static final Field GROUPING_SEPARATOR;
        public static final Field INTEGER;
        public static final Field PERCENT;
        public static final Field PERMILLE;
        public static final Field SIGN;
        static final long serialVersionUID = -4516273749929385842L;

        static {
            SIGN = new Field("sign");
            INTEGER = new Field("integer");
            FRACTION = new Field("fraction");
            EXPONENT = new Field("exponent");
            EXPONENT_SIGN = new Field("exponent sign");
            EXPONENT_SYMBOL = new Field("exponent symbol");
            DECIMAL_SEPARATOR = new Field("decimal separator");
            GROUPING_SEPARATOR = new Field("grouping separator");
            PERCENT = new Field("percent");
            PERMILLE = new Field("per mille");
            CURRENCY = new Field("currency");
        }

        protected Field(String string) {
            super(string);
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getName().equals(INTEGER.getName())) {
                return INTEGER;
            }
            if (this.getName().equals(FRACTION.getName())) {
                return FRACTION;
            }
            if (this.getName().equals(EXPONENT.getName())) {
                return EXPONENT;
            }
            if (this.getName().equals(EXPONENT_SIGN.getName())) {
                return EXPONENT_SIGN;
            }
            if (this.getName().equals(EXPONENT_SYMBOL.getName())) {
                return EXPONENT_SYMBOL;
            }
            if (this.getName().equals(CURRENCY.getName())) {
                return CURRENCY;
            }
            if (this.getName().equals(DECIMAL_SEPARATOR.getName())) {
                return DECIMAL_SEPARATOR;
            }
            if (this.getName().equals(GROUPING_SEPARATOR.getName())) {
                return GROUPING_SEPARATOR;
            }
            if (this.getName().equals(PERCENT.getName())) {
                return PERCENT;
            }
            if (this.getName().equals(PERMILLE.getName())) {
                return PERMILLE;
            }
            if (this.getName().equals(SIGN.getName())) {
                return SIGN;
            }
            throw new InvalidObjectException("An invalid object.");
        }
    }

    public static abstract class NumberFormatFactory {
        public static final int FORMAT_CURRENCY = 1;
        public static final int FORMAT_INTEGER = 4;
        public static final int FORMAT_NUMBER = 0;
        public static final int FORMAT_PERCENT = 2;
        public static final int FORMAT_SCIENTIFIC = 3;

        protected NumberFormatFactory() {
        }

        public NumberFormat createFormat(ULocale uLocale, int n) {
            return this.createFormat(uLocale.toLocale(), n);
        }

        public NumberFormat createFormat(Locale locale, int n) {
            return this.createFormat(ULocale.forLocale(locale), n);
        }

        public abstract Set<String> getSupportedLocaleNames();

        public boolean visible() {
            return true;
        }
    }

    static abstract class NumberFormatShim {
        NumberFormatShim() {
        }

        abstract NumberFormat createInstance(ULocale var1, int var2);

        abstract Locale[] getAvailableLocales();

        abstract ULocale[] getAvailableULocales();

        abstract Object registerFactory(NumberFormatFactory var1);

        abstract boolean unregister(Object var1);
    }

    public static abstract class SimpleNumberFormatFactory
    extends NumberFormatFactory {
        final Set<String> localeNames;
        final boolean visible;

        public SimpleNumberFormatFactory(ULocale uLocale) {
            this(uLocale, true);
        }

        public SimpleNumberFormatFactory(ULocale uLocale, boolean bl) {
            this.localeNames = Collections.singleton(uLocale.getBaseName());
            this.visible = bl;
        }

        public SimpleNumberFormatFactory(Locale locale) {
            this(locale, true);
        }

        public SimpleNumberFormatFactory(Locale locale, boolean bl) {
            this.localeNames = Collections.singleton(ULocale.forLocale(locale).getBaseName());
            this.visible = bl;
        }

        @Override
        public final Set<String> getSupportedLocaleNames() {
            return this.localeNames;
        }

        @Override
        public final boolean visible() {
            return this.visible;
        }
    }

}


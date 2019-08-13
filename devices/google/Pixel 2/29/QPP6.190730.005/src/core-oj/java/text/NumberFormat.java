/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.ICU
 *  libcore.icu.LocaleData
 */
package java.text;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.DontCareFieldPosition;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import libcore.icu.ICU;
import libcore.icu.LocaleData;

public abstract class NumberFormat
extends Format {
    private static final int CURRENCYSTYLE = 1;
    public static final int FRACTION_FIELD = 1;
    private static final int INTEGERSTYLE = 3;
    public static final int INTEGER_FIELD = 0;
    private static final int NUMBERSTYLE = 0;
    private static final int PERCENTSTYLE = 2;
    static final int currentSerialVersion = 1;
    static final long serialVersionUID = -2308460125733713944L;
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
    private int serialVersionOnStream = 1;

    protected NumberFormat() {
    }

    public static Locale[] getAvailableLocales() {
        return ICU.getAvailableLocales();
    }

    public static final NumberFormat getCurrencyInstance() {
        return NumberFormat.getInstance(Locale.getDefault(Locale.Category.FORMAT), 1);
    }

    public static NumberFormat getCurrencyInstance(Locale locale) {
        return NumberFormat.getInstance(locale, 1);
    }

    public static final NumberFormat getInstance() {
        return NumberFormat.getInstance(Locale.getDefault(Locale.Category.FORMAT), 0);
    }

    public static NumberFormat getInstance(Locale locale) {
        return NumberFormat.getInstance(locale, 0);
    }

    private static NumberFormat getInstance(Locale cloneable, int n) {
        Object object = LocaleData.get((Locale)cloneable);
        String string = object.numberPattern;
        String string2 = object.currencyPattern;
        object = object.percentPattern;
        cloneable = DecimalFormatSymbols.getInstance((Locale)cloneable);
        int n2 = n == 3 ? 0 : n;
        cloneable = new DecimalFormat(new String[]{string, string2, object}[n2], (DecimalFormatSymbols)cloneable);
        if (n == 3) {
            ((DecimalFormat)cloneable).setMaximumFractionDigits(0);
            ((DecimalFormat)cloneable).setDecimalSeparatorAlwaysShown(false);
            ((DecimalFormat)cloneable).setParseIntegerOnly(true);
        } else if (n == 1) {
            ((DecimalFormat)cloneable).adjustForCurrencyDefaultFractionDigits();
        }
        return cloneable;
    }

    public static final NumberFormat getIntegerInstance() {
        return NumberFormat.getInstance(Locale.getDefault(Locale.Category.FORMAT), 3);
    }

    public static NumberFormat getIntegerInstance(Locale locale) {
        return NumberFormat.getInstance(locale, 3);
    }

    public static final NumberFormat getNumberInstance() {
        return NumberFormat.getInstance(Locale.getDefault(Locale.Category.FORMAT), 0);
    }

    public static NumberFormat getNumberInstance(Locale locale) {
        return NumberFormat.getInstance(locale, 0);
    }

    public static final NumberFormat getPercentInstance() {
        return NumberFormat.getInstance(Locale.getDefault(Locale.Category.FORMAT), 2);
    }

    public static NumberFormat getPercentInstance(Locale locale) {
        return NumberFormat.getInstance(locale, 2);
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
        if ((n2 = this.minimumIntegerDigits) <= this.maximumIntegerDigits && (n = this.minimumFractionDigits) <= this.maximumFractionDigits && n2 >= 0 && n >= 0) {
            this.serialVersionOnStream = 1;
            return;
        }
        throw new InvalidObjectException("Digit count range invalid");
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
                                bl2 = true;
                            }
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public final String format(double d) {
        return this.format(d, new StringBuffer(), DontCareFieldPosition.INSTANCE).toString();
    }

    public final String format(long l) {
        return this.format(l, new StringBuffer(), DontCareFieldPosition.INSTANCE).toString();
    }

    public abstract StringBuffer format(double var1, StringBuffer var3, FieldPosition var4);

    public abstract StringBuffer format(long var1, StringBuffer var3, FieldPosition var4);

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (!(object instanceof Long || object instanceof Integer || object instanceof Short || object instanceof Byte || object instanceof AtomicInteger || object instanceof AtomicLong || object instanceof BigInteger && ((BigInteger)object).bitLength() < 64)) {
            if (object instanceof Number) {
                return this.format(((Number)object).doubleValue(), stringBuffer, fieldPosition);
            }
            throw new IllegalArgumentException("Cannot format given Object as a Number");
        }
        return this.format(((Number)object).longValue(), stringBuffer, fieldPosition);
    }

    public Currency getCurrency() {
        throw new UnsupportedOperationException();
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

    public RoundingMode getRoundingMode() {
        throw new UnsupportedOperationException();
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

    public Number parse(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Serializable serializable = this.parse(string, parsePosition);
        if (parsePosition.index != 0) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unparseable number: \"");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("\"");
        throw new ParseException(((StringBuilder)serializable).toString(), parsePosition.errorIndex);
    }

    public abstract Number parse(String var1, ParsePosition var2);

    @Override
    public final Object parseObject(String string, ParsePosition parsePosition) {
        return this.parse(string, parsePosition);
    }

    public void setCurrency(Currency currency) {
        throw new UnsupportedOperationException();
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
        int n2 = this.maximumFractionDigits;
        n = this.minimumFractionDigits;
        if (n2 < n) {
            this.maximumFractionDigits = n;
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

    public void setRoundingMode(RoundingMode roundingMode) {
        throw new UnsupportedOperationException();
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
        private static final Map<String, Field> instanceMap;
        private static final long serialVersionUID = 7494728892700160890L;

        static {
            instanceMap = new HashMap<String, Field>(11);
            INTEGER = new Field("integer");
            FRACTION = new Field("fraction");
            EXPONENT = new Field("exponent");
            DECIMAL_SEPARATOR = new Field("decimal separator");
            SIGN = new Field("sign");
            GROUPING_SEPARATOR = new Field("grouping separator");
            EXPONENT_SYMBOL = new Field("exponent symbol");
            PERCENT = new Field("percent");
            PERMILLE = new Field("per mille");
            CURRENCY = new Field("currency");
            EXPONENT_SIGN = new Field("exponent sign");
        }

        protected Field(String string) {
            super(string);
            if (this.getClass() == Field.class) {
                instanceMap.put(string, this);
            }
        }

        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() == Field.class) {
                Field field = instanceMap.get(this.getName());
                if (field != null) {
                    return field;
                }
                throw new InvalidObjectException("unknown attribute name");
            }
            throw new InvalidObjectException("subclass didn't correctly implement readResolve");
        }
    }

}


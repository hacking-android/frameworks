/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleCache;
import android.icu.lang.UCharacter;
import android.icu.math.BigDecimal;
import android.icu.text.NumberFormat;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.MissingResourceException;

public final class DateNumberFormat
extends NumberFormat {
    private static SimpleCache<ULocale, char[]> CACHE = new SimpleCache();
    private static final int DECIMAL_BUF_SIZE = 20;
    private static final long PARSE_THRESHOLD = 0xCCCCCCCCCCCCCCBL;
    private static final long serialVersionUID = -6315692826916346953L;
    private transient char[] decimalBuf = new char[20];
    private char[] digits;
    private int maxIntDigits;
    private int minIntDigits;
    private char minusSign;
    private boolean positiveOnly = false;
    private char zeroDigit;

    public DateNumberFormat(ULocale uLocale, char c, String string) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; ++i) {
            stringBuffer.append((char)(c + i));
        }
        this.initialize(uLocale, stringBuffer.toString(), string);
    }

    public DateNumberFormat(ULocale uLocale, String string, String string2) {
        if (string.length() <= 10) {
            this.initialize(uLocale, string, string2);
            return;
        }
        throw new UnsupportedOperationException("DateNumberFormat does not support digits out of BMP.");
    }

    private void initialize(ULocale uLocale, String string, String object) {
        Object object2;
        Object object3 = object2 = CACHE.get(uLocale);
        if (object2 == null) {
            object2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", uLocale);
            try {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("NumberElements/");
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append("/symbols/minusSign");
                object = object3 = ((ICUResourceBundle)object2).getStringWithFallback(((StringBuilder)object3).toString());
            }
            catch (MissingResourceException missingResourceException) {
                if (!((String)object).equals("latn")) {
                    try {
                        object = ((ICUResourceBundle)object2).getStringWithFallback("NumberElements/latn/symbols/minusSign");
                    }
                    catch (MissingResourceException missingResourceException2) {
                        object = "-";
                    }
                }
                object = "-";
            }
            object3 = new char[11];
            for (int i = 0; i < 10; ++i) {
                object3[i] = string.charAt(i);
            }
            object3[10] = ((String)object).charAt(0);
            CACHE.put(uLocale, (char[])object3);
        }
        this.digits = new char[10];
        System.arraycopy(object3, 0, this.digits, 0, 10);
        this.zeroDigit = this.digits[0];
        this.minusSign = (char)object3[10];
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.digits == null) {
            this.setZeroDigit(this.zeroDigit);
        }
        this.decimalBuf = new char[20];
    }

    @Override
    public Object clone() {
        DateNumberFormat dateNumberFormat = (DateNumberFormat)super.clone();
        dateNumberFormat.digits = (char[])this.digits.clone();
        dateNumberFormat.decimalBuf = new char[20];
        return dateNumberFormat;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && super.equals(object) && object instanceof DateNumberFormat) {
            object = (DateNumberFormat)object;
            if (this.maxIntDigits == ((DateNumberFormat)object).maxIntDigits && this.minIntDigits == ((DateNumberFormat)object).minIntDigits && this.minusSign == ((DateNumberFormat)object).minusSign && this.positiveOnly == ((DateNumberFormat)object).positiveOnly && Arrays.equals(this.digits, ((DateNumberFormat)object).digits)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(double, StringBuffer, FieldPostion) is not implemented");
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        int n;
        long l2 = l;
        if (l < 0L) {
            stringBuffer.append(this.minusSign);
            l2 = -l;
        }
        int n2 = (int)l2;
        char[] arrc = this.decimalBuf;
        int n3 = arrc.length;
        int n4 = n = this.maxIntDigits;
        if (n3 < n) {
            n4 = arrc.length;
        }
        n = n4 - 1;
        do {
            this.decimalBuf[n] = this.digits[n2 % 10];
            if (n == 0 || (n2 /= 10) == 0) break;
            --n;
        } while (true);
        for (n2 = this.minIntDigits - (n4 - n); n2 > 0; --n2) {
            arrc = this.decimalBuf;
            arrc[--n] = this.digits[0];
        }
        stringBuffer.append(this.decimalBuf, n, n4 -= n);
        fieldPosition.setBeginIndex(0);
        if (fieldPosition.getField() == 0) {
            fieldPosition.setEndIndex(n4);
        } else {
            fieldPosition.setEndIndex(0);
        }
        return stringBuffer;
    }

    @Override
    public StringBuffer format(BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigDecimal, StringBuffer, FieldPostion) is not implemented");
    }

    @Override
    public StringBuffer format(java.math.BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigDecimal, StringBuffer, FieldPostion) is not implemented");
    }

    @Override
    public StringBuffer format(BigInteger bigInteger, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        throw new UnsupportedOperationException("StringBuffer format(BigInteger, StringBuffer, FieldPostion) is not implemented");
    }

    public char[] getDigits() {
        return (char[])this.digits.clone();
    }

    @Override
    public int getMaximumIntegerDigits() {
        return this.maxIntDigits;
    }

    @Override
    public int getMinimumIntegerDigits() {
        return this.minIntDigits;
    }

    public char getZeroDigit() {
        return this.zeroDigit;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Number parse(String object, ParsePosition parsePosition) {
        long l = 0L;
        boolean bl = false;
        boolean bl2 = false;
        int n = parsePosition.getIndex();
        int n2 = 0;
        while (n + n2 < ((String)object).length()) {
            block11 : {
                int n3;
                block15 : {
                    char c;
                    int n4;
                    block14 : {
                        block13 : {
                            block12 : {
                                block10 : {
                                    c = ((String)object).charAt(n + n2);
                                    if (n2 != 0 || c != this.minusSign) break block10;
                                    if (this.positiveOnly) break;
                                    bl2 = true;
                                    break block11;
                                }
                                n3 = c - this.digits[0];
                                if (n3 < 0) break block12;
                                n4 = n3;
                                if (9 >= n3) break block13;
                            }
                            n4 = UCharacter.digit(c);
                        }
                        if (n4 < 0) break block14;
                        n3 = n4;
                        if (9 >= n4) break block15;
                    }
                    n4 = 0;
                    do {
                        n3 = ++n4;
                        if (n4 >= 10) break;
                        if (c != this.digits[n4]) continue;
                        n3 = n4;
                        break;
                    } while (true);
                }
                if (n3 < 0 || n3 > 9 || l >= 0xCCCCCCCCCCCCCCBL) break;
                bl = true;
                l = 10L * l + (long)n3;
            }
            ++n2;
        }
        object = null;
        if (bl) {
            if (bl2) {
                l = -1L * l;
            }
            object = l;
            parsePosition.setIndex(n + n2);
        }
        return object;
    }

    @Override
    public void setMaximumIntegerDigits(int n) {
        this.maxIntDigits = n;
    }

    @Override
    public void setMinimumIntegerDigits(int n) {
        this.minIntDigits = n;
    }

    public void setParsePositiveOnly(boolean bl) {
        this.positiveOnly = bl;
    }

    public void setZeroDigit(char c) {
        this.zeroDigit = c;
        if (this.digits == null) {
            this.digits = new char[10];
        }
        this.digits[0] = c;
        for (int i = 1; i < 10; ++i) {
            this.digits[i] = (char)(c + i);
        }
    }
}


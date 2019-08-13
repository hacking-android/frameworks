/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DecimalFormatSymbols
 *  android.icu.util.Currency
 *  libcore.icu.ICU
 *  libcore.icu.LocaleData
 */
package java.text;

import android.icu.util.Currency;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Locale;
import libcore.icu.ICU;
import libcore.icu.LocaleData;

public class DecimalFormatSymbols
implements Cloneable,
Serializable {
    private static final int currentSerialVersion = 3;
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("currencySymbol", String.class), new ObjectStreamField("decimalSeparator", Character.TYPE), new ObjectStreamField("digit", Character.TYPE), new ObjectStreamField("exponential", Character.TYPE), new ObjectStreamField("exponentialSeparator", String.class), new ObjectStreamField("groupingSeparator", Character.TYPE), new ObjectStreamField("infinity", String.class), new ObjectStreamField("intlCurrencySymbol", String.class), new ObjectStreamField("minusSign", Character.TYPE), new ObjectStreamField("monetarySeparator", Character.TYPE), new ObjectStreamField("NaN", String.class), new ObjectStreamField("patternSeparator", Character.TYPE), new ObjectStreamField("percent", Character.TYPE), new ObjectStreamField("perMill", Character.TYPE), new ObjectStreamField("serialVersionOnStream", Integer.TYPE), new ObjectStreamField("zeroDigit", Character.TYPE), new ObjectStreamField("locale", Locale.class), new ObjectStreamField("minusSignStr", String.class), new ObjectStreamField("percentStr", String.class)};
    static final long serialVersionUID = 5772796243397350300L;
    private String NaN;
    private transient android.icu.text.DecimalFormatSymbols cachedIcuDFS = null;
    private transient java.util.Currency currency;
    private String currencySymbol;
    private char decimalSeparator;
    private char digit;
    private char exponential;
    private String exponentialSeparator;
    private char groupingSeparator;
    private String infinity;
    private String intlCurrencySymbol;
    private Locale locale;
    private char minusSign;
    private char monetarySeparator;
    private char patternSeparator;
    private char perMill;
    private char percent;
    private int serialVersionOnStream = 3;
    private char zeroDigit;

    public DecimalFormatSymbols() {
        this.initialize(Locale.getDefault(Locale.Category.FORMAT));
    }

    public DecimalFormatSymbols(Locale locale) {
        this.initialize(locale);
    }

    protected static DecimalFormatSymbols fromIcuInstance(android.icu.text.DecimalFormatSymbols decimalFormatSymbols) {
        DecimalFormatSymbols decimalFormatSymbols2 = new DecimalFormatSymbols(decimalFormatSymbols.getLocale());
        decimalFormatSymbols2.setZeroDigit(decimalFormatSymbols.getZeroDigit());
        decimalFormatSymbols2.setDigit(decimalFormatSymbols.getDigit());
        decimalFormatSymbols2.setDecimalSeparator(decimalFormatSymbols.getDecimalSeparator());
        decimalFormatSymbols2.setGroupingSeparator(decimalFormatSymbols.getGroupingSeparator());
        decimalFormatSymbols2.setPatternSeparator(decimalFormatSymbols.getPatternSeparator());
        decimalFormatSymbols2.setPercent(decimalFormatSymbols.getPercent());
        decimalFormatSymbols2.setPerMill(decimalFormatSymbols.getPerMill());
        decimalFormatSymbols2.setMonetaryDecimalSeparator(decimalFormatSymbols.getMonetaryDecimalSeparator());
        decimalFormatSymbols2.setMinusSign(decimalFormatSymbols.getMinusSign());
        decimalFormatSymbols2.setInfinity(decimalFormatSymbols.getInfinity());
        decimalFormatSymbols2.setNaN(decimalFormatSymbols.getNaN());
        decimalFormatSymbols2.setExponentSeparator(decimalFormatSymbols.getExponentSeparator());
        try {
            if (decimalFormatSymbols.getCurrency() != null) {
                decimalFormatSymbols2.setCurrency(java.util.Currency.getInstance(decimalFormatSymbols.getCurrency().getCurrencyCode()));
            } else {
                decimalFormatSymbols2.setCurrency(java.util.Currency.getInstance("XXX"));
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            decimalFormatSymbols2.setCurrency(java.util.Currency.getInstance("XXX"));
        }
        decimalFormatSymbols2.setInternationalCurrencySymbol(decimalFormatSymbols.getInternationalCurrencySymbol());
        decimalFormatSymbols2.setCurrencySymbol(decimalFormatSymbols.getCurrencySymbol());
        return decimalFormatSymbols2;
    }

    public static Locale[] getAvailableLocales() {
        return ICU.getAvailableLocales();
    }

    public static final DecimalFormatSymbols getInstance() {
        return DecimalFormatSymbols.getInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    public static final DecimalFormatSymbols getInstance(Locale locale) {
        return new DecimalFormatSymbols(locale);
    }

    private void initialize(Locale arrobject) {
        this.locale = arrobject;
        if (arrobject != null) {
            Locale locale = LocaleData.mapInvalidAndNullLocales((Locale)arrobject);
            Object object = LocaleData.get((Locale)locale);
            arrobject = new Object[3];
            arrobject[0] = new String[]{String.valueOf(((LocaleData)object).decimalSeparator), String.valueOf(((LocaleData)object).groupingSeparator), String.valueOf(((LocaleData)object).patternSeparator), ((LocaleData)object).percent, String.valueOf(((LocaleData)object).zeroDigit), "#", ((LocaleData)object).minusSign, ((LocaleData)object).exponentSeparator, ((LocaleData)object).perMill, ((LocaleData)object).infinity, ((LocaleData)object).NaN};
            object = (String[])arrobject[0];
            this.decimalSeparator = object[0].charAt(0);
            this.groupingSeparator = object[1].charAt(0);
            this.patternSeparator = object[2].charAt(0);
            this.percent = DecimalFormatSymbols.maybeStripMarkers(object[3], '%');
            this.zeroDigit = object[4].charAt(0);
            this.digit = object[5].charAt(0);
            this.minusSign = DecimalFormatSymbols.maybeStripMarkers(object[6], '-');
            this.exponential = object[7].charAt(0);
            this.exponentialSeparator = object[7];
            this.perMill = DecimalFormatSymbols.maybeStripMarkers(object[8], '\u2030');
            this.infinity = object[9];
            this.NaN = object[10];
            if (locale.getCountry().length() > 0) {
                try {
                    this.currency = java.util.Currency.getInstance(locale);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
            }
            if ((object = this.currency) != null) {
                this.intlCurrencySymbol = ((java.util.Currency)object).getCurrencyCode();
                if (arrobject[1] != null && arrobject[1] == this.intlCurrencySymbol) {
                    this.currencySymbol = (String)arrobject[2];
                } else {
                    this.currencySymbol = this.currency.getSymbol(locale);
                    arrobject[1] = this.intlCurrencySymbol;
                    arrobject[2] = this.currencySymbol;
                }
            } else {
                this.intlCurrencySymbol = "XXX";
                try {
                    this.currency = java.util.Currency.getInstance(this.intlCurrencySymbol);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
                this.currencySymbol = "\u00a4";
            }
            this.monetarySeparator = this.decimalSeparator;
            return;
        }
        throw new NullPointerException("locale");
    }

    public static char maybeStripMarkers(String string, char c) {
        int n = string.length();
        if (n >= 1) {
            char c2 = '\u0000';
            char c3 = '\u0000';
            char c4 = c3;
            for (int i = 0; i < n; ++i) {
                char c5 = string.charAt(i);
                c3 = c2;
                char c6 = c4;
                if (c5 != '\u200e') {
                    c3 = c2;
                    c6 = c4;
                    if (c5 != '\u200f') {
                        if (c5 == '\u061c') {
                            c3 = c2;
                            c6 = c4;
                        } else {
                            if (c2 != '\u0000') {
                                return c;
                            }
                            c3 = '\u0001';
                            c6 = c2 = c5;
                        }
                    }
                }
                c2 = c3;
                c4 = c6;
            }
            if (c2 != '\u0000') {
                return c4;
            }
        }
        return c;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        object = ((ObjectInputStream)object).readFields();
        int n = ((ObjectInputStream.GetField)object).get("serialVersionOnStream", 0);
        this.currencySymbol = (String)((ObjectInputStream.GetField)object).get("currencySymbol", "");
        this.setDecimalSeparator(((ObjectInputStream.GetField)object).get("decimalSeparator", '.'));
        this.setDigit(((ObjectInputStream.GetField)object).get("digit", '#'));
        this.setGroupingSeparator(((ObjectInputStream.GetField)object).get("groupingSeparator", ','));
        this.infinity = (String)((ObjectInputStream.GetField)object).get("infinity", "");
        this.intlCurrencySymbol = (String)((ObjectInputStream.GetField)object).get("intlCurrencySymbol", "");
        this.NaN = (String)((ObjectInputStream.GetField)object).get("NaN", "");
        this.setPatternSeparator(((ObjectInputStream.GetField)object).get("patternSeparator", ';'));
        String string = (String)((ObjectInputStream.GetField)object).get("minusSignStr", null);
        if (string != null) {
            this.minusSign = string.charAt(0);
        } else {
            this.setMinusSign(((ObjectInputStream.GetField)object).get("minusSign", '-'));
        }
        string = (String)((ObjectInputStream.GetField)object).get("percentStr", null);
        if (string != null) {
            this.percent = string.charAt(0);
        } else {
            this.setPercent(((ObjectInputStream.GetField)object).get("percent", '%'));
        }
        this.setPerMill(((ObjectInputStream.GetField)object).get("perMill", '\u2030'));
        this.setZeroDigit(((ObjectInputStream.GetField)object).get("zeroDigit", '0'));
        this.locale = (Locale)((ObjectInputStream.GetField)object).get("locale", null);
        if (n == 0) {
            this.setMonetaryDecimalSeparator(this.getDecimalSeparator());
        } else {
            this.setMonetaryDecimalSeparator(((ObjectInputStream.GetField)object).get("monetarySeparator", '.'));
        }
        if (n == 0) {
            this.exponentialSeparator = "E";
        } else if (n < 3) {
            this.setExponentSeparator(String.valueOf(((ObjectInputStream.GetField)object).get("exponential", 'E')));
        } else {
            this.setExponentSeparator((String)((ObjectInputStream.GetField)object).get("exponentialSeparator", "E"));
        }
        try {
            this.currency = java.util.Currency.getInstance(this.intlCurrencySymbol);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.currency = null;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putField = objectOutputStream.putFields();
        putField.put("currencySymbol", this.currencySymbol);
        putField.put("decimalSeparator", this.getDecimalSeparator());
        putField.put("digit", this.getDigit());
        putField.put("exponential", this.exponentialSeparator.charAt(0));
        putField.put("exponentialSeparator", this.exponentialSeparator);
        putField.put("groupingSeparator", this.getGroupingSeparator());
        putField.put("infinity", this.infinity);
        putField.put("intlCurrencySymbol", this.intlCurrencySymbol);
        putField.put("monetarySeparator", this.getMonetaryDecimalSeparator());
        putField.put("NaN", this.NaN);
        putField.put("patternSeparator", this.getPatternSeparator());
        putField.put("perMill", this.getPerMill());
        putField.put("serialVersionOnStream", 3);
        putField.put("zeroDigit", this.getZeroDigit());
        putField.put("locale", this.locale);
        putField.put("minusSign", this.minusSign);
        putField.put("percent", this.percent);
        putField.put("minusSignStr", this.getMinusSignString());
        putField.put("percentStr", this.getPercentString());
        objectOutputStream.writeFields();
    }

    public Object clone() {
        try {
            DecimalFormatSymbols decimalFormatSymbols = (DecimalFormatSymbols)super.clone();
            return decimalFormatSymbols;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

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
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (DecimalFormatSymbols)object;
            if (this.zeroDigit != ((DecimalFormatSymbols)object).zeroDigit || this.groupingSeparator != ((DecimalFormatSymbols)object).groupingSeparator || this.decimalSeparator != ((DecimalFormatSymbols)object).decimalSeparator || this.percent != ((DecimalFormatSymbols)object).percent || this.perMill != ((DecimalFormatSymbols)object).perMill || this.digit != ((DecimalFormatSymbols)object).digit || this.minusSign != ((DecimalFormatSymbols)object).minusSign || this.patternSeparator != ((DecimalFormatSymbols)object).patternSeparator || !this.infinity.equals(((DecimalFormatSymbols)object).infinity) || !this.NaN.equals(((DecimalFormatSymbols)object).NaN) || !this.currencySymbol.equals(((DecimalFormatSymbols)object).currencySymbol) || !this.intlCurrencySymbol.equals(((DecimalFormatSymbols)object).intlCurrencySymbol) || this.currency != ((DecimalFormatSymbols)object).currency || this.monetarySeparator != ((DecimalFormatSymbols)object).monetarySeparator || !this.exponentialSeparator.equals(((DecimalFormatSymbols)object).exponentialSeparator) || !this.locale.equals(((DecimalFormatSymbols)object).locale)) break block3;
            bl = true;
        }
        return bl;
    }

    public java.util.Currency getCurrency() {
        return this.currency;
    }

    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    public char getDecimalSeparator() {
        return this.decimalSeparator;
    }

    public char getDigit() {
        return this.digit;
    }

    public String getExponentSeparator() {
        return this.exponentialSeparator;
    }

    char getExponentialSymbol() {
        return this.exponential;
    }

    public char getGroupingSeparator() {
        return this.groupingSeparator;
    }

    protected android.icu.text.DecimalFormatSymbols getIcuDecimalFormatSymbols() {
        android.icu.text.DecimalFormatSymbols decimalFormatSymbols = this.cachedIcuDFS;
        if (decimalFormatSymbols != null) {
            return decimalFormatSymbols;
        }
        this.cachedIcuDFS = new android.icu.text.DecimalFormatSymbols(this.locale);
        this.cachedIcuDFS.setPlusSign('+');
        this.cachedIcuDFS.setZeroDigit(this.zeroDigit);
        this.cachedIcuDFS.setDigit(this.digit);
        this.cachedIcuDFS.setDecimalSeparator(this.decimalSeparator);
        this.cachedIcuDFS.setGroupingSeparator(this.groupingSeparator);
        this.cachedIcuDFS.setMonetaryGroupingSeparator(this.groupingSeparator);
        this.cachedIcuDFS.setPatternSeparator(this.patternSeparator);
        this.cachedIcuDFS.setPercent(this.percent);
        this.cachedIcuDFS.setPerMill(this.perMill);
        this.cachedIcuDFS.setMonetaryDecimalSeparator(this.monetarySeparator);
        this.cachedIcuDFS.setMinusSign(this.minusSign);
        this.cachedIcuDFS.setInfinity(this.infinity);
        this.cachedIcuDFS.setNaN(this.NaN);
        this.cachedIcuDFS.setExponentSeparator(this.exponentialSeparator);
        try {
            this.cachedIcuDFS.setCurrency(Currency.getInstance((String)this.currency.getCurrencyCode()));
        }
        catch (NullPointerException nullPointerException) {
            this.currency = java.util.Currency.getInstance("XXX");
        }
        this.cachedIcuDFS.setCurrencySymbol(this.currencySymbol);
        this.cachedIcuDFS.setInternationalCurrencySymbol(this.intlCurrencySymbol);
        return this.cachedIcuDFS;
    }

    public String getInfinity() {
        return this.infinity;
    }

    public String getInternationalCurrencySymbol() {
        return this.intlCurrencySymbol;
    }

    public char getMinusSign() {
        return this.minusSign;
    }

    public String getMinusSignString() {
        return String.valueOf(this.minusSign);
    }

    public char getMonetaryDecimalSeparator() {
        return this.monetarySeparator;
    }

    public String getNaN() {
        return this.NaN;
    }

    public char getPatternSeparator() {
        return this.patternSeparator;
    }

    public char getPerMill() {
        return this.perMill;
    }

    public char getPercent() {
        return this.percent;
    }

    public String getPercentString() {
        return String.valueOf(this.percent);
    }

    public char getZeroDigit() {
        return this.zeroDigit;
    }

    public int hashCode() {
        return ((((((((((((((this.zeroDigit * 37 + this.groupingSeparator) * 37 + this.decimalSeparator) * 37 + this.percent) * 37 + this.perMill) * 37 + this.digit) * 37 + this.minusSign) * 37 + this.patternSeparator) * 37 + this.infinity.hashCode()) * 37 + this.NaN.hashCode()) * 37 + this.currencySymbol.hashCode()) * 37 + this.intlCurrencySymbol.hashCode()) * 37 + this.currency.hashCode()) * 37 + this.monetarySeparator) * 37 + this.exponentialSeparator.hashCode()) * 37 + this.locale.hashCode();
    }

    public void setCurrency(java.util.Currency currency) {
        if (currency != null) {
            this.currency = currency;
            this.intlCurrencySymbol = currency.getCurrencyCode();
            this.currencySymbol = currency.getSymbol(this.locale);
            this.cachedIcuDFS = null;
            return;
        }
        throw new NullPointerException();
    }

    public void setCurrencySymbol(String string) {
        this.currencySymbol = string;
        this.cachedIcuDFS = null;
    }

    public void setDecimalSeparator(char c) {
        this.decimalSeparator = c;
        this.cachedIcuDFS = null;
    }

    public void setDigit(char c) {
        this.digit = c;
        this.cachedIcuDFS = null;
    }

    public void setExponentSeparator(String string) {
        if (string != null) {
            this.exponentialSeparator = string;
            return;
        }
        throw new NullPointerException();
    }

    void setExponentialSymbol(char c) {
        this.exponential = c;
        this.cachedIcuDFS = null;
    }

    public void setGroupingSeparator(char c) {
        this.groupingSeparator = c;
        this.cachedIcuDFS = null;
    }

    public void setInfinity(String string) {
        this.infinity = string;
        this.cachedIcuDFS = null;
    }

    public void setInternationalCurrencySymbol(String string) {
        this.intlCurrencySymbol = string;
        this.currency = null;
        if (string != null) {
            try {
                this.currency = java.util.Currency.getInstance(string);
                this.currencySymbol = this.currency.getSymbol(this.locale);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        this.cachedIcuDFS = null;
    }

    public void setMinusSign(char c) {
        this.minusSign = c;
        this.cachedIcuDFS = null;
    }

    public void setMonetaryDecimalSeparator(char c) {
        this.monetarySeparator = c;
        this.cachedIcuDFS = null;
    }

    public void setNaN(String string) {
        this.NaN = string;
        this.cachedIcuDFS = null;
    }

    public void setPatternSeparator(char c) {
        this.patternSeparator = c;
        this.cachedIcuDFS = null;
    }

    public void setPerMill(char c) {
        this.perMill = c;
        this.cachedIcuDFS = null;
    }

    public void setPercent(char c) {
        this.percent = c;
        this.cachedIcuDFS = null;
    }

    public void setZeroDigit(char c) {
        this.zeroDigit = c;
        this.cachedIcuDFS = null;
    }
}


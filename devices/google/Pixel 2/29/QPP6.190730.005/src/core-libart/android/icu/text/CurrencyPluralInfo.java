/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CurrencyData;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CurrencyPluralInfo
implements Cloneable,
Serializable {
    private static final String defaultCurrencyPluralPattern;
    private static final char[] defaultCurrencyPluralPatternChar;
    private static final long serialVersionUID = 1L;
    private static final char[] tripleCurrencySign;
    private static final String tripleCurrencyStr;
    private Map<String, String> pluralCountToCurrencyUnitPattern = null;
    private PluralRules pluralRules = null;
    private ULocale ulocale = null;

    static {
        tripleCurrencySign = new char[]{'\u00a4', '\u00a4', '\u00a4'};
        tripleCurrencyStr = new String(tripleCurrencySign);
        defaultCurrencyPluralPatternChar = new char[]{'\u0000', '.', '#', '#', ' ', '\u00a4', '\u00a4', '\u00a4'};
        defaultCurrencyPluralPattern = new String(defaultCurrencyPluralPatternChar);
    }

    public CurrencyPluralInfo() {
        this.initialize(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public CurrencyPluralInfo(ULocale uLocale) {
        this.initialize(uLocale);
    }

    public CurrencyPluralInfo(Locale locale) {
        this.initialize(ULocale.forLocale(locale));
    }

    public static CurrencyPluralInfo getInstance() {
        return new CurrencyPluralInfo();
    }

    public static CurrencyPluralInfo getInstance(ULocale uLocale) {
        return new CurrencyPluralInfo(uLocale);
    }

    public static CurrencyPluralInfo getInstance(Locale locale) {
        return new CurrencyPluralInfo(locale);
    }

    private void initialize(ULocale uLocale) {
        this.ulocale = uLocale;
        this.pluralRules = PluralRules.forLocale(uLocale);
        this.setupCurrencyPluralPattern(uLocale);
    }

    /*
     * WARNING - void declaration
     */
    private void setupCurrencyPluralPattern(ULocale object2) {
        this.pluralCountToCurrencyUnitPattern = new HashMap<String, String>();
        CharSequence charSequence = NumberFormat.getPattern((ULocale)object2, 0);
        int n = ((String)charSequence).indexOf(";");
        String string = null;
        String string2 = charSequence;
        if (n != -1) {
            string = ((String)charSequence).substring(n + 1);
            string2 = ((String)charSequence).substring(0, n);
        }
        for (Map.Entry<String, String> entry : CurrencyData.provider.getInstance((ULocale)object2, true).getUnitPatterns().entrySet()) {
            void var1_7;
            String string3 = entry.getKey();
            String string4 = entry.getValue();
            charSequence = string4.replace("{0}", string2).replace("{1}", tripleCurrencyStr);
            String string5 = charSequence;
            if (n != -1) {
                String string6 = string4.replace("{0}", string).replace("{1}", tripleCurrencyStr);
                charSequence = new StringBuilder((String)charSequence);
                ((StringBuilder)charSequence).append(";");
                ((StringBuilder)charSequence).append(string6);
                String string7 = ((StringBuilder)charSequence).toString();
            }
            this.pluralCountToCurrencyUnitPattern.put(string3, (String)var1_7);
        }
    }

    public Object clone() {
        try {
            CurrencyPluralInfo currencyPluralInfo = (CurrencyPluralInfo)super.clone();
            currencyPluralInfo.ulocale = (ULocale)this.ulocale.clone();
            HashMap<String, String> object2 = new HashMap<String, String>();
            currencyPluralInfo.pluralCountToCurrencyUnitPattern = object2;
            for (String string : this.pluralCountToCurrencyUnitPattern.keySet()) {
                String string2 = this.pluralCountToCurrencyUnitPattern.get(string);
                currencyPluralInfo.pluralCountToCurrencyUnitPattern.put(string, string2);
            }
            return currencyPluralInfo;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof CurrencyPluralInfo;
        boolean bl2 = false;
        if (bl) {
            object = (CurrencyPluralInfo)object;
            if (this.pluralRules.equals(((CurrencyPluralInfo)object).pluralRules) && this.pluralCountToCurrencyUnitPattern.equals(((CurrencyPluralInfo)object).pluralCountToCurrencyUnitPattern)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public String getCurrencyPluralPattern(String string) {
        String string2;
        String string3 = string2 = this.pluralCountToCurrencyUnitPattern.get(string);
        if (string2 == null) {
            if (!string.equals("other")) {
                string2 = this.pluralCountToCurrencyUnitPattern.get("other");
            }
            string3 = string2;
            if (string2 == null) {
                string3 = defaultCurrencyPluralPattern;
            }
        }
        return string3;
    }

    public ULocale getLocale() {
        return this.ulocale;
    }

    public PluralRules getPluralRules() {
        return this.pluralRules;
    }

    public int hashCode() {
        return this.pluralCountToCurrencyUnitPattern.hashCode() ^ this.pluralRules.hashCode() ^ this.ulocale.hashCode();
    }

    @Deprecated
    public Iterator<String> pluralPatternIterator() {
        return this.pluralCountToCurrencyUnitPattern.keySet().iterator();
    }

    @Deprecated
    String select(double d) {
        return this.pluralRules.select(d);
    }

    @Deprecated
    public String select(PluralRules.FixedDecimal fixedDecimal) {
        return this.pluralRules.select(fixedDecimal);
    }

    public void setCurrencyPluralPattern(String string, String string2) {
        this.pluralCountToCurrencyUnitPattern.put(string, string2);
    }

    public void setLocale(ULocale uLocale) {
        this.ulocale = uLocale;
        this.initialize(uLocale);
    }

    public void setPluralRules(String string) {
        this.pluralRules = PluralRules.createRules(string);
    }
}


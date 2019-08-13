/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUDebug;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.PatternProps;
import android.icu.lang.UCharacter;
import android.icu.math.BigDecimal;
import android.icu.text.BreakIterator;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.DisplayContext;
import android.icu.text.NFRule;
import android.icu.text.NFRuleSet;
import android.icu.text.NumberFormat;
import android.icu.text.PluralFormat;
import android.icu.text.PluralRules;
import android.icu.text.RBNFPostProcessor;
import android.icu.text.RbnfLenientScanner;
import android.icu.text.RbnfLenientScannerProvider;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

public class RuleBasedNumberFormat
extends NumberFormat {
    private static final boolean DEBUG = ICUDebug.enabled("rbnf");
    public static final int DURATION = 3;
    private static final BigDecimal MAX_VALUE;
    private static final BigDecimal MIN_VALUE;
    public static final int NUMBERING_SYSTEM = 4;
    public static final int ORDINAL = 2;
    public static final int SPELLOUT = 1;
    private static final String[] locnames;
    private static final String[] rulenames;
    static final long serialVersionUID = -7664252765575395068L;
    private transient BreakIterator capitalizationBrkIter = null;
    private boolean capitalizationForListOrMenu = false;
    private boolean capitalizationForStandAlone = false;
    private boolean capitalizationInfoIsSet = false;
    private transient DecimalFormat decimalFormat = null;
    private transient DecimalFormatSymbols decimalFormatSymbols = null;
    private transient NFRule defaultInfinityRule = null;
    private transient NFRule defaultNaNRule = null;
    private transient NFRuleSet defaultRuleSet = null;
    private boolean lenientParse = false;
    private transient String lenientParseRules;
    private ULocale locale = null;
    private transient boolean lookedForScanner;
    private transient String postProcessRules;
    private transient RBNFPostProcessor postProcessor;
    private String[] publicRuleSetNames;
    private int roundingMode = 7;
    private Map<String, String[]> ruleSetDisplayNames;
    private transient NFRuleSet[] ruleSets = null;
    private transient Map<String, NFRuleSet> ruleSetsMap = null;
    private transient RbnfLenientScannerProvider scannerProvider = null;

    static {
        rulenames = new String[]{"SpelloutRules", "OrdinalRules", "DurationRules", "NumberingSystemRules"};
        locnames = new String[]{"SpelloutLocalizations", "OrdinalLocalizations", "DurationLocalizations", "NumberingSystemLocalizations"};
        MAX_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);
        MIN_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);
    }

    public RuleBasedNumberFormat(int n) {
        this(ULocale.getDefault(ULocale.Category.FORMAT), n);
    }

    public RuleBasedNumberFormat(ULocale arrarrstring, int n) {
        Object object;
        this.locale = arrarrstring;
        String[][] arrarrstring2 = (String[][])UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/rbnf", (ULocale)arrarrstring);
        arrarrstring = arrarrstring2.getULocale();
        this.setLocale((ULocale)arrarrstring, (ULocale)arrarrstring);
        StringBuilder stringBuilder = new StringBuilder();
        arrarrstring = null;
        try {
            object = new StringBuilder();
            ((StringBuilder)object).append("RBNFRules/");
            ((StringBuilder)object).append(rulenames[n - 1]);
            object = arrarrstring2.getWithFallback(((StringBuilder)object).toString()).getIterator();
            while (((UResourceBundleIterator)object).hasNext()) {
                stringBuilder.append(((UResourceBundleIterator)object).nextString());
            }
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        if ((object = arrarrstring2.findTopLevel(locnames[n - 1])) != null) {
            arrarrstring2 = new String[((UResourceBundle)object).getSize()][];
            n = 0;
            do {
                arrarrstring = arrarrstring2;
                if (n >= arrarrstring2.length) break;
                arrarrstring2[n] = ((UResourceBundle)object).get(n).getStringArray();
                ++n;
            } while (true);
        }
        this.init(stringBuilder.toString(), arrarrstring);
    }

    public RuleBasedNumberFormat(String string) {
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.init(string, null);
    }

    public RuleBasedNumberFormat(String string, ULocale uLocale) {
        this.locale = uLocale;
        this.init(string, null);
    }

    public RuleBasedNumberFormat(String string, Locale locale) {
        this(string, ULocale.forLocale(locale));
    }

    public RuleBasedNumberFormat(String string, String[][] arrstring) {
        this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.init(string, arrstring);
    }

    public RuleBasedNumberFormat(String string, String[][] arrstring, ULocale uLocale) {
        this.locale = uLocale;
        this.init(string, arrstring);
    }

    public RuleBasedNumberFormat(Locale locale, int n) {
        this(ULocale.forLocale(locale), n);
    }

    private String adjustForContext(String string) {
        DisplayContext displayContext = this.getContext(DisplayContext.Type.CAPITALIZATION);
        if (displayContext != DisplayContext.CAPITALIZATION_NONE && string != null && string.length() > 0 && UCharacter.isLowerCase(string.codePointAt(0)) && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone)) {
            if (this.capitalizationBrkIter == null) {
                this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
            }
            return UCharacter.toTitleCase(this.locale, string, this.capitalizationBrkIter, 768);
        }
        return string;
    }

    private String extractSpecial(StringBuilder stringBuilder, String string) {
        String string2;
        block5 : {
            int n;
            int n2;
            block6 : {
                String string3 = null;
                n = stringBuilder.indexOf(string);
                string2 = string3;
                if (n == -1) break block5;
                if (n == 0) break block6;
                string2 = string3;
                if (stringBuilder.charAt(n - 1) != ';') break block5;
            }
            int n3 = n2 = stringBuilder.indexOf(";%", n);
            if (n2 == -1) {
                n3 = stringBuilder.length() - 1;
            }
            for (n2 = string.length() + n; n2 < n3 && PatternProps.isWhiteSpace(stringBuilder.charAt(n2)); ++n2) {
            }
            string2 = stringBuilder.substring(n2, n3);
            stringBuilder.delete(n, n3 + 1);
        }
        return string2;
    }

    private String format(double d, NFRuleSet nFRuleSet) {
        StringBuilder stringBuilder = new StringBuilder();
        double d2 = d;
        if (this.getRoundingMode() != 7) {
            d2 = d;
            if (!Double.isNaN(d)) {
                d2 = d;
                if (!Double.isInfinite(d)) {
                    d2 = new BigDecimal(Double.toString(d)).setScale(this.getMaximumFractionDigits(), this.roundingMode).doubleValue();
                }
            }
        }
        nFRuleSet.format(d2, stringBuilder, 0, 0);
        this.postProcess(stringBuilder, nFRuleSet);
        return stringBuilder.toString();
    }

    private String format(long l, NFRuleSet nFRuleSet) {
        StringBuilder stringBuilder = new StringBuilder();
        if (l == Long.MIN_VALUE) {
            stringBuilder.append(this.getDecimalFormat().format(Long.MIN_VALUE));
        } else {
            nFRuleSet.format(l, stringBuilder, 0, 0);
        }
        this.postProcess(stringBuilder, nFRuleSet);
        return stringBuilder.toString();
    }

    /*
     * WARNING - void declaration
     */
    private String[] getNameListForLocale(ULocale object2) {
        if (object2 != null && this.ruleSetDisplayNames != null) {
            String[] arrstring = new String[2];
            String string = ((ULocale)object2).getBaseName();
            arrstring[0] = string;
            arrstring[1] = ULocale.getDefault(ULocale.Category.DISPLAY).getBaseName();
            for (String string2 : arrstring) {
                void var1_5;
                while (var1_5.length() > 0) {
                    String[] arrstring2 = this.ruleSetDisplayNames.get(var1_5);
                    if (arrstring2 != null) {
                        return arrstring2;
                    }
                    String string3 = ULocale.getFallback((String)var1_5);
                }
            }
        }
        return null;
    }

    private void init(String object, String[][] object2) {
        Object object3;
        this.initLocalizations((String[][])object2);
        object2 = this.stripWhitespace((String)object);
        this.lenientParseRules = this.extractSpecial((StringBuilder)object2, "%%lenient-parse:");
        this.postProcessRules = this.extractSpecial((StringBuilder)object2, "%%post-process:");
        int n = 1;
        int n2 = 0;
        while ((n2 = ((StringBuilder)object2).indexOf(";%", n2)) != -1) {
            ++n;
            n2 += 2;
        }
        this.ruleSets = new NFRuleSet[n];
        this.ruleSetsMap = new HashMap<String, NFRuleSet>(n * 2 + 1);
        this.defaultRuleSet = null;
        int n3 = 0;
        object = new String[n];
        int n4 = 0;
        n = n3;
        for (n2 = 0; n2 < ((NFRuleSet[])(object3 = this.ruleSets)).length; ++n2) {
            block16 : {
                NFRuleSet nFRuleSet;
                block17 : {
                    int n5;
                    n3 = n5 = ((StringBuilder)object2).indexOf(";%", n4);
                    if (n5 < 0) {
                        n3 = ((StringBuilder)object2).length() - 1;
                    }
                    object[n2] = ((StringBuilder)object2).substring(n4, n3 + 1);
                    this.ruleSets[n2] = nFRuleSet = new NFRuleSet(this, (String[])object, n2);
                    object3 = nFRuleSet.getName();
                    this.ruleSetsMap.put((String)object3, nFRuleSet);
                    n4 = n++;
                    if (((String)object3).startsWith("%%")) break block16;
                    if (this.defaultRuleSet == null && ((String)object3).equals("%spellout-numbering") || ((String)object3).equals("%digits-ordinal")) break block17;
                    n4 = n;
                    if (!((String)object3).equals("%duration")) break block16;
                }
                this.defaultRuleSet = nFRuleSet;
                n4 = n;
            }
            n = n4;
            n4 = ++n3;
        }
        if (this.defaultRuleSet == null) {
            for (n2 = ((NFRuleSet[])object3).length - 1; n2 >= 0; --n2) {
                if (this.ruleSets[n2].getName().startsWith("%%")) continue;
                this.defaultRuleSet = this.ruleSets[n2];
                break;
            }
        }
        if (this.defaultRuleSet == null) {
            object2 = this.ruleSets;
            this.defaultRuleSet = object2[((Object)object2).length - 1];
        }
        for (n2 = 0; n2 < ((Object)(object2 = this.ruleSets)).length; ++n2) {
            ((NFRuleSet)object2[n2]).parseRules(object[n2]);
        }
        object = new String[n];
        n3 = 0;
        for (n = ((Object)object2).length - 1; n >= 0; --n) {
            n2 = n3;
            if (!this.ruleSets[n].getName().startsWith("%%")) {
                object[n3] = this.ruleSets[n].getName();
                n2 = n3 + 1;
            }
            n3 = n2;
        }
        if (this.publicRuleSetNames != null) {
            block5 : for (n = 0; n < ((Object)(object2 = this.publicRuleSetNames)).length; ++n) {
                object2 = object2[n];
                for (n2 = 0; n2 < ((String[])object).length; ++n2) {
                    if (!((String)object2).equals(object[n2])) continue;
                    continue block5;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("did not find public rule set: ");
                ((StringBuilder)object).append((String)object2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.defaultRuleSet = this.findRuleSet((String)object2[0]);
        } else {
            this.publicRuleSetNames = object;
        }
    }

    private void initCapitalizationContextInfo(ULocale arrn) {
        block5 : {
            arrn = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)arrn);
            arrn = arrn.getWithFallback("contextTransforms/number-spellout").getIntVector();
            if (arrn.length < 2) break block5;
            boolean bl = false;
            boolean bl2 = arrn[0] != 0;
            this.capitalizationForListOrMenu = bl2;
            bl2 = bl;
            if (arrn[1] != 0) {
                bl2 = true;
            }
            try {
                this.capitalizationForStandAlone = bl2;
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
    }

    private void initLocalizations(String[][] object) {
        if (object != null) {
            this.publicRuleSetNames = (String[])object[0].clone();
            HashMap<String, String[]> hashMap = new HashMap<String, String[]>();
            for (int i = 1; i < ((String[][])object).length; ++i) {
                String[] arrstring = object[i];
                String string = arrstring[0];
                String[] arrstring2 = new String[arrstring.length - 1];
                if (arrstring2.length == this.publicRuleSetNames.length) {
                    System.arraycopy(arrstring, 1, arrstring2, 0, arrstring2.length);
                    hashMap.put(string, arrstring2);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("public name length: ");
                ((StringBuilder)object).append(this.publicRuleSetNames.length);
                ((StringBuilder)object).append(" != localized names[");
                ((StringBuilder)object).append(i);
                ((StringBuilder)object).append("] length: ");
                ((StringBuilder)object).append(arrstring2.length);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (!hashMap.isEmpty()) {
                this.ruleSetDisplayNames = hashMap;
            }
        }
    }

    private void postProcess(StringBuilder stringBuilder, NFRuleSet object) {
        String string = this.postProcessRules;
        if (string != null) {
            if (this.postProcessor == null) {
                int n;
                int n2 = n = string.indexOf(";");
                if (n == -1) {
                    n2 = this.postProcessRules.length();
                }
                string = this.postProcessRules.substring(0, n2).trim();
                try {
                    this.postProcessor = (RBNFPostProcessor)Class.forName(string).newInstance();
                    this.postProcessor.init(this, this.postProcessRules);
                }
                catch (Exception exception) {
                    if (DEBUG) {
                        object = System.out;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("could not locate ");
                        stringBuilder.append(string);
                        stringBuilder.append(", error ");
                        stringBuilder.append(exception.getClass().getName());
                        stringBuilder.append(", ");
                        stringBuilder.append(exception.getMessage());
                        ((PrintStream)object).println(stringBuilder.toString());
                    }
                    this.postProcessor = null;
                    this.postProcessRules = null;
                    return;
                }
            }
            this.postProcessor.process(stringBuilder, (NFRuleSet)object);
        }
    }

    private void readObject(ObjectInputStream object) throws IOException {
        ULocale uLocale;
        String string = ((ObjectInputStream)object).readUTF();
        try {
            uLocale = (ULocale)((ObjectInputStream)object).readObject();
        }
        catch (Exception exception) {
            uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        try {
            this.roundingMode = ((ObjectInputStream)object).readInt();
        }
        catch (Exception exception) {
            // empty catch block
        }
        object = new RuleBasedNumberFormat(string, uLocale);
        this.ruleSets = ((RuleBasedNumberFormat)object).ruleSets;
        this.ruleSetsMap = ((RuleBasedNumberFormat)object).ruleSetsMap;
        this.defaultRuleSet = ((RuleBasedNumberFormat)object).defaultRuleSet;
        this.publicRuleSetNames = ((RuleBasedNumberFormat)object).publicRuleSetNames;
        this.decimalFormatSymbols = ((RuleBasedNumberFormat)object).decimalFormatSymbols;
        this.decimalFormat = ((RuleBasedNumberFormat)object).decimalFormat;
        this.locale = ((RuleBasedNumberFormat)object).locale;
        this.defaultInfinityRule = ((RuleBasedNumberFormat)object).defaultInfinityRule;
        this.defaultNaNRule = ((RuleBasedNumberFormat)object).defaultNaNRule;
    }

    private StringBuilder stripWhitespace(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        int n2 = 0;
        while (n2 < n) {
            while (n2 < n && PatternProps.isWhiteSpace(string.charAt(n2))) {
                ++n2;
            }
            if (n2 < n && string.charAt(n2) == ';') {
                ++n2;
                continue;
            }
            int n3 = string.indexOf(59, n2);
            if (n3 == -1) {
                stringBuilder.append(string.substring(n2));
                break;
            }
            if (n3 >= n) break;
            stringBuilder.append(string.substring(n2, n3 + 1));
            n2 = n3 + 1;
        }
        return stringBuilder;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this.toString());
        objectOutputStream.writeObject(this.locale);
        objectOutputStream.writeInt(this.roundingMode);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    PluralFormat createPluralFormat(PluralRules.PluralType pluralType, String string) {
        return new PluralFormat(this.locale, pluralType, string, this.getDecimalFormat());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RuleBasedNumberFormat)) {
            return false;
        }
        object = (RuleBasedNumberFormat)object;
        if (this.locale.equals(((RuleBasedNumberFormat)object).locale) && this.lenientParse == ((RuleBasedNumberFormat)object).lenientParse) {
            NFRuleSet[] arrnFRuleSet;
            if (this.ruleSets.length != ((RuleBasedNumberFormat)object).ruleSets.length) {
                return false;
            }
            for (int i = 0; i < (arrnFRuleSet = this.ruleSets).length; ++i) {
                if (arrnFRuleSet[i].equals(((RuleBasedNumberFormat)object).ruleSets[i])) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    NFRuleSet findRuleSet(String string) throws IllegalArgumentException {
        Object object = this.ruleSetsMap.get(string);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No rule set named ");
        ((StringBuilder)object).append(string);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public String format(double d, String string) throws IllegalArgumentException {
        if (!string.startsWith("%%")) {
            return this.adjustForContext(this.format(d, this.findRuleSet(string)));
        }
        throw new IllegalArgumentException("Can't use internal rule set");
    }

    public String format(long l, String string) throws IllegalArgumentException {
        if (!string.startsWith("%%")) {
            return this.adjustForContext(this.format(l, this.findRuleSet(string)));
        }
        throw new IllegalArgumentException("Can't use internal rule set");
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (stringBuffer.length() == 0) {
            stringBuffer.append(this.adjustForContext(this.format(d, this.defaultRuleSet)));
        } else {
            stringBuffer.append(this.format(d, this.defaultRuleSet));
        }
        return stringBuffer;
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (stringBuffer.length() == 0) {
            stringBuffer.append(this.adjustForContext(this.format(l, this.defaultRuleSet)));
        } else {
            stringBuffer.append(this.format(l, this.defaultRuleSet));
        }
        return stringBuffer;
    }

    @Override
    public StringBuffer format(BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (MIN_VALUE.compareTo(bigDecimal) <= 0 && MAX_VALUE.compareTo(bigDecimal) >= 0) {
            if (bigDecimal.scale() == 0) {
                return this.format(bigDecimal.longValue(), stringBuffer, fieldPosition);
            }
            return this.format(bigDecimal.doubleValue(), stringBuffer, fieldPosition);
        }
        return this.getDecimalFormat().format(bigDecimal, stringBuffer, fieldPosition);
    }

    @Override
    public StringBuffer format(java.math.BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(new BigDecimal(bigDecimal), stringBuffer, fieldPosition);
    }

    @Override
    public StringBuffer format(BigInteger bigInteger, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(new BigDecimal(bigInteger), stringBuffer, fieldPosition);
    }

    DecimalFormat getDecimalFormat() {
        if (this.decimalFormat == null) {
            this.decimalFormat = new DecimalFormat(RuleBasedNumberFormat.getPattern(this.locale, 0), this.getDecimalFormatSymbols());
        }
        return this.decimalFormat;
    }

    DecimalFormatSymbols getDecimalFormatSymbols() {
        if (this.decimalFormatSymbols == null) {
            this.decimalFormatSymbols = new DecimalFormatSymbols(this.locale);
        }
        return this.decimalFormatSymbols;
    }

    NFRule getDefaultInfinityRule() {
        if (this.defaultInfinityRule == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Inf: ");
            stringBuilder.append(this.getDecimalFormatSymbols().getInfinity());
            this.defaultInfinityRule = new NFRule(this, stringBuilder.toString());
        }
        return this.defaultInfinityRule;
    }

    NFRule getDefaultNaNRule() {
        if (this.defaultNaNRule == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NaN: ");
            stringBuilder.append(this.getDecimalFormatSymbols().getNaN());
            this.defaultNaNRule = new NFRule(this, stringBuilder.toString());
        }
        return this.defaultNaNRule;
    }

    NFRuleSet getDefaultRuleSet() {
        return this.defaultRuleSet;
    }

    public String getDefaultRuleSetName() {
        NFRuleSet nFRuleSet = this.defaultRuleSet;
        if (nFRuleSet != null && nFRuleSet.isPublic()) {
            return this.defaultRuleSet.getName();
        }
        return "";
    }

    RbnfLenientScanner getLenientScanner() {
        RbnfLenientScannerProvider rbnfLenientScannerProvider;
        if (this.lenientParse && (rbnfLenientScannerProvider = this.getLenientScannerProvider()) != null) {
            return rbnfLenientScannerProvider.get(this.locale, this.lenientParseRules);
        }
        return null;
    }

    public RbnfLenientScannerProvider getLenientScannerProvider() {
        if (this.scannerProvider == null && this.lenientParse && !this.lookedForScanner) {
            try {
                this.lookedForScanner = true;
                this.setLenientScannerProvider((RbnfLenientScannerProvider)Class.forName("android.icu.impl.text.RbnfScannerProviderImpl").newInstance());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return this.scannerProvider;
    }

    @Override
    public int getRoundingMode() {
        return this.roundingMode;
    }

    public String getRuleSetDisplayName(String string) {
        return this.getRuleSetDisplayName(string, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getRuleSetDisplayName(String arrstring, ULocale serializable) {
        String[] arrstring2 = this.publicRuleSetNames;
        for (int i = 0; i < arrstring2.length; ++i) {
            if (!arrstring2[i].equals(arrstring)) continue;
            arrstring = this.getNameListForLocale((ULocale)serializable);
            if (arrstring != null) {
                return arrstring[i];
            }
            return arrstring2[i].substring(1);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("unrecognized rule set name: ");
        ((StringBuilder)serializable).append((String)arrstring);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public ULocale[] getRuleSetDisplayNameLocales() {
        Object object = this.ruleSetDisplayNames;
        if (object != null) {
            object = object.keySet();
            object = object.toArray(new String[object.size()]);
            Arrays.sort(object, String.CASE_INSENSITIVE_ORDER);
            ULocale[] arruLocale = new ULocale[((String[])object).length];
            for (int i = 0; i < ((String[])object).length; ++i) {
                arruLocale[i] = new ULocale(object[i]);
            }
            return arruLocale;
        }
        return null;
    }

    public String[] getRuleSetDisplayNames() {
        return this.getRuleSetDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String[] getRuleSetDisplayNames(ULocale arrstring) {
        if ((arrstring = this.getNameListForLocale((ULocale)arrstring)) != null) {
            return (String[])arrstring.clone();
        }
        arrstring = this.getRuleSetNames();
        for (int i = 0; i < arrstring.length; ++i) {
            arrstring[i] = arrstring[i].substring(1);
        }
        return arrstring;
    }

    public String[] getRuleSetNames() {
        return (String[])this.publicRuleSetNames.clone();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean lenientParseEnabled() {
        return this.lenientParse;
    }

    @Override
    public Number parse(String object, ParsePosition parsePosition) {
        Object object2;
        String string = ((String)object).substring(parsePosition.getIndex());
        ParsePosition parsePosition2 = new ParsePosition(0);
        object = NFRule.ZERO;
        ParsePosition parsePosition3 = new ParsePosition(parsePosition2.getIndex());
        int n = this.ruleSets.length;
        --n;
        do {
            object2 = object;
            if (n < 0) break;
            object2 = object;
            if (this.ruleSets[n].isPublic()) {
                if (!this.ruleSets[n].isParseable()) {
                    object2 = object;
                } else {
                    object2 = this.ruleSets[n].parse(string, parsePosition2, Double.MAX_VALUE, 0);
                    if (parsePosition2.getIndex() > parsePosition3.getIndex()) {
                        object = object2;
                        parsePosition3.setIndex(parsePosition2.getIndex());
                    }
                    if (parsePosition3.getIndex() == string.length()) {
                        object2 = object;
                        break;
                    }
                    parsePosition2.setIndex(0);
                    object2 = object;
                }
            }
            --n;
            object = object2;
        } while (true);
        parsePosition.setIndex(parsePosition.getIndex() + parsePosition3.getIndex());
        return object2;
    }

    @Override
    public void setContext(DisplayContext displayContext) {
        super.setContext(displayContext);
        if (!(this.capitalizationInfoIsSet || displayContext != DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && displayContext != DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            this.initCapitalizationContextInfo(this.locale);
            this.capitalizationInfoIsSet = true;
        }
        if (this.capitalizationBrkIter == null && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone)) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
        }
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols cloneable) {
        if (cloneable != null) {
            this.decimalFormatSymbols = (DecimalFormatSymbols)((DecimalFormatSymbols)cloneable).clone();
            cloneable = this.decimalFormat;
            if (cloneable != null) {
                ((DecimalFormat)cloneable).setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
            if (this.defaultInfinityRule != null) {
                this.defaultInfinityRule = null;
                this.getDefaultInfinityRule();
            }
            if (this.defaultNaNRule != null) {
                this.defaultNaNRule = null;
                this.getDefaultNaNRule();
            }
            cloneable = this.ruleSets;
            int n = ((Cloneable)cloneable).length;
            for (int i = 0; i < n; ++i) {
                ((NFRuleSet)((Object)cloneable[i])).setDecimalFormatSymbols(this.decimalFormatSymbols);
            }
        }
    }

    public void setDefaultRuleSet(String object) {
        block10 : {
            block8 : {
                block9 : {
                    if (object != null) break block9;
                    object = this.publicRuleSetNames;
                    if (((String[])object).length > 0) {
                        this.defaultRuleSet = this.findRuleSet(object[0]);
                    } else {
                        int n;
                        this.defaultRuleSet = null;
                        int n2 = this.ruleSets.length;
                        while (--n2 >= 0) {
                            object = this.ruleSets[n2].getName();
                            if (!((String)object).equals("%spellout-numbering") && !((String)object).equals("%digits-ordinal") && !((String)object).equals("%duration")) continue;
                            this.defaultRuleSet = this.ruleSets[n2];
                            return;
                        }
                        n2 = this.ruleSets.length;
                        while ((n = n2 - 1) >= 0) {
                            n2 = n;
                            if (!this.ruleSets[n].isPublic()) continue;
                            this.defaultRuleSet = this.ruleSets[n];
                            break block8;
                        }
                    }
                    break block8;
                }
                if (((String)object).startsWith("%%")) break block10;
                this.defaultRuleSet = this.findRuleSet((String)object);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cannot use private rule set: ");
        stringBuilder.append((String)object);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setLenientParseMode(boolean bl) {
        this.lenientParse = bl;
    }

    public void setLenientScannerProvider(RbnfLenientScannerProvider rbnfLenientScannerProvider) {
        this.scannerProvider = rbnfLenientScannerProvider;
    }

    @Override
    public void setRoundingMode(int n) {
        if (n >= 0 && n <= 7) {
            this.roundingMode = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid rounding mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        NFRuleSet[] arrnFRuleSet = this.ruleSets;
        int n = arrnFRuleSet.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrnFRuleSet[i].toString());
        }
        return stringBuilder.toString();
    }
}


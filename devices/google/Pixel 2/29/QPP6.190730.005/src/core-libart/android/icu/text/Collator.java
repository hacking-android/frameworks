/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUDebug;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.UResource;
import android.icu.impl.coll.CollationRoot;
import android.icu.lang.UCharacter;
import android.icu.text.CollationKey;
import android.icu.text.RawCollationKey;
import android.icu.text.RuleBasedCollator;
import android.icu.text.UnicodeSet;
import android.icu.util.Freezable;
import android.icu.util.ICUException;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.VersionInfo;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

public abstract class Collator
implements Comparator<Object>,
Freezable<Collator>,
Cloneable {
    private static final String BASE = "android/icu/impl/data/icudt63b/coll";
    public static final int CANONICAL_DECOMPOSITION = 17;
    private static final boolean DEBUG;
    public static final int FULL_DECOMPOSITION = 15;
    public static final int IDENTICAL = 15;
    private static final String[] KEYWORDS;
    public static final int NO_DECOMPOSITION = 16;
    public static final int PRIMARY = 0;
    public static final int QUATERNARY = 3;
    private static final String RESOURCE = "collations";
    public static final int SECONDARY = 1;
    public static final int TERTIARY = 2;
    private static ServiceShim shim;

    static {
        KEYWORDS = new String[]{"collation"};
        DEBUG = ICUDebug.enabled("collator");
    }

    protected Collator() {
    }

    private void checkNotFrozen() {
        if (!this.isFrozen()) {
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify frozen Collator");
    }

    public static Locale[] getAvailableLocales() {
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return ICUResourceBundle.getAvailableLocales(BASE, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        return serviceShim.getAvailableLocales();
    }

    public static final ULocale[] getAvailableULocales() {
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return ICUResourceBundle.getAvailableULocales(BASE, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        return serviceShim.getAvailableULocales();
    }

    public static String getDisplayName(ULocale uLocale) {
        return Collator.getShim().getDisplayName(uLocale, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public static String getDisplayName(ULocale uLocale, ULocale uLocale2) {
        return Collator.getShim().getDisplayName(uLocale, uLocale2);
    }

    public static String getDisplayName(Locale locale) {
        return Collator.getShim().getDisplayName(ULocale.forLocale(locale), ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public static String getDisplayName(Locale locale, Locale locale2) {
        return Collator.getShim().getDisplayName(ULocale.forLocale(locale), ULocale.forLocale(locale2));
    }

    public static int[] getEquivalentReorderCodes(int n) {
        return CollationRoot.getData().getEquivalentScripts(n);
    }

    public static final ULocale getFunctionalEquivalent(String string, ULocale uLocale) {
        return Collator.getFunctionalEquivalent(string, uLocale, null);
    }

    public static final ULocale getFunctionalEquivalent(String string, ULocale uLocale, boolean[] arrbl) {
        return ICUResourceBundle.getFunctionalEquivalent(BASE, ICUResourceBundle.ICU_DATA_CLASS_LOADER, RESOURCE, string, uLocale, arrbl, true);
    }

    public static final Collator getInstance() {
        return Collator.getInstance(ULocale.getDefault());
    }

    public static final Collator getInstance(ULocale object) {
        ULocale uLocale = object;
        if (object == null) {
            uLocale = ULocale.getDefault();
        }
        Collator collator = Collator.getShim().getInstance(uLocale);
        if (!uLocale.getName().equals(uLocale.getBaseName())) {
            object = collator instanceof RuleBasedCollator ? (RuleBasedCollator)collator : null;
            Collator.setAttributesFromKeywords(uLocale, collator, (RuleBasedCollator)object);
        }
        return collator;
    }

    public static final Collator getInstance(Locale locale) {
        return Collator.getInstance(ULocale.forLocale(locale));
    }

    private static final int getIntValue(String string, String string2, String ... object) {
        for (int i = 0; i < ((String[])object).length; ++i) {
            if (!ASCII.equalIgnoreCase(string2, object[i])) continue;
            return i;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("illegal locale keyword=value: ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("=");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static final String[] getKeywordValues(String string) {
        if (string.equals(KEYWORDS[0])) {
            return ICUResourceBundle.getKeywordValues(BASE, RESOURCE);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid keyword: ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static final String[] getKeywordValuesForLocale(String object, ULocale object2, boolean bl) {
        object = (ICUResourceBundle)UResourceBundle.getBundleInstance(BASE, (ULocale)object2);
        object2 = new KeywordsSink();
        ((ICUResourceBundle)object).getAllItemsWithFallback(RESOURCE, (UResource.Sink)object2);
        return ((KeywordsSink)object2).values.toArray(new String[((KeywordsSink)object2).values.size()]);
    }

    public static final String[] getKeywords() {
        return KEYWORDS;
    }

    private static final int getReorderCode(String string, String string2) {
        return Collator.getIntValue(string, string2, "space", "punct", "symbol", "currency", "digit") + 4096;
    }

    private static ServiceShim getShim() {
        if (shim == null) {
            try {
                shim = (ServiceShim)Class.forName("android.icu.text.CollatorServiceShim").newInstance();
            }
            catch (Exception exception) {
                if (DEBUG) {
                    exception.printStackTrace();
                }
                throw new ICUException(exception);
            }
            catch (MissingResourceException missingResourceException) {
                throw missingResourceException;
            }
        }
        return shim;
    }

    private static final boolean getYesOrNo(String string, String string2) {
        if (ASCII.equalIgnoreCase(string2, "yes")) {
            return true;
        }
        if (ASCII.equalIgnoreCase(string2, "no")) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("illegal locale keyword=value: ");
        stringBuilder.append(string);
        stringBuilder.append("=");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static final Object registerFactory(CollatorFactory collatorFactory) {
        return Collator.getShim().registerFactory(collatorFactory);
    }

    public static final Object registerInstance(Collator collator, ULocale uLocale) {
        return Collator.getShim().registerInstance(collator, uLocale);
    }

    private static void setAttributesFromKeywords(ULocale object, Collator collator, RuleBasedCollator arrn) {
        if (((ULocale)object).getKeywordValue("colHiraganaQuaternary") == null) {
            if (((ULocale)object).getKeywordValue("variableTop") == null) {
                block31 : {
                    int n;
                    int[] arrn2 = ((ULocale)object).getKeywordValue("colStrength");
                    if (arrn2 != null) {
                        n = Collator.getIntValue("colStrength", (String)arrn2, "primary", "secondary", "tertiary", "quaternary", "identical");
                        if (n > 3) {
                            n = 15;
                        }
                        collator.setStrength(n);
                    }
                    if ((arrn2 = ((ULocale)object).getKeywordValue("colBackwards")) != null) {
                        if (arrn != null) {
                            arrn.setFrenchCollation(Collator.getYesOrNo("colBackwards", (String)arrn2));
                        } else {
                            throw new UnsupportedOperationException("locale keyword kb/colBackwards only settable for RuleBasedCollator");
                        }
                    }
                    if ((arrn2 = ((ULocale)object).getKeywordValue("colCaseLevel")) != null) {
                        if (arrn != null) {
                            arrn.setCaseLevel(Collator.getYesOrNo("colCaseLevel", (String)arrn2));
                        } else {
                            throw new UnsupportedOperationException("locale keyword kb/colBackwards only settable for RuleBasedCollator");
                        }
                    }
                    arrn2 = ((ULocale)object).getKeywordValue("colCaseFirst");
                    boolean bl = true;
                    if (arrn2 != null) {
                        if (arrn != null) {
                            n = Collator.getIntValue("colCaseFirst", (String)arrn2, "no", "lower", "upper");
                            if (n == 0) {
                                arrn.setLowerCaseFirst(false);
                                arrn.setUpperCaseFirst(false);
                            } else if (n == 1) {
                                arrn.setLowerCaseFirst(true);
                            } else {
                                arrn.setUpperCaseFirst(true);
                            }
                        } else {
                            throw new UnsupportedOperationException("locale keyword kf/colCaseFirst only settable for RuleBasedCollator");
                        }
                    }
                    if ((arrn2 = ((ULocale)object).getKeywordValue("colAlternate")) != null) {
                        if (arrn != null) {
                            if (Collator.getIntValue("colAlternate", (String)arrn2, "non-ignorable", "shifted") == 0) {
                                bl = false;
                            }
                            arrn.setAlternateHandlingShifted(bl);
                        } else {
                            throw new UnsupportedOperationException("locale keyword ka/colAlternate only settable for RuleBasedCollator");
                        }
                    }
                    if ((arrn2 = ((ULocale)object).getKeywordValue("colNormalization")) != null) {
                        n = Collator.getYesOrNo("colNormalization", (String)arrn2) ? 17 : 16;
                        collator.setDecomposition(n);
                    }
                    if ((arrn2 = ((ULocale)object).getKeywordValue("colNumeric")) != null) {
                        if (arrn != null) {
                            arrn.setNumericCollation(Collator.getYesOrNo("colNumeric", (String)arrn2));
                        } else {
                            throw new UnsupportedOperationException("locale keyword kn/colNumeric only settable for RuleBasedCollator");
                        }
                    }
                    if ((arrn = ((ULocale)object).getKeywordValue("colReorder")) != null) {
                        arrn2 = new int[190];
                        int n2 = 0;
                        n = 0;
                        while (n2 != arrn2.length) {
                            int n3;
                            for (n3 = n; n3 < arrn.length() && arrn.charAt(n3) != '-'; ++n3) {
                            }
                            String string = arrn.substring(n, n3);
                            n = string.length() == 4 ? UCharacter.getPropertyValueEnum(4106, string) : Collator.getReorderCode("colReorder", string);
                            int n4 = n2 + 1;
                            arrn2[n2] = n;
                            if (n3 == arrn.length()) {
                                if (n4 != 0) {
                                    arrn = new int[n4];
                                    System.arraycopy(arrn2, 0, arrn, 0, n4);
                                    collator.setReorderCodes(arrn);
                                    break block31;
                                }
                                throw new IllegalArgumentException("no script codes for colReorder locale keyword");
                            }
                            n = n3 + 1;
                            n2 = n4;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("too many script codes for colReorder locale keyword: ");
                        ((StringBuilder)object).append((String)arrn);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                }
                if ((object = ((ULocale)object).getKeywordValue("kv")) != null) {
                    collator.setMaxVariable(Collator.getReorderCode("kv", (String)object));
                }
                return;
            }
            throw new UnsupportedOperationException("locale keyword vt/variableTop");
        }
        throw new UnsupportedOperationException("locale keyword kh/colHiraganaQuaternary");
    }

    public static final boolean unregister(Object object) {
        ServiceShim serviceShim = shim;
        if (serviceShim == null) {
            return false;
        }
        return serviceShim.unregister(object);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Collator cloneAsThawed() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.doCompare((CharSequence)object, (CharSequence)object2);
    }

    @Override
    public abstract int compare(String var1, String var2);

    @Deprecated
    protected int doCompare(CharSequence charSequence, CharSequence charSequence2) {
        return this.compare(charSequence.toString(), charSequence2.toString());
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = this == object || object != null && this.getClass() == object.getClass();
        return bl;
    }

    public boolean equals(String string, String string2) {
        boolean bl = this.compare(string, string2) == 0;
        return bl;
    }

    @Override
    public Collator freeze() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public abstract CollationKey getCollationKey(String var1);

    public int getDecomposition() {
        return 16;
    }

    public ULocale getLocale(ULocale.Type type) {
        return ULocale.ROOT;
    }

    public int getMaxVariable() {
        return 4097;
    }

    public abstract RawCollationKey getRawCollationKey(String var1, RawCollationKey var2);

    public int[] getReorderCodes() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public int getStrength() {
        return 2;
    }

    public UnicodeSet getTailoredSet() {
        return new UnicodeSet(0, 1114111);
    }

    public abstract VersionInfo getUCAVersion();

    public abstract int getVariableTop();

    public abstract VersionInfo getVersion();

    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isFrozen() {
        return false;
    }

    public void setDecomposition(int n) {
        this.checkNotFrozen();
    }

    void setLocale(ULocale uLocale, ULocale uLocale2) {
    }

    public Collator setMaxVariable(int n) {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public void setReorderCodes(int ... arrn) {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public void setStrength(int n) {
        this.checkNotFrozen();
    }

    @Deprecated
    public Collator setStrength2(int n) {
        this.setStrength(n);
        return this;
    }

    @Deprecated
    public abstract int setVariableTop(String var1);

    @Deprecated
    public abstract void setVariableTop(int var1);

    private static final class ASCII {
        private ASCII() {
        }

        static boolean equalIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
            int n = charSequence.length();
            if (n != charSequence2.length()) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                char c;
                char c2 = charSequence.charAt(i);
                if (c2 == (c = charSequence2.charAt(i)) || ('A' <= c2 && c2 <= 'Z' ? c2 + 32 == c : 'A' <= c && c <= 'Z' && c + 32 == c2)) {
                    continue;
                }
                return false;
            }
            return true;
        }
    }

    public static abstract class CollatorFactory {
        protected CollatorFactory() {
        }

        public Collator createCollator(ULocale uLocale) {
            return this.createCollator(uLocale.toLocale());
        }

        public Collator createCollator(Locale locale) {
            return this.createCollator(ULocale.forLocale(locale));
        }

        public String getDisplayName(ULocale uLocale, ULocale uLocale2) {
            if (this.visible() && this.getSupportedLocaleIDs().contains(uLocale.getBaseName())) {
                return uLocale.getDisplayName(uLocale2);
            }
            return null;
        }

        public String getDisplayName(Locale locale, Locale locale2) {
            return this.getDisplayName(ULocale.forLocale(locale), ULocale.forLocale(locale2));
        }

        public abstract Set<String> getSupportedLocaleIDs();

        public boolean visible() {
            return true;
        }
    }

    private static final class KeywordsSink
    extends UResource.Sink {
        boolean hasDefault = false;
        LinkedList<String> values = new LinkedList();

        private KeywordsSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string;
                int n2 = value.getType();
                if (n2 == 0) {
                    if (!this.hasDefault && key.contentEquals("default") && !(string = value.getString()).isEmpty()) {
                        this.values.remove(string);
                        this.values.addFirst(string);
                        this.hasDefault = true;
                    }
                } else if (n2 == 2 && !key.startsWith("private-") && !this.values.contains(string = key.toString())) {
                    this.values.add(string);
                }
                ++n;
            }
        }
    }

    public static interface ReorderCodes {
        public static final int CURRENCY = 4099;
        public static final int DEFAULT = -1;
        public static final int DIGIT = 4100;
        public static final int FIRST = 4096;
        @Deprecated
        public static final int LIMIT = 4101;
        public static final int NONE = 103;
        public static final int OTHERS = 103;
        public static final int PUNCTUATION = 4097;
        public static final int SPACE = 4096;
        public static final int SYMBOL = 4098;
    }

    static abstract class ServiceShim {
        ServiceShim() {
        }

        abstract Locale[] getAvailableLocales();

        abstract ULocale[] getAvailableULocales();

        abstract String getDisplayName(ULocale var1, ULocale var2);

        abstract Collator getInstance(ULocale var1);

        abstract Object registerFactory(CollatorFactory var1);

        abstract Object registerInstance(Collator var1, ULocale var2);

        abstract boolean unregister(Object var1);
    }

}


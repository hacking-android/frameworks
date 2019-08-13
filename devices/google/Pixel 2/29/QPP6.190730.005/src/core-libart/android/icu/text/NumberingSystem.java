/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CacheBase;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SoftCache;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;

public class NumberingSystem {
    public static final NumberingSystem LATIN;
    private static final String[] OTHER_NS_KEYWORDS;
    private static CacheBase<String, NumberingSystem, LocaleLookupData> cachedLocaleData;
    private static CacheBase<String, NumberingSystem, Void> cachedStringData;
    private boolean algorithmic = false;
    private String desc = "0123456789";
    private String name = "latn";
    private int radix = 10;

    static {
        OTHER_NS_KEYWORDS = new String[]{"native", "traditional", "finance"};
        LATIN = NumberingSystem.lookupInstanceByName("latn");
        cachedLocaleData = new SoftCache<String, NumberingSystem, LocaleLookupData>(){

            @Override
            protected NumberingSystem createInstance(String string, LocaleLookupData localeLookupData) {
                return NumberingSystem.lookupInstanceByLocale(localeLookupData);
            }
        };
        cachedStringData = new SoftCache<String, NumberingSystem, Void>(){

            @Override
            protected NumberingSystem createInstance(String string, Void void_) {
                return NumberingSystem.lookupInstanceByName(string);
            }
        };
    }

    public static String[] getAvailableNames() {
        Object object = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "numberingSystems").get("numberingSystems");
        ArrayList<String> arrayList = new ArrayList<String>();
        object = ((UResourceBundle)object).getIterator();
        while (((UResourceBundleIterator)object).hasNext()) {
            arrayList.add(((UResourceBundleIterator)object).next().getKey());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static NumberingSystem getInstance() {
        return NumberingSystem.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static NumberingSystem getInstance(int n, boolean bl, String string) {
        return NumberingSystem.getInstance(null, n, bl, string);
    }

    public static NumberingSystem getInstance(ULocale object) {
        boolean bl;
        Object object2;
        Object object3;
        block5 : {
            boolean bl2 = true;
            object2 = ((ULocale)object).getKeywordValue("numbers");
            if (object2 != null) {
                object3 = OTHER_NS_KEYWORDS;
                int n = ((String[])object3).length;
                int n2 = 0;
                do {
                    bl = bl2;
                    if (n2 >= n) break block5;
                    if (((String)object2).equals(object3[n2])) {
                        bl = false;
                        break block5;
                    }
                    ++n2;
                } while (true);
            }
            object2 = "default";
            bl = false;
        }
        object3 = object2;
        if (bl) {
            if ((object2 = NumberingSystem.getInstanceByName((String)object2)) != null) {
                return object2;
            }
            object3 = "default";
        }
        object2 = ((ULocale)object).getBaseName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object2);
        stringBuilder.append("@numbers=");
        stringBuilder.append((String)object3);
        object2 = stringBuilder.toString();
        object = new LocaleLookupData((ULocale)object, (String)object3);
        return cachedLocaleData.getInstance((String)object2, (LocaleLookupData)object);
    }

    private static NumberingSystem getInstance(String string, int n, boolean bl, String string2) {
        if (n >= 2) {
            if (!(bl || string2.codePointCount(0, string2.length()) == n && NumberingSystem.isValidDigitString(string2))) {
                throw new IllegalArgumentException("Invalid digit string for numbering system");
            }
            NumberingSystem numberingSystem = new NumberingSystem();
            numberingSystem.radix = n;
            numberingSystem.algorithmic = bl;
            numberingSystem.desc = string2;
            numberingSystem.name = string;
            return numberingSystem;
        }
        throw new IllegalArgumentException("Invalid radix for numbering system");
    }

    public static NumberingSystem getInstance(Locale locale) {
        return NumberingSystem.getInstance(ULocale.forLocale(locale));
    }

    public static NumberingSystem getInstanceByName(String string) {
        return cachedStringData.getInstance(string, null);
    }

    public static boolean isValidDigitString(String string) {
        int n = string.length();
        boolean bl = false;
        if (string.codePointCount(0, n) == 10) {
            bl = true;
        }
        return bl;
    }

    static NumberingSystem lookupInstanceByLocale(LocaleLookupData object) {
        ICUResourceBundle iCUResourceBundle;
        Object object2 = ((LocaleLookupData)object).locale;
        try {
            iCUResourceBundle = ((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object2)).getWithFallback("NumberElements");
            object = ((LocaleLookupData)object).numbersKeyword;
            object2 = null;
        }
        catch (MissingResourceException missingResourceException) {
            return new NumberingSystem();
        }
        do {
            block7 : {
                block8 : {
                    try {
                        String string = iCUResourceBundle.getStringWithFallback((String)object);
                        object2 = string;
                    }
                    catch (MissingResourceException missingResourceException) {
                        if (((String)object).equals("native") || ((String)object).equals("finance")) break block7;
                        if (!((String)object).equals("traditional")) break block8;
                        object = "native";
                        continue;
                    }
                }
                object = null;
                if (object2 != null) {
                    object = NumberingSystem.getInstanceByName((String)object2);
                }
                object2 = object;
                if (object == null) {
                    object2 = new NumberingSystem();
                }
                return object2;
            }
            object = "default";
        } while (true);
    }

    private static NumberingSystem lookupInstanceByName(String string) {
        boolean bl;
        int n;
        String string2;
        block2 : {
            try {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "numberingSystems").get("numberingSystems").get(string);
                string2 = uResourceBundle.getString("desc");
                UResourceBundle uResourceBundle2 = uResourceBundle.get("radix");
                uResourceBundle = uResourceBundle.get("algorithmic");
                n = uResourceBundle2.getInt();
                int n2 = uResourceBundle.getInt();
                bl = true;
                if (n2 == 1) break block2;
                bl = false;
            }
            catch (MissingResourceException missingResourceException) {
                return null;
            }
        }
        return NumberingSystem.getInstance(string, n, bl, string2);
    }

    public String getDescription() {
        return this.desc;
    }

    public String getName() {
        return this.name;
    }

    public int getRadix() {
        return this.radix;
    }

    public boolean isAlgorithmic() {
        return this.algorithmic;
    }

    private static class LocaleLookupData {
        public final ULocale locale;
        public final String numbersKeyword;

        LocaleLookupData(ULocale uLocale, String string) {
            this.locale = uLocale;
            this.numbersKeyword = string;
        }
    }

}


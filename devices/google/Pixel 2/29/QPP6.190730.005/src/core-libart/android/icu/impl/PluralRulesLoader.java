/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.StandardPlural;
import android.icu.text.PluralRanges;
import android.icu.text.PluralRules;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;

public class PluralRulesLoader
extends PluralRules.Factory {
    private static final PluralRanges UNKNOWN_RANGE;
    public static final PluralRulesLoader loader;
    private static Map<String, PluralRanges> localeIdToPluralRanges;
    private Map<String, String> localeIdToCardinalRulesId;
    private Map<String, String> localeIdToOrdinalRulesId;
    private Map<String, ULocale> rulesIdToEquivalentULocale;
    private final Map<String, PluralRules> rulesIdToRules = new HashMap<String, PluralRules>();

    static {
        int n;
        int n2;
        loader = new PluralRulesLoader();
        UNKNOWN_RANGE = new PluralRanges().freeze();
        String[][] arrarrstring = new String[171][];
        int n3 = 0;
        arrarrstring[0] = new String[]{"locales", "id ja km ko lo ms my th vi zh"};
        arrarrstring[1] = new String[]{"other", "other", "other"};
        arrarrstring[2] = new String[]{"locales", "am bn fr gu hi hy kn mr pa zu"};
        arrarrstring[3] = new String[]{"one", "one", "one"};
        arrarrstring[4] = new String[]{"one", "other", "other"};
        arrarrstring[5] = new String[]{"other", "other", "other"};
        arrarrstring[6] = new String[]{"locales", "fa"};
        arrarrstring[7] = new String[]{"one", "one", "other"};
        arrarrstring[8] = new String[]{"one", "other", "other"};
        arrarrstring[9] = new String[]{"other", "other", "other"};
        arrarrstring[10] = new String[]{"locales", "ka"};
        arrarrstring[11] = new String[]{"one", "other", "one"};
        arrarrstring[12] = new String[]{"other", "one", "other"};
        arrarrstring[13] = new String[]{"other", "other", "other"};
        arrarrstring[14] = new String[]{"locales", "az de el gl hu it kk ky ml mn ne nl pt sq sw ta te tr ug uz"};
        arrarrstring[15] = new String[]{"one", "other", "other"};
        arrarrstring[16] = new String[]{"other", "one", "one"};
        arrarrstring[17] = new String[]{"other", "other", "other"};
        arrarrstring[18] = new String[]{"locales", "af bg ca en es et eu fi nb sv ur"};
        arrarrstring[19] = new String[]{"one", "other", "other"};
        arrarrstring[20] = new String[]{"other", "one", "other"};
        arrarrstring[21] = new String[]{"other", "other", "other"};
        arrarrstring[22] = new String[]{"locales", "da fil is"};
        arrarrstring[23] = new String[]{"one", "one", "one"};
        arrarrstring[24] = new String[]{"one", "other", "other"};
        arrarrstring[25] = new String[]{"other", "one", "one"};
        arrarrstring[26] = new String[]{"other", "other", "other"};
        arrarrstring[27] = new String[]{"locales", "si"};
        arrarrstring[28] = new String[]{"one", "one", "one"};
        arrarrstring[29] = new String[]{"one", "other", "other"};
        arrarrstring[30] = new String[]{"other", "one", "other"};
        arrarrstring[31] = new String[]{"other", "other", "other"};
        arrarrstring[32] = new String[]{"locales", "mk"};
        arrarrstring[33] = new String[]{"one", "one", "other"};
        arrarrstring[34] = new String[]{"one", "other", "other"};
        arrarrstring[35] = new String[]{"other", "one", "other"};
        arrarrstring[36] = new String[]{"other", "other", "other"};
        arrarrstring[37] = new String[]{"locales", "lv"};
        arrarrstring[38] = new String[]{"zero", "zero", "other"};
        arrarrstring[39] = new String[]{"zero", "one", "one"};
        arrarrstring[40] = new String[]{"zero", "other", "other"};
        arrarrstring[41] = new String[]{"one", "zero", "other"};
        arrarrstring[42] = new String[]{"one", "one", "one"};
        arrarrstring[43] = new String[]{"one", "other", "other"};
        arrarrstring[44] = new String[]{"other", "zero", "other"};
        arrarrstring[45] = new String[]{"other", "one", "one"};
        arrarrstring[46] = new String[]{"other", "other", "other"};
        arrarrstring[47] = new String[]{"locales", "ro"};
        arrarrstring[48] = new String[]{"one", "few", "few"};
        arrarrstring[49] = new String[]{"one", "other", "other"};
        arrarrstring[50] = new String[]{"few", "one", "few"};
        arrarrstring[51] = new String[]{"few", "few", "few"};
        arrarrstring[52] = new String[]{"few", "other", "other"};
        arrarrstring[53] = new String[]{"other", "few", "few"};
        arrarrstring[54] = new String[]{"other", "other", "other"};
        arrarrstring[55] = new String[]{"locales", "hr sr bs"};
        arrarrstring[56] = new String[]{"one", "one", "one"};
        arrarrstring[57] = new String[]{"one", "few", "few"};
        arrarrstring[58] = new String[]{"one", "other", "other"};
        arrarrstring[59] = new String[]{"few", "one", "one"};
        arrarrstring[60] = new String[]{"few", "few", "few"};
        arrarrstring[61] = new String[]{"few", "other", "other"};
        arrarrstring[62] = new String[]{"other", "one", "one"};
        arrarrstring[63] = new String[]{"other", "few", "few"};
        arrarrstring[64] = new String[]{"other", "other", "other"};
        arrarrstring[65] = new String[]{"locales", "sl"};
        arrarrstring[66] = new String[]{"one", "one", "few"};
        arrarrstring[67] = new String[]{"one", "two", "two"};
        arrarrstring[68] = new String[]{"one", "few", "few"};
        arrarrstring[69] = new String[]{"one", "other", "other"};
        arrarrstring[70] = new String[]{"two", "one", "few"};
        arrarrstring[71] = new String[]{"two", "two", "two"};
        arrarrstring[72] = new String[]{"two", "few", "few"};
        arrarrstring[73] = new String[]{"two", "other", "other"};
        arrarrstring[74] = new String[]{"few", "one", "few"};
        arrarrstring[75] = new String[]{"few", "two", "two"};
        arrarrstring[76] = new String[]{"few", "few", "few"};
        arrarrstring[77] = new String[]{"few", "other", "other"};
        arrarrstring[78] = new String[]{"other", "one", "few"};
        arrarrstring[79] = new String[]{"other", "two", "two"};
        arrarrstring[80] = new String[]{"other", "few", "few"};
        arrarrstring[81] = new String[]{"other", "other", "other"};
        arrarrstring[82] = new String[]{"locales", "he"};
        arrarrstring[83] = new String[]{"one", "two", "other"};
        arrarrstring[84] = new String[]{"one", "many", "many"};
        arrarrstring[85] = new String[]{"one", "other", "other"};
        arrarrstring[86] = new String[]{"two", "many", "other"};
        arrarrstring[87] = new String[]{"two", "other", "other"};
        arrarrstring[88] = new String[]{"many", "many", "many"};
        arrarrstring[89] = new String[]{"many", "other", "many"};
        arrarrstring[90] = new String[]{"other", "one", "other"};
        arrarrstring[91] = new String[]{"other", "two", "other"};
        arrarrstring[92] = new String[]{"other", "many", "many"};
        arrarrstring[93] = new String[]{"other", "other", "other"};
        arrarrstring[94] = new String[]{"locales", "cs pl sk"};
        arrarrstring[95] = new String[]{"one", "few", "few"};
        arrarrstring[96] = new String[]{"one", "many", "many"};
        arrarrstring[97] = new String[]{"one", "other", "other"};
        arrarrstring[98] = new String[]{"few", "few", "few"};
        arrarrstring[99] = new String[]{"few", "many", "many"};
        arrarrstring[100] = new String[]{"few", "other", "other"};
        arrarrstring[101] = new String[]{"many", "one", "one"};
        arrarrstring[102] = new String[]{"many", "few", "few"};
        arrarrstring[103] = new String[]{"many", "many", "many"};
        arrarrstring[104] = new String[]{"many", "other", "other"};
        arrarrstring[105] = new String[]{"other", "one", "one"};
        arrarrstring[106] = new String[]{"other", "few", "few"};
        arrarrstring[107] = new String[]{"other", "many", "many"};
        arrarrstring[108] = new String[]{"other", "other", "other"};
        arrarrstring[109] = new String[]{"locales", "lt ru uk"};
        arrarrstring[110] = new String[]{"one", "one", "one"};
        arrarrstring[111] = new String[]{"one", "few", "few"};
        arrarrstring[112] = new String[]{"one", "many", "many"};
        arrarrstring[113] = new String[]{"one", "other", "other"};
        arrarrstring[114] = new String[]{"few", "one", "one"};
        arrarrstring[115] = new String[]{"few", "few", "few"};
        arrarrstring[116] = new String[]{"few", "many", "many"};
        arrarrstring[117] = new String[]{"few", "other", "other"};
        arrarrstring[118] = new String[]{"many", "one", "one"};
        arrarrstring[119] = new String[]{"many", "few", "few"};
        arrarrstring[120] = new String[]{"many", "many", "many"};
        arrarrstring[121] = new String[]{"many", "other", "other"};
        arrarrstring[122] = new String[]{"other", "one", "one"};
        arrarrstring[123] = new String[]{"other", "few", "few"};
        arrarrstring[124] = new String[]{"other", "many", "many"};
        arrarrstring[125] = new String[]{"other", "other", "other"};
        arrarrstring[126] = new String[]{"locales", "cy"};
        arrarrstring[127] = new String[]{"zero", "one", "one"};
        arrarrstring[128] = new String[]{"zero", "two", "two"};
        arrarrstring[129] = new String[]{"zero", "few", "few"};
        arrarrstring[130] = new String[]{"zero", "many", "many"};
        arrarrstring[131] = new String[]{"zero", "other", "other"};
        arrarrstring[132] = new String[]{"one", "two", "two"};
        arrarrstring[133] = new String[]{"one", "few", "few"};
        arrarrstring[134] = new String[]{"one", "many", "many"};
        arrarrstring[135] = new String[]{"one", "other", "other"};
        arrarrstring[136] = new String[]{"two", "few", "few"};
        arrarrstring[137] = new String[]{"two", "many", "many"};
        arrarrstring[138] = new String[]{"two", "other", "other"};
        arrarrstring[139] = new String[]{"few", "many", "many"};
        arrarrstring[140] = new String[]{"few", "other", "other"};
        arrarrstring[141] = new String[]{"many", "other", "other"};
        arrarrstring[142] = new String[]{"other", "one", "one"};
        arrarrstring[143] = new String[]{"other", "two", "two"};
        arrarrstring[144] = new String[]{"other", "few", "few"};
        arrarrstring[145] = new String[]{"other", "many", "many"};
        arrarrstring[146] = new String[]{"other", "other", "other"};
        arrarrstring[147] = new String[]{"locales", "ar"};
        arrarrstring[148] = new String[]{"zero", "one", "zero"};
        arrarrstring[149] = new String[]{"zero", "two", "zero"};
        arrarrstring[150] = new String[]{"zero", "few", "few"};
        arrarrstring[151] = new String[]{"zero", "many", "many"};
        arrarrstring[152] = new String[]{"zero", "other", "other"};
        arrarrstring[153] = new String[]{"one", "two", "other"};
        arrarrstring[154] = new String[]{"one", "few", "few"};
        arrarrstring[155] = new String[]{"one", "many", "many"};
        arrarrstring[156] = new String[]{"one", "other", "other"};
        arrarrstring[157] = new String[]{"two", "few", "few"};
        arrarrstring[158] = new String[]{"two", "many", "many"};
        arrarrstring[159] = new String[]{"two", "other", "other"};
        arrarrstring[160] = new String[]{"few", "few", "few"};
        arrarrstring[161] = new String[]{"few", "many", "many"};
        arrarrstring[162] = new String[]{"few", "other", "other"};
        arrarrstring[163] = new String[]{"many", "few", "few"};
        arrarrstring[164] = new String[]{"many", "many", "many"};
        arrarrstring[165] = new String[]{"many", "other", "other"};
        arrarrstring[166] = new String[]{"other", "one", "other"};
        arrarrstring[167] = new String[]{"other", "two", "other"};
        arrarrstring[168] = new String[]{"other", "few", "few"};
        arrarrstring[169] = new String[]{"other", "many", "many"};
        arrarrstring[170] = new String[]{"other", "other", "other"};
        String[] arrstring = null;
        HashMap<Object, PluralRanges> hashMap = new HashMap<Object, PluralRanges>();
        int n4 = arrarrstring.length;
        PluralRanges pluralRanges = null;
        for (n2 = 0; n2 < n4; ++n2) {
            String[] arrstring2 = arrarrstring[n2];
            if (arrstring2[0].equals("locales")) {
                if (pluralRanges != null) {
                    pluralRanges.freeze();
                    int n5 = arrstring.length;
                    for (n = 0; n < n5; ++n) {
                        hashMap.put(arrstring[n], pluralRanges);
                    }
                }
                arrstring = arrstring2[1].split(" ");
                pluralRanges = new PluralRanges();
                continue;
            }
            pluralRanges.add(StandardPlural.fromString(arrstring2[0]), StandardPlural.fromString(arrstring2[1]), StandardPlural.fromString(arrstring2[2]));
        }
        n = arrstring.length;
        for (n2 = n3; n2 < n; ++n2) {
            hashMap.put(arrstring[n2], pluralRanges);
        }
        localeIdToPluralRanges = Collections.unmodifiableMap(hashMap);
    }

    private PluralRulesLoader() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void checkBuildRulesIdMaps() {
        HashMap<String, ULocale> hashMap;
        TreeMap<String, String> treeMap;
        TreeMap<String, String> treeMap2;
        int n;
        synchronized (this) {
            treeMap2 = this.localeIdToCardinalRulesId;
            int n2 = 0;
            n = treeMap2 != null ? 1 : 0;
        }
        if (n != 0) return;
        try {
            UResourceBundle uResourceBundle = this.getPluralBundle();
            UResourceBundle uResourceBundle2 = uResourceBundle.get("locales");
            treeMap = new TreeMap<String, String>();
            hashMap = new HashMap<String, ULocale>();
            for (n = 0; n < uResourceBundle2.getSize(); ++n) {
                Object object = uResourceBundle2.get(n);
                treeMap2 = ((UResourceBundle)object).getKey();
                String string = ((UResourceBundle)object).getString().intern();
                treeMap.put((String)((Object)treeMap2), string);
                if (hashMap.containsKey(string)) continue;
                object = new ULocale((String)((Object)treeMap2));
                hashMap.put(string, (ULocale)object);
            }
            uResourceBundle = uResourceBundle.get("locales_ordinals");
            treeMap2 = new TreeMap<String, String>();
            for (n = n2; n < uResourceBundle.getSize(); ++n) {
                uResourceBundle2 = uResourceBundle.get(n);
                treeMap2.put(uResourceBundle2.getKey(), uResourceBundle2.getString().intern());
            }
        }
        catch (MissingResourceException missingResourceException) {
            treeMap = Collections.emptyMap();
            treeMap2 = Collections.emptyMap();
            hashMap = Collections.emptyMap();
        }
        synchronized (this) {
            if (this.localeIdToCardinalRulesId != null) return;
            this.localeIdToCardinalRulesId = treeMap;
            this.localeIdToOrdinalRulesId = treeMap2;
            this.rulesIdToEquivalentULocale = hashMap;
            return;
        }
    }

    private Map<String, String> getLocaleIdToRulesIdMap(PluralRules.PluralType object) {
        this.checkBuildRulesIdMaps();
        object = object == PluralRules.PluralType.CARDINAL ? this.localeIdToCardinalRulesId : this.localeIdToOrdinalRulesId;
        return object;
    }

    private Map<String, ULocale> getRulesIdToEquivalentULocaleMap() {
        this.checkBuildRulesIdMaps();
        return this.rulesIdToEquivalentULocale;
    }

    @Override
    public PluralRules forLocale(ULocale object, PluralRules.PluralType object2) {
        if ((object = this.getRulesIdForLocale((ULocale)object, (PluralRules.PluralType)((Object)object2))) != null && ((String)object).trim().length() != 0) {
            object2 = this.getRulesForRulesId((String)object);
            object = object2;
            if (object2 == null) {
                object = PluralRules.DEFAULT;
            }
            return object;
        }
        return PluralRules.DEFAULT;
    }

    @Override
    public ULocale[] getAvailableULocales() {
        Object object = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).keySet();
        ULocale[] arruLocale = new ULocale[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arruLocale[n] = ULocale.createCanonical((String)object.next());
            ++n;
        }
        return arruLocale;
    }

    @Override
    public ULocale getFunctionalEquivalent(ULocale object, boolean[] arrbl) {
        if (arrbl != null && arrbl.length > 0) {
            String string = ULocale.canonicalize(((ULocale)object).getBaseName());
            arrbl[0] = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).containsKey(string);
        }
        if ((object = this.getRulesIdForLocale((ULocale)object, PluralRules.PluralType.CARDINAL)) != null && ((String)object).trim().length() != 0) {
            object = this.getRulesIdToEquivalentULocaleMap().get(object);
            if (object == null) {
                return ULocale.ROOT;
            }
            return object;
        }
        return ULocale.ROOT;
    }

    public UResourceBundle getPluralBundle() throws MissingResourceException {
        return ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "plurals", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
    }

    public PluralRanges getPluralRanges(ULocale object) {
        PluralRanges pluralRanges;
        object = ULocale.canonicalize(((ULocale)object).getBaseName());
        do {
            PluralRanges pluralRanges2;
            pluralRanges = pluralRanges2 = localeIdToPluralRanges.get(object);
            if (pluralRanges2 != null) break;
            int n = ((String)object).lastIndexOf("_");
            if (n == -1) {
                pluralRanges = UNKNOWN_RANGE;
                break;
            }
            object = ((String)object).substring(0, n);
        } while (true);
        return pluralRanges;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PluralRules getRulesForRulesId(String string) {
        boolean bl;
        Object object = null;
        Object object2 = this.rulesIdToRules;
        synchronized (object2) {
            bl = this.rulesIdToRules.containsKey(string);
            if (bl) {
                object = this.rulesIdToRules.get(string);
            }
        }
        object2 = object;
        if (bl) return object2;
        try {
            UResourceBundle uResourceBundle = this.getPluralBundle().get("rules").get(string);
            object2 = new StringBuilder();
            for (int i = 0; i < uResourceBundle.getSize(); ++i) {
                UResourceBundle uResourceBundle2 = uResourceBundle.get(i);
                if (i > 0) {
                    ((StringBuilder)object2).append("; ");
                }
                ((StringBuilder)object2).append(uResourceBundle2.getKey());
                ((StringBuilder)object2).append(": ");
                ((StringBuilder)object2).append(uResourceBundle2.getString());
            }
            object = object2 = PluralRules.parseDescription(((StringBuilder)object2).toString());
        }
        catch (MissingResourceException missingResourceException) {
        }
        catch (ParseException parseException) {}
        object2 = this.rulesIdToRules;
        synchronized (object2) {
            if (this.rulesIdToRules.containsKey(string)) {
                object = this.rulesIdToRules.get(string);
            } else {
                this.rulesIdToRules.put(string, (PluralRules)object);
            }
            return object;
        }
    }

    public String getRulesIdForLocale(ULocale object, PluralRules.PluralType object2) {
        String string;
        int n;
        object2 = this.getLocaleIdToRulesIdMap((PluralRules.PluralType)((Object)object2));
        object = ULocale.canonicalize(((ULocale)object).getBaseName());
        while ((string = (String)object2.get(object)) == null && (n = ((String)object).lastIndexOf("_")) != -1) {
            object = ((String)object).substring(0, n);
        }
        return string;
    }

    @Override
    public boolean hasOverride(ULocale uLocale) {
        return false;
    }

    public boolean isPluralRangesAvailable(ULocale uLocale) {
        boolean bl = this.getPluralRanges(uLocale) == UNKNOWN_RANGE;
        return bl;
    }
}


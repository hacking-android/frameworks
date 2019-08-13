/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.ICUCache;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleCache;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

@Deprecated
public class GenderInfo {
    private static Cache genderInfoCache;
    private static GenderInfo neutral;
    private final ListGenderStyle style;

    static {
        neutral = new GenderInfo(ListGenderStyle.NEUTRAL);
        genderInfoCache = new Cache();
    }

    @Deprecated
    public GenderInfo(ListGenderStyle listGenderStyle) {
        this.style = listGenderStyle;
    }

    @Deprecated
    public static GenderInfo getInstance(ULocale uLocale) {
        return genderInfoCache.get(uLocale);
    }

    @Deprecated
    public static GenderInfo getInstance(Locale locale) {
        return GenderInfo.getInstance(ULocale.forLocale(locale));
    }

    @Deprecated
    public Gender getListGender(List<Gender> object) {
        if (object.size() == 0) {
            return Gender.OTHER;
        }
        if (object.size() == 1) {
            return object.get(0);
        }
        int n = 1.$SwitchMap$android$icu$util$GenderInfo$ListGenderStyle[this.style.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return Gender.OTHER;
                }
                object = object.iterator();
                while (object.hasNext()) {
                    if (object.next() == Gender.FEMALE) continue;
                    return Gender.MALE;
                }
                return Gender.FEMALE;
            }
            boolean bl = false;
            n = 0;
            object = object.iterator();
            while (object.hasNext()) {
                Gender gender = object.next();
                int n2 = 1.$SwitchMap$android$icu$util$GenderInfo$Gender[gender.ordinal()];
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) continue;
                        return Gender.OTHER;
                    }
                    if (bl) {
                        return Gender.OTHER;
                    }
                    n = 1;
                    continue;
                }
                if (n != 0) {
                    return Gender.OTHER;
                }
                bl = true;
            }
            object = n != 0 ? Gender.MALE : Gender.FEMALE;
            return object;
        }
        return Gender.OTHER;
    }

    @Deprecated
    public Gender getListGender(Gender ... arrgender) {
        return this.getListGender(Arrays.asList(arrgender));
    }

    private static class Cache {
        private final ICUCache<ULocale, GenderInfo> cache = new SimpleCache<ULocale, GenderInfo>();

        private Cache() {
        }

        private static GenderInfo load(ULocale object) {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "genderList", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true).get("genderList");
            try {
                object = new GenderInfo(ListGenderStyle.fromName(uResourceBundle.getString(((ULocale)object).toString())));
                return object;
            }
            catch (MissingResourceException missingResourceException) {
                return null;
            }
        }

        public GenderInfo get(ULocale uLocale) {
            GenderInfo genderInfo = this.cache.get(uLocale);
            Object object = genderInfo;
            if (genderInfo == null) {
                genderInfo = Cache.load(uLocale);
                object = genderInfo;
                if (genderInfo == null) {
                    object = uLocale.getFallback();
                    object = object == null ? neutral : this.get((ULocale)object);
                }
                this.cache.put(uLocale, (GenderInfo)object);
            }
            return object;
        }
    }

    @Deprecated
    public static enum Gender {
        MALE,
        FEMALE,
        OTHER;
        
    }

    @Deprecated
    public static enum ListGenderStyle {
        NEUTRAL,
        MIXED_NEUTRAL,
        MALE_TAINTS;
        
        private static Map<String, ListGenderStyle> fromNameMap;

        static {
            fromNameMap = new HashMap<String, ListGenderStyle>(3);
            fromNameMap.put("neutral", NEUTRAL);
            fromNameMap.put("maleTaints", MALE_TAINTS);
            fromNameMap.put("mixedNeutral", MIXED_NEUTRAL);
        }

        @Deprecated
        public static ListGenderStyle fromName(String string) {
            Object object = fromNameMap.get(string);
            if (object != null) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown gender style name: ");
            ((StringBuilder)object).append(string);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

}


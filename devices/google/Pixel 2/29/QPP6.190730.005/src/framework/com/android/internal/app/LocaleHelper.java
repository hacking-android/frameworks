/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.ListFormatter
 *  android.icu.util.ULocale
 *  libcore.icu.ICU
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.icu.text.ListFormatter;
import android.icu.util.ULocale;
import android.os.LocaleList;
import android.text.TextUtils;
import com.android.internal.app.LocaleStore;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import libcore.icu.ICU;

public class LocaleHelper {
    public static Locale addLikelySubtags(Locale locale) {
        return ICU.addLikelySubtags((Locale)locale);
    }

    public static String getDisplayCountry(Locale locale) {
        return ULocale.getDisplayCountry((String)locale.toLanguageTag(), (ULocale)ULocale.getDefault());
    }

    @UnsupportedAppUsage
    public static String getDisplayCountry(Locale locale, Locale object) {
        String string2 = locale.toLanguageTag();
        ULocale uLocale = ULocale.forLocale((Locale)object);
        object = ULocale.getDisplayCountry((String)string2, (ULocale)uLocale);
        if (locale.getUnicodeLocaleType("nu") != null) {
            return String.format("%s (%s)", object, ULocale.getDisplayKeywordValue((String)string2, (String)"numbers", (ULocale)uLocale));
        }
        return object;
    }

    public static String getDisplayLocaleList(LocaleList localeList, Locale locale, int n) {
        int n2;
        boolean bl;
        int n3;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (bl = localeList.size() > n) {
            n3 = n;
            n2 = n + 1;
        } else {
            n3 = n2 = localeList.size();
        }
        String[] arrstring = new String[n2];
        for (n2 = 0; n2 < n3; ++n2) {
            arrstring[n2] = LocaleHelper.getDisplayName(localeList.get(n2), locale, false);
        }
        if (bl) {
            arrstring[n] = TextUtils.getEllipsisString(TextUtils.TruncateAt.END);
        }
        return ListFormatter.getInstance((Locale)locale).format((Object[])arrstring);
    }

    @UnsupportedAppUsage
    public static String getDisplayName(Locale object, Locale locale, boolean bl) {
        block0 : {
            ULocale uLocale = ULocale.forLocale((Locale)locale);
            object = LocaleHelper.shouldUseDialectName((Locale)object) ? ULocale.getDisplayNameWithDialect((String)((Locale)object).toLanguageTag(), (ULocale)uLocale) : ULocale.getDisplayName((String)((Locale)object).toLanguageTag(), (ULocale)uLocale);
            if (!bl) break block0;
            object = LocaleHelper.toSentenceCase((String)object, locale);
        }
        return object;
    }

    public static String getDisplayName(Locale locale, boolean bl) {
        return LocaleHelper.getDisplayName(locale, Locale.getDefault(), bl);
    }

    @UnsupportedAppUsage
    public static String normalizeForSearch(String string2, Locale locale) {
        return string2.toUpperCase();
    }

    private static boolean shouldUseDialectName(Locale object) {
        boolean bl = "fa".equals(object = ((Locale)object).getLanguage()) || "ro".equals(object) || "zh".equals(object);
        return bl;
    }

    public static String toSentenceCase(String string2, Locale locale) {
        if (string2.isEmpty()) {
            return string2;
        }
        int n = string2.offsetByCodePoints(0, 1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2.substring(0, n).toUpperCase(locale));
        stringBuilder.append(string2.substring(n));
        return stringBuilder.toString();
    }

    public static final class LocaleInfoComparator
    implements Comparator<LocaleStore.LocaleInfo> {
        private static final String PREFIX_ARABIC = "\u0627\u0644";
        private final Collator mCollator;
        private final boolean mCountryMode;

        @UnsupportedAppUsage
        public LocaleInfoComparator(Locale locale, boolean bl) {
            this.mCollator = Collator.getInstance(locale);
            this.mCountryMode = bl;
        }

        private String removePrefixForCompare(Locale locale, String string2) {
            if ("ar".equals(locale.getLanguage()) && string2.startsWith(PREFIX_ARABIC)) {
                return string2.substring(PREFIX_ARABIC.length());
            }
            return string2;
        }

        @UnsupportedAppUsage
        @Override
        public int compare(LocaleStore.LocaleInfo localeInfo, LocaleStore.LocaleInfo localeInfo2) {
            if (localeInfo.isSuggested() == localeInfo2.isSuggested()) {
                return this.mCollator.compare(this.removePrefixForCompare(localeInfo.getLocale(), localeInfo.getLabel(this.mCountryMode)), this.removePrefixForCompare(localeInfo2.getLocale(), localeInfo2.getLabel(this.mCountryMode)));
            }
            int n = localeInfo.isSuggested() ? -1 : 1;
            return n;
        }
    }

}


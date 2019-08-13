/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.os.LocaleList;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocalePicker;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllformedLocaleException;
import java.util.Locale;
import java.util.Set;

public class LocaleStore {
    private static boolean sFullyInitialized;
    private static final HashMap<String, LocaleInfo> sLocaleCache;

    static {
        sLocaleCache = new HashMap();
        sFullyInitialized = false;
    }

    private static void addSuggestedLocalesForRegion(Locale object) {
        if (object == null) {
            return;
        }
        if (((String)(object = ((Locale)object).getCountry())).isEmpty()) {
            return;
        }
        for (LocaleInfo localeInfo : sLocaleCache.values()) {
            if (!((String)object).equals(localeInfo.getLocale().getCountry())) continue;
            LocaleInfo.access$076(localeInfo, 1);
        }
    }

    @UnsupportedAppUsage
    public static void fillCache(Context object) {
        Serializable serializable;
        if (sFullyInitialized) {
            return;
        }
        Set<String> set = LocaleStore.getSimCountries((Context)object);
        ContentResolver object22 = ((Context)object).getContentResolver();
        int n = 0;
        int n2 = Settings.Global.getInt(object22, "development_settings_enabled", 0) != 0 ? 1 : 0;
        for (String string2 : LocalePicker.getSupportedLocales((Context)object)) {
            if (!string2.isEmpty()) {
                String string3;
                LocaleInfo localeInfo = new LocaleInfo(string2);
                if (LocaleList.isPseudoLocale(localeInfo.getLocale())) {
                    if (n2 == 0) continue;
                    localeInfo.setTranslated(true);
                    localeInfo.mIsPseudo = true;
                    LocaleInfo.access$076(localeInfo, 1);
                }
                if (set.contains(localeInfo.getLocale().getCountry())) {
                    LocaleInfo.access$076(localeInfo, 1);
                }
                sLocaleCache.put(localeInfo.getId(), localeInfo);
                serializable = localeInfo.getParent();
                if (serializable == null || sLocaleCache.containsKey(string3 = ((Locale)serializable).toLanguageTag())) continue;
                sLocaleCache.put(string3, new LocaleInfo((Locale)serializable));
                continue;
            }
            throw new IllformedLocaleException("Bad locale entry in locale_config.xml");
        }
        set = new HashSet<String>();
        String[] arrstring = LocalePicker.getSystemAssetLocales();
        int n3 = arrstring.length;
        for (n2 = n; n2 < n3; ++n2) {
            serializable = new LocaleInfo(arrstring[n2]);
            String string4 = ((LocaleInfo)serializable).getLocale().getCountry();
            if (!string4.isEmpty()) {
                object = null;
                if (sLocaleCache.containsKey(((LocaleInfo)serializable).getId())) {
                    object = sLocaleCache.get(((LocaleInfo)serializable).getId());
                } else {
                    CharSequence charSequence = new StringBuilder();
                    charSequence.append(((LocaleInfo)serializable).getLangScriptKey());
                    charSequence.append("-");
                    charSequence.append(string4);
                    charSequence = charSequence.toString();
                    if (sLocaleCache.containsKey(charSequence)) {
                        object = sLocaleCache.get(charSequence);
                    }
                }
                if (object != null) {
                    LocaleInfo.access$076((LocaleInfo)object, 2);
                }
            }
            ((HashSet)set).add(((LocaleInfo)serializable).getLangScriptKey());
        }
        for (LocaleInfo localeInfo : sLocaleCache.values()) {
            localeInfo.setTranslated(((HashSet)set).contains(localeInfo.getLangScriptKey()));
        }
        LocaleStore.addSuggestedLocalesForRegion(Locale.getDefault());
        sFullyInitialized = true;
    }

    private static int getLevel(Set<String> set, LocaleInfo localeInfo, boolean bl) {
        if (set.contains(localeInfo.getId())) {
            return 0;
        }
        if (localeInfo.mIsPseudo) {
            return 2;
        }
        if (bl && !localeInfo.isTranslated()) {
            return 0;
        }
        if (localeInfo.getParent() != null) {
            return 2;
        }
        return 0;
    }

    @UnsupportedAppUsage
    public static Set<LocaleInfo> getLevelLocales(Context object, Set<String> set, LocaleInfo localeInfo, boolean bl) {
        LocaleStore.fillCache((Context)object);
        object = localeInfo == null ? null : localeInfo.getId();
        HashSet<LocaleInfo> hashSet = new HashSet<LocaleInfo>();
        for (LocaleInfo localeInfo2 : sLocaleCache.values()) {
            if (LocaleStore.getLevel(set, localeInfo2, bl) != 2) continue;
            if (localeInfo != null) {
                if (!((String)object).equals(localeInfo2.getParent().toLanguageTag())) continue;
                hashSet.add(localeInfo2);
                continue;
            }
            if (localeInfo2.isSuggestionOfType(1)) {
                hashSet.add(localeInfo2);
                continue;
            }
            hashSet.add(LocaleStore.getLocaleInfo(localeInfo2.getParent()));
        }
        return hashSet;
    }

    @UnsupportedAppUsage
    public static LocaleInfo getLocaleInfo(Locale serializable) {
        String string2 = serializable.toLanguageTag();
        if (!sLocaleCache.containsKey(string2)) {
            serializable = new LocaleInfo((Locale)serializable);
            sLocaleCache.put(string2, (LocaleInfo)serializable);
        } else {
            serializable = sLocaleCache.get(string2);
        }
        return serializable;
    }

    private static Set<String> getSimCountries(Context object) {
        HashSet<String> hashSet = new HashSet<String>();
        TelephonyManager telephonyManager = TelephonyManager.from((Context)object);
        if (telephonyManager != null) {
            object = telephonyManager.getSimCountryIso().toUpperCase(Locale.US);
            if (!((String)object).isEmpty()) {
                hashSet.add((String)object);
            }
            if (!((String)(object = telephonyManager.getNetworkCountryIso().toUpperCase(Locale.US))).isEmpty()) {
                hashSet.add((String)object);
            }
        }
        return hashSet;
    }

    public static void updateSimCountries(Context object) {
        object = LocaleStore.getSimCountries((Context)object);
        for (LocaleInfo localeInfo : sLocaleCache.values()) {
            if (!object.contains(localeInfo.getLocale().getCountry())) continue;
            LocaleInfo.access$076(localeInfo, 1);
        }
    }

    public static class LocaleInfo
    implements Serializable {
        private static final int SUGGESTION_TYPE_CFG = 2;
        private static final int SUGGESTION_TYPE_NONE = 0;
        private static final int SUGGESTION_TYPE_SIM = 1;
        private String mFullCountryNameNative;
        private String mFullNameNative;
        private final String mId;
        private boolean mIsChecked;
        private boolean mIsPseudo;
        private boolean mIsTranslated;
        private String mLangScriptKey;
        private final Locale mLocale;
        private final Locale mParent;
        private int mSuggestionFlags;

        private LocaleInfo(String string2) {
            this(Locale.forLanguageTag(string2));
        }

        private LocaleInfo(Locale locale) {
            this.mLocale = locale;
            this.mId = locale.toLanguageTag();
            this.mParent = LocaleInfo.getParent(locale);
            this.mIsChecked = false;
            this.mSuggestionFlags = 0;
            this.mIsTranslated = false;
            this.mIsPseudo = false;
        }

        static /* synthetic */ int access$076(LocaleInfo localeInfo, int n) {
            localeInfo.mSuggestionFlags = n = localeInfo.mSuggestionFlags | n;
            return n;
        }

        private String getLangScriptKey() {
            if (this.mLangScriptKey == null) {
                Object object = LocaleInfo.getParent(LocaleHelper.addLikelySubtags(new Locale.Builder().setLocale(this.mLocale).setExtension('u', "").build()));
                object = object == null ? this.mLocale.toLanguageTag() : ((Locale)object).toLanguageTag();
                this.mLangScriptKey = object;
            }
            return this.mLangScriptKey;
        }

        private static Locale getParent(Locale locale) {
            if (locale.getCountry().isEmpty()) {
                return null;
            }
            return new Locale.Builder().setLocale(locale).setRegion("").setExtension('u', "").build();
        }

        private boolean isSuggestionOfType(int n) {
            boolean bl = this.mIsTranslated;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            if ((this.mSuggestionFlags & n) == n) {
                bl2 = true;
            }
            return bl2;
        }

        public boolean getChecked() {
            return this.mIsChecked;
        }

        String getContentDescription(boolean bl) {
            if (bl) {
                return this.getFullCountryNameInUiLanguage();
            }
            return this.getFullNameInUiLanguage();
        }

        String getFullCountryNameInUiLanguage() {
            return LocaleHelper.getDisplayCountry(this.mLocale);
        }

        String getFullCountryNameNative() {
            if (this.mFullCountryNameNative == null) {
                Locale locale = this.mLocale;
                this.mFullCountryNameNative = LocaleHelper.getDisplayCountry(locale, locale);
            }
            return this.mFullCountryNameNative;
        }

        @UnsupportedAppUsage
        public String getFullNameInUiLanguage() {
            return LocaleHelper.getDisplayName(this.mLocale, true);
        }

        @UnsupportedAppUsage
        public String getFullNameNative() {
            if (this.mFullNameNative == null) {
                Locale locale = this.mLocale;
                this.mFullNameNative = LocaleHelper.getDisplayName(locale, locale, true);
            }
            return this.mFullNameNative;
        }

        @UnsupportedAppUsage
        public String getId() {
            return this.mId;
        }

        String getLabel(boolean bl) {
            if (bl) {
                return this.getFullCountryNameNative();
            }
            return this.getFullNameNative();
        }

        @UnsupportedAppUsage
        public Locale getLocale() {
            return this.mLocale;
        }

        @UnsupportedAppUsage
        public Locale getParent() {
            return this.mParent;
        }

        boolean isSuggested() {
            boolean bl = this.mIsTranslated;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            if (this.mSuggestionFlags != 0) {
                bl2 = true;
            }
            return bl2;
        }

        public boolean isTranslated() {
            return this.mIsTranslated;
        }

        public void setChecked(boolean bl) {
            this.mIsChecked = bl;
        }

        public void setTranslated(boolean bl) {
            this.mIsTranslated = bl;
        }

        public String toString() {
            return this.mId;
        }
    }

}


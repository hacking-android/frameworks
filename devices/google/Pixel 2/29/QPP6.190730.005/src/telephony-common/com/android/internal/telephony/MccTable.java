/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.app.ActivityManager
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.os.Build
 *  android.os.RemoteException
 *  android.os.SystemProperties
 *  android.text.TextUtils
 *  android.util.Slog
 *  com.android.internal.app.LocaleStore
 *  libcore.icu.ICU
 *  libcore.timezone.TimeZoneFinder
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.app.LocaleStore;
import com.android.internal.telephony.NewTimeServiceHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import libcore.icu.ICU;
import libcore.timezone.TimeZoneFinder;

public final class MccTable {
    private static final Map<Locale, Locale> FALLBACKS = new HashMap<Locale, Locale>();
    static final String LOG_TAG = "MccTable";
    static ArrayList<MccEntry> sTable;

    static {
        FALLBACKS.put(Locale.ENGLISH, Locale.US);
        sTable = new ArrayList(240);
        sTable.add(new MccEntry(202, "gr", 2));
        sTable.add(new MccEntry(204, "nl", 2));
        sTable.add(new MccEntry(206, "be", 2));
        sTable.add(new MccEntry(208, "fr", 2));
        sTable.add(new MccEntry(212, "mc", 2));
        sTable.add(new MccEntry(213, "ad", 2));
        sTable.add(new MccEntry(214, "es", 2));
        sTable.add(new MccEntry(216, "hu", 2));
        sTable.add(new MccEntry(218, "ba", 2));
        sTable.add(new MccEntry(219, "hr", 2));
        sTable.add(new MccEntry(220, "rs", 2));
        sTable.add(new MccEntry(222, "it", 2));
        sTable.add(new MccEntry(225, "va", 2));
        sTable.add(new MccEntry(226, "ro", 2));
        sTable.add(new MccEntry(228, "ch", 2));
        sTable.add(new MccEntry(230, "cz", 2));
        sTable.add(new MccEntry(231, "sk", 2));
        sTable.add(new MccEntry(232, "at", 2));
        sTable.add(new MccEntry(234, "gb", 2));
        sTable.add(new MccEntry(235, "gb", 2));
        sTable.add(new MccEntry(238, "dk", 2));
        sTable.add(new MccEntry(240, "se", 2));
        sTable.add(new MccEntry(242, "no", 2));
        sTable.add(new MccEntry(244, "fi", 2));
        sTable.add(new MccEntry(246, "lt", 2));
        sTable.add(new MccEntry(247, "lv", 2));
        sTable.add(new MccEntry(248, "ee", 2));
        sTable.add(new MccEntry(250, "ru", 2));
        sTable.add(new MccEntry(255, "ua", 2));
        sTable.add(new MccEntry(257, "by", 2));
        sTable.add(new MccEntry(259, "md", 2));
        sTable.add(new MccEntry(260, "pl", 2));
        sTable.add(new MccEntry(262, "de", 2));
        sTable.add(new MccEntry(266, "gi", 2));
        sTable.add(new MccEntry(268, "pt", 2));
        sTable.add(new MccEntry(270, "lu", 2));
        sTable.add(new MccEntry(272, "ie", 2));
        sTable.add(new MccEntry(274, "is", 2));
        sTable.add(new MccEntry(276, "al", 2));
        sTable.add(new MccEntry(278, "mt", 2));
        sTable.add(new MccEntry(280, "cy", 2));
        sTable.add(new MccEntry(282, "ge", 2));
        sTable.add(new MccEntry(283, "am", 2));
        sTable.add(new MccEntry(284, "bg", 2));
        sTable.add(new MccEntry(286, "tr", 2));
        sTable.add(new MccEntry(288, "fo", 2));
        sTable.add(new MccEntry(289, "ge", 2));
        sTable.add(new MccEntry(290, "gl", 2));
        sTable.add(new MccEntry(292, "sm", 2));
        sTable.add(new MccEntry(293, "si", 2));
        sTable.add(new MccEntry(294, "mk", 2));
        sTable.add(new MccEntry(295, "li", 2));
        sTable.add(new MccEntry(297, "me", 2));
        sTable.add(new MccEntry(302, "ca", 3));
        sTable.add(new MccEntry(308, "pm", 2));
        sTable.add(new MccEntry(310, "us", 3));
        sTable.add(new MccEntry(311, "us", 3));
        sTable.add(new MccEntry(312, "us", 3));
        sTable.add(new MccEntry(313, "us", 3));
        sTable.add(new MccEntry(314, "us", 3));
        sTable.add(new MccEntry(315, "us", 3));
        sTable.add(new MccEntry(316, "us", 3));
        sTable.add(new MccEntry(330, "pr", 2));
        sTable.add(new MccEntry(332, "vi", 2));
        sTable.add(new MccEntry(334, "mx", 3));
        sTable.add(new MccEntry(338, "jm", 3));
        sTable.add(new MccEntry(340, "gp", 2));
        sTable.add(new MccEntry(342, "bb", 3));
        sTable.add(new MccEntry(344, "ag", 3));
        sTable.add(new MccEntry(346, "ky", 3));
        sTable.add(new MccEntry(348, "vg", 3));
        sTable.add(new MccEntry(350, "bm", 2));
        sTable.add(new MccEntry(352, "gd", 2));
        sTable.add(new MccEntry(354, "ms", 2));
        sTable.add(new MccEntry(356, "kn", 2));
        sTable.add(new MccEntry(358, "lc", 2));
        sTable.add(new MccEntry(360, "vc", 2));
        sTable.add(new MccEntry(362, "ai", 2));
        sTable.add(new MccEntry(363, "aw", 2));
        sTable.add(new MccEntry(364, "bs", 2));
        sTable.add(new MccEntry(365, "ai", 3));
        sTable.add(new MccEntry(366, "dm", 2));
        sTable.add(new MccEntry(368, "cu", 2));
        sTable.add(new MccEntry(370, "do", 2));
        sTable.add(new MccEntry(372, "ht", 2));
        sTable.add(new MccEntry(374, "tt", 2));
        sTable.add(new MccEntry(376, "tc", 2));
        sTable.add(new MccEntry(400, "az", 2));
        sTable.add(new MccEntry(401, "kz", 2));
        sTable.add(new MccEntry(402, "bt", 2));
        sTable.add(new MccEntry(404, "in", 2));
        sTable.add(new MccEntry(405, "in", 2));
        sTable.add(new MccEntry(406, "in", 2));
        sTable.add(new MccEntry(410, "pk", 2));
        sTable.add(new MccEntry(412, "af", 2));
        sTable.add(new MccEntry(413, "lk", 2));
        sTable.add(new MccEntry(414, "mm", 2));
        sTable.add(new MccEntry(415, "lb", 2));
        sTable.add(new MccEntry(416, "jo", 2));
        sTable.add(new MccEntry(417, "sy", 2));
        sTable.add(new MccEntry(418, "iq", 2));
        sTable.add(new MccEntry(419, "kw", 2));
        sTable.add(new MccEntry(420, "sa", 2));
        sTable.add(new MccEntry(421, "ye", 2));
        sTable.add(new MccEntry(422, "om", 2));
        sTable.add(new MccEntry(423, "ps", 2));
        sTable.add(new MccEntry(424, "ae", 2));
        sTable.add(new MccEntry(425, "il", 2));
        sTable.add(new MccEntry(426, "bh", 2));
        sTable.add(new MccEntry(427, "qa", 2));
        sTable.add(new MccEntry(428, "mn", 2));
        sTable.add(new MccEntry(429, "np", 2));
        sTable.add(new MccEntry(430, "ae", 2));
        sTable.add(new MccEntry(431, "ae", 2));
        sTable.add(new MccEntry(432, "ir", 2));
        sTable.add(new MccEntry(434, "uz", 2));
        sTable.add(new MccEntry(436, "tj", 2));
        sTable.add(new MccEntry(437, "kg", 2));
        sTable.add(new MccEntry(438, "tm", 2));
        sTable.add(new MccEntry(440, "jp", 2));
        sTable.add(new MccEntry(441, "jp", 2));
        sTable.add(new MccEntry(450, "kr", 2));
        sTable.add(new MccEntry(452, "vn", 2));
        sTable.add(new MccEntry(454, "hk", 2));
        sTable.add(new MccEntry(455, "mo", 2));
        sTable.add(new MccEntry(456, "kh", 2));
        sTable.add(new MccEntry(457, "la", 2));
        sTable.add(new MccEntry(460, "cn", 2));
        sTable.add(new MccEntry(461, "cn", 2));
        sTable.add(new MccEntry(466, "tw", 2));
        sTable.add(new MccEntry(467, "kp", 2));
        sTable.add(new MccEntry(470, "bd", 2));
        sTable.add(new MccEntry(472, "mv", 2));
        sTable.add(new MccEntry(502, "my", 2));
        sTable.add(new MccEntry(505, "au", 2));
        sTable.add(new MccEntry(510, "id", 2));
        sTable.add(new MccEntry(514, "tl", 2));
        sTable.add(new MccEntry(515, "ph", 2));
        sTable.add(new MccEntry(520, "th", 2));
        sTable.add(new MccEntry(525, "sg", 2));
        sTable.add(new MccEntry(528, "bn", 2));
        sTable.add(new MccEntry(530, "nz", 2));
        sTable.add(new MccEntry(534, "mp", 2));
        sTable.add(new MccEntry(535, "gu", 2));
        sTable.add(new MccEntry(536, "nr", 2));
        sTable.add(new MccEntry(537, "pg", 2));
        sTable.add(new MccEntry(539, "to", 2));
        sTable.add(new MccEntry(540, "sb", 2));
        sTable.add(new MccEntry(541, "vu", 2));
        sTable.add(new MccEntry(542, "fj", 2));
        sTable.add(new MccEntry(543, "wf", 2));
        sTable.add(new MccEntry(544, "as", 2));
        sTable.add(new MccEntry(545, "ki", 2));
        sTable.add(new MccEntry(546, "nc", 2));
        sTable.add(new MccEntry(547, "pf", 2));
        sTable.add(new MccEntry(548, "ck", 2));
        sTable.add(new MccEntry(549, "ws", 2));
        sTable.add(new MccEntry(550, "fm", 2));
        sTable.add(new MccEntry(551, "mh", 2));
        sTable.add(new MccEntry(552, "pw", 2));
        sTable.add(new MccEntry(553, "tv", 2));
        sTable.add(new MccEntry(555, "nu", 2));
        sTable.add(new MccEntry(602, "eg", 2));
        sTable.add(new MccEntry(603, "dz", 2));
        sTable.add(new MccEntry(604, "ma", 2));
        sTable.add(new MccEntry(605, "tn", 2));
        sTable.add(new MccEntry(606, "ly", 2));
        sTable.add(new MccEntry(607, "gm", 2));
        sTable.add(new MccEntry(608, "sn", 2));
        sTable.add(new MccEntry(609, "mr", 2));
        sTable.add(new MccEntry(610, "ml", 2));
        sTable.add(new MccEntry(611, "gn", 2));
        sTable.add(new MccEntry(612, "ci", 2));
        sTable.add(new MccEntry(613, "bf", 2));
        sTable.add(new MccEntry(614, "ne", 2));
        sTable.add(new MccEntry(615, "tg", 2));
        sTable.add(new MccEntry(616, "bj", 2));
        sTable.add(new MccEntry(617, "mu", 2));
        sTable.add(new MccEntry(618, "lr", 2));
        sTable.add(new MccEntry(619, "sl", 2));
        sTable.add(new MccEntry(620, "gh", 2));
        sTable.add(new MccEntry(621, "ng", 2));
        sTable.add(new MccEntry(622, "td", 2));
        sTable.add(new MccEntry(623, "cf", 2));
        sTable.add(new MccEntry(624, "cm", 2));
        sTable.add(new MccEntry(625, "cv", 2));
        sTable.add(new MccEntry(626, "st", 2));
        sTable.add(new MccEntry(627, "gq", 2));
        sTable.add(new MccEntry(628, "ga", 2));
        sTable.add(new MccEntry(629, "cg", 2));
        sTable.add(new MccEntry(630, "cd", 2));
        sTable.add(new MccEntry(631, "ao", 2));
        sTable.add(new MccEntry(632, "gw", 2));
        sTable.add(new MccEntry(633, "sc", 2));
        sTable.add(new MccEntry(634, "sd", 2));
        sTable.add(new MccEntry(635, "rw", 2));
        sTable.add(new MccEntry(636, "et", 2));
        sTable.add(new MccEntry(637, "so", 2));
        sTable.add(new MccEntry(638, "dj", 2));
        sTable.add(new MccEntry(639, "ke", 2));
        sTable.add(new MccEntry(640, "tz", 2));
        sTable.add(new MccEntry(641, "ug", 2));
        sTable.add(new MccEntry(642, "bi", 2));
        sTable.add(new MccEntry(643, "mz", 2));
        sTable.add(new MccEntry(645, "zm", 2));
        sTable.add(new MccEntry(646, "mg", 2));
        sTable.add(new MccEntry(647, "re", 2));
        sTable.add(new MccEntry(648, "zw", 2));
        sTable.add(new MccEntry(649, "na", 2));
        sTable.add(new MccEntry(650, "mw", 2));
        sTable.add(new MccEntry(651, "ls", 2));
        sTable.add(new MccEntry(652, "bw", 2));
        sTable.add(new MccEntry(653, "sz", 2));
        sTable.add(new MccEntry(654, "km", 2));
        sTable.add(new MccEntry(655, "za", 2));
        sTable.add(new MccEntry(657, "er", 2));
        sTable.add(new MccEntry(658, "sh", 2));
        sTable.add(new MccEntry(659, "ss", 2));
        sTable.add(new MccEntry(702, "bz", 2));
        sTable.add(new MccEntry(704, "gt", 2));
        sTable.add(new MccEntry(706, "sv", 2));
        sTable.add(new MccEntry(708, "hn", 3));
        sTable.add(new MccEntry(710, "ni", 2));
        sTable.add(new MccEntry(712, "cr", 2));
        sTable.add(new MccEntry(714, "pa", 2));
        sTable.add(new MccEntry(716, "pe", 2));
        sTable.add(new MccEntry(722, "ar", 3));
        sTable.add(new MccEntry(724, "br", 2));
        sTable.add(new MccEntry(730, "cl", 2));
        sTable.add(new MccEntry(732, "co", 3));
        sTable.add(new MccEntry(734, "ve", 2));
        sTable.add(new MccEntry(736, "bo", 2));
        sTable.add(new MccEntry(738, "gy", 2));
        sTable.add(new MccEntry(740, "ec", 2));
        sTable.add(new MccEntry(742, "gf", 2));
        sTable.add(new MccEntry(744, "py", 2));
        sTable.add(new MccEntry(746, "sr", 2));
        sTable.add(new MccEntry(748, "uy", 2));
        sTable.add(new MccEntry(750, "fk", 2));
        Collections.sort(sTable);
    }

    @UnsupportedAppUsage
    public static String countryCodeForMcc(int n) {
        MccEntry mccEntry = MccTable.entryForMcc(n);
        if (mccEntry == null) {
            return "";
        }
        return mccEntry.mIso;
    }

    public static String countryCodeForMcc(String string) {
        try {
            string = MccTable.countryCodeForMcc(Integer.parseInt(string));
            return string;
        }
        catch (NumberFormatException numberFormatException) {
            return "";
        }
    }

    @UnsupportedAppUsage
    public static String defaultLanguageForMcc(int n) {
        Object object = MccTable.entryForMcc(n);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("defaultLanguageForMcc(");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("): no country for mcc");
            Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return null;
        }
        String string = ((MccEntry)object).mIso;
        if ("in".equals(string)) {
            return "en";
        }
        object = ICU.addLikelySubtags((Locale)new Locale("und", string)).getLanguage();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("defaultLanguageForMcc(");
        stringBuilder.append(n);
        stringBuilder.append("): country ");
        stringBuilder.append(string);
        stringBuilder.append(" uses ");
        stringBuilder.append((String)object);
        Slog.d((String)LOG_TAG, (String)stringBuilder.toString());
        return object;
    }

    @UnsupportedAppUsage
    public static String defaultTimeZoneForMcc(int n) {
        Object object = MccTable.entryForMcc(n);
        if (object == null) {
            return null;
        }
        object = ((MccEntry)object).mIso;
        return TimeZoneFinder.getInstance().lookupDefaultTimeZoneIdByCountry((String)object);
    }

    @UnsupportedAppUsage
    private static MccEntry entryForMcc(int n) {
        MccEntry mccEntry = new MccEntry(n, "", 0);
        n = Collections.binarySearch(sTable, mccEntry);
        if (n < 0) {
            return null;
        }
        return sTable.get(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static Locale getLocaleForLanguageCountry(Context object, String string, String object2) {
        if (string == null) {
            Slog.d((String)LOG_TAG, (String)"getLocaleForLanguageCountry: skipping no language");
            return null;
        }
        Object object3 = object2;
        if (object2 == null) {
            object3 = "";
        }
        object2 = new Locale(string, (String)object3);
        try {
            object3 = object.getAssets().getLocales();
            Cloneable cloneable = new ArrayList(Arrays.asList(object3));
            cloneable.remove("ar-XB");
            cloneable.remove("en-XA");
            object3 = new ArrayList();
            Iterator iterator = cloneable.iterator();
            while (iterator.hasNext()) {
                cloneable = Locale.forLanguageTag(((String)iterator.next()).replace('_', '-'));
                if (cloneable == null || "und".equals(((Locale)cloneable).getLanguage()) || ((Locale)cloneable).getLanguage().isEmpty() || ((Locale)cloneable).getCountry().isEmpty() || !((Locale)cloneable).getLanguage().equals(((Locale)object2).getLanguage())) continue;
                if (((Locale)cloneable).getCountry().equals(((Locale)object2).getCountry())) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("getLocaleForLanguageCountry: got perfect match: ");
                    ((StringBuilder)object).append(((Locale)cloneable).toLanguageTag());
                    Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                    return cloneable;
                }
                object3.add(cloneable);
            }
            if (object3.isEmpty()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("getLocaleForLanguageCountry: no locales for language ");
                ((StringBuilder)object).append(string);
                Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                return null;
            }
            cloneable = MccTable.lookupFallback((Locale)object2, (List<Locale>)object3);
            if (cloneable != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("getLocaleForLanguageCountry: got a fallback match: ");
                ((StringBuilder)object).append(((Locale)cloneable).toLanguageTag());
                Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                return cloneable;
            }
            if (!TextUtils.isEmpty((CharSequence)((Locale)object2).getCountry())) {
                LocaleStore.fillCache((Context)object);
                if (LocaleStore.getLocaleInfo((Locale)object2).isTranslated()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("getLocaleForLanguageCountry: target locale is translated: ");
                    ((StringBuilder)object).append(object2);
                    Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                    return object2;
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("getLocaleForLanguageCountry: got language-only match: ");
            ((StringBuilder)object).append(string);
            Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return (Locale)object3.get(0);
        }
        catch (Exception exception) {
            Slog.d((String)LOG_TAG, (String)"getLocaleForLanguageCountry: exception", (Throwable)exception);
            return null;
        }
    }

    public static Locale getLocaleFromMcc(Context context, int n, String object) {
        boolean bl = TextUtils.isEmpty((CharSequence)object) ^ true;
        if (!bl) {
            object = MccTable.defaultLanguageForMcc(n);
        }
        String string = MccTable.countryCodeForMcc(n);
        CharSequence charSequence = new StringBuilder();
        charSequence.append("getLocaleFromMcc(");
        charSequence.append((String)object);
        charSequence.append(", ");
        charSequence.append(string);
        charSequence.append(", ");
        charSequence.append(n);
        Slog.d((String)LOG_TAG, (String)charSequence.toString());
        object = MccTable.getLocaleForLanguageCountry(context, (String)object, string);
        if (object == null && bl) {
            charSequence = MccTable.defaultLanguageForMcc(n);
            object = new StringBuilder();
            ((StringBuilder)object).append("[retry ] getLocaleFromMcc(");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n);
            Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
            return MccTable.getLocaleForLanguageCountry(context, (String)charSequence, string);
        }
        return object;
    }

    private static Locale lookupFallback(Locale locale, List<Locale> list) {
        block1 : {
            Locale locale2 = locale;
            do {
                locale = locale2 = FALLBACKS.get(locale2);
                if (locale2 == null) break block1;
                locale2 = locale;
            } while (!list.contains(locale));
            return locale;
        }
        return null;
    }

    private static void setTimezoneFromMccIfNeeded(Context object, int n) {
        String string;
        if (!NewTimeServiceHelper.isTimeZoneSettingInitializedStatic() && (string = MccTable.defaultTimeZoneForMcc(n)) != null && string.length() > 0) {
            NewTimeServiceHelper.setDeviceTimeZoneStatic((Context)object, string);
            object = new StringBuilder();
            ((StringBuilder)object).append("timezone set to ");
            ((StringBuilder)object).append(string);
            Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

    @UnsupportedAppUsage
    public static int smallestDigitsMccForMnc(int n) {
        MccEntry mccEntry = MccTable.entryForMcc(n);
        if (mccEntry == null) {
            return 2;
        }
        return mccEntry.mSmallestDigitsMnc;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void updateMccMncConfiguration(Context object, String charSequence) {
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("updateMccMncConfiguration mccmnc='");
        ((StringBuilder)charSequence2).append((String)charSequence);
        Slog.d((String)LOG_TAG, (String)((StringBuilder)charSequence2).toString());
        charSequence2 = charSequence;
        if (Build.IS_DEBUGGABLE) {
            String string = SystemProperties.get((String)"persist.sys.override_mcc");
            charSequence2 = charSequence;
            if (!TextUtils.isEmpty((CharSequence)string)) {
                charSequence2 = string;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("updateMccMncConfiguration overriding mccmnc='");
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("'");
                Slog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            }
        }
        if (TextUtils.isEmpty((CharSequence)charSequence2)) return;
        int n = Integer.parseInt(((String)charSequence2).substring(0, 3));
        int n2 = Integer.parseInt(((String)charSequence2).substring(3));
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("updateMccMncConfiguration: mcc=");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(", mnc=");
        ((StringBuilder)charSequence).append(n2);
        Slog.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        if (n != 0) {
            MccTable.setTimezoneFromMccIfNeeded((Context)object, n);
        }
        try {
            charSequence = new Configuration();
            int n3 = 0;
            if (n != 0) {
                ((Configuration)charSequence).mcc = n;
                n3 = n2 == 0 ? 65535 : n2;
                ((Configuration)charSequence).mnc = n3;
                n3 = 1;
            }
            if (n3 != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("updateMccMncConfiguration updateConfig config=");
                ((StringBuilder)object).append((Object)charSequence);
                Slog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                ActivityManager.getService().updateConfiguration((Configuration)charSequence);
                return;
            }
            Slog.d((String)LOG_TAG, (String)"updateMccMncConfiguration nothing to update");
            return;
        }
        catch (RemoteException remoteException) {
            Slog.e((String)LOG_TAG, (String)"Can't update configuration", (Throwable)remoteException);
            return;
        }
        catch (NumberFormatException | StringIndexOutOfBoundsException runtimeException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Error parsing IMSI: ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(". ex=");
            ((StringBuilder)charSequence).append(runtimeException);
            Slog.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            return;
        }
    }

    static class MccEntry
    implements Comparable<MccEntry> {
        @UnsupportedAppUsage
        final String mIso;
        final int mMcc;
        final int mSmallestDigitsMnc;

        MccEntry(int n, String string, int n2) {
            if (string != null) {
                this.mMcc = n;
                this.mIso = string;
                this.mSmallestDigitsMnc = n2;
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public int compareTo(MccEntry mccEntry) {
            return this.mMcc - mccEntry.mMcc;
        }
    }

}


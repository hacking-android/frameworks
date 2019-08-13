/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.impl.ZoneMeta
 *  android.icu.text.TimeZoneNames
 *  android.icu.util.ULocale
 */
package java.time.format;

import android.icu.impl.ZoneMeta;
import android.icu.text.TimeZoneNames;
import android.icu.util.ULocale;
import java.util.Locale;
import java.util.Set;

class ZoneName {
    ZoneName() {
    }

    public static String toZid(String string) {
        String string2 = ZoneMeta.getCanonicalCLDRID((String)string);
        if (string2 != null) {
            return string2;
        }
        return string;
    }

    public static String toZid(String string, Locale object) {
        TimeZoneNames timeZoneNames = TimeZoneNames.getInstance((Locale)object);
        String string2 = string;
        if (timeZoneNames.getAvailableMetaZoneIDs().contains(string)) {
            ULocale uLocale = ULocale.forLocale((Locale)object);
            string2 = uLocale.getCountry();
            object = string2;
            if (string2.length() == 0) {
                object = ULocale.addLikelySubtags((ULocale)uLocale).getCountry();
            }
            string2 = timeZoneNames.getReferenceZoneID(string, (String)object);
        }
        return ZoneName.toZid(string2);
    }
}


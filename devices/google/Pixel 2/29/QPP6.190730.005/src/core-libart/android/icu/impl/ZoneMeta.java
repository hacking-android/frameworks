/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUCache;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.OlsonTimeZone;
import android.icu.impl.SimpleCache;
import android.icu.impl.SoftCache;
import android.icu.text.NumberFormat;
import android.icu.util.Output;
import android.icu.util.SimpleTimeZone;
import android.icu.util.TimeZone;
import android.icu.util.UResourceBundle;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeSet;

public final class ZoneMeta {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean ASSERT = false;
    private static ICUCache<String, String> CANONICAL_ID_CACHE;
    private static final CustomTimeZoneCache CUSTOM_ZONE_CACHE;
    private static SoftReference<Set<String>> REF_CANONICAL_SYSTEM_LOCATION_ZONES;
    private static SoftReference<Set<String>> REF_CANONICAL_SYSTEM_ZONES;
    private static SoftReference<Set<String>> REF_SYSTEM_ZONES;
    private static ICUCache<String, String> REGION_CACHE;
    private static ICUCache<String, Boolean> SINGLE_COUNTRY_CACHE;
    private static final SystemTimeZoneCache SYSTEM_ZONE_CACHE;
    private static String[] ZONEIDS;
    private static final String ZONEINFORESNAME = "zoneinfo64";
    private static final String kCUSTOM_TZ_PREFIX = "GMT";
    private static final String kGMT_ID = "GMT";
    private static final int kMAX_CUSTOM_HOUR = 23;
    private static final int kMAX_CUSTOM_MIN = 59;
    private static final int kMAX_CUSTOM_SEC = 59;
    private static final String kNAMES = "Names";
    private static final String kREGIONS = "Regions";
    private static final String kWorld = "001";
    private static final String kZONES = "Zones";

    static {
        ZONEIDS = null;
        CANONICAL_ID_CACHE = new SimpleCache<String, String>();
        REGION_CACHE = new SimpleCache<String, String>();
        SINGLE_COUNTRY_CACHE = new SimpleCache<String, Boolean>();
        SYSTEM_ZONE_CACHE = new SystemTimeZoneCache();
        CUSTOM_ZONE_CACHE = new CustomTimeZoneCache();
    }

    public static int countEquivalentIDs(String object) {
        synchronized (ZoneMeta.class) {
            int n;
            block5 : {
                int n2 = 0;
                object = ZoneMeta.openOlsonResource(null, (String)object);
                n = n2;
                if (object == null) break block5;
                try {
                    n = ((UResourceBundle)object).get("links").getIntVector().length;
                }
                catch (MissingResourceException missingResourceException) {
                    n = n2;
                }
            }
            return n;
        }
    }

    private static String findCLDRCanonicalID(String string) {
        Object object;
        block6 : {
            UResourceBundle uResourceBundle = null;
            Object var2_4 = null;
            String string2 = string.replace('/', ':');
            object = uResourceBundle;
            UResourceBundle uResourceBundle2 = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            object = uResourceBundle;
            uResourceBundle = uResourceBundle2.get("typeMap").get("timezone");
            try {
                uResourceBundle.get(string2);
            }
            catch (MissingResourceException missingResourceException) {
                string = var2_4;
            }
            object = string;
            if (string != null) break block6;
            object = string;
            try {
                string = uResourceBundle2.get("typeAlias").get("timezone").getString(string2);
                object = string;
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return object;
    }

    static String formatCustomID(int n, int n2, int n3, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("GMT");
        if (n != 0 || n2 != 0) {
            if (bl) {
                stringBuilder.append('-');
            } else {
                stringBuilder.append('+');
            }
            if (n < 10) {
                stringBuilder.append('0');
            }
            stringBuilder.append(n);
            stringBuilder.append(':');
            if (n2 < 10) {
                stringBuilder.append('0');
            }
            stringBuilder.append(n2);
            if (n3 != 0) {
                stringBuilder.append(':');
                if (n3 < 10) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(n3);
            }
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Set<String> getAvailableIDs(TimeZone.SystemTimeZoneType object, String treeSet, Integer n) {
        int n2 = 1.$SwitchMap$android$icu$util$TimeZone$SystemTimeZoneType[((Enum)object).ordinal()];
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) throw new IllegalArgumentException("Unknown SystemTimeZoneType");
                object = ZoneMeta.getCanonicalSystemLocationZIDs();
            } else {
                object = ZoneMeta.getCanonicalSystemZIDs();
            }
        } else {
            object = ZoneMeta.getSystemZIDs();
        }
        if (treeSet == null && n == null) {
            return object;
        }
        Object object2 = treeSet;
        if (treeSet != null) {
            object2 = ((String)((Object)treeSet)).toUpperCase(Locale.ENGLISH);
        }
        treeSet = new TreeSet();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            OlsonTimeZone olsonTimeZone;
            object = (String)iterator.next();
            if (object2 != null && !((String)object2).equals(ZoneMeta.getRegion((String)object)) || n != null && ((olsonTimeZone = ZoneMeta.getSystemTimeZone((String)object)) == null || !n.equals(((TimeZone)olsonTimeZone).getRawOffset()))) continue;
            treeSet.add(object);
        }
        if (!treeSet.isEmpty()) return Collections.unmodifiableSet(treeSet);
        return Collections.emptySet();
    }

    public static String getCanonicalCLDRID(TimeZone timeZone) {
        if (timeZone instanceof OlsonTimeZone) {
            return ((OlsonTimeZone)timeZone).getCanonicalID();
        }
        return ZoneMeta.getCanonicalCLDRID(timeZone.getID());
    }

    public static String getCanonicalCLDRID(String string) {
        String string2;
        block11 : {
            Object object;
            String string3;
            block9 : {
                String string4;
                string2 = string3 = CANONICAL_ID_CACHE.get(string);
                if (string3 != null) break block11;
                string3 = string4 = ZoneMeta.findCLDRCanonicalID(string);
                object = string;
                if (string4 == null) {
                    String string5;
                    block10 : {
                        string3 = string;
                        int n = ZoneMeta.getZoneIndex(string);
                        string3 = string4;
                        object = string;
                        if (n < 0) break block9;
                        string3 = string;
                        object = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER).get(kZONES).get(n);
                        string5 = string4;
                        string2 = string;
                        string3 = string;
                        if (((UResourceBundle)object).getType() != 7) break block10;
                        string3 = string;
                        string3 = string2 = ZoneMeta.getZoneID(((UResourceBundle)object).getInt());
                        try {
                            string5 = ZoneMeta.findCLDRCanonicalID(string2);
                        }
                        catch (MissingResourceException missingResourceException) {
                            object = string3;
                            string3 = string4;
                        }
                    }
                    string3 = string5;
                    object = string2;
                    if (string5 == null) {
                        string3 = string2;
                        object = string2;
                    }
                }
            }
            string2 = string3;
            if (string3 != null) {
                CANONICAL_ID_CACHE.put((String)object, string3);
                string2 = string3;
            }
        }
        return string2;
    }

    public static String getCanonicalCountry(String string) {
        String string2;
        string = string2 = ZoneMeta.getRegion(string);
        if (string2 != null) {
            string = string2;
            if (string2.equals(kWorld)) {
                string = null;
            }
        }
        return string;
    }

    public static String getCanonicalCountry(String string, Output<Boolean> output) {
        String string2;
        block10 : {
            output.value = Boolean.FALSE;
            string2 = ZoneMeta.getRegion(string);
            if (string2 != null && string2.equals(kWorld)) {
                return null;
            }
            Boolean bl = SINGLE_COUNTRY_CACHE.get(string);
            Object object = bl;
            if (bl == null) {
                object = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL_LOCATION, string2, null);
                int n = object.size();
                boolean bl2 = true;
                if (n > 1) {
                    bl2 = false;
                }
                object = bl2;
                SINGLE_COUNTRY_CACHE.put(string, (Boolean)object);
            }
            if (((Boolean)object).booleanValue()) {
                output.value = Boolean.TRUE;
            } else {
                object = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "metaZones").get("primaryZones").getString(string2);
                if (string.equals(object)) {
                    output.value = Boolean.TRUE;
                    break block10;
                }
                if ((string = ZoneMeta.getCanonicalCLDRID(string)) == null) break block10;
                try {
                    if (string.equals(object)) {
                        output.value = Boolean.TRUE;
                    }
                }
                catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
            }
        }
        return string2;
    }

    private static Set<String> getCanonicalSystemLocationZIDs() {
        synchronized (ZoneMeta.class) {
            Object object;
            block10 : {
                Object object2 = null;
                if (REF_CANONICAL_SYSTEM_LOCATION_ZONES != null) {
                    object2 = REF_CANONICAL_SYSTEM_LOCATION_ZONES.get();
                }
                object = object2;
                if (object2 != null) break block10;
                TreeSet<Object> treeSet = new TreeSet<Object>();
                object2 = ZoneMeta.getZoneIDs();
                int n = ((String[])object2).length;
                for (int i = 0; i < n; ++i) {
                    String string;
                    object = object2[i];
                    if (((String)object).equals("Etc/Unknown") || !((String)object).equals(ZoneMeta.getCanonicalCLDRID((String)object)) || (string = ZoneMeta.getRegion((String)object)) == null) continue;
                    if (string.equals(kWorld)) continue;
                    treeSet.add(object);
                }
                try {
                    object = Collections.unmodifiableSet(treeSet);
                    REF_CANONICAL_SYSTEM_LOCATION_ZONES = object2 = new SoftReference(object);
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                }
            }
            return object;
        }
    }

    /*
     * WARNING - void declaration
     */
    private static Set<String> getCanonicalSystemZIDs() {
        synchronized (ZoneMeta.class) {
            void var1_6;
            block9 : {
                SoftReference<Set<String>> softReference = null;
                if (REF_CANONICAL_SYSTEM_ZONES != null) {
                    softReference = REF_CANONICAL_SYSTEM_ZONES.get();
                }
                TreeSet set2 = softReference;
                if (softReference != null) break block9;
                softReference = new SoftReference<Set<String>>();
                for (String string : ZoneMeta.getZoneIDs()) {
                    if (string.equals("Etc/Unknown") || !string.equals(ZoneMeta.getCanonicalCLDRID(string))) continue;
                    softReference.add((Set<String>)((Object)string));
                }
                try {
                    Set set = Collections.unmodifiableSet(softReference);
                    softReference = new SoftReference<Set<String>>(set);
                    REF_CANONICAL_SYSTEM_ZONES = softReference;
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                }
            }
            return var1_6;
        }
    }

    public static String getCustomID(String string) {
        int[] arrn = new int[4];
        if (ZoneMeta.parseCustomID(string, arrn)) {
            boolean bl = true;
            int n = arrn[1];
            int n2 = arrn[2];
            int n3 = arrn[3];
            if (arrn[0] >= 0) {
                bl = false;
            }
            return ZoneMeta.formatCustomID(n, n2, n3, bl);
        }
        return null;
    }

    public static SimpleTimeZone getCustomTimeZone(int n) {
        boolean bl = false;
        int n2 = n;
        if (n < 0) {
            bl = true;
            n2 = -n;
        }
        int n3 = n2 / 1000;
        n2 = n3 / 60;
        return new SimpleTimeZone(n, ZoneMeta.formatCustomID(n2 / 60, n2 % 60, n3 % 60, bl));
    }

    public static SimpleTimeZone getCustomTimeZone(String string) {
        int[] arrn = new int[4];
        if (ZoneMeta.parseCustomID(string, arrn)) {
            int n = arrn[0];
            int n2 = arrn[1];
            int n3 = arrn[2];
            int n4 = arrn[3];
            return (SimpleTimeZone)CUSTOM_ZONE_CACHE.getInstance(n * (n2 | n3 << 5 | n4 << 11), arrn);
        }
        return null;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static String getEquivalentID(String var0, int var1_3) {
        // MONITORENTER : android.icu.impl.ZoneMeta.class
        var3_8 = var2_7 = "";
        if (var1_6 >= 0) {
            var0_1 = ZoneMeta.openOlsonResource(null, (String)var0 /* !! */ );
            var3_9 = var2_7;
            if (var0_1 != null) {
                var4_14 = -1;
                try {
                    var0_2 = var0_1.get("links").getIntVector();
                    var5_15 = var4_14;
                    ** if (var1_6 >= var0_2.length) goto lbl-1000
                }
                catch (MissingResourceException var0_3) {
                    var5_15 = var4_14;
                }
lbl-1000: // 1 sources:
                {
                    var5_15 = var0_2[var1_6];
                }
lbl-1000: // 2 sources:
                {
                }
                var3_10 = var2_7;
                if (var5_15 >= 0) {
                    var0_5 = ZoneMeta.getZoneID(var5_15);
                    var3_11 = var2_7;
                    if (var0_5 != null) {
                        var3_12 = var0_5;
                    }
                }
            }
        }
        // MONITOREXIT : android.icu.impl.ZoneMeta.class
        return var3_13;
    }

    public static String getRegion(String string) {
        String string2;
        String string3 = string2 = REGION_CACHE.get(string);
        if (string2 == null) {
            int n = ZoneMeta.getZoneIndex(string);
            string3 = string2;
            if (n >= 0) {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER).get(kREGIONS);
                string3 = string2;
                try {
                    if (n < uResourceBundle.getSize()) {
                        string3 = uResourceBundle.getString(n);
                    }
                    string2 = string3;
                }
                catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
                string3 = string2;
                if (string2 != null) {
                    REGION_CACHE.put(string, string2);
                    string3 = string2;
                }
            }
        }
        return string3;
    }

    public static String getShortID(TimeZone object) {
        if ((object = object instanceof OlsonTimeZone ? ((OlsonTimeZone)object).getCanonicalID() : ZoneMeta.getCanonicalCLDRID(((TimeZone)object).getID())) == null) {
            return null;
        }
        return ZoneMeta.getShortIDFromCanonical((String)object);
    }

    public static String getShortID(String string) {
        if ((string = ZoneMeta.getCanonicalCLDRID(string)) == null) {
            return null;
        }
        return ZoneMeta.getShortIDFromCanonical(string);
    }

    private static String getShortIDFromCanonical(String string) {
        Object var1_2 = null;
        string = string.replace('/', ':');
        try {
            string = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("typeMap").get("timezone").getString(string);
        }
        catch (MissingResourceException missingResourceException) {
            string = var1_2;
        }
        return string;
    }

    public static OlsonTimeZone getSystemTimeZone(String string) {
        return (OlsonTimeZone)SYSTEM_ZONE_CACHE.getInstance(string, string);
    }

    /*
     * WARNING - void declaration
     */
    private static Set<String> getSystemZIDs() {
        synchronized (ZoneMeta.class) {
            void var1_10;
            block9 : {
                void var0_2;
                Object object22 = null;
                if (REF_SYSTEM_ZONES != null) {
                    Set<String> throwable = REF_SYSTEM_ZONES.get();
                }
                void var1_7 = var0_2;
                if (var0_2 != null) break block9;
                TreeSet<String> treeSet = new TreeSet<String>();
                for (String string : ZoneMeta.getZoneIDs()) {
                    if (string.equals("Etc/Unknown")) continue;
                    treeSet.add(string);
                }
                try {
                    Set set = Collections.unmodifiableSet(treeSet);
                    SoftReference softReference = new SoftReference(set);
                    REF_SYSTEM_ZONES = softReference;
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                }
            }
            return var1_10;
        }
    }

    private static String getZoneID(int n) {
        String[] arrstring;
        if (n >= 0 && n < (arrstring = ZoneMeta.getZoneIDs()).length) {
            return arrstring[n];
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String[] getZoneIDs() {
        synchronized (ZoneMeta.class) {
            String[] arrstring = ZONEIDS;
            if (arrstring == null) {
                try {
                    ZONEIDS = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER).getStringArray(kNAMES);
                }
                catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
            }
            if (ZONEIDS != null) return ZONEIDS;
            ZONEIDS = new String[0];
            return ZONEIDS;
        }
    }

    private static int getZoneIndex(String string) {
        int n = -1;
        String[] arrstring = ZoneMeta.getZoneIDs();
        int n2 = n;
        if (arrstring.length > 0) {
            int n3 = 0;
            int n4 = arrstring.length;
            int n5 = Integer.MAX_VALUE;
            do {
                if (n5 == (n2 = (n3 + n4) / 2)) {
                    n2 = n;
                    break;
                }
                n5 = n2;
                int n6 = string.compareTo(arrstring[n2]);
                if (n6 == 0) break;
                if (n6 < 0) {
                    n4 = n2;
                    continue;
                }
                n3 = n2;
            } while (true);
        }
        return n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static UResourceBundle openOlsonResource(UResourceBundle var0, String var1_2) {
        var2_3 = null;
        var3_4 = ZoneMeta.getZoneIndex((String)var1_2);
        var1_2 = var2_3;
        if (var3_4 < 0) return var1_2;
        var1_2 = var0;
        if (var0 != null) ** GOTO lbl9
        try {
            var1_2 = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "zoneinfo64", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
lbl9: // 2 sources:
            var2_3 = var1_2.get("Zones");
            var0 = var1_2 = var2_3.get(var3_4);
            if (var1_2.getType() != 7) return var0;
            var0 = var2_3.get(var1_2.getInt());
            return var0;
        }
        catch (MissingResourceException var0_1) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean parseCustomID(String string, int[] arrn) {
        if (string == null || string.length() <= "GMT".length() || !string.toUpperCase(Locale.ENGLISH).startsWith("GMT")) return false;
        ParsePosition parsePosition = new ParsePosition("GMT".length());
        int n = 1;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        if (string.charAt(parsePosition.getIndex()) == '-') {
            n = -1;
        } else if (string.charAt(parsePosition.getIndex()) != '+') {
            return false;
        }
        parsePosition.setIndex(parsePosition.getIndex() + 1);
        Serializable serializable = NumberFormat.getInstance();
        ((NumberFormat)serializable).setParseIntegerOnly(true);
        int n5 = parsePosition.getIndex();
        Number number = ((NumberFormat)serializable).parse(string, parsePosition);
        if (parsePosition.getIndex() == n5) {
            return false;
        }
        int n6 = number.intValue();
        if (parsePosition.getIndex() < string.length()) {
            if (parsePosition.getIndex() - n5 > 2 || string.charAt(parsePosition.getIndex()) != ':') return false;
            parsePosition.setIndex(parsePosition.getIndex() + 1);
            n2 = parsePosition.getIndex();
            number = ((NumberFormat)serializable).parse(string, parsePosition);
            if (parsePosition.getIndex() - n2 != 2) {
                return false;
            }
            n2 = number.intValue();
            n3 = n4;
            if (parsePosition.getIndex() < string.length()) {
                if (string.charAt(parsePosition.getIndex()) != ':') {
                    return false;
                }
                parsePosition.setIndex(parsePosition.getIndex() + 1);
                n3 = parsePosition.getIndex();
                serializable = ((NumberFormat)serializable).parse(string, parsePosition);
                if (parsePosition.getIndex() != string.length() || parsePosition.getIndex() - n3 != 2) return false;
                n3 = ((Number)serializable).intValue();
            }
        } else {
            n4 = parsePosition.getIndex() - n5;
            if (n4 <= 0 || 6 < n4) return false;
            switch (n4) {
                default: {
                    break;
                }
                case 5: 
                case 6: {
                    n3 = n6 % 100;
                    n2 = n6 / 100 % 100;
                    n6 /= 10000;
                    break;
                }
                case 3: 
                case 4: {
                    n2 = n6 % 100;
                    n6 /= 100;
                }
                case 1: 
                case 2: 
            }
        }
        if (n6 > 23 || n2 > 59 || n3 > 59) return false;
        if (arrn == null) return true;
        if (arrn.length >= 1) {
            arrn[0] = n;
        }
        if (arrn.length >= 2) {
            arrn[1] = n6;
        }
        if (arrn.length >= 3) {
            arrn[2] = n2;
        }
        if (arrn.length < 4) return true;
        arrn[3] = n3;
        return true;
    }

    private static class CustomTimeZoneCache
    extends SoftCache<Integer, SimpleTimeZone, int[]> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private CustomTimeZoneCache() {
        }

        @Override
        protected SimpleTimeZone createInstance(Integer object, int[] arrn) {
            int n = arrn[1];
            int n2 = arrn[2];
            int n3 = arrn[3];
            boolean bl = arrn[0] < 0;
            object = ZoneMeta.formatCustomID(n, n2, n3, bl);
            object = new SimpleTimeZone(arrn[0] * ((arrn[1] * 60 + arrn[2]) * 60 + arrn[3]) * 1000, (String)object);
            ((SimpleTimeZone)object).freeze();
            return object;
        }
    }

    private static class SystemTimeZoneCache
    extends SoftCache<String, OlsonTimeZone, String> {
        private SystemTimeZoneCache() {
        }

        @Override
        protected OlsonTimeZone createInstance(String object, String object2) {
            block6 : {
                Object var3_4 = null;
                OlsonTimeZone olsonTimeZone = null;
                object = var3_4;
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", ZoneMeta.ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                object = var3_4;
                UResourceBundle uResourceBundle2 = ZoneMeta.openOlsonResource(uResourceBundle, (String)object2);
                object = olsonTimeZone;
                if (uResourceBundle2 == null) break block6;
                object = var3_4;
                object = var3_4;
                olsonTimeZone = new OlsonTimeZone(uResourceBundle, uResourceBundle2, (String)object2);
                object = object2 = olsonTimeZone;
                try {
                    ((OlsonTimeZone)object2).freeze();
                    object = object2;
                }
                catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
            }
            return object;
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Grego;
import android.icu.impl.ICUConfig;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.JavaTimeZone;
import android.icu.impl.OlsonTimeZone;
import android.icu.impl.TimeZoneAdapter;
import android.icu.impl.ZoneMeta;
import android.icu.text.TimeZoneFormat;
import android.icu.text.TimeZoneNames;
import android.icu.util.BasicTimeZone;
import android.icu.util.Freezable;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.Output;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import android.icu.util.VersionInfo;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.logging.Logger;

public abstract class TimeZone
implements Serializable,
Cloneable,
Freezable<TimeZone> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int GENERIC_LOCATION = 7;
    public static final TimeZone GMT_ZONE;
    static final String GMT_ZONE_ID = "Etc/GMT";
    private static final Logger LOGGER;
    public static final int LONG = 1;
    public static final int LONG_GENERIC = 3;
    public static final int LONG_GMT = 5;
    public static final int SHORT = 0;
    public static final int SHORT_COMMONLY_USED = 6;
    public static final int SHORT_GENERIC = 2;
    public static final int SHORT_GMT = 4;
    public static final int TIMEZONE_ICU = 0;
    public static final int TIMEZONE_JDK = 1;
    private static final String TZIMPL_CONFIG_ICU = "ICU";
    private static final String TZIMPL_CONFIG_JDK = "JDK";
    private static final String TZIMPL_CONFIG_KEY = "android.icu.util.TimeZone.DefaultTimeZoneType";
    private static int TZ_IMPL = 0;
    public static final TimeZone UNKNOWN_ZONE;
    public static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
    private static volatile TimeZone defaultZone;
    private static final long serialVersionUID = -744942128318337471L;
    private String ID;

    static {
        LOGGER = Logger.getLogger("android.icu.util.TimeZone");
        UNKNOWN_ZONE = new ConstantZone(0, UNKNOWN_ZONE_ID).freeze();
        GMT_ZONE = new ConstantZone(0, GMT_ZONE_ID).freeze();
        defaultZone = null;
        TZ_IMPL = 0;
        if (ICUConfig.get(TZIMPL_CONFIG_KEY, TZIMPL_CONFIG_ICU).equalsIgnoreCase(TZIMPL_CONFIG_JDK)) {
            TZ_IMPL = 1;
        }
    }

    public TimeZone() {
    }

    @Deprecated
    protected TimeZone(String string) {
        if (string != null) {
            this.ID = string;
            return;
        }
        throw new NullPointerException();
    }

    private String _getDisplayName(int n, boolean bl, ULocale object) {
        block7 : {
            Object object2;
            block13 : {
                TimeZoneFormat timeZoneFormat;
                int n2;
                block14 : {
                    Object object3;
                    block8 : {
                        block9 : {
                            long l;
                            block12 : {
                                block10 : {
                                    block11 : {
                                        if (object == null) break block7;
                                        object2 = null;
                                        object3 = null;
                                        if (n == 7 || n == 3 || n == 2) break block8;
                                        if (n == 5 || n == 4) break block9;
                                        l = System.currentTimeMillis();
                                        object3 = TimeZoneNames.getInstance((ULocale)object);
                                        object2 = null;
                                        if (n == 0) break block10;
                                        if (n == 1) break block11;
                                        if (n == 6) break block10;
                                        break block12;
                                    }
                                    object2 = bl ? TimeZoneNames.NameType.LONG_DAYLIGHT : TimeZoneNames.NameType.LONG_STANDARD;
                                    break block12;
                                }
                                object2 = bl ? TimeZoneNames.NameType.SHORT_DAYLIGHT : TimeZoneNames.NameType.SHORT_STANDARD;
                            }
                            object2 = object3 = ((TimeZoneNames)object3).getDisplayName(ZoneMeta.getCanonicalCLDRID(this), (TimeZoneNames.NameType)((Object)object2), l);
                            if (object3 == null) {
                                object = TimeZoneFormat.getInstance((ULocale)object);
                                int n3 = bl && this.useDaylightTime() ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
                                object = n == 1 ? ((TimeZoneFormat)object).formatOffsetLocalizedGMT(n3) : ((TimeZoneFormat)object).formatOffsetShortLocalizedGMT(n3);
                                object2 = object;
                            }
                            break block13;
                        }
                        object = TimeZoneFormat.getInstance((ULocale)object);
                        int n4 = bl && this.useDaylightTime() ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
                        object2 = n != 4 ? (n != 5 ? object3 : ((TimeZoneFormat)object).formatOffsetLocalizedGMT(n4)) : ((TimeZoneFormat)object).formatOffsetISO8601Basic(n4, false, false, false);
                        break block13;
                    }
                    timeZoneFormat = TimeZoneFormat.getInstance((ULocale)object);
                    long l = System.currentTimeMillis();
                    object3 = new Output<TimeZoneFormat.TimeType>(TimeZoneFormat.TimeType.UNKNOWN);
                    object = n != 2 ? (n != 3 ? (n != 7 ? object2 : timeZoneFormat.format(TimeZoneFormat.Style.GENERIC_LOCATION, this, l, (Output<TimeZoneFormat.TimeType>)object3)) : timeZoneFormat.format(TimeZoneFormat.Style.GENERIC_LONG, this, l, (Output<TimeZoneFormat.TimeType>)object3)) : timeZoneFormat.format(TimeZoneFormat.Style.GENERIC_SHORT, this, l, (Output<TimeZoneFormat.TimeType>)object3);
                    if (bl && ((Output)object3).value == TimeZoneFormat.TimeType.STANDARD) break block14;
                    object2 = object;
                    if (bl) break block13;
                    object2 = object;
                    if (((Output)object3).value != TimeZoneFormat.TimeType.DAYLIGHT) break block13;
                }
                int n5 = n2 = this.getRawOffset();
                if (bl) {
                    n5 = n2 + this.getDSTSavings();
                }
                object = n == 2 ? timeZoneFormat.formatOffsetShortLocalizedGMT(n5) : timeZoneFormat.formatOffsetLocalizedGMT(n5);
                object2 = object;
            }
            return object2;
        }
        throw new NullPointerException("locale is null");
    }

    public static int countEquivalentIDs(String string) {
        return ZoneMeta.countEquivalentIDs(string);
    }

    public static Set<String> getAvailableIDs(SystemTimeZoneType systemTimeZoneType, String string, Integer n) {
        return ZoneMeta.getAvailableIDs(systemTimeZoneType, string, n);
    }

    public static String[] getAvailableIDs() {
        return TimeZone.getAvailableIDs(SystemTimeZoneType.ANY, null, null).toArray(new String[0]);
    }

    public static String[] getAvailableIDs(int n) {
        return TimeZone.getAvailableIDs(SystemTimeZoneType.ANY, null, n).toArray(new String[0]);
    }

    public static String[] getAvailableIDs(String string) {
        return TimeZone.getAvailableIDs(SystemTimeZoneType.ANY, string, null).toArray(new String[0]);
    }

    public static String getCanonicalID(String string) {
        return TimeZone.getCanonicalID(string, null);
    }

    public static String getCanonicalID(String string, boolean[] arrbl) {
        String string2 = null;
        boolean bl = false;
        String string3 = string2;
        boolean bl2 = bl;
        if (string != null) {
            string3 = string2;
            bl2 = bl;
            if (string.length() != 0) {
                if (string.equals(UNKNOWN_ZONE_ID)) {
                    string3 = UNKNOWN_ZONE_ID;
                    bl2 = false;
                } else {
                    string3 = ZoneMeta.getCanonicalCLDRID(string);
                    if (string3 != null) {
                        bl2 = true;
                    } else {
                        string3 = ZoneMeta.getCustomID(string);
                        bl2 = bl;
                    }
                }
            }
        }
        if (arrbl != null) {
            arrbl[0] = bl2;
        }
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TimeZone getDefault() {
        TimeZone timeZone;
        TimeZone timeZone2 = timeZone = defaultZone;
        if (timeZone != null) return timeZone2.cloneAsThawed();
        synchronized (java.util.TimeZone.class) {
            block8 : {
                synchronized (TimeZone.class) {
                    timeZone2 = timeZone = defaultZone;
                    if (timeZone != null) break block8;
                    timeZone2 = TZ_IMPL == 1 ? new JavaTimeZone() : TimeZone.getFrozenTimeZone(java.util.TimeZone.getDefault().getID());
                }
                {
                    defaultZone = timeZone2;
                }
            }
            return timeZone2.cloneAsThawed();
        }
    }

    public static int getDefaultTimeZoneType() {
        return TZ_IMPL;
    }

    public static String getEquivalentID(String string, int n) {
        return ZoneMeta.getEquivalentID(string, n);
    }

    static BasicTimeZone getFrozenICUTimeZone(String string, boolean bl) {
        OlsonTimeZone olsonTimeZone = null;
        if (bl) {
            olsonTimeZone = ZoneMeta.getSystemTimeZone(string);
        }
        BasicTimeZone basicTimeZone = olsonTimeZone;
        if (olsonTimeZone == null) {
            basicTimeZone = ZoneMeta.getCustomTimeZone(string);
        }
        return basicTimeZone;
    }

    public static TimeZone getFrozenTimeZone(String string) {
        return TimeZone.getTimeZone(string, TZ_IMPL, true);
    }

    public static String getIDForWindowsID(String string, String string2) {
        block8 : {
            String string3;
            UResourceBundle uResourceBundle;
            block7 : {
                Object var2_4 = null;
                string3 = null;
                Object var4_6 = null;
                uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("mapTimezones");
                uResourceBundle = uResourceBundle.get(string);
                string = var2_4;
                if (string2 == null) break block7;
                string = var4_6;
                string = string2 = uResourceBundle.getString(string2);
                if (string2 == null) break block7;
                string = string2;
                int n = string2.indexOf(32);
                string = string2;
                if (n <= 0) break block7;
                string = string2;
                try {
                    string = string2 = string2.substring(0, n);
                }
                catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
            }
            string2 = string;
            if (string != null) break block8;
            string3 = string;
            try {
                string2 = uResourceBundle.getString("001");
            }
            catch (MissingResourceException missingResourceException) {
                string2 = string3;
            }
        }
        return string2;
    }

    public static String getRegion(String string) {
        CharSequence charSequence = null;
        if (!string.equals(UNKNOWN_ZONE_ID)) {
            charSequence = ZoneMeta.getRegion(string);
        }
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unknown system zone id: ");
        ((StringBuilder)charSequence).append(string);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public static String getTZDataVersion() {
        return VersionInfo.getTZDataVersion();
    }

    public static TimeZone getTimeZone(String string) {
        return TimeZone.getTimeZone(string, TZ_IMPL, false);
    }

    public static TimeZone getTimeZone(String string, int n) {
        return TimeZone.getTimeZone(string, n, false);
    }

    private static TimeZone getTimeZone(String object, int n, boolean bl) {
        Serializable serializable;
        Object object2;
        if (n == 1) {
            object2 = JavaTimeZone.createTimeZone((String)object);
            if (object2 != null) {
                object = bl ? ((TimeZone)object2).freeze() : object2;
                return object;
            }
            serializable = TimeZone.getFrozenICUTimeZone((String)object, false);
        } else {
            serializable = TimeZone.getFrozenICUTimeZone((String)object, true);
        }
        object2 = serializable;
        if (serializable == null) {
            object2 = LOGGER;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("\"");
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append("\" is a bogus id so timezone is falling back to Etc/Unknown(GMT).");
            ((Logger)object2).fine(((StringBuilder)serializable).toString());
            object2 = UNKNOWN_ZONE;
        }
        if (!bl) {
            object2 = ((TimeZone)object2).cloneAsThawed();
        }
        return object2;
    }

    public static String getWindowsID(String string) {
        Object object = new boolean[]{false};
        string = TimeZone.getCanonicalID(string, (boolean[])object);
        if (!object[0]) {
            return null;
        }
        object = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("mapTimezones").getIterator();
        while (((UResourceBundleIterator)object).hasNext()) {
            UResourceBundle uResourceBundle = ((UResourceBundleIterator)object).next();
            if (uResourceBundle.getType() != 2) continue;
            UResourceBundleIterator uResourceBundleIterator = uResourceBundle.getIterator();
            while (uResourceBundleIterator.hasNext()) {
                String[] arrstring = uResourceBundleIterator.next();
                if (arrstring.getType() != 0) continue;
                arrstring = arrstring.getString().split(" ");
                int n = arrstring.length;
                for (int i = 0; i < n; ++i) {
                    if (!arrstring[i].equals(string)) continue;
                    return uResourceBundle.getKey();
                }
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDefault(TimeZone timeZone) {
        synchronized (TimeZone.class) {
            TimeZone.setICUDefault(timeZone);
            if (timeZone != null) {
                java.util.TimeZone timeZone2;
                java.util.TimeZone timeZone3 = null;
                if (timeZone instanceof JavaTimeZone) {
                    timeZone3 = ((JavaTimeZone)timeZone).unwrap();
                } else if (timeZone instanceof OlsonTimeZone) {
                    String string = timeZone.getID();
                    timeZone3 = timeZone2 = java.util.TimeZone.getTimeZone(string);
                    if (!string.equals(timeZone2.getID())) {
                        string = TimeZone.getCanonicalID(string);
                        timeZone3 = timeZone2 = java.util.TimeZone.getTimeZone(string);
                        if (!string.equals(timeZone2.getID())) {
                            timeZone3 = null;
                        }
                    }
                }
                timeZone2 = timeZone3;
                if (timeZone3 == null) {
                    timeZone2 = TimeZoneAdapter.wrap(timeZone);
                }
                java.util.TimeZone.setDefault(timeZone2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDefaultTimeZoneType(int n) {
        synchronized (TimeZone.class) {
            if (n != 0 && n != 1) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Invalid timezone type");
                throw illegalArgumentException;
            }
            TZ_IMPL = n;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static void setICUDefault(TimeZone timeZone) {
        synchronized (TimeZone.class) {
            defaultZone = timeZone == null ? null : (timeZone.isFrozen() ? timeZone : ((TimeZone)timeZone.clone()).freeze());
            return;
        }
    }

    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public TimeZone cloneAsThawed() {
        try {
            TimeZone timeZone = (TimeZone)super.clone();
            return timeZone;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            return this.ID.equals(((TimeZone)object).ID);
        }
        return false;
    }

    @Override
    public TimeZone freeze() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public int getDSTSavings() {
        if (this.useDaylightTime()) {
            return 3600000;
        }
        return 0;
    }

    public final String getDisplayName() {
        return this._getDisplayName(3, false, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public final String getDisplayName(ULocale uLocale) {
        return this._getDisplayName(3, false, uLocale);
    }

    public final String getDisplayName(Locale locale) {
        return this._getDisplayName(3, false, ULocale.forLocale(locale));
    }

    public final String getDisplayName(boolean bl, int n) {
        return this.getDisplayName(bl, n, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(boolean bl, int n, ULocale serializable) {
        if (n >= 0 && n <= 7) {
            return this._getDisplayName(n, bl, (ULocale)serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Illegal style: ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public String getDisplayName(boolean bl, int n, Locale locale) {
        return this.getDisplayName(bl, n, ULocale.forLocale(locale));
    }

    public String getID() {
        return this.ID;
    }

    public abstract int getOffset(int var1, int var2, int var3, int var4, int var5, int var6);

    public int getOffset(long l) {
        int[] arrn = new int[2];
        this.getOffset(l, false, arrn);
        return arrn[0] + arrn[1];
    }

    public void getOffset(long l, boolean bl, int[] arrn) {
        arrn[0] = this.getRawOffset();
        long l2 = l;
        if (!bl) {
            l2 = l + (long)arrn[0];
        }
        int[] arrn2 = new int[6];
        int n = 0;
        do {
            Grego.timeToFields(l2, arrn2);
            arrn[1] = this.getOffset(1, arrn2[0], arrn2[1], arrn2[2], arrn2[3], arrn2[5]) - arrn[0];
            if (n != 0 || !bl || arrn[1] == 0) break;
            l2 -= (long)arrn[1];
            ++n;
        } while (true);
    }

    public abstract int getRawOffset();

    public boolean hasSameRules(TimeZone timeZone) {
        boolean bl = timeZone != null && this.getRawOffset() == timeZone.getRawOffset() && this.useDaylightTime() == timeZone.useDaylightTime();
        return bl;
    }

    public int hashCode() {
        return this.ID.hashCode();
    }

    public abstract boolean inDaylightTime(Date var1);

    @Override
    public boolean isFrozen() {
        return false;
    }

    public boolean observesDaylightTime() {
        boolean bl = this.useDaylightTime() || this.inDaylightTime(new Date());
        return bl;
    }

    public void setID(String string) {
        if (string != null) {
            if (!this.isFrozen()) {
                this.ID = string;
                return;
            }
            throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
        }
        throw new NullPointerException();
    }

    public abstract void setRawOffset(int var1);

    public abstract boolean useDaylightTime();

    private static final class ConstantZone
    extends TimeZone {
        private static final long serialVersionUID = 1L;
        private volatile transient boolean isFrozen = false;
        private int rawOffset;

        private ConstantZone(int n, String string) {
            super(string);
            this.rawOffset = n;
        }

        @Override
        public TimeZone cloneAsThawed() {
            ConstantZone constantZone = (ConstantZone)super.cloneAsThawed();
            constantZone.isFrozen = false;
            return constantZone;
        }

        @Override
        public TimeZone freeze() {
            this.isFrozen = true;
            return this;
        }

        @Override
        public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
            return this.rawOffset;
        }

        @Override
        public int getRawOffset() {
            return this.rawOffset;
        }

        @Override
        public boolean inDaylightTime(Date date) {
            return false;
        }

        @Override
        public boolean isFrozen() {
            return this.isFrozen;
        }

        @Override
        public void setRawOffset(int n) {
            if (!this.isFrozen()) {
                this.rawOffset = n;
                return;
            }
            throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
        }

        @Override
        public boolean useDaylightTime() {
            return false;
        }
    }

    public static enum SystemTimeZoneType {
        ANY,
        CANONICAL,
        CANONICAL_LOCATION;
        
    }

}


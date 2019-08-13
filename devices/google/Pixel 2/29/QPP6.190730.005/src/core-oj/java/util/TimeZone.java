/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.TimeZoneNames
 *  android.icu.text.TimeZoneNames$NameType
 *  android.icu.util.TimeZone
 *  dalvik.system.RuntimeHooks
 *  libcore.io.IoUtils
 *  libcore.timezone.ZoneInfoDB
 *  libcore.util.ZoneInfo
 */
package java.util;

import android.icu.text.TimeZoneNames;
import dalvik.system.RuntimeHooks;
import java.io.IOException;
import java.io.Serializable;
import java.security.Permission;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyPermission;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.io.IoUtils;
import libcore.timezone.ZoneInfoDB;
import libcore.util.ZoneInfo;

public abstract class TimeZone
implements Serializable,
Cloneable {
    private static final TimeZone GMT = new SimpleTimeZone(0, "GMT");
    public static final int LONG = 1;
    static final TimeZone NO_TIMEZONE;
    public static final int SHORT = 0;
    private static final TimeZone UTC;
    private static volatile TimeZone defaultTimeZone;
    static final long serialVersionUID = 3581463369166924961L;
    private String ID;

    static {
        UTC = new SimpleTimeZone(0, "UTC");
        NO_TIMEZONE = null;
    }

    private static void appendNumber(StringBuilder stringBuilder, int n, int n2) {
        String string = Integer.toString(n2);
        for (n2 = 0; n2 < n - string.length(); ++n2) {
            stringBuilder.append('0');
        }
        stringBuilder.append(string);
    }

    public static String createGmtOffsetString(boolean bl, boolean bl2, int n) {
        int n2 = n / 60000;
        char c = '+';
        n = n2;
        char c2 = c;
        if (n2 < 0) {
            c = '-';
            n = -n2;
            c2 = c;
        }
        StringBuilder stringBuilder = new StringBuilder(9);
        if (bl) {
            stringBuilder.append("GMT");
        }
        stringBuilder.append(c2);
        TimeZone.appendNumber(stringBuilder, 2, n / 60);
        if (bl2) {
            stringBuilder.append(':');
        }
        TimeZone.appendNumber(stringBuilder, 2, n % 60);
        return stringBuilder.toString();
    }

    public static String[] getAvailableIDs() {
        synchronized (TimeZone.class) {
            String[] arrstring = ZoneInfoDB.getInstance().getAvailableIDs();
            return arrstring;
        }
    }

    public static String[] getAvailableIDs(int n) {
        synchronized (TimeZone.class) {
            String[] arrstring = ZoneInfoDB.getInstance().getAvailableIDs(n);
            return arrstring;
        }
    }

    private static TimeZone getCustomTimeZone(String string) {
        block5 : {
            int n;
            int n2;
            Matcher matcher = NoImagePreloadHolder.CUSTOM_ZONE_ID_PATTERN.matcher(string);
            if (!matcher.matches()) {
                return null;
            }
            int n3 = 0;
            try {
                n2 = Integer.parseInt(matcher.group(1));
                if (matcher.group(3) != null) {
                    n3 = Integer.parseInt(matcher.group(3));
                }
                if (n2 < 0 || n2 > 23 || n3 < 0 || n3 > 59) break block5;
            }
            catch (NumberFormatException numberFormatException) {
                throw new AssertionError(numberFormatException);
            }
            char c = string.charAt(3);
            int n4 = n = 3600000 * n2 + 60000 * n3;
            if (c == '-') {
                n4 = -n;
            }
            return new SimpleTimeZone(n4, String.format(Locale.ROOT, "GMT%c%02d:%02d", Character.valueOf(c), n2, n3));
        }
        return null;
    }

    public static TimeZone getDefault() {
        return (TimeZone)TimeZone.getDefaultRef().clone();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static TimeZone getDefaultRef() {
        synchronized (TimeZone.class) {
            boolean bl;
            if (defaultTimeZone != null) return defaultTimeZone;
            Object object = RuntimeHooks.getTimeZoneIdSupplier();
            String string = object != null ? (String)object.get() : null;
            object = string;
            if (string != null) {
                object = string.trim();
            }
            if (object == null || (bl = ((String)object).isEmpty())) {
                try {
                    object = IoUtils.readFileAsString((String)"/etc/timezone");
                }
                catch (IOException iOException) {
                    object = "GMT";
                }
            }
            defaultTimeZone = TimeZone.getTimeZone((String)object);
            return defaultTimeZone;
        }
    }

    private static native String getSystemGMTOffsetID();

    private static native String getSystemTimeZoneID(String var0, String var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TimeZone getTimeZone(String object) {
        synchronized (TimeZone.class) {
            if (object != null) {
                Object object2;
                block12 : {
                    block13 : {
                        if (((String)object).length() != 3) break block12;
                        if (!((String)object).equals("GMT")) break block13;
                        return (TimeZone)GMT.clone();
                    }
                    if (!((String)object).equals("UTC")) break block12;
                    return (TimeZone)UTC.clone();
                }
                ZoneInfo zoneInfo = null;
                try {
                    zoneInfo = object2 = ZoneInfoDB.getInstance().makeTimeZone((String)object);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                object2 = zoneInfo;
                if (zoneInfo == null) {
                    object2 = zoneInfo;
                    if (((String)object).length() > 3) {
                        object2 = zoneInfo;
                        if (((String)object).startsWith("GMT")) {
                            object2 = TimeZone.getCustomTimeZone((String)object);
                        }
                    }
                }
                if (object2 == null) return (TimeZone)GMT.clone();
                return object2;
            }
            try {
                object = new NullPointerException("id == null");
                throw object;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public static TimeZone getTimeZone(ZoneId object) {
        String string = ((ZoneId)object).getId();
        char c = string.charAt(0);
        if (c != '+' && c != '-') {
            object = string;
            if (c == 'Z') {
                object = string;
                if (string.length() == 1) {
                    object = "UTC";
                }
            }
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("GMT");
            ((StringBuilder)object).append(string);
            object = ((StringBuilder)object).toString();
        }
        return TimeZone.getTimeZone((String)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDefault(TimeZone timeZone) {
        synchronized (TimeZone.class) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                PropertyPermission propertyPermission = new PropertyPermission("user.timezone", "write");
                securityManager.checkPermission(propertyPermission);
            }
            timeZone = timeZone != null ? (TimeZone)timeZone.clone() : null;
            defaultTimeZone = timeZone;
            android.icu.util.TimeZone.setICUDefault(null);
            return;
        }
    }

    public Object clone() {
        try {
            TimeZone timeZone = (TimeZone)super.clone();
            timeZone.ID = this.ID;
            return timeZone;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    public int getDSTSavings() {
        if (this.useDaylightTime()) {
            return 3600000;
        }
        return 0;
    }

    public final String getDisplayName() {
        return this.getDisplayName(false, 1, Locale.getDefault(Locale.Category.DISPLAY));
    }

    public final String getDisplayName(Locale locale) {
        return this.getDisplayName(false, 1, locale);
    }

    public final String getDisplayName(boolean bl, int n) {
        return this.getDisplayName(bl, n, Locale.getDefault(Locale.Category.DISPLAY));
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getDisplayName(boolean bl, int n, Locale object) {
        int n2;
        TimeZoneNames.NameType nameType;
        if (n != 0) {
            if (n != 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Illegal style: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            nameType = bl ? TimeZoneNames.NameType.LONG_DAYLIGHT : TimeZoneNames.NameType.LONG_STANDARD;
        } else {
            nameType = bl ? TimeZoneNames.NameType.SHORT_DAYLIGHT : TimeZoneNames.NameType.SHORT_STANDARD;
        }
        String string = android.icu.util.TimeZone.getCanonicalID((String)this.getID());
        if (string != null && (object = TimeZoneNames.getInstance((Locale)object).getDisplayName(string, nameType, System.currentTimeMillis())) != null) {
            return object;
        }
        n = n2 = this.getRawOffset();
        if (bl) {
            n = n2 + this.getDSTSavings();
        }
        return TimeZone.createGmtOffsetString(true, true, n);
    }

    public String getID() {
        return this.ID;
    }

    public abstract int getOffset(int var1, int var2, int var3, int var4, int var5, int var6);

    public int getOffset(long l) {
        if (this.inDaylightTime(new Date(l))) {
            return this.getRawOffset() + this.getDSTSavings();
        }
        return this.getRawOffset();
    }

    int getOffsets(long l, int[] arrn) {
        int n = this.getRawOffset();
        int n2 = 0;
        if (this.inDaylightTime(new Date(l))) {
            n2 = this.getDSTSavings();
        }
        if (arrn != null) {
            arrn[0] = n;
            arrn[1] = n2;
        }
        return n + n2;
    }

    public abstract int getRawOffset();

    public boolean hasSameRules(TimeZone timeZone) {
        boolean bl = timeZone != null && this.getRawOffset() == timeZone.getRawOffset() && this.useDaylightTime() == timeZone.useDaylightTime();
        return bl;
    }

    public abstract boolean inDaylightTime(Date var1);

    public boolean observesDaylightTime() {
        boolean bl = this.useDaylightTime() || this.inDaylightTime(new Date());
        return bl;
    }

    public void setID(String string) {
        if (string != null) {
            this.ID = string;
            return;
        }
        throw new NullPointerException();
    }

    public abstract void setRawOffset(int var1);

    public ZoneId toZoneId() {
        return ZoneId.of(this.getID(), ZoneId.SHORT_IDS);
    }

    public abstract boolean useDaylightTime();

    private static class NoImagePreloadHolder {
        public static final Pattern CUSTOM_ZONE_ID_PATTERN = Pattern.compile("^GMT[-+](\\d{1,2})(:?(\\d\\d))?$");

        private NoImagePreloadHolder() {
        }
    }

}


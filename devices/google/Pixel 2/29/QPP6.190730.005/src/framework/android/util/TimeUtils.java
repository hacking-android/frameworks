/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.TimeZone
 *  libcore.timezone.CountryTimeZones
 *  libcore.timezone.CountryTimeZones$TimeZoneMapping
 *  libcore.timezone.TimeZoneFinder
 *  libcore.timezone.ZoneInfoDB
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.icu.util.TimeZone;
import android.os.SystemClock;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import libcore.timezone.CountryTimeZones;
import libcore.timezone.TimeZoneFinder;
import libcore.timezone.ZoneInfoDB;

public class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    public static final long NANOS_PER_MS = 1000000L;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    public static final SimpleDateFormat sDumpDateFormat;
    private static char[] sFormatStr;
    private static final Object sFormatSync;
    private static SimpleDateFormat sLoggingFormat;
    private static char[] sTmpFormatStr;

    static {
        sLoggingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sDumpDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sFormatSync = new Object();
        sFormatStr = new char[29];
        sTmpFormatStr = new char[29];
    }

    private static int accumField(int n, int n2, boolean bl, int n3) {
        if (n > 999) {
            n3 = 0;
            while (n != 0) {
                ++n3;
                n /= 10;
            }
            return n3 + n2;
        }
        if (!(n > 99 || bl && n3 >= 3)) {
            if (!(n > 9 || bl && n3 >= 2)) {
                if (!bl && n <= 0) {
                    return 0;
                }
                return n2 + 1;
            }
            return n2 + 2;
        }
        return n2 + 3;
    }

    public static void dumpTime(PrintWriter printWriter, long l) {
        printWriter.print(sDumpDateFormat.format(new Date(l)));
    }

    public static void dumpTimeWithDelta(PrintWriter printWriter, long l, long l2) {
        printWriter.print(sDumpDateFormat.format(new Date(l)));
        if (l == l2) {
            printWriter.print(" (now)");
        } else {
            printWriter.print(" (");
            TimeUtils.formatDuration(l, l2, printWriter);
            printWriter.print(")");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String formatDuration(long l) {
        Object object = sFormatSync;
        synchronized (object) {
            int n = TimeUtils.formatDurationLocked(l, 0);
            return new String(sFormatStr, 0, n);
        }
    }

    public static void formatDuration(long l, long l2, PrintWriter printWriter) {
        if (l == 0L) {
            printWriter.print("--");
            return;
        }
        TimeUtils.formatDuration(l - l2, printWriter, 0);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static void formatDuration(long l, PrintWriter printWriter) {
        TimeUtils.formatDuration(l, printWriter, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static void formatDuration(long l, PrintWriter printWriter, int n) {
        Object object = sFormatSync;
        synchronized (object) {
            n = TimeUtils.formatDurationLocked(l, n);
            String string2 = new String(sFormatStr, 0, n);
            printWriter.print(string2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void formatDuration(long l, StringBuilder stringBuilder) {
        Object object = sFormatSync;
        synchronized (object) {
            int n = TimeUtils.formatDurationLocked(l, 0);
            stringBuilder.append(sFormatStr, 0, n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void formatDuration(long l, StringBuilder stringBuilder, int n) {
        Object object = sFormatSync;
        synchronized (object) {
            n = TimeUtils.formatDurationLocked(l, n);
            stringBuilder.append(sFormatStr, 0, n);
            return;
        }
    }

    private static int formatDurationLocked(long l, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        if (sFormatStr.length < n) {
            sFormatStr = new char[n];
        }
        char[] arrc = sFormatStr;
        if (l == 0L) {
            int n8;
            for (n8 = 0; n8 < n - 1; ++n8) {
                arrc[n8] = (char)32;
            }
            arrc[n8] = (char)48;
            return n8 + 1;
        }
        if (l > 0L) {
            n3 = 43;
        } else {
            l = -l;
            n3 = 45;
        }
        int n9 = (int)(l % 1000L);
        int n10 = (int)Math.floor(l / 1000L);
        if (n10 >= 86400) {
            n6 = n10 / 86400;
            n10 -= 86400 * n6;
        } else {
            n6 = 0;
        }
        if (n10 >= 3600) {
            n2 = n10 / 3600;
            n10 -= n2 * 3600;
        } else {
            n2 = 0;
        }
        if (n10 >= 60) {
            n5 = n10 / 60;
            n7 = n10 - n5 * 60;
        } else {
            n5 = 0;
            n7 = n10;
        }
        int n11 = 0;
        int n12 = 0;
        int n13 = 3;
        boolean bl = false;
        if (n != 0) {
            n10 = TimeUtils.accumField(n6, 1, false, 0);
            if (n10 > 0) {
                bl = true;
            }
            bl = (n10 += TimeUtils.accumField(n2, 1, bl, 2)) > 0;
            bl = (n10 += TimeUtils.accumField(n5, 1, bl, 2)) > 0;
            n4 = n10 + TimeUtils.accumField(n7, 1, bl, 2);
            n10 = n4 > 0 ? 3 : 0;
            n4 += TimeUtils.accumField(n9, 2, true, n10) + 1;
            n10 = n12;
            do {
                n11 = n10++;
                if (n4 >= n) break;
                arrc[n10] = (char)32;
                ++n4;
            } while (true);
        }
        arrc[n11] = (char)n3;
        n3 = n11 + 1;
        n10 = n != 0 ? 1 : 0;
        bl = true;
        n = 2;
        n4 = TimeUtils.printFieldLocked(arrc, n6, 'd', n3, false, 0);
        boolean bl2 = n4 != n3 ? bl : false;
        n6 = n10 != 0 ? n : 0;
        n2 = TimeUtils.printFieldLocked(arrc, n2, 'h', n4, bl2, n6);
        bl2 = n2 != n3 ? bl : false;
        n6 = n10 != 0 ? n : 0;
        if ((n6 = TimeUtils.printFieldLocked(arrc, n5, 'm', n2, bl2, n6)) == n3) {
            bl = false;
        }
        if (n10 == 0) {
            n = 0;
        }
        n7 = TimeUtils.printFieldLocked(arrc, n7, 's', n6, bl, n);
        n = n10 != 0 && n7 != n3 ? n13 : 0;
        n = TimeUtils.printFieldLocked(arrc, n9, 'm', n7, true, n);
        arrc[n] = (char)115;
        return n + 1;
    }

    public static String formatForLogging(long l) {
        if (l <= 0L) {
            return "unknown";
        }
        return sLoggingFormat.format(new Date(l));
    }

    public static String formatUptime(long l) {
        long l2 = l - SystemClock.uptimeMillis();
        if (l2 > 0L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(l);
            stringBuilder.append(" (in ");
            stringBuilder.append(l2);
            stringBuilder.append(" ms)");
            return stringBuilder.toString();
        }
        if (l2 < 0L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(l);
            stringBuilder.append(" (");
            stringBuilder.append(-l2);
            stringBuilder.append(" ms ago)");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(l);
        stringBuilder.append(" (now)");
        return stringBuilder.toString();
    }

    private static TimeZone getIcuTimeZone(int n, boolean bl, long l, String string2) {
        if (string2 == null) {
            return null;
        }
        TimeZone timeZone = TimeZone.getDefault();
        return TimeZoneFinder.getInstance().lookupTimeZoneByCountryAndOffset(string2, n, bl, l, timeZone);
    }

    public static java.util.TimeZone getTimeZone(int n, boolean bl, long l, String object) {
        object = (object = TimeUtils.getIcuTimeZone(n, bl, l, (String)object)) != null ? java.util.TimeZone.getTimeZone(object.getID()) : null;
        return object;
    }

    public static String getTimeZoneDatabaseVersion() {
        return ZoneInfoDB.getInstance().getVersion();
    }

    public static List<String> getTimeZoneIdsForCountryCode(String object) {
        if (object != null) {
            TimeZoneFinder timeZoneFinder2 = TimeZoneFinder.getInstance();
            if ((timeZoneFinder2 = timeZoneFinder2.lookupCountryTimeZones(((String)object).toLowerCase())) == null) {
                return null;
            }
            object = new ArrayList();
            for (TimeZoneFinder timeZoneFinder2 : timeZoneFinder2.getTimeZoneMappings()) {
                if (!timeZoneFinder2.showInPicker) continue;
                object.add(timeZoneFinder2.timeZoneId);
            }
            return Collections.unmodifiableList(object);
        }
        throw new NullPointerException("countryCode == null");
    }

    @UnsupportedAppUsage
    public static String logTimeOfDay(long l) {
        Calendar calendar = Calendar.getInstance();
        if (l >= 0L) {
            calendar.setTimeInMillis(l);
            return String.format("%tm-%td %tH:%tM:%tS.%tL", calendar, calendar, calendar, calendar, calendar, calendar);
        }
        return Long.toString(l);
    }

    private static int printFieldLocked(char[] arrc, int n, char c, int n2, boolean bl, int n3) {
        int n4;
        block7 : {
            block9 : {
                block13 : {
                    int n5;
                    block12 : {
                        block11 : {
                            block10 : {
                                block8 : {
                                    char[] arrc2;
                                    block6 : {
                                        if (bl) break block6;
                                        n4 = n2;
                                        if (n <= 0) break block7;
                                    }
                                    if (n <= 999) break block8;
                                    for (n3 = 0; n != 0 && n3 < (arrc2 = sTmpFormatStr).length; ++n3, n /= 10) {
                                        arrc2[n3] = (char)(n % 10 + 48);
                                    }
                                    for (n = n3 - 1; n >= 0; --n) {
                                        arrc[n2] = sTmpFormatStr[n];
                                        ++n2;
                                    }
                                    break block9;
                                }
                                if (bl && n3 >= 3) break block10;
                                n4 = n;
                                n5 = n2;
                                if (n <= 99) break block11;
                            }
                            n4 = n / 100;
                            arrc[n2] = (char)(n4 + 48);
                            n5 = n2 + 1;
                            n4 = n - n4 * 100;
                        }
                        if (bl && n3 >= 2 || n4 > 9) break block12;
                        n3 = n4;
                        n = n5;
                        if (n2 == n5) break block13;
                    }
                    n2 = n4 / 10;
                    arrc[n5] = (char)(n2 + 48);
                    n = n5 + 1;
                    n3 = n4 - n2 * 10;
                }
                arrc[n] = (char)(n3 + 48);
                n2 = n + 1;
            }
            arrc[n2] = c;
            n4 = n2 + 1;
        }
        return n4;
    }
}


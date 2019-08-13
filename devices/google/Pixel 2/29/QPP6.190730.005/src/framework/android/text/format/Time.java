/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.timezone.ZoneInfoDB
 *  libcore.util.ZoneInfo
 *  libcore.util.ZoneInfo$WallTime
 */
package android.text.format;

import android.text.format.TimeFormatter;
import android.util.TimeFormatException;
import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;
import libcore.timezone.ZoneInfoDB;
import libcore.util.ZoneInfo;

@Deprecated
public class Time {
    private static final int[] DAYS_PER_MONTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final int EPOCH_JULIAN_DAY = 2440588;
    public static final int FRIDAY = 5;
    public static final int HOUR = 3;
    public static final int MINUTE = 2;
    public static final int MONDAY = 1;
    public static final int MONDAY_BEFORE_JULIAN_EPOCH = 2440585;
    public static final int MONTH = 5;
    public static final int MONTH_DAY = 4;
    public static final int SATURDAY = 6;
    public static final int SECOND = 1;
    public static final int SUNDAY = 0;
    public static final int THURSDAY = 4;
    public static final String TIMEZONE_UTC = "UTC";
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int WEEK_DAY = 7;
    public static final int WEEK_NUM = 9;
    public static final int YEAR = 6;
    public static final int YEAR_DAY = 8;
    private static final String Y_M_D = "%Y-%m-%d";
    private static final String Y_M_D_T_H_M_S_000 = "%Y-%m-%dT%H:%M:%S.000";
    private static final String Y_M_D_T_H_M_S_000_Z = "%Y-%m-%dT%H:%M:%S.000Z";
    private static final int[] sThursdayOffset = new int[]{-3, 3, 2, 1, 0, -1, -2};
    public boolean allDay;
    private TimeCalculator calculator;
    public long gmtoff;
    public int hour;
    public int isDst;
    public int minute;
    public int month;
    public int monthDay;
    public int second;
    public String timezone;
    public int weekDay;
    public int year;
    public int yearDay;

    public Time() {
        this.initialize(TimeZone.getDefault().getID());
    }

    public Time(Time time) {
        this.initialize(time.timezone);
        this.set(time);
    }

    public Time(String string2) {
        if (string2 != null) {
            this.initialize(string2);
            return;
        }
        throw new NullPointerException("timezoneId is null!");
    }

    private void checkChar(String string2, int n, char c) {
        char c2 = string2.charAt(n);
        if (c2 == c) {
            return;
        }
        throw new TimeFormatException(String.format("Unexpected character 0x%02d at pos=%d.  Expected 0x%02d ('%c').", c2, n, (int)c, Character.valueOf(c)));
    }

    public static int compare(Time time, Time time2) {
        if (time != null) {
            if (time2 != null) {
                time.calculator.copyFieldsFromTime(time);
                time2.calculator.copyFieldsFromTime(time2);
                return TimeCalculator.compare(time.calculator, time2.calculator);
            }
            throw new NullPointerException("b == null");
        }
        throw new NullPointerException("a == null");
    }

    private static int getChar(String charSequence, int n, int n2) {
        char c = ((String)charSequence).charAt(n);
        if (Character.isDigit(c)) {
            return Character.getNumericValue(c) * n2;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Parse error at pos=");
        ((StringBuilder)charSequence).append(n);
        throw new TimeFormatException(((StringBuilder)charSequence).toString());
    }

    public static String getCurrentTimezone() {
        return TimeZone.getDefault().getID();
    }

    public static int getJulianDay(long l, long l2) {
        return (int)((l + 1000L * l2) / 86400000L) + 2440588;
    }

    public static int getJulianMondayFromWeeksSinceEpoch(int n) {
        return n * 7 + 2440585;
    }

    public static int getWeeksSinceEpochFromJulianDay(int n, int n2) {
        int n3;
        n2 = n3 = 4 - n2;
        if (n3 < 0) {
            n2 = n3 + 7;
        }
        return (n - (2440588 - n2)) / 7;
    }

    private void initialize(String string2) {
        this.timezone = string2;
        this.year = 1970;
        this.monthDay = 1;
        this.isDst = -1;
        this.calculator = new TimeCalculator(string2);
    }

    public static boolean isEpoch(Time time) {
        boolean bl = true;
        if (Time.getJulianDay(time.toMillis(true), 0L) != 2440588) {
            bl = false;
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean parse3339Internal(String string2) {
        int n = string2.length();
        if (n < 10) throw new TimeFormatException("String too short --- expected at least 10 characters.");
        boolean bl = false;
        boolean bl2 = false;
        this.year = Time.getChar(string2, 0, 1000) + Time.getChar(string2, 1, 100) + Time.getChar(string2, 2, 10) + Time.getChar(string2, 3, 1);
        this.checkChar(string2, 4, '-');
        this.month = Time.getChar(string2, 5, 10) + Time.getChar(string2, 6, 1) - 1;
        this.checkChar(string2, 7, '-');
        this.monthDay = Time.getChar(string2, 8, 10) + Time.getChar(string2, 9, 1);
        if (n >= 19) {
            int n2;
            int n3;
            int n4;
            int n5;
            block12 : {
                this.checkChar(string2, 10, 'T');
                this.allDay = false;
                n3 = Time.getChar(string2, 11, 10) + Time.getChar(string2, 12, 1);
                this.checkChar(string2, 13, ':');
                n5 = Time.getChar(string2, 14, 10) + Time.getChar(string2, 15, 1);
                this.checkChar(string2, 16, ':');
                this.second = Time.getChar(string2, 17, 10) + Time.getChar(string2, 18, 1);
                n2 = n4 = 19;
                if (19 < n) {
                    n2 = n4;
                    if (string2.charAt(19) == '.') {
                        n2 = n4;
                        do {
                            n2 = n4 = n2 + 1;
                            if (n4 >= n) break block12;
                            n2 = n4;
                        } while (Character.isDigit(string2.charAt(n4)));
                        n2 = n4;
                    }
                }
            }
            int n6 = 0;
            int n7 = n3;
            int n8 = n5;
            if (n > n2) {
                n4 = string2.charAt(n2);
                if (n4 != 43) {
                    if (n4 != 45) {
                        if (n4 != 90) throw new TimeFormatException(String.format("Unexpected character 0x%02d at position %d.  Expected + or -", n4, n2));
                        n4 = 0;
                    } else {
                        n4 = 1;
                    }
                } else {
                    n4 = -1;
                }
                bl2 = bl = true;
                n7 = n3;
                n8 = n5;
                n6 = n4;
                if (n4 != 0) {
                    if (n < n2 + 6) throw new TimeFormatException(String.format("Unexpected length; should be %d characters", n2 + 6));
                    n7 = n3 + (Time.getChar(string2, n2 + 1, 10) + Time.getChar(string2, n2 + 2, 1)) * n4;
                    n8 = n5 + (Time.getChar(string2, n2 + 4, 10) + Time.getChar(string2, n2 + 5, 1)) * n4;
                    bl2 = bl;
                    n6 = n4;
                }
            }
            this.hour = n7;
            this.minute = n8;
            if (n6 != 0) {
                this.normalize(false);
            }
        } else {
            this.allDay = true;
            this.hour = 0;
            this.minute = 0;
            this.second = 0;
            bl2 = bl;
        }
        this.weekDay = 0;
        this.yearDay = 0;
        this.isDst = -1;
        this.gmtoff = 0L;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean parseInternal(String string2) {
        int n = string2.length();
        if (n < 8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("String is too short: \"");
            stringBuilder.append(string2);
            stringBuilder.append("\" Expected at least 8 characters.");
            throw new TimeFormatException(stringBuilder.toString());
        }
        boolean bl = false;
        this.year = Time.getChar(string2, 0, 1000) + Time.getChar(string2, 1, 100) + Time.getChar(string2, 2, 10) + Time.getChar(string2, 3, 1);
        this.month = Time.getChar(string2, 4, 10) + Time.getChar(string2, 5, 1) - 1;
        this.monthDay = Time.getChar(string2, 6, 10) + Time.getChar(string2, 7, 1);
        if (n > 8) {
            if (n < 15) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("String is too short: \"");
                stringBuilder.append(string2);
                stringBuilder.append("\" If there are more than 8 characters there must be at least 15.");
                throw new TimeFormatException(stringBuilder.toString());
            }
            this.checkChar(string2, 8, 'T');
            this.allDay = false;
            this.hour = Time.getChar(string2, 9, 10) + Time.getChar(string2, 10, 1);
            this.minute = Time.getChar(string2, 11, 10) + Time.getChar(string2, 12, 1);
            this.second = Time.getChar(string2, 13, 10) + Time.getChar(string2, 14, 1);
            if (n > 15) {
                this.checkChar(string2, 15, 'Z');
                bl = true;
            }
        } else {
            this.allDay = true;
            this.hour = 0;
            this.minute = 0;
            this.second = 0;
        }
        this.weekDay = 0;
        this.yearDay = 0;
        this.isDst = -1;
        this.gmtoff = 0L;
        return bl;
    }

    public boolean after(Time time) {
        boolean bl = Time.compare(this, time) > 0;
        return bl;
    }

    public boolean before(Time time) {
        boolean bl = Time.compare(this, time) < 0;
        return bl;
    }

    public void clear(String string2) {
        if (string2 != null) {
            this.timezone = string2;
            this.allDay = false;
            this.second = 0;
            this.minute = 0;
            this.hour = 0;
            this.monthDay = 0;
            this.month = 0;
            this.year = 0;
            this.weekDay = 0;
            this.yearDay = 0;
            this.gmtoff = 0L;
            this.isDst = -1;
            return;
        }
        throw new NullPointerException("timezone is null!");
    }

    public String format(String string2) {
        this.calculator.copyFieldsFromTime(this);
        return this.calculator.format(string2);
    }

    public String format2445() {
        this.calculator.copyFieldsFromTime(this);
        return this.calculator.format2445(this.allDay ^ true);
    }

    public String format3339(boolean bl) {
        if (bl) {
            return this.format(Y_M_D);
        }
        if (TIMEZONE_UTC.equals(this.timezone)) {
            return this.format(Y_M_D_T_H_M_S_000_Z);
        }
        String string2 = this.format(Y_M_D_T_H_M_S_000);
        String string3 = this.gmtoff < 0L ? "-" : "+";
        int n = (int)Math.abs(this.gmtoff);
        int n2 = n % 3600 / 60;
        return String.format(Locale.US, "%s%s%02d:%02d", string2, string3, n /= 3600, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getActualMaximum(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("bad field=");
                stringBuilder.append(n);
                throw new RuntimeException(stringBuilder.toString());
            }
            case 9: {
                throw new RuntimeException("WEEK_NUM not implemented");
            }
            case 8: {
                n = this.year;
                if (n % 4 != 0) return 364;
                if (n % 100 != 0) return 365;
                if (n % 400 != 0) return 364;
                return 365;
            }
            case 7: {
                return 6;
            }
            case 6: {
                return 2037;
            }
            case 5: {
                return 11;
            }
            case 4: {
                n = DAYS_PER_MONTH[this.month];
                int n2 = 28;
                if (n != 28) {
                    return n;
                }
                int n3 = this.year;
                n = n2;
                if (n3 % 4 != 0) return n;
                if (n3 % 100 != 0) return 29;
                n = n2;
                if (n3 % 400 != 0) return n;
                return 29;
            }
            case 3: {
                return 23;
            }
            case 2: {
                return 59;
            }
            case 1: 
        }
        return 59;
    }

    public int getWeekNumber() {
        int n = this.yearDay + sThursdayOffset[this.weekDay];
        if (n >= 0 && n <= 364) {
            return n / 7 + 1;
        }
        Time time = new Time(this);
        time.monthDay += sThursdayOffset[this.weekDay];
        time.normalize(true);
        return time.yearDay / 7 + 1;
    }

    public long normalize(boolean bl) {
        this.calculator.copyFieldsFromTime(this);
        long l = this.calculator.toMillis(bl);
        this.calculator.copyFieldsToTime(this);
        return l;
    }

    public boolean parse(String string2) {
        if (string2 != null) {
            if (this.parseInternal(string2)) {
                this.timezone = TIMEZONE_UTC;
                return true;
            }
            return false;
        }
        throw new NullPointerException("time string is null");
    }

    public boolean parse3339(String string2) {
        if (string2 != null) {
            if (this.parse3339Internal(string2)) {
                this.timezone = TIMEZONE_UTC;
                return true;
            }
            return false;
        }
        throw new NullPointerException("time string is null");
    }

    public void set(int n, int n2, int n3) {
        this.allDay = true;
        this.second = 0;
        this.minute = 0;
        this.hour = 0;
        this.monthDay = n;
        this.month = n2;
        this.year = n3;
        this.weekDay = 0;
        this.yearDay = 0;
        this.isDst = -1;
        this.gmtoff = 0L;
    }

    public void set(int n, int n2, int n3, int n4, int n5, int n6) {
        this.allDay = false;
        this.second = n;
        this.minute = n2;
        this.hour = n3;
        this.monthDay = n4;
        this.month = n5;
        this.year = n6;
        this.weekDay = 0;
        this.yearDay = 0;
        this.isDst = -1;
        this.gmtoff = 0L;
    }

    public void set(long l) {
        this.allDay = false;
        TimeCalculator timeCalculator = this.calculator;
        timeCalculator.timezone = this.timezone;
        timeCalculator.setTimeInMillis(l);
        this.calculator.copyFieldsToTime(this);
    }

    public void set(Time time) {
        this.timezone = time.timezone;
        this.allDay = time.allDay;
        this.second = time.second;
        this.minute = time.minute;
        this.hour = time.hour;
        this.monthDay = time.monthDay;
        this.month = time.month;
        this.year = time.year;
        this.weekDay = time.weekDay;
        this.yearDay = time.yearDay;
        this.isDst = time.isDst;
        this.gmtoff = time.gmtoff;
    }

    public long setJulianDay(int n) {
        long l = (long)(n - 2440588) * 86400000L;
        this.set(l);
        int n2 = Time.getJulianDay(l, this.gmtoff);
        this.monthDay += n - n2;
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        return this.normalize(true);
    }

    public void setToNow() {
        this.set(System.currentTimeMillis());
    }

    public void switchTimezone(String string2) {
        this.calculator.copyFieldsFromTime(this);
        this.calculator.switchTimeZone(string2);
        this.calculator.copyFieldsToTime(this);
        this.timezone = string2;
    }

    public long toMillis(boolean bl) {
        this.calculator.copyFieldsFromTime(this);
        return this.calculator.toMillis(bl);
    }

    public String toString() {
        TimeCalculator timeCalculator = new TimeCalculator(this.timezone);
        timeCalculator.copyFieldsFromTime(this);
        return timeCalculator.toStringInternal();
    }

    private static class TimeCalculator {
        public String timezone;
        public final ZoneInfo.WallTime wallTime;
        private ZoneInfo zoneInfo;

        public TimeCalculator(String string2) {
            this.zoneInfo = TimeCalculator.lookupZoneInfo(string2);
            this.wallTime = new ZoneInfo.WallTime();
        }

        public static int compare(TimeCalculator timeCalculator, TimeCalculator timeCalculator2) {
            boolean bl = timeCalculator.timezone.equals(timeCalculator2.timezone);
            int n = 0;
            if (bl) {
                n = timeCalculator.wallTime.getYear() - timeCalculator2.wallTime.getYear();
                if (n != 0) {
                    return n;
                }
                n = timeCalculator.wallTime.getMonth() - timeCalculator2.wallTime.getMonth();
                if (n != 0) {
                    return n;
                }
                n = timeCalculator.wallTime.getMonthDay() - timeCalculator2.wallTime.getMonthDay();
                if (n != 0) {
                    return n;
                }
                n = timeCalculator.wallTime.getHour() - timeCalculator2.wallTime.getHour();
                if (n != 0) {
                    return n;
                }
                n = timeCalculator.wallTime.getMinute() - timeCalculator2.wallTime.getMinute();
                if (n != 0) {
                    return n;
                }
                n = timeCalculator.wallTime.getSecond() - timeCalculator2.wallTime.getSecond();
                if (n != 0) {
                    return n;
                }
                return 0;
            }
            long l = timeCalculator.toMillis(false) - timeCalculator2.toMillis(false);
            if (l < 0L) {
                n = -1;
            } else if (l > 0L) {
                n = 1;
            }
            return n;
        }

        private static ZoneInfo lookupZoneInfo(String string2) {
            Object object;
            Object object2;
            block5 : {
                try {
                    object = ZoneInfoDB.getInstance().makeTimeZone(string2);
                    object2 = object;
                    if (object != null) break block5;
                }
                catch (IOException iOException) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Error loading timezone: \"");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append("\"");
                    throw new AssertionError(((StringBuilder)object2).toString(), iOException);
                }
                object2 = ZoneInfoDB.getInstance().makeTimeZone("GMT");
            }
            if (object2 != null) {
                return object2;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("GMT not found: \"");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("\"");
            object = new AssertionError((Object)((StringBuilder)object2).toString());
            throw object;
        }

        private char toChar(int n) {
            int n2 = n >= 0 && n <= 9 ? (n = (int)((char)(n + 48))) : (n = 32);
            return (char)n2;
        }

        private void updateZoneInfoFromTimeZone() {
            if (!this.zoneInfo.getID().equals(this.timezone)) {
                this.zoneInfo = TimeCalculator.lookupZoneInfo(this.timezone);
            }
        }

        public void copyFieldsFromTime(Time time) {
            this.wallTime.setSecond(time.second);
            this.wallTime.setMinute(time.minute);
            this.wallTime.setHour(time.hour);
            this.wallTime.setMonthDay(time.monthDay);
            this.wallTime.setMonth(time.month);
            this.wallTime.setYear(time.year);
            this.wallTime.setWeekDay(time.weekDay);
            this.wallTime.setYearDay(time.yearDay);
            this.wallTime.setIsDst(time.isDst);
            this.wallTime.setGmtOffset((int)time.gmtoff);
            if (time.allDay && (time.second != 0 || time.minute != 0 || time.hour != 0)) {
                throw new IllegalArgumentException("allDay is true but sec, min, hour are not 0.");
            }
            this.timezone = time.timezone;
            this.updateZoneInfoFromTimeZone();
        }

        public void copyFieldsToTime(Time time) {
            time.second = this.wallTime.getSecond();
            time.minute = this.wallTime.getMinute();
            time.hour = this.wallTime.getHour();
            time.monthDay = this.wallTime.getMonthDay();
            time.month = this.wallTime.getMonth();
            time.year = this.wallTime.getYear();
            time.weekDay = this.wallTime.getWeekDay();
            time.yearDay = this.wallTime.getYearDay();
            time.isDst = this.wallTime.getIsDst();
            time.gmtoff = this.wallTime.getGmtOffset();
        }

        public String format(String string2) {
            String string3 = string2;
            if (string2 == null) {
                string3 = "%c";
            }
            return new TimeFormatter().format(string3, this.wallTime, this.zoneInfo);
        }

        public String format2445(boolean bl) {
            int n = bl ? 16 : 8;
            char[] arrc = new char[n];
            n = this.wallTime.getYear();
            arrc[0] = this.toChar(n / 1000);
            arrc[1] = this.toChar((n %= 1000) / 100);
            arrc[2] = this.toChar((n %= 100) / 10);
            arrc[3] = this.toChar(n % 10);
            n = this.wallTime.getMonth() + 1;
            arrc[4] = this.toChar(n / 10);
            arrc[5] = this.toChar(n % 10);
            n = this.wallTime.getMonthDay();
            arrc[6] = this.toChar(n / 10);
            arrc[7] = this.toChar(n % 10);
            if (!bl) {
                return new String(arrc, 0, 8);
            }
            arrc[8] = (char)84;
            n = this.wallTime.getHour();
            arrc[9] = this.toChar(n / 10);
            arrc[10] = this.toChar(n % 10);
            n = this.wallTime.getMinute();
            arrc[11] = this.toChar(n / 10);
            arrc[12] = this.toChar(n % 10);
            n = this.wallTime.getSecond();
            arrc[13] = this.toChar(n / 10);
            arrc[14] = this.toChar(n % 10);
            if (Time.TIMEZONE_UTC.equals(this.timezone)) {
                arrc[15] = (char)90;
                return new String(arrc, 0, 16);
            }
            return new String(arrc, 0, 15);
        }

        public void setTimeInMillis(long l) {
            int n = (int)(l / 1000L);
            this.updateZoneInfoFromTimeZone();
            this.wallTime.localtime(n, this.zoneInfo);
        }

        public void switchTimeZone(String string2) {
            int n = this.wallTime.mktime(this.zoneInfo);
            this.timezone = string2;
            this.updateZoneInfoFromTimeZone();
            this.wallTime.localtime(n, this.zoneInfo);
        }

        public long toMillis(boolean bl) {
            int n;
            if (bl) {
                this.wallTime.setIsDst(-1);
            }
            if ((n = this.wallTime.mktime(this.zoneInfo)) == -1) {
                return -1L;
            }
            return (long)n * 1000L;
        }

        public String toStringInternal() {
            return String.format("%04d%02d%02dT%02d%02d%02d%s(%d,%d,%d,%d,%d)", this.wallTime.getYear(), this.wallTime.getMonth() + 1, this.wallTime.getMonthDay(), this.wallTime.getHour(), this.wallTime.getMinute(), this.wallTime.getSecond(), this.timezone, this.wallTime.getWeekDay(), this.wallTime.getYearDay(), this.wallTime.getGmtOffset(), this.wallTime.getIsDst(), this.toMillis(false) / 1000L);
        }
    }

}


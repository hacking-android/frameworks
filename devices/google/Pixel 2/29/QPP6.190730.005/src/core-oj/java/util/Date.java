/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import sun.util.calendar.AbstractCalendar;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;

public class Date
implements Serializable,
Cloneable,
Comparable<Date> {
    private static int defaultCenturyStart = 0;
    private static final BaseCalendar gcal = CalendarSystem.getGregorianCalendar();
    private static BaseCalendar jcal;
    private static final long serialVersionUID = 7523967970034938905L;
    private static final int[] ttb;
    private static final String[] wtb;
    private transient BaseCalendar.Date cdate;
    private transient long fastTime;

    static {
        wtb = new String[]{"am", "pm", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december", "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt"};
        ttb = new int[]{14, 1, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 10000, 10000, 10000, 10300, 10240, 10360, 10300, 10420, 10360, 10480, 10420};
    }

    public Date() {
        this(System.currentTimeMillis());
    }

    @Deprecated
    public Date(int n, int n2, int n3) {
        this(n, n2, n3, 0, 0, 0);
    }

    @Deprecated
    public Date(int n, int n2, int n3, int n4, int n5) {
        this(n, n2, n3, n4, n5, 0);
    }

    @Deprecated
    public Date(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8 = n + 1900;
        if (n2 >= 12) {
            n7 = n8 + n2 / 12;
            n = n2 % 12;
        } else {
            n7 = n8;
            n = n2;
            if (n2 < 0) {
                n7 = n8 + CalendarUtils.floorDivide(n2, 12);
                n = CalendarUtils.mod(n2, 12);
            }
        }
        this.cdate = (BaseCalendar.Date)Date.getCalendarSystem(n7).newCalendarDate(TimeZone.getDefaultRef());
        this.cdate.setNormalizedDate(n7, n + 1, n3).setTimeOfDay(n4, n5, n6, 0);
        this.getTimeImpl();
        this.cdate = null;
    }

    public Date(long l) {
        this.fastTime = l;
    }

    @Deprecated
    public Date(String string) {
        this(Date.parse(string));
    }

    @Deprecated
    public static long UTC(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8 = n + 1900;
        if (n2 >= 12) {
            n = n8 + n2 / 12;
            n7 = n2 % 12;
        } else {
            n = n8;
            n7 = n2;
            if (n2 < 0) {
                n = n8 + CalendarUtils.floorDivide(n2, 12);
                n7 = CalendarUtils.mod(n2, 12);
            }
        }
        BaseCalendar.Date date = (BaseCalendar.Date)Date.getCalendarSystem(n).newCalendarDate(null);
        date.setNormalizedDate(n, n7 + 1, n3).setTimeOfDay(n4, n5, n6, 0);
        Date date2 = new Date(0L);
        date2.normalize(date);
        return date2.fastTime;
    }

    private static final StringBuilder convertToAbbr(StringBuilder stringBuilder, String string) {
        stringBuilder.append(Character.toUpperCase(string.charAt(0)));
        stringBuilder.append(string.charAt(1));
        stringBuilder.append(string.charAt(2));
        return stringBuilder;
    }

    public static Date from(Instant comparable) {
        try {
            comparable = new Date(comparable.toEpochMilli());
            return comparable;
        }
        catch (ArithmeticException arithmeticException) {
            throw new IllegalArgumentException(arithmeticException);
        }
    }

    private final BaseCalendar.Date getCalendarDate() {
        if (this.cdate == null) {
            this.cdate = (BaseCalendar.Date)Date.getCalendarSystem(this.fastTime).getCalendarDate(this.fastTime, TimeZone.getDefaultRef());
        }
        return this.cdate;
    }

    private static final BaseCalendar getCalendarSystem(int n) {
        if (n >= 1582) {
            return gcal;
        }
        return Date.getJulianCalendar();
    }

    private static final BaseCalendar getCalendarSystem(long l) {
        if (l < 0L && l < -12219292800000L - (long)TimeZone.getDefaultRef().getOffset(l)) {
            return Date.getJulianCalendar();
        }
        return gcal;
    }

    private static final BaseCalendar getCalendarSystem(BaseCalendar.Date date) {
        if (jcal == null) {
            return gcal;
        }
        if (date.getEra() != null) {
            return jcal;
        }
        return gcal;
    }

    private static final BaseCalendar getJulianCalendar() {
        synchronized (Date.class) {
            if (jcal == null) {
                jcal = (BaseCalendar)CalendarSystem.forName("julian");
            }
            BaseCalendar baseCalendar = jcal;
            return baseCalendar;
        }
    }

    static final long getMillisOf(Date cloneable) {
        BaseCalendar.Date date = cloneable.cdate;
        if (date != null && !date.isNormalized()) {
            cloneable = (BaseCalendar.Date)cloneable.cdate.clone();
            return gcal.getTime((CalendarDate)cloneable);
        }
        return cloneable.fastTime;
    }

    private final long getTimeImpl() {
        BaseCalendar.Date date = this.cdate;
        if (date != null && !date.isNormalized()) {
            this.normalize();
        }
        return this.fastTime;
    }

    private final BaseCalendar.Date normalize() {
        Cloneable cloneable = this.cdate;
        if (cloneable == null) {
            this.cdate = (BaseCalendar.Date)Date.getCalendarSystem(this.fastTime).getCalendarDate(this.fastTime, TimeZone.getDefaultRef());
            return this.cdate;
        }
        if (!cloneable.isNormalized()) {
            this.cdate = this.normalize(this.cdate);
        }
        if ((cloneable = TimeZone.getDefaultRef()) != this.cdate.getZone()) {
            this.cdate.setZone((TimeZone)cloneable);
            ((CalendarSystem)Date.getCalendarSystem(this.cdate)).getCalendarDate(this.fastTime, this.cdate);
        }
        return this.cdate;
    }

    private final BaseCalendar.Date normalize(BaseCalendar.Date cloneable) {
        int n = ((BaseCalendar.Date)cloneable).getNormalizedYear();
        int n2 = ((CalendarDate)cloneable).getMonth();
        int n3 = ((CalendarDate)cloneable).getDayOfMonth();
        int n4 = ((CalendarDate)cloneable).getHours();
        int n5 = ((CalendarDate)cloneable).getMinutes();
        int n6 = ((CalendarDate)cloneable).getSeconds();
        int n7 = ((CalendarDate)cloneable).getMillis();
        Cloneable cloneable2 = ((CalendarDate)cloneable).getZone();
        if (n != 1582 && n <= 280000000 && n >= -280000000) {
            BaseCalendar baseCalendar = Date.getCalendarSystem(n);
            if (baseCalendar != Date.getCalendarSystem((BaseCalendar.Date)cloneable)) {
                cloneable = (BaseCalendar.Date)baseCalendar.newCalendarDate((TimeZone)cloneable2);
                ((BaseCalendar.Date)cloneable).setNormalizedDate(n, n2, n3).setTimeOfDay(n4, n5, n6, n7);
            }
            this.fastTime = baseCalendar.getTime((CalendarDate)cloneable);
            BaseCalendar baseCalendar2 = Date.getCalendarSystem(this.fastTime);
            if (baseCalendar2 != baseCalendar) {
                cloneable = (BaseCalendar.Date)baseCalendar2.newCalendarDate((TimeZone)cloneable2);
                ((BaseCalendar.Date)cloneable).setNormalizedDate(n, n2, n3).setTimeOfDay(n4, n5, n6, n7);
                this.fastTime = baseCalendar2.getTime((CalendarDate)cloneable);
            }
            return cloneable;
        }
        cloneable = cloneable2 == null ? TimeZone.getTimeZone("GMT") : cloneable2;
        cloneable2 = new GregorianCalendar((TimeZone)cloneable);
        ((Calendar)cloneable2).clear();
        ((Calendar)cloneable2).set(14, n7);
        ((Calendar)cloneable2).set(n, n2 - 1, n3, n4, n5, n6);
        this.fastTime = ((Calendar)cloneable2).getTimeInMillis();
        return (BaseCalendar.Date)Date.getCalendarSystem(this.fastTime).getCalendarDate(this.fastTime, (TimeZone)cloneable);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public static long parse(String var0) {
        var1_1 = 0;
        if (var0 == null) throw new IllegalArgumentException();
        var2_2 = var0.length();
        var3_3 = -1;
        var4_4 = -1;
        var5_5 = 0;
        var6_6 = Integer.MIN_VALUE;
        var7_7 = -1;
        var8_8 = -1;
        var9_9 = -1;
        var10_10 = -1;
        block3 : do {
            block57 : {
                block56 : {
                    var11_11 = var0;
                    if (var1_1 >= var2_2) ** GOTO lbl-1000
                    var12_12 = var11_11.charAt(var1_1);
                    ++var1_1;
                    if (var12_12 <= 32 || var12_12 == 44) continue;
                    if (var12_12 != 40) {
                        if (48 <= var12_12 && var12_12 <= 57) {
                            var13_13 = var12_12 - 48;
                            do {
                                var14_14 = var12_12;
                                if (var1_1 >= var2_2) break;
                                var14_14 = var12_12 = (var15_15 = var11_11.charAt(var1_1));
                                if (48 > var15_15) break;
                                var14_14 = var12_12;
                                if (var12_12 > 57) break;
                                var13_13 = var13_13 * 10 + var12_12 - 48;
                                ++var1_1;
                            } while (true);
                            if (var5_5 != 43 && (var5_5 != 45 || var6_6 == Integer.MIN_VALUE)) {
                                if (var13_13 >= 70) {
                                    if (var6_6 != Integer.MIN_VALUE) throw new IllegalArgumentException();
                                    if (var14_14 > 32 && var14_14 != 44 && var14_14 != 47) {
                                        if (var1_1 < var2_2) throw new IllegalArgumentException();
                                    }
                                    var6_6 = var13_13;
                                    var13_13 = var7_7;
                                } else if (var14_14 == 58) {
                                    if (var8_8 < 0) {
                                        var8_8 = (byte)var13_13;
                                        var13_13 = var7_7;
                                    } else {
                                        if (var3_3 >= 0) throw new IllegalArgumentException();
                                        var3_3 = (byte)var13_13;
                                        var13_13 = var7_7;
                                    }
                                } else if (var14_14 == 47) {
                                    if (var10_10 < 0) {
                                        var10_10 = (byte)(var13_13 - 1);
                                        var13_13 = var7_7;
                                    } else {
                                        if (var7_7 >= 0) throw new IllegalArgumentException();
                                        var13_13 = (byte)var13_13;
                                    }
                                } else {
                                    if (var1_1 < var2_2 && var14_14 != 44 && var14_14 > 32) {
                                        if (var14_14 != 45) throw new IllegalArgumentException();
                                    }
                                    if (var8_8 >= 0 && var3_3 < 0) {
                                        var3_3 = (byte)var13_13;
                                        var13_13 = var7_7;
                                    } else if (var3_3 >= 0 && var4_4 < 0) {
                                        var4_4 = (byte)var13_13;
                                        var13_13 = var7_7;
                                    } else if (var7_7 < 0) {
                                        var13_13 = (byte)var13_13;
                                    } else {
                                        if (var6_6 != Integer.MIN_VALUE) throw new IllegalArgumentException();
                                        if (var10_10 < 0) throw new IllegalArgumentException();
                                        if (var7_7 < 0) throw new IllegalArgumentException();
                                        var6_6 = var13_13;
                                        var13_13 = var7_7;
                                    }
                                }
                            } else {
                                if (var9_9 != 0) {
                                    if (var9_9 != -1) throw new IllegalArgumentException();
                                }
                                if (var13_13 < 24) {
                                    var15_15 = 0;
                                    var12_12 = 0;
                                    var14_14 = var15_15;
                                    var9_9 = var1_1;
                                    if (var1_1 < var2_2) {
                                        var14_14 = var15_15;
                                        var9_9 = var1_1;
                                        if (var11_11.charAt(var1_1) == ':') {
                                            ++var1_1;
                                            do {
                                                var14_14 = var12_12;
                                                var9_9 = var1_1;
                                                if (var1_1 >= var2_2) break;
                                                var15_15 = var11_11.charAt(var1_1);
                                                var14_14 = var12_12;
                                                var9_9 = var1_1;
                                                if (48 > var15_15) break;
                                                var14_14 = var12_12;
                                                var9_9 = var1_1++;
                                                if (var15_15 > 57) break;
                                                var12_12 = var12_12 * 10 + (var15_15 - 48);
                                            } while (true);
                                        }
                                    }
                                    var13_13 = var13_13 * 60 + var14_14;
                                    var1_1 = var9_9;
                                    var9_9 = var13_13;
                                } else {
                                    var9_9 = var13_13 % 100 + var13_13 / 100 * 60;
                                }
                                var13_13 = var9_9;
                                if (var5_5 == 43) {
                                    var13_13 = -var9_9;
                                }
                                var9_9 = var13_13;
                                var13_13 = var7_7;
                            }
                            var5_5 = 0;
                            var7_7 = var13_13;
                            continue;
                        }
                        if (var12_12 != 47 && var12_12 != 58 && var12_12 != 43 && var12_12 != 45) {
                            var15_15 = var1_1 - 1;
                        } else {
                            var5_5 = var12_12;
                            continue;
                        }
                    }
                    ** GOTO lbl150
lbl-1000: // 1 sources:
                    {
                        if (var6_6 == Integer.MIN_VALUE) throw new IllegalArgumentException();
                        if (var10_10 < 0) throw new IllegalArgumentException();
                        if (var7_7 < 0) throw new IllegalArgumentException();
                        var5_5 = var6_6;
                        if (var6_6 < 100) {
                            // MONITORENTER : java.util.Date.class
                            if (Date.defaultCenturyStart == 0) {
                                Date.defaultCenturyStart = Date.gcal.getCalendarDate().getYear() - 80;
                            }
                            // MONITOREXIT : java.util.Date.class
                            var13_13 = Date.defaultCenturyStart;
                            var5_5 = var1_1 = var6_6 + var13_13 / 100 * 100;
                            if (var1_1 < var13_13) {
                                var5_5 = var1_1 + 100;
                            }
                        }
                        var1_1 = var4_4 < 0 ? 0 : var4_4;
                        var13_13 = var3_3 < 0 ? 0 : var3_3;
                        if (var8_8 < 0) {
                            var8_8 = 0;
                        }
                        var0 = Date.getCalendarSystem(var5_5);
                        if (var9_9 == -1) {
                            var11_11 = (BaseCalendar.Date)var0.newCalendarDate(TimeZone.getDefaultRef());
                            var11_11.setDate(var5_5, var10_10 + 1, var7_7);
                            var11_11.setTimeOfDay(var8_8, var13_13, var1_1, 0);
                            return var0.getTime((CalendarDate)var11_11);
                        }
                        var11_11 = (BaseCalendar.Date)var0.newCalendarDate(null);
                        var11_11.setDate(var5_5, var10_10 + 1, var7_7);
                        var11_11.setTimeOfDay(var8_8, var13_13, var1_1, 0);
                        return var0.getTime((CalendarDate)var11_11) + (long)(60000 * var9_9);
lbl150: // 1 sources:
                        var12_12 = 1;
                        var13_13 = var1_1;
                        do {
                            var1_1 = var13_13;
                            if (var13_13 >= var2_2) continue block3;
                            var14_14 = var11_11.charAt(var13_13);
                            var1_1 = var13_13 + 1;
                            if (var14_14 == 40) {
                                ++var12_12;
                                var13_13 = var1_1;
                                continue;
                            }
                            var13_13 = var1_1;
                            if (var14_14 != 41) continue;
                            var12_12 = var14_14 = var12_12 - 1;
                            var13_13 = var1_1;
                            if (var14_14 <= 0) break;
                        } while (true);
                        continue;
                    }
                    for (var13_13 = var1_1; var13_13 < var2_2; ++var13_13) {
                        var12_12 = var11_11.charAt(var13_13);
                        if (65 <= var12_12 && var12_12 <= 90 || 97 <= var12_12 && var12_12 <= 122) {
                            continue;
                        }
                        var1_1 = var12_12;
                        break block56;
                    }
                    var1_1 = var12_12;
                }
                if (var13_13 <= var15_15 + 1) {
                    var12_12 = var9_9;
                    var12_12 = var8_8;
                    throw new IllegalArgumentException();
                }
                var14_14 = Date.wtb.length;
                var12_12 = var5_5;
                var1_1 = var8_8;
                var5_5 = var9_9;
                var8_8 = var14_14;
                while ((var14_14 = var8_8 - 1) >= 0) {
                    var11_11 = Date.wtb[var14_14];
                    var8_8 = var5_5;
                    var9_9 = var1_1;
                    if (var11_11.regionMatches(true, 0, (String)var0, var15_15, var13_13 - var15_15)) {
                        var15_15 = Date.ttb[var14_14];
                        if (var15_15 == 0) break;
                        if (var15_15 == 1) {
                            var12_12 = var9_9;
                            var12_12 = var8_8;
                            if (var9_9 > 12) throw new IllegalArgumentException();
                            if (var9_9 < 1) {
                                var12_12 = var9_9;
                                var12_12 = var8_8;
                                throw new IllegalArgumentException();
                            }
                            if (var9_9 >= 12) break;
                            var5_5 = var9_9 + 12;
                            var9_9 = var8_8;
                            var8_8 = var5_5;
                        } else if (var15_15 == 14) {
                            var12_12 = var9_9;
                            var12_12 = var8_8;
                            if (var9_9 > 12) throw new IllegalArgumentException();
                            if (var9_9 < 1) {
                                var12_12 = var9_9;
                                var12_12 = var8_8;
                                throw new IllegalArgumentException();
                            }
                            if (var9_9 != 12) break;
                            var5_5 = 0;
                            var9_9 = var8_8;
                            var8_8 = var5_5;
                        } else if (var15_15 <= 13) {
                            var12_12 = var9_9;
                            var12_12 = var8_8;
                            if (var10_10 >= 0) throw new IllegalArgumentException();
                            var10_10 = (byte)(var15_15 - 2);
                            var5_5 = var9_9;
                            var9_9 = var8_8;
                            var8_8 = var5_5;
                        } else {
                            var5_5 = var15_15 - 10000;
                            var8_8 = var9_9;
                            var9_9 = var5_5;
                        }
                        break block57;
                    }
                    var5_5 = var14_14;
                    var1_1 = var8_8;
                    var8_8 = var5_5;
                    var5_5 = var1_1;
                    var1_1 = var9_9;
                }
                var8_8 = var1_1;
                var9_9 = var5_5;
            }
            if (var14_14 < 0) throw new IllegalArgumentException();
            var5_5 = 0;
            var1_1 = var13_13;
        } while (true);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.fastTime = objectInputStream.readLong();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeLong(this.getTimeImpl());
    }

    public boolean after(Date date) {
        boolean bl = Date.getMillisOf(this) > Date.getMillisOf(date);
        return bl;
    }

    public boolean before(Date date) {
        boolean bl = Date.getMillisOf(this) < Date.getMillisOf(date);
        return bl;
    }

    public Object clone() {
        Date date;
        block4 : {
            Date date2;
            Date date3 = null;
            date3 = date2 = (Date)super.clone();
            date = date2;
            if (this.cdate == null) break block4;
            date3 = date2;
            try {
                date2.cdate = (BaseCalendar.Date)this.cdate.clone();
                date = date2;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                date = date3;
            }
        }
        return date;
    }

    @Override
    public int compareTo(Date date) {
        long l;
        long l2 = Date.getMillisOf(this);
        int n = l2 < (l = Date.getMillisOf(date)) ? -1 : (l2 == l ? 0 : 1);
        return n;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Date && this.getTime() == ((Date)object).getTime();
        return bl;
    }

    @Deprecated
    public int getDate() {
        return this.normalize().getDayOfMonth();
    }

    @Deprecated
    public int getDay() {
        return this.normalize().getDayOfWeek() - 1;
    }

    @Deprecated
    public int getHours() {
        return this.normalize().getHours();
    }

    @Deprecated
    public int getMinutes() {
        return this.normalize().getMinutes();
    }

    @Deprecated
    public int getMonth() {
        return this.normalize().getMonth() - 1;
    }

    @Deprecated
    public int getSeconds() {
        return this.normalize().getSeconds();
    }

    public long getTime() {
        return this.getTimeImpl();
    }

    @Deprecated
    public int getTimezoneOffset() {
        int n;
        if (this.cdate == null) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar(this.fastTime);
            n = gregorianCalendar.get(15) + gregorianCalendar.get(16);
        } else {
            this.normalize();
            n = this.cdate.getZoneOffset();
        }
        return -n / 60000;
    }

    @Deprecated
    public int getYear() {
        return this.normalize().getYear() - 1900;
    }

    public int hashCode() {
        long l = this.getTime();
        return (int)l ^ (int)(l >> 32);
    }

    @Deprecated
    public void setDate(int n) {
        this.getCalendarDate().setDayOfMonth(n);
    }

    @Deprecated
    public void setHours(int n) {
        this.getCalendarDate().setHours(n);
    }

    @Deprecated
    public void setMinutes(int n) {
        this.getCalendarDate().setMinutes(n);
    }

    @Deprecated
    public void setMonth(int n) {
        int n2;
        int n3 = 0;
        if (n >= 12) {
            n3 = n / 12;
            n2 = n % 12;
        } else {
            n2 = n;
            if (n < 0) {
                n3 = CalendarUtils.floorDivide(n, 12);
                n2 = CalendarUtils.mod(n, 12);
            }
        }
        BaseCalendar.Date date = this.getCalendarDate();
        if (n3 != 0) {
            date.setNormalizedYear(date.getNormalizedYear() + n3);
        }
        date.setMonth(n2 + 1);
    }

    @Deprecated
    public void setSeconds(int n) {
        this.getCalendarDate().setSeconds(n);
    }

    public void setTime(long l) {
        this.fastTime = l;
        this.cdate = null;
    }

    @Deprecated
    public void setYear(int n) {
        this.getCalendarDate().setNormalizedYear(n + 1900);
    }

    @Deprecated
    public String toGMTString() {
        Object object = Date.getCalendarSystem(this.getTime());
        object = (BaseCalendar.Date)((AbstractCalendar)object).getCalendarDate(this.getTime(), (TimeZone)null);
        StringBuilder stringBuilder = new StringBuilder(32);
        CalendarUtils.sprintf0d(stringBuilder, ((CalendarDate)object).getDayOfMonth(), 1).append(' ');
        Date.convertToAbbr(stringBuilder, wtb[((CalendarDate)object).getMonth() - 1 + 2 + 7]).append(' ');
        stringBuilder.append(((CalendarDate)object).getYear());
        stringBuilder.append(' ');
        CalendarUtils.sprintf0d(stringBuilder, ((CalendarDate)object).getHours(), 2).append(':');
        CalendarUtils.sprintf0d(stringBuilder, ((CalendarDate)object).getMinutes(), 2).append(':');
        CalendarUtils.sprintf0d(stringBuilder, ((CalendarDate)object).getSeconds(), 2);
        stringBuilder.append(" GMT");
        return stringBuilder.toString();
    }

    public Instant toInstant() {
        return Instant.ofEpochMilli(this.getTime());
    }

    @Deprecated
    public String toLocaleString() {
        return DateFormat.getDateTimeInstance().format(this);
    }

    public String toString() {
        int n;
        BaseCalendar.Date date = this.normalize();
        StringBuilder stringBuilder = new StringBuilder(28);
        int n2 = n = date.getDayOfWeek();
        if (n == 1) {
            n2 = 8;
        }
        Date.convertToAbbr(stringBuilder, wtb[n2]).append(' ');
        Date.convertToAbbr(stringBuilder, wtb[date.getMonth() - 1 + 2 + 7]).append(' ');
        CalendarUtils.sprintf0d(stringBuilder, date.getDayOfMonth(), 2).append(' ');
        CalendarUtils.sprintf0d(stringBuilder, date.getHours(), 2).append(':');
        CalendarUtils.sprintf0d(stringBuilder, date.getMinutes(), 2).append(':');
        CalendarUtils.sprintf0d(stringBuilder, date.getSeconds(), 2).append(' ');
        TimeZone timeZone = date.getZone();
        if (timeZone != null) {
            stringBuilder.append(timeZone.getDisplayName(date.isDaylightTime(), 0, Locale.US));
        } else {
            stringBuilder.append("GMT");
        }
        stringBuilder.append(' ');
        stringBuilder.append(date.getYear());
        return stringBuilder.toString();
    }
}


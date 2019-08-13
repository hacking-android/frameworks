/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.TimeZone;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Gregorian;

public class SimpleTimeZone
extends TimeZone {
    private static final int DOM_MODE = 1;
    private static final int DOW_GE_DOM_MODE = 3;
    private static final int DOW_IN_MONTH_MODE = 2;
    private static final int DOW_LE_DOM_MODE = 4;
    public static final int STANDARD_TIME = 1;
    public static final int UTC_TIME = 2;
    public static final int WALL_TIME = 0;
    static final int currentSerialVersion = 2;
    private static final Gregorian gcal;
    private static final int millisPerDay = 86400000;
    private static final int millisPerHour = 3600000;
    static final long serialVersionUID = -403250971215465050L;
    private static final byte[] staticLeapMonthLength;
    private static final byte[] staticMonthLength;
    private transient long cacheEnd;
    private transient long cacheStart;
    private transient long cacheYear;
    private int dstSavings;
    private int endDay;
    private int endDayOfWeek;
    private int endMode;
    private int endMonth;
    private int endTime;
    private int endTimeMode;
    private final byte[] monthLength = staticMonthLength;
    private int rawOffset;
    private int serialVersionOnStream = 2;
    private int startDay;
    private int startDayOfWeek;
    private int startMode;
    private int startMonth;
    private int startTime;
    private int startTimeMode;
    private int startYear;
    private boolean useDaylight = false;

    static {
        staticMonthLength = new byte[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        staticLeapMonthLength = new byte[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        gcal = CalendarSystem.getGregorianCalendar();
    }

    public SimpleTimeZone(int n, String string) {
        this.rawOffset = n;
        this.setID(string);
        this.dstSavings = 3600000;
    }

    public SimpleTimeZone(int n, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this(n, string, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, 3600000);
    }

    public SimpleTimeZone(int n, String string, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        this(n, string, n2, n3, n4, n5, 0, n6, n7, n8, n9, 0, n10);
    }

    public SimpleTimeZone(int n, String charSequence, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        this.setID((String)charSequence);
        this.rawOffset = n;
        this.startMonth = n2;
        this.startDay = n3;
        this.startDayOfWeek = n4;
        this.startTime = n5;
        this.startTimeMode = n6;
        this.endMonth = n7;
        this.endDay = n8;
        this.endDayOfWeek = n9;
        this.endTime = n10;
        this.endTimeMode = n11;
        this.dstSavings = n12;
        this.decodeRules();
        if (n12 > 0) {
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Illegal daylight saving value: ");
        ((StringBuilder)charSequence).append(n12);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void decodeEndRule() {
        block8 : {
            block9 : {
                block12 : {
                    int n;
                    block11 : {
                        int n2;
                        block10 : {
                            boolean bl = this.startDay != 0 && this.endDay != 0;
                            this.useDaylight = bl;
                            n = this.endDay;
                            if (n == 0) return;
                            n2 = this.endMonth;
                            if (n2 < 0 || n2 > 11) break block8;
                            n2 = this.endTime;
                            if (n2 < 0 || n2 > 86400000) break block9;
                            n2 = this.endDayOfWeek;
                            if (n2 != 0) break block10;
                            this.endMode = 1;
                            break block11;
                        }
                        if (n2 > 0) {
                            this.endMode = 2;
                        } else {
                            this.endDayOfWeek = -n2;
                            if (n > 0) {
                                this.endMode = 3;
                            } else {
                                this.endDay = -n;
                                this.endMode = 4;
                            }
                        }
                        if (this.endDayOfWeek > 7) break block12;
                    }
                    if (this.endMode == 2) {
                        n = this.endDay;
                        if (n >= -5 && n <= 5) return;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Illegal end day of week in month ");
                        stringBuilder.append(this.endDay);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    n = this.endDay;
                    if (n >= 1 && n <= staticMonthLength[this.endMonth]) return;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Illegal end day ");
                    stringBuilder.append(this.endDay);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal end day of week ");
                stringBuilder.append(this.endDayOfWeek);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal end time ");
            stringBuilder.append(this.endTime);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal end month ");
        stringBuilder.append(this.endMonth);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void decodeRules() {
        this.decodeStartRule();
        this.decodeEndRule();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void decodeStartRule() {
        block8 : {
            block9 : {
                block12 : {
                    int n;
                    block11 : {
                        int n2;
                        block10 : {
                            boolean bl = this.startDay != 0 && this.endDay != 0;
                            this.useDaylight = bl;
                            n = this.startDay;
                            if (n == 0) return;
                            n2 = this.startMonth;
                            if (n2 < 0 || n2 > 11) break block8;
                            n2 = this.startTime;
                            if (n2 < 0 || n2 > 86400000) break block9;
                            n2 = this.startDayOfWeek;
                            if (n2 != 0) break block10;
                            this.startMode = 1;
                            break block11;
                        }
                        if (n2 > 0) {
                            this.startMode = 2;
                        } else {
                            this.startDayOfWeek = -n2;
                            if (n > 0) {
                                this.startMode = 3;
                            } else {
                                this.startDay = -n;
                                this.startMode = 4;
                            }
                        }
                        if (this.startDayOfWeek > 7) break block12;
                    }
                    if (this.startMode == 2) {
                        n = this.startDay;
                        if (n >= -5 && n <= 5) return;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Illegal start day of week in month ");
                        stringBuilder.append(this.startDay);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    n = this.startDay;
                    if (n >= 1 && n <= staticMonthLength[this.startMonth]) return;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Illegal start day ");
                    stringBuilder.append(this.startDay);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal start day of week ");
                stringBuilder.append(this.startDayOfWeek);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal start time ");
            stringBuilder.append(this.startTime);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal start month ");
        stringBuilder.append(this.startMonth);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private long getEnd(BaseCalendar baseCalendar, BaseCalendar.Date date, int n) {
        int n2;
        int n3 = n2 = this.endTime;
        if (this.endTimeMode != 2) {
            n3 = n2 - this.rawOffset;
        }
        n2 = n3;
        if (this.endTimeMode == 0) {
            n2 = n3 - this.dstSavings;
        }
        return this.getTransition(baseCalendar, date, this.endMode, n, this.endMonth, this.endDay, this.endDayOfWeek, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getOffset(BaseCalendar baseCalendar, BaseCalendar.Date date, int n, long l) {
        synchronized (this) {
            if (this.cacheStart != 0L) {
                if (l >= this.cacheStart && l < this.cacheEnd) {
                    int n2 = this.rawOffset;
                    n = this.dstSavings;
                    return n2 + n;
                }
                if ((long)n == this.cacheYear) {
                    return this.rawOffset;
                }
            }
        }
        long l2 = this.getStart(baseCalendar, date, n);
        long l3 = this.getEnd(baseCalendar, date, n);
        int n3 = this.rawOffset;
        if (l2 <= l3) {
            if (l >= l2 && l < l3) {
                n3 += this.dstSavings;
            }
            synchronized (this) {
                this.cacheYear = l = (long)n;
                this.cacheStart = l2;
                this.cacheEnd = l3;
                return n3;
            }
        }
        if (l < l3) {
            l2 = this.getStart(baseCalendar, date, n - 1);
            if (l >= l2) {
                n = n3 + this.dstSavings;
                l = l2;
            } else {
                n = n3;
                l = l2;
            }
        } else if (l >= l2) {
            l3 = this.getEnd(baseCalendar, date, n + 1);
            if (l < l3) {
                n = n3 + this.dstSavings;
                l = l2;
            } else {
                n = n3;
                l = l2;
            }
        } else {
            n = n3;
            l = l2;
        }
        n3 = n;
        if (l > l3) return n3;
        synchronized (this) {
            this.cacheYear = (long)this.startYear - 1L;
            this.cacheStart = l;
            this.cacheEnd = l3;
            return n;
        }
    }

    private long getStart(BaseCalendar baseCalendar, BaseCalendar.Date date, int n) {
        int n2;
        int n3 = n2 = this.startTime;
        if (this.startTimeMode != 2) {
            n3 = n2 - this.rawOffset;
        }
        return this.getTransition(baseCalendar, date, this.startMode, n, this.startMonth, this.startDay, this.startDayOfWeek, n3);
    }

    private long getTransition(BaseCalendar baseCalendar, BaseCalendar.Date date, int n, int n2, int n3, int n4, int n5, int n6) {
        date.setNormalizedYear(n2);
        date.setMonth(n3 + 1);
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        date.setDayOfMonth(n4);
                        date = (BaseCalendar.Date)baseCalendar.getNthDayOfWeek(-1, n5, date);
                    }
                } else {
                    date.setDayOfMonth(n4);
                    date = (BaseCalendar.Date)baseCalendar.getNthDayOfWeek(1, n5, date);
                }
            } else {
                date.setDayOfMonth(1);
                if (n4 < 0) {
                    date.setDayOfMonth(baseCalendar.getMonthLength(date));
                }
                date = (BaseCalendar.Date)baseCalendar.getNthDayOfWeek(n4, n5, date);
            }
        } else {
            date.setDayOfMonth(n4);
        }
        return baseCalendar.getTime(date) + (long)n6;
    }

    private void invalidateCache() {
        synchronized (this) {
            this.cacheYear = this.startYear - 1;
            this.cacheEnd = 0L;
            this.cacheStart = 0L;
            return;
        }
    }

    private void makeRulesCompatible() {
        int n = this.startMode;
        if (n != 1) {
            if (n != 3) {
                if (n == 4) {
                    n = this.startDay;
                    this.startDay = n >= 30 ? -1 : n / 7 + 1;
                }
            } else {
                n = this.startDay;
                if (n != 1) {
                    this.startDay = n / 7 + 1;
                }
            }
        } else {
            this.startDay = this.startDay / 7 + 1;
            this.startDayOfWeek = 1;
        }
        if ((n = this.endMode) != 1) {
            if (n != 3) {
                if (n == 4) {
                    n = this.endDay;
                    this.endDay = n >= 30 ? -1 : n / 7 + 1;
                }
            } else {
                n = this.endDay;
                if (n != 1) {
                    this.endDay = n / 7 + 1;
                }
            }
        } else {
            this.endDay = this.endDay / 7 + 1;
            this.endDayOfWeek = 1;
        }
        if (this.startTimeMode == 2) {
            this.startTime += this.rawOffset;
        }
        while ((n = this.startTime) < 0) {
            this.startTime = n + 86400000;
            this.startDayOfWeek = (this.startDayOfWeek + 5) % 7 + 1;
        }
        while ((n = this.startTime) >= 86400000) {
            this.startTime = n - 86400000;
            this.startDayOfWeek = this.startDayOfWeek % 7 + 1;
        }
        n = this.endTimeMode;
        if (n != 1) {
            if (n == 2) {
                this.endTime += this.rawOffset + this.dstSavings;
            }
        } else {
            this.endTime += this.dstSavings;
        }
        while ((n = this.endTime) < 0) {
            this.endTime = n + 86400000;
            this.endDayOfWeek = (this.endDayOfWeek + 5) % 7 + 1;
        }
        while ((n = this.endTime) >= 86400000) {
            this.endTime = n - 86400000;
            this.endDayOfWeek = this.endDayOfWeek % 7 + 1;
        }
    }

    private byte[] packRules() {
        return new byte[]{(byte)this.startDay, (byte)this.startDayOfWeek, (byte)this.endDay, (byte)this.endDayOfWeek, (byte)this.startTimeMode, (byte)this.endTimeMode};
    }

    private int[] packTimes() {
        return new int[]{this.startTime, this.endTime};
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            if (this.startDayOfWeek == 0) {
                this.startDayOfWeek = 1;
            }
            if (this.endDayOfWeek == 0) {
                this.endDayOfWeek = 1;
            }
            this.endMode = 2;
            this.startMode = 2;
            this.dstSavings = 3600000;
        } else {
            byte[] arrby = new byte[objectInputStream.readInt()];
            objectInputStream.readFully(arrby);
            this.unpackRules(arrby);
        }
        if (this.serialVersionOnStream >= 2) {
            this.unpackTimes((int[])objectInputStream.readObject());
        }
        this.serialVersionOnStream = 2;
    }

    private void unpackRules(byte[] arrby) {
        this.startDay = arrby[0];
        this.startDayOfWeek = arrby[1];
        this.endDay = arrby[2];
        this.endDayOfWeek = arrby[3];
        if (arrby.length >= 6) {
            this.startTimeMode = arrby[4];
            this.endTimeMode = arrby[5];
        }
    }

    private void unpackTimes(int[] arrn) {
        this.startTime = arrn[0];
        this.endTime = arrn[1];
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        byte[] arrby = this.packRules();
        int[] arrn = this.packTimes();
        this.makeRulesCompatible();
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(arrby.length);
        objectOutputStream.write(arrby);
        objectOutputStream.writeObject(arrn);
        this.unpackRules(arrby);
        this.unpackTimes(arrn);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof SimpleTimeZone)) {
            return false;
        }
        object = (SimpleTimeZone)object;
        if (!this.getID().equals(((TimeZone)object).getID()) || !this.hasSameRules((TimeZone)object)) {
            bl = false;
        }
        return bl;
    }

    @Override
    public int getDSTSavings() {
        int n = this.useDaylight ? this.dstSavings : 0;
        return n;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        if (n != 1 && n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal era ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int n8 = n7 = n2;
        if (n == 0) {
            n8 = 1 - n7;
        }
        if (n8 >= 292278994) {
            n8 = n8 % 2800 + 2800;
        } else if (n8 <= -292269054) {
            n8 = (int)CalendarUtils.mod((long)n8, 28L);
        }
        BaseCalendar baseCalendar = gcal;
        BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
        date.setDate(n8, ++n3, n4);
        long l = baseCalendar.getTime(date) + (long)(n6 - this.rawOffset);
        if (l < -12219292800000L) {
            baseCalendar = (BaseCalendar)CalendarSystem.forName("julian");
            date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
            date.setNormalizedDate(n8, n3, n4);
            long l2 = baseCalendar.getTime(date);
            long l3 = n6;
            l = this.rawOffset;
            l = l2 + l3 - l;
        }
        if (date.getNormalizedYear() == n8 && date.getMonth() == n3 && date.getDayOfMonth() == n4 && n5 >= 1 && n5 <= 7 && n6 >= 0 && n6 < 86400000) {
            if (this.useDaylight && n2 >= this.startYear && n == 1) {
                return this.getOffset(baseCalendar, date, n8, l);
            }
            return this.rawOffset;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getOffset(long l) {
        return this.getOffsets(l, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    int getOffsets(long l, int[] arrn) {
        int n;
        int n2 = n = this.rawOffset;
        if (this.useDaylight) {
            synchronized (this) {
                if (this.cacheStart != 0L && l >= this.cacheStart && l < this.cacheEnd) {
                    n2 = n + this.dstSavings;
                } else {
                    // MONITOREXIT [2, 5, 7] lbl8 : MonitorExitStatement: MONITOREXIT : this
                    BaseCalendar baseCalendar = l >= -12219292800000L ? gcal : (BaseCalendar)CalendarSystem.forName("julian");
                    BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE);
                    baseCalendar.getCalendarDate((long)this.rawOffset + l, date);
                    int n3 = date.getNormalizedYear();
                    n2 = n;
                    if (n3 >= this.startYear) {
                        date.setTimeOfDay(0, 0, 0, 0);
                        n2 = this.getOffset(baseCalendar, date, n3, l);
                    }
                }
            }
        }
        if (arrn != null) {
            arrn[0] = n = this.rawOffset;
            arrn[1] = n2 - n;
        }
        return n2;
    }

    @Override
    public int getRawOffset() {
        return this.rawOffset;
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        boolean bl;
        boolean bl2 = true;
        if (this == timeZone) {
            return true;
        }
        if (!(timeZone instanceof SimpleTimeZone)) {
            return false;
        }
        timeZone = (SimpleTimeZone)timeZone;
        if (this.rawOffset != ((SimpleTimeZone)timeZone).rawOffset || (bl = this.useDaylight) != ((SimpleTimeZone)timeZone).useDaylight || bl && (this.dstSavings != ((SimpleTimeZone)timeZone).dstSavings || this.startMode != ((SimpleTimeZone)timeZone).startMode || this.startMonth != ((SimpleTimeZone)timeZone).startMonth || this.startDay != ((SimpleTimeZone)timeZone).startDay || this.startDayOfWeek != ((SimpleTimeZone)timeZone).startDayOfWeek || this.startTime != ((SimpleTimeZone)timeZone).startTime || this.startTimeMode != ((SimpleTimeZone)timeZone).startTimeMode || this.endMode != ((SimpleTimeZone)timeZone).endMode || this.endMonth != ((SimpleTimeZone)timeZone).endMonth || this.endDay != ((SimpleTimeZone)timeZone).endDay || this.endDayOfWeek != ((SimpleTimeZone)timeZone).endDayOfWeek || this.endTime != ((SimpleTimeZone)timeZone).endTime || this.endTimeMode != ((SimpleTimeZone)timeZone).endTimeMode || this.startYear != ((SimpleTimeZone)timeZone).startYear)) {
            bl2 = false;
        }
        return bl2;
    }

    public int hashCode() {
        synchronized (this) {
            int n = this.startMonth;
            int n2 = this.startDay;
            int n3 = this.startDayOfWeek;
            int n4 = this.startTime;
            int n5 = this.endMonth;
            int n6 = this.endDay;
            int n7 = this.endDayOfWeek;
            int n8 = this.endTime;
            int n9 = this.rawOffset;
            return n ^ n2 ^ n3 ^ n4 ^ n5 ^ n6 ^ n7 ^ n8 ^ n9;
        }
    }

    @Override
    public boolean inDaylightTime(Date date) {
        boolean bl = this.getOffset(date.getTime()) != this.rawOffset;
        return bl;
    }

    @Override
    public boolean observesDaylightTime() {
        return this.useDaylightTime();
    }

    public void setDSTSavings(int n) {
        if (n > 0) {
            this.dstSavings = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal daylight saving value: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setEndRule(int n, int n2, int n3) {
        this.setEndRule(n, n2, 0, n3);
    }

    public void setEndRule(int n, int n2, int n3, int n4) {
        this.endMonth = n;
        this.endDay = n2;
        this.endDayOfWeek = n3;
        this.endTime = n4;
        this.endTimeMode = 0;
        this.decodeEndRule();
        this.invalidateCache();
    }

    public void setEndRule(int n, int n2, int n3, int n4, boolean bl) {
        if (bl) {
            this.setEndRule(n, n2, -n3, n4);
        } else {
            this.setEndRule(n, -n2, -n3, n4);
        }
    }

    @Override
    public void setRawOffset(int n) {
        this.rawOffset = n;
    }

    public void setStartRule(int n, int n2, int n3) {
        this.setStartRule(n, n2, 0, n3);
    }

    public void setStartRule(int n, int n2, int n3, int n4) {
        this.startMonth = n;
        this.startDay = n2;
        this.startDayOfWeek = n3;
        this.startTime = n4;
        this.startTimeMode = 0;
        this.decodeStartRule();
        this.invalidateCache();
    }

    public void setStartRule(int n, int n2, int n3, int n4, boolean bl) {
        if (bl) {
            this.setStartRule(n, n2, -n3, n4);
        } else {
            this.setStartRule(n, -n2, -n3, n4);
        }
    }

    public void setStartYear(int n) {
        this.startYear = n;
        this.invalidateCache();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[id=");
        stringBuilder.append(this.getID());
        stringBuilder.append(",offset=");
        stringBuilder.append(this.rawOffset);
        stringBuilder.append(",dstSavings=");
        stringBuilder.append(this.dstSavings);
        stringBuilder.append(",useDaylight=");
        stringBuilder.append(this.useDaylight);
        stringBuilder.append(",startYear=");
        stringBuilder.append(this.startYear);
        stringBuilder.append(",startMode=");
        stringBuilder.append(this.startMode);
        stringBuilder.append(",startMonth=");
        stringBuilder.append(this.startMonth);
        stringBuilder.append(",startDay=");
        stringBuilder.append(this.startDay);
        stringBuilder.append(",startDayOfWeek=");
        stringBuilder.append(this.startDayOfWeek);
        stringBuilder.append(",startTime=");
        stringBuilder.append(this.startTime);
        stringBuilder.append(",startTimeMode=");
        stringBuilder.append(this.startTimeMode);
        stringBuilder.append(",endMode=");
        stringBuilder.append(this.endMode);
        stringBuilder.append(",endMonth=");
        stringBuilder.append(this.endMonth);
        stringBuilder.append(",endDay=");
        stringBuilder.append(this.endDay);
        stringBuilder.append(",endDayOfWeek=");
        stringBuilder.append(this.endDayOfWeek);
        stringBuilder.append(",endTime=");
        stringBuilder.append(this.endTime);
        stringBuilder.append(",endTimeMode=");
        stringBuilder.append(this.endTimeMode);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public boolean useDaylightTime() {
        return this.useDaylight;
    }
}


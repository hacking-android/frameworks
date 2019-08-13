/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.Era;
import sun.util.calendar.Gregorian;
import sun.util.calendar.JulianCalendar;
import sun.util.calendar.LocalGregorianCalendar;

public abstract class CalendarSystem {
    private static final Gregorian GREGORIAN_INSTANCE;
    private static final ConcurrentMap<String, CalendarSystem> calendars;
    private static final Map<String, Class<?>> names;

    static {
        calendars = new ConcurrentHashMap<String, CalendarSystem>();
        names = new HashMap();
        names.put("gregorian", Gregorian.class);
        names.put("japanese", LocalGregorianCalendar.class);
        names.put("julian", JulianCalendar.class);
        GREGORIAN_INSTANCE = new Gregorian();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static CalendarSystem forName(String object) {
        if ("gregorian".equals(object)) {
            return GREGORIAN_INSTANCE;
        }
        Class<?> class_ = (CalendarSystem)calendars.get(object);
        if (class_ != null) {
            return class_;
        }
        class_ = names.get(object);
        if (class_ == null) {
            return null;
        }
        if (class_.isAssignableFrom(LocalGregorianCalendar.class)) {
            class_ = LocalGregorianCalendar.getLocalGregorianCalendar((String)object);
        } else {
            class_ = (CalendarSystem)class_.newInstance();
        }
        if (class_ == null) {
            return null;
        }
        if ((object = calendars.putIfAbsent((String)object, (CalendarSystem)((Object)class_))) != null) return object;
        return class_;
        catch (Exception exception) {
            throw new InternalError(exception);
        }
    }

    public static Properties getCalendarProperties() throws IOException {
        Properties properties;
        block7 : {
            properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("calendars.properties");
            try {
                properties.load(inputStream);
                if (inputStream == null) break block7;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    }
                    throw throwable2;
                }
            }
            inputStream.close();
        }
        return properties;
    }

    public static Gregorian getGregorianCalendar() {
        return GREGORIAN_INSTANCE;
    }

    public abstract CalendarDate getCalendarDate();

    public abstract CalendarDate getCalendarDate(long var1);

    public abstract CalendarDate getCalendarDate(long var1, TimeZone var3);

    public abstract CalendarDate getCalendarDate(long var1, CalendarDate var3);

    public abstract Era getEra(String var1);

    public abstract Era[] getEras();

    public abstract int getMonthLength(CalendarDate var1);

    public abstract String getName();

    public abstract CalendarDate getNthDayOfWeek(int var1, int var2, CalendarDate var3);

    public abstract long getTime(CalendarDate var1);

    public abstract int getWeekLength();

    public abstract int getYearLength(CalendarDate var1);

    public abstract int getYearLengthInMonths(CalendarDate var1);

    public abstract CalendarDate newCalendarDate();

    public abstract CalendarDate newCalendarDate(TimeZone var1);

    public abstract boolean normalize(CalendarDate var1);

    public abstract void setEra(CalendarDate var1, String var2);

    public abstract CalendarDate setTimeOfDay(CalendarDate var1, int var2);

    public abstract boolean validate(CalendarDate var1);
}


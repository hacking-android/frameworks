/*
 * Decompiled with CFR 0.145.
 */
package sun.util.calendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.CalendarUtils;
import sun.util.calendar.Era;

public class LocalGregorianCalendar
extends BaseCalendar {
    private Era[] eras;
    private String name;

    private LocalGregorianCalendar(String string, Era[] arrera) {
        this.name = string;
        this.eras = arrera;
        this.setEras(arrera);
    }

    private Date adjustYear(Date date, long l, int n) {
        int n2;
        for (n2 = this.eras.length - 1; n2 >= 0; --n2) {
            long l2;
            Era era = this.eras[n2];
            long l3 = l2 = era.getSince(null);
            if (era.isLocalTime()) {
                l3 = l2 - (long)n;
            }
            if (l < l3) continue;
            date.setLocalEra(era);
            date.setLocalYear(date.getNormalizedYear() - era.getSinceDate().getYear() + 1);
            break;
        }
        if (n2 < 0) {
            date.setLocalEra(null);
            date.setLocalYear(date.getNormalizedYear());
        }
        date.setNormalized(true);
        return date;
    }

    static LocalGregorianCalendar getLocalGregorianCalendar(String charSequence) {
        Object object;
        try {
            object = CalendarSystem.getCalendarProperties();
        }
        catch (IOException | IllegalArgumentException exception) {
            throw new InternalError(exception);
        }
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append("calendar.");
        charSequence2.append((String)charSequence);
        charSequence2.append(".eras");
        charSequence2 = ((Properties)object).getProperty(charSequence2.toString());
        if (charSequence2 == null) {
            return null;
        }
        ArrayList<Era> arrayList = new ArrayList<Era>();
        StringTokenizer stringTokenizer = new StringTokenizer((String)charSequence2, ";");
        while (stringTokenizer.hasMoreTokens()) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken().trim(), ",");
            String string = null;
            boolean bl = true;
            long l = 0L;
            charSequence2 = null;
            while (stringTokenizer2.hasMoreTokens()) {
                String string2 = stringTokenizer2.nextToken();
                int n = string2.indexOf(61);
                if (n == -1) {
                    return null;
                }
                String string3 = string2.substring(0, n);
                string2 = string2.substring(n + 1);
                if ("name".equals(string3)) {
                    string = string2;
                    continue;
                }
                if ("since".equals(string3)) {
                    if (string2.endsWith("u")) {
                        l = Long.parseLong(string2.substring(0, string2.length() - 1));
                        bl = false;
                        continue;
                    }
                    l = Long.parseLong(string2);
                    continue;
                }
                if ("abbr".equals(string3)) {
                    charSequence2 = string2;
                    continue;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown key word: ");
                ((StringBuilder)charSequence).append(string3);
                throw new RuntimeException(((StringBuilder)charSequence).toString());
            }
            arrayList.add(new Era(string, (String)charSequence2, l, bl));
        }
        if (!arrayList.isEmpty()) {
            object = new Era[arrayList.size()];
            arrayList.toArray((T[])object);
            return new LocalGregorianCalendar((String)charSequence, (Era[])object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No eras for ");
        ((StringBuilder)object).append((String)charSequence);
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    private boolean validateEra(Era era) {
        Era[] arrera;
        for (int i = 0; i < (arrera = this.eras).length; ++i) {
            if (era != arrera[i]) continue;
            return true;
        }
        return false;
    }

    @Override
    public Date getCalendarDate() {
        return this.getCalendarDate(System.currentTimeMillis(), this.newCalendarDate());
    }

    @Override
    public Date getCalendarDate(long l) {
        return this.getCalendarDate(l, this.newCalendarDate());
    }

    @Override
    public Date getCalendarDate(long l, TimeZone timeZone) {
        return this.getCalendarDate(l, this.newCalendarDate(timeZone));
    }

    @Override
    public Date getCalendarDate(long l, CalendarDate calendarDate) {
        calendarDate = (Date)super.getCalendarDate(l, calendarDate);
        return this.adjustYear((Date)calendarDate, l, calendarDate.getZoneOffset());
    }

    @Override
    public void getCalendarDateFromFixedDate(CalendarDate calendarDate, long l) {
        calendarDate = (Date)calendarDate;
        super.getCalendarDateFromFixedDate(calendarDate, l);
        this.adjustYear((Date)calendarDate, (l - 719163L) * 86400000L, 0);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isLeapYear(int n) {
        return CalendarUtils.isGregorianLeapYear(n);
    }

    public boolean isLeapYear(Era era, int n) {
        if (era == null) {
            return this.isLeapYear(n);
        }
        return this.isLeapYear(era.getSinceDate().getYear() + n - 1);
    }

    @Override
    public Date newCalendarDate() {
        return new Date();
    }

    @Override
    public Date newCalendarDate(TimeZone timeZone) {
        return new Date(timeZone);
    }

    @Override
    public boolean normalize(CalendarDate calendarDate) {
        int n;
        if (calendarDate.isNormalized()) {
            return true;
        }
        this.normalizeYear(calendarDate);
        Date date = (Date)calendarDate;
        super.normalize(date);
        int n2 = 0;
        long l = 0L;
        int n3 = date.getNormalizedYear();
        Era era = null;
        int n4 = this.eras.length - 1;
        do {
            long l2;
            n = n4;
            if (n4 < 0) break;
            era = this.eras[n4];
            if (era.isLocalTime()) {
                CalendarDate calendarDate2 = era.getSinceDate();
                n = calendarDate2.getYear();
                if (n3 > n) {
                    n = n4;
                    break;
                }
                if (n3 == n) {
                    int n5 = date.getMonth();
                    if (n5 > (n = calendarDate2.getMonth())) {
                        n = n4;
                        break;
                    }
                    if (n5 == n) {
                        n = date.getDayOfMonth();
                        if (n > (n5 = calendarDate2.getDayOfMonth())) {
                            n = n4;
                            break;
                        }
                        if (n == n5) {
                            if (date.getTimeOfDay() >= calendarDate2.getTimeOfDay()) {
                                n = n4;
                                break;
                            }
                            n = n4 - 1;
                            break;
                        }
                    }
                }
                l2 = l;
            } else {
                n = n2;
                if (n2 == 0) {
                    l = super.getTime(calendarDate);
                    n = 1;
                }
                n2 = n;
                l2 = l;
                if (l >= era.getSince(calendarDate.getZone())) {
                    n = n4;
                    break;
                }
            }
            --n4;
            l = l2;
        } while (true);
        if (n >= 0) {
            date.setLocalEra(era);
            date.setLocalYear(date.getNormalizedYear() - era.getSinceDate().getYear() + 1);
        } else {
            date.setEra(null);
            date.setLocalYear(n3);
            date.setNormalizedYear(n3);
        }
        date.setNormalized(true);
        return true;
    }

    @Override
    void normalizeMonth(CalendarDate calendarDate) {
        this.normalizeYear(calendarDate);
        super.normalizeMonth(calendarDate);
    }

    void normalizeYear(CalendarDate object) {
        Date date = (Date)object;
        object = date.getEra();
        if (object != null && this.validateEra((Era)object)) {
            date.setNormalizedYear(((Era)object).getSinceDate().getYear() + date.getYear() - 1);
        } else {
            date.setNormalizedYear(date.getYear());
        }
    }

    @Override
    public boolean validate(CalendarDate calendarDate) {
        Date date = (Date)calendarDate;
        Era era = date.getEra();
        if (era != null) {
            if (!this.validateEra(era)) {
                return false;
            }
            date.setNormalizedYear(era.getSinceDate().getYear() + date.getYear() - 1);
            CalendarDate calendarDate2 = this.newCalendarDate(calendarDate.getZone());
            ((Date)calendarDate2).setEra(era).setDate(calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDayOfMonth());
            this.normalize(calendarDate2);
            if (calendarDate2.getEra() != era) {
                return false;
            }
        } else {
            if (calendarDate.getYear() >= this.eras[0].getSinceDate().getYear()) {
                return false;
            }
            date.setNormalizedYear(date.getYear());
        }
        return super.validate(date);
    }

    public static class Date
    extends BaseCalendar.Date {
        private int gregorianYear = Integer.MIN_VALUE;

        protected Date() {
        }

        protected Date(TimeZone timeZone) {
            super(timeZone);
        }

        @Override
        public Date addYear(int n) {
            super.addYear(n);
            this.gregorianYear += n;
            return this;
        }

        @Override
        public int getNormalizedYear() {
            return this.gregorianYear;
        }

        @Override
        public Date setEra(Era era) {
            if (this.getEra() != era) {
                super.setEra(era);
                this.gregorianYear = Integer.MIN_VALUE;
            }
            return this;
        }

        void setLocalEra(Era era) {
            super.setEra(era);
        }

        void setLocalYear(int n) {
            super.setYear(n);
        }

        @Override
        public void setNormalizedYear(int n) {
            this.gregorianYear = n;
        }

        @Override
        public Date setYear(int n) {
            if (this.getYear() != n) {
                super.setYear(n);
                this.gregorianYear = Integer.MIN_VALUE;
            }
            return this;
        }

        @Override
        public String toString() {
            CharSequence charSequence = super.toString();
            String string = ((String)charSequence).substring(((String)charSequence).indexOf(84));
            charSequence = new StringBuffer();
            Object object = this.getEra();
            if (object != null && (object = ((Era)object).getAbbreviation()) != null) {
                ((StringBuffer)charSequence).append((String)object);
            }
            ((StringBuffer)charSequence).append(this.getYear());
            ((StringBuffer)charSequence).append('.');
            CalendarUtils.sprintf0d((StringBuffer)charSequence, this.getMonth(), 2).append('.');
            CalendarUtils.sprintf0d((StringBuffer)charSequence, this.getDayOfMonth(), 2);
            ((StringBuffer)charSequence).append(string);
            return ((StringBuffer)charSequence).toString();
        }
    }

}


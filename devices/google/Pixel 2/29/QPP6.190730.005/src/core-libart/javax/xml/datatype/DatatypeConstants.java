/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.datatype;

import javax.xml.namespace.QName;

public final class DatatypeConstants {
    public static final int APRIL = 4;
    public static final int AUGUST = 8;
    public static final QName DATE;
    public static final QName DATETIME;
    public static final Field DAYS;
    public static final int DECEMBER = 12;
    public static final QName DURATION;
    public static final QName DURATION_DAYTIME;
    public static final QName DURATION_YEARMONTH;
    public static final int EQUAL = 0;
    public static final int FEBRUARY = 2;
    public static final int FIELD_UNDEFINED = Integer.MIN_VALUE;
    public static final QName GDAY;
    public static final QName GMONTH;
    public static final QName GMONTHDAY;
    public static final int GREATER = 1;
    public static final QName GYEAR;
    public static final QName GYEARMONTH;
    public static final Field HOURS;
    public static final int INDETERMINATE = 2;
    public static final int JANUARY = 1;
    public static final int JULY = 7;
    public static final int JUNE = 6;
    public static final int LESSER = -1;
    public static final int MARCH = 3;
    public static final int MAX_TIMEZONE_OFFSET = -840;
    public static final int MAY = 5;
    public static final Field MINUTES;
    public static final int MIN_TIMEZONE_OFFSET = 840;
    public static final Field MONTHS;
    public static final int NOVEMBER = 11;
    public static final int OCTOBER = 10;
    public static final Field SECONDS;
    public static final int SEPTEMBER = 9;
    public static final QName TIME;
    public static final Field YEARS;

    static {
        YEARS = new Field("YEARS", 0);
        MONTHS = new Field("MONTHS", 1);
        DAYS = new Field("DAYS", 2);
        HOURS = new Field("HOURS", 3);
        MINUTES = new Field("MINUTES", 4);
        SECONDS = new Field("SECONDS", 5);
        DATETIME = new QName("http://www.w3.org/2001/XMLSchema", "dateTime");
        TIME = new QName("http://www.w3.org/2001/XMLSchema", "time");
        DATE = new QName("http://www.w3.org/2001/XMLSchema", "date");
        GYEARMONTH = new QName("http://www.w3.org/2001/XMLSchema", "gYearMonth");
        GMONTHDAY = new QName("http://www.w3.org/2001/XMLSchema", "gMonthDay");
        GYEAR = new QName("http://www.w3.org/2001/XMLSchema", "gYear");
        GMONTH = new QName("http://www.w3.org/2001/XMLSchema", "gMonth");
        GDAY = new QName("http://www.w3.org/2001/XMLSchema", "gDay");
        DURATION = new QName("http://www.w3.org/2001/XMLSchema", "duration");
        DURATION_DAYTIME = new QName("http://www.w3.org/2003/11/xpath-datatypes", "dayTimeDuration");
        DURATION_YEARMONTH = new QName("http://www.w3.org/2003/11/xpath-datatypes", "yearMonthDuration");
    }

    private DatatypeConstants() {
    }

    public static final class Field {
        private final int id;
        private final String str;

        private Field(String string, int n) {
            this.str = string;
            this.id = n;
        }

        public int getId() {
            return this.id;
        }

        public String toString() {
            return this.str;
        }
    }

}


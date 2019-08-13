/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.datatype;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.namespace.QName;

public abstract class Duration {
    private static long getCalendarTimeInMillis(Calendar calendar) {
        return calendar.getTime().getTime();
    }

    private int getFieldValueAsInt(DatatypeConstants.Field object) {
        if ((object = this.getField((DatatypeConstants.Field)object)) != null) {
            return ((Number)object).intValue();
        }
        return 0;
    }

    private String toString(BigDecimal serializable) {
        String string = ((BigDecimal)serializable).unscaledValue().toString();
        int n = ((BigDecimal)serializable).scale();
        if (n == 0) {
            return string;
        }
        int n2 = string.length() - n;
        if (n2 == 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("0.");
            ((StringBuilder)serializable).append(string);
            return ((StringBuilder)serializable).toString();
        }
        if (n2 > 0) {
            serializable = new StringBuilder(string);
            ((StringBuilder)serializable).insert(n2, '.');
        } else {
            serializable = new StringBuilder(3 - n2 + string.length());
            ((StringBuilder)serializable).append("0.");
            for (n = 0; n < -n2; ++n) {
                ((StringBuilder)serializable).append('0');
            }
            ((StringBuilder)serializable).append(string);
        }
        return ((StringBuilder)serializable).toString();
    }

    public abstract Duration add(Duration var1);

    public abstract void addTo(Calendar var1);

    public void addTo(Date date) {
        if (date != null) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            this.addTo(gregorianCalendar);
            date.setTime(Duration.getCalendarTimeInMillis(gregorianCalendar));
            return;
        }
        throw new NullPointerException("date == null");
    }

    public abstract int compare(Duration var1);

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof Duration) {
            if (this.compare((Duration)object) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getDays() {
        return this.getFieldValueAsInt(DatatypeConstants.DAYS);
    }

    public abstract Number getField(DatatypeConstants.Field var1);

    public int getHours() {
        return this.getFieldValueAsInt(DatatypeConstants.HOURS);
    }

    public int getMinutes() {
        return this.getFieldValueAsInt(DatatypeConstants.MINUTES);
    }

    public int getMonths() {
        return this.getFieldValueAsInt(DatatypeConstants.MONTHS);
    }

    public int getSeconds() {
        return this.getFieldValueAsInt(DatatypeConstants.SECONDS);
    }

    public abstract int getSign();

    public long getTimeInMillis(Calendar calendar) {
        Calendar calendar2 = (Calendar)calendar.clone();
        this.addTo(calendar2);
        return Duration.getCalendarTimeInMillis(calendar2) - Duration.getCalendarTimeInMillis(calendar);
    }

    public long getTimeInMillis(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        this.addTo(gregorianCalendar);
        return Duration.getCalendarTimeInMillis(gregorianCalendar) - date.getTime();
    }

    public QName getXMLSchemaType() {
        boolean bl = this.isSet(DatatypeConstants.YEARS);
        boolean bl2 = this.isSet(DatatypeConstants.MONTHS);
        boolean bl3 = this.isSet(DatatypeConstants.DAYS);
        boolean bl4 = this.isSet(DatatypeConstants.HOURS);
        boolean bl5 = this.isSet(DatatypeConstants.MINUTES);
        boolean bl6 = this.isSet(DatatypeConstants.SECONDS);
        if (bl && bl2 && bl3 && bl4 && bl5 && bl6) {
            return DatatypeConstants.DURATION;
        }
        if (!bl && !bl2 && bl3 && bl4 && bl5 && bl6) {
            return DatatypeConstants.DURATION_DAYTIME;
        }
        if (bl && bl2 && !bl3 && !bl4 && !bl5 && !bl6) {
            return DatatypeConstants.DURATION_YEARMONTH;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javax.xml.datatype.Duration#getXMLSchemaType(): this Duration does not match one of the XML Schema date/time datatypes: year set = ");
        stringBuilder.append(bl);
        stringBuilder.append(" month set = ");
        stringBuilder.append(bl2);
        stringBuilder.append(" day set = ");
        stringBuilder.append(bl3);
        stringBuilder.append(" hour set = ");
        stringBuilder.append(bl4);
        stringBuilder.append(" minute set = ");
        stringBuilder.append(bl5);
        stringBuilder.append(" second set = ");
        stringBuilder.append(bl6);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getYears() {
        return this.getFieldValueAsInt(DatatypeConstants.YEARS);
    }

    public abstract int hashCode();

    public boolean isLongerThan(Duration duration) {
        int n = this.compare(duration);
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public abstract boolean isSet(DatatypeConstants.Field var1);

    public boolean isShorterThan(Duration duration) {
        boolean bl = this.compare(duration) == -1;
        return bl;
    }

    public Duration multiply(int n) {
        return this.multiply(BigDecimal.valueOf(n));
    }

    public abstract Duration multiply(BigDecimal var1);

    public abstract Duration negate();

    public abstract Duration normalizeWith(Calendar var1);

    public Duration subtract(Duration duration) {
        return this.add(duration.negate());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getSign() < 0) {
            stringBuilder.append('-');
        }
        stringBuilder.append('P');
        Number number = (BigInteger)this.getField(DatatypeConstants.YEARS);
        if (number != null) {
            stringBuilder.append(number);
            stringBuilder.append('Y');
        }
        if ((number = (BigInteger)this.getField(DatatypeConstants.MONTHS)) != null) {
            stringBuilder.append(number);
            stringBuilder.append('M');
        }
        if ((number = (BigInteger)this.getField(DatatypeConstants.DAYS)) != null) {
            stringBuilder.append(number);
            stringBuilder.append('D');
        }
        BigInteger bigInteger = (BigInteger)this.getField(DatatypeConstants.HOURS);
        BigInteger bigInteger2 = (BigInteger)this.getField(DatatypeConstants.MINUTES);
        number = (BigDecimal)this.getField(DatatypeConstants.SECONDS);
        if (bigInteger != null || bigInteger2 != null || number != null) {
            stringBuilder.append('T');
            if (bigInteger != null) {
                stringBuilder.append(bigInteger);
                stringBuilder.append('H');
            }
            if (bigInteger2 != null) {
                stringBuilder.append(bigInteger2);
                stringBuilder.append('M');
            }
            if (number != null) {
                stringBuilder.append(this.toString((BigDecimal)number));
                stringBuilder.append('S');
            }
        }
        return stringBuilder.toString();
    }
}


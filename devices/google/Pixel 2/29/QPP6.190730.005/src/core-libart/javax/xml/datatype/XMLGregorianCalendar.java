/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;

public abstract class XMLGregorianCalendar
implements Cloneable {
    public abstract void add(Duration var1);

    public abstract void clear();

    public abstract Object clone();

    public abstract int compare(XMLGregorianCalendar var1);

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof XMLGregorianCalendar) {
            if (this.compare((XMLGregorianCalendar)object) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public abstract int getDay();

    public abstract BigInteger getEon();

    public abstract BigInteger getEonAndYear();

    public abstract BigDecimal getFractionalSecond();

    public abstract int getHour();

    public int getMillisecond() {
        if (this.getFractionalSecond() == null) {
            return Integer.MIN_VALUE;
        }
        return this.getFractionalSecond().movePointRight(3).intValue();
    }

    public abstract int getMinute();

    public abstract int getMonth();

    public abstract int getSecond();

    public abstract TimeZone getTimeZone(int var1);

    public abstract int getTimezone();

    public abstract QName getXMLSchemaType();

    public abstract int getYear();

    public int hashCode() {
        int n;
        int n2 = n = this.getTimezone();
        if (n == Integer.MIN_VALUE) {
            n2 = 0;
        }
        XMLGregorianCalendar xMLGregorianCalendar = this;
        if (n2 != 0) {
            xMLGregorianCalendar = this.normalize();
        }
        return xMLGregorianCalendar.getYear() + xMLGregorianCalendar.getMonth() + xMLGregorianCalendar.getDay() + xMLGregorianCalendar.getHour() + xMLGregorianCalendar.getMinute() + xMLGregorianCalendar.getSecond();
    }

    public abstract boolean isValid();

    public abstract XMLGregorianCalendar normalize();

    public abstract void reset();

    public abstract void setDay(int var1);

    public abstract void setFractionalSecond(BigDecimal var1);

    public abstract void setHour(int var1);

    public abstract void setMillisecond(int var1);

    public abstract void setMinute(int var1);

    public abstract void setMonth(int var1);

    public abstract void setSecond(int var1);

    public void setTime(int n, int n2, int n3) {
        this.setTime(n, n2, n3, null);
    }

    public void setTime(int n, int n2, int n3, int n4) {
        this.setHour(n);
        this.setMinute(n2);
        this.setSecond(n3);
        this.setMillisecond(n4);
    }

    public void setTime(int n, int n2, int n3, BigDecimal bigDecimal) {
        this.setHour(n);
        this.setMinute(n2);
        this.setSecond(n3);
        this.setFractionalSecond(bigDecimal);
    }

    public abstract void setTimezone(int var1);

    public abstract void setYear(int var1);

    public abstract void setYear(BigInteger var1);

    public abstract GregorianCalendar toGregorianCalendar();

    public abstract GregorianCalendar toGregorianCalendar(TimeZone var1, Locale var2, XMLGregorianCalendar var3);

    public String toString() {
        return this.toXMLFormat();
    }

    public abstract String toXMLFormat();
}


/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.datatype;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.Duration;
import javax.xml.datatype.FactoryFinder;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class DatatypeFactory {
    public static final String DATATYPEFACTORY_IMPLEMENTATION_CLASS = new String("org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl");
    public static final String DATATYPEFACTORY_PROPERTY = "javax.xml.datatype.DatatypeFactory";

    protected DatatypeFactory() {
    }

    public static DatatypeFactory newInstance() throws DatatypeConfigurationException {
        try {
            DatatypeFactory datatypeFactory = (DatatypeFactory)FactoryFinder.find(DATATYPEFACTORY_PROPERTY, DATATYPEFACTORY_IMPLEMENTATION_CLASS);
            return datatypeFactory;
        }
        catch (FactoryFinder.ConfigurationError configurationError) {
            throw new DatatypeConfigurationException(configurationError.getMessage(), configurationError.getException());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static DatatypeFactory newInstance(String var0, ClassLoader var1_4) throws DatatypeConfigurationException {
        if (var0 == null) throw new DatatypeConfigurationException("factoryClassName == null");
        var2_5 = var1_4;
        if (var1_4 == null) {
            var2_5 = Thread.currentThread().getContextClassLoader();
        }
        if (var2_5 == null) ** GOTO lbl9
        try {
            var0 = var2_5.loadClass((String)var0);
            return (DatatypeFactory)var0.newInstance();
lbl9: // 1 sources:
            var0 = Class.forName((String)var0);
            return (DatatypeFactory)var0.newInstance();
        }
        catch (IllegalAccessException var0_1) {
            throw new DatatypeConfigurationException(var0_1);
        }
        catch (InstantiationException var0_2) {
            throw new DatatypeConfigurationException(var0_2);
        }
        catch (ClassNotFoundException var0_3) {
            throw new DatatypeConfigurationException(var0_3);
        }
    }

    public abstract Duration newDuration(long var1);

    public abstract Duration newDuration(String var1);

    public Duration newDuration(boolean bl, int n, int n2, int n3, int n4, int n5, int n6) {
        BigDecimal bigDecimal = null;
        BigInteger bigInteger = n != Integer.MIN_VALUE ? BigInteger.valueOf(n) : null;
        BigInteger bigInteger2 = n2 != Integer.MIN_VALUE ? BigInteger.valueOf(n2) : null;
        BigInteger bigInteger3 = n3 != Integer.MIN_VALUE ? BigInteger.valueOf(n3) : null;
        BigInteger bigInteger4 = n4 != Integer.MIN_VALUE ? BigInteger.valueOf(n4) : null;
        BigInteger bigInteger5 = n5 != Integer.MIN_VALUE ? BigInteger.valueOf(n5) : null;
        if (n6 != Integer.MIN_VALUE) {
            bigDecimal = BigDecimal.valueOf(n6);
        }
        return this.newDuration(bl, bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigDecimal);
    }

    public abstract Duration newDuration(boolean var1, BigInteger var2, BigInteger var3, BigInteger var4, BigInteger var5, BigInteger var6, BigDecimal var7);

    public Duration newDurationDayTime(long l) {
        int n;
        boolean bl;
        if (l == 0L) {
            return this.newDuration(true, Integer.MIN_VALUE, Integer.MIN_VALUE, 0, 0, 0, 0);
        }
        int n2 = 0;
        int n3 = 0;
        if (l < 0L) {
            bl = false;
            long l2 = l;
            if (l == Long.MIN_VALUE) {
                l2 = l + 1L;
                n3 = 1;
            }
            l = l2 * -1L;
            n2 = n3;
        } else {
            bl = true;
        }
        n3 = n = (int)(l % 60000L);
        if (n2 != 0) {
            n3 = n + 1;
        }
        if (n3 % 1000 == 0) {
            n2 = n3 / 1000;
            n = (int)((l /= 60000L) % 60L);
            int n4 = (int)((l /= 60L) % 24L);
            if ((l /= 24L) <= Integer.MAX_VALUE) {
                return this.newDuration(bl, Integer.MIN_VALUE, Integer.MIN_VALUE, (int)l, n4, n, n2);
            }
            return this.newDuration(bl, null, null, BigInteger.valueOf(l), BigInteger.valueOf(n4), BigInteger.valueOf(n), BigDecimal.valueOf(n3, 3));
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(n3, 3);
        BigInteger bigInteger = BigInteger.valueOf((l /= 60000L) % 60L);
        BigInteger bigInteger2 = BigInteger.valueOf((l /= 60L) % 24L);
        return this.newDuration(bl, null, null, BigInteger.valueOf(l / 24L), bigInteger2, bigInteger, bigDecimal);
    }

    public Duration newDurationDayTime(String string) {
        if (string != null) {
            int n = string.indexOf(84);
            if (n < 0) {
                n = string.length();
            }
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                if (c != 'Y' && c != 'M') {
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid dayTimeDuration value: ");
                stringBuilder.append(string);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return this.newDuration(string);
        }
        throw new NullPointerException("lexicalRepresentation == null");
    }

    public Duration newDurationDayTime(boolean bl, int n, int n2, int n3, int n4) {
        return this.newDuration(bl, Integer.MIN_VALUE, Integer.MIN_VALUE, n, n2, n3, n4);
    }

    public Duration newDurationDayTime(boolean bl, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger number) {
        number = number != null ? new BigDecimal((BigInteger)number) : null;
        return this.newDuration(bl, null, null, bigInteger, bigInteger2, bigInteger3, (BigDecimal)number);
    }

    public Duration newDurationYearMonth(long l) {
        return this.newDuration(l);
    }

    public Duration newDurationYearMonth(String string) {
        if (string != null) {
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                if (c != 'D' && c != 'T') {
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid yearMonthDuration value: ");
                stringBuilder.append(string);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return this.newDuration(string);
        }
        throw new NullPointerException("lexicalRepresentation == null");
    }

    public Duration newDurationYearMonth(boolean bl, int n, int n2) {
        return this.newDuration(bl, n, n2, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public Duration newDurationYearMonth(boolean bl, BigInteger bigInteger, BigInteger bigInteger2) {
        return this.newDuration(bl, bigInteger, bigInteger2, null, null, null, null);
    }

    public abstract XMLGregorianCalendar newXMLGregorianCalendar();

    /*
     * Enabled aggressive block sorting
     */
    public XMLGregorianCalendar newXMLGregorianCalendar(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        BigDecimal bigDecimal;
        Serializable serializable = n != Integer.MIN_VALUE ? BigInteger.valueOf(n) : null;
        if (n7 == Integer.MIN_VALUE) {
            bigDecimal = null;
            return this.newXMLGregorianCalendar((BigInteger)serializable, n2, n3, n4, n5, n6, bigDecimal, n8);
        }
        if (n7 >= 0 && n7 <= 1000) {
            bigDecimal = BigDecimal.valueOf(n7, 3);
            return this.newXMLGregorianCalendar((BigInteger)serializable, n2, n3, n4, n5, n6, bigDecimal, n8);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("javax.xml.datatype.DatatypeFactory#newXMLGregorianCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond, int timezone)with invalid millisecond: ");
        ((StringBuilder)serializable).append(n7);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(String var1);

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(BigInteger var1, int var2, int var3, int var4, int var5, int var6, BigDecimal var7, int var8);

    public abstract XMLGregorianCalendar newXMLGregorianCalendar(GregorianCalendar var1);

    public XMLGregorianCalendar newXMLGregorianCalendarDate(int n, int n2, int n3, int n4) {
        return this.newXMLGregorianCalendar(n, n2, n3, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, n4);
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(int n, int n2, int n3, int n4) {
        return this.newXMLGregorianCalendar(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, n, n2, n3, Integer.MIN_VALUE, n4);
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(int n, int n2, int n3, int n4, int n5) {
        Serializable serializable = null;
        if (n4 != Integer.MIN_VALUE) {
            if (n4 >= 0 && n4 <= 1000) {
                serializable = BigDecimal.valueOf(n4, 3);
            } else {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("javax.xml.datatype.DatatypeFactory#newXMLGregorianCalendarTime(int hours, int minutes, int seconds, int milliseconds, int timezone)with invalid milliseconds: ");
                ((StringBuilder)serializable).append(n4);
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
        }
        return this.newXMLGregorianCalendarTime(n, n2, n3, (BigDecimal)serializable, n5);
    }

    public XMLGregorianCalendar newXMLGregorianCalendarTime(int n, int n2, int n3, BigDecimal bigDecimal, int n4) {
        return this.newXMLGregorianCalendar(null, Integer.MIN_VALUE, Integer.MIN_VALUE, n, n2, n3, bigDecimal, n4);
    }
}


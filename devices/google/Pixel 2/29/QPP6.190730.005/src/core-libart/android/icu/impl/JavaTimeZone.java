/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Grego;
import android.icu.util.TimeZone;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeSet;

public class JavaTimeZone
extends TimeZone {
    private static final TreeSet<String> AVAILABLESET = new TreeSet();
    private static Method mObservesDaylightTime;
    private static final long serialVersionUID = 6977448185543929364L;
    private volatile transient boolean isFrozen = false;
    private transient Calendar javacal;
    private java.util.TimeZone javatz;

    static {
        String[] arrstring = java.util.TimeZone.getAvailableIDs();
        for (int i = 0; i < arrstring.length; ++i) {
            AVAILABLESET.add(arrstring[i]);
        }
        try {
            mObservesDaylightTime = java.util.TimeZone.class.getMethod("observesDaylightTime", null);
        }
        catch (SecurityException securityException) {
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
    }

    public JavaTimeZone() {
        this(java.util.TimeZone.getDefault(), null);
    }

    public JavaTimeZone(java.util.TimeZone timeZone, String string) {
        String string2 = string;
        if (string == null) {
            string2 = timeZone.getID();
        }
        this.javatz = timeZone;
        this.setID(string2);
        this.javacal = new GregorianCalendar(this.javatz);
    }

    public static JavaTimeZone createTimeZone(String string) {
        java.util.TimeZone timeZone = null;
        if (AVAILABLESET.contains(string)) {
            timeZone = java.util.TimeZone.getTimeZone(string);
        }
        java.util.TimeZone timeZone2 = timeZone;
        if (timeZone == null) {
            boolean[] arrbl = new boolean[1];
            String string2 = TimeZone.getCanonicalID(string, arrbl);
            timeZone2 = timeZone;
            if (arrbl[0]) {
                timeZone2 = timeZone;
                if (AVAILABLESET.contains(string2)) {
                    timeZone2 = java.util.TimeZone.getTimeZone(string2);
                }
            }
        }
        if (timeZone2 == null) {
            return null;
        }
        return new JavaTimeZone(timeZone2, string);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.javacal = new GregorianCalendar(this.javatz);
    }

    @Override
    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    @Override
    public TimeZone cloneAsThawed() {
        JavaTimeZone javaTimeZone = (JavaTimeZone)super.cloneAsThawed();
        javaTimeZone.javatz = (java.util.TimeZone)this.javatz.clone();
        javaTimeZone.javacal = new GregorianCalendar(this.javatz);
        javaTimeZone.isFrozen = false;
        return javaTimeZone;
    }

    @Override
    public TimeZone freeze() {
        this.isFrozen = true;
        return this;
    }

    @Override
    public int getDSTSavings() {
        return this.javatz.getDSTSavings();
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        return this.javatz.getOffset(n, n2, n3, n4, n5, n6);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void getOffset(long l, boolean bl, int[] arrn) {
        Calendar calendar = this.javacal;
        synchronized (calendar) {
            if (bl) {
                int[] arrn2 = new int[6];
                Grego.timeToFields(l, arrn2);
                int n = arrn2[5];
                int n2 = n % 1000;
                int n3 = (n /= 1000) % 60;
                int n4 = (n /= 60) % 60;
                int n5 = n / 60;
                this.javacal.clear();
                this.javacal.set(arrn2[0], arrn2[1], arrn2[2], n5, n4, n3);
                this.javacal.set(14, n2);
                n = this.javacal.get(6);
                int n6 = this.javacal.get(11);
                int n7 = this.javacal.get(12);
                int n8 = this.javacal.get(13);
                int n9 = this.javacal.get(14);
                if (arrn2[4] != n || n5 != n6 || n4 != n7 || n3 != n8 || n2 != n9) {
                    n = Math.abs(n - arrn2[4]) > 1 ? 1 : (n -= arrn2[4]);
                    this.javacal.setTimeInMillis(this.javacal.getTimeInMillis() - (long)((((n * 24 + n6 - n5) * 60 + n7 - n4) * 60 + n8 - n3) * 1000 + n9 - n2) - 1L);
                }
            } else {
                this.javacal.setTimeInMillis(l);
            }
            arrn[0] = this.javacal.get(15);
            arrn[1] = this.javacal.get(16);
            return;
        }
    }

    @Override
    public int getRawOffset() {
        return this.javatz.getRawOffset();
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.javatz.hashCode();
    }

    @Override
    public boolean inDaylightTime(Date date) {
        return this.javatz.inDaylightTime(date);
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public boolean observesDaylightTime() {
        Method method = mObservesDaylightTime;
        if (method != null) {
            try {
                boolean bl = (Boolean)method.invoke(this.javatz, null);
                return bl;
            }
            catch (InvocationTargetException invocationTargetException) {
            }
            catch (IllegalArgumentException illegalArgumentException) {
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
        return super.observesDaylightTime();
    }

    @Override
    public void setRawOffset(int n) {
        if (!this.isFrozen()) {
            this.javatz.setRawOffset(n);
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify a frozen JavaTimeZone instance.");
    }

    public java.util.TimeZone unwrap() {
        return this.javatz;
    }

    @Override
    public boolean useDaylightTime() {
        return this.javatz.useDaylightTime();
    }
}


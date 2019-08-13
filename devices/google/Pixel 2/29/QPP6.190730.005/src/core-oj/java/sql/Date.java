/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

public class Date
extends java.util.Date {
    static final long serialVersionUID = 1511598038487230103L;

    @Deprecated
    public Date(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public Date(long l) {
        super(l);
    }

    public static Date valueOf(String object) {
        Object var1_1 = null;
        if (object != null) {
            int n = ((String)object).indexOf(45);
            int n2 = ((String)object).indexOf(45, n + 1);
            if (n > 0 && n2 > 0 && n2 < ((String)object).length() - 1) {
                String string = ((String)object).substring(0, n);
                String string2 = ((String)object).substring(n + 1, n2);
                object = ((String)object).substring(n2 + 1);
                if (string.length() == 4) {
                    if (string2.length() >= 1 && string2.length() <= 2) {
                        if (((String)object).length() >= 1 && ((String)object).length() <= 2) {
                            n2 = Integer.parseInt(string);
                            n = Integer.parseInt(string2);
                            int n3 = Integer.parseInt((String)object);
                            object = n >= 1 && n <= 12 && n3 >= 1 && n3 <= 31 ? new Date(n2 - 1900, n - 1, n3) : var1_1;
                        } else {
                            object = var1_1;
                        }
                    } else {
                        object = var1_1;
                    }
                } else {
                    object = var1_1;
                }
            } else {
                object = var1_1;
            }
            if (object != null) {
                return object;
            }
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public int getHours() {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public int getMinutes() {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public int getSeconds() {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public void setHours(int n) {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public void setMinutes(int n) {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public void setSeconds(int n) {
        throw new IllegalArgumentException();
    }

    @Override
    public void setTime(long l) {
        super.setTime(l);
    }

    @Override
    public String toString() {
        int n = super.getYear() + 1900;
        int n2 = super.getMonth() + 1;
        int n3 = super.getDate();
        char[] arrc = "2000-00-00".toCharArray();
        arrc[0] = Character.forDigit(n / 1000, 10);
        arrc[1] = Character.forDigit(n / 100 % 10, 10);
        arrc[2] = Character.forDigit(n / 10 % 10, 10);
        arrc[3] = Character.forDigit(n % 10, 10);
        arrc[5] = Character.forDigit(n2 / 10, 10);
        arrc[6] = Character.forDigit(n2 % 10, 10);
        arrc[8] = Character.forDigit(n3 / 10, 10);
        arrc[9] = Character.forDigit(n3 % 10, 10);
        return new String(arrc);
    }
}


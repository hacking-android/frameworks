/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.util.Date;

public class Time
extends Date {
    static final long serialVersionUID = 8397324403548013681L;

    @Deprecated
    public Time(int n, int n2, int n3) {
        super(70, 0, 1, n, n2, n3);
    }

    public Time(long l) {
        super(l);
    }

    public static Time valueOf(String string) {
        if (string != null) {
            int n = string.indexOf(58);
            int n2 = string.indexOf(58, n + 1);
            boolean bl = true;
            boolean bl2 = n > 0;
            boolean bl3 = n2 > 0;
            if (n2 >= string.length() - 1) {
                bl = false;
            }
            if (bl & (bl2 & bl3)) {
                return new Time(Integer.parseInt(string.substring(0, n)), Integer.parseInt(string.substring(n + 1, n2)), Integer.parseInt(string.substring(n2 + 1)));
            }
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public int getDate() {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public int getDay() {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public int getMonth() {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public int getYear() {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public void setDate(int n) {
        throw new IllegalArgumentException();
    }

    @Deprecated
    @Override
    public void setMonth(int n) {
        throw new IllegalArgumentException();
    }

    @Override
    public void setTime(long l) {
        super.setTime(l);
    }

    @Deprecated
    @Override
    public void setYear(int n) {
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        CharSequence charSequence;
        CharSequence charSequence2;
        CharSequence charSequence3;
        int n = super.getHours();
        int n2 = super.getMinutes();
        int n3 = super.getSeconds();
        if (n < 10) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("0");
            ((StringBuilder)charSequence2).append(n);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        } else {
            charSequence2 = Integer.toString(n);
        }
        if (n2 < 10) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("0");
            ((StringBuilder)charSequence).append(n2);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = Integer.toString(n2);
        }
        if (n3 < 10) {
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append("0");
            ((StringBuilder)charSequence3).append(n3);
            charSequence3 = ((StringBuilder)charSequence3).toString();
        } else {
            charSequence3 = Integer.toString(n3);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence2);
        stringBuilder.append(":");
        stringBuilder.append((String)charSequence);
        stringBuilder.append(":");
        stringBuilder.append((String)charSequence3);
        return stringBuilder.toString();
    }
}


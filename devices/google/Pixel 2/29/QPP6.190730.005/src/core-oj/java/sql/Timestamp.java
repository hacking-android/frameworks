/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.util.Date;

public class Timestamp
extends Date {
    static final long serialVersionUID = 2745179027874758501L;
    private int nanos;

    @Deprecated
    public Timestamp(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        super(n, n2, n3, n4, n5, n6);
        if (n7 <= 999999999 && n7 >= 0) {
            this.nanos = n7;
            return;
        }
        throw new IllegalArgumentException("nanos > 999999999 or < 0");
    }

    public Timestamp(long l) {
        super(l / 1000L * 1000L);
        int n = this.nanos = (int)(l % 1000L * 1000000L);
        if (n < 0) {
            this.nanos = n + 1000000000;
            super.setTime((l / 1000L - 1L) * 1000L);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static Timestamp valueOf(String var0) {
        var1_1 = 0;
        if (var0 == null) throw new IllegalArgumentException("null string");
        var2_2 = var0.trim();
        var3_3 = var2_2.indexOf(32);
        if (var3_3 <= 0) throw new IllegalArgumentException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]");
        var0 = var2_2.substring(0, var3_3);
        var2_2 = var2_2.substring(var3_3 + 1);
        var4_4 = var0.indexOf(45);
        var3_3 = var0.indexOf(45, var4_4 + 1);
        if (var2_2 == null) throw new IllegalArgumentException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]");
        var5_5 = var2_2.indexOf(58);
        var6_6 = var2_2.indexOf(58, var5_5 + 1);
        var7_7 = var2_2.indexOf(46, var6_6 + 1);
        var8_8 = 0;
        if (var4_4 <= 0 || var3_3 <= 0 || var3_3 >= var0.length() - 1) ** GOTO lbl-1000
        var9_9 = var0.substring(0, var4_4);
        var10_10 = var0.substring(var4_4 + 1, var3_3);
        var0 = var0.substring(var3_3 + 1);
        if (var9_9.length() == 4 && var10_10.length() >= 1 && var10_10.length() <= 2 && var0.length() >= 1 && var0.length() <= 2) {
            var3_3 = Integer.parseInt(var9_9);
            var4_4 = Integer.parseInt(var10_10);
            var1_1 = Integer.parseInt((String)var0);
            if (var4_4 >= 1 && var4_4 <= 12 && var1_1 >= 1 && var1_1 <= 31) {
                var8_8 = 1;
            }
        } else lbl-1000: // 2 sources:
        {
            var3_3 = 0;
            var4_4 = 0;
        }
        if (var8_8 == 0) throw new IllegalArgumentException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]");
        var8_8 = var5_5 > 0 ? 1 : 0;
        var11_11 = var6_6 > 0 ? 1 : 0;
        var12_12 = var6_6 < var2_2.length() - 1 ? 1 : 0;
        if ((var8_8 & var11_11 & var12_12) == false) throw new IllegalArgumentException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]");
        var12_12 = Integer.parseInt(var2_2.substring(0, var5_5));
        var5_5 = Integer.parseInt(var2_2.substring(var5_5 + 1, var6_6));
        var8_8 = var7_7 > 0 ? 1 : 0;
        var13_13 = var2_2.length();
        var11_11 = 1;
        if (var7_7 >= var13_13 - 1) {
            var11_11 = 0;
        }
        if (var8_8 & var11_11) {
            var8_8 = Integer.parseInt(var2_2.substring(var6_6 + 1, var7_7));
            if ((var2_2 = var2_2.substring(var7_7 + 1)).length() > 9) throw new IllegalArgumentException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]");
            if (Character.isDigit(var2_2.charAt(0)) == false) throw new IllegalArgumentException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]");
            var0 = new StringBuilder();
            var0.append(var2_2);
            var0.append("000000000".substring(0, 9 - var2_2.length()));
            var11_11 = Integer.parseInt(var0.toString());
            return new Timestamp(var3_3 - 1900, var4_4 - 1, var1_1, var12_12, var5_5, var8_8, var11_11);
        }
        var11_11 = 0;
        if (var7_7 > 0) throw new IllegalArgumentException("Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]");
        var8_8 = Integer.parseInt(var2_2.substring(var6_6 + 1));
        return new Timestamp(var3_3 - 1900, var4_4 - 1, var1_1, var12_12, var5_5, var8_8, var11_11);
    }

    public boolean after(Timestamp timestamp) {
        boolean bl = this.compareTo(timestamp) > 0;
        return bl;
    }

    public boolean before(Timestamp timestamp) {
        boolean bl = this.compareTo(timestamp) < 0;
        return bl;
    }

    @Override
    public int compareTo(Timestamp timestamp) {
        long l;
        long l2 = this.getTime();
        int n = l2 < (l = timestamp.getTime()) ? -1 : (l2 == l ? 0 : 1);
        if (n == 0) {
            int n2 = this.nanos;
            int n3 = timestamp.nanos;
            if (n2 > n3) {
                return 1;
            }
            if (n2 < n3) {
                return -1;
            }
        }
        return n;
    }

    @Override
    public int compareTo(Date date) {
        if (date instanceof Timestamp) {
            return this.compareTo((Timestamp)date);
        }
        return this.compareTo(new Timestamp(date.getTime()));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Timestamp) {
            return this.equals((Timestamp)object);
        }
        return false;
    }

    public boolean equals(Timestamp timestamp) {
        if (super.equals(timestamp)) {
            return this.nanos == timestamp.nanos;
        }
        return false;
    }

    public int getNanos() {
        return this.nanos;
    }

    @Override
    public long getTime() {
        long l = super.getTime();
        return (long)(this.nanos / 1000000) + l;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void setNanos(int n) {
        if (n <= 999999999 && n >= 0) {
            this.nanos = n;
            return;
        }
        throw new IllegalArgumentException("nanos > 999999999 or < 0");
    }

    @Override
    public void setTime(long l) {
        super.setTime(l / 1000L * 1000L);
        int n = this.nanos = (int)(l % 1000L * 1000000L);
        if (n < 0) {
            this.nanos = n + 1000000000;
            super.setTime((l / 1000L - 1L) * 1000L);
        }
    }

    @Override
    public String toString() {
        CharSequence charSequence;
        CharSequence charSequence2;
        CharSequence charSequence3;
        CharSequence charSequence4;
        CharSequence charSequence5;
        CharSequence charSequence6;
        CharSequence charSequence7;
        CharSequence charSequence8;
        int n = super.getYear() + 1900;
        int n2 = super.getMonth() + 1;
        int n3 = super.getDate();
        int n4 = super.getHours();
        int n5 = super.getMinutes();
        int n6 = super.getSeconds();
        if (n < 1000) {
            charSequence7 = new StringBuilder();
            ((StringBuilder)charSequence7).append("");
            ((StringBuilder)charSequence7).append(n);
            charSequence7 = ((StringBuilder)charSequence7).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("0000".substring(0, 4 - ((String)charSequence7).length()));
            ((StringBuilder)charSequence).append((String)charSequence7);
            charSequence7 = ((StringBuilder)charSequence).toString();
        } else {
            charSequence7 = new StringBuilder();
            ((StringBuilder)charSequence7).append("");
            ((StringBuilder)charSequence7).append(n);
            charSequence7 = ((StringBuilder)charSequence7).toString();
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
            charSequence5 = new StringBuilder();
            ((StringBuilder)charSequence5).append("0");
            ((StringBuilder)charSequence5).append(n3);
            charSequence5 = ((StringBuilder)charSequence5).toString();
        } else {
            charSequence5 = Integer.toString(n3);
        }
        if (n4 < 10) {
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append("0");
            ((StringBuilder)charSequence3).append(n4);
            charSequence3 = ((StringBuilder)charSequence3).toString();
        } else {
            charSequence3 = Integer.toString(n4);
        }
        if (n5 < 10) {
            charSequence6 = new StringBuilder();
            ((StringBuilder)charSequence6).append("0");
            ((StringBuilder)charSequence6).append(n5);
            charSequence6 = ((StringBuilder)charSequence6).toString();
        } else {
            charSequence6 = Integer.toString(n5);
        }
        if (n6 < 10) {
            charSequence8 = new StringBuilder();
            ((StringBuilder)charSequence8).append("0");
            ((StringBuilder)charSequence8).append(n6);
            charSequence8 = ((StringBuilder)charSequence8).toString();
        } else {
            charSequence8 = Integer.toString(n6);
        }
        n = this.nanos;
        if (n == 0) {
            charSequence2 = "0";
        } else {
            charSequence4 = Integer.toString(n);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("000000000".substring(0, 9 - ((String)charSequence4).length()));
            ((StringBuilder)charSequence2).append((String)charSequence4);
            charSequence2 = ((StringBuilder)charSequence2).toString();
            charSequence4 = new char[((String)charSequence2).length()];
            ((String)charSequence2).getChars(0, ((String)charSequence2).length(), (char[])charSequence4, 0);
            n = 8;
            while (charSequence4[n] == 48) {
                --n;
            }
            charSequence2 = new String((char[])charSequence4, 0, n + 1);
        }
        charSequence4 = new StringBuffer(((String)charSequence2).length() + 20);
        ((StringBuffer)charSequence4).append((String)charSequence7);
        ((StringBuffer)charSequence4).append("-");
        ((StringBuffer)charSequence4).append((String)charSequence);
        ((StringBuffer)charSequence4).append("-");
        ((StringBuffer)charSequence4).append((String)charSequence5);
        ((StringBuffer)charSequence4).append(" ");
        ((StringBuffer)charSequence4).append((String)charSequence3);
        ((StringBuffer)charSequence4).append(":");
        ((StringBuffer)charSequence4).append((String)charSequence6);
        ((StringBuffer)charSequence4).append(":");
        ((StringBuffer)charSequence4).append((String)charSequence8);
        ((StringBuffer)charSequence4).append(".");
        ((StringBuffer)charSequence4).append((String)charSequence2);
        return ((StringBuffer)charSequence4).toString();
    }
}


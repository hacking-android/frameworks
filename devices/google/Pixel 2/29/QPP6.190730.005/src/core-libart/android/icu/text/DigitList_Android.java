/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.math.BigDecimal;
import java.math.BigInteger;

public final class DigitList_Android {
    public static final int DBL_DIG = 17;
    private static byte[] LONG_MIN_REP;
    public static final int MAX_LONG_DIGITS = 19;
    public int count = 0;
    public int decimalAt = 0;
    private boolean didRound = false;
    public byte[] digits = new byte[19];

    static {
        String string = Long.toString(Long.MIN_VALUE);
        LONG_MIN_REP = new byte[19];
        for (int i = 0; i < 19; ++i) {
            DigitList_Android.LONG_MIN_REP[i] = (byte)string.charAt(i + 1);
        }
    }

    private final void ensureCapacity(int n, int n2) {
        byte[] arrby = this.digits;
        if (n > arrby.length) {
            byte[] arrby2 = new byte[n * 2];
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)n2);
            this.digits = arrby2;
        }
    }

    private String getStringRep(boolean bl) {
        int n;
        if (this.isZero()) {
            return "0";
        }
        StringBuilder stringBuilder = new StringBuilder(this.count + 1);
        if (!bl) {
            stringBuilder.append('-');
        }
        int n2 = n = this.decimalAt;
        if (n < 0) {
            stringBuilder.append('.');
            while (n < 0) {
                stringBuilder.append('0');
                ++n;
            }
            n2 = -1;
        }
        n = 0;
        do {
            if (n >= this.count) break;
            if (n2 == n) {
                stringBuilder.append('.');
            }
            stringBuilder.append((char)this.digits[n]);
            ++n;
        } while (true);
        for (int i = n2; i > this.count; --i) {
            stringBuilder.append('0');
        }
        return stringBuilder.toString();
    }

    private boolean isLongMIN_VALUE() {
        int n = this.decimalAt;
        int n2 = this.count;
        if (n == n2 && n2 == 19) {
            for (n2 = 0; n2 < this.count; ++n2) {
                if (this.digits[n2] == LONG_MIN_REP[n2]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private void set(String string, int n) {
        int n2;
        int n3;
        block11 : {
            this.decimalAt = -1;
            this.count = 0;
            int n4 = 0;
            n3 = 0;
            int n5 = 0;
            int n6 = 0;
            n2 = n3;
            int n7 = n5;
            if (string.charAt(0) == '-') {
                n6 = 0 + 1;
                n7 = n5;
                n2 = n3;
            }
            do {
                int n8;
                int n9;
                n3 = n4;
                if (n6 >= string.length()) break block11;
                char c = string.charAt(n6);
                if (c == '.') {
                    this.decimalAt = this.count;
                    n8 = n2;
                    n9 = n7;
                } else {
                    if (c == 'e' || c == 'E') break;
                    n8 = n2;
                    n9 = n7;
                    if (this.count < n) {
                        n5 = n2;
                        n3 = n7;
                        if (n7 == 0) {
                            n7 = c != '0' ? 1 : 0;
                            n5 = n2;
                            n3 = n7;
                            if (n7 == 0) {
                                n5 = n2;
                                n3 = n7;
                                if (this.decimalAt != -1) {
                                    n5 = n2 + 1;
                                    n3 = n7;
                                }
                            }
                        }
                        n8 = n5;
                        n9 = n3;
                        if (n3 != 0) {
                            n2 = this.count;
                            this.ensureCapacity(n2 + 1, n2);
                            byte[] arrby = this.digits;
                            n2 = this.count;
                            this.count = n2 + 1;
                            arrby[n2] = (byte)c;
                            n9 = n3;
                            n8 = n5;
                        }
                    }
                }
                ++n6;
                n2 = n8;
                n7 = n9;
            } while (true);
            n = ++n6;
            if (string.charAt(n6) == '+') {
                n = n6 + 1;
            }
            n3 = Integer.valueOf(string.substring(n));
        }
        if (this.decimalAt == -1) {
            this.decimalAt = this.count;
        }
        this.decimalAt += n3 - n2;
    }

    private void setBigDecimalDigits(String string, int n, boolean bl) {
        this.didRound = false;
        this.set(string, string.length());
        if (bl) {
            n = this.decimalAt + n;
        } else if (n == 0) {
            n = -1;
        }
        this.round(n);
    }

    private boolean shouldRoundUp(int n) {
        int n2 = this.count;
        boolean bl = false;
        if (n < n2) {
            byte[] arrby = this.digits;
            if (arrby[n] > 53) {
                return true;
            }
            if (arrby[n] == 53) {
                for (n2 = n + 1; n2 < this.count; ++n2) {
                    if (this.digits[n2] == 48) continue;
                    return true;
                }
                boolean bl2 = bl;
                if (n > 0) {
                    bl2 = bl;
                    if (this.digits[n - 1] % 2 != 0) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
        }
        return false;
    }

    public void append(int n) {
        int n2 = this.count;
        this.ensureCapacity(n2 + 1, n2);
        byte[] arrby = this.digits;
        n2 = this.count;
        this.count = n2 + 1;
        arrby[n2] = (byte)n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DigitList_Android)) {
            return false;
        }
        object = (DigitList_Android)object;
        if (this.count == ((DigitList_Android)object).count && this.decimalAt == ((DigitList_Android)object).decimalAt) {
            for (int i = 0; i < this.count; ++i) {
                if (this.digits[i] == ((DigitList_Android)object).digits[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public BigDecimal getBigDecimalICU(boolean bl) {
        if (this.isZero()) {
            return BigDecimal.valueOf(0L);
        }
        int n = this.count;
        long l = (long)n - (long)this.decimalAt;
        if (l > 0L) {
            int n2;
            int n3 = n2 = this.count;
            if (l > Integer.MAX_VALUE) {
                long l2 = l - Integer.MAX_VALUE;
                if (l2 < (long)n) {
                    n3 = (int)((long)n2 - l2);
                } else {
                    return new BigDecimal(0);
                }
            }
            StringBuilder stringBuilder = new StringBuilder(n3 + 1);
            if (!bl) {
                stringBuilder.append('-');
            }
            for (n2 = 0; n2 < n3; ++n2) {
                stringBuilder.append((char)this.digits[n2]);
            }
            return new BigDecimal(new BigInteger(stringBuilder.toString()), (int)l);
        }
        return new BigDecimal(this.getStringRep(bl));
    }

    public BigInteger getBigInteger(boolean bl) {
        if (this.isZero()) {
            return BigInteger.valueOf(0L);
        }
        int n = this.decimalAt;
        int n2 = this.count;
        if (n > n2) {
            n2 = n;
        }
        n = n2;
        if (!bl) {
            n = n2 + 1;
        }
        char[] arrc = new char[n];
        if (!bl) {
            arrc[0] = (char)45;
            for (n2 = 0; n2 < (n = this.count); ++n2) {
                arrc[n2 + 1] = (char)this.digits[n2];
            }
            n2 = n + 1;
        } else {
            for (n2 = 0; n2 < this.count; ++n2) {
                arrc[n2] = (char)this.digits[n2];
            }
            n2 = this.count;
        }
        while (n2 < arrc.length) {
            arrc[n2] = (char)48;
            ++n2;
        }
        return new BigInteger(new String(arrc));
    }

    public byte getDigitValue(int n) {
        return (byte)(this.digits[n] - 48);
    }

    public final double getDouble() {
        int n = this.count;
        if (n == 0) {
            return 0.0;
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        stringBuilder.append('.');
        for (n = 0; n < this.count; ++n) {
            stringBuilder.append((char)this.digits[n]);
        }
        stringBuilder.append('E');
        stringBuilder.append(Integer.toString(this.decimalAt));
        return Double.valueOf(stringBuilder.toString());
    }

    public final long getLong() {
        if (this.count == 0) {
            return 0L;
        }
        if (this.isLongMIN_VALUE()) {
            return Long.MIN_VALUE;
        }
        StringBuilder stringBuilder = new StringBuilder(this.count);
        for (int i = 0; i < this.decimalAt; ++i) {
            char c;
            char c2 = i < this.count ? (c = (char)((char)this.digits[i])) : (c = '0');
            stringBuilder.append(c2);
        }
        return Long.parseLong(stringBuilder.toString());
    }

    public int hashCode() {
        int n = this.decimalAt;
        for (int i = 0; i < this.count; ++i) {
            n = n * 37 + this.digits[i];
        }
        return n;
    }

    boolean isIntegral() {
        int n;
        while ((n = this.count) > 0 && this.digits[n - 1] == 48) {
            this.count = n - 1;
        }
        n = this.count;
        boolean bl = n == 0 || this.decimalAt >= n;
        return bl;
    }

    boolean isZero() {
        for (int i = 0; i < this.count; ++i) {
            if (this.digits[i] == 48) continue;
            return false;
        }
        return true;
    }

    public final void round(int n) {
        if (n >= 0 && n < this.count) {
            int n2 = n;
            if (this.shouldRoundUp(n)) {
                byte[] arrby;
                do {
                    if ((n2 = n - 1) < 0) {
                        this.digits[0] = (byte)49;
                        ++this.decimalAt;
                        n2 = 0;
                        this.didRound = true;
                        break;
                    }
                    arrby = this.digits;
                    arrby[n2] = (byte)(arrby[n2] + 1);
                    this.didRound = true;
                    n = n2;
                } while (arrby[n2] > 57);
                ++n2;
            }
            this.count = n2;
        }
        while ((n = this.count) > 1 && this.digits[n - 1] == 48) {
            this.count = n - 1;
        }
    }

    final void set(double d, int n, boolean bl) {
        int n2;
        double d2 = d;
        if (d == 0.0) {
            d2 = 0.0;
        }
        String string = Double.toString(d2);
        this.didRound = false;
        this.set(string, 19);
        if (bl) {
            if (-(n2 = this.decimalAt++) > n) {
                this.count = 0;
                return;
            }
            if (-n2 == n) {
                if (this.shouldRoundUp(0)) {
                    this.count = 1;
                    this.digits[0] = (byte)49;
                } else {
                    this.count = 0;
                }
                return;
            }
        }
        while ((n2 = this.count) > 1 && this.digits[n2 - 1] == 48) {
            this.count = n2 - 1;
        }
        if (bl) {
            n = this.decimalAt + n;
        } else if (n == 0) {
            n = -1;
        }
        this.round(n);
    }

    public final void set(long l) {
        this.set(l, 0);
    }

    public final void set(long l, int n) {
        this.didRound = false;
        if (l <= 0L) {
            if (l == Long.MIN_VALUE) {
                this.count = 19;
                this.decimalAt = 19;
                System.arraycopy((byte[])LONG_MIN_REP, (int)0, (byte[])this.digits, (int)0, (int)this.count);
            } else {
                this.count = 0;
                this.decimalAt = 0;
            }
        } else {
            byte[] arrby;
            int n2 = 19;
            while (l > 0L) {
                arrby = this.digits;
                arrby[--n2] = (byte)(l % 10L + 48L);
                l /= 10L;
            }
            this.decimalAt = 19 - n2;
            int n3 = 18;
            while ((arrby = this.digits)[n3] == 48) {
                --n3;
            }
            this.count = n3 - n2 + 1;
            System.arraycopy((byte[])arrby, (int)n2, (byte[])arrby, (int)0, (int)this.count);
        }
        if (n > 0) {
            this.round(n);
        }
    }

    public final void set(BigDecimal bigDecimal, int n, boolean bl) {
        this.setBigDecimalDigits(bigDecimal.toString(), n, bl);
    }

    public final void set(java.math.BigDecimal bigDecimal, int n, boolean bl) {
        this.setBigDecimalDigits(bigDecimal.toString(), n, bl);
    }

    public final void set(BigInteger object, int n) {
        int n2;
        object = ((BigInteger)object).toString();
        this.decimalAt = n2 = ((String)object).length();
        this.count = n2;
        this.didRound = false;
        while ((n2 = this.count--) > 1 && ((String)object).charAt(n2 - 1) == '0') {
        }
        n2 = 0;
        if (((String)object).charAt(0) == '-') {
            n2 = 0 + 1;
            --this.count;
            --this.decimalAt;
        }
        this.ensureCapacity(this.count, 0);
        for (int i = 0; i < this.count; ++i) {
            this.digits[i] = (byte)((String)object).charAt(i + n2);
        }
        if (n > 0) {
            this.round(n);
        }
    }

    public String toString() {
        if (this.isZero()) {
            return "0";
        }
        StringBuilder stringBuilder = new StringBuilder("0.");
        for (int i = 0; i < this.count; ++i) {
            stringBuilder.append((char)this.digits[i]);
        }
        stringBuilder.append("x10^");
        stringBuilder.append(this.decimalAt);
        return stringBuilder.toString();
    }

    public boolean wasRounded() {
        return this.didRound;
    }
}


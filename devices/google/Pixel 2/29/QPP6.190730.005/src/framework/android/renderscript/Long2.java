/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Long2 {
    public long x;
    public long y;

    public Long2() {
    }

    public Long2(long l) {
        this.y = l;
        this.x = l;
    }

    public Long2(long l, long l2) {
        this.x = l;
        this.y = l2;
    }

    public Long2(Long2 long2) {
        this.x = long2.x;
        this.y = long2.y;
    }

    public static Long2 add(Long2 long2, long l) {
        Long2 long22 = new Long2();
        long22.x = long2.x + l;
        long22.y = long2.y + l;
        return long22;
    }

    public static Long2 add(Long2 long2, Long2 long22) {
        Long2 long23 = new Long2();
        long23.x = long2.x + long22.x;
        long23.y = long2.y + long22.y;
        return long23;
    }

    public static Long2 div(Long2 long2, long l) {
        Long2 long22 = new Long2();
        long22.x = long2.x / l;
        long22.y = long2.y / l;
        return long22;
    }

    public static Long2 div(Long2 long2, Long2 long22) {
        Long2 long23 = new Long2();
        long23.x = long2.x / long22.x;
        long23.y = long2.y / long22.y;
        return long23;
    }

    public static long dotProduct(Long2 long2, Long2 long22) {
        return long22.x * long2.x + long22.y * long2.y;
    }

    public static Long2 mod(Long2 long2, long l) {
        Long2 long22 = new Long2();
        long22.x = long2.x % l;
        long22.y = long2.y % l;
        return long22;
    }

    public static Long2 mod(Long2 long2, Long2 long22) {
        Long2 long23 = new Long2();
        long23.x = long2.x % long22.x;
        long23.y = long2.y % long22.y;
        return long23;
    }

    public static Long2 mul(Long2 long2, long l) {
        Long2 long22 = new Long2();
        long22.x = long2.x * l;
        long22.y = long2.y * l;
        return long22;
    }

    public static Long2 mul(Long2 long2, Long2 long22) {
        Long2 long23 = new Long2();
        long23.x = long2.x * long22.x;
        long23.y = long2.y * long22.y;
        return long23;
    }

    public static Long2 sub(Long2 long2, long l) {
        Long2 long22 = new Long2();
        long22.x = long2.x - l;
        long22.y = long2.y - l;
        return long22;
    }

    public static Long2 sub(Long2 long2, Long2 long22) {
        Long2 long23 = new Long2();
        long23.x = long2.x - long22.x;
        long23.y = long2.y - long22.y;
        return long23;
    }

    public void add(long l) {
        this.x += l;
        this.y += l;
    }

    public void add(Long2 long2) {
        this.x += long2.x;
        this.y += long2.y;
    }

    public void addAt(int n, long l) {
        if (n != 0) {
            if (n == 1) {
                this.y += l;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x += l;
    }

    public void addMultiple(Long2 long2, long l) {
        this.x += long2.x * l;
        this.y += long2.y * l;
    }

    public void copyTo(long[] arrl, int n) {
        arrl[n] = this.x;
        arrl[n + 1] = this.y;
    }

    public void div(long l) {
        this.x /= l;
        this.y /= l;
    }

    public void div(Long2 long2) {
        this.x /= long2.x;
        this.y /= long2.y;
    }

    public long dotProduct(Long2 long2) {
        return this.x * long2.x + this.y * long2.y;
    }

    public long elementSum() {
        return this.x + this.y;
    }

    public long get(int n) {
        if (n != 0) {
            if (n == 1) {
                return this.y;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        return this.x;
    }

    public long length() {
        return 2L;
    }

    public void mod(long l) {
        this.x %= l;
        this.y %= l;
    }

    public void mod(Long2 long2) {
        this.x %= long2.x;
        this.y %= long2.y;
    }

    public void mul(long l) {
        this.x *= l;
        this.y *= l;
    }

    public void mul(Long2 long2) {
        this.x *= long2.x;
        this.y *= long2.y;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public void set(Long2 long2) {
        this.x = long2.x;
        this.y = long2.y;
    }

    public void setAt(int n, long l) {
        if (n != 0) {
            if (n == 1) {
                this.y = l;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x = l;
    }

    public void setValues(long l, long l2) {
        this.x = l;
        this.y = l2;
    }

    public void sub(long l) {
        this.x -= l;
        this.y -= l;
    }

    public void sub(Long2 long2) {
        this.x -= long2.x;
        this.y -= long2.y;
    }
}


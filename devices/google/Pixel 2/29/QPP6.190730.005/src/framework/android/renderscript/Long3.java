/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Long3 {
    public long x;
    public long y;
    public long z;

    public Long3() {
    }

    public Long3(long l) {
        this.z = l;
        this.y = l;
        this.x = l;
    }

    public Long3(long l, long l2, long l3) {
        this.x = l;
        this.y = l2;
        this.z = l3;
    }

    public Long3(Long3 long3) {
        this.x = long3.x;
        this.y = long3.y;
        this.z = long3.z;
    }

    public static Long3 add(Long3 long3, long l) {
        Long3 long32 = new Long3();
        long32.x = long3.x + l;
        long32.y = long3.y + l;
        long32.z = long3.z + l;
        return long32;
    }

    public static Long3 add(Long3 long3, Long3 long32) {
        Long3 long33 = new Long3();
        long33.x = long3.x + long32.x;
        long33.y = long3.y + long32.y;
        long33.z = long3.z + long32.z;
        return long33;
    }

    public static Long3 div(Long3 long3, long l) {
        Long3 long32 = new Long3();
        long32.x = long3.x / l;
        long32.y = long3.y / l;
        long32.z = long3.z / l;
        return long32;
    }

    public static Long3 div(Long3 long3, Long3 long32) {
        Long3 long33 = new Long3();
        long33.x = long3.x / long32.x;
        long33.y = long3.y / long32.y;
        long33.z = long3.z / long32.z;
        return long33;
    }

    public static long dotProduct(Long3 long3, Long3 long32) {
        return long32.x * long3.x + long32.y * long3.y + long32.z * long3.z;
    }

    public static Long3 mod(Long3 long3, long l) {
        Long3 long32 = new Long3();
        long32.x = long3.x % l;
        long32.y = long3.y % l;
        long32.z = long3.z % l;
        return long32;
    }

    public static Long3 mod(Long3 long3, Long3 long32) {
        Long3 long33 = new Long3();
        long33.x = long3.x % long32.x;
        long33.y = long3.y % long32.y;
        long33.z = long3.z % long32.z;
        return long33;
    }

    public static Long3 mul(Long3 long3, long l) {
        Long3 long32 = new Long3();
        long32.x = long3.x * l;
        long32.y = long3.y * l;
        long32.z = long3.z * l;
        return long32;
    }

    public static Long3 mul(Long3 long3, Long3 long32) {
        Long3 long33 = new Long3();
        long33.x = long3.x * long32.x;
        long33.y = long3.y * long32.y;
        long33.z = long3.z * long32.z;
        return long33;
    }

    public static Long3 sub(Long3 long3, long l) {
        Long3 long32 = new Long3();
        long32.x = long3.x - l;
        long32.y = long3.y - l;
        long32.z = long3.z - l;
        return long32;
    }

    public static Long3 sub(Long3 long3, Long3 long32) {
        Long3 long33 = new Long3();
        long33.x = long3.x - long32.x;
        long33.y = long3.y - long32.y;
        long33.z = long3.z - long32.z;
        return long33;
    }

    public void add(long l) {
        this.x += l;
        this.y += l;
        this.z += l;
    }

    public void add(Long3 long3) {
        this.x += long3.x;
        this.y += long3.y;
        this.z += long3.z;
    }

    public void addAt(int n, long l) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.z += l;
                    return;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            this.y += l;
            return;
        }
        this.x += l;
    }

    public void addMultiple(Long3 long3, long l) {
        this.x += long3.x * l;
        this.y += long3.y * l;
        this.z += long3.z * l;
    }

    public void copyTo(long[] arrl, int n) {
        arrl[n] = this.x;
        arrl[n + 1] = this.y;
        arrl[n + 2] = this.z;
    }

    public void div(long l) {
        this.x /= l;
        this.y /= l;
        this.z /= l;
    }

    public void div(Long3 long3) {
        this.x /= long3.x;
        this.y /= long3.y;
        this.z /= long3.z;
    }

    public long dotProduct(Long3 long3) {
        return this.x * long3.x + this.y * long3.y + this.z * long3.z;
    }

    public long elementSum() {
        return this.x + this.y + this.z;
    }

    public long get(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    return this.z;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            return this.y;
        }
        return this.x;
    }

    public long length() {
        return 3L;
    }

    public void mod(long l) {
        this.x %= l;
        this.y %= l;
        this.z %= l;
    }

    public void mod(Long3 long3) {
        this.x %= long3.x;
        this.y %= long3.y;
        this.z %= long3.z;
    }

    public void mul(long l) {
        this.x *= l;
        this.y *= l;
        this.z *= l;
    }

    public void mul(Long3 long3) {
        this.x *= long3.x;
        this.y *= long3.y;
        this.z *= long3.z;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public void set(Long3 long3) {
        this.x = long3.x;
        this.y = long3.y;
        this.z = long3.z;
    }

    public void setAt(int n, long l) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.z = l;
                    return;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            this.y = l;
            return;
        }
        this.x = l;
    }

    public void setValues(long l, long l2, long l3) {
        this.x = l;
        this.y = l2;
        this.z = l3;
    }

    public void sub(long l) {
        this.x -= l;
        this.y -= l;
        this.z -= l;
    }

    public void sub(Long3 long3) {
        this.x -= long3.x;
        this.y -= long3.y;
        this.z -= long3.z;
    }
}


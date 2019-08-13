/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Long4 {
    public long w;
    public long x;
    public long y;
    public long z;

    public Long4() {
    }

    public Long4(long l) {
        this.w = l;
        this.z = l;
        this.y = l;
        this.x = l;
    }

    public Long4(long l, long l2, long l3, long l4) {
        this.x = l;
        this.y = l2;
        this.z = l3;
        this.w = l4;
    }

    public Long4(Long4 long4) {
        this.x = long4.x;
        this.y = long4.y;
        this.z = long4.z;
        this.w = long4.w;
    }

    public static Long4 add(Long4 long4, long l) {
        Long4 long42 = new Long4();
        long42.x = long4.x + l;
        long42.y = long4.y + l;
        long42.z = long4.z + l;
        long42.w = long4.w + l;
        return long42;
    }

    public static Long4 add(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x + long42.x;
        long43.y = long4.y + long42.y;
        long43.z = long4.z + long42.z;
        long43.w = long4.w + long42.w;
        return long43;
    }

    public static Long4 div(Long4 long4, long l) {
        Long4 long42 = new Long4();
        long42.x = long4.x / l;
        long42.y = long4.y / l;
        long42.z = long4.z / l;
        long42.w = long4.w / l;
        return long42;
    }

    public static Long4 div(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x / long42.x;
        long43.y = long4.y / long42.y;
        long43.z = long4.z / long42.z;
        long43.w = long4.w / long42.w;
        return long43;
    }

    public static long dotProduct(Long4 long4, Long4 long42) {
        return long42.x * long4.x + long42.y * long4.y + long42.z * long4.z + long42.w * long4.w;
    }

    public static Long4 mod(Long4 long4, long l) {
        Long4 long42 = new Long4();
        long42.x = long4.x % l;
        long42.y = long4.y % l;
        long42.z = long4.z % l;
        long42.w = long4.w % l;
        return long42;
    }

    public static Long4 mod(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x % long42.x;
        long43.y = long4.y % long42.y;
        long43.z = long4.z % long42.z;
        long43.w = long4.w % long42.w;
        return long43;
    }

    public static Long4 mul(Long4 long4, long l) {
        Long4 long42 = new Long4();
        long42.x = long4.x * l;
        long42.y = long4.y * l;
        long42.z = long4.z * l;
        long42.w = long4.w * l;
        return long42;
    }

    public static Long4 mul(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x * long42.x;
        long43.y = long4.y * long42.y;
        long43.z = long4.z * long42.z;
        long43.w = long4.w * long42.w;
        return long43;
    }

    public static Long4 sub(Long4 long4, long l) {
        Long4 long42 = new Long4();
        long42.x = long4.x - l;
        long42.y = long4.y - l;
        long42.z = long4.z - l;
        long42.w = long4.w - l;
        return long42;
    }

    public static Long4 sub(Long4 long4, Long4 long42) {
        Long4 long43 = new Long4();
        long43.x = long4.x - long42.x;
        long43.y = long4.y - long42.y;
        long43.z = long4.z - long42.z;
        long43.w = long4.w - long42.w;
        return long43;
    }

    public void add(long l) {
        this.x += l;
        this.y += l;
        this.z += l;
        this.w += l;
    }

    public void add(Long4 long4) {
        this.x += long4.x;
        this.y += long4.y;
        this.z += long4.z;
        this.w += long4.w;
    }

    public void addAt(int n, long l) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.w += l;
                        return;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                this.z += l;
                return;
            }
            this.y += l;
            return;
        }
        this.x += l;
    }

    public void addMultiple(Long4 long4, long l) {
        this.x += long4.x * l;
        this.y += long4.y * l;
        this.z += long4.z * l;
        this.w += long4.w * l;
    }

    public void copyTo(long[] arrl, int n) {
        arrl[n] = this.x;
        arrl[n + 1] = this.y;
        arrl[n + 2] = this.z;
        arrl[n + 3] = this.w;
    }

    public void div(long l) {
        this.x /= l;
        this.y /= l;
        this.z /= l;
        this.w /= l;
    }

    public void div(Long4 long4) {
        this.x /= long4.x;
        this.y /= long4.y;
        this.z /= long4.z;
        this.w /= long4.w;
    }

    public long dotProduct(Long4 long4) {
        return this.x * long4.x + this.y * long4.y + this.z * long4.z + this.w * long4.w;
    }

    public long elementSum() {
        return this.x + this.y + this.z + this.w;
    }

    public long get(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        return this.w;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                return this.z;
            }
            return this.y;
        }
        return this.x;
    }

    public long length() {
        return 4L;
    }

    public void mod(long l) {
        this.x %= l;
        this.y %= l;
        this.z %= l;
        this.w %= l;
    }

    public void mod(Long4 long4) {
        this.x %= long4.x;
        this.y %= long4.y;
        this.z %= long4.z;
        this.w %= long4.w;
    }

    public void mul(long l) {
        this.x *= l;
        this.y *= l;
        this.z *= l;
        this.w *= l;
    }

    public void mul(Long4 long4) {
        this.x *= long4.x;
        this.y *= long4.y;
        this.z *= long4.z;
        this.w *= long4.w;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public void set(Long4 long4) {
        this.x = long4.x;
        this.y = long4.y;
        this.z = long4.z;
        this.w = long4.w;
    }

    public void setAt(int n, long l) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.w = l;
                        return;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                this.z = l;
                return;
            }
            this.y = l;
            return;
        }
        this.x = l;
    }

    public void setValues(long l, long l2, long l3, long l4) {
        this.x = l;
        this.y = l2;
        this.z = l3;
        this.w = l4;
    }

    public void sub(long l) {
        this.x -= l;
        this.y -= l;
        this.z -= l;
        this.w -= l;
    }

    public void sub(Long4 long4) {
        this.x -= long4.x;
        this.y -= long4.y;
        this.z -= long4.z;
        this.w -= long4.w;
    }
}


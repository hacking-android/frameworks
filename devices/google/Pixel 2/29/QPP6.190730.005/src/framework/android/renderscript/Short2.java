/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Short2 {
    public short x;
    public short y;

    public Short2() {
    }

    public Short2(Short2 short2) {
        this.x = short2.x;
        this.y = short2.y;
    }

    public Short2(short s) {
        this.y = s;
        this.x = s;
    }

    public Short2(short s, short s2) {
        this.x = s;
        this.y = s2;
    }

    public static Short2 add(Short2 short2, Short2 short22) {
        Short2 short23 = new Short2();
        short23.x = (short)(short2.x + short22.x);
        short23.y = (short)(short2.y + short22.y);
        return short23;
    }

    public static Short2 add(Short2 short2, short s) {
        Short2 short22 = new Short2();
        short22.x = (short)(short2.x + s);
        short22.y = (short)(short2.y + s);
        return short22;
    }

    public static Short2 div(Short2 short2, Short2 short22) {
        Short2 short23 = new Short2();
        short23.x = (short)(short2.x / short22.x);
        short23.y = (short)(short2.y / short22.y);
        return short23;
    }

    public static Short2 div(Short2 short2, short s) {
        Short2 short22 = new Short2();
        short22.x = (short)(short2.x / s);
        short22.y = (short)(short2.y / s);
        return short22;
    }

    public static short dotProduct(Short2 short2, Short2 short22) {
        return (short)(short22.x * short2.x + short22.y * short2.y);
    }

    public static Short2 mod(Short2 short2, Short2 short22) {
        Short2 short23 = new Short2();
        short23.x = (short)(short2.x % short22.x);
        short23.y = (short)(short2.y % short22.y);
        return short23;
    }

    public static Short2 mod(Short2 short2, short s) {
        Short2 short22 = new Short2();
        short22.x = (short)(short2.x % s);
        short22.y = (short)(short2.y % s);
        return short22;
    }

    public static Short2 mul(Short2 short2, Short2 short22) {
        Short2 short23 = new Short2();
        short23.x = (short)(short2.x * short22.x);
        short23.y = (short)(short2.y * short22.y);
        return short23;
    }

    public static Short2 mul(Short2 short2, short s) {
        Short2 short22 = new Short2();
        short22.x = (short)(short2.x * s);
        short22.y = (short)(short2.y * s);
        return short22;
    }

    public static Short2 sub(Short2 short2, Short2 short22) {
        Short2 short23 = new Short2();
        short23.x = (short)(short2.x - short22.x);
        short23.y = (short)(short2.y - short22.y);
        return short23;
    }

    public static Short2 sub(Short2 short2, short s) {
        Short2 short22 = new Short2();
        short22.x = (short)(short2.x - s);
        short22.y = (short)(short2.y - s);
        return short22;
    }

    public void add(Short2 short2) {
        this.x = (short)(this.x + short2.x);
        this.y = (short)(this.y + short2.y);
    }

    public void add(short s) {
        this.x = (short)(this.x + s);
        this.y = (short)(this.y + s);
    }

    public void addAt(int n, short s) {
        if (n != 0) {
            if (n == 1) {
                this.y = (short)(this.y + s);
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x = (short)(this.x + s);
    }

    public void addMultiple(Short2 short2, short s) {
        this.x = (short)(this.x + short2.x * s);
        this.y = (short)(this.y + short2.y * s);
    }

    public void copyTo(short[] arrs, int n) {
        arrs[n] = this.x;
        arrs[n + 1] = this.y;
    }

    public void div(Short2 short2) {
        this.x = (short)(this.x / short2.x);
        this.y = (short)(this.y / short2.y);
    }

    public void div(short s) {
        this.x = (short)(this.x / s);
        this.y = (short)(this.y / s);
    }

    public short dotProduct(Short2 short2) {
        return (short)(this.x * short2.x + this.y * short2.y);
    }

    public short elementSum() {
        return (short)(this.x + this.y);
    }

    public short get(int n) {
        if (n != 0) {
            if (n == 1) {
                return this.y;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        return this.x;
    }

    public short length() {
        return 2;
    }

    public void mod(Short2 short2) {
        this.x = (short)(this.x % short2.x);
        this.y = (short)(this.y % short2.y);
    }

    public void mod(short s) {
        this.x = (short)(this.x % s);
        this.y = (short)(this.y % s);
    }

    public void mul(Short2 short2) {
        this.x = (short)(this.x * short2.x);
        this.y = (short)(this.y * short2.y);
    }

    public void mul(short s) {
        this.x = (short)(this.x * s);
        this.y = (short)(this.y * s);
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public void set(Short2 short2) {
        this.x = short2.x;
        this.y = short2.y;
    }

    public void setAt(int n, short s) {
        if (n != 0) {
            if (n == 1) {
                this.y = s;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x = s;
    }

    public void setValues(short s, short s2) {
        this.x = s;
        this.y = s2;
    }

    public void sub(Short2 short2) {
        this.x = (short)(this.x - short2.x);
        this.y = (short)(this.y - short2.y);
    }

    public void sub(short s) {
        this.x = (short)(this.x - s);
        this.y = (short)(this.y - s);
    }
}


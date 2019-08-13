/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Byte2 {
    public byte x;
    public byte y;

    public Byte2() {
    }

    public Byte2(byte by, byte by2) {
        this.x = by;
        this.y = by2;
    }

    public Byte2(Byte2 byte2) {
        this.x = byte2.x;
        this.y = byte2.y;
    }

    public static Byte2 add(Byte2 byte2, byte by) {
        Byte2 byte22 = new Byte2();
        byte22.x = (byte)(byte2.x + by);
        byte22.y = (byte)(byte2.y + by);
        return byte22;
    }

    public static Byte2 add(Byte2 byte2, Byte2 byte22) {
        Byte2 byte23 = new Byte2();
        byte23.x = (byte)(byte2.x + byte22.x);
        byte23.y = (byte)(byte2.y + byte22.y);
        return byte23;
    }

    public static Byte2 div(Byte2 byte2, byte by) {
        Byte2 byte22 = new Byte2();
        byte22.x = (byte)(byte2.x / by);
        byte22.y = (byte)(byte2.y / by);
        return byte22;
    }

    public static Byte2 div(Byte2 byte2, Byte2 byte22) {
        Byte2 byte23 = new Byte2();
        byte23.x = (byte)(byte2.x / byte22.x);
        byte23.y = (byte)(byte2.y / byte22.y);
        return byte23;
    }

    public static byte dotProduct(Byte2 byte2, Byte2 byte22) {
        return (byte)(byte22.x * byte2.x + byte22.y * byte2.y);
    }

    public static Byte2 mul(Byte2 byte2, byte by) {
        Byte2 byte22 = new Byte2();
        byte22.x = (byte)(byte2.x * by);
        byte22.y = (byte)(byte2.y * by);
        return byte22;
    }

    public static Byte2 mul(Byte2 byte2, Byte2 byte22) {
        Byte2 byte23 = new Byte2();
        byte23.x = (byte)(byte2.x * byte22.x);
        byte23.y = (byte)(byte2.y * byte22.y);
        return byte23;
    }

    public static Byte2 sub(Byte2 byte2, byte by) {
        Byte2 byte22 = new Byte2();
        byte22.x = (byte)(byte2.x - by);
        byte22.y = (byte)(byte2.y - by);
        return byte22;
    }

    public static Byte2 sub(Byte2 byte2, Byte2 byte22) {
        Byte2 byte23 = new Byte2();
        byte23.x = (byte)(byte2.x - byte22.x);
        byte23.y = (byte)(byte2.y - byte22.y);
        return byte23;
    }

    public void add(byte by) {
        this.x = (byte)(this.x + by);
        this.y = (byte)(this.y + by);
    }

    public void add(Byte2 byte2) {
        this.x = (byte)(this.x + byte2.x);
        this.y = (byte)(this.y + byte2.y);
    }

    public void addAt(int n, byte by) {
        if (n != 0) {
            if (n == 1) {
                this.y = (byte)(this.y + by);
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x = (byte)(this.x + by);
    }

    public void addMultiple(Byte2 byte2, byte by) {
        this.x = (byte)(this.x + byte2.x * by);
        this.y = (byte)(this.y + byte2.y * by);
    }

    public void copyTo(byte[] arrby, int n) {
        arrby[n] = this.x;
        arrby[n + 1] = this.y;
    }

    public void div(byte by) {
        this.x = (byte)(this.x / by);
        this.y = (byte)(this.y / by);
    }

    public void div(Byte2 byte2) {
        this.x = (byte)(this.x / byte2.x);
        this.y = (byte)(this.y / byte2.y);
    }

    public byte dotProduct(Byte2 byte2) {
        return (byte)(this.x * byte2.x + this.y * byte2.y);
    }

    public byte elementSum() {
        return (byte)(this.x + this.y);
    }

    public byte get(int n) {
        if (n != 0) {
            if (n == 1) {
                return this.y;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        return this.x;
    }

    public byte length() {
        return 2;
    }

    public void mul(byte by) {
        this.x = (byte)(this.x * by);
        this.y = (byte)(this.y * by);
    }

    public void mul(Byte2 byte2) {
        this.x = (byte)(this.x * byte2.x);
        this.y = (byte)(this.y * byte2.y);
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public void set(Byte2 byte2) {
        this.x = byte2.x;
        this.y = byte2.y;
    }

    public void setAt(int n, byte by) {
        if (n != 0) {
            if (n == 1) {
                this.y = by;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x = by;
    }

    public void setValues(byte by, byte by2) {
        this.x = by;
        this.y = by2;
    }

    public void sub(byte by) {
        this.x = (byte)(this.x - by);
        this.y = (byte)(this.y - by);
    }

    public void sub(Byte2 byte2) {
        this.x = (byte)(this.x - byte2.x);
        this.y = (byte)(this.y - byte2.y);
    }
}


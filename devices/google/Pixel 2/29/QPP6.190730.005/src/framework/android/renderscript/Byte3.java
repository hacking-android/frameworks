/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Byte3 {
    public byte x;
    public byte y;
    public byte z;

    public Byte3() {
    }

    public Byte3(byte by, byte by2, byte by3) {
        this.x = by;
        this.y = by2;
        this.z = by3;
    }

    public Byte3(Byte3 byte3) {
        this.x = byte3.x;
        this.y = byte3.y;
        this.z = byte3.z;
    }

    public static Byte3 add(Byte3 byte3, byte by) {
        Byte3 byte32 = new Byte3();
        byte32.x = (byte)(byte3.x + by);
        byte32.y = (byte)(byte3.y + by);
        byte32.z = (byte)(byte3.z + by);
        return byte32;
    }

    public static Byte3 add(Byte3 byte3, Byte3 byte32) {
        Byte3 byte33 = new Byte3();
        byte33.x = (byte)(byte3.x + byte32.x);
        byte33.y = (byte)(byte3.y + byte32.y);
        byte33.z = (byte)(byte3.z + byte32.z);
        return byte33;
    }

    public static Byte3 div(Byte3 byte3, byte by) {
        Byte3 byte32 = new Byte3();
        byte32.x = (byte)(byte3.x / by);
        byte32.y = (byte)(byte3.y / by);
        byte32.z = (byte)(byte3.z / by);
        return byte32;
    }

    public static Byte3 div(Byte3 byte3, Byte3 byte32) {
        Byte3 byte33 = new Byte3();
        byte33.x = (byte)(byte3.x / byte32.x);
        byte33.y = (byte)(byte3.y / byte32.y);
        byte33.z = (byte)(byte3.z / byte32.z);
        return byte33;
    }

    public static byte dotProduct(Byte3 byte3, Byte3 byte32) {
        return (byte)((byte)((byte)(byte32.x * byte3.x) + (byte)(byte32.y * byte3.y)) + (byte)(byte32.z * byte3.z));
    }

    public static Byte3 mul(Byte3 byte3, byte by) {
        Byte3 byte32 = new Byte3();
        byte32.x = (byte)(byte3.x * by);
        byte32.y = (byte)(byte3.y * by);
        byte32.z = (byte)(byte3.z * by);
        return byte32;
    }

    public static Byte3 mul(Byte3 byte3, Byte3 byte32) {
        Byte3 byte33 = new Byte3();
        byte33.x = (byte)(byte3.x * byte32.x);
        byte33.y = (byte)(byte3.y * byte32.y);
        byte33.z = (byte)(byte3.z * byte32.z);
        return byte33;
    }

    public static Byte3 sub(Byte3 byte3, byte by) {
        Byte3 byte32 = new Byte3();
        byte32.x = (byte)(byte3.x - by);
        byte32.y = (byte)(byte3.y - by);
        byte32.z = (byte)(byte3.z - by);
        return byte32;
    }

    public static Byte3 sub(Byte3 byte3, Byte3 byte32) {
        Byte3 byte33 = new Byte3();
        byte33.x = (byte)(byte3.x - byte32.x);
        byte33.y = (byte)(byte3.y - byte32.y);
        byte33.z = (byte)(byte3.z - byte32.z);
        return byte33;
    }

    public void add(byte by) {
        this.x = (byte)(this.x + by);
        this.y = (byte)(this.y + by);
        this.z = (byte)(this.z + by);
    }

    public void add(Byte3 byte3) {
        this.x = (byte)(this.x + byte3.x);
        this.y = (byte)(this.y + byte3.y);
        this.z = (byte)(this.z + byte3.z);
    }

    public void addAt(int n, byte by) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.z = (byte)(this.z + by);
                    return;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            this.y = (byte)(this.y + by);
            return;
        }
        this.x = (byte)(this.x + by);
    }

    public void addMultiple(Byte3 byte3, byte by) {
        this.x = (byte)(this.x + byte3.x * by);
        this.y = (byte)(this.y + byte3.y * by);
        this.z = (byte)(this.z + byte3.z * by);
    }

    public void copyTo(byte[] arrby, int n) {
        arrby[n] = this.x;
        arrby[n + 1] = this.y;
        arrby[n + 2] = this.z;
    }

    public void div(byte by) {
        this.x = (byte)(this.x / by);
        this.y = (byte)(this.y / by);
        this.z = (byte)(this.z / by);
    }

    public void div(Byte3 byte3) {
        this.x = (byte)(this.x / byte3.x);
        this.y = (byte)(this.y / byte3.y);
        this.z = (byte)(this.z / byte3.z);
    }

    public byte dotProduct(Byte3 byte3) {
        return (byte)((byte)((byte)(this.x * byte3.x) + (byte)(this.y * byte3.y)) + (byte)(this.z * byte3.z));
    }

    public byte elementSum() {
        return (byte)(this.x + this.y + this.z);
    }

    public byte get(int n) {
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

    public byte length() {
        return 3;
    }

    public void mul(byte by) {
        this.x = (byte)(this.x * by);
        this.y = (byte)(this.y * by);
        this.z = (byte)(this.z * by);
    }

    public void mul(Byte3 byte3) {
        this.x = (byte)(this.x * byte3.x);
        this.y = (byte)(this.y * byte3.y);
        this.z = (byte)(this.z * byte3.z);
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public void set(Byte3 byte3) {
        this.x = byte3.x;
        this.y = byte3.y;
        this.z = byte3.z;
    }

    public void setAt(int n, byte by) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.z = by;
                    return;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            this.y = by;
            return;
        }
        this.x = by;
    }

    public void setValues(byte by, byte by2, byte by3) {
        this.x = by;
        this.y = by2;
        this.z = by3;
    }

    public void sub(byte by) {
        this.x = (byte)(this.x - by);
        this.y = (byte)(this.y - by);
        this.z = (byte)(this.z - by);
    }

    public void sub(Byte3 byte3) {
        this.x = (byte)(this.x - byte3.x);
        this.y = (byte)(this.y - byte3.y);
        this.z = (byte)(this.z - byte3.z);
    }
}


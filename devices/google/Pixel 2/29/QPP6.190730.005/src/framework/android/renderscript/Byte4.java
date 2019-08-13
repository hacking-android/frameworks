/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Byte4 {
    public byte w;
    public byte x;
    public byte y;
    public byte z;

    public Byte4() {
    }

    public Byte4(byte by, byte by2, byte by3, byte by4) {
        this.x = by;
        this.y = by2;
        this.z = by3;
        this.w = by4;
    }

    public Byte4(Byte4 byte4) {
        this.x = byte4.x;
        this.y = byte4.y;
        this.z = byte4.z;
        this.w = byte4.w;
    }

    public static Byte4 add(Byte4 byte4, byte by) {
        Byte4 byte42 = new Byte4();
        byte42.x = (byte)(byte4.x + by);
        byte42.y = (byte)(byte4.y + by);
        byte42.z = (byte)(byte4.z + by);
        byte42.w = (byte)(byte4.w + by);
        return byte42;
    }

    public static Byte4 add(Byte4 byte4, Byte4 byte42) {
        Byte4 byte43 = new Byte4();
        byte43.x = (byte)(byte4.x + byte42.x);
        byte43.y = (byte)(byte4.y + byte42.y);
        byte43.z = (byte)(byte4.z + byte42.z);
        byte43.w = (byte)(byte4.w + byte42.w);
        return byte43;
    }

    public static Byte4 div(Byte4 byte4, byte by) {
        Byte4 byte42 = new Byte4();
        byte42.x = (byte)(byte4.x / by);
        byte42.y = (byte)(byte4.y / by);
        byte42.z = (byte)(byte4.z / by);
        byte42.w = (byte)(byte4.w / by);
        return byte42;
    }

    public static Byte4 div(Byte4 byte4, Byte4 byte42) {
        Byte4 byte43 = new Byte4();
        byte43.x = (byte)(byte4.x / byte42.x);
        byte43.y = (byte)(byte4.y / byte42.y);
        byte43.z = (byte)(byte4.z / byte42.z);
        byte43.w = (byte)(byte4.w / byte42.w);
        return byte43;
    }

    public static byte dotProduct(Byte4 byte4, Byte4 byte42) {
        return (byte)(byte42.x * byte4.x + byte42.y * byte4.y + byte42.z * byte4.z + byte42.w * byte4.w);
    }

    public static Byte4 mul(Byte4 byte4, byte by) {
        Byte4 byte42 = new Byte4();
        byte42.x = (byte)(byte4.x * by);
        byte42.y = (byte)(byte4.y * by);
        byte42.z = (byte)(byte4.z * by);
        byte42.w = (byte)(byte4.w * by);
        return byte42;
    }

    public static Byte4 mul(Byte4 byte4, Byte4 byte42) {
        Byte4 byte43 = new Byte4();
        byte43.x = (byte)(byte4.x * byte42.x);
        byte43.y = (byte)(byte4.y * byte42.y);
        byte43.z = (byte)(byte4.z * byte42.z);
        byte43.w = (byte)(byte4.w * byte42.w);
        return byte43;
    }

    public static Byte4 sub(Byte4 byte4, byte by) {
        Byte4 byte42 = new Byte4();
        byte42.x = (byte)(byte4.x - by);
        byte42.y = (byte)(byte4.y - by);
        byte42.z = (byte)(byte4.z - by);
        byte42.w = (byte)(byte4.w - by);
        return byte42;
    }

    public static Byte4 sub(Byte4 byte4, Byte4 byte42) {
        Byte4 byte43 = new Byte4();
        byte43.x = (byte)(byte4.x - byte42.x);
        byte43.y = (byte)(byte4.y - byte42.y);
        byte43.z = (byte)(byte4.z - byte42.z);
        byte43.w = (byte)(byte4.w - byte42.w);
        return byte43;
    }

    public void add(byte by) {
        this.x = (byte)(this.x + by);
        this.y = (byte)(this.y + by);
        this.z = (byte)(this.z + by);
        this.w = (byte)(this.w + by);
    }

    public void add(Byte4 byte4) {
        this.x = (byte)(this.x + byte4.x);
        this.y = (byte)(this.y + byte4.y);
        this.z = (byte)(this.z + byte4.z);
        this.w = (byte)(this.w + byte4.w);
    }

    public void addAt(int n, byte by) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.w = (byte)(this.w + by);
                        return;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                this.z = (byte)(this.z + by);
                return;
            }
            this.y = (byte)(this.y + by);
            return;
        }
        this.x = (byte)(this.x + by);
    }

    public void addMultiple(Byte4 byte4, byte by) {
        this.x = (byte)(this.x + byte4.x * by);
        this.y = (byte)(this.y + byte4.y * by);
        this.z = (byte)(this.z + byte4.z * by);
        this.w = (byte)(this.w + byte4.w * by);
    }

    public void copyTo(byte[] arrby, int n) {
        arrby[n] = this.x;
        arrby[n + 1] = this.y;
        arrby[n + 2] = this.z;
        arrby[n + 3] = this.w;
    }

    public void div(byte by) {
        this.x = (byte)(this.x / by);
        this.y = (byte)(this.y / by);
        this.z = (byte)(this.z / by);
        this.w = (byte)(this.w / by);
    }

    public void div(Byte4 byte4) {
        this.x = (byte)(this.x / byte4.x);
        this.y = (byte)(this.y / byte4.y);
        this.z = (byte)(this.z / byte4.z);
        this.w = (byte)(this.w / byte4.w);
    }

    public byte dotProduct(Byte4 byte4) {
        return (byte)(this.x * byte4.x + this.y * byte4.y + this.z * byte4.z + this.w * byte4.w);
    }

    public byte elementSum() {
        return (byte)(this.x + this.y + this.z + this.w);
    }

    public byte get(int n) {
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

    public byte length() {
        return 4;
    }

    public void mul(byte by) {
        this.x = (byte)(this.x * by);
        this.y = (byte)(this.y * by);
        this.z = (byte)(this.z * by);
        this.w = (byte)(this.w * by);
    }

    public void mul(Byte4 byte4) {
        this.x = (byte)(this.x * byte4.x);
        this.y = (byte)(this.y * byte4.y);
        this.z = (byte)(this.z * byte4.z);
        this.w = (byte)(this.w * byte4.w);
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public void set(Byte4 byte4) {
        this.x = byte4.x;
        this.y = byte4.y;
        this.z = byte4.z;
        this.w = byte4.w;
    }

    public void setAt(int n, byte by) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.w = by;
                        return;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                this.z = by;
                return;
            }
            this.y = by;
            return;
        }
        this.x = by;
    }

    public void setValues(byte by, byte by2, byte by3, byte by4) {
        this.x = by;
        this.y = by2;
        this.z = by3;
        this.w = by4;
    }

    public void sub(byte by) {
        this.x = (byte)(this.x - by);
        this.y = (byte)(this.y - by);
        this.z = (byte)(this.z - by);
        this.w = (byte)(this.w - by);
    }

    public void sub(Byte4 byte4) {
        this.x = (byte)(this.x - byte4.x);
        this.y = (byte)(this.y - byte4.y);
        this.z = (byte)(this.z - byte4.z);
        this.w = (byte)(this.w - byte4.w);
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Int4 {
    public int w;
    public int x;
    public int y;
    public int z;

    public Int4() {
    }

    public Int4(int n) {
        this.w = n;
        this.z = n;
        this.y = n;
        this.x = n;
    }

    public Int4(int n, int n2, int n3, int n4) {
        this.x = n;
        this.y = n2;
        this.z = n3;
        this.w = n4;
    }

    public Int4(Int4 int4) {
        this.x = int4.x;
        this.y = int4.y;
        this.z = int4.z;
        this.w = int4.w;
    }

    public static Int4 add(Int4 int4, int n) {
        Int4 int42 = new Int4();
        int42.x = int4.x + n;
        int42.y = int4.y + n;
        int42.z = int4.z + n;
        int42.w = int4.w + n;
        return int42;
    }

    public static Int4 add(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x + int42.x;
        int43.y = int4.y + int42.y;
        int43.z = int4.z + int42.z;
        int43.w = int4.w + int42.w;
        return int43;
    }

    public static Int4 div(Int4 int4, int n) {
        Int4 int42 = new Int4();
        int42.x = int4.x / n;
        int42.y = int4.y / n;
        int42.z = int4.z / n;
        int42.w = int4.w / n;
        return int42;
    }

    public static Int4 div(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x / int42.x;
        int43.y = int4.y / int42.y;
        int43.z = int4.z / int42.z;
        int43.w = int4.w / int42.w;
        return int43;
    }

    public static int dotProduct(Int4 int4, Int4 int42) {
        return int42.x * int4.x + int42.y * int4.y + int42.z * int4.z + int42.w * int4.w;
    }

    public static Int4 mod(Int4 int4, int n) {
        Int4 int42 = new Int4();
        int42.x = int4.x % n;
        int42.y = int4.y % n;
        int42.z = int4.z % n;
        int42.w = int4.w % n;
        return int42;
    }

    public static Int4 mod(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x % int42.x;
        int43.y = int4.y % int42.y;
        int43.z = int4.z % int42.z;
        int43.w = int4.w % int42.w;
        return int43;
    }

    public static Int4 mul(Int4 int4, int n) {
        Int4 int42 = new Int4();
        int42.x = int4.x * n;
        int42.y = int4.y * n;
        int42.z = int4.z * n;
        int42.w = int4.w * n;
        return int42;
    }

    public static Int4 mul(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x * int42.x;
        int43.y = int4.y * int42.y;
        int43.z = int4.z * int42.z;
        int43.w = int4.w * int42.w;
        return int43;
    }

    public static Int4 sub(Int4 int4, int n) {
        Int4 int42 = new Int4();
        int42.x = int4.x - n;
        int42.y = int4.y - n;
        int42.z = int4.z - n;
        int42.w = int4.w - n;
        return int42;
    }

    public static Int4 sub(Int4 int4, Int4 int42) {
        Int4 int43 = new Int4();
        int43.x = int4.x - int42.x;
        int43.y = int4.y - int42.y;
        int43.z = int4.z - int42.z;
        int43.w = int4.w - int42.w;
        return int43;
    }

    public void add(int n) {
        this.x += n;
        this.y += n;
        this.z += n;
        this.w += n;
    }

    public void add(Int4 int4) {
        this.x += int4.x;
        this.y += int4.y;
        this.z += int4.z;
        this.w += int4.w;
    }

    public void addAt(int n, int n2) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.w += n2;
                        return;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                this.z += n2;
                return;
            }
            this.y += n2;
            return;
        }
        this.x += n2;
    }

    public void addMultiple(Int4 int4, int n) {
        this.x += int4.x * n;
        this.y += int4.y * n;
        this.z += int4.z * n;
        this.w += int4.w * n;
    }

    public void copyTo(int[] arrn, int n) {
        arrn[n] = this.x;
        arrn[n + 1] = this.y;
        arrn[n + 2] = this.z;
        arrn[n + 3] = this.w;
    }

    public void div(int n) {
        this.x /= n;
        this.y /= n;
        this.z /= n;
        this.w /= n;
    }

    public void div(Int4 int4) {
        this.x /= int4.x;
        this.y /= int4.y;
        this.z /= int4.z;
        this.w /= int4.w;
    }

    public int dotProduct(Int4 int4) {
        return this.x * int4.x + this.y * int4.y + this.z * int4.z + this.w * int4.w;
    }

    public int elementSum() {
        return this.x + this.y + this.z + this.w;
    }

    public int get(int n) {
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

    public int length() {
        return 4;
    }

    public void mod(int n) {
        this.x %= n;
        this.y %= n;
        this.z %= n;
        this.w %= n;
    }

    public void mod(Int4 int4) {
        this.x %= int4.x;
        this.y %= int4.y;
        this.z %= int4.z;
        this.w %= int4.w;
    }

    public void mul(int n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
        this.w *= n;
    }

    public void mul(Int4 int4) {
        this.x *= int4.x;
        this.y *= int4.y;
        this.z *= int4.z;
        this.w *= int4.w;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public void set(Int4 int4) {
        this.x = int4.x;
        this.y = int4.y;
        this.z = int4.z;
        this.w = int4.w;
    }

    public void setAt(int n, int n2) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.w = n2;
                        return;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                this.z = n2;
                return;
            }
            this.y = n2;
            return;
        }
        this.x = n2;
    }

    public void setValues(int n, int n2, int n3, int n4) {
        this.x = n;
        this.y = n2;
        this.z = n3;
        this.w = n4;
    }

    public void sub(int n) {
        this.x -= n;
        this.y -= n;
        this.z -= n;
        this.w -= n;
    }

    public void sub(Int4 int4) {
        this.x -= int4.x;
        this.y -= int4.y;
        this.z -= int4.z;
        this.w -= int4.w;
    }
}


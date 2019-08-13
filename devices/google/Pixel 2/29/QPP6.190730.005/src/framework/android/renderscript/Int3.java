/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Int3 {
    public int x;
    public int y;
    public int z;

    public Int3() {
    }

    public Int3(int n) {
        this.z = n;
        this.y = n;
        this.x = n;
    }

    public Int3(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
    }

    public Int3(Int3 int3) {
        this.x = int3.x;
        this.y = int3.y;
        this.z = int3.z;
    }

    public static Int3 add(Int3 int3, int n) {
        Int3 int32 = new Int3();
        int32.x = int3.x + n;
        int32.y = int3.y + n;
        int32.z = int3.z + n;
        return int32;
    }

    public static Int3 add(Int3 int3, Int3 int32) {
        Int3 int33 = new Int3();
        int33.x = int3.x + int32.x;
        int33.y = int3.y + int32.y;
        int33.z = int3.z + int32.z;
        return int33;
    }

    public static Int3 div(Int3 int3, int n) {
        Int3 int32 = new Int3();
        int32.x = int3.x / n;
        int32.y = int3.y / n;
        int32.z = int3.z / n;
        return int32;
    }

    public static Int3 div(Int3 int3, Int3 int32) {
        Int3 int33 = new Int3();
        int33.x = int3.x / int32.x;
        int33.y = int3.y / int32.y;
        int33.z = int3.z / int32.z;
        return int33;
    }

    public static int dotProduct(Int3 int3, Int3 int32) {
        return int32.x * int3.x + int32.y * int3.y + int32.z * int3.z;
    }

    public static Int3 mod(Int3 int3, int n) {
        Int3 int32 = new Int3();
        int32.x = int3.x % n;
        int32.y = int3.y % n;
        int32.z = int3.z % n;
        return int32;
    }

    public static Int3 mod(Int3 int3, Int3 int32) {
        Int3 int33 = new Int3();
        int33.x = int3.x % int32.x;
        int33.y = int3.y % int32.y;
        int33.z = int3.z % int32.z;
        return int33;
    }

    public static Int3 mul(Int3 int3, int n) {
        Int3 int32 = new Int3();
        int32.x = int3.x * n;
        int32.y = int3.y * n;
        int32.z = int3.z * n;
        return int32;
    }

    public static Int3 mul(Int3 int3, Int3 int32) {
        Int3 int33 = new Int3();
        int33.x = int3.x * int32.x;
        int33.y = int3.y * int32.y;
        int33.z = int3.z * int32.z;
        return int33;
    }

    public static Int3 sub(Int3 int3, int n) {
        Int3 int32 = new Int3();
        int32.x = int3.x - n;
        int32.y = int3.y - n;
        int32.z = int3.z - n;
        return int32;
    }

    public static Int3 sub(Int3 int3, Int3 int32) {
        Int3 int33 = new Int3();
        int33.x = int3.x - int32.x;
        int33.y = int3.y - int32.y;
        int33.z = int3.z - int32.z;
        return int33;
    }

    public void add(int n) {
        this.x += n;
        this.y += n;
        this.z += n;
    }

    public void add(Int3 int3) {
        this.x += int3.x;
        this.y += int3.y;
        this.z += int3.z;
    }

    public void addAt(int n, int n2) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.z += n2;
                    return;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            this.y += n2;
            return;
        }
        this.x += n2;
    }

    public void addMultiple(Int3 int3, int n) {
        this.x += int3.x * n;
        this.y += int3.y * n;
        this.z += int3.z * n;
    }

    public void copyTo(int[] arrn, int n) {
        arrn[n] = this.x;
        arrn[n + 1] = this.y;
        arrn[n + 2] = this.z;
    }

    public void div(int n) {
        this.x /= n;
        this.y /= n;
        this.z /= n;
    }

    public void div(Int3 int3) {
        this.x /= int3.x;
        this.y /= int3.y;
        this.z /= int3.z;
    }

    public int dotProduct(Int3 int3) {
        return this.x * int3.x + this.y * int3.y + this.z * int3.z;
    }

    public int elementSum() {
        return this.x + this.y + this.z;
    }

    public int get(int n) {
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

    public int length() {
        return 3;
    }

    public void mod(int n) {
        this.x %= n;
        this.y %= n;
        this.z %= n;
    }

    public void mod(Int3 int3) {
        this.x %= int3.x;
        this.y %= int3.y;
        this.z %= int3.z;
    }

    public void mul(int n) {
        this.x *= n;
        this.y *= n;
        this.z *= n;
    }

    public void mul(Int3 int3) {
        this.x *= int3.x;
        this.y *= int3.y;
        this.z *= int3.z;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public void set(Int3 int3) {
        this.x = int3.x;
        this.y = int3.y;
        this.z = int3.z;
    }

    public void setAt(int n, int n2) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.z = n2;
                    return;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            this.y = n2;
            return;
        }
        this.x = n2;
    }

    public void setValues(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
    }

    public void sub(int n) {
        this.x -= n;
        this.y -= n;
        this.z -= n;
    }

    public void sub(Int3 int3) {
        this.x -= int3.x;
        this.y -= int3.y;
        this.z -= int3.z;
    }
}


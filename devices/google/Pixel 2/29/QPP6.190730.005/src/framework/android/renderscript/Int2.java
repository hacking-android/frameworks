/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Int2 {
    public int x;
    public int y;

    public Int2() {
    }

    public Int2(int n) {
        this.y = n;
        this.x = n;
    }

    public Int2(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public Int2(Int2 int2) {
        this.x = int2.x;
        this.y = int2.y;
    }

    public static Int2 add(Int2 int2, int n) {
        Int2 int22 = new Int2();
        int22.x = int2.x + n;
        int22.y = int2.y + n;
        return int22;
    }

    public static Int2 add(Int2 int2, Int2 int22) {
        Int2 int23 = new Int2();
        int23.x = int2.x + int22.x;
        int23.y = int2.y + int22.y;
        return int23;
    }

    public static Int2 div(Int2 int2, int n) {
        Int2 int22 = new Int2();
        int22.x = int2.x / n;
        int22.y = int2.y / n;
        return int22;
    }

    public static Int2 div(Int2 int2, Int2 int22) {
        Int2 int23 = new Int2();
        int23.x = int2.x / int22.x;
        int23.y = int2.y / int22.y;
        return int23;
    }

    public static int dotProduct(Int2 int2, Int2 int22) {
        return int22.x * int2.x + int22.y * int2.y;
    }

    public static Int2 mod(Int2 int2, int n) {
        Int2 int22 = new Int2();
        int22.x = int2.x % n;
        int22.y = int2.y % n;
        return int22;
    }

    public static Int2 mod(Int2 int2, Int2 int22) {
        Int2 int23 = new Int2();
        int23.x = int2.x % int22.x;
        int23.y = int2.y % int22.y;
        return int23;
    }

    public static Int2 mul(Int2 int2, int n) {
        Int2 int22 = new Int2();
        int22.x = int2.x * n;
        int22.y = int2.y * n;
        return int22;
    }

    public static Int2 mul(Int2 int2, Int2 int22) {
        Int2 int23 = new Int2();
        int23.x = int2.x * int22.x;
        int23.y = int2.y * int22.y;
        return int23;
    }

    public static Int2 sub(Int2 int2, int n) {
        Int2 int22 = new Int2();
        int22.x = int2.x - n;
        int22.y = int2.y - n;
        return int22;
    }

    public static Int2 sub(Int2 int2, Int2 int22) {
        Int2 int23 = new Int2();
        int23.x = int2.x - int22.x;
        int23.y = int2.y - int22.y;
        return int23;
    }

    public void add(int n) {
        this.x += n;
        this.y += n;
    }

    public void add(Int2 int2) {
        this.x += int2.x;
        this.y += int2.y;
    }

    public void addAt(int n, int n2) {
        if (n != 0) {
            if (n == 1) {
                this.y += n2;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x += n2;
    }

    public void addMultiple(Int2 int2, int n) {
        this.x += int2.x * n;
        this.y += int2.y * n;
    }

    public void copyTo(int[] arrn, int n) {
        arrn[n] = this.x;
        arrn[n + 1] = this.y;
    }

    public void div(int n) {
        this.x /= n;
        this.y /= n;
    }

    public void div(Int2 int2) {
        this.x /= int2.x;
        this.y /= int2.y;
    }

    public int dotProduct(Int2 int2) {
        return this.x * int2.x + this.y * int2.y;
    }

    public int elementSum() {
        return this.x + this.y;
    }

    public int get(int n) {
        if (n != 0) {
            if (n == 1) {
                return this.y;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        return this.x;
    }

    public int length() {
        return 2;
    }

    public void mod(int n) {
        this.x %= n;
        this.y %= n;
    }

    public void mod(Int2 int2) {
        this.x %= int2.x;
        this.y %= int2.y;
    }

    public void mul(int n) {
        this.x *= n;
        this.y *= n;
    }

    public void mul(Int2 int2) {
        this.x *= int2.x;
        this.y *= int2.y;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public void set(Int2 int2) {
        this.x = int2.x;
        this.y = int2.y;
    }

    public void setAt(int n, int n2) {
        if (n != 0) {
            if (n == 1) {
                this.y = n2;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x = n2;
    }

    public void setValues(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public void sub(int n) {
        this.x -= n;
        this.y -= n;
    }

    public void sub(Int2 int2) {
        this.x -= int2.x;
        this.y -= int2.y;
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Double4 {
    public double w;
    public double x;
    public double y;
    public double z;

    public Double4() {
    }

    public Double4(double d, double d2, double d3, double d4) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.w = d4;
    }

    public Double4(Double4 double4) {
        this.x = double4.x;
        this.y = double4.y;
        this.z = double4.z;
        this.w = double4.w;
    }

    public static Double4 add(Double4 double4, double d) {
        Double4 double42 = new Double4();
        double42.x = double4.x + d;
        double42.y = double4.y + d;
        double42.z = double4.z + d;
        double42.w = double4.w + d;
        return double42;
    }

    public static Double4 add(Double4 double4, Double4 double42) {
        Double4 double43 = new Double4();
        double43.x = double4.x + double42.x;
        double43.y = double4.y + double42.y;
        double43.z = double4.z + double42.z;
        double43.w = double4.w + double42.w;
        return double43;
    }

    public static Double4 div(Double4 double4, double d) {
        Double4 double42 = new Double4();
        double42.x = double4.x / d;
        double42.y = double4.y / d;
        double42.z = double4.z / d;
        double42.w = double4.w / d;
        return double42;
    }

    public static Double4 div(Double4 double4, Double4 double42) {
        Double4 double43 = new Double4();
        double43.x = double4.x / double42.x;
        double43.y = double4.y / double42.y;
        double43.z = double4.z / double42.z;
        double43.w = double4.w / double42.w;
        return double43;
    }

    public static double dotProduct(Double4 double4, Double4 double42) {
        return double42.x * double4.x + double42.y * double4.y + double42.z * double4.z + double42.w * double4.w;
    }

    public static Double4 mul(Double4 double4, double d) {
        Double4 double42 = new Double4();
        double42.x = double4.x * d;
        double42.y = double4.y * d;
        double42.z = double4.z * d;
        double42.w = double4.w * d;
        return double42;
    }

    public static Double4 mul(Double4 double4, Double4 double42) {
        Double4 double43 = new Double4();
        double43.x = double4.x * double42.x;
        double43.y = double4.y * double42.y;
        double43.z = double4.z * double42.z;
        double43.w = double4.w * double42.w;
        return double43;
    }

    public static Double4 sub(Double4 double4, double d) {
        Double4 double42 = new Double4();
        double42.x = double4.x - d;
        double42.y = double4.y - d;
        double42.z = double4.z - d;
        double42.w = double4.w - d;
        return double42;
    }

    public static Double4 sub(Double4 double4, Double4 double42) {
        Double4 double43 = new Double4();
        double43.x = double4.x - double42.x;
        double43.y = double4.y - double42.y;
        double43.z = double4.z - double42.z;
        double43.w = double4.w - double42.w;
        return double43;
    }

    public void add(double d) {
        this.x += d;
        this.y += d;
        this.z += d;
        this.w += d;
    }

    public void add(Double4 double4) {
        this.x += double4.x;
        this.y += double4.y;
        this.z += double4.z;
        this.w += double4.w;
    }

    public void addAt(int n, double d) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.w += d;
                        return;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                this.z += d;
                return;
            }
            this.y += d;
            return;
        }
        this.x += d;
    }

    public void addMultiple(Double4 double4, double d) {
        this.x += double4.x * d;
        this.y += double4.y * d;
        this.z += double4.z * d;
        this.w += double4.w * d;
    }

    public void copyTo(double[] arrd, int n) {
        arrd[n] = this.x;
        arrd[n + 1] = this.y;
        arrd[n + 2] = this.z;
        arrd[n + 3] = this.w;
    }

    public void div(double d) {
        this.x /= d;
        this.y /= d;
        this.z /= d;
        this.w /= d;
    }

    public void div(Double4 double4) {
        this.x /= double4.x;
        this.y /= double4.y;
        this.z /= double4.z;
        this.w /= double4.w;
    }

    public double dotProduct(Double4 double4) {
        return this.x * double4.x + this.y * double4.y + this.z * double4.z + this.w * double4.w;
    }

    public double elementSum() {
        return this.x + this.y + this.z + this.w;
    }

    public double get(int n) {
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

    public void mul(double d) {
        this.x *= d;
        this.y *= d;
        this.z *= d;
        this.w *= d;
    }

    public void mul(Double4 double4) {
        this.x *= double4.x;
        this.y *= double4.y;
        this.z *= double4.z;
        this.w *= double4.w;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public void set(Double4 double4) {
        this.x = double4.x;
        this.y = double4.y;
        this.z = double4.z;
        this.w = double4.w;
    }

    public void setAt(int n, double d) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.w = d;
                        return;
                    }
                    throw new IndexOutOfBoundsException("Index: i");
                }
                this.z = d;
                return;
            }
            this.y = d;
            return;
        }
        this.x = d;
    }

    public void setValues(double d, double d2, double d3, double d4) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.w = d4;
    }

    public void sub(double d) {
        this.x -= d;
        this.y -= d;
        this.z -= d;
        this.w -= d;
    }

    public void sub(Double4 double4) {
        this.x -= double4.x;
        this.y -= double4.y;
        this.z -= double4.z;
        this.w -= double4.w;
    }
}


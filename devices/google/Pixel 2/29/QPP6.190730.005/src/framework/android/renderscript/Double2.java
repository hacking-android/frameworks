/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Double2 {
    public double x;
    public double y;

    public Double2() {
    }

    public Double2(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public Double2(Double2 double2) {
        this.x = double2.x;
        this.y = double2.y;
    }

    public static Double2 add(Double2 double2, double d) {
        Double2 double22 = new Double2();
        double22.x = double2.x + d;
        double22.y = double2.y + d;
        return double22;
    }

    public static Double2 add(Double2 double2, Double2 double22) {
        Double2 double23 = new Double2();
        double23.x = double2.x + double22.x;
        double23.y = double2.y + double22.y;
        return double23;
    }

    public static Double2 div(Double2 double2, double d) {
        Double2 double22 = new Double2();
        double22.x = double2.x / d;
        double22.y = double2.y / d;
        return double22;
    }

    public static Double2 div(Double2 double2, Double2 double22) {
        Double2 double23 = new Double2();
        double23.x = double2.x / double22.x;
        double23.y = double2.y / double22.y;
        return double23;
    }

    public static Double dotProduct(Double2 double2, Double2 double22) {
        return double22.x * double2.x + double22.y * double2.y;
    }

    public static Double2 mul(Double2 double2, double d) {
        Double2 double22 = new Double2();
        double22.x = double2.x * d;
        double22.y = double2.y * d;
        return double22;
    }

    public static Double2 mul(Double2 double2, Double2 double22) {
        Double2 double23 = new Double2();
        double23.x = double2.x * double22.x;
        double23.y = double2.y * double22.y;
        return double23;
    }

    public static Double2 sub(Double2 double2, double d) {
        Double2 double22 = new Double2();
        double22.x = double2.x - d;
        double22.y = double2.y - d;
        return double22;
    }

    public static Double2 sub(Double2 double2, Double2 double22) {
        Double2 double23 = new Double2();
        double23.x = double2.x - double22.x;
        double23.y = double2.y - double22.y;
        return double23;
    }

    public void add(double d) {
        this.x += d;
        this.y += d;
    }

    public void add(Double2 double2) {
        this.x += double2.x;
        this.y += double2.y;
    }

    public void addAt(int n, double d) {
        if (n != 0) {
            if (n == 1) {
                this.y += d;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x += d;
    }

    public void addMultiple(Double2 double2, double d) {
        this.x += double2.x * d;
        this.y += double2.y * d;
    }

    public void copyTo(double[] arrd, int n) {
        arrd[n] = this.x;
        arrd[n + 1] = this.y;
    }

    public void div(double d) {
        this.x /= d;
        this.y /= d;
    }

    public void div(Double2 double2) {
        this.x /= double2.x;
        this.y /= double2.y;
    }

    public double dotProduct(Double2 double2) {
        return this.x * double2.x + this.y * double2.y;
    }

    public double elementSum() {
        return this.x + this.y;
    }

    public double get(int n) {
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

    public void mul(double d) {
        this.x *= d;
        this.y *= d;
    }

    public void mul(Double2 double2) {
        this.x *= double2.x;
        this.y *= double2.y;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public void set(Double2 double2) {
        this.x = double2.x;
        this.y = double2.y;
    }

    public void setAt(int n, double d) {
        if (n != 0) {
            if (n == 1) {
                this.y = d;
                return;
            }
            throw new IndexOutOfBoundsException("Index: i");
        }
        this.x = d;
    }

    public void setValues(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public void sub(double d) {
        this.x -= d;
        this.y -= d;
    }

    public void sub(Double2 double2) {
        this.x -= double2.x;
        this.y -= double2.y;
    }
}


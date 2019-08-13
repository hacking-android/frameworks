/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

public class Float3 {
    public float x;
    public float y;
    public float z;

    public Float3() {
    }

    public Float3(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public Float3(Float3 float3) {
        this.x = float3.x;
        this.y = float3.y;
        this.z = float3.z;
    }

    public static Float3 add(Float3 float3, float f) {
        Float3 float32 = new Float3();
        float32.x = float3.x + f;
        float32.y = float3.y + f;
        float32.z = float3.z + f;
        return float32;
    }

    public static Float3 add(Float3 float3, Float3 float32) {
        Float3 float33 = new Float3();
        float33.x = float3.x + float32.x;
        float33.y = float3.y + float32.y;
        float33.z = float3.z + float32.z;
        return float33;
    }

    public static Float3 div(Float3 float3, float f) {
        Float3 float32 = new Float3();
        float32.x = float3.x / f;
        float32.y = float3.y / f;
        float32.z = float3.z / f;
        return float32;
    }

    public static Float3 div(Float3 float3, Float3 float32) {
        Float3 float33 = new Float3();
        float33.x = float3.x / float32.x;
        float33.y = float3.y / float32.y;
        float33.z = float3.z / float32.z;
        return float33;
    }

    public static Float dotProduct(Float3 float3, Float3 float32) {
        return new Float(float32.x * float3.x + float32.y * float3.y + float32.z * float3.z);
    }

    public static Float3 mul(Float3 float3, float f) {
        Float3 float32 = new Float3();
        float32.x = float3.x * f;
        float32.y = float3.y * f;
        float32.z = float3.z * f;
        return float32;
    }

    public static Float3 mul(Float3 float3, Float3 float32) {
        Float3 float33 = new Float3();
        float33.x = float3.x * float32.x;
        float33.y = float3.y * float32.y;
        float33.z = float3.z * float32.z;
        return float33;
    }

    public static Float3 sub(Float3 float3, float f) {
        Float3 float32 = new Float3();
        float32.x = float3.x - f;
        float32.y = float3.y - f;
        float32.z = float3.z - f;
        return float32;
    }

    public static Float3 sub(Float3 float3, Float3 float32) {
        Float3 float33 = new Float3();
        float33.x = float3.x - float32.x;
        float33.y = float3.y - float32.y;
        float33.z = float3.z - float32.z;
        return float33;
    }

    public void add(float f) {
        this.x += f;
        this.y += f;
        this.z += f;
    }

    public void add(Float3 float3) {
        this.x += float3.x;
        this.y += float3.y;
        this.z += float3.z;
    }

    public void addAt(int n, float f) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.z += f;
                    return;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            this.y += f;
            return;
        }
        this.x += f;
    }

    public void addMultiple(Float3 float3, float f) {
        this.x += float3.x * f;
        this.y += float3.y * f;
        this.z += float3.z * f;
    }

    public void copyTo(float[] arrf, int n) {
        arrf[n] = this.x;
        arrf[n + 1] = this.y;
        arrf[n + 2] = this.z;
    }

    public void div(float f) {
        this.x /= f;
        this.y /= f;
        this.z /= f;
    }

    public void div(Float3 float3) {
        this.x /= float3.x;
        this.y /= float3.y;
        this.z /= float3.z;
    }

    public Float dotProduct(Float3 float3) {
        return new Float(this.x * float3.x + this.y * float3.y + this.z * float3.z);
    }

    public Float elementSum() {
        return new Float(this.x + this.y + this.z);
    }

    public float get(int n) {
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

    public void mul(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }

    public void mul(Float3 float3) {
        this.x *= float3.x;
        this.y *= float3.y;
        this.z *= float3.z;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public void set(Float3 float3) {
        this.x = float3.x;
        this.y = float3.y;
        this.z = float3.z;
    }

    public void setAt(int n, float f) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    this.z = f;
                    return;
                }
                throw new IndexOutOfBoundsException("Index: i");
            }
            this.y = f;
            return;
        }
        this.x = f;
    }

    public void setValues(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public void sub(float f) {
        this.x -= f;
        this.y -= f;
        this.z -= f;
    }

    public void sub(Float3 float3) {
        this.x -= float3.x;
        this.y -= float3.y;
        this.z -= float3.z;
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.FastMath;
import java.io.PrintWriter;

public class RectF
implements Parcelable {
    public static final Parcelable.Creator<RectF> CREATOR = new Parcelable.Creator<RectF>(){

        @Override
        public RectF createFromParcel(Parcel parcel) {
            RectF rectF = new RectF();
            rectF.readFromParcel(parcel);
            return rectF;
        }

        public RectF[] newArray(int n) {
            return new RectF[n];
        }
    };
    public float bottom;
    public float left;
    public float right;
    public float top;

    public RectF() {
    }

    public RectF(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public RectF(Rect rect) {
        if (rect == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
        } else {
            this.left = rect.left;
            this.top = rect.top;
            this.right = rect.right;
            this.bottom = rect.bottom;
        }
    }

    public RectF(RectF rectF) {
        if (rectF == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
        } else {
            this.left = rectF.left;
            this.top = rectF.top;
            this.right = rectF.right;
            this.bottom = rectF.bottom;
        }
    }

    public static boolean intersects(RectF rectF, RectF rectF2) {
        boolean bl = rectF.left < rectF2.right && rectF2.left < rectF.right && rectF.top < rectF2.bottom && rectF2.top < rectF.bottom;
        return bl;
    }

    public final float centerX() {
        return (this.left + this.right) * 0.5f;
    }

    public final float centerY() {
        return (this.top + this.bottom) * 0.5f;
    }

    public boolean contains(float f, float f2) {
        float f3;
        float f4;
        float f5 = this.left;
        float f6 = this.right;
        boolean bl = f5 < f6 && (f3 = this.top) < (f4 = this.bottom) && f >= f5 && f < f6 && f2 >= f3 && f2 < f4;
        return bl;
    }

    public boolean contains(float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        float f7 = this.left;
        float f8 = this.right;
        boolean bl = f7 < f8 && (f5 = this.top) < (f6 = this.bottom) && f7 <= f && f5 <= f2 && f8 >= f3 && f6 >= f4;
        return bl;
    }

    public boolean contains(RectF rectF) {
        float f;
        float f2;
        float f3 = this.left;
        float f4 = this.right;
        boolean bl = f3 < f4 && (f2 = this.top) < (f = this.bottom) && f3 <= rectF.left && f2 <= rectF.top && f4 >= rectF.right && f >= rectF.bottom;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (RectF)object;
            if (this.left != ((RectF)object).left || this.top != ((RectF)object).top || this.right != ((RectF)object).right || this.bottom != ((RectF)object).bottom) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        float f = this.left;
        int n = 0;
        int n2 = f != 0.0f ? Float.floatToIntBits(f) : 0;
        f = this.top;
        int n3 = f != 0.0f ? Float.floatToIntBits(f) : 0;
        f = this.right;
        int n4 = f != 0.0f ? Float.floatToIntBits(f) : 0;
        f = this.bottom;
        if (f != 0.0f) {
            n = Float.floatToIntBits(f);
        }
        return ((n2 * 31 + n3) * 31 + n4) * 31 + n;
    }

    public final float height() {
        return this.bottom - this.top;
    }

    public void inset(float f, float f2) {
        this.left += f;
        this.top += f2;
        this.right -= f;
        this.bottom -= f2;
    }

    public boolean intersect(float f, float f2, float f3, float f4) {
        float f5 = this.left;
        if (f5 < f3 && f < this.right && this.top < f4 && f2 < this.bottom) {
            if (f5 < f) {
                this.left = f;
            }
            if (this.top < f2) {
                this.top = f2;
            }
            if (this.right > f3) {
                this.right = f3;
            }
            if (this.bottom > f4) {
                this.bottom = f4;
            }
            return true;
        }
        return false;
    }

    public boolean intersect(RectF rectF) {
        return this.intersect(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public boolean intersects(float f, float f2, float f3, float f4) {
        boolean bl = this.left < f3 && f < this.right && this.top < f4 && f2 < this.bottom;
        return bl;
    }

    public final boolean isEmpty() {
        boolean bl = this.left >= this.right || this.top >= this.bottom;
        return bl;
    }

    public void offset(float f, float f2) {
        this.left += f;
        this.top += f2;
        this.right += f;
        this.bottom += f2;
    }

    public void offsetTo(float f, float f2) {
        this.right += f - this.left;
        this.bottom += f2 - this.top;
        this.left = f;
        this.top = f2;
    }

    public void printShortString(PrintWriter printWriter) {
        printWriter.print('[');
        printWriter.print(this.left);
        printWriter.print(',');
        printWriter.print(this.top);
        printWriter.print("][");
        printWriter.print(this.right);
        printWriter.print(',');
        printWriter.print(this.bottom);
        printWriter.print(']');
    }

    public void readFromParcel(Parcel parcel) {
        this.left = parcel.readFloat();
        this.top = parcel.readFloat();
        this.right = parcel.readFloat();
        this.bottom = parcel.readFloat();
    }

    public void round(Rect rect) {
        rect.set(FastMath.round(this.left), FastMath.round(this.top), FastMath.round(this.right), FastMath.round(this.bottom));
    }

    public void roundOut(Rect rect) {
        rect.set((int)Math.floor(this.left), (int)Math.floor(this.top), (int)Math.ceil(this.right), (int)Math.ceil(this.bottom));
    }

    public void scale(float f) {
        if (f != 1.0f) {
            this.left *= f;
            this.top *= f;
            this.right *= f;
            this.bottom *= f;
        }
    }

    public void set(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public void set(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public void set(RectF rectF) {
        this.left = rectF.left;
        this.top = rectF.top;
        this.right = rectF.right;
        this.bottom = rectF.bottom;
    }

    public void setEmpty() {
        this.bottom = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.left = 0.0f;
    }

    public boolean setIntersect(RectF rectF, RectF rectF2) {
        float f;
        float f2 = rectF.left;
        if (f2 < rectF2.right && (f = rectF2.left) < rectF.right && rectF.top < rectF2.bottom && rectF2.top < rectF.bottom) {
            this.left = Math.max(f2, f);
            this.top = Math.max(rectF.top, rectF2.top);
            this.right = Math.min(rectF.right, rectF2.right);
            this.bottom = Math.min(rectF.bottom, rectF2.bottom);
            return true;
        }
        return false;
    }

    public void sort() {
        float f = this.left;
        float f2 = this.right;
        if (f > f2) {
            f = this.left;
            this.left = f2;
            this.right = f;
        }
        if ((f = this.top) > (f2 = this.bottom)) {
            f = this.top;
            this.top = f2;
            this.bottom = f;
        }
    }

    public String toShortString() {
        return this.toShortString(new StringBuilder(32));
    }

    public String toShortString(StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        stringBuilder.append('[');
        stringBuilder.append(this.left);
        stringBuilder.append(',');
        stringBuilder.append(this.top);
        stringBuilder.append("][");
        stringBuilder.append(this.right);
        stringBuilder.append(',');
        stringBuilder.append(this.bottom);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RectF(");
        stringBuilder.append(this.left);
        stringBuilder.append(", ");
        stringBuilder.append(this.top);
        stringBuilder.append(", ");
        stringBuilder.append(this.right);
        stringBuilder.append(", ");
        stringBuilder.append(this.bottom);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void union(float f, float f2) {
        if (f < this.left) {
            this.left = f;
        } else if (f > this.right) {
            this.right = f;
        }
        if (f2 < this.top) {
            this.top = f2;
        } else if (f2 > this.bottom) {
            this.bottom = f2;
        }
    }

    public void union(float f, float f2, float f3, float f4) {
        if (f < f3 && f2 < f4) {
            float f5 = this.left;
            if (f5 < this.right && this.top < this.bottom) {
                if (f5 > f) {
                    this.left = f;
                }
                if (this.top > f2) {
                    this.top = f2;
                }
                if (this.right < f3) {
                    this.right = f3;
                }
                if (this.bottom < f4) {
                    this.bottom = f4;
                }
            } else {
                this.left = f;
                this.top = f2;
                this.right = f3;
                this.bottom = f4;
            }
        }
    }

    public void union(RectF rectF) {
        this.union(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public final float width() {
        return this.right - this.left;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeFloat(this.left);
        parcel.writeFloat(this.top);
        parcel.writeFloat(this.right);
        parcel.writeFloat(this.bottom);
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

public final class Insets
implements Parcelable {
    public static final Parcelable.Creator<Insets> CREATOR;
    public static final Insets NONE;
    public final int bottom;
    public final int left;
    public final int right;
    public final int top;

    static {
        NONE = new Insets(0, 0, 0, 0);
        CREATOR = new Parcelable.Creator<Insets>(){

            @Override
            public Insets createFromParcel(Parcel parcel) {
                return new Insets(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            }

            public Insets[] newArray(int n) {
                return new Insets[n];
            }
        };
    }

    private Insets(int n, int n2, int n3, int n4) {
        this.left = n;
        this.top = n2;
        this.right = n3;
        this.bottom = n4;
    }

    public static Insets add(Insets insets, Insets insets2) {
        return Insets.of(insets.left + insets2.left, insets.top + insets2.top, insets.right + insets2.right, insets.bottom + insets2.bottom);
    }

    public static Insets max(Insets insets, Insets insets2) {
        return Insets.of(Math.max(insets.left, insets2.left), Math.max(insets.top, insets2.top), Math.max(insets.right, insets2.right), Math.max(insets.bottom, insets2.bottom));
    }

    public static Insets min(Insets insets, Insets insets2) {
        return Insets.of(Math.min(insets.left, insets2.left), Math.min(insets.top, insets2.top), Math.min(insets.right, insets2.right), Math.min(insets.bottom, insets2.bottom));
    }

    public static Insets of(int n, int n2, int n3, int n4) {
        if (n == 0 && n2 == 0 && n3 == 0 && n4 == 0) {
            return NONE;
        }
        return new Insets(n, n2, n3, n4);
    }

    public static Insets of(Rect parcelable) {
        parcelable = parcelable == null ? NONE : Insets.of(parcelable.left, parcelable.top, parcelable.right, parcelable.bottom);
        return parcelable;
    }

    public static Insets subtract(Insets insets, Insets insets2) {
        return Insets.of(insets.left - insets2.left, insets.top - insets2.top, insets.right - insets2.right, insets.bottom - insets2.bottom);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (Insets)object;
            if (this.bottom != ((Insets)object).bottom) {
                return false;
            }
            if (this.left != ((Insets)object).left) {
                return false;
            }
            if (this.right != ((Insets)object).right) {
                return false;
            }
            return this.top == ((Insets)object).top;
        }
        return false;
    }

    public int hashCode() {
        return ((this.left * 31 + this.top) * 31 + this.right) * 31 + this.bottom;
    }

    public Rect toRect() {
        return new Rect(this.left, this.top, this.right, this.bottom);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Insets{left=");
        stringBuilder.append(this.left);
        stringBuilder.append(", top=");
        stringBuilder.append(this.top);
        stringBuilder.append(", right=");
        stringBuilder.append(this.right);
        stringBuilder.append(", bottom=");
        stringBuilder.append(this.bottom);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.left);
        parcel.writeInt(this.top);
        parcel.writeInt(this.right);
        parcel.writeInt(this.bottom);
    }

}


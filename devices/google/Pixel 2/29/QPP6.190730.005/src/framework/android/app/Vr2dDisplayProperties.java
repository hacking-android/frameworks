/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class Vr2dDisplayProperties
implements Parcelable {
    public static final Parcelable.Creator<Vr2dDisplayProperties> CREATOR = new Parcelable.Creator<Vr2dDisplayProperties>(){

        @Override
        public Vr2dDisplayProperties createFromParcel(Parcel parcel) {
            return new Vr2dDisplayProperties(parcel);
        }

        public Vr2dDisplayProperties[] newArray(int n) {
            return new Vr2dDisplayProperties[n];
        }
    };
    public static final int FLAG_VIRTUAL_DISPLAY_ENABLED = 1;
    private final int mAddedFlags;
    private final int mDpi;
    private final int mHeight;
    private final int mRemovedFlags;
    private final int mWidth;

    public Vr2dDisplayProperties(int n, int n2, int n3) {
        this(n, n2, n3, 0, 0);
    }

    private Vr2dDisplayProperties(int n, int n2, int n3, int n4, int n5) {
        this.mWidth = n;
        this.mHeight = n2;
        this.mDpi = n3;
        this.mAddedFlags = n4;
        this.mRemovedFlags = n5;
    }

    private Vr2dDisplayProperties(Parcel parcel) {
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mDpi = parcel.readInt();
        this.mAddedFlags = parcel.readInt();
        this.mRemovedFlags = parcel.readInt();
    }

    private static String toReadableFlags(int n) {
        CharSequence charSequence = "{";
        if ((n & 1) == 1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("{");
            ((StringBuilder)charSequence).append("enabled");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(this.toString());
        printWriter.println(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (Vr2dDisplayProperties)object;
            if (this.getAddedFlags() != ((Vr2dDisplayProperties)object).getAddedFlags()) {
                return false;
            }
            if (this.getRemovedFlags() != ((Vr2dDisplayProperties)object).getRemovedFlags()) {
                return false;
            }
            if (this.getWidth() != ((Vr2dDisplayProperties)object).getWidth()) {
                return false;
            }
            if (this.getHeight() != ((Vr2dDisplayProperties)object).getHeight()) {
                return false;
            }
            if (this.getDpi() != ((Vr2dDisplayProperties)object).getDpi()) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getAddedFlags() {
        return this.mAddedFlags;
    }

    public int getDpi() {
        return this.mDpi;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getRemovedFlags() {
        return this.mRemovedFlags;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int hashCode() {
        return (this.getWidth() * 31 + this.getHeight()) * 31 + this.getDpi();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Vr2dDisplayProperties{mWidth=");
        stringBuilder.append(this.mWidth);
        stringBuilder.append(", mHeight=");
        stringBuilder.append(this.mHeight);
        stringBuilder.append(", mDpi=");
        stringBuilder.append(this.mDpi);
        stringBuilder.append(", flags=");
        stringBuilder.append(Vr2dDisplayProperties.toReadableFlags(this.mAddedFlags));
        stringBuilder.append(", removed_flags=");
        stringBuilder.append(Vr2dDisplayProperties.toReadableFlags(this.mRemovedFlags));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mDpi);
        parcel.writeInt(this.mAddedFlags);
        parcel.writeInt(this.mRemovedFlags);
    }

    public static final class Builder {
        private int mAddedFlags = 0;
        private int mDpi = -1;
        private int mHeight = -1;
        private int mRemovedFlags = 0;
        private int mWidth = -1;

        public Builder addFlags(int n) {
            this.mAddedFlags |= n;
            this.mRemovedFlags &= n;
            return this;
        }

        public Vr2dDisplayProperties build() {
            return new Vr2dDisplayProperties(this.mWidth, this.mHeight, this.mDpi, this.mAddedFlags, this.mRemovedFlags);
        }

        public Builder removeFlags(int n) {
            this.mRemovedFlags |= n;
            this.mAddedFlags &= n;
            return this;
        }

        public Builder setDimensions(int n, int n2, int n3) {
            this.mWidth = n;
            this.mHeight = n2;
            this.mDpi = n3;
            return this;
        }

        public Builder setEnabled(boolean bl) {
            if (bl) {
                this.addFlags(1);
            } else {
                this.removeFlags(1);
            }
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Vr2dDisplayFlag {
    }

}


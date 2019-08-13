/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.os.Parcel;
import android.os.Parcelable;

public final class SliceSpec
implements Parcelable {
    public static final Parcelable.Creator<SliceSpec> CREATOR = new Parcelable.Creator<SliceSpec>(){

        @Override
        public SliceSpec createFromParcel(Parcel parcel) {
            return new SliceSpec(parcel);
        }

        public SliceSpec[] newArray(int n) {
            return new SliceSpec[n];
        }
    };
    private final int mRevision;
    private final String mType;

    public SliceSpec(Parcel parcel) {
        this.mType = parcel.readString();
        this.mRevision = parcel.readInt();
    }

    public SliceSpec(String string2, int n) {
        this.mType = string2;
        this.mRevision = n;
    }

    public boolean canRender(SliceSpec sliceSpec) {
        boolean bl = this.mType.equals(sliceSpec.mType);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (this.mRevision >= sliceSpec.mRevision) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof SliceSpec;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (SliceSpec)object;
        bl = bl2;
        if (this.mType.equals(((SliceSpec)object).mType)) {
            bl = bl2;
            if (this.mRevision == ((SliceSpec)object).mRevision) {
                bl = true;
            }
        }
        return bl;
    }

    public int getRevision() {
        return this.mRevision;
    }

    public String getType() {
        return this.mType;
    }

    public String toString() {
        return String.format("SliceSpec{%s,%d}", this.mType, this.mRevision);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mType);
        parcel.writeInt(this.mRevision);
    }

}


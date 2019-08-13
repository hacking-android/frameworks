/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public class MemoryRegion
implements Parcelable {
    public static final Parcelable.Creator<MemoryRegion> CREATOR = new Parcelable.Creator<MemoryRegion>(){

        @Override
        public MemoryRegion createFromParcel(Parcel parcel) {
            return new MemoryRegion(parcel);
        }

        public MemoryRegion[] newArray(int n) {
            return new MemoryRegion[n];
        }
    };
    private boolean mIsExecutable;
    private boolean mIsReadable;
    private boolean mIsWritable;
    private int mSizeBytes;
    private int mSizeBytesFree;

    public MemoryRegion(Parcel parcel) {
        this.mSizeBytes = parcel.readInt();
        this.mSizeBytesFree = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.mIsReadable = bl2;
        bl2 = parcel.readInt() != 0;
        this.mIsWritable = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mIsExecutable = bl2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        boolean bl2 = false;
        if (object instanceof MemoryRegion) {
            bl2 = ((MemoryRegion)(object = (MemoryRegion)object)).getCapacityBytes() == this.mSizeBytes && ((MemoryRegion)object).getFreeCapacityBytes() == this.mSizeBytesFree && ((MemoryRegion)object).isReadable() == this.mIsReadable && ((MemoryRegion)object).isWritable() == this.mIsWritable && ((MemoryRegion)object).isExecutable() == this.mIsExecutable ? bl : false;
        }
        return bl2;
    }

    public int getCapacityBytes() {
        return this.mSizeBytes;
    }

    public int getFreeCapacityBytes() {
        return this.mSizeBytesFree;
    }

    public boolean isExecutable() {
        return this.mIsExecutable;
    }

    public boolean isReadable() {
        return this.mIsReadable;
    }

    public boolean isWritable() {
        return this.mIsWritable;
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder;
        if (this.isReadable()) {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append("r");
            charSequence = charSequence.toString();
        } else {
            charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append("-");
            charSequence = charSequence.toString();
        }
        if (this.isWritable()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("w");
            charSequence = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("-");
            charSequence = stringBuilder.toString();
        }
        if (this.isExecutable()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("x");
            charSequence = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("-");
            charSequence = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        stringBuilder.append(this.mSizeBytesFree);
        stringBuilder.append("/ ");
        stringBuilder.append(this.mSizeBytes);
        stringBuilder.append(" ] : ");
        stringBuilder.append((String)charSequence);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSizeBytes);
        parcel.writeInt(this.mSizeBytesFree);
        parcel.writeInt((int)this.mIsReadable);
        parcel.writeInt((int)this.mIsWritable);
        parcel.writeInt((int)this.mIsExecutable);
    }

}


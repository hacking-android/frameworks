/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;

public final class RcsMessageSnippet
implements Parcelable {
    public static final Parcelable.Creator<RcsMessageSnippet> CREATOR = new Parcelable.Creator<RcsMessageSnippet>(){

        @Override
        public RcsMessageSnippet createFromParcel(Parcel parcel) {
            return new RcsMessageSnippet(parcel);
        }

        public RcsMessageSnippet[] newArray(int n) {
            return new RcsMessageSnippet[n];
        }
    };
    private final int mStatus;
    private final String mText;
    private final long mTimestamp;

    private RcsMessageSnippet(Parcel parcel) {
        this.mText = parcel.readString();
        this.mStatus = parcel.readInt();
        this.mTimestamp = parcel.readLong();
    }

    public RcsMessageSnippet(String string2, int n, long l) {
        this.mText = string2;
        this.mStatus = n;
        this.mTimestamp = l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getSnippetStatus() {
        return this.mStatus;
    }

    public String getSnippetText() {
        return this.mText;
    }

    public long getSnippetTimestamp() {
        return this.mTimestamp;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mText);
        parcel.writeInt(this.mStatus);
        parcel.writeLong(this.mTimestamp);
    }

}


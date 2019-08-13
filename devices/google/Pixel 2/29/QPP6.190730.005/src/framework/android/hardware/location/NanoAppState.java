/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class NanoAppState
implements Parcelable {
    public static final Parcelable.Creator<NanoAppState> CREATOR = new Parcelable.Creator<NanoAppState>(){

        @Override
        public NanoAppState createFromParcel(Parcel parcel) {
            return new NanoAppState(parcel);
        }

        public NanoAppState[] newArray(int n) {
            return new NanoAppState[n];
        }
    };
    private boolean mIsEnabled;
    private long mNanoAppId;
    private int mNanoAppVersion;

    public NanoAppState(long l, int n, boolean bl) {
        this.mNanoAppId = l;
        this.mNanoAppVersion = n;
        this.mIsEnabled = bl;
    }

    private NanoAppState(Parcel parcel) {
        this.mNanoAppId = parcel.readLong();
        this.mNanoAppVersion = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.mIsEnabled = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getNanoAppId() {
        return this.mNanoAppId;
    }

    public long getNanoAppVersion() {
        return this.mNanoAppVersion;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mNanoAppId);
        parcel.writeInt(this.mNanoAppVersion);
        parcel.writeInt((int)this.mIsEnabled);
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
public final class AudioFocusInfo
implements Parcelable {
    public static final Parcelable.Creator<AudioFocusInfo> CREATOR = new Parcelable.Creator<AudioFocusInfo>(){

        @Override
        public AudioFocusInfo createFromParcel(Parcel parcel) {
            AudioFocusInfo audioFocusInfo = new AudioFocusInfo(AudioAttributes.CREATOR.createFromParcel(parcel), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            audioFocusInfo.setGen(parcel.readLong());
            return audioFocusInfo;
        }

        public AudioFocusInfo[] newArray(int n) {
            return new AudioFocusInfo[n];
        }
    };
    private final AudioAttributes mAttributes;
    private final String mClientId;
    private final int mClientUid;
    private int mFlags;
    private int mGainRequest;
    private long mGenCount = -1L;
    private int mLossReceived;
    private final String mPackageName;
    private final int mSdkTarget;

    public AudioFocusInfo(AudioAttributes object, int n, String string2, String object2, int n2, int n3, int n4, int n5) {
        if (object == null) {
            object = new AudioAttributes.Builder().build();
        }
        this.mAttributes = object;
        this.mClientUid = n;
        object = "";
        if (string2 == null) {
            string2 = "";
        }
        this.mClientId = string2;
        if (object2 == null) {
            object2 = object;
        }
        this.mPackageName = object2;
        this.mGainRequest = n2;
        this.mLossReceived = n3;
        this.mFlags = n4;
        this.mSdkTarget = n5;
    }

    public void clearLossReceived() {
        this.mLossReceived = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AudioFocusInfo)object;
        if (!this.mAttributes.equals(((AudioFocusInfo)object).mAttributes)) {
            return false;
        }
        if (this.mClientUid != ((AudioFocusInfo)object).mClientUid) {
            return false;
        }
        if (!this.mClientId.equals(((AudioFocusInfo)object).mClientId)) {
            return false;
        }
        if (!this.mPackageName.equals(((AudioFocusInfo)object).mPackageName)) {
            return false;
        }
        if (this.mGainRequest != ((AudioFocusInfo)object).mGainRequest) {
            return false;
        }
        if (this.mLossReceived != ((AudioFocusInfo)object).mLossReceived) {
            return false;
        }
        if (this.mFlags != ((AudioFocusInfo)object).mFlags) {
            return false;
        }
        return this.mSdkTarget == ((AudioFocusInfo)object).mSdkTarget;
    }

    public AudioAttributes getAttributes() {
        return this.mAttributes;
    }

    public String getClientId() {
        return this.mClientId;
    }

    public int getClientUid() {
        return this.mClientUid;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getGainRequest() {
        return this.mGainRequest;
    }

    public long getGen() {
        return this.mGenCount;
    }

    public int getLossReceived() {
        return this.mLossReceived;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getSdkTarget() {
        return this.mSdkTarget;
    }

    public int hashCode() {
        return Objects.hash(this.mAttributes, this.mClientUid, this.mClientId, this.mPackageName, this.mGainRequest, this.mFlags);
    }

    public void setGen(long l) {
        this.mGenCount = l;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mAttributes.writeToParcel(parcel, n);
        parcel.writeInt(this.mClientUid);
        parcel.writeString(this.mClientId);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mGainRequest);
        parcel.writeInt(this.mLossReceived);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mSdkTarget);
        parcel.writeLong(this.mGenCount);
    }

}


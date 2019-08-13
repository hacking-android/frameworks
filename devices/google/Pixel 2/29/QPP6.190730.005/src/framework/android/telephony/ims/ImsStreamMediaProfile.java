/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class ImsStreamMediaProfile
implements Parcelable {
    public static final int AUDIO_QUALITY_AMR = 1;
    public static final int AUDIO_QUALITY_AMR_WB = 2;
    public static final int AUDIO_QUALITY_EVRC = 4;
    public static final int AUDIO_QUALITY_EVRC_B = 5;
    public static final int AUDIO_QUALITY_EVRC_NW = 7;
    public static final int AUDIO_QUALITY_EVRC_WB = 6;
    public static final int AUDIO_QUALITY_EVS_FB = 20;
    public static final int AUDIO_QUALITY_EVS_NB = 17;
    public static final int AUDIO_QUALITY_EVS_SWB = 19;
    public static final int AUDIO_QUALITY_EVS_WB = 18;
    public static final int AUDIO_QUALITY_G711A = 13;
    public static final int AUDIO_QUALITY_G711AB = 15;
    public static final int AUDIO_QUALITY_G711U = 11;
    public static final int AUDIO_QUALITY_G722 = 14;
    public static final int AUDIO_QUALITY_G723 = 12;
    public static final int AUDIO_QUALITY_G729 = 16;
    public static final int AUDIO_QUALITY_GSM_EFR = 8;
    public static final int AUDIO_QUALITY_GSM_FR = 9;
    public static final int AUDIO_QUALITY_GSM_HR = 10;
    public static final int AUDIO_QUALITY_NONE = 0;
    public static final int AUDIO_QUALITY_QCELP13K = 3;
    public static final Parcelable.Creator<ImsStreamMediaProfile> CREATOR = new Parcelable.Creator<ImsStreamMediaProfile>(){

        @Override
        public ImsStreamMediaProfile createFromParcel(Parcel parcel) {
            return new ImsStreamMediaProfile(parcel);
        }

        public ImsStreamMediaProfile[] newArray(int n) {
            return new ImsStreamMediaProfile[n];
        }
    };
    public static final int DIRECTION_INACTIVE = 0;
    public static final int DIRECTION_INVALID = -1;
    public static final int DIRECTION_RECEIVE = 1;
    public static final int DIRECTION_SEND = 2;
    public static final int DIRECTION_SEND_RECEIVE = 3;
    public static final int RTT_MODE_DISABLED = 0;
    public static final int RTT_MODE_FULL = 1;
    private static final String TAG = "ImsStreamMediaProfile";
    public static final int VIDEO_QUALITY_NONE = 0;
    public static final int VIDEO_QUALITY_QCIF = 1;
    public static final int VIDEO_QUALITY_QVGA_LANDSCAPE = 2;
    public static final int VIDEO_QUALITY_QVGA_PORTRAIT = 4;
    public static final int VIDEO_QUALITY_VGA_LANDSCAPE = 8;
    public static final int VIDEO_QUALITY_VGA_PORTRAIT = 16;
    @UnsupportedAppUsage
    public int mAudioDirection;
    @UnsupportedAppUsage
    public int mAudioQuality;
    public boolean mIsReceivingRttAudio = false;
    public int mRttMode;
    @UnsupportedAppUsage
    public int mVideoDirection;
    public int mVideoQuality;

    @UnsupportedAppUsage
    public ImsStreamMediaProfile() {
        this.mAudioQuality = 0;
        this.mAudioDirection = 3;
        this.mVideoQuality = 0;
        this.mVideoDirection = -1;
        this.mRttMode = 0;
    }

    public ImsStreamMediaProfile(int n) {
        this.mRttMode = n;
    }

    public ImsStreamMediaProfile(int n, int n2, int n3, int n4) {
        this.mAudioQuality = n;
        this.mAudioDirection = n2;
        this.mVideoQuality = n3;
        this.mVideoDirection = n4;
    }

    public ImsStreamMediaProfile(int n, int n2, int n3, int n4, int n5) {
        this.mAudioQuality = n;
        this.mAudioDirection = n2;
        this.mVideoQuality = n3;
        this.mVideoDirection = n4;
        this.mRttMode = n5;
    }

    public ImsStreamMediaProfile(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.mAudioQuality = parcel.readInt();
        this.mAudioDirection = parcel.readInt();
        this.mVideoQuality = parcel.readInt();
        this.mVideoDirection = parcel.readInt();
        this.mRttMode = parcel.readInt();
        this.mIsReceivingRttAudio = parcel.readBoolean();
    }

    public void copyFrom(ImsStreamMediaProfile imsStreamMediaProfile) {
        this.mAudioQuality = imsStreamMediaProfile.mAudioQuality;
        this.mAudioDirection = imsStreamMediaProfile.mAudioDirection;
        this.mVideoQuality = imsStreamMediaProfile.mVideoQuality;
        this.mVideoDirection = imsStreamMediaProfile.mVideoDirection;
        this.mRttMode = imsStreamMediaProfile.mRttMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAudioDirection() {
        return this.mAudioDirection;
    }

    public int getAudioQuality() {
        return this.mAudioQuality;
    }

    public int getRttMode() {
        return this.mRttMode;
    }

    public int getVideoDirection() {
        return this.mVideoDirection;
    }

    public int getVideoQuality() {
        return this.mVideoQuality;
    }

    public boolean isReceivingRttAudio() {
        return this.mIsReceivingRttAudio;
    }

    public boolean isRttCall() {
        int n = this.mRttMode;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public void setReceivingRttAudio(boolean bl) {
        this.mIsReceivingRttAudio = bl;
    }

    public void setRttMode(int n) {
        this.mRttMode = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ audioQuality=");
        stringBuilder.append(this.mAudioQuality);
        stringBuilder.append(", audioDirection=");
        stringBuilder.append(this.mAudioDirection);
        stringBuilder.append(", videoQuality=");
        stringBuilder.append(this.mVideoQuality);
        stringBuilder.append(", videoDirection=");
        stringBuilder.append(this.mVideoDirection);
        stringBuilder.append(", rttMode=");
        stringBuilder.append(this.mRttMode);
        stringBuilder.append(", hasRttAudioSpeech=");
        stringBuilder.append(this.mIsReceivingRttAudio);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAudioQuality);
        parcel.writeInt(this.mAudioDirection);
        parcel.writeInt(this.mVideoQuality);
        parcel.writeInt(this.mVideoDirection);
        parcel.writeInt(this.mRttMode);
        parcel.writeBoolean(this.mIsReceivingRttAudio);
    }

}


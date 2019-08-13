/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PlaybackParams
implements Parcelable {
    public static final int AUDIO_FALLBACK_MODE_DEFAULT = 0;
    public static final int AUDIO_FALLBACK_MODE_FAIL = 2;
    public static final int AUDIO_FALLBACK_MODE_MUTE = 1;
    public static final int AUDIO_STRETCH_MODE_DEFAULT = 0;
    public static final int AUDIO_STRETCH_MODE_VOICE = 1;
    public static final Parcelable.Creator<PlaybackParams> CREATOR = new Parcelable.Creator<PlaybackParams>(){

        @Override
        public PlaybackParams createFromParcel(Parcel parcel) {
            return new PlaybackParams(parcel);
        }

        public PlaybackParams[] newArray(int n) {
            return new PlaybackParams[n];
        }
    };
    @UnsupportedAppUsage
    private static final int SET_AUDIO_FALLBACK_MODE = 4;
    @UnsupportedAppUsage
    private static final int SET_AUDIO_STRETCH_MODE = 8;
    @UnsupportedAppUsage
    private static final int SET_PITCH = 2;
    @UnsupportedAppUsage
    private static final int SET_SPEED = 1;
    @UnsupportedAppUsage
    private int mAudioFallbackMode = 0;
    @UnsupportedAppUsage
    private int mAudioStretchMode = 0;
    @UnsupportedAppUsage
    private float mPitch = 1.0f;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mSet = 0;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private float mSpeed = 1.0f;

    public PlaybackParams() {
    }

    private PlaybackParams(Parcel parcel) {
        this.mSet = parcel.readInt();
        this.mAudioFallbackMode = parcel.readInt();
        this.mAudioStretchMode = parcel.readInt();
        this.mPitch = parcel.readFloat();
        if (this.mPitch < 0.0f) {
            this.mPitch = 0.0f;
        }
        this.mSpeed = parcel.readFloat();
    }

    public PlaybackParams allowDefaults() {
        this.mSet |= 15;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAudioFallbackMode() {
        if ((this.mSet & 4) != 0) {
            return this.mAudioFallbackMode;
        }
        throw new IllegalStateException("audio fallback mode not set");
    }

    public int getAudioStretchMode() {
        if ((this.mSet & 8) != 0) {
            return this.mAudioStretchMode;
        }
        throw new IllegalStateException("audio stretch mode not set");
    }

    public float getPitch() {
        if ((this.mSet & 2) != 0) {
            return this.mPitch;
        }
        throw new IllegalStateException("pitch not set");
    }

    public float getSpeed() {
        if ((this.mSet & 1) != 0) {
            return this.mSpeed;
        }
        throw new IllegalStateException("speed not set");
    }

    public PlaybackParams setAudioFallbackMode(int n) {
        this.mAudioFallbackMode = n;
        this.mSet |= 4;
        return this;
    }

    public PlaybackParams setAudioStretchMode(int n) {
        this.mAudioStretchMode = n;
        this.mSet |= 8;
        return this;
    }

    public PlaybackParams setPitch(float f) {
        if (!(f < 0.0f)) {
            this.mPitch = f;
            this.mSet |= 2;
            return this;
        }
        throw new IllegalArgumentException("pitch must not be negative");
    }

    public PlaybackParams setSpeed(float f) {
        this.mSpeed = f;
        this.mSet |= 1;
        return this;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSet);
        parcel.writeInt(this.mAudioFallbackMode);
        parcel.writeInt(this.mAudioStretchMode);
        parcel.writeFloat(this.mPitch);
        parcel.writeFloat(this.mSpeed);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AudioFallbackMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AudioStretchMode {
    }

}


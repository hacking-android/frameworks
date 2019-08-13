/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class TvTrackInfo
implements Parcelable {
    public static final Parcelable.Creator<TvTrackInfo> CREATOR = new Parcelable.Creator<TvTrackInfo>(){

        @Override
        public TvTrackInfo createFromParcel(Parcel parcel) {
            return new TvTrackInfo(parcel);
        }

        public TvTrackInfo[] newArray(int n) {
            return new TvTrackInfo[n];
        }
    };
    public static final int TYPE_AUDIO = 0;
    public static final int TYPE_SUBTITLE = 2;
    public static final int TYPE_VIDEO = 1;
    private final int mAudioChannelCount;
    private final int mAudioSampleRate;
    private final CharSequence mDescription;
    private final Bundle mExtra;
    private final String mId;
    private final String mLanguage;
    private final int mType;
    private final byte mVideoActiveFormatDescription;
    private final float mVideoFrameRate;
    private final int mVideoHeight;
    private final float mVideoPixelAspectRatio;
    private final int mVideoWidth;

    private TvTrackInfo(int n, String string2, String string3, CharSequence charSequence, int n2, int n3, int n4, int n5, float f, float f2, byte by, Bundle bundle) {
        this.mType = n;
        this.mId = string2;
        this.mLanguage = string3;
        this.mDescription = charSequence;
        this.mAudioChannelCount = n2;
        this.mAudioSampleRate = n3;
        this.mVideoWidth = n4;
        this.mVideoHeight = n5;
        this.mVideoFrameRate = f;
        this.mVideoPixelAspectRatio = f2;
        this.mVideoActiveFormatDescription = by;
        this.mExtra = bundle;
    }

    private TvTrackInfo(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mId = parcel.readString();
        this.mLanguage = parcel.readString();
        this.mDescription = parcel.readString();
        this.mAudioChannelCount = parcel.readInt();
        this.mAudioSampleRate = parcel.readInt();
        this.mVideoWidth = parcel.readInt();
        this.mVideoHeight = parcel.readInt();
        this.mVideoFrameRate = parcel.readFloat();
        this.mVideoPixelAspectRatio = parcel.readFloat();
        this.mVideoActiveFormatDescription = parcel.readByte();
        this.mExtra = parcel.readBundle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        int n;
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof TvTrackInfo)) {
            return false;
        }
        object = (TvTrackInfo)object;
        if (!(TextUtils.equals(this.mId, ((TvTrackInfo)object).mId) && this.mType == ((TvTrackInfo)object).mType && TextUtils.equals(this.mLanguage, ((TvTrackInfo)object).mLanguage) && TextUtils.equals(this.mDescription, ((TvTrackInfo)object).mDescription) && Objects.equals(this.mExtra, ((TvTrackInfo)object).mExtra) && ((n = this.mType) == 0 ? this.mAudioChannelCount == ((TvTrackInfo)object).mAudioChannelCount && this.mAudioSampleRate == ((TvTrackInfo)object).mAudioSampleRate : n != 1 || this.mVideoWidth == ((TvTrackInfo)object).mVideoWidth && this.mVideoHeight == ((TvTrackInfo)object).mVideoHeight && this.mVideoFrameRate == ((TvTrackInfo)object).mVideoFrameRate && this.mVideoPixelAspectRatio == ((TvTrackInfo)object).mVideoPixelAspectRatio))) {
            bl = false;
        }
        return bl;
    }

    public final int getAudioChannelCount() {
        if (this.mType == 0) {
            return this.mAudioChannelCount;
        }
        throw new IllegalStateException("Not an audio track");
    }

    public final int getAudioSampleRate() {
        if (this.mType == 0) {
            return this.mAudioSampleRate;
        }
        throw new IllegalStateException("Not an audio track");
    }

    public final CharSequence getDescription() {
        return this.mDescription;
    }

    public final Bundle getExtra() {
        return this.mExtra;
    }

    public final String getId() {
        return this.mId;
    }

    public final String getLanguage() {
        return this.mLanguage;
    }

    public final int getType() {
        return this.mType;
    }

    public final byte getVideoActiveFormatDescription() {
        if (this.mType == 1) {
            return this.mVideoActiveFormatDescription;
        }
        throw new IllegalStateException("Not a video track");
    }

    public final float getVideoFrameRate() {
        if (this.mType == 1) {
            return this.mVideoFrameRate;
        }
        throw new IllegalStateException("Not a video track");
    }

    public final int getVideoHeight() {
        if (this.mType == 1) {
            return this.mVideoHeight;
        }
        throw new IllegalStateException("Not a video track");
    }

    public final float getVideoPixelAspectRatio() {
        if (this.mType == 1) {
            return this.mVideoPixelAspectRatio;
        }
        throw new IllegalStateException("Not a video track");
    }

    public final int getVideoWidth() {
        if (this.mType == 1) {
            return this.mVideoWidth;
        }
        throw new IllegalStateException("Not a video track");
    }

    public int hashCode() {
        return Objects.hashCode(this.mId);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeString(this.mId);
        parcel.writeString(this.mLanguage);
        CharSequence charSequence = this.mDescription;
        charSequence = charSequence != null ? charSequence.toString() : null;
        parcel.writeString((String)charSequence);
        parcel.writeInt(this.mAudioChannelCount);
        parcel.writeInt(this.mAudioSampleRate);
        parcel.writeInt(this.mVideoWidth);
        parcel.writeInt(this.mVideoHeight);
        parcel.writeFloat(this.mVideoFrameRate);
        parcel.writeFloat(this.mVideoPixelAspectRatio);
        parcel.writeByte(this.mVideoActiveFormatDescription);
        parcel.writeBundle(this.mExtra);
    }

    public static final class Builder {
        private int mAudioChannelCount;
        private int mAudioSampleRate;
        private CharSequence mDescription;
        private Bundle mExtra;
        private final String mId;
        private String mLanguage;
        private final int mType;
        private byte mVideoActiveFormatDescription;
        private float mVideoFrameRate;
        private int mVideoHeight;
        private float mVideoPixelAspectRatio = 1.0f;
        private int mVideoWidth;

        public Builder(int n, String charSequence) {
            if (n != 0 && n != 1 && n != 2) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown type: ");
                ((StringBuilder)charSequence).append(n);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            Preconditions.checkNotNull(charSequence);
            this.mType = n;
            this.mId = charSequence;
        }

        public TvTrackInfo build() {
            return new TvTrackInfo(this.mType, this.mId, this.mLanguage, this.mDescription, this.mAudioChannelCount, this.mAudioSampleRate, this.mVideoWidth, this.mVideoHeight, this.mVideoFrameRate, this.mVideoPixelAspectRatio, this.mVideoActiveFormatDescription, this.mExtra);
        }

        public final Builder setAudioChannelCount(int n) {
            if (this.mType == 0) {
                this.mAudioChannelCount = n;
                return this;
            }
            throw new IllegalStateException("Not an audio track");
        }

        public final Builder setAudioSampleRate(int n) {
            if (this.mType == 0) {
                this.mAudioSampleRate = n;
                return this;
            }
            throw new IllegalStateException("Not an audio track");
        }

        public final Builder setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public final Builder setExtra(Bundle bundle) {
            this.mExtra = new Bundle(bundle);
            return this;
        }

        public final Builder setLanguage(String string2) {
            this.mLanguage = string2;
            return this;
        }

        public final Builder setVideoActiveFormatDescription(byte by) {
            if (this.mType == 1) {
                this.mVideoActiveFormatDescription = by;
                return this;
            }
            throw new IllegalStateException("Not a video track");
        }

        public final Builder setVideoFrameRate(float f) {
            if (this.mType == 1) {
                this.mVideoFrameRate = f;
                return this;
            }
            throw new IllegalStateException("Not a video track");
        }

        public final Builder setVideoHeight(int n) {
            if (this.mType == 1) {
                this.mVideoHeight = n;
                return this;
            }
            throw new IllegalStateException("Not a video track");
        }

        public final Builder setVideoPixelAspectRatio(float f) {
            if (this.mType == 1) {
                this.mVideoPixelAspectRatio = f;
                return this;
            }
            throw new IllegalStateException("Not a video track");
        }

        public final Builder setVideoWidth(int n) {
            if (this.mType == 1) {
                this.mVideoWidth = n;
                return this;
            }
            throw new IllegalStateException("Not a video track");
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}


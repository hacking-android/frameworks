/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class VideoProfile
implements Parcelable {
    public static final Parcelable.Creator<VideoProfile> CREATOR = new Parcelable.Creator<VideoProfile>(){

        @Override
        public VideoProfile createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            VideoProfile.class.getClassLoader();
            return new VideoProfile(n, n2);
        }

        public VideoProfile[] newArray(int n) {
            return new VideoProfile[n];
        }
    };
    public static final int QUALITY_DEFAULT = 4;
    public static final int QUALITY_HIGH = 1;
    public static final int QUALITY_LOW = 3;
    public static final int QUALITY_MEDIUM = 2;
    public static final int QUALITY_UNKNOWN = 0;
    public static final int STATE_AUDIO_ONLY = 0;
    public static final int STATE_BIDIRECTIONAL = 3;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_RX_ENABLED = 2;
    public static final int STATE_TX_ENABLED = 1;
    private final int mQuality;
    private final int mVideoState;

    public VideoProfile(int n) {
        this(n, 4);
    }

    public VideoProfile(int n, int n2) {
        this.mVideoState = n;
        this.mQuality = n2;
    }

    private static boolean hasState(int n, int n2) {
        boolean bl = (n & n2) == n2;
        return bl;
    }

    public static boolean isAudioOnly(int n) {
        boolean bl = true;
        if (VideoProfile.hasState(n, 1) || VideoProfile.hasState(n, 2)) {
            bl = false;
        }
        return bl;
    }

    public static boolean isBidirectional(int n) {
        return VideoProfile.hasState(n, 3);
    }

    public static boolean isPaused(int n) {
        return VideoProfile.hasState(n, 4);
    }

    public static boolean isReceptionEnabled(int n) {
        return VideoProfile.hasState(n, 2);
    }

    public static boolean isTransmissionEnabled(int n) {
        return VideoProfile.hasState(n, 1);
    }

    public static boolean isVideo(int n) {
        boolean bl;
        block0 : {
            bl = true;
            if (VideoProfile.hasState(n, 1) || VideoProfile.hasState(n, 2) || VideoProfile.hasState(n, 3)) break block0;
            bl = false;
        }
        return bl;
    }

    public static String videoStateToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Audio");
        if (n == 0) {
            stringBuilder.append(" Only");
        } else {
            if (VideoProfile.isTransmissionEnabled(n)) {
                stringBuilder.append(" Tx");
            }
            if (VideoProfile.isReceptionEnabled(n)) {
                stringBuilder.append(" Rx");
            }
            if (VideoProfile.isPaused(n)) {
                stringBuilder.append(" Pause");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getQuality() {
        return this.mQuality;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[VideoProfile videoState = ");
        stringBuilder.append(VideoProfile.videoStateToString(this.mVideoState));
        stringBuilder.append(" videoQuality = ");
        stringBuilder.append(this.mQuality);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mVideoState);
        parcel.writeInt(this.mQuality);
    }

    public static final class CameraCapabilities
    implements Parcelable {
        public static final Parcelable.Creator<CameraCapabilities> CREATOR = new Parcelable.Creator<CameraCapabilities>(){

            @Override
            public CameraCapabilities createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                int n2 = parcel.readInt();
                boolean bl = parcel.readByte() != 0;
                return new CameraCapabilities(n, n2, bl, parcel.readFloat());
            }

            public CameraCapabilities[] newArray(int n) {
                return new CameraCapabilities[n];
            }
        };
        private final int mHeight;
        private final float mMaxZoom;
        private final int mWidth;
        private final boolean mZoomSupported;

        public CameraCapabilities(int n, int n2) {
            this(n, n2, false, 1.0f);
        }

        public CameraCapabilities(int n, int n2, boolean bl, float f) {
            this.mWidth = n;
            this.mHeight = n2;
            this.mZoomSupported = bl;
            this.mMaxZoom = f;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public float getMaxZoom() {
            return this.mMaxZoom;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public boolean isZoomSupported() {
            return this.mZoomSupported;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.getWidth());
            parcel.writeInt(this.getHeight());
            parcel.writeByte((byte)(this.isZoomSupported() ? 1 : 0));
            parcel.writeFloat(this.getMaxZoom());
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VideoQuality {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VideoState {
    }

}


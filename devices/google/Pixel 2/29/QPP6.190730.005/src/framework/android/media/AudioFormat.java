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
import java.util.Arrays;
import java.util.Objects;

public final class AudioFormat
implements Parcelable {
    public static final int AUDIO_FORMAT_HAS_PROPERTY_CHANNEL_INDEX_MASK = 8;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_CHANNEL_MASK = 4;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_ENCODING = 1;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_NONE = 0;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_SAMPLE_RATE = 2;
    @Deprecated
    public static final int CHANNEL_CONFIGURATION_DEFAULT = 1;
    @Deprecated
    public static final int CHANNEL_CONFIGURATION_INVALID = 0;
    @Deprecated
    public static final int CHANNEL_CONFIGURATION_MONO = 2;
    @Deprecated
    public static final int CHANNEL_CONFIGURATION_STEREO = 3;
    public static final int CHANNEL_INVALID = 0;
    public static final int CHANNEL_IN_BACK = 32;
    public static final int CHANNEL_IN_BACK_PROCESSED = 512;
    public static final int CHANNEL_IN_DEFAULT = 1;
    public static final int CHANNEL_IN_FRONT = 16;
    public static final int CHANNEL_IN_FRONT_BACK = 48;
    public static final int CHANNEL_IN_FRONT_PROCESSED = 256;
    public static final int CHANNEL_IN_LEFT = 4;
    public static final int CHANNEL_IN_LEFT_PROCESSED = 64;
    public static final int CHANNEL_IN_MONO = 16;
    public static final int CHANNEL_IN_PRESSURE = 1024;
    public static final int CHANNEL_IN_RIGHT = 8;
    public static final int CHANNEL_IN_RIGHT_PROCESSED = 128;
    public static final int CHANNEL_IN_STEREO = 12;
    public static final int CHANNEL_IN_VOICE_DNLINK = 32768;
    public static final int CHANNEL_IN_VOICE_UPLINK = 16384;
    public static final int CHANNEL_IN_X_AXIS = 2048;
    public static final int CHANNEL_IN_Y_AXIS = 4096;
    public static final int CHANNEL_IN_Z_AXIS = 8192;
    public static final int CHANNEL_OUT_5POINT1 = 252;
    public static final int CHANNEL_OUT_5POINT1_SIDE = 6204;
    @Deprecated
    public static final int CHANNEL_OUT_7POINT1 = 1020;
    public static final int CHANNEL_OUT_7POINT1_SURROUND = 6396;
    public static final int CHANNEL_OUT_BACK_CENTER = 1024;
    public static final int CHANNEL_OUT_BACK_LEFT = 64;
    public static final int CHANNEL_OUT_BACK_RIGHT = 128;
    public static final int CHANNEL_OUT_DEFAULT = 1;
    public static final int CHANNEL_OUT_FRONT_CENTER = 16;
    public static final int CHANNEL_OUT_FRONT_LEFT = 4;
    public static final int CHANNEL_OUT_FRONT_LEFT_OF_CENTER = 256;
    public static final int CHANNEL_OUT_FRONT_RIGHT = 8;
    public static final int CHANNEL_OUT_FRONT_RIGHT_OF_CENTER = 512;
    public static final int CHANNEL_OUT_LOW_FREQUENCY = 32;
    public static final int CHANNEL_OUT_MONO = 4;
    public static final int CHANNEL_OUT_QUAD = 204;
    public static final int CHANNEL_OUT_QUAD_SIDE = 6156;
    public static final int CHANNEL_OUT_SIDE_LEFT = 2048;
    public static final int CHANNEL_OUT_SIDE_RIGHT = 4096;
    public static final int CHANNEL_OUT_STEREO = 12;
    public static final int CHANNEL_OUT_SURROUND = 1052;
    public static final int CHANNEL_OUT_TOP_BACK_CENTER = 262144;
    public static final int CHANNEL_OUT_TOP_BACK_LEFT = 131072;
    public static final int CHANNEL_OUT_TOP_BACK_RIGHT = 524288;
    public static final int CHANNEL_OUT_TOP_CENTER = 8192;
    public static final int CHANNEL_OUT_TOP_FRONT_CENTER = 32768;
    public static final int CHANNEL_OUT_TOP_FRONT_LEFT = 16384;
    public static final int CHANNEL_OUT_TOP_FRONT_RIGHT = 65536;
    public static final Parcelable.Creator<AudioFormat> CREATOR = new Parcelable.Creator<AudioFormat>(){

        @Override
        public AudioFormat createFromParcel(Parcel parcel) {
            return new AudioFormat(parcel);
        }

        public AudioFormat[] newArray(int n) {
            return new AudioFormat[n];
        }
    };
    public static final int ENCODING_AAC_ELD = 15;
    public static final int ENCODING_AAC_HE_V1 = 11;
    public static final int ENCODING_AAC_HE_V2 = 12;
    public static final int ENCODING_AAC_LC = 10;
    public static final int ENCODING_AAC_XHE = 16;
    public static final int ENCODING_AC3 = 5;
    public static final int ENCODING_AC4 = 17;
    public static final int ENCODING_DEFAULT = 1;
    public static final int ENCODING_DOLBY_MAT = 19;
    public static final int ENCODING_DOLBY_TRUEHD = 14;
    public static final int ENCODING_DTS = 7;
    public static final int ENCODING_DTS_HD = 8;
    public static final int ENCODING_E_AC3 = 6;
    public static final int ENCODING_E_AC3_JOC = 18;
    public static final int ENCODING_IEC61937 = 13;
    public static final int ENCODING_INVALID = 0;
    public static final int ENCODING_MP3 = 9;
    public static final int ENCODING_PCM_16BIT = 2;
    public static final int ENCODING_PCM_8BIT = 3;
    public static final int ENCODING_PCM_FLOAT = 4;
    public static final int SAMPLE_RATE_HZ_MAX = 192000;
    public static final int SAMPLE_RATE_HZ_MIN = 4000;
    public static final int SAMPLE_RATE_UNSPECIFIED = 0;
    public static final int[] SURROUND_SOUND_ENCODING = new int[]{5, 6, 7, 8, 10, 14, 17, 18, 19};
    private final int mChannelCount;
    private final int mChannelIndexMask;
    @UnsupportedAppUsage
    private final int mChannelMask;
    @UnsupportedAppUsage
    private final int mEncoding;
    private final int mFrameSizeInBytes;
    private final int mPropertySetMask;
    @UnsupportedAppUsage
    private final int mSampleRate;

    public AudioFormat() {
        throw new UnsupportedOperationException("There is no valid usage of this constructor");
    }

    @UnsupportedAppUsage
    private AudioFormat(int n, int n2, int n3, int n4) {
        this(15, n, n2, n3, n4);
    }

    private AudioFormat(int n, int n2, int n3, int n4, int n5) {
        this.mPropertySetMask = n;
        int n6 = 0;
        if ((n & 1) == 0) {
            n2 = 0;
        }
        this.mEncoding = n2;
        if ((n & 2) == 0) {
            n3 = 0;
        }
        this.mSampleRate = n3;
        n2 = (n & 4) != 0 ? n4 : 0;
        this.mChannelMask = n2;
        n = (n & 8) != 0 ? n5 : n6;
        this.mChannelIndexMask = n;
        n2 = Integer.bitCount(this.getChannelIndexMask());
        n3 = AudioFormat.channelCountFromOutChannelMask(this.getChannelMask());
        if (n3 == 0) {
            n = n2;
        } else {
            n = n3;
            if (n3 != n2) {
                n = n3;
                if (n2 != 0) {
                    n = 0;
                }
            }
        }
        this.mChannelCount = n;
        n2 = 1;
        try {
            n3 = AudioFormat.getBytesPerSample(this.mEncoding);
            n = n3 * n;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            n = n2;
        }
        if (n == 0) {
            n = 1;
        }
        this.mFrameSizeInBytes = n;
    }

    private AudioFormat(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public static int channelCountFromInChannelMask(int n) {
        return Integer.bitCount(n);
    }

    public static int channelCountFromOutChannelMask(int n) {
        return Integer.bitCount(n);
    }

    public static int convertChannelOutMaskToNativeMask(int n) {
        return n >> 2;
    }

    public static int convertNativeChannelMaskToOutMask(int n) {
        return n << 2;
    }

    public static int[] filterPublicFormats(int[] arrn) {
        if (arrn == null) {
            return null;
        }
        arrn = Arrays.copyOf(arrn, arrn.length);
        int n = 0;
        for (int i = 0; i < arrn.length; ++i) {
            int n2 = n;
            if (AudioFormat.isPublicEncoding(arrn[i])) {
                if (n != i) {
                    arrn[n] = arrn[i];
                }
                n2 = n + 1;
            }
            n = n2;
        }
        return Arrays.copyOf(arrn, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getBytesPerSample(int n) {
        if (n == 1 || n == 2) return 2;
        if (n == 3) return 1;
        if (n == 4) return 4;
        if (n == 13) return 2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad audio format ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static int inChannelMaskFromOutChannelMask(int n) throws IllegalArgumentException {
        if (n != 1) {
            if ((n = AudioFormat.channelCountFromOutChannelMask(n)) != 1) {
                if (n == 2) {
                    return 12;
                }
                throw new IllegalArgumentException("Unsupported channel configuration for input.");
            }
            return 16;
        }
        throw new IllegalArgumentException("Illegal CHANNEL_OUT_DEFAULT channel mask for input.");
    }

    public static boolean isEncodingLinearFrames(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad audio format ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: {
                return false;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 13: 
        }
        return true;
    }

    public static boolean isEncodingLinearPcm(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad audio format ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: {
                return false;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
        }
        return true;
    }

    public static boolean isPublicEncoding(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
        }
        return true;
    }

    public static boolean isValidEncoding(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
        }
        return true;
    }

    public static String toDisplayName(int n) {
        if (n != 5) {
            if (n != 6) {
                if (n != 7) {
                    if (n != 8) {
                        if (n != 10) {
                            if (n != 14) {
                                switch (n) {
                                    default: {
                                        return "Unknown surround sound format";
                                    }
                                    case 19: {
                                        return "Dolby MAT";
                                    }
                                    case 18: {
                                        return "Dolby Atmos in Dolby Digital Plus";
                                    }
                                    case 17: 
                                }
                                return "Dolby AC-4";
                            }
                            return "Dolby TrueHD";
                        }
                        return "AAC";
                    }
                    return "DTS HD";
                }
                return "DTS";
            }
            return "Dolby Digital Plus";
        }
        return "Dolby Digital";
    }

    public static String toLogFriendlyEncoding(int n) {
        if (n != 0) {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid encoding ");
                    stringBuilder.append(n);
                    return stringBuilder.toString();
                }
                case 19: {
                    return "ENCODING_DOLBY_MAT";
                }
                case 18: {
                    return "ENCODING_E_AC3_JOC";
                }
                case 17: {
                    return "ENCODING_AC4";
                }
                case 16: {
                    return "ENCODING_AAC_XHE";
                }
                case 15: {
                    return "ENCODING_AAC_ELD";
                }
                case 14: {
                    return "ENCODING_DOLBY_TRUEHD";
                }
                case 13: {
                    return "ENCODING_IEC61937";
                }
                case 12: {
                    return "ENCODING_AAC_HE_V2";
                }
                case 11: {
                    return "ENCODING_AAC_HE_V1";
                }
                case 10: {
                    return "ENCODING_AAC_LC";
                }
                case 9: {
                    return "ENCODING_MP3";
                }
                case 8: {
                    return "ENCODING_DTS_HD";
                }
                case 7: {
                    return "ENCODING_DTS";
                }
                case 6: {
                    return "ENCODING_E_AC3";
                }
                case 5: {
                    return "ENCODING_AC3";
                }
                case 4: {
                    return "ENCODING_PCM_FLOAT";
                }
                case 3: {
                    return "ENCODING_PCM_8BIT";
                }
                case 2: 
            }
            return "ENCODING_PCM_16BIT";
        }
        return "ENCODING_INVALID";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        object = (AudioFormat)object;
        int n = this.mPropertySetMask;
        if (n != ((AudioFormat)object).mPropertySetMask) {
            return false;
        }
        if ((n & 1) != 0) {
            if (this.mEncoding != ((AudioFormat)object).mEncoding) return false;
        }
        if ((this.mPropertySetMask & 2) != 0) {
            if (this.mSampleRate != ((AudioFormat)object).mSampleRate) return false;
        }
        if ((this.mPropertySetMask & 4) != 0) {
            if (this.mChannelMask != ((AudioFormat)object).mChannelMask) return false;
        }
        boolean bl2 = bl;
        if ((this.mPropertySetMask & 8) == 0) return bl2;
        if (this.mChannelIndexMask != ((AudioFormat)object).mChannelIndexMask) return false;
        return bl;
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getChannelIndexMask() {
        return this.mChannelIndexMask;
    }

    public int getChannelMask() {
        return this.mChannelMask;
    }

    public int getEncoding() {
        return this.mEncoding;
    }

    public int getFrameSizeInBytes() {
        return this.mFrameSizeInBytes;
    }

    public int getPropertySetMask() {
        return this.mPropertySetMask;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int hashCode() {
        return Objects.hash(this.mPropertySetMask, this.mSampleRate, this.mEncoding, this.mChannelMask, this.mChannelIndexMask);
    }

    public String toLogFriendlyString() {
        return String.format("%dch %dHz %s", this.mChannelCount, this.mSampleRate, AudioFormat.toLogFriendlyEncoding(this.mEncoding));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AudioFormat: props=");
        stringBuilder.append(this.mPropertySetMask);
        stringBuilder.append(" enc=");
        stringBuilder.append(this.mEncoding);
        stringBuilder.append(" chan=0x");
        stringBuilder.append(Integer.toHexString(this.mChannelMask).toUpperCase());
        stringBuilder.append(" chan_index=0x");
        stringBuilder.append(Integer.toHexString(this.mChannelIndexMask).toUpperCase());
        stringBuilder.append(" rate=");
        stringBuilder.append(this.mSampleRate);
        return new String(stringBuilder.toString());
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mPropertySetMask);
        parcel.writeInt(this.mEncoding);
        parcel.writeInt(this.mSampleRate);
        parcel.writeInt(this.mChannelMask);
        parcel.writeInt(this.mChannelIndexMask);
    }

    public static class Builder {
        private int mChannelIndexMask = 0;
        private int mChannelMask = 0;
        private int mEncoding = 0;
        private int mPropertySetMask = 0;
        private int mSampleRate = 0;

        public Builder() {
        }

        public Builder(AudioFormat audioFormat) {
            this.mEncoding = audioFormat.mEncoding;
            this.mSampleRate = audioFormat.mSampleRate;
            this.mChannelMask = audioFormat.mChannelMask;
            this.mChannelIndexMask = audioFormat.mChannelIndexMask;
            this.mPropertySetMask = audioFormat.mPropertySetMask;
        }

        public AudioFormat build() {
            return new AudioFormat(this.mPropertySetMask, this.mEncoding, this.mSampleRate, this.mChannelMask, this.mChannelIndexMask);
        }

        public Builder setChannelIndexMask(int n) {
            if (n != 0) {
                if (this.mChannelMask != 0 && Integer.bitCount(n) != Integer.bitCount(this.mChannelMask)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Mismatched channel count for index mask ");
                    stringBuilder.append(Integer.toHexString(n).toUpperCase());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                this.mChannelIndexMask = n;
                this.mPropertySetMask |= 8;
                return this;
            }
            throw new IllegalArgumentException("Invalid zero channel index mask");
        }

        public Builder setChannelMask(int n) {
            if (n != 0) {
                if (this.mChannelIndexMask != 0 && Integer.bitCount(n) != Integer.bitCount(this.mChannelIndexMask)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Mismatched channel count for mask ");
                    stringBuilder.append(Integer.toHexString(n).toUpperCase());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                this.mChannelMask = n;
                this.mPropertySetMask |= 4;
                return this;
            }
            throw new IllegalArgumentException("Invalid zero channel mask");
        }

        public Builder setEncoding(int n) throws IllegalArgumentException {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid encoding ");
                    stringBuilder.append(n);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 17: 
                case 18: 
                case 19: {
                    this.mEncoding = n;
                    break;
                }
                case 1: {
                    this.mEncoding = 2;
                }
            }
            this.mPropertySetMask |= 1;
            return this;
        }

        public Builder setSampleRate(int n) throws IllegalArgumentException {
            if (n >= 4000 && n <= 192000 || n == 0) {
                this.mSampleRate = n;
                this.mPropertySetMask |= 2;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid sample rate ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Encoding {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SurroundSoundEncoding {
    }

}


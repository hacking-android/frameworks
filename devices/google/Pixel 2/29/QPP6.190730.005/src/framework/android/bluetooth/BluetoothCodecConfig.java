/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class BluetoothCodecConfig
implements Parcelable {
    @UnsupportedAppUsage
    public static final int BITS_PER_SAMPLE_16 = 1;
    @UnsupportedAppUsage
    public static final int BITS_PER_SAMPLE_24 = 2;
    @UnsupportedAppUsage
    public static final int BITS_PER_SAMPLE_32 = 4;
    @UnsupportedAppUsage
    public static final int BITS_PER_SAMPLE_NONE = 0;
    @UnsupportedAppUsage
    public static final int CHANNEL_MODE_MONO = 1;
    @UnsupportedAppUsage
    public static final int CHANNEL_MODE_NONE = 0;
    @UnsupportedAppUsage
    public static final int CHANNEL_MODE_STEREO = 2;
    @UnsupportedAppUsage
    public static final int CODEC_PRIORITY_DEFAULT = 0;
    @UnsupportedAppUsage
    public static final int CODEC_PRIORITY_DISABLED = -1;
    @UnsupportedAppUsage
    public static final int CODEC_PRIORITY_HIGHEST = 1000000;
    public static final Parcelable.Creator<BluetoothCodecConfig> CREATOR = new Parcelable.Creator<BluetoothCodecConfig>(){

        @Override
        public BluetoothCodecConfig createFromParcel(Parcel parcel) {
            return new BluetoothCodecConfig(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readLong(), parcel.readLong(), parcel.readLong(), parcel.readLong());
        }

        public BluetoothCodecConfig[] newArray(int n) {
            return new BluetoothCodecConfig[n];
        }
    };
    @UnsupportedAppUsage
    public static final int SAMPLE_RATE_176400 = 16;
    @UnsupportedAppUsage
    public static final int SAMPLE_RATE_192000 = 32;
    @UnsupportedAppUsage
    public static final int SAMPLE_RATE_44100 = 1;
    @UnsupportedAppUsage
    public static final int SAMPLE_RATE_48000 = 2;
    @UnsupportedAppUsage
    public static final int SAMPLE_RATE_88200 = 4;
    @UnsupportedAppUsage
    public static final int SAMPLE_RATE_96000 = 8;
    @UnsupportedAppUsage
    public static final int SAMPLE_RATE_NONE = 0;
    @UnsupportedAppUsage
    public static final int SOURCE_CODEC_TYPE_AAC = 1;
    @UnsupportedAppUsage
    public static final int SOURCE_CODEC_TYPE_APTX = 2;
    @UnsupportedAppUsage
    public static final int SOURCE_CODEC_TYPE_APTX_HD = 3;
    @UnsupportedAppUsage
    public static final int SOURCE_CODEC_TYPE_INVALID = 1000000;
    @UnsupportedAppUsage
    public static final int SOURCE_CODEC_TYPE_LDAC = 4;
    @UnsupportedAppUsage
    public static final int SOURCE_CODEC_TYPE_MAX = 5;
    @UnsupportedAppUsage
    public static final int SOURCE_CODEC_TYPE_SBC = 0;
    private final int mBitsPerSample;
    private final int mChannelMode;
    private int mCodecPriority;
    private final long mCodecSpecific1;
    private final long mCodecSpecific2;
    private final long mCodecSpecific3;
    private final long mCodecSpecific4;
    private final int mCodecType;
    private final int mSampleRate;

    @UnsupportedAppUsage
    public BluetoothCodecConfig(int n) {
        this.mCodecType = n;
        this.mCodecPriority = 0;
        this.mSampleRate = 0;
        this.mBitsPerSample = 0;
        this.mChannelMode = 0;
        this.mCodecSpecific1 = 0L;
        this.mCodecSpecific2 = 0L;
        this.mCodecSpecific3 = 0L;
        this.mCodecSpecific4 = 0L;
    }

    @UnsupportedAppUsage
    public BluetoothCodecConfig(int n, int n2, int n3, int n4, int n5, long l, long l2, long l3, long l4) {
        this.mCodecType = n;
        this.mCodecPriority = n2;
        this.mSampleRate = n3;
        this.mBitsPerSample = n4;
        this.mChannelMode = n5;
        this.mCodecSpecific1 = l;
        this.mCodecSpecific2 = l2;
        this.mCodecSpecific3 = l3;
        this.mCodecSpecific4 = l4;
    }

    private static String appendCapabilityToString(String string2, String string3) {
        if (string2 == null) {
            return string3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("|");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof BluetoothCodecConfig;
        boolean bl2 = false;
        if (bl) {
            object = (BluetoothCodecConfig)object;
            bl = bl2;
            if (((BluetoothCodecConfig)object).mCodecType == this.mCodecType) {
                bl = bl2;
                if (((BluetoothCodecConfig)object).mCodecPriority == this.mCodecPriority) {
                    bl = bl2;
                    if (((BluetoothCodecConfig)object).mSampleRate == this.mSampleRate) {
                        bl = bl2;
                        if (((BluetoothCodecConfig)object).mBitsPerSample == this.mBitsPerSample) {
                            bl = bl2;
                            if (((BluetoothCodecConfig)object).mChannelMode == this.mChannelMode) {
                                bl = bl2;
                                if (((BluetoothCodecConfig)object).mCodecSpecific1 == this.mCodecSpecific1) {
                                    bl = bl2;
                                    if (((BluetoothCodecConfig)object).mCodecSpecific2 == this.mCodecSpecific2) {
                                        bl = bl2;
                                        if (((BluetoothCodecConfig)object).mCodecSpecific3 == this.mCodecSpecific3) {
                                            bl = bl2;
                                            if (((BluetoothCodecConfig)object).mCodecSpecific4 == this.mCodecSpecific4) {
                                                bl = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    public int getBitsPerSample() {
        return this.mBitsPerSample;
    }

    @UnsupportedAppUsage
    public int getChannelMode() {
        return this.mChannelMode;
    }

    public String getCodecName() {
        int n = this.mCodecType;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1000000) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("UNKNOWN CODEC(");
                                stringBuilder.append(this.mCodecType);
                                stringBuilder.append(")");
                                return stringBuilder.toString();
                            }
                            return "INVALID CODEC";
                        }
                        return "LDAC";
                    }
                    return "aptX HD";
                }
                return "aptX";
            }
            return "AAC";
        }
        return "SBC";
    }

    @UnsupportedAppUsage
    public int getCodecPriority() {
        return this.mCodecPriority;
    }

    @UnsupportedAppUsage
    public long getCodecSpecific1() {
        return this.mCodecSpecific1;
    }

    @UnsupportedAppUsage
    public long getCodecSpecific2() {
        return this.mCodecSpecific2;
    }

    @UnsupportedAppUsage
    public long getCodecSpecific3() {
        return this.mCodecSpecific3;
    }

    @UnsupportedAppUsage
    public long getCodecSpecific4() {
        return this.mCodecSpecific4;
    }

    @UnsupportedAppUsage
    public int getCodecType() {
        return this.mCodecType;
    }

    @UnsupportedAppUsage
    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int hashCode() {
        return Objects.hash(this.mCodecType, this.mCodecPriority, this.mSampleRate, this.mBitsPerSample, this.mChannelMode, this.mCodecSpecific1, this.mCodecSpecific2, this.mCodecSpecific3, this.mCodecSpecific4);
    }

    public boolean isMandatoryCodec() {
        boolean bl = this.mCodecType == 0;
        return bl;
    }

    public boolean isValid() {
        boolean bl = this.mSampleRate != 0 && this.mBitsPerSample != 0 && this.mChannelMode != 0;
        return bl;
    }

    public boolean sameAudioFeedingParameters(BluetoothCodecConfig bluetoothCodecConfig) {
        boolean bl = bluetoothCodecConfig != null && bluetoothCodecConfig.mSampleRate == this.mSampleRate && bluetoothCodecConfig.mBitsPerSample == this.mBitsPerSample && bluetoothCodecConfig.mChannelMode == this.mChannelMode;
        return bl;
    }

    @UnsupportedAppUsage
    public void setCodecPriority(int n) {
        this.mCodecPriority = n;
    }

    public String toString() {
        CharSequence charSequence = null;
        if (this.mSampleRate == 0) {
            charSequence = BluetoothCodecConfig.appendCapabilityToString(null, "NONE");
        }
        String string2 = charSequence;
        if ((this.mSampleRate & 1) != 0) {
            string2 = BluetoothCodecConfig.appendCapabilityToString((String)charSequence, "44100");
        }
        charSequence = string2;
        if ((this.mSampleRate & 2) != 0) {
            charSequence = BluetoothCodecConfig.appendCapabilityToString(string2, "48000");
        }
        string2 = charSequence;
        if ((this.mSampleRate & 4) != 0) {
            string2 = BluetoothCodecConfig.appendCapabilityToString((String)charSequence, "88200");
        }
        String string3 = string2;
        if ((this.mSampleRate & 8) != 0) {
            string3 = BluetoothCodecConfig.appendCapabilityToString(string2, "96000");
        }
        charSequence = string3;
        if ((this.mSampleRate & 16) != 0) {
            charSequence = BluetoothCodecConfig.appendCapabilityToString(string3, "176400");
        }
        string3 = charSequence;
        if ((this.mSampleRate & 32) != 0) {
            string3 = BluetoothCodecConfig.appendCapabilityToString((String)charSequence, "192000");
        }
        string2 = null;
        if (this.mBitsPerSample == 0) {
            string2 = BluetoothCodecConfig.appendCapabilityToString(null, "NONE");
        }
        charSequence = string2;
        if ((this.mBitsPerSample & 1) != 0) {
            charSequence = BluetoothCodecConfig.appendCapabilityToString(string2, "16");
        }
        string2 = charSequence;
        if ((this.mBitsPerSample & 2) != 0) {
            string2 = BluetoothCodecConfig.appendCapabilityToString((String)charSequence, "24");
        }
        String string4 = string2;
        if ((this.mBitsPerSample & 4) != 0) {
            string4 = BluetoothCodecConfig.appendCapabilityToString(string2, "32");
        }
        string2 = null;
        if (this.mChannelMode == 0) {
            string2 = BluetoothCodecConfig.appendCapabilityToString(null, "NONE");
        }
        charSequence = string2;
        if ((this.mChannelMode & 1) != 0) {
            charSequence = BluetoothCodecConfig.appendCapabilityToString(string2, "MONO");
        }
        string2 = charSequence;
        if ((this.mChannelMode & 2) != 0) {
            string2 = BluetoothCodecConfig.appendCapabilityToString((String)charSequence, "STEREO");
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("{codecName:");
        ((StringBuilder)charSequence).append(this.getCodecName());
        ((StringBuilder)charSequence).append(",mCodecType:");
        ((StringBuilder)charSequence).append(this.mCodecType);
        ((StringBuilder)charSequence).append(",mCodecPriority:");
        ((StringBuilder)charSequence).append(this.mCodecPriority);
        ((StringBuilder)charSequence).append(",mSampleRate:");
        ((StringBuilder)charSequence).append(String.format("0x%x", this.mSampleRate));
        ((StringBuilder)charSequence).append("(");
        ((StringBuilder)charSequence).append(string3);
        ((StringBuilder)charSequence).append("),mBitsPerSample:");
        ((StringBuilder)charSequence).append(String.format("0x%x", this.mBitsPerSample));
        ((StringBuilder)charSequence).append("(");
        ((StringBuilder)charSequence).append(string4);
        ((StringBuilder)charSequence).append("),mChannelMode:");
        ((StringBuilder)charSequence).append(String.format("0x%x", this.mChannelMode));
        ((StringBuilder)charSequence).append("(");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("),mCodecSpecific1:");
        ((StringBuilder)charSequence).append(this.mCodecSpecific1);
        ((StringBuilder)charSequence).append(",mCodecSpecific2:");
        ((StringBuilder)charSequence).append(this.mCodecSpecific2);
        ((StringBuilder)charSequence).append(",mCodecSpecific3:");
        ((StringBuilder)charSequence).append(this.mCodecSpecific3);
        ((StringBuilder)charSequence).append(",mCodecSpecific4:");
        ((StringBuilder)charSequence).append(this.mCodecSpecific4);
        ((StringBuilder)charSequence).append("}");
        return ((StringBuilder)charSequence).toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCodecType);
        parcel.writeInt(this.mCodecPriority);
        parcel.writeInt(this.mSampleRate);
        parcel.writeInt(this.mBitsPerSample);
        parcel.writeInt(this.mChannelMode);
        parcel.writeLong(this.mCodecSpecific1);
        parcel.writeLong(this.mCodecSpecific2);
        parcel.writeLong(this.mCodecSpecific3);
        parcel.writeLong(this.mCodecSpecific4);
    }

}


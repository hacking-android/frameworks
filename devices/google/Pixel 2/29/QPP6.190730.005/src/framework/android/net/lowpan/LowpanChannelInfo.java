/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class LowpanChannelInfo
implements Parcelable {
    public static final Parcelable.Creator<LowpanChannelInfo> CREATOR = new Parcelable.Creator<LowpanChannelInfo>(){

        @Override
        public LowpanChannelInfo createFromParcel(Parcel parcel) {
            LowpanChannelInfo lowpanChannelInfo = new LowpanChannelInfo();
            lowpanChannelInfo.mIndex = parcel.readInt();
            lowpanChannelInfo.mName = parcel.readString();
            lowpanChannelInfo.mSpectrumCenterFrequency = parcel.readFloat();
            lowpanChannelInfo.mSpectrumBandwidth = parcel.readFloat();
            lowpanChannelInfo.mMaxTransmitPower = parcel.readInt();
            lowpanChannelInfo.mIsMaskedByRegulatoryDomain = parcel.readBoolean();
            return lowpanChannelInfo;
        }

        public LowpanChannelInfo[] newArray(int n) {
            return new LowpanChannelInfo[n];
        }
    };
    public static final float UNKNOWN_BANDWIDTH = 0.0f;
    public static final float UNKNOWN_FREQUENCY = 0.0f;
    public static final int UNKNOWN_POWER = Integer.MAX_VALUE;
    private int mIndex = 0;
    private boolean mIsMaskedByRegulatoryDomain = false;
    private int mMaxTransmitPower = Integer.MAX_VALUE;
    private String mName = null;
    private float mSpectrumBandwidth = 0.0f;
    private float mSpectrumCenterFrequency = 0.0f;

    private LowpanChannelInfo() {
    }

    private LowpanChannelInfo(int n, String string2, float f, float f2) {
        this.mIndex = n;
        this.mName = string2;
        this.mSpectrumCenterFrequency = f;
        this.mSpectrumBandwidth = f2;
    }

    public static LowpanChannelInfo getChannelInfoForIeee802154Page0(int n) {
        LowpanChannelInfo lowpanChannelInfo = new LowpanChannelInfo();
        if (n < 0) {
            lowpanChannelInfo = null;
        } else if (n == 0) {
            lowpanChannelInfo.mSpectrumCenterFrequency = 8.6830003E8f;
            lowpanChannelInfo.mSpectrumBandwidth = 600000.0f;
        } else if (n < 11) {
            lowpanChannelInfo.mSpectrumCenterFrequency = (float)n * 2000000.0f + 9.04E8f;
            lowpanChannelInfo.mSpectrumBandwidth = 0.0f;
        } else if (n < 26) {
            lowpanChannelInfo.mSpectrumCenterFrequency = (float)n * 5000000.0f + 2.34999987E9f;
            lowpanChannelInfo.mSpectrumBandwidth = 2000000.0f;
        } else {
            lowpanChannelInfo = null;
        }
        lowpanChannelInfo.mName = Integer.toString(n);
        return lowpanChannelInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof LowpanChannelInfo;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (LowpanChannelInfo)object;
        bl = bl2;
        if (Objects.equals(this.mName, ((LowpanChannelInfo)object).mName)) {
            bl = bl2;
            if (this.mIndex == ((LowpanChannelInfo)object).mIndex) {
                bl = bl2;
                if (this.mIsMaskedByRegulatoryDomain == ((LowpanChannelInfo)object).mIsMaskedByRegulatoryDomain) {
                    bl = bl2;
                    if (this.mSpectrumCenterFrequency == ((LowpanChannelInfo)object).mSpectrumCenterFrequency) {
                        bl = bl2;
                        if (this.mSpectrumBandwidth == ((LowpanChannelInfo)object).mSpectrumBandwidth) {
                            bl = bl2;
                            if (this.mMaxTransmitPower == ((LowpanChannelInfo)object).mMaxTransmitPower) {
                                bl = true;
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public int getMaxTransmitPower() {
        return this.mMaxTransmitPower;
    }

    public String getName() {
        return this.mName;
    }

    public float getSpectrumBandwidth() {
        return this.mSpectrumBandwidth;
    }

    public float getSpectrumCenterFrequency() {
        return this.mSpectrumCenterFrequency;
    }

    public int hashCode() {
        return Objects.hash(this.mName, this.mIndex, this.mIsMaskedByRegulatoryDomain, Float.valueOf(this.mSpectrumCenterFrequency), Float.valueOf(this.mSpectrumBandwidth), this.mMaxTransmitPower);
    }

    public boolean isMaskedByRegulatoryDomain() {
        return this.mIsMaskedByRegulatoryDomain;
    }

    public String toString() {
        float f;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Channel ");
        stringBuffer.append(this.mIndex);
        String string2 = this.mName;
        if (string2 != null && !string2.equals(Integer.toString(this.mIndex))) {
            stringBuffer.append(" (");
            stringBuffer.append(this.mName);
            stringBuffer.append(")");
        }
        if ((f = this.mSpectrumCenterFrequency) > 0.0f) {
            if (f > 1.0E9f) {
                stringBuffer.append(", SpectrumCenterFrequency: ");
                stringBuffer.append(this.mSpectrumCenterFrequency / 1.0E9f);
                stringBuffer.append("GHz");
            } else if (f > 1000000.0f) {
                stringBuffer.append(", SpectrumCenterFrequency: ");
                stringBuffer.append(this.mSpectrumCenterFrequency / 1000000.0f);
                stringBuffer.append("MHz");
            } else {
                stringBuffer.append(", SpectrumCenterFrequency: ");
                stringBuffer.append(this.mSpectrumCenterFrequency / 1000.0f);
                stringBuffer.append("kHz");
            }
        }
        if ((f = this.mSpectrumBandwidth) > 0.0f) {
            if (f > 1.0E9f) {
                stringBuffer.append(", SpectrumBandwidth: ");
                stringBuffer.append(this.mSpectrumBandwidth / 1.0E9f);
                stringBuffer.append("GHz");
            } else if (f > 1000000.0f) {
                stringBuffer.append(", SpectrumBandwidth: ");
                stringBuffer.append(this.mSpectrumBandwidth / 1000000.0f);
                stringBuffer.append("MHz");
            } else {
                stringBuffer.append(", SpectrumBandwidth: ");
                stringBuffer.append(this.mSpectrumBandwidth / 1000.0f);
                stringBuffer.append("kHz");
            }
        }
        if (this.mMaxTransmitPower != Integer.MAX_VALUE) {
            stringBuffer.append(", MaxTransmitPower: ");
            stringBuffer.append(this.mMaxTransmitPower);
            stringBuffer.append("dBm");
        }
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mIndex);
        parcel.writeString(this.mName);
        parcel.writeFloat(this.mSpectrumCenterFrequency);
        parcel.writeFloat(this.mSpectrumBandwidth);
        parcel.writeInt(this.mMaxTransmitPower);
        parcel.writeBoolean(this.mIsMaskedByRegulatoryDomain);
    }

}


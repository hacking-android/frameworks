/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class TvInputHardwareInfo
implements Parcelable {
    public static final int CABLE_CONNECTION_STATUS_CONNECTED = 1;
    public static final int CABLE_CONNECTION_STATUS_DISCONNECTED = 2;
    public static final int CABLE_CONNECTION_STATUS_UNKNOWN = 0;
    public static final Parcelable.Creator<TvInputHardwareInfo> CREATOR = new Parcelable.Creator<TvInputHardwareInfo>(){

        @Override
        public TvInputHardwareInfo createFromParcel(Parcel parcel) {
            try {
                TvInputHardwareInfo tvInputHardwareInfo = new TvInputHardwareInfo();
                tvInputHardwareInfo.readFromParcel(parcel);
                return tvInputHardwareInfo;
            }
            catch (Exception exception) {
                Log.e(TvInputHardwareInfo.TAG, "Exception creating TvInputHardwareInfo from parcel", exception);
                return null;
            }
        }

        public TvInputHardwareInfo[] newArray(int n) {
            return new TvInputHardwareInfo[n];
        }
    };
    static final String TAG = "TvInputHardwareInfo";
    public static final int TV_INPUT_TYPE_COMPONENT = 6;
    public static final int TV_INPUT_TYPE_COMPOSITE = 3;
    public static final int TV_INPUT_TYPE_DISPLAY_PORT = 10;
    public static final int TV_INPUT_TYPE_DVI = 8;
    public static final int TV_INPUT_TYPE_HDMI = 9;
    public static final int TV_INPUT_TYPE_OTHER_HARDWARE = 1;
    public static final int TV_INPUT_TYPE_SCART = 5;
    public static final int TV_INPUT_TYPE_SVIDEO = 4;
    public static final int TV_INPUT_TYPE_TUNER = 2;
    public static final int TV_INPUT_TYPE_VGA = 7;
    private String mAudioAddress;
    private int mAudioType;
    private int mCableConnectionStatus;
    private int mDeviceId;
    private int mHdmiPortId;
    private int mType;

    private TvInputHardwareInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAudioAddress() {
        return this.mAudioAddress;
    }

    public int getAudioType() {
        return this.mAudioType;
    }

    public int getCableConnectionStatus() {
        return this.mCableConnectionStatus;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public int getHdmiPortId() {
        if (this.mType == 9) {
            return this.mHdmiPortId;
        }
        throw new IllegalStateException();
    }

    public int getType() {
        return this.mType;
    }

    public void readFromParcel(Parcel parcel) {
        this.mDeviceId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mAudioType = parcel.readInt();
        this.mAudioAddress = parcel.readString();
        if (this.mType == 9) {
            this.mHdmiPortId = parcel.readInt();
        }
        this.mCableConnectionStatus = parcel.readInt();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("TvInputHardwareInfo {id=");
        stringBuilder.append(this.mDeviceId);
        stringBuilder.append(", type=");
        stringBuilder.append(this.mType);
        stringBuilder.append(", audio_type=");
        stringBuilder.append(this.mAudioType);
        stringBuilder.append(", audio_addr=");
        stringBuilder.append(this.mAudioAddress);
        if (this.mType == 9) {
            stringBuilder.append(", hdmi_port=");
            stringBuilder.append(this.mHdmiPortId);
        }
        stringBuilder.append(", cable_connection_status=");
        stringBuilder.append(this.mCableConnectionStatus);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mDeviceId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mAudioType);
        parcel.writeString(this.mAudioAddress);
        if (this.mType == 9) {
            parcel.writeInt(this.mHdmiPortId);
        }
        parcel.writeInt(this.mCableConnectionStatus);
    }

    public static final class Builder {
        private String mAudioAddress = "";
        private int mAudioType = 0;
        private Integer mCableConnectionStatus = 0;
        private Integer mDeviceId = null;
        private Integer mHdmiPortId = null;
        private Integer mType = null;

        public Builder audioAddress(String string2) {
            this.mAudioAddress = string2;
            return this;
        }

        public Builder audioType(int n) {
            this.mAudioType = n;
            return this;
        }

        public TvInputHardwareInfo build() {
            Object object;
            if (this.mDeviceId != null && (object = this.mType) != null) {
                Integer n;
                if ((Integer)object == 9 && this.mHdmiPortId == null || this.mType != 9 && this.mHdmiPortId != null) {
                    throw new UnsupportedOperationException();
                }
                object = new TvInputHardwareInfo();
                ((TvInputHardwareInfo)object).mDeviceId = this.mDeviceId;
                ((TvInputHardwareInfo)object).mType = this.mType;
                ((TvInputHardwareInfo)object).mAudioType = this.mAudioType;
                if (((TvInputHardwareInfo)object).mAudioType != 0) {
                    ((TvInputHardwareInfo)object).mAudioAddress = this.mAudioAddress;
                }
                if ((n = this.mHdmiPortId) != null) {
                    ((TvInputHardwareInfo)object).mHdmiPortId = n;
                }
                ((TvInputHardwareInfo)object).mCableConnectionStatus = this.mCableConnectionStatus;
                return object;
            }
            throw new UnsupportedOperationException();
        }

        public Builder cableConnectionStatus(int n) {
            this.mCableConnectionStatus = n;
            return this;
        }

        public Builder deviceId(int n) {
            this.mDeviceId = n;
            return this;
        }

        public Builder hdmiPortId(int n) {
            this.mHdmiPortId = n;
            return this;
        }

        public Builder type(int n) {
            this.mType = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CableConnectionStatus {
    }

}


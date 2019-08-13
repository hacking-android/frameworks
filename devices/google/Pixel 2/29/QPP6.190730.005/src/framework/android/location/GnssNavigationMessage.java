/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;

public final class GnssNavigationMessage
implements Parcelable {
    public static final Parcelable.Creator<GnssNavigationMessage> CREATOR;
    private static final byte[] EMPTY_ARRAY;
    public static final int STATUS_PARITY_PASSED = 1;
    public static final int STATUS_PARITY_REBUILT = 2;
    public static final int STATUS_UNKNOWN = 0;
    public static final int TYPE_BDS_D1 = 1281;
    public static final int TYPE_BDS_D2 = 1282;
    public static final int TYPE_GAL_F = 1538;
    public static final int TYPE_GAL_I = 1537;
    public static final int TYPE_GLO_L1CA = 769;
    public static final int TYPE_GPS_CNAV2 = 260;
    public static final int TYPE_GPS_L1CA = 257;
    public static final int TYPE_GPS_L2CNAV = 258;
    public static final int TYPE_GPS_L5CNAV = 259;
    public static final int TYPE_UNKNOWN = 0;
    private byte[] mData;
    private int mMessageId;
    private int mStatus;
    private int mSubmessageId;
    private int mSvid;
    private int mType;

    static {
        EMPTY_ARRAY = new byte[0];
        CREATOR = new Parcelable.Creator<GnssNavigationMessage>(){

            @Override
            public GnssNavigationMessage createFromParcel(Parcel parcel) {
                GnssNavigationMessage gnssNavigationMessage = new GnssNavigationMessage();
                gnssNavigationMessage.setType(parcel.readInt());
                gnssNavigationMessage.setSvid(parcel.readInt());
                gnssNavigationMessage.setMessageId(parcel.readInt());
                gnssNavigationMessage.setSubmessageId(parcel.readInt());
                byte[] arrby = new byte[parcel.readInt()];
                parcel.readByteArray(arrby);
                gnssNavigationMessage.setData(arrby);
                gnssNavigationMessage.setStatus(parcel.readInt());
                return gnssNavigationMessage;
            }

            public GnssNavigationMessage[] newArray(int n) {
                return new GnssNavigationMessage[n];
            }
        };
    }

    public GnssNavigationMessage() {
        this.initialize();
    }

    private String getStatusString() {
        int n = this.mStatus;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("<Invalid:");
                    stringBuilder.append(this.mStatus);
                    stringBuilder.append(">");
                    return stringBuilder.toString();
                }
                return "ParityRebuilt";
            }
            return "ParityPassed";
        }
        return "Unknown";
    }

    private String getTypeString() {
        int n = this.mType;
        if (n != 0) {
            if (n != 769) {
                if (n != 1281) {
                    if (n != 1282) {
                        if (n != 1537) {
                            if (n != 1538) {
                                switch (n) {
                                    default: {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("<Invalid:");
                                        stringBuilder.append(this.mType);
                                        stringBuilder.append(">");
                                        return stringBuilder.toString();
                                    }
                                    case 260: {
                                        return "GPS CNAV2";
                                    }
                                    case 259: {
                                        return "GPS L5-CNAV";
                                    }
                                    case 258: {
                                        return "GPS L2-CNAV";
                                    }
                                    case 257: 
                                }
                                return "GPS L1 C/A";
                            }
                            return "Galileo F";
                        }
                        return "Galileo I";
                    }
                    return "Beidou D2";
                }
                return "Beidou D1";
            }
            return "Glonass L1 C/A";
        }
        return "Unknown";
    }

    private void initialize() {
        this.mType = 0;
        this.mSvid = 0;
        this.mMessageId = -1;
        this.mSubmessageId = -1;
        this.mData = EMPTY_ARRAY;
        this.mStatus = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getData() {
        return this.mData;
    }

    public int getMessageId() {
        return this.mMessageId;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getSubmessageId() {
        return this.mSubmessageId;
    }

    public int getSvid() {
        return this.mSvid;
    }

    public int getType() {
        return this.mType;
    }

    public void reset() {
        this.initialize();
    }

    public void set(GnssNavigationMessage gnssNavigationMessage) {
        this.mType = gnssNavigationMessage.mType;
        this.mSvid = gnssNavigationMessage.mSvid;
        this.mMessageId = gnssNavigationMessage.mMessageId;
        this.mSubmessageId = gnssNavigationMessage.mSubmessageId;
        this.mData = gnssNavigationMessage.mData;
        this.mStatus = gnssNavigationMessage.mStatus;
    }

    public void setData(byte[] arrby) {
        if (arrby != null) {
            this.mData = arrby;
            return;
        }
        throw new InvalidParameterException("Data must be a non-null array");
    }

    public void setMessageId(int n) {
        this.mMessageId = n;
    }

    public void setStatus(int n) {
        this.mStatus = n;
    }

    public void setSubmessageId(int n) {
        this.mSubmessageId = n;
    }

    public void setSvid(int n) {
        this.mSvid = n;
    }

    public void setType(int n) {
        this.mType = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GnssNavigationMessage:\n");
        stringBuilder.append(String.format("   %-15s = %s\n", "Type", this.getTypeString()));
        stringBuilder.append(String.format("   %-15s = %s\n", "Svid", this.mSvid));
        stringBuilder.append(String.format("   %-15s = %s\n", "Status", this.getStatusString()));
        stringBuilder.append(String.format("   %-15s = %s\n", "MessageId", this.mMessageId));
        stringBuilder.append(String.format("   %-15s = %s\n", "SubmessageId", this.mSubmessageId));
        stringBuilder.append(String.format("   %-15s = %s\n", "Data", "{"));
        String string2 = "        ";
        for (byte by : this.mData) {
            stringBuilder.append(string2);
            stringBuilder.append(by);
            string2 = ", ";
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mSvid);
        parcel.writeInt(this.mMessageId);
        parcel.writeInt(this.mSubmessageId);
        parcel.writeInt(this.mData.length);
        parcel.writeByteArray(this.mData);
        parcel.writeInt(this.mStatus);
    }

    public static abstract class Callback {
        public static final int STATUS_LOCATION_DISABLED = 2;
        public static final int STATUS_NOT_SUPPORTED = 0;
        public static final int STATUS_READY = 1;

        public void onGnssNavigationMessageReceived(GnssNavigationMessage gnssNavigationMessage) {
        }

        public void onStatusChanged(int n) {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface GnssNavigationMessageStatus {
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GnssNavigationMessageType {
    }

}


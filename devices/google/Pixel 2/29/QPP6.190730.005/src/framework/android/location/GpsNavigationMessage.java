/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.security.InvalidParameterException;

@SystemApi
public class GpsNavigationMessage
implements Parcelable {
    public static final Parcelable.Creator<GpsNavigationMessage> CREATOR;
    private static final byte[] EMPTY_ARRAY;
    public static final short STATUS_PARITY_PASSED = 1;
    public static final short STATUS_PARITY_REBUILT = 2;
    public static final short STATUS_UNKNOWN = 0;
    public static final byte TYPE_CNAV2 = 4;
    public static final byte TYPE_L1CA = 1;
    public static final byte TYPE_L2CNAV = 2;
    public static final byte TYPE_L5CNAV = 3;
    public static final byte TYPE_UNKNOWN = 0;
    private byte[] mData;
    private short mMessageId;
    private byte mPrn;
    private short mStatus;
    private short mSubmessageId;
    private byte mType;

    static {
        EMPTY_ARRAY = new byte[0];
        CREATOR = new Parcelable.Creator<GpsNavigationMessage>(){

            @Override
            public GpsNavigationMessage createFromParcel(Parcel parcel) {
                GpsNavigationMessage gpsNavigationMessage = new GpsNavigationMessage();
                gpsNavigationMessage.setType(parcel.readByte());
                gpsNavigationMessage.setPrn(parcel.readByte());
                gpsNavigationMessage.setMessageId((short)parcel.readInt());
                gpsNavigationMessage.setSubmessageId((short)parcel.readInt());
                byte[] arrby = new byte[parcel.readInt()];
                parcel.readByteArray(arrby);
                gpsNavigationMessage.setData(arrby);
                if (parcel.dataAvail() >= 32) {
                    gpsNavigationMessage.setStatus((short)parcel.readInt());
                } else {
                    gpsNavigationMessage.setStatus((short)0);
                }
                return gpsNavigationMessage;
            }

            public GpsNavigationMessage[] newArray(int n) {
                return new GpsNavigationMessage[n];
            }
        };
    }

    GpsNavigationMessage() {
        this.initialize();
    }

    private String getStatusString() {
        short s = this.mStatus;
        if (s != 0) {
            if (s != 1) {
                if (s != 2) {
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
        byte by = this.mType;
        if (by != 0) {
            if (by != 1) {
                if (by != 2) {
                    if (by != 3) {
                        if (by != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("<Invalid:");
                            stringBuilder.append(this.mType);
                            stringBuilder.append(">");
                            return stringBuilder.toString();
                        }
                        return "CNAV-2";
                    }
                    return "L5-CNAV";
                }
                return "L2-CNAV";
            }
            return "L1 C/A";
        }
        return "Unknown";
    }

    private void initialize() {
        this.mType = (byte)(false ? 1 : 0);
        this.mPrn = (byte)(false ? 1 : 0);
        this.mMessageId = (short)-1;
        this.mSubmessageId = (short)-1;
        this.mData = EMPTY_ARRAY;
        this.mStatus = (short)(false ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getData() {
        return this.mData;
    }

    public short getMessageId() {
        return this.mMessageId;
    }

    public byte getPrn() {
        return this.mPrn;
    }

    public short getStatus() {
        return this.mStatus;
    }

    public short getSubmessageId() {
        return this.mSubmessageId;
    }

    public byte getType() {
        return this.mType;
    }

    public void reset() {
        this.initialize();
    }

    public void set(GpsNavigationMessage gpsNavigationMessage) {
        this.mType = gpsNavigationMessage.mType;
        this.mPrn = gpsNavigationMessage.mPrn;
        this.mMessageId = gpsNavigationMessage.mMessageId;
        this.mSubmessageId = gpsNavigationMessage.mSubmessageId;
        this.mData = gpsNavigationMessage.mData;
        this.mStatus = gpsNavigationMessage.mStatus;
    }

    public void setData(byte[] arrby) {
        if (arrby != null) {
            this.mData = arrby;
            return;
        }
        throw new InvalidParameterException("Data must be a non-null array");
    }

    public void setMessageId(short s) {
        this.mMessageId = s;
    }

    public void setPrn(byte by) {
        this.mPrn = by;
    }

    public void setStatus(short s) {
        this.mStatus = s;
    }

    public void setSubmessageId(short s) {
        this.mSubmessageId = s;
    }

    public void setType(byte by) {
        this.mType = by;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GpsNavigationMessage:\n");
        stringBuilder.append(String.format("   %-15s = %s\n", "Type", this.getTypeString()));
        stringBuilder.append(String.format("   %-15s = %s\n", "Prn", this.mPrn));
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
        parcel.writeByte(this.mType);
        parcel.writeByte(this.mPrn);
        parcel.writeInt(this.mMessageId);
        parcel.writeInt(this.mSubmessageId);
        parcel.writeInt(this.mData.length);
        parcel.writeByteArray(this.mData);
        parcel.writeInt(this.mStatus);
    }

}


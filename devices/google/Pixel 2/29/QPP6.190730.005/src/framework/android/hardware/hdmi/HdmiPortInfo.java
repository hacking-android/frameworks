/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class HdmiPortInfo
implements Parcelable {
    public static final Parcelable.Creator<HdmiPortInfo> CREATOR = new Parcelable.Creator<HdmiPortInfo>(){

        @Override
        public HdmiPortInfo createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            boolean bl = parcel.readInt() == 1;
            boolean bl2 = parcel.readInt() == 1;
            boolean bl3 = parcel.readInt() == 1;
            return new HdmiPortInfo(n, n2, n3, bl, bl3, bl2);
        }

        public HdmiPortInfo[] newArray(int n) {
            return new HdmiPortInfo[n];
        }
    };
    public static final int PORT_INPUT = 0;
    public static final int PORT_OUTPUT = 1;
    private final int mAddress;
    private final boolean mArcSupported;
    private final boolean mCecSupported;
    private final int mId;
    private final boolean mMhlSupported;
    private final int mType;

    public HdmiPortInfo(int n, int n2, int n3, boolean bl, boolean bl2, boolean bl3) {
        this.mId = n;
        this.mType = n2;
        this.mAddress = n3;
        this.mCecSupported = bl;
        this.mArcSupported = bl3;
        this.mMhlSupported = bl2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof HdmiPortInfo;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (HdmiPortInfo)object;
        bl = bl2;
        if (this.mId == ((HdmiPortInfo)object).mId) {
            bl = bl2;
            if (this.mType == ((HdmiPortInfo)object).mType) {
                bl = bl2;
                if (this.mAddress == ((HdmiPortInfo)object).mAddress) {
                    bl = bl2;
                    if (this.mCecSupported == ((HdmiPortInfo)object).mCecSupported) {
                        bl = bl2;
                        if (this.mArcSupported == ((HdmiPortInfo)object).mArcSupported) {
                            bl = bl2;
                            if (this.mMhlSupported == ((HdmiPortInfo)object).mMhlSupported) {
                                bl = true;
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }

    public int getAddress() {
        return this.mAddress;
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public boolean isArcSupported() {
        return this.mArcSupported;
    }

    public boolean isCecSupported() {
        return this.mCecSupported;
    }

    public boolean isMhlSupported() {
        return this.mMhlSupported;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("port_id: ");
        stringBuffer.append(this.mId);
        stringBuffer.append(", ");
        stringBuffer.append("address: ");
        stringBuffer.append(String.format("0x%04x", this.mAddress));
        stringBuffer.append(", ");
        stringBuffer.append("cec: ");
        stringBuffer.append(this.mCecSupported);
        stringBuffer.append(", ");
        stringBuffer.append("arc: ");
        stringBuffer.append(this.mArcSupported);
        stringBuffer.append(", ");
        stringBuffer.append("mhl: ");
        stringBuffer.append(this.mMhlSupported);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mAddress);
        parcel.writeInt((int)this.mCecSupported);
        parcel.writeInt((int)this.mArcSupported);
        parcel.writeInt((int)this.mMhlSupported);
    }

}


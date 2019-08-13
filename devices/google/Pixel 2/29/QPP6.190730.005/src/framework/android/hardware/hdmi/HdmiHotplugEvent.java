/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class HdmiHotplugEvent
implements Parcelable {
    public static final Parcelable.Creator<HdmiHotplugEvent> CREATOR = new Parcelable.Creator<HdmiHotplugEvent>(){

        @Override
        public HdmiHotplugEvent createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            byte by = parcel.readByte();
            boolean bl = true;
            if (by != 1) {
                bl = false;
            }
            return new HdmiHotplugEvent(n, bl);
        }

        public HdmiHotplugEvent[] newArray(int n) {
            return new HdmiHotplugEvent[n];
        }
    };
    private final boolean mConnected;
    private final int mPort;

    public HdmiHotplugEvent(int n, boolean bl) {
        this.mPort = n;
        this.mConnected = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getPort() {
        return this.mPort;
    }

    public boolean isConnected() {
        return this.mConnected;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mPort);
        parcel.writeByte((byte)(this.mConnected ? 1 : 0));
    }

}


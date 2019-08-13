/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

@SystemApi
public final class DhcpClientEvent
implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<DhcpClientEvent> CREATOR = new Parcelable.Creator<DhcpClientEvent>(){

        @Override
        public DhcpClientEvent createFromParcel(Parcel parcel) {
            return new DhcpClientEvent(parcel);
        }

        public DhcpClientEvent[] newArray(int n) {
            return new DhcpClientEvent[n];
        }
    };
    public final int durationMs;
    public final String msg;

    private DhcpClientEvent(Parcel parcel) {
        this.msg = parcel.readString();
        this.durationMs = parcel.readInt();
    }

    @UnsupportedAppUsage
    private DhcpClientEvent(String string2, int n) {
        this.msg = string2;
        this.durationMs = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass().equals(DhcpClientEvent.class)) {
            object = (DhcpClientEvent)object;
            boolean bl2 = bl;
            if (TextUtils.equals(this.msg, ((DhcpClientEvent)object).msg)) {
                bl2 = bl;
                if (this.durationMs == ((DhcpClientEvent)object).durationMs) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        return String.format("DhcpClientEvent(%s, %dms)", this.msg, this.durationMs);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.msg);
        parcel.writeInt(this.durationMs);
    }

    public static final class Builder {
        private int mDurationMs;
        private String mMsg;

        public DhcpClientEvent build() {
            return new DhcpClientEvent(this.mMsg, this.mDurationMs);
        }

        public Builder setDurationMs(int n) {
            this.mDurationMs = n;
            return this;
        }

        public Builder setMsg(String string2) {
            this.mMsg = string2;
            return this;
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.annotation.SystemApi;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.android.internal.util.MessageUtils;

@SystemApi
public final class IpReachabilityEvent
implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<IpReachabilityEvent> CREATOR = new Parcelable.Creator<IpReachabilityEvent>(){

        @Override
        public IpReachabilityEvent createFromParcel(Parcel parcel) {
            return new IpReachabilityEvent(parcel);
        }

        public IpReachabilityEvent[] newArray(int n) {
            return new IpReachabilityEvent[n];
        }
    };
    public static final int NUD_FAILED = 512;
    public static final int NUD_FAILED_ORGANIC = 1024;
    public static final int PROBE = 256;
    public static final int PROVISIONING_LOST = 768;
    public static final int PROVISIONING_LOST_ORGANIC = 1280;
    public final int eventType;

    public IpReachabilityEvent(int n) {
        this.eventType = n;
    }

    private IpReachabilityEvent(Parcel parcel) {
        this.eventType = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass().equals(IpReachabilityEvent.class)) {
            object = (IpReachabilityEvent)object;
            if (this.eventType == ((IpReachabilityEvent)object).eventType) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public String toString() {
        int n = this.eventType;
        return String.format("IpReachabilityEvent(%s:%02x)", Decoder.constants.get(65280 & n), n & 255);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.eventType);
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{IpReachabilityEvent.class}, new String[]{"PROBE", "PROVISIONING_", "NUD_"});

        Decoder() {
        }
    }

}


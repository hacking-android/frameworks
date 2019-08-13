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
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class NetworkEvent
implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<NetworkEvent> CREATOR = new Parcelable.Creator<NetworkEvent>(){

        @Override
        public NetworkEvent createFromParcel(Parcel parcel) {
            return new NetworkEvent(parcel);
        }

        public NetworkEvent[] newArray(int n) {
            return new NetworkEvent[n];
        }
    };
    public static final int NETWORK_CAPTIVE_PORTAL_FOUND = 4;
    public static final int NETWORK_CONNECTED = 1;
    public static final int NETWORK_CONSECUTIVE_DNS_TIMEOUT_FOUND = 12;
    public static final int NETWORK_DISCONNECTED = 7;
    public static final int NETWORK_FIRST_VALIDATION_PORTAL_FOUND = 10;
    public static final int NETWORK_FIRST_VALIDATION_SUCCESS = 8;
    public static final int NETWORK_LINGER = 5;
    public static final int NETWORK_PARTIAL_CONNECTIVITY = 13;
    public static final int NETWORK_REVALIDATION_PORTAL_FOUND = 11;
    public static final int NETWORK_REVALIDATION_SUCCESS = 9;
    public static final int NETWORK_UNLINGER = 6;
    public static final int NETWORK_VALIDATED = 2;
    public static final int NETWORK_VALIDATION_FAILED = 3;
    public final long durationMs;
    public final int eventType;

    public NetworkEvent(int n) {
        this(n, 0L);
    }

    public NetworkEvent(int n, long l) {
        this.eventType = n;
        this.durationMs = l;
    }

    private NetworkEvent(Parcel parcel) {
        this.eventType = parcel.readInt();
        this.durationMs = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass().equals(NetworkEvent.class)) {
            object = (NetworkEvent)object;
            boolean bl2 = bl;
            if (this.eventType == ((NetworkEvent)object).eventType) {
                bl2 = bl;
                if (this.durationMs == ((NetworkEvent)object).durationMs) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        return String.format("NetworkEvent(%s, %dms)", Decoder.constants.get(this.eventType), this.durationMs);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.eventType);
        parcel.writeLong(this.durationMs);
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{NetworkEvent.class}, new String[]{"NETWORK_"});

        Decoder() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EventType {
    }

}


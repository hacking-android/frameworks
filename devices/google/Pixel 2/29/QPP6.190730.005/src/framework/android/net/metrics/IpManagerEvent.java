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
public final class IpManagerEvent
implements IpConnectivityLog.Event {
    public static final int COMPLETE_LIFECYCLE = 3;
    public static final Parcelable.Creator<IpManagerEvent> CREATOR = new Parcelable.Creator<IpManagerEvent>(){

        @Override
        public IpManagerEvent createFromParcel(Parcel parcel) {
            return new IpManagerEvent(parcel);
        }

        public IpManagerEvent[] newArray(int n) {
            return new IpManagerEvent[n];
        }
    };
    public static final int ERROR_INTERFACE_NOT_FOUND = 8;
    public static final int ERROR_INVALID_PROVISIONING = 7;
    public static final int ERROR_STARTING_IPREACHABILITYMONITOR = 6;
    public static final int ERROR_STARTING_IPV4 = 4;
    public static final int ERROR_STARTING_IPV6 = 5;
    public static final int PROVISIONING_FAIL = 2;
    public static final int PROVISIONING_OK = 1;
    public final long durationMs;
    public final int eventType;

    public IpManagerEvent(int n, long l) {
        this.eventType = n;
        this.durationMs = l;
    }

    private IpManagerEvent(Parcel parcel) {
        this.eventType = parcel.readInt();
        this.durationMs = parcel.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass().equals(IpManagerEvent.class)) {
            object = (IpManagerEvent)object;
            boolean bl2 = bl;
            if (this.eventType == ((IpManagerEvent)object).eventType) {
                bl2 = bl;
                if (this.durationMs == ((IpManagerEvent)object).durationMs) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        return String.format("IpManagerEvent(%s, %dms)", Decoder.constants.get(this.eventType), this.durationMs);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.eventType);
        parcel.writeLong(this.durationMs);
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{IpManagerEvent.class}, new String[]{"PROVISIONING_", "COMPLETE_", "ERROR_"});

        Decoder() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EventType {
    }

}


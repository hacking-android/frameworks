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
public final class ValidationProbeEvent
implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<ValidationProbeEvent> CREATOR = new Parcelable.Creator<ValidationProbeEvent>(){

        @Override
        public ValidationProbeEvent createFromParcel(Parcel parcel) {
            return new ValidationProbeEvent(parcel);
        }

        public ValidationProbeEvent[] newArray(int n) {
            return new ValidationProbeEvent[n];
        }
    };
    public static final int DNS_FAILURE = 0;
    public static final int DNS_SUCCESS = 1;
    private static final int FIRST_VALIDATION = 256;
    public static final int PROBE_DNS = 0;
    public static final int PROBE_FALLBACK = 4;
    public static final int PROBE_HTTP = 1;
    public static final int PROBE_HTTPS = 2;
    public static final int PROBE_PAC = 3;
    public static final int PROBE_PRIVDNS = 5;
    private static final int REVALIDATION = 512;
    public final long durationMs;
    public final int probeType;
    public final int returnCode;

    private ValidationProbeEvent(long l, int n, int n2) {
        this.durationMs = l;
        this.probeType = n;
        this.returnCode = n2;
    }

    private ValidationProbeEvent(Parcel parcel) {
        this.durationMs = parcel.readLong();
        this.probeType = parcel.readInt();
        this.returnCode = parcel.readInt();
    }

    public static String getProbeName(int n) {
        return Decoder.constants.get(n & 255, "PROBE_???");
    }

    private static String getValidationStage(int n) {
        return Decoder.constants.get(65280 & n, "UNKNOWN");
    }

    private static int makeProbeType(int n, boolean bl) {
        int n2 = bl ? 256 : 512;
        return n & 255 | n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass().equals(ValidationProbeEvent.class)) {
            object = (ValidationProbeEvent)object;
            boolean bl2 = bl;
            if (this.durationMs == ((ValidationProbeEvent)object).durationMs) {
                bl2 = bl;
                if (this.probeType == ((ValidationProbeEvent)object).probeType) {
                    bl2 = bl;
                    if (this.returnCode == ((ValidationProbeEvent)object).returnCode) {
                        bl2 = true;
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        return String.format("ValidationProbeEvent(%s:%d %s, %dms)", ValidationProbeEvent.getProbeName(this.probeType), this.returnCode, ValidationProbeEvent.getValidationStage(this.probeType), this.durationMs);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.durationMs);
        parcel.writeInt(this.probeType);
        parcel.writeInt(this.returnCode);
    }

    public static final class Builder {
        private long mDurationMs;
        private int mProbeType;
        private int mReturnCode;

        public ValidationProbeEvent build() {
            return new ValidationProbeEvent(this.mDurationMs, this.mProbeType, this.mReturnCode);
        }

        public Builder setDurationMs(long l) {
            this.mDurationMs = l;
            return this;
        }

        public Builder setProbeType(int n, boolean bl) {
            this.mProbeType = ValidationProbeEvent.makeProbeType(n, bl);
            return this;
        }

        public Builder setReturnCode(int n) {
            this.mReturnCode = n;
            return this;
        }
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{ValidationProbeEvent.class}, new String[]{"PROBE_", "FIRST_", "REVALIDATION"});

        Decoder() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ReturnCode {
    }

}


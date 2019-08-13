/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.annotation.SystemApi;
import android.net.MacAddress;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.rtt.ResponderLocation;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

public final class RangingResult
implements Parcelable {
    public static final Parcelable.Creator<RangingResult> CREATOR;
    private static final byte[] EMPTY_BYTE_ARRAY;
    public static final int STATUS_FAIL = 1;
    public static final int STATUS_RESPONDER_DOES_NOT_SUPPORT_IEEE80211MC = 2;
    public static final int STATUS_SUCCESS = 0;
    private static final String TAG = "RangingResult";
    private final int mDistanceMm;
    private final int mDistanceStdDevMm;
    private final byte[] mLci;
    private final byte[] mLcr;
    private final MacAddress mMac;
    private final int mNumAttemptedMeasurements;
    private final int mNumSuccessfulMeasurements;
    private final PeerHandle mPeerHandle;
    private final ResponderLocation mResponderLocation;
    private final int mRssi;
    private final int mStatus;
    private final long mTimestamp;

    static {
        EMPTY_BYTE_ARRAY = new byte[0];
        CREATOR = new Parcelable.Creator<RangingResult>(){

            @Override
            public RangingResult createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                MacAddress macAddress = parcel.readBoolean() ? MacAddress.CREATOR.createFromParcel(parcel) : null;
                boolean bl = parcel.readBoolean();
                PeerHandle peerHandle = bl ? new PeerHandle(parcel.readInt()) : null;
                int n2 = parcel.readInt();
                int n3 = parcel.readInt();
                int n4 = parcel.readInt();
                int n5 = parcel.readInt();
                int n6 = parcel.readInt();
                byte[] arrby = parcel.createByteArray();
                byte[] arrby2 = parcel.createByteArray();
                ResponderLocation responderLocation = (ResponderLocation)parcel.readParcelable(this.getClass().getClassLoader());
                long l = parcel.readLong();
                if (bl) {
                    return new RangingResult(n, peerHandle, n2, n3, n4, n5, n6, arrby, arrby2, responderLocation, l);
                }
                return new RangingResult(n, macAddress, n2, n3, n4, n5, n6, arrby, arrby2, responderLocation, l);
            }

            public RangingResult[] newArray(int n) {
                return new RangingResult[n];
            }
        };
    }

    public RangingResult(int n, MacAddress macAddress, int n2, int n3, int n4, int n5, int n6, byte[] arrby, byte[] arrby2, ResponderLocation responderLocation, long l) {
        this.mStatus = n;
        this.mMac = macAddress;
        this.mPeerHandle = null;
        this.mDistanceMm = n2;
        this.mDistanceStdDevMm = n3;
        this.mRssi = n4;
        this.mNumAttemptedMeasurements = n5;
        this.mNumSuccessfulMeasurements = n6;
        if (arrby == null) {
            arrby = EMPTY_BYTE_ARRAY;
        }
        this.mLci = arrby;
        if (arrby2 == null) {
            arrby2 = EMPTY_BYTE_ARRAY;
        }
        this.mLcr = arrby2;
        this.mResponderLocation = responderLocation;
        this.mTimestamp = l;
    }

    public RangingResult(int n, PeerHandle arrby, int n2, int n3, int n4, int n5, int n6, byte[] arrby2, byte[] arrby3, ResponderLocation responderLocation, long l) {
        this.mStatus = n;
        this.mMac = null;
        this.mPeerHandle = arrby;
        this.mDistanceMm = n2;
        this.mDistanceStdDevMm = n3;
        this.mRssi = n4;
        this.mNumAttemptedMeasurements = n5;
        this.mNumSuccessfulMeasurements = n6;
        arrby = arrby2 == null ? EMPTY_BYTE_ARRAY : arrby2;
        this.mLci = arrby;
        arrby = arrby3 == null ? EMPTY_BYTE_ARRAY : arrby3;
        this.mLcr = arrby;
        this.mResponderLocation = responderLocation;
        this.mTimestamp = l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof RangingResult)) {
            return false;
        }
        object = (RangingResult)object;
        if (!(this.mStatus == ((RangingResult)object).mStatus && Objects.equals(this.mMac, ((RangingResult)object).mMac) && Objects.equals(this.mPeerHandle, ((RangingResult)object).mPeerHandle) && this.mDistanceMm == ((RangingResult)object).mDistanceMm && this.mDistanceStdDevMm == ((RangingResult)object).mDistanceStdDevMm && this.mRssi == ((RangingResult)object).mRssi && this.mNumAttemptedMeasurements == ((RangingResult)object).mNumAttemptedMeasurements && this.mNumSuccessfulMeasurements == ((RangingResult)object).mNumSuccessfulMeasurements && Arrays.equals(this.mLci, ((RangingResult)object).mLci) && Arrays.equals(this.mLcr, ((RangingResult)object).mLcr) && this.mTimestamp == ((RangingResult)object).mTimestamp && Objects.equals(this.mResponderLocation, ((RangingResult)object).mResponderLocation))) {
            bl = false;
        }
        return bl;
    }

    public int getDistanceMm() {
        if (this.mStatus == 0) {
            return this.mDistanceMm;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getDistanceMm(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getDistanceStdDevMm() {
        if (this.mStatus == 0) {
            return this.mDistanceStdDevMm;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getDistanceStdDevMm(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @SystemApi
    public byte[] getLci() {
        if (this.mStatus == 0) {
            return this.mLci;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getLci(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @SystemApi
    public byte[] getLcr() {
        if (this.mStatus == 0) {
            return this.mLcr;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getReportedLocationCivic(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public MacAddress getMacAddress() {
        return this.mMac;
    }

    public int getNumAttemptedMeasurements() {
        if (this.mStatus == 0) {
            return this.mNumAttemptedMeasurements;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getNumAttemptedMeasurements(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getNumSuccessfulMeasurements() {
        if (this.mStatus == 0) {
            return this.mNumSuccessfulMeasurements;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getNumSuccessfulMeasurements(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public PeerHandle getPeerHandle() {
        return this.mPeerHandle;
    }

    public long getRangingTimestampMillis() {
        if (this.mStatus == 0) {
            return this.mTimestamp;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getRangingTimestampMillis(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getRssi() {
        if (this.mStatus == 0) {
            return this.mRssi;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getRssi(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getStatus() {
        return this.mStatus;
    }

    public ResponderLocation getUnverifiedResponderLocation() {
        if (this.mStatus == 0) {
            return this.mResponderLocation;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getUnverifiedResponderLocation(): invoked on an invalid result: getStatus()=");
        stringBuilder.append(this.mStatus);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int hashCode() {
        return Objects.hash(this.mStatus, this.mMac, this.mPeerHandle, this.mDistanceMm, this.mDistanceStdDevMm, this.mRssi, this.mNumAttemptedMeasurements, this.mNumSuccessfulMeasurements, this.mLci, this.mLcr, this.mResponderLocation, this.mTimestamp);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("RangingResult: [status=");
        stringBuilder.append(this.mStatus);
        stringBuilder.append(", mac=");
        stringBuilder.append(this.mMac);
        stringBuilder.append(", peerHandle=");
        Object object = this.mPeerHandle;
        object = object == null ? "<null>" : Integer.valueOf(((PeerHandle)object).peerId);
        stringBuilder.append(object);
        stringBuilder.append(", distanceMm=");
        stringBuilder.append(this.mDistanceMm);
        stringBuilder.append(", distanceStdDevMm=");
        stringBuilder.append(this.mDistanceStdDevMm);
        stringBuilder.append(", rssi=");
        stringBuilder.append(this.mRssi);
        stringBuilder.append(", numAttemptedMeasurements=");
        stringBuilder.append(this.mNumAttemptedMeasurements);
        stringBuilder.append(", numSuccessfulMeasurements=");
        stringBuilder.append(this.mNumSuccessfulMeasurements);
        stringBuilder.append(", lci=");
        stringBuilder.append(this.mLci);
        stringBuilder.append(", lcr=");
        stringBuilder.append(this.mLcr);
        stringBuilder.append(", responderLocation=");
        stringBuilder.append(this.mResponderLocation);
        stringBuilder.append(", timestamp=");
        stringBuilder.append(this.mTimestamp);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mStatus);
        if (this.mMac == null) {
            parcel.writeBoolean(false);
        } else {
            parcel.writeBoolean(true);
            this.mMac.writeToParcel(parcel, n);
        }
        if (this.mPeerHandle == null) {
            parcel.writeBoolean(false);
        } else {
            parcel.writeBoolean(true);
            parcel.writeInt(this.mPeerHandle.peerId);
        }
        parcel.writeInt(this.mDistanceMm);
        parcel.writeInt(this.mDistanceStdDevMm);
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mNumAttemptedMeasurements);
        parcel.writeInt(this.mNumSuccessfulMeasurements);
        parcel.writeByteArray(this.mLci);
        parcel.writeByteArray(this.mLcr);
        parcel.writeParcelable(this.mResponderLocation, n);
        parcel.writeLong(this.mTimestamp);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RangeResultStatus {
    }

}


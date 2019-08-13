/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.annotation.SystemApi;
import android.net.MacAddress;
import android.net.wifi.ScanResult;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.rtt.ResponderConfig;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public final class RangingRequest
implements Parcelable {
    public static final Parcelable.Creator<RangingRequest> CREATOR = new Parcelable.Creator<RangingRequest>(){

        @Override
        public RangingRequest createFromParcel(Parcel parcel) {
            return new RangingRequest(parcel.readArrayList(null));
        }

        public RangingRequest[] newArray(int n) {
            return new RangingRequest[n];
        }
    };
    private static final int MAX_PEERS = 10;
    public final List<ResponderConfig> mRttPeers;

    private RangingRequest(List<ResponderConfig> list) {
        this.mRttPeers = list;
    }

    public static int getMaxPeers() {
        return 10;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void enforceValidity(boolean bl) {
        if (this.mRttPeers.size() <= 10) {
            Iterator<ResponderConfig> iterator = this.mRttPeers.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().isValid(bl)) continue;
                throw new IllegalArgumentException("Invalid Responder specification");
            }
            return;
        }
        throw new IllegalArgumentException("Ranging to too many peers requested. Use getMaxPeers() API to get limit.");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof RangingRequest)) {
            return false;
        }
        object = (RangingRequest)object;
        if (this.mRttPeers.size() != ((RangingRequest)object).mRttPeers.size() || !this.mRttPeers.containsAll(((RangingRequest)object).mRttPeers)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return this.mRttPeers.hashCode();
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "RangingRequest: mRttPeers=[", "]");
        Iterator<ResponderConfig> iterator = this.mRttPeers.iterator();
        while (iterator.hasNext()) {
            stringJoiner.add(iterator.next().toString());
        }
        return stringJoiner.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeList(this.mRttPeers);
    }

    public static final class Builder {
        private List<ResponderConfig> mRttPeers = new ArrayList<ResponderConfig>();

        public Builder addAccessPoint(ScanResult scanResult) {
            if (scanResult != null) {
                return this.addResponder(ResponderConfig.fromScanResult(scanResult));
            }
            throw new IllegalArgumentException("Null ScanResult!");
        }

        public Builder addAccessPoints(List<ScanResult> object) {
            if (object != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    this.addAccessPoint((ScanResult)object.next());
                }
                return this;
            }
            throw new IllegalArgumentException("Null list of ScanResults!");
        }

        @SystemApi
        public Builder addResponder(ResponderConfig responderConfig) {
            if (responderConfig != null) {
                this.mRttPeers.add(responderConfig);
                return this;
            }
            throw new IllegalArgumentException("Null Responder!");
        }

        public Builder addWifiAwarePeer(MacAddress macAddress) {
            if (macAddress != null) {
                return this.addResponder(ResponderConfig.fromWifiAwarePeerMacAddressWithDefaults(macAddress));
            }
            throw new IllegalArgumentException("Null peer MAC address");
        }

        public Builder addWifiAwarePeer(PeerHandle peerHandle) {
            if (peerHandle != null) {
                return this.addResponder(ResponderConfig.fromWifiAwarePeerHandleWithDefaults(peerHandle));
            }
            throw new IllegalArgumentException("Null peer handler (identifier)");
        }

        public RangingRequest build() {
            return new RangingRequest(this.mRttPeers);
        }
    }

}


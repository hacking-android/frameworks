/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.annotation.SystemApi;
import android.net.NetworkSpecifier;
import android.net.wifi.aware.DiscoverySession;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.SubscribeDiscoverySession;
import android.net.wifi.aware.WifiAwareAgentNetworkSpecifier;
import android.net.wifi.aware.WifiAwareUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.Objects;

public final class WifiAwareNetworkSpecifier
extends NetworkSpecifier
implements Parcelable {
    public static final Parcelable.Creator<WifiAwareNetworkSpecifier> CREATOR = new Parcelable.Creator<WifiAwareNetworkSpecifier>(){

        @Override
        public WifiAwareNetworkSpecifier createFromParcel(Parcel parcel) {
            return new WifiAwareNetworkSpecifier(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createByteArray(), parcel.createByteArray(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public WifiAwareNetworkSpecifier[] newArray(int n) {
            return new WifiAwareNetworkSpecifier[n];
        }
    };
    public static final int NETWORK_SPECIFIER_TYPE_IB = 0;
    public static final int NETWORK_SPECIFIER_TYPE_IB_ANY_PEER = 1;
    public static final int NETWORK_SPECIFIER_TYPE_MAX_VALID = 3;
    public static final int NETWORK_SPECIFIER_TYPE_OOB = 2;
    public static final int NETWORK_SPECIFIER_TYPE_OOB_ANY_PEER = 3;
    public final int clientId;
    public final String passphrase;
    public final int peerId;
    public final byte[] peerMac;
    public final byte[] pmk;
    public final int port;
    public final int requestorUid;
    public final int role;
    public final int sessionId;
    public final int transportProtocol;
    public final int type;

    public WifiAwareNetworkSpecifier(int n, int n2, int n3, int n4, int n5, byte[] arrby, byte[] arrby2, String string2, int n6, int n7, int n8) {
        this.type = n;
        this.role = n2;
        this.clientId = n3;
        this.sessionId = n4;
        this.peerId = n5;
        this.peerMac = arrby;
        this.pmk = arrby2;
        this.passphrase = string2;
        this.port = n6;
        this.transportProtocol = n7;
        this.requestorUid = n8;
    }

    @Override
    public void assertValidFromUid(int n) {
        if (this.requestorUid == n) {
            return;
        }
        throw new SecurityException("mismatched UIDs");
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
        if (!(object instanceof WifiAwareNetworkSpecifier)) {
            return false;
        }
        object = (WifiAwareNetworkSpecifier)object;
        if (!(this.type == ((WifiAwareNetworkSpecifier)object).type && this.role == ((WifiAwareNetworkSpecifier)object).role && this.clientId == ((WifiAwareNetworkSpecifier)object).clientId && this.sessionId == ((WifiAwareNetworkSpecifier)object).sessionId && this.peerId == ((WifiAwareNetworkSpecifier)object).peerId && Arrays.equals(this.peerMac, ((WifiAwareNetworkSpecifier)object).peerMac) && Arrays.equals(this.pmk, ((WifiAwareNetworkSpecifier)object).pmk) && Objects.equals(this.passphrase, ((WifiAwareNetworkSpecifier)object).passphrase) && this.port == ((WifiAwareNetworkSpecifier)object).port && this.transportProtocol == ((WifiAwareNetworkSpecifier)object).transportProtocol && this.requestorUid == ((WifiAwareNetworkSpecifier)object).requestorUid)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.type, this.role, this.clientId, this.sessionId, this.peerId, Arrays.hashCode(this.peerMac), Arrays.hashCode(this.pmk), this.passphrase, this.port, this.transportProtocol, this.requestorUid);
    }

    public boolean isOutOfBand() {
        int n = this.type;
        boolean bl = n == 2 || n == 3;
        return bl;
    }

    @Override
    public boolean satisfiedBy(NetworkSpecifier networkSpecifier) {
        if (networkSpecifier instanceof WifiAwareAgentNetworkSpecifier) {
            return ((WifiAwareAgentNetworkSpecifier)networkSpecifier).satisfiesAwareNetworkSpecifier(this);
        }
        return this.equals(networkSpecifier);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("WifiAwareNetworkSpecifier [");
        stringBuilder.append("type=");
        stringBuilder.append(this.type);
        stringBuilder.append(", role=");
        stringBuilder.append(this.role);
        stringBuilder.append(", clientId=");
        stringBuilder.append(this.clientId);
        stringBuilder.append(", sessionId=");
        stringBuilder.append(this.sessionId);
        stringBuilder.append(", peerId=");
        stringBuilder.append(this.peerId);
        stringBuilder.append(", peerMac=");
        Object object = this.peerMac;
        String string2 = "<null>";
        object = object == null ? "<null>" : "<non-null>";
        stringBuilder.append((String)object);
        stringBuilder.append(", pmk=");
        object = this.pmk == null ? "<null>" : "<non-null>";
        stringBuilder.append((String)object);
        stringBuilder.append(", passphrase=");
        object = this.passphrase == null ? string2 : "<non-null>";
        stringBuilder.append((String)object);
        stringBuilder.append(", port=");
        stringBuilder.append(this.port);
        stringBuilder.append(", transportProtocol=");
        stringBuilder.append(this.transportProtocol);
        stringBuilder.append(", requestorUid=");
        stringBuilder.append(this.requestorUid);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.type);
        parcel.writeInt(this.role);
        parcel.writeInt(this.clientId);
        parcel.writeInt(this.sessionId);
        parcel.writeInt(this.peerId);
        parcel.writeByteArray(this.peerMac);
        parcel.writeByteArray(this.pmk);
        parcel.writeString(this.passphrase);
        parcel.writeInt(this.port);
        parcel.writeInt(this.transportProtocol);
        parcel.writeInt(this.requestorUid);
    }

    public static final class Builder {
        private DiscoverySession mDiscoverySession;
        private PeerHandle mPeerHandle;
        private byte[] mPmk;
        private int mPort = 0;
        private String mPskPassphrase;
        private int mTransportProtocol = -1;

        public Builder(DiscoverySession discoverySession, PeerHandle peerHandle) {
            if (discoverySession != null) {
                if (peerHandle != null) {
                    this.mDiscoverySession = discoverySession;
                    this.mPeerHandle = peerHandle;
                    return;
                }
                throw new IllegalArgumentException("Non-null peerHandle required");
            }
            throw new IllegalArgumentException("Non-null discoverySession required");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public WifiAwareNetworkSpecifier build() {
            boolean bl;
            if (this.mDiscoverySession == null) throw new IllegalStateException("Null discovery session!?");
            if (this.mPeerHandle == null) throw new IllegalStateException("Null peerHandle!?");
            String string2 = this.mPskPassphrase;
            int n = 0;
            int n2 = string2 != null ? 1 : 0;
            if (n2 & (bl = this.mPmk != null)) throw new IllegalStateException("Can only specify a Passphrase or a PMK - not both!");
            n2 = this.mDiscoverySession instanceof SubscribeDiscoverySession ? n : 1;
            if (this.mPort == 0 && this.mTransportProtocol == -1) return new WifiAwareNetworkSpecifier(0, n2, this.mDiscoverySession.mClientId, this.mDiscoverySession.mSessionId, this.mPeerHandle.peerId, null, this.mPmk, this.mPskPassphrase, this.mPort, this.mTransportProtocol, Process.myUid());
            if (n2 != true) throw new IllegalStateException("Port and transport protocol information can only be specified on the Publisher device (which is the server");
            if (!TextUtils.isEmpty(this.mPskPassphrase) || this.mPmk != null) return new WifiAwareNetworkSpecifier(0, n2, this.mDiscoverySession.mClientId, this.mDiscoverySession.mSessionId, this.mPeerHandle.peerId, null, this.mPmk, this.mPskPassphrase, this.mPort, this.mTransportProtocol, Process.myUid());
            throw new IllegalStateException("Port and transport protocol information can only be specified on a secure link");
        }

        @SystemApi
        public Builder setPmk(byte[] arrby) {
            if (WifiAwareUtils.validatePmk(arrby)) {
                this.mPmk = arrby;
                return this;
            }
            throw new IllegalArgumentException("PMK must 32 bytes");
        }

        public Builder setPort(int n) {
            if (n > 0 && n <= 65535) {
                this.mPort = n;
                return this;
            }
            throw new IllegalArgumentException("The port must be a positive value (0, 65535]");
        }

        public Builder setPskPassphrase(String string2) {
            if (WifiAwareUtils.validatePassphrase(string2)) {
                this.mPskPassphrase = string2;
                return this;
            }
            throw new IllegalArgumentException("Passphrase must meet length requirements");
        }

        public Builder setTransportProtocol(int n) {
            if (n >= 0 && n <= 255) {
                this.mTransportProtocol = n;
                return this;
            }
            throw new IllegalArgumentException("The transport protocol must be in range [0, 255]");
        }
    }

}


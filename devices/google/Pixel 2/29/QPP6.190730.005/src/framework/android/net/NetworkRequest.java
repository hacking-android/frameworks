/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.MatchAllNetworkSpecifier;
import android.net.NetworkCapabilities;
import android.net.NetworkSpecifier;
import android.net.StringNetworkSpecifier;
import android.net.UidRange;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import android.util.proto.ProtoOutputStream;
import java.util.Objects;
import java.util.Set;

public class NetworkRequest
implements Parcelable {
    public static final Parcelable.Creator<NetworkRequest> CREATOR = new Parcelable.Creator<NetworkRequest>(){

        @Override
        public NetworkRequest createFromParcel(Parcel parcel) {
            return new NetworkRequest(NetworkCapabilities.CREATOR.createFromParcel(parcel), parcel.readInt(), parcel.readInt(), Type.valueOf(parcel.readString()));
        }

        public NetworkRequest[] newArray(int n) {
            return new NetworkRequest[n];
        }
    };
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public final int legacyType;
    @UnsupportedAppUsage
    public final NetworkCapabilities networkCapabilities;
    @UnsupportedAppUsage
    public final int requestId;
    public final Type type;

    public NetworkRequest(NetworkCapabilities networkCapabilities, int n, int n2, Type type) {
        if (networkCapabilities != null) {
            this.requestId = n2;
            this.networkCapabilities = networkCapabilities;
            this.legacyType = n;
            this.type = type;
            return;
        }
        throw new NullPointerException();
    }

    public NetworkRequest(NetworkRequest networkRequest) {
        this.networkCapabilities = new NetworkCapabilities(networkRequest.networkCapabilities);
        this.requestId = networkRequest.requestId;
        this.legacyType = networkRequest.legacyType;
        this.type = networkRequest.type;
    }

    private int typeToProtoEnum(Type type) {
        int n = 2.$SwitchMap$android$net$NetworkRequest$Type[type.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return 0;
                        }
                        return 5;
                    }
                    return 4;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof NetworkRequest;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (NetworkRequest)object;
            if (((NetworkRequest)object).legacyType != this.legacyType || ((NetworkRequest)object).requestId != this.requestId || ((NetworkRequest)object).type != this.type || !Objects.equals(((NetworkRequest)object).networkCapabilities, this.networkCapabilities)) break block1;
            bl = true;
        }
        return bl;
    }

    public boolean hasCapability(int n) {
        return this.networkCapabilities.hasCapability(n);
    }

    public boolean hasTransport(int n) {
        return this.networkCapabilities.hasTransport(n);
    }

    public boolean hasUnwantedCapability(int n) {
        return this.networkCapabilities.hasUnwantedCapability(n);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.requestId, this.legacyType, this.networkCapabilities, this.type});
    }

    public boolean isBackgroundRequest() {
        boolean bl = this.type == Type.BACKGROUND_REQUEST;
        return bl;
    }

    public boolean isForegroundRequest() {
        boolean bl = this.type == Type.TRACK_DEFAULT || this.type == Type.REQUEST;
        return bl;
    }

    public boolean isListen() {
        boolean bl = this.type == Type.LISTEN;
        return bl;
    }

    public boolean isRequest() {
        boolean bl = this.isForegroundRequest() || this.isBackgroundRequest();
        return bl;
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NetworkRequest [ ");
        stringBuilder.append((Object)this.type);
        stringBuilder.append(" id=");
        stringBuilder.append(this.requestId);
        if (this.legacyType != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(", legacyType=");
            ((StringBuilder)charSequence).append(this.legacyType);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(", ");
        stringBuilder.append(this.networkCapabilities.toString());
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.networkCapabilities.writeToParcel(parcel, n);
        parcel.writeInt(this.legacyType);
        parcel.writeInt(this.requestId);
        parcel.writeString(this.type.name());
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1159641169921L, this.typeToProtoEnum(this.type));
        protoOutputStream.write(1120986464258L, this.requestId);
        protoOutputStream.write(1120986464259L, this.legacyType);
        this.networkCapabilities.writeToProto(protoOutputStream, 1146756268036L);
        protoOutputStream.end(l);
    }

    public static class Builder {
        private final NetworkCapabilities mNetworkCapabilities = new NetworkCapabilities();

        public Builder() {
            this.mNetworkCapabilities.setSingleUid(Process.myUid());
        }

        public Builder addCapability(int n) {
            this.mNetworkCapabilities.addCapability(n);
            return this;
        }

        public Builder addTransportType(int n) {
            this.mNetworkCapabilities.addTransportType(n);
            return this;
        }

        public Builder addUnwantedCapability(int n) {
            this.mNetworkCapabilities.addUnwantedCapability(n);
            return this;
        }

        public NetworkRequest build() {
            NetworkCapabilities networkCapabilities = new NetworkCapabilities(this.mNetworkCapabilities);
            networkCapabilities.maybeMarkCapabilitiesRestricted();
            return new NetworkRequest(networkCapabilities, -1, 0, Type.NONE);
        }

        @UnsupportedAppUsage
        public Builder clearCapabilities() {
            this.mNetworkCapabilities.clearAll();
            return this;
        }

        public Builder removeCapability(int n) {
            this.mNetworkCapabilities.removeCapability(n);
            return this;
        }

        public Builder removeTransportType(int n) {
            this.mNetworkCapabilities.removeTransportType(n);
            return this;
        }

        public Builder setCapabilities(NetworkCapabilities networkCapabilities) {
            this.mNetworkCapabilities.set(networkCapabilities);
            return this;
        }

        public Builder setLinkDownstreamBandwidthKbps(int n) {
            this.mNetworkCapabilities.setLinkDownstreamBandwidthKbps(n);
            return this;
        }

        public Builder setLinkUpstreamBandwidthKbps(int n) {
            this.mNetworkCapabilities.setLinkUpstreamBandwidthKbps(n);
            return this;
        }

        public Builder setNetworkSpecifier(NetworkSpecifier networkSpecifier) {
            MatchAllNetworkSpecifier.checkNotMatchAllNetworkSpecifier(networkSpecifier);
            this.mNetworkCapabilities.setNetworkSpecifier(networkSpecifier);
            return this;
        }

        public Builder setNetworkSpecifier(String object) {
            object = TextUtils.isEmpty((CharSequence)object) ? null : new StringNetworkSpecifier((String)object);
            return this.setNetworkSpecifier((NetworkSpecifier)object);
        }

        @SystemApi
        public Builder setSignalStrength(int n) {
            this.mNetworkCapabilities.setSignalStrength(n);
            return this;
        }

        public Builder setUids(Set<UidRange> set) {
            this.mNetworkCapabilities.setUids(set);
            return this;
        }
    }

    public static enum Type {
        NONE,
        LISTEN,
        TRACK_DEFAULT,
        REQUEST,
        BACKGROUND_REQUEST;
        
    }

}


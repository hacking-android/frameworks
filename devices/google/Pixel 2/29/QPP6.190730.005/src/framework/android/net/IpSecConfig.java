/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.IpSecAlgorithm;
import android.net.Network;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;

public final class IpSecConfig
implements Parcelable {
    public static final Parcelable.Creator<IpSecConfig> CREATOR = new Parcelable.Creator<IpSecConfig>(){

        @Override
        public IpSecConfig createFromParcel(Parcel parcel) {
            return new IpSecConfig(parcel);
        }

        public IpSecConfig[] newArray(int n) {
            return new IpSecConfig[n];
        }
    };
    private static final String TAG = "IpSecConfig";
    private IpSecAlgorithm mAuthenticatedEncryption;
    private IpSecAlgorithm mAuthentication;
    private String mDestinationAddress = "";
    private int mEncapRemotePort;
    private int mEncapSocketResourceId = -1;
    private int mEncapType = 0;
    private IpSecAlgorithm mEncryption;
    private int mMarkMask;
    private int mMarkValue;
    private int mMode = 0;
    private int mNattKeepaliveInterval;
    private Network mNetwork;
    private String mSourceAddress = "";
    private int mSpiResourceId = -1;
    private int mXfrmInterfaceId;

    @VisibleForTesting
    public IpSecConfig() {
    }

    @VisibleForTesting
    public IpSecConfig(IpSecConfig ipSecConfig) {
        this.mMode = ipSecConfig.mMode;
        this.mSourceAddress = ipSecConfig.mSourceAddress;
        this.mDestinationAddress = ipSecConfig.mDestinationAddress;
        this.mNetwork = ipSecConfig.mNetwork;
        this.mSpiResourceId = ipSecConfig.mSpiResourceId;
        this.mEncryption = ipSecConfig.mEncryption;
        this.mAuthentication = ipSecConfig.mAuthentication;
        this.mAuthenticatedEncryption = ipSecConfig.mAuthenticatedEncryption;
        this.mEncapType = ipSecConfig.mEncapType;
        this.mEncapSocketResourceId = ipSecConfig.mEncapSocketResourceId;
        this.mEncapRemotePort = ipSecConfig.mEncapRemotePort;
        this.mNattKeepaliveInterval = ipSecConfig.mNattKeepaliveInterval;
        this.mMarkValue = ipSecConfig.mMarkValue;
        this.mMarkMask = ipSecConfig.mMarkMask;
        this.mXfrmInterfaceId = ipSecConfig.mXfrmInterfaceId;
    }

    private IpSecConfig(Parcel parcel) {
        this.mMode = parcel.readInt();
        this.mSourceAddress = parcel.readString();
        this.mDestinationAddress = parcel.readString();
        this.mNetwork = (Network)parcel.readParcelable(Network.class.getClassLoader());
        this.mSpiResourceId = parcel.readInt();
        this.mEncryption = (IpSecAlgorithm)parcel.readParcelable(IpSecAlgorithm.class.getClassLoader());
        this.mAuthentication = (IpSecAlgorithm)parcel.readParcelable(IpSecAlgorithm.class.getClassLoader());
        this.mAuthenticatedEncryption = (IpSecAlgorithm)parcel.readParcelable(IpSecAlgorithm.class.getClassLoader());
        this.mEncapType = parcel.readInt();
        this.mEncapSocketResourceId = parcel.readInt();
        this.mEncapRemotePort = parcel.readInt();
        this.mNattKeepaliveInterval = parcel.readInt();
        this.mMarkValue = parcel.readInt();
        this.mMarkMask = parcel.readInt();
        this.mXfrmInterfaceId = parcel.readInt();
    }

    @VisibleForTesting
    public static boolean equals(IpSecConfig ipSecConfig, IpSecConfig ipSecConfig2) {
        boolean bl = true;
        boolean bl2 = true;
        if (ipSecConfig != null && ipSecConfig2 != null) {
            Network network;
            if (!(ipSecConfig.mMode == ipSecConfig2.mMode && ipSecConfig.mSourceAddress.equals(ipSecConfig2.mSourceAddress) && ipSecConfig.mDestinationAddress.equals(ipSecConfig2.mDestinationAddress) && ((network = ipSecConfig.mNetwork) != null && network.equals(ipSecConfig2.mNetwork) || ipSecConfig.mNetwork == ipSecConfig2.mNetwork) && ipSecConfig.mEncapType == ipSecConfig2.mEncapType && ipSecConfig.mEncapSocketResourceId == ipSecConfig2.mEncapSocketResourceId && ipSecConfig.mEncapRemotePort == ipSecConfig2.mEncapRemotePort && ipSecConfig.mNattKeepaliveInterval == ipSecConfig2.mNattKeepaliveInterval && ipSecConfig.mSpiResourceId == ipSecConfig2.mSpiResourceId && IpSecAlgorithm.equals(ipSecConfig.mEncryption, ipSecConfig2.mEncryption) && IpSecAlgorithm.equals(ipSecConfig.mAuthenticatedEncryption, ipSecConfig2.mAuthenticatedEncryption) && IpSecAlgorithm.equals(ipSecConfig.mAuthentication, ipSecConfig2.mAuthentication) && ipSecConfig.mMarkValue == ipSecConfig2.mMarkValue && ipSecConfig.mMarkMask == ipSecConfig2.mMarkMask && ipSecConfig.mXfrmInterfaceId == ipSecConfig2.mXfrmInterfaceId)) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = ipSecConfig == ipSecConfig2 ? bl : false;
        return bl2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public IpSecAlgorithm getAuthenticatedEncryption() {
        return this.mAuthenticatedEncryption;
    }

    public IpSecAlgorithm getAuthentication() {
        return this.mAuthentication;
    }

    public String getDestinationAddress() {
        return this.mDestinationAddress;
    }

    public int getEncapRemotePort() {
        return this.mEncapRemotePort;
    }

    public int getEncapSocketResourceId() {
        return this.mEncapSocketResourceId;
    }

    public int getEncapType() {
        return this.mEncapType;
    }

    public IpSecAlgorithm getEncryption() {
        return this.mEncryption;
    }

    public int getMarkMask() {
        return this.mMarkMask;
    }

    public int getMarkValue() {
        return this.mMarkValue;
    }

    public int getMode() {
        return this.mMode;
    }

    public int getNattKeepaliveInterval() {
        return this.mNattKeepaliveInterval;
    }

    public Network getNetwork() {
        return this.mNetwork;
    }

    public String getSourceAddress() {
        return this.mSourceAddress;
    }

    public int getSpiResourceId() {
        return this.mSpiResourceId;
    }

    public int getXfrmInterfaceId() {
        return this.mXfrmInterfaceId;
    }

    public void setAuthenticatedEncryption(IpSecAlgorithm ipSecAlgorithm) {
        this.mAuthenticatedEncryption = ipSecAlgorithm;
    }

    public void setAuthentication(IpSecAlgorithm ipSecAlgorithm) {
        this.mAuthentication = ipSecAlgorithm;
    }

    public void setDestinationAddress(String string2) {
        this.mDestinationAddress = string2;
    }

    public void setEncapRemotePort(int n) {
        this.mEncapRemotePort = n;
    }

    public void setEncapSocketResourceId(int n) {
        this.mEncapSocketResourceId = n;
    }

    public void setEncapType(int n) {
        this.mEncapType = n;
    }

    public void setEncryption(IpSecAlgorithm ipSecAlgorithm) {
        this.mEncryption = ipSecAlgorithm;
    }

    public void setMarkMask(int n) {
        this.mMarkMask = n;
    }

    public void setMarkValue(int n) {
        this.mMarkValue = n;
    }

    public void setMode(int n) {
        this.mMode = n;
    }

    public void setNattKeepaliveInterval(int n) {
        this.mNattKeepaliveInterval = n;
    }

    public void setNetwork(Network network) {
        this.mNetwork = network;
    }

    public void setSourceAddress(String string2) {
        this.mSourceAddress = string2;
    }

    public void setSpiResourceId(int n) {
        this.mSpiResourceId = n;
    }

    public void setXfrmInterfaceId(int n) {
        this.mXfrmInterfaceId = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mMode=");
        String string2 = this.mMode == 1 ? "TUNNEL" : "TRANSPORT";
        stringBuilder.append(string2);
        stringBuilder.append(", mSourceAddress=");
        stringBuilder.append(this.mSourceAddress);
        stringBuilder.append(", mDestinationAddress=");
        stringBuilder.append(this.mDestinationAddress);
        stringBuilder.append(", mNetwork=");
        stringBuilder.append(this.mNetwork);
        stringBuilder.append(", mEncapType=");
        stringBuilder.append(this.mEncapType);
        stringBuilder.append(", mEncapSocketResourceId=");
        stringBuilder.append(this.mEncapSocketResourceId);
        stringBuilder.append(", mEncapRemotePort=");
        stringBuilder.append(this.mEncapRemotePort);
        stringBuilder.append(", mNattKeepaliveInterval=");
        stringBuilder.append(this.mNattKeepaliveInterval);
        stringBuilder.append("{mSpiResourceId=");
        stringBuilder.append(this.mSpiResourceId);
        stringBuilder.append(", mEncryption=");
        stringBuilder.append(this.mEncryption);
        stringBuilder.append(", mAuthentication=");
        stringBuilder.append(this.mAuthentication);
        stringBuilder.append(", mAuthenticatedEncryption=");
        stringBuilder.append(this.mAuthenticatedEncryption);
        stringBuilder.append(", mMarkValue=");
        stringBuilder.append(this.mMarkValue);
        stringBuilder.append(", mMarkMask=");
        stringBuilder.append(this.mMarkMask);
        stringBuilder.append(", mXfrmInterfaceId=");
        stringBuilder.append(this.mXfrmInterfaceId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMode);
        parcel.writeString(this.mSourceAddress);
        parcel.writeString(this.mDestinationAddress);
        parcel.writeParcelable(this.mNetwork, n);
        parcel.writeInt(this.mSpiResourceId);
        parcel.writeParcelable(this.mEncryption, n);
        parcel.writeParcelable(this.mAuthentication, n);
        parcel.writeParcelable(this.mAuthenticatedEncryption, n);
        parcel.writeInt(this.mEncapType);
        parcel.writeInt(this.mEncapSocketResourceId);
        parcel.writeInt(this.mEncapRemotePort);
        parcel.writeInt(this.mNattKeepaliveInterval);
        parcel.writeInt(this.mMarkValue);
        parcel.writeInt(this.mMarkMask);
        parcel.writeInt(this.mXfrmInterfaceId);
    }

}


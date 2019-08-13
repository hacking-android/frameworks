/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.os.Parcel;
import android.os.Parcelable;

public final class WifiDisplaySessionInfo
implements Parcelable {
    public static final Parcelable.Creator<WifiDisplaySessionInfo> CREATOR = new Parcelable.Creator<WifiDisplaySessionInfo>(){

        @Override
        public WifiDisplaySessionInfo createFromParcel(Parcel parcel) {
            boolean bl = parcel.readInt() != 0;
            return new WifiDisplaySessionInfo(bl, parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        public WifiDisplaySessionInfo[] newArray(int n) {
            return new WifiDisplaySessionInfo[n];
        }
    };
    private final boolean mClient;
    private final String mGroupId;
    private final String mIP;
    private final String mPassphrase;
    private final int mSessionId;

    public WifiDisplaySessionInfo() {
        this(true, 0, "", "", "");
    }

    public WifiDisplaySessionInfo(boolean bl, int n, String string2, String string3, String string4) {
        this.mClient = bl;
        this.mSessionId = n;
        this.mGroupId = string2;
        this.mPassphrase = string3;
        this.mIP = string4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getGroupId() {
        return this.mGroupId;
    }

    public String getIP() {
        return this.mIP;
    }

    public String getPassphrase() {
        return this.mPassphrase;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public boolean isClient() {
        return this.mClient;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WifiDisplaySessionInfo:\n    Client/Owner: ");
        String string2 = this.mClient ? "Client" : "Owner";
        stringBuilder.append(string2);
        stringBuilder.append("\n    GroupId: ");
        stringBuilder.append(this.mGroupId);
        stringBuilder.append("\n    Passphrase: ");
        stringBuilder.append(this.mPassphrase);
        stringBuilder.append("\n    SessionId: ");
        stringBuilder.append(this.mSessionId);
        stringBuilder.append("\n    IP Address: ");
        stringBuilder.append(this.mIP);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mClient);
        parcel.writeInt(this.mSessionId);
        parcel.writeString(this.mGroupId);
        parcel.writeString(this.mPassphrase);
        parcel.writeString(this.mIP);
    }

}


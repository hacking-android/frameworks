/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.os.Parcel;
import android.os.Parcelable;

public class PasspointManagementObjectDefinition
implements Parcelable {
    public static final Parcelable.Creator<PasspointManagementObjectDefinition> CREATOR = new Parcelable.Creator<PasspointManagementObjectDefinition>(){

        @Override
        public PasspointManagementObjectDefinition createFromParcel(Parcel parcel) {
            return new PasspointManagementObjectDefinition(parcel.readString(), parcel.readString(), parcel.readString());
        }

        public PasspointManagementObjectDefinition[] newArray(int n) {
            return new PasspointManagementObjectDefinition[n];
        }
    };
    private final String mBaseUri;
    private final String mMoTree;
    private final String mUrn;

    public PasspointManagementObjectDefinition(String string2, String string3, String string4) {
        this.mBaseUri = string2;
        this.mUrn = string3;
        this.mMoTree = string4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBaseUri() {
        return this.mBaseUri;
    }

    public String getMoTree() {
        return this.mMoTree;
    }

    public String getUrn() {
        return this.mUrn;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mBaseUri);
        parcel.writeString(this.mUrn);
        parcel.writeString(this.mMoTree);
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.PackageParser;
import android.content.pm.Signature;
import android.os.Parcel;
import android.os.Parcelable;

public final class SigningInfo
implements Parcelable {
    public static final Parcelable.Creator<SigningInfo> CREATOR = new Parcelable.Creator<SigningInfo>(){

        @Override
        public SigningInfo createFromParcel(Parcel parcel) {
            return new SigningInfo(parcel);
        }

        public SigningInfo[] newArray(int n) {
            return new SigningInfo[n];
        }
    };
    private final PackageParser.SigningDetails mSigningDetails;

    public SigningInfo() {
        this.mSigningDetails = PackageParser.SigningDetails.UNKNOWN;
    }

    public SigningInfo(PackageParser.SigningDetails signingDetails) {
        this.mSigningDetails = new PackageParser.SigningDetails(signingDetails);
    }

    public SigningInfo(SigningInfo signingInfo) {
        this.mSigningDetails = new PackageParser.SigningDetails(signingInfo.mSigningDetails);
    }

    private SigningInfo(Parcel parcel) {
        this.mSigningDetails = PackageParser.SigningDetails.CREATOR.createFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Signature[] getApkContentsSigners() {
        return this.mSigningDetails.signatures;
    }

    public Signature[] getSigningCertificateHistory() {
        if (this.hasMultipleSigners()) {
            return null;
        }
        if (!this.hasPastSigningCertificates()) {
            return this.mSigningDetails.signatures;
        }
        return this.mSigningDetails.pastSigningCertificates;
    }

    public boolean hasMultipleSigners() {
        Signature[] arrsignature = this.mSigningDetails.signatures;
        boolean bl = true;
        if (arrsignature == null || this.mSigningDetails.signatures.length <= 1) {
            bl = false;
        }
        return bl;
    }

    public boolean hasPastSigningCertificates() {
        boolean bl = this.mSigningDetails.signatures != null && this.mSigningDetails.pastSigningCertificates != null;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mSigningDetails.writeToParcel(parcel, n);
    }

}


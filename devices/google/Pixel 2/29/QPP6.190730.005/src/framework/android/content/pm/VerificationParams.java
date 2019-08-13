/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class VerificationParams
implements Parcelable {
    public static final Parcelable.Creator<VerificationParams> CREATOR = new Parcelable.Creator<VerificationParams>(){

        @Override
        public VerificationParams createFromParcel(Parcel parcel) {
            return new VerificationParams(parcel);
        }

        public VerificationParams[] newArray(int n) {
            return new VerificationParams[n];
        }
    };
    public static final int NO_UID = -1;
    private static final String TO_STRING_PREFIX = "VerificationParams{";
    private int mInstallerUid;
    private final Uri mOriginatingURI;
    private final int mOriginatingUid;
    private final Uri mReferrer;
    private final Uri mVerificationURI;

    public VerificationParams(Uri uri, Uri uri2, Uri uri3, int n) {
        this.mVerificationURI = uri;
        this.mOriginatingURI = uri2;
        this.mReferrer = uri3;
        this.mOriginatingUid = n;
        this.mInstallerUid = -1;
    }

    private VerificationParams(Parcel parcel) {
        this.mVerificationURI = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        this.mOriginatingURI = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        this.mReferrer = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        this.mOriginatingUid = parcel.readInt();
        this.mInstallerUid = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VerificationParams)) {
            return false;
        }
        object = (VerificationParams)object;
        Uri uri = this.mVerificationURI;
        if (uri == null ? ((VerificationParams)object).mVerificationURI != null : !uri.equals(((VerificationParams)object).mVerificationURI)) {
            return false;
        }
        uri = this.mOriginatingURI;
        if (uri == null ? ((VerificationParams)object).mOriginatingURI != null : !uri.equals(((VerificationParams)object).mOriginatingURI)) {
            return false;
        }
        uri = this.mReferrer;
        if (uri == null ? ((VerificationParams)object).mReferrer != null : !uri.equals(((VerificationParams)object).mReferrer)) {
            return false;
        }
        if (this.mOriginatingUid != ((VerificationParams)object).mOriginatingUid) {
            return false;
        }
        return this.mInstallerUid == ((VerificationParams)object).mInstallerUid;
    }

    public int getInstallerUid() {
        return this.mInstallerUid;
    }

    public Uri getOriginatingURI() {
        return this.mOriginatingURI;
    }

    public int getOriginatingUid() {
        return this.mOriginatingUid;
    }

    public Uri getReferrer() {
        return this.mReferrer;
    }

    public Uri getVerificationURI() {
        return this.mVerificationURI;
    }

    public int hashCode() {
        Uri uri = this.mVerificationURI;
        int n = 1;
        int n2 = uri == null ? 1 : uri.hashCode();
        uri = this.mOriginatingURI;
        int n3 = uri == null ? 1 : uri.hashCode();
        uri = this.mReferrer;
        if (uri != null) {
            n = uri.hashCode();
        }
        return 3 + n2 * 5 + n3 * 7 + n * 11 + this.mOriginatingUid * 13 + this.mInstallerUid * 17;
    }

    public void setInstallerUid(int n) {
        this.mInstallerUid = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(TO_STRING_PREFIX);
        stringBuilder.append("mVerificationURI=");
        stringBuilder.append(this.mVerificationURI.toString());
        stringBuilder.append(",mOriginatingURI=");
        stringBuilder.append(this.mOriginatingURI.toString());
        stringBuilder.append(",mReferrer=");
        stringBuilder.append(this.mReferrer.toString());
        stringBuilder.append(",mOriginatingUid=");
        stringBuilder.append(this.mOriginatingUid);
        stringBuilder.append(",mInstallerUid=");
        stringBuilder.append(this.mInstallerUid);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mVerificationURI, 0);
        parcel.writeParcelable(this.mOriginatingURI, 0);
        parcel.writeParcelable(this.mReferrer, 0);
        parcel.writeInt(this.mOriginatingUid);
        parcel.writeInt(this.mInstallerUid);
    }

}


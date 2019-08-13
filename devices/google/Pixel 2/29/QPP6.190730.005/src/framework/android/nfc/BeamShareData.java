/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.net.Uri;
import android.nfc.NdefMessage;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;

public final class BeamShareData
implements Parcelable {
    public static final Parcelable.Creator<BeamShareData> CREATOR = new Parcelable.Creator<BeamShareData>(){

        @Override
        public BeamShareData createFromParcel(Parcel parcel) {
            Uri[] arruri = null;
            NdefMessage ndefMessage = (NdefMessage)parcel.readParcelable(NdefMessage.class.getClassLoader());
            int n = parcel.readInt();
            if (n > 0) {
                arruri = new Uri[n];
                parcel.readTypedArray(arruri, Uri.CREATOR);
            }
            return new BeamShareData(ndefMessage, arruri, (UserHandle)parcel.readParcelable(UserHandle.class.getClassLoader()), parcel.readInt());
        }

        public BeamShareData[] newArray(int n) {
            return new BeamShareData[n];
        }
    };
    public final int flags;
    public final NdefMessage ndefMessage;
    public final Uri[] uris;
    public final UserHandle userHandle;

    public BeamShareData(NdefMessage ndefMessage, Uri[] arruri, UserHandle userHandle, int n) {
        this.ndefMessage = ndefMessage;
        this.uris = arruri;
        this.userHandle = userHandle;
        this.flags = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Uri[] arruri = this.uris;
        n = arruri != null ? arruri.length : 0;
        parcel.writeParcelable(this.ndefMessage, 0);
        parcel.writeInt(n);
        if (n > 0) {
            parcel.writeTypedArray((Parcelable[])this.uris, 0);
        }
        parcel.writeParcelable(this.userHandle, 0);
        parcel.writeInt(this.flags);
    }

}


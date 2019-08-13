/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProviderNative;
import android.content.IContentProvider;
import android.content.pm.ProviderInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class ContentProviderHolder
implements Parcelable {
    public static final Parcelable.Creator<ContentProviderHolder> CREATOR = new Parcelable.Creator<ContentProviderHolder>(){

        @Override
        public ContentProviderHolder createFromParcel(Parcel parcel) {
            return new ContentProviderHolder(parcel);
        }

        public ContentProviderHolder[] newArray(int n) {
            return new ContentProviderHolder[n];
        }
    };
    public IBinder connection;
    @UnsupportedAppUsage
    public final ProviderInfo info;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean noReleaseNeeded;
    @UnsupportedAppUsage
    public IContentProvider provider;

    @UnsupportedAppUsage
    public ContentProviderHolder(ProviderInfo providerInfo) {
        this.info = providerInfo;
    }

    @UnsupportedAppUsage
    private ContentProviderHolder(Parcel parcel) {
        this.info = ProviderInfo.CREATOR.createFromParcel(parcel);
        this.provider = ContentProviderNative.asInterface(parcel.readStrongBinder());
        this.connection = parcel.readStrongBinder();
        boolean bl = parcel.readInt() != 0;
        this.noReleaseNeeded = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.info.writeToParcel(parcel, 0);
        IContentProvider iContentProvider = this.provider;
        if (iContentProvider != null) {
            parcel.writeStrongBinder(iContentProvider.asBinder());
        } else {
            parcel.writeStrongBinder(null);
        }
        parcel.writeStrongBinder(this.connection);
        parcel.writeInt((int)this.noReleaseNeeded);
    }

}


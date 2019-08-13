/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.euicc.DownloadableSubscription;

@SystemApi
public final class GetDownloadableSubscriptionMetadataResult
implements Parcelable {
    public static final Parcelable.Creator<GetDownloadableSubscriptionMetadataResult> CREATOR = new Parcelable.Creator<GetDownloadableSubscriptionMetadataResult>(){

        @Override
        public GetDownloadableSubscriptionMetadataResult createFromParcel(Parcel parcel) {
            return new GetDownloadableSubscriptionMetadataResult(parcel);
        }

        public GetDownloadableSubscriptionMetadataResult[] newArray(int n) {
            return new GetDownloadableSubscriptionMetadataResult[n];
        }
    };
    private final DownloadableSubscription mSubscription;
    @Deprecated
    @UnsupportedAppUsage
    public final int result;

    public GetDownloadableSubscriptionMetadataResult(int n, DownloadableSubscription object) {
        block4 : {
            block3 : {
                block2 : {
                    this.result = n;
                    if (this.result != 0) break block2;
                    this.mSubscription = object;
                    break block3;
                }
                if (object != null) break block4;
                this.mSubscription = null;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error result with non-null subscription: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private GetDownloadableSubscriptionMetadataResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.mSubscription = parcel.readTypedObject(DownloadableSubscription.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public DownloadableSubscription getDownloadableSubscription() {
        return this.mSubscription;
    }

    public int getResult() {
        return this.result;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.result);
        parcel.writeTypedObject(this.mSubscription, n);
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.euicc.DownloadableSubscription;
import java.util.Arrays;
import java.util.List;

@SystemApi
public final class GetDefaultDownloadableSubscriptionListResult
implements Parcelable {
    public static final Parcelable.Creator<GetDefaultDownloadableSubscriptionListResult> CREATOR = new Parcelable.Creator<GetDefaultDownloadableSubscriptionListResult>(){

        @Override
        public GetDefaultDownloadableSubscriptionListResult createFromParcel(Parcel parcel) {
            return new GetDefaultDownloadableSubscriptionListResult(parcel);
        }

        public GetDefaultDownloadableSubscriptionListResult[] newArray(int n) {
            return new GetDefaultDownloadableSubscriptionListResult[n];
        }
    };
    private final DownloadableSubscription[] mSubscriptions;
    @Deprecated
    @UnsupportedAppUsage
    public final int result;

    public GetDefaultDownloadableSubscriptionListResult(int n, DownloadableSubscription[] object) {
        block4 : {
            block3 : {
                block2 : {
                    this.result = n;
                    if (this.result != 0) break block2;
                    this.mSubscriptions = object;
                    break block3;
                }
                if (object != null) break block4;
                this.mSubscriptions = null;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error result with non-null subscriptions: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private GetDefaultDownloadableSubscriptionListResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.mSubscriptions = parcel.createTypedArray(DownloadableSubscription.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<DownloadableSubscription> getDownloadableSubscriptions() {
        DownloadableSubscription[] arrdownloadableSubscription = this.mSubscriptions;
        if (arrdownloadableSubscription == null) {
            return null;
        }
        return Arrays.asList(arrdownloadableSubscription);
    }

    public int getResult() {
        return this.result;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.result);
        parcel.writeTypedArray((Parcelable[])this.mSubscriptions, n);
    }

}


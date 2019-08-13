/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class DownloadSubscriptionResult
implements Parcelable {
    public static final Parcelable.Creator<DownloadSubscriptionResult> CREATOR = new Parcelable.Creator<DownloadSubscriptionResult>(){

        @Override
        public DownloadSubscriptionResult createFromParcel(Parcel parcel) {
            return new DownloadSubscriptionResult(parcel);
        }

        public DownloadSubscriptionResult[] newArray(int n) {
            return new DownloadSubscriptionResult[n];
        }
    };
    private final int mCardId;
    private final int mResolvableErrors;
    private final int mResult;

    public DownloadSubscriptionResult(int n, int n2, int n3) {
        this.mResult = n;
        this.mResolvableErrors = n2;
        this.mCardId = n3;
    }

    private DownloadSubscriptionResult(Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mResolvableErrors = parcel.readInt();
        this.mCardId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCardId() {
        return this.mCardId;
    }

    public int getResolvableErrors() {
        return this.mResolvableErrors;
    }

    public int getResult() {
        return this.mResult;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mResult);
        parcel.writeInt(this.mResolvableErrors);
        parcel.writeInt(this.mCardId);
    }

}


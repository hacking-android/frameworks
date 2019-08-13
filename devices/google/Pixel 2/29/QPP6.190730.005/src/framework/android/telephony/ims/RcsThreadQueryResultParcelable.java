/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import com.android.ims.RcsTypeIdPair;
import java.util.ArrayList;
import java.util.List;

public final class RcsThreadQueryResultParcelable
implements Parcelable {
    public static final Parcelable.Creator<RcsThreadQueryResultParcelable> CREATOR = new Parcelable.Creator<RcsThreadQueryResultParcelable>(){

        @Override
        public RcsThreadQueryResultParcelable createFromParcel(Parcel parcel) {
            return new RcsThreadQueryResultParcelable(parcel);
        }

        public RcsThreadQueryResultParcelable[] newArray(int n) {
            return new RcsThreadQueryResultParcelable[n];
        }
    };
    final RcsQueryContinuationToken mContinuationToken;
    final List<RcsTypeIdPair> mRcsThreadIds;

    private RcsThreadQueryResultParcelable(Parcel parcel) {
        this.mContinuationToken = (RcsQueryContinuationToken)parcel.readParcelable(RcsQueryContinuationToken.class.getClassLoader());
        this.mRcsThreadIds = new ArrayList<RcsTypeIdPair>();
        parcel.readList(this.mRcsThreadIds, RcsTypeIdPair.class.getClassLoader());
    }

    public RcsThreadQueryResultParcelable(RcsQueryContinuationToken rcsQueryContinuationToken, List<RcsTypeIdPair> list) {
        this.mContinuationToken = rcsQueryContinuationToken;
        this.mRcsThreadIds = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mContinuationToken, n);
        parcel.writeList(this.mRcsThreadIds);
    }

}


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

public class RcsMessageQueryResultParcelable
implements Parcelable {
    public static final Parcelable.Creator<RcsMessageQueryResultParcelable> CREATOR = new Parcelable.Creator<RcsMessageQueryResultParcelable>(){

        @Override
        public RcsMessageQueryResultParcelable createFromParcel(Parcel parcel) {
            return new RcsMessageQueryResultParcelable(parcel);
        }

        public RcsMessageQueryResultParcelable[] newArray(int n) {
            return new RcsMessageQueryResultParcelable[n];
        }
    };
    final RcsQueryContinuationToken mContinuationToken;
    final List<RcsTypeIdPair> mMessageTypeIdPairs;

    private RcsMessageQueryResultParcelable(Parcel parcel) {
        this.mContinuationToken = (RcsQueryContinuationToken)parcel.readParcelable(RcsQueryContinuationToken.class.getClassLoader());
        this.mMessageTypeIdPairs = new ArrayList<RcsTypeIdPair>();
        parcel.readTypedList(this.mMessageTypeIdPairs, RcsTypeIdPair.CREATOR);
    }

    public RcsMessageQueryResultParcelable(RcsQueryContinuationToken rcsQueryContinuationToken, List<RcsTypeIdPair> list) {
        this.mContinuationToken = rcsQueryContinuationToken;
        this.mMessageTypeIdPairs = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mContinuationToken, n);
        parcel.writeTypedList(this.mMessageTypeIdPairs);
    }

}


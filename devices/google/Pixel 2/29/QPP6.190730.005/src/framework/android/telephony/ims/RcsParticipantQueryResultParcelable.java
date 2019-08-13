/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import java.util.ArrayList;
import java.util.List;

public final class RcsParticipantQueryResultParcelable
implements Parcelable {
    public static final Parcelable.Creator<RcsParticipantQueryResultParcelable> CREATOR = new Parcelable.Creator<RcsParticipantQueryResultParcelable>(){

        @Override
        public RcsParticipantQueryResultParcelable createFromParcel(Parcel parcel) {
            return new RcsParticipantQueryResultParcelable(parcel);
        }

        public RcsParticipantQueryResultParcelable[] newArray(int n) {
            return new RcsParticipantQueryResultParcelable[n];
        }
    };
    final RcsQueryContinuationToken mContinuationToken;
    final List<Integer> mParticipantIds;

    private RcsParticipantQueryResultParcelable(Parcel parcel) {
        this.mContinuationToken = (RcsQueryContinuationToken)parcel.readParcelable(RcsQueryContinuationToken.class.getClassLoader());
        this.mParticipantIds = new ArrayList<Integer>();
        parcel.readList(this.mParticipantIds, Integer.class.getClassLoader());
    }

    public RcsParticipantQueryResultParcelable(RcsQueryContinuationToken rcsQueryContinuationToken, List<Integer> list) {
        this.mContinuationToken = rcsQueryContinuationToken;
        this.mParticipantIds = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mContinuationToken, n);
        parcel.writeList(this.mParticipantIds);
    }

}


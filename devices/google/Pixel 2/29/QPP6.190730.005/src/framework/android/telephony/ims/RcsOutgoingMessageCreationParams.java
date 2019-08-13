/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsMessageCreationParams;

public final class RcsOutgoingMessageCreationParams
extends RcsMessageCreationParams
implements Parcelable {
    public static final Parcelable.Creator<RcsOutgoingMessageCreationParams> CREATOR = new Parcelable.Creator<RcsOutgoingMessageCreationParams>(){

        @Override
        public RcsOutgoingMessageCreationParams createFromParcel(Parcel parcel) {
            return new RcsOutgoingMessageCreationParams(parcel);
        }

        public RcsOutgoingMessageCreationParams[] newArray(int n) {
            return new RcsOutgoingMessageCreationParams[n];
        }
    };

    private RcsOutgoingMessageCreationParams(Parcel parcel) {
        super(parcel);
    }

    private RcsOutgoingMessageCreationParams(Builder builder) {
        super(builder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel);
    }

    public static class Builder
    extends RcsMessageCreationParams.Builder {
        public Builder(long l, int n) {
            super(l, n);
        }

        @Override
        public RcsOutgoingMessageCreationParams build() {
            return new RcsOutgoingMessageCreationParams(this);
        }
    }

}


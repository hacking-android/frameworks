/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsThread;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;

public final class RcsParticipantQueryParams
implements Parcelable {
    public static final Parcelable.Creator<RcsParticipantQueryParams> CREATOR = new Parcelable.Creator<RcsParticipantQueryParams>(){

        @Override
        public RcsParticipantQueryParams createFromParcel(Parcel parcel) {
            return new RcsParticipantQueryParams(parcel);
        }

        public RcsParticipantQueryParams[] newArray(int n) {
            return new RcsParticipantQueryParams[n];
        }
    };
    public static final String PARTICIPANT_QUERY_PARAMETERS_KEY = "participant_query_parameters";
    public static final int SORT_BY_ALIAS = 1;
    public static final int SORT_BY_CANONICAL_ADDRESS = 2;
    public static final int SORT_BY_CREATION_ORDER = 0;
    private String mAliasLike;
    private String mCanonicalAddressLike;
    private boolean mIsAscending;
    private int mLimit;
    private int mSortingProperty;
    private int mThreadId;

    RcsParticipantQueryParams(int n, String string2, String string3, int n2, boolean bl, int n3) {
        this.mThreadId = n;
        this.mAliasLike = string2;
        this.mCanonicalAddressLike = string3;
        this.mSortingProperty = n2;
        this.mIsAscending = bl;
        this.mLimit = n3;
    }

    private RcsParticipantQueryParams(Parcel parcel) {
        this.mAliasLike = parcel.readString();
        this.mCanonicalAddressLike = parcel.readString();
        this.mSortingProperty = parcel.readInt();
        byte by = parcel.readByte();
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        this.mIsAscending = bl;
        this.mLimit = parcel.readInt();
        this.mThreadId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAliasLike() {
        return this.mAliasLike;
    }

    public String getCanonicalAddressLike() {
        return this.mCanonicalAddressLike;
    }

    public int getLimit() {
        return this.mLimit;
    }

    public boolean getSortDirection() {
        return this.mIsAscending;
    }

    public int getSortingProperty() {
        return this.mSortingProperty;
    }

    public int getThreadId() {
        return this.mThreadId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mAliasLike);
        parcel.writeString(this.mCanonicalAddressLike);
        parcel.writeInt(this.mSortingProperty);
        parcel.writeByte((byte)(this.mIsAscending ? 1 : 0));
        parcel.writeInt(this.mLimit);
        parcel.writeInt(this.mThreadId);
    }

    public static class Builder {
        private String mAliasLike;
        private String mCanonicalAddressLike;
        private boolean mIsAscending;
        private int mLimit = 100;
        private int mSortingProperty;
        private int mThreadId;

        public RcsParticipantQueryParams build() {
            return new RcsParticipantQueryParams(this.mThreadId, this.mAliasLike, this.mCanonicalAddressLike, this.mSortingProperty, this.mIsAscending, this.mLimit);
        }

        public Builder setAliasLike(String string2) {
            this.mAliasLike = string2;
            return this;
        }

        public Builder setCanonicalAddressLike(String string2) {
            this.mCanonicalAddressLike = string2;
            return this;
        }

        public Builder setResultLimit(int n) throws InvalidParameterException {
            if (n >= 0) {
                this.mLimit = n;
                return this;
            }
            throw new InvalidParameterException("The query limit must be non-negative");
        }

        public Builder setSortDirection(boolean bl) {
            this.mIsAscending = bl;
            return this;
        }

        public Builder setSortProperty(int n) {
            this.mSortingProperty = n;
            return this;
        }

        public Builder setThread(RcsThread rcsThread) {
            this.mThreadId = rcsThread.getThreadId();
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SortingProperty {
    }

}


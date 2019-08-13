/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RcsQueryContinuationToken
implements Parcelable {
    public static final Parcelable.Creator<RcsQueryContinuationToken> CREATOR = new Parcelable.Creator<RcsQueryContinuationToken>(){

        @Override
        public RcsQueryContinuationToken createFromParcel(Parcel parcel) {
            return new RcsQueryContinuationToken(parcel);
        }

        public RcsQueryContinuationToken[] newArray(int n) {
            return new RcsQueryContinuationToken[n];
        }
    };
    public static final int EVENT_QUERY_CONTINUATION_TOKEN_TYPE = 0;
    public static final int MESSAGE_QUERY_CONTINUATION_TOKEN_TYPE = 1;
    public static final int PARTICIPANT_QUERY_CONTINUATION_TOKEN_TYPE = 2;
    public static final String QUERY_CONTINUATION_TOKEN = "query_continuation_token";
    public static final int THREAD_QUERY_CONTINUATION_TOKEN_TYPE = 3;
    private final int mLimit;
    private int mOffset;
    private int mQueryType;
    private final String mRawQuery;

    public RcsQueryContinuationToken(int n, String string2, int n2, int n3) {
        this.mQueryType = n;
        this.mRawQuery = string2;
        this.mLimit = n2;
        this.mOffset = n3;
    }

    private RcsQueryContinuationToken(Parcel parcel) {
        this.mQueryType = parcel.readInt();
        this.mRawQuery = parcel.readString();
        this.mLimit = parcel.readInt();
        this.mOffset = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public int getQueryType() {
        return this.mQueryType;
    }

    public String getRawQuery() {
        return this.mRawQuery;
    }

    public void incrementOffset() {
        this.mOffset += this.mLimit;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mQueryType);
        parcel.writeString(this.mRawQuery);
        parcel.writeInt(this.mLimit);
        parcel.writeInt(this.mOffset);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ContinuationTokenType {
    }

}


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

public final class RcsMessageQueryParams
implements Parcelable {
    public static final Parcelable.Creator<RcsMessageQueryParams> CREATOR = new Parcelable.Creator<RcsMessageQueryParams>(){

        @Override
        public RcsMessageQueryParams createFromParcel(Parcel parcel) {
            return new RcsMessageQueryParams(parcel);
        }

        public RcsMessageQueryParams[] newArray(int n) {
            return new RcsMessageQueryParams[n];
        }
    };
    public static final int MESSAGES_WITHOUT_FILE_TRANSFERS = 8;
    public static final int MESSAGES_WITH_FILE_TRANSFERS = 4;
    public static final String MESSAGE_QUERY_PARAMETERS_KEY = "message_query_parameters";
    public static final int MESSAGE_TYPE_INCOMING = 1;
    public static final int MESSAGE_TYPE_OUTGOING = 2;
    public static final int SORT_BY_CREATION_ORDER = 0;
    public static final int SORT_BY_TIMESTAMP = 1;
    public static final int THREAD_ID_NOT_SET = -1;
    private int mFileTransferPresence;
    private boolean mIsAscending;
    private int mLimit;
    private String mMessageLike;
    private int mMessageType;
    private int mSortingProperty;
    private int mThreadId;

    RcsMessageQueryParams(int n, int n2, String string2, int n3, int n4, boolean bl, int n5) {
        this.mMessageType = n;
        this.mFileTransferPresence = n2;
        this.mMessageLike = string2;
        this.mSortingProperty = n4;
        this.mIsAscending = bl;
        this.mLimit = n5;
        this.mThreadId = n3;
    }

    private RcsMessageQueryParams(Parcel parcel) {
        this.mMessageType = parcel.readInt();
        this.mFileTransferPresence = parcel.readInt();
        this.mMessageLike = parcel.readString();
        this.mSortingProperty = parcel.readInt();
        this.mIsAscending = parcel.readBoolean();
        this.mLimit = parcel.readInt();
        this.mThreadId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getFileTransferPresence() {
        return this.mFileTransferPresence;
    }

    public int getLimit() {
        return this.mLimit;
    }

    public String getMessageLike() {
        return this.mMessageLike;
    }

    public int getMessageType() {
        return this.mMessageType;
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
        parcel.writeInt(this.mMessageType);
        parcel.writeInt(this.mFileTransferPresence);
        parcel.writeString(this.mMessageLike);
        parcel.writeInt(this.mSortingProperty);
        parcel.writeBoolean(this.mIsAscending);
        parcel.writeInt(this.mLimit);
        parcel.writeInt(this.mThreadId);
    }

    public static class Builder {
        private int mFileTransferPresence;
        private boolean mIsAscending;
        private int mLimit = 100;
        private String mMessageLike;
        private int mMessageType;
        private int mSortingProperty;
        private int mThreadId = -1;

        public RcsMessageQueryParams build() {
            return new RcsMessageQueryParams(this.mMessageType, this.mFileTransferPresence, this.mMessageLike, this.mThreadId, this.mSortingProperty, this.mIsAscending, this.mLimit);
        }

        public Builder setFileTransferPresence(int n) {
            this.mFileTransferPresence = n;
            return this;
        }

        public Builder setMessageLike(String string2) {
            this.mMessageLike = string2;
            return this;
        }

        public Builder setMessageType(int n) {
            this.mMessageType = n;
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
            this.mThreadId = rcsThread == null ? -1 : rcsThread.getThreadId();
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SortingProperty {
    }

}


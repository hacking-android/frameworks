/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsGroupThread;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;

public final class RcsEventQueryParams
implements Parcelable {
    public static final int ALL_EVENTS = -1;
    public static final int ALL_GROUP_THREAD_EVENTS = 0;
    public static final Parcelable.Creator<RcsEventQueryParams> CREATOR = new Parcelable.Creator<RcsEventQueryParams>(){

        @Override
        public RcsEventQueryParams createFromParcel(Parcel parcel) {
            return new RcsEventQueryParams(parcel);
        }

        public RcsEventQueryParams[] newArray(int n) {
            return new RcsEventQueryParams[n];
        }
    };
    public static final String EVENT_QUERY_PARAMETERS_KEY = "event_query_parameters";
    public static final int GROUP_THREAD_ICON_CHANGED_EVENT = 8;
    public static final int GROUP_THREAD_NAME_CHANGED_EVENT = 16;
    public static final int GROUP_THREAD_PARTICIPANT_JOINED_EVENT = 2;
    public static final int GROUP_THREAD_PARTICIPANT_LEFT_EVENT = 4;
    public static final int PARTICIPANT_ALIAS_CHANGED_EVENT = 1;
    public static final int SORT_BY_CREATION_ORDER = 0;
    public static final int SORT_BY_TIMESTAMP = 1;
    private int mEventType;
    private boolean mIsAscending;
    private int mLimit;
    private int mSortingProperty;
    private int mThreadId;

    RcsEventQueryParams(int n, int n2, int n3, boolean bl, int n4) {
        this.mEventType = n;
        this.mSortingProperty = n3;
        this.mIsAscending = bl;
        this.mLimit = n4;
        this.mThreadId = n2;
    }

    private RcsEventQueryParams(Parcel parcel) {
        this.mEventType = parcel.readInt();
        this.mThreadId = parcel.readInt();
        this.mSortingProperty = parcel.readInt();
        this.mIsAscending = parcel.readBoolean();
        this.mLimit = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getEventType() {
        return this.mEventType;
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
        parcel.writeInt(this.mEventType);
        parcel.writeInt(this.mThreadId);
        parcel.writeInt(this.mSortingProperty);
        parcel.writeBoolean(this.mIsAscending);
        parcel.writeInt(this.mLimit);
    }

    public static class Builder {
        private int mEventType;
        private boolean mIsAscending;
        private int mLimit = 100;
        private int mSortingProperty;
        private int mThreadId;

        public RcsEventQueryParams build() {
            return new RcsEventQueryParams(this.mEventType, this.mThreadId, this.mSortingProperty, this.mIsAscending, this.mLimit);
        }

        public Builder setEventType(int n) {
            this.mEventType = n;
            return this;
        }

        public Builder setGroupThread(RcsGroupThread rcsGroupThread) {
            this.mThreadId = rcsGroupThread.getThreadId();
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
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EventType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SortingProperty {
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsParticipant;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class RcsThreadQueryParams
implements Parcelable {
    public static final Parcelable.Creator<RcsThreadQueryParams> CREATOR = new Parcelable.Creator<RcsThreadQueryParams>(){

        @Override
        public RcsThreadQueryParams createFromParcel(Parcel parcel) {
            return new RcsThreadQueryParams(parcel);
        }

        public RcsThreadQueryParams[] newArray(int n) {
            return new RcsThreadQueryParams[n];
        }
    };
    public static final int SORT_BY_CREATION_ORDER = 0;
    public static final int SORT_BY_TIMESTAMP = 1;
    public static final String THREAD_QUERY_PARAMETERS_KEY = "thread_query_parameters";
    public static final int THREAD_TYPE_1_TO_1 = 2;
    public static final int THREAD_TYPE_GROUP = 1;
    private final boolean mIsAscending;
    private final int mLimit;
    private final List<Integer> mRcsParticipantIds;
    private final int mSortingProperty;
    private final int mThreadType;

    RcsThreadQueryParams(int n, Set<RcsParticipant> set, int n2, int n3, boolean bl) {
        this.mThreadType = n;
        this.mRcsParticipantIds = RcsThreadQueryParams.convertParticipantSetToIdList(set);
        this.mLimit = n2;
        this.mSortingProperty = n3;
        this.mIsAscending = bl;
    }

    private RcsThreadQueryParams(Parcel parcel) {
        this.mThreadType = parcel.readInt();
        this.mRcsParticipantIds = new ArrayList<Integer>();
        parcel.readList(this.mRcsParticipantIds, Integer.class.getClassLoader());
        this.mLimit = parcel.readInt();
        this.mSortingProperty = parcel.readInt();
        byte by = parcel.readByte();
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        this.mIsAscending = bl;
    }

    private static List<Integer> convertParticipantSetToIdList(Set<RcsParticipant> object) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(((RcsParticipant)object.next()).getId());
        }
        return arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getLimit() {
        return this.mLimit;
    }

    public List<Integer> getRcsParticipantsIds() {
        return Collections.unmodifiableList(this.mRcsParticipantIds);
    }

    public boolean getSortDirection() {
        return this.mIsAscending;
    }

    public int getSortingProperty() {
        return this.mSortingProperty;
    }

    public int getThreadType() {
        return this.mThreadType;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mThreadType);
        parcel.writeList(this.mRcsParticipantIds);
        parcel.writeInt(this.mLimit);
        parcel.writeInt(this.mSortingProperty);
        parcel.writeByte((byte)(this.mIsAscending ? 1 : 0));
    }

    public static class Builder {
        private boolean mIsAscending;
        private int mLimit = 100;
        private Set<RcsParticipant> mParticipants = new HashSet<RcsParticipant>();
        private int mSortingProperty;
        private int mThreadType;

        public RcsThreadQueryParams build() {
            return new RcsThreadQueryParams(this.mThreadType, this.mParticipants, this.mLimit, this.mSortingProperty, this.mIsAscending);
        }

        public Builder setParticipant(RcsParticipant rcsParticipant) {
            this.mParticipants.add(rcsParticipant);
            return this;
        }

        public Builder setParticipants(List<RcsParticipant> list) {
            this.mParticipants.addAll(list);
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

        public Builder setThreadType(int n) {
            this.mThreadType = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SortingProperty {
    }

}


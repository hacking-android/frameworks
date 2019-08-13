/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.app.ActivityThread;
import android.app.Application;
import android.content.LocusId;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.IntArray;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public final class DataRemovalRequest
implements Parcelable {
    public static final Parcelable.Creator<DataRemovalRequest> CREATOR = new Parcelable.Creator<DataRemovalRequest>(){

        @Override
        public DataRemovalRequest createFromParcel(Parcel parcel) {
            return new DataRemovalRequest(parcel);
        }

        public DataRemovalRequest[] newArray(int n) {
            return new DataRemovalRequest[n];
        }
    };
    public static final int FLAG_IS_PREFIX = 1;
    private final boolean mForEverything;
    private ArrayList<LocusIdRequest> mLocusIdRequests;
    private final String mPackageName;

    private DataRemovalRequest(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mForEverything = parcel.readBoolean();
        if (!this.mForEverything) {
            int n = parcel.readInt();
            this.mLocusIdRequests = new ArrayList(n);
            for (int i = 0; i < n; ++i) {
                this.mLocusIdRequests.add(new LocusIdRequest((LocusId)parcel.readValue(null), parcel.readInt()));
            }
        }
    }

    private DataRemovalRequest(Builder builder) {
        this.mPackageName = ActivityThread.currentActivityThread().getApplication().getPackageName();
        this.mForEverything = builder.mForEverything;
        if (builder.mLocusIds != null) {
            int n = builder.mLocusIds.size();
            this.mLocusIdRequests = new ArrayList(n);
            for (int i = 0; i < n; ++i) {
                this.mLocusIdRequests.add(new LocusIdRequest((LocusId)builder.mLocusIds.get(i), builder.mFlags.get(i)));
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<LocusIdRequest> getLocusIdRequests() {
        return this.mLocusIdRequests;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public boolean isForEverything() {
        return this.mForEverything;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeBoolean(this.mForEverything);
        if (!this.mForEverything) {
            int n2 = this.mLocusIdRequests.size();
            parcel.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                LocusIdRequest locusIdRequest = this.mLocusIdRequests.get(n);
                parcel.writeValue(locusIdRequest.getLocusId());
                parcel.writeInt(locusIdRequest.getFlags());
            }
        }
    }

    public static final class Builder {
        private boolean mDestroyed;
        private IntArray mFlags;
        private boolean mForEverything;
        private ArrayList<LocusId> mLocusIds;

        private void throwIfDestroyed() {
            Preconditions.checkState(this.mDestroyed ^ true, "Already destroyed!");
        }

        public Builder addLocusId(LocusId locusId, int n) {
            this.throwIfDestroyed();
            Preconditions.checkState(this.mForEverything ^ true, "Already is for everything");
            Preconditions.checkNotNull(locusId);
            if (this.mLocusIds == null) {
                this.mLocusIds = new ArrayList();
                this.mFlags = new IntArray();
            }
            this.mLocusIds.add(locusId);
            this.mFlags.add(n);
            return this;
        }

        public DataRemovalRequest build() {
            this.throwIfDestroyed();
            boolean bl = this.mForEverything || this.mLocusIds != null;
            Preconditions.checkState(bl, "must call either #forEverything() or add one #addLocusId()");
            this.mDestroyed = true;
            return new DataRemovalRequest(this);
        }

        public Builder forEverything() {
            this.throwIfDestroyed();
            boolean bl = this.mLocusIds == null;
            Preconditions.checkState(bl, "Already added LocusIds");
            this.mForEverything = true;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface Flags {
    }

    public final class LocusIdRequest {
        private final int mFlags;
        private final LocusId mLocusId;

        private LocusIdRequest(LocusId locusId, int n) {
            this.mLocusId = locusId;
            this.mFlags = n;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public LocusId getLocusId() {
            return this.mLocusId;
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.content.LocusId;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DebugUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ContentCaptureCondition
implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureCondition> CREATOR = new Parcelable.Creator<ContentCaptureCondition>(){

        @Override
        public ContentCaptureCondition createFromParcel(Parcel parcel) {
            return new ContentCaptureCondition((LocusId)parcel.readParcelable(null), parcel.readInt());
        }

        public ContentCaptureCondition[] newArray(int n) {
            return new ContentCaptureCondition[n];
        }
    };
    public static final int FLAG_IS_REGEX = 2;
    private final int mFlags;
    private final LocusId mLocusId;

    public ContentCaptureCondition(LocusId locusId, int n) {
        this.mLocusId = Preconditions.checkNotNull(locusId);
        this.mFlags = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (ContentCaptureCondition)object;
        if (this.mFlags != ((ContentCaptureCondition)object).mFlags) {
            return false;
        }
        LocusId locusId = this.mLocusId;
        return !(locusId == null ? ((ContentCaptureCondition)object).mLocusId != null : !locusId.equals(((ContentCaptureCondition)object).mLocusId));
    }

    public int getFlags() {
        return this.mFlags;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public int hashCode() {
        int n = this.mFlags;
        LocusId locusId = this.mLocusId;
        int n2 = locusId == null ? 0 : locusId.hashCode();
        return (1 * 31 + n) * 31 + n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.mLocusId.toString());
        if (this.mFlags != 0) {
            stringBuilder.append(" (");
            stringBuilder.append(DebugUtils.flagsToString(ContentCaptureCondition.class, "FLAG_", this.mFlags));
            stringBuilder.append(')');
        }
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mLocusId, n);
        parcel.writeInt(this.mFlags);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface Flags {
    }

}


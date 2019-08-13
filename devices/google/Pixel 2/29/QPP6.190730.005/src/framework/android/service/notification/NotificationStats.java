/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class NotificationStats
implements Parcelable {
    public static final Parcelable.Creator<NotificationStats> CREATOR = new Parcelable.Creator<NotificationStats>(){

        @Override
        public NotificationStats createFromParcel(Parcel parcel) {
            return new NotificationStats(parcel);
        }

        public NotificationStats[] newArray(int n) {
            return new NotificationStats[n];
        }
    };
    public static final int DISMISSAL_AOD = 2;
    public static final int DISMISSAL_NOT_DISMISSED = -1;
    public static final int DISMISSAL_OTHER = 0;
    public static final int DISMISSAL_PEEK = 1;
    public static final int DISMISSAL_SHADE = 3;
    public static final int DISMISS_SENTIMENT_NEGATIVE = 0;
    public static final int DISMISS_SENTIMENT_NEUTRAL = 1;
    public static final int DISMISS_SENTIMENT_POSITIVE = 2;
    public static final int DISMISS_SENTIMENT_UNKNOWN = -1000;
    private boolean mDirectReplied;
    private int mDismissalSentiment = -1000;
    private int mDismissalSurface = -1;
    private boolean mExpanded;
    private boolean mInteracted;
    private boolean mSeen;
    private boolean mSnoozed;
    private boolean mViewedSettings;

    public NotificationStats() {
    }

    @SystemApi
    protected NotificationStats(Parcel parcel) {
        byte by = parcel.readByte();
        boolean bl = true;
        boolean bl2 = by != 0;
        this.mSeen = bl2;
        bl2 = parcel.readByte() != 0;
        this.mExpanded = bl2;
        bl2 = parcel.readByte() != 0;
        this.mDirectReplied = bl2;
        bl2 = parcel.readByte() != 0;
        this.mSnoozed = bl2;
        bl2 = parcel.readByte() != 0;
        this.mViewedSettings = bl2;
        bl2 = parcel.readByte() != 0 ? bl : false;
        this.mInteracted = bl2;
        this.mDismissalSurface = parcel.readInt();
        this.mDismissalSentiment = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (NotificationStats)object;
            if (this.mSeen != ((NotificationStats)object).mSeen) {
                return false;
            }
            if (this.mExpanded != ((NotificationStats)object).mExpanded) {
                return false;
            }
            if (this.mDirectReplied != ((NotificationStats)object).mDirectReplied) {
                return false;
            }
            if (this.mSnoozed != ((NotificationStats)object).mSnoozed) {
                return false;
            }
            if (this.mViewedSettings != ((NotificationStats)object).mViewedSettings) {
                return false;
            }
            if (this.mInteracted != ((NotificationStats)object).mInteracted) {
                return false;
            }
            if (this.mDismissalSurface != ((NotificationStats)object).mDismissalSurface) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getDismissalSentiment() {
        return this.mDismissalSentiment;
    }

    public int getDismissalSurface() {
        return this.mDismissalSurface;
    }

    public boolean hasDirectReplied() {
        return this.mDirectReplied;
    }

    public boolean hasExpanded() {
        return this.mExpanded;
    }

    public boolean hasInteracted() {
        return this.mInteracted;
    }

    public boolean hasSeen() {
        return this.mSeen;
    }

    public boolean hasSnoozed() {
        return this.mSnoozed;
    }

    public boolean hasViewedSettings() {
        return this.mViewedSettings;
    }

    public int hashCode() {
        return (((((this.mSeen * 31 + this.mExpanded) * 31 + this.mDirectReplied) * 31 + this.mSnoozed) * 31 + this.mViewedSettings) * 31 + this.mInteracted) * 31 + this.mDismissalSurface;
    }

    public void setDirectReplied() {
        this.mDirectReplied = true;
        this.mInteracted = true;
    }

    public void setDismissalSentiment(int n) {
        this.mDismissalSentiment = n;
    }

    public void setDismissalSurface(int n) {
        this.mDismissalSurface = n;
    }

    public void setExpanded() {
        this.mExpanded = true;
        this.mInteracted = true;
    }

    public void setSeen() {
        this.mSeen = true;
    }

    public void setSnoozed() {
        this.mSnoozed = true;
        this.mInteracted = true;
    }

    public void setViewedSettings() {
        this.mViewedSettings = true;
        this.mInteracted = true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("NotificationStats{");
        stringBuilder.append("mSeen=");
        stringBuilder.append(this.mSeen);
        stringBuilder.append(", mExpanded=");
        stringBuilder.append(this.mExpanded);
        stringBuilder.append(", mDirectReplied=");
        stringBuilder.append(this.mDirectReplied);
        stringBuilder.append(", mSnoozed=");
        stringBuilder.append(this.mSnoozed);
        stringBuilder.append(", mViewedSettings=");
        stringBuilder.append(this.mViewedSettings);
        stringBuilder.append(", mInteracted=");
        stringBuilder.append(this.mInteracted);
        stringBuilder.append(", mDismissalSurface=");
        stringBuilder.append(this.mDismissalSurface);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.mSeen ? 1 : 0));
        parcel.writeByte((byte)(this.mExpanded ? 1 : 0));
        parcel.writeByte((byte)(this.mDirectReplied ? 1 : 0));
        parcel.writeByte((byte)(this.mSnoozed ? 1 : 0));
        parcel.writeByte((byte)(this.mViewedSettings ? 1 : 0));
        parcel.writeByte((byte)(this.mInteracted ? 1 : 0));
        parcel.writeInt(this.mDismissalSurface);
        parcel.writeInt(this.mDismissalSentiment);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DismissalSentiment {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DismissalSurface {
    }

}


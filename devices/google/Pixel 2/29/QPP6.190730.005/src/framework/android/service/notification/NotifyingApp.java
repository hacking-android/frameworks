/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class NotifyingApp
implements Parcelable,
Comparable<NotifyingApp> {
    public static final Parcelable.Creator<NotifyingApp> CREATOR = new Parcelable.Creator<NotifyingApp>(){

        @Override
        public NotifyingApp createFromParcel(Parcel parcel) {
            return new NotifyingApp(parcel);
        }

        public NotifyingApp[] newArray(int n) {
            return new NotifyingApp[n];
        }
    };
    private long mLastNotified;
    private String mPkg;
    private int mUserId;

    public NotifyingApp() {
    }

    protected NotifyingApp(Parcel parcel) {
        this.mUserId = parcel.readInt();
        this.mPkg = parcel.readString();
        this.mLastNotified = parcel.readLong();
    }

    @Override
    public int compareTo(NotifyingApp notifyingApp) {
        if (this.getLastNotified() == notifyingApp.getLastNotified()) {
            if (this.getUserId() == notifyingApp.getUserId()) {
                return this.getPackage().compareTo(notifyingApp.getPackage());
            }
            return Integer.compare(this.getUserId(), notifyingApp.getUserId());
        }
        return -Long.compare(this.getLastNotified(), notifyingApp.getLastNotified());
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
            object = (NotifyingApp)object;
            if (this.getUserId() != ((NotifyingApp)object).getUserId() || this.getLastNotified() != ((NotifyingApp)object).getLastNotified() || !Objects.equals(this.mPkg, ((NotifyingApp)object).mPkg)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public long getLastNotified() {
        return this.mLastNotified;
    }

    public String getPackage() {
        return this.mPkg;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public int hashCode() {
        return Objects.hash(this.getUserId(), this.mPkg, this.getLastNotified());
    }

    public NotifyingApp setLastNotified(long l) {
        this.mLastNotified = l;
        return this;
    }

    public NotifyingApp setPackage(String string2) {
        this.mPkg = string2;
        return this;
    }

    public NotifyingApp setUserId(int n) {
        this.mUserId = n;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NotifyingApp{mUserId=");
        stringBuilder.append(this.mUserId);
        stringBuilder.append(", mPkg='");
        stringBuilder.append(this.mPkg);
        stringBuilder.append('\'');
        stringBuilder.append(", mLastNotified=");
        stringBuilder.append(this.mLastNotified);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mUserId);
        parcel.writeString(this.mPkg);
        parcel.writeLong(this.mLastNotified);
    }

}


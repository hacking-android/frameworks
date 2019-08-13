/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.statusbar;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationVisibility
implements Parcelable {
    public static final Parcelable.Creator<NotificationVisibility> CREATOR;
    private static final int MAX_POOL_SIZE = 25;
    private static final String TAG = "NoViz";
    private static int sNexrId;
    public int count;
    int id;
    public String key;
    public NotificationLocation location;
    public int rank;
    public boolean visible = true;

    static {
        sNexrId = 0;
        CREATOR = new Parcelable.Creator<NotificationVisibility>(){

            @Override
            public NotificationVisibility createFromParcel(Parcel parcel) {
                return NotificationVisibility.obtain(parcel);
            }

            public NotificationVisibility[] newArray(int n) {
                return new NotificationVisibility[n];
            }
        };
    }

    private NotificationVisibility() {
        int n = sNexrId;
        sNexrId = n + 1;
        this.id = n;
    }

    private NotificationVisibility(String string2, int n, int n2, boolean bl, NotificationLocation notificationLocation) {
        this();
        this.key = string2;
        this.rank = n;
        this.count = n2;
        this.visible = bl;
        this.location = notificationLocation;
    }

    private static NotificationVisibility obtain() {
        return new NotificationVisibility();
    }

    private static NotificationVisibility obtain(Parcel parcel) {
        NotificationVisibility notificationVisibility = NotificationVisibility.obtain();
        notificationVisibility.readFromParcel(parcel);
        return notificationVisibility;
    }

    public static NotificationVisibility obtain(String string2, int n, int n2, boolean bl) {
        return NotificationVisibility.obtain(string2, n, n2, bl, NotificationLocation.LOCATION_UNKNOWN);
    }

    public static NotificationVisibility obtain(String string2, int n, int n2, boolean bl, NotificationLocation notificationLocation) {
        NotificationVisibility notificationVisibility = NotificationVisibility.obtain();
        notificationVisibility.key = string2;
        notificationVisibility.rank = n;
        notificationVisibility.count = n2;
        notificationVisibility.visible = bl;
        notificationVisibility.location = notificationLocation;
        return notificationVisibility;
    }

    private void readFromParcel(Parcel parcel) {
        this.key = parcel.readString();
        this.rank = parcel.readInt();
        this.count = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.visible = bl;
        this.location = NotificationLocation.valueOf(parcel.readString());
    }

    public NotificationVisibility clone() {
        return NotificationVisibility.obtain(this.key, this.rank, this.count, this.visible, this.location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof NotificationVisibility;
        boolean bl2 = false;
        if (bl) {
            object = (NotificationVisibility)object;
            if (this.key == null && ((NotificationVisibility)object).key == null || this.key.equals(((NotificationVisibility)object).key)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int hashCode() {
        String string2 = this.key;
        int n = string2 == null ? 0 : string2.hashCode();
        return n;
    }

    public void recycle() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NotificationVisibility(id=");
        stringBuilder.append(this.id);
        stringBuilder.append(" key=");
        stringBuilder.append(this.key);
        stringBuilder.append(" rank=");
        stringBuilder.append(this.rank);
        stringBuilder.append(" count=");
        stringBuilder.append(this.count);
        String string2 = this.visible ? " visible" : "";
        stringBuilder.append(string2);
        stringBuilder.append(" location=");
        stringBuilder.append(this.location.name());
        stringBuilder.append(" )");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.key);
        parcel.writeInt(this.rank);
        parcel.writeInt(this.count);
        parcel.writeInt((int)this.visible);
        parcel.writeString(this.location.name());
    }

    public static enum NotificationLocation {
        LOCATION_UNKNOWN(0),
        LOCATION_FIRST_HEADS_UP(1),
        LOCATION_HIDDEN_TOP(2),
        LOCATION_MAIN_AREA(3),
        LOCATION_BOTTOM_STACK_PEEKING(4),
        LOCATION_BOTTOM_STACK_HIDDEN(5),
        LOCATION_GONE(6);
        
        private final int mMetricsEventNotificationLocation;

        private NotificationLocation(int n2) {
            this.mMetricsEventNotificationLocation = n2;
        }

        public int toMetricsEventEnum() {
            return this.mMetricsEventNotificationLocation;
        }
    }

}


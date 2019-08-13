/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.NotificationListenerService;

public class NotificationRankingUpdate
implements Parcelable {
    public static final Parcelable.Creator<NotificationRankingUpdate> CREATOR = new Parcelable.Creator<NotificationRankingUpdate>(){

        @Override
        public NotificationRankingUpdate createFromParcel(Parcel parcel) {
            return new NotificationRankingUpdate(parcel);
        }

        public NotificationRankingUpdate[] newArray(int n) {
            return new NotificationRankingUpdate[n];
        }
    };
    private final NotificationListenerService.RankingMap mRankingMap;

    public NotificationRankingUpdate(Parcel parcel) {
        this.mRankingMap = (NotificationListenerService.RankingMap)parcel.readParcelable(this.getClass().getClassLoader());
    }

    public NotificationRankingUpdate(NotificationListenerService.Ranking[] arrranking) {
        this.mRankingMap = new NotificationListenerService.RankingMap(arrranking);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (NotificationRankingUpdate)object;
            return this.mRankingMap.equals(((NotificationRankingUpdate)object).mRankingMap);
        }
        return false;
    }

    public NotificationListenerService.RankingMap getRankingMap() {
        return this.mRankingMap;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mRankingMap, n);
    }

}


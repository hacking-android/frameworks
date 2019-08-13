/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class NetworkScorerAppData
implements Parcelable {
    public static final Parcelable.Creator<NetworkScorerAppData> CREATOR = new Parcelable.Creator<NetworkScorerAppData>(){

        @Override
        public NetworkScorerAppData createFromParcel(Parcel parcel) {
            return new NetworkScorerAppData(parcel);
        }

        public NetworkScorerAppData[] newArray(int n) {
            return new NetworkScorerAppData[n];
        }
    };
    private final ComponentName mEnableUseOpenWifiActivity;
    private final String mNetworkAvailableNotificationChannelId;
    private final ComponentName mRecommendationService;
    private final String mRecommendationServiceLabel;
    public final int packageUid;

    public NetworkScorerAppData(int n, ComponentName componentName, String string2, ComponentName componentName2, String string3) {
        this.packageUid = n;
        this.mRecommendationService = componentName;
        this.mRecommendationServiceLabel = string2;
        this.mEnableUseOpenWifiActivity = componentName2;
        this.mNetworkAvailableNotificationChannelId = string3;
    }

    protected NetworkScorerAppData(Parcel parcel) {
        this.packageUid = parcel.readInt();
        this.mRecommendationService = ComponentName.readFromParcel(parcel);
        this.mRecommendationServiceLabel = parcel.readString();
        this.mEnableUseOpenWifiActivity = ComponentName.readFromParcel(parcel);
        this.mNetworkAvailableNotificationChannelId = parcel.readString();
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
            object = (NetworkScorerAppData)object;
            if (!(this.packageUid == ((NetworkScorerAppData)object).packageUid && Objects.equals(this.mRecommendationService, ((NetworkScorerAppData)object).mRecommendationService) && Objects.equals(this.mRecommendationServiceLabel, ((NetworkScorerAppData)object).mRecommendationServiceLabel) && Objects.equals(this.mEnableUseOpenWifiActivity, ((NetworkScorerAppData)object).mEnableUseOpenWifiActivity) && Objects.equals(this.mNetworkAvailableNotificationChannelId, ((NetworkScorerAppData)object).mNetworkAvailableNotificationChannelId))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public ComponentName getEnableUseOpenWifiActivity() {
        return this.mEnableUseOpenWifiActivity;
    }

    public String getNetworkAvailableNotificationChannelId() {
        return this.mNetworkAvailableNotificationChannelId;
    }

    public ComponentName getRecommendationServiceComponent() {
        return this.mRecommendationService;
    }

    public String getRecommendationServiceLabel() {
        return this.mRecommendationServiceLabel;
    }

    public String getRecommendationServicePackageName() {
        return this.mRecommendationService.getPackageName();
    }

    public int hashCode() {
        return Objects.hash(this.packageUid, this.mRecommendationService, this.mRecommendationServiceLabel, this.mEnableUseOpenWifiActivity, this.mNetworkAvailableNotificationChannelId);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NetworkScorerAppData{packageUid=");
        stringBuilder.append(this.packageUid);
        stringBuilder.append(", mRecommendationService=");
        stringBuilder.append(this.mRecommendationService);
        stringBuilder.append(", mRecommendationServiceLabel=");
        stringBuilder.append(this.mRecommendationServiceLabel);
        stringBuilder.append(", mEnableUseOpenWifiActivity=");
        stringBuilder.append(this.mEnableUseOpenWifiActivity);
        stringBuilder.append(", mNetworkAvailableNotificationChannelId=");
        stringBuilder.append(this.mNetworkAvailableNotificationChannelId);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.packageUid);
        ComponentName.writeToParcel(this.mRecommendationService, parcel);
        parcel.writeString(this.mRecommendationServiceLabel);
        ComponentName.writeToParcel(this.mEnableUseOpenWifiActivity, parcel);
        parcel.writeString(this.mNetworkAvailableNotificationChannelId);
    }

}


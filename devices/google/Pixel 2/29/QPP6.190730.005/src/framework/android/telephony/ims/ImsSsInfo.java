/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class ImsSsInfo
implements Parcelable {
    public static final int CLIR_OUTGOING_DEFAULT = 0;
    public static final int CLIR_OUTGOING_INVOCATION = 1;
    public static final int CLIR_OUTGOING_SUPPRESSION = 2;
    public static final int CLIR_STATUS_NOT_PROVISIONED = 0;
    public static final int CLIR_STATUS_PROVISIONED_PERMANENT = 1;
    public static final int CLIR_STATUS_TEMPORARILY_ALLOWED = 4;
    public static final int CLIR_STATUS_TEMPORARILY_RESTRICTED = 3;
    public static final int CLIR_STATUS_UNKNOWN = 2;
    public static final Parcelable.Creator<ImsSsInfo> CREATOR = new Parcelable.Creator<ImsSsInfo>(){

        @Override
        public ImsSsInfo createFromParcel(Parcel parcel) {
            return new ImsSsInfo(parcel);
        }

        public ImsSsInfo[] newArray(int n) {
            return new ImsSsInfo[n];
        }
    };
    public static final int DISABLED = 0;
    public static final int ENABLED = 1;
    public static final int NOT_REGISTERED = -1;
    public static final int SERVICE_NOT_PROVISIONED = 0;
    public static final int SERVICE_PROVISIONED = 1;
    public static final int SERVICE_PROVISIONING_UNKNOWN = -1;
    private int mClirInterrogationStatus = 2;
    private int mClirOutgoingState = 0;
    @UnsupportedAppUsage
    public String mIcbNum;
    public int mProvisionStatus = -1;
    @UnsupportedAppUsage
    public int mStatus;

    @UnsupportedAppUsage
    public ImsSsInfo() {
    }

    @Deprecated
    public ImsSsInfo(int n, String string2) {
        this.mStatus = n;
        this.mIcbNum = string2;
    }

    private ImsSsInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    private static String provisionStatusToString(int n) {
        if (n != 0) {
            if (n != 1) {
                return "Service provisioning unknown";
            }
            return "Service provisioned";
        }
        return "Service not provisioned";
    }

    private void readFromParcel(Parcel parcel) {
        this.mStatus = parcel.readInt();
        this.mIcbNum = parcel.readString();
        this.mProvisionStatus = parcel.readInt();
        this.mClirInterrogationStatus = parcel.readInt();
        this.mClirOutgoingState = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getClirInterrogationStatus() {
        return this.mClirInterrogationStatus;
    }

    public int getClirOutgoingState() {
        return this.mClirOutgoingState;
    }

    @Deprecated
    public String getIcbNum() {
        return this.mIcbNum;
    }

    public String getIncomingCommunicationBarringNumber() {
        return this.mIcbNum;
    }

    public int getProvisionStatus() {
        return this.mProvisionStatus;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(", Status: ");
        String string2 = this.mStatus == 0 ? "disabled" : "enabled";
        stringBuilder.append(string2);
        stringBuilder.append(", ProvisionStatus: ");
        stringBuilder.append(ImsSsInfo.provisionStatusToString(this.mProvisionStatus));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mStatus);
        parcel.writeString(this.mIcbNum);
        parcel.writeInt(this.mProvisionStatus);
        parcel.writeInt(this.mClirInterrogationStatus);
        parcel.writeInt(this.mClirOutgoingState);
    }

    public static final class Builder {
        private final ImsSsInfo mImsSsInfo = new ImsSsInfo();

        public Builder(int n) {
            this.mImsSsInfo.mStatus = n;
        }

        public ImsSsInfo build() {
            return this.mImsSsInfo;
        }

        public Builder setClirInterrogationStatus(int n) {
            this.mImsSsInfo.mClirInterrogationStatus = n;
            return this;
        }

        public Builder setClirOutgoingState(int n) {
            this.mImsSsInfo.mClirOutgoingState = n;
            return this;
        }

        public Builder setIncomingCommunicationBarringNumber(String string2) {
            this.mImsSsInfo.mIcbNum = string2;
            return this;
        }

        public Builder setProvisionStatus(int n) {
            this.mImsSsInfo.mProvisionStatus = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ClirInterrogationStatus {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ClirOutgoingState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ServiceProvisionStatus {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ServiceStatus {
    }

}


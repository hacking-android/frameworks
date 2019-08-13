/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.UiccAccessRule;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DownloadableSubscription
implements Parcelable {
    public static final Parcelable.Creator<DownloadableSubscription> CREATOR = new Parcelable.Creator<DownloadableSubscription>(){

        @Override
        public DownloadableSubscription createFromParcel(Parcel parcel) {
            return new DownloadableSubscription(parcel);
        }

        public DownloadableSubscription[] newArray(int n) {
            return new DownloadableSubscription[n];
        }
    };
    private List<UiccAccessRule> accessRules;
    private String carrierName;
    private String confirmationCode;
    @Deprecated
    @UnsupportedAppUsage
    public final String encodedActivationCode;

    private DownloadableSubscription(Parcel parcel) {
        this.encodedActivationCode = parcel.readString();
        this.confirmationCode = parcel.readString();
        this.carrierName = parcel.readString();
        this.accessRules = new ArrayList<UiccAccessRule>();
        parcel.readTypedList(this.accessRules, UiccAccessRule.CREATOR);
    }

    private DownloadableSubscription(String string2) {
        this.encodedActivationCode = string2;
    }

    private DownloadableSubscription(String string2, String string3, String string4, List<UiccAccessRule> list) {
        this.encodedActivationCode = string2;
        this.confirmationCode = string3;
        this.carrierName = string4;
        this.accessRules = list;
    }

    public static DownloadableSubscription forActivationCode(String string2) {
        Preconditions.checkNotNull(string2, "Activation code may not be null");
        return new DownloadableSubscription(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public List<UiccAccessRule> getAccessRules() {
        return this.accessRules;
    }

    @SystemApi
    public String getCarrierName() {
        return this.carrierName;
    }

    public String getConfirmationCode() {
        return this.confirmationCode;
    }

    public String getEncodedActivationCode() {
        return this.encodedActivationCode;
    }

    @Deprecated
    public void setAccessRules(List<UiccAccessRule> list) {
        this.accessRules = list;
    }

    @Deprecated
    @UnsupportedAppUsage
    public void setAccessRules(UiccAccessRule[] arruiccAccessRule) {
        this.accessRules = Arrays.asList(arruiccAccessRule);
    }

    @Deprecated
    @UnsupportedAppUsage
    public void setCarrierName(String string2) {
        this.carrierName = string2;
    }

    @Deprecated
    public void setConfirmationCode(String string2) {
        this.confirmationCode = string2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.encodedActivationCode);
        parcel.writeString(this.confirmationCode);
        parcel.writeString(this.carrierName);
        parcel.writeTypedList(this.accessRules);
    }

    @SystemApi
    public static final class Builder {
        List<UiccAccessRule> accessRules;
        private String carrierName;
        private String confirmationCode;
        private String encodedActivationCode;

        public Builder() {
        }

        public Builder(DownloadableSubscription downloadableSubscription) {
            this.encodedActivationCode = downloadableSubscription.getEncodedActivationCode();
            this.confirmationCode = downloadableSubscription.getConfirmationCode();
            this.carrierName = downloadableSubscription.getCarrierName();
            this.accessRules = downloadableSubscription.getAccessRules();
        }

        public DownloadableSubscription build() {
            return new DownloadableSubscription(this.encodedActivationCode, this.confirmationCode, this.carrierName, this.accessRules);
        }

        public Builder setAccessRules(List<UiccAccessRule> list) {
            this.accessRules = list;
            return this;
        }

        public Builder setCarrierName(String string2) {
            this.carrierName = string2;
            return this;
        }

        public Builder setConfirmationCode(String string2) {
            this.confirmationCode = string2;
            return this;
        }

        public Builder setEncodedActivationCode(String string2) {
            this.encodedActivationCode = string2;
            return this;
        }
    }

}


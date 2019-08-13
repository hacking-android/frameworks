/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.PhoneAccountHandle;

public final class VisualVoicemailSms
implements Parcelable {
    public static final Parcelable.Creator<VisualVoicemailSms> CREATOR = new Parcelable.Creator<VisualVoicemailSms>(){

        @Override
        public VisualVoicemailSms createFromParcel(Parcel parcel) {
            return new Builder().setPhoneAccountHandle((PhoneAccountHandle)parcel.readParcelable(null)).setPrefix(parcel.readString()).setFields(parcel.readBundle()).setMessageBody(parcel.readString()).build();
        }

        public VisualVoicemailSms[] newArray(int n) {
            return new VisualVoicemailSms[n];
        }
    };
    private final Bundle mFields;
    private final String mMessageBody;
    private final PhoneAccountHandle mPhoneAccountHandle;
    private final String mPrefix;

    VisualVoicemailSms(Builder builder) {
        this.mPhoneAccountHandle = builder.mPhoneAccountHandle;
        this.mPrefix = builder.mPrefix;
        this.mFields = builder.mFields;
        this.mMessageBody = builder.mMessageBody;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Bundle getFields() {
        return this.mFields;
    }

    public String getMessageBody() {
        return this.mMessageBody;
    }

    public PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccountHandle;
    }

    public String getPrefix() {
        return this.mPrefix;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.getPhoneAccountHandle(), n);
        parcel.writeString(this.getPrefix());
        parcel.writeBundle(this.getFields());
        parcel.writeString(this.getMessageBody());
    }

    public static class Builder {
        private Bundle mFields;
        private String mMessageBody;
        private PhoneAccountHandle mPhoneAccountHandle;
        private String mPrefix;

        public VisualVoicemailSms build() {
            return new VisualVoicemailSms(this);
        }

        public Builder setFields(Bundle bundle) {
            this.mFields = bundle;
            return this;
        }

        public Builder setMessageBody(String string2) {
            this.mMessageBody = string2;
            return this;
        }

        public Builder setPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
            this.mPhoneAccountHandle = phoneAccountHandle;
            return this;
        }

        public Builder setPrefix(String string2) {
            this.mPrefix = string2;
            return this;
        }
    }

}


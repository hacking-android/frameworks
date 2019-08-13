/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public final class UssdResponse
implements Parcelable {
    public static final Parcelable.Creator<UssdResponse> CREATOR = new Parcelable.Creator<UssdResponse>(){

        @Override
        public UssdResponse createFromParcel(Parcel parcel) {
            return new UssdResponse(parcel.readString(), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel));
        }

        public UssdResponse[] newArray(int n) {
            return new UssdResponse[n];
        }
    };
    private CharSequence mReturnMessage;
    private String mUssdRequest;

    public UssdResponse(String string2, CharSequence charSequence) {
        this.mUssdRequest = string2;
        this.mReturnMessage = charSequence;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CharSequence getReturnMessage() {
        return this.mReturnMessage;
    }

    public String getUssdRequest() {
        return this.mUssdRequest;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mUssdRequest);
        TextUtils.writeToParcel(this.mReturnMessage, parcel, 0);
    }

}


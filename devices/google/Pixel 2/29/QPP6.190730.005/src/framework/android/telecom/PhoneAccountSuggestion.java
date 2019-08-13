/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.PhoneAccountHandle;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class PhoneAccountSuggestion
implements Parcelable {
    public static final Parcelable.Creator<PhoneAccountSuggestion> CREATOR = new Parcelable.Creator<PhoneAccountSuggestion>(){

        @Override
        public PhoneAccountSuggestion createFromParcel(Parcel parcel) {
            return new PhoneAccountSuggestion(parcel);
        }

        public PhoneAccountSuggestion[] newArray(int n) {
            return new PhoneAccountSuggestion[n];
        }
    };
    public static final int REASON_FREQUENT = 2;
    public static final int REASON_INTRA_CARRIER = 1;
    public static final int REASON_NONE = 0;
    public static final int REASON_OTHER = 4;
    public static final int REASON_USER_SET = 3;
    private PhoneAccountHandle mHandle;
    private int mReason;
    private boolean mShouldAutoSelect;

    private PhoneAccountSuggestion(Parcel parcel) {
        this.mHandle = (PhoneAccountHandle)parcel.readParcelable(PhoneAccountHandle.class.getClassLoader());
        this.mReason = parcel.readInt();
        boolean bl = parcel.readByte() != 0;
        this.mShouldAutoSelect = bl;
    }

    public PhoneAccountSuggestion(PhoneAccountHandle phoneAccountHandle, int n, boolean bl) {
        this.mHandle = phoneAccountHandle;
        this.mReason = n;
        this.mShouldAutoSelect = bl;
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
            object = (PhoneAccountSuggestion)object;
            if (this.mReason != ((PhoneAccountSuggestion)object).mReason || this.mShouldAutoSelect != ((PhoneAccountSuggestion)object).mShouldAutoSelect || !Objects.equals(this.mHandle, ((PhoneAccountSuggestion)object).mHandle)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public PhoneAccountHandle getPhoneAccountHandle() {
        return this.mHandle;
    }

    public int getReason() {
        return this.mReason;
    }

    public int hashCode() {
        return Objects.hash(this.mHandle, this.mReason, this.mShouldAutoSelect);
    }

    public boolean shouldAutoSelect() {
        return this.mShouldAutoSelect;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mHandle, n);
        parcel.writeInt(this.mReason);
        parcel.writeByte((byte)(this.mShouldAutoSelect ? 1 : 0));
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SuggestionReason {
    }

}


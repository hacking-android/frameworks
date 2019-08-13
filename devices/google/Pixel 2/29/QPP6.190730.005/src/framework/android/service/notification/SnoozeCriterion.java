/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class SnoozeCriterion
implements Parcelable {
    public static final Parcelable.Creator<SnoozeCriterion> CREATOR = new Parcelable.Creator<SnoozeCriterion>(){

        @Override
        public SnoozeCriterion createFromParcel(Parcel parcel) {
            return new SnoozeCriterion(parcel);
        }

        public SnoozeCriterion[] newArray(int n) {
            return new SnoozeCriterion[n];
        }
    };
    private final CharSequence mConfirmation;
    private final CharSequence mExplanation;
    private final String mId;

    protected SnoozeCriterion(Parcel parcel) {
        this.mId = parcel.readByte() != 0 ? parcel.readString() : null;
        this.mExplanation = parcel.readByte() != 0 ? parcel.readCharSequence() : null;
        this.mConfirmation = parcel.readByte() != 0 ? parcel.readCharSequence() : null;
    }

    public SnoozeCriterion(String string2, CharSequence charSequence, CharSequence charSequence2) {
        this.mId = string2;
        this.mExplanation = charSequence;
        this.mConfirmation = charSequence2;
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
            object = (SnoozeCriterion)object;
            CharSequence charSequence = this.mId;
            if (charSequence != null ? !((String)charSequence).equals(((SnoozeCriterion)object).mId) : ((SnoozeCriterion)object).mId != null) {
                return false;
            }
            charSequence = this.mExplanation;
            if (charSequence != null ? !charSequence.equals(((SnoozeCriterion)object).mExplanation) : ((SnoozeCriterion)object).mExplanation != null) {
                return false;
            }
            charSequence = this.mConfirmation;
            if (charSequence != null) {
                bl = charSequence.equals(((SnoozeCriterion)object).mConfirmation);
            } else if (((SnoozeCriterion)object).mConfirmation != null) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public CharSequence getConfirmation() {
        return this.mConfirmation;
    }

    public CharSequence getExplanation() {
        return this.mExplanation;
    }

    public String getId() {
        return this.mId;
    }

    public int hashCode() {
        CharSequence charSequence = this.mId;
        int n = 0;
        int n2 = charSequence != null ? ((String)charSequence).hashCode() : 0;
        charSequence = this.mExplanation;
        int n3 = charSequence != null ? charSequence.hashCode() : 0;
        charSequence = this.mConfirmation;
        if (charSequence != null) {
            n = charSequence.hashCode();
        }
        return (n2 * 31 + n3) * 31 + n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mId != null) {
            parcel.writeByte((byte)1);
            parcel.writeString(this.mId);
        } else {
            parcel.writeByte((byte)0);
        }
        if (this.mExplanation != null) {
            parcel.writeByte((byte)1);
            parcel.writeCharSequence(this.mExplanation);
        } else {
            parcel.writeByte((byte)0);
        }
        if (this.mConfirmation != null) {
            parcel.writeByte((byte)1);
            parcel.writeCharSequence(this.mConfirmation);
        } else {
            parcel.writeByte((byte)0);
        }
    }

}


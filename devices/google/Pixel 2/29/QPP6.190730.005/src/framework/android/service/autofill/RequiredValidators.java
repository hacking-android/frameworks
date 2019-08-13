/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalValidator;
import android.service.autofill.ValueFinder;
import android.util.Log;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;

final class RequiredValidators
extends InternalValidator {
    public static final Parcelable.Creator<RequiredValidators> CREATOR = new Parcelable.Creator<RequiredValidators>(){

        @Override
        public RequiredValidators createFromParcel(Parcel parcel) {
            return new RequiredValidators((InternalValidator[])parcel.readParcelableArray(null, InternalValidator.class));
        }

        public RequiredValidators[] newArray(int n) {
            return new RequiredValidators[n];
        }
    };
    private static final String TAG = "RequiredValidators";
    private final InternalValidator[] mValidators;

    RequiredValidators(InternalValidator[] arrinternalValidator) {
        this.mValidators = Preconditions.checkArrayElementsNotNull(arrinternalValidator, "validators");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean isValid(ValueFinder valueFinder) {
        for (InternalValidator internalValidator : this.mValidators) {
            boolean bl = internalValidator.isValid(valueFinder);
            if (Helper.sDebug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("isValid(");
                stringBuilder.append(internalValidator);
                stringBuilder.append("): ");
                stringBuilder.append(bl);
                Log.d(TAG, stringBuilder.toString());
            }
            if (bl) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("RequiredValidators: [validators=");
        stringBuilder.append(this.mValidators);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelableArray((Parcelable[])this.mValidators, n);
    }

}


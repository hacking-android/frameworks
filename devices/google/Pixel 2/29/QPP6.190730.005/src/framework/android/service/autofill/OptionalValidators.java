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

final class OptionalValidators
extends InternalValidator {
    public static final Parcelable.Creator<OptionalValidators> CREATOR = new Parcelable.Creator<OptionalValidators>(){

        @Override
        public OptionalValidators createFromParcel(Parcel parcel) {
            return new OptionalValidators((InternalValidator[])parcel.readParcelableArray(null, InternalValidator.class));
        }

        public OptionalValidators[] newArray(int n) {
            return new OptionalValidators[n];
        }
    };
    private static final String TAG = "OptionalValidators";
    private final InternalValidator[] mValidators;

    OptionalValidators(InternalValidator[] arrinternalValidator) {
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
            if (!bl) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("OptionalValidators: [validators=");
        stringBuilder.append(this.mValidators);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelableArray((Parcelable[])this.mValidators, n);
    }

}


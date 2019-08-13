/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalValidator;
import android.service.autofill.ValueFinder;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;

final class NegationValidator
extends InternalValidator {
    public static final Parcelable.Creator<NegationValidator> CREATOR = new Parcelable.Creator<NegationValidator>(){

        @Override
        public NegationValidator createFromParcel(Parcel parcel) {
            return new NegationValidator((InternalValidator)parcel.readParcelable(null));
        }

        public NegationValidator[] newArray(int n) {
            return new NegationValidator[n];
        }
    };
    private final InternalValidator mValidator;

    NegationValidator(InternalValidator internalValidator) {
        this.mValidator = Preconditions.checkNotNull(internalValidator);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean isValid(ValueFinder valueFinder) {
        return this.mValidator.isValid(valueFinder) ^ true;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NegationValidator: [validator=");
        stringBuilder.append(this.mValidator);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mValidator, n);
    }

}


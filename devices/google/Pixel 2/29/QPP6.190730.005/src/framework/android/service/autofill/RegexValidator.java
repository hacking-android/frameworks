/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalValidator;
import android.service.autofill.Validator;
import android.service.autofill.ValueFinder;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexValidator
extends InternalValidator
implements Validator,
Parcelable {
    public static final Parcelable.Creator<RegexValidator> CREATOR = new Parcelable.Creator<RegexValidator>(){

        @Override
        public RegexValidator createFromParcel(Parcel parcel) {
            return new RegexValidator((AutofillId)parcel.readParcelable(null), (Pattern)parcel.readSerializable());
        }

        public RegexValidator[] newArray(int n) {
            return new RegexValidator[n];
        }
    };
    private static final String TAG = "RegexValidator";
    private final AutofillId mId;
    private final Pattern mRegex;

    public RegexValidator(AutofillId autofillId, Pattern pattern) {
        this.mId = Preconditions.checkNotNull(autofillId);
        this.mRegex = Preconditions.checkNotNull(pattern);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean isValid(ValueFinder object) {
        if ((object = object.findByAutofillId(this.mId)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No view for id ");
            ((StringBuilder)object).append(this.mId);
            Log.w(TAG, ((StringBuilder)object).toString());
            return false;
        }
        boolean bl = this.mRegex.matcher((CharSequence)object).matches();
        if (Helper.sDebug) {
            object = new StringBuilder();
            ((StringBuilder)object).append("isValid(): ");
            ((StringBuilder)object).append(bl);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        return bl;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RegexValidator: [id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", regex=");
        stringBuilder.append(this.mRegex);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mId, n);
        parcel.writeSerializable(this.mRegex);
    }

}


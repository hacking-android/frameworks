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
import java.util.Arrays;

public final class LuhnChecksumValidator
extends InternalValidator
implements Validator,
Parcelable {
    public static final Parcelable.Creator<LuhnChecksumValidator> CREATOR = new Parcelable.Creator<LuhnChecksumValidator>(){

        @Override
        public LuhnChecksumValidator createFromParcel(Parcel parcel) {
            return new LuhnChecksumValidator((AutofillId[])parcel.readParcelableArray(null, AutofillId.class));
        }

        public LuhnChecksumValidator[] newArray(int n) {
            return new LuhnChecksumValidator[n];
        }
    };
    private static final String TAG = "LuhnChecksumValidator";
    private final AutofillId[] mIds;

    public LuhnChecksumValidator(AutofillId ... arrautofillId) {
        this.mIds = Preconditions.checkArrayElementsNotNull(arrautofillId, "ids");
    }

    private static boolean isLuhnChecksumValid(String string2) {
        int n = 0;
        int n2 = 0;
        int n3 = string2.length();
        boolean bl = true;
        int n4 = n3 - 1;
        do {
            int n5 = 0;
            if (n4 < 0) break;
            int n6 = string2.charAt(n4) - 48;
            int n7 = n;
            n3 = n2;
            if (n6 >= 0) {
                if (n6 > 9) {
                    n7 = n;
                    n3 = n2;
                } else {
                    if (n2 != 0) {
                        n3 = n7 = n6 * 2;
                        if (n7 > 9) {
                            n3 = n7 - 9;
                        }
                    } else {
                        n3 = n6;
                    }
                    n7 = n + n3;
                    n3 = n5;
                    if (n2 == 0) {
                        n3 = 1;
                    }
                }
            }
            --n4;
            n = n7;
            n2 = n3;
        } while (true);
        if (n % 10 != 0) {
            bl = false;
        }
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean isValid(ValueFinder object) {
        AutofillId[] object22 = this.mIds;
        if (object22 != null && object22.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (AutofillId autofillId : this.mIds) {
                String string2 = object.findByAutofillId(autofillId);
                if (string2 == null) {
                    if (Helper.sDebug) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("No partial number for id ");
                        ((StringBuilder)object).append(autofillId);
                        Log.d(TAG, ((StringBuilder)object).toString());
                    }
                    return false;
                }
                stringBuilder.append(string2);
            }
            object = stringBuilder.toString();
            boolean bl = LuhnChecksumValidator.isLuhnChecksumValid((String)object);
            if (Helper.sDebug) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("isValid(");
                stringBuilder2.append(((String)object).length());
                stringBuilder2.append(" chars): ");
                stringBuilder2.append(bl);
                Log.d(TAG, stringBuilder2.toString());
            }
            return bl;
        }
        return false;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LuhnChecksumValidator: [ids=");
        stringBuilder.append(Arrays.toString(this.mIds));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelableArray((Parcelable[])this.mIds, n);
    }

}


/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DateFormat
 */
package android.service.autofill;

import android.icu.text.DateFormat;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalSanitizer;
import android.service.autofill.Sanitizer;
import android.util.Log;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.Date;

public final class DateValueSanitizer
extends InternalSanitizer
implements Sanitizer,
Parcelable {
    public static final Parcelable.Creator<DateValueSanitizer> CREATOR = new Parcelable.Creator<DateValueSanitizer>(){

        @Override
        public DateValueSanitizer createFromParcel(Parcel parcel) {
            return new DateValueSanitizer((DateFormat)parcel.readSerializable());
        }

        public DateValueSanitizer[] newArray(int n) {
            return new DateValueSanitizer[n];
        }
    };
    private static final String TAG = "DateValueSanitizer";
    private final DateFormat mDateFormat;

    public DateValueSanitizer(DateFormat dateFormat) {
        this.mDateFormat = Preconditions.checkNotNull(dateFormat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public AutofillValue sanitize(AutofillValue autofillValue) {
        if (autofillValue == null) {
            Log.w(TAG, "sanitize() called with null value");
            return null;
        }
        if (!autofillValue.isDate()) {
            if (Helper.sDebug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(autofillValue);
                stringBuilder.append(" is not a date");
                Log.d(TAG, stringBuilder.toString());
            }
            return null;
        }
        try {
            Object object;
            Date date = new Date(autofillValue.getDateValue());
            String string2 = this.mDateFormat.format(date);
            if (Helper.sDebug) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Transformed ");
                ((StringBuilder)object).append(date);
                ((StringBuilder)object).append(" to ");
                ((StringBuilder)object).append(string2);
                Log.d(TAG, ((StringBuilder)object).toString());
            }
            date = this.mDateFormat.parse(string2);
            if (Helper.sDebug) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Sanitized to ");
                ((StringBuilder)object).append(date);
                Log.d(TAG, ((StringBuilder)object).toString());
            }
            object = AutofillValue.forDate(date.getTime());
            return object;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not apply ");
            stringBuilder.append((Object)this.mDateFormat);
            stringBuilder.append(" to ");
            stringBuilder.append(autofillValue);
            stringBuilder.append(": ");
            stringBuilder.append(exception);
            Log.w(TAG, stringBuilder.toString());
            return null;
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DateValueSanitizer: [dateFormat=");
        stringBuilder.append((Object)this.mDateFormat);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeSerializable((Serializable)this.mDateFormat);
    }

}


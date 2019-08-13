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
import android.service.autofill.InternalTransformation;
import android.service.autofill.Transformation;
import android.service.autofill.ValueFinder;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.Date;

public final class DateTransformation
extends InternalTransformation
implements Transformation,
Parcelable {
    public static final Parcelable.Creator<DateTransformation> CREATOR = new Parcelable.Creator<DateTransformation>(){

        @Override
        public DateTransformation createFromParcel(Parcel parcel) {
            return new DateTransformation((AutofillId)parcel.readParcelable(null), (DateFormat)parcel.readSerializable());
        }

        public DateTransformation[] newArray(int n) {
            return new DateTransformation[n];
        }
    };
    private static final String TAG = "DateTransformation";
    private final DateFormat mDateFormat;
    private final AutofillId mFieldId;

    public DateTransformation(AutofillId autofillId, DateFormat dateFormat) {
        this.mFieldId = Preconditions.checkNotNull(autofillId);
        this.mDateFormat = Preconditions.checkNotNull(dateFormat);
    }

    @Override
    public void apply(ValueFinder object, RemoteViews object2, int n) throws Exception {
        if ((object = object.findRawValueByAutofillId(this.mFieldId)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No value for id ");
            ((StringBuilder)object).append(this.mFieldId);
            Log.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        if (!((AutofillValue)object).isDate()) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Value for ");
            ((StringBuilder)object2).append(this.mFieldId);
            ((StringBuilder)object2).append(" is not date: ");
            ((StringBuilder)object2).append(object);
            Log.w(TAG, ((StringBuilder)object2).toString());
            return;
        }
        try {
            Date date = new Date(((AutofillValue)object).getDateValue());
            String string2 = this.mDateFormat.format(date);
            if (Helper.sDebug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Transformed ");
                stringBuilder.append(date);
                stringBuilder.append(" to ");
                stringBuilder.append(string2);
                Log.d(TAG, stringBuilder.toString());
            }
            ((RemoteViews)object2).setCharSequence(n, "setText", string2);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not apply ");
            stringBuilder.append((Object)this.mDateFormat);
            stringBuilder.append(" to ");
            stringBuilder.append(object);
            stringBuilder.append(": ");
            stringBuilder.append(exception);
            Log.w(TAG, stringBuilder.toString());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DateTransformation: [id=");
        stringBuilder.append(this.mFieldId);
        stringBuilder.append(", format=");
        stringBuilder.append((Object)this.mDateFormat);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mFieldId, n);
        parcel.writeSerializable((Serializable)this.mDateFormat);
    }

}


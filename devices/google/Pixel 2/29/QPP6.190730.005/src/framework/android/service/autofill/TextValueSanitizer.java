/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalSanitizer;
import android.service.autofill.Sanitizer;
import android.util.Slog;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextValueSanitizer
extends InternalSanitizer
implements Sanitizer,
Parcelable {
    public static final Parcelable.Creator<TextValueSanitizer> CREATOR = new Parcelable.Creator<TextValueSanitizer>(){

        @Override
        public TextValueSanitizer createFromParcel(Parcel parcel) {
            return new TextValueSanitizer((Pattern)parcel.readSerializable(), parcel.readString());
        }

        public TextValueSanitizer[] newArray(int n) {
            return new TextValueSanitizer[n];
        }
    };
    private static final String TAG = "TextValueSanitizer";
    private final Pattern mRegex;
    private final String mSubst;

    public TextValueSanitizer(Pattern pattern, String string2) {
        this.mRegex = Preconditions.checkNotNull(pattern);
        this.mSubst = Preconditions.checkNotNull(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public AutofillValue sanitize(AutofillValue object) {
        Object object2;
        block7 : {
            if (object == null) {
                Slog.w(TAG, "sanitize() called with null value");
                return null;
            }
            if (!((AutofillValue)object).isText()) {
                if (Helper.sDebug) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("sanitize() called with non-text value: ");
                    stringBuilder.append(object);
                    Slog.d(TAG, stringBuilder.toString());
                }
                return null;
            }
            object2 = ((AutofillValue)object).getTextValue();
            try {
                object2 = this.mRegex.matcher((CharSequence)object2);
                if (((Matcher)object2).matches()) break block7;
                if (Helper.sDebug) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("sanitize(): ");
                    ((StringBuilder)object2).append(this.mRegex);
                    ((StringBuilder)object2).append(" failed for ");
                    ((StringBuilder)object2).append(object);
                    Slog.d(TAG, ((StringBuilder)object2).toString());
                }
                return null;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Exception evaluating ");
                ((StringBuilder)object).append(this.mRegex);
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(this.mSubst);
                ((StringBuilder)object).append(": ");
                ((StringBuilder)object).append(exception);
                Slog.w(TAG, ((StringBuilder)object).toString());
                return null;
            }
        }
        object = AutofillValue.forText(((Matcher)object2).replaceAll(this.mSubst));
        return object;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return Object.super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TextValueSanitizer: [regex=");
        stringBuilder.append(this.mRegex);
        stringBuilder.append(", subst=");
        stringBuilder.append(this.mSubst);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeSerializable(this.mRegex);
        parcel.writeString(this.mSubst);
    }

}


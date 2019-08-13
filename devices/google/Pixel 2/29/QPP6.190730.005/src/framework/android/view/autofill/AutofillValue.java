/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.util.Objects;

public final class AutofillValue
implements Parcelable {
    public static final Parcelable.Creator<AutofillValue> CREATOR = new Parcelable.Creator<AutofillValue>(){

        @Override
        public AutofillValue createFromParcel(Parcel parcel) {
            return new AutofillValue(parcel);
        }

        public AutofillValue[] newArray(int n) {
            return new AutofillValue[n];
        }
    };
    private static final String TAG = "AutofillValue";
    private final int mType;
    private final Object mValue;

    private AutofillValue(int n, Object object) {
        this.mType = n;
        this.mValue = object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private AutofillValue(Parcel object) {
        int n = this.mType = ((Parcel)object).readInt();
        boolean bl = true;
        if (n == 1) {
            this.mValue = ((Parcel)object).readCharSequence();
            return;
        }
        if (n != 2) {
            if (n == 3) {
                this.mValue = ((Parcel)object).readInt();
                return;
            }
            if (n == 4) {
                this.mValue = ((Parcel)object).readLong();
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("type=");
            ((StringBuilder)object).append(this.mType);
            ((StringBuilder)object).append(" not valid");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (((Parcel)object).readInt() == 0) {
            bl = false;
        }
        this.mValue = bl;
    }

    public static AutofillValue forDate(long l) {
        return new AutofillValue(4, l);
    }

    public static AutofillValue forList(int n) {
        return new AutofillValue(3, n);
    }

    public static AutofillValue forText(CharSequence object) {
        if (Helper.sVerbose && !Looper.getMainLooper().isCurrentThread()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("forText() not called on main thread: ");
            stringBuilder.append(Thread.currentThread());
            Log.v(TAG, stringBuilder.toString());
        }
        object = object == null ? null : new AutofillValue(1, TextUtils.trimNoCopySpans((CharSequence)object));
        return object;
    }

    public static AutofillValue forToggle(boolean bl) {
        return new AutofillValue(2, bl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AutofillValue)object;
        if (this.mType != ((AutofillValue)object).mType) {
            return false;
        }
        if (this.isText()) {
            return this.mValue.toString().equals(((AutofillValue)object).mValue.toString());
        }
        return Objects.equals(this.mValue, ((AutofillValue)object).mValue);
    }

    public long getDateValue() {
        boolean bl = this.isDate();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value must be a date value, not type=");
        stringBuilder.append(this.mType);
        Preconditions.checkState(bl, stringBuilder.toString());
        return (Long)this.mValue;
    }

    public int getListValue() {
        boolean bl = this.isList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value must be a list value, not type=");
        stringBuilder.append(this.mType);
        Preconditions.checkState(bl, stringBuilder.toString());
        return (Integer)this.mValue;
    }

    public CharSequence getTextValue() {
        boolean bl = this.isText();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value must be a text value, not type=");
        stringBuilder.append(this.mType);
        Preconditions.checkState(bl, stringBuilder.toString());
        return (CharSequence)this.mValue;
    }

    public boolean getToggleValue() {
        boolean bl = this.isToggle();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value must be a toggle value, not type=");
        stringBuilder.append(this.mType);
        Preconditions.checkState(bl, stringBuilder.toString());
        return (Boolean)this.mValue;
    }

    public int hashCode() {
        return this.mType + this.mValue.hashCode();
    }

    public boolean isDate() {
        boolean bl = this.mType == 4;
        return bl;
    }

    public boolean isEmpty() {
        boolean bl = this.isText() && ((CharSequence)this.mValue).length() == 0;
        return bl;
    }

    public boolean isList() {
        boolean bl = this.mType == 3;
        return bl;
    }

    public boolean isText() {
        int n = this.mType;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isToggle() {
        boolean bl = this.mType == 2;
        return bl;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[type=");
        stringBuilder.append(this.mType);
        stringBuilder = stringBuilder.append(", value=");
        if (this.isText()) {
            Helper.appendRedacted(stringBuilder, (CharSequence)this.mValue);
        } else {
            stringBuilder.append(this.mValue);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        n = this.mType;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        parcel.writeLong((Long)this.mValue);
                    }
                } else {
                    parcel.writeInt((Integer)this.mValue);
                }
            } else {
                parcel.writeInt((int)((Boolean)this.mValue).booleanValue());
            }
        } else {
            parcel.writeCharSequence((CharSequence)this.mValue);
        }
    }

}


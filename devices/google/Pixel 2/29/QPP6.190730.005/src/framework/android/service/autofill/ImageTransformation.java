/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalTransformation;
import android.service.autofill.Transformation;
import android.service.autofill.ValueFinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ImageTransformation
extends InternalTransformation
implements Transformation,
Parcelable {
    public static final Parcelable.Creator<ImageTransformation> CREATOR = new Parcelable.Creator<ImageTransformation>(){

        @Override
        public ImageTransformation createFromParcel(Parcel object) {
            AutofillId autofillId = (AutofillId)((Parcel)object).readParcelable(null);
            Pattern[] arrpattern = (Pattern[])((Parcel)object).readSerializable();
            int[] arrn = ((Parcel)object).createIntArray();
            CharSequence[] arrcharSequence = ((Parcel)object).readCharSequenceArray();
            object = arrcharSequence[0];
            object = object != null ? new Builder(autofillId, arrpattern[0], arrn[0], (CharSequence)object) : new Builder(autofillId, arrpattern[0], arrn[0]);
            int n = arrpattern.length;
            for (int i = 1; i < n; ++i) {
                if (arrcharSequence[i] != null) {
                    ((Builder)object).addOption(arrpattern[i], arrn[i], arrcharSequence[i]);
                    continue;
                }
                ((Builder)object).addOption(arrpattern[i], arrn[i]);
            }
            return ((Builder)object).build();
        }

        public ImageTransformation[] newArray(int n) {
            return new ImageTransformation[n];
        }
    };
    private static final String TAG = "ImageTransformation";
    private final AutofillId mId;
    private final ArrayList<Option> mOptions;

    private ImageTransformation(Builder builder) {
        this.mId = builder.mId;
        this.mOptions = builder.mOptions;
    }

    @Override
    public void apply(ValueFinder object, RemoteViews object2, int n) throws Exception {
        CharSequence charSequence = object.findByAutofillId(this.mId);
        if (charSequence == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No view for id ");
            ((StringBuilder)object).append(this.mId);
            Log.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        int n2 = this.mOptions.size();
        if (Helper.sDebug) {
            object = new StringBuilder();
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" multiple options on id ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" to compare against");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        for (int i = 0; i < n2; ++i) {
            object = this.mOptions.get(i);
            try {
                if (!((Option)object).pattern.matcher(charSequence).matches()) continue;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Found match at ");
                ((StringBuilder)charSequence).append(i);
                ((StringBuilder)charSequence).append(": ");
                ((StringBuilder)charSequence).append(object);
                Log.d(TAG, ((StringBuilder)charSequence).toString());
                ((RemoteViews)object2).setImageViewResource(n, ((Option)object).resId);
                if (((Option)object).contentDescription != null) {
                    ((RemoteViews)object2).setContentDescription(n, ((Option)object).contentDescription);
                }
                return;
            }
            catch (Exception exception) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Error matching regex #");
                ((StringBuilder)object2).append(i);
                ((StringBuilder)object2).append("(");
                ((StringBuilder)object2).append(((Option)object).pattern);
                ((StringBuilder)object2).append(") on id ");
                ((StringBuilder)object2).append(((Option)object).resId);
                ((StringBuilder)object2).append(": ");
                ((StringBuilder)object2).append(exception.getClass());
                Log.w(TAG, ((StringBuilder)object2).toString());
                throw exception;
            }
        }
        if (Helper.sDebug) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No match for ");
            ((StringBuilder)object).append((String)charSequence);
            Log.d(TAG, ((StringBuilder)object).toString());
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
        stringBuilder.append("ImageTransformation: [id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", options=");
        stringBuilder.append(this.mOptions);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mId, n);
        int n2 = this.mOptions.size();
        Pattern[] arrpattern = new Pattern[n2];
        int[] arrn = new int[n2];
        CharSequence[] arrcharSequence = new String[n2];
        for (n = 0; n < n2; ++n) {
            Option option = this.mOptions.get(n);
            arrpattern[n] = option.pattern;
            arrn[n] = option.resId;
            arrcharSequence[n] = option.contentDescription;
        }
        parcel.writeSerializable((Serializable)arrpattern);
        parcel.writeIntArray(arrn);
        parcel.writeCharSequenceArray(arrcharSequence);
    }

    public static class Builder {
        private boolean mDestroyed;
        private final AutofillId mId;
        private final ArrayList<Option> mOptions = new ArrayList();

        @Deprecated
        public Builder(AutofillId autofillId, Pattern pattern, int n) {
            this.mId = Preconditions.checkNotNull(autofillId);
            this.addOption(pattern, n);
        }

        public Builder(AutofillId autofillId, Pattern pattern, int n, CharSequence charSequence) {
            this.mId = Preconditions.checkNotNull(autofillId);
            this.addOption(pattern, n, charSequence);
        }

        private void addOptionInternal(Pattern pattern, int n, CharSequence charSequence) {
            this.throwIfDestroyed();
            Preconditions.checkNotNull(pattern);
            boolean bl = n != 0;
            Preconditions.checkArgument(bl);
            this.mOptions.add(new Option(pattern, n, charSequence));
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(this.mDestroyed ^ true, "Already called build()");
        }

        @Deprecated
        public Builder addOption(Pattern pattern, int n) {
            this.addOptionInternal(pattern, n, null);
            return this;
        }

        public Builder addOption(Pattern pattern, int n, CharSequence charSequence) {
            this.addOptionInternal(pattern, n, Preconditions.checkNotNull(charSequence));
            return this;
        }

        public ImageTransformation build() {
            this.throwIfDestroyed();
            this.mDestroyed = true;
            return new ImageTransformation(this);
        }
    }

    private static final class Option {
        public final CharSequence contentDescription;
        public final Pattern pattern;
        public final int resId;

        Option(Pattern pattern, int n, CharSequence charSequence) {
            this.pattern = pattern;
            this.resId = n;
            this.contentDescription = TextUtils.trimNoCopySpans(charSequence);
        }
    }

}


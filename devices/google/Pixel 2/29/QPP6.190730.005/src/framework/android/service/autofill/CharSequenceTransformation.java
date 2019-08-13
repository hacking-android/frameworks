/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.InternalTransformation;
import android.service.autofill.Transformation;
import android.service.autofill.ValueFinder;
import android.util.Log;
import android.util.Pair;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CharSequenceTransformation
extends InternalTransformation
implements Transformation,
Parcelable {
    public static final Parcelable.Creator<CharSequenceTransformation> CREATOR = new Parcelable.Creator<CharSequenceTransformation>(){

        @Override
        public CharSequenceTransformation createFromParcel(Parcel object) {
            AutofillId[] arrautofillId = (AutofillId[])object.readParcelableArray(null, AutofillId.class);
            Pattern[] arrpattern = (Pattern[])((Parcel)object).readSerializable();
            String[] arrstring = ((Parcel)object).createStringArray();
            object = new Builder(arrautofillId[0], arrpattern[0], arrstring[0]);
            int n = arrautofillId.length;
            for (int i = 1; i < n; ++i) {
                ((Builder)object).addField(arrautofillId[i], arrpattern[i], arrstring[i]);
            }
            return ((Builder)object).build();
        }

        public CharSequenceTransformation[] newArray(int n) {
            return new CharSequenceTransformation[n];
        }
    };
    private static final String TAG = "CharSequenceTransformation";
    private final LinkedHashMap<AutofillId, Pair<Pattern, String>> mFields;

    private CharSequenceTransformation(Builder builder) {
        this.mFields = builder.mFields;
    }

    @Override
    public void apply(ValueFinder object, RemoteViews remoteViews, int n) throws Exception {
        Object object2;
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = this.mFields.size();
        if (Helper.sDebug) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(n2);
            ((StringBuilder)object2).append(" fields on id ");
            ((StringBuilder)object2).append(n);
            Log.d(TAG, ((StringBuilder)object2).toString());
        }
        for (Map.Entry<AutofillId, Pair<Pattern, String>> entry : this.mFields.entrySet()) {
            object2 = entry.getKey();
            Pair<Pattern, String> object3 = entry.getValue();
            Object object4 = object.findByAutofillId((AutofillId)object2);
            if (object4 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("No value for id ");
                ((StringBuilder)object).append(object2);
                Log.w(TAG, ((StringBuilder)object).toString());
                return;
            }
            try {
                object4 = ((Pattern)object3.first).matcher((CharSequence)object4);
                if (!((Matcher)object4).find()) {
                    if (Helper.sDebug) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Match for ");
                        ((StringBuilder)object).append(object3.first);
                        ((StringBuilder)object).append(" failed on id ");
                        ((StringBuilder)object).append(object2);
                        Log.d(TAG, ((StringBuilder)object).toString());
                    }
                    return;
                }
                stringBuilder.append(((Matcher)object4).replaceAll((String)object3.second));
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot apply ");
                ((StringBuilder)object).append(((Pattern)object3.first).pattern());
                ((StringBuilder)object).append("->");
                ((StringBuilder)object).append((String)object3.second);
                ((StringBuilder)object).append(" to field with autofill id");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append(": ");
                ((StringBuilder)object).append(exception.getClass());
                Log.w(TAG, ((StringBuilder)object).toString());
                throw exception;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Converting text on child ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" to ");
        ((StringBuilder)object).append(stringBuilder.length());
        ((StringBuilder)object).append("_chars");
        Log.d(TAG, ((StringBuilder)object).toString());
        remoteViews.setCharSequence(n, "setText", stringBuilder);
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
        stringBuilder.append("MultipleViewsCharSequenceTransformation: [fields=");
        stringBuilder.append(this.mFields);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        int n2 = this.mFields.size();
        Parcelable[] arrparcelable = new AutofillId[n2];
        Pattern[] arrpattern = new Pattern[n2];
        String[] arrstring = new String[n2];
        n2 = 0;
        for (Map.Entry<AutofillId, Pair<Pattern, String>> entry : this.mFields.entrySet()) {
            arrparcelable[n2] = entry.getKey();
            Pair<Pattern, String> object = entry.getValue();
            arrpattern[n2] = (Pattern)object.first;
            arrstring[n2] = (String)object.second;
            ++n2;
        }
        parcel.writeParcelableArray(arrparcelable, n);
        parcel.writeSerializable((Serializable)arrpattern);
        parcel.writeStringArray(arrstring);
    }

    public static class Builder {
        private boolean mDestroyed;
        private final LinkedHashMap<AutofillId, Pair<Pattern, String>> mFields = new LinkedHashMap();

        public Builder(AutofillId autofillId, Pattern pattern, String string2) {
            this.addField(autofillId, pattern, string2);
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(this.mDestroyed ^ true, "Already called build()");
        }

        public Builder addField(AutofillId autofillId, Pattern pattern, String string2) {
            this.throwIfDestroyed();
            Preconditions.checkNotNull(autofillId);
            Preconditions.checkNotNull(pattern);
            Preconditions.checkNotNull(string2);
            this.mFields.put(autofillId, new Pair<Pattern, String>(pattern, string2));
            return this;
        }

        public CharSequenceTransformation build() {
            this.throwIfDestroyed();
            this.mDestroyed = true;
            return new CharSequenceTransformation(this);
        }
    }

}


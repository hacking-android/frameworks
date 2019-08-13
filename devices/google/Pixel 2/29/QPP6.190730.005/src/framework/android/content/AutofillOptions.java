/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.app.ActivityThread;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Log;
import android.view.autofill.AutofillManager;
import java.io.PrintWriter;

public final class AutofillOptions
implements Parcelable {
    public static final Parcelable.Creator<AutofillOptions> CREATOR;
    private static final String TAG;
    public boolean augmentedAutofillEnabled;
    public final boolean compatModeEnabled;
    public final int loggingLevel;
    public ArraySet<ComponentName> whitelistedActivitiesForAugmentedAutofill;

    static {
        TAG = AutofillOptions.class.getSimpleName();
        CREATOR = new Parcelable.Creator<AutofillOptions>(){

            @Override
            public AutofillOptions createFromParcel(Parcel parcel) {
                AutofillOptions autofillOptions = new AutofillOptions(parcel.readInt(), parcel.readBoolean());
                autofillOptions.augmentedAutofillEnabled = parcel.readBoolean();
                autofillOptions.whitelistedActivitiesForAugmentedAutofill = parcel.readArraySet(null);
                return autofillOptions;
            }

            public AutofillOptions[] newArray(int n) {
                return new AutofillOptions[n];
            }
        };
    }

    public AutofillOptions(int n, boolean bl) {
        this.loggingLevel = n;
        this.compatModeEnabled = bl;
    }

    public static AutofillOptions forWhitelistingItself() {
        Object object = ActivityThread.currentActivityThread();
        if (object != null) {
            if ("android.autofillservice.cts".equals(object = ((ActivityThread)object).getApplication().getPackageName())) {
                AutofillOptions autofillOptions = new AutofillOptions(4, true);
                autofillOptions.augmentedAutofillEnabled = true;
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("forWhitelistingItself(");
                stringBuilder.append((String)object);
                stringBuilder.append("): ");
                stringBuilder.append(autofillOptions);
                Log.i(string2, stringBuilder.toString());
                return autofillOptions;
            }
            String string3 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("forWhitelistingItself(): called by ");
            stringBuilder.append((String)object);
            Log.e(string3, stringBuilder.toString());
            throw new SecurityException("Thou shall not pass!");
        }
        throw new IllegalStateException("No ActivityThread");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dumpShort(PrintWriter printWriter) {
        printWriter.print("logLvl=");
        printWriter.print(this.loggingLevel);
        printWriter.print(", compatMode=");
        printWriter.print(this.compatModeEnabled);
        printWriter.print(", augmented=");
        printWriter.print(this.augmentedAutofillEnabled);
        if (this.whitelistedActivitiesForAugmentedAutofill != null) {
            printWriter.print(", whitelistedActivitiesForAugmentedAutofill=");
            printWriter.print(this.whitelistedActivitiesForAugmentedAutofill);
        }
    }

    public boolean isAugmentedAutofillEnabled(Context arraySet) {
        boolean bl = this.augmentedAutofillEnabled;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if ((arraySet = ((Context)((Object)arraySet)).getAutofillClient()) == null) {
            return false;
        }
        ComponentName componentName = arraySet.autofillClientGetComponentName();
        arraySet = this.whitelistedActivitiesForAugmentedAutofill;
        if (arraySet == null || arraySet.contains(componentName)) {
            bl2 = true;
        }
        return bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AutofillOptions [loggingLevel=");
        stringBuilder.append(this.loggingLevel);
        stringBuilder.append(", compatMode=");
        stringBuilder.append(this.compatModeEnabled);
        stringBuilder.append(", augmentedAutofillEnabled=");
        stringBuilder.append(this.augmentedAutofillEnabled);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.loggingLevel);
        parcel.writeBoolean(this.compatModeEnabled);
        parcel.writeBoolean(this.augmentedAutofillEnabled);
        parcel.writeArraySet(this.whitelistedActivitiesForAugmentedAutofill);
    }

}


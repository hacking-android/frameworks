/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.view.autofill.AutofillId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public final class Helper {
    public static boolean sDebug = false;
    public static boolean sVerbose = false;

    private Helper() {
        throw new UnsupportedOperationException("contains static members only");
    }

    public static void appendRedacted(StringBuilder stringBuilder, CharSequence charSequence) {
        stringBuilder.append(Helper.getRedacted(charSequence));
    }

    public static void appendRedacted(StringBuilder stringBuilder, String[] arrstring) {
        if (arrstring == null) {
            stringBuilder.append("N/A");
            return;
        }
        stringBuilder.append("[");
        for (String string2 : arrstring) {
            stringBuilder.append(" '");
            Helper.appendRedacted(stringBuilder, string2);
            stringBuilder.append("'");
        }
        stringBuilder.append(" ]");
    }

    public static String getRedacted(CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "null";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(charSequence.length());
            stringBuilder.append("_chars");
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }

    public static AutofillId[] toArray(Collection<AutofillId> collection) {
        if (collection == null) {
            return new AutofillId[0];
        }
        AutofillId[] arrautofillId = new AutofillId[collection.size()];
        collection.toArray(arrautofillId);
        return arrautofillId;
    }

    public static <T> ArrayList<T> toList(Set<T> collection) {
        collection = collection == null ? null : new ArrayList<T>(collection);
        return collection;
    }
}


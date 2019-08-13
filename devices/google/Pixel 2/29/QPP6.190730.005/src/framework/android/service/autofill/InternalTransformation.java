/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.os.Parcelable;
import android.service.autofill.Transformation;
import android.service.autofill.ValueFinder;
import android.util.Log;
import android.util.Pair;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import java.util.ArrayList;

public abstract class InternalTransformation
implements Transformation,
Parcelable {
    private static final String TAG = "InternalTransformation";

    public static boolean batchApply(ValueFinder valueFinder, RemoteViews object, ArrayList<Pair<Integer, InternalTransformation>> arrayList) {
        Object object2;
        int n = arrayList.size();
        if (Helper.sDebug) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getPresentation(): applying ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(" transformations");
            Log.d(TAG, ((StringBuilder)object2).toString());
        }
        for (int i = 0; i < n; ++i) {
            object2 = arrayList.get(i);
            int n2 = (Integer)((Pair)object2).first;
            object2 = (InternalTransformation)((Pair)object2).second;
            if (Helper.sDebug) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("#");
                stringBuilder.append(i);
                stringBuilder.append(": ");
                stringBuilder.append(object2);
                Log.d(TAG, stringBuilder.toString());
            }
            try {
                ((InternalTransformation)object2).apply(valueFinder, (RemoteViews)object, n2);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not apply transformation ");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append(": ");
                ((StringBuilder)object).append(exception.getClass());
                Log.e(TAG, ((StringBuilder)object).toString());
                return false;
            }
        }
        return true;
    }

    abstract void apply(ValueFinder var1, RemoteViews var2, int var3) throws Exception;
}


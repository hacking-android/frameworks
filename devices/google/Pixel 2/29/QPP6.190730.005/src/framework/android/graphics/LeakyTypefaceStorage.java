/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Process;
import android.util.ArrayMap;
import com.android.internal.annotations.GuardedBy;
import java.util.ArrayList;

public class LeakyTypefaceStorage {
    private static final Object sLock = new Object();
    @GuardedBy(value={"sLock"})
    private static final ArrayList<Typeface> sStorage = new ArrayList();
    @GuardedBy(value={"sLock"})
    private static final ArrayMap<Typeface, Integer> sTypefaceMap = new ArrayMap();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Typeface readTypefaceFromParcel(Parcel object) {
        int n = ((Parcel)object).readInt();
        int n2 = ((Parcel)object).readInt();
        if (n != Process.myPid()) {
            return null;
        }
        object = sLock;
        synchronized (object) {
            return sStorage.get(n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void writeTypefaceToParcel(Typeface typeface, Parcel parcel) {
        parcel.writeInt(Process.myPid());
        Object object = sLock;
        synchronized (object) {
            int n;
            Integer n2 = sTypefaceMap.get(typeface);
            if (n2 != null) {
                n = n2;
            } else {
                n = sStorage.size();
                sStorage.add(typeface);
                sTypefaceMap.put(typeface, n);
            }
            parcel.writeInt(n);
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.servertransaction.ObjectPoolItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ObjectPool {
    private static final int MAX_POOL_SIZE = 50;
    private static final Map<Class, ArrayList<? extends ObjectPoolItem>> sPoolMap;
    private static final Object sPoolSync;

    static {
        sPoolSync = new Object();
        sPoolMap = new HashMap<Class, ArrayList<? extends ObjectPoolItem>>();
    }

    ObjectPool() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T extends ObjectPoolItem> T obtain(Class<T> object) {
        Object object2 = sPoolSync;
        synchronized (object2) {
            object = sPoolMap.get(object);
            if (object != null && !((ArrayList)object).isEmpty()) {
                object = (ObjectPoolItem)((ArrayList)object).remove(((ArrayList)object).size() - 1);
                return (T)object;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T extends ObjectPoolItem> void recycle(T object) {
        Object object2 = sPoolSync;
        synchronized (object2) {
            ArrayList<T> arrayList;
            ArrayList<T> arrayList2 = arrayList = sPoolMap.get(object.getClass());
            if (arrayList == null) {
                arrayList2 = new ArrayList<T>();
                sPoolMap.put(object.getClass(), arrayList2);
            }
            int n = arrayList2.size();
            for (int i = 0; i < n; ++i) {
                if (arrayList2.get(i) != object) continue;
                object = new Object("Trying to recycle already recycled item");
                throw object;
            }
            if (n < 50) {
                arrayList2.add(object);
            }
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package com.android.server;

import android.util.ArrayMap;
import com.android.internal.annotations.VisibleForTesting;
import java.io.Serializable;

public final class LocalServices {
    private static final ArrayMap<Class<?>, Object> sLocalServiceObjects = new ArrayMap();

    private LocalServices() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> void addService(Class<T> serializable, T t) {
        ArrayMap<Class<?>, Object> arrayMap = sLocalServiceObjects;
        synchronized (arrayMap) {
            if (!sLocalServiceObjects.containsKey(serializable)) {
                sLocalServiceObjects.put((Class<?>)serializable, t);
                return;
            }
            serializable = new Serializable("Overriding service registration");
            throw serializable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> T getService(Class<T> object) {
        ArrayMap<Class<?>, Object> arrayMap = sLocalServiceObjects;
        synchronized (arrayMap) {
            object = sLocalServiceObjects.get(object);
            return (T)object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public static <T> void removeServiceForTest(Class<T> class_) {
        ArrayMap<Class<?>, Object> arrayMap = sLocalServiceObjects;
        synchronized (arrayMap) {
            sLocalServiceObjects.remove(class_);
            return;
        }
    }
}


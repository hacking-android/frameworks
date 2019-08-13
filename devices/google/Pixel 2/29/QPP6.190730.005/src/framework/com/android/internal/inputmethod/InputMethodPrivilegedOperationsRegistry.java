/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import android.os.IBinder;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.InputMethodPrivilegedOperations;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class InputMethodPrivilegedOperationsRegistry {
    private static final Object sLock = new Object();
    private static InputMethodPrivilegedOperations sNop;
    @GuardedBy(value={"sLock"})
    private static WeakHashMap<IBinder, WeakReference<InputMethodPrivilegedOperations>> sRegistry;

    private InputMethodPrivilegedOperationsRegistry() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static InputMethodPrivilegedOperations get(IBinder object) {
        Object object2 = sLock;
        synchronized (object2) {
            if (sRegistry == null) {
                return InputMethodPrivilegedOperationsRegistry.getNopOps();
            }
            if ((object = sRegistry.get(object)) == null) {
                return InputMethodPrivilegedOperationsRegistry.getNopOps();
            }
            if ((object = (InputMethodPrivilegedOperations)((Reference)object).get()) != null) return object;
            return InputMethodPrivilegedOperationsRegistry.getNopOps();
        }
    }

    private static InputMethodPrivilegedOperations getNopOps() {
        if (sNop == null) {
            sNop = new InputMethodPrivilegedOperations();
        }
        return sNop;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isRegistered(IBinder iBinder) {
        Object object = sLock;
        synchronized (object) {
            if (sRegistry != null) return sRegistry.containsKey(iBinder);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void put(IBinder iBinder, InputMethodPrivilegedOperations inputMethodPrivilegedOperations) {
        Object object = sLock;
        synchronized (object) {
            Object object2;
            if (sRegistry == null) {
                object2 = new WeakHashMap();
                sRegistry = object2;
            }
            object2 = sRegistry;
            WeakReference<InputMethodPrivilegedOperations> weakReference = new WeakReference<InputMethodPrivilegedOperations>(inputMethodPrivilegedOperations);
            if ((weakReference = ((WeakHashMap)object2).put((IBinder)iBinder, weakReference)) == null) {
                return;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(weakReference.get());
            ((StringBuilder)object2).append(" is already registered for  this token=");
            ((StringBuilder)object2).append(iBinder);
            ((StringBuilder)object2).append(" newOps=");
            ((StringBuilder)object2).append(inputMethodPrivilegedOperations);
            IllegalStateException illegalStateException = new IllegalStateException(((StringBuilder)object2).toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void remove(IBinder iBinder) {
        Object object = sLock;
        synchronized (object) {
            if (sRegistry == null) {
                return;
            }
            sRegistry.remove(iBinder);
            if (sRegistry.isEmpty()) {
                sRegistry = null;
            }
            return;
        }
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import dalvik.system.VMRuntime;
import java.lang.ref.Reference;
import sun.misc.Cleaner;

public class NativeAllocationRegistry {
    private static final long IS_MALLOCED = 1L;
    private final ClassLoader classLoader;
    private final long freeFunction;
    private final long size;

    public NativeAllocationRegistry(ClassLoader classLoader, long l, long l2) {
        boolean bl = l2 == 0L;
        this(classLoader, l, l2, bl);
    }

    private NativeAllocationRegistry(ClassLoader object, long l, long l2, boolean bl) {
        if (l2 >= 0L) {
            this.classLoader = object;
            this.freeFunction = l;
            l = bl ? 1L | l2 : -2L & l2;
            this.size = l;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid native allocation size: ");
        ((StringBuilder)object).append(l2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static native void applyFreeFunction(long var0, long var2);

    public static NativeAllocationRegistry createMalloced(ClassLoader classLoader, long l) {
        return new NativeAllocationRegistry(classLoader, l, 0L, true);
    }

    public static NativeAllocationRegistry createMalloced(ClassLoader classLoader, long l, long l2) {
        return new NativeAllocationRegistry(classLoader, l, l2, true);
    }

    public static NativeAllocationRegistry createNonmalloced(ClassLoader classLoader, long l, long l2) {
        return new NativeAllocationRegistry(classLoader, l, l2, false);
    }

    private static void registerNativeAllocation(long l) {
        VMRuntime vMRuntime = VMRuntime.getRuntime();
        if ((1L & l) != 0L) {
            if (l >= 300000L) {
                vMRuntime.notifyNativeAllocationsInternal();
            } else {
                vMRuntime.notifyNativeAllocation();
            }
        } else {
            vMRuntime.registerNativeAllocation(l);
        }
    }

    private static void registerNativeFree(long l) {
        if ((1L & l) == 0L) {
            VMRuntime.getRuntime().registerNativeFree(l);
        }
    }

    public Runnable registerNativeAllocation(Object object, long l) {
        if (object != null) {
            if (l != 0L) {
                CleanerRunner cleanerRunner;
                try {
                    CleanerThunk cleanerThunk = new CleanerThunk();
                    Cleaner cleaner = Cleaner.create(object, cleanerThunk);
                    cleanerRunner = new CleanerRunner(cleaner);
                    NativeAllocationRegistry.registerNativeAllocation(this.size);
                    cleanerThunk.setNativePtr(l);
                }
                catch (VirtualMachineError virtualMachineError) {
                    NativeAllocationRegistry.applyFreeFunction(this.freeFunction, l);
                    throw virtualMachineError;
                }
                Reference.reachabilityFence((Object)object);
                return cleanerRunner;
            }
            throw new IllegalArgumentException("nativePtr is null");
        }
        throw new IllegalArgumentException("referent is null");
    }

    private static class CleanerRunner
    implements Runnable {
        private final Cleaner cleaner;

        public CleanerRunner(Cleaner cleaner) {
            this.cleaner = cleaner;
        }

        @Override
        public void run() {
            this.cleaner.clean();
        }
    }

    private class CleanerThunk
    implements Runnable {
        private long nativePtr = 0L;

        @Override
        public void run() {
            if (this.nativePtr != 0L) {
                NativeAllocationRegistry.applyFreeFunction(NativeAllocationRegistry.this.freeFunction, this.nativePtr);
                NativeAllocationRegistry.registerNativeFree(NativeAllocationRegistry.this.size);
            }
        }

        public void setNativePtr(long l) {
            this.nativePtr = l;
        }
    }

}


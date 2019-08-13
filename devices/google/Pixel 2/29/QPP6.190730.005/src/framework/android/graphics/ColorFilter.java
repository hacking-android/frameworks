/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import libcore.util.NativeAllocationRegistry;

public class ColorFilter {
    private Runnable mCleaner;
    private long mNativeInstance;

    static /* synthetic */ long access$000() {
        return ColorFilter.nativeGetFinalizer();
    }

    private static native long nativeGetFinalizer();

    long createNativeInstance() {
        return 0L;
    }

    void discardNativeInstance() {
        if (this.mNativeInstance != 0L) {
            this.mCleaner.run();
            this.mCleaner = null;
            this.mNativeInstance = 0L;
        }
    }

    public long getNativeInstance() {
        if (this.mNativeInstance == 0L) {
            this.mNativeInstance = this.createNativeInstance();
            if (this.mNativeInstance != 0L) {
                this.mCleaner = NoImagePreloadHolder.sRegistry.registerNativeAllocation((Object)this, this.mNativeInstance);
            }
        }
        return this.mNativeInstance;
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)ColorFilter.class.getClassLoader(), (long)ColorFilter.access$000());

        private NoImagePreloadHolder() {
        }
    }

}


/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.nio.fs.NativeBuffers;

class NativeBuffer {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private final long address;
    private final Cleaner cleaner;
    private Object owner;
    private final int size;

    NativeBuffer(int n) {
        this.address = unsafe.allocateMemory(n);
        this.size = n;
        this.cleaner = Cleaner.create(this, new Deallocator(this.address));
    }

    long address() {
        return this.address;
    }

    Cleaner cleaner() {
        return this.cleaner;
    }

    Object owner() {
        return this.owner;
    }

    void release() {
        NativeBuffers.releaseNativeBuffer(this);
    }

    void setOwner(Object object) {
        this.owner = object;
    }

    int size() {
        return this.size;
    }

    private static class Deallocator
    implements Runnable {
        private final long address;

        Deallocator(long l) {
            this.address = l;
        }

        @Override
        public void run() {
            unsafe.freeMemory(this.address);
        }
    }

}


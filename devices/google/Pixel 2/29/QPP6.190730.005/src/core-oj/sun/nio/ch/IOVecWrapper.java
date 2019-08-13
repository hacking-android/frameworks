/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.nio.ByteBuffer;
import sun.misc.Cleaner;
import sun.nio.ch.AllocatedNativeObject;
import sun.nio.ch.Util;

class IOVecWrapper {
    private static final int BASE_OFFSET = 0;
    private static final int LEN_OFFSET;
    private static final int SIZE_IOVEC;
    static int addressSize;
    private static final ThreadLocal<IOVecWrapper> cached;
    final long address;
    private final ByteBuffer[] buf;
    private final int[] position;
    private final int[] remaining;
    private final ByteBuffer[] shadow;
    private final int size;
    private final AllocatedNativeObject vecArray;

    static {
        int n;
        cached = new ThreadLocal();
        LEN_OFFSET = n = (addressSize = Util.unsafe().addressSize());
        SIZE_IOVEC = (short)(n * 2);
    }

    private IOVecWrapper(int n) {
        this.size = n;
        this.buf = new ByteBuffer[n];
        this.position = new int[n];
        this.remaining = new int[n];
        this.shadow = new ByteBuffer[n];
        this.vecArray = new AllocatedNativeObject(SIZE_IOVEC * n, false);
        this.address = this.vecArray.address();
    }

    static IOVecWrapper get(int n) {
        IOVecWrapper iOVecWrapper;
        IOVecWrapper iOVecWrapper2 = iOVecWrapper = cached.get();
        if (iOVecWrapper != null) {
            iOVecWrapper2 = iOVecWrapper;
            if (iOVecWrapper.size < n) {
                iOVecWrapper.vecArray.free();
                iOVecWrapper2 = null;
            }
        }
        iOVecWrapper = iOVecWrapper2;
        if (iOVecWrapper2 == null) {
            iOVecWrapper = new IOVecWrapper(n);
            Cleaner.create(iOVecWrapper, new Deallocator(iOVecWrapper.vecArray));
            cached.set(iOVecWrapper);
        }
        return iOVecWrapper;
    }

    void clearRefs(int n) {
        this.buf[n] = null;
        this.shadow[n] = null;
    }

    ByteBuffer getBuffer(int n) {
        return this.buf[n];
    }

    int getPosition(int n) {
        return this.position[n];
    }

    int getRemaining(int n) {
        return this.remaining[n];
    }

    ByteBuffer getShadow(int n) {
        return this.shadow[n];
    }

    void putBase(int n, long l) {
        n = SIZE_IOVEC * n + 0;
        if (addressSize == 4) {
            this.vecArray.putInt(n, (int)l);
        } else {
            this.vecArray.putLong(n, l);
        }
    }

    void putLen(int n, long l) {
        n = SIZE_IOVEC * n + LEN_OFFSET;
        if (addressSize == 4) {
            this.vecArray.putInt(n, (int)l);
        } else {
            this.vecArray.putLong(n, l);
        }
    }

    void setBuffer(int n, ByteBuffer byteBuffer, int n2, int n3) {
        this.buf[n] = byteBuffer;
        this.position[n] = n2;
        this.remaining[n] = n3;
    }

    void setShadow(int n, ByteBuffer byteBuffer) {
        this.shadow[n] = byteBuffer;
    }

    private static class Deallocator
    implements Runnable {
        private final AllocatedNativeObject obj;

        Deallocator(AllocatedNativeObject allocatedNativeObject) {
            this.obj = allocatedNativeObject;
        }

        @Override
        public void run() {
            this.obj.free();
        }
    }

}


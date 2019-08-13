/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.nio.fs.NativeBuffer;

class NativeBuffers {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int TEMP_BUF_POOL_SIZE = 3;
    private static ThreadLocal<NativeBuffer[]> threadLocal;
    private static final Unsafe unsafe;

    static {
        unsafe = Unsafe.getUnsafe();
        threadLocal = new ThreadLocal();
    }

    private NativeBuffers() {
    }

    static NativeBuffer allocNativeBuffer(int n) {
        int n2 = n;
        if (n < 2048) {
            n2 = 2048;
        }
        return new NativeBuffer(n2);
    }

    static NativeBuffer asNativeBuffer(byte[] arrby) {
        NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(arrby.length + 1);
        NativeBuffers.copyCStringToNativeBuffer(arrby, nativeBuffer);
        return nativeBuffer;
    }

    static void copyCStringToNativeBuffer(byte[] arrby, NativeBuffer nativeBuffer) {
        long l = arrby.length;
        int n = 0;
        while ((long)n < l) {
            unsafe.putByte(nativeBuffer.address() + (long)n, arrby[n]);
            ++n;
        }
        unsafe.putByte(nativeBuffer.address() + l, (byte)0);
    }

    static NativeBuffer getNativeBuffer(int n) {
        NativeBuffer nativeBuffer = NativeBuffers.getNativeBufferFromCache(n);
        if (nativeBuffer != null) {
            nativeBuffer.setOwner(null);
            return nativeBuffer;
        }
        return NativeBuffers.allocNativeBuffer(n);
    }

    static NativeBuffer getNativeBufferFromCache(int n) {
        NativeBuffer[] arrnativeBuffer = threadLocal.get();
        if (arrnativeBuffer != null) {
            for (int i = 0; i < 3; ++i) {
                NativeBuffer nativeBuffer = arrnativeBuffer[i];
                if (nativeBuffer == null || nativeBuffer.size() < n) continue;
                arrnativeBuffer[i] = null;
                return nativeBuffer;
            }
        }
        return null;
    }

    static void releaseNativeBuffer(NativeBuffer nativeBuffer) {
        int n;
        NativeBuffer[] arrnativeBuffer = threadLocal.get();
        if (arrnativeBuffer == null) {
            NativeBuffer[] arrnativeBuffer2 = new NativeBuffer[3];
            arrnativeBuffer2[0] = nativeBuffer;
            threadLocal.set(arrnativeBuffer2);
            return;
        }
        for (n = 0; n < 3; ++n) {
            if (arrnativeBuffer[n] != null) continue;
            arrnativeBuffer[n] = nativeBuffer;
            return;
        }
        for (n = 0; n < 3; ++n) {
            NativeBuffer nativeBuffer2 = arrnativeBuffer[n];
            if (nativeBuffer2.size() >= nativeBuffer.size()) continue;
            nativeBuffer2.cleaner().clean();
            arrnativeBuffer[n] = nativeBuffer;
            return;
        }
        nativeBuffer.cleaner().clean();
    }
}


/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import sun.nio.fs.NativeBuffer;
import sun.nio.fs.NativeBuffers;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixMountEntry;
import sun.nio.fs.UnixNativeDispatcher;

class LinuxNativeDispatcher
extends UnixNativeDispatcher {
    static {
        LinuxNativeDispatcher.init();
    }

    private LinuxNativeDispatcher() {
    }

    static native void endmntent(long var0) throws UnixException;

    static int fgetxattr(int n, byte[] object, long l, int n2) throws UnixException {
        object = NativeBuffers.asNativeBuffer((byte[])object);
        try {
            n = LinuxNativeDispatcher.fgetxattr0(n, ((NativeBuffer)object).address(), l, n2);
            return n;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native int fgetxattr0(int var0, long var1, long var3, int var5) throws UnixException;

    static native int flistxattr(int var0, long var1, int var3) throws UnixException;

    static void fremovexattr(int n, byte[] object) throws UnixException {
        object = NativeBuffers.asNativeBuffer((byte[])object);
        try {
            LinuxNativeDispatcher.fremovexattr0(n, ((NativeBuffer)object).address());
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void fremovexattr0(int var0, long var1) throws UnixException;

    static void fsetxattr(int n, byte[] object, long l, int n2) throws UnixException {
        object = NativeBuffers.asNativeBuffer((byte[])object);
        try {
            LinuxNativeDispatcher.fsetxattr0(n, ((NativeBuffer)object).address(), l, n2);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void fsetxattr0(int var0, long var1, long var3, int var5) throws UnixException;

    static native int getmntent(long var0, UnixMountEntry var2) throws UnixException;

    private static native void init();

    static long setmntent(byte[] object, byte[] arrby) throws UnixException {
        object = NativeBuffers.asNativeBuffer((byte[])object);
        NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(arrby);
        try {
            long l = LinuxNativeDispatcher.setmntent0(((NativeBuffer)object).address(), nativeBuffer.address());
            return l;
        }
        finally {
            nativeBuffer.release();
            ((NativeBuffer)object).release();
        }
    }

    private static native long setmntent0(long var0, long var2) throws UnixException;
}


/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import sun.nio.fs.NativeBuffer;
import sun.nio.fs.NativeBuffers;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileStoreAttributes;
import sun.nio.fs.UnixPath;
import sun.nio.fs.Util;

class UnixNativeDispatcher {
    private static final int SUPPORTS_BIRTHTIME = 65536;
    private static final int SUPPORTS_FUTIMES = 4;
    private static final int SUPPORTS_OPENAT = 2;
    private static final int capabilities = UnixNativeDispatcher.init();

    protected UnixNativeDispatcher() {
    }

    static void access(UnixPath object, int n) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            UnixNativeDispatcher.access0(((NativeBuffer)object).address(), n);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void access0(long var0, int var2) throws UnixException;

    static boolean birthtimeSupported() {
        boolean bl = (capabilities & 65536) != 0;
        return bl;
    }

    static void chmod(UnixPath unixPath, int n) throws UnixException {
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            UnixNativeDispatcher.chmod0(nativeBuffer.address(), n);
            return;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native void chmod0(long var0, int var2) throws UnixException;

    static void chown(UnixPath unixPath, int n, int n2) throws UnixException {
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            UnixNativeDispatcher.chown0(nativeBuffer.address(), n, n2);
            return;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native void chown0(long var0, int var2, int var3) throws UnixException;

    static native void close(int var0);

    static native void closedir(long var0) throws UnixException;

    private static NativeBuffer copyToNativeBuffer(UnixPath unixPath) {
        NativeBuffer nativeBuffer;
        byte[] arrby = unixPath.getByteArrayForSysCalls();
        int n = arrby.length + 1;
        NativeBuffer nativeBuffer2 = NativeBuffers.getNativeBufferFromCache(n);
        if (nativeBuffer2 == null) {
            nativeBuffer = NativeBuffers.allocNativeBuffer(n);
        } else {
            nativeBuffer = nativeBuffer2;
            if (nativeBuffer2.owner() == unixPath) {
                return nativeBuffer2;
            }
        }
        NativeBuffers.copyCStringToNativeBuffer(arrby, nativeBuffer);
        nativeBuffer.setOwner(unixPath);
        return nativeBuffer;
    }

    static native int dup(int var0) throws UnixException;

    static native void fchmod(int var0, int var1) throws UnixException;

    static native void fchown(int var0, int var1, int var2) throws UnixException;

    static native void fclose(long var0) throws UnixException;

    static native long fdopendir(int var0) throws UnixException;

    static long fopen(UnixPath object, String object2) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        object2 = NativeBuffers.asNativeBuffer(Util.toBytes((String)object2));
        try {
            long l = UnixNativeDispatcher.fopen0(((NativeBuffer)object).address(), ((NativeBuffer)object2).address());
            return l;
        }
        finally {
            ((NativeBuffer)object2).release();
            ((NativeBuffer)object).release();
        }
    }

    private static native long fopen0(long var0, long var2) throws UnixException;

    static native long fpathconf(int var0, int var1) throws UnixException;

    static native void fstat(int var0, UnixFileAttributes var1) throws UnixException;

    static void fstatat(int n, byte[] object, int n2, UnixFileAttributes unixFileAttributes) throws UnixException {
        object = NativeBuffers.asNativeBuffer((byte[])object);
        try {
            UnixNativeDispatcher.fstatat0(n, ((NativeBuffer)object).address(), n2, unixFileAttributes);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void fstatat0(int var0, long var1, int var3, UnixFileAttributes var4) throws UnixException;

    static native void futimes(int var0, long var1, long var3) throws UnixException;

    static boolean futimesSupported() {
        boolean bl = (capabilities & 4) != 0;
        return bl;
    }

    static native byte[] getcwd();

    static native byte[] getgrgid(int var0) throws UnixException;

    static int getgrnam(String object) throws UnixException {
        object = NativeBuffers.asNativeBuffer(Util.toBytes((String)object));
        try {
            int n = UnixNativeDispatcher.getgrnam0(((NativeBuffer)object).address());
            return n;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native int getgrnam0(long var0) throws UnixException;

    static int getpwnam(String string) throws UnixException {
        NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(Util.toBytes(string));
        try {
            int n = UnixNativeDispatcher.getpwnam0(nativeBuffer.address());
            return n;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native int getpwnam0(long var0) throws UnixException;

    static native byte[] getpwuid(int var0) throws UnixException;

    private static native int init();

    static void lchown(UnixPath object, int n, int n2) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            UnixNativeDispatcher.lchown0(((NativeBuffer)object).address(), n, n2);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void lchown0(long var0, int var2, int var3) throws UnixException;

    static void link(UnixPath object, UnixPath unixPath) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            UnixNativeDispatcher.link0(((NativeBuffer)object).address(), nativeBuffer.address());
            return;
        }
        finally {
            nativeBuffer.release();
            ((NativeBuffer)object).release();
        }
    }

    private static native void link0(long var0, long var2) throws UnixException;

    static void lstat(UnixPath object, UnixFileAttributes unixFileAttributes) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            UnixNativeDispatcher.lstat0(((NativeBuffer)object).address(), unixFileAttributes);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void lstat0(long var0, UnixFileAttributes var2) throws UnixException;

    static void mkdir(UnixPath unixPath, int n) throws UnixException {
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            UnixNativeDispatcher.mkdir0(nativeBuffer.address(), n);
            return;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native void mkdir0(long var0, int var2) throws UnixException;

    static void mknod(UnixPath object, int n, long l) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            UnixNativeDispatcher.mknod0(((NativeBuffer)object).address(), n, l);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void mknod0(long var0, int var2, long var3) throws UnixException;

    static int open(UnixPath object, int n, int n2) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            n = UnixNativeDispatcher.open0(((NativeBuffer)object).address(), n, n2);
            return n;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native int open0(long var0, int var2, int var3) throws UnixException;

    static int openat(int n, byte[] arrby, int n2, int n3) throws UnixException {
        NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(arrby);
        try {
            n = UnixNativeDispatcher.openat0(n, nativeBuffer.address(), n2, n3);
            return n;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native int openat0(int var0, long var1, int var3, int var4) throws UnixException;

    static boolean openatSupported() {
        boolean bl = (capabilities & 2) != 0;
        return bl;
    }

    static long opendir(UnixPath unixPath) throws UnixException {
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            long l = UnixNativeDispatcher.opendir0(nativeBuffer.address());
            return l;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native long opendir0(long var0) throws UnixException;

    static long pathconf(UnixPath object, int n) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            long l = UnixNativeDispatcher.pathconf0(((NativeBuffer)object).address(), n);
            return l;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native long pathconf0(long var0, int var2) throws UnixException;

    static native int read(int var0, long var1, int var3) throws UnixException;

    static native byte[] readdir(long var0) throws UnixException;

    static byte[] readlink(UnixPath object) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            byte[] arrby = UnixNativeDispatcher.readlink0(((NativeBuffer)object).address());
            return arrby;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native byte[] readlink0(long var0) throws UnixException;

    static byte[] realpath(UnixPath object) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            byte[] arrby = UnixNativeDispatcher.realpath0(((NativeBuffer)object).address());
            return arrby;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native byte[] realpath0(long var0) throws UnixException;

    static void rename(UnixPath object, UnixPath object2) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        object2 = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object2);
        try {
            UnixNativeDispatcher.rename0(((NativeBuffer)object).address(), ((NativeBuffer)object2).address());
            return;
        }
        finally {
            ((NativeBuffer)object2).release();
            ((NativeBuffer)object).release();
        }
    }

    private static native void rename0(long var0, long var2) throws UnixException;

    static void renameat(int n, byte[] object, int n2, byte[] arrby) throws UnixException {
        object = NativeBuffers.asNativeBuffer((byte[])object);
        NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(arrby);
        try {
            UnixNativeDispatcher.renameat0(n, ((NativeBuffer)object).address(), n2, nativeBuffer.address());
            return;
        }
        finally {
            nativeBuffer.release();
            ((NativeBuffer)object).release();
        }
    }

    private static native void renameat0(int var0, long var1, int var3, long var4) throws UnixException;

    static void rmdir(UnixPath unixPath) throws UnixException {
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            UnixNativeDispatcher.rmdir0(nativeBuffer.address());
            return;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native void rmdir0(long var0) throws UnixException;

    static void stat(UnixPath object, UnixFileAttributes unixFileAttributes) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            UnixNativeDispatcher.stat0(((NativeBuffer)object).address(), unixFileAttributes);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void stat0(long var0, UnixFileAttributes var2) throws UnixException;

    static void statvfs(UnixPath object, UnixFileStoreAttributes unixFileStoreAttributes) throws UnixException {
        object = UnixNativeDispatcher.copyToNativeBuffer((UnixPath)object);
        try {
            UnixNativeDispatcher.statvfs0(((NativeBuffer)object).address(), unixFileStoreAttributes);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void statvfs0(long var0, UnixFileStoreAttributes var2) throws UnixException;

    static native byte[] strerror(int var0);

    static void symlink(byte[] object, UnixPath unixPath) throws UnixException {
        object = NativeBuffers.asNativeBuffer((byte[])object);
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            UnixNativeDispatcher.symlink0(((NativeBuffer)object).address(), nativeBuffer.address());
            return;
        }
        finally {
            nativeBuffer.release();
            ((NativeBuffer)object).release();
        }
    }

    private static native void symlink0(long var0, long var2) throws UnixException;

    static void unlink(UnixPath unixPath) throws UnixException {
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            UnixNativeDispatcher.unlink0(nativeBuffer.address());
            return;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native void unlink0(long var0) throws UnixException;

    static void unlinkat(int n, byte[] object, int n2) throws UnixException {
        object = NativeBuffers.asNativeBuffer((byte[])object);
        try {
            UnixNativeDispatcher.unlinkat0(n, ((NativeBuffer)object).address(), n2);
            return;
        }
        finally {
            ((NativeBuffer)object).release();
        }
    }

    private static native void unlinkat0(int var0, long var1, int var3) throws UnixException;

    static void utimes(UnixPath unixPath, long l, long l2) throws UnixException {
        NativeBuffer nativeBuffer = UnixNativeDispatcher.copyToNativeBuffer(unixPath);
        try {
            UnixNativeDispatcher.utimes0(nativeBuffer.address(), l, l2);
            return;
        }
        finally {
            nativeBuffer.release();
        }
    }

    private static native void utimes0(long var0, long var2, long var4) throws UnixException;

    static native int write(int var0, long var1, int var3) throws UnixException;
}


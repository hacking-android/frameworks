/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.IOVecWrapper;
import sun.nio.ch.NativeDispatcher;
import sun.nio.ch.Util;

public class IOUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IOV_MAX = IOUtil.iovMax();

    private IOUtil() {
    }

    public static native void configureBlocking(FileDescriptor var0, boolean var1) throws IOException;

    static native boolean drain(int var0) throws IOException;

    static native int fdLimit();

    public static native int fdVal(FileDescriptor var0);

    static native int iovMax();

    static native long makePipe(boolean var0);

    public static FileDescriptor newFD(int n) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        IOUtil.setfdVal(fileDescriptor, n);
        return fileDescriptor;
    }

    static native boolean randomBytes(byte[] var0);

    static int read(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l, NativeDispatcher nativeDispatcher) throws IOException {
        if (!byteBuffer.isReadOnly()) {
            ByteBuffer byteBuffer2;
            int n;
            block6 : {
                if (byteBuffer instanceof DirectBuffer) {
                    return IOUtil.readIntoNativeBuffer(fileDescriptor, byteBuffer, l, nativeDispatcher);
                }
                byteBuffer2 = Util.getTemporaryDirectBuffer(byteBuffer.remaining());
                n = IOUtil.readIntoNativeBuffer(fileDescriptor, byteBuffer2, l, nativeDispatcher);
                byteBuffer2.flip();
                if (n <= 0) break block6;
                byteBuffer.put(byteBuffer2);
            }
            return n;
            finally {
                Util.offerFirstTemporaryDirectBuffer(byteBuffer2);
            }
        }
        throw new IllegalArgumentException("Read-only buffer");
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static long read(FileDescriptor object, ByteBuffer[] object2, int n, int n2, NativeDispatcher nativeDispatcher) throws IOException {
        IOVecWrapper iOVecWrapper;
        void var0_3;
        block28 : {
            long l;
            int n3;
            int n4;
            long l2;
            iOVecWrapper = IOVecWrapper.get(n2);
            int n5 = n + n2;
            n2 = 0;
            while (n < n5) {
                block26 : {
                    block27 : {
                        if (n2 >= IOV_MAX) break;
                        ByteBuffer byteBuffer = object2[n];
                        if (byteBuffer.isReadOnly()) break block26;
                        int n6 = byteBuffer.position();
                        n4 = n6 <= (n4 = byteBuffer.limit()) ? (n4 -= n6) : 0;
                        n3 = n2;
                        if (n4 <= 0) break block27;
                        iOVecWrapper.setBuffer(n2, byteBuffer, n6, n4);
                        ByteBuffer byteBuffer2 = byteBuffer;
                        n3 = n6;
                        if (!(byteBuffer instanceof DirectBuffer)) {
                            byteBuffer = Util.getTemporaryDirectBuffer(n4);
                            iOVecWrapper.setShadow(n2, byteBuffer);
                            byteBuffer2 = byteBuffer;
                            n3 = byteBuffer.position();
                        }
                        iOVecWrapper.putBase(n2, ((DirectBuffer)((Object)byteBuffer2)).address() + (long)n3);
                        iOVecWrapper.putLen(n2, n4);
                        n3 = n2 + 1;
                    }
                    ++n;
                    n2 = n3;
                    continue;
                }
                object = new IllegalArgumentException("Read-only buffer");
                throw object;
            }
            if (n2 == 0) {
                if (!false) {
                    for (n = 0; n < n2; ++n) {
                        object = iOVecWrapper.getShadow(n);
                        if (object != null) {
                            Util.offerLastTemporaryDirectBuffer((ByteBuffer)object);
                        }
                        iOVecWrapper.clearRefs(n);
                    }
                }
                return 0L;
            }
            try {
                l = iOVecWrapper.address;
            }
            catch (Throwable throwable) {
                break block28;
            }
            try {
                l = l2 = nativeDispatcher.readv((FileDescriptor)object, l, n2);
                n4 = n5;
                for (n3 = 0; n3 < n2; ++n3) {
                    object2 = iOVecWrapper.getShadow(n3);
                    if (l > 0L) {
                        object = iOVecWrapper.getBuffer(n3);
                        n5 = iOVecWrapper.getRemaining(n3);
                        if (l <= (long)n5) {
                            n5 = (int)l;
                        }
                        if (object2 == null) {
                            ((ByteBuffer)object).position(iOVecWrapper.getPosition(n3) + n5);
                        } else {
                            ((ByteBuffer)object2).limit(((Buffer)object2).position() + n5);
                            ((ByteBuffer)object).put((ByteBuffer)object2);
                        }
                        l -= (long)n5;
                    }
                    if (object2 != null) {
                        Util.offerLastTemporaryDirectBuffer((ByteBuffer)object2);
                    }
                    iOVecWrapper.clearRefs(n3);
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (!true) {
                for (n = 0; n < n2; ++n) {
                    object = iOVecWrapper.getShadow(n);
                    if (object != null) {
                        Util.offerLastTemporaryDirectBuffer((ByteBuffer)object);
                    }
                    iOVecWrapper.clearRefs(n);
                }
            }
            return l2;
        }
        if (!false) {
            for (n = 0; n < n2; ++n) {
                object2 = iOVecWrapper.getShadow(n);
                if (object2 != null) {
                    Util.offerLastTemporaryDirectBuffer((ByteBuffer)object2);
                }
                iOVecWrapper.clearRefs(n);
            }
        }
        throw var0_3;
    }

    static long read(FileDescriptor fileDescriptor, ByteBuffer[] arrbyteBuffer, NativeDispatcher nativeDispatcher) throws IOException {
        return IOUtil.read(fileDescriptor, arrbyteBuffer, 0, arrbyteBuffer.length, nativeDispatcher);
    }

    private static int readIntoNativeBuffer(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l, NativeDispatcher nativeDispatcher) throws IOException {
        int n;
        int n2 = byteBuffer.position();
        n = n2 <= (n = byteBuffer.limit()) ? (n -= n2) : 0;
        if (n == 0) {
            return 0;
        }
        if (l != -1L) {
            long l2 = ((DirectBuffer)((Object)byteBuffer)).address();
            n = nativeDispatcher.pread(fileDescriptor, (long)n2 + l2, n, l);
        } else {
            n = nativeDispatcher.read(fileDescriptor, ((DirectBuffer)((Object)byteBuffer)).address() + (long)n2, n);
        }
        if (n > 0) {
            byteBuffer.position(n2 + n);
        }
        return n;
    }

    static native void setfdVal(FileDescriptor var0, int var1);

    static int write(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l, NativeDispatcher nativeDispatcher) throws IOException {
        ByteBuffer byteBuffer2;
        int n;
        block5 : {
            if (byteBuffer instanceof DirectBuffer) {
                return IOUtil.writeFromNativeBuffer(fileDescriptor, byteBuffer, l, nativeDispatcher);
            }
            int n2 = byteBuffer.position();
            n = n2 <= (n = byteBuffer.limit()) ? (n -= n2) : 0;
            byteBuffer2 = Util.getTemporaryDirectBuffer(n);
            byteBuffer2.put(byteBuffer);
            byteBuffer2.flip();
            byteBuffer.position(n2);
            n = IOUtil.writeFromNativeBuffer(fileDescriptor, byteBuffer2, l, nativeDispatcher);
            if (n <= 0) break block5;
            byteBuffer.position(n2 + n);
        }
        return n;
        finally {
            Util.offerFirstTemporaryDirectBuffer(byteBuffer2);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static long write(FileDescriptor object, ByteBuffer[] object2, int n, int n2, NativeDispatcher nativeDispatcher) throws IOException {
        IOVecWrapper iOVecWrapper;
        void var0_3;
        block24 : {
            int n3;
            long l;
            long l2;
            int n4;
            int n5;
            iOVecWrapper = IOVecWrapper.get(n2);
            int n6 = n + n2;
            n2 = 0;
            while (n < n6) {
                block23 : {
                    if (n2 >= IOV_MAX) break;
                    ByteBuffer byteBuffer = object2[n];
                    n3 = byteBuffer.position();
                    n5 = n3 <= (n5 = byteBuffer.limit()) ? (n5 -= n3) : 0;
                    n4 = n2;
                    if (n5 <= 0) break block23;
                    iOVecWrapper.setBuffer(n2, byteBuffer, n3, n5);
                    ByteBuffer byteBuffer2 = byteBuffer;
                    n4 = n3;
                    if (!(byteBuffer instanceof DirectBuffer)) {
                        ByteBuffer byteBuffer3 = Util.getTemporaryDirectBuffer(n5);
                        byteBuffer3.put(byteBuffer);
                        byteBuffer3.flip();
                        iOVecWrapper.setShadow(n2, byteBuffer3);
                        byteBuffer.position(n3);
                        byteBuffer2 = byteBuffer3;
                        n4 = byteBuffer3.position();
                    }
                    iOVecWrapper.putBase(n2, ((DirectBuffer)((Object)byteBuffer2)).address() + (long)n4);
                    iOVecWrapper.putLen(n2, n5);
                    n4 = n2 + 1;
                }
                ++n;
                n2 = n4;
            }
            if (n2 == 0) {
                if (!false) {
                    for (n = 0; n < n2; ++n) {
                        object = iOVecWrapper.getShadow(n);
                        if (object != null) {
                            Util.offerLastTemporaryDirectBuffer((ByteBuffer)object);
                        }
                        iOVecWrapper.clearRefs(n);
                    }
                }
                return 0L;
            }
            try {
                l2 = iOVecWrapper.address;
            }
            catch (Throwable throwable) {
                break block24;
            }
            try {
                l2 = l = nativeDispatcher.writev((FileDescriptor)object, l2, n2);
                n5 = n;
                n = n6;
                for (n4 = 0; n4 < n2; ++n4) {
                    if (l2 > 0L) {
                        object = iOVecWrapper.getBuffer(n4);
                        n3 = iOVecWrapper.getPosition(n4);
                        n6 = iOVecWrapper.getRemaining(n4);
                        if (l2 <= (long)n6) {
                            n6 = (int)l2;
                        }
                        ((ByteBuffer)object).position(n3 + n6);
                        l2 -= (long)n6;
                    }
                    if ((object = iOVecWrapper.getShadow(n4)) != null) {
                        Util.offerLastTemporaryDirectBuffer((ByteBuffer)object);
                    }
                    iOVecWrapper.clearRefs(n4);
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (!true) {
                for (n = 0; n < n2; ++n) {
                    object = iOVecWrapper.getShadow(n);
                    if (object != null) {
                        Util.offerLastTemporaryDirectBuffer((ByteBuffer)object);
                    }
                    iOVecWrapper.clearRefs(n);
                }
            }
            return l;
        }
        if (!false) {
            for (n = 0; n < n2; ++n) {
                object2 = iOVecWrapper.getShadow(n);
                if (object2 != null) {
                    Util.offerLastTemporaryDirectBuffer((ByteBuffer)object2);
                }
                iOVecWrapper.clearRefs(n);
            }
        }
        throw var0_3;
    }

    static long write(FileDescriptor fileDescriptor, ByteBuffer[] arrbyteBuffer, NativeDispatcher nativeDispatcher) throws IOException {
        return IOUtil.write(fileDescriptor, arrbyteBuffer, 0, arrbyteBuffer.length, nativeDispatcher);
    }

    private static int writeFromNativeBuffer(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l, NativeDispatcher nativeDispatcher) throws IOException {
        int n;
        int n2 = byteBuffer.position();
        n = n2 <= (n = byteBuffer.limit()) ? (n -= n2) : 0;
        if (n == 0) {
            return 0;
        }
        if (l != -1L) {
            long l2 = ((DirectBuffer)((Object)byteBuffer)).address();
            n = nativeDispatcher.pwrite(fileDescriptor, (long)n2 + l2, n, l);
        } else {
            n = nativeDispatcher.write(fileDescriptor, ((DirectBuffer)((Object)byteBuffer)).address() + (long)n2, n);
        }
        if (n > 0) {
            byteBuffer.position(n2 + n);
        }
        return n;
    }
}

